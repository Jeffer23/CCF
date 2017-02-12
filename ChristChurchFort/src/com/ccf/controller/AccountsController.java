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
import com.ccf.util.AccountNames;

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
			float pcAccBalance = impl.getAccountBalance(AccountNames.PCAccount);
			this.pcAccountBalance.setText(String.valueOf(pcAccBalance));
			float missionaryAccBalance = impl
					.getAccountBalance(AccountNames.MissionaryAccount);
			this.missionaryAccountBalance.setText(String
					.valueOf(missionaryAccBalance));
			float mensAccBalance = impl
					.getAccountBalance(AccountNames.MensAccount);
			this.mensAccountBalance.setText(String.valueOf(mensAccBalance));
			float womensAccBalance = impl
					.getAccountBalance(AccountNames.WomensAccount);
			this.womensAccountBalance.setText(String.valueOf(womensAccBalance));
			float sundaySchoolAccbalance = impl
					.getAccountBalance(AccountNames.SundaySchoolAccount);
			this.sundaySchoolAccountBalance.setText(String
					.valueOf(sundaySchoolAccbalance));
			float youthAccBalance = impl
					.getAccountBalance(AccountNames.YouthAccount);
			this.youthAccountBalance.setText(String.valueOf(youthAccBalance));
			float stoAccBalance = impl
					.getAccountBalance(AccountNames.BuildingAccount);
			this.stoAccountBalance.setText(String.valueOf(stoAccBalance));
			float graveyardAccBalance = impl
					.getAccountBalance(AccountNames.GraveyardAccount);
			this.graveyardAccountBalance
					.setText(String.valueOf(graveyardAccBalance));
			float educationalFundAccBalance = impl
					.getAccountBalance(AccountNames.EducationalFundAccount);
			this.educationalFundAccountBalance.setText(String
					.valueOf(educationalFundAccBalance));
			float overAllBalance = pcAccBalance + missionaryAccBalance
					+ mensAccBalance + womensAccBalance
					+ sundaySchoolAccbalance + youthAccBalance + stoAccBalance
					+ graveyardAccBalance + educationalFundAccBalance;
			this.overAllCashBalance.setText(String.valueOf(overAllBalance));

			//Bank Balance
			float bankPcAccBalance = impl
					.getAccountBalance(AccountNames.BankPCAccount);
			this.bankPCAccountBalance.setText(String.valueOf(bankPcAccBalance));
			float bankMissionaryAccBalance = impl
					.getAccountBalance(AccountNames.BankMissionaryAccount);
			this.bankMissionaryAccountBalance.setText(String
					.valueOf(bankMissionaryAccBalance));
			float bankMensAccBalance = impl
					.getAccountBalance(AccountNames.BankMensAccount);
			this.bankMensAccountBalance.setText(String.valueOf(bankMensAccBalance));
			float bankWomensAccBalance = impl
					.getAccountBalance(AccountNames.BankWomensAccount);
			this.bankWomensAccountBalance.setText(String.valueOf(bankWomensAccBalance));
			float bankSundaySchoolAccbalance = impl
					.getAccountBalance(AccountNames.BankSundaySchoolAccount);
			this.bankSundaySchoolAccountBalance.setText(String
					.valueOf(bankSundaySchoolAccbalance));
			float bankYouthAccBalance = impl
					.getAccountBalance(AccountNames.BankYouthAccount);
			this.bankYouthAccountBalance.setText(String.valueOf(bankYouthAccBalance));
			float bankStoAccBalance = impl
					.getAccountBalance(AccountNames.BankBuildingAccount);
			this.bankStoAccountBalance.setText(String.valueOf(bankStoAccBalance));
			float bankGraveyardAccBalance = impl
					.getAccountBalance(AccountNames.BankGraveyardAccount);
			this.bankGraveyardAccountBalance.setText(String
					.valueOf(bankGraveyardAccBalance));
			float bankEducationalFundAccBalance = impl
					.getAccountBalance(AccountNames.BankEducationalFundAccount);
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
					impl.getAccountBalance(AccountNames.PCAccount));
			accountBalance.put("Missionary Account",
					impl.getAccountBalance(AccountNames.MissionaryAccount));
			accountBalance.put("Men's Account",
					impl.getAccountBalance(AccountNames.MensAccount));
			accountBalance.put("Women's Account",
					impl.getAccountBalance(AccountNames.WomensAccount));
			accountBalance.put("Sunday School Account",
					impl.getAccountBalance(AccountNames.SundaySchoolAccount));
			accountBalance.put("Youth Account",
					impl.getAccountBalance(AccountNames.YouthAccount));
			accountBalance.put("Special Thanks Offering Account",
					impl.getAccountBalance(AccountNames.BuildingAccount));
			accountBalance.put("Graveyard Account",
					impl.getAccountBalance(AccountNames.GraveyardAccount));
			accountBalance.put("Educational Fund Account",
					impl.getAccountBalance(AccountNames.EducationalFundAccount));
			
			Map<String, Float> bankAccountBalance = new HashMap<>();
			bankAccountBalance.put("PC Account",
					impl.getAccountBalance(AccountNames.BankPCAccount));
			bankAccountBalance.put("Missionary Account",
					impl.getAccountBalance(AccountNames.BankMissionaryAccount));
			bankAccountBalance.put("Men's Account",
					impl.getAccountBalance(AccountNames.BankMensAccount));
			bankAccountBalance.put("Women's Account",
					impl.getAccountBalance(AccountNames.BankWomensAccount));
			bankAccountBalance.put("Sunday School Account",
					impl.getAccountBalance(AccountNames.BankSundaySchoolAccount));
			bankAccountBalance.put("Youth Account",
					impl.getAccountBalance(AccountNames.BankYouthAccount));
			bankAccountBalance.put("Special Thanks Offering Account",
					impl.getAccountBalance(AccountNames.BankBuildingAccount));
			bankAccountBalance.put("Graveyard Account",
					impl.getAccountBalance(AccountNames.BankGraveyardAccount));
			bankAccountBalance.put("Educational Fund Account",
					impl.getAccountBalance(AccountNames.BankEducationalFundAccount));

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
}
