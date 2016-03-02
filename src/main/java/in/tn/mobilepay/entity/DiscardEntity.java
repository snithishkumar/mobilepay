package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DiscardDetails")
public class DiscardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DiscardId")
	private int discardId;
	@Column(name = "DiscardGuid")
	private String discardGuid;
	@ManyToOne
	@JoinColumn(name = "MerchantId", referencedColumnName = "MerchantId")
	private MerchantEntity merchantEntity;
	@ManyToOne
	@JoinColumn(name = "UserId", referencedColumnName = "UserId")
	private UserEntity userEntity;
	@Column(name = "Reason")
	private String reason;
	@ManyToOne
	@JoinColumn(name = "PurchaseId", referencedColumnName = "PurchaseId")
	private PurchaseEntity purchaseEntity;

	public int getDiscardId() {
		return discardId;
	}

	public void setDiscardId(int discardId) {
		this.discardId = discardId;
	}

	public String getDiscardGuid() {
		return discardGuid;
	}

	public void setDiscardGuid(String discardGuid) {
		this.discardGuid = discardGuid;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	

	public PurchaseEntity getPurchaseEntity() {
		return purchaseEntity;
	}

	public void setPurchaseEntity(PurchaseEntity purchaseEntity) {
		this.purchaseEntity = purchaseEntity;
	}

	@Override
	public String toString() {
		return "DiscardEntity [discardId=" + discardId + ", discardGuid=" + discardGuid + ", merchantEntity="
				+ merchantEntity + ", userEntity=" + userEntity + ", reason=" + reason + ", purchaseEntity="
				+ purchaseEntity + "]";
	}

}
