package com.ccf.persistence.classes;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ServiceOffering {

	private int id;
	private Date date;
	private String time;
	private float serviceOffering;
	private float thanksOffering;
	private float specialThanksOffering;
	private float missionary;
	private float auction;
	private float confirmation;
	private float sundaySchool;
	private float marriage;
	private float others;
	private String otherReason;
	private Set<PCAccount> pcAccounts = new LinkedHashSet<PCAccount>();
	private Set<MissionaryAccount> missionaryAccounts = new LinkedHashSet<MissionaryAccount>();
	private Set<SundaySchoolAccount> sundaySchoolAccounts = new LinkedHashSet<SundaySchoolAccount>();
	private Set<SpecialThanksOfferingAccount> specialThanksOfferingAccounts = new LinkedHashSet<>();
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public float getServiceOffering() {
		return serviceOffering;
	}
	public void setServiceOffering(float serviceOffering) {
		this.serviceOffering = serviceOffering;
	}
	public float getThanksOffering() {
		return thanksOffering;
	}
	public void setThanksOffering(float thanksOffering) {
		this.thanksOffering = thanksOffering;
	}
	public float getSpecialThanksOffering() {
		return specialThanksOffering;
	}
	public void setSpecialThanksOffering(float specialThanksOffering) {
		this.specialThanksOffering = specialThanksOffering;
	}
	public float getMissionary() {
		return missionary;
	}
	public void setMissionary(float missionary) {
		this.missionary = missionary;
	}
	public float getAuction() {
		return auction;
	}
	public void setAuction(float auction) {
		this.auction = auction;
	}
	public float getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(float confirmation) {
		this.confirmation = confirmation;
	}
	public float getSundaySchool() {
		return sundaySchool;
	}
	public void setSundaySchool(float sundaySchool) {
		this.sundaySchool = sundaySchool;
	}
	public float getMarriage() {
		return marriage;
	}
	public void setMarriage(float marriage) {
		this.marriage = marriage;
	}
	public float getOthers() {
		return others;
	}
	public void setOthers(float others) {
		this.others = others;
	}
	
	public String getOtherReason() {
		return otherReason;
	}
	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}
	
	public Set<PCAccount> getPcAccounts() {
		return pcAccounts;
	}
	public void setPcAccounts(Set<PCAccount> pcAccounts) {
		this.pcAccounts = pcAccounts;
	}
	public Set<MissionaryAccount> getMissionaryAccounts() {
		return missionaryAccounts;
	}
	public void setMissionaryAccounts(Set<MissionaryAccount> missionaryAccounts) {
		this.missionaryAccounts = missionaryAccounts;
	}
	
	public Set<SundaySchoolAccount> getSundaySchoolAccounts() {
		return sundaySchoolAccounts;
	}
	public void setSundaySchoolAccounts(
			Set<SundaySchoolAccount> sundaySchoolAccounts) {
		this.sundaySchoolAccounts = sundaySchoolAccounts;
	}
	public Set<SpecialThanksOfferingAccount> getSpecialThanksOfferingAccounts() {
		return specialThanksOfferingAccounts;
	}
	public void setSpecialThanksOfferingAccounts(
			Set<SpecialThanksOfferingAccount> specialThanksOfferingAccounts) {
		this.specialThanksOfferingAccounts = specialThanksOfferingAccounts;
	}
	
	
	
}
