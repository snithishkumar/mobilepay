package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import in.tn.mobilepay.enumeration.DeviceType;
import in.tn.mobilepay.request.model.CloudMessageJson;

@Entity
@Table(name = "CloudMessageEntity")
public class CloudMessageEntity {

	public static final String CLOUD_MESSAGE_ID = "cloudMessageId";
	public static final String IMEI_NUMBER = "imeiNumber";
	public static final String CLOUD_ID = "cloudId";
	public static final String DEVICE_TYPE = "deviceType";
	public static final String USER_ENTITY = "userEntity";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CloudMessageId")
	private int cloudMessageId;
	@Column(name = "ImeiNumber",unique=true)
	private String imeiNumber;
	@Column(name = "CloudId")
	private String cloudId;
	@Enumerated(EnumType.STRING)
	@Column(name = "DeviceType")
	private DeviceType deviceType;

	@ManyToOne
	@JoinColumn(name = "UserId", referencedColumnName = "UserId",unique=true)
	private UserEntity userEntity;
	
	public CloudMessageEntity(){
		
	}
	
	public CloudMessageEntity(CloudMessageJson cloudMessageJson) {
		toCloudMessageEntity(cloudMessageJson);;
	}
	
	public void toCloudMessageEntity(CloudMessageJson cloudMessageJson){
		this.imeiNumber = cloudMessageJson.getImeiNumber();
		this.cloudId = cloudMessageJson.getCloudId();
		this.deviceType = cloudMessageJson.getDeviceType();
	}

	public int getCloudMessageId() {
		return cloudMessageId;
	}

	public void setCloudMessageId(int cloudMessageId) {
		this.cloudMessageId = cloudMessageId;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public String getCloudId() {
		return cloudId;
	}

	public void setCloudId(String cloudId) {
		this.cloudId = cloudId;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public String toString() {
		return "CloudMessageEntity [cloudMessageId=" + cloudMessageId + ", imeiNumber=" + imeiNumber + ", cloudId="
				+ cloudId + ", deviceType=" + deviceType + ", userEntity=" + userEntity + "]";
	}

}
