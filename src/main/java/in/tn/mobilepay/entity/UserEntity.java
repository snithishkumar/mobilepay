package in.tn.mobilepay.entity;

import javax.persistence.Entity;

@Entity
public class UserEntity {

	private int userId;
	private String name;
	private String password;
	private String mobileName;
	private int loginId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileName() {
		return mobileName;
	}

	public void setMobileName(String mobileName) {
		this.mobileName = mobileName;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	@Override
	public String toString() {
		return "UserEntity [userId=" + userId + ", name=" + name
				+ ", password=" + password + ", mobileName=" + mobileName
				+ ", loginId=" + loginId + "]";
	}

}
