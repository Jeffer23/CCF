package com.ccf.dao.impl;

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
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.ccf.doa.FamilyDao;
import com.ccf.doa.MemberDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Family;
import com.ccf.persistence.classes.GraveyardAccount;
import com.ccf.persistence.classes.Member;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.PrimarySchoolAccount;
import com.ccf.persistence.classes.Santha;
import com.ccf.persistence.classes.ServiceOffering;
import com.ccf.persistence.classes.SpecialThanksOfferingAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.util.HibernateSessionFactory;

public class MemberDaoImpl implements MemberDao {

	final static Logger logger = Logger.getLogger(MemberDaoImpl.class);

	@Override
	public void addMember(Member member) throws CcfException {
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.save(member);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public void updateMember(Member member) throws CcfException {
		try {
			logger.info("Member Id : " + member.getId());
			logger.info("Member Name : " + member.getName());
			logger.info("Member DOB : " + member.getDob());
			logger.info("Member Eligibility : " + member.getEligibility());
			logger.info("Member Lived Till : " + member.getLivedTill());
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String hql = "UPDATE Member set name = :name, dob = :dob, eligibility = :eligibility, lived_till = :livedTill, subscription_amount = :subscriptionAmount "
					+ "WHERE id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("name", member.getName());
			query.setParameter("dob", member.getDob());
			query.setParameter("eligibility", member.getEligibility());
			query.setParameter("livedTill", member.getLivedTill());
			query.setParameter("id", member.getId());
			query.setParameter("subscriptionAmount",
					member.getSubscriptionAmount());
			int result = query.executeUpdate();
			System.out.println("Update Status : " + result);
			transaction.commit();
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public void deleteMember(Member member) throws CcfException {
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.delete(member);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public List<Member> getMembers(int familyNo) throws CcfException {
		try {
			logger.info("Family No : " + familyNo);
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			/*
			 * List<Member> members = session.createQuery(
			 * "from Member where no=" + familyNo).list();
			 */
			List<Member> members = session.createCriteria(Member.class)
					.add(Restrictions.eq("family.no", familyNo)).list();
			transaction.commit();
			session.close();
			return members;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<String> getMemberNames(int familyNo) throws CcfException {
		try {
			logger.info("Family No : " + familyNo);
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			List<String> members = session.createQuery(
					"select name from Member where no=" + familyNo
							+ " and eligibility='yes'").list();
			transaction.commit();
			session.close();
			return members;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public int getMemberId(String memberName, int familyNo) throws CcfException {
		try {
			logger.info("Family No : " + familyNo);
			logger.info("Member Name : " + memberName);
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			int memberId = (int) session.createQuery(
					"select id from Member where no=" + familyNo
							+ " and name='" + memberName + "'").uniqueResult();
			transaction.commit();
			session.close();
			return memberId;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public Member getMember(int memberId) throws CcfException {
		try {
			logger.info("Member Id : " + memberId);
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Criteria cr = session.createCriteria(Member.class);
			cr.add(Restrictions.eq("id", memberId));
			Member member = (Member) cr.uniqueResult();
			transaction.commit();
			session.close();
			return member;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public float getSubscriptionAmount(int familyNo, String memberName)
			throws CcfException {
		try {
			logger.info("Family No : " + familyNo);
			logger.info("Member Name : " + memberName);
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			float subscriptionAmount = (float) session.createQuery(
					"select subscriptionAmount from Member where name='"
							+ memberName + "' and no=" + familyNo)
					.uniqueResult();
			transaction.commit();
			session.close();
			return subscriptionAmount;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<Member> getBirthdayMembers(Date fromDate, Date toDate,
			Session session) throws CcfException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			logger.info("From Date : " + fromDate);
			logger.info("To Date : " + toDate);
			Transaction transaction = session.beginTransaction();
			List<Member> birthdayMembers = session.createQuery(
					"FROM Member WHERE DATE_FORMAT(dob, '%c-%d') BETWEEN DATE_FORMAT('"
							+ sdf.format(fromDate)
							+ "', '%c-%d')  AND DATE_FORMAT('"
							+ sdf.format(toDate)
							+ "', '%c-%d') AND lived_till is null").list();
			transaction.commit();
			return birthdayMembers;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<Member> getNonPaidMember(Date fromDate, Date toDate,
			Session session) throws CcfException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			logger.info("From Date : " + fromDate);
			logger.info("To Date : " + toDate);
			Transaction transaction = session.beginTransaction();
			List<Member> members = session.createQuery(
					"from Member where eligibility='yes'").list();
			List<com.ccf.persistence.classes.Santha> santhas = session
					.createQuery(
							"from Santha where paidForDate>='"
									+ sdf.format(fromDate)
									+ "' and paidForDate<='"
									+ sdf.format(toDate) + "'").list();
			for (com.ccf.persistence.classes.Santha santha : santhas) {
				if (members.contains(santha.getMember())) {
					members.remove(santha.getMember());
				}
			}
			transaction.commit();
			return members;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	public static void main(String[] args) {
		try {
			
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			
			Santha santha = (Santha) session.load(Santha.class, 1);
			
			ServiceOffering so = new ServiceOffering();
			so.setDate(new Date());
			so.setTime("Test");
			
			
			SpecialThanksOfferingAccount ma = new SpecialThanksOfferingAccount();
			ma.setAmount(34);
			ma.setBalance(34);
			ma.setCr_dr("cr");
			ma.setDescription("Test Data");
			ma.setServiceOffering(so);
			ma.setSantha(santha);
			
			so.getSpecialThanksOfferingAccounts().add(ma);
			santha.getSpecialThanksOfferingAccounts().add(ma);
			//so.setSundaySchoolAccount(ma);
			
			
			session.save(so);
			//session.save(pc);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
