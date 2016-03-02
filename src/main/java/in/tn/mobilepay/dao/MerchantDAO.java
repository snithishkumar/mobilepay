package in.tn.mobilepay.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import in.tn.mobilepay.entity.MerchantEntity;

@Repository
public class MerchantDAO extends BaseDAO{
	
	public void createMerchant(MerchantEntity merchantEntity){
		saveObject(merchantEntity);
	}
	
	public void updateMerchant(MerchantEntity merchantEntity){
		updateObject(merchantEntity);
	}
	
	public MerchantEntity getMerchant(long mobileNumber){
		Criteria criteria = createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.eq(MerchantEntity.MOBILE_NUMBER, mobileNumber));
		return (MerchantEntity)criteria.uniqueResult();
	}
	
	
	public MerchantEntity getMerchant(String merchantToken,String serverToken){
		Criteria criteria = createCriteria(MerchantEntity.class);
		criteria.add(Restrictions.eq(MerchantEntity.MERCHANT_TOKEN, merchantToken));
		criteria.add(Restrictions.eq(MerchantEntity.SERVER_TOKEN, serverToken));
		return (MerchantEntity)criteria.uniqueResult();
	}

}
