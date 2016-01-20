package com.ccf.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ccf.dao.impl.SanthaDaoImpl;
import com.ccf.doa.SanthaDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Santha;
import com.ccf.util.HibernateSessionFactory;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import eu.schudt.javafx.controls.calendar.DatePicker;

public class ComparisonReportController extends Application {

	final static Logger logger = Logger.getLogger(ComparisonReportController.class);
	
	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
	private Label fromDateError;

	@FXML
	private Label toDateError;
	
	@FXML
	public AnchorPane anchorPane;
	
	private CategoryAxis xAxis = new CategoryAxis();

	
	private NumberAxis yAxis = new NumberAxis();

	
	private StackedBarChart<String, Number> chart = new StackedBarChart<String, Number>(xAxis, yAxis); ;

	@FXML
	void initialize() {
		logger.debug("Initialize method Starts...");
		fromDate.setDateFormat(new SimpleDateFormat("MMMM-YYYY"));
		toDate.setDateFormat(new SimpleDateFormat("MMMM-YYYY"));
		chart.setLayoutX(45.0);
		chart.setLayoutY(187.0);
		chart.setPrefHeight(456.0);
		chart.setPrefWidth(1206.0);
		logger.debug("Initialize method Ends...");
	}

    
    
	public void getReport() {
		logger.debug("Get Report method Starts...");
		try {
			fromDateError.setText("");
			toDateError.setText("");
			anchorPane.getChildren().remove(chart);
			if (fromDate.getSelectedDate() == null)
				throw new CcfException("Choose the month");
			if (toDate.getSelectedDate() == null)
				throw new CcfException("Choose the month");
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM-YYYY");
			xAxis.setLabel("Offerings");
			xAxis.setCategories(FXCollections
					.<String> observableArrayList(Arrays.asList("Bag Offer",
							"Church Re.", "Edu. Help", "Graveyard",
							"Har. Fest.", "MF",
							"Missionary", "Other1", "Other2", "Poor",
							"Pri. School", "STO", "Thanks Offer",
							"WF", "Youth")));
			yAxis.setLabel("INR.");
			
			XYChart.Series<String, Number> series;
			chart.getData().clear();

			Date tempFromDate = null;
			Calendar cal = Calendar.getInstance();
			cal.setTime(fromDate.getSelectedDate());
			int days = cal.getActualMaximum(cal.DAY_OF_MONTH);

			tempFromDate = cal.getTime();
			tempFromDate.setDate(1);

			Date tempToDate = cal.getTime();
			tempToDate.setDate(days);

			do {
				SanthaDao santhaDaoImpl = new SanthaDaoImpl();
				SessionFactory sessionFactory = HibernateSessionFactory
						.getSessionFactory();
				Session session = sessionFactory.openSession();
				float bagOffer = 0;
				float churchRenovation = 0;
				float educationHelp = 0;
				float graveyard = 0;
				float harvestFestival = 0;
				float mensFellowship = 0;
				float missionary = 0;
				float other1 = 0;
				float other2 = 0;
				float poorHelp = 0;
				float primarySchool = 0;
				float sto = 0;
				float thanksOffer = 0;
				float womensFelloShip = 0;
				float youth = 0;
				SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
				List<Santha> santhas = santhaDaoImpl.getReport(tempFromDate,
						tempToDate, session);
				for (Santha santha : santhas) {
					bagOffer += santha.getBagOffer();
					churchRenovation += santha.getChurchRenovation();
					educationHelp += santha.getEducationHelp();
					graveyard += santha.getGraveyard();
					harvestFestival += santha.getHarvestFestival();
					mensFellowship += santha.getMensFellowship();
					missionary += santha.getMissionary();
					other1 += santha.getOther1();
					other2 += santha.getOther2();
					poorHelp += santha.getPoorHelp();
					primarySchool += santha.getPrimarySchool();
					sto += santha.getSto();
					thanksOffer += santha.getThanksOffer();
					womensFelloShip += santha.getWomensFellowship();
					youth += santha.getYouth();
				}
				series = new XYChart.Series<String, Number>();
				series.setName(sdf.format(tempFromDate));
				series.getData()
						.add(new XYChart.Data<String, Number>("Bag Offer",
								bagOffer));
				series.getData().add(
						new XYChart.Data<String, Number>("Church Re.",
								churchRenovation));
				series.getData().add(
						new XYChart.Data<String, Number>("Edu. Help",
								educationHelp));
				series.getData()
						.add(new XYChart.Data<String, Number>("Graveyard",
								graveyard));
				series.getData().add(
						new XYChart.Data<String, Number>("Har. Fest.",
								harvestFestival));
				series.getData().add(
						new XYChart.Data<String, Number>("MF",
								mensFellowship));
				series.getData()
						.add(new XYChart.Data<String, Number>("Missionary",
								missionary));
				series.getData().add(
						new XYChart.Data<String, Number>("Other1", other1));
				series.getData().add(
						new XYChart.Data<String, Number>("Other2", other2));
				series.getData().add(
						new XYChart.Data<String, Number>("Poor", poorHelp));
				series.getData().add(
						new XYChart.Data<String, Number>("Pri. School",
								primarySchool));
				series.getData().add(
						new XYChart.Data<String, Number>("STO", sto));
				series.getData()
						.add(new XYChart.Data<String, Number>("Thanks Offer",
								thanksOffer));
				series.getData().add(
						new XYChart.Data<String, Number>("WF",
								womensFelloShip));
				series.getData().add(
						new XYChart.Data<String, Number>("Youth", youth));
				chart.getData().add(series);
				session.close();

				cal.add(cal.MONTH, 1);
				int monthDays = cal.getActualMaximum(cal.DAY_OF_MONTH);
				tempFromDate = cal.getTime();
				tempFromDate.setDate(1);
				tempToDate = cal.getTime();
				tempToDate.setDate(monthDays);
				
			} while (tempFromDate.before(toDate.getSelectedDate()));
			anchorPane.getChildren().add(chart);
		} catch (CcfException e) {
			if (fromDate.getSelectedDate() == null)
				fromDateError.setText(e.getMessage());
			else if (toDate.getSelectedDate() == null)
				toDateError.setText(e.getMessage());
			else
				e.printStackTrace();
		} catch (Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("Get Report method Ends...");
	}

	  @Override
	    public void start(Stage stage) {
	      
	    }
	  
	

}
