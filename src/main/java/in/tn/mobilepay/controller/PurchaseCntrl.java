package in.tn.mobilepay.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.tn.mobilepay.services.MerchantPurchaseService;
import in.tn.mobilepay.services.PurchaseServices;

@RestController
public class PurchaseCntrl {
	
	@Autowired
	private PurchaseServices purchaseServices;
	
	@Autowired
	private MerchantPurchaseService merchantPurchaseService;
	
	
	private static Logger synLogger = Logger.getLogger("sync");
	
	
	@RequestMapping(value="/merchant/createPurchase")
	public ResponseEntity<String> createPurchase(@RequestBody String requestData){
		return purchaseServices.createPurchase(requestData);
	}
	
	//
	@RequestMapping(value="/merchant/updateOrderStatus")
	public ResponseEntity<String> updateOrderStatus(@RequestBody String requestData){
		return purchaseServices.updateOrderStatus(requestData);
	}
	
	//
	
	@RequestMapping(value="/merchant/discardPurchase")
	public ResponseEntity<String> discardPurchase(@RequestBody String requestData){
		return purchaseServices.discardPurchase(requestData);
	}
	
	@RequestMapping(value="/merchant/getUnPayedPurchaseList")
	public ResponseEntity<String> getUnPayedPurchaseList(@RequestBody String requestData){
		return merchantPurchaseService.getUnPayedPurchaseList(requestData);
	}
	
	@RequestMapping(value="/merchant/getPayedPurchaseList")
	public ResponseEntity<String> getPayedPurchaseList(@RequestBody String requestData){
		return merchantPurchaseService.getPayedPurchaseList(requestData);
	}
	
	@RequestMapping(value="/merchant/getHistoryList")
	public ResponseEntity<String> getMerchantPurchaseHistoryList(@RequestBody String requestData){
		return merchantPurchaseService.getPurchaseHistoryList(requestData);
	}
	

	@RequestMapping(value="/mobilePayUser/mobile/getPurchaseList")
	public ResponseEntity<String> getPurchaseList(Principal principal){
		ResponseEntity<String> responseEntity =   purchaseServices.getPurchaseList(principal);
		String responseData = responseEntity.getBody();
		synLogger.info(principal);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	@RequestMapping(value="/mobilePayUser/mobile/getPurchaseHistoryList")
	public ResponseEntity<String> getPurchaseHistoryList(Principal principal){
		ResponseEntity<String> responseEntity =  purchaseServices.getPurchaseHistoryList(principal);
		String responseData = responseEntity.getBody();
		synLogger.info(principal);
		synLogger.info(responseData);
		return responseEntity;
	}
	

	
	@RequestMapping(value="/mobilePayUser/mobile/getOrderStatusList")
	public ResponseEntity<String> getOrderStatusList(@RequestBody String requestData,Principal principal){
		ResponseEntity<String> responseEntity = purchaseServices.getOrderStatusList(requestData,principal);
		String responseData = responseEntity.getBody();
		synLogger.info(principal);
		synLogger.info(requestData);
		synLogger.info(responseData);
		return responseEntity;
	}
	

	@RequestMapping(value="/mobilePayUser/mobile/getPurchaseDetails")
	public ResponseEntity<String> getPurchaseDetails(@RequestBody String requestData,Principal principal){
		ResponseEntity<String> responseEntity =  purchaseServices.getPurchaseDetailsList(requestData,principal);
		String responseData = responseEntity.getBody();
		synLogger.info(principal);
		synLogger.info(requestData);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	
	@RequestMapping(value="/mobilePayUser/mobile/syncDiscardData")
	public ResponseEntity<String> discardPurchaseByUser(@RequestBody String requestData,Principal principal){
		ResponseEntity<String> responseEntity =  purchaseServices.discardPurchaseByUser(requestData,principal);
		String responseData = responseEntity.getBody();
		synLogger.info(principal);
		synLogger.info(requestData);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	
	@RequestMapping(value="/mobilePayUser/mobile/syncPayedData")
	public ResponseEntity<String> syncPayedData(@RequestBody String requestData,Principal principal){
		ResponseEntity<String> responseEntity = purchaseServices.syncPayedData(requestData,principal);
		String responseData = responseEntity.getBody();
		synLogger.info(principal);
		synLogger.info(requestData);
		synLogger.info(responseData);
		return responseEntity;
	}
	
	@RequestMapping(value="/mobile/{purchaseUUID}/syncTransactions")
	public ResponseEntity<String> syncTransactions(@PathVariable("purchaseUUID") String purchaseUUID,@RequestBody String requestData){
		return purchaseServices.syncTransactionData(purchaseUUID,requestData);
		
	}

}
