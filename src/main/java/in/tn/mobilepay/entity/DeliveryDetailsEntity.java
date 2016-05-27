package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import in.tn.mobilepay.enumeration.DeliveryStatus;

@Entity
@Table(name = "DeliveryDetails")
public class DeliveryDetailsEntity {

	public static final String DELIVERY_DETAILS_ID = "deliveryDetailsId";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String PURCHASE_ENTITY = "purchaseEntity";
	public static final String OUT_FOR_DELIVERY = "outForDeliveryDate";
	public static final String DELIVERED_DATE = "deliveredDate";
	public static final String MESSAGE = "message";
	public static final String DELIVERED_STATUS = "deliveryStatus";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DeliveryDetailsId")
	private int deliveryDetailsId;
	@Column(name = "MobileNumber")
	private String mobileNumber;
	@ManyToOne
	@JoinColumn(name = "PurchaseId", referencedColumnName = "PurchaseId")
	private PurchaseEntity purchaseEntity;
	@Column(name = "OutForDeliveryDate")
	private long outForDeliveryDate;
	@Column(name = "DeliveredDate")
	private long deliveredDate;
	@Column(name = "Message")
	private String message;
	@Column(name = "DeliveryStatus")
	@Enumerated
	private DeliveryStatus deliveryStatus;

	public int getDeliveryDetailsId() {
		return deliveryDetailsId;
	}

	public void setDeliveryDetailsId(int deliveryDetailsId) {
		this.deliveryDetailsId = deliveryDetailsId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public PurchaseEntity getPurchaseEntity() {
		return purchaseEntity;
	}

	public void setPurchaseEntity(PurchaseEntity purchaseEntity) {
		this.purchaseEntity = purchaseEntity;
	}

	public long getOutForDeliveryDate() {
		return outForDeliveryDate;
	}

	public void setOutForDeliveryDate(long outForDeliveryDate) {
		this.outForDeliveryDate = outForDeliveryDate;
	}

	public long getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(long deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	@Override
	public String toString() {
		return "DeliveryDetailsEntity [deliveryDetailsId=" + deliveryDetailsId + ", mobileNumber=" + mobileNumber
				+ ", purchaseEntity=" + purchaseEntity + ", outForDeliveryDate=" + outForDeliveryDate
				+ ", deliveredDate=" + deliveredDate + ", message=" + message + ", deliveryStatus=" + deliveryStatus
				+ "]";
	}

}
