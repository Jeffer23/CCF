package com.ccf.persistence.interfaces;

import java.util.Date;

import com.ccf.persistence.classes.Ledger;

public interface Account {

	void setAmount(float amount);
	void setDescription(String description);
	void setCr_dr(String cr_dr);
	void setDate(Date date);
	Date getDate();
	float getAmount();
	String getDescription();
	String getCr_dr();
	public Ledger getLedger();
	public void setLedger(Ledger ledger);
	public float getBalance();
	public void setBalance(float balance);
	public int getId();
	public void setId(int id);
}
