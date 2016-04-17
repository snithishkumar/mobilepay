package in.tn.mobilepay.request.model;

public class MerchantOrderStatusJson extends TokenJson {
	private long purchaseDateTime;
	private int limit;
	private int offSet;

	public long getPurchaseDateTime() {
		return purchaseDateTime;
	}

	public void setPurchaseDateTime(long purchaseDateTime) {
		this.purchaseDateTime = purchaseDateTime;
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
		return "MerchantOrderStatusJson [purchaseDateTime=" + purchaseDateTime + ", limit=" + limit + ", offSet="
				+ offSet + "]";
	}

}
