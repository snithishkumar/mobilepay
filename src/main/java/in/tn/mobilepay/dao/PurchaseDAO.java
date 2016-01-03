package in.tn.mobilepay.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import in.tn.mobilepay.entity.PurchaseEntity;

@Repository
public class PurchaseDAO extends BaseDAO{
	
	public List<PurchaseEntity> gePurchase(int purchaseId){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, false));
		if(purchaseId > 0){
			criteria.add(Restrictions.gt(PurchaseEntity.PURCHASE_ID, purchaseId));
		}
		return criteria.list();
	}
	
	
	public void updatePurchaseObject(PurchaseEntity purchaseEntity){
		updateObject(purchaseEntity);
	}
	
	public PurchaseEntity getPurchaseById(int purchaseId){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.PURCHASE_ID, purchaseId));
		return (PurchaseEntity) criteria.uniqueResult();
	}

}
