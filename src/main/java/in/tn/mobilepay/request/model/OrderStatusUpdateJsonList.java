package in.tn.mobilepay.request.model;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusUpdateJsonList extends TokenJson{
	
	private List<OrderStatusUpdate> orderStatusUpdates = new ArrayList<>();

	public List<OrderStatusUpdate> getOrderStatusUpdates() {
		return orderStatusUpdates;
	}

	public void setOrderStatusUpdates(List<OrderStatusUpdate> orderStatusUpdates) {
		this.orderStatusUpdates = orderStatusUpdates;
	}
	
	

}
