package com.ccf.persistence.classes;

import java.util.Date;

import com.ccf.vo.Account;

public class PrimarySchoolAccount implements Account {

	private int id;
	private float amount;
	private float balance;
	private String description;
	private String cr_dr;
	private Santha santha;
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
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

	public Santha getSantha() {
		return santha;
	}

	public void setSantha(Santha santha) {
		this.santha = santha;
	}
}
