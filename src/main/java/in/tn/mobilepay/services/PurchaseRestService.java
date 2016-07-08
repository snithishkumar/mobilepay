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
import in.tn.mobilepay.dao.impl.PurchaseDAOImpl;
import in.tn.mobilepay.dao.impl.UserDAOImpl;
import in.tn.mobilepay.entity.AddressEntity;
import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.MerchantEntity;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.response.model.AddressJson;
import in.tn.mobilepay.rest.json.AmountDetails;
import in.tn.mobilepay.rest.json.MerchantPurchaseData;
import in.tn.mobilepay.rest.json.MerchantPurchaseDatas;
import in.tn.mobilepay.rest.json.PurchaseDetails;
import in.tn.mobilepay.rest.json.PurchaseItem;
import in.tn.mobilepay.rest.json.PurchaseItems;

@Service
public class PurchaseRestService {
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private UserDAOImpl userDAOImpl;
	
	@Autowired
	private PurchaseDAOImpl purchaseDAOImpl;
	
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
			return serviceUtil.getRestResponse(true, responseData);
		}catch(Exception e){
			logger.error("Error in PurchaseRestService", e);
		}
		return serviceUtil.getRestResponse(false, "OOPS.");
	}
	
	
	private void copyMerchantPurchaseData(MerchantPurchaseData merchantPurchaseData,PurchaseEntity purchaseEntity){
		AmountDetails amountDetails = merchantPurchaseData.getAmountDetails();
		if(amountDetails != null){
			purchaseEntity.setAmountDetails(gson.toJson(amountDetails));
		}
		purchaseEntity.setBillNumber(merchantPurchaseData.getBillNumber());
		purchaseEntity.setDeliverable(merchantPurchaseData.getIsHomeDelivery());
		purchaseEntity.setEditable(merchantPurchaseData.getIsRemovable());
		List<PurchaseItem> purchaseItems = merchantPurchaseData.getPurchaseItems();
		if(purchaseItems != null && purchaseItems.size() > 0){
			purchaseEntity.setPurchaseData(gson.toJson(purchaseItems));
		}
		purchaseEntity.setPurchaseDateTime(merchantPurchaseData.getPurchaseDate());
		purchaseEntity.setPurchaseGuid(serviceUtil.uuid());
		purchaseEntity.setServerDateTime(ServiceUtil.getCurrentGmtTime());
		purchaseEntity.setTotalAmount(merchantPurchaseData.getTotalAmount());
		
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
	
	
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public void getPurchaseList(Integer index,Integer limit,String status,Long fromDate,Long toDate,Principal principal){
		try{
			// Get Merchant Entity
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			List<PurchaseEntity> purchaseEntities = purchaseDAOImpl.getPurchaseEntityList(merchantEntity, index, limit, status, fromDate, toDate);
			List<PurchaseDetails> purchaseDetailsList = new ArrayList<>();
			for(PurchaseEntity purchaseEntity : purchaseEntities){
				PurchaseDetails purchaseDetails = getPurchaseData(purchaseEntity);
				purchaseDetailsList.add(purchaseDetails);
			}
			
		}catch(Exception e){
			
		}
	}
	
	
	private PurchaseDetails getPurchaseData(PurchaseEntity purchaseEntity){
		PurchaseDetails purchaseDetails = new PurchaseDetails(purchaseEntity);
		String purchaseData = purchaseEntity.getPurchaseData();
		PurchaseItems purchaseItems = gson.fromJson(purchaseData,PurchaseItems.class);
		purchaseDetails.setPurchaseItem(purchaseItems.getPurchaseItems());
		if(purchaseEntity.isDiscard()){
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
	public void getPurchaseData(String purchaseUUID,Principal principal){
		try{
			MerchantEntity merchantEntity = serviceUtil.getMerchantEntity(principal);
			PurchaseEntity purchaseEntity = purchaseDAOImpl.getPurchaseEntity(purchaseUUID,merchantEntity);
			if(purchaseEntity == null){
				// return
			}
			PurchaseDetails purchaseDetails = getPurchaseData(purchaseEntity);
		}catch(Exception e){
			
		}
	}

}