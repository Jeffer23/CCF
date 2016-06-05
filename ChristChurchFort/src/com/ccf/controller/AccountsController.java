package com.ccf.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

import application.Main;

import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.doa.AccountsDao;
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
}
