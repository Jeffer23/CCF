package com.ccf.dao;

import java.util.Date;

import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.ServiceOffering;

public interface ServiceOfferingDao {

	public void saveServiceOffering (ServiceOffering serviceOffering) throws CcfException;
	public boolean isAlreadyExists (Date date, String time);
}
