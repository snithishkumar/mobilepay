package in.tn.mobilepay.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.tn.mobilepay.services.UserServices;

@RestController
public class AccountCntrl {
	
	
	private static Logger synLogger = Logger.getLogger("sync");

	@Autowired
	private UserServices userServices;
	
	@RequestMapping(value="/mobile/register")
	public ResponseEntity<String> userRegister(@RequestBody String requestData){
		ResponseEntity<String> responseEntity =  userServices.userRegisteration(requestData);
		String responseData = responseEntity.getBody();
		synLogger.info(requestData);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	@RequestMapping(value="/mobilePayUser/mobile/updateProfile")
	public ResponseEntity<String> updateProfile(@RequestBody String requestData,Principal principal){
		ResponseEntity<String> responseEntity = userServices.updateUserProfile(requestData,principal);
		String responseData = responseEntity.getBody();
		synLogger.info(principal);
		synLogger.info(requestData);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	@RequestMapping(value="/mobilePayUser/mobile/getUserProfile")
	public ResponseEntity<String> getUserProfile(Principal principal){
		ResponseEntity<String> responseEntity = userServices.getUserProfile(principal);
		String responseData = responseEntity.getBody();
		synLogger.info(principal);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	
	@RequestMapping(value="/mobile/getUserProfile/{mobileNumber}")
	public ResponseEntity<String> getUserProfile(@PathVariable("mobileNumber")String mobileNumber){
		ResponseEntity<String> responseEntity =  userServices.getUserProfile(mobileNumber);
		String responseData = responseEntity.getBody();
		synLogger.info(mobileNumber);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	
	
	@RequestMapping(value="/mobile/verifyMobileNo")
	public ResponseEntity<String> verifyMobileNumber(@RequestBody String requestData){
		ResponseEntity<String> responseEntity = userServices.sendOtp(requestData);
		String responseData = responseEntity.getBody();
		synLogger.info(requestData);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	@RequestMapping(value="/mobile/otp/validate")
	public ResponseEntity<String> otpValidate(@RequestBody String requestData){
		ResponseEntity<String> responseEntity = userServices.validateOtp(requestData);
		String responseData = responseEntity.getBody();
		synLogger.info(requestData);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	@RequestMapping(value="/mobile/login")
	public ResponseEntity<String> loginById(@RequestBody String requestData){
		return userServices.login(requestData);
	}
	
	
	@RequestMapping(value="/mobile/loginByMobileNumber")
	public ResponseEntity<String> loginByMobileNumber(@RequestBody String requestData){
		ResponseEntity<String> responseEntity =  userServices.loginByMobileNumber(requestData);
		String responseData = responseEntity.getBody();
		synLogger.info(requestData);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	@RequestMapping(value="/mobilePayUser/mobile/syncUserDeliveryAddress")
	public ResponseEntity<String> syncUserDeliveryAddress(@RequestBody String requestData,Principal principal){
		ResponseEntity<String> responseEntity =  userServices.syncUserDeliveryAddress(requestData,principal);
		String responseData = responseEntity.getBody();
		synLogger.info(requestData);
		synLogger.info(principal);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	
	@RequestMapping(value = "/mobilePayUser/mobile/addCloudId")
	public ResponseEntity<String> addCloudId(@RequestBody String requestData, Principal principal) {
		ResponseEntity<String> responseEntity = userServices.addCloudToken(requestData, principal);
		String responseData = responseEntity.getBody();
		synLogger.info(requestData);
		synLogger.info(responseData);
		return responseEntity;
	}
}
