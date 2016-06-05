package com.ccf.vo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BirthdayMember {

	SimpleIntegerProperty familyNo = new SimpleIntegerProperty();
	SimpleStringProperty name = new SimpleStringProperty("");
	SimpleStringProperty dob = new SimpleStringProperty("");
	SimpleIntegerProperty age = new SimpleIntegerProperty();
	SimpleStringProperty address = new SimpleStringProperty("");
	SimpleStringProperty phoneNo = new SimpleStringProperty("");
	
	
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
	public String getDob() {
		return dob.getValue();
	}
	public void setDob(String dob) {
		this.dob.setValue(dob);
	}
	public int getAge() {
		return age.getValue();
	}
	public void setAge(int age) {
		this.age.setValue(age);
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
	
	
}
