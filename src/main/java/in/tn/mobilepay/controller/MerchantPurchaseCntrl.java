package in.tn.mobilepay.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.tn.mobilepay.rest.json.ResponseData;

@RestController
public class MerchantPurchaseCntrl {
	
	@RequestMapping(value="/rest/merchant/createPurchase")
	public ResponseData createPurchaseData(@RequestBody String requestData){
		return null;
	}

}
