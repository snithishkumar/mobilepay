package in.tn.mobilepay.request.model;

public class UnPayedMerchantPurchaseJson extends TokenJson{
	private long serverSyncTime;
	private int limit;
	private int offSet;
	public long getServerSyncTime() {
		return serverSyncTime;
	}
	public void setServerSyncTime(long serverSyncTime) {
		this.serverSyncTime = serverSyncTime;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffSet() {
		return offSet;
	}
	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}
	@Override
	public String toString() {
		return "UnPayedMerchantPurchaseJson [serverSyncTime=" + serverSyncTime + ", limit=" + limit + ", offSet="
				+ offSet + "]";
	}
	
	

}
