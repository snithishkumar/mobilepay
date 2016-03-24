package in.tn.mobilepay.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.tn.mobilepay.dao.MerchantDAO;
import in.tn.mobilepay.dao.PurchaseDAO;
import in.tn.mobilepay.dao.UserDAO;
import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.MerchantEntity;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.enumeration.DiscardBy;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.DiscardJson;
import in.tn.mobilepay.request.model.GetPurchaseList;
import in.tn.mobilepay.request.model.PurchaseUpdateJson;
import in.tn.mobilepay.response.model.MerchantJson;
import in.tn.mobilepay.response.model.PurchaseJson;
import in.tn.mobilepay.response.model.UserJson;

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

	/*@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> updatePurchaseDetails(String requestData) {
		try {
			String purchaseJson = serviceUtil.netDecryption(requestData);
			PurchaseUpdateJson purchaseUpdateJson = serviceUtil.fromJson(purchaseJson, PurchaseUpdateJson.class);
			PurchaseEntity dbPurchaseEntity = purchaseDAO.getPurchaseById(purchaseUpdateJson.getPurchaseId());
			if (dbPurchaseEntity != null) {
				if (dbPurchaseEntity.getUnModifiedPurchaseData() != null) {
					dbPurchaseEntity.setUnModifiedPurchaseData(dbPurchaseEntity.getPurchaseData());
				}
				if (dbPurchaseEntity.getUnModifiedAmountDetails() != null) {
					dbPurchaseEntity.setUnModifiedAmountDetails(dbPurchaseEntity.getAmountDetails());
				}
				dbPurchaseEntity.setUpdatedDateTime(purchaseUpdateJson.getUpdatedDateTime());
				dbPurchaseEntity.setAmountDetails(purchaseUpdateJson.getAmountDetails());
				dbPurchaseEntity.setPurchaseData(purchaseUpdateJson.getProductDetails());
				purchaseDAO.updatePurchaseObject(dbPurchaseEntity);
				return serviceUtil.getSuccessResponse(HttpStatus.OK, "Success");
			}
			return serviceUtil.getErrorResponse(HttpStatus.EXPECTATION_FAILED, "Invalid Details");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}*/
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> discardPurchase(String requestData){
		try{
			DiscardJson discardJson = serviceUtil.fromJson(requestData, DiscardJson.class);
			MerchantEntity merchantEntity = validateToken(discardJson.getAccessToken(), discardJson.getServerToken());
			UserEntity userEntity = validateMobile(discardJson.getUserMobile());
			PurchaseEntity purchaseEntity  = purchaseDAO.getPurchaseEntity(discardJson.getPurchaseGuid());
			DiscardEntity discardEntity = new DiscardEntity();
			discardEntity.setDiscardGuid(serviceUtil.uuid());
			discardEntity.setMerchantEntity(merchantEntity);
			discardEntity.setUserEntity(userEntity);
			discardEntity.setReason(discardJson.getReason());
			discardEntity.setPurchaseEntity(purchaseEntity);
			purchaseEntity.setDiscard(true);
			purchaseDAO.updatePurchaseObject(purchaseEntity);
			purchaseDAO.createDiscard(discardEntity);
			return serviceUtil.getResponse(200, "success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(200, "success");
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> discardPurchaseByUser(String requestData){
		try{
			DiscardJson discardJson = serviceUtil.fromJson(requestData, DiscardJson.class);
			UserEntity userEntity = validateUserToken(discardJson.getAccessToken(), discardJson.getServerToken());
			PurchaseEntity purchaseEntity  = purchaseDAO.getPurchaseEntity(discardJson.getPurchaseGuid());
			DiscardEntity discardEntity = new DiscardEntity();
			discardEntity.setDiscardGuid(serviceUtil.uuid());
			discardEntity.setUserEntity(userEntity);
			discardEntity.setReason(discardJson.getReason());
			discardEntity.setPurchaseEntity(purchaseEntity);
			discardEntity.setDiscardBy(DiscardBy.USER);
			purchaseEntity.setDiscard(true);
			purchaseDAO.updatePurchaseObject(purchaseEntity);
			purchaseDAO.createDiscard(discardEntity);
			return serviceUtil.getResponse(200, "success");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(200, "success");
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> createPurchase(String requestData){
		try{
			in.tn.mobilepay.request.model.PurchaseJson purchaseJson =	serviceUtil.fromJson(requestData, in.tn.mobilepay.request.model.PurchaseJson.class);
			MerchantEntity merchantEntity = validateToken(purchaseJson.getAccessToken(), purchaseJson.getServerToken());
			UserEntity userEntity = validateMobile(purchaseJson.getUserMobile());
			PurchaseEntity dbPurchaseEntity = purchaseDAO.getPurchaseEntity(purchaseJson.getPurchaseUuid());
			if(dbPurchaseEntity == null){
				dbPurchaseEntity = new PurchaseEntity();
				dbPurchaseEntity.setPurchaseDateTime(Long.valueOf(purchaseJson.getDateTime()));
				dbPurchaseEntity.setMerchantEntity(merchantEntity);
				dbPurchaseEntity.setUserEntity(userEntity);
				populatePurchaseData(dbPurchaseEntity, purchaseJson);
				purchaseDAO.createPurchaseObject(dbPurchaseEntity);
				return serviceUtil.getResponse(200, "success");
			}else{
				populatePurchaseData(dbPurchaseEntity, purchaseJson);
				dbPurchaseEntity.setMerchantEntity(merchantEntity);
				dbPurchaseEntity.setUserEntity(userEntity);
				purchaseDAO.updatePurchaseObject(dbPurchaseEntity);
				return serviceUtil.getResponse(200, "success");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(200, "failure");
	}
	
	private MerchantEntity validateToken(String merchantToken,String serverToken) throws ValidationException{
		MerchantEntity merchantEntity = merchantDAO.getMerchant(merchantToken, serverToken);
		if(merchantEntity == null){
			throw new ValidationException(10, "Invalid User", null);
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
	
	
	private UserEntity validateMobile(String mobileNumber) throws ValidationException{
		UserEntity userEntity = userDAO.getUserEntity(mobileNumber);
		if(userEntity == null){
			throw new ValidationException(10, "Invalid Mobile", null);
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
	
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResponseEntity<String> getPurchaseList(String requestData) {
		try {
			GetPurchaseList getPurchaseList =	serviceUtil.fromJson(requestData, GetPurchaseList.class);
			UserEntity userEntity = validateUserToken(getPurchaseList.getAccessToken(), getPurchaseList.getServerToken());
			
			List<PurchaseEntity> purchaseList = purchaseDAO.gePurchaseList(getPurchaseList.getServerTime(),userEntity);
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
			e.printStackTrace();
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}

}
