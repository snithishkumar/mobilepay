package in.tn.mobilepay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import in.tn.mobilepay.dao.MerchantDAO;
import in.tn.mobilepay.entity.MerchantEntity;
import in.tn.mobilepay.request.model.MerchantLoginJson;

@Service
public class MerchantServices {
	@Autowired
	private Gson gson;
	@Autowired
	private MerchantDAO merchantDAO;
	@Autowired
	private ServiceUtil serviceUtil;
	
	@Transactional(readOnly = false,propagation= Propagation.REQUIRED)
	public ResponseEntity<String> merchantRegister(String requestData){
		try{
			MerchantEntity merchantEntity =	gson.fromJson(requestData, MerchantEntity.class);
			MerchantEntity dbMerchantEntity =	merchantDAO.getMerchant(merchantEntity.getMobileNumber());
			if(dbMerchantEntity == null){
				merchantEntity.setMerchantGuid(serviceUtil.uuid());
				merchantEntity.setMerchantToken(serviceUtil.getToken());
				merchantEntity.setServerToken(serviceUtil.getToken());
				merchantEntity.setCreatedTime(serviceUtil.getCurrentGmtTime());
				merchantEntity.setUpdatedTime(merchantEntity.getCreatedTime());
				merchantDAO.createMerchant(merchantEntity);
				JsonObject result = new JsonObject();
				result.addProperty("merchantToken", merchantEntity.getMerchantToken());
				result.addProperty("serverToken", merchantEntity.getServerToken());
				return serviceUtil.getResponse(200, result);
			}
			return serviceUtil.getResponse(200, "UserName is already present.");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(500, "Internal Server Error.");
	}
	
	@Transactional(readOnly = false,propagation= Propagation.REQUIRED)
	public ResponseEntity<String> merchantLogin(String requestData){
		try{
			MerchantLoginJson merchantLogin  =	gson.fromJson(requestData, MerchantLoginJson.class);
			MerchantEntity dbMerchantEntity = merchantDAO.getMerchant(merchantLogin.getMobileNumber());
			if(dbMerchantEntity == null){
				return serviceUtil.getResponse(500, "Invalid Login");
			}
			if(dbMerchantEntity.getPassword().equals(merchantLogin.getPassword())){
				dbMerchantEntity.setMerchantToken(serviceUtil.getToken());
				dbMerchantEntity.setServerToken(serviceUtil.getToken());
				dbMerchantEntity.setUpdatedTime(dbMerchantEntity.getCreatedTime());
				merchantDAO.updateMerchant(dbMerchantEntity);
				JsonObject result = new JsonObject();
				result.addProperty("merchantToken", dbMerchantEntity.getMerchantToken());
				result.addProperty("serverToken", dbMerchantEntity.getServerToken());
				return serviceUtil.getResponse(200, result);
			}
			return serviceUtil.getResponse(500, "Invalid Login");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(500, "Internal Server Error.");
	}

}
