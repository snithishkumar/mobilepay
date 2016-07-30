package in.tn.mobilepay.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import in.tn.mobilepay.enumeration.DeliveryOptions;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.enumeration.PaymentStatus;
import in.tn.mobilepay.request.model.PurchaseJson;
import in.tn.mobilepay.services.ServiceUtil;

@Entity
@Table(name = "Purchase")
public class PurchaseEntity {

	public static final String PURCHASE_ID = "purchaseId";
	public static final String PURCHASE_GUID = "purchaseGuid";
	public static final String PURCHASE_DATE_TIME = "purchaseDateTime";
	public static final String USER_ID = "userEntity";
	public static final String MERCHANT_ID = "merchantEntity";
	public static final String PAYMENT_STATUS = "paymentStatus";
	public static final String BILL_NUMBER = "billNumber";
	public static final String IS_EDITABLE = "isEditable";
	public static final String UPDATED_DATE_TIME = "updatedDateTime";
	public static final String SERVER_DATE_TIME = "serverDateTime";
	public static final String ORDER_STATUS = "orderStatus";
	public static final String MERCHANT_DELIVERY_OPTIONS = "merchantDeliveryOptions";
	public static final String USER_DELIVERY_OPTIONS = "userDeliveryOptions";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PurchaseId")
	private int purchaseId;
	@Column(name = "PurchaseGuid")
	private String purchaseGuid;
	@Column(name = "PurchaseDateTime")
	private long purchaseDateTime;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "UserId", referencedColumnName = "UserId")
	private UserEntity userEntity;
	@ManyToOne
	@JoinColumn(name = "MerchantId", referencedColumnName = "MerchantId")
	private MerchantEntity merchantEntity;
	@Column(name = "PaymentStatus")
	@Enumerated(EnumType.ORDINAL)
	private PaymentStatus paymentStatus;
	@Column(name = "BillNumber")
	private String billNumber;
	// Product Number,Product Name,Quantity and Amount as a json array
	@Column(name = "PurchaseData")
	@Lob
	private String purchaseData;
	@Column(name = "UnModifiedPurchaseData")
	@Lob
	private String unModifiedPurchaseData;
	@Column(name = "IsEditable")
	private boolean isEditable;
	@Column(name = "UpdatedDateTime")
	private long updatedDateTime;

	// Amount, Tax, Total amount
	@Column(name = "AmountDetails")
	private String amountDetails;

	@Column(name = "UnModifiedAmountDetails")
	private String unModifiedAmountDetails;

	@Column(name = "ServerDateTime")
	private long serverDateTime;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "MerchantDeliveryOptions")
	private DeliveryOptions merchantDeliveryOptions;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "UserDeliveryOptions")
	private DeliveryOptions userDeliveryOptions;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "OrderStatus") // ORDER_STATUS any one of status
	private OrderStatus orderStatus;

	@Column(name = "TotalAmount")
	private String totalAmount;

	@ManyToMany
	@JoinTable(name = "HomeDeliveryAddress", joinColumns = { @JoinColumn(name = "addressId") }, inverseJoinColumns = {
			@JoinColumn(name = "purchaseId") })
	private Collection<AddressEntity> addressEntities;

	public PurchaseEntity() {

	}

	public void loadValue(PurchaseJson purchaseJson) {
		this.totalAmount = purchaseJson.getTotalAmount();
		this.billNumber = purchaseJson.getBillNumber();
		this.purchaseGuid = purchaseJson.getPurchaseUuid();
		this.purchaseDateTime = Long.valueOf(purchaseJson.getPurchaseDateTime());
		this.updatedDateTime = ServiceUtil.getCurrentGmtTime();
		this.isEditable = purchaseJson.getIsEditable();
		this.serverDateTime = updatedDateTime;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
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

	public String getPurchaseGuid() {
		return purchaseGuid;
	}

	public void setPurchaseGuid(String purchaseGuid) {
		this.purchaseGuid = purchaseGuid;
	}

	public long getServerDateTime() {
		return serverDateTime;
	}

	public void setServerDateTime(long serverDateTime) {
		this.serverDateTime = serverDateTime;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Collection<AddressEntity> getAddressEntities() {
		return addressEntities;
	}

	public void setAddressEntities(Collection<AddressEntity> addressEntities) {
		this.addressEntities = addressEntities;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public DeliveryOptions getMerchantDeliveryOptions() {
		return merchantDeliveryOptions;
	}

	public void setMerchantDeliveryOptions(DeliveryOptions merchantDeliveryOptions) {
		this.merchantDeliveryOptions = merchantDeliveryOptions;
	}

	public DeliveryOptions getUserDeliveryOptions() {
		return userDeliveryOptions;
	}

	public void setUserDeliveryOptions(DeliveryOptions userDeliveryOptions) {
		this.userDeliveryOptions = userDeliveryOptions;
	}

	@Override
	public String toString() {
		return "PurchaseEntity [purchaseId=" + purchaseId + ", purchaseGuid=" + purchaseGuid + ", purchaseDateTime="
				+ purchaseDateTime + ", userEntity=" + userEntity + ", merchantEntity=" + merchantEntity
				+ ", paymentStatus=" + paymentStatus + ", billNumber=" + billNumber + ", purchaseData=" + purchaseData
				+ ", unModifiedPurchaseData=" + unModifiedPurchaseData + ", isEditable=" + isEditable
				+ ", updatedDateTime=" + updatedDateTime + ", amountDetails=" + amountDetails
				+ ", unModifiedAmountDetails=" + unModifiedAmountDetails + ", serverDateTime=" + serverDateTime
				+ ", merchantDeliveryOptions=" + merchantDeliveryOptions + ", userDeliveryOptions="
				+ userDeliveryOptions + ", orderStatus=" + orderStatus + ", totalAmount=" + totalAmount
				+ ", addressEntities=" + addressEntities + "]";
	}

}
