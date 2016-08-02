package in.tn.mobilepay.services;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import in.tn.mobilepay.dao.UserDAO;
import in.tn.mobilepay.entity.AddressEntity;
import in.tn.mobilepay.entity.CloudMessageEntity;
import in.tn.mobilepay.entity.OtpEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.CloudMessageJson;
import in.tn.mobilepay.request.model.OtpJson;
import in.tn.mobilepay.request.model.RegisterJson;
import in.tn.mobilepay.response.model.AddressBookJson;
import in.tn.mobilepay.response.model.AddressJson;
import in.tn.mobilepay.response.model.OTPData;
import in.tn.mobilepay.response.model.OTPResponse;
import in.tn.mobilepay.util.StatusCode;

@Service
public class UserServices {

	@Autowired
	private UserDAO userDAOImpl;

	@Autowired
	private Gson gson;

	@Autowired
	private ServiceUtil serviceUtil;

	private static final Logger logger = Logger.getLogger(UserServices.class);

	private UserEntity validate(RegisterJson registerJson) throws ValidationException {
		UserEntity dbUserEntity = userDAOImpl.getUserEntity(registerJson.getMobileNumber());
		if (dbUserEntity != null && dbUserEntity.isActive()) {
			throw new ValidationException(StatusCode.INVALID_MOBILE, "Mobile Number Already Registered");
		}
		return dbUserEntity;
	}

	/**
	 * Send OTP to the given mobile Number
	 * 
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> sendOtp(String requestData) {
		try {
			// Json to Object
			OtpJson otpJson = serviceUtil.fromJson(requestData, OtpJson.class);
			// Check whether mobile number is present or not.
			if (otpJson.getMobileNumber() == null) {
				return serviceUtil.getResponse(StatusCode.MOB_VAL_INVALID, "Not Valid Data");
			}
			// Send OTP Password to the given mobile
			OTPResponse otpResponse = sendOtpPassword(otpJson.getMobileNumber());
			// Check the OTP status from OTP provider
			if (otpResponse.getStatus().equals("success")
					&& otpResponse.getResponse().getCode().equals("OTP_SENT_SUCCESSFULLY")) {
				String otpPassword = otpResponse.getResponse().getOneTimePassword();
				// If OTP Entity is present, it will update otherwise it will
				// create.
				OtpEntity otpEntity = userDAOImpl.getOtpEntity(otpJson.getMobileNumber());
				if (otpEntity != null) {
					otpEntity.setOptNumber(otpPassword);
					otpEntity.setCreatedDateTime(ServiceUtil.getCurrentGmtTime());
					userDAOImpl.updateOtpEntity(otpEntity);
				} else {
					otpEntity = new OtpEntity();
					otpEntity.setMobileNumber(otpJson.getMobileNumber());
					otpEntity.setOptNumber(otpPassword);
					otpEntity.setCreatedDateTime(ServiceUtil.getCurrentGmtTime());
					userDAOImpl.createOtp(otpEntity);
				}
				// Success response
				return serviceUtil.getResponse(StatusCode.MOB_VAL_OK, "Success");
			} else {
				// Failure response
				return serviceUtil.getResponse(StatusCode.OTP_ERROR, "Failure");
			}

		} catch (Exception e) {
			logger.error("Raw Data[" + requestData + "]");
			logger.error("Error in sendOtp - UserServices", e);
		}
		return serviceUtil.getResponse(StatusCode.MOB_VAL_INTERNAL_ERROR, "Failure");
	}

	/**
	 * Sent OTP password to the given number
	 * 
	 * @param mobileNumber
	 * @return
	 */
	private OTPResponse sendOtpPassword(String mobileNumber) {
		/*
		 * JsonObject jsonObject = new JsonObject();
		 * jsonObject.addProperty("countryCode", "91");
		 * jsonObject.addProperty("mobileNumber", mobileNumber);
		 * jsonObject.addProperty("getGeneratedOTP", true);
		 * 
		 * MultiValueMap<String, String> headers = new
		 * LinkedMultiValueMap<String, String>(); headers.add("Content-Type",
		 * "application/json"); headers.add("application-Key",
		 * "yOFpqvU7yea9fWq3SvA1BcE-O9SNvvVXW5Cdqgft53YOO9aDlUlSeR4i7W5o1zjZo0GMhA6np5JNaka4jVYip3W0vD1Tpy9uod5NJpVm6JeVPvE6IPiemhKl6q44dmTrgZifGruhn4ddS3aSZpiDjA=="
		 * );
		 * 
		 * RestTemplate restTemplate = new RestTemplate();
		 * HttpEntity<JsonObject> request = new HttpEntity<>(jsonObject,
		 * headers); OTPResponse otpResponse = restTemplate.postForObject(
		 * "https://sendotp.msg91.com/api/generateOTP", request,
		 * OTPResponse.class); return otpResponse;
		 */
		OTPResponse otpResponse = new OTPResponse();
		otpResponse.setStatus("success");
		OTPData otpData = new OTPData();
		otpData.setCode("OTP_SENT_SUCCESSFULLY");
		otpData.setOneTimePassword("123");
		otpResponse.setResponse(otpData);
		return otpResponse;
	}

	/**
	 * Check whether given OTP is correct or wrong
	 * 
	 * @param optValidationData
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> validateOtp(String optValidationData) {
		try {
			// Json to Object
			OtpJson otpJson = serviceUtil.fromJson(optValidationData, OtpJson.class);

			if (otpJson.getMobileNumber() == null || otpJson.getOtpPassword() == null) {
				return serviceUtil.getResponse(StatusCode.INVALID_OTP, "Not Valid Data");
			}
			// Get OTP entity by Mobile Number
			OtpEntity otpEntity = userDAOImpl.getOtpEntity(otpJson.getMobileNumber());
			// Validate given otp number
			if (otpEntity == null || !otpEntity.getOptNumber().equals(otpJson.getOtpPassword())) {
				return serviceUtil.getResponse(StatusCode.INVALID_OTP, "Invalid OTP Number");
			}
			// Check whether given otp is expired or not.
			// -- TODO Need to handle this in mobile side
			if (ServiceUtil.getCurrentGmtTime() - otpEntity.getCreatedDateTime() > (1000 * 60 * 10)) {
				return serviceUtil.getResponse(StatusCode.OTP_EXPIRED, "OTP Expired");
			}
			userDAOImpl.deleteOtpEntity(otpEntity);

			return serviceUtil.getResponse(StatusCode.OTP_OK, "Success");
		} catch (Exception e) {
			logger.error("Raw Data[" + optValidationData + "]");
			logger.error("Error in validateOtp", e);
		}
		return serviceUtil.getResponse(StatusCode.OTP_INTERNAL_ERROR, "Failure");
	}

	
    /**
     * Update User Profile data
     * @param registerData
     * @param principal
     * @return
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> updateUserProfile(String registerData, Principal principal) {
		try {
			// String register = serviceUtil.netDecryption(registerData);
			RegisterJson registerJson = serviceUtil.fromJson(registerData, RegisterJson.class);

			UserEntity dbUserEntity = serviceUtil.getUserEntity(principal);

			dbUserEntity.setName(registerJson.getName());
			dbUserEntity.setMobileNumber(registerJson.getMobileNumber());
			if (registerJson.getPassword() != null && !registerJson.getPassword().trim().isEmpty()) {
				dbUserEntity.setLoginId(Integer.valueOf(registerJson.getPassword()));
			}
			dbUserEntity.toUser(registerJson);
			userDAOImpl.updateUser(dbUserEntity);

			return serviceUtil.getResponse(StatusCode.REG_OK, "Success");
		} catch (Exception e) {
			logger.error("Raw Data[" + registerData + "],["+principal+"]");
			logger.error("Error in validateOtp", e);
		}
		return serviceUtil.getResponse(StatusCode.INTERNAL_ERROR, "Failure");
	}

	/**
	 * Get User Profile based on mobile number.
	 * @param mobileNumber
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getUserProfile(String mobileNumber) {
		try {
			// Get User based on mobile number
			UserEntity dbUserEntity = userDAOImpl.getUserEntity(mobileNumber);
			if (dbUserEntity != null) {
				RegisterJson registerJson = new RegisterJson();
				registerJson.setEmail(dbUserEntity.getEmail());
				registerJson.setName(dbUserEntity.getName());
				registerJson.setImei(dbUserEntity.getImeiNumber());
				registerJson.setMobileNumber(dbUserEntity.getMobileNumber());
				// -- TODO do we need really this? gson
				String profileData = gson.toJson(registerJson);
				return serviceUtil.getResponse(StatusCode.PROFILE_OK, profileData);
			}
			return serviceUtil.getResponse(StatusCode.INVALID_MOBILE, "");
		} catch (Exception e) {
			logger.error("Raw Data[" + mobileNumber + "]");
			logger.error("Error in getUserProfile", e);
		}
		return serviceUtil.getResponse(StatusCode.INTERNAL_ERROR, "Failure");
	}

	/**
	 * Get User Profile data
	 * @param principal
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getUserProfile(Principal principal) {
		try {
			// Get User object based on access token
			UserEntity dbUserEntity = serviceUtil.getUserEntity(principal);
			// If user present, then convert into json object
			if (dbUserEntity != null) {
				RegisterJson registerJson = new RegisterJson();
				registerJson.setEmail(dbUserEntity.getEmail());
				registerJson.setName(dbUserEntity.getName());
				registerJson.setImei(dbUserEntity.getImeiNumber());
				registerJson.setMobileNumber(dbUserEntity.getMobileNumber());
				// -- TODO do we need really this?
				String profileData = gson.toJson(registerJson);
				return serviceUtil.getResponse(StatusCode.PROFILE_OK, profileData);
			}
			return serviceUtil.getResponse(StatusCode.INVALID_MOBILE, "");

		} catch (Exception e) {
			logger.error("Raw Data[" + principal + "]");
			logger.error("Error in getUserProfile", e);
		}
		return serviceUtil.getResponse(StatusCode.INTERNAL_ERROR, "Failure");
	}

	/**
	 * Register User Data.Check whether any user is present or not based on
	 * mobile number.If User is present, then update otherwise it will create
	 * new user
	 * 
	 * @param registerData
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> userRegisteration(String registerData) {
		try {
			RegisterJson registerJson = serviceUtil.fromJson(registerData, RegisterJson.class);
			// Get UserEntity by Mobile Number
			UserEntity dbUserEntity = userDAOImpl.getUserEntity(registerJson.getMobileNumber());
			// If User is not present, then it will create new User
			if (dbUserEntity == null) {
				dbUserEntity = new UserEntity();
				dbUserEntity.toUser(registerJson);
				userDAOImpl.createUser(dbUserEntity);
			} else { // Otherwise, it will update
				dbUserEntity.toUser(registerJson);
				userDAOImpl.updateUser(dbUserEntity);
			}

			return serviceUtil.getResponse(StatusCode.REG_OK, "Success");
		} catch (Exception e) {
			logger.error("Raw Data[" + registerData + "]");
			logger.error("Error in userRegisteration", e);
		}
		return serviceUtil.getResponse(StatusCode.INTERNAL_ERROR, "Failure");
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> login(String loginData) {
		try {
			RegisterJson registerJson = serviceUtil.fromJson(loginData, RegisterJson.class);
			UserEntity dbUserEntity = userDAOImpl.getUserEnity(registerJson.getImei(), registerJson.getPassword());
			if (dbUserEntity == null) {
				return serviceUtil.getResponse(StatusCode.LOGIN_INVALID_PIN, "Invalid LoginId");
			}
			return serviceUtil.getResponse(StatusCode.LOGIN_OK, "Success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.LOGIN_INTERNAL_ERROR, "Internal Error");
	}

	/**
	 * User authentication
	 * @param loginData
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> loginByMobileNumber(String loginData) {
		try {
			RegisterJson registerJson = serviceUtil.fromJson(loginData, RegisterJson.class);
			// Get User Entity based on Mobile Number
			UserEntity dbUserEntity = userDAOImpl.getUserEntity(registerJson.getMobileNumber());
			if (dbUserEntity == null) {
				return serviceUtil.getResponse(StatusCode.LOGIN_INVALID_MOBILE,
						"You are not yet register. Please register");
			}
			
			if (dbUserEntity.getLoginId() == Integer.valueOf(registerJson.getPassword())) {
				// Generate Access token
				String accessToken = serviceUtil.generateLoginToken();
				String serverToken = serviceUtil.generateLoginToken();
				dbUserEntity.setAccessToken(accessToken);
				dbUserEntity.setServerToken(serverToken);
				
				userDAOImpl.updateUser(dbUserEntity);
				JsonObject res = new JsonObject();
				res.addProperty("serverToken", serverToken);
				res.addProperty("accessToken", accessToken);
				return serviceUtil.getResponse(StatusCode.LOGIN_OK, res.toString());
			}
			return serviceUtil.getResponse(StatusCode.LOGIN_INVALID_PIN, "Invalid LoginId");
		} catch (Exception e) {
			logger.error("Raw Data[" + loginData + "]");
			logger.error("Error in loginByMobileNumber", e);
		}
		return serviceUtil.getResponse(StatusCode.LOGIN_INTERNAL_ERROR, "Failure");
	}

	/**
	 * Create or Update given address details and send back address list
	 * 
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> syncUserDeliveryAddress(String requestData, Principal principal) {
		try {
			// Json to Obj
			AddressBookJson addressBookJson = serviceUtil.fromJson(requestData, AddressBookJson.class);

			UserEntity userEntity = serviceUtil.getUserEntity(principal);

			// Get Address List to send back to device
			List<AddressEntity> dbAddressList = userDAOImpl.getAddressList(addressBookJson.getLastModifiedTime(),
					userEntity);

			/**
			 * Check address is already present in DB or not. If not present,
			 * then create new record. Suppose, if its present then check last
			 * modified time
			 */
			for (AddressJson addressJson : addressBookJson.getAddressList()) {
				AddressEntity addressEntity = userDAOImpl.getAddressEntity(addressJson.getAddressUUID(), userEntity);
				if (addressEntity == null) {
					addressEntity = new AddressEntity(addressJson);
					addressEntity.setUserEntity(userEntity);
					userDAOImpl.createAddressEntity(addressEntity);

				} else if (addressJson.getLastModifiedTime() > addressEntity.getLastModifiedTime()) {
					addressEntity.toAddress(addressJson);
					userDAOImpl.updateAddressEntity(addressEntity);
				}
			}
			/**
			 * Entity to Json Object Conversion
			 */
			AddressBookJson responseAddressBook = new AddressBookJson();
			for (AddressEntity addressEntity : dbAddressList) {
				AddressJson addressJson = new AddressJson(addressEntity);
				responseAddressBook.getAddressList().add(addressJson);
			}
			// Response
			return serviceUtil.getResponse(StatusCode.MER_OK, responseAddressBook);
		} catch (Exception e) {
			logger.error("Raw Data[" + requestData + "],["+principal+"]");
			logger.error("Error in syncUserDeliveryAddress", e);
		}
		// Error Response
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Failure");
	}

	/**
	 * Add Cloud id
	 * 
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> addCloudToken(String requestData, Principal principal) {
		try {
			// Json to Object
			CloudMessageJson cloudMessageJson = serviceUtil.fromJson(requestData, CloudMessageJson.class);

			UserEntity userEntity = serviceUtil.getUserEntity(principal);

			//userDao.removeCloudMessageEntity(userEntity, cloudMessageJson.getImeiNumber());

			// Get CloudMessageEntity for this user
			CloudMessageEntity cloudMessageEntity = userDAOImpl.getCloudMessageEntity(userEntity);
			// If its present, then update otherwise need to create
			if (cloudMessageEntity != null) {
				cloudMessageEntity.toCloudMessageEntity(cloudMessageJson);
				userDAOImpl.updateCloudMessageEntity(cloudMessageEntity);
			} else {
				cloudMessageEntity = new CloudMessageEntity(cloudMessageJson);
				cloudMessageEntity.setUserEntity(userEntity);
				userDAOImpl.saveCloudMessageEntity(cloudMessageEntity);
			}
			// Response
			return serviceUtil.getResponse(StatusCode.MER_OK, "success");
		} catch (Exception e) {
			logger.error("Raw Data[" + requestData + "]");
			logger.error("Error in addCloudToken", e);
		}
		return serviceUtil.getResponse(StatusCode.INTERNAL_ERROR, "failure");
	}
}
