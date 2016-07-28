package in.tn.mobilepay.request.model;

import in.tn.mobilepay.enumeration.OrderStatus;

public class OrderStatusUpdate extends TokenJson{
	private String purchaseUUID;
	private OrderStatus orderStatus;
	private String orderStatusDesc;
	private String counterNumber;
	
	


	public String getCounterNumber() {
		return counterNumber;
	}

	public void setCounterNumber(String counterNumber) {
		this.counterNumber = counterNumber;
	}

	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}

	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}

	public String getPurchaseUUID() {
		return purchaseUUID;
	}

	public void setPurchaseUUID(String purchaseUUID) {
		this.purchaseUUID = purchaseUUID;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "OrderStatusUpdate [purchaseUUID=" + purchaseUUID + ", orderStatus=" + orderStatus + ", orderStatusDesc="
				+ orderStatusDesc + "]";
	}

	
	

}
