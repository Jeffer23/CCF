package com.ccf.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;

import sun.rmi.transport.LiveRef;

import com.ccf.util.AgeCalculator;
import com.ccf.vo.Member;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import eu.schudt.javafx.controls.calendar.DatePicker;

public class MemberController extends Application{

	final static Logger logger = Logger.getLogger(MemberController.class);
	
	@FXML
	private TextField memberName;
	
	@FXML
	private DatePicker memberDob;
	
	@FXML
	private DatePicker livedTill;
	
	@FXML
	private Label labelLivedTill;
	
	@FXML
	private RadioButton yesButton;
	
	@FXML
	private RadioButton noButton;
	
	@FXML
	private TextField subscriptionAmount;
	
	private static Stage addMemberPopupStage;
	private static FamilyController controller;
	private static Member familyMember;
	
	@FXML
	void initialize(){
		logger.debug("Initialize method starts...");
		livedTill.selectedDateProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable observable) {
				System.out.println("livedTill set date method Starts...");
				if(livedTill.getSelectedDate() != null){
					yesButton.setSelected(false);
					noButton.setSelected(true);
					yesButton.setDisable(true);
					noButton.setDisable(true);
				}
				else {
					yesButton.setDisable(false);
					noButton.setDisable(false);
				}
				System.out.println("livedTill set date method Ends...");
			}
		});
		logger.debug("Initialize method ends...");
	}
	
	public static void setStage(Stage stage){
		addMemberPopupStage = stage;
	}
	
	public static void setController(FamilyController controller){
		MemberController.controller = controller;
	}
	
	
	public void initData(Member member){
		familyMember = member;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			memberName.setText(member.getName());
			memberDob.setSelectedDate(sdf.parse(member.getDob()));
			subscriptionAmount.setText(String.valueOf(member.getSubscriptionAmount()));
			if(member.getEligibility().equalsIgnoreCase("Yes")){
				yesButton.setSelected(true);
				noButton.setSelected(false);
			}
			else {
				yesButton.setSelected(false);
				noButton.setSelected(true);
			}
			livedTill.setVisible(true);
			labelLivedTill.setVisible(true);
			if(member.getLivedTill() == null || member.getLivedTill() == "")
				livedTill.setSelectedDate(null);
			else
				livedTill.setSelectedDate(sdf.parse(member.getLivedTill()));
			
		} catch (ParseException e) {
			e.printStackTrace();
			controller.message.setText(e.getMessage());
			logger.error(e.getMessage());
		}
	}
	
	
	public void saveMember(){
		logger.debug("Save member method Starts...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try{
		
		String name = memberName.getText();
		String dob = sdf.format(memberDob.getSelectedDate());
		int age = AgeCalculator.calculateAge(memberDob.getSelectedDate());
		String eligibility = null;
		if(yesButton.isSelected()){
			eligibility = "Yes";
		}
		else if(noButton.isSelected()){
			eligibility = "No";
		}
		float subscriptionAmount = Float.parseFloat(this.subscriptionAmount.getText());
		
		logger.info("Name : " + name);
		logger.info("D.O.B : " + dob);
		logger.info("Eligibility : " + eligibility);
		logger.info("Age : " + age);
		logger.info("Subscription Amount : " + subscriptionAmount);
		logger.info("Lived Till : " + livedTill.getSelectedDate());
		
		Member member = new Member();
		member.setName(name);
		member.setDob(dob);
		member.setEligibility(eligibility);
		member.setAge(age);
		member.setSubscriptionAmount(subscriptionAmount);
		if(livedTill.getSelectedDate() == null)
			livedTill.setSelectedDate(null);
		else
			member.setLivedTill(sdf.format(livedTill.getSelectedDate()));
		//Adding the member in the tableview.
		ObservableList<Member> data = controller.members.getItems();
		if(familyMember != null){
			int index = data.indexOf(familyMember);
			data.remove(familyMember);
			member.setId(familyMember.getId());
			data.add(index, member);
			controller.message.setText(member.getName() + " updated successfully.");
			logger.info(member.getName() + " updated successfully.");
		}
		else {
			data.add(member);
			controller.message.setText(member.getName() + " added successfully.");
			logger.info(member.getName() + " added successfully.");
		}
		
		
		//Closing the popup
        addMemberPopupStage.close();
        
        //Clearing familyMember, This will be later set when edit/Delete menu is selected.
        familyMember = null;
        
        
		} catch (Exception e){
			logger.error(e.getMessage());
			controller.message.setText(e.getMessage());
			e.printStackTrace();
		}
		
		
		logger.debug("Save member method Ends...");
	}
	
	public void cancelAddMember(){
		logger.debug("Cancel Add Member method Starts...");
		if(addMemberPopupStage != null)
			addMemberPopupStage.close();
		else
			logger.error("Stage is null");
		
        //Clearing familyMember, This will be later set when edit/Delete menu is selected.
        familyMember = null;
        
		logger.debug("Cancel Add Member method Ends...");
	}
	
	public void yesButtonPressed(){
		logger.debug("YesButtonPressed method Starts...");
		this.yesButton.setSelected(true);
		this.noButton.setSelected(false);
		logger.debug("YesButtonPressed method Ends...");
	}
	
	public void noButtonPressed(){
		logger.debug("NoButtonPressed method Starts...");
		this.yesButton.setSelected(false);
		this.noButton.setSelected(true);
		logger.debug("NoButtonPressed method Ends...");
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
