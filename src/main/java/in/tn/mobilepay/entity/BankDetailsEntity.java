package in.tn.mobilepay.entity;

public class BankDetailsEntity {

	private int bankDetailsId;
	private String bankName;

	public int getBankDetailsId() {
		return bankDetailsId;
	}

	public void setBankDetailsId(int bankDetailsId) {
		this.bankDetailsId = bankDetailsId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public String toString() {
		return "BankDetailsEntity [bankDetailsId=" + bankDetailsId
				+ ", bankName=" + bankName + "]";
	}

}
