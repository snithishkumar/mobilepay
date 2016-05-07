package in.tn.mobilepay.request.model;

public class OtpJson {
	
	private String mobileNumber;
	private String otpPassword;
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public String getOtpPassword() {
		return otpPassword;
	}
	public void setOtpPassword(String otpPassword) {
		this.otpPassword = otpPassword;
	}
	@Override
	public String toString() {
		return "OtpJson [mobileNumber=" + mobileNumber + ", otpPassword=" + otpPassword + "]";
	}

	

}
