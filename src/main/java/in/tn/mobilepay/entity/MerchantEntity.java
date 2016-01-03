package in.tn.mobilepay.entity;

public class MerchantEntity {

	private int merchantId;
	private String merchantName;
	private String merchantAddress;
	private String area;
	private long mobileNumber;
	private long landLineNumber;

	public int getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public long getLandLineNumber() {
		return landLineNumber;
	}

	public void setLandLineNumber(long landLineNumber) {
		this.landLineNumber = landLineNumber;
	}

	@Override
	public String toString() {
		return "MerchantEntity [merchantId=" + merchantId + ", merchantName=" + merchantName + ", merchantAddress="
				+ merchantAddress + ", area=" + area + ", mobileNumber=" + mobileNumber + ", landLineNumber="
				+ landLineNumber + "]";
	}

}
