package in.tn.mobilepay.rest.json;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusUpdateList {
	
	private List<OrderStatusUpdate> orderStatusUpdates = new ArrayList<>();

	public List<OrderStatusUpdate> getOrderStatusUpdates() {
		return orderStatusUpdates;
	}

	public void setOrderStatusUpdates(List<OrderStatusUpdate> orderStatusUpdates) {
		this.orderStatusUpdates = orderStatusUpdates;
	}

	@Override
	public String toString() {
		return "OrderStatusUpdateList [orderStatusUpdates=" + orderStatusUpdates + "]";
	}
	
	

}
