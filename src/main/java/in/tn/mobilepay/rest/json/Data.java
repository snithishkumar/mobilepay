package in.tn.mobilepay.rest.json;

public class Data {

	private int statusCode;
	private String billNumber;
	private String purchaseUUID;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getPurchaseUUID() {
		return purchaseUUID;
	}

	public void setPurchaseUUID(String purchaseUUID) {
		this.purchaseUUID = purchaseUUID;
	}

	@Override
	public String toString() {
		return "Data [statusCode=" + statusCode + ", billNumber=" + billNumber + ", purchaseUUID=" + purchaseUUID
				+ "]";
	}
}
