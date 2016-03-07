package in.tn.mobilepay.response.model;

import in.tn.mobilepay.entity.MerchantEntity;

public class MerchantJson {

	private String merchantName;
	private String merchantUuid;
	private String address;
	private String area;
	private String pinCode;
	private long mobileNumber;
	private long landNumber;
    private long createdDateTime;
    private long lastModifiedDateTime;

	public MerchantJson() {

	}

	public MerchantJson(MerchantEntity merchantEntity) {
		this.merchantName = merchantEntity.getMerchantName();
		this.merchantUuid = merchantEntity.getMerchantGuid();
		this.pinCode = merchantEntity.getPinCode();
		this.createdDateTime = merchantEntity.getCreatedTime();
		this.lastModifiedDateTime = merchantEntity.getUpdatedTime();
		this.address = merchantEntity.getMerchantAddress();
		this.area = merchantEntity.getArea();
		this.mobileNumber = merchantEntity.getMobileNumber();
		this.landNumber = merchantEntity.getLandLineNumber();

	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public long getLandNumber() {
		return landNumber;
	}

	public void setLandNumber(long landNumber) {
		this.landNumber = landNumber;
	}

	@Override
	public String toString() {
		return "MerchantJson [merchantName=" + merchantName + ", address=" + address + ", area=" + area
				+ ", mobileNumber=" + mobileNumber + ", landNumber=" + landNumber + "]";
	}

}
