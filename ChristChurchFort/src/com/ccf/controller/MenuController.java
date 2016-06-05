package com.ccf.controller;

import org.apache.log4j.Logger;

import com.ccf.util.HibernateSessionFactory;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuController {

	final static Logger logger = Logger.getLogger(MenuController.class);
	/**
	 * This is the action method. This method gets triggered when
	 * "Add New Family" menu is selected.
	 */
	public void addNewFamily() {
		logger.debug("Add New Family Method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/Add_Family.fxml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("Add New Family Method Ends...");
	}


	/**
	 * This is the action method. This method is triggered when "Exit" menu is
	 * selected.
	 */
	public void exit() {
		logger.debug("Exit Method Starts...");
		Main mainClazz = new Main();
		mainClazz.getPrimaryStage().close();
		HibernateSessionFactory.shutdown();
		logger.debug("Exit Method Ends...");
	}

	/**
	 * This is the action method. This method is triggered when "Edit Family" menu is
	 * selected.
	 */
	public void editFamily(){
		logger.debug("Edit Family method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/Edit_Family.fxml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("Edit Family method Ends...");
	}
	
	
	/**
	 * This is the action method. This method is triggered when "Pay Santha" menu is
	 * selected.
	 */
	public void paySantha(){
		logger.debug("Pay Santha method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/Santha.fxml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("Pay Santha method Ends...");
	}
	
	
	public void payServiceOffering(){
		logger.debug("payServiceOffering method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/Service_Offering.fxml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("payServiceOffering method Ends...");
	}
	
	
	public void showReportPage(){
		logger.debug("showReportPage method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/Report.fxml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("showReportPage method Ends...");
	}
	
	public void showBirthdayReportPage(){
		logger.debug("showBirthdayReportPage method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/Birthday_Report.fxml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("showBirthdayReportPage method Ends...");
	}
	
	public void showMonthReportPage(){
		logger.debug("showMonthReportPage method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/PieChart_Report.fxml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("showMonthReportPage method Ends...");
	}
	
	public void showComparisonPage(){
		logger.debug("showComparisonPage method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/BarChart_Report.fxml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("showComparisonPage method Ends...");
	}
	
	public void loadExpensePage(){
		logger.info("loadExpensePage method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/Expense.fxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("loadExpensePage method Ends...");
	}
	
	public void loadAccountDetails(){
		logger.info("loadAccountDetails method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/Accounts.fxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("loadAccountDetails method Ends...");
	}
	
	public void showAddIncomePage(){
		logger.info("showAddIncomePage method Starts...");
		try {
			replaceSceneContent("/com/ccf/fxml/Template.fxml",
					"/com/ccf/fxml/Add_Income.fxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("showAddIncomePage method Ends...");
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
