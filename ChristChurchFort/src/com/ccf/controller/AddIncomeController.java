package com.ccf.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ccf.dao.AccountsDao;
import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.GraveyardAccount;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;

public class AddIncomeController {

	final static Logger logger = Logger.getLogger(AddIncomeController.class);

	@FXML
	private TextField amount;

	@FXML
	private ChoiceBox<String> accounts;

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
	void initialize() {
		List<String> accountNames = new ArrayList<>();
		accountNames.add("PC Account");
		accountNames.add("Missionary Account");
		accountNames.add("Men's Account");
		accountNames.add("Women's Account");
		accountNames.add("Sunday School Account");
		accountNames.add("Youth Account");
		accountNames.add("STO Account");
		accountNames.add("Graveyard Account");
		accountNames.add("Primary School Account");
		accounts.setValue("PC Account");
		accounts.getItems().addAll(accountNames);
	}

	public void saveIncome() {
		logger.info("Save Income method Starts..");
		AccountsDao dao = new AccountsDaoImpl();
		Account account = null;
		try {
			/*
			 * Validation
			 */
			if(amount.getText() == null || amount.getText().trim().equals("")){
				throw new CcfException("Amount cannot be empty");
			} else if(reason.getText() == null || reason.getText().trim().equals("")){
				throw new CcfException("Reason cannot be empty");
			}
			float balance = 0;
			logger.debug("Account Name : " + accounts.getValue());
			if (accounts.getValue().equals("PC Account")) {
				account = new PCAccount();
				balance = dao.getAccountBalance("PC Account")
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Missionary Account")){
				account = new MissionaryAccount();
				balance = dao.getAccountBalance("Missionary Account")
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Men's Account")){
				account = new MensAccount();
				balance = dao.getAccountBalance("Mens Account")
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Women's Account")){
				account = new WomensAccount();
				balance = dao.getAccountBalance("Womens Account")
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Sunday School Account")){
				account = new SundaySchoolAccount();
				balance = dao.getAccountBalance("Sunday School Account")
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Youth Account")){
				account = new YouthAccount();
				balance = dao.getAccountBalance("Youth Account")
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("STO Account")){
				account = new SpecialThanksOfferingAccount();
				balance = dao.getAccountBalance("STO Account")
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Graveyard Account")){
				account = new GraveyardAccount();
				balance = dao.getAccountBalance("Graveyard Account")
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Primary School Account")){
				account = new PrimarySchoolAccount();
				balance = dao.getAccountBalance("Primary School Account")
						+ Float.valueOf(amount.getText());
			}
				logger.debug(amount.getText());
				logger.debug(account);
				account.setAmount(Float.valueOf(amount.getText()));
				account.setDescription(reason.getText());
				account.setCr_dr("CR");
				account.setDate(new Date());
				dao.addIncomeorExpense(account);
			
				message.setTextFill(Paint.valueOf("Green"));
				message.setText("Income added Successfully");
				
				/*
				 * Clearing the Fields
				 */
				amount.setText(null);
				reason.setText(null);
		} catch (CcfException e) {
			message.setTextFill(Paint.valueOf("Red"));
			message.setText(e.getMessage());
			e.printStackTrace();
		}
		logger.info("Save Income method Ends..");
	}
	
	public void onCashButtonPressed(){
		this.cash.setSelected(true);
		this.cheque.setSelected(false);
		//this.chequeDetails.setVisible(false);
	}
	
	public void onChequeButtonPressed(){
		this.cash.setSelected(false);
		this.cheque.setSelected(true);
		//this.chequeDetails.setVisible(true);
	}
	
	public void clear() {
		logger.info("clear method Starts...");
		amount.setText("");
		reason.setText("");
		accounts.setValue("PC Account");
		cash.setSelected(true);
		chequeDate.setSelectedDate(null);
		chequeNumber.setText("");
		message.setText("");
		logger.info("clear method Ends...");
	}
}
