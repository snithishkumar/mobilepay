package in.tn.mobilepay.services;

import java.lang.reflect.Type;
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
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

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
import in.tn.mobilepay.response.model.AddressJson;
import in.tn.mobilepay.response.model.NotificationJson;
import in.tn.mobilepay.response.model.UserJson;
import in.tn.mobilepay.rest.json.AmountDetails;
import in.tn.mobilepay.rest.json.CommonPurchaseData;
import in.tn.mobilepay.rest.json.DeliveredPurchaseData;
import in.tn.mobilepay.rest.json.HistoryPurchaseData;
import in.tn.mobilepay.rest.json.MerchantPurchaseData;
import in.tn.mobilepay.rest.json.MerchantPurchaseDatas;
import in.tn.mobilepay.rest.json.OrderStatusUpdate;
import in.tn.mobilepay.rest.json.OrderStatusUpdateList;
import in.tn.mobilepay.rest.json.PaiedPurchaseDetails;
import in.tn.mobilepay.rest.json.PurchaseDetails;
import in.tn.mobilepay.rest.json.PurchaseItem;
import in.tn.mobilepay.rest.json.PurchaseItems;
import in.tn.mobilepay.rest.json.PurchaseStatus;

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

	private static final Logger logger = Logger.getLogger(PurchaseRestService.class);

	/**
	 * Create New Purchase Entity
	 * 
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> createPurchase(String requestData, Principal principal) {
		MerchantEntity merchantEntity = null;
		try {
			// Get Merchant Entity
		    merchantEntity = serviceUtil.getMerchantEntity(principal);
			// Json to Object
			MerchantPurchaseDatas merchantPurchaseDatas = gson.fromJson(requestData, MerchantPurchaseDatas.class);
			List<MerchantPurchaseData> purchaseDatas = merchantPurchaseDatas.getMerchantPurchaseDatas();
			JsonArray responseData = new JsonArray();
			// Process each data
			for (MerchantPurchaseData merchantPurchaseData : purchaseDatas) {
				try {
					//Validate Merchant Input
					merchantPurchaseData.validateData();
					
					// Validate Customer mobile number
					UserEntity userEntity = validateUserMobile(merchantPurchaseData.getCustomerMobileNo());
					
					
					PurchaseEntity purchaseEntity = new PurchaseEntity();
					// Copy MerchantPurchase Data to Purchase
					copyMerchantPurchaseData(merchantPurchaseData, purchaseEntity);

					purchaseEntity.setUserEntity(userEntity);
					
					purchaseEntity.setMerchantEntity(merchantEntity);

					purchaseDAOImpl.createPurchaseObject(purchaseEntity);
					// Response
					JsonObject response = new JsonObject();
					response.addProperty("statusCode", 200);
					response.addProperty("billNumber", purchaseEntity.getBillNumber());
					response.addProperty("purchaseUUID", purchaseEntity.getPurchaseGuid());
					response.addProperty("message", "Created Successfully.");
					responseData.add(response);
				} catch (ValidationException e) {
					// Error Response
					JsonObject response = new JsonObject();
					response.addProperty("statusCode", e.getCode());
					response.addProperty("billNumber", merchantPurchaseData.getBillNumber());
					response.addProperty("message", e.getMessage());
					responseData.add(response);
				}

			}
			return serviceUtil.getRestResponse(true, responseData, 200);
		}catch(JsonSyntaxException e){
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("Error in Json, Request Data[");
			stringBuilder.append(requestData);
			stringBuilder.append("]");
			if(merchantEntity != null){
				stringBuilder.append("],Merchant Name:");
				stringBuilder.append(merchantEntity.getMerchantName());
			}
			logger.error(stringBuilder.toString(), e);
			return serviceUtil.getRestResponse(false, "OOPS.Invalid Json", 400);
		}catch (Exception e) {
			logger.error("Error in PurchaseRestService", e);
		}
		return serviceUtil.getRestResponse(false, "OOPS.Internal server Error", 500);
	}

	private void copyMerchantPurchaseData(MerchantPurchaseData merchantPurchaseData, PurchaseEntity purchaseEntity) {
		AmountDetails amountDetails = merchantPurchaseData.getAmountDetails();
		if (amountDetails != null) {
			purchaseEntity.setAmountDetails(gson.toJson(amountDetails));
		}
		purchaseEntity.setBillNumber(merchantPurchaseData.getBillNumber());
		purchaseEntity.setMerchantDeliveryOptions(merchantPurchaseData.getDeliveryOptions());
		purchaseEntity.setEditable(merchantPurchaseData.getIsRemovable());
		List<PurchaseItem> purchaseItems = merchantPurchaseData.getPurchaseItems();
		if (purchaseItems != null && purchaseItems.size() > 0) {
			purchaseItems.sort((p1,p2) -> p1.getAmount().compareTo(p2.getAmount()));
			purchaseEntity.setPurchaseData(gson.toJson(purchaseItems));
		}
		purchaseEntity.setPurchaseDateTime(merchantPurchaseData.getPurchaseDate());
		purchaseEntity.setPurchaseGuid(serviceUtil.uuid());
		purchaseEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
		purchaseEntity.setTotalAmount(Double.valueOf(merchantPurchaseData.getTotalAmount()));
		purchaseEntity.setOrderStatus(OrderStatus.PURCHASE);
		purchaseEntity.setPaymentStatus(PaymentStatus.NOT_PAID);
	}
	
	
	
	

	/**
	 * Validate given mobile Number is present or not
	 * 
	 * @param mobileNumber
	 * @return
	 * @throws ValidationException
	 */
	private UserEntity validateUserMobile(String mobileNumber) throws ValidationException {
		UserEntity userEntity = userDAOImpl.getUserEntity(mobileNumber);
		if (userEntity == null) {
			throw new ValidationException(400, "Invalid Customer Number.");
		}
		return userEntity;
	}

	/***
	 * Returns UnPaid Data based on given filters. It returns purchaseUUID,purchaseDate,billNumber,orderStatus,lastModifiedDate and paymentStatus
	 * @param index
	 * @param limit
	 * @param fromDate
	 * @param toDate
	 * @param principal
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getUnPaidList(Integer index, Integer limit, Long fromDate,
			Long toDate, Principal principal) {
		try {
			// Get Merchant Entity
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			// Get Purchase Detail List
			List<PurchaseEntity> purchaseEntities = purchaseDAOImpl.getUnPaiedList(merchantEntity, index, limit, fromDate, toDate);
			List<CommonPurchaseData> purchaseDetailsList = new ArrayList<>();
			boolean isAdded = false;

			// Convert Purchase Entity to Purchase Json
			for (PurchaseEntity purchaseEntity : purchaseEntities) {
				CommonPurchaseData commonPurchaseData = new CommonPurchaseData(purchaseEntity);
				
				purchaseDetailsList.add(commonPurchaseData);
				isAdded = true;
			}
			if (isAdded) {
				return serviceUtil.getRestResponse(true, purchaseDetailsList, 200);
			}
			return serviceUtil.getRestResponse(true, purchaseDetailsList, 403);

		} catch (Exception e) {
			logger.error("Error in getUnPaidList", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.", 500);
	}

	/**
	 * Returns Order Status (Paid and not delivered).
	 * @param index
	 * @param limit
	 * @param fromDate
	 * @param toDate
	 * @param principal
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getPaidList(Integer index, Integer limit, Long fromDate,
			Long toDate, Principal principal) {
		try {
			// Get Merchant Entity
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			// Get Purchase Detail List
			List<PurchaseEntity> purchaseEntities = purchaseDAOImpl.getPaiedList(merchantEntity, index, limit, fromDate, toDate);
			List<PaiedPurchaseDetails> purchaseDetailsList = new ArrayList<>();
			boolean isAdded = false;

			// Convert Purchase Entity to Purchase Json
			for (PurchaseEntity purchaseEntity : purchaseEntities) {
				PaiedPurchaseDetails paiedPurchaseDetails = getPaiedList(purchaseEntity);
				purchaseDetailsList.add(paiedPurchaseDetails);
				isAdded = true;
			}
			if (isAdded) {
				return serviceUtil.getRestResponse(true, purchaseDetailsList, 200);
			}
			return serviceUtil.getRestResponse(true, purchaseDetailsList, 403);

		} catch (Exception e) {
			logger.error("Error in getPaiedList", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.", 500);
	}
	
	
	private PaiedPurchaseDetails getPaiedList(PurchaseEntity purchaseEntity){
		PaiedPurchaseDetails paiedPurchaseDetails = new PaiedPurchaseDetails(purchaseEntity);
		
		// User Amount Details
		getCalculatedAmounts(purchaseEntity, paiedPurchaseDetails);
		
		// Delivery Details
		getDeliveryDetails(purchaseEntity, paiedPurchaseDetails);
		
		return paiedPurchaseDetails;
	}
	
	
	/**
	 * Returns list of cancelled data.
	 * @param index
	 * @param limit
	 * @param fromDate
	 * @param toDate
	 * @param principal
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getCancelledList(Integer index, Integer limit, Long fromDate,
			Long toDate, Principal principal) {
		try {
			// Get Merchant Entity
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			// Get Purchase Detail List
			List<PurchaseEntity> purchaseEntities = purchaseDAOImpl.getCancelledList(merchantEntity, index, limit, fromDate, toDate);
			List<DiscardJson> discardDetailList = new ArrayList<>();
			boolean isAdded = false;

			// Convert Purchase Entity to Purchase Json
			for (PurchaseEntity purchaseEntity : purchaseEntities) {
				DiscardJson discardJson =	getCancelledList(purchaseEntity);
				if(discardJson != null){
					discardDetailList.add(discardJson);
					isAdded = true;
				}
				
			}
			if (isAdded) {
				return serviceUtil.getRestResponse(true, discardDetailList, 200);
			}
			return serviceUtil.getRestResponse(true, discardDetailList, 403);

		} catch (Exception e) {
			logger.error("Error in getCancelledList", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.", 500);
	}
	
	
	private DiscardJson getCancelledList(PurchaseEntity purchaseEntity){
		DiscardEntity discardEntity = purchaseDAOImpl.getDiscardEntity(purchaseEntity);
		if (discardEntity != null) {
			DiscardJson discardJson = new DiscardJson(discardEntity);
			discardJson.setBillNumber( purchaseEntity.getBillNumber());
			discardJson.setPurchaseDate(purchaseEntity.getPurchaseDateTime());
			discardJson.setOrderStatus(purchaseEntity.getOrderStatus());
			discardJson.setLastModifiedDate(purchaseEntity.getUpdatedDateTime());
			discardJson.setPaymentStatus(purchaseEntity.getPaymentStatus());
			return discardJson;
		}
		return null;
	}
	
	/**
	 * Returns list of purchased data (Delivered and cancelled)
	 * @param index
	 * @param limit
	 * @param fromDate
	 * @param toDate
	 * @param principal
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getHistoryList(Integer index, Integer limit, Long fromDate,
			Long toDate, Principal principal) {
		try {
			// Get Merchant Entity
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			// Get Purchase Detail List
			List<PurchaseEntity> purchaseEntities = purchaseDAOImpl.getDeliveredList(merchantEntity, index, limit, fromDate, toDate);
			List<HistoryPurchaseData> historyPurchaseDatas = new ArrayList<>();
			boolean isAdded = false;

			// Convert Purchase Entity to Purchase Json
			for (PurchaseEntity purchaseEntity : purchaseEntities) {
				HistoryPurchaseData historyPurchaseData = getPurchaseHistoryData(purchaseEntity);
				historyPurchaseDatas.add(historyPurchaseData);
				isAdded = true;
			}
			if (isAdded) {
				return serviceUtil.getRestResponse(true, historyPurchaseDatas, 200);
			}
			return serviceUtil.getRestResponse(true, historyPurchaseDatas, 403);

		} catch (Exception e) {
			logger.error("Error in getHistoryList", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.", 500);
	}
	
	
	private HistoryPurchaseData getPurchaseHistoryData(PurchaseEntity purchaseEntity){
		// Convert Purchase to History
		HistoryPurchaseData historyPurchaseData = new HistoryPurchaseData(purchaseEntity);
		
		// Get Address Json
		AddressJson addressJson = getDeliveryDetails(purchaseEntity);
		historyPurchaseData.setDeliveryAddress(addressJson);
		
		// Get Amount Json
		AmountDetails amountDetails = getCalculatedAmounts(purchaseEntity);
		historyPurchaseData.setAmountDetails(amountDetails);
		
		// Get Purchase Item
		String purchaseData = purchaseEntity.getPurchaseData();
		PurchaseItems purchaseItems = serviceUtil.fromJson(purchaseData, PurchaseItems.class);
		historyPurchaseData.setPurchaseItem(purchaseItems.getPurchaseItems());
		
		// Get Discard Json
		DiscardJson discardJson = getDiscardDetails(purchaseEntity);
		historyPurchaseData.setDiscardDetails(discardJson);
		return historyPurchaseData;
	}
	
	/**
	 * Returns list of Delivered Data.
	 * @param index
	 * @param limit
	 * @param fromDate
	 * @param toDate
	 * @param principal
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getDeliveredList(Integer index, Integer limit, Long fromDate,
			Long toDate, Principal principal) {
		try {
			// Get Merchant Entity
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			// Get Purchase Detail List
			List<PurchaseEntity> purchaseEntities = purchaseDAOImpl.getDeliveredList(merchantEntity, index, limit, fromDate, toDate);
			List<DeliveredPurchaseData> deliveredPurchaseDatas = new ArrayList<>();
			boolean isAdded = false;

			// Convert Purchase Entity to Purchase Json
			for (PurchaseEntity purchaseEntity : purchaseEntities) {
				// Convert 
				DeliveredPurchaseData deliveredPurchaseData = new DeliveredPurchaseData(purchaseEntity);
				
				AddressJson addressJson = getDeliveryDetails(purchaseEntity);
				deliveredPurchaseData.setDeliveryAddressDetails(addressJson);
				
				AmountDetails amountDetails = getCalculatedAmounts(purchaseEntity);
				deliveredPurchaseData.setAmountDetails(amountDetails);
				
				String purchaseData = purchaseEntity.getPurchaseData();
				PurchaseItems purchaseItems = serviceUtil.fromJson(purchaseData, PurchaseItems.class);
				deliveredPurchaseData.setPurchaseItem(purchaseItems.getPurchaseItems());
				deliveredPurchaseDatas.add(deliveredPurchaseData);
				isAdded = true;
			}
			if (isAdded) {
				return serviceUtil.getRestResponse(true, deliveredPurchaseDatas, 200);
			}
			return serviceUtil.getRestResponse(true, deliveredPurchaseDatas, 403);

		} catch (Exception e) {
			logger.error("Error in getCancelledList", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.", 500);
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseStatus(String purchaseGuidsJson, Principal principal) {
		try {
			
			Type listType = new TypeToken<ArrayList<String>>() {
			}.getType();
			
			List<String> purchaseGuids = serviceUtil.fromJson(purchaseGuidsJson, listType);
			if(purchaseGuids.size() > 20){
				return serviceUtil.getRestResponse(false, "Request not morethan 20.", 403);
			}
			
			// Get Merchant Entity
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			// Get Purchase Detail List
			List<PurchaseEntity> purchaseEntities = purchaseDAOImpl.getPurhcaseList(merchantEntity, purchaseGuids);
			boolean isAdded = false;
			PurchaseStatus purchaseStatus = new PurchaseStatus();
			// Convert Purchase Entity to Purchase Json
			for (PurchaseEntity purchaseEntity : purchaseEntities) {
				isAdded = true;
				switch (purchaseEntity.getOrderStatus()) {
				case PURCHASE:
					CommonPurchaseData commonPurchaseData = new CommonPurchaseData(purchaseEntity);
					purchaseStatus.getUnPaidData().add(commonPurchaseData);
					break;
				case FAILED_TO_DELIVER:
				case OUT_FOR_DELIVERY:
				case PACKING:
				case READY_TO_COLLECT:
				case READY_TO_SHIPPING:
					PaiedPurchaseDetails paiedPurchaseDetails = getPaiedList(purchaseEntity);
					purchaseStatus.getPaidData().add(paiedPurchaseDetails);
					break;
					
				case CANCELLED:
				case DELIVERED:
					HistoryPurchaseData historyPurchaseData = getPurchaseHistoryData(purchaseEntity);
					purchaseStatus.getHistoryData().add(historyPurchaseData);
					break;

				default:
					break;
				}
			}
			if (isAdded) {
				return serviceUtil.getRestResponse(true, purchaseStatus, 200);
			}
			return serviceUtil.getRestResponse(true, purchaseStatus, 403);

		} catch (Exception e) {
			logger.error("Error in getPurchaseStatus", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.", 500);
	}
	
	/**
	 * Get List of Purchase Details
	 * 
	 * @param index
	 * @param limit
	 * @param status
	 * @param fromDate
	 * @param toDate
	 * @param principal
	 * @return ResponseEntity
	 */
	/*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseList(Integer index, Integer limit, String status, Long fromDate,
			Long toDate, Principal principal) {
		try {
			// Get Merchant Entity
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			// Get Purchase Detail List
			List<PurchaseEntity> purchaseEntities = purchaseDAOImpl.getPurchaseEntityList(merchantEntity, index, limit,
					status, fromDate, toDate);
			List<PurchaseDetails> purchaseDetailsList = new ArrayList<>();
			boolean isAdded = false;
			// Convert Purchase Entity to Purchase Json
			for (PurchaseEntity purchaseEntity : purchaseEntities) {
				PurchaseDetails purchaseDetails = getPurchaseData(purchaseEntity);
				purchaseDetailsList.add(purchaseDetails);
				isAdded = true;
			}
			if (isAdded) {
				return serviceUtil.getRestResponse(true, purchaseDetailsList, 200);
			}
			return serviceUtil.getRestResponse(true, purchaseDetailsList, 403);
		} catch (Exception e) {
			logger.error("Error in getPurchaseList", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error.", 500);
	}*/
	
	/**
	 * Get Delivery Details
	 * @param purchaseEntity
	 * @param purchaseDetails
	 */
	private AddressJson getDeliveryDetails(PurchaseEntity purchaseEntity){
		if (purchaseEntity.getUserDeliveryOptions() != null
				&& purchaseEntity.getUserDeliveryOptions().toString().equals(DeliveryOptions.HOME.toString())) {
			Collection<AddressEntity> addressEntities = purchaseEntity.getAddressEntities();
			if (addressEntities != null) {
				for (AddressEntity addressEntity : addressEntities) {
					AddressJson addressJson = new AddressJson(addressEntity);
					return addressJson;
				}
			}
		}
		return null;
	}
	
	
	/**
	 * Get Delivery Details
	 * @param purchaseEntity
	 * @param purchaseDetails
	 */
	private void getDeliveryDetails(PurchaseEntity purchaseEntity,PaiedPurchaseDetails purchaseDetails){
		purchaseDetails.setDeliveryAddress(getDeliveryDetails(purchaseEntity));
	}
	
	
	/**
	 * Get Delivery Details
	 * @param purchaseEntity
	 * @param purchaseDetails
	 */
	private void getDeliveryDetails(PurchaseEntity purchaseEntity,PurchaseDetails purchaseDetails){
		purchaseDetails.setDeliverAddress(getDeliveryDetails(purchaseEntity));
	}
	
	/**
	 * Get User Details
	 * @param purchaseEntity
	 * @param purchaseDetails
	 */
	private void getUserDetails(PurchaseEntity purchaseEntity,PurchaseDetails purchaseDetails){
		UserEntity userEntity = purchaseEntity.getUserEntity();
		if(userEntity != null){
			UserJson userJson = new UserJson(userEntity);
			purchaseDetails.setUserDetails(userJson);
		}
		
	}
	
	
	/**
	 * Get Calculate Amount Details
	 * @param purchaseEntity
	 * @param purchaseDetails
	 */
	private void getCalculatedAmounts(PurchaseEntity purchaseEntity,PaiedPurchaseDetails purchaseDetails){
		purchaseDetails.setAmountDetails(getCalculatedAmounts(purchaseEntity));
	}
	
	
	
	
	/**
	 * Get Calculate Amount Details
	 * @param purchaseEntity
	 * @param purchaseDetails
	 */
	private AmountDetails getCalculatedAmounts(PurchaseEntity purchaseEntity){
		String calculatedAmount = purchaseEntity.getCalculatedAmounts();
		if(calculatedAmount != null){
			AmountDetails amountDetails = serviceUtil.fromJson(calculatedAmount, AmountDetails.class);
			return amountDetails;
		}
		return null;
	}
	
	/**
	 * Convert Discard Entity to Discard Json
	 * @param purchaseEntity
	 * @param purchaseDetails
	 */
	private void getDiscardDetails(PurchaseEntity purchaseEntity,PurchaseDetails purchaseDetails){
		purchaseDetails.setDiscardDetails(getDiscardDetails(purchaseEntity));
	}
	
	
	
	/**
	 * Convert Discard Entity to Discard Json
	 * @param purchaseEntity
	 * @param purchaseDetails
	 */
	private DiscardJson getDiscardDetails(PurchaseEntity purchaseEntity){
		if (purchaseEntity.getOrderStatus().ordinal() == OrderStatus.CANCELLED.ordinal()) {
			DiscardEntity discardEntity = purchaseDAOImpl.getDiscardEntity(purchaseEntity);
			if (discardEntity != null) {
				DiscardJson discardJson = new DiscardJson(discardEntity);
				return discardJson;
			}

		}
		return null;
	}
	
	

	/**
	 * Purchase Entity to Purchase Json
	 * 
	 * @param purchaseEntity
	 * @return
	 */
	private PurchaseDetails getPurchaseData(PurchaseEntity purchaseEntity) {
		PurchaseDetails purchaseDetails = new PurchaseDetails(purchaseEntity,serviceUtil);
		
		getDiscardDetails(purchaseEntity, purchaseDetails);
		
		getDeliveryDetails(purchaseEntity, purchaseDetails);

		
		return purchaseDetails;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseData(String purchaseUUID, Principal principal) {
		try {
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			PurchaseEntity purchaseEntity = purchaseDAOImpl.getPurchaseEntity(purchaseUUID, merchantEntity);
			if (purchaseEntity == null) {
				return serviceUtil.getRestResponse(false, "PurchaseUUID is not matched", 404);
			}
			PurchaseDetails purchaseDetails = getPurchaseData(purchaseEntity);

			String amountDetailsJson = purchaseEntity.getCalculatedAmounts();
			AmountDetails amountDetails = serviceUtil.fromJson(amountDetailsJson, AmountDetails.class);
			purchaseDetails.setAmountDetails(amountDetails);

			
			getUserDetails(purchaseEntity, purchaseDetails);
			
			return serviceUtil.getRestResponse(true, purchaseDetails, 200);
		} catch (Exception e) {
			logger.error("Error in getPurchaseData", e);
		}
		return serviceUtil.getRestResponse(false, "Internal Server Error", 500);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> updateOrderStatus(Principal principal, String orderStatusJson) {
		try {
			OrderStatusUpdateList orderStatusUpdateList = serviceUtil.fromJson(orderStatusJson,
					OrderStatusUpdateList.class);
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			List<OrderStatusUpdate> orderStatusUpdates = orderStatusUpdateList.getOrderStatusUpdates();
			JsonArray results = new JsonArray();
			for (OrderStatusUpdate orderStatusUpdate : orderStatusUpdates) {
				JsonObject result = updateOrderStatus(orderStatusUpdate, merchantEntity);
				results.add(result);
			}
			return serviceUtil.getRestResponse(true, results, 200);

		} catch (Exception e) {
			logger.error("Error in updateOrderStatus", e);
		}
		return serviceUtil.getRestResponse(false, "OOPS.Internal server Error", 500);
	}

	private JsonObject updateOrderStatus(OrderStatusUpdate orderStatusUpdate, MerchantEntity merchantEntity) {
		JsonObject result = new JsonObject();
		try {
			PurchaseEntity purchaseEntity = purchaseDAOImpl.getPurchaseEntity(orderStatusUpdate.getPurchaseUUID(),
					merchantEntity);
			if (purchaseEntity == null) {
				logger.error("Invalid PurchaseUUID [" + orderStatusUpdate.getPurchaseUUID() + "],principal["
						+ merchantEntity + "]");
				result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
				result.addProperty("statusCode", 404);
				result.addProperty("message", "PurchaseUUID is not found.");
				return result;
			}

			purchaseEntity.setUpdatedDateTime(ServiceUtil.getCurrentGmtTime());
			purchaseEntity.setServerDateTime(purchaseEntity.getUpdatedDateTime());

			switch (orderStatusUpdate.getOrderStatus()) {
			case CANCELLED:
				if(purchaseEntity.getPaymentStatus().getPaymentStatusType() == PaymentStatus.PAID.getPaymentStatusType()){
					result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
					result.addProperty("statusCode", 405);
					result.addProperty("message", "Already paid by Customer.");
					return result;
				}
				
				purchaseEntity.setOrderStatus(OrderStatus.CANCELLED);
				updateOrderDiscard(orderStatusUpdate, purchaseEntity);

				break;
			case DELIVERED:
				if(purchaseEntity.getPaymentStatus().getPaymentStatusType() == PaymentStatus.NOT_PAID.getPaymentStatusType()){
					result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
					result.addProperty("statusCode", 406);
					result.addProperty("message", "Customer is not yet Paid. or Already Cancelled");
					return result;
				}
				purchaseEntity.setOrderStatus(OrderStatus.DELIVERED);
				updateOrderDelivered(purchaseEntity);

				break;
			case FAILED_TO_DELIVER:
				// TODO  added in next release
				break;
			case OUT_FOR_DELIVERY:
				if(purchaseEntity.getOrderStatus().getOrderStatusId() == OrderStatus.PURCHASE.getOrderStatusId()){
					result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
					result.addProperty("statusCode", 406);
					result.addProperty("message", "Customer is not yet Paid");
					return result;
				}else if(purchaseEntity.getOrderStatus().getOrderStatusId() >= OrderStatus.DELIVERED.getOrderStatusId()){
					result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
					result.addProperty("statusCode", 407);
					result.addProperty("message", "Purchase is already Delivered or  Cancelled.Current Status is "+purchaseEntity.getOrderStatus());
					return result;
				}/*else if(purchaseEntity.getUserDeliveryOptions() == null || purchaseEntity.getUserDeliveryOptions().getDeliveryOptions() != DeliveryOptions.HOME.getDeliveryOptions()){
					result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
					result.addProperty("statusCode", 408);
					result.addProperty("message", "Customer not choose home delivery.Customer delivery options is "+purchaseEntity.getUserDeliveryOptions());
					return result;
				}*/// Need to get opinion from merchant -- TODO
				purchaseEntity.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
				updateOrderOutForDelivery(purchaseEntity);
				break;
			case READY_TO_COLLECT:
				if(purchaseEntity.getOrderStatus().getOrderStatusId() == OrderStatus.PURCHASE.getOrderStatusId()){
					result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
					result.addProperty("statusCode", 406);
					result.addProperty("message", "Customer is not yet Paid");
					return result;
				}else if(purchaseEntity.getOrderStatus().getOrderStatusId() >= OrderStatus.DELIVERED.getOrderStatusId()){
					result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
					result.addProperty("statusCode", 407);
					result.addProperty("message", "Purchase is already Delivered or  Cancelled.Current Status is "+purchaseEntity.getOrderStatus());
					return result;
				}
				purchaseEntity.setOrderStatus(OrderStatus.READY_TO_COLLECT);
				updateCounterDetails(orderStatusUpdate, purchaseEntity);
				break;
			case READY_TO_SHIPPING:
				if(purchaseEntity.getOrderStatus().getOrderStatusId() == OrderStatus.PURCHASE.getOrderStatusId()){
					result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
					result.addProperty("statusCode", 406);
					result.addProperty("message", "Customer is not yet Paid");
					return result;
				}else if(purchaseEntity.getOrderStatus().getOrderStatusId() >= OrderStatus.DELIVERED.getOrderStatusId()){
					result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
					result.addProperty("statusCode", 407);
					result.addProperty("message", "Purchase is already Delivered or  Cancelled.Current Status is "+purchaseEntity.getOrderStatus());
					return result;
				}/*else if(purchaseEntity.getUserDeliveryOptions() == null || purchaseEntity.getUserDeliveryOptions().getDeliveryOptions() != DeliveryOptions.HOME.getDeliveryOptions()){
					result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
					result.addProperty("statusCode", 408);
					result.addProperty("message", "Customer not choose home delivery.Customer delivery options is "+purchaseEntity.getUserDeliveryOptions());
					return result;
				}*/ // Need to get opinion from merchant -- TODO
				purchaseEntity.setOrderStatus(OrderStatus.READY_TO_SHIPPING);
				sendPushNotification(purchaseEntity, "Your order has been Ready. It will be dispatched shortly.");
				break;

			default:
				break;
			}
			purchaseDAOImpl.updatePurchaseObject(purchaseEntity);
			result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
			result.addProperty("statusCode", "200");
			result.addProperty("message", "Success.");
		} catch (Exception e) {
			logger.error("Error in updateOrderStatus", e);
			result.addProperty("purchaseUUID", orderStatusUpdate.getPurchaseUUID());
			result.addProperty("statusCode", "500");
			result.addProperty("message", "OOPS.Internal server Error.");
		}
		return result;
	}

	private void updateCounterDetails(OrderStatusUpdate orderStatusUpdate, PurchaseEntity purchaseEntity) {
		CounterDetailsEntity counterDetailsEntity = deliveryDAO.geCounterDetailsEntity(purchaseEntity.getPurchaseId());
		boolean isCreate = false;
		if (counterDetailsEntity == null) {
			counterDetailsEntity = new CounterDetailsEntity();
			counterDetailsEntity.setCounterGuid(serviceUtil.uuid());
			isCreate = true;
		}

		counterDetailsEntity.setCounterNumber(orderStatusUpdate.getCounterNumber());
		counterDetailsEntity.setCreatedDateTime(purchaseEntity.getServerDateTime());
		counterDetailsEntity.setMessage(orderStatusUpdate.getDescription());

		counterDetailsEntity.setPurchaseEntity(purchaseEntity);
		purchaseEntity.setOrderStatus(OrderStatus.READY_TO_COLLECT);
		if (isCreate) {
			purchaseDAOImpl.createCounterStatus(counterDetailsEntity);
		} else {
			purchaseDAOImpl.updateCounterStatus(counterDetailsEntity);
		}
		sendPushNotification(purchaseEntity, "Your order has been Ready.You can collect it from "+purchaseEntity.getMerchantEntity().getMerchantName());
//
	}

	private void updateOrderOutForDelivery(PurchaseEntity purchaseEntity) {
		DeliveryDetailsEntity deliveryDetailsEntity = deliveryDAO.getDeliveryDetailsEntity(purchaseEntity);
		if (deliveryDetailsEntity == null) {
			deliveryDetailsEntity = new DeliveryDetailsEntity();
			deliveryDetailsEntity.setOutForDeliveryDate(purchaseEntity.getServerDateTime());
			deliveryDetailsEntity.setPurchaseEntity(purchaseEntity);
			deliveryDAO.createDeliveryDetails(deliveryDetailsEntity);
		}
		
		sendPushNotification(purchaseEntity, "Your order has went to delivery. It will be dispatched shortly.");
	}

	private void updateOrderDelivered(PurchaseEntity purchaseEntity) {
		DeliveryDetailsEntity deliveryDetailsEntity = deliveryDAO.getDeliveryDetailsEntity(purchaseEntity);
		if (deliveryDetailsEntity != null) {
			deliveryDetailsEntity.setDeliveredDate(purchaseEntity.getServerDateTime());
			deliveryDetailsEntity.setDeliveryStatus(DeliveryStatus.SUCCESS);
			deliveryDAO.updateDeliveryDetails(deliveryDetailsEntity);
			
		}
		sendPushNotification(purchaseEntity, "Your order has been Deliverd. Thanks for Shopping!!!");
		
	}

	private void updateOrderDiscard(OrderStatusUpdate orderStatusUpdate, PurchaseEntity purchaseEntity) {
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
		sendPushNotification(purchaseEntity, "Your order has been Declined by Merchant. Because of " + discardEntity.getReason());
		
	}
	
	
	private void sendPushNotification(PurchaseEntity purchaseEntity,String message){
		// Send Push Notification
				CloudMessageEntity cloudMessageEntity = userDAOImpl.getCloudMessageEntity(purchaseEntity.getUserEntity());
				if (cloudMessageEntity != null) {
					NotificationJson notificationJson = new NotificationJson();
					notificationJson.setNotificationType(NotificationType.STATUS);
					notificationJson.setMessage(message); 
					notificationJson.setPurchaseGuid(purchaseEntity.getPurchaseGuid());
					serviceUtil.sendAndroidNotification(notificationJson, cloudMessageEntity.getCloudId());
				}
	}

}
