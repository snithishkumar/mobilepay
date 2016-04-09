package in.tn.mobilepay.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import in.tn.mobilepay.entity.AddressEntity;
import in.tn.mobilepay.entity.OtpEntity;
import in.tn.mobilepay.entity.UserEntity;

@Repository
public class UserDAO extends BaseDAO{

	
	public void createUser(UserEntity userEntity){
		saveObject(userEntity);
	}
	
	public void createOtp(OtpEntity otpEntity){
		saveObject(otpEntity);
	}
	
	public void updateUser(UserEntity userEntity){
		updateObject(userEntity);
	}
	
	public UserEntity getUserEntity(String mobileNumber){
		Criteria criteria = createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq(UserEntity.MOBILE_NUMBER, mobileNumber));
		return (UserEntity) criteria.uniqueResult();
	}
	
	
	public UserEntity getUserEnity(String imeiNumber,String password){
		Criteria criteria = createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq(UserEntity.LOGIN_ID, password));
		criteria.add(Restrictions.eq(UserEntity.IMEI_NUMBER, imeiNumber));
		return (UserEntity) criteria.uniqueResult();
	}
	
	public UserEntity getUserEnityByToken(String clientToken,String serverToken){
		Criteria criteria = createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq(UserEntity.ACCESS_TOKEN, clientToken));
		criteria.add(Restrictions.eq(UserEntity.SERVER_TOKEN, serverToken));
		return (UserEntity) criteria.uniqueResult();
	}
	
	
	
	public OtpEntity getOtpEntity(String mobileNumber){
		Criteria criteria = createCriteria(OtpEntity.class);
		criteria.add(Restrictions.eq(OtpEntity.MOBILE_NUMBER, mobileNumber));
		return (OtpEntity)criteria.uniqueResult();
	}
	
	public void updateOtpEntity(OtpEntity otpEntity){
		updateObject(otpEntity);
	}
	
	/**
	 * Returns AddressEntity for an addressGuid
	 * @param addressGuid
	 * @return
	 */
	public AddressEntity getAddressEntity(String addressGuid){
		Criteria criteria = createCriteria(AddressEntity.class);
		criteria.add(Restrictions.eq(AddressEntity.ADDRESS_UUID, addressGuid));
		return (AddressEntity)criteria.uniqueResult();
	}
	
	/**
	 * Returns AddressEntity for an addressGuid
	 * @param addressGuid
	 * @return
	 */
	public AddressEntity getAddressEntity(String addressGuid,UserEntity userEntity){
		Criteria criteria = createCriteria(AddressEntity.class);
		criteria.add(Restrictions.eq(AddressEntity.ADDRESS_UUID, addressGuid));
		criteria.add(Restrictions.eq(AddressEntity.USER_ENTITY, userEntity));
		return (AddressEntity)criteria.uniqueResult();
	}
	
	/**
	 * Get Address List
	 * @param lastModifiedTime
	 * @return
	 */
	public List<AddressEntity> getAddressList(Long lastModifiedTime,UserEntity userEntity){
		Criteria criteria = createCriteria(AddressEntity.class);
		if(lastModifiedTime != null && lastModifiedTime > 0){
			criteria.add(Restrictions.gt(AddressEntity.LAST_MODIFIED_TIME, lastModifiedTime));
		}
		criteria.add(Restrictions.eq(AddressEntity.USER_ENTITY, userEntity));
		return criteria.list();
	}
	
	public void createAddressEntity(AddressEntity addressEntity){
		saveObject(addressEntity);
	}
	
	public void updateAddressEntity(AddressEntity addressEntity){
		updateObject(addressEntity);
	}
	
	
}
