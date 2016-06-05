package com.ccf.dao.impl;

import java.awt.image.RescaleOp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ccf.doa.SanthaDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Family;
import com.ccf.persistence.classes.Member;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.Santha;
import com.ccf.util.HibernateSessionFactory;

public class SanthaDaoImpl implements SanthaDao {

	final static Logger logger = Logger.getLogger(SanthaDaoImpl.class);

	@Override
	public int paySantha(Santha santha) throws CcfException {
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			int key = (int) session.save(santha);
			transaction.commit();
			session.close();
			return key;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<Santha> getPaidMembers(int familyNo, Date fromDate,
			Date toDate, Session session) throws CcfException {
		try {
			logger.info("Family No : " + familyNo);
			logger.info("From Date : " + fromDate);
			logger.info("To Date : " + toDate);
			Transaction transaction = session.beginTransaction();

			List<Santha> paidMembers = session
					.createCriteria(Santha.class)
					.add(Restrictions.and(Restrictions.and(
							Restrictions.ge("paidForDate", fromDate),
							Restrictions.le("paidForDate", toDate)),
							Restrictions.in(
									"member",
									session.createCriteria(Member.class)
											.add(Restrictions.eq("family.no",
													familyNo)).list()))).list();
			transaction.commit();

			return paidMembers;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public void updateSantha(Santha santha) throws CcfException {
		try {
			logger.info("bagOffer" + santha.getBagOffer());
			logger.info("churchRenovation" + santha.getChurchRenovation());
			logger.info("educationHelp" + santha.getEducationHelp());
			logger.info("graveyard" + santha.getGraveyard());
			logger.info("harvestFestival" + santha.getHarvestFestival());
			logger.info("mensFellowship" + santha.getMensFellowship());
			logger.info("missionary" + santha.getMissionary());
			logger.info("other1" + santha.getOther1());
			logger.info("other2" + santha.getOther2());
			logger.info("poorHelp" + santha.getPoorHelp());
			logger.info("primarySchool" + santha.getPrimarySchool());
			logger.info("sto" + santha.getSto());
			logger.info("thanksOffer" + santha.getThanksOffer());
			logger.info("womensFellowship" + santha.getWomensFellowship());
			logger.info("youth" + santha.getYouth());
			logger.info("total" + santha.getTotal());
			logger.info("santhaId" + santha.getSanthaId());

			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String hql = "UPDATE Santha set bagOffer = :bagOffer, churchRenovation = :churchRenovation , educationHelp = :educationHelp, graveyard = :graveyard, harvestFestival = :harvestFestival, mensFellowship = :mensFellowship, missionary = :missionary, other1 = :other1, other2 = :other2, poorHelp = :poorHelp, primarySchool = :primarySchool, sto = :sto, thanksOffer = :thanksOffer, womensFellowship = :womensFellowship, youth = :youth, total = :total "
					+ "WHERE santhaId = :santhaId";
			Query query = session.createQuery(hql);
			query.setParameter("bagOffer", santha.getBagOffer());
			query.setParameter("churchRenovation", santha.getChurchRenovation());
			query.setParameter("educationHelp", santha.getEducationHelp());
			query.setParameter("graveyard", santha.getGraveyard());
			query.setParameter("harvestFestival", santha.getHarvestFestival());
			query.setParameter("mensFellowship", santha.getMensFellowship());
			query.setParameter("missionary", santha.getMissionary());
			query.setParameter("other1", santha.getOther1());
			query.setParameter("other2", santha.getOther2());
			query.setParameter("poorHelp", santha.getPoorHelp());
			query.setParameter("primarySchool", santha.getPrimarySchool());
			query.setParameter("sto", santha.getSto());
			query.setParameter("thanksOffer", santha.getThanksOffer());
			query.setParameter("womensFellowship", santha.getWomensFellowship());
			query.setParameter("youth", santha.getYouth());
			query.setParameter("total", santha.getTotal());
			query.setParameter("santhaId", santha.getSanthaId());

			int result = query.executeUpdate();
			System.out.println("Update Status : " + result);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public void deleteSantha(Santha santha) throws CcfException {
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.delete(santha);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public Santha getLastPaidAmount(int familyNo, String memberName,
			Date fromDate, Date toDate) throws CcfException {
		try {
			logger.info("Family No : " + familyNo);
			logger.info("Member Name : " + memberName);
			logger.info("From Date : " + fromDate);
			logger.info("To Date : " + toDate);
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Santha lastPaidAmount = (Santha) session
					.createCriteria(Santha.class)
					.add(Restrictions.and(Restrictions.and(
							Restrictions.ge("paidForDate", fromDate),
							Restrictions.le("paidForDate", toDate)),
							Restrictions.eq(
									"member",
									session.createCriteria(Member.class)
											.add(Restrictions.eq("name",
													memberName))
											.add(Restrictions.eq("family.no",
													familyNo)).uniqueResult())))
					.uniqueResult();
			transaction.commit();
			session.close();
			return lastPaidAmount;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<Santha> getReport(Date fromDate, Date toDate, Session session)
			throws CcfException {
		List<Santha> santhas = null;
		try {
			logger.info("From Date : " + fromDate);
			logger.info("To Date : " + toDate);
			Transaction transaction = session.beginTransaction();
			Criteria cr = session.createCriteria(Santha.class);

			cr.add(Restrictions.ge("paidForDate", fromDate));
			cr.add(Restrictions.le("paidForDate", toDate));
			santhas = cr.list();
			transaction.commit();

			return santhas;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public Santha getSantha(int santhaId) throws CcfException {
		logger.debug("Santha Id : " + santhaId);
		Santha santha = null;
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From Santha where santhaId=:santhaId";
			Query query = session.createQuery(hql);
			query.setInteger("santhaId", santhaId);
			Object result = query.uniqueResult();
			if (result == null) {
				throw new CcfException(
						"Something went wrong - Santha record for Id "
								+ santhaId + " not available");
			}
			santha = (Santha) result;
			session.close();
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
		return santha;
	}

	
}
