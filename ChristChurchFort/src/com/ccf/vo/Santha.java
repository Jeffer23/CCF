package com.ccf.vo;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Santha {

	SimpleIntegerProperty santhaId =  new SimpleIntegerProperty();
	SimpleStringProperty name = new SimpleStringProperty("");
	SimpleFloatProperty subscription = new SimpleFloatProperty();
	SimpleFloatProperty harvestFestival = new SimpleFloatProperty();
	SimpleFloatProperty missionary = new SimpleFloatProperty();
	SimpleFloatProperty mensFellowship = new SimpleFloatProperty();
	SimpleFloatProperty womensFellowship = new SimpleFloatProperty();
	SimpleFloatProperty educationHelp = new SimpleFloatProperty();
	SimpleFloatProperty primarySchool = new SimpleFloatProperty();
	SimpleFloatProperty youth = new SimpleFloatProperty();
	SimpleFloatProperty poorHelp = new SimpleFloatProperty();
	SimpleFloatProperty churchRenovation = new SimpleFloatProperty();
	SimpleFloatProperty graveyard = new SimpleFloatProperty();
	SimpleFloatProperty bagOffer = new SimpleFloatProperty();
	SimpleFloatProperty thanksOffer = new SimpleFloatProperty();
	SimpleFloatProperty sto = new SimpleFloatProperty();
	SimpleFloatProperty other1 = new SimpleFloatProperty();
	SimpleFloatProperty other2 = new SimpleFloatProperty();
	SimpleFloatProperty total = new SimpleFloatProperty();
	
	
	public String getName() {
		return name.getValue();
	}
	public void setName(String name) {
		this.name.setValue(name);
	}
	public float getSubscription() {
		return subscription.getValue();
	}
	public void setSubscription(float subscription) {
		this.subscription.setValue(subscription);
	}
	public float getHarvestFestival() {
		return harvestFestival.getValue();
	}
	public void setHarvestFestival(float harvestFestival) {
		this.harvestFestival.setValue(harvestFestival);
	}
	public float getMissionary() {
		return missionary.getValue();
	}
	public void setMissionary(float missionary) {
		this.missionary.setValue(missionary);
	}
	public float getMensFellowship() {
		return mensFellowship.getValue();
	}
	public void setMensFellowship(float mensFellowship) {
		this.mensFellowship.setValue(mensFellowship);
	}
	public float getWomensFellowship() {
		return womensFellowship.getValue();
	}
	public void setWomensFellowship(float womensFellowship) {
		this.womensFellowship.setValue(womensFellowship);
	}
	public float getEducationHelp() {
		return educationHelp.getValue();
	}
	public void setEducationHelp(float educationHelp) {
		this.educationHelp.setValue(educationHelp);
	}
	public float getPrimarySchool() {
		return primarySchool.getValue();
	}
	public void setPrimarySchool(float primarySchool) {
		this.primarySchool.setValue(primarySchool);
	}
	public float getYouth() {
		return youth.getValue();
	}
	public void setYouth(float youth) {
		this.youth.setValue(youth);
	}
	public float getPoorHelp() {
		return poorHelp.getValue();
	}
	public void setPoorHelp(float poorHelp) {
		this.poorHelp.setValue(poorHelp);
	}
	public float getChurchRenovation() {
		return churchRenovation.getValue();
	}
	public void setChurchRenovation(float churchRenovation) {
		this.churchRenovation.setValue(churchRenovation);
	}
	public float getGraveyard() {
		return graveyard.getValue();
	}
	public void setGraveyard(float graveyard) {
		this.graveyard.setValue(graveyard);
	}
	public float getBagOffer() {
		return bagOffer.getValue();
	}
	public void setBagOffer(float bagOffer) {
		this.bagOffer.setValue(bagOffer);
	}
	public float getThanksOffer() {
		return thanksOffer.getValue();
	}
	public void setThanksOffer(float thanksOffer) {
		this.thanksOffer.setValue(thanksOffer);
	}
	public float getSto() {
		return sto.getValue();
	}
	public void setSto(float sto) {
		this.sto.setValue(sto);
	}
	public float getOther1() {
		return other1.getValue();
	}
	public void setOther1(float other1) {
		this.other1.setValue(other1);
	}
	public float getOther2() {
		return other2.getValue();
	}
	public void setOther2(float other2) {
		this.other2.setValue(other2);
	}
	public float getTotal() {
		return total.getValue();
	}
	public void setTotal(float total) {
		this.total.setValue(total);
	}
	public int getSanthaId() {
		return santhaId.getValue();
	}
	public void setSanthaId(int santhaId) {
		this.santhaId.setValue(santhaId);
	}
	
	
}
