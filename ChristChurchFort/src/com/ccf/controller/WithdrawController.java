package com.ccf.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ccf.dao.AccountsDao;
import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.BankBuildingAccount;
import com.ccf.persistence.classes.BankEducationalFundAccount;
import com.ccf.persistence.classes.BankGraveyardAccount;
import com.ccf.persistence.classes.BankMensAccount;
import com.ccf.persistence.classes.BankMissionaryAccount;
import com.ccf.persistence.classes.BankPCAccount;
import com.ccf.persistence.classes.BankSundaySchoolAccount;
import com.ccf.persistence.classes.BankWomensAccount;
import com.ccf.persistence.classes.BankYouthAccount;
import com.ccf.persistence.classes.BuildingAccount;
import com.ccf.persistence.classes.EducationalFundAccount;
import com.ccf.persistence.classes.GraveyardAccount;
import com.ccf.persistence.classes.Ledger;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.util.AccountNames;
import com.ccf.util.ProjectProperties;
import com.ccf.vo.Account;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

public class WithdrawController {

	private final static Logger logger = Logger
			.getLogger(WithdrawController.class);
	
	@FXML
	private Label message;
	
	@FXML
	private TextField amount;
	
	@FXML
	private Label bankBalance;
	
	@FXML
	private DatePicker date;
	
	@FXML
	private ChoiceBox<String> accountType;
	
	private AccountsDao impl = new AccountsDaoImpl();
	private Map<String, Ledger> ledgerMap = new HashMap<>();
	
	@FXML
	void initialize() {
		List<String> accountNames = new ArrayList<>();
		accountNames.add(AccountNames.PCAccount);
		accountNames.add(AccountNames.MissionaryAccount);
		accountNames.add(AccountNames.MensAccount);
		accountNames.add(AccountNames.WomensAccount);
		accountNames.add(AccountNames.SundaySchoolAccount);
		accountNames.add(AccountNames.YouthAccount);
		accountNames.add(AccountNames.BuildingAccount);
		accountNames.add(AccountNames.GraveyardAccount);
		accountNames.add(AccountNames.EducationalFundAccount);
		accountType.setValue(AccountNames.PCAccount);
		accountType.getItems().addAll(accountNames);

		try {
			float balance = impl.getAccountBalance(AccountNames.BankPCAccount);
			this.bankBalance.setText(String.valueOf(balance));
		} catch (CcfException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		}

		/*
		 * Getting ledger records from DB
		 */
		List<Ledger> ledgers = null;
		try {
			ledgers = impl.getAllLedgers();
		} catch (CcfException e1) {
			e1.printStackTrace();
		}
		for (Ledger l : ledgers) {
			ledgerMap.put(l.getLedgerName(), l);
		}
		
		
		this.date.setDateFormat(ProjectProperties.sdf);

		this.accountType.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0,
							String oldValue, String newValue) {
						logger.info("accountType change Starts...");
						try {
							float balance = impl.getAccountBalance("Bank " + newValue);
							bankBalance.setText(String.valueOf(balance));
						} catch (CcfException e) {
							e.printStackTrace();
							logger.error(e.getMessage());
							message.setText(e.getMessage());
							message.setTextFill(Paint.valueOf("RED"));
						}
						logger.info("accountType change Ends...");
					}

				});
	}
	
	public void withdraw(){
		logger.info("withdraw method Starts...");
		String debitAccName = null;
		float amount = Float.parseFloat(this.amount.getText());
		String creditAccName = this.accountType.getValue();
		Account creditAcc = null;
		Account debitAcc = null;
		try {
			/*
			 * Validation
			 */
			if(this.date.getSelectedDate() == null){
				throw new CcfException("Please Select the date.");
			}
			else if (amount > Float.parseFloat(this.bankBalance.getText())) {
				throw new CcfException("You are out of balance. Maximum allowed withdrawal is INR. "
						+ this.bankBalance.getText());
			}

			if (creditAccName.equals(AccountNames.PCAccount)) {
				debitAccName = AccountNames.BankPCAccount;
				debitAcc = new BankPCAccount();
				creditAcc = new PCAccount();
			} else if (creditAccName.equals(AccountNames.EducationalFundAccount)) {
				debitAccName = AccountNames.BankEducationalFundAccount;
				debitAcc = new BankEducationalFundAccount();
				creditAcc = new EducationalFundAccount();
			} else if (creditAccName.equals(AccountNames.GraveyardAccount)) {
				debitAccName = AccountNames.BankGraveyardAccount;
				debitAcc = new BankGraveyardAccount();
				creditAcc = new GraveyardAccount();
			} else if (creditAccName.equals(AccountNames.MensAccount)) {
				debitAccName = AccountNames.BankMensAccount;
				debitAcc = new BankMensAccount();
				creditAcc = new MensAccount();
			} else if (creditAccName.equals(AccountNames.MissionaryAccount)) {
				debitAccName = AccountNames.BankMissionaryAccount;
				debitAcc = new BankMissionaryAccount();
				creditAcc = new MissionaryAccount();
			} else if (creditAccName.equals(AccountNames.SundaySchoolAccount)) {
				debitAccName = AccountNames.BankSundaySchoolAccount;
				debitAcc = new BankSundaySchoolAccount();
				creditAcc = new SundaySchoolAccount();
			} else if (creditAccName.equals(AccountNames.WomensAccount)) {
				debitAccName = AccountNames.BankWomensAccount;
				debitAcc = new BankWomensAccount();
				creditAcc = new WomensAccount();
			} else if (creditAccName.equals(AccountNames.YouthAccount)) {
				debitAccName = AccountNames.BankYouthAccount;
				debitAcc = new BankYouthAccount();
				creditAcc = new YouthAccount();
			} else if (creditAccName.equals(AccountNames.BuildingAccount)) {
				debitAccName = AccountNames.BankBuildingAccount;
				debitAcc = new BankBuildingAccount();
				creditAcc = new BuildingAccount();
			}

			Ledger ledger = ledgerMap.get("Withdrawal");

			debitAcc.setAmount(amount);
			debitAcc.setCr_dr("DR");
			debitAcc.setDate(date.getSelectedDate());
			debitAcc.setDescription("Withdrawal");
			debitAcc.setLedger(ledger);

			ledger = ledgerMap.get("Deposited");
			creditAcc.setAmount(amount);
			creditAcc.setCr_dr("CR");
			creditAcc.setDate(date.getSelectedDate());
			creditAcc.setDescription("Deposited");
			creditAcc.setLedger(ledger);

			impl.withdrawOrDeposit(creditAcc, creditAccName, debitAcc,
					debitAccName, amount);

			this.bankBalance.setText(String.valueOf(impl.getAccountBalance(debitAccName)));
			clear();
			message.setText("Cash withdrawn Successfully");
			message.setTextFill(Paint.valueOf("GREEN"));
		} catch (CcfException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		}
		logger.info("withdraw method Ends...");
	}
	
	public void clear(){
		logger.info("clear method Starts...");
		this.date.setSelectedDate(null);
		this.amount.setText(null);
		logger.info("clear method Ends...");
	}
}
