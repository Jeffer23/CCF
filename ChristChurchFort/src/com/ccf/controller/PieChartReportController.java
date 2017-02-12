package com.ccf.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ccf.dao.MemberDao;
import com.ccf.dao.SanthaDao;
import com.ccf.dao.impl.MemberDaoImpl;
import com.ccf.dao.impl.SanthaDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Member;
import com.ccf.persistence.classes.Santha;
import com.ccf.util.ProjectProperties;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PieChartReportController extends Application {

	final static Logger logger = Logger.getLogger(PieChartReportController.class);
	
	@FXML
	private PieChart chart;

	@FXML
	private PieChart monthChart;

	@FXML
	private DatePicker date;

	@FXML
	private Label error;

	@FXML
	void initialize() {
		this.date.setDateFormat(ProjectProperties.sdf);
	}

	public void getReport() {
		logger.debug("Get Report method Starts...");

		try {
			ObservableList<PieChart.Data> data = chart.getData();
			error.setText("");
			data.clear();
			MemberDao memberDaoImpl = new MemberDaoImpl();
			SanthaDao santhaDaoImpl = new SanthaDaoImpl();
			Calendar cal = Calendar.getInstance();
			if (date.getSelectedDate() == null
					|| date.getSelectedDate().equals(""))
				throw new CcfException("Select the month.");
			cal.setTime(date.getSelectedDate());
			int days = cal.getActualMaximum(cal.DAY_OF_MONTH);

			Date fromDate = cal.getTime();
			fromDate.setDate(1);

			Date toDate = cal.getTime();
			toDate.setDate(days);
			List<Member> totalNonPaidMembers = memberDaoImpl.getNonPaidMember(fromDate, toDate);
			List<Santha> santhas = santhaDaoImpl.getReport(fromDate, toDate);
			int paidMembers = santhas.size();
			int nonPaidMembers = totalNonPaidMembers.size();
			int totalMembers = paidMembers + nonPaidMembers;
			float paidMembersPercentage = ((float) paidMembers / (float) totalMembers) * 100;
			data.add(new PieChart.Data("Paid Members (" + paidMembers + ")",
					paidMembersPercentage));
			data.add(new PieChart.Data("Members not paid ("
					+ nonPaidMembers + ")",
					(100 - paidMembersPercentage)));
			chart.setData(data);
			chart.setTitle("Total Members : " + totalMembers);
			chart.setLegendSide(Side.TOP);

			// Month Report
			ObservableList<PieChart.Data> monthData = monthChart.getData();
			monthData.clear();
			float bagOffer = 0;
			float churchRenovation = 0;
			float educationHelp = 0;
			float graveyard = 0;
			float harvestFestival = 0;
			float mensFellowship = 0;
			float missionary = 0;
			float subscription = 0;
			float poorHelp = 0;
			float primarySchool = 0;
			float sto = 0;
			float thanksOffer = 0;
			float womensFelloShip = 0;
			float youth = 0;
			for (Santha santha : santhas) {
				bagOffer += santha.getBagOffer();
				churchRenovation += santha.getChurchRenovation();
				educationHelp += santha.getEducationHelp();
				graveyard += santha.getGraveyard();
				harvestFestival += santha.getHarvestFestival();
				mensFellowship += santha.getMensFellowship();
				missionary += santha.getMissionary();
				subscription += santha.getSubscriptionAmount();
				poorHelp += santha.getPoorHelp();
				primarySchool += santha.getPreSchool();
				sto += santha.getSto();
				thanksOffer += santha.getThanksOffer();
				womensFelloShip += santha.getWomensFellowship();
				youth += santha.getYouth();
			}
			monthData.add(new PieChart.Data("Bag Offer (" + bagOffer + ")",
					bagOffer));
			monthData.add(new PieChart.Data("Church Renovation ("
					+ churchRenovation + ")", churchRenovation));
			monthData.add(new PieChart.Data("Education Help (" + educationHelp
					+ ")", educationHelp));
			monthData.add(new PieChart.Data("Graveyard (" + graveyard + ")",
					graveyard));
			monthData.add(new PieChart.Data("Harvest Festival ("
					+ harvestFestival + ")", harvestFestival));
			monthData.add(new PieChart.Data("Mens Fellowship ("
					+ mensFellowship + ")", mensFellowship));
			monthData.add(new PieChart.Data("Missionary (" + missionary + ")",
					missionary));
			monthData.add(new PieChart.Data("subcription (" + subscription + ")", subscription));
			monthData.add(new PieChart.Data("Poor Help (" + poorHelp + ")",
					poorHelp));
			monthData.add(new PieChart.Data("Primary School (" + primarySchool
					+ ")", primarySchool));
			monthData.add(new PieChart.Data("STO (" + sto + ")", sto));
			monthData.add(new PieChart.Data("Thanks Offer (" + thanksOffer
					+ ")", thanksOffer));
			monthData.add(new PieChart.Data("Womens FelloShip ("
					+ womensFelloShip + ")", womensFelloShip));
			monthData.add(new PieChart.Data("Youth (" + youth + ")", youth));

			float total = bagOffer + churchRenovation + educationHelp
					+ graveyard + harvestFestival + mensFellowship + missionary
					+ subscription + poorHelp + primarySchool + sto
					+ thanksOffer + womensFelloShip + youth;
			monthChart.setData(monthData);
			monthChart.setTitle("Total Amount : Rs." + total);
			monthChart.setLegendSide(Side.TOP);
		} catch (CcfException e) {
			logger.error(e.getMessage());
			error.setText(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("Get Report method Ends...");
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}
}
