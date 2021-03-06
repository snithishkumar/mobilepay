package in.tn.mobilepay.services;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.reflect.TypeToken;

import in.tn.mobilepay.dao.DeliveryDAO;
import in.tn.mobilepay.dao.PurchaseDAO;
import in.tn.mobilepay.dao.UserDAO;
import in.tn.mobilepay.dao.impl.MerchantDAOImpl;
import in.tn.mobilepay.entity.AddressEntity;
import in.tn.mobilepay.entity.CloudMessageEntity;
import in.tn.mobilepay.entity.CounterDetailsEntity;
import in.tn.mobilepay.entity.DeliveryDetailsEntity;
import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.HomeDeliveryOptionsEntity;
import in.tn.mobilepay.entity.MerchantEntity;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.entity.TransactionalDetailsEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.DeliveryStatus;
import in.tn.mobilepay.enumeration.DiscardBy;
import in.tn.mobilepay.enumeration.NotificationType;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.enumeration.PaymentStatus;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.request.model.DiscardJsonList;
import in.tn.mobilepay.request.model.GetLuggageList;
import in.tn.mobilepay.request.model.GetPurchaseDetailsList;
import in.tn.mobilepay.request.model.OrderStatusUpdate;
import in.tn.mobilepay.request.model.PayedPurchaseDetailsJson;
import in.tn.mobilepay.request.model.PayedPurchaseDetailsList;
import in.tn.mobilepay.request.model.TokenJson;
import in.tn.mobilepay.response.model.CounterDetailsJson;
import in.tn.mobilepay.response.model.LuggagesListJson;
import in.tn.mobilepay.response.model.MerchantJson;
import in.tn.mobilepay.response.model.NotificationJson;
import in.tn.mobilepay.response.model.OrderStatusJson;
import in.tn.mobilepay.response.model.PurchaseJson;
import in.tn.mobilepay.response.model.UserJson;
import in.tn.mobilepay.rest.json.CalculatedAmounts;
import in.tn.mobilepay.util.StatusCode;

@Service
public class PurchaseServices {

	@Autowired
	private PurchaseDAO purchaseDAOImpl;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private MerchantDAOImpl merchantDAO;
	@Autowired
	private ServiceUtil serviceUtil;
	
	@Autowired
	private DeliveryDAO deliveryDAO;
	
	private static final Logger logger = Logger.getLogger(PurchaseServices.class);

	
	
	/**
	 * Discard Purchase Data
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> discardPurchase(String requestData){
		try{
			// Json to Object
			//DiscardJsonList discardJsonList = serviceUtil.fromJson(requestData, DiscardJsonList.class);
			//for(DiscardJson discardJson : discardJsonList.getDiscardJsons()){
			DiscardJson discardJson = serviceUtil.fromJson(requestData, DiscardJson.class);
			TokenJson tokenJson = serviceUtil.fromJson(requestData, TokenJson.class);
				// Validate Merchant Authorize
				MerchantEntity merchantEntity = validateToken(tokenJson.getAccessToken(), tokenJson.getServerToken());
				//  Validate User Mobile
				//UserEntity userEntity = validateMobile(discardJson.getUserMobile());
				// Get Purchase Data
				PurchaseEntity purchaseEntity  = purchaseDAOImpl.getDiscardablePurchaseEntity(discardJson.getPurchaseGuid(),merchantEntity);
				
				// Discard
				DiscardEntity discardEntity = new DiscardEntity();
				discardEntity.setDiscardGuid(serviceUtil.uuid());
				discardEntity.setMerchantEntity(merchantEntity);
				discardEntity.setUserEntity(purchaseEntity.getUserEntity());
				discardEntity.setReason(discardJson.getReason());
				discardEntity.setCreatedDateTime(ServiceUtil.getCurrentGmtTime());
				discardEntity.setPurchaseEntity(purchaseEntity);
				discardEntity.setDiscardBy(DiscardBy.MERCHANT);
				purchaseEntity.setOrderStatus(OrderStatus.CANCELLED);
				purchaseEntity.setServerDateTime(discardEntity.getCreatedDateTime());
				purchaseEntity.setUpdatedDateTime(discardEntity.getCreatedDateTime());
				purchaseDAOImpl.updatePurchaseObject(purchaseEntity);
				purchaseDAOImpl.createDiscard(discardEntity);
				
				//Send Push Notification
				CloudMessageEntity cloudMessageEntity = userDAO.getCloudMessageEntity(purchaseEntity.getUserEntity());
				if(cloudMessageEntity != null){
					NotificationJson notificationJson = new NotificationJson();
					notificationJson.setNotificationType(NotificationType.STATUS);
					notificationJson.setMessage("Your order has been Declined by Merchant. Because of "+discardEntity.getReason()); // TODO
					notificationJson.setPurchaseGuid(purchaseEntity.getPurchaseGuid());
					serviceUtil.sendAndroidNotification(notificationJson, cloudMessageEntity.getCloudId());
				}
			//}
			
			return serviceUtil.getResponse(StatusCode.MER_OK, "success");
		}catch(ValidationException e){
			logger.error("Error in ValidationException", e);
			return serviceUtil.getResponse(e.getCode(), e.getMessage());
		}catch(Exception e){
			logger.error("Error in discardPurchase", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "failure");
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> syncPayedData(String requestData,Principal principal){
		try{
			PayedPurchaseDetailsList payedPurchaseDetailsJsons = serviceUtil.fromJson(requestData,PayedPurchaseDetailsList.class);
			UserEntity userEntity = serviceUtil.getUserEntity(principal);
			List<PurchaseJson> purchaseJsons = new ArrayList<>();
			Map<String, AddressEntity> addressList = new HashMap<>();
			for (PayedPurchaseDetailsJson payedPurchaseDetailsJson : payedPurchaseDetailsJsons.getPurchaseDetailsJsons()) {
				PurchaseEntity purchaseEntity = purchaseDAOImpl.getNonDiscardPurchaseEntity(payedPurchaseDetailsJson.getPurchaseId());
				if(purchaseEntity != null){
					purchaseEntity.setUserDeliveryOptions(payedPurchaseDetailsJson.getUserDeliveryOptions());
					if(payedPurchaseDetailsJson.getUserDeliveryOptions().ordinal() == DeliveryOptions.HOME.ordinal()){
						if(payedPurchaseDetailsJson.getAddressGuid() != null){
							AddressEntity addressEntity = userDAO.getAddressEntity(payedPurchaseDetailsJson.getAddressGuid());
							purchaseEntity.getAddressEntities().add(addressEntity);
						}else{
							AddressEntity addressEntity =  addressList.get(payedPurchaseDetailsJson.getAddressJson());
							if(addressEntity == null){
								addressEntity = new AddressEntity(payedPurchaseDetailsJson.getAddressJson());
								addressEntity.setUserEntity(userEntity);
								userDAO.createAddressEntity(addressEntity);
							}
							purchaseEntity.getAddressEntities().add(addressEntity);
							addressList.put(payedPurchaseDetailsJson.getAddressJson().getAddressUUID(), addressEntity);
						}
					}
					purchaseEntity.setOrderStatus(payedPurchaseDetailsJson.getOrderStatus());
					purchaseEntity.setPaymentStatus(PaymentStatus.PAID);
					purchaseEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
					purchaseEntity.setUpdatedDateTime(payedPurchaseDetailsJson.getPayemetTime());
					purchaseEntity.setUnModifiedAmountDetails(purchaseEntity.getAmountDetails());
					purchaseEntity.setUnModifiedPurchaseData(purchaseEntity.getPurchaseData());
					purchaseEntity.setAmountDetails(payedPurchaseDetailsJson.getAmountDetails());
					purchaseEntity.setPurchaseData(payedPurchaseDetailsJson.getProductDetails());
					purchaseEntity.setTotalAmount(payedPurchaseDetailsJson.getCalculatedAmounts().getTotalAmount());
					purchaseEntity.setCalculatedAmounts(serviceUtil.toJson(payedPurchaseDetailsJson.getCalculatedAmounts()));
					purchaseDAOImpl.updatePurchaseObject(purchaseEntity);
					processTransactions(purchaseEntity, payedPurchaseDetailsJson.getTransactions());
					PurchaseJson purchaseJson = new PurchaseJson();
					purchaseJson.setPurchaseId(purchaseEntity.getPurchaseGuid());
					purchaseJson.setServerDateTime(purchaseEntity.getPurchaseDateTime());
					purchaseJsons.add(purchaseJson);
				}
				
			}
			String responseJson = serviceUtil.toJson(purchaseJsons);
			return serviceUtil.getResponse(200, responseJson);
		}catch(Exception e){
			logger.error("Raw Data[" + requestData + "],["+principal+"]");
			logger.error("Error in syncPayedData", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "failure");
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> syncTransactionData(String purchaseUUID, String requestData) {
		try {
			PurchaseEntity purchaseEntity = purchaseDAOImpl.getPurchaseEntity(purchaseUUID);
			if (purchaseEntity != null) {
				Type listType = new TypeToken<ArrayList<TransactionalDetailsEntity>>() {
				}.getType();
				List<TransactionalDetailsEntity> transactions = serviceUtil.fromJson(requestData, listType);
				processTransactions(purchaseEntity, transactions);
			}
			return serviceUtil.getResponse(200, "success");
		} catch (Exception e) {
			logger.error("Error in syncTransactionData", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "failure");
	}
	
	
	private void processTransactions(PurchaseEntity purchaseEntity,List<TransactionalDetailsEntity> transactions){
		for(TransactionalDetailsEntity transactionalDetailsEntity : transactions){
			transactionalDetailsEntity.setPurchaseEntity(purchaseEntity);
			purchaseDAOImpl.createTransactions(transactionalDetailsEntity);
		}
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> discardPurchaseByUser(String requestData,Principal principal){
		try {
			// Json to Object
			DiscardJsonList discardJsonList = serviceUtil.fromJson(requestData,DiscardJsonList.class);
			UserEntity userEntity = serviceUtil.getUserEntity(principal);
			List<PurchaseJson> purchaseJsons = new ArrayList<>();
			for (DiscardJson discardJson : discardJsonList.getDiscardJsons()) {
				PurchaseEntity purchaseEntity = purchaseDAOImpl.getPurchaseEntity(discardJson.getPurchaseGuid());
				if(purchaseEntity != null && purchaseEntity.getOrderStatus().ordinal() != OrderStatus.CANCELLED.ordinal()){
					DiscardEntity discardEntity = new DiscardEntity();
					discardEntity.setDiscardGuid(serviceUtil.uuid());
					discardEntity.setUserEntity(userEntity);
					discardEntity.setReason(discardJson.getReason());
					discardEntity.setCreatedDateTime(ServiceUtil.getCurrentGmtTime());
					discardEntity.setPurchaseEntity(purchaseEntity);
					discardEntity.setDiscardBy(DiscardBy.USER);
					
					purchaseEntity.setUnModifiedAmountDetails(purchaseEntity.getAmountDetails());
					purchaseEntity.setUnModifiedPurchaseData(purchaseEntity.getPurchaseData());
					purchaseEntity.setAmountDetails(discardJson.getAmountDetails());
					purchaseEntity.setPurchaseData(discardJson.getProductDetails());
					
					
					purchaseEntity.setTotalAmount(discardJson.getCalculatedAmounts().getTotalAmount());
					purchaseEntity.setCalculatedAmounts(serviceUtil.toJson(discardJson.getCalculatedAmounts()));
					
					purchaseEntity.setOrderStatus(OrderStatus.CANCELLED);
					purchaseEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
					purchaseEntity.setUpdatedDateTime(discardEntity.getCreatedDateTime());
					purchaseDAOImpl.updatePurchaseObject(purchaseEntity);
					processTransactions(purchaseEntity, discardJson.getTransactions());
					purchaseDAOImpl.createDiscard(discardEntity);
					PurchaseJson purchaseJson = new PurchaseJson();
					purchaseJson.setPurchaseId(purchaseEntity.getPurchaseGuid());
					purchaseJson.setServerDateTime(purchaseEntity.getPurchaseDateTime());
					purchaseJsons.add(purchaseJson);
				}
			}
			String responseJson = serviceUtil.toJson(purchaseJsons);
			return serviceUtil.getResponse(200, responseJson);
		}catch(Exception e){
			logger.error("Raw Data[" + requestData + "],["+principal+"]");
			logger.error("Error in discardPurchaseByUser", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "failure");
	}
	
	private void updateCounterStatus(PurchaseEntity purchaseEntity,OrderStatusUpdate orderStatusUpdate){
		if(orderStatusUpdate.getOrderStatus() == null){
			CounterDetailsEntity counterDetailsEntity = deliveryDAO.geCounterDetailsEntity(purchaseEntity.getPurchaseId());
			boolean isCreate = false;
			if(counterDetailsEntity == null){
				 counterDetailsEntity = new CounterDetailsEntity();
				 counterDetailsEntity.setCounterGuid(serviceUtil.uuid());
				 isCreate = true;
			}
			
			counterDetailsEntity.setCounterNumber("45");
			counterDetailsEntity.setCreatedDateTime(purchaseEntity.getServerDateTime());
			counterDetailsEntity.setMessage(orderStatusUpdate.getOrderStatusDesc());
			counterDetailsEntity.setPurchaseEntity(purchaseEntity);
			purchaseEntity.setOrderStatus(OrderStatus.READY_TO_COLLECT);
			if(isCreate){
				purchaseDAOImpl.createCounterStatus(counterDetailsEntity);
			}else{
				purchaseDAOImpl.updateCounterStatus(counterDetailsEntity);
			}
			return;
		}
		switch (orderStatusUpdate.getOrderStatus()) {
		case READY_TO_COLLECT:
			CounterDetailsEntity counterDetailsEntity = deliveryDAO.geCounterDetailsEntity(purchaseEntity.getPurchaseId());
			boolean isCreate = false;
			if(counterDetailsEntity == null){
				 counterDetailsEntity = new CounterDetailsEntity();
				 counterDetailsEntity.setCounterGuid(serviceUtil.uuid());
				 isCreate = true;
			}
			
			counterDetailsEntity.setCounterNumber(orderStatusUpdate.getCounterNumber());
			counterDetailsEntity.setCreatedDateTime(purchaseEntity.getServerDateTime());
			counterDetailsEntity.setMessage(orderStatusUpdate.getOrderStatusDesc());
			counterDetailsEntity.setPurchaseEntity(purchaseEntity);
			purchaseEntity.setOrderStatus(OrderStatus.READY_TO_COLLECT);
			if(isCreate){
				purchaseDAOImpl.createCounterStatus(counterDetailsEntity);
			}else{
				purchaseDAOImpl.updateCounterStatus(counterDetailsEntity);
			}
			break;
			
		case OUT_FOR_DELIVERY :
			purchaseEntity.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
			DeliveryDetailsEntity deliveryDetailsEntity = deliveryDAO.getDeliveryDetailsEntity(purchaseEntity);
			if(deliveryDetailsEntity == null){
				deliveryDetailsEntity = new DeliveryDetailsEntity();
				deliveryDetailsEntity.setOutForDeliveryDate(purchaseEntity.getServerDateTime());
				deliveryDetailsEntity.setPurchaseEntity(purchaseEntity);
				deliveryDAO.createDeliveryDetails(deliveryDetailsEntity);
			}
			break;
		case DELIVERED:
			purchaseEntity.setOrderStatus(OrderStatus.DELIVERED);
			 deliveryDetailsEntity = deliveryDAO.getDeliveryDetailsEntity(purchaseEntity);
			if(deliveryDetailsEntity != null){
				deliveryDetailsEntity.setDeliveredDate(purchaseEntity.getServerDateTime());
				deliveryDetailsEntity.setDeliveryStatus(DeliveryStatus.SUCCESS);
				deliveryDAO.updateDeliveryDetails(deliveryDetailsEntity);
			}
			break;
			
/*		case FAILED_TO_DELIVER:
			break;
			
		case READY_TO_SHIPPING:
			break;
*/
		default:
			purchaseEntity.setOrderStatus(orderStatusUpdate.getOrderStatus());
			break;
		}
			
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> updateOrderStatus(String requestData){
		try{
			//OrderStatusUpdateJsonList orderStatusUpdateJsonList =	serviceUtil.fromJson(requestData, OrderStatusUpdateJsonList.class);
			OrderStatusUpdate orderStatusUpdate =	serviceUtil.fromJson(requestData, OrderStatusUpdate.class);
			MerchantEntity merchantEntity = validateToken(orderStatusUpdate.getAccessToken(), orderStatusUpdate.getServerToken());
			//for(OrderStatusUpdate orderStatusUpdate : orderStatusUpdateJsonList.getOrderStatusUpdates()){
				PurchaseEntity purchaseEntity = purchaseDAOImpl.getOrderStatusPurchaseEntity(orderStatusUpdate.getPurchaseUUID(),merchantEntity);
				if(purchaseEntity != null){
					purchaseEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
					purchaseEntity.setUpdatedDateTime(purchaseEntity.getServerDateTime());
					updateCounterStatus(purchaseEntity, orderStatusUpdate);
					
					purchaseDAOImpl.updatePurchaseObject(purchaseEntity);
					
					//Send Push Notification
					CloudMessageEntity cloudMessageEntity = userDAO.getCloudMessageEntity(purchaseEntity.getUserEntity());
					if(cloudMessageEntity != null){
						NotificationJson notificationJson = new NotificationJson();
						notificationJson.setNotificationType(NotificationType.STATUS);
						switch (purchaseEntity.getOrderStatus()) {
						case CANCELLED:
							notificationJson.setMessage("Your order has been Canceled by merchant."); // TODO
							break;
						case READY_TO_COLLECT:
							notificationJson.setMessage("Your order has been Ready.You can collect it from "+merchantEntity.getMerchantName()); // TODO
							break;
						case DELIVERED:
							notificationJson.setMessage("Your order has been Deliverd. Thanks for Shopping!!!"); // TODO
							break;
							
						case FAILED_TO_DELIVER:
							notificationJson.setMessage("Your order has been not Deliverd."); // TODO
							break;
							
						case READY_TO_SHIPPING:
						case OUT_FOR_DELIVERY:
							notificationJson.setMessage("Your order has been Ready. It will be dispatched shortly."); // TODO
							break;
						}
						
						notificationJson.setPurchaseGuid(purchaseEntity.getPurchaseGuid());
						serviceUtil.sendAndroidNotification(notificationJson, cloudMessageEntity.getCloudId());
					}
				}
			//}
			return serviceUtil.getResponse(200, "success");
		}catch(ValidationException e){
			logger.error("Error in updateOrderStatus", e);
			return serviceUtil.getResponse(e.getCode(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in updateOrderStatus", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "failure");
	}
	
	/**
	 * Create or update Purchase Record
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> createPurchase(String requestData){
		try{
			// Json to Object conversion
			in.tn.mobilepay.request.model.PurchaseJson purchaseJson =	serviceUtil.fromJson(requestData, in.tn.mobilepay.request.model.PurchaseJson.class);
			// Validate Merchant Authorize
			MerchantEntity merchantEntity = validateToken(purchaseJson.getAccessToken(), purchaseJson.getServerToken());
		    // Validate User Mobile
			UserEntity userEntity = validateMobile(purchaseJson.getUserMobile());
			
			PurchaseEntity dbPurchaseEntity = new PurchaseEntity();
			
			dbPurchaseEntity.setPurchaseDateTime(Long.valueOf(purchaseJson.getPurchaseDateTime()));
			dbPurchaseEntity.setMerchantEntity(merchantEntity);
			dbPurchaseEntity.setUserEntity(userEntity);
			dbPurchaseEntity.setPaymentStatus(PaymentStatus.NOT_PAID);
			dbPurchaseEntity.setOrderStatus(OrderStatus.PURCHASE);
			// Json obj to Entity
			populatePurchaseData(dbPurchaseEntity, purchaseJson);
			//Create New Record
			purchaseDAOImpl.createPurchaseObject(dbPurchaseEntity);
			//Send Push Notification
			CloudMessageEntity cloudMessageEntity = userDAO.getCloudMessageEntity(userEntity);
			if(cloudMessageEntity != null){
				NotificationJson notificationJson = new NotificationJson();
				notificationJson.setNotificationType(NotificationType.PURCHASE);
				notificationJson.setMessage("You have Purchased in"+ merchantEntity.getMerchantName()+".Total Cost : "+purchaseJson.getTotalAmount()+"."); // TODO
				notificationJson.setPurchaseGuid(dbPurchaseEntity.getPurchaseGuid());
				serviceUtil.sendAndroidNotification(notificationJson, cloudMessageEntity.getCloudId());
			}
			//Response
			return serviceUtil.getResponse(StatusCode.MER_OK, dbPurchaseEntity.getPurchaseGuid());
			
		}catch(ValidationException e){
			logger.error("Error in ValidationException", e);
			return serviceUtil.getResponse(e.getCode(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in createPurchase", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "failure");
	}
	
	/**
	 * Validate Merchant Authorization
	 * @param merchantToken
	 * @param serverToken
	 * @return
	 * @throws ValidationException
	 */
	private MerchantEntity validateToken(String merchantToken,String serverToken) throws ValidationException{
		MerchantEntity merchantEntity = merchantDAO.getMerchant(merchantToken, serverToken);
		if(merchantEntity == null){
			throw new ValidationException(StatusCode.MER_UNAUTHORIZE, "Invalid User", null);
		}
		return merchantEntity;
		
	}
	
	
	/*private UserEntity validateUserToken(String client,String serverToken) throws ValidationException{
		UserEntity userEntity = userDAO.getUserEnityByToken(client, serverToken);
		if(userEntity == null){
			throw new ValidationException(10, "Invalid User", null);
		}
		return userEntity;
		
	}*/
	
	/**
	 * Validate User Details
	 * @param mobileNumber
	 * @return
	 * @throws ValidationException
	 */
	private UserEntity validateMobile(String mobileNumber) throws ValidationException{
		UserEntity userEntity = userDAO.getUserEntity(mobileNumber);
		if(userEntity == null){
			throw new ValidationException(StatusCode.MER_INVALID_MOBILE, "Invalid Mobile", null);
		}
		return userEntity;
	}
	
	private void populatePurchaseData(PurchaseEntity purchaseEntity,in.tn.mobilepay.request.model.PurchaseJson purchaseJson){
		purchaseEntity.loadValue(purchaseJson);
		purchaseEntity.setPaymentStatus(PaymentStatus.NOT_PAID);
		purchaseEntity.setPurchaseGuid(serviceUtil.uuid());
		String purchaseData = serviceUtil.toJson(purchaseJson.getPurchaseDetails());
		purchaseEntity.setPurchaseData(purchaseData);
		String amountDetails = serviceUtil.toJson(purchaseJson.getAmountDetails());
		purchaseEntity.setAmountDetails(amountDetails);
		purchaseEntity.setMerchantDeliveryOptions(purchaseJson.getDeliveryOptions());
	}
	
	
	/**
	 * Returns Current Purchase Detail List
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseDetailsList(String requestData,Principal principal) {
		try {
			// Json to Object
			GetPurchaseDetailsList getPurchaseList =	serviceUtil.fromJson(requestData, GetPurchaseDetailsList.class);
			
			UserEntity userEntity = serviceUtil.getUserEntity(principal);
			
			// Get Current Purchase List
			List<PurchaseEntity> purchaseList = purchaseDAOImpl.getPurchaseDetails(getPurchaseList.getPurchaseUUIDs(), userEntity);
			// Entity to Json Object
			List<PurchaseJson> purchaseJsons = new ArrayList<PurchaseJson>();
			for (PurchaseEntity purchaseEntity : purchaseList) {
				
				PurchaseJson purchaseJson = getPurchaseJson(purchaseEntity, userEntity);
				
				if(purchaseEntity.getOrderStatus().ordinal() == OrderStatus.CANCELLED.ordinal()){
					DiscardEntity discardEntity = purchaseDAOImpl.getDiscardEntity(purchaseEntity);
					DiscardJson discardJson = new DiscardJson(discardEntity);
					purchaseJson.setDiscardJson(discardJson);
				}
				
				purchaseJsons.add(purchaseJson);
			}
			String responseJson = serviceUtil.toJson(purchaseJsons);
		//	String responseEncrypt = serviceUtil.netEncryption(responseJson);
			return serviceUtil.getResponse(300, responseJson);
		} catch (Exception e) {
			logger.error("Raw Data[" + requestData + "],["+principal+"]");
			logger.error("Error in getPurchaseList", e);
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}
	
	
	private PurchaseJson getPurchaseJson(PurchaseEntity purchaseEntity,UserEntity userEntity)throws Exception{
		PurchaseJson purchaseJson = new PurchaseJson(purchaseEntity);
		// Get Home Delivery Conditions
		if(purchaseEntity.getMerchantDeliveryOptions().ordinal() == DeliveryOptions.HOME.ordinal() || purchaseEntity.getMerchantDeliveryOptions().ordinal() == DeliveryOptions.BOTH.ordinal()){
			HomeDeliveryOptionsEntity homeDeliveryOptionsEntity =	merchantDAO.geHomeDeliveryOptionsEntity(purchaseEntity.getMerchantEntity());
			homeDeliveryOptionsEntity.setMerchantEntity(null);
			purchaseJson.setHomeDeliveryOptions(homeDeliveryOptionsEntity);
		}
		if(purchaseEntity.getCalculatedAmounts() != null){
			CalculatedAmounts calculatedAmounts = serviceUtil.fromJson(purchaseEntity.getCalculatedAmounts(), CalculatedAmounts.class);
			purchaseJson.setCalculatedAmounts(calculatedAmounts);
		}
		
		UserJson userJson = new UserJson(userEntity);
		purchaseJson.setUsers(userJson);
		MerchantJson merchantJson = new MerchantJson(purchaseEntity.getMerchantEntity());
		purchaseJson.setMerchants(merchantJson);
		return purchaseJson;
	}
	
	
	
	/**
	 * Returns Current Purchase List
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseList(Principal principal) {
		try {
			UserEntity userEntity = serviceUtil.getUserEntity(principal);
			// Get Current Purchase List
			List<String> purchaseList = purchaseDAOImpl.gePurchaseList(userEntity);
			String responseJson = serviceUtil.toJson(purchaseList);
		//	String responseEncrypt = serviceUtil.netEncryption(responseJson);
			return serviceUtil.getResponse(300, responseJson);
		}catch (Exception e) {
			logger.error("Raw Data[" + principal + "]");
			logger.error("Error in getPurchaseList", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Internal ServerError");
	}
	
	/**
	 * Returns Purchase History List
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseHistoryList(Principal principal) {
		try {
			UserEntity userEntity = serviceUtil.getUserEntity(principal);
			// Get Current Purchase List
			List<String> purchaseUUIDsList = purchaseDAOImpl.getPurchaseHistoryList(userEntity);
			String responseJson = serviceUtil.toJson(purchaseUUIDsList);	
			return serviceUtil.getResponse(300, responseJson);
		}catch (Exception e) {
			logger.error("Raw Data[" + principal + "]");
			logger.error("Error in getPurchaseHistoryList", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Internal ServerError");
	}
	
	
	
	/**
	 * Returns Luggage List
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getOrderStatusList(String requestData,Principal principal){
		try{
			//Json to Object
			GetLuggageList getLuggageList =	serviceUtil.fromJson(requestData, GetLuggageList.class);
			
			UserEntity userEntity = serviceUtil.getUserEntity(principal);
			
			LuggagesListJson luggagesListJson = new LuggagesListJson();
			List<PurchaseEntity> purchaseList = null;
			// Get Luggage Details and Purchase with Luggage Details
			if(getLuggageList.getStartTime() > 0){
				List<OrderStatusJson> luggageJsons = purchaseDAOImpl.getOrderStatusList(getLuggageList.getStartTime() , getLuggageList.getEndTime(), userEntity);
				for(OrderStatusJson orderStatusJson : luggageJsons){
					if(orderStatusJson.getOrderStatus().toString().equals(OrderStatus.READY_TO_COLLECT.toString())){
						CounterDetailsEntity counterDetailsEntity = deliveryDAO.geCounterDetailsEntity(orderStatusJson.getPurchaseId());
						if(counterDetailsEntity != null){
							CounterDetailsJson counterDetailsJson = new CounterDetailsJson(counterDetailsEntity);
							orderStatusJson.setCounterDetails(counterDetailsJson);
						}
					}
					
				}
				luggagesListJson.setLuggageJsons(luggageJsons);
				purchaseList = purchaseDAOImpl.getOrderStatusWithPurchaseList(getLuggageList.getStartTime() , getLuggageList.getEndTime(), userEntity);
			}else{ // Get  Purchase with Luggage Full List
				purchaseList = purchaseDAOImpl.getOrderStatusWithPurchaseList(userEntity);
			}
			
			// Entity to Json Object
			List<PurchaseJson> purchaseJsons = new ArrayList<PurchaseJson>();
			luggagesListJson.setPurchaseJsons(purchaseJsons);
			for (PurchaseEntity purchaseEntity : purchaseList) {
				PurchaseJson purchaseJson = getPurchaseJson(purchaseEntity, userEntity);
				purchaseJsons.add(purchaseJson);
			}
			String responseJson = serviceUtil.toJson(luggagesListJson);
		//	String responseEncrypt = serviceUtil.netEncryption(responseJson);
			return serviceUtil.getResponse(300, responseJson);
		}catch(Exception e){
			logger.error("Raw Data[" + requestData + "],["+principal+"]");
			logger.error("Error in getOrderStatusList", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Internal Server Error.");
	}
	
	
	

}
