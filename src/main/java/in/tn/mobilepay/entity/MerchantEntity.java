package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Merchant")
public class MerchantEntity {
	
	public static final String MERCHANT_ID = "merchantId";
	public static final String MERCHANT_NME = "merchantName";
	public static final String MERCHANT_GUID = "merchantGuid";
	public static final String MERCHANT_ADDRESS = "merchantAddress";
	public static final String AREA = "area";
	public static final String PIN_CODE = "pinCode";
	public static final String CREATED_DATE = "createdTime";
	public static final String UPDATED_TIME = "updatedTime";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String LAND_LINE_NUMBER = "landLineNumber";
	public static final String CATEGORY = "category";
	public static final String PASSWORD = "password";
	public static final String MERCHANT_TOKEN = "merchantToken";
	public static final String SERVER_TOKEN = "serverToken";
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MerchantId")
	private int merchantId;
	@Column(name = "MerchantName")
	private String merchantName;
	@Column(name = "MerchantGuid")
	private String merchantGuid;
	@Column(name = "MerchantAddress")
	private String merchantAddress;
	@Column(name = "Area")
	private String area;
	@Column(name = "PinCode")
	private String pinCode;
	@Column(name = "CreatedTime")
	private long createdTime;
	@Column(name = "UpdatedTime")
	private long updatedTime;
	@Column(name = "MobileNumber")
	private long mobileNumber;
	@Column(name = "LandLineNumber")
	private long landLineNumber;
	@Column(name = "Category")
	private String category;
	@Column(name = "Password")
	private String password;
	@Column(name = "MerchantToken")
	private String merchantToken;
	@Column(name = "ServerToken")
	private String serverToken;

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

	public String getMerchantGuid() {
		return merchantGuid;
	}

	public void setMerchantGuid(String merchantGuid) {
		this.merchantGuid = merchantGuid;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMerchantToken() {
		return merchantToken;
	}

	public void setMerchantToken(String merchantToken) {
		this.merchantToken = merchantToken;
	}

	public String getServerToken() {
		return serverToken;
	}

	public void setServerToken(String serverToken) {
		this.serverToken = serverToken;
	}

	@Override
	public String toString() {
		return "MerchantEntity [merchantId=" + merchantId + ", merchantName=" + merchantName + ", merchantGuid="
				+ merchantGuid + ", merchantAddress=" + merchantAddress + ", area=" + area + ", pinCode=" + pinCode
				+ ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + ", mobileNumber=" + mobileNumber
				+ ", landLineNumber=" + landLineNumber + ", category=" + category + ", password=" + password
				+ ", merchantToken=" + merchantToken + ", serverToken=" + serverToken + "]";
	}

}
