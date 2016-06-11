package in.tn.mobilepay.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.tn.mobilepay.response.model.ResponseData;
import in.tn.mobilepay.services.UserServices;

@RestController
public class AccountCntrl {

	@Autowired
	private UserServices userServices;
	
	@RequestMapping(value="/mobile/register")
	public ResponseEntity<String> userRegister(@RequestBody String requestData){
		return userServices.userRegisteration(requestData);
	}
	
	@RequestMapping(value="/mobilePayUser/mobile/updateProfile")
	public ResponseEntity<String> updateProfile(@RequestBody String requestData,Principal principal){
		return userServices.updateUserProfile(requestData,principal);
	}
	
	@RequestMapping(value="/mobilePayUser/mobile/getUserProfile")
	public ResponseEntity<String> getUserProfile(Principal principal){
		return userServices.getUserProfile(principal);
	}
	
	
	
	@RequestMapping(value="/mobile/verifyMobileNo")
	public ResponseEntity<String> verifyMobileNumber(@RequestBody String requestData){
		return userServices.sendOtp(requestData);
	}
	
	@RequestMapping(value="/mobile/otp/validate")
	public ResponseEntity<String> otpValidate(@RequestBody String requestData){
		return userServices.validateOtp(requestData);
	}
	
	@RequestMapping(value="/mobile/login")
	public ResponseEntity<String> loginById(@RequestBody String requestData){
		return userServices.login(requestData);
	}
	
	
	@RequestMapping(value="/mobile/loginByMobileNumber")
	public ResponseEntity<String> loginByMobileNumber(@RequestBody String requestData){
		return userServices.loginByMobileNumber(requestData);
	}
	
	@RequestMapping(value="/mobilePayUser/mobile/syncUserDeliveryAddress")
	public ResponseEntity<String> syncUserDeliveryAddress(@RequestBody String requestData,Principal principal){
		return userServices.syncUserDeliveryAddress(requestData,principal);
	}
	
	
	@RequestMapping(value="/mobilePayUser/mobile/addCloudId")
	public ResponseEntity<String> addCloudId(@RequestBody String requestData,Principal principal){
		return userServices.addCloudToken(requestData,principal);
	}
}
