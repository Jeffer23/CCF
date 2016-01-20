package com.ccf.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ccf.dao.impl.FamilyDaoImpl;
import com.ccf.dao.impl.MemberDaoImpl;
import com.ccf.doa.FamilyDao;
import com.ccf.doa.MemberDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Family;
import com.ccf.util.AgeCalculator;
import com.ccf.vo.Member;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FamilyController extends Application {

	final static Logger logger = Logger.getLogger(FamilyController.class);
	
	@FXML
	private TextField familyNo;

	@FXML
	private ComboBox<Integer> familyNos;

	@FXML
	private TextArea address;

	@FXML
	private TextField phoneNo;

	@FXML
	public TableView<Member> members;

	@FXML
	public Label message;

	@FXML
	private Label error;

	private static int count;

	@FXML
	void initialize() {
		logger.debug("init method Starts...");
		/*
		 * If familyNo is null, it means "Edit Family" menu is selected. Is
		 * FamilyNos is null, it means "Add New Family menu is selected"
		 */
		if (familyNo != null) {
			familyNo.focusedProperty().addListener(
					new ChangeListener<Boolean>() {
						@Override
						public void changed(
								ObservableValue<? extends Boolean> arg0,
								Boolean arg1, Boolean arg2) {
							// Handling only when focus is out.
							if (!arg2) {
								FamilyDao familyDaoImpl = new FamilyDaoImpl();
								try {
									boolean familyExists = familyDaoImpl
											.isFamilyExists(Integer
													.parseInt(familyNo
															.getText()));
									logger.info("Family no : " + familyNo.getText());
									logger.info("Family Exists : "
											+ familyExists);
									if (familyExists) {
										error.setText("This Family already added");
									} else {
										error.setText("");
									}

								} catch (NumberFormatException e) {
									error.setText("Please Enter a valid number");
									e.printStackTrace();
								} catch (CcfException e) {
									message.setText(e.getMessage());
									e.printStackTrace();
								} catch (Exception e){
									message.setText(e.getMessage());
									e.printStackTrace();
								}
							}
						}
					});
		}

		if (familyNos != null) {
			FamilyDao familyDaoImpl = new FamilyDaoImpl();
			try {
				familyNos.getItems().addAll(familyDaoImpl.getFamilyNos());

				familyNos.focusedProperty().addListener(
						new ChangeListener<Boolean>() {
							@Override
							public void changed(
									ObservableValue<? extends Boolean> arg0,
									Boolean arg1, Boolean arg2) {
								// Handling only when focus is out.
								if (!arg2) {
									FamilyDao familyDaoImpl = new FamilyDaoImpl();
									try {
										Family family = familyDaoImpl
												.getFamily(Integer
														.parseInt(familyNos
																.getEditor()
																.getText()));
										if (family != null) {
											address.setText(family.getAddress());
											phoneNo.setText(String
													.valueOf(family
															.getPhoneNo()));

											// Getting the Family member list
											MemberDao memberDaoImpl = new MemberDaoImpl();
											List<com.ccf.persistence.classes.Member> mems = memberDaoImpl.getMembers(Integer
													.parseInt(familyNos
															.getEditor()
															.getText()));
											SimpleDateFormat sdf = new SimpleDateFormat(
													"yyyy-MM-dd");
											Member member = null;
											members.getItems().clear();
											if (mems != null) {
												for (com.ccf.persistence.classes.Member mem : mems) {
													member = new Member();
													member.setName(mem
															.getName());
													member.setDob(sdf
															.format(mem
																	.getDob()));
													member.setEligibility(mem
															.getEligibility());
													member.setAge(AgeCalculator
															.calculateAge(mem
																	.getDob()));
													member.setSubscriptionAmount(mem
															.getSubscriptionAmount());
													if (mem.getLivedTill() == null)
														member.setLivedTill(null);
													else
														member.setLivedTill(sdf.format(mem
																.getLivedTill()));
													member.setId(mem.getId());
													members.getItems().add(
															member);
												}
											}
										} else {
											error.setText("This family not registered!!");
											address.setText("");
											phoneNo.setText("");
											members.getItems().clear();
											message.setText("");
											logger.error("This family not registered!!");
										}
									} catch (NumberFormatException e) {
										error.setText("Only Numbers are allowed");
										logger.error("Only Numbers are allowed");
										e.printStackTrace();
									} catch (CcfException e) {
										error.setText(e.getMessage());
										logger.error(e.getMessage());
										e.printStackTrace();
									} catch (Exception e) {
										error.setText(e.getMessage());
										logger.error(e.getMessage());
										e.printStackTrace();
									}
								}
							}
						});

			} catch (CcfException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

		logger.debug("init method Ends...");
	}

	public void addMember() {
		logger.debug("Add Member method Starts...");
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(
					"/com/ccf/fxml/Add_Member_Popup.fxml"));

		} catch (IOException e) {

			logger.error(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
		}

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setTitle("Add New Member");
		stage.setScene(scene);
		MemberController.setStage(stage);
		MemberController.setController(this);
		stage.show();

		logger.debug("Add Member method Ends...");
	}

	public void editMember() {
		logger.debug("Edit Member method Starts...");
		AnchorPane root = null;
		MemberController controller;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/com/ccf/fxml/Add_Member_Popup.fxml"));
			root = (AnchorPane) loader.load();
			controller = loader.<MemberController> getController();
			controller.initData(members.getSelectionModel().getSelectedItem());
		} catch (IOException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			message.setText(e.getMessage());
		}

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setTitle("Edit Member");
		stage.setScene(scene);
		MemberController.setStage(stage);
		MemberController.setController(this);
		stage.show();
		logger.debug("Edit Member method Ends...");
	}

	public void deleteMember() {
		logger.debug("Delete Member method Starts...");
		Member selectedMember = members.getSelectionModel().getSelectedItem();
		if (selectedMember == null)
			message.setText("Please Select the member");
		

		// Removing the member from DB
		if (selectedMember.getId() != 0) {
			try {
				MemberDao memberDaoImpl = new MemberDaoImpl();
				com.ccf.persistence.classes.Member member = new com.ccf.persistence.classes.Member();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				member.setId(selectedMember.getId());
				member.setDob(sdf.parse(selectedMember.getDob()));
				member.setName(selectedMember.getName());
				member.setEligibility(selectedMember.getEligibility());
				member.setSubscriptionAmount(selectedMember
						.getSubscriptionAmount());
				if (selectedMember.getLivedTill() == null
						|| selectedMember.getLivedTill() == "")
					member.setLivedTill(null);
				else
					member.setLivedTill(sdf.parse(selectedMember.getLivedTill()));
				Family family = new Family();
				if (familyNo != null)
					family.setNo(Integer.parseInt(familyNo.getText()));
				else if (familyNos != null)
					family.setNo(Integer.parseInt(familyNos.getEditor()
							.getText()));
				member.setFamily(family);
				memberDaoImpl.deleteMember(member);
				
				members.getItems().remove(selectedMember);
				
				message.setText(selectedMember.getName() + " has deleted.");
				logger.info(selectedMember.getName() + " has deleted.");
			} catch (ParseException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			} catch (CcfException e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		logger.debug("Delete Member method Ends...");
	}

	public void saveFamily() {
		logger.debug("Save Family method Starts...");

		logger.info("Family No : " + familyNo.getText());
		logger.info("Family Address : " + address.getText());
		logger.info("Family Phone No : " + phoneNo.getText());

		try {
			if(familyNo.getText() == null)
				throw new CcfException("Enter Family no");
			if(address.getText() == null)
				throw new CcfException("Enter the address");
			if(phoneNo.getText() == null)
				throw new CcfException("Enter the Phone No");
			if(phoneNo.getText().length() != 10 || !Pattern.matches("[0-9]+", phoneNo.getText()))
				throw new CcfException("Enter correct Phone No.");
			if(address.getText().length() >=100)
				throw new CcfException("Address is too long.");
			
			// Inserting Family Data
			FamilyDao familyDaoImpl = new FamilyDaoImpl();
			Family family = new Family();
			family.setNo(Integer.parseInt(familyNo.getText()));
			family.setAddress(address.getText());
			family.setPhoneNo(Long.parseLong(phoneNo.getText()));
			familyDaoImpl.saveFamily(family);

			// Inserting Member Data
			MemberDao memberDaoImpl = new MemberDaoImpl();
			ObservableList<Member> memberList = members.getItems();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			com.ccf.persistence.classes.Member member = null;
			for (Member memberVO : memberList) {
				member = new com.ccf.persistence.classes.Member();
				member.setName(memberVO.getName());
				member.setDob(sdf.parse(memberVO.getDob()));
				member.setEligibility(memberVO.getEligibility());
				member.setSubscriptionAmount(memberVO.getSubscriptionAmount());
				member.setFamily(family);
				member.setLivedTill(null);
				memberDaoImpl.addMember(member);
			}

			// Clearing values
			familyNo.setText("");
			address.setText("");
			phoneNo.setText("");
			members.getItems().removeAll(memberList);

			message.setText("Family Added Successfully");
			logger.info("Family " + familyNo.getText() + " added successfully.");

		} catch (ParseException e) {
			message.setText(e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (CcfException e) {
			message.setText(e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		logger.debug("Save Family method Ends...");
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
			if (filteredFamilyNos.size() > 0) {
				familyNos.getItems().addAll(filteredFamilyNos);
				error.setText("");
			} else {
				error.setText("This family not registered!!");
			}
		} catch (CcfException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.debug("filterFamilyNos method Ends...");
	}

	public void updateFamily() {
		logger.debug("Update Family Starts...");
		FamilyDao familyDaoImpl = new FamilyDaoImpl();
		try {
			if(familyNos.getEditor().getText() == null)
				throw new CcfException("Enter Family no");
			if(address.getText() == null)
				throw new CcfException("Enter the address");
			if(phoneNo.getText() == null)
				throw new CcfException("Enter the Phone No");
			if(phoneNo.getText().length() != 10 || !Pattern.matches("[0-9]+", phoneNo.getText()))
				throw new CcfException("Enter correct Phone No.");
			if(address.getText().length() >=100)
				throw new CcfException("Address is too long.");
			
			Family family = new Family();
			family.setAddress(address.getText());
			family.setPhoneNo(Long.parseLong(phoneNo.getText()));
			family.setNo(Integer.parseInt(familyNos.getEditor().getText()));
			familyDaoImpl.updateFamily(family);

			// Updating family members
			MemberDao memberDaoImpl = new MemberDaoImpl();
			com.ccf.persistence.classes.Member member = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Family existingfamily = new Family();
			existingfamily.setNo(Integer.parseInt(familyNos.getEditor()
					.getText()));
			for (Member mem : members.getItems()) {
				member = new com.ccf.persistence.classes.Member();
				member.setName(mem.getName());
				member.setDob(sdf.parse(mem.getDob()));
				member.setEligibility(mem.getEligibility());
				member.setSubscriptionAmount(mem.getSubscriptionAmount());
				member.setFamily(existingfamily);
				if (mem.getLivedTill() == null || mem.getLivedTill() == "")
					member.setLivedTill(null);
				else
					member.setLivedTill(sdf.parse(mem.getLivedTill()));
				if (mem.getId() != 0) {
					member.setId(mem.getId());
					memberDaoImpl.updateMember(member);
				} else if (mem.getId() == 0) {
					memberDaoImpl.addMember(member);
				}
			}

			// Getting the Family member list
			List<com.ccf.persistence.classes.Member> mems = memberDaoImpl
					.getMembers(Integer.parseInt(familyNos.getEditor()
							.getText()));
			Member memberUI = null;
			members.getItems().clear();
			if (mems != null) {
				for (com.ccf.persistence.classes.Member mem : mems) {
					memberUI = new Member();
					memberUI.setName(mem.getName());
					memberUI.setDob(sdf.format(mem.getDob()));
					memberUI.setEligibility(mem.getEligibility());
					memberUI.setAge(AgeCalculator.calculateAge(mem.getDob()));
					memberUI.setSubscriptionAmount(mem.getSubscriptionAmount());
					memberUI.setId(mem.getId());
					if (mem.getLivedTill() == null)
						memberUI.setLivedTill(null);
					else
						memberUI.setLivedTill(sdf.format(mem.getLivedTill()));
					members.getItems().add(memberUI);
				}
			}

			message.setText("Family updated Successfully");
			logger.info("Family " + familyNos.getEditor().getText() + " Updated Successfully.");

		} catch (CcfException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (ParseException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		logger.debug("Update Family Ends...");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

}
