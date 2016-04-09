package in.tn.mobilepay.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import in.tn.mobilepay.dao.UserDAO;
import in.tn.mobilepay.entity.AddressEntity;
import in.tn.mobilepay.entity.OtpEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.OtpJson;
import in.tn.mobilepay.request.model.RegisterJson;
import in.tn.mobilepay.response.model.AddressBookJson;
import in.tn.mobilepay.response.model.AddressJson;
import in.tn.mobilepay.util.StatusCode;

@Service
public class UserServices {
	
	@Autowired
	private UserDAO userDao;
	
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
			OtpJson otpJson = serviceUtil.fromJson(requestData, OtpJson.class);
			if(otpJson.getMobileNumber() == null){
				return serviceUtil.getResponse(StatusCode.MOB_VAL_INVALID, "Not Valid Data");
			}
			OtpEntity  otpEntity = userDao.getOtpEntity(otpJson.getMobileNumber());
			if(otpEntity != null){
				
			}else{
				otpEntity = new OtpEntity();
				otpEntity.setMobileNumber(otpJson.getMobileNumber());
				userDao.createOtp(otpEntity);
			}
			return serviceUtil.getResponse(StatusCode.MOB_VAL_OK, "Success");
		}catch(Exception e){
			
		}
		return serviceUtil.getResponse(StatusCode.MOB_VAL_INTERNAL_ERROR, "Failure");
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> validateOtp(String optValidationData){
		try{
			OtpJson otpJson = serviceUtil.fromJson(optValidationData, OtpJson.class);
			/*OtpJson otpJson = serviceUtil.fromJson(optValidationData, OtpJson.class);
			if(otpJson.getMobileNumber() == null || otpJson.getOtpNumber() == null){
				return serviceUtil.getResponse(StatusCode.INVALID_OTP, "Not Valid Data");
			}
			OtpEntity  otpEntity = userDao.getOtpEntity(otpJson.getMobileNumber());
			if(otpEntity == null || otpEntity.getOptNumber() == Integer.valueOf(otpJson.getOtpNumber())){
				return serviceUtil.getResponse(StatusCode.INVALID_OTP, "Invalid OTP Number");
			}
			otpEntity.setValidationTime(serviceUtil.getCurrentGmtTime());
			userDao.updateOtpEntity(otpEntity);*/
		/*	UserEntity userEntity = userDao.getUserEntity(otpJson.getMobileNumber());
			//UserEntity userEntity = otpEntity.getUserEntity();
			userEntity.setActive(true);
			userDao.updateUser(userEntity);*/
			return serviceUtil.getResponse(StatusCode.OTP_OK, "Success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.OTP_INTERNAL_ERROR, "Failure");
	}
	
	private UserEntity validateUserToken(String client,String serverToken) throws ValidationException{
		UserEntity userEntity = userDao.getUserEnityByToken(client, serverToken);
		if(userEntity == null){
			throw new ValidationException(10, "Invalid User", null);
		}
		return userEntity;
		
	}
	
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> updateUserProfile(String registerData){
		try{
			//String register = serviceUtil.netDecryption(registerData);
			RegisterJson registerJson = serviceUtil.fromJson(registerData, RegisterJson.class);
			UserEntity dbUserEntity = validateUserToken(registerJson.getAccessToken(), registerJson.getServerToken());
			dbUserEntity.setName(registerJson.getName());
			dbUserEntity.setMobileNumber(registerJson.getMobileNumber());
			if(registerJson.getPassword() != null && !registerJson.getPassword().trim().isEmpty()){
				dbUserEntity.setLoginId(Integer.valueOf(registerJson.getPassword()));
			}
			dbUserEntity.toUser(registerJson);
			userDao.updateUser(dbUserEntity);
			
			return serviceUtil.getResponse(StatusCode.REG_OK, "Success");
		}catch(ValidationException e){
			return serviceUtil.getResponse(e.getCode(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.INTERNAL_ERROR, "Failure");
	}

	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> userRegisteration(String registerData){
		try{
			String register = serviceUtil.netDecryption(registerData);
			RegisterJson registerJson = serviceUtil.fromJson(register, RegisterJson.class);
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
		}catch(ValidationException e){
			return serviceUtil.getResponse(e.getCode(), e.getMessage());
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
	public ResponseEntity<String> syncUserDeliveryAddress(String requestData){
		try{
			// Json to  Obj
			AddressBookJson addressBookJson = serviceUtil.fromJson(requestData, AddressBookJson.class);
			// Validate User Request
			UserEntity userEntity = validateUserToken(addressBookJson.getAccessToken(), addressBookJson.getServerToken());
			if(userEntity == null){
				return serviceUtil.getResponse(StatusCode.LOGIN_INVALID_MOBILE, "You are not yet register. Please register");
			}
			
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
			logger.error("Error in syncUserDeliveryAddress", e);
		}
		// Error Response
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Failure");
	}
}
