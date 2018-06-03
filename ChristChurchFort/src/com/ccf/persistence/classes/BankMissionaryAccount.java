package com.ccf.persistence.classes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ccf.persistence.interfaces.Account;
import com.ccf.persistence.interfaces.IMissionaryAccount;

public class BankMissionaryAccount implements Account, IMissionaryAccount {

	private float amount;
	private String description;
	private String cr_dr;
	private int id;
	private ServiceOffering serviceOffering;
	private Santha santha;
	private Date date;
	private Set<Cheque> cheques = new HashSet<>();
	private Ledger ledger;
	private float balance;

	public Ledger getLedger() {
		return ledger;
	}

	public void setLedger(Ledger ledger) {
		this.ledger = ledger;
	}

	public Set<Cheque> getCheques() {
		return cheques;
	}

	public void setCheques(Set<Cheque> cheques) {
		this.cheques = cheques;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCr_dr() {
		return cr_dr;
	}

	public void setCr_dr(String cr_dr) {
		this.cr_dr = cr_dr;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ServiceOffering getServiceOffering() {
		return serviceOffering;
	}

	public void setServiceOffering(ServiceOffering serviceOffering) {
		this.serviceOffering = serviceOffering;
	}

	public Santha getSantha() {
		return santha;
	}

	public void setSantha(Santha santha) {
		this.santha = santha;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}
	
}
