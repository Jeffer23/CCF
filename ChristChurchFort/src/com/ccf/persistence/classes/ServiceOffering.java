package com.ccf.persistence.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ccf.persistence.interfaces.IMissionaryAccount;
import com.ccf.persistence.interfaces.IPCAccount;
import com.ccf.persistence.interfaces.ISpecialThanksOfferingAccount;
import com.ccf.persistence.interfaces.ISundaySchoolAccount;

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
	private Set<IPCAccount> pcAccounts = new LinkedHashSet<>();
	private Set<IPCAccount> bankPCAccounts = new LinkedHashSet<>();
	private Set<IMissionaryAccount> missionaryAccounts = new LinkedHashSet<>();
	private Set<IMissionaryAccount> bankMissionaryAccounts = new LinkedHashSet<>();
	private Set<ISundaySchoolAccount> sundaySchoolAccounts = new LinkedHashSet<>();
	private Set<ISundaySchoolAccount> bankSundaySchoolAccounts = new LinkedHashSet<>();
	private Set<ISpecialThanksOfferingAccount> specialThanksOfferingAccounts = new LinkedHashSet<>();
	private Set<ISpecialThanksOfferingAccount> bankSpecialThanksOfferingAccounts = new LinkedHashSet<>();
	

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

	public Set<IPCAccount> getPcAccounts() {
		return pcAccounts;
	}

	public Set<IMissionaryAccount> getMissionaryAccounts() {
		return missionaryAccounts;
	}

	public Set<ISundaySchoolAccount> getSundaySchoolAccounts() {
		return sundaySchoolAccounts;
	}

	public Set<ISpecialThanksOfferingAccount> getSpecialThanksOfferingAccounts() {
		return specialThanksOfferingAccounts;
	}

	public void setPcAccounts(Set<IPCAccount> pcAccounts) {
		this.pcAccounts = pcAccounts;
	}

	public void setMissionaryAccounts(Set<IMissionaryAccount> missionaryAccounts) {
		this.missionaryAccounts = missionaryAccounts;
	}

	public void setSundaySchoolAccounts(
			Set<ISundaySchoolAccount> sundaySchoolAccounts) {
		this.sundaySchoolAccounts = sundaySchoolAccounts;
	}

	public void setSpecialThanksOfferingAccounts(
			Set<ISpecialThanksOfferingAccount> specialThanksOfferingAccounts) {
		this.specialThanksOfferingAccounts = specialThanksOfferingAccounts;
	}

	public Set<IPCAccount> getBankPCAccounts() {
		return bankPCAccounts;
	}

	public void setBankPCAccounts(Set<IPCAccount> bankPCAccounts) {
		this.bankPCAccounts = bankPCAccounts;
	}

	public Set<IMissionaryAccount> getBankMissionaryAccounts() {
		return bankMissionaryAccounts;
	}

	public void setBankMissionaryAccounts(
			Set<IMissionaryAccount> bankMissionaryAccounts) {
		this.bankMissionaryAccounts = bankMissionaryAccounts;
	}

	public Set<ISundaySchoolAccount> getBankSundaySchoolAccounts() {
		return bankSundaySchoolAccounts;
	}

	public void setBankSundaySchoolAccounts(
			Set<ISundaySchoolAccount> bankSundaySchoolAccounts) {
		this.bankSundaySchoolAccounts = bankSundaySchoolAccounts;
	}

	public Set<ISpecialThanksOfferingAccount> getBankSpecialThanksOfferingAccounts() {
		return bankSpecialThanksOfferingAccounts;
	}

	public void setBankSpecialThanksOfferingAccounts(
			Set<ISpecialThanksOfferingAccount> bankSpecialThanksOfferingAccounts) {
		this.bankSpecialThanksOfferingAccounts = bankSpecialThanksOfferingAccounts;
	}

}
