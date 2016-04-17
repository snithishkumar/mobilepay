package in.tn.mobilepay.request.model;

public class OrderStatusUpdate {
	private String purchaseUUID;
	private String orderStatus;

	public String getPurchaseUUID() {
		return purchaseUUID;
	}

	public void setPurchaseUUID(String purchaseUUID) {
		this.purchaseUUID = purchaseUUID;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "OrderStatusUpdate [purchaseUUID=" + purchaseUUID + ", orderStatus=" + orderStatus + "]";
	}

}
