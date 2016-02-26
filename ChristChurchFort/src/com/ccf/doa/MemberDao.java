package com.ccf.doa;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Member;

public interface MemberDao {

	void addMember(Member member) throws CcfException;
	void updateMember(Member member) throws CcfException;
	void deleteMember(Member member) throws CcfException;
	List<Member> getMembers(int familyNo) throws CcfException;
	List<String> getMemberNames(int familyNo) throws CcfException;
	int getMemberId(String memberName, int familyNo) throws CcfException;
	Member getMember(int memberId) throws CcfException;
	float getSubscriptionAmount(int familyNo, String memberName) throws CcfException;
	List<Member> getBirthdayMembers(Date fromDate, Date toDate, Session session) throws CcfException;
	List<Member> getNonPaidMember(Date fromDate, Date toDate, Session session) throws CcfException;
	
}
