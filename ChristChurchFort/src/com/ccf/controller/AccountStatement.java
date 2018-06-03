package com.ccf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

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
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.SundaySchoolAccount;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.persistence.interfaces.Account;
import com.ccf.util.Constants;
import com.ccf.util.ProjectProperties;
import com.ccf.vo.AccStatement;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.paint.Paint;

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
	private Label openingBankAmt;

	@FXML
	private Label openingCashAmt;

	@FXML
	private Label closingBankAmt;

	@FXML
	private Label closingCashAmt;

	@FXML
	private Label totalIncomeLbl;

	@FXML
	private Label totalExpenseLbl;

	@FXML
	void initialize() {
		List<String> accountNamesList = new ArrayList<>();
		accountNamesList.add(Constants.PCAccount);
		accountNamesList.add(Constants.MissionaryAccount);
		accountNamesList.add(Constants.MensAccount);
		accountNamesList.add(Constants.WomensAccount);
		accountNamesList.add(Constants.SundaySchoolAccount);
		accountNamesList.add(Constants.YouthAccount);
		accountNamesList.add(Constants.BuildingAccount);
		accountNamesList.add(Constants.GraveyardAccount);
		accountNamesList.add(Constants.EducationalFundAccount);
		accountNames.setValue(Constants.PCAccount);
		accountNames.getItems().addAll(accountNamesList);

		transactionType.getItems().add("Both-Credit and Debit");
		transactionType.getItems().add("Credit");
		transactionType.getItems().add("Debit");
		transactionType.setValue("Both-Credit and Debit");

		this.from.setDateFormat(ProjectProperties.sdf);
		this.to.setDateFormat(ProjectProperties.sdf);
	}

	public void getDetails() {
		logger.debug("getDetails method starts...");
		this.message.setText(null);
		try {
			if(this.from.getSelectedDate() == null)
				throw new CcfException("Select From Date");
			else if(this.to.getSelectedDate() == null)
				throw new CcfException("Select To Date");
			else if (this.from.getSelectedDate().after(this.to.getSelectedDate()))
				throw new CcfException("'To' date should not be before 'From' date");
			
			income.getItems().clear();
			expense.getItems().clear();
			AccountsDao dao = new AccountsDaoImpl();
			float totalIncome = 0.0f;
			float totalExpense = 0.0f;
			
			float cashOpenbal = 0.0f;
			float cashclosebal = 0.0f;
			float bankOpenbal = 0.0f;
			float bankClosebal = 0.0f;

			List<Account> accounts = null;
			if (accountNames.getValue().equals(Constants.PCAccount)) {
				accounts = dao.getAccountStatement(PCAccount.class,
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement(BankPCAccount.class,
						from.getSelectedDate(), to.getSelectedDate()));
				
				Map<String, Float> balances = dao.getOpeningAndClosingBalance(PCAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				cashOpenbal = balances.get(Constants.OpeningBalance);
				cashclosebal = balances.get(Constants.ClosingBalance);
				balances = dao.getOpeningAndClosingBalance(BankPCAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				bankOpenbal = balances.get(Constants.OpeningBalance);
				bankClosebal = balances.get(Constants.ClosingBalance);
			} else if (accountNames.getValue().equals(
					Constants.MissionaryAccount)) {
				accounts = dao.getAccountStatement(MissionaryAccount.class,
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement(BankMissionaryAccount.class,
						from.getSelectedDate(), to.getSelectedDate()));
				
				Map<String, Float> balances = dao.getOpeningAndClosingBalance(MissionaryAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				cashOpenbal = balances.get(Constants.OpeningBalance);
				cashclosebal = balances.get(Constants.ClosingBalance);
				balances = dao.getOpeningAndClosingBalance(BankMissionaryAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				bankOpenbal = balances.get(Constants.OpeningBalance);
				bankClosebal = balances.get(Constants.ClosingBalance);
			} else if (accountNames.getValue().equals(Constants.MensAccount)) {
				accounts = dao.getAccountStatement(MensAccount.class,
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement(BankMensAccount.class,
						from.getSelectedDate(), to.getSelectedDate()));
				
				Map<String, Float> balances = dao.getOpeningAndClosingBalance(MensAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				cashOpenbal = balances.get(Constants.OpeningBalance);
				cashclosebal = balances.get(Constants.ClosingBalance);
				balances = dao.getOpeningAndClosingBalance(BankMensAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				bankOpenbal = balances.get(Constants.OpeningBalance);
				bankClosebal = balances.get(Constants.ClosingBalance);
			} else if (accountNames.getValue().equals(
					Constants.WomensAccount)) {
				accounts = dao.getAccountStatement(WomensAccount.class,
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement(BankWomensAccount.class,
						from.getSelectedDate(), to.getSelectedDate()));
				
				Map<String, Float> balances = dao.getOpeningAndClosingBalance(WomensAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				cashOpenbal = balances.get(Constants.OpeningBalance);
				cashclosebal = balances.get(Constants.ClosingBalance);
				balances = dao.getOpeningAndClosingBalance(BankWomensAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				bankOpenbal = balances.get(Constants.OpeningBalance);
				bankClosebal = balances.get(Constants.ClosingBalance);
			} else if (accountNames.getValue().equals(
					Constants.SundaySchoolAccount)) {
				accounts = dao.getAccountStatement(SundaySchoolAccount.class,
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement(BankSundaySchoolAccount.class,
						from.getSelectedDate(), to.getSelectedDate()));
				
				Map<String, Float> balances = dao.getOpeningAndClosingBalance(SundaySchoolAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				cashOpenbal = balances.get(Constants.OpeningBalance);
				cashclosebal = balances.get(Constants.ClosingBalance);
				balances = dao.getOpeningAndClosingBalance(BankSundaySchoolAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				bankOpenbal = balances.get(Constants.OpeningBalance);
				bankClosebal = balances.get(Constants.ClosingBalance);
			} else if (accountNames.getValue()
					.equals(Constants.YouthAccount)) {
				accounts = dao.getAccountStatement(YouthAccount.class,
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement(BankYouthAccount.class,
						from.getSelectedDate(), to.getSelectedDate()));
				
				Map<String, Float> balances = dao.getOpeningAndClosingBalance(YouthAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				cashOpenbal = balances.get(Constants.OpeningBalance);
				cashclosebal = balances.get(Constants.ClosingBalance);
				balances = dao.getOpeningAndClosingBalance(BankYouthAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				bankOpenbal = balances.get(Constants.OpeningBalance);
				bankClosebal = balances.get(Constants.ClosingBalance);
			} else if (accountNames.getValue().equals(
					Constants.BuildingAccount)) {
				accounts = dao.getAccountStatement(BuildingAccount.class,
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao
						.getAccountStatement(BankBuildingAccount.class,
								from.getSelectedDate(), to.getSelectedDate()));
				
				Map<String, Float> balances = dao.getOpeningAndClosingBalance(BuildingAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				cashOpenbal = balances.get(Constants.OpeningBalance);
				cashclosebal = balances.get(Constants.ClosingBalance);
				balances = dao.getOpeningAndClosingBalance(BankBuildingAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				bankOpenbal = balances.get(Constants.OpeningBalance);
				bankClosebal = balances.get(Constants.ClosingBalance);
			} else if (accountNames.getValue().equals(
					Constants.GraveyardAccount)) {
				accounts = dao.getAccountStatement(GraveyardAccount.class,
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement(BankGraveyardAccount.class,
						from.getSelectedDate(), to.getSelectedDate()));
				
				Map<String, Float> balances = dao.getOpeningAndClosingBalance(GraveyardAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				cashOpenbal = balances.get(Constants.OpeningBalance);
				cashclosebal = balances.get(Constants.ClosingBalance);
				balances = dao.getOpeningAndClosingBalance(BankGraveyardAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				bankOpenbal = balances.get(Constants.OpeningBalance);
				bankClosebal = balances.get(Constants.ClosingBalance);
			} else if (accountNames.getValue().equals(
					Constants.EducationalFundAccount)) {
				accounts = dao.getAccountStatement(EducationalFundAccount.class,
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao
						.getAccountStatement(BankEducationalFundAccount.class,
								from.getSelectedDate(), to.getSelectedDate()));
				
				Map<String, Float> balances = dao.getOpeningAndClosingBalance(EducationalFundAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				cashOpenbal = balances.get(Constants.OpeningBalance);
				cashclosebal = balances.get(Constants.ClosingBalance);
				balances = dao.getOpeningAndClosingBalance(BankEducationalFundAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
				bankOpenbal = balances.get(Constants.OpeningBalance);
				bankClosebal = balances.get(Constants.ClosingBalance);
			}

			logger.debug("Record Count : " + accounts.size());
			AccStatement accStmt = null;
			for (Account account : accounts) {
				accStmt = new AccStatement();
				accStmt.setAmount(Float.valueOf(account.getAmount()));
				accStmt.setDescription(account.getDescription());
				accStmt.setDate(ProjectProperties.sdf.format(account.getDate()));
				if (account.getCr_dr().equals("CR")) {
					income.getItems().add(accStmt);
					totalIncome += account.getAmount();
				} else if (account.getCr_dr().equals("DR")) {
					expense.getItems().add(accStmt);
					totalExpense += account.getAmount();
				}
			}

			this.totalIncomeLbl.setText(String.valueOf(totalIncome));
			this.totalExpenseLbl.setText(String.valueOf(totalExpense));


			this.openingBankAmt.setText(String.valueOf(bankOpenbal));
			this.openingCashAmt.setText(String.valueOf(cashOpenbal));
			this.closingBankAmt.setText(String.valueOf(bankClosebal));
			this.closingCashAmt.setText(String.valueOf(cashclosebal));

		} catch (CcfException e) {
			this.message.setText(e.getMessage());
			this.message.setTextFill(Paint.valueOf("Red"));
			e.printStackTrace();
		}

		logger.debug("getDetails method ends...");
	}

	public void exportToExcel() {
		logger.debug("exportToExcel method Starts...");
		this.message.setText(null);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Accounts Statement Report");

		int rownum = 0;
		Row row = sheet.createRow(rownum++);
		sheet.createRow(rownum++);
		row.createCell(2).setCellValue(
				"ACCOUNT STATEMENT - " + accountNames.getValue());
		sheet.createRow(rownum++);
		sheet.createRow(rownum++);

		row.createCell(1).setCellValue("Opening Balance : ");
		row.createCell(2).setCellValue("Cash");
		row.createCell(3).setCellValue("Bank");

		row = sheet.createRow(rownum++);
		row.createCell(2).setCellValue(openingCashAmt.getText());
		row.createCell(3).setCellValue(openingBankAmt.getText());

		sheet.createRow(rownum++);
		sheet.createRow(rownum++);

		Row title = sheet.createRow(rownum++);
		title.createCell(2).setCellValue("Income");
		sheet.createRow(rownum++);

		Row IncomeHeader = sheet.createRow(rownum++);
		IncomeHeader.createCell(1).setCellValue("Date");
		IncomeHeader.createCell(2).setCellValue("Description");
		IncomeHeader.createCell(3).setCellValue("Amount");

		AccStatement accStmt = null;
		ObservableList<AccStatement> incomeStmt = income.getItems();
		for (int i = 0; i < incomeStmt.size(); i++) {
			accStmt = incomeStmt.get(i);
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue(accStmt.getDate());
			row.createCell(2).setCellValue(accStmt.getDescription());
			row.createCell(3).setCellValue(accStmt.getAmount());

		}
		row = sheet.createRow(rownum++);
		row.createCell(2).setCellValue("Total : ");
		row.createCell(3).setCellValue(totalIncomeLbl.getText());

		sheet.createRow(rownum++);
		sheet.createRow(rownum++);
		sheet.createRow(rownum++);
		title = sheet.createRow(rownum++);
		title.createCell(2).setCellValue("expense");
		sheet.createRow(rownum++);

		Row expenseHeader = sheet.createRow(rownum++);
		expenseHeader.createCell(1).setCellValue("Date");
		expenseHeader.createCell(2).setCellValue("Description");
		expenseHeader.createCell(3).setCellValue("Amount");

		ObservableList<AccStatement> expenseStmt = expense.getItems();
		for (int i = 0; i < expenseStmt.size(); i++) {
			accStmt = expenseStmt.get(i);
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue(accStmt.getDate());
			row.createCell(2).setCellValue(accStmt.getDescription());
			row.createCell(3).setCellValue(accStmt.getAmount());

		}
		row = sheet.createRow(rownum++);
		row.createCell(2).setCellValue("Total : ");
		row.createCell(3).setCellValue(totalExpenseLbl.getText());

		sheet.createRow(rownum++);
		sheet.createRow(rownum++);

		row = sheet.createRow(rownum++);
		row.createCell(1).setCellValue("Closing Balance : ");
		row.createCell(2).setCellValue("Cash");
		row.createCell(3).setCellValue("Bank");

		row = sheet.createRow(rownum++);
		row.createCell(2).setCellValue(closingCashAmt.getText());
		row.createCell(3).setCellValue(closingBankAmt.getText());

		try {
			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream(ProjectProperties.propertyFileLocation);
			// load a properties file
			prop.load(input);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			File file = new File(prop.getProperty("export_path")
					+ "Christ Church Fort - Account Statement Details - "
					+ sdf.format(new Date()) + ".xls");

			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();

			message.setTextFill(Paint.valueOf("Green"));
			message.setText("Saved at " + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			message.setTextFill(Paint.valueOf("Red"));
			message.setText(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			message.setTextFill(Paint.valueOf("Red"));
			message.setText(e.getMessage());
		}
		logger.debug("exportToExcel method Ends...");
	}

	public void print() {
		logger.info("print method Starts...");
		message.setText("Functionality under construction.");
		message.setTextFill(Paint.valueOf("Red"));
		logger.info("print method Ends...");
	}
}
