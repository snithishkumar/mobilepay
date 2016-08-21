package in.tn.mobilepay.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import in.tn.mobilepay.dao.PurchaseDAO;
import in.tn.mobilepay.entity.CounterDetailsEntity;
import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.MerchantEntity;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.entity.TransactionalDetailsEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.enumeration.OrderStatus;
import in.tn.mobilepay.enumeration.PaymentStatus;
import in.tn.mobilepay.request.model.MerchantOrderStatusJson;
import in.tn.mobilepay.request.model.UnPayedMerchantPurchaseJson;
import in.tn.mobilepay.response.model.OrderStatusJson;

@Repository
public class PurchaseDAOImpl extends BaseDAOImpl implements PurchaseDAO{

	/**
	 * Get Current Purchase UUIDs List
	 * 
	 * @param serverDateTime
	 * @param userEntity
	 * @return
	 */
	public List<String> gePurchaseList(UserEntity userEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		applyUnPayedCriteria(criteria, userEntity);

		criteria.addOrder(Order.asc(PurchaseEntity.SERVER_DATE_TIME));
		criteria.setProjection(Projections.property(PurchaseEntity.PURCHASE_GUID));
		return criteria.list();
	}

	/**
	 * Returns List of PurchaseEntity based on purchaseGuids
	 * 
	 * @param purchaseGuids
	 * @param userEntity
	 * @return
	 */
	public List<PurchaseEntity> getPurchaseDetails(List<String> purchaseGuids, UserEntity userEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
		criteria.add(Restrictions.in(PurchaseEntity.PURCHASE_GUID, purchaseGuids));
		return criteria.list();
	}

	/**
	 * Get Purchase History UUIDs List
	 * 
	 * @param serverDateTime
	 * @param userEntity
	 * @return
	 */
	public List<String> getPurchaseHistoryList(UserEntity userEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.or(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELLED),
				Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED)));
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
		criteria.setProjection(Projections.property(PurchaseEntity.PURCHASE_GUID));
		criteria.addOrder(Order.asc(PurchaseEntity.SERVER_DATE_TIME));
		criteria.setMaxResults(25);
		return criteria.list();
	}

	/**
	 * Get Purchase Luggage List
	 * 
	 * @param serverDateTime
	 * @param userEntity
	 * @return
	 */
	public List<PurchaseEntity> getOrderStatusWithPurchaseList(UserEntity userEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		applyOrderStatusCriteria(criteria, userEntity);
		return criteria.list();
	}

	/**
	 * Get Luggage List
	 * 
	 * @param serverDateTime
	 * @param userEntity
	 * @return
	 */
	public List<OrderStatusJson> getOrderStatusList(long startTime, long endTime, UserEntity userEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.ge(PurchaseEntity.PURCHASE_DATE_TIME, startTime));
		criteria.add(Restrictions.le(PurchaseEntity.PURCHASE_DATE_TIME, endTime));

		applyOrderStatusCriteria(criteria, userEntity);

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property(PurchaseEntity.SERVER_DATE_TIME), PurchaseEntity.SERVER_DATE_TIME);
		projectionList.add(Projections.property(PurchaseEntity.PURCHASE_GUID), PurchaseEntity.PURCHASE_GUID);
		projectionList.add(Projections.property(PurchaseEntity.PURCHASE_ID), PurchaseEntity.PURCHASE_ID);
		projectionList.add(Projections.property(PurchaseEntity.ORDER_STATUS), PurchaseEntity.ORDER_STATUS);
		projectionList.add(Projections.property(PurchaseEntity.UPDATED_DATE_TIME), PurchaseEntity.UPDATED_DATE_TIME);
		criteria.setProjection(projectionList);
		criteria.setResultTransformer(Transformers.aliasToBean(OrderStatusJson.class));
		return criteria.list();
	}

	/**
	 * Get Purchase with Luggage List
	 * 
	 * @param startTime
	 * @param endTime
	 * @param userEntity
	 * @return
	 */
	public List<PurchaseEntity> getOrderStatusWithPurchaseList(long startTime, long endTime, UserEntity userEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.lt(PurchaseEntity.PURCHASE_DATE_TIME, startTime));
		criteria.add(Restrictions.gt(PurchaseEntity.PURCHASE_DATE_TIME, endTime));
		applyOrderStatusCriteria(criteria, userEntity);
		return criteria.list();
	}

	public void createDiscard(DiscardEntity discardEntity) {
		saveObject(discardEntity);
	}

	public void createPurchaseObject(PurchaseEntity purchaseEntity) {
		saveObject(purchaseEntity);
	}

	public void updatePurchaseObject(PurchaseEntity purchaseEntity) {
		updateObject(purchaseEntity);
	}

	public PurchaseEntity getPurchaseById(int purchaseId) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.PURCHASE_ID, purchaseId));
		return (PurchaseEntity) criteria.uniqueResult();
	}

	public PurchaseEntity getPurchaseEntity(String purchaseGuid) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.PURCHASE_GUID, purchaseGuid));
		return (PurchaseEntity) criteria.uniqueResult();
	}
	
	
	public PurchaseEntity getPurchaseEntity(String purchaseGuid, MerchantEntity merchantEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.PURCHASE_GUID, purchaseGuid));
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		return (PurchaseEntity) criteria.uniqueResult();
	}

	public PurchaseEntity getDiscardablePurchaseEntity(String purchaseGuid, MerchantEntity merchantEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.BILL_NUMBER, purchaseGuid));
		applyDiscardableCriteria(criteria, merchantEntity);
		return (PurchaseEntity) criteria.uniqueResult();
	}

	private void applyDiscardableCriteria(Criteria criteria, MerchantEntity merchantEntity) {
		applyUnPayedCriteria(criteria, merchantEntity);

	}

	private void applyOrderStatusCriteria(Criteria criteria, MerchantEntity merchantEntity) {
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		applyOrderStatusCriteria(criteria);
	}

	private void applyOrderStatusCriteria(Criteria criteria, UserEntity userEntity) {
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
		applyOrderStatusCriteria(criteria);
	}

	private void applyOrderStatusCriteria(Criteria criteria) {
		criteria.add(Restrictions.eq(PurchaseEntity.PAYMENT_STATUS, PaymentStatus.PAID));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELLED));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED));
	}

	public PurchaseEntity getOrderStatusPurchaseEntity(String purchaseGuid, MerchantEntity merchantEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.BILL_NUMBER, purchaseGuid));
		applyOrderStatusCriteria(criteria, merchantEntity);
		return (PurchaseEntity) criteria.uniqueResult();
	}

	public PurchaseEntity getNonDiscardPurchaseEntity(String purchaseGuid) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.PURCHASE_GUID, purchaseGuid));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS,  OrderStatus.CANCELLED));
		return (PurchaseEntity) criteria.uniqueResult();
	}

	public DiscardEntity getDiscardEntity(PurchaseEntity purchaseEntity) {
		Criteria criteria = createCriteria(DiscardEntity.class);
		criteria.add(Restrictions.eq(DiscardEntity.PURCHASE_ID, purchaseEntity));
		return (DiscardEntity) criteria.uniqueResult();
	}

	private void applyUnPayedCriteria(Criteria criteria, MerchantEntity merchantEntity) {
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		applyUnPayedCriteria(criteria);
	}

	private void applyUnPayedCriteria(Criteria criteria) {
		criteria.add(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.PURCHASE));
	}

	private void applyUnPayedCriteria(Criteria criteria, UserEntity userEntity) {
		criteria.add(Restrictions.eq(PurchaseEntity.USER_ID, userEntity));
		applyUnPayedCriteria(criteria);
	}

	/**
	 * Returns list of PurchaseEntity which are not payed.
	 * 
	 * @param merchantPurchaseJson
	 * @param merchantEntity
	 * @return
	 */
	public List<PurchaseEntity> getUnPayedPurchase(UnPayedMerchantPurchaseJson merchantPurchaseJson,
			MerchantEntity merchantEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		// Add Merchant Restriction
		applyUnPayedCriteria(criteria, merchantEntity);

		// If ServerSyncTime is > 0, then send after those record.
		if (merchantPurchaseJson.getServerSyncTime() > 0) {
			criteria.add(Restrictions.gt(PurchaseEntity.SERVER_DATE_TIME, merchantPurchaseJson.getServerSyncTime()));
		}
		// OffSet - Offset for lists of records
		if (merchantPurchaseJson.getOffSet() > 0) {
			criteria.setFirstResult(merchantPurchaseJson.getOffSet());
		}
		// Limit - Maximum number of items to return
		if (merchantPurchaseJson.getLimit() > 0) {
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
		applyUnPayedCriteria(criteria, merchantEntity);
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
	public long getUnPayedPurchaseCount(MerchantEntity merchantEntity, long serverSyncTime) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		applyUnPayedCriteria(criteria, merchantEntity);
		if (serverSyncTime > 0) {
			criteria.add(Restrictions.gt(PurchaseEntity.SERVER_DATE_TIME, serverSyncTime));
		}
		// set projection to be Purchase count
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}

	/**
	 * Returns list of PurchaseEntity which are payed and not cancel and not
	 * delivered.
	 * 
	 * @param merchantPurchaseJson
	 * @param merchantEntity
	 * @return
	 */
	public List<PurchaseEntity> getPurchaseOrderStatusList(MerchantOrderStatusJson merchantOrderStatusJson,
			MerchantEntity merchantEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);

		applyOrderStatusCriteria(criteria, merchantEntity);

		// If ServerSyncTime is > 0, then send after those record.
		if (merchantOrderStatusJson.getPurchaseDateTime() > 0) {
			criteria.add(
					Restrictions.gt(PurchaseEntity.PURCHASE_DATE_TIME, merchantOrderStatusJson.getPurchaseDateTime()));
		}
		// OffSet - Offset for lists of records
		if (merchantOrderStatusJson.getOffSet() > 0) {
			criteria.setFirstResult(merchantOrderStatusJson.getOffSet());
		}
		// Limit - Maximum number of items to return
		if (merchantOrderStatusJson.getLimit() > 0) {
			criteria.setMaxResults(merchantOrderStatusJson.getLimit());
		}
		criteria.addOrder(Order.asc(PurchaseEntity.PURCHASE_DATE_TIME));
		return criteria.list();
	}

	/**
	 * Returns list of PurchaseEntity which are payed and not cancel and not
	 * delivered.
	 * 
	 * @param merchantEntity
	 * @return
	 */
	public long getPurchaseOrderStatusListCount(MerchantEntity merchantEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		applyOrderStatusCriteria(criteria, merchantEntity);

		// set projection to be Purchase count
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}

	/**
	 * Returns list of PurchaseEntity which are payed and not cancel and not
	 * delivered.
	 * 
	 * @param merchantEntity
	 * @return
	 */
	public long getPurchaseOrderStatusListCount(MerchantEntity merchantEntity, long purchaseDateTime) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		applyOrderStatusCriteria(criteria, merchantEntity);
		if (purchaseDateTime > 0) {
			criteria.add(Restrictions.eq(PurchaseEntity.PURCHASE_DATE_TIME, purchaseDateTime));
		}
		// set projection to be Purchase count
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}

	/**
	 * Returns list of PurchaseEntity which are canceled or delivered.
	 * 
	 * @param merchantPurchaseJson
	 * @param merchantEntity
	 * @return
	 */
	public List<PurchaseEntity> getPurchaseHistoryList(UnPayedMerchantPurchaseJson merchantPurchaseJson,
			MerchantEntity merchantEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		// Add Merchant Restriction
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));

		// CANCELED or DELIVERED
		criteria.add(Restrictions.or(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELLED),
				Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED)));

		// If ServerSyncTime is > 0, then send after those record.
		if (merchantPurchaseJson.getServerSyncTime() > 0) {
			criteria.add(Restrictions.gt(PurchaseEntity.SERVER_DATE_TIME, merchantPurchaseJson.getServerSyncTime()));
		}
		// OffSet - Offset for lists of records
		if (merchantPurchaseJson.getOffSet() > 0) {
			criteria.setFirstResult(merchantPurchaseJson.getOffSet());
		}
		// Limit - Maximum number of items to return
		if (merchantPurchaseJson.getLimit() > 0) {
			criteria.setMaxResults(merchantPurchaseJson.getLimit());
		}
		criteria.addOrder(Order.asc(PurchaseEntity.SERVER_DATE_TIME));
		return criteria.list();
	}

	/**
	 * Returns Number of PurchaseEntity which are canceled or delivered.
	 * 
	 * @param merchantEntity
	 * @return
	 */
	public long getPurchaseHistoryListCount(MerchantEntity merchantEntity) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		// CANCELED or DELIVERED
		criteria.add(Restrictions.or(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELLED),
				Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED)));

		// set projection to be Purchase count
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}

	/**
	 * Returns Number of PurchaseEntity which are canceled or delivered.
	 * 
	 * @param merchantEntity
	 * @return
	 */
	public long getPurchaseHistoryListCount(MerchantEntity merchantEntity, long serverSyncTime) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		// CANCELED or DELIVERED
		criteria.add(Restrictions.or(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELLED),
				Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED)));

		if (serverSyncTime > 0) {
			criteria.add(Restrictions.gt(PurchaseEntity.SERVER_DATE_TIME, serverSyncTime));
		}
		// set projection to be Purchase count
		criteria.setProjection(Projections.rowCount());

		return (Long) criteria.uniqueResult();
	}

	/**
	 * Create Transaction
	 * 
	 * @param transactionalDetailsEntity
	 */
	public void createTransactions(TransactionalDetailsEntity transactionalDetailsEntity) {
		saveObject(transactionalDetailsEntity);
	}

	public void createCounterStatus(CounterDetailsEntity counterDetailsEntity) {
		saveObject(counterDetailsEntity);
	}

	public void updateCounterStatus(CounterDetailsEntity counterDetailsEntity) {
		updateCounterStatus(counterDetailsEntity);
	}
	
	private void applyMerchantRestCriteria(MerchantEntity merchantEntity, Integer index, Integer limit,
			 Long fromDate, Long toDate,Criteria criteria){
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		if (index != null && index > -1) {
			criteria.setFirstResult(index);
		}

		if (limit != null && limit > -1) {
			criteria.setMaxResults(limit);
		}

		if (fromDate != null && fromDate > 0) {
			criteria.add(Restrictions.ge(PurchaseEntity.PURCHASE_DATE_TIME, fromDate));
			if (toDate != null && toDate > 0 && fromDate > toDate) {
				toDate = 0L;
			}
		}

		if (toDate != null && toDate > 0) {
			criteria.add(Restrictions.le(PurchaseEntity.PURCHASE_DATE_TIME, fromDate));
		}
	}
	
	public List<PurchaseEntity> getUnPaiedList(MerchantEntity merchantEntity, Integer index, Integer limit, Long fromDate, Long toDate) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		applyMerchantRestCriteria(merchantEntity, index, limit, fromDate, toDate, criteria);
		criteria.add(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.PURCHASE));
		return criteria.list();
	}
	
	
	public List<PurchaseEntity> getPaiedList(MerchantEntity merchantEntity, Integer index, Integer limit, Long fromDate, Long toDate) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		applyMerchantRestCriteria(merchantEntity, index, limit, fromDate, toDate, criteria);
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELLED));
		criteria.add(Restrictions.ne(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED));
		criteria.add(Restrictions.eq(PurchaseEntity.PAYMENT_STATUS, PaymentStatus.PAID));
		return criteria.list();
	}
	
	
	public List<PurchaseEntity> getCancelledList(MerchantEntity merchantEntity, Integer index, Integer limit, Long fromDate, Long toDate) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		applyMerchantRestCriteria(merchantEntity, index, limit, fromDate, toDate, criteria);
		criteria.add(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELLED));
		return criteria.list();
	}
	
	
	public List<PurchaseEntity> getDeliveredList(MerchantEntity merchantEntity, Integer index, Integer limit, Long fromDate, Long toDate) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		applyMerchantRestCriteria(merchantEntity, index, limit, fromDate, toDate, criteria);
		criteria.add(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED));
		return criteria.list();
	}
	
	
	public List<PurchaseEntity> getHistoryList(MerchantEntity merchantEntity, Integer index, Integer limit, Long fromDate, Long toDate) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		applyMerchantRestCriteria(merchantEntity, index, limit, fromDate, toDate, criteria);
		criteria.add(Restrictions.or(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELLED),
				Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.DELIVERED)));
		return criteria.list();
	}
	
	
	public List<PurchaseEntity> getPurhcaseList(MerchantEntity merchantEntity,List<String> purchaseUUIDs) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		criteria.add(Restrictions.in(PurchaseEntity.PURCHASE_GUID, purchaseUUIDs));
		return criteria.list();
	}
	

	public List<PurchaseEntity> getPurchaseEntityList(MerchantEntity merchantEntity, Integer index, Integer limit,
			String status, Long fromDate, Long toDate) {
		Criteria criteria = createCriteria(PurchaseEntity.class);
		criteria.add(Restrictions.eq(PurchaseEntity.MERCHANT_ID, merchantEntity));
		if (index != null && index > -1) {
			criteria.setFirstResult(index);
		}

		if (limit != null && limit > -1) {
			criteria.setMaxResults(limit);
		}

		if (fromDate != null && fromDate > 0) {
			criteria.add(Restrictions.ge(PurchaseEntity.PURCHASE_DATE_TIME, fromDate));
			if (toDate != null && toDate > 0 && fromDate > toDate) {
				toDate = 0L;
			}
		}

		if (toDate != null && toDate > 0) {
			criteria.add(Restrictions.le(PurchaseEntity.PURCHASE_DATE_TIME, fromDate));
		}

		if (status != null && !status.trim().isEmpty()) {
			switch (status.toLowerCase()) {
			case "paid":
				criteria.add(Restrictions.eq(PurchaseEntity.PAYMENT_STATUS, PaymentStatus.PAID));
				break;
			case "unpaid":
				criteria.add(Restrictions.eq(PurchaseEntity.PAYMENT_STATUS, PaymentStatus.NOT_PAID));
				break;
			case "discard":
				criteria.add(Restrictions.eq(PurchaseEntity.ORDER_STATUS, OrderStatus.CANCELLED));
				break;

			default:
				break;
			}
		}

		return criteria.list();
	}

}
