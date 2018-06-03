package com.ccf.dao.impl;


import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ccf.dao.SanthaDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.AccountsBalance;
import com.ccf.persistence.classes.BankGraveyardAccount;
import com.ccf.persistence.classes.BankMensAccount;
import com.ccf.persistence.classes.BankMissionaryAccount;
import com.ccf.persistence.classes.BankPCAccount;
import com.ccf.persistence.classes.BankEducationalFundAccount;
import com.ccf.persistence.classes.BankBuildingAccount;
import com.ccf.persistence.classes.BankWomensAccount;
import com.ccf.persistence.classes.BankYouthAccount;
import com.ccf.persistence.classes.BuildingAccount;
import com.ccf.persistence.classes.Cheque;
import com.ccf.persistence.classes.EducationalFundAccount;
import com.ccf.persistence.classes.Family;
import com.ccf.persistence.classes.GraveyardAccount;
import com.ccf.persistence.classes.Ledger;
import com.ccf.persistence.classes.Member;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.Santha;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.persistence.interfaces.Account;
import com.ccf.persistence.interfaces.IGraveyardAccount;
import com.ccf.persistence.interfaces.IMensAccount;
import com.ccf.persistence.interfaces.IMissionaryAccount;
import com.ccf.persistence.interfaces.IPCAccount;
import com.ccf.persistence.interfaces.IEducationalFundAccount;
import com.ccf.persistence.interfaces.ISpecialThanksOfferingAccount;
import com.ccf.persistence.interfaces.IWomensAccount;
import com.ccf.persistence.interfaces.IYouthAccount;
import com.ccf.util.Constants;
import com.ccf.util.HibernateSessionFactory;

public class SanthaDaoImpl implements SanthaDao {

	final static Logger logger = Logger.getLogger(SanthaDaoImpl.class);

	@Override
	public int paySantha(Santha santha, Session session) throws CcfException {
		try {
			/*SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();*/
			Transaction transaction = session.beginTransaction();
			//Update accountBalance and Cheque amount.
			updateAccountBalance(santha, session, true);
			
			
			
			int key = (int) session.save(santha);
			transaction.commit();
			//session.close();
			return key;
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}

	@Override
	public List<Santha> getPaidMembers(int familyNo, Date fromDate, Date toDate)
			throws CcfException {
		try {
			logger.info("Family No : " + familyNo);
			logger.info("From Date : " + fromDate);
			logger.info("To Date : " + toDate);
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
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
			session.close();
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
			logger.info("Subscription" + santha.getSubscriptionAmount());
			logger.info("poorHelp" + santha.getPoorHelp());
			logger.info("preSchool" + santha.getPreSchool());
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
			updateSantha(session, santha);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}

	}
	
	private void updateSantha(Session session, Santha santha){
		String hql = "UPDATE Santha set bagOffer = :bagOffer, churchRenovation = :churchRenovation , educationHelp = :educationHelp, graveyard = :graveyard, harvestFestival = :harvestFestival, mensFellowship = :mensFellowship, missionary = :missionary,subscriptionAmount = :subscription, poorHelp = :poorHelp, preSchool = :preSchool, sto = :sto, thanksOffer = :thanksOffer, womensFellowship = :womensFellowship, youth = :youth, total = :total "
				+ "WHERE santhaId = :santhaId";
		Query query = session.createQuery(hql);
		query.setParameter("bagOffer", santha.getBagOffer());
		query.setParameter("churchRenovation", santha.getChurchRenovation());
		query.setParameter("educationHelp", santha.getEducationHelp());
		query.setParameter("graveyard", santha.getGraveyard());
		query.setParameter("harvestFestival", santha.getHarvestFestival());
		query.setParameter("mensFellowship", santha.getMensFellowship());
		query.setParameter("missionary", santha.getMissionary());
		query.setParameter("subscription", santha.getSubscriptionAmount());
		query.setParameter("poorHelp", santha.getPoorHelp());
		query.setParameter("preSchool", santha.getPreSchool());
		query.setParameter("sto", santha.getSto());
		query.setParameter("thanksOffer", santha.getThanksOffer());
		query.setParameter("womensFellowship", santha.getWomensFellowship());
		query.setParameter("youth", santha.getYouth());
		query.setParameter("total", santha.getTotal());
		query.setParameter("santhaId", santha.getSanthaId());

		int result = query.executeUpdate();
		System.out.println("Update Status : " + result);
	}

	@Override
	public void deleteSantha(int santhaId) throws CcfException {
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String hql = "From Santha where santhaId=:santhaId";
			Query query = session.createQuery(hql);
			query.setInteger("santhaId", santhaId);
			Santha santha = (Santha) query.uniqueResult();
			
			//Update Account balance and update cheque amount
			updateAccountBalance(santha, session, false);
			
			// Deleting Santha
			session.delete(santha);
			
			/*float pcAmount = santha.getHarvestFestival()
					+ santha.getPoorHelp()
					+ santha.getBagOffer() + santha.getSubscriptionAmount();
			float stoAmount = santha.getThanksOffer() + santha.getSto()
					+ santha.getChurchRenovation();
			float womensAmount = santha.getWomensFellowship() + santha.getPreSchool();

			//Updating the balance in accounts table
			//updateSanthaBalance(santha, session);
			
			AccountsBalance accountsBalance = null;
			if (pcAmount > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, Constants.PCAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- pcAmount);
				session.update(accountsBalance);
			}

			if (stoAmount > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, Constants.BuildingAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- stoAmount);
				session.update(accountsBalance);
			}


			if (womensAmount > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, Constants.WomensAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- womensAmount);
				session.update(accountsBalance);
			}
			
			if (santha.getMissionary() > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, Constants.MissionaryAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- santha.getMissionary());
				session.update(accountsBalance);
			}

			if (santha.getMensFellowship() > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, Constants.MensAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- santha.getMensFellowship());
				session.update(accountsBalance);
			}

			if (santha.getYouth() > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, Constants.YouthAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- santha.getYouth());
				session.update(accountsBalance);
			}

			if (santha.getGraveyard() > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, Constants.GraveyardAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- santha.getGraveyard());
				session.update(accountsBalance);
			}
			
			if (santha.getEducationHelp() > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class,
						Constants.EducationalFundAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- santha.getEducationHelp());
				session.update(accountsBalance);
			}*/

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
	public List<Santha> getReport(Date fromDate, Date toDate)
			throws CcfException {
		List<Santha> santhas = null;
		try {
			logger.info("From Date : " + fromDate);
			logger.info("To Date : " + toDate);
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Criteria cr = session.createCriteria(Santha.class);

			cr.add(Restrictions.ge("paidForDate", fromDate));
			cr.add(Restrictions.le("paidForDate", toDate));
			// cr.createAlias("member", "member");
			// cr.createAlias("member.family", "family");
			santhas = cr.list();
			transaction.commit();
			session.close();
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

	@Override
	public Cheque getCheque(int SanthaId) throws CcfException {
		logger.info("getCheque method Starts...");
		try {
			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();
			Criteria c = session.createCriteria(Santha.class);
			c.add(Restrictions.eq("santhaId", SanthaId));
			
			
			Santha santha = (Santha) c.uniqueResult();
			Cheque cheque = null;
			if (santha.getBankGraveyardAccounts().size() > 0
					|| santha.getBankMensAccounts().size() > 0
					|| santha.getBankMissionaryAccounts().size() > 0
					|| santha.getBankPCAccounts().size() > 0
					|| santha.getBankEducationalFundAccounts().size() > 0
					|| santha.getBankSpecialThanksOfferingAccounts()
							.size() > 0
					|| santha.getBankWomensAccounts().size() > 0
					|| santha.getBankYouthAccounts().size() > 0) {
				
				if(!santha.getBankGraveyardAccounts().isEmpty()){
					BankGraveyardAccount bgAcc = (BankGraveyardAccount) santha.getBankGraveyardAccounts().iterator().next();
					cheque = bgAcc.getCheques().iterator().next();
				}
				else if(!santha.getBankMensAccounts().isEmpty()){
					BankMensAccount bmAcc = (BankMensAccount) santha.getBankMensAccounts().iterator().next();
					cheque = bmAcc.getCheques().iterator().next();
				}
				else if(!santha.getBankMissionaryAccounts().isEmpty()){
					BankMissionaryAccount bmAcc = (BankMissionaryAccount) santha.getBankMissionaryAccounts().iterator().next();
					cheque = bmAcc.getCheques().iterator().next();
				}
				else if(!santha.getBankPCAccounts().isEmpty()){
					BankPCAccount bpcAcc = (BankPCAccount) santha.getBankPCAccounts().iterator().next();
					cheque = bpcAcc.getCheques().iterator().next();
				}
				else if(!santha.getBankEducationalFundAccounts().isEmpty()){
					BankEducationalFundAccount bpsAcc = (BankEducationalFundAccount) santha.getBankEducationalFundAccounts().iterator().next();
					cheque = bpsAcc.getCheques().iterator().next();
				}
				else if(!santha.getBankSpecialThanksOfferingAccounts().isEmpty()){
					BankBuildingAccount bstoAcc = (BankBuildingAccount) santha.getBankSpecialThanksOfferingAccounts().iterator().next();
					cheque = bstoAcc.getCheques().iterator().next();
				}
				else if(!santha.getBankWomensAccounts().isEmpty()){
					BankWomensAccount bwAcc = (BankWomensAccount) santha.getBankWomensAccounts().iterator().next();
					cheque = bwAcc.getCheques().iterator().next();
				}
				else if(!santha.getBankYouthAccounts().isEmpty()){
					BankYouthAccount byAcc = (BankYouthAccount) santha.getBankYouthAccounts().iterator().next();
					cheque = byAcc.getCheques().iterator().next();
				}
			}
			
			session.close();
			logger.info("getCheque method Ends...");
			return cheque;

		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
	}
	

	private void updateSanthaBalance(Santha santha, Session session) throws CcfException {
		logger.info("updateSanthaBalance method Starts...");
		logger.info("Santha Id : " + santha.getSanthaId());
		try {
			
			float pcAmount = santha.getHarvestFestival()
					+ santha.getPoorHelp()
					+ santha.getBagOffer() + santha.getSubscriptionAmount();
			float stoAmount = santha.getThanksOffer() + santha.getSto()
					+ santha.getChurchRenovation();
			float womensAmount = santha.getWomensFellowship() + santha.getPreSchool();
			
				if(pcAmount > 0){
					IPCAccount pcAccount = null;
					Iterator<IPCAccount> iterator = null;
					if(santha.getPcAccounts().size() > 0)
						iterator = santha.getPcAccounts().iterator();
					else if(santha.getBankPCAccounts().size() > 0)
						iterator = santha.getBankPCAccounts().iterator();
					
					while(iterator.hasNext()){
						IPCAccount pca = (IPCAccount) iterator.next(); // To get the last element
						if(pcAccount == null){
							pcAccount = pca;
						}
						else if(pcAccount.getId() < pca.getId()){
							pcAccount = pca;
						}
					}
					
					List<Account> accounts = null;
					if(santha.getPcAccounts().size() > 0)
						accounts = getAccounts(PCAccount.class, pcAccount.getId(), session);
					else if(santha.getBankPCAccounts().size() > 0)
						accounts = getAccounts(BankPCAccount.class, pcAccount.getId(), session);
					
					for(Account account : accounts){
						//PCAccount pca = (PCAccount) account;
						account.setBalance(account.getBalance() - pcAmount);
						session.update(account);
					}
					
				}
				
				if(stoAmount > 0){
					ISpecialThanksOfferingAccount stoAccount = null;
					Iterator<ISpecialThanksOfferingAccount> iterator = null;
					if(santha.getSpecialThanksOfferingAccounts().size() > 0)
						iterator = santha.getSpecialThanksOfferingAccounts().iterator();
					else if(santha.getBankSpecialThanksOfferingAccounts().size() > 0)
						iterator = santha.getBankSpecialThanksOfferingAccounts().iterator();
					
					while(iterator.hasNext()){
						ISpecialThanksOfferingAccount stoa = (ISpecialThanksOfferingAccount) iterator.next();
						if(stoAccount == null)
							stoAccount = stoa;
						else if(stoAccount.getId() < stoa.getId())
							stoAccount = stoa;
					}
					
					List<Account>  accounts = null;
					if(santha.getSpecialThanksOfferingAccounts().size() > 0)
						accounts = getAccounts(BuildingAccount.class, stoAccount.getId(), session);
					else if(santha.getBankSpecialThanksOfferingAccounts().size() > 0){
						accounts = getAccounts(BankBuildingAccount.class, stoAccount.getId(), session);
					}
					for(Account account : accounts){
						account.setBalance(account.getBalance() - stoAmount);
						session.update(account);
					}
				}
				
				if(womensAmount > 0){
					IWomensAccount womensAccount = null;
					Iterator<IWomensAccount> iterator = null;
					if(santha.getWomensAccounts().size() > 0)
						iterator = santha.getWomensAccounts().iterator();
					else if(santha.getBankWomensAccounts().size() > 0)
						iterator = santha.getBankWomensAccounts().iterator();
					
					while(iterator.hasNext()){
						IWomensAccount wa = (IWomensAccount) iterator.next();
						if(womensAccount == null)
							womensAccount = wa;
						else if(womensAccount.getId() < wa.getId())
							womensAccount = wa;
					}
					
					List<Account>  accounts = null;
					if(santha.getWomensAccounts().size() > 0)
						accounts = getAccounts(WomensAccount.class, womensAccount.getId(), session);
					else if(santha.getBankWomensAccounts().size() > 0){
						accounts = getAccounts(BankWomensAccount.class, womensAccount.getId(), session);
					}
					for(Account account : accounts){
						account.setBalance(account.getBalance() - stoAmount);
						session.update(account);
					}
				}
				
				if(santha.getEducationHelp() > 0){
					IEducationalFundAccount eduFundAccount = null;
					Iterator<IEducationalFundAccount> iterator = null;
					if(santha.getEducationalFundAccounts().size() > 0)
						iterator = santha.getEducationalFundAccounts().iterator();
					else if(santha.getBankEducationalFundAccounts().size() > 0)
						iterator = santha.getBankEducationalFundAccounts().iterator();
					
					while(iterator.hasNext()){
						IEducationalFundAccount wa = (IEducationalFundAccount) iterator.next();
						if(eduFundAccount == null)
							eduFundAccount = wa;
						else if(eduFundAccount.getId() < wa.getId())
							eduFundAccount = wa;
					}
					
					List<Account>  accounts = null;
					if(santha.getEducationalFundAccounts().size() > 0)
						accounts = getAccounts(EducationalFundAccount.class, eduFundAccount.getId(), session);
					else if(santha.getBankEducationalFundAccounts().size() > 0){
						accounts = getAccounts(BankEducationalFundAccount.class, eduFundAccount.getId(), session);
					}
					for(Account account : accounts){
						account.setBalance(account.getBalance() - stoAmount);
						session.update(account);
					}
				}
				
				if(santha.getMissionary() > 0){
					IMissionaryAccount missionaryAccount = null;
					Iterator<IMissionaryAccount> iterator = null;
					if(santha.getMissionaryAccounts().size() > 0)
						iterator = santha.getMissionaryAccounts().iterator();
					else if(santha.getBankMissionaryAccounts().size() > 0)
						iterator = santha.getBankMissionaryAccounts().iterator();
					
					while(iterator.hasNext()){
						IMissionaryAccount wa = (IMissionaryAccount) iterator.next();
						if(missionaryAccount == null)
							missionaryAccount = wa;
						else if(missionaryAccount.getId() < wa.getId())
							missionaryAccount = wa;
					}
					
					List<Account>  accounts = null;
					if(santha.getMissionaryAccounts().size() > 0)
						accounts = getAccounts(MissionaryAccount.class, missionaryAccount.getId(), session);
					else if(santha.getBankMissionaryAccounts().size() > 0){
						accounts = getAccounts(BankMissionaryAccount.class, missionaryAccount.getId(), session);
					}
					for(Account account : accounts){
						account.setBalance(account.getBalance() - stoAmount);
						session.update(account);
					}
				}
				
				if(santha.getMensFellowship() > 0){
					IMensAccount mensAccount = null;
					Iterator<IMensAccount> iterator = null;
					if(santha.getMensAccounts().size() > 0)
						iterator = santha.getMensAccounts().iterator();
					else if(santha.getBankMensAccounts().size() > 0)
						iterator = santha.getBankMensAccounts().iterator();
					
					while(iterator.hasNext()){
						IMensAccount wa = (IMensAccount) iterator.next();
						if(mensAccount == null)
							mensAccount = wa;
						else if(mensAccount.getId() < wa.getId())
							mensAccount = wa;
					}
					
					List<Account>  accounts = null;
					if(santha.getMensAccounts().size() > 0)
						accounts = getAccounts(MensAccount.class, mensAccount.getId(), session);
					else if(santha.getBankMensAccounts().size() > 0){
						accounts = getAccounts(BankMensAccount.class, mensAccount.getId(), session);
					}
					for(Account account : accounts){
						account.setBalance(account.getBalance() - stoAmount);
						session.update(account);
					}
				}
				
				if(santha.getYouth() > 0){
					IYouthAccount youthAccount = null;
					Iterator<IYouthAccount> iterator = null;
					if(santha.getYouthAccounts().size() > 0)
						iterator = santha.getYouthAccounts().iterator();
					else if(santha.getBankYouthAccounts().size() > 0)
						iterator = santha.getBankYouthAccounts().iterator();
					
					while(iterator.hasNext()){
						IYouthAccount wa = (IYouthAccount) iterator.next();
						if(youthAccount == null)
							youthAccount = wa;
						else if(youthAccount.getId() < wa.getId())
							youthAccount = wa;
					}
					
					List<Account>  accounts = null;
					if(santha.getYouthAccounts().size() > 0)
						accounts = getAccounts(YouthAccount.class, youthAccount.getId(), session);
					else if(santha.getBankYouthAccounts().size() > 0){
						accounts = getAccounts(BankYouthAccount.class, youthAccount.getId(), session);
					}
					for(Account account : accounts){
						account.setBalance(account.getBalance() - stoAmount);
						session.update(account);
					}
				}
				
				if(santha.getGraveyard() > 0){
					IGraveyardAccount graveyardAccount = null;
					Iterator<IGraveyardAccount> iterator = null;
					if(santha.getGraveyardAccounts().size() > 0)
						iterator = santha.getGraveyardAccounts().iterator();
					else if(santha.getBankGraveyardAccounts().size() > 0)
						iterator = santha.getBankGraveyardAccounts().iterator();
					
					while(iterator.hasNext()){
						IGraveyardAccount wa = (IGraveyardAccount) iterator.next();
						if(graveyardAccount == null)
							graveyardAccount = wa;
						else if(graveyardAccount.getId() < wa.getId())
							graveyardAccount = wa;
					}
					
					List<Account>  accounts = null;
					if(santha.getGraveyardAccounts().size() > 0)
						accounts = getAccounts(GraveyardAccount.class, graveyardAccount.getId(), session);
					else if(santha.getBankGraveyardAccounts().size() > 0){
						accounts = getAccounts(BankGraveyardAccount.class, graveyardAccount.getId(), session);
					}
					for(Account account : accounts){
						account.setBalance(account.getBalance() - stoAmount);
						session.update(account);
					}
				}
				
		} catch (Exception e) {
			throw new CcfException(e.getMessage());
		}
		logger.info("updateSanthaBalance method Ends...");
	}

	private List<Account> getAccounts(Class entity, int id, Session session) throws CcfException {
		List<Account> accounts = null;
	/*	SessionFactory sessionFactory = HibernateSessionFactory
				.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();*/
		String hql = "From " + entity.getName() + " where id>:id";
		Query query = session.createQuery(hql);
		query.setInteger("id", id);
		accounts = query.list();
		return accounts;
	}
	
	@Override
	public Cheque getCheque(String chequeNumber, Session session) throws CcfException{
		logger.debug("getCheque method Starts");
		/*SessionFactory sessionFactory = HibernateSessionFactory
				.getSessionFactory();
		Session session = sessionFactory.openSession();*/
		String hql = "from Cheque where chequeNumber = '" + chequeNumber +"'";
		Query query = session.createQuery(hql);
		Cheque cheque = (Cheque) query.uniqueResult();
		//session.close();
		logger.debug("getCheque method Ends");
		return cheque;
	}
	
	
	private void updateAccountBalance(Santha santha, Session session, boolean isSave){
		AccountsBalance accBalance = null;
		// PC Account
		if (santha.getPcAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.PCAccount);
			Set<IPCAccount> pcAccounts = santha.getPcAccounts();
			for (IPCAccount account : pcAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
				}
			}
			session.update(accBalance);
			
			
		}

		// Bank PC Account
		if (santha.getBankPCAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.BankPCAccount);
			Set<IPCAccount> pcAccounts = santha.getBankPCAccounts();
			for (IPCAccount account : pcAccounts) {
				if(isSave){
					// Executed during Pay Santha
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
					for(Cheque cheque : ((BankPCAccount)account).getCheques()){
						if(!cheque.isNew())
							session.update(cheque);
						else
							session.save(cheque);
					}
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
					for(Cheque cheque : ((BankPCAccount)account).getCheques()){
						cheque.setChequeAmount(cheque.getChequeAmount() - account.getAmount());
						session.update(cheque);
						if(cheque.getChequeAmount() == 0){
							session.delete(cheque);
						}
					}
				} 
				
			}
			session.update(accBalance);
		}

		// Missionary Account
		if (santha.getMissionaryAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.MissionaryAccount);
			Set<IMissionaryAccount> missionaryAccounts = santha
					.getMissionaryAccounts();
			for (IMissionaryAccount account : missionaryAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
				}
			}
			session.update(accBalance);
		}

		// Bank Missionary Account
		if (santha.getBankMissionaryAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class,
					Constants.BankMissionaryAccount);
			Set<IMissionaryAccount> missionaryAccounts = santha
					.getBankMissionaryAccounts();
			for (IMissionaryAccount account : missionaryAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
					for(Cheque cheque : ((BankMissionaryAccount)account).getCheques()){
						if(!cheque.isNew())
							session.update(cheque);
						else
							session.save(cheque);
					}
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
					for(Cheque cheque : ((BankMissionaryAccount)account).getCheques()){
						cheque.setChequeAmount(cheque.getChequeAmount() - account.getAmount());
						session.update(cheque);
						if(cheque.getChequeAmount() == 0){
							session.delete(cheque);
						}
					}
				}
				
			}
			session.update(accBalance);
		}

		// Men's Account
		if (santha.getMensAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.MensAccount);
			Set<IMensAccount> mensAccounts = santha.getMensAccounts();
			for (IMensAccount account : mensAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
				}
			}
			session.update(accBalance);
		}

		// Bank Mens's Account
		if (santha.getBankMensAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.BankMensAccount);
			Set<IMensAccount> mensAccounts = santha.getBankMensAccounts();
			for (IMensAccount account : mensAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
					for(Cheque cheque : ((BankMensAccount)account).getCheques()){
						if(!cheque.isNew())
						session.update(cheque);
						else
							session.save(cheque);
					}
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
					for(Cheque cheque : ((BankMensAccount)account).getCheques()){
						cheque.setChequeAmount(cheque.getChequeAmount() - account.getAmount());
						session.update(cheque);
						if(cheque.getChequeAmount() == 0){
							session.delete(cheque);
						}
					}
				}
				
			}
			session.update(accBalance);
		}

		// Womens's Account
		if (santha.getWomensAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.WomensAccount);
			Set<IWomensAccount> womensAccounts = santha.getWomensAccounts();
			for (IWomensAccount account : womensAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
				}
			}
			session.update(accBalance);
		}

		// Bank womens's Account
		if (santha.getBankWomensAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.BankWomensAccount);
			Set<IWomensAccount> womensAccounts = santha
					.getBankWomensAccounts();
			for (IWomensAccount account : womensAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
					for(Cheque cheque : ((BankWomensAccount)account).getCheques()){
						if(!cheque.isNew())
						session.update(cheque);	
						else
							session.save(cheque);
					}
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
					for(Cheque cheque : ((BankWomensAccount)account).getCheques()){
						cheque.setChequeAmount(cheque.getChequeAmount() - account.getAmount());
						session.update(cheque);
						if(cheque.getChequeAmount() == 0){
							session.delete(cheque);
						}
					}
				}
				
			}
			session.update(accBalance);
		}

		// Youth Account
		if (santha.getYouthAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.YouthAccount);
			Set<IYouthAccount> youthAccounts = santha.getYouthAccounts();
			for (IYouthAccount account : youthAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
				}
			}
			session.update(accBalance);
		}
		// Bank Youth Account
		if (santha.getBankYouthAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.BankYouthAccount);
			Set<IYouthAccount> youthAccounts = santha
					.getBankYouthAccounts();
			for (IYouthAccount account : youthAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
					for(Cheque cheque : ((BankYouthAccount)account).getCheques()){
						if(!cheque.isNew())
						session.update(cheque);
						else
							session.save(cheque);
					}
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
					for(Cheque cheque : ((BankYouthAccount)account).getCheques()){
						cheque.setChequeAmount(cheque.getChequeAmount() - account.getAmount());
						session.update(cheque);
						if(cheque.getChequeAmount() == 0){
							session.delete(cheque);
						}
					}
				}
				
			}
			session.update(accBalance);
		}

		// Graveyard Account
		if (santha.getGraveyardAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.GraveyardAccount);
			Set<IGraveyardAccount> graveyardAccounts = santha
					.getGraveyardAccounts();
			for (IGraveyardAccount account : graveyardAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
				}
			}
			session.update(accBalance);
		}

		// Bank Graveyard Account
		if (santha.getBankGraveyardAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class,
					Constants.BankGraveyardAccount);
			Set<IGraveyardAccount> graveyardAccounts = santha
					.getBankGraveyardAccounts();
			for (IGraveyardAccount account : graveyardAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
					for(Cheque cheque : ((BankGraveyardAccount)account).getCheques()){
						if(!cheque.isNew())
						session.update(cheque);	
						else
							session.save(cheque);
					}
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
					for(Cheque cheque : ((BankGraveyardAccount)account).getCheques()){
						cheque.setChequeAmount(cheque.getChequeAmount() - account.getAmount());
						session.update(cheque);
						if(cheque.getChequeAmount() == 0){
							session.delete(cheque);
						}
					}
				}
				
			}
			session.update(accBalance);
		}

		// Educational Fund Account
		if (santha.getEducationalFundAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class,
					Constants.EducationalFundAccount);
			Set<IEducationalFundAccount> educationalFundAccounts = santha
					.getEducationalFundAccounts();
			for (IEducationalFundAccount account : educationalFundAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
				}
			}
			session.update(accBalance);
		}

		// Bank Educational Fund Account
		if (santha.getBankEducationalFundAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class,
					Constants.BankEducationalFundAccount);
			Set<IEducationalFundAccount> educationalFundAccounts = santha
					.getBankEducationalFundAccounts();
			for (IEducationalFundAccount account : educationalFundAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
					for(Cheque cheque : ((BankEducationalFundAccount)account).getCheques()){
						if(!cheque.isNew())
						session.update(cheque);
						else
							session.save(cheque);
					}
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
					for(Cheque cheque : ((BankEducationalFundAccount)account).getCheques()){
						cheque.setChequeAmount(cheque.getChequeAmount() - account.getAmount());
						session.update(cheque);
						if(cheque.getChequeAmount() == 0){
							session.delete(cheque);
						}
					}
				}
				
			}
			session.update(accBalance);
		}

		// Special Thanks Offering Account
		if (santha.getSpecialThanksOfferingAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.BuildingAccount);
			Set<ISpecialThanksOfferingAccount> stoAccounts = santha
					.getSpecialThanksOfferingAccounts();
			for (ISpecialThanksOfferingAccount account : stoAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
				}
			}
			session.update(accBalance);
		}

		// Bank Special Thanks Offering Account
		if (santha.getBankSpecialThanksOfferingAccounts().size() > 0) {
			accBalance = (AccountsBalance) session.get(
					AccountsBalance.class, Constants.BankBuildingAccount);
			Set<ISpecialThanksOfferingAccount> stoAccounts = santha
					.getBankSpecialThanksOfferingAccounts();
			for (ISpecialThanksOfferingAccount account : stoAccounts) {
				if(isSave){
					accBalance.setBalance(accBalance.getBalance()
						+ account.getAmount());
					for(Cheque cheque : ((BankBuildingAccount)account).getCheques()){
						if(!cheque.isNew())
							session.update(cheque);
						else
							session.save(cheque);
					}
				} else {
					//Executed during delete Santha
					accBalance.setBalance(accBalance.getBalance()
							- account.getAmount());
					for(Cheque cheque : ((BankBuildingAccount)account).getCheques()){
						cheque.setChequeAmount(cheque.getChequeAmount() - account.getAmount());
						session.update(cheque);
						if(cheque.getChequeAmount() == 0){
							session.delete(cheque);
						}
					}
				}
				
			}
			session.update(accBalance);
		}
	}
	public static void main(String[] args) {
		Member member = new Member();
		member.setId(16);
		
		Santha santha =new Santha();
		santha.setMember(member);
		santha.setPaidDate(new Date());
		santha.setPaidForDate(new Date());
		
		Ledger ledger= new Ledger();
		ledger.setLedgerId(1);
		
		BankPCAccount pcAccount = new BankPCAccount();
		pcAccount.setDate(new Date());
		pcAccount.setDescription("description");
		pcAccount.setCr_dr("CR");
		pcAccount.setLedger(ledger);
		
		Cheque cheque = new Cheque();
		cheque.setNew(false);
		cheque.setChequeId(118);
		cheque.setChequeAmount(400);
		cheque.setChequeNumber("123567");
		cheque.setChequeDate(new Date());
		
		pcAccount.getCheques().add(cheque);
		santha.getBankPCAccounts().add(pcAccount);
		
		SanthaDaoImpl impl = new SanthaDaoImpl();
		/*try {
			impl.paySantha(santha);
		} catch (CcfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
