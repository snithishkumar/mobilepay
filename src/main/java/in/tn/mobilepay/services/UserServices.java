package in.tn.mobilepay.services;

import in.tn.mobilepay.dao.UserDAO;
import in.tn.mobilepay.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServices {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private ServiceUtil serviceUtil;

	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	private ResponseEntity<String> userRegisteration(String name,String mobileNumber,int loginId){
		try{
			UserEntity dbUserEntity =	userDao.getUserEntity(mobileNumber);
			if(dbUserEntity != null){
				return serviceUtil.getErrorResponse(HttpStatus.EXPECTATION_FAILED, "Mobile Number Already Registered");
			}
			UserEntity userEntity = new UserEntity();
			userEntity.setLoginId(loginId);
			userEntity.setMobileNumber(mobileNumber);
			userEntity.setName(name);
			userDao.createUser(userEntity);
			return serviceUtil.getSuccessResponse(HttpStatus.OK, "Success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}
	
	@Transactional(readOnly = true,propagation=Propagation.REQUIRED)
	private ResponseEntity<String> login(int loginId,String mobileNumber){
		try{
			UserEntity dbUserEntity =	userDao.getUserEntity(mobileNumber);
			if(dbUserEntity == null){
				return serviceUtil.getErrorResponse(HttpStatus.EXPECTATION_FAILED, "Mobile Number Already Registered");
			}
			if(dbUserEntity.getLoginId() == loginId){
				return serviceUtil.getSuccessResponse(HttpStatus.OK, "Success");
			}
			return serviceUtil.getErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid LoginId");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}
}
