package com.ccf.dao.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ccf.dao.ServiceOfferingDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.ServiceOffering;
import com.ccf.util.HibernateSessionFactory;

public class ServiceOfferingDaoImpl implements ServiceOfferingDao{

	final static Logger logger = Logger.getLogger(ServiceOfferingDaoImpl.class);
	
	@Override
	public void saveServiceOffering(ServiceOffering serviceOffering)
			throws CcfException {
		logger.info("saveServiceOffering method starts...");
		SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(serviceOffering);
		transaction.commit();
		session.close();
		logger.info("saveServiceOffering method ends...");
	}

	@Override
	public boolean isAlreadyExists(Date date, String time) {
		logger.info("isAlreadyExists method starts...");
		boolean isAlreadyExists = false;
		SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
		Session session = sessionFactory.openSession();
		String hql = "From ServiceOffering where date=:date and time=:time";
		Query query = session.createQuery(hql);
		query.setDate("date", date);
		query.setString("time", time);
		Object result = query.uniqueResult();
		if(result != null)
			isAlreadyExists = true;
		session.close();
		logger.info("isAlreadyExists method ends...");
		return isAlreadyExists;
	}

	

}
