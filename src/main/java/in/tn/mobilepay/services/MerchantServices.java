package in.tn.mobilepay.services;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import in.tn.mobilepay.dao.MerchantDAO;
import in.tn.mobilepay.entity.MerchantEntity;
import in.tn.mobilepay.entity.MerchantProfile;
import in.tn.mobilepay.request.model.MerchantLoginJson;
import in.tn.mobilepay.util.StatusCode;

@Service
public class MerchantServices {
	@Autowired
	private Gson gson;
	@Autowired
	private MerchantDAO merchantDAO;
	@Autowired
	private ServiceUtil serviceUtil;
	
	private static final Logger logger = Logger.getLogger(MerchantServices.class);
	
	/**
	 * Create New Merchant Account
	 * @param multipartFile
	 * @param merchantName
	 * @param merchantAddress
	 * @param area
	 * @param pinCode
	 * @param mobileNumber
	 * @param landLineNumber
	 * @param category
	 * @param password
	 * @return
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public ResponseEntity<String> merchantRegsiteration(MultipartFile multipartFile, String merchantName,
			String merchantAddress, String area, String pinCode, String mobileNumber, String landLineNumber,
			String category, String password) {
		try {
			// Check whether mobile number is already present or not
			MerchantEntity dbMerchantEntity = merchantDAO.getMerchant(Long.valueOf(mobileNumber));
			// Populate all values
			if (dbMerchantEntity == null) {
				dbMerchantEntity = new MerchantEntity();
				dbMerchantEntity.setArea(area);
				dbMerchantEntity.setCategory(category);
				dbMerchantEntity.setCreatedTime(ServiceUtil.getCurrentGmtTime());
				dbMerchantEntity.setLandLineNumber(Long.valueOf(landLineNumber));
				dbMerchantEntity.setMerchantAddress(merchantAddress);
				dbMerchantEntity.setPassword(password);
				dbMerchantEntity.setPinCode(pinCode);
				dbMerchantEntity.setMerchantName(merchantName);
				dbMerchantEntity.setMobileNumber(Long.valueOf(mobileNumber));
				dbMerchantEntity.setMerchantGuid(serviceUtil.uuid());
				dbMerchantEntity.setMerchantToken(serviceUtil.getToken());
				dbMerchantEntity.setServerToken(serviceUtil.getToken());
				dbMerchantEntity.setUpdatedTime(dbMerchantEntity.getCreatedTime());
				// Create Merchant
				merchantDAO.createMerchant(dbMerchantEntity);
				//Create Merchant Profile
				if(multipartFile != null){
					MerchantProfile merchantProfile = new MerchantProfile();
					merchantProfile.setMerchantEntity(dbMerchantEntity);
					merchantDAO.createMerchantProfile(merchantProfile,multipartFile.getBytes());
				}
				
				
				// Merchant and Sever Token as response
				JsonObject result = new JsonObject();
				result.addProperty("merchantToken", dbMerchantEntity.getMerchantToken());
				result.addProperty("serverToken", dbMerchantEntity.getServerToken());
				return serviceUtil.getResponse(StatusCode.MER_OK, result);
			}
			return serviceUtil.getResponse(StatusCode.MER_MOBILE_ALREADY_PRESENT, "UserName is already present.");
		} catch (Exception e) {
			logger.error("Error in merchantRegsiteration", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Internal Server Error.");
	}

	@Deprecated
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
	
	/**
	 * Merchant Authentication
	 * @param requestData
	 * @return
	 */
	@Transactional(readOnly = false,propagation= Propagation.REQUIRED)
	public ResponseEntity<String> merchantLogin(String requestData){
		try{
			// Json to Obj
			MerchantLoginJson merchantLogin  =	gson.fromJson(requestData, MerchantLoginJson.class);
			// Check whether Mobile Number is Present or not
			MerchantEntity dbMerchantEntity = merchantDAO.getMerchant(merchantLogin.getMobileNumber());
			// If Not Present, then invalid mobile
			if(dbMerchantEntity == null){
				return serviceUtil.getResponse(StatusCode.MER_INVALID_LOGIN, "Invalid Login");
			}
			// Validate User Password 
			if(dbMerchantEntity.getPassword().equals(merchantLogin.getPassword())){
				// Generate New Token and Send back to Merchant
				dbMerchantEntity.setMerchantToken(serviceUtil.getToken());
				dbMerchantEntity.setServerToken(serviceUtil.getToken());
				dbMerchantEntity.setUpdatedTime(dbMerchantEntity.getCreatedTime());
				merchantDAO.updateMerchant(dbMerchantEntity);
				JsonObject result = new JsonObject();
				result.addProperty("merchantToken", dbMerchantEntity.getMerchantToken());
				result.addProperty("serverToken", dbMerchantEntity.getServerToken());
				return serviceUtil.getResponse(StatusCode.MER_OK, result);
			}
			return serviceUtil.getResponse(StatusCode.MER_INVALID_LOGIN, "Invalid Login");
		}catch(Exception e){
			logger.error("Error in merchantLogin", e);
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Internal Server Error.");
	}
	
	@Transactional(readOnly = true,propagation= Propagation.REQUIRED)
	public ResponseEntity getShopLogo(String merchantGuid,String merchantId){
		try{
			MerchantProfile merchantProfile  = merchantDAO.getMerchantProfile(merchantGuid, Integer.valueOf(merchantId));
			if(merchantProfile != null){
				InputStream inputStream = merchantProfile.getMerchantProfile().getBinaryStream();
				byte[] data = IOUtils.toByteArray(inputStream);
				ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(data, HttpStatus.OK);
				return responseEntity;
				
			}
			return serviceUtil.getResponse(StatusCode.MER_INVALID_PROFILE, "File");
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.MER_ERROR, "Internal Server Error.");
	}

}
