package com.ccf.vo;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class NonPaidMember {

	SimpleIntegerProperty familyNo = new SimpleIntegerProperty();
	SimpleStringProperty name = new SimpleStringProperty("");
	SimpleStringProperty address = new SimpleStringProperty("");
	SimpleLongProperty phoneNo = new SimpleLongProperty();
	SimpleFloatProperty subscriptionAmount = new SimpleFloatProperty();
	
	public int getFamilyNo() {
		return familyNo.getValue();
	}
	public void setFamilyNo(int familyNo) {
		this.familyNo.setValue(familyNo);
	}
	public String getName() {
		return name.getValue();
	}
	public void setName(String name) {
		this.name.setValue(name);
	}
	public String getAddress() {
		return address.getValue();
	}
	public void setAddress(String address) {
		this.address.setValue(address);
	}
	public long getPhoneNo() {
		return phoneNo.getValue();
	}
	public void setPhoneNo(long phoneNo) {
		this.phoneNo.setValue(phoneNo);
	}
	public float getSubscriptionAmount() {
		return subscriptionAmount.getValue();
	}
	public void setSubscriptionAmount(float subscriptionAmount) {
		this.subscriptionAmount.setValue(subscriptionAmount);
	}
	
}
