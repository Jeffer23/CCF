package com.ccf.persistence.classes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ccf.persistence.interfaces.ISpecialThanksOfferingAccount;
import com.ccf.vo.Account;

public class BankSpecialThanksOfferingAccount implements Account,
		ISpecialThanksOfferingAccount {

	private int id;
	private float amount;
	private String description;
	private String cr_dr;
	private ServiceOffering serviceOffering;
	private Santha santha;
	private Date date;
	private Set<Cheque> cheques = new HashSet<>();

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
}
