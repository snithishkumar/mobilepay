package in.tn.mobilepay.dao;

import in.tn.mobilepay.entity.CounterDetailsEntity;
import in.tn.mobilepay.entity.DeliveryDetailsEntity;
import in.tn.mobilepay.entity.PurchaseEntity;

public interface DeliveryDAO {

	/** Get Delivery Entity based on PurchaseEntity **/
	DeliveryDetailsEntity getDeliveryDetailsEntity(PurchaseEntity purchaseEntity);

	/** Create a Delivery Record **/
	void createDeliveryDetails(DeliveryDetailsEntity deliveryDetailsEntity);

	/** Update already existing record **/
	void updateDeliveryDetails(DeliveryDetailsEntity deliveryDetailsEntity);

	/** Get CounterDetails based on PurchaseId **/
	CounterDetailsEntity geCounterDetailsEntity(int purchaseId);

}
