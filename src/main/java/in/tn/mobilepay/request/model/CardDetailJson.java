package in.tn.mobilepay.request.model;

import in.tn.mobilepay.enumeration.CardType;

public class CardDetailJson {

	private String number;
	private String name;
	private String expiryDate;
	private CardType cardType;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	@Override
	public String toString() {
		return "CardDetailJson [number=" + number + ", name=" + name + ", expiryDate=" + expiryDate + ", cardType="
				+ cardType + "]";
	}

}
