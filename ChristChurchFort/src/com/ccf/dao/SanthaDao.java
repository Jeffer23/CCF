package com.ccf.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Cheque;
import com.ccf.persistence.classes.Santha;

public interface SanthaDao {

	int paySantha(Santha santha,Session session) throws CcfException;
	List<Santha> getPaidMembers(int familyNo, Date fromDate, Date toDate) throws CcfException;
	void updateSantha(Santha santha) throws CcfException;
	void deleteSantha(int santhaId) throws CcfException;
	Santha getLastPaidAmount(int familyNo, String memberName, Date fromDate, Date toDate) throws CcfException;
	List<Santha> getReport(Date fromDate, Date toDate) throws CcfException;
	Santha getSantha(int santhaId) throws CcfException;
	Cheque getCheque(int SanthaId) throws CcfException;
	public Cheque getCheque(String chequeNumber, Session session) throws CcfException;
}
