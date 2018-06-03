package com.ccf.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ccf.exception.CcfException;
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

public interface AccountsDao {

	public void addIncomeorExpense(Account account, String accountName, float amount) throws CcfException;
	public List<PCAccount> getPCAccountsAfter(int id) throws CcfException;
	public List<MissionaryAccount> getMissionaryAccountsAfter(int id) throws CcfException;
	public List<MensAccount> getMensAccountsAfter(int id) throws CcfException;
	public List<WomensAccount> getWomensAccountsAfter(int id) throws CcfException;
	public List<BuildingAccount> getSTOAccountsAfter(int id) throws CcfException;
	public List<EducationalFundAccount> getEducationalFundAccountsAfter(int id) throws CcfException;
	public List<YouthAccount> getYouthAccountsAfter(int id) throws CcfException;
	public List<GraveyardAccount> getGraveyardAccountsAfter(int id) throws CcfException;
	public void updatePCAccount(List<PCAccount> pcAccounts) throws CcfException; 
	public void updateMissionaryAccount(List<MissionaryAccount> missionaryAccounts) throws CcfException; 
	public void updateMensAccount(List<MensAccount> mensAccounts) throws CcfException; 
	public void updateWomensAccount(List<WomensAccount> womensAccounts) throws CcfException; 
	public void updateSpecialThanksOfferingAccount(List<BuildingAccount> stoAccounts) throws CcfException; 
	public void updateEducationalFundAccount(List<EducationalFundAccount> educationalFundAccounts) throws CcfException; 
	public void updateYouthAccount(List<YouthAccount> youthAccounts) throws CcfException; 
	public void updateGraveyardAccount(List<GraveyardAccount> graveyardAccounts) throws CcfException;
	public List<Account> getAccountStatement(Class entity, Date from, Date to ) throws CcfException;
	public int addLedger(Ledger ledger) throws CcfException;
	public List<Ledger> getAllLedgers(String startsWithValue) throws CcfException;
	/**
	 * This method is used to get all the Ledger records expects that starts with 'Service - ' and 'Santha - '
	 * @return
	 * @throws CcfException
	 */
	public List<Ledger> getAllLedgers() throws CcfException;
	public boolean isChequeExists(String chequeNumber) throws CcfException;
	public void withdrawOrDeposit(Account creditAcc, String creditAccName, Account debitAcc, String debitAccName, float amount) throws CcfException;
	/**
	 * This method is used to get the account balance from the respective Accounts table
	 */
	public float getCurrentAccountBalance(Class entity) throws CcfException;
	/**
	 * This method is used to get the account balance from the respective Accounts table and for the given date.
	 */
	public float getAccountBalance(Class entity, Date date) throws CcfException;
	public Map<String, Float> getOpeningAndClosingBalance(Class entity, Date startDate, Date endDate) throws CcfException;
	public void updateBalance(Class entity) throws CcfException;
	
	
}
