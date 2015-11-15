package in.tn.mobilepay.entity;

import in.tn.mobilepay.enumeration.CardType;

public class CardDetailsEntity {

	private int cardDetailsId;
	private long cardNumber;
	private int cvv;
	private long expiryDate;
	private BankDetailsEntity bankDetailsEntity;
	private CardType cardType;

	public int getCardDetailsId() {
		return cardDetailsId;
	}

	public void setCardDetailsId(int cardDetailsId) {
		this.cardDetailsId = cardDetailsId;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public BankDetailsEntity getBankDetailsEntity() {
		return bankDetailsEntity;
	}

	public void setBankDetailsEntity(BankDetailsEntity bankDetailsEntity) {
		this.bankDetailsEntity = bankDetailsEntity;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	@Override
	public String toString() {
		return "CardDetailsEntity [cardDetailsId=" + cardDetailsId
				+ ", cardNumber=" + cardNumber + ", cvv=" + cvv
				+ ", expiryDate=" + expiryDate + ", bankDetailsEntity="
				+ bankDetailsEntity + ", cardType=" + cardType + "]";
	}

}
