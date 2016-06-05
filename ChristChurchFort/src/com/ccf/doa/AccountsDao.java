package com.ccf.doa;

import java.util.Date;
import java.util.List;

import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.GraveyardAccount;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.PrimarySchoolAccount;
import com.ccf.persistence.classes.SpecialThanksOfferingAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.vo.Account;

public interface AccountsDao {

	public float getPCAccountBalance() throws CcfException;
	public float getMissionaryAccountBalance() throws CcfException;
	public float getMensAccountBalance() throws CcfException;
	public float getWomensAccountBalance() throws CcfException;
	public float getSundaySchoolAccountBalance() throws CcfException;
	public float getYouthAccountBalance() throws CcfException;
	public float getSpecialThanksOfferingAccountBalance() throws CcfException;
	public float getGraveyardAccountBalance() throws CcfException;
	public float getPrimarySchoolAccountBalance() throws CcfException;
	public void addIncomeorExpense(Account account) throws CcfException;
	public List<PCAccount> getPCAccountsAfter(int id) throws CcfException;
	public List<MissionaryAccount> getMissionaryAccountsAfter(int id) throws CcfException;
	public List<MensAccount> getMensAccountsAfter(int id) throws CcfException;
	public List<WomensAccount> getWomensAccountsAfter(int id) throws CcfException;
	public List<SpecialThanksOfferingAccount> getSTOAccountsAfter(int id) throws CcfException;
	public List<PrimarySchoolAccount> getPrimarySchoolAccountsAfter(int id) throws CcfException;
	public List<YouthAccount> getYouthAccountsAfter(int id) throws CcfException;
	public List<GraveyardAccount> getGraveyardAccountsAfter(int id) throws CcfException;
	public void updatePCAccount(List<PCAccount> pcAccounts) throws CcfException; 
	public void updateMissionaryAccount(List<MissionaryAccount> missionaryAccounts) throws CcfException; 
	public void updateMensAccount(List<MensAccount> mensAccounts) throws CcfException; 
	public void updateWomensAccount(List<WomensAccount> womensAccounts) throws CcfException; 
	public void updateSpecialThanksOfferingAccount(List<SpecialThanksOfferingAccount> stoAccounts) throws CcfException; 
	public void updatePrimarySchoolAccount(List<PrimarySchoolAccount> primarySchoolAccounts) throws CcfException; 
	public void updateYouthAccount(List<YouthAccount> youthAccounts) throws CcfException; 
	public void updateGraveyardAccount(List<GraveyardAccount> graveyardAccounts) throws CcfException;
	public List<PCAccount> getPCAccountStatement(Date from, Date to ) throws CcfException;
	public List<MissionaryAccount> getMissionaryStatement(Date from, Date to ) throws CcfException;
	public List<MensAccount> getMensStatement(Date from, Date to ) throws CcfException;
	public List<WomensAccount> getWomensStatement(Date from, Date to ) throws CcfException;
	public List<SundaySchoolAccount> getSundaySchoolStatement(Date from, Date to ) throws CcfException;
	public List<YouthAccount> getYouthStatement(Date from, Date to ) throws CcfException;
	public List<SpecialThanksOfferingAccount> getSTOStatement(Date from, Date to ) throws CcfException;
	public List<GraveyardAccount> getGraveyardStatement(Date from, Date to ) throws CcfException;
	public List<PrimarySchoolAccount> getPrimarySchoolStatement(Date from, Date to ) throws CcfException;
	
	
}
