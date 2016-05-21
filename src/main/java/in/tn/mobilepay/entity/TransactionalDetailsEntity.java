package in.tn.mobilepay.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import in.tn.mobilepay.enumeration.DeviceType;
import in.tn.mobilepay.enumeration.PaymentStatus;


@Entity
@Table(name = "TransactionalDetails")
public class TransactionalDetailsEntity {

	private int transactionId;
	private long paymentDate;
	private double amount;
	private String transactionUUID;
	private DeviceType deviceType;
	private PurchaseEntity purchaseEntity;
	private String imeiNumber;
	private PaymentStatus paymentStatus;
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
