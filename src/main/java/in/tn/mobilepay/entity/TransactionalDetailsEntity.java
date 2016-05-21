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

import in.tn.mobilepay.enumeration.DeviceType;
import in.tn.mobilepay.enumeration.PaymentStatus;


@Entity
@Table(name = "TransactionalDetails")
public class TransactionalDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "transactionId")
	private int transactionId;
	@Column(name = "paymentDate")
	private long paymentDate;
	@Column(name = "amount")
	private double amount;
	@Column(name = "transactionUUID")
	private String transactionUUID;
	@Column(name = "deviceType")
	@Enumerated
	private DeviceType deviceType;
	@ManyToOne
	@JoinColumn(name = "PurchaseId", referencedColumnName = "PurchaseId")
	private PurchaseEntity purchaseEntity;
	@Column(name = "imeiNumber")
	private String imeiNumber;
	@Column(name = "paymentStatus")
	@Enumerated
	private PaymentStatus paymentStatus;
	@Column(name = "reason")
	private String reason;
	
	

	public String getTransactionUUID() {
		return transactionUUID;
	}

	public void setTransactionUUID(String transactionUUID) {
		this.transactionUUID = transactionUUID;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public long getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(long paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public PurchaseEntity getPurchaseEntity() {
		return purchaseEntity;
	}

	public void setPurchaseEntity(PurchaseEntity purchaseEntity) {
		this.purchaseEntity = purchaseEntity;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "TransactionalDetailsEntity [transactionId=" + transactionId + ", paymentDate=" + paymentDate
				+ ", amount=" + amount + ", deviceType=" + deviceType + ", purchaseEntity=" + purchaseEntity
				+ ", imeiNumber=" + imeiNumber + ", paymentStatus=" + paymentStatus + ", reason=" + reason + "]";
	}

}
