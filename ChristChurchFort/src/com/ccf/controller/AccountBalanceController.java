package com.ccf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import application.Main;

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
import com.ccf.util.Constants;
import com.ccf.util.ProjectProperties;

import eu.schudt.javafx.controls.calendar.DatePicker;

public class AccountBalanceController {

	final static Logger logger = Logger.getLogger(AccountBalanceController.class);
	
	@FXML
	private Label pcCashOpenBal;
	
	@FXML
	private Label missionaryCashOpenBal;
	
	@FXML
	private Label mensCashOpenBal;
	
	@FXML
	private Label womensCashOpenBal;
	
	@FXML
	private Label sundaySchoolCashOpenBal;
	
	@FXML
	private Label youthCashOpenBal;
	
	@FXML
	private Label stoCashOpenBal;
	
	@FXML
	private Label graveyardCashOpenBal;
	
	@FXML
	private Label eduFundCashOpenBal;

	@FXML
	private Label pcBankOpenBal;
	
	@FXML
	private Label missionaryBankOpenBal;
	
	@FXML
	private Label mensBankOpenBal;
	
	@FXML
	private Label womensBankOpenBal;
	
	@FXML
	private Label sundaySchoolBankOpenBal;
	
	@FXML
	private Label youthBankOpenBal;
	
	@FXML
	private Label stoBankOpenBal;
	
	@FXML
	private Label graveyardBankOpenBal;
	
	@FXML
	private Label eduFundBankOpenBal;
	
	@FXML
	private Label pcCashCloseBal;
	
	@FXML
	private Label missionaryCashCloseBal;
	
	@FXML
	private Label mensCashCloseBal;
	
	@FXML
	private Label womensCashCloseBal;
	
	@FXML
	private Label sundaySchoolCashCloseBal;
	
	@FXML
	private Label youthCashCloseBal;
	
	@FXML
	private Label stoCashCloseBal;
	
	@FXML
	private Label graveyardCashCloseBal;
	
	@FXML
	private Label eduFundCashCloseBal;
	
	@FXML
	private Label pcBankCloseBal;
	
	@FXML
	private Label missionaryBankCloseBal;
	
	@FXML
	private Label mensBankCloseBal;
	
	@FXML
	private Label womensBankCloseBal;
	
	@FXML
	private Label sundaySchoolBankCloseBal;
	
	@FXML
	private Label youthBankCloseBal;
	
	@FXML
	private Label stoBankCloseBal;
	
	@FXML
	private Label graveyardBankCloseBal;
	
	@FXML
	private Label eduFundBankCloseBal;
	
	@FXML
	private Label overAllCashOpenBal;
	
	@FXML
	private Label overAllBankOpenBal;
	
	@FXML
	private Label overAllCashCloseBal;
	
	@FXML
	private Label overAllBankCloseBal;
	
	@FXML
	private DatePicker from;
	
	@FXML
	private DatePicker to;

	@FXML
	private Label message;

	@FXML
	void initialize() {
		this.from.setDateFormat(ProjectProperties.sdf);
		this.to.setDateFormat(ProjectProperties.sdf);
	}

	public void getDetails(){
		logger.debug("getDetails method Starts...");
		AccountsDao accountDao = new AccountsDaoImpl();
		try {
			//Validation
			if(this.from.getSelectedDate() == null)
				throw new CcfException("Select the from date");
			if(this.to.getSelectedDate() == null)
				throw new CcfException("Select the to date");
			
			float totalCashOpenBal = 0.0f;
			float totalCashCloseBal = 0.0f;
			float totalBankOpenBal = 0.0f;
			float totalBankCloseBal = 0.0f;
			
			Map<String, Float> balances = accountDao.getOpeningAndClosingBalance(PCAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.pcCashOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.pcCashCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalCashOpenBal += balances.get(Constants.OpeningBalance);
			totalCashCloseBal += balances.get(Constants.ClosingBalance);
			balances = accountDao.getOpeningAndClosingBalance(BankPCAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.pcBankOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.pcBankCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalBankOpenBal += balances.get(Constants.OpeningBalance);
			totalBankCloseBal += balances.get(Constants.ClosingBalance);
			
			balances = accountDao.getOpeningAndClosingBalance(MissionaryAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.missionaryCashOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.missionaryCashCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalCashOpenBal += balances.get(Constants.OpeningBalance);
			totalCashCloseBal += balances.get(Constants.ClosingBalance);
			balances = accountDao.getOpeningAndClosingBalance(BankMissionaryAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.missionaryBankOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.missionaryBankCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalBankOpenBal += balances.get(Constants.OpeningBalance);
			totalBankCloseBal += balances.get(Constants.ClosingBalance);
			
			balances = accountDao.getOpeningAndClosingBalance(MensAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.mensCashOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.mensCashCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalCashOpenBal += balances.get(Constants.OpeningBalance);
			totalCashCloseBal += balances.get(Constants.ClosingBalance);
			balances = accountDao.getOpeningAndClosingBalance(BankMensAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.mensBankOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.mensBankCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalBankOpenBal += balances.get(Constants.OpeningBalance);
			totalBankCloseBal += balances.get(Constants.ClosingBalance);
			
			balances = accountDao.getOpeningAndClosingBalance(WomensAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.womensCashOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.womensCashCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalCashOpenBal += balances.get(Constants.OpeningBalance);
			totalCashCloseBal += balances.get(Constants.ClosingBalance);
			balances = accountDao.getOpeningAndClosingBalance(BankWomensAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.womensBankOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.womensBankCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalBankOpenBal += balances.get(Constants.OpeningBalance);
			totalBankCloseBal += balances.get(Constants.ClosingBalance);
			
			balances = accountDao.getOpeningAndClosingBalance(SundaySchoolAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.sundaySchoolCashOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.sundaySchoolCashCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalCashOpenBal += balances.get(Constants.OpeningBalance);
			totalCashCloseBal += balances.get(Constants.ClosingBalance);
			balances = accountDao.getOpeningAndClosingBalance(BankSundaySchoolAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.sundaySchoolBankOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.sundaySchoolBankCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalBankOpenBal += balances.get(Constants.OpeningBalance);
			totalBankCloseBal += balances.get(Constants.ClosingBalance);
			
			balances = accountDao.getOpeningAndClosingBalance(YouthAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.youthCashOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.youthCashCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalCashOpenBal += balances.get(Constants.OpeningBalance);
			totalCashCloseBal += balances.get(Constants.ClosingBalance);
			balances = accountDao.getOpeningAndClosingBalance(BankYouthAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.youthBankOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.youthBankCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalBankOpenBal += balances.get(Constants.OpeningBalance);
			totalBankCloseBal += balances.get(Constants.ClosingBalance);
			
			balances = accountDao.getOpeningAndClosingBalance(BuildingAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.stoCashOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.stoCashCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalCashOpenBal += balances.get(Constants.OpeningBalance);
			totalCashCloseBal += balances.get(Constants.ClosingBalance);
			balances = accountDao.getOpeningAndClosingBalance(BankBuildingAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.stoBankOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.stoBankCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalBankOpenBal += balances.get(Constants.OpeningBalance);
			totalBankCloseBal += balances.get(Constants.ClosingBalance);
			
			balances = accountDao.getOpeningAndClosingBalance(GraveyardAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.graveyardCashOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.graveyardCashCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalCashOpenBal += balances.get(Constants.OpeningBalance);
			totalCashCloseBal += balances.get(Constants.ClosingBalance);
			balances = accountDao.getOpeningAndClosingBalance(BankGraveyardAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.graveyardBankOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.graveyardBankCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalBankOpenBal += balances.get(Constants.OpeningBalance);
			totalBankCloseBal += balances.get(Constants.ClosingBalance);
			
			balances = accountDao.getOpeningAndClosingBalance(EducationalFundAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.eduFundCashOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.eduFundCashCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalCashOpenBal += balances.get(Constants.OpeningBalance);
			totalCashCloseBal += balances.get(Constants.ClosingBalance);
			balances = accountDao.getOpeningAndClosingBalance(BankEducationalFundAccount.class, this.from.getSelectedDate(), this.to.getSelectedDate());
			this.eduFundBankOpenBal.setText(String.valueOf(balances.get(Constants.OpeningBalance)));
			this.eduFundBankCloseBal.setText(String.valueOf(balances.get(Constants.ClosingBalance)));
			totalBankOpenBal += balances.get(Constants.OpeningBalance);
			totalBankCloseBal += balances.get(Constants.ClosingBalance);
			
			this.overAllCashOpenBal.setText(String.valueOf(totalCashOpenBal));
			this.overAllCashCloseBal.setText(String.valueOf(totalCashCloseBal));
			this.overAllBankOpenBal.setText(String.valueOf(totalBankOpenBal));
			this.overAllBankCloseBal.setText(String.valueOf(totalBankCloseBal));
			
			this.message.setText(null);
		} catch (CcfException e) {
			this.message.setText(e.getMessage());
			e.printStackTrace();
		}
		
		logger.debug("getDetails method Ends...");
	}

	public void exportToExcel() {
		logger.debug("exportToExcel method Starts...");
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Accounts Balance");
			int rownum = 0;
			sheet.createRow(rownum++);
			sheet.createRow(rownum++);

			Row title = sheet.createRow(rownum++);
			title.createCell(4).setCellValue("Accounts Balance");
			sheet.createRow(rownum++);
			Row header = sheet.createRow(rownum++);
			header.createCell(1).setCellValue("NO");
			header.createCell(2).setCellValue("Account Name");
			header.createCell(3).setCellValue("CCY");
			header.createCell(4).setCellValue("Cach");
			header.createCell(5).setCellValue("Balance");
			header.createCell(6).setCellValue("Bank");
			header.createCell(7).setCellValue("Balance");
			
			header = sheet.createRow(rownum++);
			header.createCell(4).setCellValue("Opening Balance");
			header.createCell(5).setCellValue("Closing Balance");
			header.createCell(6).setCellValue("Opening Balance");
			header.createCell(7).setCellValue("Closing Balance");
			
			Row row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue("1");
			row.createCell(2).setCellValue(Constants.PCAccount);
			row.createCell(3).setCellValue("INR");
			row.createCell(4).setCellValue(this.pcCashOpenBal.getText());
			row.createCell(5).setCellValue(this.pcCashCloseBal.getText());
			row.createCell(6).setCellValue(this.pcBankOpenBal.getText());
			row.createCell(7).setCellValue(this.pcBankCloseBal.getText());
			
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue("2");
			row.createCell(2).setCellValue(Constants.MissionaryAccount);
			row.createCell(3).setCellValue("INR");
			row.createCell(4).setCellValue(this.missionaryCashOpenBal.getText());
			row.createCell(5).setCellValue(this.missionaryCashCloseBal.getText());
			row.createCell(6).setCellValue(this.missionaryBankOpenBal.getText());
			row.createCell(7).setCellValue(this.missionaryBankCloseBal.getText());
			
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue("3");
			row.createCell(2).setCellValue(Constants.MensAccount);
			row.createCell(3).setCellValue("INR");
			row.createCell(4).setCellValue(this.mensCashOpenBal.getText());
			row.createCell(5).setCellValue(this.mensCashCloseBal.getText());
			row.createCell(6).setCellValue(this.mensBankOpenBal.getText());
			row.createCell(7).setCellValue(this.mensBankCloseBal.getText());
			
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue("4");
			row.createCell(2).setCellValue(Constants.WomensAccount);
			row.createCell(3).setCellValue("INR");
			row.createCell(4).setCellValue(this.womensCashOpenBal.getText());
			row.createCell(5).setCellValue(this.womensCashCloseBal.getText());
			row.createCell(6).setCellValue(this.womensBankOpenBal.getText());
			row.createCell(7).setCellValue(this.womensBankCloseBal.getText());
			
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue("5");
			row.createCell(2).setCellValue(Constants.SundaySchoolAccount);
			row.createCell(3).setCellValue("INR");
			row.createCell(4).setCellValue(this.sundaySchoolCashOpenBal.getText());
			row.createCell(5).setCellValue(this.sundaySchoolCashCloseBal.getText());
			row.createCell(6).setCellValue(this.sundaySchoolBankOpenBal.getText());
			row.createCell(7).setCellValue(this.sundaySchoolBankCloseBal.getText());
			
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue("6");
			row.createCell(2).setCellValue(Constants.YouthAccount);
			row.createCell(3).setCellValue("INR");
			row.createCell(4).setCellValue(this.youthCashOpenBal.getText());
			row.createCell(5).setCellValue(this.youthCashCloseBal.getText());
			row.createCell(6).setCellValue(this.youthBankOpenBal.getText());
			row.createCell(7).setCellValue(this.youthBankCloseBal.getText());
			
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue("7");
			row.createCell(2).setCellValue(Constants.BuildingAccount);
			row.createCell(3).setCellValue("INR");
			row.createCell(4).setCellValue(this.stoCashOpenBal.getText());
			row.createCell(5).setCellValue(this.stoCashCloseBal.getText());
			row.createCell(6).setCellValue(this.stoBankOpenBal.getText());
			row.createCell(7).setCellValue(this.stoBankCloseBal.getText());
			
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue("8");
			row.createCell(2).setCellValue(Constants.GraveyardAccount);
			row.createCell(3).setCellValue("INR");
			row.createCell(4).setCellValue(this.graveyardCashOpenBal.getText());
			row.createCell(5).setCellValue(this.graveyardCashCloseBal.getText());
			row.createCell(6).setCellValue(this.graveyardBankOpenBal.getText());
			row.createCell(7).setCellValue(this.graveyardBankCloseBal.getText());
			
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue("9");
			row.createCell(2).setCellValue(Constants.EducationalFundAccount);
			row.createCell(3).setCellValue("INR");
			row.createCell(4).setCellValue(this.eduFundCashOpenBal.getText());
			row.createCell(5).setCellValue(this.eduFundCashCloseBal.getText());
			row.createCell(6).setCellValue(this.eduFundBankOpenBal.getText());
			row.createCell(7).setCellValue(this.eduFundBankCloseBal.getText());
			
			sheet.createRow(rownum++);
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue("10");
			row.createCell(2).setCellValue("All Account");
			row.createCell(3).setCellValue("INR");
			row.createCell(4).setCellValue(this.overAllCashOpenBal.getText());
			row.createCell(5).setCellValue(this.overAllCashCloseBal.getText());
			row.createCell(6).setCellValue(this.overAllBankOpenBal.getText());
			row.createCell(7).setCellValue(this.overAllBankCloseBal.getText());
			
			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream("c://CCF//ccf.properties");
			// load a properties file
			prop.load(input);
			File file = new File(prop.getProperty("export_path")
					+ "Christ Church Fort - Account Balance - "
					+ ProjectProperties.sdf.format(new Date()) + ".xls");

			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();

			message.setTextFill(Paint.valueOf("Green"));
			message.setText("Saved at " + file.getAbsolutePath());

		} catch (FileNotFoundException e) {
			message.setTextFill(Paint.valueOf("RED"));
			message.setText("Looking for File at \"c://CCF//ccf.properties\" not found");
			e.printStackTrace();
		} catch (IOException e) {
			message.setTextFill(Paint.valueOf("RED"));
			message.setText("Error loading property File");
			e.printStackTrace();
		}

		logger.debug("exportToExcel method Ends...");
	}
	
	public void print(){
		logger.info("print method Starts...");
		message.setText("Functionality under construction.");
		message.setTextFill(Paint.valueOf("Red"));
		logger.info("print method Ends...");
	}
}
