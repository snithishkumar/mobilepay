package in.tn.mobilepay.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.MerchantEntity;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.request.model.MerchantOrderStatusJson;
import in.tn.mobilepay.request.model.UnPayedMerchantPurchaseJson;
import in.tn.mobilepay.response.model.LuggageJson;
import in.tn.mobilepay.util.MessageConstants;

@Repository
public class PurchaseDAO extends BaseDAO{
	
	/**
	 * Get List of Purchase 
	 * @param purchaseId
	 * @return
	 */
	public List<PurchaseEntity> gePurchase(int purchaseId){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, false));
		if(purchaseId > 0){
			criteria.add(Restrictions.gt(PurchaseEntity.PURCHASE_ID, purchaseId));
		}
		return criteria.list();
	}
	
	/**
	 * Get Current Purchase UUIDs List
	 * @param serverDateTime
	 * @param userEntity
	 * @return
	 */
	public List<String> gePurchaseList(UserEntity userEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, false));
		criteria.add(Restrictions.eq(PurchaseEntity.IS_DISCARD, false));
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
		criteria.addOrder(Order.asc(PurchaseEntity.SERVER_DATE_TIME));
		criteria.setProjection(Projections.property(PurchaseEntity.PURCHASE_GUID));
		return criteria.list();
	}
	
	
	/**
	 * Returns List of PurchaseEntity based on purchaseGuids
	 * @param purchaseGuids
	 * @param userEntity
	 * @return
	 */
	public List<PurchaseEntity> getPurchaseDetails(List<String> purchaseGuids,UserEntity userEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
		criteria.add(Restrictions.in(PurchaseEntity.PURCHASE_GUID, purchaseGuids));
		return criteria.list();
	}
	
	
	
	
	/**
	 * Get Purchase History UUIDs List
	 * @param serverDateTime
	 * @param userEntity
	 * @return
	 */
	public List<String> getPurchaseHistoryList(UserEntity userEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.or(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELED.toString()), Restrictions.eq(PurchaseEntity.ORDER_STATUS,OrderStatus.DELIVERED.toString())));
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
		criteria.setProjection(Projections.property(PurchaseEntity.PURCHASE_GUID));
		criteria.addOrder(Order.desc(PurchaseEntity.SERVER_DATE_TIME));
		criteria.setMaxResults(25);
		return criteria.list();
	}
	
	/**
	 * Get Purchase Luggage List
	 * @param serverDateTime
	 * @param userEntity
	 * @return
	 */
	public List<PurchaseEntity> getLuggageWithPurchaseList(UserEntity userEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		defaultLuggFilter(userEntity, criteria);
		return criteria.list();
	}
	
	private void defaultLuggFilter(UserEntity userEntity,Criteria criteria){
		
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, true));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELED.toString()));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED.toString()));
	}
	
	/**
	 * Get Luggage List
	 * @param serverDateTime
	 * @param userEntity
	 * @return
	 */
	public List<LuggageJson> getLuggageList(long startTime,long endTime,UserEntity userEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.ge(PurchaseEntity.PURCHASE_DATE_TIME, startTime));
		criteria.add(Restrictions.le(PurchaseEntity.PURCHASE_DATE_TIME, endTime));
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, true));
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property(PurchaseEntity.SERVER_DATE_TIME),PurchaseEntity.SERVER_DATE_TIME);
		projectionList.add(Projections.property(PurchaseEntity.PURCHASE_GUID),PurchaseEntity.PURCHASE_GUID);
		projectionList.add(Projections.property(PurchaseEntity.ORDER_STATUS),PurchaseEntity.ORDER_STATUS);
		projectionList.add(Projections.property(PurchaseEntity.UPDATED_DATE_TIME),PurchaseEntity.UPDATED_DATE_TIME);
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Transformers.aliasToBean(LuggageJson.class));
		return criteria.list();
	}
	
	/**
	 * Get Purchase with Luggage List
	 * @param startTime
	 * @param endTime
	 * @param userEntity
	 * @return
	 */
	public List<PurchaseEntity> getLuggageWithPurchaseList(long startTime,long endTime,UserEntity userEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.lt(PurchaseEntity.PURCHASE_DATE_TIME, startTime));
		criteria.add(Restrictions.gt(PurchaseEntity.PURCHASE_DATE_TIME, endTime));
		defaultLuggFilter(userEntity, criteria);
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
	
	public PurchaseEntity getPurchaseEntity(String purchaseGuid,MerchantEntity merchantEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.PURCHASE_GUID, purchaseGuid));
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, true));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELED.toString()));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED.toString()));
		return (PurchaseEntity) criteria.uniqueResult();
	}
	
	
	public PurchaseEntity getNonDiscardPurchaseEntity(String purchaseGuid){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.PURCHASE_GUID, purchaseGuid));
		criteria.add(Restrictions.eq(PurchaseEntity.IS_DISCARD, false));
		return (PurchaseEntity) criteria.uniqueResult();
	}
	
	
	public DiscardEntity getDiscardEntity(PurchaseEntity purchaseEntity){
		Criteria criteria =  createCriteria(DiscardEntity.class);
		criteria.add(Restrictions.eq(DiscardEntity.PURCHASE_ID, purchaseEntity));
		return (DiscardEntity) criteria.uniqueResult();
	}
	
	/**
	 * Returns list of PurchaseEntity which are not payed.
	 * @param merchantPurchaseJson
	 * @param merchantEntity
	 * @return
	 */
	public List<PurchaseEntity> getUnPayedPurchase(UnPayedMerchantPurchaseJson merchantPurchaseJson,MerchantEntity merchantEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		// Add Merchant Restriction
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, false));
		// If ServerSyncTime is > 0, then send after those record.
		if(merchantPurchaseJson.getServerSyncTime() > 0){
			criteria.add(Restrictions.gt(PurchaseEntity.SERVER_DATE_TIME, merchantPurchaseJson.getServerSyncTime()));
		}
		//OffSet -  Offset for lists of records
		if(merchantPurchaseJson.getOffSet() > 0){
			criteria.setFirstResult(merchantPurchaseJson.getOffSet());
		}
		//Limit - Maximum number of items to return
		if(merchantPurchaseJson.getLimit() > 0){
			criteria.setMaxResults(merchantPurchaseJson.getLimit());
		}
		criteria.addOrder(Order.asc(PurchaseEntity.SERVER_DATE_TIME));
		return criteria.list();
	}
	
	
	/**
	 * Returns Number of PurchaseEntity which are not payed.
	 * 
	 * @param merchantEntity
	 * @return
	 */
	public long getUnPayedPurchaseCount(MerchantEntity merchantEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, false));
		// set projection to be Purchase count
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}
	
	/**
	 * Returns Number of PurchaseEntity which are not payed.
	 * 
	 * @param merchantEntity
	 * @return
	 */
	public long getUnPayedPurchaseCount(MerchantEntity merchantEntity,long serverSyncTime) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, false));
		if(serverSyncTime > 0){
			criteria.add(Restrictions.gt(PurchaseEntity.SERVER_DATE_TIME, serverSyncTime));
		}
		// set projection to be Purchase count
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}
	
	
	
	/**
	 * Returns list of PurchaseEntity which are payed and not cancel and not delivered.
	 * @param merchantPurchaseJson
	 * @param merchantEntity
	 * @return
	 */
	public List<PurchaseEntity> getPurchaseOrderStatusList(MerchantOrderStatusJson merchantOrderStatusJson,MerchantEntity merchantEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		// Add Merchant Restriction
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, true));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELED.toString()));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED.toString()));
		
		// If ServerSyncTime is > 0, then send after those record.
		if(merchantOrderStatusJson.getPurchaseDateTime() > 0){
			criteria.add(Restrictions.gt(PurchaseEntity.PURCHASE_DATE_TIME, merchantOrderStatusJson.getPurchaseDateTime()));
		}
		//OffSet -  Offset for lists of records
		if(merchantOrderStatusJson.getOffSet() > 0){
			criteria.setFirstResult(merchantOrderStatusJson.getOffSet());
		}
		//Limit - Maximum number of items to return
		if(merchantOrderStatusJson.getLimit() > 0){
			criteria.setMaxResults(merchantOrderStatusJson.getLimit());
		}
		criteria.addOrder(Order.asc(PurchaseEntity.PURCHASE_DATE_TIME));
		return criteria.list();
	}
	
	
	/**
	 * Returns list of PurchaseEntity which are payed and not cancel and not delivered.
	 * 
	 * @param merchantEntity
	 * @return
	 */
	public long getPurchaseOrderStatusListCount(MerchantEntity merchantEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, true));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELED.toString()));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED.toString()));
		// set projection to be Purchase count
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}
	
	/**
	 * Returns list of PurchaseEntity which are payed and not cancel and not delivered.
	 * 
	 * @param merchantEntity
	 * @return
	 */
	public long getPurchaseOrderStatusListCount(MerchantEntity merchantEntity,long purchaseDateTime) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, true));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELED.toString()));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED.toString()));
		if(purchaseDateTime > 0){
			criteria.add(Restrictions.eq(PurchaseEntity.PURCHASE_DATE_TIME, purchaseDateTime));
		}
		// set projection to be Purchase count
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}
	
	
	/**
	 * Returns list of PurchaseEntity which are  canceled or  delivered.
	 * @param merchantPurchaseJson
	 * @param merchantEntity
	 * @return
	 */
	public List<PurchaseEntity> getPurchaseHistoryList(UnPayedMerchantPurchaseJson merchantPurchaseJson,MerchantEntity merchantEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		// Add Merchant Restriction
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		
		//CANCELED or DELIVERED
		criteria.add(Restrictions.or(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELED.toString()), Restrictions.eq(PurchaseEntity.ORDER_STATUS,OrderStatus.DELIVERED.toString())));
		
		// If ServerSyncTime is > 0, then send after those record.
		if(merchantPurchaseJson.getServerSyncTime() > 0){
			criteria.add(Restrictions.gt(PurchaseEntity.PURCHASE_DATE_TIME, merchantPurchaseJson.getServerSyncTime()));
		}
		//OffSet -  Offset for lists of records
		if(merchantPurchaseJson.getOffSet() > 0){
			criteria.setFirstResult(merchantPurchaseJson.getOffSet());
		}
		//Limit - Maximum number of items to return
		if(merchantPurchaseJson.getLimit() > 0){
			criteria.setMaxResults(merchantPurchaseJson.getLimit());
		}
		criteria.addOrder(Order.asc(PurchaseEntity.SERVER_DATE_TIME));
		return criteria.list();
	}
	
	
	/**
	 * Returns Number of PurchaseEntity which are  canceled or  delivered.
	 * 
	 * @param merchantEntity
	 * @return
	 */
	public long getPurchaseHistoryListCount(MerchantEntity merchantEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		//CANCELED or DELIVERED
		criteria.add(Restrictions.or(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELED.toString()), Restrictions.eq(PurchaseEntity.ORDER_STATUS,OrderStatus.DELIVERED.toString())));
				
		// set projection to be Purchase count
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}
	
	
	/**
	 * Returns Number of PurchaseEntity which are  canceled or  delivered.
	 * 
	 * @param merchantEntity
	 * @return
	 */
	public long getPurchaseHistoryListCount(MerchantEntity merchantEntity,long serverSyncTime) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		//CANCELED or DELIVERED
		criteria.add(Restrictions.or(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELED.toString()), Restrictions.eq(PurchaseEntity.ORDER_STATUS,OrderStatus.DELIVERED.toString())));
				
		if(serverSyncTime > 0){
			criteria.add(Restrictions.gt(PurchaseEntity.SERVER_DATE_TIME, serverSyncTime));
		}
		// set projection to be Purchase count
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}
	
	
	
	

}
