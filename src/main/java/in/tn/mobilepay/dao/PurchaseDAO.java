package in.tn.mobilepay.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import in.tn.mobilepay.entity.DiscardEntity;
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
	
	
	public List<PurchaseEntity> gePurchaseList(long serverDateTime){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, false));
		if(serverDateTime > 0){
			criteria.add(Restrictions.gt(PurchaseEntity.SERVER_DATE_TIME, serverDateTime));
		}
		return criteria.list();
	}
	
	public void createDiscard(DiscardEntity discardEntity){
		saveObject(discardEntity);
	}
	
	
	public void createPurchaseObject(PurchaseEntity purchaseEntity){
		saveObject(purchaseEntity);
	}
	
	
	public void updatePurchaseObject(PurchaseEntity purchaseEntity){
		updateObject(purchaseEntity);
	}
	
	public PurchaseEntity getPurchaseById(int purchaseId){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.PURCHASE_ID, purchaseId));
		return (PurchaseEntity) criteria.uniqueResult();
	}
	
	public PurchaseEntity getPurchaseEntity(String purchaseGuid){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.PURCHASE_GUID, purchaseGuid));
		return (PurchaseEntity) criteria.uniqueResult();
	}

}
