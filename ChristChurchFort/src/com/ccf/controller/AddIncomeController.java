package com.ccf.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
			float balance = 0;
			logger.debug("Account Name : " + accounts.getValue());
			if (accounts.getValue().equals("PC Account")) {
				account = new PCAccount();
				balance = dao.getPCAccountBalance()
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Missionary Account")){
				account = new MissionaryAccount();
				balance = dao.getMissionaryAccountBalance()
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Men's Account")){
				account = new MensAccount();
				balance = dao.getMensAccountBalance()
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Women's Account")){
				account = new WomensAccount();
				balance = dao.getWomensAccountBalance()
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Sunday School Account")){
				account = new SundaySchoolAccount();
				balance = dao.getSundaySchoolAccountBalance()
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Youth Account")){
				account = new YouthAccount();
				balance = dao.getYouthAccountBalance()
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("STO Account")){
				account = new SpecialThanksOfferingAccount();
				balance = dao.getSpecialThanksOfferingAccountBalance()
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Graveyard Account")){
				account = new GraveyardAccount();
				balance = dao.getGraveyardAccountBalance()
						+ Float.valueOf(amount.getText());
			} else if(accounts.getValue().equals("Primary School Account")){
				account = new PrimarySchoolAccount();
				balance = dao.getPrimarySchoolAccountBalance()
						+ Float.valueOf(amount.getText());
			}
				logger.debug(amount.getText());
				logger.debug(account);
				account.setAmount(Float.valueOf(amount.getText()));
				account.setBalance(balance);
				account.setDescription(reason.getText());
				account.setCr_dr("CR");
				account.setDate(new Date());
				dao.addIncomeorExpense(account);
			
				message.setText("Income added Successfully");
		} catch (CcfException e) {
			message.setText(e.getMessage());
			e.printStackTrace();
		}
		logger.info("Save Income method Ends..");
	}
}
