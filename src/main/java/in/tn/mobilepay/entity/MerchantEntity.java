package in.tn.mobilepay.entity;

public class MerchantEntity {

	private int merchantId;
	private String merchantName;
	private String merchantAddress;

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

	@Override
	public String toString() {
		return "MerchantEntity [merchantId=" + merchantId + ", merchantName="
				+ merchantName + ", merchantAddress=" + merchantAddress + "]";
	}

}
