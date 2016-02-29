package in.tn.mobilepay.request.model;

public class MerchantLoginJson {

	private long mobileNumber;
	private String password;

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "MerchantLoginJson [mobileNumber=" + mobileNumber + ", password=" + password + "]";
	}

}
