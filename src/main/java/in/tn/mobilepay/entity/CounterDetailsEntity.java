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
@Table(name = "CounterDetails")
public class CounterDetailsEntity {
	
	public static final String COUNTER_DETAILS_ID = "counterDetailsId";
	public static final String COUNTER_NUMBER = "counterNumber";
	public static final String MESSAGE = "message";
	public static final String CREATED_DATE_TIME = "createdDateTime";
	public static final String PURCHASE_ID = "purchaseEntity";
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CounterDetailsId")
	private int counterDetailsId;
	@Column(name = "CounterNumber")
	private String counterNumber;
	@Column(name = "Message")
	private String message;
	@Column(name = "CreatedDateime")
	private long createdDateTime;
	@Column(name = "CounterGuid")
	private String counterGuid;
	
	@ManyToOne
	@JoinColumn(name = "PurchaseId", referencedColumnName = "PurchaseId")
	private PurchaseEntity purchaseEntity;

	public int getCounterDetailsId() {
		return counterDetailsId;
	}

	public void setCounterDetailsId(int counterDetailsId) {
		this.counterDetailsId = counterDetailsId;
	}

	public String getCounterNumber() {
		return counterNumber;
	}

	public void setCounterNumber(String counterNumber) {
		this.counterNumber = counterNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(long createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	
	

	public PurchaseEntity getPurchaseEntity() {
		return purchaseEntity;
	}

	public void setPurchaseEntity(PurchaseEntity purchaseEntity) {
		this.purchaseEntity = purchaseEntity;
	}
	
	

	public String getCounterGuid() {
		return counterGuid;
	}

	public void setCounterGuid(String counterGuid) {
		this.counterGuid = counterGuid;
	}

	@Override
	public String toString() {
		return "CounterDetailsEntity [counterDetailsId=" + counterDetailsId + ", counterNumber=" + counterNumber
				+ ", message=" + message + ", createdDateTime=" + createdDateTime + ", counterGuid=" + counterGuid
				+ ", purchaseEntity=" + purchaseEntity + "]";
	}

	

}
