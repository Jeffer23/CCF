package com.ccf.dao;

import java.util.List;

import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Family;

public interface FamilyDao {

	void saveFamily(Family family) throws CcfException;
	void updateFamily(Family family) throws CcfException;
	boolean isFamilyExists(int familyNo) throws CcfException;
	List<Integer> getFamilyNos() throws CcfException;
	List<Integer> getFilteredFamilyNos(int familyNosStartsWith) throws CcfException;
	Family getFamily(int familyNo) throws CcfException;
}
