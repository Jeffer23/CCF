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
import com.ccf.vo.AccStatement;
import com.ccf.vo.Account;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.collections.ObservableList;
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
	
	public void exportToExcel(){
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
		IncomeHeader.createCell(1).setCellValue("Date");
		IncomeHeader.createCell(2).setCellValue("Description");
		IncomeHeader.createCell(3).setCellValue("Amount");
		
		Row row = null;
		AccStatement accStmt = null;
		ObservableList<AccStatement> incomeStmt = income.getItems();
		for(int i = 0; i<incomeStmt.size(); i++){
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
		for(int i = 0; i<expenseStmt.size(); i++){
			accStmt = expenseStmt.get(i);
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue(accStmt.getDate());
			row.createCell(2).setCellValue(accStmt.getDescription());
			row.createCell(3).setCellValue(accStmt.getAmount());
			
		}
		row = sheet.createRow(rownum++);
		row.createCell(2).setCellValue("Total : ");
		row.createCell(3).setCellValue(totalExpenseLbl.getText());
		
		try{
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("c://CCF//ccf.properties");
		// load a properties file
		prop.load(input);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		File file = new File(prop.getProperty("export_path")
				+ "Christ Church Fort - Account Statement Details - " + sdf.format(new Date()) + ".xls");

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("exportToExcel method Ends...");
	}
}
