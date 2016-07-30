package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import in.tn.mobilepay.enumeration.DeliveryConditons;
import in.tn.mobilepay.enumeration.DeliveryOptions;

@Entity
@Table(name = "DeliveryOptions")
public class HomeDeliveryOptionsEntity {
	
	public static final String DELIVERY_OPTIONS_ID = "deliveryOptionsId";
	public static final String DELIVERY_OPTIONS = "deliveryOptions";
	public static final String DELIVERY_CONDITIONS = "deliveryConditions";
	public static final String MIN_AMOUNT = "minAmount";
	public static final String MAX_DISTANCE = "maxDistance";
	public static final String AMOUNT = "amount";
	public static final String MERCHANT_ENTITY = "merchantEntity";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DeliveryOptionsId")
	private int deliveryOptionsId;

	@ManyToOne
	@JoinColumn(name = "MerchantId", referencedColumnName = "MerchantId")
	private MerchantEntity merchantEntity;

	@Column(name = "DeliveryOptions")
	@Enumerated(EnumType.STRING)
	private DeliveryOptions deliveryOptions;

	@Column(name = "DeliveryConditions")
	@Enumerated(EnumType.STRING)
	private DeliveryConditons deliveryConditions;

	@Column(name = "MinAmount")
	private float minAmount;

	@Column(name = "MaxDistance")
	private float maxDistance;

	@Column(name = "Amount")
	private float amount;

	public int getDeliveryOptionsId() {
		return deliveryOptionsId;
	}

	public void setDeliveryOptionsId(int deliveryOptionsId) {
		this.deliveryOptionsId = deliveryOptionsId;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	public DeliveryOptions getDeliveryOptions() {
		return deliveryOptions;
	}

	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}

	

	public DeliveryConditons getDeliveryConditions() {
		return deliveryConditions;
	}

	public void setDeliveryConditions(DeliveryConditons deliveryConditions) {
		this.deliveryConditions = deliveryConditions;
	}

	public float getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(float minAmount) {
		this.minAmount = minAmount;
	}

	public float getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "HomeDeliveryOptionsEntity [deliveryOptionsId=" + deliveryOptionsId + ", merchantEntity="
				+ merchantEntity + ", deliveryOptions=" + deliveryOptions + ", deliveryConditions=" + deliveryConditions
				+ ", minAmount=" + minAmount + ", maxDistance=" + maxDistance + ", amount=" + amount + "]";
	}

	
}
