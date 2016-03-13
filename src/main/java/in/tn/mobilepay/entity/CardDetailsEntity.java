package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import in.tn.mobilepay.enumeration.CardType;
import in.tn.mobilepay.enumeration.PaymentType;

/**
 * For Security reason names are renamed.
 * @author Nithish
 *
 */
@Entity
@Table(name = "TermsConditions")
public class CardDetailsEntity {
	
	public static final String CARD_DETAILS_ID = "cardDetailsId";
	public static final String CARD_DATA = "cardData";
	public static final String BANK_ID = "bankDetailsEntity";
	public static final String PAYMENT_TYPE = "paymentType";
	public static final String USER_ID = "userEntity";
	public static final String IS_ACTIVE = "isActive";
	public static final String CARD_GUID = "cardGuid";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CardId")
	private int cardDetailsId;
	
	@Column(name="CardGuid")
	private String cardGuid;

	@Column(name = "CardData")
	private String cardData;

	
	@Column(name = "PaymentType")
	@Enumerated(EnumType.ORDINAL)
	private PaymentType paymentType;
	
	@ManyToOne
	@JoinColumn(name="UserId",referencedColumnName="UserId")
	private UserEntity userEntity;
	
	@Column(name="IsActive")
	private boolean isActive;
	
	@Column(name="CreatedDateTime")
	private long createdDateTime;
	
	
	public int getCardDetailsId() {
		return cardDetailsId;
	}

	public void setCardDetailsId(int cardDetailsId) {
		this.cardDetailsId = cardDetailsId;
	}

	public String getCardGuid() {
		return cardGuid;
	}

	public void setCardGuid(String cardGuid) {
		this.cardGuid = cardGuid;
	}

	public String getCardData() {
		return cardData;
	}

	public void setCardData(String cardData) {
		this.cardData = cardData;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public long getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	@Override
	public String toString() {
		return "CardDetailsEntity [cardDetailsId=" + cardDetailsId + ", cardGuid=" + cardGuid + ", cardData=" + cardData
				+ ", paymentType=" + paymentType + ", userEntity=" + userEntity + ", isActive=" + isActive
				+ ", createdDateTime=" + createdDateTime + "]";
	}



	

}
