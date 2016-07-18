package in.tn.mobilepay.rest.json;

import in.tn.mobilepay.enumeration.OrderStatus;

public class OrderStatusUpdate {

	private String purchaseUUID;
	private OrderStatus orderStatus;
	private String declineReason;
	private String description;
	private String counterNumber;

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

	public String getDeclineReason() {
		return declineReason;
	}

	public void setDeclineReason(String declineReason) {
		this.declineReason = declineReason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCounterNumber() {
		return counterNumber;
	}

	public void setCounterNumber(String counterNumber) {
		this.counterNumber = counterNumber;
	}

	@Override
	public String toString() {
		return "OrderStatusUpdate [purchaseUUID=" + purchaseUUID + ", orderStatus=" + orderStatus + ", declineReason="
				+ declineReason + ", description=" + description + ", counterNumber=" + counterNumber + "]";
	}

}
