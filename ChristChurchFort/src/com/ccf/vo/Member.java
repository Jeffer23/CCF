package com.ccf.vo;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Member {

	private SimpleIntegerProperty id = new SimpleIntegerProperty();
	private SimpleStringProperty name = new SimpleStringProperty("");
	private SimpleStringProperty dob = new SimpleStringProperty("");
	private SimpleStringProperty livedTill = new SimpleStringProperty("");
	private SimpleStringProperty marriageDate = new SimpleStringProperty("");
	private SimpleStringProperty eligibility = new SimpleStringProperty("");
	private SimpleIntegerProperty age = new SimpleIntegerProperty();
	private SimpleFloatProperty subscriptionAmount = new SimpleFloatProperty();
	
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public String getDob() {
		return dob.get();
	}
	public void setDob(String dob) {
		this.dob.set(dob);
	}
	public String getEligibility() {
		return eligibility.get();
	}
	public void setEligibility(String eligibility) {
		this.eligibility.set(eligibility);
	}
	public int getAge() {
		return age.get();
	}
	public void setAge(int age) {
		this.age.set(age);
	}
	public int getId() {
		return id.getValue();
	}
	public void setId(int id) {
		this.id.set(id);
	}
	public float getSubscriptionAmount() {
		return subscriptionAmount.getValue();
	}
	public void setSubscriptionAmount(float subscriptionAmount) {
		this.subscriptionAmount.setValue(subscriptionAmount);
	}
	public String getLivedTill() {
		return livedTill.getValue();
	}
	public void setLivedTill(String livedTill) {
		this.livedTill.setValue(livedTill);
	}
	public String getMarriageDate() {
		return marriageDate.getValue();
	}
	public void setMarriageDate(String marriageDate) {
		this.marriageDate.setValue(marriageDate);
	}
	
	
	
	
}
