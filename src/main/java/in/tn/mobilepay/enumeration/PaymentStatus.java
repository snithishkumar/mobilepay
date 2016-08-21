package in.tn.mobilepay.enumeration;

public enum PaymentStatus {

	NOT_PAID(1), PAID(2), SUCCESS(3), FAILURE(4);

	private int paymentStatusType;

	private PaymentStatus(int paymentStatusType) {
		this.paymentStatusType = paymentStatusType;
	}

	public int getPaymentStatusType() {
		return paymentStatusType;
	}

}
