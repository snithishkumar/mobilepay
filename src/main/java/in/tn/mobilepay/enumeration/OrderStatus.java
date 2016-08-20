package in.tn.mobilepay.enumeration;

public enum OrderStatus {
	PURCHASE(1),PACKING(2),READY_TO_COLLECT(3),READY_TO_SHIPPING(4),OUT_FOR_DELIVERY(5),FAILED_TO_DELIVER(6),DELIVERED(7),CANCELLED(8);
	private int orderStatusId;

	private OrderStatus(int orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public int getOrderStatusId() {
		return orderStatusId;
	}

}
