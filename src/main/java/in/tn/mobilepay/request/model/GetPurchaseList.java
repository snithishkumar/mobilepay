package in.tn.mobilepay.request.model;

public class GetPurchaseList{
	
	private long serverTime;

	public long getServerTime() {
		return serverTime;
	}

	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}

	@Override
	public String toString() {
		return "GetPurchaseList [serverTime=" + serverTime + "]";
	}
	
	

}
