package in.tn.mobilepay.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import in.tn.mobilepay.dao.impl.MerchantDAOImpl;
import in.tn.mobilepay.dao.impl.PurchaseDAOImpl;
import in.tn.mobilepay.entity.AddressEntity;
import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.MerchantEntity;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.request.model.MerchantOrderStatusJson;
import in.tn.mobilepay.request.model.UnPayedMerchantPurchaseJson;
import in.tn.mobilepay.response.model.AddressJson;
import in.tn.mobilepay.response.model.PurchaseJson;
import in.tn.mobilepay.response.model.PurchaseMerchantJson;
import in.tn.mobilepay.response.model.UserJson;
import in.tn.mobilepay.util.StatusCode;

@Service
public class MerchantPurchaseService {
	
	private static final Logger logger = Logger.getLogger(MerchantPurchaseService.class);
	
	private static final String TOTAL_COUNT = "X-Total-Count";
	private static final String COUNT = "X-Count";
	
	@Autowired
	private PurchaseDAOImpl purchaseDAO;
	@Autowired
	private ServiceUtil serviceUtil;
	@Autowired
	private MerchantDAOImpl merchantDAO;
	@Autowired
	private Gson gson;
	
	
	private MerchantEntity validateToken(String merchantToken,String serverToken) throws ValidationException{
		MerchantEntity merchantEntity = merchantDAO.getMerchant(merchantToken, serverToken);
		if(merchantEntity == null){
			throw new ValidationException(StatusCode.MER_UNAUTHORIZE, "Invalid User", null);
		}
		return merchantEntity;
		
	}
	
	/**
	 * Gets unPayed Data.
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = true)
	public ResponseEntity<String> getUnPayedPurchaseList(String requestData){
		try{
			//Json to object
			UnPayedMerchantPurchaseJson unPayedMerchantPurchaseJson = 	serviceUtil.fromJson(requestData, UnPayedMerchantPurchaseJson.class);
			// Validate Merchant Token
			MerchantEntity merchantEntity = validateToken(unPayedMerchantPurchaseJson.getAccessToken(), unPayedMerchantPurchaseJson.getServerToken());
			// Get UnPayed Data 
			List<PurchaseEntity>  purchaseEntities = purchaseDAO.getUnPayedPurchase(unPayedMerchantPurchaseJson, merchantEntity);
			List<PurchaseMerchantJson> purchaseJsons = new ArrayList<>();
			// Entity to Json
			for(PurchaseEntity purchaseEntity : purchaseEntities){
				PurchaseMerchantJson purchaseJson = new PurchaseMerchantJson(purchaseEntity,gson);
				// Add User Details
				UserJson userJson = new UserJson(purchaseEntity.getUserEntity());
				purchaseJson.setUsers(userJson);
				purchaseJsons.add(purchaseJson);
			}
			//String response = serviceUtil.toJson(purchaseJsons);
			// Get Total Count
			//long totalCount = purchaseDAO.getUnPayedPurchaseCount(merchantEntity);
			// Get Count (After ServerSyncTime)
			//long count = purchaseDAO.getUnPayedPurchaseCount(merchantEntity,unPayedMerchantPurchaseJson.getServerSyncTime());
			ResponseEntity<String>  responseEntity = serviceUtil.getResponse(200,purchaseJsons);
			//responseEntity.getHeaders().add(TOTAL_COUNT, String.valueOf(totalCount));
			//responseEntity.getHeaders().add(COUNT, String.valueOf(count));
			return responseEntity;
		}catch(ValidationException e1){
			logger.error("Error in getUnPayedPurchaseList, Raw Data["+requestData+"]", e1);
			return serviceUtil.getResponse(e1.getCode(), e1.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Internal Error.");
	}
	
	
	/**
	 * Gets Payed Data.
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = true)
	public ResponseEntity<String> getPayedPurchaseList(String requestData){
		try{
			//Json to object
			MerchantOrderStatusJson payedMerchantPurchaseJson = 	serviceUtil.fromJson(requestData, MerchantOrderStatusJson.class);
			// Validate Merchant Token
			MerchantEntity merchantEntity = validateToken(payedMerchantPurchaseJson.getAccessToken(), payedMerchantPurchaseJson.getServerToken());
			// Get UnPayed Data 
			List<PurchaseEntity>  purchaseEntities = purchaseDAO.getPurchaseOrderStatusList(payedMerchantPurchaseJson, merchantEntity);
			List<PurchaseMerchantJson> purchaseJsons = new ArrayList<>();
			// Entity to Json
			for(PurchaseEntity purchaseEntity : purchaseEntities){
				PurchaseMerchantJson purchaseJson = new PurchaseMerchantJson(purchaseEntity,gson);
				//Add User Details
				UserJson userJson = new UserJson(purchaseEntity.getUserEntity());
				purchaseJson.setUsers(userJson);
				// If its home delivery, then we need to send Home delivery address
				if(purchaseEntity.getDeliveryOptions().toString().equals(DeliveryOptions.HOME.toString())){
					Collection<AddressEntity> collections = purchaseEntity.getAddressEntities();
					for(AddressEntity addressEntity : collections){
						AddressJson addressJson = new AddressJson(addressEntity);
						purchaseJson.setAddressDetails(addressJson);
						break;
					}
				}
				
				purchaseJsons.add(purchaseJson);
			}
			//String response = serviceUtil.toJson(purchaseJsons);
			// Get Total Count
			//long totalCount = purchaseDAO.getPurchaseOrderStatusListCount(merchantEntity);
			// Get Count (After PurchaseDateTime)
			//long count = purchaseDAO.getPurchaseOrderStatusListCount(merchantEntity,payedMerchantPurchaseJson.getPurchaseDateTime());
			ResponseEntity<String>  responseEntity = serviceUtil.getResponse(200, purchaseJsons);
			//responseEntity.getHeaders().add(TOTAL_COUNT, String.valueOf(totalCount));
			//responseEntity.getHeaders().add(COUNT, String.valueOf(count));
			return responseEntity;
		}catch(ValidationException e1){
			logger.error("Error in getPayedPurchaseList, Raw Data["+requestData+"]", e1);
			return serviceUtil.getResponse(e1.getCode(), e1.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Internal Error.");
	}
	
	
	
	/**
	 * Gets Payed Data.
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = true)
	public ResponseEntity<String> getPurchaseHistoryList(String requestData){
		try{
			//Json to object
			UnPayedMerchantPurchaseJson unPayedMerchantPurchaseJson = 	serviceUtil.fromJson(requestData, UnPayedMerchantPurchaseJson.class);
			// Validate Merchant Token
			MerchantEntity merchantEntity = validateToken(unPayedMerchantPurchaseJson.getAccessToken(), unPayedMerchantPurchaseJson.getServerToken());
			// Get UnPayed Data 
			List<PurchaseEntity>  purchaseEntities = purchaseDAO.getPurchaseHistoryList(unPayedMerchantPurchaseJson, merchantEntity);
			List<PurchaseMerchantJson> purchaseJsons = new ArrayList<>();
			// Entity to Json
			for(PurchaseEntity purchaseEntity : purchaseEntities){
				PurchaseMerchantJson purchaseJson = new PurchaseMerchantJson(purchaseEntity,gson);
				//Add User Details
				UserJson userJson = new UserJson(purchaseEntity.getUserEntity());
				purchaseJson.setUsers(userJson);
				// If its discard, then we need to add Discard Details.
				if(purchaseEntity.getOrderStatus().ordinal() == OrderStatus.CANCELLED.ordinal()){
					DiscardEntity discardEntity = purchaseDAO.getDiscardEntity(purchaseEntity);
					DiscardJson discardJson = new DiscardJson(discardEntity);
					discardJson.setCreatedDateTime(discardEntity.getCreatedDateTime());
					purchaseJson.setDiscardJson(discardJson);
				}
				// If its home delivery, then we need to send Home delivery address
				if(purchaseEntity.getDeliveryOptions() != null && purchaseEntity.getDeliveryOptions().toString().equals(DeliveryOptions.HOME.toString())){
					Collection<AddressEntity> collections = purchaseEntity.getAddressEntities();
					for(AddressEntity addressEntity : collections){
						AddressJson addressJson = new AddressJson(addressEntity);
						purchaseJson.setAddressDetails(addressJson);
						break;
					}
				}
				
				purchaseJsons.add(purchaseJson);
			}
			//String response = serviceUtil.toJson(purchaseJsons);
			// Get Total Count
			//long totalCount = purchaseDAO.getPurchaseHistoryListCount(merchantEntity);
			// Get Count (After ServerSyncTime)
			//long count = purchaseDAO.getPurchaseHistoryListCount(merchantEntity,unPayedMerchantPurchaseJson.getServerSyncTime());
			ResponseEntity<String>  responseEntity = serviceUtil.getResponse(200, purchaseJsons);
		//	responseEntity.getHeaders().add(TOTAL_COUNT, String.valueOf(totalCount));
		//	responseEntity.getHeaders().add(COUNT, String.valueOf(count));
			return responseEntity;
		}catch(ValidationException e1){
			logger.error("Error in getPayedPurchaseList, Raw Data["+requestData+"]", e1);
			return serviceUtil.getResponse(e1.getCode(), e1.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Internal Error.");
	}
	

}
