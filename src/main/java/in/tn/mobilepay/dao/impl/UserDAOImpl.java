package in.tn.mobilepay.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import in.tn.mobilepay.entity.AddressEntity;
import in.tn.mobilepay.entity.CloudMessageEntity;
import in.tn.mobilepay.entity.OtpEntity;
import in.tn.mobilepay.entity.UserEntity;

@Repository
public class UserDAOImpl extends BaseDAOImpl{

	
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
	
	public UserEntity getUserEntity(int userId){
		Criteria criteria = createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq(UserEntity.USER_ID, userId));
		return (UserEntity) criteria.uniqueResult();
	}
	
	public UserEntity getUserEnity(String imeiNumber,String password){
		Criteria criteria = createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq(UserEntity.LOGIN_ID, password));
		criteria.add(Restrictions.eq(UserEntity.IMEI_NUMBER, imeiNumber));
		return (UserEntity) criteria.uniqueResult();
	}
	
	public Integer getUserEnityByToken(String clientToken,String serverToken){
		Criteria criteria = createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq(UserEntity.ACCESS_TOKEN, clientToken));
		criteria.add(Restrictions.eq(UserEntity.SERVER_TOKEN, serverToken));
		criteria.setProjection(Projections.property(UserEntity.USER_ID));
		return (Integer) criteria.uniqueResult();
	}
	
	
	
	public OtpEntity getOtpEntity(String mobileNumber){
		Criteria criteria = createCriteria(OtpEntity.class);
		criteria.add(Restrictions.eq(OtpEntity.MOBILE_NUMBER, mobileNumber));
		return (OtpEntity)criteria.uniqueResult();
	}
	
	public void updateOtpEntity(OtpEntity otpEntity){
		updateObject(otpEntity);
	}
	
	public void deleteOtpEntity(OtpEntity otpEntity){
		sessionFactory.getCurrentSession().delete(otpEntity);
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
	
	/**
	 * Get CloudMessageEntity by User
	 * @param userEntity
	 * @return
	 */
	public CloudMessageEntity getCloudMessageEntity(UserEntity userEntity){
		Criteria criteria = createCriteria(CloudMessageEntity.class);
		criteria.add(Restrictions.eq(CloudMessageEntity.USER_ENTITY, userEntity));
		return (CloudMessageEntity)criteria.uniqueResult();
	}
	
	/**
	 * Get CloudMessageEntity by Imei
	 * @param userEntity
	 * @return
	 */
	public CloudMessageEntity getCloudMessageEntity(UserEntity userEntity,String imeiNumber){
		Criteria criteria = createCriteria(CloudMessageEntity.class);
		criteria.add(Restrictions.ne(CloudMessageEntity.USER_ENTITY, userEntity));
		criteria.add(Restrictions.eq(CloudMessageEntity.IMEI_NUMBER, imeiNumber));
		return (CloudMessageEntity)criteria.uniqueResult();
	}
	
	
	/**
	 * Every device must only single record. so remove CloudMessageEntity
	 * @param cloudMessageEntity
	 */
	public void removeCloudMessageEntity(UserEntity userEntity,String imeiNumber){
		CloudMessageEntity cloudMessageEntity = getCloudMessageEntity(userEntity,imeiNumber);
		if(cloudMessageEntity != null){
			sessionFactory.getCurrentSession().delete(cloudMessageEntity);
		}
		
	}
	
	/**
	 * Create New CloudMessageEntity
	 * @param cloudMessageEntity
	 */
	public void saveCloudMessageEntity(CloudMessageEntity cloudMessageEntity){
		sessionFactory.getCurrentSession().save(cloudMessageEntity);
	}
	
	/**
	 * Update CloudMessageEntity
	 * @param cloudMessageEntity
	 */
	public void updateCloudMessageEntity(CloudMessageEntity cloudMessageEntity){
		sessionFactory.getCurrentSession().update(cloudMessageEntity);
	}
	
	
}
