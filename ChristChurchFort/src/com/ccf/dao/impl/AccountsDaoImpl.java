package com.ccf.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ccf.dao.AccountsDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.AccountsBalance;
import com.ccf.persistence.classes.Cheque;
import com.ccf.persistence.classes.GraveyardAccount;
import com.ccf.persistence.classes.Ledger;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.EducationalFundAccount;
import com.ccf.persistence.classes.BuildingAccount;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.persistence.interfaces.Account;
import com.ccf.util.Constants;
import com.ccf.util.HibernateSessionFactory;

public class AccountsDaoImpl implements AccountsDao {

	final static Logger logger = Logger.getLogger(AccountsDaoImpl.class);

	
	/*
	 * Helper method to update the Account balance.
	 */
	private static void updateAccountBalance(Session session,Account account, String accountName,
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
	public List<BuildingAccount> getSTOAccountsAfter(int id)
			throws CcfException {
		logger.debug("Special Thanks Offering Account Id : " + id);
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From SpecialThanksOfferingAccount where id>:id";
			Query query = session.createQuery(hql);
			query.setInteger("id", id);
			List<BuildingAccount> stoAccounts = query.list();
			session.close();
			return stoAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<EducationalFundAccount> getEducationalFundAccountsAfter(int id)
			throws CcfException {
		logger.debug("Educational Fund Account Id : " + id);
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From EducationalFundAccount where id>:id";
			Query query = session.createQuery(hql);
			query.setInteger("id", id);
			List<EducationalFundAccount> educationalFundAccountAccounts = query
					.list();
			session.close();
			return educationalFundAccountAccounts;
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
			List<BuildingAccount> stoAccounts) throws CcfException {

		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			for (BuildingAccount stoAccount : stoAccounts) {
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
	public void updateEducationalFundAccount(
			List<EducationalFundAccount> educationalFundAccounts)
			throws CcfException {

		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			for (EducationalFundAccount educationalFundAccount : educationalFundAccounts) {
				logger.debug("Educational Fund Account Id : "
						+ educationalFundAccount.getId());
				session.update(educationalFundAccount);
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
		AccountsDaoImpl impl = new AccountsDaoImpl();
		
		Calendar from = Calendar.getInstance();
		from.set(2017, 4, 01);
		Calendar to = Calendar.getInstance();
		to.set(2017, 4, 30);
		try {
			impl.updateBalance(PCAccount.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Account> getAccountStatement(Class entity, Date from, Date to)
			throws CcfException {
		logger.debug("getAccountStatement method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Criteria c = session.createCriteria(entity);
			c.add(Restrictions.ge("date", from));
			c.add(Restrictions.le("date", to));
			c.createAlias("ledger", "ledger");
			
			List<Account> accounts = c.list();
			session.close();
			logger.debug("getAccountStatement method Ends...");
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
			logger.debug("getAllLedgers method Ends...");
			return ledgers;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}
	
	@Override
	public List<Ledger> getAllLedgers(String startsWithValue) throws CcfException {
		logger.debug("getAllLedgers method starts...");
		logger.info("Starts With Value : " + startsWithValue);
		try {

			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Criteria c = session.createCriteria(Ledger.class);
			c.add(Restrictions.like("ledgerName", startsWithValue, MatchMode.START));
			List<Ledger> ledgers = c.list();
			session.close();
			logger.debug("getAllLedgers method Ends...");
			return ledgers;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}
	
	@Override
	public boolean isChequeExists(String chequeNumber) throws CcfException{
		logger.debug("isChequeExists method Starts");
		boolean chequeExists = false;
		SessionFactory sessionFactory = HibernateSessionFactory
				.getSessionFactory();
		Session session = sessionFactory.openSession();
		String hql = "from Cheque where chequeNumber = '" + chequeNumber +"'";
		Query query = session.createQuery(hql);
		Cheque cheque = (Cheque) query.uniqueResult();
		if(cheque!= null)
			chequeExists = true;
		session.close();
		logger.debug("isChequeExists method Ends");
		return chequeExists;
	}
	
	
	
	@Override
	public void withdrawOrDeposit(Account creditAcc, String creditAccName, Account debitAcc, String debitAccName, float amount) throws CcfException {
		logger.debug("withdrawOrDeposit method Starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			session.save(creditAcc);
			updateAccountBalance(session, creditAcc, creditAccName, amount);
			session.save(debitAcc);
			updateAccountBalance(session, debitAcc, debitAccName, amount);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
		logger.debug("withdrawOrDeposit method Ends...");
	}
	
	

	@Override
	public float getCurrentAccountBalance(Class entity) throws CcfException {
		logger.debug("getCurrentAccountBalance method Starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Criteria c = session.createCriteria(entity);
			c.addOrder(Order.desc("date"));
			c.addOrder(Order.desc("id"));
			c.setMaxResults(1);
			Account account = (Account) c.uniqueResult();
			if(account == null) // First time no record will be in database.
				return 0.0f;
			logger.debug("Id : " + account.getDescription());
			session.close();
			logger.debug("getCurrentAccountBalance method Ends...");
			return account.getBalance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public Map<String, Float> getOpeningAndClosingBalance(Class entity, Date startDate, Date endDate)
			throws CcfException {
		Map<String, Float> balances = new HashMap<>();
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Criteria c = session.createCriteria(entity);
			c.add(Restrictions.between("date", startDate, endDate));
			c.addOrder(Order.asc("date"));
			c.addOrder(Order.asc("id"));
			c.setMaxResults(1);
			Account account = (Account) c.uniqueResult();
			if(account == null) // First time no record will be in database.
				balances.put(Constants.OpeningBalance, 0.0f);
			else
				balances.put(Constants.OpeningBalance, account.getBalance() - account.getAmount());
			
			c = session.createCriteria(entity);
			c.add(Restrictions.between("date", startDate, endDate));
			c.addOrder(Order.desc("date"));
			c.addOrder(Order.desc("id"));
			c.setMaxResults(1);
			account = (Account) c.uniqueResult();
			if(account == null) // First time no record will be in database.
				balances.put(Constants.ClosingBalance, 0.0f);
			else
				balances.put(Constants.ClosingBalance, account.getBalance());
			session.close();
			logger.debug("getCurrentAccountBalance method Ends...");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
		return balances;
	}

	@Override
	public float getAccountBalance(Class entity, Date date) throws CcfException{
		float balance = 0.0f;
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Criteria c = session.createCriteria(entity);
			c.add(Restrictions.le("date", date));
			c.addOrder(Order.desc("date"));
			c.addOrder(Order.desc("id"));
			c.setMaxResults(1);
			Account account = (Account) c.uniqueResult();
			if(account != null) // First time no record will be in database, returns null.
				balance = account.getBalance();
			
			session.close();
			logger.debug("getCurrentAccountBalance method Ends...");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
		return balance;
	}

	@Override
	public void updateBalance(Class entity) throws CcfException {
		logger.debug("updateBalance method Starts...");
		
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction tnx = session.beginTransaction();
			Criteria c = session.createCriteria(entity);
			c.addOrder(Order.asc("date"));
			c.addOrder(Order.asc("id"));
			List<Account> accounts = c.list();
			float balance = 0.0f;
			for(Account account : accounts){
				if(account.getCr_dr().equals("CR"))
					balance += account.getAmount();
				else if(account.getCr_dr().equals("DR"))
					balance -= account.getAmount();
				account.setBalance(balance);
			}
			
			tnx.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}
		
		logger.debug("updateBalance method Ends...");
	}

}
