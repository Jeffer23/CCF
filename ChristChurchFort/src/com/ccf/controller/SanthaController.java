package com.ccf.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ccf.dao.AccountsDao;
import com.ccf.dao.FamilyDao;
import com.ccf.dao.MemberDao;
import com.ccf.dao.SanthaDao;
import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.dao.impl.FamilyDaoImpl;
import com.ccf.dao.impl.MemberDaoImpl;
import com.ccf.dao.impl.SanthaDaoImpl;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.BankGraveyardAccount;
import com.ccf.persistence.classes.BankMensAccount;
import com.ccf.persistence.classes.BankMissionaryAccount;
import com.ccf.persistence.classes.BankPCAccount;
import com.ccf.persistence.classes.BankEducationalFundAccount;
import com.ccf.persistence.classes.BankBuildingAccount;
import com.ccf.persistence.classes.BankWomensAccount;
import com.ccf.persistence.classes.BankYouthAccount;
import com.ccf.persistence.classes.Cheque;
import com.ccf.persistence.classes.GraveyardAccount;
import com.ccf.persistence.classes.Ledger;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.EducationalFundAccount;
import com.ccf.persistence.classes.Santha;
import com.ccf.persistence.classes.BuildingAccount;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.persistence.interfaces.Account;
import com.ccf.persistence.interfaces.IGraveyardAccount;
import com.ccf.persistence.interfaces.IMensAccount;
import com.ccf.persistence.interfaces.IMissionaryAccount;
import com.ccf.persistence.interfaces.IPCAccount;
import com.ccf.persistence.interfaces.IEducationalFundAccount;
import com.ccf.persistence.interfaces.ISpecialThanksOfferingAccount;
import com.ccf.persistence.interfaces.IWomensAccount;
import com.ccf.persistence.interfaces.IYouthAccount;
import com.ccf.util.BalanceUpdator;
import com.ccf.util.HibernateSessionFactory;
import com.ccf.util.ProjectProperties;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class SanthaController {

	final static Logger logger = Logger.getLogger(SanthaController.class);

	@FXML
	private ComboBox<Integer> familyNos;

	@FXML
	private DatePicker paidDate;

	@FXML
	private DatePicker paidForDate;

	@FXML
	private ChoiceBox<String> familyMembers;

	@FXML
	private TextField educationHelp;

	@FXML
	private TextField preSchool;

	@FXML
	private TextField youth;

	@FXML
	private TextField poorHelp;

	@FXML
	private TextField graveyard;

	@FXML
	private TextField churchRenovation;

	@FXML
	private TextField bagOffer;

	@FXML
	private TextField thanksOffer;

	@FXML
	private TextField harvestFestival;

	@FXML
	private TextField missionary;

	@FXML
	private TextField sto;

	@FXML
	private TextField subscriptionAmt;

	@FXML
	private TextField mensFellowship;

	@FXML
	private TextField womensFellowship;

	@FXML
	private RadioButton cash;

	@FXML
	private RadioButton chequeBtn;

	@FXML
	private HBox chequeDetails;

	@FXML
	private Label total;

	@FXML
	private Label error;

	@FXML
	private Label message;

	@FXML
	private TableView<com.ccf.vo.Santha> membersSantha;

	@FXML
	private Label familyMemberError;

	@FXML
	private Button saveButton;

	@FXML
	private Button updateButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Label memberTotal;

	@FXML
	private Label paidForDateError;

	@FXML
	private Label paidDateError;

	@FXML
	private DatePicker chequeDate;

	@FXML
	private TextField chequeNumber;
	
	@FXML
	private Button print;

	private Cheque cheque = null;
	private com.ccf.vo.Santha memberSantha = null;

	

	@FXML
	void initialize() {
		logger.debug("init method Starts...");
		this.paidDate.setDateFormat(ProjectProperties.sdf);
		this.paidForDate.setDateFormat(ProjectProperties.sdf);
		this.chequeDate.setDateFormat(ProjectProperties.sdf);
		
		paidDate.setDisable(true);
		familyMembers.setDisable(true);
		paidForDate.setDisable(true);
		disableAll();
		familyNos.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				// Handling only when focus is out.
				if (!arg2) {
					logger.debug("Family No focus change listener method starts...");
					try {
						if (familyNos.getEditor().getText().trim().equals("")
								|| familyNos.getEditor().getText()
										.matches(".*[a-zA-Z]+.*")) {
							error.setText("Enter Only the number");
							throw new CcfException("Enter Only the number");
						}
						FamilyDao familyDaoImpl = new FamilyDaoImpl();
						boolean familyExists = familyDaoImpl
								.isFamilyExists(Integer.parseInt(familyNos
										.getEditor().getText()));
						if (familyExists) {
							paidDate.setDisable(false);
							familyMembers.setDisable(false);
							paidForDate.setDisable(false);
							familyMemberError.setText("");
							message.setText("");
							error.setText("");
							total.setText("0.00");
							membersSantha.getItems().clear();
							enableAll();
							MemberDao memberDaoImpl = new MemberDaoImpl();

							List<String> members = memberDaoImpl
									.getMemberNames(Integer.parseInt(familyNos
											.getEditor().getText()));
							familyMembers.getItems().clear();
							familyMembers.getItems().addAll(members);
							if (members.size() > 0) {
								familyMembers.setValue(members.get(0)); // This
																		// calls
																		// the
																		// value
																		// change
																		// listener.
								if (paidDate.getSelectedDate() != null) {
									loadPaidMembers();
								}
							} else if (error.getText().equals("")) {
								familyMemberError
										.setText("No members in this family added.");
							}

							if (paidDate.getSelectedDate() != null)
								paidForDate.setSelectedDate(paidDate
										.getSelectedDate());// This calls the
															// value change
															// listener.

						} else {
							paidDate.setDisable(true);
							familyMembers.getItems().clear();
							familyMembers.setDisable(true);
							paidForDate.setDisable(true);
							familyMembers.getItems().clear();
							membersSantha.getItems().clear();
							clearData();
							disableAll();
							error.setText("This family is not registered.");
						}

					} catch (NumberFormatException e) {
						familyMemberError.setText("Only numbers are allowed");
						familyMemberError.setTextFill(Paint.valueOf("Red"));
						logger.error(e.getMessage());
						e.printStackTrace();
					} catch (CcfException e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					} catch (Exception e) {
						message.setText(e.getMessage());
						message.setTextFill(Paint.valueOf("Red"));
						e.printStackTrace();
						logger.error(e.getMessage());
					}
					logger.debug("Family No focus change listener method Ends...");
				}
			}
		});

		familyMembers.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> arg0,
							String oldValue, String newValue) {
						logger.debug("Family members Value change listener Starts...");
						List<String> paidMemberNames = new ArrayList<String>();
						for (com.ccf.vo.Santha paidMembers : membersSantha
								.getItems()) {
							paidMemberNames.add(paidMembers.getName());
						}

						if (paidMemberNames.contains(newValue)) {
							disableAll();
							clearData();
							familyMemberError
									.setText("This member already paid");
						} else {
							enableAll();
							getPreviouslyPaidAmount();
							MemberDao dao = new MemberDaoImpl();
							try {
								if (familyMembers.getValue() != null
										&& (subscriptionAmt.getText() == null || subscriptionAmt.getText().equals("") || subscriptionAmt.getText().equals("0")) && (!subscriptionAmt.getText().equals("") && Float.parseFloat(subscriptionAmt.getText()) <=0)) // Temporary
																					// fix
									subscriptionAmt.setText(String.valueOf(dao
											.getSubscriptionAmount(Integer
													.parseInt(familyNos
															.getEditor()
															.getText()),
													familyMembers.getValue())));
							} catch (NumberFormatException e) {
								error.setText("Only numbers are allowed");
								error.setTextFill(Paint.valueOf("Red"));
								logger.error(e.getMessage());
							} catch (CcfException e) {
								message.setText("Error : " + e.getMessage());
								message.setTextFill(Paint.valueOf("Red"));
								e.printStackTrace();
							}
							familyMemberError.setText("");
						}
						logger.debug("Family members Value change listener Ends...");
					}

				});

		paidDate.selectedDateProperty().addListener(new InvalidationListener() {
			public void invalidated(Observable observable) {
				logger.debug("Paid Date set date method Starts...");
				// Updating paid for datePicker
				paidForDate.setSelectedDate(paidDate.getSelectedDate());

				// loadPaidMembers();
				logger.debug("Paid Date set date method Ends...");
			}
		});

		paidForDate.selectedDateProperty().addListener(
				new InvalidationListener() {
					public void invalidated(Observable observable) {
						logger.debug("paidForDate set date method Starts...");
						if (paidDate.getSelectedDate() != null)
							paidDateError.setText("");
						if (paidForDate.getSelectedDate() != null)
							paidForDateError.setText("");
						loadPaidMembers();

						if (familyMembers.getItems().size() != membersSantha
								.getItems().size()) {
							getPreviouslyPaidAmount();
							/*
							 * MemberDao impl = new MemberDaoImpl(); try {
							 * logger.debug("Member Name : " +
							 * familyMembers.getValue());
							 * logger.debug("Family No : " +
							 * familyNos.getValue()); int memberId =
							 * impl.getMemberId(familyMembers.getValue(),
							 * familyNos.getValue());
							 * logger.debug("member Id : " + memberId);
							 * com.ccf.persistence.classes.Member member =
							 * impl.getMember(memberId);
							 * subscriptionAmt.setText(
							 * String.valueOf(member.getSubscriptionAmount()));
							 * } catch (CcfException e) { e.printStackTrace(); }
							 */
						}
						
						logger.debug("paidForDate set date method Ends...");
					}
				});

		bagOffer.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean onBlur, Boolean onFocus) {
				if (onBlur) {
					logger.debug("on Blur of bag Offer starts...");
					float total = calculateTotal();
					memberTotal.setText(String.valueOf(total));
					logger.debug("on Blur of bag Offer Ends...");
				}
			}
		});
		churchRenovation.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean onBlur, Boolean onFocus) {
						if (onBlur) {
							logger.debug("on Blur of churchRenovation starts...");
							float total = calculateTotal();
							memberTotal.setText(String.valueOf(total));
							logger.debug("on Blur of churchRenovation Ends...");
						}
					}
				});
		educationHelp.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean onBlur, Boolean onFocus) {
						if (onBlur) {
							logger.debug("on Blur of educationHelp starts...");
							float total = calculateTotal();
							memberTotal.setText(String.valueOf(total));
							logger.debug("on Blur of educationHelp Ends...");
						}
					}
				});
		graveyard.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean onBlur, Boolean onFocus) {
				if (onBlur) {
					logger.debug("on Blur of graveyard starts...");
					float total = calculateTotal();
					memberTotal.setText(String.valueOf(total));
					logger.debug("on Blur of graveyard Ends...");
				}
			}
		});
		harvestFestival.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean onBlur, Boolean onFocus) {
						if (onBlur) {
							logger.debug("on Blur of harvestFestival starts...");
							float total = calculateTotal();
							memberTotal.setText(String.valueOf(total));
							logger.debug("on Blur of harvestFestival Ends...");
						}
					}
				});
		mensFellowship.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean onBlur, Boolean onFocus) {
						if (onBlur) {
							logger.debug("on Blur of mensFellowship starts...");
							float total = calculateTotal();
							memberTotal.setText(String.valueOf(total));
							logger.debug("on Blur of mensFellowship Ends...");
						}
					}
				});
		missionary.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean onBlur, Boolean onFocus) {
				if (onBlur) {
					logger.debug("on Blur of missionary starts...");
					float total = calculateTotal();
					memberTotal.setText(String.valueOf(total));
					logger.debug("on Blur of missionary Ends...");
				}
			}
		});

		subscriptionAmt.textProperty().addListener(
				new ChangeListener<String>() {

					@Override
					public void changed(
							ObservableValue<? extends String> observable,
							String oldValue, String newValue) {
						logger.debug("on change of subscription Amount Starts...");
						float total = calculateTotal();
						memberTotal.setText(String.valueOf(total));
						logger.debug("on change of subscription Amount Ends...");

					}
				});
		/*
		 * subscriptionAmt.focusedProperty().addListener(new
		 * ChangeListener<Boolean>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Boolean>
		 * arg0, Boolean onBlur, Boolean onFocus) { if (onBlur) {
		 * logger.debug("on Blur of other2 starts..."); float total =
		 * calculateTotal(); memberTotal.setText(String.valueOf(total));
		 * logger.debug("on Blur of other2 Ends..."); } } });
		 */
		poorHelp.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean onBlur, Boolean onFocus) {
				if (onBlur) {
					logger.debug("on Blur of poorHelp starts...");
					float total = calculateTotal();
					memberTotal.setText(String.valueOf(total));
					logger.debug("on Blur of poorHelp Ends...");
				}
			}
		});
		preSchool.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean onBlur, Boolean onFocus) {
						if (onBlur) {
							logger.debug("on Blur of primarySchool starts...");
							float total = calculateTotal();
							memberTotal.setText(String.valueOf(total));
							logger.debug("on Blur of primarySchool Ends...");
						}
					}
				});
		sto.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean onBlur, Boolean onFocus) {
				if (onBlur) {
					logger.debug("on Blur of sto starts...");
					float total = calculateTotal();
					memberTotal.setText(String.valueOf(total));
					logger.debug("on Blur of sto Ends...");
				}
			}
		});
		thanksOffer.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean onBlur, Boolean onFocus) {
						if (onBlur) {
							logger.debug("on Blur of thanksOffer starts...");
							float total = calculateTotal();
							memberTotal.setText(String.valueOf(total));
							logger.debug("on Blur of thanksOffer Ends...");
						}
					}
				});
		womensFellowship.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					@Override
					public void changed(
							ObservableValue<? extends Boolean> arg0,
							Boolean onBlur, Boolean onFocus) {
						if (onBlur) {
							logger.debug("on Blur of womensFellowship starts...");
							float total = calculateTotal();
							memberTotal.setText(String.valueOf(total));
							logger.debug("on Blur of womensFellowship Ends...");
						}
					}
				});
		youth.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean onBlur, Boolean onFocus) {
				if (onBlur) {
					logger.debug("on Blur of youth starts...");
					float total = calculateTotal();
					memberTotal.setText(String.valueOf(total));
					logger.debug("on Blur of youth Ends...");
				}
			}
		});
		logger.debug("init method Ends...");

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
			message.setTextFill(Paint.valueOf("Red"));
			message.setText(e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			message.setTextFill(Paint.valueOf("Red"));
			message.setText(e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.debug("filterFamilyNos method Ends...");
	}

	public void save() {
		logger.info("Save Family method Starts...");

		/*
		 * Logger
		 */
		logger.debug("Family No : " + familyNos.getEditor().getText());
		logger.debug("Paid Date : " + paidDate.getSelectedDate());
		logger.debug("Family Member : " + familyMembers.getValue());
		logger.debug("Paid For : " + paidForDate.getSelectedDate());
		logger.debug("Harvest Festival : " + harvestFestival.getText());
		logger.debug("Missionary : " + missionary.getText());
		logger.debug("Men's Fellowship : " + mensFellowship.getText());
		logger.debug("Women's Fellowship : " + womensFellowship.getText());
		logger.debug("Education Help : " + educationHelp.getText());
		logger.debug("Primary School : " + preSchool.getText());
		logger.debug("Youth : " + youth.getText());
		logger.debug("Poor Help : " + poorHelp.getText());
		logger.debug("Graveyard : " + graveyard.getText());
		logger.debug("Church Renovation : " + churchRenovation.getText());
		logger.debug("Bag Offer : " + bagOffer.getText());
		logger.debug("Thanks Offer : " + thanksOffer.getText());
		logger.debug("Special Thanks Offer : " + sto.getText());
		logger.debug("SubScription Amount : " + subscriptionAmt.getText());


		// Creating Hibernate Session
		SessionFactory sessionFactory = HibernateSessionFactory
				.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		
		try {
			/*
			 * Validating the inputs
			 */
			if (familyNos.getEditor().getText() == null
					|| familyNos.getEditor().getText().equals("")) {
				familyMemberError.setText("Family number cannot be empty");
				throw new CcfException("Family number cannot be empty");
			}
			if (paidDate.getSelectedDate() == null) {
				paidDateError.setText("Select the Date");
				throw new CcfException("Select the Paid Date");
			}
			if (paidForDate.getSelectedDate() == null) {
				paidForDateError.setText("Select the Date");
				throw new CcfException("Select the Paid For Date");
			}
			if (harvestFestival.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Harvest Festival amount cannot contain alphabets");
			}
			if (missionary.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Missionary amount cannot contain alphabets");
			}
			if (mensFellowship.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Men's Fellowship amount cannot contain alphabets");
			}
			if (womensFellowship.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Women's Fellowship amount cannot contain alphabets");
			}
			if (educationHelp.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Education Help amount cannot contain alphabets");
			}
			if (preSchool.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Pre School amount cannot contain alphabets");
			}
			if (youth.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException("Youth amount cannot contain alphabets");
			}
			if (poorHelp.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Poor Help amount cannot contain alphabets");
			}
			if (graveyard.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Graveyard amount cannot contain alphabets");
			}
			if (churchRenovation.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Church Renovation amount cannot contain alphabets");
			}
			if (bagOffer.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Bag Offer amount cannot contain alphabets");
			}
			if (thanksOffer.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Thanks Offer amount cannot contain alphabets");
			}
			if (sto.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException("STO amount cannot contain alphabets");
			}
			if (subscriptionAmt.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Subscription amount cannot contain alphabets");
			} else if (subscriptionAmt.getText().equals("")) {
				throw new CcfException("Subscription amount cannot be empty.");
			}

			if (bagOffer.getText().equals(""))
				bagOffer.setText("0");
			if (churchRenovation.getText().equals(""))
				churchRenovation.setText("0");
			if (educationHelp.getText().equals(""))
				educationHelp.setText("0");
			if (graveyard.getText().equals(""))
				graveyard.setText("0");
			if (harvestFestival.getText().equals(""))
				harvestFestival.setText("0");
			if (mensFellowship.getText().equals(""))
				mensFellowship.setText("0");
			if (missionary.getText().equals(""))
				missionary.setText("0");
			if (poorHelp.getText().equals(""))
				poorHelp.setText("0");
			if (preSchool.getText().equals(""))
				preSchool.setText("0");
			if (sto.getText().equals(""))
				sto.setText("0");
			if (thanksOffer.getText().equals(""))
				thanksOffer.setText("0");
			if (womensFellowship.getText().equals(""))
				womensFellowship.setText("0");
			if (youth.getText().equals(""))
				youth.setText("0");

			
			MemberDao memberDaoImpl = new MemberDaoImpl();
			Santha santha = new Santha();
			int membetId;

			if (paidDate.getSelectedDate() == null) {
				throw new CcfException("Please Select the date");
			}

			membetId = memberDaoImpl.getMemberId(familyMembers.getValue(),
					Integer.parseInt(familyNos.getEditor().getText()));

			float bagOffer = Float.parseFloat(this.bagOffer.getText());
			float churchRenovation = Float.parseFloat(this.churchRenovation
					.getText());
			float educationHelp = Float
					.parseFloat(this.educationHelp.getText());
			float graveyard = Float.parseFloat(this.graveyard.getText());
			float harvestFestival = Float.parseFloat(this.harvestFestival
					.getText());
			float mensFellowship = Float.parseFloat(this.mensFellowship
					.getText());
			float missionary = Float.parseFloat(this.missionary.getText());
			float poorHelp = Float.parseFloat(this.poorHelp.getText());
			float preSchool = Float
					.parseFloat(this.preSchool.getText());
			float sto = Float.parseFloat(this.sto.getText());
			float thanksOffer = Float.parseFloat(this.thanksOffer.getText());
			float womensFellowship = Float.parseFloat(this.womensFellowship
					.getText());
			float youth = Float.parseFloat(this.youth.getText());

			// get subscription amount of this person
			float subscriptionAmount = Float.parseFloat(this.subscriptionAmt
					.getText());

			/*
			 * Delete getSubscriptionAmount method from memberDao interface.
			 */
			/*
			 * memberDaoImpl.getSubscriptionAmount(
			 * Integer.parseInt(familyNos.getEditor().getText()),
			 * familyMembers.getValue());
			 */

			com.ccf.persistence.classes.Member member = new com.ccf.persistence.classes.Member();
			member.setId(membetId);
			santha.setMember(member);
			santha.setPaidDate(paidDate.getSelectedDate());
			santha.setPaidForDate(paidForDate.getSelectedDate());
			santha.setBagOffer(bagOffer);
			santha.setChurchRenovation(churchRenovation);
			santha.setEducationHelp(educationHelp);
			santha.setGraveyard(graveyard);
			santha.setHarvestFestival(harvestFestival);
			santha.setMensFellowship(mensFellowship);
			santha.setMissionary(missionary);
			santha.setPoorHelp(poorHelp);
			santha.setPreSchool(preSchool);
			santha.setSto(sto);
			santha.setThanksOffer(thanksOffer);
			santha.setWomensFellowship(womensFellowship);
			santha.setYouth(youth);
			santha.setSubscriptionAmount(subscriptionAmount);
			float memberTotal = santha.getBagOffer()
					+ santha.getChurchRenovation() + santha.getEducationHelp()
					+ santha.getGraveyard() + santha.getHarvestFestival()
					+ santha.getMensFellowship() + santha.getMissionary()
					+ santha.getSubscriptionAmount() + santha.getPoorHelp()
					+ santha.getPreSchool() + santha.getSto()
					+ santha.getThanksOffer() + santha.getWomensFellowship()
					+ santha.getYouth();
			santha.setTotal(memberTotal);

			AccountsDao impl = new AccountsDaoImpl();
			IPCAccount pcAccount = null;
			Ledger ledger = null;
			Map<String, Ledger> ledgerMap = new HashMap<>();
			/*
			 * Getting ledger records from DB
			 */
			List<Ledger> ledgers = impl.getAllLedgers("Santha - ");
			for (Ledger l : ledgers) {
				ledgerMap.put(l.getLedgerName(), l);
			}

			/*
			 * Creating cheque object
			 */

			if (this.chequeBtn.isSelected()) {
				cheque = createChequeObject(memberTotal, session);
			}
			
			if(subscriptionAmount <= 0 ){
				throw new CcfException("Enter Subscription Amount");
			}

			/*
			 * Adding Subscription amount to PC Account
			 */
			{
				ledger = ledgerMap.get("Santha - Subscription Amount");
				float currentBalance = 0.0f;
				if (this.cash.isSelected()) {
					pcAccount = new PCAccount();
					currentBalance = impl.getCurrentAccountBalance(pcAccount.getClass());
					for(IPCAccount pca : santha.getPcAccounts()){
						currentBalance += pca.getAmount();
					}
					santha.getPcAccounts().add(pcAccount);
				} else if (this.chequeBtn.isSelected()) {
					pcAccount = new BankPCAccount();
					currentBalance = impl.getCurrentAccountBalance(pcAccount.getClass());
					for(IPCAccount pca : santha.getBankPCAccounts()){
						currentBalance += pca.getAmount();
					}
					santha.getBankPCAccounts().add(pcAccount);
					// ledger.getBankPCAccounts().add((BankPCAccount)pcAccount);
					addChequetoBankPCAcc(pcAccount);
				}
			
				 
				float balance = currentBalance + subscriptionAmount;
				pcAccount.setAmount(subscriptionAmount);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Santha - Subscription Amount");
				pcAccount.setSantha(santha);
				pcAccount.setDate(paidForDate.getSelectedDate());
				pcAccount.setBalance(balance);
				((Account) pcAccount).setLedger(ledger);
			}

			if (harvestFestival != 0.0f) {
				ledger = ledgerMap.get("Santha - Harvest Festival");
				float currentBalance = 0.0f;
				if (this.cash.isSelected()) {
					pcAccount = new PCAccount();
					currentBalance = impl.getCurrentAccountBalance(pcAccount.getClass());
					for(IPCAccount pca : santha.getPcAccounts()){
						currentBalance += pca.getAmount();
					}
					santha.getPcAccounts().add(pcAccount);
				} else if (this.chequeBtn.isSelected()) {
					pcAccount = new BankPCAccount();
					currentBalance = impl.getCurrentAccountBalance(pcAccount.getClass());
					for(IPCAccount pca : santha.getBankPCAccounts()){
						currentBalance += pca.getAmount();
					}
					santha.getBankPCAccounts().add(pcAccount);
					// ledger.getBankPCAccounts().add((BankPCAccount)pcAccount);
					addChequetoBankPCAcc(pcAccount);
				}
				
				float balance = currentBalance + harvestFestival;
				pcAccount.setAmount(harvestFestival);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Santha - Harvest Festival");
				pcAccount.setSantha(santha);
				pcAccount.setDate(paidForDate.getSelectedDate());
				pcAccount.setBalance(balance);
				((Account) pcAccount).setLedger(ledger);

			}

			IEducationalFundAccount  educationalAccount = null;
			if (educationHelp != 0.0f) {
				ledger = ledgerMap.get("Santha - Education Help");
				if (this.cash.isSelected()) {
					educationalAccount = new EducationalFundAccount();
					santha.getEducationalFundAccounts().add(educationalAccount);
				} else if (this.chequeBtn.isSelected()) {
					educationalAccount = new BankEducationalFundAccount();
					santha.getBankEducationalFundAccounts().add(educationalAccount);
					
					BankEducationalFundAccount bankEduFundAcc = (BankEducationalFundAccount) educationalAccount;
					bankEduFundAcc.getCheques().add(cheque);
					cheque.getBankEducationalFundAccounts().add(bankEduFundAcc);
				}
				float currentBalance = impl.getCurrentAccountBalance(educationalAccount.getClass());
				float balance = currentBalance + educationHelp;
				educationalAccount.setAmount(educationHelp);
				educationalAccount.setCr_dr("CR");
				educationalAccount.setDescription("Santha - Education Help");
				educationalAccount.setSantha(santha);
				educationalAccount.setDate(paidForDate.getSelectedDate());
				educationalAccount.setBalance(balance);
				((Account) educationalAccount).setLedger(ledger);
			}

			if (poorHelp != 0.0f) {
				ledger = ledgerMap.get("Santha - Poor Help");
				float currentBalance = 0.0f;
				if (this.cash.isSelected()) {
					pcAccount = new PCAccount();
					currentBalance = impl.getCurrentAccountBalance(pcAccount.getClass());
					for(IPCAccount pca : santha.getPcAccounts()){
						currentBalance += pca.getAmount();
					}
					santha.getPcAccounts().add(pcAccount);
				} else if (this.chequeBtn.isSelected()) {
					pcAccount = new BankPCAccount();
					currentBalance = impl.getCurrentAccountBalance(pcAccount.getClass());
					for(IPCAccount pca : santha.getBankPCAccounts()){
						currentBalance += pca.getAmount();
					}
					santha.getBankPCAccounts().add(pcAccount);
					// ledger.getBankPCAccounts().add((BankPCAccount)pcAccount);
					addChequetoBankPCAcc(pcAccount);
				}
				
				float balance = currentBalance + poorHelp;
				pcAccount.setAmount(poorHelp);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Santha - Poor Help");
				pcAccount.setSantha(santha);
				pcAccount.setDate(paidForDate.getSelectedDate());
				pcAccount.setBalance(balance);
				((Account) pcAccount).setLedger(ledger);
			}

			if (bagOffer != 0.0f) {
				ledger = ledgerMap.get("Santha - Bag Offer");
				float currentBalance = 0.0f;
				if (this.cash.isSelected()) {
					pcAccount = new PCAccount();
					currentBalance = impl.getCurrentAccountBalance(pcAccount.getClass());
					for(IPCAccount pca : santha.getPcAccounts()){
						currentBalance += pca.getAmount();
					}
					santha.getPcAccounts().add(pcAccount);
				} else if (this.chequeBtn.isSelected()) {
					pcAccount = new BankPCAccount();
					currentBalance = impl.getCurrentAccountBalance(pcAccount.getClass());
					for(IPCAccount pca : santha.getBankPCAccounts()){
						currentBalance += pca.getAmount();
					}
					santha.getBankPCAccounts().add(pcAccount);
					// ledger.getBankPCAccounts().add((BankPCAccount)pcAccount);
					addChequetoBankPCAcc(pcAccount);
				}
				
				float balance = currentBalance + bagOffer;
				pcAccount.setAmount(bagOffer);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Santha - Bag Offer");
				pcAccount.setSantha(santha);
				pcAccount.setDate(paidForDate.getSelectedDate());
				pcAccount.setBalance(balance);
				((Account) pcAccount).setLedger(ledger);
			}

			ISpecialThanksOfferingAccount stoAccount = null;
			if (thanksOffer != 0.0f) {
				ledger = ledgerMap.get("Santha - Thanks Offering");
				float currentBalance = 0.0f;
				if (this.cash.isSelected()) {
					stoAccount = new BuildingAccount();
					currentBalance = impl.getCurrentAccountBalance(stoAccount.getClass());
					for(ISpecialThanksOfferingAccount ba : santha.getSpecialThanksOfferingAccounts()){
						currentBalance += ba.getAmount();
					}
					santha.getSpecialThanksOfferingAccounts().add(stoAccount);
				} else if (this.chequeBtn.isSelected()) {
					stoAccount = new BankBuildingAccount();
					currentBalance = impl.getCurrentAccountBalance(stoAccount.getClass());
					for(ISpecialThanksOfferingAccount ba : santha.getBankSpecialThanksOfferingAccounts()){
						currentBalance += ba.getAmount();
					}
					santha.getBankSpecialThanksOfferingAccounts().add(
							stoAccount);
					addChequeToStoAcc(stoAccount);
				}
				 
				float balance = currentBalance + thanksOffer;
				stoAccount.setAmount(thanksOffer);
				stoAccount.setCr_dr("CR");
				stoAccount.setDescription("Santha - Thanks Offering");
				stoAccount.setSantha(santha);
				stoAccount.setDate(paidForDate.getSelectedDate());
				stoAccount.setBalance(balance);
				((Account) stoAccount).setLedger(ledger);
			}

			if (sto != 0.0f) {
				ledger = ledgerMap.get("Santha - Special Thanks Offering");
				float currentBalance = 0.0f;
				if (this.cash.isSelected()) {
					stoAccount = new BuildingAccount();
					currentBalance = impl.getCurrentAccountBalance(stoAccount.getClass());
					for(ISpecialThanksOfferingAccount ba : santha.getSpecialThanksOfferingAccounts()){
						currentBalance += ba.getAmount();
					}
					santha.getSpecialThanksOfferingAccounts().add(stoAccount);
				} else if (this.chequeBtn.isSelected()) {
					stoAccount = new BankBuildingAccount();
					currentBalance = impl.getCurrentAccountBalance(stoAccount.getClass());
					for(ISpecialThanksOfferingAccount ba : santha.getBankSpecialThanksOfferingAccounts()){
						currentBalance += ba.getAmount();
					}
					santha.getBankSpecialThanksOfferingAccounts().add(
							stoAccount);
					addChequeToStoAcc(stoAccount);
				}
				
				float balance = currentBalance + sto;
				stoAccount.setAmount(sto);
				stoAccount.setCr_dr("CR");
				stoAccount.setDescription("Santha - Special Thanks Offering");
				stoAccount.setSantha(santha);
				stoAccount.setDate(paidForDate.getSelectedDate());
				stoAccount.setBalance(balance);
				((Account) stoAccount).setLedger(ledger);
			}

			if (churchRenovation != 0.0f) {
				ledger = ledgerMap.get("Santha - Church Renovation");
				float currentBalance = 0.0f;
				if (this.cash.isSelected()) {
					stoAccount = new BuildingAccount();
					currentBalance = impl.getCurrentAccountBalance(stoAccount.getClass());
					for(ISpecialThanksOfferingAccount ba : santha.getSpecialThanksOfferingAccounts()){
						currentBalance += ba.getAmount();
					}
					santha.getSpecialThanksOfferingAccounts().add(stoAccount);
				} else if (this.chequeBtn.isSelected()) {
					stoAccount = new BankBuildingAccount();
					currentBalance = impl.getCurrentAccountBalance(stoAccount.getClass());
					for(ISpecialThanksOfferingAccount ba : santha.getBankSpecialThanksOfferingAccounts()){
						currentBalance += ba.getAmount();
					}
					santha.getBankSpecialThanksOfferingAccounts().add(
							stoAccount);
					addChequeToStoAcc(stoAccount);
				}
				
				float balance = currentBalance + churchRenovation;
				stoAccount.setAmount(churchRenovation);
				stoAccount.setCr_dr("CR");
				stoAccount.setDescription("Santha - Church Renovation");
				stoAccount.setSantha(santha);
				stoAccount.setDate(paidForDate.getSelectedDate());
				stoAccount.setBalance(balance);
				((Account) stoAccount).setLedger(ledger);
			}

			IMissionaryAccount missionaryAccount = null;
			if (missionary != 0.0f) {
				ledger = ledgerMap.get("Santha - Missionary Offering");
				if (this.cash.isSelected()) {
					missionaryAccount = new MissionaryAccount();
					santha.getMissionaryAccounts().add(missionaryAccount);
					// ledger.getMissionaryAccounts().add((MissionaryAccount)missionaryAccount);
				} else if (this.chequeBtn.isSelected()) {
					missionaryAccount = new BankMissionaryAccount();
					santha.getBankMissionaryAccounts().add(missionaryAccount);
					// ledger.getBankMissionaryAccounts().add((BankMissionaryAccount)missionaryAccount);
					BankMissionaryAccount bankMissionaryAcc = (BankMissionaryAccount) missionaryAccount;
					bankMissionaryAcc.getCheques().add(cheque);
					cheque.getBankMissionaryAccounts().add(bankMissionaryAcc);
				}
				float currentBalance = impl.getCurrentAccountBalance(missionaryAccount.getClass());
				float balance = currentBalance + missionary;
				missionaryAccount.setAmount(missionary);
				missionaryAccount.setCr_dr("CR");
				missionaryAccount
						.setDescription("Santha - Missionary Offering");
				missionaryAccount.setSantha(santha);
				missionaryAccount.setDate(paidForDate.getSelectedDate());
				missionaryAccount.setBalance(balance);
				((Account) missionaryAccount).setLedger(ledger);
			}

			IMensAccount mensAccount = null;
			if (mensFellowship != 0.0f) {
				ledger = ledgerMap.get("Santha - Men's Fellowship");
				if (this.cash.isSelected()) {
					mensAccount = new MensAccount();
					santha.getMensAccounts().add(mensAccount);
					// ledger.getMensAccounts().add((MensAccount)mensAccount);
				} else if (this.chequeBtn.isSelected()) {
					mensAccount = new BankMensAccount();
					santha.getBankMensAccounts().add(mensAccount);
					// ledger.getBankMensAccounts().add((BankMensAccount)mensAccount);
					BankMensAccount bankMensAcc = (BankMensAccount) mensAccount;
					bankMensAcc.getCheques().add(cheque);
					cheque.getBankMensAccounts().add(bankMensAcc);
				}
				float currentBalance = impl.getCurrentAccountBalance(mensAccount.getClass());
				float balance = currentBalance + mensFellowship;
				mensAccount.setAmount(mensFellowship);
				mensAccount.setCr_dr("CR");
				mensAccount.setDescription("Santha - Men's Fellowship");
				mensAccount.setSantha(santha);
				mensAccount.setDate(paidForDate.getSelectedDate());
				mensAccount.setBalance(balance);
				((Account) mensAccount).setLedger(ledger);
			}

			IWomensAccount womensAccount = null;
			if (womensFellowship != 0.0f) {
				ledger = ledgerMap.get("Santha - Women's Fellowship");
				float currentBalance = 0.0f;
				if (this.cash.isSelected()) {
					womensAccount = new WomensAccount();
					currentBalance = impl.getCurrentAccountBalance(womensAccount.getClass());
					for(IWomensAccount wa : santha.getWomensAccounts()){
						currentBalance += wa.getAmount();
					}
					santha.getWomensAccounts().add(womensAccount);
				} else if (this.chequeBtn.isSelected()) {
					womensAccount = new BankWomensAccount();
					currentBalance = impl.getCurrentAccountBalance(womensAccount.getClass());
					for(IWomensAccount wa : santha.getBankWomensAccounts()){
						currentBalance += wa.getAmount();
					}
					santha.getBankWomensAccounts().add(womensAccount);
					BankWomensAccount bankWomensAcc = (BankWomensAccount) womensAccount;
					bankWomensAcc.getCheques().add(cheque);
					cheque.getBankWomensAccounts().add(bankWomensAcc);
				}

				float balance = currentBalance + womensFellowship;
				womensAccount.setAmount(womensFellowship);
				womensAccount.setCr_dr("CR");
				womensAccount.setDescription("Santha - Women's Fellowship");
				womensAccount.setSantha(santha);
				womensAccount.setDate(paidForDate.getSelectedDate());
				womensAccount.setBalance(balance);
				((Account) womensAccount).setLedger(ledger);
			}

			if (preSchool != 0.0f) {
				ledger = ledgerMap.get("Santha - Pre School");
				float currentBalance = 0.0f;
				if (this.cash.isSelected()) {
					womensAccount = new WomensAccount();
					currentBalance = impl.getCurrentAccountBalance(womensAccount.getClass());
					for(IWomensAccount wa : santha.getWomensAccounts()){
						currentBalance += wa.getAmount();
					}
					santha.getWomensAccounts().add(womensAccount);
				} else if (this.chequeBtn.isSelected()) {
					womensAccount = new BankWomensAccount();
					currentBalance = impl.getCurrentAccountBalance(womensAccount.getClass());
					for(IWomensAccount wa : santha.getBankWomensAccounts()){
						currentBalance += wa.getAmount();
					}
					santha.getBankWomensAccounts().add(womensAccount);
					BankWomensAccount bankWomensAcc = (BankWomensAccount) womensAccount;
					bankWomensAcc.getCheques().add(cheque);
					cheque.getBankWomensAccounts().add(bankWomensAcc);
				}
				
				float balance = currentBalance + preSchool;
				womensAccount.setAmount(preSchool);
				womensAccount.setCr_dr("CR");
				womensAccount.setDescription("Santha - Pre School");
				womensAccount.setSantha(santha);
				womensAccount.setDate(paidForDate.getSelectedDate());
				womensAccount.setBalance(balance);
				((Account) womensAccount).setLedger(ledger);
			}

			IYouthAccount youthAccount = null;
			if (youth != 0.0f) {
				ledger = ledgerMap.get("Santha - Youth");
				if (this.cash.isSelected()) {
					youthAccount = new YouthAccount();
					santha.getYouthAccounts().add(youthAccount);
					// ledger.getYouthAccounts().add((YouthAccount)youthAccount);
				} else if (this.chequeBtn.isSelected()) {
					youthAccount = new BankYouthAccount();
					santha.getBankYouthAccounts().add(youthAccount);
					// ledger.getBankYouthAccounts().add((BankYouthAccount)youthAccount);
					BankYouthAccount bankYouthAccount = (BankYouthAccount) youthAccount;
					bankYouthAccount.getCheques().add(cheque);
					cheque.getBankYouthAccounts().add(bankYouthAccount);
				}
				float currentBalance = impl.getCurrentAccountBalance(youthAccount.getClass());
				float balance = currentBalance + youth;
				youthAccount.setAmount(youth);
				youthAccount.setCr_dr("CR");
				youthAccount.setDescription("Santha - Youth");
				youthAccount.setSantha(santha);
				youthAccount.setDate(paidForDate.getSelectedDate());
				youthAccount.setBalance(balance);
				((Account) youthAccount).setLedger(ledger);
			}

			IGraveyardAccount graveyardAccount = null;
			if (graveyard != 0.0f) {
				ledger = ledgerMap.get("Santha - Graveyard");
				if (this.cash.isSelected()) {
					graveyardAccount = new GraveyardAccount();
					santha.getGraveyardAccounts().add(graveyardAccount);
					// ledger.getGraveyardAccounts().add((GraveyardAccount)graveyardAccount);
				} else if (this.chequeBtn.isSelected()) {
					graveyardAccount = new BankGraveyardAccount();
					santha.getBankGraveyardAccounts().add(graveyardAccount);
					// ledger.getBankGraveyardAccounts().add((BankGraveyardAccount)graveyardAccount);
					BankGraveyardAccount bankGraveyardAcc = (BankGraveyardAccount) graveyardAccount;
					bankGraveyardAcc.getCheques().add(cheque);
					cheque.getBankGraveyardAccounts().add(bankGraveyardAcc);
				}
				float currentBalance = impl.getCurrentAccountBalance(graveyardAccount.getClass());
				float balance = currentBalance + graveyard;
				graveyardAccount.setAmount(graveyard);
				graveyardAccount.setCr_dr("CR");
				graveyardAccount.setDescription("Santha - Graveyard");
				graveyardAccount.setSantha(santha);
				graveyardAccount.setDate(paidForDate.getSelectedDate());
				graveyardAccount.setBalance(balance);
				((Account) graveyardAccount).setLedger(ledger);
			}

			// Inserting into DB
			SanthaDao santhaDaoImpl = new SanthaDaoImpl();
			int key = santhaDaoImpl.paySantha(santha, session);

			// Adding Success Message
			message.setText("Payment added successfully !!!");
			message.setTextFill(Paint.valueOf("GREEN"));
			logger.info("Payment added successfully for"
					+ santha.getMember().getId());

			// Adding data to the table.
			com.ccf.vo.Santha santhaPayment = new com.ccf.vo.Santha();
			santhaPayment.setSanthaId(key);
			santhaPayment.setName(familyMembers.getValue());
			santhaPayment.setSubscription(subscriptionAmount);
			santhaPayment.setBagOffer(bagOffer);
			santhaPayment.setChurchRenovation(churchRenovation);
			santhaPayment.setEducationHelp(educationHelp);
			santhaPayment.setGraveyard(graveyard);
			santhaPayment.setHarvestFestival(harvestFestival);
			santhaPayment.setMensFellowship(mensFellowship);
			santhaPayment.setMissionary(missionary);
			santhaPayment.setPoorHelp(poorHelp);
			santhaPayment.setPreSchool(preSchool);
			santhaPayment.setSto(sto);
			santhaPayment.setThanksOffer(thanksOffer);
			santhaPayment.setWomensFellowship(womensFellowship);
			santhaPayment.setYouth(youth);
			santhaPayment.setTotal(memberTotal);

			membersSantha.getItems().add(santhaPayment);

			validatePaidMembers();
			
			/*
			 * Running Thread to update the balances
			 */
			BalanceUpdator balanceUpdator = BalanceUpdator.getInstance();
			balanceUpdator.updateAllBalances();

		} catch (NumberFormatException e) {
			message.setText("Some Input is wrong, Enter Only numbers...");
			message.setTextFill(Paint.valueOf("RED"));
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (CcfException e) {
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			message.setTextFill(Paint.valueOf("RED"));
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			message.setText("Something went wrong..please contact admin");
			message.setTextFill(Paint.valueOf("RED"));
		}
		session.close();
		logger.debug("Save Family method Ends...");
	}

	private void clearData() {
		logger.debug("Cleardata method Starts...");
		this.bagOffer.setText("");
		this.churchRenovation.setText("");
		this.educationHelp.setText("");
		this.graveyard.setText("");
		this.harvestFestival.setText("");
		this.mensFellowship.setText("");
		this.missionary.setText("");
		this.subscriptionAmt.setText("");
		this.poorHelp.setText("");
		this.preSchool.setText("");
		this.sto.setText("");
		this.thanksOffer.setText("");
		this.womensFellowship.setText("");
		this.youth.setText("");
		this.memberTotal.setText("");
		logger.debug("Cleardata method Ends...");
	}

	private void disableAll() {
		logger.debug("disable All method Starts...");
		this.bagOffer.setDisable(true);
		this.churchRenovation.setDisable(true);
		this.educationHelp.setDisable(true);
		this.graveyard.setDisable(true);
		this.harvestFestival.setDisable(true);
		this.mensFellowship.setDisable(true);
		this.missionary.setDisable(true);
		this.subscriptionAmt.setDisable(true);
		this.poorHelp.setDisable(true);
		this.preSchool.setDisable(true);
		this.sto.setDisable(true);
		this.thanksOffer.setDisable(true);
		this.womensFellowship.setDisable(true);
		this.youth.setDisable(true);
		this.saveButton.setDisable(true);
		this.print.setVisible(true);
		logger.debug("disable All method Ends...");
	}

	private void enableAll() {
		logger.debug("Enable All method Starts...");
		this.bagOffer.setDisable(false);
		this.churchRenovation.setDisable(false);
		this.educationHelp.setDisable(false);
		this.graveyard.setDisable(false);
		this.harvestFestival.setDisable(false);
		this.mensFellowship.setDisable(false);
		this.missionary.setDisable(false);
		this.subscriptionAmt.setDisable(false);
		this.poorHelp.setDisable(false);
		this.preSchool.setDisable(false);
		this.sto.setDisable(false);
		this.thanksOffer.setDisable(false);
		this.womensFellowship.setDisable(false);
		this.youth.setDisable(false);
		this.saveButton.setDisable(false);
		this.print.setVisible(false);
		logger.debug("Enable All method Ends...");
	}

	private void validatePaidMembers() {
		logger.debug("Validate paid Member method Starts...");
		List<String> paidMemberNames = new ArrayList<String>();
		this.total.setText("0.00");
		for (com.ccf.vo.Santha santha : membersSantha.getItems()) {
			paidMemberNames.add(santha.getName());
			this.total.setText(String.valueOf(Float.parseFloat(this.total
					.getText()) + santha.getTotal()));
		}
		if (paidMemberNames.size() == familyMembers.getItems().size()) {
			disableAll();
			clearData();
			familyMemberError.setText("All members of this family paid.");
		} else {
			for (String familyMember : familyMembers.getItems()) {
				if (!paidMemberNames.contains(familyMember)) {
					enableAll();
					familyMembers.setValue(familyMember); // This will trigger
															// the
															// change listener
															// method. This will
															// not trigger in
															// the
															// first time since
															// we are setting
															// the same value
															// (means we are not
															// changing the
															// value).

					familyMemberError.setText("");
					break;
				}
			}
		}
		logger.debug("Validate paid Member method Ends...");
	}

	private void loadPaidMembers() {
		logger.debug("Load paid members Starts...");
		try {
			membersSantha.getItems().clear();
			if (paidDate.getSelectedDate() == null) {
				throw new CcfException("Select the Date.");
			}
			if (paidForDate.getSelectedDate() == null) {
				throw new CcfException("Select the Date.");
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(paidForDate.getSelectedDate());
			int days = cal.getActualMaximum(cal.DAY_OF_MONTH);

			Date fromDate = cal.getTime();
			fromDate.setDate(1);

			Date toDate = cal.getTime();
			toDate.setDate(days);

			SanthaDao santhaDaoImpl = new SanthaDaoImpl();
			MemberDao memberDaoImpl = new MemberDaoImpl();

			List<Santha> membersPayment = santhaDaoImpl.getPaidMembers(
					Integer.parseInt(familyNos.getEditor().getText()),
					fromDate, toDate);

			com.ccf.vo.Santha paidMember = null;
			total.setText("0.00");
			for (Santha santha : membersPayment) {
				paidMember = new com.ccf.vo.Santha();
				paidMember.setSanthaId(santha.getSanthaId());

				/*
				 * com.ccf.persistence.classes.Member member = memberDaoImpl
				 * .getMember(santha.getMember().getId());
				 */
				paidMember.setName(santha.getMember().getName());
				paidMember.setSubscription(santha.getSubscriptionAmount());
				paidMember.setBagOffer(santha.getBagOffer());
				paidMember.setChurchRenovation(santha.getChurchRenovation());
				paidMember.setEducationHelp(santha.getEducationHelp());
				paidMember.setGraveyard(santha.getGraveyard());
				paidMember.setHarvestFestival(santha.getHarvestFestival());
				paidMember.setMensFellowship(santha.getMensFellowship());
				paidMember.setMissionary(santha.getMissionary());
				paidMember.setPoorHelp(santha.getPoorHelp());
				paidMember.setPreSchool(santha.getPreSchool());
				paidMember.setSto(santha.getSto());
				paidMember.setThanksOffer(santha.getThanksOffer());

				paidMember.setWomensFellowship(santha.getWomensFellowship());
				paidMember.setYouth(santha.getYouth());
				paidMember.setTotal(santha.getTotal());

				membersSantha.getItems().add(paidMember);

				/*
				 * total.setText(String.valueOf(Float.parseFloat(total.getText())
				 * + santha.getTotal()));
				 */

			}
			validatePaidMembers();

		} catch (NumberFormatException e) {
			this.error.setText("Enter only number");
			this.error.setTextFill(Paint.valueOf("Red"));
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (CcfException e) {
			logger.error(e.getMessage());
			if (paidDate.getSelectedDate() == null)
				paidDateError.setText(e.getMessage());
			else if (paidForDate.getSelectedDate() == null)
				paidForDateError.setText(e.getMessage());
			else{
				message.setTextFill(Paint.valueOf("Red"));
				message.setText(e.getMessage());
			}
			e.printStackTrace();
		} catch (Exception e) {
			message.setTextFill(Paint.valueOf("Red"));
			message.setText(e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.debug("Load paid members Ends...");
	}

	private void getPreviouslyPaidAmount() {
		logger.debug("getPreviouslyPaidAmount method Starts...");
		clearData();
		Date selectedDate = this.paidForDate.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		SanthaDao santhaDaoImpl = new SanthaDaoImpl();
		Santha santha = null;
		if (selectedDate != null) {
			cal.setTime(selectedDate);
			Date fromDate = null;
			Date toDate = null;
			int count = 0;
			do {
				try {
					if (count <= 12) {
						Date checkedDate = cal.getTime();
						cal.add(cal.MONTH, -1);
						int monthDays = cal.getActualMaximum(cal.DAY_OF_MONTH);
						fromDate = cal.getTime();
						fromDate.setDate(1);
						toDate = cal.getTime();
						toDate.setDate(monthDays);
						count++;
					} else {
						break;
					}
					santha = santhaDaoImpl.getLastPaidAmount(
							Integer.parseInt(familyNos.getEditor().getText()),
							familyMembers.getValue(), fromDate, toDate);

					if (santha != null) {
						this.bagOffer.setText(String.valueOf(santha
								.getBagOffer()));
						this.churchRenovation.setText(String.valueOf(santha
								.getChurchRenovation()));
						this.educationHelp.setText(String.valueOf(santha
								.getEducationHelp()));
						this.graveyard.setText(String.valueOf(santha
								.getGraveyard()));
						this.harvestFestival.setText(String.valueOf(santha
								.getHarvestFestival()));
						this.mensFellowship.setText(String.valueOf(santha
								.getMensFellowship()));
						this.missionary.setText(String.valueOf(santha
								.getMissionary()));

						this.poorHelp.setText(String.valueOf(santha
								.getPoorHelp()));
						this.preSchool.setText(String.valueOf(santha
								.getPreSchool()));
						this.sto.setText(String.valueOf(santha.getSto()));
						this.thanksOffer.setText(String.valueOf(santha
								.getThanksOffer()));
						this.womensFellowship.setText(String.valueOf(santha
								.getWomensFellowship()));
						this.youth.setText(String.valueOf(santha.getYouth()));
						this.memberTotal.setText(String.valueOf(santha
								.getTotal()));
						this.subscriptionAmt.setText(String.valueOf(santha
								.getSubscriptionAmount()));

						this.saveButton.setVisible(true);
						this.updateButton.setVisible(false);
						this.cancelButton.setVisible(false);

						break;
					}

				} catch (NumberFormatException e) {
					this.error.setText("Enter only Number");
					this.error.setTextFill(Paint.valueOf("Red"));
					logger.error(e.getMessage());
					e.printStackTrace();
				} catch (CcfException e) {
					message.setTextFill(Paint.valueOf("Red"));
					message.setText(e.getMessage());
					logger.error(e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					message.setTextFill(Paint.valueOf("Red"));
					message.setText(e.getMessage());
					e.printStackTrace();
					logger.error(e.getMessage());
				}

			} while (santha == null);
		}
		logger.info("getPreviouslyPaidAmount method Ends...");
	}

	private float calculateTotal() {
		logger.info("calculateTotal method Starts...");
		if (bagOffer.getText().equals(""))
			bagOffer.setText("0");
		if (churchRenovation.getText().equals(""))
			churchRenovation.setText("0");
		if (educationHelp.getText().equals(""))
			educationHelp.setText("0");
		if (graveyard.getText().equals(""))
			graveyard.setText("0");
		if (harvestFestival.getText().equals(""))
			harvestFestival.setText("0");
		if (mensFellowship.getText().equals(""))
			mensFellowship.setText("0");
		if (missionary.getText().equals(""))
			missionary.setText("0");
		if (subscriptionAmt.getText().equals(""))
			subscriptionAmt.setText("0");
		if (poorHelp.getText().equals(""))
			poorHelp.setText("0");
		if (preSchool.getText().equals(""))
			preSchool.setText("0");
		if (sto.getText().equals(""))
			sto.setText("0");
		if (thanksOffer.getText().equals(""))
			thanksOffer.setText("0");
		if (womensFellowship.getText().equals(""))
			womensFellowship.setText("0");
		if (youth.getText().equals(""))
			youth.setText("0");

		float bagOffer = Float.parseFloat(this.bagOffer.getText());
		float churchRenovation = Float.parseFloat(this.churchRenovation
				.getText());
		float educationHelp = Float.parseFloat(this.educationHelp.getText());
		float graveyard = Float.parseFloat(this.graveyard.getText());
		float harvestFestival = Float
				.parseFloat(this.harvestFestival.getText());
		float mensFellowship = Float.parseFloat(this.mensFellowship.getText());
		float missionary = Float.parseFloat(this.missionary.getText());
		float poorHelp = Float.parseFloat(this.poorHelp.getText());
		float primarySchool = Float.parseFloat(this.preSchool.getText());
		float sto = Float.parseFloat(this.sto.getText());
		float thanksOffer = Float.parseFloat(this.thanksOffer.getText());
		float womensFellowship = Float.parseFloat(this.womensFellowship
				.getText());
		float youth = Float.parseFloat(this.youth.getText());

		float total = bagOffer + churchRenovation + educationHelp + graveyard
				+ harvestFestival + mensFellowship + missionary + poorHelp
				+ primarySchool + sto + thanksOffer + womensFellowship + youth
				+ Float.valueOf(subscriptionAmt.getText());

		logger.info("calculateTotal method Ends...");
		return total;
	}

	public void editPaidMember() {
		logger.info("Edit Paid Member method Starts...");
		// clearData();
		// enableAll();
		TableViewSelectionModel<com.ccf.vo.Santha> selectionModel = membersSantha
				.getSelectionModel();
		memberSantha = selectionModel.getSelectedItem();
		
		membersSantha.getItems().remove(memberSantha);
		
		this.familyMembers.setValue(memberSantha.getName());
		this.bagOffer.setText(String.valueOf(memberSantha.getBagOffer()));
		this.churchRenovation.setText(String.valueOf(memberSantha
				.getChurchRenovation()));
		this.educationHelp.setText(String.valueOf(memberSantha
				.getEducationHelp()));
		this.graveyard.setText(String.valueOf(memberSantha.getGraveyard()));
		this.harvestFestival.setText(String.valueOf(memberSantha
				.getHarvestFestival()));
		this.mensFellowship.setText(String.valueOf(memberSantha
				.getMensFellowship()));
		this.missionary.setText(String.valueOf(memberSantha.getMissionary()));
		this.subscriptionAmt.setText(String.valueOf(memberSantha
				.getSubscription()));
		this.poorHelp.setText(String.valueOf(memberSantha.getPoorHelp()));
		this.preSchool.setText(String.valueOf(memberSantha
				.getPreSchool()));
		this.sto.setText(String.valueOf(memberSantha.getSto()));
		this.thanksOffer.setText(String.valueOf(memberSantha.getThanksOffer()));
		this.womensFellowship.setText(String.valueOf(memberSantha
				.getWomensFellowship()));
		this.youth.setText(String.valueOf(memberSantha.getYouth()));
		

		this.saveButton.setVisible(false);
		this.updateButton.setVisible(true);
		this.cancelButton.setVisible(true);

		SanthaDao santhaImpl = new SanthaDaoImpl();
		try {
			Cheque cheque = santhaImpl.getCheque(memberSantha.getSanthaId());
			if (cheque == null) {
				onCashButtonPressed();
			} else {
				onChequeButtonPressed();
				this.chequeNumber.setText(cheque.getChequeNumber());
				this.chequeDate.setSelectedDate(cheque.getChequeDate());
			}

			message.setTextFill(Paint.valueOf("Green"));
			message.setText("");
		} catch (CcfException e) {
			e.printStackTrace();
			message.setTextFill(Paint.valueOf("Red"));
			message.setText(e.getMessage());
		}

		/*
		 * Calculating total
		 */
		this.total.setText("0");
		for(com.ccf.vo.Santha santha :  membersSantha.getItems()){
			this.total.setText(String.valueOf(Float.parseFloat(this.total
					.getText()) + santha.getTotal()));
		}
		enableAll();
		this.memberTotal.setText(String.valueOf(memberSantha.getTotal()));
		this.membersSantha.setSelectionModel(selectionModel);
		logger.info("Edit Paid Member method Ends...");
	}

	public void updatePayment() {
		logger.info("Update Payment method Starts...");
		try {
			SanthaDao santhaDaoImpl = new SanthaDaoImpl();
			/*
			 * Deleting from Database.
			 */
			santhaDaoImpl.deleteSantha(this.memberSantha
					.getSanthaId());

			// Removing From UI
			membersSantha.getItems().remove(this.memberSantha);

			save();

			this.saveButton.setVisible(true);
			this.updateButton.setVisible(false);
			this.cancelButton.setVisible(false);

			message.setTextFill(Paint.valueOf("Green"));
			this.message.setText("Payment for " + familyMembers.getValue()
					+ " updated Successfully.");
			logger.info("Payment for " + familyMembers.getValue()
					+ " updated Successfully.");
		} catch (CcfException e) {
			e.printStackTrace();
			message.setTextFill(Paint.valueOf("Red"));
			message.setText(e.getMessage());
		}

		logger.info("Update Payment method Ends...");
	}

	public void cancelEditSantha() {
		logger.debug("cancelEditSantha method Starts...");
		membersSantha.getItems().add(memberSantha);
		validatePaidMembers();
		this.saveButton.setVisible(true);
		this.updateButton.setVisible(false);
		this.cancelButton.setVisible(false);
		logger.debug("cancelEditSantha method Ends...");
	}

	public void deletePaidMember() {
		logger.debug("Delete Paid Member method Starts...");
		com.ccf.vo.Santha santha = membersSantha.getSelectionModel()
				.getSelectedItem();

		try {
			SanthaDao santhaDaoImpl = new SanthaDaoImpl();
			/*
			 * Deleting from Database.
			 */
			santhaDaoImpl.deleteSantha(santha.getSanthaId());
			
			// Removing From UI
			membersSantha.getItems().remove(santha);

			// Adding message to the UI
			this.message.setTextFill(Paint.valueOf("GREEN"));
			this.message.setText("Santha Payment of " + santha.getName()
					+ " has been deleted.");
			logger.info("Santha Payment of " + santha.getName()
					+ " has been deleted.");

			validatePaidMembers();
			
			/*
			 * Running Thread to update the balances
			 */
			BalanceUpdator balanceUpdator = BalanceUpdator.getInstance();
			balanceUpdator.updateAllBalances();

		} catch (CcfException e) {
			message.setTextFill(Paint.valueOf("Red"));
			message.setText(e.getMessage());
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			message.setTextFill(Paint.valueOf("Red"));
			message.setText(e.getMessage());
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.debug("Delete Paid Member method Ends...");
	}

	public void onCashButtonPressed() {
		this.cash.setSelected(true);
		this.chequeBtn.setSelected(false);
		this.chequeDetails.setVisible(false);
	}

	public void onChequeButtonPressed() {
		this.cash.setSelected(false);
		this.chequeBtn.setSelected(true);
		this.chequeDetails.setVisible(true);
	}

	private void addChequetoBankPCAcc(IPCAccount pcAccount) {
		BankPCAccount bankPCAcc = (BankPCAccount) pcAccount;
		bankPCAcc.getCheques().add(cheque);
		cheque.getBankPCAccounts().add(bankPCAcc);
	}

	private void addChequeToStoAcc(ISpecialThanksOfferingAccount ISTOAcc) {
		BankBuildingAccount bankSTOAcc = (BankBuildingAccount) ISTOAcc;
		bankSTOAcc.getCheques().add(cheque);
		cheque.getBankSTOAccounts().add(bankSTOAcc);
	}
	
	private Cheque createChequeObject(float memberTotal, Session session) throws CcfException{
		SanthaDao santhaDao = new SanthaDaoImpl();
		cheque = santhaDao.getCheque(this.chequeNumber.getText(), session);
		if(cheque == null){
			cheque = new Cheque();
			cheque.setNew(true);
			cheque.setChequeNumber(this.chequeNumber.getText());
			cheque.setChequeDate(this.chequeDate.getSelectedDate());
			cheque.setChequeAmount(memberTotal);
		} else {
			cheque.setNew(false);
			if(cheque.getChequeDate().equals(this.chequeDate.getSelectedDate())){
				cheque.setChequeAmount(cheque.getChequeAmount() + memberTotal);
			} else {
				this.message.setTextFill(Paint.valueOf("Red"));
				this.message.setText("Same Cheque is available with different date.");
			}
		}
		
		return cheque;
	}
	public void print(){
		logger.debug("Print method Starts...");
		this.message.setText("Functionality under construction");
		this.message.setTextFill(Paint.valueOf("Red"));
		logger.debug("Print method Ends...");
	}
}
