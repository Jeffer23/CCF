package com.ccf.persistence.interfaces;

import java.util.Date;

import com.ccf.persistence.classes.Santha;
import com.ccf.persistence.classes.ServiceOffering;

public interface IMissionaryAccount {
	public Date getDate();

	public void setDate(Date date);

	public float getAmount();

	public void setAmount(float amount);

	public float getBalance();

	public void setBalance(float balance);

	public String getDescription();

	public void setDescription(String description);

	public String getCr_dr();

	public void setCr_dr(String cr_dr);

	public int getId();

	public void setId(int id);

	public ServiceOffering getServiceOffering();

	public void setServiceOffering(ServiceOffering serviceOffering);

	public Santha getSantha();

	public void setSantha(Santha santha);
}
