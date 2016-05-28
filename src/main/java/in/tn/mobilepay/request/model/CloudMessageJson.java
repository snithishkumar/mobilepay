package in.tn.mobilepay.request.model;

import in.tn.mobilepay.enumeration.DeviceType;

public class CloudMessageJson{
	
	private String cloudId;
	private DeviceType deviceType;
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
	
	
	

}
