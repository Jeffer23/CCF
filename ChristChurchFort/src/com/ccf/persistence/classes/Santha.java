package com.ccf.persistence.classes;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.ccf.persistence.interfaces.*;

public class Santha {

	private int santhaId;
	private Date paidDate;
	private Date paidForDate;
	private float harvestFestival;
	private float missionary;
	private float mensFellowship;
	private float womensFellowship;
	private float educationHelp;
	private float preSchool;
	private float youth;
	private float poorHelp;
	private float churchRenovation;
	private float graveyard;
	private float bagOffer;
	private float thanksOffer;
	private float sto;
	private float subscriptionAmount;
	private float total;
	private Member member;
	private Set<IPCAccount> pcAccounts = new LinkedHashSet<>();
	private Set<IPCAccount> bankPCAccounts = new LinkedHashSet<>();
	private Set<IMissionaryAccount> missionaryAccounts = new LinkedHashSet<>();
	private Set<IMissionaryAccount> bankMissionaryAccounts = new LinkedHashSet<>();
	private Set<IMensAccount> mensAccounts = new LinkedHashSet<>();
	private Set<IMensAccount> bankMensAccounts = new LinkedHashSet<>();
	private Set<IWomensAccount> womensAccounts = new LinkedHashSet<>();
	private Set<IWomensAccount> bankWomensAccounts = new LinkedHashSet<>();
	private Set<IYouthAccount> youthAccounts =  new LinkedHashSet<>();
	private Set<IYouthAccount> bankYouthAccounts =  new LinkedHashSet<>();
	private Set<IGraveyardAccount> graveyardAccounts = new LinkedHashSet<>();
	private Set<IGraveyardAccount> bankGraveyardAccounts = new LinkedHashSet<>();
	private Set<IEducationalFundAccount> educationalFundAccounts = new LinkedHashSet<>();
	private Set<IEducationalFundAccount> bankEducationalFundAccounts = new LinkedHashSet<>();
	private Set<ISpecialThanksOfferingAccount> specialThanksOfferingAccounts = new LinkedHashSet<>();
	private Set<ISpecialThanksOfferingAccount> bankSpecialThanksOfferingAccounts = new LinkedHashSet<>();
	
	
	public int getSanthaId() {
		return santhaId;
	}
	public void setSanthaId(int santhaId) {
		this.santhaId = santhaId;
	}
	/*public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}*/
	public float getHarvestFestival() {
		return harvestFestival;
	}
	public void setHarvestFestival(float harvestFestival) {
		this.harvestFestival = harvestFestival;
	}
	public float getMissionary() {
		return missionary;
	}
	public void setMissionary(float missionary) {
		this.missionary = missionary;
	}
	public float getMensFellowship() {
		return mensFellowship;
	}
	public void setMensFellowship(float mensFellowship) {
		this.mensFellowship = mensFellowship;
	}
	public float getWomensFellowship() {
		return womensFellowship;
	}
	public void setWomensFellowship(float womensFellowship) {
		this.womensFellowship = womensFellowship;
	}
	public float getEducationHelp() {
		return educationHelp;
	}
	public void setEducationHelp(float educationHelp) {
		this.educationHelp = educationHelp;
	}
	
	public float getPreSchool() {
		return preSchool;
	}
	public void setPreSchool(float preSchool) {
		this.preSchool = preSchool;
	}
	public float getYouth() {
		return youth;
	}
	public void setYouth(float youth) {
		this.youth = youth;
	}
	public float getPoorHelp() {
		return poorHelp;
	}
	public void setPoorHelp(float poorHelp) {
		this.poorHelp = poorHelp;
	}
	public float getChurchRenovation() {
		return churchRenovation;
	}
	public void setChurchRenovation(float churchRenovation) {
		this.churchRenovation = churchRenovation;
	}
	public float getGraveyard() {
		return graveyard;
	}
	public void setGraveyard(float graveyard) {
		this.graveyard = graveyard;
	}
	public float getBagOffer() {
		return bagOffer;
	}
	public void setBagOffer(float bagOffer) {
		this.bagOffer = bagOffer;
	}
	public float getThanksOffer() {
		return thanksOffer;
	}
	public void setThanksOffer(float thanksOffer) {
		this.thanksOffer = thanksOffer;
	}
	public float getSto() {
		return sto;
	}
	public void setSto(float sto) {
		this.sto = sto;
	}
	
	public float getSubscriptionAmount() {
		return subscriptionAmount;
	}
	public void setSubscriptionAmount(float subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	public Date getPaidForDate() {
		return paidForDate;
	}
	public void setPaidForDate(Date paidForDate) {
		this.paidForDate = paidForDate;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Set<IPCAccount> getPcAccounts() {
		return pcAccounts;
	}
	public void setPcAccounts(Set<IPCAccount> pcAccounts) {
		this.pcAccounts = pcAccounts;
	}
	public Set<IPCAccount> getBankPCAccounts() {
		return bankPCAccounts;
	}
	public void setBankPCAccounts(Set<IPCAccount> bankPCAccounts) {
		this.bankPCAccounts = bankPCAccounts;
	}
	public Set<IMissionaryAccount> getMissionaryAccounts() {
		return missionaryAccounts;
	}
	public void setMissionaryAccounts(Set<IMissionaryAccount> missionaryAccounts) {
		this.missionaryAccounts = missionaryAccounts;
	}
	public Set<IMissionaryAccount> getBankMissionaryAccounts() {
		return bankMissionaryAccounts;
	}
	public void setBankMissionaryAccounts(
			Set<IMissionaryAccount> bankMissionaryAccounts) {
		this.bankMissionaryAccounts = bankMissionaryAccounts;
	}
	public Set<IMensAccount> getMensAccounts() {
		return mensAccounts;
	}
	public void setMensAccounts(Set<IMensAccount> mensAccounts) {
		this.mensAccounts = mensAccounts;
	}
	public Set<IMensAccount> getBankMensAccounts() {
		return bankMensAccounts;
	}
	public void setBankMensAccounts(Set<IMensAccount> bankMensAccounts) {
		this.bankMensAccounts = bankMensAccounts;
	}
	public Set<IWomensAccount> getWomensAccounts() {
		return womensAccounts;
	}
	public void setWomensAccounts(Set<IWomensAccount> womensAccounts) {
		this.womensAccounts = womensAccounts;
	}
	public Set<IWomensAccount> getBankWomensAccounts() {
		return bankWomensAccounts;
	}
	public void setBankWomensAccounts(Set<IWomensAccount> bankWomensAccounts) {
		this.bankWomensAccounts = bankWomensAccounts;
	}
	public Set<IYouthAccount> getYouthAccounts() {
		return youthAccounts;
	}
	public void setYouthAccounts(Set<IYouthAccount> youthAccounts) {
		this.youthAccounts = youthAccounts;
	}
	public Set<IYouthAccount> getBankYouthAccounts() {
		return bankYouthAccounts;
	}
	public void setBankYouthAccounts(Set<IYouthAccount> bankYouthAccounts) {
		this.bankYouthAccounts = bankYouthAccounts;
	}
	public Set<IGraveyardAccount> getGraveyardAccounts() {
		return graveyardAccounts;
	}
	public void setGraveyardAccounts(Set<IGraveyardAccount> graveyardAccounts) {
		this.graveyardAccounts = graveyardAccounts;
	}
	public Set<IGraveyardAccount> getBankGraveyardAccounts() {
		return bankGraveyardAccounts;
	}
	public void setBankGraveyardAccounts(
			Set<IGraveyardAccount> bankGraveyardAccounts) {
		this.bankGraveyardAccounts = bankGraveyardAccounts;
	}
	public Set<IEducationalFundAccount> getEducationalFundAccounts() {
		return educationalFundAccounts;
	}
	public void setEducationalFundAccounts(
			Set<IEducationalFundAccount> educationalFundAccounts) {
		this.educationalFundAccounts = educationalFundAccounts;
	}
	public Set<IEducationalFundAccount> getBankEducationalFundAccounts() {
		return bankEducationalFundAccounts;
	}
	public void setBankEducationalFundAccounts(
			Set<IEducationalFundAccount> bankEducationalFundAccounts) {
		this.bankEducationalFundAccounts = bankEducationalFundAccounts;
	}
	public Set<ISpecialThanksOfferingAccount> getSpecialThanksOfferingAccounts() {
		return specialThanksOfferingAccounts;
	}
	public void setSpecialThanksOfferingAccounts(
			Set<ISpecialThanksOfferingAccount> specialThanksOfferingAccounts) {
		this.specialThanksOfferingAccounts = specialThanksOfferingAccounts;
	}
	public Set<ISpecialThanksOfferingAccount> getBankSpecialThanksOfferingAccounts() {
		return bankSpecialThanksOfferingAccounts;
	}
	public void setBankSpecialThanksOfferingAccounts(
			Set<ISpecialThanksOfferingAccount> bankSpecialThanksOfferingAccounts) {
		this.bankSpecialThanksOfferingAccounts = bankSpecialThanksOfferingAccounts;
	}
	
	
}
