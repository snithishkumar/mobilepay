package in.tn.mobilepay.request.model;

public class OrderStatusUpdate extends TokenJson{
	private String purchaseUUID;
	private String orderStatus;
	private String orderStatusDesc;
	
	


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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "OrderStatusUpdate [purchaseUUID=" + purchaseUUID + ", orderStatus=" + orderStatus + ", orderStatusDesc="
				+ orderStatusDesc + "]";
	}

	
	

}
