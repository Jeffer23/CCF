package com.ccf.dao.impl;

import java.awt.image.RescaleOp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.ccf.persistence.classes.Cheque;
import com.ccf.persistence.classes.Family;
import com.ccf.persistence.classes.Member;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.Santha;
import com.ccf.persistence.interfaces.IGraveyardAccount;
import com.ccf.persistence.interfaces.IMensAccount;
import com.ccf.persistence.interfaces.IMissionaryAccount;
import com.ccf.persistence.interfaces.IPCAccount;
import com.ccf.persistence.interfaces.IEducationalFundAccount;
import com.ccf.persistence.interfaces.ISpecialThanksOfferingAccount;
import com.ccf.persistence.interfaces.IWomensAccount;
import com.ccf.persistence.interfaces.IYouthAccount;
import com.ccf.util.AccountNames;
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

			AccountsBalance accBalance = null;
			// PC Account
			if (santha.getPcAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.PCAccount);
				Set<IPCAccount> pcAccounts = santha.getPcAccounts();
				for (IPCAccount account : pcAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Bank PC Account
			if (santha.getBankPCAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.BankPCAccount);
				Set<IPCAccount> pcAccounts = santha.getBankPCAccounts();
				for (IPCAccount account : pcAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Missionary Account
			if (santha.getMissionaryAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.MissionaryAccount);
				Set<IMissionaryAccount> missionaryAccounts = santha
						.getMissionaryAccounts();
				for (IMissionaryAccount account : missionaryAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Bank Missionary Account
			if (santha.getBankMissionaryAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class,
						AccountNames.BankMissionaryAccount);
				Set<IMissionaryAccount> missionaryAccounts = santha
						.getBankMissionaryAccounts();
				for (IMissionaryAccount account : missionaryAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Men's Account
			if (santha.getMensAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.MensAccount);
				Set<IMensAccount> mensAccounts = santha.getMensAccounts();
				for (IMensAccount account : mensAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Bank Mens's Account
			if (santha.getBankMensAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.BankMensAccount);
				Set<IMensAccount> mensAccounts = santha.getBankMensAccounts();
				for (IMensAccount account : mensAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Womens's Account
			if (santha.getWomensAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.WomensAccount);
				Set<IWomensAccount> womensAccounts = santha.getWomensAccounts();
				for (IWomensAccount account : womensAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Bank womens's Account
			if (santha.getBankWomensAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.BankWomensAccount);
				Set<IWomensAccount> womensAccounts = santha
						.getBankWomensAccounts();
				for (IWomensAccount account : womensAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Youth Account
			if (santha.getYouthAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.YouthAccount);
				Set<IYouthAccount> youthAccounts = santha.getYouthAccounts();
				for (IYouthAccount account : youthAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}
			// Bank Youth Account
			if (santha.getBankYouthAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.BankYouthAccount);
				Set<IYouthAccount> youthAccounts = santha
						.getBankYouthAccounts();
				for (IYouthAccount account : youthAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Graveyard Account
			if (santha.getGraveyardAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.GraveyardAccount);
				Set<IGraveyardAccount> graveyardAccounts = santha
						.getGraveyardAccounts();
				for (IGraveyardAccount account : graveyardAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Bank Graveyard Account
			if (santha.getBankGraveyardAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class,
						AccountNames.BankGraveyardAccount);
				Set<IGraveyardAccount> graveyardAccounts = santha
						.getBankGraveyardAccounts();
				for (IGraveyardAccount account : graveyardAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Educational Fund Account
			if (santha.getEducationalFundAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class,
						AccountNames.EducationalFundAccount);
				Set<IEducationalFundAccount> educationalFundAccounts = santha
						.getEducationalFundAccounts();
				for (IEducationalFundAccount account : educationalFundAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Bank Educational Fund Account
			if (santha.getBankEducationalFundAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class,
						AccountNames.BankEducationalFundAccount);
				Set<IEducationalFundAccount> educationalFundAccounts = santha
						.getBankEducationalFundAccounts();
				for (IEducationalFundAccount account : educationalFundAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Special Thanks Offering Account
			if (santha.getSpecialThanksOfferingAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.BuildingAccount);
				Set<ISpecialThanksOfferingAccount> stoAccounts = santha
						.getSpecialThanksOfferingAccounts();
				for (ISpecialThanksOfferingAccount account : stoAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}

			// Bank Special Thanks Offering Account
			if (santha.getBankSpecialThanksOfferingAccounts().size() > 0) {
				accBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.BankBuildingAccount);
				Set<ISpecialThanksOfferingAccount> stoAccounts = santha
						.getBankSpecialThanksOfferingAccounts();
				for (ISpecialThanksOfferingAccount account : stoAccounts) {
					accBalance.setBalance(accBalance.getBalance()
							+ account.getAmount());
				}
				session.update(accBalance);
			}
			transaction.commit();
			session.close();
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
			logger.info("primarySchool" + santha.getPreSchool());
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
			String hql = "UPDATE Santha set bagOffer = :bagOffer, churchRenovation = :churchRenovation , educationHelp = :educationHelp, graveyard = :graveyard, harvestFestival = :harvestFestival, mensFellowship = :mensFellowship, missionary = :missionary,subscriptionAmount = :subscription, poorHelp = :poorHelp, primarySchool = :primarySchool, sto = :sto, thanksOffer = :thanksOffer, womensFellowship = :womensFellowship, youth = :youth, total = :total "
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
			query.setParameter("primarySchool", santha.getPreSchool());
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
			float pcAmount = santha.getHarvestFestival()
					+ santha.getEducationHelp() + santha.getPoorHelp()
					+ santha.getBagOffer() + santha.getSubscriptionAmount();
			float stoAmount = santha.getThanksOffer() + santha.getSto()
					+ santha.getChurchRenovation();

			AccountsBalance accountsBalance = null;
			if (pcAmount > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.PCAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- pcAmount);
				session.update(accountsBalance);
			}

			if (stoAmount > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.BuildingAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- stoAmount);
				session.update(accountsBalance);
			}

			if (santha.getMissionary() > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.MissionaryAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- santha.getMissionary());
				session.update(accountsBalance);
			}

			if (santha.getMensFellowship() > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.MensAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- santha.getMensFellowship());
				session.update(accountsBalance);
			}

			if (santha.getWomensFellowship() > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.WomensAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- santha.getWomensFellowship());
				session.update(accountsBalance);
			}

			if (santha.getPreSchool() > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class,
						AccountNames.EducationalFundAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- santha.getPreSchool());
				session.update(accountsBalance);
			}

			if (santha.getYouth() > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.YouthAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- santha.getYouth());
				session.update(accountsBalance);
			}

			if (santha.getGraveyard() > 0.0f) {
				accountsBalance = (AccountsBalance) session.get(
						AccountsBalance.class, AccountNames.GraveyardAccount);
				accountsBalance.setBalance(accountsBalance.getBalance()
						- santha.getGraveyard());
				session.update(accountsBalance);
			}

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
	public static void main(String[] args) throws CcfException {
		SanthaDaoImpl impl = new SanthaDaoImpl();
		Cheque cheque =impl.getCheque(48);

		System.out.println(cheque.getChequeNumber());

	}
}
