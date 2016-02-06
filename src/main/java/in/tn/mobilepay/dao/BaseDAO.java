package in.tn.mobilepay.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDAO {

	@Autowired
	protected SessionFactory sessionFactory;
	
	
	protected void saveObject(Object object){
		sessionFactory.getCurrentSession().save(object);
	}
	
	protected void updateObject(Object object){
		sessionFactory.getCurrentSession().update(object);
	}
	
	protected void batchInsert(List<Object> objects){
		for(Object object : objects){
			saveObject(object);
		}
	}
	
	protected Criteria createCriteria(Class className) {
		return sessionFactory.getCurrentSession().createCriteria(className);
	}
	
	protected String appendAlias(String name1,String name2){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name1);
		stringBuilder.append(".");
		stringBuilder.append(name2);
		return stringBuilder.toString();
	}
}
