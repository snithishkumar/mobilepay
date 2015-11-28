package in.tn.mobilepay.dao;

import in.tn.mobilepay.entity.BankDetailsEntity;
import in.tn.mobilepay.entity.CardDetailsEntity;
import in.tn.mobilepay.entity.UserEntity;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CardDAO extends BaseDAO {

	public void createCard(CardDetailsEntity cardDetailsEntity) {
		saveObject(cardDetailsEntity);
	}

	public List<CardDetailsEntity> getCardList(UserEntity userEntity) {
		Criteria criteria = createCriteria(CardDetailsEntity.class);
		criteria.add(Restrictions.eq(CardDetailsEntity.USER_ID, userEntity));
		criteria.add(Restrictions.eq(CardDetailsEntity.IS_ACTIVE, true));
		return criteria.list();
	}

	public boolean isCardPresent(UserEntity userEntity,
			BankDetailsEntity bankDetailsEntity) {
		Criteria criteria = createCriteria(CardDetailsEntity.class);
		criteria.add(Restrictions.eq(CardDetailsEntity.USER_ID, userEntity));
		criteria.add(Restrictions.eq(CardDetailsEntity.BANK_ID,bankDetailsEntity));
		criteria.add(Restrictions.eq(CardDetailsEntity.IS_ACTIVE, true));
		criteria.setProjection(Projections.count(CardDetailsEntity.CARD_DETAILS_ID));
		Integer integer = (Integer) criteria.list().get(0);
		return integer > 0 ? true : false;
	}

	public void createBanks(BankDetailsEntity bankDetailsEntity) {
		saveObject(bankDetailsEntity);
	}
	
	public void updateBanks(BankDetailsEntity bankDetailsEntity){
		updateBanks(bankDetailsEntity);
	}

	public BankDetailsEntity getBankDetail(String bankGuid) {
		Criteria criteria = createCriteria(BankDetailsEntity.class);
		criteria.add(Restrictions.eq(BankDetailsEntity.BANK_GUID, bankGuid));
		return (BankDetailsEntity) criteria.uniqueResult();
	}

	public BankDetailsEntity getBankDetail(int bankId) {
		Criteria criteria = createCriteria(BankDetailsEntity.class);
		criteria.add(Restrictions.eq(BankDetailsEntity.BANK_DETAILS_ID, bankId));
		return (BankDetailsEntity) criteria.uniqueResult();
	}

	public List<BankDetailsEntity> getBankDetailList() {
		Criteria criteria = createCriteria(BankDetailsEntity.class);
		criteria.add(Restrictions.eq(BankDetailsEntity.IS_ACTIVE, true));
		return criteria.list();
	}
	
	

}
