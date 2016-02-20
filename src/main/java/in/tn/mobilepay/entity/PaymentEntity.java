package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PaymentDetails")
public class PaymentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PaymentId")
	private int paymentId;
	@Column(name = "Lat")
	private double lat;
	@Column(name = "Lng")
	private double lng;
	@Column(name = "PaymentAmount")
	private double paymentAmount;
	@Column(name = "PayedDateTime")
	private long payedDateTime;

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public long getPayedDateTime() {
		return payedDateTime;
	}

	public void setPayedDateTime(long payedDateTime) {
		this.payedDateTime = payedDateTime;
	}

	@Override
	public String toString() {
		return "PaymentEntity [paymentId=" + paymentId + ", lat=" + lat
				+ ", lng=" + lng + ", paymentAmount=" + paymentAmount
				+ ", payedDateTime=" + payedDateTime + "]";
	}

}
