package com.ccf.vo;

import java.text.SimpleDateFormat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Report extends Santha {

	SimpleIntegerProperty familyNo =  new SimpleIntegerProperty();
	SimpleStringProperty paidDate = new SimpleStringProperty();
	SimpleStringProperty paidForDate = new SimpleStringProperty();

	public int getFamilyNo() {
		return familyNo.getValue();
	}

	public void setFamilyNo(int familyNo) {
		this.familyNo.setValue(familyNo);;
	}

	public String getPaidDate() {
		return paidDate.getValue();
	}

	public void setPaidDate(String paidDate) {
		this.paidDate.setValue(paidDate);
	}

	public String getPaidForDate() {
		return paidForDate.getValue();
	}

	public void setPaidForDate(String paidForDate) {
		this.paidForDate.setValue(paidForDate);
	}
	
	
	
}
