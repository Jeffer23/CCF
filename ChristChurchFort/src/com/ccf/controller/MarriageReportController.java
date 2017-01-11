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

import com.ccf.dao.MemberDao;
import com.ccf.dao.impl.MemberDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Member;
import com.ccf.vo.MarriedMembers;
import com.ccf.vo.Report;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import eu.schudt.javafx.controls.calendar.DatePicker;

public class MarriageReportController {

	final static Logger logger = Logger.getLogger(Report.class);

	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker toDate;

	@FXML
	private TableView<com.ccf.vo.MarriedMembers> marriedMembersReports;

	@FXML
	private Label message;

	public void getMarriedMembers() {
		logger.info("getMarriedMembers method Starts...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		MemberDao memberDaoImpl = new MemberDaoImpl();
		try {
			if (fromDate.getSelectedDate() == null)
				throw new CcfException("Select From date");
			if (toDate.getSelectedDate() == null)
				throw new CcfException("Select To date");
			marriedMembersReports.getItems().clear();
			MarriedMembers marriedMembers = null;
			List<Member> members = memberDaoImpl.getMarriedMembers(
					fromDate.getSelectedDate(), toDate.getSelectedDate());
			for (int i = 0; i + 1 < members.size(); i++) {
				if (members.get(i).getFamily().getNo() == members.get(i + 1)
						.getFamily().getNo()) {
					marriedMembers = new MarriedMembers();
					marriedMembers.setNames(members.get(i).getName() + " & "
							+ members.get(i + 1).getName());
					marriedMembers.setFamilyNo(members.get(i).getFamily()
							.getNo());
					marriedMembers.setMgeDate(sdf.format(members.get(i)
							.getMarriageDate()));
					marriedMembers.setAddress(members.get(i).getFamily()
							.getAddress());
					marriedMembers.setPhoneNo(String.valueOf(members.get(i)
							.getFamily().getPhoneNo()));

					marriedMembersReports.getItems().add(marriedMembers);
				}
			}
			message.setText(marriedMembersReports.getItems().size()
					+ " members celebrating their Marriage day");
			logger.info(marriedMembersReports.getItems().size()
					+ " members celebrating their Marriage day");

		} catch (CcfException e) {
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			e.printStackTrace();
		}
		logger.info("getMarriedMembers method Ends...");
	}

	public void exportMarriageReport() {
		logger.info("exportMarriageReport method Starts...");
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("CCf Marriage Report");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		ObservableList<MarriedMembers> reports = this.marriedMembersReports
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
		firstRow.createCell(2).setCellValue("Names");
		firstRow.createCell(3).setCellValue("Marriage Date");
		firstRow.createCell(4).setCellValue("Address");
		firstRow.createCell(5).setCellValue("Phone No");
		Row row = null;
		for (MarriedMembers report : reports) {
			row = sheet.createRow(rownum++);

			row.createCell(1).setCellValue(report.getFamilyNo());
			row.createCell(2).setCellValue(report.getNames());
			row.createCell(3).setCellValue(report.getMgeDate());
			row.createCell(4).setCellValue(report.getAddress());
			row.createCell(5).setCellValue(report.getPhoneNo());

		}

		try {
			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream("c://CCF//ccf.properties");
			// load a properties file
			prop.load(input);
			File file = new File(prop.getProperty("export_path")
					+ "Christ Church Fort-Marriage Report "
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

		logger.info("exportMarriageReport method Ends...");
	}
}
