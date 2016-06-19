package in.tn.mobilepay.request.model;

import in.tn.mobilepay.enumeration.DeviceType;

public class CloudMessageJson{
	
	private String cloudId;
	private DeviceType deviceType;
	private String imeiNumber;
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
	public String getImeiNumber() {
		return imeiNumber;
	}
	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}
	@Override
	public String toString() {
		return "CloudMessageJson [cloudId=" + cloudId + ", deviceType=" + deviceType + ", imeiNumber=" + imeiNumber
				+ "]";
	}
	
	
	

}
