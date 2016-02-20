package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Merchant")
public class MerchantEntity {

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
	@Column(name = "MobileNumber")
	private long mobileNumber;
	@Column(name = "LandLineNumber")
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
