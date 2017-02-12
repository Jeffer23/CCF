package com.ccf.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import com.ccf.dao.FamilyDao;
import com.ccf.dao.MemberDao;
import com.ccf.dao.impl.FamilyDaoImpl;
import com.ccf.dao.impl.MemberDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Member;
import com.ccf.vo.Santha;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class MemberSanthaController {

	final static Logger logger = Logger.getLogger(MemberSanthaController.class);

	@FXML
	ComboBox<Integer> familyNos;

	@FXML
	DatePicker fromDate;

	@FXML
	ChoiceBox<Member> familyMembers;

	@FXML
	DatePicker toDate;

	@FXML
	TableView<Santha> membersSantha;

	@FXML
	Label error;

	@FXML
	Label message;

	@FXML
	void initialize() {
		logger.debug("init method Starts...");
		familyNos.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				if (arg1) {
					logger.debug("FamilyNos focus change Listener method starts...");
					try {
						if (familyNos.getEditor().getText().trim().equals("") || familyNos.getEditor().getText()
								.matches(".*[a-zA-Z]+.*")) {
							error.setText("Enter Only the number");
							throw new CcfException("Enter Only the number");
						}
						FamilyDao familyDaoImpl = new FamilyDaoImpl();
						boolean familyExists = familyDaoImpl
								.isFamilyExists(Integer.parseInt(familyNos
										.getEditor().getText()));
						if (familyExists) {
							error.setText("");
							membersSantha.getItems().clear();
							MemberDao memberDaoImpl = new MemberDaoImpl();

							List<Member> members = memberDaoImpl
									.getMembers(Integer.parseInt(familyNos
											.getEditor().getText()));
							familyMembers.getItems().clear();
							familyMembers.getItems().addAll(members);
							if (members.size() > 0) {
								familyMembers.setValue(members.get(0));
							} else if (error.getText().equals("")) {
								message.setText("No members in this family added.");
							}
						}else {
							error.setText("This family is not registered.");
						}
					} catch (CcfException e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					} catch (Exception e){
						logger.error(e.getMessage());
						e.printStackTrace();
					}
					logger.debug("FamilyNos focus change Listener method ends...");
				}

			}

		});
	}

	public void filterFamilyNos() {
		logger.debug("filterFamilyNos method Starts...");
		FamilyDao familyDaoImpl = new FamilyDaoImpl();
		try {
			logger.info("Family No : " + familyNos.getEditor().getText());
			List<Integer> filteredFamilyNos = familyDaoImpl
					.getFilteredFamilyNos(Integer.parseInt(familyNos
							.getEditor().getText()));
			familyNos.getItems().clear();
			familyNos.getItems().addAll(filteredFamilyNos);

		} catch (CcfException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.debug("filterFamilyNos method Ends...");
	}

	public void getDetails() {
		logger.debug("getDetails method Starts...");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		membersSantha.getItems().clear();
		logger.debug("Family No : " + familyNos.getEditor().getText());
		logger.debug("Member Id : " + familyMembers.getSelectionModel().getSelectedItem().getId());
		logger.debug("From Date : " + sdf.format(fromDate.getSelectedDate()));
		logger.debug("To Date : " + sdf.format(toDate.getSelectedDate()));
		
		MemberDao dao = new MemberDaoImpl();
		try {
			List<com.ccf.persistence.classes.Santha> santhas = dao.getMemberSanthaDetails(fromDate.getSelectedDate(), toDate.getSelectedDate(), familyMembers.getSelectionModel().getSelectedItem());
			Santha santhaVO = null;
			for(com.ccf.persistence.classes.Santha santha: santhas){
				santhaVO = new Santha();
				santhaVO.setName(santha.getMember().getName());
				santhaVO.setSubscription(santha.getMember().getSubscriptionAmount());
				santhaVO.setHarvestFestival(santha.getHarvestFestival());
				santhaVO.setMissionary(santha.getMissionary());
				santhaVO.setMensFellowship(santha.getMensFellowship());
				santhaVO.setWomensFellowship(santha.getWomensFellowship());
				santhaVO.setEducationHelp(santha.getEducationHelp());
				santhaVO.setPreSchool(santha.getPreSchool());
				santhaVO.setYouth(santha.getYouth());
				santhaVO.setPoorHelp(santha.getPoorHelp());
				santhaVO.setChurchRenovation(santha.getChurchRenovation());
				santhaVO.setGraveyard(santha.getGraveyard());
				santhaVO.setBagOffer(santha.getBagOffer());
				santhaVO.setThanksOffer(santha.getThanksOffer());
				santhaVO.setSto(santha.getSto());
				santhaVO.setPaidDate(sdf.format(santha.getPaidDate()));
				santhaVO.setPaidForDate(sdf.format(santha.getPaidForDate()));
				santhaVO.setTotal(santha.getTotal());
				
				membersSantha.getItems().add(santhaVO);
			}
		} catch (CcfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.debug("getDetails method Ends...");
	}
}
