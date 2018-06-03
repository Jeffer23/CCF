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
import com.ccf.persistence.classes.BankMissionaryAccount;
import com.ccf.persistence.classes.BankPCAccount;
import com.ccf.persistence.classes.BankBuildingAccount;
import com.ccf.persistence.classes.BankSundaySchoolAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.ServiceOffering;
import com.ccf.persistence.classes.BuildingAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;
import com.ccf.persistence.interfaces.IMissionaryAccount;
import com.ccf.persistence.interfaces.IPCAccount;
import com.ccf.persistence.interfaces.ISpecialThanksOfferingAccount;
import com.ccf.persistence.interfaces.ISundaySchoolAccount;
import com.ccf.util.Constants;
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
					AccountsBalance.class, Constants.PCAccount);
			Set<PCAccount> pcAccounts = serviceOffering.getPcAccounts();
			for (PCAccount account : pcAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Bank PC Account
		if (serviceOffering.getBankPCAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.BankPCAccount);
			Set<BankPCAccount> pcAccounts = serviceOffering.getBankPCAccounts();
			for (BankPCAccount account : pcAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Missionary Account
		if (serviceOffering.getMissionaryAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.MissionaryAccount);
			Set<MissionaryAccount> missionaryAccounts = serviceOffering
					.getMissionaryAccounts();
			for (MissionaryAccount account : missionaryAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Bank Missionary Account
		if (serviceOffering.getBankMissionaryAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.BankMissionaryAccount);
			Set<BankMissionaryAccount> missionaryAccounts = serviceOffering
					.getBankMissionaryAccounts();
			for (BankMissionaryAccount account : missionaryAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Sunday School Account
		if (serviceOffering.getSundaySchoolAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.SundaySchoolAccount);
			Set<SundaySchoolAccount> sundaySchoolAccounts = serviceOffering
					.getSundaySchoolAccounts();
			for (SundaySchoolAccount account : sundaySchoolAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Bank Sunday School Account
		if (serviceOffering.getBankSundaySchoolAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session
					.get(AccountsBalance.class,
							Constants.BankSundaySchoolAccount);
			Set<BankSundaySchoolAccount> sundaySchoolAccounts = serviceOffering
					.getBankSundaySchoolAccounts();
			for (BankSundaySchoolAccount account : sundaySchoolAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Special Thanks offering account
		if (serviceOffering.getSpecialThanksOfferingAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.BuildingAccount);
			Set<BuildingAccount> stoAccounts = serviceOffering
					.getSpecialThanksOfferingAccounts();
			for (BuildingAccount account : stoAccounts) {
				accountBalance.setBalance(accountBalance.getBalance()
						+ account.getAmount());
			}
			session.update(accountBalance);
		}

		// Bank Special Thanks offering account
		if (serviceOffering.getBankSpecialThanksOfferingAccounts().size() > 0) {
			accountBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.BankBuildingAccount);
			Set<BankBuildingAccount> stoAccounts = serviceOffering
					.getBankSpecialThanksOfferingAccounts();
			for (BankBuildingAccount account : stoAccounts) {
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
