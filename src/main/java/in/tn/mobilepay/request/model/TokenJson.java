package in.tn.mobilepay.request.model;

public class TokenJson {
	
	private String accessToken;
	private String serverToken;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getServerToken() {
		return serverToken;
	}
	public void setServerToken(String serverToken) {
		this.serverToken = serverToken;
	}
	@Override
	public String toString() {
		return "TokenJson [clientToken=" + accessToken + ", serverToken=" + serverToken + "]";
	}
	
	

}
