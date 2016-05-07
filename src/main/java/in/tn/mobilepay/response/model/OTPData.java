package in.tn.mobilepay.response.model;

public class OTPData {
	private String code;
	private String oneTimePassword;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOneTimePassword() {
		return oneTimePassword;
	}

	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	@Override
	public String toString() {
		return "OTPData [code=" + code + ", oneTimePassword=" + oneTimePassword + "]";
	}

}
