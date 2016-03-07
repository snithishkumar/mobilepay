package in.tn.mobilepay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonObject;

import in.tn.mobilepay.dao.UserDAO;
import in.tn.mobilepay.entity.OtpEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.OtpJson;
import in.tn.mobilepay.request.model.RegisterJson;
import in.tn.mobilepay.util.StatusCode;

@Service
public class UserServices {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
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

	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> userRegisteration(String registerData){
		try{
			String register = serviceUtil.netDecryption(registerData);
			RegisterJson registerJson = serviceUtil.fromJson(register, RegisterJson.class);
			UserEntity dbUserEntity = validate(registerJson);
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
}
