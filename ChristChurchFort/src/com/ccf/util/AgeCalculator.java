package com.ccf.util;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ccf.dao.impl.FamilyDaoImpl;

public class AgeCalculator {

	final static Logger logger = Logger.getLogger(AgeCalculator.class);
	
	public static int calculateAge(Date dob) throws Exception{
		System.out.println("Calculate Age method Starts...");
		int age = 0;
		int bornYear = dob.getYear();
		int bornMonth = dob.getMonth();
		int bornDate = dob.getDate();
		
		Date today = new Date();
		int thisYear = today.getYear();
		int thisMonth = today.getMonth();
		int thisDate = today.getDate();
		
		age = thisYear - bornYear;
		if(bornMonth < thisMonth){
			return age;
		}
		else if(bornMonth > thisMonth){
			return --age;
		}
		else if(bornMonth == thisMonth){
			if (bornDate > thisDate){
				return --age;
			}
			else {
				return age;
			}
		}
		
		if(age < 0){
			throw new Exception("Select the date Properly");
		}
		System.out.println("Calculate Age method Ends...");
		return age;
	}
}
