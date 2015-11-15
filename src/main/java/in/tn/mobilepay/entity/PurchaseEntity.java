package in.tn.mobilepay.entity;

public class PurchaseEntity {

	private int purchaseId;
	private long purchaseDateTime;
	private UserEntity userEntity;
	private MerchantEntity merchantEntity;
	private double totalAmount;
	private boolean isPayed;

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public long getPurchaseDateTime() {
		return purchaseDateTime;
	}

	public void setPurchaseDateTime(long purchaseDateTime) {
		this.purchaseDateTime = purchaseDateTime;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public boolean isPayed() {
		return isPayed;
	}

	public void setPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}

	@Override
	public String toString() {
		return "PurchaseEntity [purchaseId=" + purchaseId
				+ ", purchaseDateTime=" + purchaseDateTime + ", userEntity="
				+ userEntity + ", merchantEntity=" + merchantEntity
				+ ", totalAmount=" + totalAmount + ", isPayed=" + isPayed + "]";
	}

}
