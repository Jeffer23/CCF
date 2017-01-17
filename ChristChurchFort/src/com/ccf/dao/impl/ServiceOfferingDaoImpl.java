package com.ccf.dao.impl;

import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ccf.dao.ServiceOfferingDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.AccountsBalance;
import com.ccf.persistence.classes.ServiceOffering;
import com.ccf.persistence.interfaces.IMissionaryAccount;
import com.ccf.persistence.interfaces.IPCAccount;
import com.ccf.persistence.interfaces.ISpecialThanksOfferingAccount;
import com.ccf.persistence.interfaces.ISundaySchoolAccount;
import com.ccf.util.AccountNames;
import com.ccf.util.HibernateSessionFactory;

public class ServiceOfferingDaoImpl implements ServiceOfferingDao {

	final static Logger logger = Logger.getLogger(ServiceOfferingDaoImpl.class);

	@Override
	public void saveServiceOffering(ServiceOffering serviceOffering)
			throws CcfException {
		logger.info("saveServiceOffering method starts...");
		SessionFactory sessionFactory = HibernateSessionFactory
				.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(serviceOffering);

		AccountsBalance accountBalance = null;
		// PCAccount
		if (serviceOffering.getPcAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, AccountNames.PCAccount);
			Set<IPCAccount> pcAccounts = serviceOffering.getPcAccounts();
			for (IPCAccount account : pcAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Bank PC Account
		if (serviceOffering.getBankPCAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, AccountNames.BankPCAccount);
			Set<IPCAccount> pcAccounts = serviceOffering.getBankPCAccounts();
			for (IPCAccount account : pcAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Missionary Account
		if (serviceOffering.getMissionaryAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, AccountNames.MissionaryAccount);
			Set<IMissionaryAccount> missionaryAccounts = serviceOffering
					.getMissionaryAccounts();
			for (IMissionaryAccount account : missionaryAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Bank Missionary Account
		if (serviceOffering.getBankMissionaryAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, AccountNames.BankMissionaryAccount);
			Set<IMissionaryAccount> missionaryAccounts = serviceOffering
					.getBankMissionaryAccounts();
			for (IMissionaryAccount account : missionaryAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Sunday School Account
		if (serviceOffering.getSundaySchoolAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, AccountNames.SundaySchoolAccount);
			Set<ISundaySchoolAccount> sundaySchoolAccounts = serviceOffering
					.getSundaySchoolAccounts();
			for (ISundaySchoolAccount account : sundaySchoolAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Bank Sunday School Account
		if (serviceOffering.getBankSundaySchoolAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session
					.get(AccountsBalance.class,
							AccountNames.BankSundaySchoolAccount);
			Set<ISundaySchoolAccount> sundaySchoolAccounts = serviceOffering
					.getBankSundaySchoolAccounts();
			for (ISundaySchoolAccount account : sundaySchoolAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Special Thanks offering account
		if (serviceOffering.getSpecialThanksOfferingAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, AccountNames.STOAccount);
			Set<ISpecialThanksOfferingAccount> stoAccounts = serviceOffering
					.getSpecialThanksOfferingAccounts();
			for (ISpecialThanksOfferingAccount account : stoAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Bank Special Thanks offering account
		if (serviceOffering.getBankSpecialThanksOfferingAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, AccountNames.BankSTOAccount);
			Set<ISpecialThanksOfferingAccount> stoAccounts = serviceOffering
					.getBankSpecialThanksOfferingAccounts();
			for (ISpecialThanksOfferingAccount account : stoAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		transaction.commit();
		session.close();
		logger.info("saveServiceOffering method ends...");
	}

	@Override
	public boolean isAlreadyExists(Date date, String time) {
		logger.info("isAlreadyExists method starts...");
		boolean isAlreadyExists = false;
		SessionFactory sessionFactory = HibernateSessionFactory
				.getSessionFactory();
		Session session = sessionFactory.openSession();
		String hql = "From ServiceOffering where date=:date and time=:time";
		Query query = session.createQuery(hql);
		query.setDate("date", date);
		query.setString("time", time);
		Object result = query.uniqueResult();
		if (result != null)
			isAlreadyExists = true;
		session.close();
		logger.info("isAlreadyExists method ends...");
		return isAlreadyExists;
	}

}
