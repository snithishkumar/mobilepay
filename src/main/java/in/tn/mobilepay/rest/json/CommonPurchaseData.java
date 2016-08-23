package in.tn.mobilepay.rest.json;

import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.enumeration.PaymentStatus;

public class CommonPurchaseData {
	
private String purchaseUUID;
	
	private String billNumber;
	private long purchaseDate;
	private OrderStatus orderStatus;
	private long lastModifiedDate;
	private PaymentStatus paymentStatus;
	
	
	public CommonPurchaseData(){
		
	}
	
	public CommonPurchaseData(PurchaseEntity purchaseEntity){
		this.billNumber = purchaseEntity.getBillNumber();
		this.purchaseDate = purchaseEntity.getPurchaseDateTime();
		this.orderStatus = purchaseEntity.getOrderStatus();
		this.lastModifiedDate = purchaseEntity.getUpdatedDateTime();
		this.paymentStatus = purchaseEntity.getPaymentStatus();
		this.purchaseUUID = purchaseEntity.getPurchaseGuid();
	}
	public String getPurchaseUUID() {
		return purchaseUUID;
	}
	public void setPurchaseUUID(String purchaseUUID) {
		this.purchaseUUID = purchaseUUID;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public long getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(long purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public long getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(long lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	@Override
	public String toString() {
		return "CommonPurchaseData [purchaseUUID=" + purchaseUUID + ", billNumber=" + billNumber + ", purchaseDate="
				+ purchaseDate + ", orderStatus=" + orderStatus + ", lastModifiedDate=" + lastModifiedDate
				+ ", paymentStatus=" + paymentStatus + "]";
	}
	
	
	

}
