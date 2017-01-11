package com.ccf.vo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MarriedMembers {

	SimpleIntegerProperty familyNo = new SimpleIntegerProperty();
	SimpleStringProperty names = new SimpleStringProperty("");
	SimpleStringProperty mgeDate = new SimpleStringProperty("");
	SimpleStringProperty address = new SimpleStringProperty("");
	SimpleStringProperty phoneNo = new SimpleStringProperty("");
	
	public int getFamilyNo() {
		return familyNo.getValue();
	}
	public void setFamilyNo(int familyNo) {
		this.familyNo.setValue(familyNo);
	}
	public String getNames() {
		return names.getValue();
	}
	public void setNames(String names) {
		this.names.setValue(names);
	}
	public String getAddress() {
		return address.getValue();
	}
	public void setAddress(String address) {
		this.address.setValue(address);
	}
	public String getPhoneNo() {
		return phoneNo.getValue();
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo.setValue(phoneNo);
	}
	public String getMgeDate() {
		return mgeDate.getValue();
	}
	public void setMgeDate(String mgeDate) {
		this.mgeDate.setValue(mgeDate);
	}
	
	
}
