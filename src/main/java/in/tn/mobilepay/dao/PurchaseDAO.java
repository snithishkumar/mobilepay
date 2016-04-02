package in.tn.mobilepay.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.response.model.LuggageJson;
import in.tn.mobilepay.util.MessageConstants;

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
	
	/**
	 * Get Current Purchase List
	 * @param serverDateTime
	 * @param userEntity
	 * @return
	 */
	public List<PurchaseEntity> gePurchaseList(long serverDateTime,UserEntity userEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, false));
		criteria.add(Restrictions.eq(PurchaseEntity.IS_DISCARD, false));
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
		if(serverDateTime > 0){
			criteria.add(Restrictions.gt(PurchaseEntity.SERVER_DATE_TIME, serverDateTime));
		}
		return criteria.list();
	}
	
	/**
	 * Get Purchase History List
	 * @param serverDateTime
	 * @param userEntity
	 * @return
	 */
	public List<PurchaseEntity> getPurchaseHistoryList(long serverDateTime,UserEntity userEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.IS_PAYED, true));
		criteria.add(Restrictions.or(Restrictions.eq(PurchaseEntity.ORDER_STATUS, MessageConstants.ORDER_STATUS[3]), Restrictions.eq(PurchaseEntity.ORDER_STATUS, MessageConstants.ORDER_STATUS[4])));
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
		if(serverDateTime > 0){
			criteria.add(Restrictions.gt(PurchaseEntity.SERVER_DATE_TIME, serverDateTime));
		}
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
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, MessageConstants.ORDER_STATUS[3]));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, MessageConstants.ORDER_STATUS[4]));
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
	}
	
	/**
	 * Get Luggage List
	 * @param serverDateTime
	 * @param userEntity
	 * @return
	 */
	public List<LuggageJson> getLuggageList(long startTime,long endTime,UserEntity userEntity){
		Criteria criteria =  createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.ge(PurchaseEntity.SERVER_DATE_TIME, startTime));
		criteria.add(Restrictions.le(PurchaseEntity.SERVER_DATE_TIME, endTime));
		defaultLuggFilter(userEntity, criteria);
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
		criteria.add(Restrictions.le(PurchaseEntity.SERVER_DATE_TIME, startTime));
		criteria.add(Restrictions.ge(PurchaseEntity.SERVER_DATE_TIME, endTime));
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

}
