package in.tn.mobilepay.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.tn.mobilepay.dao.CardDAO;
import in.tn.mobilepay.dao.UserDAO;
import in.tn.mobilepay.entity.CardDetailsEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.exception.ValidationException;
import in.tn.mobilepay.request.model.CardDetailJson;
import in.tn.mobilepay.request.model.CardJson;
import in.tn.mobilepay.util.StatusCode;

@Service
public class CardServices {

	@Autowired
	private ServiceUtil serviceUtil;
	
	@Autowired
	private CardDAO cardDao;
	
	@Autowired
	private UserDAO userDAO;
	
	
	
	@Transactional(readOnly = false,propagation= Propagation.REQUIRED)
	public ResponseEntity<String> addCards(String requestData){
		try{
			//String decryptedData = serviceUtil.netDecryption(requestData);
			CardJson cardJson = serviceUtil.fromJson(requestData, CardJson.class);
			UserEntity userEntity = validateUserToken(cardJson.getAccessToken(), cardJson.getServerToken());
			CardDetailsEntity cardDetailsEntity = new CardDetailsEntity();
			cardDetailsEntity.setCardGuid(serviceUtil.uuid());
			cardDetailsEntity.setCreatedDateTime(ServiceUtil.getCurrentGmtTime());
			cardDetailsEntity.setActive(true);
			cardDetailsEntity.setPaymentType(cardJson.getPaymentType());
			cardDetailsEntity.setUserEntity(userEntity);
			CardDetailJson cardDetailJson = cardJson.getCardDetails();
			String cardData = serviceUtil.toJson(cardDetailJson);
			//cardData = serviceUtil.dbEncryption(cardData);
			cardDetailsEntity.setCardData(cardData);
			cardDao.createCard(cardDetailsEntity);
			return serviceUtil.getResponse(StatusCode.CARD_LIST_SUCCESS, "success");
		}catch(ValidationException e){
			return serviceUtil.getResponse(e.getCode(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.CARD_LIST_FAILURE, "Failure");
	}
	
	
	
	@Transactional(readOnly = false,propagation= Propagation.REQUIRED)
	public ResponseEntity<String> removeCards(String requestData){
		try{
			//String decryptedData = serviceUtil.netDecryption(requestData);
			CardJson cardJson = serviceUtil.fromJson(requestData, CardJson.class);
			 validateUserToken(cardJson.getAccessToken(), cardJson.getServerToken());
			 CardDetailsEntity cardDetailsEntity =  cardDao.getCardDetailsEntity(cardJson.getCardGuid());
			if(cardDetailsEntity != null){
				cardDao.removeCard(cardDetailsEntity);
			}
			 
			return serviceUtil.getResponse(StatusCode.CARD_LIST_SUCCESS, "success");
		}catch(ValidationException e){
			return serviceUtil.getResponse(e.getCode(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.CARD_LIST_FAILURE, "Failure");
	}
	
	@Transactional(readOnly = true,propagation= Propagation.REQUIRED)
	public ResponseEntity<String> getCardList(String requestData){
		try{
			CardJson cardJson = serviceUtil.fromJson(requestData, CardJson.class);
			UserEntity userEntity =  validateUserToken(cardJson.getAccessToken(), cardJson.getServerToken());
			List<CardDetailsEntity> cardDetails = cardDao.getCardList(userEntity);
			List<CardJson> cardList = new ArrayList<CardJson>();
			for(CardDetailsEntity cardDetailsEntity : cardDetails){
				CardJson tempCardJson = new CardJson();
				tempCardJson.setCardGuid(cardDetailsEntity.getCardGuid());
				tempCardJson.setCreatedDateTime(String.valueOf(cardDetailsEntity.getCreatedDateTime()));
				tempCardJson.setPaymentType(cardDetailsEntity.getPaymentType());
				String cardEncrypt = cardDetailsEntity.getCardData();
				//String cardData = serviceUtil.dbDecryption(cardEncrypt);
				CardDetailJson cardDetailJson = serviceUtil.fromJson(cardEncrypt, CardDetailJson.class);
				tempCardJson.setCardDetails(cardDetailJson);
				cardList.add(tempCardJson);
			}
			String cardValues = serviceUtil.toJson(cardList);
			return serviceUtil.getResponse(StatusCode.CARD_LIST_SUCCESS, cardValues);
		}catch(ValidationException e){
			return serviceUtil.getResponse(e.getCode(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
		return serviceUtil.getResponse(StatusCode.CARD_LIST_FAILURE, "Failure");
		
	}
	
	private UserEntity validateUserToken(String client,String serverToken) throws ValidationException{
		UserEntity userEntity = userDAO.getUserEnityByToken(client, serverToken);
		if(userEntity == null){
			throw new ValidationException(StatusCode.LOGIN_INVALID_PIN, "Invalid User", null);
		}
		return userEntity;
		
	}
	
}
