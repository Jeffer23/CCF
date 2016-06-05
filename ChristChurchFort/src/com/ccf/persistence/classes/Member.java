package com.ccf.persistence.classes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Member {

	private int id;
	private String name;
	private Date dob;
	private Date livedTill;
	private String eligibility;
	private float subscriptionAmount;
	private Family family;
	private Set<Santha> santhas = new HashSet<Santha>(0);
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getEligibility() {
		return eligibility;
	}
	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}
	public float getSubscriptionAmount() {
		return subscriptionAmount;
	}
	public void setSubscriptionAmount(float subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}
	public Family getFamily() {
		return family;
	}
	public void setFamily(Family family) {
		this.family = family;
	}
	public Set<Santha> getSanthas() {
		return santhas;
	}
	public void setSanthas(Set<Santha> santhas) {
		this.santhas = santhas;
	}
	
	public Date getLivedTill() {
		return livedTill;
	}
	public void setLivedTill(Date livedTill) {
		this.livedTill = livedTill;
	}
	
	
}
