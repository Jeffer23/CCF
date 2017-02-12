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

public class DepositController {

	private final static Logger logger = Logger
			.getLogger(DepositController.class);

	@FXML
	private Label message;

	@FXML
	private TextField amount;

	@FXML
	private Label cashAmt;

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
			float balance = impl.getAccountBalance(AccountNames.PCAccount);
			this.cashAmt.setText(String.valueOf(balance));
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
							float balance = impl.getAccountBalance(newValue);
							cashAmt.setText(String.valueOf(balance));
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

	public void deposit() {
		logger.info("deposit method Starts...");
		String debitAccName = this.accountType.getValue();
		float amount = Float.parseFloat(this.amount.getText());
		String creditAccName = null;
		Account creditAcc = null;
		Account debitAcc = null;
		try {
			/*
			 * Validation
			 */
			if(this.date.getSelectedDate() == null){
				throw new CcfException("Please Select the date.");
			}
			else if (amount > Float.parseFloat(this.cashAmt.getText())) {
				throw new CcfException("You are out of cash. Maximum allowed deposit is INR. "
						+ this.cashAmt.getText());
			}

			if (debitAccName.equals(AccountNames.PCAccount)) {
				creditAccName = AccountNames.BankPCAccount;
				debitAcc = new PCAccount();
				creditAcc = new BankPCAccount();
			} else if (debitAccName.equals(AccountNames.EducationalFundAccount)) {
				creditAccName = AccountNames.BankEducationalFundAccount;
				debitAcc = new EducationalFundAccount();
				creditAcc = new BankEducationalFundAccount();
			} else if (debitAccName.equals(AccountNames.GraveyardAccount)) {
				creditAccName = AccountNames.BankGraveyardAccount;
				debitAcc = new GraveyardAccount();
				creditAcc = new BankGraveyardAccount();
			} else if (debitAccName.equals(AccountNames.MensAccount)) {
				creditAccName = AccountNames.BankMensAccount;
				debitAcc = new MensAccount();
				creditAcc = new BankMensAccount();
			} else if (debitAccName.equals(AccountNames.MissionaryAccount)) {
				creditAccName = AccountNames.BankMissionaryAccount;
				debitAcc = new MissionaryAccount();
				creditAcc = new BankMissionaryAccount();
			} else if (debitAccName.equals(AccountNames.SundaySchoolAccount)) {
				creditAccName = AccountNames.BankSundaySchoolAccount;
				debitAcc = new SundaySchoolAccount();
				creditAcc = new BankSundaySchoolAccount();
			} else if (debitAccName.equals(AccountNames.WomensAccount)) {
				creditAccName = AccountNames.BankWomensAccount;
				debitAcc = new WomensAccount();
				creditAcc = new BankWomensAccount();
			} else if (debitAccName.equals(AccountNames.YouthAccount)) {
				creditAccName = AccountNames.BankYouthAccount;
				debitAcc = new YouthAccount();
				creditAcc = new BankYouthAccount();
			} else if (debitAccName.equals(AccountNames.BuildingAccount)) {
				creditAccName = AccountNames.BankBuildingAccount;
				debitAcc = new BuildingAccount();
				creditAcc = new BankBuildingAccount();
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

			this.cashAmt.setText(String.valueOf(impl.getAccountBalance(debitAccName)));
			clear();
			message.setText("Cash deposited Successfully");
			message.setTextFill(Paint.valueOf("GREEN"));
		} catch (CcfException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		}
		logger.info("deposit method Ends...");
	}

	public void clear() {
		logger.info("clear method Starts...");
		this.date.setSelectedDate(null);
		this.amount.setText(null);
		logger.info("clear method Ends...");
	}
}
