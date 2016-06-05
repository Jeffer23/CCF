package com.ccf.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ccf.dao.impl.AccountsDaoImpl;
import com.ccf.dao.impl.FamilyDaoImpl;
import com.ccf.dao.impl.MemberDaoImpl;
import com.ccf.dao.impl.SanthaDaoImpl;
import com.ccf.doa.AccountsDao;
import com.ccf.doa.FamilyDao;
import com.ccf.doa.MemberDao;
import com.ccf.doa.SanthaDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.GraveyardAccount;
import com.ccf.persistence.classes.MensAccount;
import com.ccf.persistence.classes.MissionaryAccount;
import com.ccf.persistence.classes.PCAccount;
import com.ccf.persistence.classes.PrimarySchoolAccount;
import com.ccf.persistence.classes.Santha;
import com.ccf.persistence.classes.SpecialThanksOfferingAccount;
import com.ccf.persistence.classes.WomensAccount;
import com.ccf.persistence.classes.YouthAccount;
import com.ccf.util.HibernateSessionFactory;
import com.ccf.vo.Member;

import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class SanthaController extends Application {

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
	private TextField primarySchool;

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
	private TextField other1;

	@FXML
	private TextField other2;

	@FXML
	private TextField mensFellowship;

	@FXML
	private TextField womensFellowship;

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

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

	@FXML
	void initialize() {
		logger.debug("init method Starts...");
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
						if (familyNos.getEditor().getText()
								.matches(".*[a-zA-Z]+.*")) {
							familyMemberError.setText("Enter Only the number");
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
								loadPaidMembers();
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
						error.setText("Only numbers are allowed");
						logger.error(e.getMessage());
						e.printStackTrace();
					} catch (CcfException e) {
						logger.error(e.getMessage());
						e.printStackTrace();
					} catch (Exception e) {
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
		other1.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean onBlur, Boolean onFocus) {
				if (onBlur) {
					logger.debug("on Blur of other1 starts...");
					float total = calculateTotal();
					memberTotal.setText(String.valueOf(total));
					logger.debug("on Blur of other1 Ends...");
				}
			}
		});
		other2.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean onBlur, Boolean onFocus) {
				if (onBlur) {
					logger.debug("on Blur of other2 starts...");
					float total = calculateTotal();
					memberTotal.setText(String.valueOf(total));
					logger.debug("on Blur of other2 Ends...");
				}
			}
		});
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
		primarySchool.focusedProperty().addListener(
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
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
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
		logger.debug("Primary School : " + primarySchool.getText());
		logger.debug("Youth : " + youth.getText());
		logger.debug("Poor Help : " + poorHelp.getText());
		logger.debug("Graveyard : " + graveyard.getText());
		logger.debug("Church Renovation : " + churchRenovation.getText());
		logger.debug("Bag Offer : " + bagOffer.getText());
		logger.debug("Thanks Offer : " + thanksOffer.getText());
		logger.debug("Special Thanks Offer : " + sto.getText());
		logger.debug("Other 1 : " + other1.getText());
		logger.debug("Other 2 : " + other2.getText());

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
			if (primarySchool.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException(
						"Primary School amount cannot contain alphabets");
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
			if (other1.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException("Other1 amount cannot contain alphabets");
			}
			if (other2.getText().matches(".*[a-zA-Z]+.*")) {
				throw new CcfException("Other2 amount cannot contain alphabets");
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
			if (other1.getText().equals(""))
				other1.setText("0");
			if (other2.getText().equals(""))
				other2.setText("0");
			if (poorHelp.getText().equals(""))
				poorHelp.setText("0");
			if (primarySchool.getText().equals(""))
				primarySchool.setText("0");
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
			float other1 = Float.parseFloat(this.other1.getText());
			float other2 = Float.parseFloat(this.other2.getText());
			float poorHelp = Float.parseFloat(this.poorHelp.getText());
			float primarySchool = Float
					.parseFloat(this.primarySchool.getText());
			float sto = Float.parseFloat(this.sto.getText());
			float thanksOffer = Float.parseFloat(this.thanksOffer.getText());
			float womensFellowship = Float.parseFloat(this.womensFellowship
					.getText());
			float youth = Float.parseFloat(this.youth.getText());

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
			santha.setOther1(other1);
			santha.setOther2(other2);
			santha.setPoorHelp(poorHelp);
			santha.setPrimarySchool(primarySchool);
			santha.setSto(sto);
			santha.setThanksOffer(thanksOffer);
			santha.setWomensFellowship(womensFellowship);
			santha.setYouth(youth);
			float memberTotal = santha.getBagOffer()
					+ santha.getChurchRenovation() + santha.getEducationHelp()
					+ santha.getGraveyard() + santha.getHarvestFestival()
					+ santha.getMensFellowship() + santha.getMissionary()
					+ santha.getOther1() + santha.getOther2()
					+ santha.getPoorHelp() + santha.getPrimarySchool()
					+ santha.getSto() + santha.getThanksOffer()
					+ santha.getWomensFellowship() + santha.getYouth();
			santha.setTotal(memberTotal);

			AccountsDao impl = new AccountsDaoImpl();
			PCAccount pcAccount = null;
			if (harvestFestival != 0.0f) {
				pcAccount = new PCAccount();
				pcAccount.setAmount(harvestFestival);
				float currentBalance = impl.getPCAccountBalance();
				float balance = pcAccount.getAmount() + currentBalance;
				pcAccount.setBalance(balance);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Santha - Harvest Festival");
				pcAccount.setSantha(santha);
				pcAccount.setDate(paidDate.getSelectedDate());
				santha.getPcAccounts().add(pcAccount);
			}

			if (educationHelp != 0.0f) {
				pcAccount = new PCAccount();
				pcAccount.setAmount(educationHelp);
				float currentBalance = 0.0f;
				if (santha.getPcAccounts().size() == 0) {
					currentBalance = impl.getPCAccountBalance();
				} else {
					Iterator<PCAccount> pcAccounts = santha.getPcAccounts()
							.iterator();
					while (pcAccounts.hasNext()) {
						currentBalance = pcAccounts.next().getBalance();
					}
				}
				float balance = pcAccount.getAmount() + currentBalance;
				pcAccount.setBalance(balance);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Santha - Education Help");
				pcAccount.setSantha(santha);
				pcAccount.setDate(paidDate.getSelectedDate());
				santha.getPcAccounts().add(pcAccount);
			}

			if (poorHelp != 0.0f) {
				pcAccount = new PCAccount();
				pcAccount.setAmount(poorHelp);
				float currentBalance = 0.0f;
				if (santha.getPcAccounts().size() == 0) {
					currentBalance = impl.getPCAccountBalance();
				} else {
					Iterator<PCAccount> pcAccounts = santha.getPcAccounts()
							.iterator();
					while (pcAccounts.hasNext()) {
						currentBalance = pcAccounts.next().getBalance();
					}
				}
				float balance = pcAccount.getAmount() + currentBalance;
				pcAccount.setBalance(balance);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Santha - Poor Help");
				pcAccount.setSantha(santha);
				pcAccount.setDate(paidDate.getSelectedDate());
				santha.getPcAccounts().add(pcAccount);
			}

			if (bagOffer != 0.0f) {
				pcAccount = new PCAccount();
				pcAccount.setAmount(bagOffer);
				float currentBalance = 0.0f;
				if (santha.getPcAccounts().size() == 0) {
					currentBalance = impl.getPCAccountBalance();
				} else {
					Iterator<PCAccount> pcAccounts = santha.getPcAccounts()
							.iterator();
					while (pcAccounts.hasNext()) {
						currentBalance = pcAccounts.next().getBalance();
					}
				}
				float balance = pcAccount.getAmount() + currentBalance;
				pcAccount.setBalance(balance);
				pcAccount.setCr_dr("CR");
				pcAccount.setDescription("Santha - Bag Offer");
				pcAccount.setSantha(santha);
				pcAccount.setDate(paidDate.getSelectedDate());
				santha.getPcAccounts().add(pcAccount);
			}

			SpecialThanksOfferingAccount stoAccount = null;
			if (thanksOffer != 0.0f) {
				stoAccount = new SpecialThanksOfferingAccount();
				stoAccount.setAmount(thanksOffer);
				float currentBalance = impl
						.getSpecialThanksOfferingAccountBalance();
				float balance = stoAccount.getAmount() + currentBalance;
				stoAccount.setBalance(balance);
				stoAccount.setCr_dr("CR");
				stoAccount.setDescription("Santha - Thanks Offering");
				stoAccount.setSantha(santha);
				stoAccount.setDate(paidDate.getSelectedDate());
				santha.getSpecialThanksOfferingAccounts().add(stoAccount);
			}

			if (sto != 0.0f) {
				stoAccount = new SpecialThanksOfferingAccount();
				stoAccount.setAmount(sto);
				float currentBalance = 0.0f;
				if (santha.getSpecialThanksOfferingAccounts().size() == 0) {
					currentBalance = impl
							.getSpecialThanksOfferingAccountBalance();
				} else {
					Iterator<SpecialThanksOfferingAccount> stoAccounts = santha
							.getSpecialThanksOfferingAccounts().iterator();
					while (stoAccounts.hasNext()) {
						currentBalance = stoAccounts.next().getBalance();
					}
				}
				float balance = stoAccount.getAmount() + currentBalance;
				stoAccount.setBalance(balance);
				stoAccount.setCr_dr("CR");
				stoAccount.setDescription("Santha - Special Thanks Offering");
				stoAccount.setSantha(santha);
				stoAccount.setDate(paidDate.getSelectedDate());
				santha.getSpecialThanksOfferingAccounts().add(stoAccount);
			}

			if (churchRenovation != 0.0f) {
				stoAccount = new SpecialThanksOfferingAccount();
				stoAccount.setAmount(churchRenovation);
				float currentBalance = 0.0f;
				if (santha.getSpecialThanksOfferingAccounts().size() == 0) {
					currentBalance = impl
							.getSpecialThanksOfferingAccountBalance();
				} else {
					Iterator<SpecialThanksOfferingAccount> stoAccounts = santha
							.getSpecialThanksOfferingAccounts().iterator();
					while (stoAccounts.hasNext()) {
						currentBalance = stoAccounts.next().getBalance();
					}
				}
				float balance = stoAccount.getAmount() + currentBalance;
				stoAccount.setBalance(balance);
				stoAccount.setCr_dr("CR");
				stoAccount.setDescription("Santha - Church Renovation");
				stoAccount.setSantha(santha);
				stoAccount.setDate(paidDate.getSelectedDate());
				santha.getSpecialThanksOfferingAccounts().add(stoAccount);
			}

			MissionaryAccount missionaryAccount = null;
			if (missionary != 0.0f) {
				missionaryAccount = new MissionaryAccount();
				missionaryAccount.setAmount(missionary);
				float currentBalance = impl.getMissionaryAccountBalance();
				float balance = missionaryAccount.getAmount() + currentBalance;
				missionaryAccount.setBalance(balance);
				missionaryAccount.setCr_dr("CR");
				missionaryAccount
						.setDescription("Santha - Missionary Offering");
				missionaryAccount.setSantha(santha);
				missionaryAccount.setDate(paidDate.getSelectedDate());
				santha.getMissionaryAccounts().add(missionaryAccount);
			}

			MensAccount mensAccount = null;
			if (mensFellowship != 0.0f) {
				mensAccount = new MensAccount();
				mensAccount.setAmount(mensFellowship);
				float currentBalance = impl.getMensAccountBalance();
				float balance = mensAccount.getAmount() + currentBalance;
				mensAccount.setBalance(balance);
				mensAccount.setCr_dr("CR");
				mensAccount.setDescription("Santha - Men's Fellowship");
				mensAccount.setSantha(santha);
				mensAccount.setDate(paidDate.getSelectedDate());
				santha.getMensAccounts().add(mensAccount);
			}

			WomensAccount womensAccount = null;
			if (womensFellowship != 0.0f) {
				womensAccount = new WomensAccount();
				womensAccount.setAmount(womensFellowship);
				float currentBalance = impl.getWomensAccountBalance();
				float balance = womensAccount.getAmount() + currentBalance;
				womensAccount.setBalance(balance);
				womensAccount.setCr_dr("CR");
				womensAccount.setDescription("Santha - Women's Fellowship");
				womensAccount.setSantha(santha);
				womensAccount.setDate(paidDate.getSelectedDate());
				santha.getWomensAccounts().add(womensAccount);
			}

			PrimarySchoolAccount primarySchoolAccount = null;
			if (primarySchool != 0.0f) {
				primarySchoolAccount = new PrimarySchoolAccount();
				primarySchoolAccount.setAmount(primarySchool);
				float currentBalance = impl.getPrimarySchoolAccountBalance();
				float balance = primarySchoolAccount.getAmount()
						+ currentBalance;
				primarySchoolAccount.setBalance(balance);
				primarySchoolAccount.setCr_dr("CR");
				primarySchoolAccount.setDescription("Santha - Primary School");
				primarySchoolAccount.setSantha(santha);
				primarySchoolAccount.setDate(paidDate.getSelectedDate());
				santha.getPrimarySchoolAccounts().add(primarySchoolAccount);
			}

			YouthAccount youthAccount = null;
			if (youth != 0.0f) {
				youthAccount = new YouthAccount();
				youthAccount.setAmount(youth);
				float currentBalance = impl.getYouthAccountBalance();
				float balance = youthAccount.getAmount() + currentBalance;
				youthAccount.setBalance(balance);
				youthAccount.setCr_dr("CR");
				youthAccount.setDescription("Santha - Youth");
				youthAccount.setSantha(santha);
				youthAccount.setDate(paidDate.getSelectedDate());
				santha.getYouthAccounts().add(youthAccount);
			}

			GraveyardAccount graveyardAccount = null;
			if (graveyard != 0.0f) {
				graveyardAccount = new GraveyardAccount();
				graveyardAccount.setAmount(graveyard);
				float currentBalance = impl.getGraveyardAccountBalance();
				float balance = graveyardAccount.getAmount() + currentBalance;
				graveyardAccount.setBalance(balance);
				graveyardAccount.setCr_dr("CR");
				graveyardAccount.setDescription("Santha - Graveyard");
				graveyardAccount.setSantha(santha);
				graveyardAccount.setDate(paidDate.getSelectedDate());
				santha.getGraveyardAccounts().add(graveyardAccount);
			}

			// Inserting into DB
			SanthaDao santhaDaoImpl = new SanthaDaoImpl();
			int key = santhaDaoImpl.paySantha(santha);

			// Adding Success Message
			message.setText("Payment added successfully !!!");
			message.setTextFill(Paint.valueOf("GREEN"));
			logger.info("Payment added successfully for"
					+ santha.getMember().getId());

			// get subscription amount of this person
			float subscriptionAmount = memberDaoImpl.getSubscriptionAmount(
					Integer.parseInt(familyNos.getEditor().getText()),
					familyMembers.getValue());

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
			santhaPayment.setOther1(other1);
			santhaPayment.setOther2(other2);
			santhaPayment.setPoorHelp(poorHelp);
			santhaPayment.setPrimarySchool(primarySchool);
			santhaPayment.setSto(sto);
			santhaPayment.setThanksOffer(thanksOffer);
			santhaPayment.setWomensFellowship(womensFellowship);
			santhaPayment.setYouth(youth);
			santhaPayment.setTotal(memberTotal);

			membersSantha.getItems().add(santhaPayment);

			validatePaidMembers();

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
		this.other1.setText("");
		this.other2.setText("");
		this.poorHelp.setText("");
		this.primarySchool.setText("");
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
		this.other1.setDisable(true);
		this.other2.setDisable(true);
		this.poorHelp.setDisable(true);
		this.primarySchool.setDisable(true);
		this.sto.setDisable(true);
		this.thanksOffer.setDisable(true);
		this.womensFellowship.setDisable(true);
		this.youth.setDisable(true);
		this.saveButton.setDisable(true);
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
		this.other1.setDisable(false);
		this.other2.setDisable(false);
		this.poorHelp.setDisable(false);
		this.primarySchool.setDisable(false);
		this.sto.setDisable(false);
		this.thanksOffer.setDisable(false);
		this.womensFellowship.setDisable(false);
		this.youth.setDisable(false);
		this.saveButton.setDisable(false);
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(paidForDate.getSelectedDate());
			int days = cal.getActualMaximum(cal.DAY_OF_MONTH);

			Date fromDate = cal.getTime();
			fromDate.setDate(1);

			Date toDate = cal.getTime();
			toDate.setDate(days);

			SanthaDao santhaDaoImpl = new SanthaDaoImpl();
			MemberDao memberDaoImpl = new MemberDaoImpl();

			SessionFactory sessionFactory = HibernateSessionFactory
					.getSessionFactory();
			Session session = sessionFactory.openSession();

			List<Santha> membersPayment = santhaDaoImpl.getPaidMembers(
					Integer.parseInt(familyNos.getEditor().getText()),
					fromDate, toDate, session);

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
				paidMember.setSubscription(santha.getMember()
						.getSubscriptionAmount());
				paidMember.setBagOffer(santha.getBagOffer());
				paidMember.setChurchRenovation(santha.getChurchRenovation());
				paidMember.setEducationHelp(santha.getEducationHelp());
				paidMember.setGraveyard(santha.getGraveyard());
				paidMember.setHarvestFestival(santha.getHarvestFestival());
				paidMember.setMensFellowship(santha.getMensFellowship());
				paidMember.setMissionary(santha.getMissionary());

				paidMember.setOther1(santha.getOther1());
				paidMember.setOther2(santha.getOther2());
				paidMember.setPoorHelp(santha.getPoorHelp());
				paidMember.setPrimarySchool(santha.getPrimarySchool());
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
			session.close();
			validatePaidMembers();

		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (CcfException e) {
			logger.error(e.getMessage());
			if (paidDate.getSelectedDate() == null)
				paidDateError.setText(e.getMessage());
			else if (paidForDate.getSelectedDate() == null)
				paidForDateError.setText(e.getMessage());
			else
				e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.debug("Load paid members Ends...");
	}

	private void getPreviouslyPaidAmount() {
		logger.debug("getPreviouslyPaidAmount method Starts...");
		Date selectedDate = this.paidForDate.getSelectedDate();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
						this.other1.setText(String.valueOf(santha.getOther1()));
						this.other2.setText(String.valueOf(santha.getOther2()));
						this.poorHelp.setText(String.valueOf(santha
								.getPoorHelp()));
						this.primarySchool.setText(String.valueOf(santha
								.getPrimarySchool()));
						this.sto.setText(String.valueOf(santha.getSto()));
						this.thanksOffer.setText(String.valueOf(santha
								.getThanksOffer()));
						this.womensFellowship.setText(String.valueOf(santha
								.getWomensFellowship()));
						this.youth.setText(String.valueOf(santha.getYouth()));
						this.memberTotal.setText(String.valueOf(santha
								.getTotal()));

						this.saveButton.setVisible(true);
						this.updateButton.setVisible(false);
						this.cancelButton.setVisible(false);

						break;
					}

				} catch (NumberFormatException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				} catch (CcfException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
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
		if (other1.getText().equals(""))
			other1.setText("0");
		if (other2.getText().equals(""))
			other2.setText("0");
		if (poorHelp.getText().equals(""))
			poorHelp.setText("0");
		if (primarySchool.getText().equals(""))
			primarySchool.setText("0");
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
		float other1 = Float.parseFloat(this.other1.getText());
		float other2 = Float.parseFloat(this.other2.getText());
		float poorHelp = Float.parseFloat(this.poorHelp.getText());
		float primarySchool = Float.parseFloat(this.primarySchool.getText());
		float sto = Float.parseFloat(this.sto.getText());
		float thanksOffer = Float.parseFloat(this.thanksOffer.getText());
		float womensFellowship = Float.parseFloat(this.womensFellowship
				.getText());
		float youth = Float.parseFloat(this.youth.getText());

		float total = bagOffer + churchRenovation + educationHelp + graveyard
				+ harvestFestival + mensFellowship + missionary + other1
				+ other2 + poorHelp + primarySchool + sto + thanksOffer
				+ womensFellowship + youth;

		logger.info("calculateTotal method Ends...");
		return total;
	}

	public void editPaidMember() {
		logger.info("Edit Paid Member method Starts...");
		clearData();
		enableAll();
		com.ccf.vo.Santha santha = membersSantha.getSelectionModel()
				.getSelectedItem();
		this.bagOffer.setText(String.valueOf(santha.getBagOffer()));
		this.churchRenovation.setText(String.valueOf(santha
				.getChurchRenovation()));
		this.educationHelp.setText(String.valueOf(santha.getEducationHelp()));
		this.graveyard.setText(String.valueOf(santha.getGraveyard()));
		this.harvestFestival
				.setText(String.valueOf(santha.getHarvestFestival()));
		this.mensFellowship.setText(String.valueOf(santha.getMensFellowship()));
		this.missionary.setText(String.valueOf(santha.getMissionary()));
		this.other1.setText(String.valueOf(santha.getOther1()));
		this.other2.setText(String.valueOf(santha.getOther2()));
		this.poorHelp.setText(String.valueOf(santha.getPoorHelp()));
		this.primarySchool.setText(String.valueOf(santha.getPrimarySchool()));
		this.sto.setText(String.valueOf(santha.getSto()));
		this.thanksOffer.setText(String.valueOf(santha.getThanksOffer()));
		this.womensFellowship.setText(String.valueOf(santha
				.getWomensFellowship()));
		this.youth.setText(String.valueOf(santha.getYouth()));

		this.saveButton.setVisible(false);
		this.updateButton.setVisible(true);
		this.cancelButton.setVisible(true);
		logger.info("Edit Paid Member method Ends...");
	}

	public void updatePayment() {
		logger.info("Update Payment method Starts...");
		deletePaidMember();
		save();
		
		this.saveButton.setVisible(true);
		this.updateButton.setVisible(false);
		this.cancelButton.setVisible(false);
		
		
		this.message.setText("Payment for " + familyMembers.getValue()
				+ " updated Successfully.");
		logger.info("Payment for " + familyMembers.getValue()
				+ " updated Successfully.");
		logger.info("Update Payment method Ends...");
	}

	public void cancelEditSantha() {
		logger.debug("cancelEditSantha method Starts...");
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
			Santha santhaAmount = null;
			santhaAmount = santhaDaoImpl.getSantha(santha.getSanthaId());
			/*
			 * Deleting from Database.
			 */
			santhaDaoImpl.deleteSantha(santhaAmount);

			/*
			 * Calculating and Updating the balance of other affected records.
			 */
			AccountsDao accountsImpl = new AccountsDaoImpl();
			if (santhaAmount.getPcAccounts().size() > 0) {
				Iterator<PCAccount> pcAccIterator = santhaAmount
						.getPcAccounts().iterator();
				float pcAccDiff = 0.0f;
				int smallestId = 0;
				while (pcAccIterator.hasNext()) {
					PCAccount pcAccount = pcAccIterator.next();
					pcAccDiff = pcAccDiff + pcAccount.getAmount();
					if (smallestId == 0 || pcAccount.getId() < smallestId) {
						smallestId = pcAccount.getId();
					}
				}
				List<PCAccount> pcAccounts = accountsImpl
						.getPCAccountsAfter(smallestId);
				for (PCAccount pcAccount : pcAccounts) {
					pcAccount.setBalance(pcAccount.getBalance() - pcAccDiff);
				}
				accountsImpl.updatePCAccount(pcAccounts);
			}

			if (santhaAmount.getSpecialThanksOfferingAccounts().size() > 0) {
				Iterator<SpecialThanksOfferingAccount> stoAccIterator = santhaAmount
						.getSpecialThanksOfferingAccounts().iterator();
				float stoAccDiff = 0.0f;
				int smallestId = 0;
				while (stoAccIterator.hasNext()) {
					SpecialThanksOfferingAccount stoAccount = stoAccIterator
							.next();
					stoAccDiff = stoAccDiff + stoAccount.getAmount();
					if (smallestId == 0 || stoAccount.getId() < smallestId) {
						smallestId = stoAccount.getId();
					}
				}
				List<SpecialThanksOfferingAccount> stoAccounts = accountsImpl
						.getSTOAccountsAfter(smallestId);
				for (SpecialThanksOfferingAccount stoAccount : stoAccounts) {
					stoAccount.setBalance(stoAccount.getBalance() - stoAccDiff);
				}
				accountsImpl.updateSpecialThanksOfferingAccount(stoAccounts);
			}

			if (santhaAmount.getMissionaryAccounts().size() > 0) {
				Iterator<MissionaryAccount> MissionaryAccIterator = santhaAmount
						.getMissionaryAccounts().iterator();
				float missionaryAccDiff = 0.0f;
				int smallestId = 0;
				while (MissionaryAccIterator.hasNext()) {
					MissionaryAccount missionaryAccount = MissionaryAccIterator
							.next();
					missionaryAccDiff = missionaryAccDiff
							+ missionaryAccount.getAmount();
					if (smallestId == 0
							|| missionaryAccount.getId() < smallestId) {
						smallestId = missionaryAccount.getId();
					}
				}
				List<MissionaryAccount> missionaryAccounts = accountsImpl
						.getMissionaryAccountsAfter(smallestId);
				for (MissionaryAccount missionaryAccount : missionaryAccounts) {
					missionaryAccount.setBalance(missionaryAccount.getBalance()
							- missionaryAccDiff);
				}
				accountsImpl.updateMissionaryAccount(missionaryAccounts);
			}

			if (santhaAmount.getMensAccounts().size() > 0) {
				Iterator<MensAccount> MensAccIterator = santhaAmount
						.getMensAccounts().iterator();
				float mensAccDiff = 0.0f;
				int smallestId = 0;
				while (MensAccIterator.hasNext()) {
					MensAccount mensAccount = MensAccIterator.next();
					mensAccDiff = mensAccDiff + mensAccount.getAmount();
					if (smallestId == 0 || mensAccount.getId() < smallestId) {
						smallestId = mensAccount.getId();
					}
				}
				List<MensAccount> mensAccounts = accountsImpl
						.getMensAccountsAfter(smallestId);
				for (MensAccount mensAccount : mensAccounts) {
					mensAccount.setBalance(mensAccount.getBalance()
							- mensAccDiff);
				}
				accountsImpl.updateMensAccount(mensAccounts);
			}

			if (santhaAmount.getWomensAccounts().size() > 0) {
				Iterator<WomensAccount> womensAccIterator = santhaAmount
						.getWomensAccounts().iterator();
				float womensAccDiff = 0.0f;
				int smallestId = 0;
				while (womensAccIterator.hasNext()) {
					WomensAccount womensAccount = womensAccIterator.next();
					womensAccDiff = womensAccDiff + womensAccount.getAmount();
					if (smallestId == 0 || womensAccount.getId() < smallestId) {
						smallestId = womensAccount.getId();
					}
				}
				List<WomensAccount> womensAccounts = accountsImpl
						.getWomensAccountsAfter(smallestId);
				for (WomensAccount womensAccount : womensAccounts) {
					womensAccount.setBalance(womensAccount.getBalance()
							- womensAccDiff);
				}
				accountsImpl.updateWomensAccount(womensAccounts);
			}

			if (santhaAmount.getPrimarySchoolAccounts().size() > 0) {
				Iterator<PrimarySchoolAccount> primarySchoolAccIterator = santhaAmount
						.getPrimarySchoolAccounts().iterator();
				float primarySchoolAccDiff = 0.0f;
				int smallestId = 0;
				while (primarySchoolAccIterator.hasNext()) {
					PrimarySchoolAccount primarySchoolAccount = primarySchoolAccIterator
							.next();
					primarySchoolAccDiff = primarySchoolAccDiff
							+ primarySchoolAccount.getAmount();
					if (smallestId == 0
							|| primarySchoolAccount.getId() < smallestId) {
						smallestId = primarySchoolAccount.getId();
					}
				}
				List<PrimarySchoolAccount> primarySchoolAccounts = accountsImpl
						.getPrimarySchoolAccountsAfter(smallestId);
				for (PrimarySchoolAccount primarySchoolAccount : primarySchoolAccounts) {
					primarySchoolAccount.setBalance(primarySchoolAccount
							.getBalance() - primarySchoolAccDiff);
				}
				accountsImpl.updatePrimarySchoolAccount(primarySchoolAccounts);
			}

			if (santhaAmount.getYouthAccounts().size() > 0) {
				Iterator<YouthAccount> youthAccIterator = santhaAmount
						.getYouthAccounts().iterator();
				float youthAccDiff = 0.0f;
				int smallestId = 0;
				while (youthAccIterator.hasNext()) {
					YouthAccount youthAccount = youthAccIterator.next();
					youthAccDiff = youthAccDiff + youthAccount.getAmount();
					if (smallestId == 0 || youthAccount.getId() < smallestId) {
						smallestId = youthAccount.getId();
					}
				}
				List<YouthAccount> youthAccounts = accountsImpl
						.getYouthAccountsAfter(smallestId);
				for (YouthAccount youthAccount : youthAccounts) {
					youthAccount.setBalance(youthAccount.getBalance()
							- youthAccDiff);
				}
				accountsImpl.updateYouthAccount(youthAccounts);
			}

			if (santhaAmount.getGraveyardAccounts().size() > 0) {
				Iterator<GraveyardAccount> graveyardAccIterator = santhaAmount
						.getGraveyardAccounts().iterator();
				float graveyardAccDiff = 0.0f;
				int smallestId = 0;
				while (graveyardAccIterator.hasNext()) {
					GraveyardAccount graveyardAccount = graveyardAccIterator
							.next();
					graveyardAccDiff = graveyardAccDiff
							+ graveyardAccount.getAmount();
					if (smallestId == 0
							|| graveyardAccount.getId() < smallestId) {
						smallestId = graveyardAccount.getId();
					}
				}
				List<GraveyardAccount> graveyardAccounts = accountsImpl
						.getGraveyardAccountsAfter(smallestId);
				for (GraveyardAccount graveyardAccount : graveyardAccounts) {
					graveyardAccount.setBalance(graveyardAccount.getBalance()
							- graveyardAccDiff);
				}
				accountsImpl.updateGraveyardAccount(graveyardAccounts);
			}

			// Removing From UI
			membersSantha.getItems().remove(santha);

			// Adding message to the UI
			this.message.setText("Santha Payment of " + santha.getName()
					+ " has been deleted.");
			logger.info("Santha Payment of " + santha.getName()
					+ " has been deleted.");

			validatePaidMembers();

		} catch (CcfException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.debug("Delete Paid Member method Ends...");
	}
}
