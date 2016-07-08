package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OtpDetails")
public class OtpEntity {

	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String OTP_NUMBER = "optNumber";
	public static final String VALIDATION_TIME = "validationTime";
	public static final String CREATED_DATE = "createdDateTime";
	
	@Column(name = "MobileNumber",unique = true)
	private String mobileNumber;
	@Column(name = OTP_NUMBER)
	private String optNumber;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OtpId")
	private int optId;

	@Column(name = "ValidationTime")
	private long validationTime;
	
	@Column(name = "CreatedDate")
	private long createdDateTime;

	

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOptNumber() {
		return optNumber;
	}

	public void setOptNumber(String optNumber) {
		this.optNumber = optNumber;
	}

	public int getOptId() {
		return optId;
	}

	public void setOptId(int optId) {
		this.optId = optId;
	}
	
	
	
	

	public long getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public long getValidationTime() {
		return validationTime;
	}

	public void setValidationTime(long validationTime) {
		this.validationTime = validationTime;
	}

	@Override
	public String toString() {
		return "OtpEntity [mobileNumber=" + mobileNumber + ", optNumber=" + optNumber + ", optId=" + optId
				+ ", validationTime=" + validationTime + "]";
	}

}
