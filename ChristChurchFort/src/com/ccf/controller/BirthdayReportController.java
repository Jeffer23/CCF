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
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import com.ccf.dao.MemberDao;
import com.ccf.dao.SanthaDao;
import com.ccf.dao.impl.MemberDaoImpl;
import com.ccf.dao.impl.SanthaDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Member;
import com.ccf.persistence.classes.Santha;
import com.ccf.util.AgeCalculator;
import com.ccf.util.ProjectProperties;
import com.ccf.vo.BirthdayMember;
import com.ccf.vo.NonPaidMember;
import com.ccf.vo.Report;

import eu.schudt.javafx.controls.calendar.DatePicker;

public class BirthdayReportController {

	final static Logger logger = Logger.getLogger(Report.class);
	

	@FXML
	private TableView<com.ccf.vo.BirthdayMember> birthdayReports;

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

	@FXML
	void initialize(){
		this.fromDate.setDateFormat(ProjectProperties.sdf);
		this.toDate.setDateFormat(ProjectProperties.sdf);
		
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
			message.setTextFill(Paint.valueOf("Green"));
			logger.info("Data exported to " + file.getAbsolutePath());
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("Red"));
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("Red"));
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("Red"));
		}
		logger.debug("exportBirthdayReport method Ends...");
	}

	public void print(){
		logger.info("print method Starts...");
		message.setText("Functionality under construction.");
		message.setTextFill(Paint.valueOf("Red"));
		logger.info("print method Ends...");
	}
}
