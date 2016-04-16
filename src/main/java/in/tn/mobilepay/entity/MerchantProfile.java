package in.tn.mobilepay.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MerchantProfile")
public class MerchantProfile {
	
	public static final String MERCHANT_PRO_ID = "merchantProfileId";
	public static final String MERCHANT_ID = "merchantEntity";
	public static final String MERCHANT_PRO = "merchantProfile";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "MerchantProfileId")
	private int merchantProfileId;
	@ManyToOne
	@JoinColumn(name = "MerchantId", referencedColumnName = "MerchantId")
	private MerchantEntity merchantEntity;
	@Lob
	@Column(name = "MerchantProfile")
	private Blob merchantProfile;

	public int getMerchantProfileId() {
		return merchantProfileId;
	}

	public void setMerchantProfileId(int merchantProfileId) {
		this.merchantProfileId = merchantProfileId;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	public Blob getMerchantProfile() {
		return merchantProfile;
	}

	public void setMerchantProfile(Blob merchantProfile) {
		this.merchantProfile = merchantProfile;
	}

}
