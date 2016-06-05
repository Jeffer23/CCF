package com.ccf.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.doa.AccountsDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.vo.AccStatement;
import com.ccf.vo.Account;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class AccountStatement {

	final static Logger logger = Logger.getLogger(AccountStatement.class);

	@FXML
	private Label message;

	@FXML
	private ChoiceBox<String> accountNames;

	@FXML
	private DatePicker from;

	@FXML
	private DatePicker to;

	@FXML
	private ChoiceBox<String> transactionType;

	@FXML
	private TableView<AccStatement> income;

	@FXML
	private TableView<AccStatement> expense;
	
	@FXML
	private Label totalIncomeLbl;
	
	@FXML
	private Label totalExpenseLbl;
	

	@FXML
	void initialize() {
		List<String> accountNamesList = new ArrayList<>();
		accountNamesList.add("PC Account");
		accountNamesList.add("Missionary Account");
		accountNamesList.add("Men's Account");
		accountNamesList.add("Women's Account");
		accountNamesList.add("Sunday School Account");
		accountNamesList.add("Youth Account");
		accountNamesList.add("STO Account");
		accountNamesList.add("Graveyard Account");
		accountNamesList.add("Primary School Account");
		accountNames.setValue("PC Account");
		accountNames.getItems().addAll(accountNamesList);

		transactionType.getItems().add("Both-Credit and Debit");
		transactionType.getItems().add("Credit");
		transactionType.getItems().add("Debit");
		transactionType.setValue("Both-Credit and Debit");
	}

	public void getDetails() {
		logger.debug("getDetails method starts...");
		income.getItems().clear();
		expense.getItems().clear();
		AccountsDao dao = new AccountsDaoImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		float totalIncome = 0.0f;
		float totalExpense = 0.0f;
		try {
			List<? extends Account> accounts = null;
			if (accountNames.getValue().equals("PC Account")) {
				accounts = dao.getPCAccountStatement(
						from.getSelectedDate(), to.getSelectedDate());
			} else if (accountNames.getValue().equals("Missionary Account")) {
				accounts = dao.getMissionaryStatement(
						from.getSelectedDate(), to.getSelectedDate());
			} else if (accountNames.getValue().equals("Men's Account")) {
				accounts = dao.getMensStatement(
						from.getSelectedDate(), to.getSelectedDate());
			} else if (accountNames.getValue().equals("Women's Account")) {
				accounts = dao.getWomensStatement(
						from.getSelectedDate(), to.getSelectedDate());
			} else if (accountNames.getValue().equals("Sunday School Account")) {
				accounts = dao.getSundaySchoolStatement(
						from.getSelectedDate(), to.getSelectedDate());
			} else if (accountNames.getValue().equals("Youth Account")) {
				accounts = dao.getYouthStatement(
						from.getSelectedDate(), to.getSelectedDate());
			} else if (accountNames.getValue().equals("STO Account")) {
				accounts = dao.getSTOStatement(
						from.getSelectedDate(), to.getSelectedDate());
			} else if (accountNames.getValue().equals("Graveyard Account")) {
				accounts = dao.getGraveyardStatement(
						from.getSelectedDate(), to.getSelectedDate());
			} else if (accountNames.getValue().equals("Primary School Account")) {
				accounts = dao.getPrimarySchoolStatement(
						from.getSelectedDate(), to.getSelectedDate());
			}
			
			logger.debug("Record Count : " + accounts.size());
			AccStatement accStmt = null;
			for(Account account : accounts){
				accStmt = new AccStatement();
				accStmt.setAmount(Float.valueOf(account.getAmount()));
				accStmt.setDescription(account.getDescription());
				accStmt.setDate(sdf.format(account.getDate()));
				if(account.getCr_dr().equals("CR")) {
					income.getItems().add(accStmt);
					totalIncome += account.getAmount();
				}
				else if(account.getCr_dr().equals("DR")) {
					expense.getItems().add(accStmt);
					totalExpense += account.getAmount();
				}
			}
			
			totalIncomeLbl.setText(String.valueOf(totalIncome));
			totalExpenseLbl.setText(String.valueOf(totalExpense));
			
		} catch (CcfException e) {
			e.printStackTrace();
		}

		logger.debug("getDetails method ends...");
	}
}
