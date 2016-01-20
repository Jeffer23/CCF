package com.ccf.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ccf.dao.impl.FamilyDaoImpl;
import com.ccf.dao.impl.MemberDaoImpl;
import com.ccf.dao.impl.SanthaDaoImpl;
import com.ccf.doa.FamilyDao;
import com.ccf.doa.MemberDao;
import com.ccf.doa.SanthaDao;
import com.ccf.exception.CcfException;
import com.ccf.persistence.classes.Santha;
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
						if(paidDate.getSelectedDate() != null)
							paidDateError.setText("");
						if(paidForDate.getSelectedDate() != null)
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
		logger.debug("Save Family method Starts...");

		try {
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

			// Inserting into DB
			SanthaDao santhaDaoImpl = new SanthaDaoImpl();
			int key = santhaDaoImpl.paySantha(santha);

			// Adding Success Message
			message.setText("Payment added successfully !!!");
			logger.info("Payment added successfully for" + santha.getMember().getId());

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
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (CcfException e) {
			logger.error(e.getMessage());
			message.setText(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
			if(paidDate.getSelectedDate() == null){
				throw new CcfException("Select the Date.");
			}
			if(paidForDate.getSelectedDate() == null){
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
			if(paidDate.getSelectedDate() == null)
				paidDateError.setText(e.getMessage());
			else if(paidForDate.getSelectedDate() == null)
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
		logger.debug("getPreviouslyPaidAmount method Ends...");
	}

	private float calculateTotal() {
		logger.debug("calculateTotal method Starts...");
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

		logger.debug("calculateTotal method Ends...");
		return total;
	}

	public void editPaidMember() {
		logger.debug("Edit Paid Member method Starts...");
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
		logger.debug("Edit Paid Member method Ends...");
	}

	public void updatePayment() {
		logger.debug("Update Payment method Starts...");
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

		try {
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
			float memberTotal = bagOffer + churchRenovation + educationHelp
					+ graveyard + harvestFestival + mensFellowship + missionary
					+ other1 + other2 + poorHelp + primarySchool + sto
					+ thanksOffer + womensFellowship + youth;

			if (memberTotal <= 0) {
				throw new CcfException("Please Check the payment Entered..");
			}

			com.ccf.vo.Santha selectedSantha = membersSantha
					.getSelectionModel().getSelectedItem();

			// Updating database
			SanthaDao santhaDaoImpl = new SanthaDaoImpl();
			Santha santhaAmount = new Santha();
			santhaAmount.setSanthaId(selectedSantha.getSanthaId());
			santhaAmount.setBagOffer(bagOffer);
			santhaAmount.setChurchRenovation(churchRenovation);
			santhaAmount.setEducationHelp(educationHelp);
			santhaAmount.setGraveyard(graveyard);
			santhaAmount.setHarvestFestival(harvestFestival);
			santhaAmount.setMensFellowship(mensFellowship);
			santhaAmount.setMissionary(missionary);
			santhaAmount.setOther1(other1);
			santhaAmount.setOther2(other2);
			santhaAmount.setPoorHelp(poorHelp);
			santhaAmount.setPrimarySchool(primarySchool);
			santhaAmount.setSto(sto);
			santhaAmount.setThanksOffer(thanksOffer);
			santhaAmount.setWomensFellowship(womensFellowship);
			santhaAmount.setYouth(youth);
			santhaAmount.setTotal(memberTotal);

			santhaDaoImpl.updateSantha(santhaAmount);

			int index = membersSantha.getItems().indexOf(selectedSantha);
			membersSantha.getItems().remove(index);

			com.ccf.vo.Santha santha = new com.ccf.vo.Santha();
			santha.setSanthaId(selectedSantha.getSanthaId());
			santha.setName(selectedSantha.getName());
			santha.setSubscription(selectedSantha.getSubscription());
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
			santha.setTotal(memberTotal);

			/*
			 * total.setText(String.valueOf(Float.parseFloat(total.getText()) -
			 * selectedSantha.getTotal() + memberTotal));
			 */
			membersSantha.getItems().add(index, santha);

			this.saveButton.setVisible(true);
			this.updateButton.setVisible(false);
			this.cancelButton.setVisible(false);

			// clearData();
			validatePaidMembers();

			this.message.setText("Payment for " + santha.getName()
					+ " updated Successfully.");
			logger.info("Payment for " + santha.getName()
					+ " updated Successfully.");
		} catch (CcfException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.debug("Update Payment method Ends...");
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
		Santha santhaAmount = new Santha();
		santhaAmount.setSanthaId(santha.getSanthaId());
		santhaAmount.setBagOffer(santha.getBagOffer());
		santhaAmount.setChurchRenovation(santha.getChurchRenovation());
		santhaAmount.setEducationHelp(santha.getEducationHelp());
		santhaAmount.setGraveyard(santha.getGraveyard());
		santhaAmount.setHarvestFestival(santha.getHarvestFestival());
		santhaAmount.setMensFellowship(santha.getMensFellowship());
		santhaAmount.setMissionary(santha.getMissionary());
		santhaAmount.setOther1(santha.getOther1());
		santhaAmount.setOther2(santha.getOther2());
		santhaAmount.setPoorHelp(santha.getPoorHelp());
		santhaAmount.setPrimarySchool(santha.getPrimarySchool());
		santhaAmount.setSto(santha.getSto());
		santhaAmount.setThanksOffer(santha.getThanksOffer());
		santhaAmount.setWomensFellowship(santha.getWomensFellowship());
		santhaAmount.setYouth(santha.getYouth());
		santhaAmount.setTotal(santha.getTotal());

		try {
			// Deleting from DataBase
			SanthaDao santhaDaoImpl = new SanthaDaoImpl();
			MemberDao memberDaoImpl = new MemberDaoImpl();
			List<com.ccf.persistence.classes.Member> members = memberDaoImpl
					.getMembers(Integer.parseInt(familyNos.getEditor()
							.getText()));
			for (com.ccf.persistence.classes.Member familyMember : members) {
				if (familyMember.getName().equals(familyMembers.getValue())) {
					santhaAmount.setMember(familyMember);
					break;
				}
			}
			santhaDaoImpl.deleteSantha(santhaAmount);

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
