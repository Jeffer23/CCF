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
	private Label primarySchoolAccountBalance;

	@FXML
	private Label overAllBalance;
	
	@FXML
	private Label message;

	@FXML
	void initialize() {
		logger.debug("init method Starts...");
		AccountsDao impl = new AccountsDaoImpl();
		try {
			float pcAccBalance = impl.getPCAccountBalance();
			pcAccountBalance.setText(String.valueOf(pcAccBalance));
			float missionaryAccBalance = impl.getMissionaryAccountBalance();
			missionaryAccountBalance.setText(String
					.valueOf(missionaryAccBalance));
			float mensAccBalance = impl.getMensAccountBalance();
			mensAccountBalance.setText(String.valueOf(mensAccBalance));
			float womensAccBalance = impl.getWomensAccountBalance();
			womensAccountBalance.setText(String.valueOf(womensAccBalance));
			float sundaySchoolAccbalance = impl.getSundaySchoolAccountBalance();
			sundaySchoolAccountBalance.setText(String
					.valueOf(sundaySchoolAccbalance));
			float youthAccBalance = impl.getYouthAccountBalance();
			youthAccountBalance.setText(String.valueOf(youthAccBalance));
			float stoAccBalance = impl.getSpecialThanksOfferingAccountBalance();
			stoAccountBalance.setText(String.valueOf(stoAccBalance));
			float graveyardAccBalance = impl.getGraveyardAccountBalance();
			graveyardAccountBalance
					.setText(String.valueOf(graveyardAccBalance));
			float primarySchoolAccBalance = impl
					.getPrimarySchoolAccountBalance();
			primarySchoolAccountBalance.setText(String
					.valueOf(primarySchoolAccBalance));
			float overAllBalance = pcAccBalance + missionaryAccBalance
					+ mensAccBalance + womensAccBalance
					+ sundaySchoolAccbalance + youthAccBalance + stoAccBalance
					+ graveyardAccBalance + primarySchoolAccBalance;
			this.overAllBalance.setText(String.valueOf(overAllBalance));
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
	
	public void exportToExcel(){
		logger.debug("exportToExcel method Starts...");
		try{
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
		header.createCell(4).setCellValue("Account Balance");
		
		
		AccountsDao impl = new AccountsDaoImpl();
		Map<String, Float> accountBalance = new HashMap<>();
		accountBalance.put("PC Account", impl.getPCAccountBalance());
		accountBalance.put("Missionary Account", impl.getMissionaryAccountBalance());
		accountBalance.put("Men's Account", impl.getMensAccountBalance());
		accountBalance.put("Women's Account", impl.getWomensAccountBalance());
		accountBalance.put("Sunday School Account", impl.getSundaySchoolAccountBalance());
		accountBalance.put("Youth Account", impl.getYouthAccountBalance());
		accountBalance.put("Special Thanks Offering Account", impl.getSpecialThanksOfferingAccountBalance());
		accountBalance.put("Graveyard Account", impl.getGraveyardAccountBalance());
		accountBalance.put("primary School Account", impl.getPrimarySchoolAccountBalance());
		
		Row row = null;
		int rowCount = 1;
		float totalBalance = 0;
		Set<Entry<String, Float>> entrySet = accountBalance.entrySet();
		Iterator<Entry<String, Float>> iterator = entrySet.iterator();
		while(iterator.hasNext()){
			Entry<String, Float> entry = iterator.next();
			row = sheet.createRow(rownum++);
			row.createCell(1).setCellValue(rowCount++);
			row.createCell(2).setCellValue(entry.getKey());
			row.createCell(3).setCellValue("INR");
			row.createCell(4).setCellValue(entry.getValue());
			
			totalBalance = totalBalance + entry.getValue();
		}
		
		sheet.createRow(rownum++);
		Row allAccount = sheet.createRow(rownum++);
		allAccount.createCell(1).setCellValue(rowCount);
		allAccount.createCell(2).setCellValue("All Account");
		allAccount.createCell(3).setCellValue("INR");
		allAccount.createCell(4).setCellValue(totalBalance);
		
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("c://CCF//ccf.properties");
		// load a properties file
		prop.load(input);
		File file = new File(prop.getProperty("export_path")
				+ "Christ Church Fort - Account Details - " + sdf.format(new Date()) + ".xls");

		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		out.close();

		message.setTextFill(Paint.valueOf("Green"));
		message.setText("Saved at " + prop.getProperty("export_path"));
		
		} catch(CcfException e){
			message.setTextFill(Paint.valueOf("RED"));
			message.setText("Error in DB execution");
		} catch (FileNotFoundException e) {
			message.setTextFill(Paint.valueOf("RED"));
			message.setText("Looking for File at \"c://CCF//ccf.properties\" not found" );
			e.printStackTrace();
		} catch (IOException e) {
			message.setTextFill(Paint.valueOf("RED"));
			message.setText("Error loading property File");
			e.printStackTrace();
		}
		
		logger.debug("exportToExcel method Ends...");
	}
}
