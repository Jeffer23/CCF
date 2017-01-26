package com.ccf.persistence.classes;

import java.util.HashSet;
import java.util.Set;

public class Ledger {

	private int ledgerId;
	private String ledgerName;
	private Set<BankGraveyardAccount> bankGraveyardAccounts = new HashSet<>();
	private Set<BankMensAccount> bankMensAccounts = new HashSet<>();
	private Set<BankMissionaryAccount> bankMissionaryAccounts = new HashSet<>();
	private Set<BankPCAccount> bankPCAccounts = new HashSet<>();
	private Set<BankPrimarySchoolAccount> bankPrimarySchoolAccounts = new HashSet<>();
	private Set<BankSpecialThanksOfferingAccount> bankSTOAccounts = new HashSet<>();
	private Set<BankSundaySchoolAccount> bankSundaySchoolAccounts = new HashSet<>();
	private Set<BankWomensAccount> bankWomensAccounts = new HashSet<>();
	private Set<BankYouthAccount> bankYouthAccounts = new HashSet<>();
	private Set<GraveyardAccount> graveyardAccounts = new HashSet<>();
	private Set<MensAccount> mensAccounts = new HashSet<>();
	private Set<MissionaryAccount> missionaryAccounts = new HashSet<>();
	private Set<PCAccount> pcAccounts = new HashSet<>();
	private Set<PrimarySchoolAccount> primarySchoolAccounts = new HashSet<>();
	private Set<SpecialThanksOfferingAccount> stoAccounts = new HashSet<>();
	private Set<SundaySchoolAccount> sundaySchoolAccounts = new HashSet<>();
	private Set<WomensAccount> womensAccounts = new HashSet<>();
	private Set<YouthAccount> youthAccounts = new HashSet<>();
	
	
	
	public Set<BankGraveyardAccount> getBankGraveyardAccounts() {
		return bankGraveyardAccounts;
	}
	public void setBankGraveyardAccounts(
			Set<BankGraveyardAccount> bankGraveyardAccounts) {
		this.bankGraveyardAccounts = bankGraveyardAccounts;
	}
	public Set<BankMensAccount> getBankMensAccounts() {
		return bankMensAccounts;
	}
	public void setBankMensAccounts(Set<BankMensAccount> bankMensAccounts) {
		this.bankMensAccounts = bankMensAccounts;
	}
	public Set<BankMissionaryAccount> getBankMissionaryAccounts() {
		return bankMissionaryAccounts;
	}
	public void setBankMissionaryAccounts(
			Set<BankMissionaryAccount> bankMissionaryAccounts) {
		this.bankMissionaryAccounts = bankMissionaryAccounts;
	}
	public Set<BankPCAccount> getBankPCAccounts() {
		return bankPCAccounts;
	}
	public void setBankPCAccounts(Set<BankPCAccount> bankPCAccounts) {
		this.bankPCAccounts = bankPCAccounts;
	}
	public Set<BankPrimarySchoolAccount> getBankPrimarySchoolAccounts() {
		return bankPrimarySchoolAccounts;
	}
	public void setBankPrimarySchoolAccounts(
			Set<BankPrimarySchoolAccount> bankPrimarySchoolAccounts) {
		this.bankPrimarySchoolAccounts = bankPrimarySchoolAccounts;
	}
	public Set<BankSpecialThanksOfferingAccount> getBankSTOAccounts() {
		return bankSTOAccounts;
	}
	public void setBankSTOAccounts(
			Set<BankSpecialThanksOfferingAccount> bankSTOAccounts) {
		this.bankSTOAccounts = bankSTOAccounts;
	}
	public Set<BankSundaySchoolAccount> getBankSundaySchoolAccounts() {
		return bankSundaySchoolAccounts;
	}
	public void setBankSundaySchoolAccounts(
			Set<BankSundaySchoolAccount> bankSundaySchoolAccounts) {
		this.bankSundaySchoolAccounts = bankSundaySchoolAccounts;
	}
	public Set<BankWomensAccount> getBankWomensAccounts() {
		return bankWomensAccounts;
	}
	public void setBankWomensAccounts(Set<BankWomensAccount> bankWomensAccounts) {
		this.bankWomensAccounts = bankWomensAccounts;
	}
	public Set<BankYouthAccount> getBankYouthAccounts() {
		return bankYouthAccounts;
	}
	public void setBankYouthAccounts(Set<BankYouthAccount> bankYouthAccounts) {
		this.bankYouthAccounts = bankYouthAccounts;
	}
	public Set<GraveyardAccount> getGraveyardAccounts() {
		return graveyardAccounts;
	}
	public void setGraveyardAccounts(Set<GraveyardAccount> graveyardAccounts) {
		this.graveyardAccounts = graveyardAccounts;
	}
	public Set<MensAccount> getMensAccounts() {
		return mensAccounts;
	}
	public void setMensAccounts(Set<MensAccount> mensAccounts) {
		this.mensAccounts = mensAccounts;
	}
	public Set<MissionaryAccount> getMissionaryAccounts() {
		return missionaryAccounts;
	}
	public void setMissionaryAccounts(Set<MissionaryAccount> missionaryAccounts) {
		this.missionaryAccounts = missionaryAccounts;
	}
	public Set<PCAccount> getPcAccounts() {
		return pcAccounts;
	}
	public void setPcAccounts(Set<PCAccount> pcAccounts) {
		this.pcAccounts = pcAccounts;
	}
	public Set<PrimarySchoolAccount> getPrimarySchoolAccounts() {
		return primarySchoolAccounts;
	}
	public void setPrimarySchoolAccounts(
			Set<PrimarySchoolAccount> primarySchoolAccounts) {
		this.primarySchoolAccounts = primarySchoolAccounts;
	}
	public Set<SpecialThanksOfferingAccount> getStoAccounts() {
		return stoAccounts;
	}
	public void setStoAccounts(Set<SpecialThanksOfferingAccount> stoAccounts) {
		this.stoAccounts = stoAccounts;
	}
	public Set<SundaySchoolAccount> getSundaySchoolAccounts() {
		return sundaySchoolAccounts;
	}
	public void setSundaySchoolAccounts(
			Set<SundaySchoolAccount> sundaySchoolAccounts) {
		this.sundaySchoolAccounts = sundaySchoolAccounts;
	}
	public Set<WomensAccount> getWomensAccounts() {
		return womensAccounts;
	}
	public void setWomensAccounts(Set<WomensAccount> womensAccounts) {
		this.womensAccounts = womensAccounts;
	}
	public Set<YouthAccount> getYouthAccounts() {
		return youthAccounts;
	}
	public void setYouthAccounts(Set<YouthAccount> youthAccounts) {
		this.youthAccounts = youthAccounts;
	}
	public int getLedgerId() {
		return ledgerId;
	}
	public void setLedgerId(int ledgerId) {
		this.ledgerId = ledgerId;
	}
	public String getLedgerName() {
		return ledgerName;
	}
	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}
	@Override
	public String toString() {
		return ledgerName;
	}
	
	
}
