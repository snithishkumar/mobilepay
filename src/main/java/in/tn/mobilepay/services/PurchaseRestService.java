package in.tn.mobilepay.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import in.tn.mobilepay.dao.DeliveryDAO;
import in.tn.mobilepay.dao.PurchaseDAO;
import in.tn.mobilepay.dao.UserDAO;
import in.tn.mobilepay.entity.AddressEntity;
import in.tn.mobilepay.entity.CloudMessageEntity;
import in.tn.mobilepay.entity.CounterDetailsEntity;
import in.tn.mobilepay.entity.DeliveryDetailsEntity;
import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.MerchantEntity;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.DeliveryStatus;
import in.tn.mobilepay.enumeration.DiscardBy;
import in.tn.mobilepay.enumeration.NotificationType;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.response.model.AddressJson;
import in.tn.mobilepay.response.model.NotificationJson;
import in.tn.mobilepay.response.model.UserJson;
import in.tn.mobilepay.rest.json.AmountDetails;
import in.tn.mobilepay.rest.json.MerchantPurchaseData;
import in.tn.mobilepay.rest.json.MerchantPurchaseDatas;
import in.tn.mobilepay.rest.json.OrderStatusUpdate;
import in.tn.mobilepay.rest.json.OrderStatusUpdateList;
import in.tn.mobilepay.rest.json.PurchaseDetails;
import in.tn.mobilepay.rest.json.PurchaseItem;
import in.tn.mobilepay.rest.json.PurchaseItems;

@Service
public class PurchaseRestService {
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private UserDAO userDAOImpl;
	
	@Autowired
	private PurchaseDAO purchaseDAOImpl;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	@Autowired
	private DeliveryDAO deliveryDAO;
	
	
	
	private static final Logger  logger = Logger.getLogger(PurchaseRestService.class);
	
	/**
	 * Create New Purchase Entity
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> createPurchase(String requestData,Principal principal){
		try{
			// Json to Object
			MerchantPurchaseDatas merchantPurchaseDatas = gson.fromJson(requestData, MerchantPurchaseDatas.class);
			List<MerchantPurchaseData> purchaseDatas = merchantPurchaseDatas.getMerchantPurchaseDatas();
			JsonArray responseData = new JsonArray();
			// Process each data
			for(MerchantPurchaseData merchantPurchaseData : purchaseDatas){
				try{
					// Validate Customer mobile number
					UserEntity userEntity =	validateUserMobile(merchantPurchaseData.getCustomerMobileNo());
					PurchaseEntity purchaseEntity = new PurchaseEntity();
					//Copy MerchantPurchase Data to Purchase
					copyMerchantPurchaseData(merchantPurchaseData, purchaseEntity);
					
					purchaseEntity.setUserEntity(userEntity);
					// Get Merchant Entity
					MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
					purchaseEntity.setMerchantEntity(merchantEntity);
					
					purchaseDAOImpl.createPurchaseObject(purchaseEntity);
					// Response
					JsonObject response = new JsonObject();
					response.addProperty("statusCode", 200);
					response.addProperty("billNumber", purchaseEntity.getBillNumber());
					response.addProperty("purchaseUUID", purchaseEntity.getPurchaseGuid());
					response.addProperty("message", "Created Successfully.");
					responseData.add(response);
				}catch(ValidationException e){
					// Error Response
					JsonObject response = new JsonObject();
					response.addProperty("statusCode", 404);
					response.addProperty("message", "Invalid Customer Number.");
					responseData.add(response);
				}
				
			}
			return serviceUtil.getRestResponse(true, responseData,200);
		}catch(Exception e){
			logger.error("Error in PurchaseRestService", e);
		}
		return serviceUtil.getRestResponse(false, "OOPS.",500);
	}
	
	
	private void copyMerchantPurchaseData(MerchantPurchaseData merchantPurchaseData,PurchaseEntity purchaseEntity){
		AmountDetails amountDetails = merchantPurchaseData.getAmountDetails();
		if(amountDetails != null){
			purchaseEntity.setAmountDetails(gson.toJson(amountDetails));
		}
		purchaseEntity.setBillNumber(merchantPurchaseData.getBillNumber());
		purchaseEntity.setMerchantDeliveryOptions(merchantPurchaseData.getDeliveryOptions());
		purchaseEntity.setEditable(merchantPurchaseData.getIsRemovable());
		List<PurchaseItem> purchaseItems = merchantPurchaseData.getPurchaseItems();
		if(purchaseItems != null && purchaseItems.size() > 0){
			purchaseEntity.setPurchaseData(gson.toJson(purchaseItems));
		}
		purchaseEntity.setPurchaseDateTime(merchantPurchaseData.getPurchaseDate());
		purchaseEntity.setPurchaseGuid(serviceUtil.uuid());
		purchaseEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
		//purchaseEntity.setTotalAmount(merchantPurchaseData.getTotalAmount());
		
	}
	
	/**
	 * Validate given mobile Number is present or not
	 * @param mobileNumber
	 * @return
	 * @throws ValidationException
	 */
	private UserEntity validateUserMobile(String mobileNumber)throws ValidationException{
		UserEntity userEntity =	userDAOImpl.getUserEntity(mobileNumber);
		if(userEntity == null){
			throw new ValidationException(0, "");
		}
		return userEntity;
	}
	
	
	/**
	 * Get List of Purchase Details
	 * @param index
	 * @param limit
	 * @param status
	 * @param fromDate
	 * @param toDate
	 * @param principal
	 * @return ResponseEntity
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseList(Integer index,Integer limit,String status,Long fromDate,Long toDate,Principal principal){
		try{
			// Get Merchant Entity
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			// Get Purchase Detail List
			List<PurchaseEntity> purchaseEntities = purchaseDAOImpl.getPurchaseEntityList(merchantEntity, index, limit, status, fromDate, toDate);
			List<PurchaseDetails> purchaseDetailsList = new ArrayList<>();
			boolean isAdded = false;
			// Convert Purchase Entity to Purchase Json
			for(PurchaseEntity purchaseEntity : purchaseEntities){
				PurchaseDetails purchaseDetails = getPurchaseData(purchaseEntity);
				purchaseDetailsList.add(purchaseDetails);
				isAdded = true;
			}
			if(isAdded){
				return serviceUtil.getRestResponse(true, purchaseDetailsList, 200);
			}
			return serviceUtil.getRestResponse(true, purchaseDetailsList, 403);
		}catch(Exception e){
			logger.error("Error in getPurchaseList", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.", 500);
	}
	
	/**
	 * Purchase Entity to Purchase Json
	 * @param purchaseEntity
	 * @return
	 */
	private PurchaseDetails getPurchaseData(PurchaseEntity purchaseEntity){
		PurchaseDetails purchaseDetails = new PurchaseDetails(purchaseEntity);
		String purchaseData = purchaseEntity.getPurchaseData();
		PurchaseItems purchaseItems = gson.fromJson(purchaseData,PurchaseItems.class);
		purchaseDetails.setPurchaseItem(purchaseItems.getPurchaseItems());
		if(purchaseEntity.getOrderStatus().ordinal() == OrderStatus.CANCELLED.ordinal()){
			DiscardEntity discardEntity =  purchaseDAOImpl.getDiscardEntity(purchaseEntity);
			if(discardEntity != null){
				DiscardJson discardJson = new DiscardJson(discardEntity);
				purchaseDetails.setDiscardDetails(discardJson);
			}
			
		}
		
		if(purchaseDetails.getDeliveryOptions() != null && purchaseDetails.getDeliveryOptions().toString().equals(DeliveryOptions.HOME.toString())){
			Collection<AddressEntity> addressEntities = purchaseEntity.getAddressEntities();
			if(addressEntities != null){
				for(AddressEntity addressEntity : addressEntities){
					AddressJson addressJson = new AddressJson(addressEntity);
					purchaseDetails.setAddressDetails(addressJson);
				}
			}
		}
		return purchaseDetails;
	}
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseData(String purchaseUUID,Principal principal){
		try{
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			PurchaseEntity purchaseEntity = purchaseDAOImpl.getPurchaseEntity(purchaseUUID,merchantEntity);
			if(purchaseEntity == null){
				return serviceUtil.getRestResponse(false, "PurchaseUUID is not matched", 404);
			}
			PurchaseDetails purchaseDetails = getPurchaseData(purchaseEntity);
			
			String amountDetailsJson = purchaseEntity.getAmountDetails();
			AmountDetails amountDetails =  serviceUtil.fromJson(amountDetailsJson, AmountDetails.class);
			purchaseDetails.setAmountDetails(amountDetails);
			
			String unModifiedPurchase = purchaseEntity.getUnModifiedPurchaseData();
			if(unModifiedPurchase != null){
				PurchaseItems purchaseItems =  serviceUtil.fromJson(unModifiedPurchase, PurchaseItems.class);
				purchaseDetails.setUnModifiedPurchaseItem(purchaseItems.getPurchaseItems());
			}
			
			UserEntity userEntity = purchaseEntity.getUserEntity();
			UserJson userJson = new UserJson(userEntity);
			purchaseDetails.setUserDetails(userJson);
			return serviceUtil.getRestResponse(true, purchaseDetails, 200);
		}catch(Exception e){
			logger.error("Error in getPurchaseData", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error", 500);
	}
	
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> updateOrderStatus(Principal principal,String orderStatusJson){
		try{
			OrderStatusUpdateList orderStatusUpdateList = serviceUtil.fromJson(orderStatusJson, OrderStatusUpdateList.class);
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			List<OrderStatusUpdate> orderStatusUpdates = orderStatusUpdateList.getOrderStatusUpdates();
			JsonArray results = new JsonArray();
			for(OrderStatusUpdate orderStatusUpdate : orderStatusUpdates){
				JsonObject result = updateOrderStatus( orderStatusUpdate, merchantEntity);
				results.add(result);
			}
			return serviceUtil.getRestResponse(true, results, 200);
			
		}catch(Exception e){
			logger.error("Error in updateOrderStatus", e);
		}
		return serviceUtil.getRestResponse(false, "Internal server Error", 500);
	}
	
	
	private JsonObject updateOrderStatus(OrderStatusUpdate orderStatusUpdate,MerchantEntity merchantEntity){
		JsonObject result = new JsonObject();
		try{
			PurchaseEntity purchaseEntity = purchaseDAOImpl.getPurchaseEntity(orderStatusUpdate.getPurchaseUUID(),merchantEntity);
			if(purchaseEntity == null){
				logger.error("Invalid PurchaseUUID ["+orderStatusUpdate.getPurchaseUUID()+"],principal["+merchantEntity+"]");
				result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
				result.addProperty("statusCode", 404);
				result.addProperty("message", "PurchaseUUID is not found.");
				return result;
			}
			
			purchaseEntity.setUpdatedDateTime(ServiceUtil.getCurrentGmtTime());
			purchaseEntity.setServerDateTime(purchaseEntity.getUpdatedDateTime());
			
			switch (orderStatusUpdate.getOrderStatus()) {
			case CANCELLED:
				PurchaseEntity dbPurchaseEntity  = purchaseDAOImpl.getDiscardablePurchaseEntity(purchaseEntity.getPurchaseGuid(),purchaseEntity.getMerchantEntity());
				if(dbPurchaseEntity == null){
					result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
					result.addProperty("statusCode", "");
					result.addProperty("message", "Already paied by Customer.");
					return result;
				}
				purchaseEntity.setOrderStatus(OrderStatus.CANCELLED);
				updateOrderDiscard(orderStatusUpdate, dbPurchaseEntity);
				
				break;
			case DELIVERED:
				purchaseEntity.setOrderStatus(OrderStatus.DELIVERED);
				updateOrderDelivered(purchaseEntity);
				
				break;
			case FAILED_TO_DELIVER:
				break;
			case OUT_FOR_DELIVERY:
				purchaseEntity.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
				updateOrderOutForDelivery(purchaseEntity);
				break;
			case READY_TO_COLLECT:
				purchaseEntity.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
				updateCounterDetails(orderStatusUpdate, purchaseEntity);
				break;
			case READY_TO_SHIPPING:
				purchaseEntity.setOrderStatus(OrderStatus.READY_TO_SHIPPING);
				break;
			
			default:
				break;
			}
			purchaseDAOImpl.updatePurchaseObject(purchaseEntity);
			result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
			result.addProperty("statusCode", "200");
			result.addProperty("message", "Success.");
		}catch(Exception e){
			logger.error("Error in updateOrderStatus", e);
		}
		return result;
	}
	
	
	private void updateCounterDetails(OrderStatusUpdate orderStatusUpdate,PurchaseEntity purchaseEntity){
		CounterDetailsEntity counterDetailsEntity = deliveryDAO.geCounterDetailsEntity(purchaseEntity.getPurchaseId());
		boolean isCreate = false;
		if(counterDetailsEntity == null){
			 counterDetailsEntity = new CounterDetailsEntity();
			 counterDetailsEntity.setCounterGuid(serviceUtil.uuid());
			 isCreate = true;
		}
		
		counterDetailsEntity.setCounterNumber(orderStatusUpdate.getCounterNumber());
		counterDetailsEntity.setCreatedDateTime(purchaseEntity.getServerDateTime());
		counterDetailsEntity.setMessage(orderStatusUpdate.getDescription());
		
		counterDetailsEntity.setPurchaseEntity(purchaseEntity);
		purchaseEntity.setOrderStatus(OrderStatus.READY_TO_COLLECT);
		if(isCreate){
			purchaseDAOImpl.createCounterStatus(counterDetailsEntity);
		}else{
			purchaseDAOImpl.updateCounterStatus(counterDetailsEntity);
		}
		
	}
	
	
	private void updateOrderOutForDelivery(PurchaseEntity purchaseEntity){
		DeliveryDetailsEntity deliveryDetailsEntity = deliveryDAO.getDeliveryDetailsEntity(purchaseEntity);
		if(deliveryDetailsEntity == null){
			deliveryDetailsEntity = new DeliveryDetailsEntity();
			deliveryDetailsEntity.setOutForDeliveryDate(purchaseEntity.getServerDateTime());
			deliveryDetailsEntity.setPurchaseEntity(purchaseEntity);
			deliveryDAO.createDeliveryDetails(deliveryDetailsEntity);
		}
	}
	
	
	private void updateOrderDelivered(PurchaseEntity purchaseEntity){
		DeliveryDetailsEntity deliveryDetailsEntity = deliveryDAO.getDeliveryDetailsEntity(purchaseEntity);
		if(deliveryDetailsEntity != null){
			deliveryDetailsEntity.setDeliveredDate(purchaseEntity.getServerDateTime());
			deliveryDetailsEntity.setDeliveryStatus(DeliveryStatus.SUCCESS);
			deliveryDAO.updateDeliveryDetails(deliveryDetailsEntity);
		}
		
	}
	
	
	private void updateOrderDiscard(OrderStatusUpdate orderStatusUpdate,PurchaseEntity purchaseEntity){
		// Discard
		DiscardEntity discardEntity = new DiscardEntity();
		discardEntity.setDiscardGuid(serviceUtil.uuid());
		discardEntity.setMerchantEntity(purchaseEntity.getMerchantEntity());
		discardEntity.setUserEntity(purchaseEntity.getUserEntity());
		discardEntity.setReason(orderStatusUpdate.getDeclineReason());
		discardEntity.setCreatedDateTime(purchaseEntity.getServerDateTime());
		discardEntity.setPurchaseEntity(purchaseEntity);
		discardEntity.setDiscardBy(DiscardBy.MERCHANT);
		purchaseEntity.setOrderStatus(OrderStatus.CANCELLED);
		
		
		purchaseDAOImpl.createDiscard(discardEntity);
		
		//Send Push Notification
		CloudMessageEntity cloudMessageEntity = userDAOImpl.getCloudMessageEntity(purchaseEntity.getUserEntity());
		if(cloudMessageEntity != null){
			NotificationJson notificationJson = new NotificationJson();
			notificationJson.setNotificationType(NotificationType.STATUS);
			notificationJson.setMessage("Your order has been Declined by Merchant. Because of "+discardEntity.getReason()); // TODO
			notificationJson.setPurchaseGuid(purchaseEntity.getPurchaseGuid());
			serviceUtil.sendAndroidNotification(notificationJson, cloudMessageEntity.getCloudId());
		}
	}

}
