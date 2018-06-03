package com.ccf.persistence.interfaces;

import com.ccf.persistence.classes.Santha;
import com.ccf.persistence.classes.ServiceOffering;

public interface IPCAccount  extends Account{


	
	public ServiceOffering getServiceOffering();

	public void setServiceOffering(ServiceOffering serviceOffering);

	public Santha getSantha();

	public void setSantha(Santha santha);
}
