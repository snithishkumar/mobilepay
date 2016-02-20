package in.tn.mobilepay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.tn.mobilepay.dao.UserDAO;
import in.tn.mobilepay.entity.OtpEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.OtpJson;
import in.tn.mobilepay.request.model.RegisterJson;

@Service
public class UserServices {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	private UserEntity validate(RegisterJson registerJson) throws ValidationException{
		UserEntity dbUserEntity =	userDao.getUserEntity(registerJson.getMobileNumber());
		if(dbUserEntity != null && dbUserEntity.isActive()){
			throw new ValidationException(HttpStatus.EXPECTATION_FAILED, "Mobile Number Already Registered");
		}
		return dbUserEntity;
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> validateOtp(String optValidationData){
		try{
			OtpJson otpJson = serviceUtil.fromJson(optValidationData, OtpJson.class);
			if(otpJson.getMobileNumber() == null || otpJson.getOtpNumber() == null){
				return serviceUtil.getErrorResponse(HttpStatus.EXPECTATION_FAILED, "Not Valid Data");
			}
			OtpEntity  otpEntity = userDao.getOtpEntity(otpJson.getMobileNumber());
			if(otpEntity == null || otpEntity.getOptNumber() == Integer.valueOf(otpJson.getOtpNumber())){
				return serviceUtil.getErrorResponse(HttpStatus.EXPECTATION_FAILED, "Invalid OTP Number");
			}
			otpEntity.setValidationTime(serviceUtil.getCurrentGmtTime());
			userDao.updateOtpEntity(otpEntity);
			UserEntity userEntity = otpEntity.getUserEntity();
			userEntity.setActive(true);
			userDao.updateUser(userEntity);
			return serviceUtil.getSuccessResponse(HttpStatus.OK, "Success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
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
			
			return serviceUtil.getSuccessResponse(HttpStatus.OK, "Success");
		}catch(ValidationException e){
			return serviceUtil.getErrorResponse(e.getCode(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}
	
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> login(String loginData){
		try{
			RegisterJson registerJson = serviceUtil.fromJson(loginData, RegisterJson.class);
			UserEntity dbUserEntity =	userDao.getUserEnity(registerJson.getImei(),registerJson.getPassword());
			if(dbUserEntity == null){
				return serviceUtil.getErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid LoginId");
			}
			return serviceUtil.getSuccessResponse(HttpStatus.OK, "Success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}
	
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> loginByMobileNumber(String loginData){
		try{
			RegisterJson registerJson = serviceUtil.fromJson(loginData, RegisterJson.class);
			UserEntity dbUserEntity =	userDao.getUserEntity(registerJson.getMobileNumber());
			if(dbUserEntity == null){
				return serviceUtil.getErrorResponse(HttpStatus.PRECONDITION_FAILED, "You are not yet register. Please register");
			}
			if(!dbUserEntity.isActive()){
				return serviceUtil.getErrorResponse(HttpStatus.PRECONDITION_FAILED, "Your Account is not yet activate");
			}
			if(dbUserEntity.getLoginId() == Integer.valueOf(registerJson.getPassword())){
				String token = serviceUtil.generateLoginToken();
				dbUserEntity.setToken(token);
				userDao.updateUser(dbUserEntity);
				return serviceUtil.getErrorResponse(HttpStatus.OK,token);
			}
			return serviceUtil.getErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid LoginId");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}
}
