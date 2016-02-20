package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import in.tn.mobilepay.enumeration.CardType;

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
	public static final String CARD_TYPE = "cardType";
	public static final String USER_ID = "userEntity";
	public static final String IS_ACTIVE = "isActive";
	public static final String CARD_GUID = "cardGuid";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Discount")
	private int cardDetailsId;
	
	@Column(name="CardGuid")
	private String cardGuid;

	@Column(name = "CardData")
	private String cardData;

	@ManyToOne
	@JoinColumn(name="BankDetailsId",referencedColumnName="BankDetailsId")
	private BankDetailsEntity bankDetailsEntity;
	
	@Column(name = "Outlet")
	private String cardType;
	
	@ManyToOne
	@JoinColumn(name="UserId",referencedColumnName="UserId")
	private UserEntity userEntity;
	
	@Column(name="IsActive")
	private boolean isActive;
	
	@Column(name="CreatedDateTime")
	private long createdDateTime;
	
	@Column(name="ModifiedDateTime")
	private long modifiedDateTime;

	public int getCardDetailsId() {
		return cardDetailsId;
	}

	public void setCardDetailsId(int cardDetailsId) {
		this.cardDetailsId = cardDetailsId;
	}

	public BankDetailsEntity getBankDetailsEntity() {
		return bankDetailsEntity;
	}

	public void setBankDetailsEntity(BankDetailsEntity bankDetailsEntity) {
		this.bankDetailsEntity = bankDetailsEntity;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardData() {
		return cardData;
	}

	public void setCardData(String cardData) {
		this.cardData = cardData;
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

	public long getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(long modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	@Override
	public String toString() {
		return "CardDetailsEntity [cardDetailsId=" + cardDetailsId
				+ ", cardData=" + cardData + ", bankDetailsEntity="
				+ bankDetailsEntity + ", cardType=" + cardType
				+ ", userEntity=" + userEntity + ", isActive=" + isActive + "]";
	}

}
