package in.tn.mobilepay.response.model;

import in.tn.mobilepay.entity.UserEntity;

public class UserJson {
	private String name;
	private String mobileNumber;
	
	public UserJson(){
		
	}
	
	public UserJson(UserEntity userEntity){
		this.name = userEntity.getName();
		this.mobileNumber = userEntity.getMobileNumber();
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

	@Override
	public String toString() {
		return "UserJson [name=" + name + ", mobileNumber=" + mobileNumber + "]";
	}

}
