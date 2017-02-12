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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.ccf.dao.AccountsDao;
import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.util.AccountNames;
import com.ccf.util.ProjectProperties;
import com.ccf.vo.AccStatement;
import com.ccf.vo.Account;
import com.ccf.vo.AccumulatedAccStmt;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.paint.Paint;

public class AccumulatedAccountStatement {

	final static Logger logger = Logger.getLogger(AccumulatedAccountStatement.class);

	@FXML
	private Label message;

	@FXML
	private ChoiceBox<String> accountNames;

	@FXML
	private DatePicker from;

	@FXML
	private DatePicker to;

	@FXML
	private TableView<AccumulatedAccStmt> income;

	@FXML
	private TableView<AccumulatedAccStmt> expense;

	@FXML
	private Label totalIncomeLbl;

	@FXML
	private Label totalExpenseLbl;

	@FXML
	void initialize() {
		List<String> accountNamesList = new ArrayList<>();
		accountNamesList.add(AccountNames.PCAccount);
		accountNamesList.add(AccountNames.MissionaryAccount);
		accountNamesList.add(AccountNames.MensAccount);
		accountNamesList.add(AccountNames.WomensAccount);
		accountNamesList.add(AccountNames.SundaySchoolAccount);
		accountNamesList.add(AccountNames.YouthAccount);
		accountNamesList.add(AccountNames.BuildingAccount);
		accountNamesList.add(AccountNames.GraveyardAccount);
		accountNamesList.add(AccountNames.EducationalFundAccount);
		accountNames.setValue(AccountNames.PCAccount);
		accountNames.getItems().addAll(accountNamesList);
	}

	public void getDetails() {
		logger.debug("getDetails method starts...");
		income.getItems().clear();
		expense.getItems().clear();
		AccountsDao dao = new AccountsDaoImpl();
		float totalIncome = 0.0f;
		float totalExpense = 0.0f;
		try {
			List<Account> accounts = null;
			if (accountNames.getValue().equals(AccountNames.PCAccount)) {
				accounts = dao.getAccountStatement("com.ccf.persistence.classes.PCAccount",
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement("com.ccf.persistence.classes.BankPCAccount",
						from.getSelectedDate(), to.getSelectedDate()));
			} else if (accountNames.getValue().equals(
					AccountNames.MissionaryAccount)) {
				accounts = dao.getAccountStatement("com.ccf.persistence.classes.MissionaryAccount",
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement(
						"com.ccf.persistence.classes.BankMissionaryAccount", from.getSelectedDate(),
						to.getSelectedDate()));
			} else if (accountNames.getValue().equals(AccountNames.MensAccount)) {
				accounts = dao.getAccountStatement("com.ccf.persistence.classes.MensAccount",
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement("com.ccf.persistence.classes.BankMensAccount",
						from.getSelectedDate(), to.getSelectedDate()));
			} else if (accountNames.getValue().equals(
					AccountNames.WomensAccount)) {
				accounts = dao.getAccountStatement("com.ccf.persistence.classes.WomensAccount",
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement("com.ccf.persistence.classes.BankWomensAccount",
						from.getSelectedDate(), to.getSelectedDate()));
			} else if (accountNames.getValue().equals(
					AccountNames.SundaySchoolAccount)) {
				accounts = dao.getAccountStatement("com.ccf.persistence.classes.SundaySchoolAccount",
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement(
						"com.ccf.persistence.classes.BankSundaySchoolAccount", from.getSelectedDate(),
						to.getSelectedDate()));
			} else if (accountNames.getValue()
					.equals(AccountNames.YouthAccount)) {
				accounts = dao.getAccountStatement("com.ccf.persistence.classes.YouthAccount",
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement("com.ccf.persistence.classes.BankYouthAccount",
						from.getSelectedDate(), to.getSelectedDate()));
			} else if (accountNames.getValue().equals(AccountNames.BuildingAccount)) {
				accounts = dao.getAccountStatement(
						"com.ccf.persistence.classes.BuildingAccount", from.getSelectedDate(),
						to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement(
						"com.ccf.persistence.classes.BankEducationalFundAccount",
						from.getSelectedDate(), to.getSelectedDate()));
			} else if (accountNames.getValue().equals(
					AccountNames.GraveyardAccount)) {
				accounts = dao.getAccountStatement("com.ccf.persistence.classes.GraveyardAccount",
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement("com.ccf.persistence.classes.BankGraveyardAccount",
						from.getSelectedDate(), to.getSelectedDate()));
			} else if (accountNames.getValue().equals(
					AccountNames.EducationalFundAccount)) {
				accounts = dao.getAccountStatement("com.ccf.persistence.classes.EducationalFundAccount",
						from.getSelectedDate(), to.getSelectedDate());
				accounts.addAll(dao.getAccountStatement(
						"com.ccf.persistence.classes.BankEducationalFundAccount", from.getSelectedDate(),
						to.getSelectedDate()));
			}

			logger.debug("Record Count : " + accounts.size());
			AccStatement accStmt = null;
			Map<String, Float> incomeAccountsMap = new HashMap<>();
			Map<String, Float> expenseAccountsMap = new HashMap<>();
			for (Account account : accounts) {
				String ledgerName = account.getLedger().getLedgerName();
				if(account.getCr_dr().equals("CR")){
					totalIncome += account.getAmount();
					if (incomeAccountsMap.keySet().contains(ledgerName)) {
						incomeAccountsMap.put(ledgerName, incomeAccountsMap.get(ledgerName)
								+ account.getAmount());
					} else {
						incomeAccountsMap.put(ledgerName,
								account.getAmount());
					}
				} else if(account.getCr_dr().equals("DR")){
					totalExpense += account.getAmount();
					if (expenseAccountsMap.keySet().contains(ledgerName)) {
						expenseAccountsMap.put(ledgerName, expenseAccountsMap.get(ledgerName)
								+ account.getAmount());
					} else {
						expenseAccountsMap.put(ledgerName,
								account.getAmount());
					}
				}
				
			}
			
			income.getItems().clear();
			AccumulatedAccStmt stmt = null;
			for(Entry<String, Float> entry : incomeAccountsMap.entrySet()){
				stmt = new AccumulatedAccStmt();
				stmt.setLedgerName(entry.getKey());
				stmt.setAmount(entry.getValue());
				income.getItems().add(stmt);
			}
			
			expense.getItems().clear();
			for(Entry<String, Float> entry : expenseAccountsMap.entrySet()){
				stmt = new AccumulatedAccStmt();
				stmt.setLedgerName(entry.getKey());
				stmt.setAmount(entry.getValue());
				expense.getItems().add(stmt);
			}
			

			this.totalIncomeLbl.setText(String.valueOf(totalIncome));
			this.totalExpenseLbl.setText(String.valueOf(totalExpense));

		} catch (CcfException e) {
			e.printStackTrace();
		}

		logger.debug("getDetails method ends...");
	}

	public void exportToExcel() {
		logger.debug("exportToExcel method Starts...");
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Accounts Statement Report");
		int rownum = 0;
		sheet.createRow(rownum++);
		sheet.createRow(rownum++);

		Row title = sheet.createRow(rownum++);
		title.createCell(2).setCellValue("Income");
		sheet.createRow(rownum++);

		Row IncomeHeader = sheet.createRow(rownum++);
		IncomeHeader.createCell(1).setCellValue("Ledger Name");
		IncomeHeader.createCell(2).setCellValue("Amount");

		Row row = null;
		AccumulatedAccStmt accStmt = null;
		ObservableList<AccumulatedAccStmt> incomeStmt = income.getItems();
		for (int i = 0; i < incomeStmt.size(); i++) {
			accStmt = incomeStmt.get(i);
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue(accStmt.getLedgerName());
			row.createCell(2).setCellValue(accStmt.getAmount());

		}
		sheet.createRow(rownum++);
		row = sheet.createRow(rownum++);
		row.createCell(1).setCellValue("Total : ");
		row.createCell(2).setCellValue(totalIncomeLbl.getText());

		sheet.createRow(rownum++);
		sheet.createRow(rownum++);
		sheet.createRow(rownum++);
		title = sheet.createRow(rownum++);
		title.createCell(2).setCellValue("expense");
		sheet.createRow(rownum++);

		Row expenseHeader = sheet.createRow(rownum++);
		expenseHeader.createCell(1).setCellValue("Ledger Name");
		expenseHeader.createCell(2).setCellValue("Amount");

		ObservableList<AccumulatedAccStmt> expenseStmt = expense.getItems();
		for (int i = 0; i < expenseStmt.size(); i++) {
			accStmt = expenseStmt.get(i);
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue(accStmt.getLedgerName());
			row.createCell(2).setCellValue(accStmt.getAmount());

		}
		sheet.createRow(rownum++);
		row = sheet.createRow(rownum++);
		row.createCell(1).setCellValue("Total : ");
		row.createCell(2).setCellValue(totalExpenseLbl.getText());

		try {
			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream(ProjectProperties.propertyFileLocation);
			// load a properties file
			prop.load(input);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			File file = new File(prop.getProperty("export_path")
					+ "Christ Church Fort - Accumulated Account Statement Details - "
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
}
