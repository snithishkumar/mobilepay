package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.tn.mobilepay.request.model.RegisterJson;

@Entity
@Table(name = "UserDetails")
public class UserEntity {

	public static final String USER_ID = "userId";
	public static final String NAME = "name";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String LOGIN_ID = "loginId";
	public static final String IMEI_NUMBER = "imeiNumber";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String SERVER_TOKEN = "serverToken";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UserId")
	private int userId;
	@Column(name = "Name")
	private String name;
	@Column(name = "MobileNumber")
	private String mobileNumber;
	@Column(name = "LoginId")
	private int loginId;
	@Column(name = "ImeiNumber")
	private String imeiNumber;
	@Column(name = "IsActive")
	private boolean isActive;
	@Column(name = "AccessToken")
	private String accessToken;
	@Column(name = "ServerToken")
	private String serverToken;
	
	public void toUser(RegisterJson registerJson){
		this.setLoginId(Integer.valueOf(registerJson.getPassword()));
		this.setMobileNumber(registerJson.getMobileNumber());
		this.setName(registerJson.getName());
		this.setImeiNumber(registerJson.getImei());
		this.setActive(true);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	
	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	

	public String getServerToken() {
		return serverToken;
	}

	public void setServerToken(String serverToken) {
		this.serverToken = serverToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", name=" + name + ", mobileNumber=" + mobileNumber + ", loginId="
				+ loginId + ", imeiNumber=" + imeiNumber + ", isActive=" + isActive + ", accessToken=" + accessToken
				+ ", serverToken=" + serverToken + "]";
	}

	
	

	
}
