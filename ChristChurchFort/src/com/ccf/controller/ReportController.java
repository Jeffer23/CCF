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

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import com.ccf.dao.MemberDao;
import com.ccf.dao.SanthaDao;
import com.ccf.dao.impl.MemberDaoImpl;
import com.ccf.dao.impl.SanthaDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Member;
import com.ccf.persistence.classes.Santha;
import com.ccf.util.AgeCalculator;
import com.ccf.vo.BirthdayMember;
import com.ccf.vo.NonPaidMember;
import com.ccf.vo.Report;

import eu.schudt.javafx.controls.calendar.DatePicker;

public class ReportController extends Application {

	final static Logger logger = Logger.getLogger(Report.class);
	
	@FXML
	private TableView<com.ccf.vo.Report> reports;

	@FXML
	private TableView<com.ccf.vo.BirthdayMember> birthdayReports;

	@FXML
	private TableView<com.ccf.vo.NonPaidMember> nonPaidMembers;

	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
	private Label message;

	@FXML
	private ImageView exportPaidMembers;

	@FXML
	private ImageView exportNonPaidMembers;

	

	public void getPaidMembers() {
		logger.debug("getPaidMembers method Starts...");
		reports.setVisible(true);
		nonPaidMembers.setVisible(false);
		SanthaDao santhaDaoImpl = new SanthaDaoImpl();
		exportPaidMembers.setVisible(true);
		exportNonPaidMembers.setVisible(false);
		try {
			if(fromDate.getSelectedDate() == null)
				throw new CcfException("Select From date");
			if(toDate.getSelectedDate() == null)
				throw new CcfException("Select To date");
			List<Santha> santhas = santhaDaoImpl.getReport(
					fromDate.getSelectedDate(), toDate.getSelectedDate());
			Report report = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			reports.getItems().clear();
			if (santhas != null) {
				for (Santha santha : santhas) {
					report = new Report();
					report.setBagOffer(santha.getBagOffer());
					report.setChurchRenovation(santha.getChurchRenovation());
					report.setEducationHelp(santha.getEducationHelp());
					report.setFamilyNo(santha.getMember().getFamily().getNo());
					report.setGraveyard(santha.getGraveyard());
					report.setHarvestFestival(santha.getHarvestFestival());
					report.setMensFellowship(santha.getMensFellowship());
					report.setMissionary(santha.getMissionary());
					report.setName(santha.getMember().getName());
					report.setSubscription(santha.getSubscriptionAmount());
					report.setPaidDate(sdf.format(santha.getPaidDate()));
					report.setPaidForDate(sdf.format(santha.getPaidForDate()));
					report.setPoorHelp(santha.getPoorHelp());
					report.setPreSchool(santha.getPreSchool());
					report.setSto(santha.getSto());
					report.setSubscription(santha.getSubscriptionAmount());
					report.setThanksOffer(santha.getThanksOffer());
					report.setTotal(santha.getTotal());
					report.setWomensFellowship(santha.getWomensFellowship());
					report.setYouth(santha.getYouth());

					reports.getItems().add(report);
				}
			}
			message.setText("Found " + reports.getItems().size() + " Records");
			logger.info("Found " + reports.getItems().size() + " Records");
		} catch (CcfException e) {
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		logger.debug("getPaidMembers method Ends...");
	}

	public void getNonPaidMembers() {
		logger.debug("getPaidMembers method Starts...");
		reports.setVisible(false);
		nonPaidMembers.setVisible(true);
		exportPaidMembers.setVisible(false);
		exportNonPaidMembers.setVisible(true);
		MemberDao memberDaoImpl = new MemberDaoImpl();
		try {
			if(fromDate.getSelectedDate() == null)
				throw new CcfException("Select From date");
			if(toDate.getSelectedDate() == null)
				throw new CcfException("Select To date");
			List<Member> nonPaidmembersList = memberDaoImpl.getNonPaidMember(
					fromDate.getSelectedDate(), toDate.getSelectedDate());
			NonPaidMember nonPaidMember = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			nonPaidMembers.getItems().clear();
			if (nonPaidmembersList != null) {
				for (Member member : nonPaidmembersList) {
					nonPaidMember = new NonPaidMember();
					nonPaidMember.setAddress(member.getFamily().getAddress());
					nonPaidMember.setFamilyNo(member.getFamily().getNo());
					nonPaidMember.setName(member.getName());
					nonPaidMember.setPhoneNo(member.getFamily().getPhoneNo());
					nonPaidMember.setSubscriptionAmount(member
							.getSubscriptionAmount());

					nonPaidMembers.getItems().add(nonPaidMember);
				}
			}
			message.setText("Found " + nonPaidMembers.getItems().size()
					+ " Records");
			logger.info("Found " + nonPaidMembers.getItems().size()
					+ " Records");
		} catch (CcfException e) {
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			e.printStackTrace();
		}  catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("getPaidMembers method Ends...");
	}

	public void exportToExcel() {
		logger.debug("exportToExcel method Starts...");
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("CCF Payment Report");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			ObservableList<Report> reports = this.reports.getItems();

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
			firstRow.createCell(19).setCellValue("Other1");
			firstRow.createCell(20).setCellValue("Other2");
			firstRow.createCell(21).setCellValue("Total");
			for (Report report : reports) {
				Row row = sheet.createRow(rownum++);

				row.createCell(1).setCellValue(report.getFamilyNo());
				row.createCell(2).setCellValue(report.getName());
				row.createCell(3).setCellValue(report.getPaidDate());
				row.createCell(4).setCellValue(report.getPaidForDate());
				row.createCell(5).setCellValue(report.getSubscription());
				row.createCell(6).setCellValue(report.getHarvestFestival());
				row.createCell(7).setCellValue(report.getMissionary());
				row.createCell(8).setCellValue(report.getMensFellowship());
				row.createCell(9).setCellValue(report.getWomensFellowship());
				row.createCell(10).setCellValue(report.getEducationHelp());
				row.createCell(11).setCellValue(report.getPreSchool());
				row.createCell(12).setCellValue(report.getYouth());
				row.createCell(13).setCellValue(report.getPoorHelp());
				row.createCell(14).setCellValue(report.getChurchRenovation());
				row.createCell(15).setCellValue(report.getGraveyard());
				row.createCell(16).setCellValue(report.getBagOffer());
				row.createCell(17).setCellValue(report.getThanksOffer());
				row.createCell(18).setCellValue(report.getSto());
				row.createCell(19).setCellValue(report.getSubscription());
				row.createCell(21).setCellValue(report.getTotal());

				totalHarvestAmt = totalHarvestAmt + report.getHarvestFestival();
				totalMissionaryAmt = totalMissionaryAmt
						+ report.getMissionary();
				totalMensFellowshipAmt = totalMensFellowshipAmt
						+ report.getMensFellowship();
				totalWomensFellowshipAmt = totalWomensFellowshipAmt
						+ report.getWomensFellowship();
				totalEducationAmt = totalEducationAmt
						+ report.getEducationHelp();
				totalPreSchoolAmt = totalPreSchoolAmt
						+ report.getPreSchool();
				totalYouthAmt = totalYouthAmt + report.getYouth();
				totalPoorHelpAmt = totalPoorHelpAmt + report.getPoorHelp();
				totalChurchRenovationAmt = totalChurchRenovationAmt
						+ report.getChurchRenovation();
				totalGraveyardAmt = totalGraveyardAmt + report.getGraveyard();
				totalBagOfferAmt = totalBagOfferAmt + report.getBagOffer();
				totalThanksOffer = totalThanksOffer + report.getThanksOffer();
				totalStoAmt = totalStoAmt + report.getSto();
				totalSubscriptionAmt = totalSubscriptionAmt + report.getSubscription();
				grandTotal = grandTotal + report.getTotal();
			}

			Row row = sheet.createRow(rownum++);
			row = sheet.createRow(rownum++);
			row.createCell(0).setCellValue("Total :");
			row.createCell(6).setCellValue(totalHarvestAmt);
			row.createCell(7).setCellValue(totalMissionaryAmt);
			row.createCell(8).setCellValue(totalMensFellowshipAmt);
			row.createCell(9).setCellValue(totalWomensFellowshipAmt);
			row.createCell(10).setCellValue(totalEducationAmt);
			row.createCell(11).setCellValue(totalPreSchoolAmt);
			row.createCell(12).setCellValue(totalYouthAmt);
			row.createCell(13).setCellValue(totalPoorHelpAmt);
			row.createCell(14).setCellValue(totalChurchRenovationAmt);
			row.createCell(15).setCellValue(totalGraveyardAmt);
			row.createCell(16).setCellValue(totalBagOfferAmt);
			row.createCell(17).setCellValue(totalThanksOffer);
			row.createCell(18).setCellValue(totalStoAmt);
			row.createCell(19).setCellValue(totalSubscriptionAmt);
			row.createCell(21).setCellValue(grandTotal);
			
			sheet.createRow(rownum++);
			sheet.createRow(rownum++);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Harvest Amount");
			row.createCell(11).setCellValue(totalHarvestAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Missionary Amount");
			row.createCell(11).setCellValue(totalMissionaryAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Mens Fellowship Amount");
			row.createCell(11).setCellValue(totalMensFellowshipAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Womens Fellowship Amount");
			row.createCell(11).setCellValue(totalWomensFellowshipAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Education Amount");
			row.createCell(11).setCellValue(totalEducationAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Pre School Amount");
			row.createCell(11).setCellValue(totalPreSchoolAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Youth Amount");
			row.createCell(11).setCellValue(totalYouthAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Poor Help Amount");
			row.createCell(11).setCellValue(totalPoorHelpAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Church Renovation Amount");
			row.createCell(11).setCellValue(totalChurchRenovationAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Graveyard Amount");
			row.createCell(11).setCellValue(totalGraveyardAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Bag Offer Amount");
			row.createCell(11).setCellValue(totalBagOfferAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Thanks Offer Amount");
			row.createCell(11).setCellValue(totalThanksOffer);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("STO Amount");
			row.createCell(11).setCellValue(totalStoAmt);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Other1 Amount");
			row.createCell(11).setCellValue(totalSubscriptionAmt);
			row = sheet.createRow(rownum++);
			sheet.createRow(rownum++);
			row = sheet.createRow(rownum++);
			row.createCell(10).setCellValue("Total Amount");
			row.createCell(11).setCellValue(grandTotal);

			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream("c://CCF//ccf.properties");
			// load a properties file
			prop.load(input);
			File file = new File(prop.getProperty("export_path")
					+ "Christ Church Fort - paid Members" + sdf.format(new Date()) + ".xls");

			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			logger.info("Excel written successfully..");
			message.setText("Data exported to " + file.getAbsolutePath());
			logger.info("Data exported to " + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
		}  catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("exportToExcel method Ends...");
	}

	public void exportNonPaidMembers() {
		logger.debug("exportNonPaidMembers method Starts...");
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("CCF Non Payment Report");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ObservableList<NonPaidMember> members = this.nonPaidMembers
					.getItems();
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
			firstRow.createCell(3).setCellValue("Address");
			firstRow.createCell(4).setCellValue("Phone No");
			firstRow.createCell(5).setCellValue("Subscription Amount");

			for (NonPaidMember member : members) {
				Row row = sheet.createRow(rownum++);

				row.createCell(1).setCellValue(member.getFamilyNo());
				row.createCell(2).setCellValue(member.getName());
				row.createCell(3).setCellValue(member.getAddress());
				row.createCell(4).setCellValue(member.getPhoneNo());
				row.createCell(5).setCellValue(member.getSubscriptionAmount());

			}

			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream("c://CCF//ccf.properties");
			// load a properties file
			prop.load(input);
			File file = new File(prop.getProperty("export_path")
					+ "Christ Church Fort - Non Paid Member" + sdf.format(new Date()) + ".xls");

			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			logger.info("Excel written successfully..");
			message.setText("Data exported to " + file.getAbsolutePath());
			logger.info("Data exported to " + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("exportNonPaidMembers method Ends...");
	}

	public void getBirthdays() {
		logger.debug("getMethods method Starts...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		MemberDao memberDaoImpl = new MemberDaoImpl();
		try {
			if(fromDate.getSelectedDate() == null)
				throw new CcfException("Select From date");
			if(toDate.getSelectedDate() == null)
				throw new CcfException("Select To date");
			List<Member> members = memberDaoImpl.getBirthdayMembers(
					fromDate.getSelectedDate(), toDate.getSelectedDate());
			BirthdayMember bDayMember = null;
			birthdayReports.getItems().clear();
			for (Member member : members) {
				bDayMember = new BirthdayMember();
				bDayMember.setName(member.getName());
				bDayMember.setAddress(member.getFamily().getAddress());
				bDayMember.setAge(AgeCalculator.calculateAge(member.getDob()));
				bDayMember.setDob(sdf.format(member.getDob()));
				bDayMember.setFamilyNo(member.getFamily().getNo());
				bDayMember.setPhoneNo(String.valueOf(member.getFamily()
						.getPhoneNo()));
				birthdayReports.getItems().add(bDayMember);
			}
			message.setText(birthdayReports.getItems().size()
					+ " members celebrating their birthday");
			logger.info(birthdayReports.getItems().size()
					+ " members celebrating their birthday");
		} catch (CcfException e) {
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("getMethods method Ends...");
	}

	public void exportBirthdayReport() {
		logger.debug("exportBirthdayReport method Starts...");
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("CCf Birthday Report");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			ObservableList<BirthdayMember> reports = this.birthdayReports
					.getItems();

			int rownum = 0;
			sheet.createRow(rownum++);
			sheet.createRow(rownum++);
			Row reportPeriodRow = sheet.createRow(rownum++);
			reportPeriodRow.createCell(1).setCellValue("Period : ");
			reportPeriodRow.createCell(2).setCellValue(
					sdf.format(fromDate.getSelectedDate()));
			reportPeriodRow.createCell(3).setCellValue("-");
			reportPeriodRow.createCell(4).setCellValue(
					sdf.format(toDate.getSelectedDate()));
			sheet.createRow(rownum++);
			sheet.createRow(rownum++);
			Row firstRow = sheet.createRow(rownum++);
			firstRow.createCell(1).setCellValue("Family No");
			firstRow.createCell(2).setCellValue("Name");
			firstRow.createCell(3).setCellValue("D.O.B.");
			firstRow.createCell(4).setCellValue("Age");
			firstRow.createCell(5).setCellValue("Address");
			firstRow.createCell(6).setCellValue("Phone No");
			for (BirthdayMember report : reports) {
				Row row = sheet.createRow(rownum++);

				row.createCell(1).setCellValue(report.getFamilyNo());
				row.createCell(2).setCellValue(report.getName());
				row.createCell(3).setCellValue(report.getDob());
				row.createCell(4).setCellValue(report.getAge());
				row.createCell(5).setCellValue(report.getAddress());
				row.createCell(6).setCellValue(report.getPhoneNo());

			}

			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream("c://CCF//ccf.properties");
			// load a properties file
			prop.load(input);
			File file = new File(prop.getProperty("export_path")
					+ "Christ Church Fort-Birthday Report "
					+ sdf.format(new Date()) + ".xls");

			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			out.close();
			logger.info("Excel written successfully..");
			message.setText("Data exported to " + file.getAbsolutePath());
			logger.info("Data exported to " + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("exportBirthdayReport method Ends...");
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub

	}
}
