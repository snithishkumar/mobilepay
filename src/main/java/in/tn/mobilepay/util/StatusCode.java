package in.tn.mobilepay.util;

public class StatusCode {
	
	
	public static final int MER_OK = 200;
	public static final int MER_ERROR = 500;
	public static final int MER_MOBILE_ALREADY_PRESENT = 403;
	public static final int MER_INVALID_LOGIN = 404;
	public static final int MER_UNAUTHORIZE = 405;
	public static final int MER_INVALID_MOBILE = 406;
	public static final int MER_INVALID_PROFILE = 407;
	
	/** Registration 2^1 **/
	public static final int REG_OK = 2;
	public static final int INVALID_MOBILE = 201;
	public static final int INTERNAL_ERROR = 202;
	
	/** OTP Validation 2^2 **/
	public static final int OTP_OK = 4;
	public static final int INVALID_OTP = 401;
	public static final int OTP_EXPIRED = 403;
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
	public static final int OTP_ERROR = 1604;
	
	
	/** Card 2^7 **/
    public static final int CARD_LIST_SUCCESS = 128;
    public static final int CARD_LIST_FAILURE = 12801;
    
    
    /** Profile 2^8 **/
    public static final int PROFILE_OK = 25601;
	

}
