package in.tn.mobilepay.response.model;

import in.tn.mobilepay.enumeration.OrderStatus;

public class OrderStatusJson {
	private long serverDateTime;
	private String purchaseGuid;
	private OrderStatus orderStatus;
	private long updatedDateTime;
	private int purchaseId;
	private CounterDetailsJson counterDetails;

	public long getServerDateTime() {
		return serverDateTime;
	}

	public void setServerDateTime(long serverDateTime) {
		this.serverDateTime = serverDateTime;
	}

	public String getPurchaseGuid() {
		return purchaseGuid;
	}

	public void setPurchaseGuid(String purchaseGuid) {
		this.purchaseGuid = purchaseGuid;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public long getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(long updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public CounterDetailsJson getCounterDetails() {
		return counterDetails;
	}

	public void setCounterDetails(CounterDetailsJson counterDetails) {
		this.counterDetails = counterDetails;
	}

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	@Override
	public String toString() {
		return "OrderStatusJson [serverDateTime=" + serverDateTime + ", purchaseGuid=" + purchaseGuid + ", orderStatus="
				+ orderStatus + ", updatedDateTime=" + updatedDateTime + ", purchaseId=" + purchaseId
				+ ", counterDetails=" + counterDetails + "]";
	}

}
