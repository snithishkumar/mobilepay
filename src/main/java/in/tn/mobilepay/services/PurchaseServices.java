package in.tn.mobilepay.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.tn.mobilepay.dao.PurchaseDAO;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.request.model.PurchaseUpdateJson;
import in.tn.mobilepay.response.model.MerchantJson;
import in.tn.mobilepay.response.model.PurchaseJson;
import in.tn.mobilepay.response.model.UserJson;

@Service
public class PurchaseServices {

	@Autowired
	private PurchaseDAO purchaseDAO;
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
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
	}

}
