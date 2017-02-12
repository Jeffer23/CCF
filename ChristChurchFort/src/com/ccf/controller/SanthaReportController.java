package com.ccf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.ccf.dao.SanthaDao;
import com.ccf.dao.impl.SanthaDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Santha;
import com.ccf.util.ProjectProperties;
import com.ccf.vo.Report;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.paint.Paint;

public class SanthaReportController {

	private final static Logger logger = Logger
			.getLogger(SanthaReportController.class);

	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
	private TableView<com.ccf.vo.Santha> santha;

	@FXML
	private Label subscriptionTotal;

	@FXML
	private Label harvestTotal;

	@FXML
	private Label missionaryTotal;

	@FXML
	private Label mensTotal;

	@FXML
	private Label womensTotal;

	@FXML
	private Label educationTotal;

	@FXML
	private Label preSchoolTotal;

	@FXML
	private Label youthTotal;

	@FXML
	private Label poorHelpTotal;

	@FXML
	private Label churchRenTotal;

	@FXML
	private Label graveyardTotal;

	@FXML
	private Label bagOfferTotal;

	@FXML
	private Label thanksOfferingTotal;

	@FXML
	private Label stoTotal;

	@FXML
	private Label total;

	@FXML
	private Label message;

	@FXML
	void initialize(){
		this.fromDate.setDateFormat(ProjectProperties.sdf);
		this.toDate.setDateFormat(ProjectProperties.sdf);
	}
	public void print() {
		logger.info("print method Starts...");
		message.setText("Functionality Under Construction");
		message.setTextFill(Paint.valueOf("RED"));
		logger.info("print method Ends...");
	}

	public void save() {
		logger.info("save method Starts...");
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("CCF Santha Report");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			ObservableList<com.ccf.vo.Santha> santhas = this.santha.getItems();

			

			int rownum = 0;
			sheet.createRow(rownum++);
			sheet.createRow(rownum++);
			Row reportPeriodRow = sheet.createRow(rownum++);
			reportPeriodRow.createCell(9).setCellValue("Period : ");
			reportPeriodRow.createCell(10).setCellValue(
					sdf.format(fromDate.getSelectedDate()));
			reportPeriodRow.createCell(11).setCellValue("-");
			reportPeriodRow.createCell(12).setCellValue(
					sdf.format(toDate.getSelectedDate()));
			sheet.createRow(rownum++);
			sheet.createRow(rownum++);
			Row firstRow = sheet.createRow(rownum++);
			firstRow.createCell(1).setCellValue("Family No");
			firstRow.createCell(2).setCellValue("Name");
			firstRow.createCell(3).setCellValue("Paid Date");
			firstRow.createCell(4).setCellValue("Paid For Date");
			firstRow.createCell(5).setCellValue("Subscription");
			firstRow.createCell(6).setCellValue("Harvest Festival");
			firstRow.createCell(7).setCellValue("Missionary");
			firstRow.createCell(8).setCellValue("Mens Fellowship");
			firstRow.createCell(9).setCellValue("Womens Fellowship");
			firstRow.createCell(10).setCellValue("Education Help");
			firstRow.createCell(11).setCellValue("Pre School");
			firstRow.createCell(12).setCellValue("Youth");
			firstRow.createCell(13).setCellValue("Poor Help");
			firstRow.createCell(14).setCellValue("Church Renovation");
			firstRow.createCell(15).setCellValue("Graveyard");
			firstRow.createCell(16).setCellValue("Bag Offer");
			firstRow.createCell(17).setCellValue("Thanks Offer");
			firstRow.createCell(18).setCellValue("Sto");
			firstRow.createCell(20).setCellValue("Total");
			for (com.ccf.vo.Santha santha : santhas) {
				Row row = sheet.createRow(rownum++);

				row.createCell(1).setCellValue(santha.getFamilyNo());
				row.createCell(2).setCellValue(santha.getName());
				row.createCell(3).setCellValue(santha.getPaidDate());
				row.createCell(4).setCellValue(santha.getPaidForDate());
				row.createCell(5).setCellValue(santha.getSubscription());
				row.createCell(6).setCellValue(santha.getHarvestFestival());
				row.createCell(7).setCellValue(santha.getMissionary());
				row.createCell(8).setCellValue(santha.getMensFellowship());
				row.createCell(9).setCellValue(santha.getWomensFellowship());
				row.createCell(10).setCellValue(santha.getEducationHelp());
				row.createCell(11).setCellValue(santha.getPreSchool());
				row.createCell(12).setCellValue(santha.getYouth());
				row.createCell(13).setCellValue(santha.getPoorHelp());
				row.createCell(14).setCellValue(santha.getChurchRenovation());
				row.createCell(15).setCellValue(santha.getGraveyard());
				row.createCell(16).setCellValue(santha.getBagOffer());
				row.createCell(17).setCellValue(santha.getThanksOffer());
				row.createCell(18).setCellValue(santha.getSto());
				row.createCell(20).setCellValue(santha.getTotal());

				
			}

			Row row = sheet.createRow(rownum++);
			row = sheet.createRow(rownum++);
			row.createCell(0).setCellValue("Total :");
			row.createCell(5).setCellValue(this.subscriptionTotal.getText());
			row.createCell(6).setCellValue(this.harvestTotal.getText());
			row.createCell(7).setCellValue(this.missionaryTotal.getText());
			row.createCell(8).setCellValue(this.mensTotal.getText());
			row.createCell(9).setCellValue(this.womensTotal.getText());
			row.createCell(10).setCellValue(this.educationTotal.getText());
			row.createCell(11).setCellValue(this.preSchoolTotal.getText());
			row.createCell(12).setCellValue(this.youthTotal.getText());
			row.createCell(13).setCellValue(this.poorHelpTotal.getText());
			row.createCell(14).setCellValue(this.churchRenTotal.getText());
			row.createCell(15).setCellValue(this.graveyardTotal.getText());
			row.createCell(16).setCellValue(this.bagOfferTotal.getText());
			row.createCell(17).setCellValue(this.thanksOfferingTotal.getText());
			row.createCell(18).setCellValue(this.stoTotal.getText());
			row.createCell(20).setCellValue(this.total.getText());
			
			sheet.createRow(rownum++);
			sheet.createRow(rownum++);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Subscription Amount");
			row.createCell(11).setCellValue(this.subscriptionTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Harvest Amount");
			row.createCell(11).setCellValue(this.harvestTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Missionary Amount");
			row.createCell(11).setCellValue(this.missionaryTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Mens Fellowship Amount");
			row.createCell(11).setCellValue(this.mensTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Womens Fellowship Amount");
			row.createCell(11).setCellValue(this.womensTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Education Amount");
			row.createCell(11).setCellValue(this.educationTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Pre School Amount");
			row.createCell(11).setCellValue(this.preSchoolTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Youth Amount");
			row.createCell(11).setCellValue(this.youthTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Poor Help Amount");
			row.createCell(11).setCellValue(this.poorHelpTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Church Renovation Amount");
			row.createCell(11).setCellValue(this.churchRenTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Graveyard Amount");
			row.createCell(11).setCellValue(this.graveyardTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Bag Offer Amount");
			row.createCell(11).setCellValue(this.bagOfferTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Thanks Offer Amount");
			row.createCell(11).setCellValue(this.thanksOfferingTotal.getText());
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("STO Amount");
			row.createCell(11).setCellValue(this.stoTotal.getText());
			sheet.createRow(rownum++);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Total Amount");
			row.createCell(11).setCellValue(this.total.getText());

			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream("c://CCF//ccf.properties");
			// load a properties file
			prop.load(input);
			File file = new File(prop.getProperty("export_path")
					+ "Christ Church Fort - Santha Report" + sdf.format(new Date()) + ".xls");

			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			logger.info("Excel written successfully..");
			message.setText("Data exported to " + file.getAbsolutePath());
			message.setTextFill(Paint.valueOf("GREEN"));
			logger.info("Data exported to " + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
		}  catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setTextFill(Paint.valueOf("RED"));
		}
		logger.info("save method Ends...");
	}

	public void getSanthaDetails() {
		logger.info("getSanthaDetails method Starts...");
		SanthaDao santhaDaoImpl = new SanthaDaoImpl();
		try {
			if (fromDate.getSelectedDate() == null)
				throw new CcfException("Select From date");
			if (toDate.getSelectedDate() == null)
				throw new CcfException("Select To date");
			
			float totalHarvestAmt = 0.0f;
			float totalMissionaryAmt = 0.0f;
			float totalMensFellowshipAmt = 0.0f;
			float totalWomensFellowshipAmt = 0.0f;
			float totalEducationAmt = 0.0f;
			float totalPreSchoolAmt = 0.0f;
			float totalYouthAmt = 0.0f;
			float totalPoorHelpAmt = 0.0f;
			float totalChurchRenovationAmt = 0.0f;
			float totalGraveyardAmt = 0.0f;
			float totalBagOfferAmt = 0.0f;
			float totalThanksOffer = 0.0f;
			float totalStoAmt = 0.0f;
			float totalSubscriptionAmt = 0.0f;
			float grandTotal = 0.0f;
			

			List<Santha> santhas = santhaDaoImpl.getReport(
					fromDate.getSelectedDate(), toDate.getSelectedDate());
			com.ccf.vo.Santha santhaVO = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.santha.getItems().clear();
			for (Santha santha : santhas) {
				santhaVO = new com.ccf.vo.Santha();
				santhaVO.setBagOffer(santha.getBagOffer());
				santhaVO.setChurchRenovation(santha.getChurchRenovation());
				santhaVO.setEducationHelp(santha.getEducationHelp());
				santhaVO.setFamilyNo(santha.getMember().getFamily().getNo());
				santhaVO.setGraveyard(santha.getGraveyard());
				santhaVO.setHarvestFestival(santha.getHarvestFestival());
				santhaVO.setMensFellowship(santha.getMensFellowship());
				santhaVO.setMissionary(santha.getMissionary());
				santhaVO.setName(santha.getMember().getName());
				santhaVO.setSubscription(santha.getSubscriptionAmount());
				santhaVO.setPaidDate(sdf.format(santha.getPaidDate()));
				santhaVO.setPaidForDate(sdf.format(santha.getPaidForDate()));
				santhaVO.setPoorHelp(santha.getPoorHelp());
				santhaVO.setPreSchool(santha.getPreSchool());
				santhaVO.setSto(santha.getSto());
				santhaVO.setSubscription(santha.getSubscriptionAmount());
				santhaVO.setThanksOffer(santha.getThanksOffer());
				santhaVO.setTotal(santha.getTotal());
				santhaVO.setWomensFellowship(santha.getWomensFellowship());
				santhaVO.setYouth(santha.getYouth());

				this.santha.getItems().add(santhaVO);
				
				totalHarvestAmt = totalHarvestAmt + santha.getHarvestFestival();
				totalMissionaryAmt = totalMissionaryAmt
						+ santha.getMissionary();
				totalMensFellowshipAmt = totalMensFellowshipAmt
						+ santha.getMensFellowship();
				totalWomensFellowshipAmt = totalWomensFellowshipAmt
						+ santha.getWomensFellowship();
				totalEducationAmt = totalEducationAmt
						+ santha.getEducationHelp();
				totalPreSchoolAmt = totalPreSchoolAmt
						+ santha.getPreSchool();
				totalYouthAmt = totalYouthAmt + santha.getYouth();
				totalPoorHelpAmt = totalPoorHelpAmt + santha.getPoorHelp();
				totalChurchRenovationAmt = totalChurchRenovationAmt
						+ santha.getChurchRenovation();
				totalGraveyardAmt = totalGraveyardAmt + santha.getGraveyard();
				totalBagOfferAmt = totalBagOfferAmt + santha.getBagOffer();
				totalThanksOffer = totalThanksOffer + santha.getThanksOffer();
				totalStoAmt = totalStoAmt + santha.getSto();
				totalSubscriptionAmt = totalSubscriptionAmt + santha.getSubscriptionAmount();
				grandTotal = grandTotal + santha.getTotal();
			}
			
			this.subscriptionTotal.setText(String.valueOf(totalSubscriptionAmt));
			this.harvestTotal.setText(String.valueOf(totalHarvestAmt));
			this.missionaryTotal.setText(String.valueOf(totalMissionaryAmt));
			this.mensTotal.setText(String.valueOf(totalMensFellowshipAmt));
			this.womensTotal.setText(String.valueOf(totalWomensFellowshipAmt));
			this.educationTotal.setText(String.valueOf(totalEducationAmt));
			this.preSchoolTotal.setText(String.valueOf(totalPreSchoolAmt));
			this.youthTotal.setText(String.valueOf(totalYouthAmt));
			this.poorHelpTotal.setText(String.valueOf(totalPoorHelpAmt));
			this.churchRenTotal.setText(String.valueOf(totalChurchRenovationAmt));
			this.graveyardTotal.setText(String.valueOf(totalGraveyardAmt));
			this.bagOfferTotal.setText(String.valueOf(totalBagOfferAmt));
			this.thanksOfferingTotal.setText(String.valueOf(totalThanksOffer));
			this.stoTotal.setText(String.valueOf(totalStoAmt));
			this.total.setText(String.valueOf(grandTotal));
			
			message.setText("Found " + this.santha.getItems().size() + " Records");
			message.setTextFill(Paint.valueOf("GREEN"));
			logger.info("Found " + this.santha.getItems().size() + " Records");
		} catch (CcfException e) {
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
			e.printStackTrace();
		}

		logger.info("getSanthaDetails method Ends...");
	}

}
