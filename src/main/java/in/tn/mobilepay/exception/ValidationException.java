package in.tn.mobilepay.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends Exception {

	private HttpStatus code;

	private String message;

	public ValidationException(HttpStatus code, String msg, Throwable cause) {
		super(msg, cause);
		this.code = code;
		this.message = msg;
	}

	public ValidationException(HttpStatus code, String msg) {
		super(msg);
		this.code = code;
		this.message = msg;
	}

	public HttpStatus getCode() {
		return code;
	}

	public void setCode(HttpStatus code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
