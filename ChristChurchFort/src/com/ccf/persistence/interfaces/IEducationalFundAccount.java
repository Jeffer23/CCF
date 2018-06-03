package com.ccf.persistence.interfaces;


import com.ccf.persistence.classes.Santha;

public interface IEducationalFundAccount extends Account {

	public int getId();

	public void setId(int id);

	public Santha getSantha();

	public void setSantha(Santha santha);
}
