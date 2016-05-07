package in.tn.mobilepay.request.model;

public class RegisterJson extends TokenJson{
	
	private String name;
	private String password;
	private String mobileNumber;
	private String imei;
	 private boolean isPasswordForget;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	
	

	public boolean isPasswordForget() {
		return isPasswordForget;
	}

	public void setPasswordForget(boolean isPasswordForget) {
		this.isPasswordForget = isPasswordForget;
	}

	@Override
	public String toString() {
		return "RegisterJson [name=" + name + ", password=" + password + ", mobileNumber=" + mobileNumber + ", imei="
				+ imei + "]";
	}

}
