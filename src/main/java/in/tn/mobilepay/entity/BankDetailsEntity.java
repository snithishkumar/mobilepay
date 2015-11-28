package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Banks")
public class BankDetailsEntity {

	public static final String BANK_DETAILS_ID = "bankDetailsId";
	public static final String BANK_GUID = "bankGuid";
	public static final String BANK_NAME = "bankName";
	public static final String IS_ACTIVE = "isActive";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BankDetailsId")
	private int bankDetailsId;
	@Column(name = "BankGuid")
	private String bankGuid;
	@Column(name = "BankName")
	private String bankName;
	
	@Column(name="IsActive")
	private boolean isActive;

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
	
	

	public String getBankGuid() {
		return bankGuid;
	}

	public void setBankGuid(String bankGuid) {
		this.bankGuid = bankGuid;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "BankDetailsEntity [bankDetailsId=" + bankDetailsId
				+ ", bankName=" + bankName + "]";
	}

}
