package com.ccf.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ccf.controller.FamilyController;
import com.ccf.doa.FamilyDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Family;
import com.ccf.util.HibernateSessionFactory;

public class FamilyDaoImpl implements FamilyDao {

	final static Logger logger = Logger.getLogger(FamilyDaoImpl.class);

	@Override
	public void saveFamily(Family family) throws CcfException {
		try {
			logger.info("Family No : " + family.getNo());
			logger.info("Family address : " + family.getAddress());
			logger.info("Family Phone No : " + family.getPhoneNo());
			SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(family);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public void updateFamily(Family family) throws CcfException {
		try {
			logger.info("Family No : " + family.getNo());
			logger.info("Family address : " + family.getAddress());
			logger.info("Family Phone No : " + family.getPhoneNo());
			SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String hql = "UPDATE Family set address = :address, phoneNo = :phoneNo "
					+ "WHERE no = :no";
			Query query = session.createQuery(hql);
			query.setParameter("address", family.getAddress());
			query.setParameter("phoneNo", family.getPhoneNo());
			query.setParameter("no", family.getNo());
			int result = query.executeUpdate();
			System.out.println("Update Status : " + result);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}

		
		
	}

	@Override
	public boolean isFamilyExists(int familyNo) throws CcfException {
		try {
			logger.info("Family No : " + familyNo);
			SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Criteria cr = session.createCriteria(Family.class);
			cr.add(Restrictions.eq("no", familyNo));
			Family family = (Family) cr.uniqueResult();
			transaction.commit();
			session.close();
			if (family == null)
				return false;
			else
				return true;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<Integer> getFamilyNos() throws CcfException {
		try {
			SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction tnx = session.beginTransaction();
			List<Integer> familyNos = session.createQuery(
					"select no from Family").list();
			tnx.commit();
			session.close();
			return familyNos;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<Integer> getFilteredFamilyNos(int familyNosStartsWith)
			throws CcfException {
		try {
			logger.info("Family No Starts With : " + familyNosStartsWith);
			SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction tnx = session.beginTransaction();
			List<Integer> familyNos = session.createQuery(
					"select no from Family where no like '"
							+ familyNosStartsWith + "%'").list();
			tnx.commit();
			session.close();
			return familyNos;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public Family getFamily(int familyNo) throws CcfException {
		try {
			logger.info("Family No : " + familyNo);
			SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction tnx = session.beginTransaction();
			Criteria cr = session.createCriteria(Family.class);
			cr.add(Restrictions.eq("no", familyNo));
			Family family = (Family) cr.uniqueResult();
			tnx.commit();
			session.close();
			return family;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

}
