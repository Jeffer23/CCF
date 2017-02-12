package com.ccf.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;

import com.ccf.dao.AccountsDao;
import com.ccf.dao.ServiceOfferingDao;
import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.dao.impl.ServiceOfferingDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.BankMissionaryAccount;
import com.ccf.persistence.classes.BankPCAccount;
import com.ccf.persistence.classes.BankBuildingAccount;
import com.ccf.persistence.classes.BankSundaySchoolAccount;
import com.ccf.persistence.classes.Cheque;
import com.ccf.persistence.classes.Ledger;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.ServiceOffering;
import com.ccf.persistence.classes.BuildingAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;
import com.ccf.persistence.interfaces.IMissionaryAccount;
import com.ccf.persistence.interfaces.IPCAccount;
import com.ccf.persistence.interfaces.ISpecialThanksOfferingAccount;
import com.ccf.persistence.interfaces.ISundaySchoolAccount;
import com.ccf.util.AccountNames;
import com.ccf.util.ProjectProperties;
import com.ccf.vo.AccStatement;
import com.ccf.vo.Account;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	private TextField baptism;

	@FXML
	private TextField sundaySchool;

	@FXML
	private TextField marriage;

	@FXML
	private DatePicker chequeDate;

	@FXML
	private TextField chequeNumber;

	@FXML
	private TextField chequeAmount;

	@FXML
	private Label message;

	@FXML
	private VBox chequeDetails;

	@FXML
	private TableView<com.ccf.vo.Cheque> chequeTable;

	private enum OfferingType {ServiceOffering, ThanksOffering, SpecialThanksOffering, MissionaryOffering, AuctionOffering, BaptismOffering, SundaySchoolOffering, MarriageOffering};
	private ServiceOffering so = new ServiceOffering();
	private OfferingType offeringType = null;
	private Map<String, Ledger> ledgerMap = new HashMap<>();
	
	private BankMissionaryAccount missionaryOfferingAcc = new BankMissionaryAccount();
	private BankPCAccount serviceOfferingAcc = new BankPCAccount();
	private BankPCAccount auctionOfferingAcc = new BankPCAccount();
	private BankPCAccount marriageOfferingAcc = new BankPCAccount();
	private BankPCAccount baptismOfferingAcc = new BankPCAccount();
	private BankPCAccount thanksOfferingAcc = new BankPCAccount();
	private BankBuildingAccount stoOfferingAcc = new BankBuildingAccount();
	private BankSundaySchoolAccount sundaySchoolOfferingAcc = new BankSundaySchoolAccount();
	

	@FXML
	void initialize() {
		logger.info("init method Starts...");
		this.date.setDateFormat(ProjectProperties.sdf);
		this.chequeDate.setDateFormat(ProjectProperties.sdf);
		
		time.getItems().add("Morning - 6:30");
		time.getItems().add("Morning - 8:30");
		time.getItems().add("Evening - 6:30");
		time.setValue("Morning - 6:30");

		this.chequeDetails.setVisible(false);

		/*
		 * Getting ledger records from DB
		 */
		AccountsDao accountImpl = new AccountsDaoImpl();
		List<Ledger> ledgers;
		try {
			ledgers = accountImpl.getAllLedgers("Service - ");
			for (Ledger l : ledgers) {
				this.ledgerMap.put(l.getLedgerName(), l);
			}
		} catch (CcfException e) {
			e.printStackTrace();
		}
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
		logger.debug("Confirmation Offering Amount : " + baptism.getText());
		logger.debug("Sunday School Offering Amount : "
				+ sundaySchool.getText());
		logger.debug("Marriage Offering Amount : " + marriage.getText());
		logger.debug("Cheque Date : " + chequeDate.getSelectedDate());
		logger.debug("Cheque Number : " + chequeNumber.getText());

		try {
			/**
			 * Validating the inputs.
			 */
			message.setStyle("color:red;");
			if (date.getSelectedDate() == null)
				throw new CcfException("Please select the date.");
			if (serviceOffering.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Service Offering can contain only numbers.");
			if (thanksOffering.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Thanks Offering can contain only numbers.");
			if (sto.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Special Thanks Offering can contain only numbers.");
			if (missionary.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Missionary Offering can contain only numbers.");
			if (auctionAmt.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Auction Offering can contain only numbers.");
			if (baptism.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Confirmation Offering can contain only numbers.");
			if (sundaySchool.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Sunday School Offering can contain only numbers.");
			if (marriage.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Marriage Offering can contain only numbers.");

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
			if (baptism.getText().equals(""))
				baptism.setText("0");
			if (marriage.getText().equals(""))
				marriage.setText("0");
			if (missionary.getText().equals(""))
				missionary.setText("0");
			if (serviceOffering.getText().equals(""))
				serviceOffering.setText("0");
			if (sto.getText().equals(""))
				sto.setText("0");
			if (sundaySchool.getText().equals(""))
				sundaySchool.setText("0");
			if (thanksOffering.getText().equals(""))
				thanksOffering.setText("0");

			so.setAuction(Float.parseFloat(auctionAmt.getText()));
			so.setConfirmation(Float.parseFloat(baptism.getText()));
			so.setDate(date.getSelectedDate());
			so.setMarriage(Float.parseFloat(marriage.getText()));
			so.setMissionary(Float.parseFloat(missionary.getText()));
			so.setServiceOffering(Float.parseFloat(serviceOffering.getText()));
			so.setSpecialThanksOffering(Float.parseFloat(sto.getText()));
			so.setSundaySchool(Float.parseFloat(sundaySchool.getText()));
			so.setThanksOffering(Float.parseFloat(thanksOffering.getText()));
			so.setTime(time.getValue());

			
			Ledger ledger = null;
			
			if (this.missionary.getText() != null
					&& !this.missionary.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Missionary Offering");
				float totalChequeAmount = 0.0f;
				if(this.missionaryOfferingAcc.getCheques().size() > 0){
					for(Cheque c : this.missionaryOfferingAcc.getCheques()){
						totalChequeAmount += c.getChequeAmount();
					}
					this.missionaryOfferingAcc.setAmount(totalChequeAmount);
					this.missionaryOfferingAcc.setCr_dr("CR");
					this.missionaryOfferingAcc.setDescription("Service - Missionary Offering");
					this.missionaryOfferingAcc.setServiceOffering(so);
					this.missionaryOfferingAcc.setDate(date.getSelectedDate());
					this.missionaryOfferingAcc.setLedger(ledger);
					this.so.getBankMissionaryAccounts().add(this.missionaryOfferingAcc);
					
				}
				
				float totalCashAmount = Float.parseFloat(this.missionary.getText()) - totalChequeAmount;
				if(totalCashAmount > 0.0f){
					MissionaryAccount missionaryAccount = new MissionaryAccount();
					missionaryAccount.setAmount(totalCashAmount);
					missionaryAccount.setCr_dr("CR");
					missionaryAccount.setDescription("Service - Missionary Offering");
					missionaryAccount.setServiceOffering(so);
					missionaryAccount.setDate(date.getSelectedDate());
					missionaryAccount.setLedger(ledger);
					so.getMissionaryAccounts().add(missionaryAccount);
				}
			}

			if (serviceOffering.getText() != null
					&& !serviceOffering.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Service Offering");
				float totalChequeAmount = 0.0f;
				if(this.serviceOfferingAcc.getCheques().size() > 0){
					for(Cheque c : this.serviceOfferingAcc.getCheques()){
						totalChequeAmount += c.getChequeAmount();
					}
					this.serviceOfferingAcc.setAmount(totalChequeAmount);
					this.serviceOfferingAcc.setCr_dr("CR");
					this.serviceOfferingAcc.setDescription("Service - Service Offering");
					this.serviceOfferingAcc.setServiceOffering(so);
					this.serviceOfferingAcc.setDate(date.getSelectedDate());
					this.serviceOfferingAcc.setLedger(ledger);
					this.so.getBankPCAccounts().add(this.serviceOfferingAcc);
					
				}
				
				float totalCashAmount = Float.parseFloat(this.serviceOffering.getText()) - totalChequeAmount;
				if(totalCashAmount > 0.0f){
					PCAccount pcAccount = new PCAccount();
					pcAccount.setAmount(totalCashAmount);
					pcAccount.setCr_dr("CR");
					pcAccount.setDescription("Service - Service Offering");
					pcAccount.setServiceOffering(so);
					pcAccount.setDate(date.getSelectedDate());
					pcAccount.setLedger(ledger);
					so.getPcAccounts().add(pcAccount);
				}
			}

			if (auctionAmt.getText() != null
					&& !auctionAmt.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Auction Offering");
				float totalChequeAmount = 0.0f;
				if(this.auctionOfferingAcc.getCheques().size() > 0){
					for(Cheque c : this.auctionOfferingAcc.getCheques()){
						totalChequeAmount += c.getChequeAmount();
					}
					this.auctionOfferingAcc.setAmount(totalChequeAmount);
					this.auctionOfferingAcc.setCr_dr("CR");
					this.auctionOfferingAcc.setDescription("Service - Auction Offering");
					this.auctionOfferingAcc.setServiceOffering(so);
					this.auctionOfferingAcc.setDate(date.getSelectedDate());
					this.auctionOfferingAcc.setLedger(ledger);
					this.so.getBankPCAccounts().add(this.auctionOfferingAcc);
					
				}
				
				float totalCashAmount = Float.parseFloat(this.auctionAmt.getText()) - totalChequeAmount;
				if(totalCashAmount > 0.0f){
					PCAccount pcAccount = new PCAccount();
					pcAccount.setAmount(totalCashAmount);
					pcAccount.setCr_dr("CR");
					pcAccount.setDescription("Service - Auction Offering");
					pcAccount.setServiceOffering(so);
					pcAccount.setDate(date.getSelectedDate());
					pcAccount.setLedger(ledger);
					so.getPcAccounts().add(pcAccount);
				}
			}

			if (marriage.getText() != null && !marriage.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Marriage Offering");
				float totalChequeAmount = 0.0f;
				if(this.marriageOfferingAcc.getCheques().size() > 0){
					for(Cheque c : this.marriageOfferingAcc.getCheques()){
						totalChequeAmount += c.getChequeAmount();
					}
					this.marriageOfferingAcc.setAmount(totalChequeAmount);
					this.marriageOfferingAcc.setCr_dr("CR");
					this.marriageOfferingAcc.setDescription("Service - Marriage Offering");
					this.marriageOfferingAcc.setServiceOffering(so);
					this.marriageOfferingAcc.setDate(date.getSelectedDate());
					this.marriageOfferingAcc.setLedger(ledger);
					this.so.getBankPCAccounts().add(this.marriageOfferingAcc);
					
				}
				
				float totalCashAmount = Float.parseFloat(this.marriage.getText()) - totalChequeAmount;
				if(totalCashAmount > 0.0f){
					PCAccount pcAccount = new PCAccount();
					pcAccount.setAmount(totalCashAmount);
					pcAccount.setCr_dr("CR");
					pcAccount.setDescription("Service - Marriage Offering");
					pcAccount.setServiceOffering(so);
					pcAccount.setDate(date.getSelectedDate());
					pcAccount.setLedger(ledger);
					so.getPcAccounts().add(pcAccount);
				}
			}

			if (baptism.getText() != null
					&& !baptism.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Baptism Offering");
				float totalChequeAmount = 0.0f;
				if(this.baptismOfferingAcc.getCheques().size() > 0){
					for(Cheque c : this.baptismOfferingAcc.getCheques()){
						totalChequeAmount += c.getChequeAmount();
					}
					this.baptismOfferingAcc.setAmount(totalChequeAmount);
					this.baptismOfferingAcc.setCr_dr("CR");
					this.baptismOfferingAcc.setDescription("Service - Baptism Offering");
					this.baptismOfferingAcc.setServiceOffering(so);
					this.baptismOfferingAcc.setDate(date.getSelectedDate());
					this.baptismOfferingAcc.setLedger(ledger);
					this.so.getBankPCAccounts().add(this.baptismOfferingAcc);
					
				}
				
				float totalCashAmount = Float.parseFloat(this.baptism.getText()) - totalChequeAmount;
				if(totalCashAmount > 0.0f){
					PCAccount pcAccount = new PCAccount();
					pcAccount.setAmount(totalCashAmount);
					pcAccount.setCr_dr("CR");
					pcAccount.setDescription("Service - Baptism Offering");
					pcAccount.setServiceOffering(so);
					pcAccount.setDate(date.getSelectedDate());
					pcAccount.setLedger(ledger);
					so.getPcAccounts().add(pcAccount);
				}
			}

			if (thanksOffering.getText() != null
					&& !thanksOffering.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Thanks Offering");
				float totalChequeAmount = 0.0f;
				if(this.thanksOfferingAcc.getCheques().size() > 0){
					for(Cheque c : this.thanksOfferingAcc.getCheques()){
						totalChequeAmount += c.getChequeAmount();
					}
					this.thanksOfferingAcc.setAmount(totalChequeAmount);
					this.thanksOfferingAcc.setCr_dr("CR");
					this.thanksOfferingAcc.setDescription("Service - Thanks Offering");
					this.thanksOfferingAcc.setServiceOffering(so);
					this.thanksOfferingAcc.setDate(date.getSelectedDate());
					this.thanksOfferingAcc.setLedger(ledger);
					this.so.getBankPCAccounts().add(this.thanksOfferingAcc);
					
				}
				
				float totalCashAmount = Float.parseFloat(this.thanksOffering.getText()) - totalChequeAmount;
				if(totalCashAmount > 0.0f){
					PCAccount pcAccount = new PCAccount();
					pcAccount.setAmount(totalCashAmount);
					pcAccount.setCr_dr("CR");
					pcAccount.setDescription("Service - Thanks Offering");
					pcAccount.setServiceOffering(so);
					pcAccount.setDate(date.getSelectedDate());
					pcAccount.setLedger(ledger);
					so.getPcAccounts().add(pcAccount);
				}
			}

			if (this.sto.getText() != null && !this.sto.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Special Thanks Offering");
				float totalChequeAmount = 0.0f;
				if(this.stoOfferingAcc.getCheques().size() > 0){
					for(Cheque c : this.stoOfferingAcc.getCheques()){
						totalChequeAmount += c.getChequeAmount();
					}
					this.stoOfferingAcc.setAmount(totalChequeAmount);
					this.stoOfferingAcc.setCr_dr("CR");
					this.stoOfferingAcc.setDescription("Service - Special Thanks Offering");
					this.stoOfferingAcc.setServiceOffering(so);
					this.stoOfferingAcc.setDate(date.getSelectedDate());
					this.stoOfferingAcc.setLedger(ledger);
					this.so.getBankSpecialThanksOfferingAccounts().add(this.stoOfferingAcc);
					
				}
				
				float totalCashAmount = Float.parseFloat(this.sto.getText()) - totalChequeAmount;
				if(totalCashAmount > 0.0f){
					BuildingAccount stoAccount = new BuildingAccount();
					stoAccount.setAmount(totalCashAmount);
					stoAccount.setCr_dr("CR");
					stoAccount.setDescription("Service - Special Thanks Offering");
					stoAccount.setServiceOffering(so);
					stoAccount.setDate(date.getSelectedDate());
					stoAccount.setLedger(ledger);
					so.getSpecialThanksOfferingAccounts().add(stoAccount);
				}
			}

			
			if (sundaySchool.getText() != null
					&& !sundaySchool.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Sunday School Offering");
				float totalChequeAmount = 0.0f;
				if(this.sundaySchoolOfferingAcc.getCheques().size() > 0){
					for(Cheque c : this.sundaySchoolOfferingAcc.getCheques()){
						totalChequeAmount += c.getChequeAmount();
					}
					this.sundaySchoolOfferingAcc.setAmount(totalChequeAmount);
					this.sundaySchoolOfferingAcc.setCr_dr("CR");
					this.sundaySchoolOfferingAcc.setDescription("Service - Sunday School Offering");
					this.sundaySchoolOfferingAcc.setServiceOffering(so);
					this.sundaySchoolOfferingAcc.setDate(date.getSelectedDate());
					this.sundaySchoolOfferingAcc.setLedger(ledger);
					this.so.getBankSundaySchoolAccounts().add(this.sundaySchoolOfferingAcc);
					
				}
				
				float totalCashAmount = Float.parseFloat(this.sundaySchool.getText()) - totalChequeAmount;
				if(totalCashAmount > 0.0f){
					SundaySchoolAccount sundaySchoolAccount = new SundaySchoolAccount();
					sundaySchoolAccount.setAmount(totalCashAmount);
					sundaySchoolAccount.setCr_dr("CR");
					sundaySchoolAccount.setDescription("Service - Sunday School Offering");
					sundaySchoolAccount.setServiceOffering(so);
					sundaySchoolAccount.setDate(date.getSelectedDate());
					sundaySchoolAccount.setLedger(ledger);
					so.getSundaySchoolAccounts().add(sundaySchoolAccount);
				}
			}

			/*
			 * Persisting the data
			 */
			serviceImpl.saveServiceOffering(so);

			message.setText("Service Offerings saved Successfully");
			message.setTextFill(Paint.valueOf("GREEN"));

			so = new ServiceOffering();

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

	public void cancelCheque() {
		logger.info("cancelCheque method Starts...");
		this.chequeDetails.setVisible(false);
		this.offeringType = null;
		logger.info("cancelCheque method Ends...");
	}

	public void addCheque() {
		logger.info("addCheque method Starts...");
		try {
			/*
			 * Validation
			 */
			if (this.chequeDate.getSelectedDate() == null)
				throw new CcfException("Please select the Cheque Date.");
			if (this.chequeAmount.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Cheque Amount can contain only numbers.");
			if (this.chequeNumber.getText() == null)
				throw new CcfException("Please enter the cheque number.");
			validateCheque();

			Cheque cheque = new Cheque();
			cheque.setChequeAmount(Float.parseFloat(this.chequeAmount.getText()));
			cheque.setChequeDate(this.chequeDate.getSelectedDate());
			cheque.setChequeNumber(this.chequeNumber.getText());

			if (this.offeringType.equals(OfferingType.MissionaryOffering)) {
				if (this.missionary.getText().equals(""))
					this.missionary.setText("0");
				this.missionaryOfferingAcc.getCheques().add(cheque);
				this.missionary.setDisable(true);
				this.missionary.setText(String.valueOf(Float.parseFloat(this.missionary.getText()) + cheque.getChequeAmount()));
				
			}

			else if (this.offeringType.equals(OfferingType.ServiceOffering)) {
				if (this.serviceOffering.getText().equals(""))
					this.serviceOffering.setText("0");
				
				this.serviceOfferingAcc.getCheques().add(cheque);
				this.serviceOffering.setDisable(true);
				this.serviceOffering.setText(String.valueOf(Float.parseFloat(this.serviceOffering.getText()) + cheque.getChequeAmount()));
			}

			else if (this.offeringType.equals(OfferingType.AuctionOffering)) {
				if (this.auctionAmt.getText().equals(""))
					this.auctionAmt.setText("0");
				this.auctionOfferingAcc.getCheques().add(cheque);
				this.auctionAmt.setDisable(true);
				this.auctionAmt.setText(String.valueOf(Float.parseFloat(this.auctionAmt.getText()) + cheque.getChequeAmount()));
			}

			else if (this.offeringType.equals(OfferingType.MarriageOffering)) {
				if (this.marriage.getText().equals(""))
					this.marriage.setText("0");
				this.marriageOfferingAcc.getCheques().add(cheque);
				this.marriage.setDisable(true);
				this.marriage.setText(String.valueOf(Float.parseFloat(this.marriage.getText()) + cheque.getChequeAmount()));
			}

			else if (this.offeringType.equals(OfferingType.BaptismOffering)) {
				if (this.baptism.getText().equals(""))
					this.baptism.setText("0");
				this.baptismOfferingAcc.getCheques().add(cheque);
				this.baptism.setDisable(true);
				this.baptism.setText(String.valueOf(Float.parseFloat(this.baptism.getText()) + cheque.getChequeAmount()));
			}

			else if (this.offeringType.equals(OfferingType.ThanksOffering)) {
				if (this.thanksOffering.getText().equals(""))
					this.thanksOffering.setText("0");
				this.thanksOfferingAcc.getCheques().add(cheque);
				this.thanksOffering.setDisable(true);
				this.thanksOffering.setText(String.valueOf(Float.parseFloat(this.thanksOffering.getText()) + cheque.getChequeAmount()));
			}

			else if (this.offeringType.equals(OfferingType.SpecialThanksOffering)) {
				if (this.sto.getText().equals(""))
					this.sto.setText("0");
				this.stoOfferingAcc.getCheques().add(cheque);
				this.sto.setDisable(true);
				this.sto.setText(String.valueOf(Float.parseFloat(this.sto.getText()) + cheque.getChequeAmount()));
			}


			else if (this.offeringType.equals(OfferingType.SundaySchoolOffering)) {
				if (this.sundaySchool.getText().equals(""))
					this.sundaySchool.setText("0");
				this.sundaySchoolOfferingAcc.getCheques().add(cheque);
				this.sundaySchool.setDisable(true);
				this.sundaySchool.setText(String.valueOf(Float.parseFloat(this.sundaySchool.getText()) + cheque.getChequeAmount()));
			}
			
			com.ccf.vo.Cheque chequeUI = new com.ccf.vo.Cheque();
			chequeUI.setChequeAmount(cheque.getChequeAmount());
			chequeUI.setChequeNumber(cheque.getChequeNumber());
			chequeUI.setChequeDate(ProjectProperties.sdf.format(cheque.getChequeDate()));
			this.chequeTable.getItems().add(chequeUI);
			
			/*
			 * Resetting Values
			 */
			this.chequeDetails.setVisible(false);
			this.offeringType = null;
			this.chequeAmount.setText(null);
			this.chequeDate.setSelectedDate(null);
			this.chequeNumber.setText(null);

		} catch (CcfException e) {
			e.printStackTrace();
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		} catch (Exception e) {
			e.printStackTrace();
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		}
		
		logger.info("addCheque method Ends...");
	}

	public void addChequeForServiceOffering() {
		logger.info("addChequeForServiceOffering method Starts...");
		this.chequeDetails.setVisible(true);
		this.offeringType = OfferingType.ServiceOffering;

		logger.info("addChequeForServiceOffering method Ends...");
	}

	public void addChequeForThanksOffering() {
		logger.info("addChequeForThanksOffering method Starts...");
		this.chequeDetails.setVisible(true);
		this.offeringType = OfferingType.ThanksOffering;
		logger.info("addChequeForThanksOffering method Ends...");
	}

	public void addChequeForSTOOffering() {
		logger.info("addChequeForSTOOffering method Starts...");
		this.chequeDetails.setVisible(true);
		this.offeringType = OfferingType.SpecialThanksOffering;
		logger.info("addChequeForSTOOffering method Ends...");
	}

	public void addChequeForMissionary() {
		logger.info("addChequeForMissionary method Starts...");
		this.chequeDetails.setVisible(true);
		this.offeringType = OfferingType.MissionaryOffering;
		logger.info("addChequeForMissionary method Ends...");
	}

	public void addChequeForAuction() {
		logger.info("addChequeForAuction method Starts...");
		this.chequeDetails.setVisible(true);
		this.offeringType = OfferingType.AuctionOffering;
		logger.info("addChequeForAuction method Ends...");
	}

	public void addChequeForBaptism() {
		logger.info("addChequeForBaptism method Starts...");
		this.chequeDetails.setVisible(true);
		this.offeringType = OfferingType.BaptismOffering;
		logger.info("addChequeForBaptism method Ends...");
	}

	public void addChequeForSundaySchool() {
		logger.info("addChequeForSundaySchool method Starts...");
		this.chequeDetails.setVisible(true);
		this.offeringType = OfferingType.SundaySchoolOffering;
		logger.info("addChequeForSundaySchool method Ends...");
	}

	public void addChequeForMarriage() {
		logger.info("addChequeForMarriage method Starts...");
		this.chequeDetails.setVisible(true);
		this.offeringType = OfferingType.MarriageOffering;
		logger.info("addChequeForMarriage method Ends...");
	}

		
	private void validateCheque() throws CcfException{
		AccountsDao impl = new AccountsDaoImpl();
		boolean chequeExists = impl.isChequeExists(this.chequeNumber.getText());
		if(chequeExists)
			throw new CcfException("Cheque Already Exists.");
		for(IMissionaryAccount bma : so.getBankMissionaryAccounts()){
			for(Cheque cheque : ((BankMissionaryAccount)bma).getCheques()){
				if(cheque.getChequeNumber().equals(this.chequeNumber.getText())){
					throw new CcfException("This Cheque is just added by you now. Please check the table");
				}
			}
		}
		
		for(IPCAccount pca : so.getBankPCAccounts()){
			for(Cheque cheque : ((BankPCAccount)pca).getCheques()){
				if(cheque.getChequeNumber().equals(this.chequeNumber.getText())){
					throw new CcfException("This Cheque is just added by you now. Please check the table");
				}
			}
		}
		
		for(ISpecialThanksOfferingAccount bstoa : so.getBankSpecialThanksOfferingAccounts()){
			for(Cheque cheque : ((BankBuildingAccount)bstoa).getCheques()){
				if(cheque.getChequeNumber().equals(this.chequeNumber.getText())){
					throw new CcfException("This Cheque is just added by you now. Please check the table");
				}
			}
		}
		
		for(ISundaySchoolAccount bssa : so.getBankSundaySchoolAccounts()){
			for(Cheque cheque : ((BankSundaySchoolAccount)bssa).getCheques()){
				if(cheque.getChequeNumber().equals(this.chequeNumber.getText())){
					throw new CcfException("This Cheque is just added by you now. Please check the table");
				}
			}
		}
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
		baptism.setText("");
		sundaySchool.setText("");
		marriage.setText("");
		message.setText("");
		logger.info("clear method Ends...");
	}
}
