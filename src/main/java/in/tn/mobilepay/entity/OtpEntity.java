package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OtpDetails")
public class OtpEntity {

	public static final String USER_ID = "userEntity";
	public static final String OTP_NUMBER = "optNumber";
	public static final String VALIDATION_TIME = "validationTime";

	@Column(name = "UserId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserId", referencedColumnName = "UserId")
	private UserEntity userEntity;
	@Column(name = OTP_NUMBER)
	private int optNumber;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OtpId")
	private int optId;

	@Column(name = "ValidationTime")
	private long validationTime;

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public int getOptNumber() {
		return optNumber;
	}

	public void setOptNumber(int optNumber) {
		this.optNumber = optNumber;
	}

	public int getOptId() {
		return optId;
	}

	public void setOptId(int optId) {
		this.optId = optId;
	}
	
	

	public long getValidationTime() {
		return validationTime;
	}

	public void setValidationTime(long validationTime) {
		this.validationTime = validationTime;
	}

	@Override
	public String toString() {
		return "OtpEntity [userEntity=" + userEntity + ", optNumber=" + optNumber + ", optId=" + optId + "]";
	}

}
