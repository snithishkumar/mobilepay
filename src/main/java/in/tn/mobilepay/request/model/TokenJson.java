package in.tn.mobilepay.request.model;

public class TokenJson {
	
	private String clientToken;
	private String serverToken;
	public String getClientToken() {
		return clientToken;
	}
	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}
	public String getServerToken() {
		return serverToken;
	}
	public void setServerToken(String serverToken) {
		this.serverToken = serverToken;
	}
	@Override
	public String toString() {
		return "TokenJson [clientToken=" + clientToken + ", serverToken=" + serverToken + "]";
	}
	
	

}
