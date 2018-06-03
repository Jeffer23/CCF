package com.ccf.persistence.interfaces;

import com.ccf.persistence.classes.ServiceOffering;

public interface ISundaySchoolAccount extends Account {

	public int getId();

	public void setId(int id);

	public ServiceOffering getServiceOffering();

	public void setServiceOffering(ServiceOffering serviceOffering);
}
