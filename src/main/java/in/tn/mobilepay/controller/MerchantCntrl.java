package in.tn.mobilepay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.tn.mobilepay.services.MerchantServices;

@RestController
public class MerchantCntrl {
	
	@Autowired
	private MerchantServices merchantServices;
	
	@RequestMapping(value="/merchant/signup")
	public ResponseEntity<String> signup(@RequestBody String requestData){
		return merchantServices.merchantRegister(requestData);
	}
	
	
	@RequestMapping(value="/merchant/login")
	public ResponseEntity<String> login(@RequestBody String requestData){
		return merchantServices.merchantLogin(requestData);
	}

}
