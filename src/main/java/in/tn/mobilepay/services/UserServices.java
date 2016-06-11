package in.tn.mobilepay.services;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import in.tn.mobilepay.dao.impl.UserDAOImpl;
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
	private UserDAOImpl userDao;
	
	@Autowired
	private Gson gson;
	
	
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	private static final Logger logger = Logger.getLogger(UserServices.class);
	
	private UserEntity validate(RegisterJson registerJson) throws ValidationException{
		UserEntity dbUserEntity =	userDao.getUserEntity(registerJson.getMobileNumber());
		if(dbUserEntity != null && dbUserEntity.isActive()){
			throw new ValidationException(StatusCode.INVALID_MOBILE, "Mobile Number Already Registered");
		}
		return dbUserEntity;
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> sendOtp(String requestData){
		try{
			// Json to Object
			OtpJson otpJson = serviceUtil.fromJson(requestData, OtpJson.class);
			if(otpJson.getMobileNumber() == null){
				return serviceUtil.getResponse(StatusCode.MOB_VAL_INVALID, "Not Valid Data");
			}
			OTPResponse otpResponse = sendOtpPassword(otpJson.getMobileNumber());
			if(otpResponse.getStatus().equals("success") && otpResponse.getResponse().getCode().equals("OTP_SENT_SUCCESSFULLY")){
				String otpPassword = otpResponse.getResponse().getOneTimePassword();
				OtpEntity  otpEntity = userDao.getOtpEntity(otpJson.getMobileNumber());
				if(otpEntity != null){
					otpEntity.setOptNumber(otpPassword);
					otpEntity.setCreatedDateTime(ServiceUtil.getCurrentGmtTime());
					userDao.updateOtpEntity(otpEntity);
				}else{
					otpEntity = new OtpEntity();
					otpEntity.setMobileNumber(otpJson.getMobileNumber());
					otpEntity.setOptNumber(otpPassword);
					otpEntity.setCreatedDateTime(ServiceUtil.getCurrentGmtTime());
					userDao.createOtp(otpEntity);
				}
				return serviceUtil.getResponse(StatusCode.MOB_VAL_OK, "Success");
			}else{
				return serviceUtil.getResponse(StatusCode.OTP_ERROR, "Failure");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.MOB_VAL_INTERNAL_ERROR, "Failure");
	}
	
	
	
	
	private OTPResponse sendOtpPassword(String mobileNumber){
		/*JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("countryCode", "91");
		jsonObject.addProperty("mobileNumber", mobileNumber);
		jsonObject.addProperty("getGeneratedOTP", true);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("application-Key", "yOFpqvU7yea9fWq3SvA1BcE-O9SNvvVXW5Cdqgft53YOO9aDlUlSeR4i7W5o1zjZo0GMhA6np5JNaka4jVYip3W0vD1Tpy9uod5NJpVm6JeVPvE6IPiemhKl6q44dmTrgZifGruhn4ddS3aSZpiDjA==");
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<JsonObject> request = new HttpEntity<>(jsonObject, headers);
		OTPResponse otpResponse = restTemplate.postForObject("https://sendotp.msg91.com/api/generateOTP", request, OTPResponse.class);
		return otpResponse;*/
		OTPResponse otpResponse = new OTPResponse();
		otpResponse.setStatus("success");
		OTPData otpData = new OTPData();
		otpData.setCode("OTP_SENT_SUCCESSFULLY");
		otpData.setOneTimePassword("123");
		otpResponse.setResponse(otpData);
		return otpResponse;
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> validateOtp(String optValidationData){
		try{
			OtpJson otpJson = serviceUtil.fromJson(optValidationData, OtpJson.class);
			if(otpJson.getMobileNumber() == null || otpJson.getOtpPassword() == null){
				return serviceUtil.getResponse(StatusCode.INVALID_OTP, "Not Valid Data");
			}
			OtpEntity  otpEntity = userDao.getOtpEntity(otpJson.getMobileNumber());
			if(otpEntity == null || !otpEntity.getOptNumber().equals(otpJson.getOtpPassword())){
				return serviceUtil.getResponse(StatusCode.INVALID_OTP, "Invalid OTP Number");
			}
			if(ServiceUtil.getCurrentGmtTime() - otpEntity.getCreatedDateTime() > (1000*60*10)){
				return serviceUtil.getResponse(StatusCode.OTP_EXPIRED, "OTP Expired");
			}
			otpEntity.setValidationTime(ServiceUtil.getCurrentGmtTime());
			userDao.deleteOtpEntity(otpEntity);
		
			return serviceUtil.getResponse(StatusCode.OTP_OK, "Success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.OTP_INTERNAL_ERROR, "Failure");
	}
	
	/*private UserEntity validateUserToken(String client,String serverToken) throws ValidationException{
		UserEntity userEntity = userDao.getUserEnityByToken(client, serverToken);
		if(userEntity == null){
			throw new ValidationException(10, "Invalid User", null);
		}
		return userEntity;
		
	}*/
	
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> updateUserProfile(String registerData,Principal principal){
		try{
			//String register = serviceUtil.netDecryption(registerData);
			RegisterJson registerJson = serviceUtil.fromJson(registerData, RegisterJson.class);
			
			UserEntity dbUserEntity = serviceUtil.getUserEntity(principal);
			
			dbUserEntity.setName(registerJson.getName());
			dbUserEntity.setMobileNumber(registerJson.getMobileNumber());
			if(registerJson.getPassword() != null && !registerJson.getPassword().trim().isEmpty()){
				dbUserEntity.setLoginId(Integer.valueOf(registerJson.getPassword()));
			}
			dbUserEntity.toUser(registerJson);
			userDao.updateUser(dbUserEntity);
			
			return serviceUtil.getResponse(StatusCode.REG_OK, "Success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.INTERNAL_ERROR, "Failure");
	}
	
	
	
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getUserProfile(Principal principal){
		try{
			UserEntity dbUserEntity = serviceUtil.getUserEntity(principal);
			RegisterJson registerJson = new RegisterJson();
			registerJson.setEmail(dbUserEntity.getEmail());
			registerJson.setName(dbUserEntity.getName());
			registerJson.setImei(dbUserEntity.getImeiNumber());
			registerJson.setMobileNumber(dbUserEntity.getMobileNumber());
			String profileData = gson.toJson(registerJson);
			return serviceUtil.getResponse(StatusCode.PROFILE_OK, profileData);
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.INTERNAL_ERROR, "Failure");
	}

	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> userRegisteration(String registerData){
		try{
			//String register = serviceUtil.netDecryption(registerData);
			RegisterJson registerJson = serviceUtil.fromJson(registerData, RegisterJson.class);
			UserEntity dbUserEntity =	userDao.getUserEntity(registerJson.getMobileNumber());
			if(dbUserEntity ==  null){
				dbUserEntity = new UserEntity();
				dbUserEntity.toUser(registerJson);
				userDao.createUser(dbUserEntity);
			}else{
				
				dbUserEntity.toUser(registerJson);
				userDao.updateUser(dbUserEntity);
			}
			
			return serviceUtil.getResponse(StatusCode.REG_OK, "Success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.INTERNAL_ERROR, "Failure");
	}
	
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> login(String loginData){
		try{
			RegisterJson registerJson = serviceUtil.fromJson(loginData, RegisterJson.class);
			UserEntity dbUserEntity =	userDao.getUserEnity(registerJson.getImei(),registerJson.getPassword());
			if(dbUserEntity == null){
				return serviceUtil.getResponse(StatusCode.LOGIN_INVALID_PIN, "Invalid LoginId");
			}
			return serviceUtil.getResponse(StatusCode.LOGIN_OK, "Success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.LOGIN_INTERNAL_ERROR, "Internal Error");
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> loginByMobileNumber(String loginData){
		try{
			RegisterJson registerJson = serviceUtil.fromJson(loginData, RegisterJson.class);
			UserEntity dbUserEntity =	userDao.getUserEntity(registerJson.getMobileNumber());
			if(dbUserEntity == null){
				return serviceUtil.getResponse(StatusCode.LOGIN_INVALID_MOBILE, "You are not yet register. Please register");
			}
			/*if(!dbUserEntity.isActive()){
				return serviceUtil.getResponse(HttpStatus.PRECONDITION_FAILED, "Your Account is not yet activate");
			}*/
			if(dbUserEntity.getLoginId() == Integer.valueOf(registerJson.getPassword())){
				String accessToken = serviceUtil.generateLoginToken();
				dbUserEntity.setAccessToken(accessToken);
				String serverToken = serviceUtil.generateLoginToken();
				dbUserEntity.setServerToken(serverToken);
				userDao.updateUser(dbUserEntity);
				JsonObject res = new JsonObject();
				res.addProperty("serverToken", serverToken);
				res.addProperty("accessToken", accessToken);
				return serviceUtil.getResponse(StatusCode.LOGIN_OK,res.toString());
			}
			return serviceUtil.getResponse(StatusCode.LOGIN_INVALID_PIN, "Invalid LoginId");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.LOGIN_INTERNAL_ERROR, "Failure");
	}
	
	/**
	 * Create or Update given address details and send back address list
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> syncUserDeliveryAddress(String requestData,Principal principal){
		try{
			// Json to  Obj
			AddressBookJson addressBookJson = serviceUtil.fromJson(requestData, AddressBookJson.class);
			
			UserEntity userEntity = serviceUtil.getUserEntity(principal);
			
			//Get Address List to send back to device
			List<AddressEntity>  dbAddressList =	userDao.getAddressList(addressBookJson.getLastModifiedTime(), userEntity);
			
			/**
             * Check address is already present in DB or not. If not present, then create new record. Suppose, if its present then check last modified time
             */
			for(AddressJson addressJson : addressBookJson.getAddressList()){
				AddressEntity addressEntity =  userDao.getAddressEntity(addressJson.getAddressUUID(),userEntity);
				if(addressEntity == null){
					addressEntity = new AddressEntity(addressJson);
					addressEntity.setUserEntity(userEntity);
					userDao.createAddressEntity(addressEntity);
					
				}else if(addressJson.getLastModifiedTime() > addressEntity.getLastModifiedTime()){
					addressEntity.toAddress(addressJson);
					userDao.updateAddressEntity(addressEntity);
				}
			}
			/**
			 * Entity to Json Object Conversion
			 */
			AddressBookJson responseAddressBook = new AddressBookJson();
			for(AddressEntity addressEntity : dbAddressList){
				AddressJson addressJson  =new AddressJson(addressEntity);
				responseAddressBook.getAddressList().add(addressJson);
			}
			//Response
			return serviceUtil.getResponse(StatusCode.MER_OK, responseAddressBook);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in syncUserDeliveryAddress", e);
		}
		// Error Response
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Failure");
	}
	
	/**
	 * Add Cloud id
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> addCloudToken(String requestData,Principal principal){
		try{
			//Json to Object
			CloudMessageJson cloudMessageJson = serviceUtil.fromJson(requestData, CloudMessageJson.class);
			
			UserEntity userEntity = serviceUtil.getUserEntity(principal);
			
			
			// Get CloudMessageEntity for this user
			CloudMessageEntity cloudMessageEntity = 	userDao.getCloudMessageEntity(userEntity);
			// If its present, then update otherwise need to create
			if(cloudMessageEntity != null){
				cloudMessageEntity.toCloudMessageEntity(cloudMessageJson);
				userDao.updateCloudMessageEntity(cloudMessageEntity);
			}else{
				cloudMessageEntity = new CloudMessageEntity(cloudMessageJson);
				cloudMessageEntity.setUserEntity(userEntity);
				userDao.saveCloudMessageEntity(cloudMessageEntity);
			}
			//Response
			return serviceUtil.getResponse(StatusCode.MER_OK, "success");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in addCloudToken", e);
		}
		return serviceUtil.getResponse(StatusCode.INTERNAL_ERROR, "failure");
	}
}
