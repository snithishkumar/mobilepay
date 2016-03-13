package in.tn.mobilepay.util;

public class StatusCode {
	
	/** Registration 2^1 **/
	public static final int REG_OK = 2;
	public static final int INVALID_MOBILE = 201;
	public static final int INTERNAL_ERROR = 202;
	
	/** OTP Validation 2^2 **/
	public static final int OTP_OK = 4;
	public static final int INVALID_OTP = 401;
	public static final int OTP_INTERNAL_ERROR = 402;
	
	/** Login 2^3 **/
	public static final int LOGIN_OK = 8;
	public static final int LOGIN_INVALID_PIN = 801;
	public static final int LOGIN_INTERNAL_ERROR = 802;
	public static final int LOGIN_INVALID_MOBILE = 802;
	
	
	/** Mobile Number Validation 2^4 **/
	public static final int MOB_VAL_OK = 16;
	public static final int MOB_VAL_INVALID = 1601;
	public static final int MOB_VAL_INTERNAL_ERROR = 1602;
	
	
	/** Card 2^7 **/
    public static final int CARD_LIST_SUCCESS = 128;
    public static final int CARD_LIST_FAILURE = 12801;
	

}
