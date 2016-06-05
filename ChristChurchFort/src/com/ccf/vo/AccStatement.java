package com.ccf.vo;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AccStatement {

	private SimpleStringProperty date = new SimpleStringProperty("");
	private SimpleStringProperty description = new SimpleStringProperty("");
	private SimpleFloatProperty amount = new SimpleFloatProperty();
	
	public String getDate() {
		return date.getValue();
	}
	public void setDate(String date) {
		this.date.set(date);
	}
	public String getDescription() {
		return description.getValue();
	}
	public void setDescription(String description) {
		this.description.set(description);
	}
	public float getAmount() {
		return amount.getValue();
	}
	public void setAmount(float amount) {
		this.amount.set(amount);
	}
	
	
}
