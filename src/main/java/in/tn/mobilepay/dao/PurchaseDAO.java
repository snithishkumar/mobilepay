package in.tn.mobilepay.dao;

import java.util.List;

import in.tn.mobilepay.entity.CounterDetailsEntity;
import in.tn.mobilepay.entity.DiscardEntity;
import in.tn.mobilepay.entity.MerchantEntity;
import in.tn.mobilepay.entity.PurchaseEntity;
import in.tn.mobilepay.entity.TransactionalDetailsEntity;
import in.tn.mobilepay.entity.UserEntity;
import in.tn.mobilepay.request.model.MerchantOrderStatusJson;
import in.tn.mobilepay.request.model.UnPayedMerchantPurchaseJson;
import in.tn.mobilepay.response.model.OrderStatusJson;

public interface PurchaseDAO {

	List<String> gePurchaseList(UserEntity userEntity);

	List<PurchaseEntity> getPurchaseDetails(List<String> purchaseGuids, UserEntity userEntity);

	List<String> getPurchaseHistoryList(UserEntity userEntity);

	List<PurchaseEntity> getOrderStatusWithPurchaseList(UserEntity userEntity);

	List<OrderStatusJson> getOrderStatusList(long startTime, long endTime, UserEntity userEntity);

	List<PurchaseEntity> getOrderStatusWithPurchaseList(long startTime, long endTime, UserEntity userEntity);

	void createDiscard(DiscardEntity discardEntity);

	void createPurchaseObject(PurchaseEntity purchaseEntity);

	void updatePurchaseObject(PurchaseEntity purchaseEntity);

	PurchaseEntity getPurchaseById(int purchaseId);

	PurchaseEntity getPurchaseEntity(String purchaseGuid);

	PurchaseEntity getDiscardablePurchaseEntity(String purchaseGuid, MerchantEntity merchantEntity);

	PurchaseEntity getOrderStatusPurchaseEntity(String purchaseGuid, MerchantEntity merchantEntity);

	PurchaseEntity getNonDiscardPurchaseEntity(String purchaseGuid);

	DiscardEntity getDiscardEntity(PurchaseEntity purchaseEntity);

	List<PurchaseEntity> getUnPayedPurchase(UnPayedMerchantPurchaseJson merchantPurchaseJson,
			MerchantEntity merchantEntity);

	long getUnPayedPurchaseCount(MerchantEntity merchantEntity);

	long getUnPayedPurchaseCount(MerchantEntity merchantEntity, long serverSyncTime);

	List<PurchaseEntity> getPurchaseOrderStatusList(MerchantOrderStatusJson merchantOrderStatusJson,
			MerchantEntity merchantEntity);

	long getPurchaseOrderStatusListCount(MerchantEntity merchantEntity);

	long getPurchaseOrderStatusListCount(MerchantEntity merchantEntity, long purchaseDateTime);

	List<PurchaseEntity> getPurchaseHistoryList(UnPayedMerchantPurchaseJson merchantPurchaseJson,
			MerchantEntity merchantEntity);

	long getPurchaseHistoryListCount(MerchantEntity merchantEntity);

	long getPurchaseHistoryListCount(MerchantEntity merchantEntity, long serverSyncTime);

	void createTransactions(TransactionalDetailsEntity transactionalDetailsEntity);

	void createCounterStatus(CounterDetailsEntity counterDetailsEntity);

	void updateCounterStatus(CounterDetailsEntity counterDetailsEntity);

	List<PurchaseEntity> getPurchaseEntityList(MerchantEntity merchantEntity, Integer index, Integer limit,
			String status, Long fromDate, Long toDate);

	PurchaseEntity getPurchaseEntity(String purchaseGuid, MerchantEntity merchantEntity);

	List<PurchaseEntity> getUnPaiedList(MerchantEntity merchantEntity, Integer index, Integer limit, Long fromDate, Long toDate);

	List<PurchaseEntity> getPaiedList(MerchantEntity merchantEntity, Integer index, Integer limit, Long fromDate, Long toDate);

	List<PurchaseEntity> getCancelledList(MerchantEntity merchantEntity, Integer index, Integer limit, Long fromDate, Long toDate);

	List<PurchaseEntity> getDeliveredList(MerchantEntity merchantEntity, Integer index, Integer limit, Long fromDate, Long toDate);

	List<PurchaseEntity> getHistoryList(MerchantEntity merchantEntity, Integer index, Integer limit, Long fromDate, Long toDate);
	
	 List<PurchaseEntity> getPurhcaseList(MerchantEntity merchantEntity,List<String> purchaseUUIDs);
}
