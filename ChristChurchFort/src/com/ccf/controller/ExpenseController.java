package com.ccf.controller;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.doa.AccountsDao;
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
		account.getItems().add("PC Account");
		account.getItems().add("Missionary Account");
		account.getItems().add("Men's Account");
		account.getItems().add("Women's Account");
		account.getItems().add("Sunday School Account");
		account.getItems().add("Youth Account");
		account.getItems().add("Special Thanks Offering Account");
		account.getItems().add("Graveyard Account");
		account.getItems().add("Primary Account");
		account.setValue("PC Account");
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
			if (this.account.getValue().equals("PC Account")) {
				account = new PCAccount();
				currentBalance = impl.getPCAccountBalance();
			} else if (this.account.getValue().equals("Missionary Account")) {
				account = new MissionaryAccount();
				currentBalance = impl.getMissionaryAccountBalance();
			} else if (this.account.getValue().equals("Men's Account")) {
				account = new MensAccount();
				currentBalance = impl.getMensAccountBalance();
			} else if (this.account.getValue().equals("Women's Account")) {
				account = new WomensAccount();
				currentBalance = impl.getWomensAccountBalance();
			} else if (this.account.getValue().equals("Sunday School Account")) {
				account = new SundaySchoolAccount();
				currentBalance = impl.getSundaySchoolAccountBalance();
			} else if (this.account.getValue().equals("Youth Account")) {
				account = new YouthAccount();
				currentBalance = impl.getYouthAccountBalance();
			} else if (this.account.getValue().equals(
					"Special Thanks Offering Account")) {
				account = new SpecialThanksOfferingAccount();
				currentBalance = impl.getSpecialThanksOfferingAccountBalance();
			} else if (this.account.getValue().equals("Graveyard Account")) {
				account = new GraveyardAccount();
				currentBalance = impl.getGraveyardAccountBalance();
			} else if (this.account.getValue().equals("Primary Account")) {
				account = new PrimarySchoolAccount();
				currentBalance = impl.getPrimarySchoolAccountBalance();
			}

			float amount = Float.parseFloat(this.amount.getText());
			float balance = currentBalance - amount;
			if (balance < 0.0f) {
				throw new CcfException("Sorry!! No Enough Money to withdraw (Balance : " + currentBalance + ")");
			}
			account.setAmount(amount);
			account.setBalance(balance);
			account.setCr_dr("DR");
			account.setDescription(description.getText());
			account.setDate(new Date());
			impl.addIncomeorExpense(account);

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
