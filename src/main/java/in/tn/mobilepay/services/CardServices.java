package in.tn.mobilepay.services;

import in.tn.mobilepay.dao.CardDAO;
import in.tn.mobilepay.entity.BankDetailsEntity;
import in.tn.mobilepay.entity.CardDetailsEntity;
import in.tn.mobilepay.json.TermsConditions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class CardServices {

	@Autowired
	private ServiceUtil serviceUtil;
	
	@Autowired
	private CardDAO cardDao;
	
	@Autowired
	private Gson gson;
	
	public ResponseEntity<String> addCards(String requestData){
		try{
			String decryptedData = serviceUtil.netDecryption(requestData);
			TermsConditions termsConditions = gson.fromJson(decryptedData, TermsConditions.class);
			
			String bankGuid = termsConditions.getId();
			BankDetailsEntity bankDetailsEntity = cardDao.getBankDetail(bankGuid);
			if(bankDetailsEntity != null){
				boolean isCardAdded = cardDao.isCardPresent(null, bankDetailsEntity);
				if(isCardAdded){
					return serviceUtil.getErrorResponse(HttpStatus.NO_CONTENT, "Card is added");
				}
				CardDetailsEntity cardDetailsEntity = getCardDetails(termsConditions, decryptedData);
				cardDao.createCard(cardDetailsEntity);
				return serviceUtil.getSuccessResponse(HttpStatus.OK, "success");
			}
			
		}catch(Exception e){
			
		}
		return serviceUtil.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failure");
	}
	
	
	private CardDetailsEntity getCardDetails(TermsConditions termsConditions,String decryptedData) throws Exception{
		CardDetailsEntity cardDetailsEntity = new CardDetailsEntity();
		cardDetailsEntity.setCardData(serviceUtil.dbEncryption(decryptedData));
		cardDetailsEntity.setCardType(serviceUtil.dbEncryption(termsConditions.getOutlet()));
		cardDetailsEntity.setCreatedDateTime(serviceUtil.getCurrentGmtTime());
		//cardDetailsEntity.setBankDetailsEntity(bankDetailsEntity); -- TODO
		return cardDetailsEntity;
	}
	
	public ResponseEntity<String> removeCards(String requestData){
		return null;
	}
	
	
}
