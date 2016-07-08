package in.tn.mobilepay.response.model;

public class ResponseData {

	private int statusCode;
	private Object data;
	private Boolean success;
	
	public ResponseData(boolean success, Object data) {
		this.success = success;
		this.data = data;
	}

	public ResponseData(int statusCode, Object data) {
		this.statusCode = statusCode;
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseData [statusCode=" + statusCode + ", data=" + data + "]";
	}

}
