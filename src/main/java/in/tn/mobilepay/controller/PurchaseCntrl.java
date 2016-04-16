package in.tn.mobilepay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.tn.mobilepay.services.PurchaseServices;

@RestController
public class PurchaseCntrl {
	
	@Autowired
	private PurchaseServices purchaseServices;
	
	@RequestMapping(value="/mobile/getPurchaseDetails")
	public ResponseEntity<String> getPurchaseDetails(@RequestParam int purchaseId){
		return purchaseServices.getPurchaseDetails(purchaseId);
	}
	
	/*@RequestMapping(value="/mobile/updatePurchaseDetails")
	public ResponseEntity<String> updatePurchaseDetails(@RequestBody String requestData){
		return purchaseServices.updatePurchaseDetails(requestData);
	}*/
	
	
	@RequestMapping(value="/merchant/createPurchase")
	public ResponseEntity<String> createPurchase(@RequestBody String requestData){
		return purchaseServices.createPurchase(requestData);
	}
	
	@RequestMapping(value="/merchant/discardPurchase")
	public ResponseEntity<String> discardPurchase(@RequestBody String requestData){
		return purchaseServices.discardPurchase(requestData);
	}
	

	@RequestMapping(value="/mobile/getPurchaseList")
	public ResponseEntity<String> getPurchaseList(@RequestBody String requestData){
		return purchaseServices.getPurchaseList(requestData);
	}
	
	@RequestMapping(value="/mobile/getPurchaseHistoryList")
	public ResponseEntity<String> getPurchaseHistoryList(@RequestBody String requestData){
		return purchaseServices.getPurchaseHistoryList(requestData);
	}
	
	@RequestMapping(value="/mobile/getLuggageList")
	public ResponseEntity<String> getLuggageList(@RequestBody String requestData){
		return purchaseServices.getLuggageList(requestData);
	}
	
	@RequestMapping(value="/mobile/syncDiscardData")
	public ResponseEntity<String> discardPurchaseByUser(@RequestBody String requestData){
		return purchaseServices.discardPurchaseByUser(requestData);
	}

}
