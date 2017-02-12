package com.ccf.persistence.classes;

import java.util.Date;
import java.util.LinkedHashSet;
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
	private Set<PCAccount> pcAccounts = new LinkedHashSet<>();
	private Set<BankPCAccount> bankPCAccounts = new LinkedHashSet<>();
	private Set<MissionaryAccount> missionaryAccounts = new LinkedHashSet<>();
	private Set<BankMissionaryAccount> bankMissionaryAccounts = new LinkedHashSet<>();
	private Set<SundaySchoolAccount> sundaySchoolAccounts = new LinkedHashSet<>();
	private Set<BankSundaySchoolAccount> bankSundaySchoolAccounts = new LinkedHashSet<>();
	private Set<BuildingAccount> specialThanksOfferingAccounts = new LinkedHashSet<>();
	private Set<BankBuildingAccount> bankSpecialThanksOfferingAccounts = new LinkedHashSet<>();
	

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

	public Set<BankPCAccount> getBankPCAccounts() {
		return bankPCAccounts;
	}

	public void setBankPCAccounts(Set<BankPCAccount> bankPCAccounts) {
		this.bankPCAccounts = bankPCAccounts;
	}

	public Set<MissionaryAccount> getMissionaryAccounts() {
		return missionaryAccounts;
	}

	public void setMissionaryAccounts(Set<MissionaryAccount> missionaryAccounts) {
		this.missionaryAccounts = missionaryAccounts;
	}

	public Set<BankMissionaryAccount> getBankMissionaryAccounts() {
		return bankMissionaryAccounts;
	}

	public void setBankMissionaryAccounts(
			Set<BankMissionaryAccount> bankMissionaryAccounts) {
		this.bankMissionaryAccounts = bankMissionaryAccounts;
	}

	public Set<SundaySchoolAccount> getSundaySchoolAccounts() {
		return sundaySchoolAccounts;
	}

	public void setSundaySchoolAccounts(
			Set<SundaySchoolAccount> sundaySchoolAccounts) {
		this.sundaySchoolAccounts = sundaySchoolAccounts;
	}

	public Set<BankSundaySchoolAccount> getBankSundaySchoolAccounts() {
		return bankSundaySchoolAccounts;
	}

	public void setBankSundaySchoolAccounts(
			Set<BankSundaySchoolAccount> bankSundaySchoolAccounts) {
		this.bankSundaySchoolAccounts = bankSundaySchoolAccounts;
	}

	public Set<BuildingAccount> getSpecialThanksOfferingAccounts() {
		return specialThanksOfferingAccounts;
	}

	public void setSpecialThanksOfferingAccounts(
			Set<BuildingAccount> specialThanksOfferingAccounts) {
		this.specialThanksOfferingAccounts = specialThanksOfferingAccounts;
	}

	public Set<BankBuildingAccount> getBankSpecialThanksOfferingAccounts() {
		return bankSpecialThanksOfferingAccounts;
	}

	public void setBankSpecialThanksOfferingAccounts(
			Set<BankBuildingAccount> bankSpecialThanksOfferingAccounts) {
		this.bankSpecialThanksOfferingAccounts = bankSpecialThanksOfferingAccounts;
	}

	

}
