package in.tn.mobilepay.exception;

public class ValidationException extends Exception {

	private int code;

	private String message;

	public ValidationException(int code, String msg, Throwable cause) {
		super(msg, cause);
		this.code = code;
		this.message = msg;
	}

	public ValidationException(int code, String msg) {
		super(msg);
		this.code = code;
		this.message = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
