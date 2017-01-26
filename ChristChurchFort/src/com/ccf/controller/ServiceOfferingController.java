package com.ccf.controller;

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
import com.ccf.persistence.classes.BankSpecialThanksOfferingAccount;
import com.ccf.persistence.classes.BankSundaySchoolAccount;
import com.ccf.persistence.classes.Cheque;
import com.ccf.persistence.classes.Ledger;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.ServiceOffering;
import com.ccf.persistence.classes.SpecialThanksOfferingAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;
import com.ccf.persistence.interfaces.IMissionaryAccount;
import com.ccf.persistence.interfaces.IPCAccount;
import com.ccf.persistence.interfaces.ISpecialThanksOfferingAccount;
import com.ccf.persistence.interfaces.ISundaySchoolAccount;
import com.ccf.vo.Account;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
	private RadioButton cash;

	@FXML
	private RadioButton chequeBtn;

	@FXML
	private HBox chequeDetails;

	@FXML
	private DatePicker chequeDate;

	@FXML
	private TextField chequeNumber;

	@FXML
	private Label message;
	
	Cheque cheque = null;

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
		logger.debug("Cash : " + cash.isSelected());
		logger.debug("Cheque : " + chequeBtn.isSelected());
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
			if (confirmation.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Confirmation Offering can contain only numbers.");
			if (sundaySchool.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Sunday School Offering can contain only numbers.");
			if (marriage.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Marriage Offering can contain only numbers.");
			if (otherAmt.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException(
						"Other Offering can contain only numbers.");

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
			
			if(this.chequeBtn.isSelected()){
				cheque = new Cheque();
				cheque.setChequeNumber(this.chequeNumber.getText());
				cheque.setChequeDate(chequeDate.getSelectedDate());
			}
			
			Ledger ledger = null;
			AccountsDao accountImpl = new AccountsDaoImpl();
			Map<String, Ledger> ledgerMap = new HashMap<>();
			/*
			 * Getting ledger records from DB
			 */
			List<Ledger> ledgers = accountImpl.getAllLedgers("Service - ");
			for(Ledger l : ledgers){
				ledgerMap.put(l.getLedgerName(), l);
			}
			
			IMissionaryAccount missionaryAccount = null;
			if (missionary.getText() != null
					&& !missionary.getText().equals("0")) {
				ledger =  ledgerMap.get("Service - Missionary Offering");
				if (cash.isSelected()) {
					missionaryAccount = new MissionaryAccount();
					so.getMissionaryAccounts().add(missionaryAccount);
				} else if (chequeBtn.isSelected()) {
					missionaryAccount = new BankMissionaryAccount();
					so.getBankMissionaryAccounts().add(missionaryAccount);
					BankMissionaryAccount bankMissionaryAcc = (BankMissionaryAccount) missionaryAccount;
					bankMissionaryAcc.getCheques().add(cheque);
					cheque.getBankMissionaryAccounts().add(bankMissionaryAcc);

				}

				missionaryAccount.setAmount(Float.parseFloat(missionary
						.getText()));
				missionaryAccount.setCr_dr("CR");
				missionaryAccount.setDescription("Service - Missionary Offering");
				missionaryAccount.setServiceOffering(so);
				missionaryAccount.setDate(date.getSelectedDate());
				((Account)missionaryAccount).setLedger(ledger);
			}

			IPCAccount pcAccount = null;
			if (serviceOffering.getText() != null
					&& !serviceOffering.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Service Offering");
				if (cash.isSelected()) {
					pcAccount = new PCAccount();
					so.getPcAccounts().add(pcAccount);
				} else if (chequeBtn.isSelected()) {
					pcAccount = new BankPCAccount();
					so.getBankPCAccounts().add(pcAccount);
					BankPCAccount bankPCAcc = (BankPCAccount) pcAccount;
					bankPCAcc.getCheques().add(cheque);
					cheque.getBankPCAccounts().add(bankPCAcc);
				}

				pcAccount
						.setAmount(Float.parseFloat(serviceOffering.getText()));
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Service - Service Offering");
				pcAccount.setServiceOffering(so);
				pcAccount.setDate(date.getSelectedDate());
				((Account)pcAccount).setLedger(ledger);
			}

			if (auctionAmt.getText() != null
					&& !auctionAmt.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Auction Offering");
				if (cash.isSelected()) {
					pcAccount = new PCAccount();
					so.getPcAccounts().add(pcAccount);
				} else if (chequeBtn.isSelected()) {
					pcAccount = new BankPCAccount();
					so.getBankPCAccounts().add(pcAccount);
					BankPCAccount bankPCAcc = (BankPCAccount) pcAccount;
					bankPCAcc.getCheques().add(cheque);
					cheque.getBankPCAccounts().add(bankPCAcc);
				}

				pcAccount.setAmount(Float.parseFloat(auctionAmt.getText()));
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Service - Auction Offering");
				pcAccount.setServiceOffering(so);
				pcAccount.setDate(date.getSelectedDate());
				((Account)pcAccount).setLedger(ledger);
			}

			if (marriage.getText() != null && !marriage.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Marriage Offering");
				if (cash.isSelected()) {
					pcAccount = new PCAccount();
					so.getPcAccounts().add(pcAccount);
				} else if (chequeBtn.isSelected()) {
					pcAccount = new BankPCAccount();
					so.getBankPCAccounts().add(pcAccount);
					BankPCAccount bankPCAcc = (BankPCAccount) pcAccount;
					bankPCAcc.getCheques().add(cheque);
					cheque.getBankPCAccounts().add(bankPCAcc);
				}

				pcAccount.setAmount(Float.parseFloat(marriage.getText()));
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Service - Marriage Offering");
				pcAccount.setDate(date.getSelectedDate());
				pcAccount.setServiceOffering(so);
				((Account)pcAccount).setLedger(ledger);
			}

			if (confirmation.getText() != null
					&& !confirmation.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Confirmation Offering");
				if (cash.isSelected()) {
					pcAccount = new PCAccount();
					so.getPcAccounts().add(pcAccount);
				} else if (chequeBtn.isSelected()) {
					pcAccount = new BankPCAccount();
					so.getBankPCAccounts().add(pcAccount);
					BankPCAccount bankPCAcc = (BankPCAccount) pcAccount;
					bankPCAcc.getCheques().add(cheque);
					cheque.getBankPCAccounts().add(bankPCAcc);
				}

				pcAccount.setAmount(Float.parseFloat(confirmation.getText()));
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Service - Confirmation Offering");
				pcAccount.setDate(date.getSelectedDate());
				pcAccount.setServiceOffering(so);
				((Account)pcAccount).setLedger(ledger);
			}

			ISpecialThanksOfferingAccount sto = null;
			if (thanksOffering.getText() != null
					&& !thanksOffering.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Thanks Offering");
				if (cash.isSelected()) {
					sto = new SpecialThanksOfferingAccount();
					so.getSpecialThanksOfferingAccounts().add(sto);
				} else if (chequeBtn.isSelected()) {
					sto = new BankSpecialThanksOfferingAccount();
					so.getBankSpecialThanksOfferingAccounts().add(sto);
					BankSpecialThanksOfferingAccount bankSTOAcc = (BankSpecialThanksOfferingAccount) sto;
					bankSTOAcc.getCheques().add(cheque);
					cheque.getBankSTOAccounts().add(bankSTOAcc);
				}

				sto.setAmount(Float.parseFloat(thanksOffering.getText()));
				sto.setCr_dr("CR");
				sto.setDescription("Service - Thanks Offering");
				sto.setDate(date.getSelectedDate());
				sto.setServiceOffering(so);
				((Account)sto).setLedger(ledger);
			}

			if (this.sto.getText() != null && !this.sto.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Special Thanks Offering");
				if (cash.isSelected()) {
					sto = new SpecialThanksOfferingAccount();
					so.getSpecialThanksOfferingAccounts().add(sto);
				} else if (chequeBtn.isSelected()) {
					sto = new BankSpecialThanksOfferingAccount();
					so.getBankSpecialThanksOfferingAccounts().add(sto);
					BankSpecialThanksOfferingAccount bankSTOAcc = (BankSpecialThanksOfferingAccount) sto;
					bankSTOAcc.getCheques().add(cheque);
					cheque.getBankSTOAccounts().add(bankSTOAcc);
				}

				sto.setAmount(Float.parseFloat(this.sto.getText()));
				sto.setCr_dr("CR");
				sto.setDescription("Service - Special Thanks Offering");
				sto.setDate(date.getSelectedDate());
				sto.setServiceOffering(so);
				((Account)sto).setLedger(ledger);
			}

			if (otherAmt.getText() != null && !otherAmt.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Other Offering");
				if (cash.isSelected()) {
					sto = new SpecialThanksOfferingAccount();
					so.getSpecialThanksOfferingAccounts().add(sto);
				} else if (chequeBtn.isSelected()) {
					sto = new BankSpecialThanksOfferingAccount();
					so.getBankSpecialThanksOfferingAccounts().add(sto);
					BankSpecialThanksOfferingAccount bankSTOAcc = (BankSpecialThanksOfferingAccount) sto;
					bankSTOAcc.getCheques().add(cheque);
					cheque.getBankSTOAccounts().add(bankSTOAcc);
				}

				sto.setAmount(Float.parseFloat(otherAmt.getText()));
				sto.setCr_dr("CR");
				sto.setDescription("Service - Other Offering - " + otherReason.getText());
				sto.setDate(date.getSelectedDate());
				sto.setServiceOffering(so);
				((Account)sto).setLedger(ledger);
			}

			if (sundaySchool.getText() != null
					&& !sundaySchool.getText().equals("0")) {
				ledger = ledgerMap.get("Service - Sunday School Offering");
				ISundaySchoolAccount ssa = null;
				if (cash.isSelected()) {
					ssa = new SundaySchoolAccount();
					so.getSundaySchoolAccounts().add(ssa);
				} else if (chequeBtn.isSelected()) {
					ssa = new BankSundaySchoolAccount();
					so.getBankSundaySchoolAccounts().add(ssa);
					BankSundaySchoolAccount bankSsaAcc = (BankSundaySchoolAccount) ssa;
					bankSsaAcc.getCheques().add(cheque);
					cheque.getBankSundaySchoolAccounts().add(bankSsaAcc);
				}

				ssa.setAmount(Float.parseFloat(sundaySchool.getText()));
				ssa.setCr_dr("CR");
				ssa.setDescription("Service - Sunday School Offering");
				ssa.setDate(date.getSelectedDate());
				ssa.setServiceOffering(so);
				((Account)ssa).setLedger(ledger);
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

	public void onCashButtonPressed() {
		this.cash.setSelected(true);
		this.chequeBtn.setSelected(false);
		this.chequeDetails.setVisible(false);
	}

	public void onChequeButtonPressed() {
		this.cash.setSelected(false);
		this.chequeBtn.setSelected(true);
		this.chequeDetails.setVisible(true);
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
