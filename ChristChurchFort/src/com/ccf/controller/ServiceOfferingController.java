package com.ccf.controller;

import java.util.Iterator;

import org.apache.log4j.Logger;

import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.dao.impl.ServiceOfferingDaoImpl;
import com.ccf.doa.AccountsDao;
import com.ccf.doa.ServiceOfferingDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.ServiceOffering;
import com.ccf.persistence.classes.SpecialThanksOfferingAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import eu.schudt.javafx.controls.calendar.DatePicker;

public class ServiceOfferingController {

	final static Logger logger = Logger.getLogger(FamilyController.class);

	@FXML
	private DatePicker date;

	@FXML
	private ChoiceBox<String> time;

	@FXML
	private TextField serviceOffering;

	@FXML
	private TextField thanksOffering;

	@FXML
	private TextField sto;

	@FXML
	private TextField missionary;

	@FXML
	private TextField auctionAmt;

	@FXML
	private TextField confirmation;

	@FXML
	private TextField sundaySchool;

	@FXML
	private TextField marriage;

	@FXML
	private TextField otherAmt;

	@FXML
	private TextField otherReason;

	@FXML
	private Label message;

	@FXML
	void initialize() {
		logger.info("init method Starts...");
		time.getItems().add("Morning - 6:30");
		time.getItems().add("Morning - 8:30");
		time.getItems().add("Evening - 6:30");
		time.setValue("Morning - 6:30");
		logger.info("initialize method Ends...");
	}

	public void save() {
		logger.info("Save method Starts...");
		message.setText("");
		/**
		 * Logging the inputs
		 */
		logger.debug("Date : " + date.getSelectedDate());
		logger.debug("Time : " + time.getValue());
		logger.debug("Service Offering Amount : " + serviceOffering.getText());
		logger.debug("Thanks Offering Amount : " + thanksOffering.getText());
		logger.debug("Special Thanks Offering Amount : " + sto.getText());
		logger.debug("Missionary Offering Amount : " + missionary.getText());
		logger.debug("Auction Offering Amount : " + auctionAmt.getText());
		logger.debug("Confirmation Offering Amount : " + confirmation.getText());
		logger.debug("Sunday School Offering Amount : "
				+ sundaySchool.getText());
		logger.debug("Marriage Offering Amount : " + marriage.getText());
		logger.debug("Other Offering Amount : " + otherAmt.getText());
		logger.debug("Other Offering Reason : " + otherReason.getText());

		try {
			/**
			 * Validating the inputs.
			 */
			message.setStyle("color:red;");
			if (date.getSelectedDate() == null)
				throw new CcfException("Please select the date.");
			if (serviceOffering.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException("Service Offering can contain only numbers.");
			if (thanksOffering.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException("Thanks Offering can contain only numbers.");
			if (sto.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException("Special Thanks Offering can contain only numbers.");
			if (missionary.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException("Missionary Offering can contain only numbers.");
			if (auctionAmt.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException("Auction Offering can contain only numbers.");
			if (confirmation.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException("Confirmation Offering can contain only numbers.");
			if (sundaySchool.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException("Sunday School Offering can contain only numbers.");
			if (marriage.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException("Marriage Offering can contain only numbers.");
			if (otherAmt.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException("Other Offering can contain only numbers.");
			ServiceOfferingDao serviceImpl = new ServiceOfferingDaoImpl();
			boolean isAlreadyExists = serviceImpl.isAlreadyExists(
					date.getSelectedDate(), time.getValue());
			if (isAlreadyExists) {
				throw new CcfException(
						"Given Date and time already have an entry");
			}

			/**
			 * Logic
			 */
			if (auctionAmt.getText().equals(""))
				auctionAmt.setText("0");
			if (confirmation.getText().equals(""))
				confirmation.setText("0");
			if (marriage.getText().equals(""))
				marriage.setText("0");
			if (missionary.getText().equals(""))
				missionary.setText("0");
			if (otherAmt.getText().equals(""))
				otherAmt.setText("0");
			if (serviceOffering.getText().equals(""))
				serviceOffering.setText("0");
			if (sto.getText().equals(""))
				sto.setText("0");
			if (sundaySchool.getText().equals(""))
				sundaySchool.setText("0");
			if (thanksOffering.getText().equals(""))
				thanksOffering.setText("0");
			AccountsDao impl = new AccountsDaoImpl();
			ServiceOffering so = new ServiceOffering();
			so.setAuction(Float.parseFloat(auctionAmt.getText()));
			so.setConfirmation(Float.parseFloat(confirmation.getText()));
			so.setDate(date.getSelectedDate());
			so.setMarriage(Float.parseFloat(marriage.getText()));
			so.setMissionary(Float.parseFloat(missionary.getText()));
			so.setOthers(Float.parseFloat(otherAmt.getText()));
			so.setOtherReason(otherReason.getText());
			so.setServiceOffering(Float.parseFloat(serviceOffering.getText()));
			so.setSpecialThanksOffering(Float.parseFloat(sto.getText()));
			so.setSundaySchool(Float.parseFloat(sundaySchool.getText()));
			so.setThanksOffering(Float.parseFloat(thanksOffering.getText()));
			so.setTime(time.getValue());

			MissionaryAccount missionaryAccount = null;
			if (missionary.getText() != null
					&& !missionary.getText().equals("0")) {
				missionaryAccount = new MissionaryAccount();
				missionaryAccount.setAmount(Float.parseFloat(missionary
						.getText()));
				float currentBalance = impl.getMissionaryAccountBalance();
				float balance = missionaryAccount.getAmount() + currentBalance;
				missionaryAccount.setBalance(balance);
				missionaryAccount.setCr_dr("CR");
				missionaryAccount.setDescription("Missionary Offering");
				missionaryAccount.setServiceOffering(so);
				missionaryAccount.setDate(date.getSelectedDate());
				so.getMissionaryAccounts().add(missionaryAccount);
			}

			PCAccount pcAccount = null;
			if (serviceOffering.getText() != null
					&& !serviceOffering.getText().equals("0")) {
				pcAccount = new PCAccount();
				pcAccount
						.setAmount(Float.parseFloat(serviceOffering.getText()));
				float currentBalance = impl.getPCAccountBalance();
				float balance = pcAccount.getAmount() + currentBalance;
				pcAccount.setBalance(balance);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Service Offering");
				pcAccount.setServiceOffering(so);
				pcAccount.setDate(date.getSelectedDate());
				so.getPcAccounts().add(pcAccount);
			}

			if (auctionAmt.getText() != null
					&& !auctionAmt.getText().equals("0")) {
				pcAccount = new PCAccount();
				pcAccount.setAmount(Float.parseFloat(auctionAmt.getText()));
				float currentBalance = 0.0f;
				if (so.getPcAccounts().size() == 0)
					currentBalance = impl.getPCAccountBalance();
				Iterator<PCAccount> pcAccounts = so.getPcAccounts().iterator();
				while (pcAccounts.hasNext()) {
					currentBalance = pcAccounts.next().getBalance();
				}

				float balance = pcAccount.getAmount() + currentBalance;
				pcAccount.setBalance(balance);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Auction Offering");
				pcAccount.setServiceOffering(so);
				pcAccount.setDate(date.getSelectedDate());
				so.getPcAccounts().add(pcAccount);
			}

			if (marriage.getText() != null && !marriage.getText().equals("0")) {
				pcAccount = new PCAccount();
				pcAccount.setAmount(Float.parseFloat(marriage.getText()));
				float currentBalance = 0.0f;
				if (so.getPcAccounts().size() == 0)
					currentBalance = impl.getPCAccountBalance();
				Iterator<PCAccount> pcAccounts = so.getPcAccounts().iterator();
				while (pcAccounts.hasNext()) {
					currentBalance = pcAccounts.next().getBalance();
				}

				float balance = pcAccount.getAmount() + currentBalance;
				pcAccount.setBalance(balance);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Marriage Offering");
				pcAccount.setDate(date.getSelectedDate());
				pcAccount.setServiceOffering(so);
				so.getPcAccounts().add(pcAccount);
			}

			if (confirmation.getText() != null
					&& !confirmation.getText().equals("0")) {
				pcAccount = new PCAccount();
				pcAccount.setAmount(Float.parseFloat(confirmation.getText()));
				float currentBalance = 0.0f;
				if (so.getPcAccounts().size() == 0)
					currentBalance = impl.getPCAccountBalance();
				Iterator<PCAccount> pcAccounts = so.getPcAccounts().iterator();
				while (pcAccounts.hasNext()) {
					currentBalance = pcAccounts.next().getBalance();
				}

				float balance = pcAccount.getAmount() + currentBalance;
				pcAccount.setBalance(balance);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Confirmation Offering");
				pcAccount.setDate(date.getSelectedDate());
				pcAccount.setServiceOffering(so);
				so.getPcAccounts().add(pcAccount);
			}

			SpecialThanksOfferingAccount sto = null;
			if (thanksOffering.getText() != null
					&& !thanksOffering.getText().equals("0")) {
				sto = new SpecialThanksOfferingAccount();
				sto.setAmount(Float.parseFloat(thanksOffering.getText()));
				float currentBalance = impl
						.getSpecialThanksOfferingAccountBalance();
				float balance = sto.getAmount() + currentBalance;
				sto.setBalance(balance);
				sto.setCr_dr("CR");
				sto.setDescription("Thanks Offering");
				sto.setDate(date.getSelectedDate());
				sto.setServiceOffering(so);
				so.getSpecialThanksOfferingAccounts().add(sto);
			}

			if (this.sto.getText() != null && !this.sto.getText().equals("0")) {
				sto = new SpecialThanksOfferingAccount();
				sto.setAmount(Float.parseFloat(this.sto.getText()));
				float currentBalance = 0.0f;
				if (so.getSpecialThanksOfferingAccounts().size() == 0)
					currentBalance = impl
							.getSpecialThanksOfferingAccountBalance();
				Iterator<SpecialThanksOfferingAccount> stoAccounts = so
						.getSpecialThanksOfferingAccounts().iterator();
				while (stoAccounts.hasNext()) {
					currentBalance = stoAccounts.next().getBalance();
				}
				float balance = sto.getAmount() + currentBalance;
				sto.setBalance(balance);
				sto.setCr_dr("CR");
				sto.setDescription("Special Thanks Offering");
				sto.setDate(date.getSelectedDate());
				sto.setServiceOffering(so);
				so.getSpecialThanksOfferingAccounts().add(sto);
			}

			if (otherAmt.getText() != null && !otherAmt.getText().equals("0")) {
				sto = new SpecialThanksOfferingAccount();
				sto.setAmount(Float.parseFloat(otherAmt.getText()));
				float currentBalance = 0.0f;
				if (so.getSpecialThanksOfferingAccounts().size() == 0)
					currentBalance = impl
							.getSpecialThanksOfferingAccountBalance();
				Iterator<SpecialThanksOfferingAccount> stoAccounts = so
						.getSpecialThanksOfferingAccounts().iterator();
				while (stoAccounts.hasNext()) {
					currentBalance = stoAccounts.next().getBalance();
				}
				float balance = sto.getAmount() + currentBalance;
				sto.setBalance(balance);
				sto.setCr_dr("CR");
				sto.setDescription("Other Offering - " + otherReason.getText());
				sto.setDate(date.getSelectedDate());
				sto.setServiceOffering(so);
				so.getSpecialThanksOfferingAccounts().add(sto);
			}

			if (sundaySchool.getText() != null
					&& !sundaySchool.getText().equals("0")) {
				SundaySchoolAccount ssa = new SundaySchoolAccount();
				ssa.setAmount(Float.parseFloat(sundaySchool.getText()));
				float currentBalance = impl.getSundaySchoolAccountBalance();
				float balance = ssa.getAmount() + currentBalance;
				ssa.setBalance(balance);
				ssa.setCr_dr("CR");
				ssa.setDescription("Sunday School Offering");
				ssa.setDate(date.getSelectedDate());
				ssa.setServiceOffering(so);
				so.getSundaySchoolAccounts().add(ssa);
			}

			/*
			 * Persisting the data
			 */
			serviceImpl.saveServiceOffering(so);

			message.setText("Service Offerings saved Successfully");
			message.setTextFill(Paint.valueOf("GREEN"));

		} catch (CcfException e) {
			e.printStackTrace();
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		} catch (Exception e) {
			e.printStackTrace();
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		}

		logger.info("Save method Ends...");
	}

	public void clear() {
		logger.info("clear method Starts...");
		date.setSelectedDate(null);
		time.setValue("Morning - 6:30");
		serviceOffering.setText("");
		thanksOffering.setText("");
		thanksOffering.setText("");
		sto.setText("");
		missionary.setText("");
		auctionAmt.setText("");
		confirmation.setText("");
		sundaySchool.setText("");
		marriage.setText("");
		otherAmt.setText("");
		otherReason.setText("");
		message.setText("");
		logger.info("clear method Ends...");
	}
}
