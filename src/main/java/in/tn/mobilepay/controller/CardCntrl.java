package in.tn.mobilepay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.tn.mobilepay.services.CardServices;

@RestController
public class CardCntrl {
	
	@Autowired
	private CardServices cardServices;
	
	@RequestMapping(value="/mobile/addCards")
	public ResponseEntity<String> addCards(@RequestBody String cardData){
		return cardServices.addCards(cardData);
	}
		
	@RequestMapping(value="/mobile/getCardList")
	public ResponseEntity<String> getCardList(@RequestBody String cardData){
		return cardServices.getCardList(cardData);
	}
	
	
	@RequestMapping(value="/mobile/removeCards")
	public ResponseEntity<String> removeCards(@RequestBody String cardData){
		return cardServices.removeCards(cardData);
	}
	

}
