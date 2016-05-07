package in.tn.mobilepay.response.model;

public class OTPResponse {
	
	private String status;
	private OTPData response;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public OTPData getResponse() {
		return response;
	}
	public void setResponse(OTPData response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return "OTPResponse [status=" + status + ", response=" + response + "]";
	}
	
	

}
