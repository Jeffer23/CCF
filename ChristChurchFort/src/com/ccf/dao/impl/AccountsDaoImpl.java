package com.ccf.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ccf.dao.AccountsDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.AccountsBalance;
import com.ccf.persistence.classes.BankGraveyardAccount;
import com.ccf.persistence.classes.BankPCAccount;
import com.ccf.persistence.classes.Cheque;
import com.ccf.persistence.classes.GraveyardAccount;
import com.ccf.persistence.classes.Ledger;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.PrimarySchoolAccount;
import com.ccf.persistence.classes.SpecialThanksOfferingAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.util.HibernateSessionFactory;
import com.ccf.vo.Account;

public class AccountsDaoImpl implements AccountsDao {

	final static Logger logger = Logger.getLogger(AccountsDaoImpl.class);

	@Override
	public float getAccountBalance(String accountName) throws CcfException {
		logger.info("getPCAccountBalance method start...");
		float balance = 0.0f;
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "Select balance from AccountsBalance where accountName='"
					+ accountName + "'";
			Query query = session.createQuery(hql);
			Object result = query.uniqueResult();
			if (result != null)
				balance = (float) result;
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
		logger.info("getPCAccountBalance method Ends");
		return balance;
	}

	/*
	 * Helper method to update the Account balance.
	 */
	public static void updateAccountBalance(Session session,Account account, String accountName,
			float amount) {

		AccountsBalance accountsBalance = (AccountsBalance) session.get(
				AccountsBalance.class, accountName);
		if(account.getCr_dr().equals("CR"))
			accountsBalance.setBalance(accountsBalance.getBalance() + amount);
		else if(account.getCr_dr().equals("DR"))
			accountsBalance.setBalance(accountsBalance.getBalance() - amount);
		session.update(accountsBalance);

	}

	@Override
	public void addIncomeorExpense(Account account, String accountName,
			float amount) throws CcfException {
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			session.save(account);
			updateAccountBalance(session, account, accountName, amount);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<PCAccount> getPCAccountsAfter(int id) throws CcfException {
		logger.debug("PC Account Id : " + id);
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From PCAccount where id>:id";
			Query query = session.createQuery(hql);
			query.setInteger("id", id);
			List<PCAccount> pcAccounts = query.list();
			session.close();
			return pcAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<MissionaryAccount> getMissionaryAccountsAfter(int id)
			throws CcfException {
		logger.debug("Missionary Account Id : " + id);
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From MissionaryAccount where id>:id";
			Query query = session.createQuery(hql);
			query.setInteger("id", id);
			List<MissionaryAccount> missionaryAccounts = query.list();
			session.close();
			return missionaryAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<MensAccount> getMensAccountsAfter(int id) throws CcfException {
		logger.debug("Mens Account Id : " + id);
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From MensAccount where id>:id";
			Query query = session.createQuery(hql);
			query.setInteger("id", id);
			List<MensAccount> mensAccounts = query.list();
			session.close();
			return mensAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<WomensAccount> getWomensAccountsAfter(int id)
			throws CcfException {
		logger.debug("Womens Account Id : " + id);
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From WomensAccount where id>:id";
			Query query = session.createQuery(hql);
			query.setInteger("id", id);
			List<WomensAccount> womensAccounts = query.list();
			session.close();
			return womensAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<SpecialThanksOfferingAccount> getSTOAccountsAfter(int id)
			throws CcfException {
		logger.debug("Special Thanks Offering Account Id : " + id);
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From SpecialThanksOfferingAccount where id>:id";
			Query query = session.createQuery(hql);
			query.setInteger("id", id);
			List<SpecialThanksOfferingAccount> stoAccounts = query.list();
			session.close();
			return stoAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<PrimarySchoolAccount> getPrimarySchoolAccountsAfter(int id)
			throws CcfException {
		logger.debug("Primary School Account Id : " + id);
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From PrimarySchoolAccount where id>:id";
			Query query = session.createQuery(hql);
			query.setInteger("id", id);
			List<PrimarySchoolAccount> primarySchoolAccountAccounts = query
					.list();
			session.close();
			return primarySchoolAccountAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<YouthAccount> getYouthAccountsAfter(int id) throws CcfException {
		logger.debug("Youth Account Id : " + id);
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From YouthAccount where id>:id";
			Query query = session.createQuery(hql);
			query.setInteger("id", id);
			List<YouthAccount> youthAccounts = query.list();
			session.close();
			return youthAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<GraveyardAccount> getGraveyardAccountsAfter(int id)
			throws CcfException {
		logger.debug("Graveyard Account Id : " + id);
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From GraveyardAccount where id>:id";
			Query query = session.createQuery(hql);
			query.setInteger("id", id);
			List<GraveyardAccount> graveyardAccounts = query.list();
			session.close();
			return graveyardAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public void updatePCAccount(List<PCAccount> pcAccounts) throws CcfException {

		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			for (PCAccount pcAccount : pcAccounts) {
				logger.debug("PC Account Id : " + pcAccount.getId());
				session.update(pcAccount);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public void updateMissionaryAccount(
			List<MissionaryAccount> missionaryAccounts) throws CcfException {

		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			for (MissionaryAccount missionaryAccount : missionaryAccounts) {
				logger.debug("Missionary Account Id : "
						+ missionaryAccount.getId());
				session.update(missionaryAccount);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public void updateMensAccount(List<MensAccount> mensAccounts)
			throws CcfException {

		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			for (MensAccount mensAccount : mensAccounts) {
				logger.debug("Men's Account Id : " + mensAccount.getId());
				session.update(mensAccount);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public void updateWomensAccount(List<WomensAccount> womensAccounts)
			throws CcfException {

		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			for (WomensAccount womensAccount : womensAccounts) {
				logger.debug("Women's Account Id : " + womensAccount.getId());
				session.update(womensAccount);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public void updateSpecialThanksOfferingAccount(
			List<SpecialThanksOfferingAccount> stoAccounts) throws CcfException {

		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			for (SpecialThanksOfferingAccount stoAccount : stoAccounts) {
				logger.debug("Special Thanks Offering Account Id : "
						+ stoAccount.getId());
				session.update(stoAccount);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public void updatePrimarySchoolAccount(
			List<PrimarySchoolAccount> primarySchoolAccounts)
			throws CcfException {

		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			for (PrimarySchoolAccount primarySchoolAccount : primarySchoolAccounts) {
				logger.debug("Primary School Account Id : "
						+ primarySchoolAccount.getId());
				session.update(primarySchoolAccount);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public void updateYouthAccount(List<YouthAccount> youthAccounts)
			throws CcfException {

		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			for (YouthAccount youthAccount : youthAccounts) {
				logger.debug("Youth Account Id : " + youthAccount.getId());
				session.update(youthAccount);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public void updateGraveyardAccount(List<GraveyardAccount> graveyardAccounts)
			throws CcfException {

		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			for (GraveyardAccount graveyardAccount : graveyardAccounts) {
				logger.debug("graveyard Account Id : "
						+ graveyardAccount.getId());
				session.update(graveyardAccount);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	public static void main(String[] args) {
		String accountName = "com.ccf.persistence.classes.PCAccount";
		Calendar from = Calendar.getInstance();
		from.set(2017, 0, 01);
		Calendar to = Calendar.getInstance();
		to.set(2017, 0, 31);
		try {
			
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Criteria c = session.createCriteria(Ledger.class);
			c.add(Restrictions.not(Restrictions.like("ledgerName", "Santha - ", MatchMode.START)));
			c.add(Restrictions.not(Restrictions.like("ledgerName", "Service - ", MatchMode.START)));
			List<Ledger> ledgers = c.list();
			session.close();
			logger.debug("getPrimarySchoolStatement method Ends...");
			for(Ledger l : ledgers){
				System.out.println(l.getLedgerName());
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Account> getAccountStatement(String accountName, Date from, Date to)
			throws CcfException {
		logger.debug("getPCAccountStatement method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Criteria c = session.createCriteria(Class.forName(accountName));
			c.add(Restrictions.ge("date", from));
			c.add(Restrictions.le("date", to));
			c.createAlias("ledger", "ledger");
			
			List<Account> accounts = c.list();
			session.close();
			logger.debug("getPCAccountStatement method Ends...");
			return accounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}

	}

	
	
	@Override
	public int addLedger(Ledger ledger) throws CcfException {
		logger.debug("addLedger method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			int ledgerId = (int) session.save(ledger);
			session.getTransaction().commit();
			session.close();
			logger.debug("addLedger method ends...");
			return ledgerId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}
	
	@Override
	public List<Ledger> getAllLedgers() throws CcfException{
		logger.debug("getAllLedgers method starts...");
		try {

			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Criteria c = session.createCriteria(Ledger.class);
			c.add(Restrictions.not(Restrictions.like("ledgerName", "Santha - ", MatchMode.START)));
			c.add(Restrictions.not(Restrictions.like("ledgerName", "Service - ", MatchMode.START)));
			List<Ledger> ledgers = c.list();
			session.close();
			logger.debug("getPrimarySchoolStatement method Ends...");
			return ledgers;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}
	
	@Override
	public List<Ledger> getAllLedgers(String startsWithValue) throws CcfException {
		logger.debug("getAllLedgers method starts...");
		try {

			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Criteria c = session.createCriteria(Ledger.class);
			c.add(Restrictions.like("ledgerName", startsWithValue, MatchMode.START));
			List<Ledger> ledgers = c.list();
			session.close();
			logger.debug("getPrimarySchoolStatement method Ends...");
			return ledgers;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}
	

}
