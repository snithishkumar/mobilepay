package in.tn.mobilepay.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import in.tn.mobilepay.dao.DeliveryDAO;
import in.tn.mobilepay.entity.CounterDetailsEntity;
import in.tn.mobilepay.entity.DeliveryDetailsEntity;
import in.tn.mobilepay.entity.PurchaseEntity;

@Repository
public class DeliveryDAOImpl extends BaseDAOImpl implements DeliveryDAO{
	
	/**
	 * Get Delivery Entity based on PurchaseEntity
	 * @param purchaseEntity
	 * @return
	 */
	public DeliveryDetailsEntity getDeliveryDetailsEntity(PurchaseEntity purchaseEntity){
		Criteria deliveryCriteria = createCriteria(DeliveryDetailsEntity.class);
		deliveryCriteria.add(Restrictions.eq(DeliveryDetailsEntity.PURCHASE_ENTITY, purchaseEntity));
		return (DeliveryDetailsEntity)deliveryCriteria.uniqueResult();
	}
	
	public void createDeliveryDetails(DeliveryDetailsEntity deliveryDetailsEntity){
		saveObject(deliveryDetailsEntity);
	}
	
	public void updateDeliveryDetails(DeliveryDetailsEntity deliveryDetailsEntity){
		updateObject(deliveryDetailsEntity);
	}
	
	
	/**
	 * Get Counter Details by PurchaseId
	 * @param purchaseId
	 * @return
	 */
	public CounterDetailsEntity geCounterDetailsEntity(int purchaseId){
		Criteria counterDetails = createCriteria(CounterDetailsEntity.class);
		counterDetails.createAlias(CounterDetailsEntity.PURCHASE_ID, CounterDetailsEntity.PURCHASE_ID);
		counterDetails.add(Restrictions.eqOrIsNull(appendAlias(CounterDetailsEntity.PURCHASE_ID, PurchaseEntity.PURCHASE_ID), purchaseId));
		return (CounterDetailsEntity)counterDetails.uniqueResult();
	}

}
