package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Purchase")
public class PurchaseEntity {

	public static final String PURCHASE_ID = "purchaseId";
	public static final String PURCHASE_GUID = "purchaseGuid";
	public static final String PURCHASE_DATE_TIME = "purchaseDateTime";
	public static final String USER_ID = "userEntity";
	public static final String MERCHANT_ID = "merchantEntity";
	public static final String IS_PAYED = "isPayed";
	public static final String BILL_NUMBER = "billNumber";
	public static final String IS_EDITABLE = "isEditable";
	public static final String UPDATED_DATE_TIME = "updatedDateTime";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PurchaseId")
	private int purchaseId;
	@Column(name="PurchaseGuid")
	private String purchaseGuid;
	@Column(name = "PurchaseDateTime")
	private long purchaseDateTime;
	@ManyToOne
	@Column(name = "UserId")
	private UserEntity userEntity;
	@ManyToOne
	@Column(name = "MerchantId")
	private MerchantEntity merchantEntity;
	@Column(name = "IsPayed")
	private boolean isPayed;
	@Column(name = "BillNumber")
	private int billNumber;
	// Product Number,Product Name,Quantity and Amount as a json array
	@Column(name = "PurchaseData")
	private String purchaseData;
	@Column(name = "UnModifiedPurchaseData")
	private String unModifiedPurchaseData;
	@Column(name = "IsEditable")
	private boolean isEditable;
	@Column(name = "UpdatedDateTime")
	private long updatedDateTime;
	@Column(name = "IsDeliverable")
	private boolean isDeliverable;
	// Amount, Tax, Total amount
	@Column(name = "AmountDetails")
	private String amountDetails;
	@Column(name = "UnModifiedAmountDetails")
	private String unModifiedAmountDetails;

	public int getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}

	public String getPurchaseData() {
		return purchaseData;
	}

	public void setPurchaseData(String purchaseData) {
		this.purchaseData = purchaseData;
	}

	public String getUnModifiedPurchaseData() {
		return unModifiedPurchaseData;
	}

	public void setUnModifiedPurchaseData(String unModifiedPurchaseData) {
		this.unModifiedPurchaseData = unModifiedPurchaseData;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public long getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(long updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public boolean isDeliverable() {
		return isDeliverable;
	}

	public void setDeliverable(boolean isDeliverable) {
		this.isDeliverable = isDeliverable;
	}

	public String getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(String amountDetails) {
		this.amountDetails = amountDetails;
	}

	public String getUnModifiedAmountDetails() {
		return unModifiedAmountDetails;
	}

	public void setUnModifiedAmountDetails(String unModifiedAmountDetails) {
		this.unModifiedAmountDetails = unModifiedAmountDetails;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public long getPurchaseDateTime() {
		return purchaseDateTime;
	}

	public void setPurchaseDateTime(long purchaseDateTime) {
		this.purchaseDateTime = purchaseDateTime;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	public boolean isPayed() {
		return isPayed;
	}

	public void setPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}

	@Override
	public String toString() {
		return "PurchaseEntity [purchaseId=" + purchaseId + ", purchaseDateTime=" + purchaseDateTime + ", userEntity="
				+ userEntity + ", merchantEntity=" + merchantEntity + ", isPayed=" + isPayed + ", billNumber="
				+ billNumber + ", purchaseData=" + purchaseData + ", unModifiedPurchaseData=" + unModifiedPurchaseData
				+ ", isEditable=" + isEditable + ", updatedDateTime=" + updatedDateTime + ", isDeliverable="
				+ isDeliverable + ", amountDetails=" + amountDetails + ", unModifiedAmountDetails="
				+ unModifiedAmountDetails + "]";
	}

}
