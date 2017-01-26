package com.ccf.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ccf.dao.AccountsDao;
import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.BankGraveyardAccount;
import com.ccf.persistence.classes.BankMensAccount;
import com.ccf.persistence.classes.BankMissionaryAccount;
import com.ccf.persistence.classes.BankPCAccount;
import com.ccf.persistence.classes.BankPrimarySchoolAccount;
import com.ccf.persistence.classes.BankSpecialThanksOfferingAccount;
import com.ccf.persistence.classes.BankSundaySchoolAccount;
import com.ccf.persistence.classes.BankWomensAccount;
import com.ccf.persistence.classes.BankYouthAccount;
import com.ccf.persistence.classes.Cheque;
import com.ccf.persistence.classes.GraveyardAccount;
import com.ccf.persistence.classes.Ledger;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.PrimarySchoolAccount;
import com.ccf.persistence.classes.SpecialThanksOfferingAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.vo.Account;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

import com.ccf.util.AccountNames;

public class AddIncomeController {

	final static Logger logger = Logger.getLogger(AddIncomeController.class);

	@FXML
	private TextField amount;

	@FXML
	private ChoiceBox<String> accounts;

	@FXML
	private ChoiceBox<Ledger> ledgers;

	@FXML
	private TextArea reason;

	@FXML
	private Label message;

	@FXML
	private RadioButton cash;

	@FXML
	private RadioButton cheque;

	@FXML
	private DatePicker chequeDate;

	@FXML
	private TextField chequeNumber;

	@FXML
	private GridPane grid;
	
	@FXML
	private TextField ledgerName;
	
	@FXML
	private Button addLedger;
	
	@FXML
	private Button cancelLedger;

	@FXML
	void initialize() {
		List<String> accountNames = new ArrayList<>();
		accountNames.add(AccountNames.PCAccount);
		accountNames.add(AccountNames.MissionaryAccount);
		accountNames.add(AccountNames.MensAccount);
		accountNames.add(AccountNames.WomensAccount);
		accountNames.add(AccountNames.SundaySchoolAccount);
		accountNames.add(AccountNames.YouthAccount);
		accountNames.add(AccountNames.STOAccount);
		accountNames.add(AccountNames.GraveyardAccount);
		accountNames.add(AccountNames.PrimarySchoolAccount);
		accounts.setValue(AccountNames.PCAccount);
		accounts.getItems().addAll(accountNames);

		grid.getChildren().get(9).setVisible(false);
		grid.getChildren().get(8).setVisible(false);
		grid.getChildren().get(10).setVisible(false);
		grid.getChildren().get(11).setVisible(false);
		
		this.ledgerName.setVisible(false);
		this.addLedger.setVisible(false);
		this.cancelLedger.setVisible(false);
		
		AccountsDao dao = new AccountsDaoImpl();
		try {
			List<Ledger> ledgers = dao.getAllLedgers();
			if(ledgers.size() > 0)
				this.ledgers.setValue(ledgers.get(0));
			this.ledgers.getItems().addAll(ledgers);
		} catch (CcfException e) {
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		}
		
		this.cash.setSelected(true);
	}

	public void saveIncome() {
		logger.info("Save Income method Starts..");
		AccountsDao dao = new AccountsDaoImpl();
		Account account = null;
		try {
			/*
			 * Validation
			 */
			if (amount.getText() == null || amount.getText().trim().equals("")) {
				throw new CcfException("Amount cannot be empty");
			} else if (reason.getText() == null
					|| reason.getText().trim().equals("")) {
				throw new CcfException("Reason cannot be empty");
			}

			Cheque cheque = null;
			String accName = null;
			if (this.cheque.isSelected()) {
				cheque = new Cheque();
				cheque.setChequeNumber(this.chequeNumber.getText());
				cheque.setChequeDate(chequeDate.getSelectedDate());
			}

			float amt = Float.valueOf(amount.getText());
			logger.debug("Account Name : " + accounts.getValue());
			if (accounts.getValue().equals(AccountNames.PCAccount)) {
				if (this.cash.isSelected()) {
					account = new PCAccount();
					accName = AccountNames.PCAccount;
				} else if (this.cheque.isSelected()) {
					accName = AccountNames.BankPCAccount;
					account = new BankPCAccount();
					BankPCAccount bankPCAccount = (BankPCAccount) account;
					bankPCAccount.getCheques().add(cheque);
					cheque.getBankPCAccounts().add(bankPCAccount);
				}
			} else if (accounts.getValue().equals(
					AccountNames.MissionaryAccount)) {
				if (this.cash.isSelected()) {
					accName = AccountNames.MissionaryAccount;
					account = new MissionaryAccount();
				} else if (this.cheque.isSelected()) {
					accName = AccountNames.BankMissionaryAccount;
					account = new BankMissionaryAccount();
					BankMissionaryAccount bankMissionaryAccount = (BankMissionaryAccount) account;
					bankMissionaryAccount.getCheques().add(cheque);
					cheque.getBankMissionaryAccounts().add(bankMissionaryAccount);
				}
			} else if (accounts.getValue().equals(AccountNames.MensAccount)) {
				if (this.cash.isSelected()) {
					account = new MensAccount();
					accName = AccountNames.MensAccount;
				} else if (this.cheque.isSelected()) {
					accName = AccountNames.BankMensAccount;
					account = new BankMensAccount();
					BankMensAccount bankMensAccount = (BankMensAccount) account;
					bankMensAccount.getCheques().add(cheque);
					cheque.getBankMensAccounts().add(bankMensAccount);
				}
			} else if (accounts.getValue().equals(AccountNames.WomensAccount)) {
				if (this.cash.isSelected()) {
					accName = AccountNames.WomensAccount;
					account = new WomensAccount();
				} else if (this.cheque.isSelected()) {
					accName = AccountNames.BankWomensAccount;
					account = new BankWomensAccount();
					BankWomensAccount bankWomensAccount = (BankWomensAccount) account;
					bankWomensAccount.getCheques().add(cheque);
					cheque.getBankWomensAccounts().add(bankWomensAccount);
				}
			} else if (accounts.getValue().equals(
					AccountNames.SundaySchoolAccount)) {
				if (this.cash.isSelected()) {
					accName = AccountNames.SundaySchoolAccount;
					account = new SundaySchoolAccount();
				} else if (this.cheque.isSelected()) {
					accName = AccountNames.BankSundaySchoolAccount;
					account = new BankSundaySchoolAccount();
					BankSundaySchoolAccount bankSundaySchoolAccount = (BankSundaySchoolAccount) account;
					bankSundaySchoolAccount.getCheques().add(cheque);
					cheque.getBankSundaySchoolAccounts().add(bankSundaySchoolAccount);
				}
			} else if (accounts.getValue().equals(AccountNames.YouthAccount)) {
				if (this.cash.isSelected()) {
					accName = AccountNames.YouthAccount;
					account = new YouthAccount();
				} else if (this.cheque.isSelected()) {
					accName = AccountNames.BankYouthAccount;
					account = new BankYouthAccount();
					BankYouthAccount bankYouthAccount = (BankYouthAccount) account;
					bankYouthAccount.getCheques().add(cheque);
					cheque.getBankYouthAccounts().add(bankYouthAccount);
				}
			} else if (accounts.getValue().equals(AccountNames.STOAccount)) {
				if (this.cash.isSelected()) {
					accName = AccountNames.STOAccount;
					account = new SpecialThanksOfferingAccount();
				} else if (this.cheque.isSelected()) {
					accName = AccountNames.BankSTOAccount;
					account = new BankSpecialThanksOfferingAccount();
					BankSpecialThanksOfferingAccount bankSTOAccount = (BankSpecialThanksOfferingAccount) account;
					bankSTOAccount.getCheques().add(cheque);
					cheque.getBankSTOAccounts().add(bankSTOAccount);
				}
			} else if (accounts.getValue()
					.equals(AccountNames.GraveyardAccount)) {
				if (this.cash.isSelected()) {
					accName = AccountNames.GraveyardAccount;
					account = new GraveyardAccount();
				} else if (this.cheque.isSelected()) {
					accName = AccountNames.BankGraveyardAccount;
					account = new BankGraveyardAccount();
					BankGraveyardAccount bankGraveyardAccount = (BankGraveyardAccount) account;
					bankGraveyardAccount.getCheques().add(cheque);
					cheque.getBankGraveyardAccounts().add(bankGraveyardAccount);
				}
			} else if (accounts.getValue().equals(
					AccountNames.PrimarySchoolAccount)) {
				if (this.cash.isSelected()) {
					accName = AccountNames.PrimarySchoolAccount;
					account = new PrimarySchoolAccount();
				} else if (this.cheque.isSelected()) {
					accName = AccountNames.BankPrimarySchoolAccount;
					account = new BankPrimarySchoolAccount();
					BankPrimarySchoolAccount bankPrimarySchoolAccount = (BankPrimarySchoolAccount) account;
					bankPrimarySchoolAccount.getCheques().add(cheque);
					cheque.getBankPrimarySchoolAccounts().add(bankPrimarySchoolAccount);
				}
			}
			logger.debug(amount.getText());
			logger.debug(account);
			account.setAmount(Float.valueOf(amount.getText()));
			account.setDescription(reason.getText());
			account.setCr_dr("CR");
			account.setDate(new Date());
			account.setLedger(this.ledgers.getValue());
			dao.addIncomeorExpense(account, accName, amt);

			message.setTextFill(Paint.valueOf("Green"));
			message.setText("Income added Successfully");

			/*
			 * Clearing the Fields
			 */
			clear();
		} catch (CcfException e) {
			message.setTextFill(Paint.valueOf("Red"));
			message.setText(e.getMessage());
			e.printStackTrace();
		}
		logger.info("Save Income method Ends..");
	}

	public void addLedger(){
		AccountsDao dao = new AccountsDaoImpl();
		Ledger ledger = new Ledger();
		ledger.setLedgerName(this.ledgerName.getText());
		try {
			dao.addLedger(ledger);
			this.ledgers.getItems().clear();
			List<Ledger> ledgers = dao.getAllLedgers();
			this.ledgers.getItems().addAll(ledgers);
			this.ledgers.setValue(ledgers.get(ledgers.size() - 1));
			message.setText("Ledger Added");
			message.setTextFill(Paint.valueOf("GREEN"));
			
			//Clear Text
			this.ledgerName.setText("");
		} catch (CcfException e) {
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		}
		this.ledgerName.setVisible(false);
		this.addLedger.setVisible(false);
		this.cancelLedger.setVisible(false);
	}
	
	public void cancelLedger(){
		this.ledgerName.setVisible(false);
		this.addLedger.setVisible(false);
		this.cancelLedger.setVisible(false);
	}
	
	public void showLedgerDetails(){
		this.ledgerName.setVisible(true);
		this.addLedger.setVisible(true);
		this.cancelLedger.setVisible(true);
	}
	
	public void onCashButtonPressed() {
		this.cash.setSelected(true);
		this.cheque.setSelected(false);
		grid.getChildren().get(9).setVisible(false);
		grid.getChildren().get(8).setVisible(false);
		grid.getChildren().get(10).setVisible(false);
		grid.getChildren().get(11).setVisible(false);
	}

	public void onChequeButtonPressed() {
		this.cash.setSelected(false);
		this.cheque.setSelected(true);
		grid.getChildren().get(9).setVisible(true);
		grid.getChildren().get(8).setVisible(true);
		grid.getChildren().get(10).setVisible(true);
		grid.getChildren().get(11).setVisible(true);
	}

	public void clear() {
		logger.info("clear method Starts...");
		amount.setText("");
		reason.setText("");
		accounts.setValue("PC Account");
		cash.setSelected(true);
		cheque.setSelected(false);
		grid.getChildren().get(9).setVisible(false);
		grid.getChildren().get(8).setVisible(false);
		grid.getChildren().get(10).setVisible(false);
		grid.getChildren().get(11).setVisible(false);
		chequeDate.setSelectedDate(null);
		chequeNumber.setText("");
		logger.info("clear method Ends...");
	}
}
