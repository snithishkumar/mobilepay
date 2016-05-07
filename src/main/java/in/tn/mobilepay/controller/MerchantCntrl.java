package in.tn.mobilepay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.tn.mobilepay.services.MerchantServices;

@RestController
public class MerchantCntrl {
	
	@Autowired
	private MerchantServices merchantServices;
	
	@RequestMapping(value = "/merchant/signup")
	public ResponseEntity<String> signup(@RequestPart("file") MultipartFile multipartFile,
			@RequestParam(value = "merchantName") String merchantName,
			@RequestParam(value = "merchantAddress") String merchantAddress, @RequestParam(value = "area") String area,
			@RequestParam(value = "pinCode") String pinCode, @RequestParam(value = "mobileNumber") String mobileNumber,
			@RequestParam(value = "landLineNumber") String landLineNumber,
			@RequestParam(value = "category") String category, @RequestParam(value = "password") String password) {
		return merchantServices.merchantRegsiteration(multipartFile, merchantName, merchantAddress, area, pinCode,
				mobileNumber, landLineNumber, category, password);
	}
	
	
	@RequestMapping(value = "/merchant/register")
	public ResponseEntity<String> register(@RequestBody String requestData) {
		return merchantServices.merchantRegister(requestData);
	}
	
	
	@RequestMapping(value="/merchant/login")
	public ResponseEntity<String> login(@RequestBody String requestData){
		return merchantServices.merchantLogin(requestData);
	}
	
	
	@RequestMapping(value="/user/merchant/profilepic")
	public ResponseEntity getProfilePic(@RequestParam String merchantGuid,@RequestParam String merchantId){
		return merchantServices.getShopLogo(merchantGuid,merchantId);
	}

}
