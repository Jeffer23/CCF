package com.ccf.persistence.classes;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Santha {

	private int santhaId;
	//private int memberId;
	private Date paidDate;
	private Date paidForDate;
	private float harvestFestival;
	private float missionary;
	private float mensFellowship;
	private float womensFellowship;
	private float educationHelp;
	private float primarySchool;
	private float youth;
	private float poorHelp;
	private float churchRenovation;
	private float graveyard;
	private float bagOffer;
	private float thanksOffer;
	private float sto;
	private float other1;
	private float other2;
	private float total;
	private Member member;
	private Set<PCAccount> pcAccounts = new LinkedHashSet<PCAccount>();
	private Set<MissionaryAccount> missionaryAccounts = new LinkedHashSet<MissionaryAccount>();
	private Set<MensAccount> mensAccounts = new LinkedHashSet<>();
	private Set<WomensAccount> womensAccounts = new LinkedHashSet<>();
	private Set<YouthAccount> youthAccounts =  new LinkedHashSet<>();
	private Set<GraveyardAccount> graveyardAccounts = new LinkedHashSet<>();
	private Set<PrimarySchoolAccount> primarySchoolAccounts = new LinkedHashSet<>();
	private Set<SpecialThanksOfferingAccount> specialThanksOfferingAccounts = new LinkedHashSet<>();
	
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
	public float getPrimarySchool() {
		return primarySchool;
	}
	public void setPrimarySchool(float primarySchool) {
		this.primarySchool = primarySchool;
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
	public float getOther1() {
		return other1;
	}
	public void setOther1(float other1) {
		this.other1 = other1;
	}
	public float getOther2() {
		return other2;
	}
	public void setOther2(float other2) {
		this.other2 = other2;
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
	public Set<PCAccount> getPcAccounts() {
		return pcAccounts;
	}
	public void setPcAccounts(Set<PCAccount> pcAccounts) {
		this.pcAccounts = pcAccounts;
	}
	public Set<MissionaryAccount> getMissionaryAccounts() {
		return missionaryAccounts;
	}
	public void setMissionaryAccounts(Set<MissionaryAccount> missionaryAccounts) {
		this.missionaryAccounts = missionaryAccounts;
	}
	
	public Set<MensAccount> getMensAccounts() {
		return mensAccounts;
	}
	public void setMensAccounts(Set<MensAccount> mensAccounts) {
		this.mensAccounts = mensAccounts;
	}
	public Set<WomensAccount> getWomensAccounts() {
		return womensAccounts;
	}
	public void setWomensAccounts(Set<WomensAccount> womensAccounts) {
		this.womensAccounts = womensAccounts;
	}
	public Set<YouthAccount> getYouthAccounts() {
		return youthAccounts;
	}
	public void setYouthAccounts(Set<YouthAccount> youthAccounts) {
		this.youthAccounts = youthAccounts;
	}
	public Set<GraveyardAccount> getGraveyardAccounts() {
		return graveyardAccounts;
	}
	public void setGraveyardAccounts(Set<GraveyardAccount> graveyardAccounts) {
		this.graveyardAccounts = graveyardAccounts;
	}
	public Set<PrimarySchoolAccount> getPrimarySchoolAccounts() {
		return primarySchoolAccounts;
	}
	public void setPrimarySchoolAccounts(
			Set<PrimarySchoolAccount> primarySchoolAccounts) {
		this.primarySchoolAccounts = primarySchoolAccounts;
	}
	public Set<SpecialThanksOfferingAccount> getSpecialThanksOfferingAccounts() {
		return specialThanksOfferingAccounts;
	}
	public void setSpecialThanksOfferingAccounts(
			Set<SpecialThanksOfferingAccount> specialThanksOfferingAccounts) {
		this.specialThanksOfferingAccounts = specialThanksOfferingAccounts;
	}
	
	
	
	
}
