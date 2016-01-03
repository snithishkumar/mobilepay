package in.tn.mobilepay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.tn.mobilepay.dao.UserDAO;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.RegisterJson;

@Service
public class UserServices {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	private void validate(RegisterJson registerJson) throws ValidationException{
		UserEntity dbUserEntity =	userDao.getUserEntity(registerJson.getMobileNumber());
		if(dbUserEntity != null){
			throw new ValidationException(HttpStatus.EXPECTATION_FAILED, "Mobile Number Already Registered");
		}
	}

	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> userRegisteration(String registerData){
		try{
			RegisterJson registerJson = serviceUtil.fromJson(registerData, RegisterJson.class);
			validate(registerJson);
			UserEntity userEntity = new UserEntity();
			userEntity.setLoginId(Integer.valueOf(registerJson.getPassword()));
			userEntity.setMobileNumber(registerJson.getMobileNumber());
			userEntity.setName(registerJson.getName());
			userEntity.setImeiNumber(registerJson.getImei());
			userDao.createUser(userEntity);
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
				return serviceUtil.getErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid LoginId");
			}
			if(dbUserEntity.getLoginId() == Integer.valueOf(registerJson.getPassword())){
				return serviceUtil.getErrorResponse(HttpStatus.OK, "Success");
			}
			return serviceUtil.getErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid LoginId");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}
}
