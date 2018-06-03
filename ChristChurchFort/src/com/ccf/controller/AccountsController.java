package com.ccf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
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

public class AccountsController {

	final static Logger logger = Logger.getLogger(AccountsController.class);

	@FXML
	private Label pcAccountBalance;

	@FXML
	private Label missionaryAccountBalance;

	@FXML
	private Label mensAccountBalance;

	@FXML
	private Label womensAccountBalance;

	@FXML
	private Label sundaySchoolAccountBalance;

	@FXML
	private Label youthAccountBalance;

	@FXML
	private Label stoAccountBalance;

	@FXML
	private Label graveyardAccountBalance;

	@FXML
	private Label educationalFundAccountBalance;

	@FXML
	private Label bankPCAccountBalance;

	@FXML
	private Label bankMissionaryAccountBalance;

	@FXML
	private Label bankMensAccountBalance;

	@FXML
	private Label bankWomensAccountBalance;

	@FXML
	private Label bankSundaySchoolAccountBalance;

	@FXML
	private Label bankYouthAccountBalance;

	@FXML
	private Label bankStoAccountBalance;

	@FXML
	private Label bankGraveyardAccountBalance;

	@FXML
	private Label bankEducationalFundAccountBalance;

	@FXML
	private Label totalPCAccountBalance;

	@FXML
	private Label totalMissionaryAccountBalance;

	@FXML
	private Label totalMensAccountBalance;

	@FXML
	private Label totalWomenAccountBalance;

	@FXML
	private Label totalSundaySchoolAccountBalance;

	@FXML
	private Label totalYouthAccountBalance;

	@FXML
	private Label totalStoAccountBalance;

	@FXML
	private Label totalGraveyardAccountBalance;

	@FXML
	private Label totalEducationalFundAccountBalance;

	@FXML
	private Label overAllCashBalance;

	@FXML
	private Label overAllBankBalance;

	@FXML
	private Label overAllTotalBalance;

	@FXML
	private Label message;

	@FXML
	void initialize() {
		logger.debug("init method Starts...");
		AccountsDao impl = new AccountsDaoImpl();
		try {
			// Cash Balance
			float pcAccBalance = impl.getCurrentAccountBalance(PCAccount.class);
			this.pcAccountBalance.setText(String.valueOf(pcAccBalance));
			float missionaryAccBalance = impl
					.getCurrentAccountBalance(MissionaryAccount.class);
			this.missionaryAccountBalance.setText(String
					.valueOf(missionaryAccBalance));
			float mensAccBalance = impl
					.getCurrentAccountBalance(MensAccount.class);
			this.mensAccountBalance.setText(String.valueOf(mensAccBalance));
			float womensAccBalance = impl
					.getCurrentAccountBalance(WomensAccount.class);
			this.womensAccountBalance.setText(String.valueOf(womensAccBalance));
			float sundaySchoolAccbalance = impl
					.getCurrentAccountBalance(SundaySchoolAccount.class);
			this.sundaySchoolAccountBalance.setText(String
					.valueOf(sundaySchoolAccbalance));
			float youthAccBalance = impl
					.getCurrentAccountBalance(YouthAccount.class);
			this.youthAccountBalance.setText(String.valueOf(youthAccBalance));
			float stoAccBalance = impl
					.getCurrentAccountBalance(BuildingAccount.class);
			this.stoAccountBalance.setText(String.valueOf(stoAccBalance));
			float graveyardAccBalance = impl
					.getCurrentAccountBalance(GraveyardAccount.class);
			this.graveyardAccountBalance
					.setText(String.valueOf(graveyardAccBalance));
			float educationalFundAccBalance = impl
					.getCurrentAccountBalance(EducationalFundAccount.class);
			this.educationalFundAccountBalance.setText(String
					.valueOf(educationalFundAccBalance));
			float overAllBalance = pcAccBalance + missionaryAccBalance
					+ mensAccBalance + womensAccBalance
					+ sundaySchoolAccbalance + youthAccBalance + stoAccBalance
					+ graveyardAccBalance + educationalFundAccBalance;
			this.overAllCashBalance.setText(String.valueOf(overAllBalance));

			//Bank Balance
			float bankPcAccBalance = impl
					.getCurrentAccountBalance(BankPCAccount.class);
			this.bankPCAccountBalance.setText(String.valueOf(bankPcAccBalance));
			float bankMissionaryAccBalance = impl
					.getCurrentAccountBalance(BankMissionaryAccount.class);
			this.bankMissionaryAccountBalance.setText(String
					.valueOf(bankMissionaryAccBalance));
			float bankMensAccBalance = impl
					.getCurrentAccountBalance(BankMensAccount.class);
			this.bankMensAccountBalance.setText(String.valueOf(bankMensAccBalance));
			float bankWomensAccBalance = impl
					.getCurrentAccountBalance(BankWomensAccount.class);
			this.bankWomensAccountBalance.setText(String.valueOf(bankWomensAccBalance));
			float bankSundaySchoolAccbalance = impl
					.getCurrentAccountBalance(BankSundaySchoolAccount.class);
			this.bankSundaySchoolAccountBalance.setText(String
					.valueOf(bankSundaySchoolAccbalance));
			float bankYouthAccBalance = impl
					.getCurrentAccountBalance(BankYouthAccount.class);
			this.bankYouthAccountBalance.setText(String.valueOf(bankYouthAccBalance));
			float bankStoAccBalance = impl
					.getCurrentAccountBalance(BankBuildingAccount.class);
			this.bankStoAccountBalance.setText(String.valueOf(bankStoAccBalance));
			float bankGraveyardAccBalance = impl
					.getCurrentAccountBalance(BankGraveyardAccount.class);
			this.bankGraveyardAccountBalance.setText(String
					.valueOf(bankGraveyardAccBalance));
			float bankEducationalFundAccBalance = impl
					.getCurrentAccountBalance(BankEducationalFundAccount.class);
			this.bankEducationalFundAccountBalance.setText(String
					.valueOf(bankEducationalFundAccBalance));
			float overAllBankBalance = bankPcAccBalance
					+ bankMissionaryAccBalance + bankMensAccBalance
					+ bankWomensAccBalance + bankSundaySchoolAccbalance
					+ bankYouthAccBalance + bankStoAccBalance
					+ bankGraveyardAccBalance + bankEducationalFundAccBalance;
			this.overAllBankBalance.setText(String.valueOf(overAllBankBalance));
			
			this.totalGraveyardAccountBalance.setText(String.valueOf(graveyardAccBalance + bankGraveyardAccBalance));
			this.totalMensAccountBalance.setText(String.valueOf(mensAccBalance + bankMensAccBalance));
			this.totalMissionaryAccountBalance.setText(String.valueOf(missionaryAccBalance + bankMissionaryAccBalance));
			this.totalPCAccountBalance.setText(String.valueOf(pcAccBalance + bankPcAccBalance));
			this.totalEducationalFundAccountBalance.setText(String.valueOf(educationalFundAccBalance + bankEducationalFundAccBalance));
			this.totalStoAccountBalance.setText(String.valueOf(stoAccBalance + bankStoAccBalance));
			this.totalSundaySchoolAccountBalance.setText(String.valueOf(sundaySchoolAccbalance + bankSundaySchoolAccbalance));
			this.totalWomenAccountBalance.setText(String.valueOf(womensAccBalance + bankWomensAccBalance));
			this.totalYouthAccountBalance.setText(String.valueOf(youthAccBalance + bankYouthAccBalance));
			this.overAllTotalBalance.setText(String.valueOf(overAllBalance + overAllBankBalance));

		} catch (CcfException e) {
			e.printStackTrace();
		}
		logger.debug("initialize method Ends...");
	}

	public void showAccountStatements() {
		logger.debug("showAccountStatements method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/Account_Statement.fxml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("showAccountStatements method Ends...");
	}

	/**
	 * This method is used to replace the content on the UI. i.e., for
	 * Templating.
	 * 
	 * @param template
	 *            Represents the template page.
	 * @param displayPage
	 *            Represents the page to be displayed.
	 * @throws Exception
	 */
	private void replaceSceneContent(String template, String displayPage)
			throws Exception {
		logger.debug("replaceSceneContent starts...");
		// AnchorPane main;
		VBox templatePage;

		/*
		 * // get main page System.out.println("Display Page : " +
		 * _displayPage); Page mainPage = Page.getPage(_displayPage);
		 * if(mainPage == null){ System.out.println("Page is null"); }
		 */
		VBox vBox = FXMLLoader.load(getClass().getResource(displayPage));
		// main = (AnchorPane) vBox.lookup("#addFamilyAnchorPane");
		// get reference of Pane to be added to the template.
		// main = (AnchorPane) main.lookup("#addStock");

		// get template
		templatePage = FXMLLoader.load(getClass().getResource(template));

		// get reference of the "main" section in template i.e. where the new
		// contents will be added
		AnchorPane origMain = (AnchorPane) templatePage.lookup("#body");

		// set contents of "mainPage" into the template
		origMain.getChildren().setAll(vBox.getChildren());

		// create scene
		Scene scene = new Scene(templatePage);

		// set stage
		Main mainClazz = new Main();
		Stage globalStage = mainClazz.getPrimaryStage();
		if (globalStage == null) {
			System.out.println("Yes its null");
		}
		globalStage.setScene(scene);
		globalStage.sizeToScene();
		/*
		 * System.out.println("is loader.getController() == null ?" +
		 * (mainController == null ? true : false)); return mainController;
		 */
		logger.debug("replaceSceneContent ends...");
	}

	public void exportToExcel() {
		logger.debug("exportToExcel method Starts...");
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Accounts Report");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int rownum = 0;
			sheet.createRow(rownum++);
			sheet.createRow(rownum++);

			Row title = sheet.createRow(rownum++);
			title.createCell(2).setCellValue("Accounts Report");
			sheet.createRow(rownum++);
			Row header = sheet.createRow(rownum++);
			header.createCell(1).setCellValue("NO");
			header.createCell(2).setCellValue("Account Name");
			header.createCell(3).setCellValue("CCY");
			header.createCell(4).setCellValue("Cach Balance");
			header.createCell(5).setCellValue("Bank Balance");
			header.createCell(6).setCellValue("Total Balance");

			AccountsDao impl = new AccountsDaoImpl();
			Map<String, Float> accountBalance = new HashMap<>();
			accountBalance.put("PC Account",
					impl.getCurrentAccountBalance(PCAccount.class));
			accountBalance.put("Missionary Account",
					impl.getCurrentAccountBalance(MissionaryAccount.class));
			accountBalance.put("Men's Account",
					impl.getCurrentAccountBalance(MensAccount.class));
			accountBalance.put("Women's Account",
					impl.getCurrentAccountBalance(WomensAccount.class));
			accountBalance.put("Sunday School Account",
					impl.getCurrentAccountBalance(SundaySchoolAccount.class));
			accountBalance.put("Youth Account",
					impl.getCurrentAccountBalance(YouthAccount.class));
			accountBalance.put("Special Thanks Offering Account",
					impl.getCurrentAccountBalance(BuildingAccount.class));
			accountBalance.put("Graveyard Account",
					impl.getCurrentAccountBalance(GraveyardAccount.class));
			accountBalance.put("Educational Fund Account",
					impl.getCurrentAccountBalance(EducationalFundAccount.class));
			
			Map<String, Float> bankAccountBalance = new HashMap<>();
			bankAccountBalance.put("PC Account",
					impl.getCurrentAccountBalance(BankPCAccount.class));
			bankAccountBalance.put("Missionary Account",
					impl.getCurrentAccountBalance(BankMissionaryAccount.class));
			bankAccountBalance.put("Men's Account",
					impl.getCurrentAccountBalance(BankMensAccount.class));
			bankAccountBalance.put("Women's Account",
					impl.getCurrentAccountBalance(BankWomensAccount.class));
			bankAccountBalance.put("Sunday School Account",
					impl.getCurrentAccountBalance(BankSundaySchoolAccount.class));
			bankAccountBalance.put("Youth Account",
					impl.getCurrentAccountBalance(BankYouthAccount.class));
			bankAccountBalance.put("Special Thanks Offering Account",
					impl.getCurrentAccountBalance(BankBuildingAccount.class));
			bankAccountBalance.put("Graveyard Account",
					impl.getCurrentAccountBalance(BankGraveyardAccount.class));
			bankAccountBalance.put("Educational Fund Account",
					impl.getCurrentAccountBalance(BankEducationalFundAccount.class));

			Row row = null;
			int rowCount = 1;
			float totalCashBalance = 0;
			float totalBankBalance = 0;
			Set<Entry<String, Float>> entrySet = accountBalance.entrySet();
			Iterator<Entry<String, Float>> iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Entry<String, Float> entry = iterator.next();
				float bankBalance = bankAccountBalance.get(entry.getKey());
				row = sheet.createRow(rownum++);
				row.createCell(1).setCellValue(rowCount++);
				row.createCell(2).setCellValue(entry.getKey());
				row.createCell(3).setCellValue("INR");
				row.createCell(4).setCellValue(entry.getValue());
				row.createCell(5).setCellValue(bankBalance);
				row.createCell(6).setCellValue(entry.getValue() + bankBalance);

				totalCashBalance = totalCashBalance + entry.getValue();
				totalBankBalance = totalBankBalance + bankBalance;
			}

			sheet.createRow(rownum++);
			Row allAccount = sheet.createRow(rownum++);
			allAccount.createCell(1).setCellValue(rowCount);
			allAccount.createCell(2).setCellValue("All Account");
			allAccount.createCell(3).setCellValue("INR");
			allAccount.createCell(4).setCellValue(totalCashBalance);
			allAccount.createCell(5).setCellValue(totalBankBalance);
			allAccount.createCell(6).setCellValue(totalCashBalance + totalBankBalance);

			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream("c://CCF//ccf.properties");
			// load a properties file
			prop.load(input);
			File file = new File(prop.getProperty("export_path")
					+ "Christ Church Fort - Account Details - "
					+ sdf.format(new Date()) + ".xls");

			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();

			message.setTextFill(Paint.valueOf("Green"));
			message.setText("Saved at " + prop.getProperty("export_path"));

		} catch (CcfException e) {
			message.setTextFill(Paint.valueOf("RED"));
			message.setText("Error in DB execution");
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
