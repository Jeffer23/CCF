package com.ccf.persistence.classes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Cheque {

	private int chequeId;
	
	private String chequeNumber;
	
	private Date chequeDate;
	
	private float chequeAmount;
	
	private Set<BankPCAccount> bankPCAccounts = new HashSet<>();
	
	private Set<BankGraveyardAccount> bankGraveyardAccounts = new HashSet<>();
	
	private Set<BankMensAccount> bankMensAccounts = new HashSet<>();
	
	private Set<BankMissionaryAccount> bankMissionaryAccounts = new HashSet<>();
	
	private Set<BankPrimarySchoolAccount> bankPrimarySchoolAccounts = new HashSet<>();
	
	private Set<BankSpecialThanksOfferingAccount> bankSTOAccounts = new HashSet<>();
	
	private Set<BankWomensAccount> bankWomensAccounts = new HashSet<>();
	
	private Set<BankSundaySchoolAccount> bankSundaySchoolAccounts = new HashSet<>();
	
	private Set<BankYouthAccount> bankYouthAccounts = new HashSet<>();

	public int getChequeId() {
		return chequeId;
	}

	public void setChequeId(int chequeId) {
		this.chequeId = chequeId;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public float getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(float chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public Set<BankPCAccount> getBankPCAccounts() {
		return bankPCAccounts;
	}

	public void setBankPCAccounts(Set<BankPCAccount> bankPCAccounts) {
		this.bankPCAccounts = bankPCAccounts;
	}

	public Set<BankSpecialThanksOfferingAccount> getBankSTOAccounts() {
		return bankSTOAccounts;
	}

	public void setBankSTOAccounts(
			Set<BankSpecialThanksOfferingAccount> bankSTOAccounts) {
		this.bankSTOAccounts = bankSTOAccounts;
	}

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

	public Set<BankPrimarySchoolAccount> getBankPrimarySchoolAccounts() {
		return bankPrimarySchoolAccounts;
	}

	public void setBankPrimarySchoolAccounts(
			Set<BankPrimarySchoolAccount> bankPrimarySchoolAccounts) {
		this.bankPrimarySchoolAccounts = bankPrimarySchoolAccounts;
	}

	public Set<BankWomensAccount> getBankWomensAccounts() {
		return bankWomensAccounts;
	}

	public void setBankWomensAccounts(Set<BankWomensAccount> bankWomensAccounts) {
		this.bankWomensAccounts = bankWomensAccounts;
	}

	public Set<BankSundaySchoolAccount> getBankSundaySchoolAccounts() {
		return bankSundaySchoolAccounts;
	}

	public void setBankSundaySchoolAccounts(
			Set<BankSundaySchoolAccount> bankSundaySchoolAccounts) {
		this.bankSundaySchoolAccounts = bankSundaySchoolAccounts;
	}

	public Set<BankYouthAccount> getBankYouthAccounts() {
		return bankYouthAccounts;
	}

	public void setBankYouthAccounts(Set<BankYouthAccount> bankYouthAccounts) {
		this.bankYouthAccounts = bankYouthAccounts;
	}

	
	
	
}
