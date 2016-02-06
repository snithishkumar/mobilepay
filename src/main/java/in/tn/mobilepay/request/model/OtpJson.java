package in.tn.mobilepay.request.model;

public class OtpJson {
	
	private String mobileNumber;
	private String otpNumber;
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getOtpNumber() {
		return otpNumber;
	}
	public void setOtpNumber(String otpNumber) {
		this.otpNumber = otpNumber;
	}
	@Override
	public String toString() {
		return "OtpJson [mobileNumber=" + mobileNumber + ", otpNumber=" + otpNumber + "]";
	}
	
	

}
