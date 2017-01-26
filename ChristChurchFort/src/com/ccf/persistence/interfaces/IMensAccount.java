package com.ccf.persistence.interfaces;

import java.util.Date;

import com.ccf.persistence.classes.Santha;

public interface IMensAccount {

	public Date getDate();

	public void setDate(Date date);

	public float getAmount();

	public void setAmount(float amount);


	public String getDescription();

	public void setDescription(String description);

	public String getCr_dr();

	public void setCr_dr(String cr_dr);

	public Santha getSantha();

	public void setSantha(Santha santha);

	public int getId();

	public void setId(int id);
}