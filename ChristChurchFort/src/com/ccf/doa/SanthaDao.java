package com.ccf.doa;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Santha;

public interface SanthaDao {

	int paySantha(Santha santha) throws CcfException;
	List<Santha> getPaidMembers(int familyNo, Date fromDate, Date toDate, Session session) throws CcfException;
	void updateSantha(Santha santha) throws CcfException;
	void deleteSantha(Santha santha) throws CcfException;
	Santha getLastPaidAmount(int familyNo, String memberName, Date fromDate, Date toDate) throws CcfException;
	List<Santha> getReport(Date fromDate, Date toDate,Session session) throws CcfException;
	Santha getSantha(int santhaId) throws CcfException;
}
