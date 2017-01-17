package com.ccf.controller;

import java.util.Date;

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

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

import com.ccf.util.AccountNames;

public class ExpenseController {

	final static Logger logger = Logger.getLogger(MenuController.class);

	@FXML
	private ChoiceBox<String> account;

	@FXML
	private TextField amount;

	@FXML
	private TextArea description;

	@FXML
	private Label message;

	@FXML
	void initialize() {
		logger.debug("init method Starts...");
		account.getItems().add(AccountNames.PCAccount);
		account.getItems().add(AccountNames.MissionaryAccount);
		account.getItems().add(AccountNames.MensAccount);
		account.getItems().add(AccountNames.WomensAccount);
		account.getItems().add(AccountNames.SundaySchoolAccount);
		account.getItems().add(AccountNames.YouthAccount);
		account.getItems().add(AccountNames.STOAccount);
		account.getItems().add(AccountNames.GraveyardAccount);
		account.getItems().add(AccountNames.PrimarySchoolAccount);
		account.setValue(AccountNames.PCAccount);
		logger.debug("initialize method Ends...");
	}

	public void saveExpense() {
		logger.info("saveExpense method Starts...");
		/*
		 * Logging
		 */
		logger.debug("Account : " + account.getValue());
		logger.debug("Amount : " + amount.getText());
		logger.debug("Description : " + description.getText());

		try {
			/*
			 * Validations
			 */
			if (amount.getText().matches(".*[a-zA-Z]+.*"))
				throw new CcfException("Amount can contain only numbers.");
			if (amount.getText().trim().equals("") || Float.parseFloat(amount.getText()) <= 0.0f)
				throw new CcfException("Please provide the amount");
			if (description.getText() == null
					|| description.getText().trim().equals(""))
				throw new CcfException("Please provide proper description.");

			/*
			 * Logic
			 */

			AccountsDao impl = new AccountsDaoImpl();
			float currentBalance = 0.0f;
			Account account = null;
			if (this.account.getValue().equals(AccountNames.PCAccount)) {
				account = new PCAccount();
				currentBalance = impl.getAccountBalance(AccountNames.PCAccount);
			} else if (this.account.getValue().equals(AccountNames.MissionaryAccount)) {
				account = new MissionaryAccount();
				currentBalance = impl.getAccountBalance(AccountNames.MissionaryAccount);
			} else if (this.account.getValue().equals(AccountNames.MensAccount)) {
				account = new MensAccount();
				currentBalance = impl.getAccountBalance(AccountNames.MensAccount);
			} else if (this.account.getValue().equals(AccountNames.WomensAccount)) {
				account = new WomensAccount();
				currentBalance = impl.getAccountBalance(AccountNames.WomensAccount);
			} else if (this.account.getValue().equals(AccountNames.SundaySchoolAccount)) {
				account = new SundaySchoolAccount();
				currentBalance = impl.getAccountBalance(AccountNames.SundaySchoolAccount);
			} else if (this.account.getValue().equals(AccountNames.YouthAccount)) {
				account = new YouthAccount();
				currentBalance = impl.getAccountBalance(AccountNames.YouthAccount);
			} else if (this.account.getValue().equals(
					AccountNames.STOAccount)) {
				account = new SpecialThanksOfferingAccount();
				currentBalance = impl.getAccountBalance(AccountNames.STOAccount);
			} else if (this.account.getValue().equals(AccountNames.GraveyardAccount)) {
				account = new GraveyardAccount();
				currentBalance = impl.getAccountBalance(AccountNames.GraveyardAccount);
			} else if (this.account.getValue().equals(AccountNames.PrimarySchoolAccount)) {
				account = new PrimarySchoolAccount();
				currentBalance = impl.getAccountBalance(AccountNames.PrimarySchoolAccount);
			}

			float amount = Float.parseFloat(this.amount.getText());
			if (currentBalance < amount) {
				throw new CcfException("Sorry!! No Enough Money to withdraw (Balance : " + currentBalance + ")");
			}
			account.setAmount(amount);
			account.setCr_dr("DR");
			account.setDescription(description.getText());
			account.setDate(new Date());
			impl.addIncomeorExpense(account,this.account.getValue() ,amount);

			message.setText("Expense Added Successfully..");
			message.setTextFill(Paint.valueOf("GREEN"));

		} catch (CcfException e) {
			e.printStackTrace();
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		}
		logger.info("saveExpense method Ends...");
	}

}
