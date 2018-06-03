package com.ccf.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.ccf.persistence.interfaces.Account;
import com.ccf.util.BalanceUpdator;
import com.ccf.util.Constants;
import com.ccf.util.ProjectProperties;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
		accountNames.add(Constants.PCAccount);
		accountNames.add(Constants.MissionaryAccount);
		accountNames.add(Constants.MensAccount);
		accountNames.add(Constants.WomensAccount);
		accountNames.add(Constants.SundaySchoolAccount);
		accountNames.add(Constants.YouthAccount);
		accountNames.add(Constants.BuildingAccount);
		accountNames.add(Constants.GraveyardAccount);
		accountNames.add(Constants.EducationalFundAccount);
		accountType.setValue(Constants.PCAccount);
		accountType.getItems().addAll(accountNames);
		
		this.date.setDateFormat(ProjectProperties.sdf);
		this.date.setSelectedDate(new Date());

		getBalance();

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
		
		
		

		this.accountType.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0,
							String oldValue, String newValue) {
						logger.info("accountType change Starts...");
						getBalance();
						logger.info("accountType change Ends...");
					}

				});
		
		this.date.selectedDateProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable observable) {
				logger.debug("Date change listener Starts...");
				getBalance();
				logger.debug("Date change listener Ends...");
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

			if (debitAccName.equals(Constants.PCAccount)) {
				creditAccName = Constants.BankPCAccount;
				debitAcc = new PCAccount();
				creditAcc = new BankPCAccount();
			} else if (debitAccName.equals(Constants.EducationalFundAccount)) {
				creditAccName = Constants.BankEducationalFundAccount;
				debitAcc = new EducationalFundAccount();
				creditAcc = new BankEducationalFundAccount();
			} else if (debitAccName.equals(Constants.GraveyardAccount)) {
				creditAccName = Constants.BankGraveyardAccount;
				debitAcc = new GraveyardAccount();
				creditAcc = new BankGraveyardAccount();
			} else if (debitAccName.equals(Constants.MensAccount)) {
				creditAccName = Constants.BankMensAccount;
				debitAcc = new MensAccount();
				creditAcc = new BankMensAccount();
			} else if (debitAccName.equals(Constants.MissionaryAccount)) {
				creditAccName = Constants.BankMissionaryAccount;
				debitAcc = new MissionaryAccount();
				creditAcc = new BankMissionaryAccount();
			} else if (debitAccName.equals(Constants.SundaySchoolAccount)) {
				creditAccName = Constants.BankSundaySchoolAccount;
				debitAcc = new SundaySchoolAccount();
				creditAcc = new BankSundaySchoolAccount();
			} else if (debitAccName.equals(Constants.WomensAccount)) {
				creditAccName = Constants.BankWomensAccount;
				debitAcc = new WomensAccount();
				creditAcc = new BankWomensAccount();
			} else if (debitAccName.equals(Constants.YouthAccount)) {
				creditAccName = Constants.BankYouthAccount;
				debitAcc = new YouthAccount();
				creditAcc = new BankYouthAccount();
			} else if (debitAccName.equals(Constants.BuildingAccount)) {
				creditAccName = Constants.BankBuildingAccount;
				debitAcc = new BuildingAccount();
				creditAcc = new BankBuildingAccount();
			}

			Ledger ledger = ledgerMap.get("Withdrawal");

			float debitAccCurrentBalance = impl.getAccountBalance(debitAcc.getClass(), date.getSelectedDate());
			float balance = debitAccCurrentBalance - amount;
			
			debitAcc.setAmount(amount);
			debitAcc.setCr_dr("DR");
			debitAcc.setDate(date.getSelectedDate());
			debitAcc.setDescription("Withdrawal");
			debitAcc.setBalance(balance);
			debitAcc.setLedger(ledger);

			float creditAccCurrentBalance = impl.getAccountBalance(creditAcc.getClass(), date.getSelectedDate());
			balance = creditAccCurrentBalance + amount;
			
			ledger = ledgerMap.get("Deposited");
			creditAcc.setAmount(amount);
			creditAcc.setCr_dr("CR");
			creditAcc.setDate(date.getSelectedDate());
			creditAcc.setDescription("Deposited");
			creditAcc.setBalance(balance);
			creditAcc.setLedger(ledger);

			impl.withdrawOrDeposit(creditAcc, creditAccName, debitAcc,
					debitAccName, amount);

			getBalance();
			clear();
			
			/*
			 * Running Thread to update the balances
			 */
			BalanceUpdator balanceUpdator = BalanceUpdator.getInstance();
			balanceUpdator.updateAllBalances();
			
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
	
	public void getBalance(){
		logger.debug("getBalance method Starts...");
		Class entity = null;
		if(this.accountType.getValue().equals(Constants.PCAccount))
			entity = PCAccount.class;
		else if(this.accountType.getValue().equals(Constants.MissionaryAccount))
			entity = MissionaryAccount.class;
		else if(this.accountType.getValue().equals(Constants.MensAccount))
			entity = MensAccount.class;
		else if(this.accountType.getValue().equals(Constants.WomensAccount))
			entity = WomensAccount.class;
		else if(this.accountType.getValue().equals(Constants.SundaySchoolAccount))
			entity = SundaySchoolAccount.class;
		else if(this.accountType.getValue().equals(Constants.YouthAccount))
			entity = YouthAccount.class;
		else if(this.accountType.getValue().equals(Constants.BuildingAccount))
			entity = BuildingAccount.class;
		else if(this.accountType.getValue().equals(Constants.GraveyardAccount))
			entity = GraveyardAccount.class;
		else if(this.accountType.getValue().equals(Constants.EducationalFundAccount))
			entity = EducationalFundAccount.class;
		try {
			float balance = impl.getAccountBalance(entity, date.getSelectedDate());
			this.cashAmt.setText(String.valueOf(balance));
		} catch (CcfException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		}
		logger.debug("getBalance method Ends...");
	}
}
