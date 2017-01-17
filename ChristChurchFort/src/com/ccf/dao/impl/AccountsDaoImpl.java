package com.ccf.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;

import com.ccf.dao.AccountsDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.AccountsBalance;
import com.ccf.persistence.classes.BankPCAccount;
import com.ccf.persistence.classes.Cheque;
import com.ccf.persistence.classes.GraveyardAccount;
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
		AccountsDaoImpl impl = new AccountsDaoImpl();
		try {
			Cheque cheque1 = new Cheque();
			cheque1.setChequeNumber("223334334");
			cheque1.setChequeDate(new Date());
			cheque1.setChequeAmount(500f);
			
			Cheque cheque2 = new Cheque();
			cheque2.setChequeNumber("223334335");
			cheque2.setChequeDate(new Date());
			cheque2.setChequeAmount(300f);
			
			BankPCAccount account1 = new BankPCAccount();
			account1.setAmount(100.0f);
			account1.setDescription("Test");
			account1.setCr_dr("CR");
			account1.setDate(new Date());
			
			cheque1.getBankPCAccounts().add(account1);
			account1.getCheques().add(cheque1);
			
			cheque2.getBankPCAccounts().add(account1);
			account1.getCheques().add(cheque2);
			
			try {
				SessionFactory sessionFactory = HibernateSessionFactory
						.getSessionFactory();
				Session session = sessionFactory.openSession();
				session.getTransaction().begin();
				session.save(account1);
				session.getTransaction().commit();
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new CcfException(e.getMessage());
			}
		} catch (CcfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<PCAccount> getPCAccountStatement(Date from, Date to)
			throws CcfException {
		logger.debug("getPCAccountStatement method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From PCAccount where date>=:from and date<=:to";
			Query query = session.createQuery(hql);
			query.setDate("from", from);
			query.setDate("to", to);
			List<PCAccount> pcAccounts = query.list();
			session.close();
			logger.debug("getPCAccountStatement method Ends...");
			return pcAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public List<MissionaryAccount> getMissionaryStatement(Date from, Date to)
			throws CcfException {
		logger.debug("getMissionaryStatement method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From MissionaryAccount where date>=:from and date<=:to";
			Query query = session.createQuery(hql);
			query.setDate("from", from);
			query.setDate("to", to);
			List<MissionaryAccount> missionaryAccounts = query.list();
			session.close();
			logger.debug("getMissionaryStatement method Ends...");
			return missionaryAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public List<MensAccount> getMensStatement(Date from, Date to)
			throws CcfException {
		logger.debug("getMensStatement method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From MensAccount where date>=:from and date<=:to";
			Query query = session.createQuery(hql);
			query.setDate("from", from);
			query.setDate("to", to);
			List<MensAccount> mensAccounts = query.list();
			session.close();
			logger.debug("getMensStatement method Ends...");
			return mensAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public List<WomensAccount> getWomensStatement(Date from, Date to)
			throws CcfException {
		logger.debug("getWomensStatement method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From WomensAccount where date>=:from and date<=:to";
			Query query = session.createQuery(hql);
			query.setDate("from", from);
			query.setDate("to", to);
			List<WomensAccount> womensAccounts = query.list();
			session.close();
			logger.debug("getWomensStatement method Ends...");
			return womensAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public List<SundaySchoolAccount> getSundaySchoolStatement(Date from, Date to)
			throws CcfException {
		logger.debug("getSundaySchoolStatement method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From SundaySchoolAccount where date>=:from and date<=:to";
			Query query = session.createQuery(hql);
			query.setDate("from", from);
			query.setDate("to", to);
			List<SundaySchoolAccount> sundaySchoolAccounts = query.list();
			session.close();
			logger.debug("getSundaySchoolStatement method Ends...");
			return sundaySchoolAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public List<YouthAccount> getYouthStatement(Date from, Date to)
			throws CcfException {
		logger.debug("getYouthStatement method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From YouthAccount where date>=:from and date<=:to";
			Query query = session.createQuery(hql);
			query.setDate("from", from);
			query.setDate("to", to);
			List<YouthAccount> youthAccounts = query.list();
			session.close();
			logger.debug("getYouthStatement method Ends...");
			return youthAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public List<SpecialThanksOfferingAccount> getSTOStatement(Date from, Date to)
			throws CcfException {
		logger.debug("getSTOStatement method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From SpecialThanksOfferingAccount where date>=:from and date<=:to";
			Query query = session.createQuery(hql);
			query.setDate("from", from);
			query.setDate("to", to);
			List<SpecialThanksOfferingAccount> specialThanksOfferingAccounts = query
					.list();
			session.close();
			logger.debug("getSTOStatement method Ends...");
			return specialThanksOfferingAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public List<GraveyardAccount> getGraveyardStatement(Date from, Date to)
			throws CcfException {
		logger.debug("getGraveyardStatement method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From GraveyardAccount where date>=:from and date<=:to";
			Query query = session.createQuery(hql);
			query.setDate("from", from);
			query.setDate("to", to);
			List<GraveyardAccount> graveyardAccounts = query.list();
			session.close();
			logger.debug("getGraveyardStatement method Ends...");
			return graveyardAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}

	}

	@Override
	public List<PrimarySchoolAccount> getPrimarySchoolStatement(Date from,
			Date to) throws CcfException {
		logger.debug("getPrimarySchoolStatement method starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			String hql = "From PrimarySchoolAccount where date>=:from and date<=:to";
			Query query = session.createQuery(hql);
			query.setDate("from", from);
			query.setDate("to", to);
			List<PrimarySchoolAccount> primarySchoolAccounts = query.list();
			session.close();
			logger.debug("getPrimarySchoolStatement method Ends...");
			return primarySchoolAccounts;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CcfException(e.getMessage());
		}

	}

}
