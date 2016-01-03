package in.tn.mobilepay.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import in.tn.mobilepay.entity.UserEntity;

@Repository
public class UserDAO extends BaseDAO{

	
	public void createUser(UserEntity userEntity){
		saveObject(userEntity);
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
	
	
}
