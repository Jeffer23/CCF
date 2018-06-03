package com.ccf.persistence.interfaces;


import com.ccf.persistence.classes.Santha;

public interface IMensAccount extends Account {

	public Santha getSantha();

	public void setSantha(Santha santha);

	public int getId();

	public void setId(int id);
}
