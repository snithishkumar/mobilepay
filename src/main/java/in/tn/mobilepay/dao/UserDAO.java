package in.tn.mobilepay.dao;

import in.tn.mobilepay.entity.UserEntity;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends BaseDAO{

	
	public void createUser(UserEntity userEntity){
		saveObject(userEntity);
	}
	
	public UserEntity getUserEntity(String mobileNumber){
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserEntity.class);
		criteria.add(Restrictions.eq(UserEntity.MOBILE_NUMBER, mobileNumber));
		return (UserEntity) criteria.uniqueResult();
	}
}
