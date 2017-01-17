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
	
	private Set<BankSpecialThanksOfferingAccount> bankSTOAccounts = new HashSet<>();

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

	
	
	
}
