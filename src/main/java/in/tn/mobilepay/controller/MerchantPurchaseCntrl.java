package in.tn.mobilepay.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.tn.mobilepay.services.PurchaseRestService;

@RestController
public class MerchantPurchaseCntrl {

	@Autowired
	private PurchaseRestService purchaseRestService;

	@RequestMapping(value = "/rest/merchant/createPurchase")
	public ResponseEntity<String> createPurchaseData(@RequestBody String requestData, Principal principal) {
		return purchaseRestService.createPurchase(requestData, principal);
	}

	@RequestMapping(value = "/rest/merchant/updateOrderStatus")
	public ResponseEntity<String> updateOrderStatus(@RequestBody String requestData, Principal principal) {
		return purchaseRestService.updateOrderStatus(principal, requestData);
	}

	@RequestMapping(value = "/rest/merchant/getPurchaseList")
	public ResponseEntity<String> getPurchaseList(@RequestParam(required = false) Integer index,
			@RequestParam(required = false) Integer limit, @RequestParam(required = false) String status,
			@RequestParam(required = false) Long fromDate, @RequestParam(required = false) Long toDate,
			Principal principal) {
		return purchaseRestService.getPurchaseList(index, limit, status, fromDate, toDate, principal);
	}
	
	
	@RequestMapping(value = "/rest/merchant/{purchaseUUID}/getPurchaseData")
	public ResponseEntity<String> getPurchaseData(@PathVariable("purchaseUUID") String purchaseUUID, Principal principal) {
		return purchaseRestService.getPurchaseData(purchaseUUID, principal);
	}

}
