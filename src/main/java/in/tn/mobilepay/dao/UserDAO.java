package in.tn.mobilepay.dao;

import java.util.List;

import in.tn.mobilepay.entity.AddressEntity;
import in.tn.mobilepay.entity.CloudMessageEntity;
import in.tn.mobilepay.entity.OtpEntity;
import in.tn.mobilepay.entity.UserEntity;

public interface UserDAO {

	void createUser(UserEntity userEntity);

	void createOtp(OtpEntity otpEntity);

	void updateUser(UserEntity userEntity);

	UserEntity getUserEntity(String mobileNumber);

	UserEntity getUserEntity(int userId);

	UserEntity getUserEnity(String imeiNumber, String password);

	Integer getUserEnityByToken(String clientToken, String serverToken);

	OtpEntity getOtpEntity(String mobileNumber);

	void updateOtpEntity(OtpEntity otpEntity);

	void deleteOtpEntity(OtpEntity otpEntity);


	void createAddressEntity(AddressEntity addressEntity);

	void updateAddressEntity(AddressEntity addressEntity);

	List<AddressEntity> getAddressList(Long lastModifiedTime, UserEntity userEntity);

	AddressEntity getAddressEntity(String addressGuid, UserEntity userEntity);

	AddressEntity getAddressEntity(String addressGuid);

	
	
	void saveCloudMessageEntity(CloudMessageEntity cloudMessageEntity);

	void updateCloudMessageEntity(CloudMessageEntity cloudMessageEntity);

	void removeCloudMessageEntity(CloudMessageEntity cloudMessageEntity);

	CloudMessageEntity getCloudMessageEntity(UserEntity userEntity);

}
