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
import com.ccf.persistence.classes.BankEducationalFundAccount;
import com.ccf.persistence.classes.BankBuildingAccount;
import com.ccf.persistence.classes.BankSundaySchoolAccount;
import com.ccf.persistence.classes.BankWomensAccount;
import com.ccf.persistence.classes.BankYouthAccount;
import com.ccf.persistence.classes.Cheque;
import com.ccf.persistence.classes.GraveyardAccount;
import com.ccf.persistence.classes.Ledger;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.EducationalFundAccount;
import com.ccf.persistence.classes.BuildingAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.persistence.interfaces.Account;

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

import com.ccf.thread.BalanceUpdateThread;
import com.ccf.util.BalanceUpdator;
import com.ccf.util.Constants;
import com.ccf.util.ProjectProperties;

public class AddIncomeController {

	final static Logger logger = Logger.getLogger(AddIncomeController.class);

	@FXML
	private TextField amount;

	@FXML
	private ChoiceBox<String> accounts;

	@FXML
	private ChoiceBox<Ledger> ledgers;
	
	@FXML
	private DatePicker date;

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
		accountNames.add(Constants.PCAccount);
		accountNames.add(Constants.MissionaryAccount);
		accountNames.add(Constants.MensAccount);
		accountNames.add(Constants.WomensAccount);
		accountNames.add(Constants.SundaySchoolAccount);
		accountNames.add(Constants.YouthAccount);
		accountNames.add(Constants.BuildingAccount);
		accountNames.add(Constants.GraveyardAccount);
		accountNames.add(Constants.EducationalFundAccount);
		accounts.setValue(Constants.PCAccount);
		accounts.getItems().addAll(accountNames);

		grid.getChildren().get(9).setVisible(false);
		grid.getChildren().get(8).setVisible(false);
		grid.getChildren().get(10).setVisible(false);
		grid.getChildren().get(11).setVisible(false);
		
		this.ledgerName.setVisible(false);
		this.addLedger.setVisible(false);
		this.cancelLedger.setVisible(false);
		
		this.chequeDate.setDateFormat(ProjectProperties.sdf);
		this.date.setDateFormat(ProjectProperties.sdf);
		this.date.setSelectedDate(new Date());
		
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
			} else if(this.date.getSelectedDate() == null){
				throw new CcfException("Please select the date");
			}

			Cheque cheque = null;
			String accName = null;
			
			/*
			 * Validate Cheque Exists
			 */
			boolean chequeExists = dao.isChequeExists(this.chequeNumber.getText());
			if(chequeExists){
				throw new CcfException("This Cheque details already entered");
			}
			
			if (this.cheque.isSelected()) {
				cheque = new Cheque();
				cheque.setChequeNumber(this.chequeNumber.getText());
				cheque.setChequeDate(chequeDate.getSelectedDate());
			}

			float amt = Float.valueOf(amount.getText());
			logger.debug("Account Name : " + accounts.getValue());
			if (accounts.getValue().equals(Constants.PCAccount)) {
				if (this.cash.isSelected()) {
					account = new PCAccount();
					accName = Constants.PCAccount;
				} else if (this.cheque.isSelected()) {
					accName = Constants.BankPCAccount;
					account = new BankPCAccount();
					BankPCAccount bankPCAccount = (BankPCAccount) account;
					bankPCAccount.getCheques().add(cheque);
					cheque.getBankPCAccounts().add(bankPCAccount);
				}
			} else if (accounts.getValue().equals(
					Constants.MissionaryAccount)) {
				if (this.cash.isSelected()) {
					accName = Constants.MissionaryAccount;
					account = new MissionaryAccount();
				} else if (this.cheque.isSelected()) {
					accName = Constants.BankMissionaryAccount;
					account = new BankMissionaryAccount();
					BankMissionaryAccount bankMissionaryAccount = (BankMissionaryAccount) account;
					bankMissionaryAccount.getCheques().add(cheque);
					cheque.getBankMissionaryAccounts().add(bankMissionaryAccount);
				}
			} else if (accounts.getValue().equals(Constants.MensAccount)) {
				if (this.cash.isSelected()) {
					account = new MensAccount();
					accName = Constants.MensAccount;
				} else if (this.cheque.isSelected()) {
					accName = Constants.BankMensAccount;
					account = new BankMensAccount();
					BankMensAccount bankMensAccount = (BankMensAccount) account;
					bankMensAccount.getCheques().add(cheque);
					cheque.getBankMensAccounts().add(bankMensAccount);
				}
			} else if (accounts.getValue().equals(Constants.WomensAccount)) {
				if (this.cash.isSelected()) {
					accName = Constants.WomensAccount;
					account = new WomensAccount();
				} else if (this.cheque.isSelected()) {
					accName = Constants.BankWomensAccount;
					account = new BankWomensAccount();
					BankWomensAccount bankWomensAccount = (BankWomensAccount) account;
					bankWomensAccount.getCheques().add(cheque);
					cheque.getBankWomensAccounts().add(bankWomensAccount);
				}
			} else if (accounts.getValue().equals(
					Constants.SundaySchoolAccount)) {
				if (this.cash.isSelected()) {
					accName = Constants.SundaySchoolAccount;
					account = new SundaySchoolAccount();
				} else if (this.cheque.isSelected()) {
					accName = Constants.BankSundaySchoolAccount;
					account = new BankSundaySchoolAccount();
					BankSundaySchoolAccount bankSundaySchoolAccount = (BankSundaySchoolAccount) account;
					bankSundaySchoolAccount.getCheques().add(cheque);
					cheque.getBankSundaySchoolAccounts().add(bankSundaySchoolAccount);
				}
			} else if (accounts.getValue().equals(Constants.YouthAccount)) {
				if (this.cash.isSelected()) {
					accName = Constants.YouthAccount;
					account = new YouthAccount();
				} else if (this.cheque.isSelected()) {
					accName = Constants.BankYouthAccount;
					account = new BankYouthAccount();
					BankYouthAccount bankYouthAccount = (BankYouthAccount) account;
					bankYouthAccount.getCheques().add(cheque);
					cheque.getBankYouthAccounts().add(bankYouthAccount);
				}
			} else if (accounts.getValue().equals(Constants.BuildingAccount)) {
				if (this.cash.isSelected()) {
					accName = Constants.BuildingAccount;
					account = new BuildingAccount();
				} else if (this.cheque.isSelected()) {
					accName = Constants.BankBuildingAccount;
					account = new BankBuildingAccount();
					BankBuildingAccount bankSTOAccount = (BankBuildingAccount) account;
					bankSTOAccount.getCheques().add(cheque);
					cheque.getBankSTOAccounts().add(bankSTOAccount);
				}
			} else if (accounts.getValue()
					.equals(Constants.GraveyardAccount)) {
				if (this.cash.isSelected()) {
					accName = Constants.GraveyardAccount;
					account = new GraveyardAccount();
				} else if (this.cheque.isSelected()) {
					accName = Constants.BankGraveyardAccount;
					account = new BankGraveyardAccount();
					BankGraveyardAccount bankGraveyardAccount = (BankGraveyardAccount) account;
					bankGraveyardAccount.getCheques().add(cheque);
					cheque.getBankGraveyardAccounts().add(bankGraveyardAccount);
				}
			} else if (accounts.getValue().equals(
					Constants.EducationalFundAccount)) {
				if (this.cash.isSelected()) {
					accName = Constants.EducationalFundAccount;
					account = new EducationalFundAccount();
				} else if (this.cheque.isSelected()) {
					accName = Constants.BankEducationalFundAccount;
					account = new BankEducationalFundAccount();
					BankEducationalFundAccount bankEducationalFundAccount = (BankEducationalFundAccount) account;
					bankEducationalFundAccount.getCheques().add(cheque);
					cheque.getBankEducationalFundAccounts().add(bankEducationalFundAccount);
				}
			}
			logger.debug(amount.getText());
			logger.debug(account);
			
			float amount = Float.valueOf(this.amount.getText());
			float currentBalance = dao.getAccountBalance(account.getClass(),  this.date.getSelectedDate());
			float balance = currentBalance + amount;
			account.setAmount(amount);
			account.setDescription(reason.getText());
			account.setCr_dr("CR");
			account.setDate(this.date.getSelectedDate());
			account.setLedger(this.ledgers.getValue());
			account.setBalance(balance);
			dao.addIncomeorExpense(account, accName, amt);
			
			/*
			 * Running Thread to update the balances
			 */
			BalanceUpdator balanceUpdator = BalanceUpdator.getInstance();
			balanceUpdator.updateAllBalances();

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
