package in.tn.mobilepay.services;

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

import in.tn.mobilepay.dao.MerchantDAO;
import in.tn.mobilepay.dao.PurchaseDAO;
import in.tn.mobilepay.dao.UserDAO;
import in.tn.mobilepay.entity.AddressEntity;
import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.MerchantEntity;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.DiscardBy;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.request.model.DiscardJsonList;
import in.tn.mobilepay.request.model.GetLuggageList;
import in.tn.mobilepay.request.model.GetPurchaseList;
import in.tn.mobilepay.request.model.OrderStatusUpdate;
import in.tn.mobilepay.request.model.OrderStatusUpdateJsonList;
import in.tn.mobilepay.request.model.PayedPurchaseDetailsJson;
import in.tn.mobilepay.request.model.PayedPurchaseDetailsList;
import in.tn.mobilepay.request.model.PurchaseDetailsJson;
import in.tn.mobilepay.request.model.UnPayedMerchantPurchaseJson;
import in.tn.mobilepay.response.model.LuggageJson;
import in.tn.mobilepay.response.model.LuggagesListJson;
import in.tn.mobilepay.response.model.MerchantJson;
import in.tn.mobilepay.response.model.PurchaseJson;
import in.tn.mobilepay.response.model.UserJson;
import in.tn.mobilepay.util.StatusCode;

@Service
public class PurchaseServices {

	@Autowired
	private PurchaseDAO purchaseDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private MerchantDAO merchantDAO;
	@Autowired
	private ServiceUtil serviceUtil;
	
	private static final Logger logger = Logger.getLogger(PurchaseServices.class);

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseDetails(int purchaseId) {
		try {
			List<PurchaseEntity> purchaseList = purchaseDAO.gePurchase(purchaseId);
			List<PurchaseJson> purchaseJsons = new ArrayList<PurchaseJson>();
			for (PurchaseEntity purchaseEntity : purchaseList) {
				PurchaseJson purchaseJson = new PurchaseJson(purchaseEntity);
				UserJson userJson = new UserJson(purchaseEntity.getUserEntity());
				purchaseJson.setUsers(userJson);
				MerchantJson merchantJson = new MerchantJson(purchaseEntity.getMerchantEntity());
				purchaseJson.setMerchants(merchantJson);
				purchaseJsons.add(purchaseJson);
			}
			String responseJson = serviceUtil.toJson(purchaseJsons);
			String responseEncrypt = serviceUtil.netEncryption(responseJson);
			return serviceUtil.getSuccessResponse(HttpStatus.OK, responseEncrypt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}

	
	
	/**
	 * Discard Purchase Data
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> discardPurchase(String requestData){
		try{
			// Json to Object
			DiscardJsonList discardJsonList = serviceUtil.fromJson(requestData, DiscardJsonList.class);
			for(DiscardJson discardJson : discardJsonList.getDiscardJsons()){
				
				// Validate Merchant Authorize
				MerchantEntity merchantEntity = validateToken(discardJson.getAccessToken(), discardJson.getServerToken());
				//  Validate User Mobile
				UserEntity userEntity = validateMobile(discardJson.getUserMobile());
				// Get Purchase Data
				PurchaseEntity purchaseEntity  = purchaseDAO.getPurchaseEntity(discardJson.getPurchaseGuid());
				// Discard
				DiscardEntity discardEntity = new DiscardEntity();
				discardEntity.setDiscardGuid(serviceUtil.uuid());
				discardEntity.setMerchantEntity(merchantEntity);
				discardEntity.setUserEntity(userEntity);
				discardEntity.setReason(discardJson.getReason());
				discardEntity.setCreatedDateTime(ServiceUtil.getCurrentGmtTime());
				discardEntity.setPurchaseEntity(purchaseEntity);
				discardEntity.setDiscardBy(DiscardBy.MERCHANT);
				purchaseEntity.setDiscard(true);
				purchaseEntity.setOrderStatus(OrderStatus.CANCELED.toString());
				purchaseEntity.setServerDateTime(discardEntity.getCreatedDateTime());
				purchaseEntity.setUpdatedDateTime(discardEntity.getCreatedDateTime());
				purchaseDAO.updatePurchaseObject(purchaseEntity);
				purchaseDAO.createDiscard(discardEntity);
			}
			
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
	public ResponseEntity<String> syncPayedData(String requestData){
		try{
			PayedPurchaseDetailsList payedPurchaseDetailsJsons = serviceUtil.fromJson(requestData,PayedPurchaseDetailsList.class);
			UserEntity userEntity = validateUserToken(payedPurchaseDetailsJsons.getAccessToken(), payedPurchaseDetailsJsons.getServerToken());
			List<PurchaseJson> purchaseJsons = new ArrayList<>();
			Map<String, AddressEntity> addressList = new HashMap<>();
			for (PayedPurchaseDetailsJson payedPurchaseDetailsJson : payedPurchaseDetailsJsons.getPurchaseDetailsJsons()) {
				PurchaseEntity purchaseEntity = purchaseDAO.getNonDiscardPurchaseEntity(payedPurchaseDetailsJson.getPurchaseId());
				if(purchaseEntity != null){
					purchaseEntity.setDeliveryOptions(payedPurchaseDetailsJson.getDeliveryOptions());
					switch (payedPurchaseDetailsJson.getDeliveryOptions()) {
					case NONE:
						purchaseEntity.setOrderStatus(OrderStatus.DELIVERED.toString());
						break;
					case LUGGAGE:
						purchaseEntity.setOrderStatus(OrderStatus.PACKING.toString());
						break;
					case HOME:
						purchaseEntity.setOrderStatus(OrderStatus.PACKING.toString());
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
						break;
					
					}
					
					
					purchaseEntity.setPayed(true);
					purchaseEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
					purchaseEntity.setUpdatedDateTime(payedPurchaseDetailsJson.getPayemetTime());
					purchaseEntity.setUnModifiedAmountDetails(purchaseEntity.getAmountDetails());
					purchaseEntity.setUnModifiedPurchaseData(purchaseEntity.getPurchaseData());
					purchaseEntity.setAmountDetails(payedPurchaseDetailsJson.getAmountDetails());
					purchaseEntity.setPurchaseData(payedPurchaseDetailsJson.getProductDetails());
					purchaseDAO.updatePurchaseObject(purchaseEntity);
					PurchaseJson purchaseJson = new PurchaseJson();
					purchaseJson.setPurchaseId(purchaseEntity.getPurchaseGuid());
					purchaseJson.setServerDateTime(purchaseEntity.getPurchaseDateTime());
					purchaseJsons.add(purchaseJson);
				}
				
			}
			String responseJson = serviceUtil.toJson(purchaseJsons);
			return serviceUtil.getResponse(200, responseJson);
		}catch(Exception e){
			logger.error("Error in discardPurchaseByUser", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "failure");
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> discardPurchaseByUser(String requestData){
		try {
			// Json to Object
			DiscardJsonList discardJsonList = serviceUtil.fromJson(requestData,DiscardJsonList.class);
			UserEntity userEntity = validateUserToken(discardJsonList.getAccessToken(), discardJsonList.getServerToken());
			List<PurchaseJson> purchaseJsons = new ArrayList<>();
			for (DiscardJson discardJson : discardJsonList.getDiscardJsons()) {
				PurchaseEntity purchaseEntity = purchaseDAO.getPurchaseEntity(discardJson.getPurchaseGuid());
				DiscardEntity discardEntity = new DiscardEntity();
				discardEntity.setDiscardGuid(serviceUtil.uuid());
				discardEntity.setUserEntity(userEntity);
				discardEntity.setReason(discardJson.getReason());
				discardEntity.setCreatedDateTime(ServiceUtil.getCurrentGmtTime());
				discardEntity.setPurchaseEntity(purchaseEntity);
				discardEntity.setDiscardBy(DiscardBy.USER);
				purchaseEntity.setDiscard(true);
				purchaseEntity.setOrderStatus(OrderStatus.CANCELED.toString());
				purchaseEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
				purchaseEntity.setUpdatedDateTime(discardEntity.getCreatedDateTime());
				purchaseDAO.updatePurchaseObject(purchaseEntity);
				purchaseDAO.createDiscard(discardEntity);
				PurchaseJson purchaseJson = new PurchaseJson();
				purchaseJson.setPurchaseId(purchaseEntity.getPurchaseGuid());
				purchaseJson.setServerDateTime(purchaseEntity.getPurchaseDateTime());
				purchaseJsons.add(purchaseJson);
			}
			String responseJson = serviceUtil.toJson(purchaseJsons);
			return serviceUtil.getResponse(200, responseJson);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in discardPurchaseByUser", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "failure");
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> updateOrderStatus(String requestData){
		try{
			OrderStatusUpdateJsonList orderStatusUpdateJsonList =	serviceUtil.fromJson(requestData, OrderStatusUpdateJsonList.class);
			MerchantEntity merchantEntity = validateToken(orderStatusUpdateJsonList.getAccessToken(), orderStatusUpdateJsonList.getServerToken());
			for(OrderStatusUpdate orderStatusUpdate : orderStatusUpdateJsonList.getOrderStatusUpdates()){
				PurchaseEntity purchaseEntity = purchaseDAO.getPurchaseEntity(orderStatusUpdate.getPurchaseUUID(),merchantEntity);
				if(purchaseEntity != null){
					purchaseEntity.setOrderStatus(orderStatusUpdate.getOrderStatus());
					purchaseEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
					purchaseEntity.setUpdatedDateTime(purchaseEntity.getServerDateTime());
					purchaseDAO.updatePurchaseObject(purchaseEntity);
				}
			}
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
			//Check whether Purchase Details already Present
			PurchaseEntity dbPurchaseEntity = purchaseDAO.getPurchaseEntity(purchaseJson.getPurchaseUuid());
			// Create New Purchase record
			if(dbPurchaseEntity == null){
				dbPurchaseEntity = new PurchaseEntity();
				dbPurchaseEntity.setPurchaseDateTime(Long.valueOf(purchaseJson.getDateTime()));
				dbPurchaseEntity.setMerchantEntity(merchantEntity);
				dbPurchaseEntity.setUserEntity(userEntity);
				// Json obj to Entity
				populatePurchaseData(dbPurchaseEntity, purchaseJson);
				//Create New Record
				purchaseDAO.createPurchaseObject(dbPurchaseEntity);
				//Response
				return serviceUtil.getResponse(StatusCode.MER_OK, "success");
			}else{ // Update Purchase Data // -- TODO
				populatePurchaseData(dbPurchaseEntity, purchaseJson);
				dbPurchaseEntity.setMerchantEntity(merchantEntity);
				dbPurchaseEntity.setUserEntity(userEntity);
				purchaseDAO.updatePurchaseObject(dbPurchaseEntity);
				return serviceUtil.getResponse(StatusCode.MER_OK, "updated");
			}
			
		}catch(ValidationException e){
			logger.error("Error in ValidationException", e);
			return serviceUtil.getResponse(e.getCode(), e.getMessage());
		}catch(Exception e){
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
	
	
	private UserEntity validateUserToken(String client,String serverToken) throws ValidationException{
		UserEntity userEntity = userDAO.getUserEnityByToken(client, serverToken);
		if(userEntity == null){
			throw new ValidationException(10, "Invalid User", null);
		}
		return userEntity;
		
	}
	
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
		String purchaseData = serviceUtil.toJson(purchaseJson.getPurchaseDetails());
		purchaseEntity.setPurchaseData(purchaseData);
		String amountDetails = serviceUtil.toJson(purchaseJson.getAmountDetails());
		purchaseEntity.setAmountDetails(amountDetails);
		
	}
	
	/**
	 * Returns Current Purchase List
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseList(String requestData) {
		try {
			// Json to Object
			GetPurchaseList getPurchaseList =	serviceUtil.fromJson(requestData, GetPurchaseList.class);
			// Validate User Details
			UserEntity userEntity = validateUserToken(getPurchaseList.getAccessToken(), getPurchaseList.getServerToken());
			// Get Current Purchase List
			List<PurchaseEntity> purchaseList = purchaseDAO.gePurchaseList(getPurchaseList.getServerTime(),userEntity);
			// Entity to Json Object
			List<PurchaseJson> purchaseJsons = new ArrayList<PurchaseJson>();
			for (PurchaseEntity purchaseEntity : purchaseList) {
				PurchaseJson purchaseJson = new PurchaseJson(purchaseEntity);
				UserJson userJson = new UserJson(userEntity);
				purchaseJson.setUsers(userJson);
				MerchantJson merchantJson = new MerchantJson(purchaseEntity.getMerchantEntity());
				purchaseJson.setMerchants(merchantJson);
				purchaseJsons.add(purchaseJson);
			}
			String responseJson = serviceUtil.toJson(purchaseJsons);
		//	String responseEncrypt = serviceUtil.netEncryption(responseJson);
			return serviceUtil.getResponse(300, responseJson);
		} catch (Exception e) {
			logger.error("Error in getPurchaseList",e);
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}
	
	/**
	 * Returns Purchase History List
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseHistoryList(String requestData) {
		try {
			//Json to Object
			GetPurchaseList getPurchaseList =	serviceUtil.fromJson(requestData, GetPurchaseList.class);
			// Validate User Details
			UserEntity userEntity = validateUserToken(getPurchaseList.getAccessToken(), getPurchaseList.getServerToken());
			// Get Current Purchase List
			List<PurchaseEntity> purchaseList = purchaseDAO.getPurchaseHistoryList(getPurchaseList.getServerTime(),userEntity);
			// Entity to Json Object
			List<PurchaseJson> purchaseJsons = new ArrayList<PurchaseJson>();
			for (PurchaseEntity purchaseEntity : purchaseList) {
				DiscardEntity discardEntity = purchaseDAO.getDiscardEntity(purchaseEntity);
				DiscardJson discardJson = new DiscardJson(discardEntity);
				PurchaseJson purchaseJson = new PurchaseJson(purchaseEntity);
				UserJson userJson = new UserJson(userEntity);
				purchaseJson.setUsers(userJson);
				MerchantJson merchantJson = new MerchantJson(purchaseEntity.getMerchantEntity());
				purchaseJson.setMerchants(merchantJson);
				purchaseJsons.add(purchaseJson);
				purchaseJson.setDiscardJson(discardJson);
			}
			String responseJson = serviceUtil.toJson(purchaseJsons);
		//	String responseEncrypt = serviceUtil.netEncryption(responseJson);
			return serviceUtil.getResponse(300, responseJson);
		} catch (Exception e) {
			logger.error("Error in getPurchaseHistoryList",e);
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}
	
	/**
	 * Returns Luggage List
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getLuggageList(String requestData){
		try{
			//Json to Object
			GetLuggageList getLuggageList =	serviceUtil.fromJson(requestData, GetLuggageList.class);
			// Validate User Details
			UserEntity userEntity = validateUserToken(getLuggageList.getAccessToken(), getLuggageList.getServerToken());
			LuggagesListJson luggagesListJson = new LuggagesListJson();
			List<PurchaseEntity> purchaseList = null;
			// Get Luggage Details and Purchase with Luggage Details
			if(getLuggageList.getStartTime() > 0){
				List<LuggageJson> luggageJsons = purchaseDAO.getLuggageList(getLuggageList.getStartTime() , getLuggageList.getEndTime(), userEntity);
				luggagesListJson.setLuggageJsons(luggageJsons);
				purchaseList = purchaseDAO.getLuggageWithPurchaseList(getLuggageList.getStartTime() , getLuggageList.getEndTime(), userEntity);
			}else{ // Get  Purchase with Luggage Full List
				purchaseList = purchaseDAO.getLuggageWithPurchaseList(userEntity);
			}
			
			// Entity to Json Object
			List<PurchaseJson> purchaseJsons = new ArrayList<PurchaseJson>();
			luggagesListJson.setPurchaseJsons(purchaseJsons);
			for (PurchaseEntity purchaseEntity : purchaseList) {
				PurchaseJson purchaseJson = new PurchaseJson(purchaseEntity);
				UserJson userJson = new UserJson(userEntity);
				purchaseJson.setUsers(userJson);
				MerchantJson merchantJson = new MerchantJson(purchaseEntity.getMerchantEntity());
				purchaseJson.setMerchants(merchantJson);
				purchaseJsons.add(purchaseJson);
			}
			String responseJson = serviceUtil.toJson(luggagesListJson);
		//	String responseEncrypt = serviceUtil.netEncryption(responseJson);
			return serviceUtil.getResponse(300, responseJson);
		}catch(Exception e){
			logger.error("Error in getLuggageList",e);
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}
	
	
	public ResponseEntity<String> getUnPayedPurchase(String requestData){
		try{
			UnPayedMerchantPurchaseJson payedMerchantPurchaseJson = serviceUtil.fromJson(requestData, UnPayedMerchantPurchaseJson.class);
			MerchantEntity merchantEntity = validateToken(payedMerchantPurchaseJson.getAccessToken(), payedMerchantPurchaseJson.getServerToken());
			List<PurchaseEntity> purchaseEntities = purchaseDAO.getUnPayedPurchase(payedMerchantPurchaseJson, merchantEntity);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Internal Server Error.");
	}

}
