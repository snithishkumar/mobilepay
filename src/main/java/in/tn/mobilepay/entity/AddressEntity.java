package in.tn.mobilepay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import in.tn.mobilepay.response.model.AddressJson;

@Entity
@Table(name = "DeliveryAddress")
public class AddressEntity {
	public static final String ADDRESS_ID = "addressId";
	public static final String ADDRESS_UUID = "addressUUID";
	public static final String NAME = "name";
	public static final String MOBILE_NUMBER = "mobileNumber";
	public static final String STREET = "street";
	public static final String ADDRESS = "address";
	public static final String AREA = "area";
	public static final String CITY = "city";
	public static final String POSTAL_CODE = "postalCode";
	public static final String LAST_MODIFIED_TIME = "lastModifiedTime";
	public static final String USER_ENTITY = "userEntity";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AddressId")
	private int addressId;
	@Column(name = "Name")
	private String name;
	@Column(name = "MobileNumber")
	private String mobileNumber;
	@Column(name = "AddressUUID",unique = true)
	private String addressUUID;
	@Column(name = "Street")
	private String street;
	@Column(name = "Address")
	private String address;
	@Column(name = "Area")
	private String area;
	@Column(name = "City")
	private String city;
	@Column(name = "PostalCode")
	private Long postalCode;
	@Column(name = "LastModifiedTime")
	private long lastModifiedTime;
	
	@ManyToOne
	@JoinColumn(name = "UserId", referencedColumnName = "UserId")
	private UserEntity userEntity;
	
	public AddressEntity(){
		
	}
	
	public AddressEntity(AddressJson addressJson){
		toAddress(addressJson);
	}
	
	public void toAddress(AddressJson addressJson){
		this.name = addressJson.getName();
		this.mobileNumber =addressJson.getMobile();
		this.addressUUID = addressJson.getAddressUUID();
		this.street = addressJson.getStreet();
		this.address = addressJson.getAddress();
		this.area = addressJson.getArea();
		this.city = addressJson.getCity();
		this.postalCode = addressJson.getPostalCode();
		this.lastModifiedTime = addressJson.getLastModifiedTime();
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getAddressUUID() {
		return addressUUID;
	}

	public void setAddressUUID(String addressUUID) {
		this.addressUUID = addressUUID;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(Long postalCode) {
		this.postalCode = postalCode;
	}

	public long getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(long lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public String toString() {
		return "AddressEntity [addressId=" + addressId + ", addressUUID=" + addressUUID + ", street=" + street
				+ ", address=" + address + ", area=" + area + ", city=" + city + ", postalCode=" + postalCode
				+ ", lastModifiedTime=" + lastModifiedTime + ", userEntity=" + userEntity + "]";
	}
	
	

}
