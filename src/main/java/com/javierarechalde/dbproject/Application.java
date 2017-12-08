package com.javierarechalde.dbproject;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application extends JFrame{
	
	private static final long serialVersionUID = 5162873977671955329L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	//Panel for the main menu
	private JPanel controlpanel = new JPanel(new GridLayout(2,2));
	
	//Variables for the patients layout
	private JTextField patidTextField;
	private JTextField patfnTextField;
	private JTextField patlnTextField;
	private JTextField patpnTextField;
	private JTextField paticTextField;
	
	//Fields for the Appointments Layout
	private JTextField appidTextField;
	private JTextField apppatidTextField;
	private JTextField appdridTextField;
	private JTextField appridTextField;
	private JTextField appstartTextField;
	private JTextField appendTextField;
	private JTextArea appcommentsTextArea;
	
	//Actions for the toolbar
	private Action refreshAction;
	private Action newAction;
	private Action saveAction;
	private Action deleteAction;
	private Action homeAction;
	
	//Actions for the appointments toolbar
	private Action refreshActionApp;
	private Action newActionApp;
	private Action saveActionApp;
	private Action deleteActionApp;
	private Action homeActionApp;
	
	//List for the patients
	private DefaultListModel<Patient> patientsListModel;
	private JList<Patient> patientsList;
	
	//Lists for the appointments
	private DefaultListModel<Appointment> appsListModel;
	private JList<Appointment> appsList;
	
	//Variable for the selected patient
	private Patient selected;
	
	//Variable for the selected appointment
	private Appointment selectedapp;
	
	public Application() {
		initUI();
		initActions();
		initActionsApp();
		initActionsDiag();
		initActionsFac();
		initActionsStaff();
	}
	
	
	private void refreshDatapat() {
		
		patientsListModel.removeAllElements();
		final SwingWorker<Void, Patient> worker = new SwingWorker<Void, Patient>() {
			@Override
			protected Void doInBackground() throws Exception{
				final List<Patient> patients = PatientsHelper.getInstance().getPatients();
				for(final Patient patient: patients) {
					publish(patient);
				}
				return null;
			}
			
			@Override
			protected void process(final List<Patient> chunks) {
				for(final Patient patient: chunks) {
					patientsListModel.addElement(patient);
				}
			}
		};
		
		worker.execute();
		
	}
	
	private void createNewpat() {
		Patient patient =  new Patient();
		patient.setPatid(000000);
		patient.setFname("First Name");
		patient.setLname("Last Name");
		patient.setPnumber(0000000000);
		patient.setInscard(000000);
		setSelectedPatient(patient);
	}
	
	private void save() {
		if (selected!=null) {
		selected.setPatid(Integer.parseInt(patidTextField.getText()));
		selected.setFname(patfnTextField.getText());
		selected.setLname(patlnTextField.getText());
		selected.setPnumber(Integer.parseInt(patpnTextField.getText()));
		selected.setInscard(Integer.parseInt(paticTextField.getText()));
				
		try{
			selected.save();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Failed to save the selected patient", "Save", JOptionPane.WARNING_MESSAGE);
		} finally {
			refreshDatapat();
		}
		}

	}
	
	private void delete() {
		if (selected!=null) {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete " + selected +"?", "Delete", JOptionPane.YES_NO_OPTION)) {
			try {
				selected.delete();
			} catch (final SQLException e) {
				Application.LOGGER.error("Error",e);
				JOptionPane.showMessageDialog(this, "Failed to delete the selected patient", "Delete", JOptionPane.WARNING_MESSAGE);;
			} finally {
				setSelectedPatient(null);
				refreshDatapat();
				}
			}
		}
	}
	
	//For creating the toolbar with icons
	private ImageIcon load(final String name) {
		return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
	}
	
	//Actions for the appointments toolbar
	private void initActionsApp() {

		homeActionApp = new AbstractAction("HomeApp", load("Home")) {
			private static final long serialVersionUID = -3876237444679320139L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				Application.LOGGER.debug("Main menu");
                getContentPane().removeAll();
                initUI();
                getContentPane().repaint();
				
			}
		};
		
		refreshActionApp = new AbstractAction("RefreshApp", load("Refresh")) {
			private static final long serialVersionUID = -3876237444679320139L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				refreshApps();
				
			}
		};
		
		newActionApp = new AbstractAction("NewApp", load("New")) {
			private static final long serialVersionUID = -605237333970985709L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				createNewApp();
				
			}
		};
		
		saveActionApp = new AbstractAction("SaveApp", load("Save")) {
			private static final long serialVersionUID = 2918460914014829628L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				saveApp();
				
			}
		};
		
		deleteActionApp = new AbstractAction("DeleteApp", load("Delete")) {
			private static final long serialVersionUID = 9008483889368221463L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				deleteApp();
				
			}
		};
	}
	
	private void initActions() {
		

			homeAction = new AbstractAction("Home", load("Home")) {
				private static final long serialVersionUID = -3876237444679320139L;
	
				@Override
				public void actionPerformed(final ActionEvent e) {
					Application.LOGGER.debug("Main menu");
	                getContentPane().removeAll();
	                initUI();
	                getContentPane().repaint();

					
				}
			};
		
			
			refreshAction = new AbstractAction("Refresh", load("Refresh")) {
				private static final long serialVersionUID = -3876237444679320139L;
	
				@Override
				public void actionPerformed(final ActionEvent e) {
					refreshDatapat();
					
				}
			};
			
			newAction = new AbstractAction("New", load("New")) {
				private static final long serialVersionUID = -605237333970985709L;
	
				@Override
				public void actionPerformed(final ActionEvent e) {
					createNewpat();
					
				}
			};
			
			saveAction = new AbstractAction("Save", load("Save")) {
				private static final long serialVersionUID = 2918460914014829628L;
	
				@Override
				public void actionPerformed(final ActionEvent e) {
					save();
					
				}
			};
			
			deleteAction = new AbstractAction("Delete", load("Delete")) {
				private static final long serialVersionUID = 9008483889368221463L;
	
				@Override
				public void actionPerformed(final ActionEvent e) {
					delete();
					
				}
			};
		}
	
	//Creating the actions for the diagnosis
	private Action refreshActionDiag;
	private Action newActionDiag;
	private Action saveActionDiag;
	private Action deleteActionDiag;
	private Action homeActionDiag;
	
	//Actions for the diagnosis toolbar
	private void initActionsDiag() {
	
		homeActionDiag = new AbstractAction("HomeDiag", load("Home")) {
			private static final long serialVersionUID = -3876237444679320139L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				Application.LOGGER.debug("Main menu");
                getContentPane().removeAll();
                initUI();
                getContentPane().repaint();
				
			}
		};
		
		refreshActionDiag = new AbstractAction("RefreshDiag", load("Refresh")) {
			private static final long serialVersionUID = -3876237444679320139L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				refreshDiags();
				
			}
		};
		
		newActionDiag = new AbstractAction("NewApp", load("New")) {
			private static final long serialVersionUID = -605237333970985709L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				createNewDiag();
				
			}
		};
		
		saveActionDiag = new AbstractAction("SaveApp", load("Save")) {
			private static final long serialVersionUID = 2918460914014829628L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				saveDiag();
				
			}
		};
		
		deleteActionDiag = new AbstractAction("DeleteApp", load("Delete")) {
			private static final long serialVersionUID = 9008483889368221463L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				deleteDiag();
				
			}
		};
	}
	
	//Functions for the diagnosis panel
	
	private void createNewDiag() {
		
		Diagnosis diagnosis = new Diagnosis();
		
		diagnosis.setDiagid(-1);
		diagnosis.setDrid(000000);
		diagnosis.setPatid(000000);
		diagnosis.setDiagdate(java.sql.Date.valueOf("2015-01-01"));
		diagnosis.setDiagcomm("Comments go here");
		
		setSelectedDiagnosis(diagnosis);
		
	}
	
	private void saveDiag() {
		
		if (selecteddiag!=null) {
		
		selecteddiag.setDiagid(Integer.parseInt(diagidTF.getText()));
		selecteddiag.setDrid(Integer.parseInt(diagdridTF.getText()));
		selecteddiag.setPatid(Integer.parseInt(diagpatidTF.getText()));
		selecteddiag.setDiagdate(java.sql.Date.valueOf(diagdateTF.getText()));
		selecteddiag.setDiagcomm(diagcommTA.getText());
				
		try{
			selecteddiag.save();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Failed to save the selected diagnosis", "Save", JOptionPane.WARNING_MESSAGE);
			Application.LOGGER.debug("Error", e);
		} finally {
			refreshDiags();
		}
		}

	}
	
	private void deleteDiag() {
		Application.LOGGER.debug("Called delete function");
		
		if (selecteddiag!=null) {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete " + selecteddiag +"?", "Delete", JOptionPane.YES_NO_OPTION)) {
			try {
				selecteddiag.delete();
			} catch (final SQLException e) {
				Application.LOGGER.error("Error",e);
				JOptionPane.showMessageDialog(this, "Failed to delete the selected diagnosis", "Delete", JOptionPane.WARNING_MESSAGE);;
			} finally {
				setSelectedDiagnosis(null);
				refreshDiags();
				}
			}
		}
	}

	//Creating the list of patients
	private JComponent createListPane() {
		patientsListModel = new DefaultListModel<>();
		patientsList = new JList<>(patientsListModel);
		patientsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					Patient selected = patientsList.getSelectedValue();
					setSelectedPatient(selected);
				}
			}
		});
		
		return new JScrollPane(patientsList);
	
	}
	
	
	//Lists for the facilities
	private DefaultListModel<Facilities> facsListModel;
	private JList<Facilities> facsList;
		
	//Variable for the selected diagnosis
	private Facilities selectedfac;
	
	//Creating the list of facilities
	private JComponent createListPaneFac() {
		facsListModel = new DefaultListModel<>();
		facsList = new JList<>(facsListModel);
		facsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					Facilities selectedfac = facsList.getSelectedValue();
					setSelectedFacility(selectedfac);
				}
			}
		});
		
		return new JScrollPane(facsList);
	
	}
	
	//Lists for the staff
	private DefaultListModel<Staff> staffListModel;
	private JList<Staff> staffList;
		
	//Variable for the selected diagnosis
	private Staff selectedstaff;
	
	//Creating the list of facilities
	private JComponent createListPaneStaff() {
		staffListModel = new DefaultListModel<>();
		staffList = new JList<>(staffListModel);
		staffList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					Staff selectedstaff = staffList.getSelectedValue();
					setSelectedStaff(selectedstaff);
				}
			}
		});
		
		return new JScrollPane(staffList);
	
	}
	
	//Setting selected staff
	private void setSelectedStaff(Staff staff) {
		
		this.selectedstaff = staff;
		
		if(staff==null) {
			
			staffidTF.setText("");
			staffnameTF.setText("");
			stafflnameTF.setText("");
			staffdeptTF.setText("");
			staffdridTF.setText("");
			
		} else {
			

			staffidTF.setText(String.valueOf(staff.getStaffid()));
			staffnameTF.setText(String.valueOf(staff.getStaffname()));
			stafflnameTF.setText(String.valueOf(staff.getStafflname()));
			staffdeptTF.setText(String.valueOf(staff.getStaffdept()));
			staffdridTF.setText(String.valueOf(staff.getStaffdrid()));
		}
	}
	
	//Setting selected facility
	private void setSelectedFacility(Facilities facility) {
		
		this.selectedfac = facility;
		
		if(facility==null) {
			
			facidTF.setText("");
			facnameTF.setText("");
			facbuildTF.setText("");
			facplantTF.setText("");
			factypeTF.setText("");
			facdescTA.setText("");
			
		} else {
			
			facidTF.setText(String.valueOf(facility.getFacid()));
			facnameTF.setText(String.valueOf(facility.getFacname()));
			facbuildTF.setText(String.valueOf(facility.getFacbuild()));
			facplantTF.setText(String.valueOf(facility.getFacplant()));
			factypeTF.setText(String.valueOf(facility.getFactype()));
			facdescTA.setText(String.valueOf(facility.getFacdesc()));
		}
	}

	
	private void refreshPatients() {
			
			patientsListModel.removeAllElements();
			final SwingWorker<Void, Patient> worker = new SwingWorker<Void, Patient>() {
				@Override
				protected Void doInBackground() throws Exception{
					final List<Patient> patients = PatientsHelper.getInstance().getPatients();
					for(final Patient patient: patients) {
						publish(patient);
					}
					return null;
				}
				
				@Override
				protected void process(final List<Patient> chunks) {
					for(final Patient patient: chunks) {
						patientsListModel.addElement(patient);
					}
				}
			};
			
			worker.execute();
			
	}
	
	private JToolBar createToolBar() {
		final JToolBar toolBar = new JToolBar();

		toolBar.add(homeAction);
		toolBar.addSeparator();
		toolBar.add(refreshAction);
		toolBar.addSeparator();
		toolBar.add(newAction);
		toolBar.addSeparator();
		toolBar.add(saveAction);
		toolBar.addSeparator();
		toolBar.add(deleteAction);
		
		return toolBar;
	}
		
	private JToolBar createToolBarApp() {
		final JToolBar toolBar = new JToolBar();

		toolBar.add(homeActionApp);
		toolBar.addSeparator();
		toolBar.add(refreshActionApp);
		toolBar.addSeparator();
		toolBar.add(newActionApp);
		toolBar.addSeparator();
		toolBar.add(saveActionApp);
		toolBar.addSeparator();
		toolBar.add(deleteActionApp);
		
		return toolBar;
	}
	
	//Toolbar for the diagnosis
	private JToolBar createToolBarDiag() {
		final JToolBar toolBar = new JToolBar();
		
		toolBar.add(homeActionDiag);
		toolBar.addSeparator();
		toolBar.add(refreshActionDiag);
		toolBar.addSeparator();
		toolBar.add(newActionDiag);
		toolBar.addSeparator();
		toolBar.add(saveActionDiag);
		toolBar.addSeparator();
		toolBar.add(deleteActionDiag);
		
		return toolBar;
	}
	
	//Actions for the facilities
	private Action refreshActionFac;
	private Action newActionFac;
	private Action saveActionFac;
	private Action deleteActionFac;
	private Action homeActionFac;
	
	//Actions for the facilities toolbar
		private void initActionsFac() {
			
			homeActionFac = new AbstractAction("HomeFac", load("Home")) {
				private static final long serialVersionUID = 9008483889368221463L;

				@Override
				public void actionPerformed(final ActionEvent e) {
					Application.LOGGER.debug("Main menu");
	                getContentPane().removeAll();
	                initUI();
	                getContentPane().repaint();
					
				}
			};
			
			refreshActionFac = new AbstractAction("RefreshFac", load("Refresh")) {
				private static final long serialVersionUID = -3876237444679320139L;

				@Override
				public void actionPerformed(final ActionEvent e) {
					refreshFacilities();
					
				}
			};
			
			newActionFac = new AbstractAction("NewFac", load("New")) {
				private static final long serialVersionUID = -605237333970985709L;

				@Override
				public void actionPerformed(final ActionEvent e) {
					createNewFac();
					
				}
			};
			
			saveActionFac = new AbstractAction("SaveFac", load("Save")) {
				private static final long serialVersionUID = 2918460914014829628L;

				@Override
				public void actionPerformed(final ActionEvent e) {
					saveFac();
					
				}
			};
			
			deleteActionFac = new AbstractAction("DeleteFac", load("Delete")) {
				private static final long serialVersionUID = 9008483889368221463L;

				@Override
				public void actionPerformed(final ActionEvent e) {
					deleteFac();
					
				}
			};
		}
		
		//Functions for the facilities toolbar
		private void createNewFac() {
			
			Facilities facility = new Facilities();
			
			facility.setFacid(-1);
			facility.setFacbuild("Building");
			facility.setFacname("Name");
			facility.setFacplant(0);
			facility.setFacdesc("Description");
			facility.setFactype("Type");
			
			setSelectedFacility(facility);			
		}
		
		private void saveFac() {
			
			if (selectedfac!=null) {
				
			selectedfac.setFacbuild(facbuildTF.getText());
			selectedfac.setFacname(facnameTF.getText());
			selectedfac.setFacplant(Integer.parseInt(facplantTF.getText()));
			selectedfac.setFacdesc(facdescTA.getText());
			selectedfac.setFactype(factypeTF.getText());
			
			try{
				selectedfac.save();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(this, "Failed to save the selected facility", "Save", JOptionPane.WARNING_MESSAGE);
				Application.LOGGER.debug("Error", e);
			} finally {
				refreshFacilities();
			}
			}

		}
		
		private void deleteFac() {
			Application.LOGGER.debug("Called delete function");
			
			if (selectedfac!=null) {
			if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete " + selectedfac +"?", "Delete", JOptionPane.YES_NO_OPTION)) {
				try {
					selectedfac.delete();
				} catch (final SQLException e) {
					Application.LOGGER.error("Error",e);
					JOptionPane.showMessageDialog(this, "Failed to delete the selected facility", "Delete", JOptionPane.WARNING_MESSAGE);;
				} finally {
					setSelectedFacility(null);
					refreshFacilities();
					}
				}
			}
		}
		
	
	//Toolbar for the facilities
	private JToolBar createToolBarFac() {
		final JToolBar toolBar = new JToolBar();

		toolBar.add(homeActionFac);
		toolBar.addSeparator();
		toolBar.add(refreshActionFac);
		toolBar.addSeparator();
		toolBar.add(newActionFac);
		toolBar.addSeparator();
		toolBar.add(saveActionFac);
		toolBar.addSeparator();
		toolBar.add(deleteActionFac);
		
		return toolBar;
	}
	
	//Actions for the staff toolbar
	private Action refreshActionStaff;
	private Action newActionStaff;
	private Action saveActionStaff;
	private Action deleteActionStaff;
	private Action homeActionStaff;
	
	//Toolbar for the staff
	private JToolBar createToolBarStaff() {
		final JToolBar toolBar = new JToolBar();

		toolBar.add(homeActionStaff);
		toolBar.addSeparator();
		toolBar.add(refreshActionStaff);
		toolBar.addSeparator();
		toolBar.add(newActionStaff);
		toolBar.addSeparator();
		toolBar.add(saveActionStaff);
		toolBar.addSeparator();
		toolBar.add(deleteActionStaff);
		
		return toolBar;
	}
	
	//Actions for the staff toolbar
	private void initActionsStaff() {
		
		homeActionStaff = new AbstractAction("HomeStaff", load("Home")) {
			private static final long serialVersionUID = -3876237444679320139L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				Application.LOGGER.debug("Main menu");
                getContentPane().removeAll();
                initUI();
                getContentPane().repaint();
				
			}
		};
		
		refreshActionStaff = new AbstractAction("RefreshStaff", load("Refresh")) {
			private static final long serialVersionUID = -3876237444679320139L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				refreshStaff();
				
			}
		};
		
		newActionStaff = new AbstractAction("NewStaff", load("New")) {
			private static final long serialVersionUID = -605237333970985709L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				createNewStaff();
				
			}
		};
		
		saveActionStaff = new AbstractAction("SaveStaff", load("Save")) {
			private static final long serialVersionUID = 2918460914014829628L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				saveStaff();
				
			}
		};
		
		deleteActionStaff = new AbstractAction("DeleteStaff", load("Delete")) {
			private static final long serialVersionUID = 9008483889368221463L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				deleteStaff();
				
			}
		};
	}
	
	private void setSelectedPatient(Patient patient) {
		
		this.selected = patient;
		
		if(patient==null) {
			patidTextField.setText("");
			patfnTextField.setText("");
			patlnTextField.setText("");
			patpnTextField.setText("");
			paticTextField.setText("");	
		} else {
			patidTextField.setText(String.valueOf(patient.getPatid()));
			patfnTextField.setText(String.valueOf(patient.getFname()));
			patlnTextField.setText(String.valueOf(patient.getLname()));
			patpnTextField.setText(String.valueOf(patient.getPnumber()));
			paticTextField.setText(String.valueOf(patient.getInscard()));	
		}
	}
	
	//Creating the list of appointments
	private JComponent createListApp() {
		appsListModel = new DefaultListModel<>();
		appsList = new JList<>(appsListModel);
		appsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					Appointment selectedapp = appsList.getSelectedValue();
					setSelectedAppointment(selectedapp);
				}
			}
		});
		
		return new JScrollPane(appsList);
	
	}
	
	//Lists for the diagnosis
	private DefaultListModel<Diagnosis> diagsListModel;
	private JList<Diagnosis> diagsList;
		
	//Variable for the selected diagnosis
	private Diagnosis selecteddiag;
	
	//Creating the list of the diagnosis
	private JComponent createListDiag() {
		diagsListModel = new DefaultListModel<>();
		diagsList = new JList<>(diagsListModel);
		diagsList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					Diagnosis selecteddiag = diagsList.getSelectedValue();
					setSelectedDiagnosis(selecteddiag);
				}
			}
		});
		
		return new JScrollPane(diagsList);
	
	}
	
	//Function for when we select and appointment
	private void setSelectedDiagnosis(Diagnosis diagnosis) {
		
		this.selecteddiag = diagnosis;
		
		if(diagnosis==null) {
			diagidTF.setText("");
			diagdateTF.setText("");
			diagdridTF.setText("");
			diagpatidTF.setText("");
			diagcommTA.setText("");
			
		} else {
			

			diagidTF.setText(String.valueOf(diagnosis.getDiagid()));
			diagdateTF.setText(String.valueOf(diagnosis.getDiagdate()));
			diagdridTF.setText(String.valueOf(diagnosis.getDrid()));
			diagpatidTF.setText(String.valueOf(diagnosis.getPatid()));
			diagcommTA.setText(String.valueOf(diagnosis.getDiagcomm()));
		}
	}
	

	//Lists for the appointments show
	private DefaultListModel<Appointment> appsListModelShow;
	private JList<Appointment> appsListShow;
	
	//Variable for the selected appointment show
	private Appointment selectedappshow;
	
	//List of appointments to show the appointments
	private JComponent createListAppShow() {
		appsListModelShow = new DefaultListModel<>();
		appsListShow = new JList<>(appsListModelShow);
		appsListShow.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					Appointment selectedappshow = appsListShow.getSelectedValue();
					setSelectedAppointmentShow(selectedappshow);
				}
			}
		});
		
		return new JScrollPane(appsListShow);
	
	}
	
	private void refreshStaff() {
		
		staffListModel.removeAllElements();
		final SwingWorker<Void, Staff> worker = new SwingWorker<Void, Staff>() {
			@Override
			protected Void doInBackground() throws Exception{
				final List<Staff> staffs = StaffHelper.getInstance().getStaff();
				for(final Staff staff: staffs) {
					publish(staff);
				}
				return null;
			}
			
			@Override
			protected void process(final List<Staff> chunks) {
				for(final Staff staff: chunks) {
					staffListModel.addElement(staff);
				}
			}
		};
		
		worker.execute();
		
	}
	
	private void createNewStaff() {
		
		Staff staff = new Staff();
		staff.setStaffid(000000);
		staff.setStaffname("First Name");
		staff.setStafflname("Last Name");
		staff.setStaffdept("Department");
		staff.setStaffdrid(000000);
		
		setSelectedStaff(staff);
	}
	
	private void saveStaff() {
		if (selectedstaff!=null) {
			
		selectedstaff.setStaffid(Integer.parseInt(staffidTF.getText()));
		selectedstaff.setStaffname(staffnameTF.getText());
		selectedstaff.setStafflname(stafflnameTF.getText());
		selectedstaff.setStaffdept(staffdeptTF.getText());
		selectedstaff.setStaffdrid(Integer.parseInt(staffdridTF.getText()));
				
		try{
			selectedstaff.save();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Failed to save the selected staff", "Save", JOptionPane.WARNING_MESSAGE);
		} finally {
			refreshStaff();
		}
		}

	}
	
	private void deleteStaff() {
		if (selectedstaff!=null) {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete " + selected +"?", "Delete", JOptionPane.YES_NO_OPTION)) {
			try {
				selectedstaff.delete();
			} catch (final SQLException e) {
				Application.LOGGER.error("Error",e);
				JOptionPane.showMessageDialog(this, "Failed to delete the selected staff", "Delete", JOptionPane.WARNING_MESSAGE);;
			} finally {
				setSelectedStaff(null);
				refreshStaff();
				}
			}
		}
	}
	
	//Refreshing the appointments show
	private void refreshAppsShow() {
		
		Application.LOGGER.info("Refreshing Appointments");
		
		appsListModelShow.removeAllElements();
		final SwingWorker<Void, Appointment> worker = new SwingWorker<Void, Appointment>() {
			@Override
			protected Void doInBackground() throws Exception{
				final List<Appointment> appointments = AppointmentsHelper.getInstance().getAppointments();
				for(final Appointment appointment: appointments) {
					publish(appointment);
				}
				return null;
			}
			
			@Override
			protected void process(final List<Appointment> chunks) {
				for(final Appointment appointment: chunks) {
					appsListModelShow.addElement(appointment);
				}
			}
		};
		
		worker.execute();
		
	}
	
	//Refreshing the Diagnosis to show
		private void refreshDiagsShow() {
			
			Application.LOGGER.info("Refreshing Diagnosis");
			
			diagsListModelShow.removeAllElements();
			final SwingWorker<Void, Diagnosis> worker = new SwingWorker<Void, Diagnosis>() {
				@Override
				protected Void doInBackground() throws Exception{
					final List<Diagnosis> diagnosiss = DiagnosisHelper.getInstance().getDiagnosis();
					for(final Diagnosis diagnosis: diagnosiss) {
						publish(diagnosis);
					}
					return null;
				}
				
				@Override
				protected void process(final List<Diagnosis> chunks) {
					for(final Diagnosis diagnosis: chunks) {
						diagsListModelShow.addElement(diagnosis);
					}
				}
			};
			
			worker.execute();
			
		}
		
		//Set the selected diagnosis to show
		private void setSelectedDiagnosisShow(Diagnosis diagnosishow) {
			
			this.selecteddiagshow = diagnosishow;
			
			if(diagnosishow==null) {
				duidrnameTF.setText("");
				duidrlnameTF.setText("");
				duipatnameTF.setText("");
				duipatlnameTF.setText("");
				duidateTF.setText("");
				duicommTA.setText("");
				
			} else {
				
			
				final int drid  = diagnosishow.getDrid();
				final int patid = diagnosishow.getPatid();
				
				try {
					
					String drfname = diagnosishow.getdrname(drid);
					duidrnameTF.setText(String.valueOf(drfname));
					
					String drlname = diagnosishow.getdrlname(drid);
					duidrlnameTF.setText(String.valueOf(drlname));
					
					
					String patname = diagnosishow.getpatname(patid);
					duipatnameTF.setText(String.valueOf(patname));
					
					String patlname = diagnosishow.getpatlname(patid);
					duipatlnameTF.setText(String.valueOf(patlname));
					
					
				} catch (SQLException e) {
					Application.LOGGER.error("Error", e);
				}
				
				duidateTF.setText(String.valueOf(diagnosishow.getDiagdate()));
				duicommTA.setText(String.valueOf(diagnosishow.getDiagcomm()));

		
			
				
			}
		}
	
	//Function for refreshing the appointment list
	private void refreshApps() {
		
		Application.LOGGER.info("Refreshing Appointments");
		
		appsListModel.removeAllElements();
		final SwingWorker<Void, Appointment> worker = new SwingWorker<Void, Appointment>() {
			@Override
			protected Void doInBackground() throws Exception{
				final List<Appointment> appointments = AppointmentsHelper.getInstance().getAppointments();
				for(final Appointment appointment: appointments) {
					publish(appointment);
				}
				return null;
			}
			
			@Override
			protected void process(final List<Appointment> chunks) {
				for(final Appointment appointment: chunks) {
					appsListModel.addElement(appointment);
				}
			}
		};
		
		worker.execute();
		
	}
	
	private void createNewApp() {
		
		Appointment appointment =  new Appointment();
		appointment.setAppid(-1);
		appointment.setPatid(000000);
		appointment.setDrid(000000);
		appointment.setRoomid(000000);
		appointment.setAstart(java.sql.Date.valueOf("2015-01-01"));
		appointment.setAend(java.sql.Date.valueOf("2015-01-01"));
		appointment.setAppcom("Insert comments here");
		setSelectedAppointment(appointment);
		
	}
	
	private void saveApp() {
		
		if (selectedapp!=null) {
		selectedapp.setPatid(Integer.parseInt(apppatidTextField.getText()));
		selectedapp.setDrid(Integer.parseInt(appdridTextField.getText()));
		selectedapp.setRoomid(Integer.parseInt(appridTextField.getText()));
		selectedapp.setAstart(java.sql.Date.valueOf(appstartTextField.getText()));
		selectedapp.setAend(java.sql.Date.valueOf(appendTextField.getText()));
		selectedapp.setAppcom(appcommentsTextArea.getText());
				
		try{
			selectedapp.save();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Failed to save the selected appointment", "Save", JOptionPane.WARNING_MESSAGE);
			Application.LOGGER.debug("Error", e);
		} finally {
			refreshApps();
		}
		}

	}
	
	private void deleteApp() {
		Application.LOGGER.debug("Called delete function");
		
		if (selectedapp!=null) {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete " + selectedapp +"?", "Delete", JOptionPane.YES_NO_OPTION)) {
			try {
				selectedapp.delete();
			} catch (final SQLException e) {
				Application.LOGGER.error("Error",e);
				JOptionPane.showMessageDialog(this, "Failed to delete the selected appointment", "Delete", JOptionPane.WARNING_MESSAGE);;
			} finally {
				setSelectedAppointment(null);
				refreshApps();
				}
			}
		}
	}
	
	//Function for when we select and appointment
	private void setSelectedAppointment(Appointment appointment) {
		
		this.selectedapp = appointment;
		
		Application.LOGGER.info("Appointment", appointment);
		
		if(appointment==null) {
			appidTextField.setText("");
			apppatidTextField.setText("");
			appdridTextField.setText("");
			appridTextField.setText("");
			appstartTextField.setText("");
			appendTextField.setText("");
			appcommentsTextArea.setText("");
			
		} else {
			
			appidTextField.setText(String.valueOf(appointment.getAppid()));
			apppatidTextField.setText(String.valueOf(appointment.getPatid()));
			appdridTextField.setText(String.valueOf(appointment.getDrid()));
			appridTextField.setText(String.valueOf(appointment.getRoomid()));
			appstartTextField.setText(String.valueOf(appointment.getAstart()));
			appendTextField.setText(String.valueOf(appointment.getAend()));
			appcommentsTextArea.setText(String.valueOf(appointment.getAppcom()));
		}
	}
	
	//Set the selected appointment to show
	private void setSelectedAppointmentShow(Appointment appointmentshow) {
		
		this.selectedappshow = appointmentshow;
		
		if(appointmentshow==null) {
			drnameTF.setText("");
			drlnameTF.setText("");
			drspecialtyTF.setText("");
			patnameTF.setText("");
			patlnameTF.setText("");
			appendTF.setText("");
			appstartTF.setText("");
			approomTF.setText("");
			appdiagTA.setText("");
			appcommTA.setText("");
			
		} else {
			
		
			int drid  = appointmentshow.getDrid();
			int patid = appointmentshow.getPatid();
			
			try {
				String fname = appointmentshow.getdrname(drid);
				drnameTF.setText(String.valueOf(fname));
				
				String lname = appointmentshow.getdrlname(drid);
				drlnameTF.setText(String.valueOf(lname));
				

				String drspecialty = appointmentshow.getdrspecialty(drid);
				drspecialtyTF.setText(String.valueOf(drspecialty));
				
				String patname = appointmentshow.getpatname(patid);
				patnameTF.setText(String.valueOf(patname));
				
				String patlname = appointmentshow.getpatlname(patid);
				patlnameTF.setText(String.valueOf(patlname));
				
				String patphone = appointmentshow.getpatphone(patid);
				patphoneTF.setText(String.valueOf(patphone));

				String diagcomm = appointmentshow.getdiagcomm(patid,drid);
				appdiagTA.setText(String.valueOf(diagcomm));
				
				
			} catch (SQLException e) {
				Application.LOGGER.error("Error", e);
			}
			
			

			approomTF.setText(String.valueOf(appointmentshow.getRoomid()));
			appstartTF.setText(String.valueOf(appointmentshow.getAstart()));
			appendTF.setText(String.valueOf(appointmentshow.getAend()));
			appcommTA.setText(String.valueOf(appointmentshow.getAppcom()));
	
		
			
		}
	}
	
	private JTextField diagidTF; 
	private JTextField diagdridTF;
	private JTextField diagpatidTF;
	private JTextField diagdateTF;
	private JTextArea diagcommTA;
	
	private JPanel createDiagPanel() {
		final JPanel diagpanel =  new JPanel(new GridBagLayout());

		//Diagnosis ID
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		diagpanel.add(new JLabel("Diagnosis ID"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		diagidTF = new JTextField();
		diagpanel.add(diagidTF, constraints);
		
		//DR ID
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		diagpanel.add(new JLabel("DOCTOR ID"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		diagdridTF = new JTextField();
		diagpanel.add(diagdridTF, constraints);
		

		//Patient ID
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		diagpanel.add(new JLabel("PATIENT ID"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		diagpatidTF = new JTextField();
		diagpanel.add(diagpatidTF, constraints);
		
		//Diagnosis Date
		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		diagpanel.add(new JLabel("DATE"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		diagdateTF = new JTextField();
		diagpanel.add(diagdateTF, constraints);

		//Diagnosis Comments
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		diagpanel.add(new JLabel("COMMENTS"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		diagcommTA = new JTextArea();
		diagpanel.add(new JScrollPane(diagcommTA), constraints);
								
		return diagpanel;
		
	}
	
	private JPanel createAppPanel() {
		final JPanel apppanel =  new JPanel(new GridBagLayout());

		//Appointment ID
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		apppanel.add(new JLabel("Appointment ID"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appidTextField = new JTextField();
		appidTextField.setEditable(false);
		apppanel.add(appidTextField, constraints);
		
		//DR ID
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		apppanel.add(new JLabel("DOCTOR ID"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appdridTextField = new JTextField();
		apppanel.add(appdridTextField, constraints);
		

		//Patient ID
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		apppanel.add(new JLabel("PATIENT ID"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		apppatidTextField = new JTextField();
		apppanel.add(apppatidTextField, constraints);
		
		//Room ID
		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		apppanel.add(new JLabel("ROOM ID"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appridTextField = new JTextField();
		apppanel.add(appridTextField, constraints);

		//Start date of the appointment
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		apppanel.add(new JLabel("START"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.weightx = 1;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appstartTextField = new JTextField();
		apppanel.add(appstartTextField, constraints);
				
		
		//End date of the appointment
		constraints = new GridBagConstraints();
		constraints.gridy = 5;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		apppanel.add(new JLabel("END"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.weightx = 1;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appendTextField = new JTextField();
		apppanel.add(appendTextField, constraints);
		
		
		
		//Comments on the appointment
		constraints = new GridBagConstraints();
		constraints.gridy = 6;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		apppanel.add(new JLabel("COMMENTS"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 6;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appcommentsTextArea = new JTextArea();
		apppanel.add(new JScrollPane(appcommentsTextArea), constraints);
		
		
		
		return apppanel;
		
	}
	
	public JPanel createDrSel() {
		
		final JPanel drselpanel = new JPanel(new GridLayout(2,1));
		
		//Buttons for the panel
		final JButton appbutton = new JButton("APPOINTMENTS");
		final JButton diagbutton = new JButton("DIAGNOSIS");
		
		drselpanel.add(appbutton);
		drselpanel.add(diagbutton);
		
		appbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	Application.LOGGER.info("Appointments menu");
                getContentPane().remove(drselpanel);
                JPanel apppanel = createAppSel();
                add(apppanel);
                //refreshDiagsShow();
                getContentPane().invalidate();
                getContentPane().validate();
            	
            }
        });
		
		diagbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Application.LOGGER.info("Diagnosis menu");
                getContentPane().remove(drselpanel);
                JPanel diagpanel = createDiagSel();
                add(diagpanel);
                getContentPane().invalidate();
                getContentPane().validate();
				
			}
		});
		

		return drselpanel;
		
	}
	
	//Function for refreshing the diagnosis list
		private void refreshDiags() {
			
			Application.LOGGER.info("Refreshing Diagnosis");
			
			diagsListModel.removeAllElements();
			final SwingWorker<Void, Diagnosis> worker = new SwingWorker<Void, Diagnosis>() {
				@Override
				protected Void doInBackground() throws Exception{
					final List<Diagnosis> diagnosiss = DiagnosisHelper.getInstance().getDiagnosis();
					for(final Diagnosis diagnosis: diagnosiss) {
						publish(diagnosis);
					}
					return null;
				}
				
				@Override
				protected void process(final List<Diagnosis> chunks) {
					for(final Diagnosis diagnosis: chunks) {
						diagsListModel.addElement(diagnosis);
					}
				}
			};
			
			worker.execute();
			
		}
		
	//Lists for the diagnosis show
	private DefaultListModel<Diagnosis> diagsListModelShow;
	private JList<Diagnosis> diagsListShow;
	
	//Variable for the selected appointment show
	private Diagnosis selecteddiagshow;
	
	//List of appointments to show the appointments
	private JComponent createListDiagShow() {
		diagsListModelShow = new DefaultListModel<>();
		diagsListShow = new JList<>(diagsListModelShow);
		diagsListShow.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					Diagnosis selecteddiagshow = diagsListShow.getSelectedValue();
					setSelectedDiagnosisShow(selecteddiagshow);
					
				}
			}
		});
		
		return new JScrollPane(diagsListShow);
	
	}
	
	private JPanel createDiagSel() {
		
		final JPanel diagselpanel = new JPanel(new GridLayout(2,1));
		
		//Buttons for the panel
		final JButton seediag = new JButton("SEE DIAGNOSIS");
		final JButton editdiag = new JButton("EDIT DIAGNOSIS");
		
		//Adding the buttons to the panel
		diagselpanel.add(seediag);
		diagselpanel.add(editdiag);
		
		seediag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	Application.LOGGER.info("Seeing Diagnosis");
            	getContentPane().remove(diagselpanel);
            	
           
            	
            	JComponent listdiagsshow = createListDiagShow();
            	add(listdiagsshow, BorderLayout.WEST);
            	
            	JPanel diagui = diagnosisUI();
            	add(diagui, BorderLayout.CENTER);
            	
             	final JButton homediag = new JButton("MAIN MENU");
            	homediag.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						Application.LOGGER.debug("Main menu");
		                getContentPane().removeAll();
		                initUI();
		                getContentPane().repaint();
						
					}
            		
            	});
            	
            	add(homediag, BorderLayout.PAGE_START);
            	
            	
            	//Adding diagnosis to the panel
            	refreshDiagsShow();
                getContentPane().invalidate();
                getContentPane().validate();
            	
            }
        });
		
		editdiag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Application.LOGGER.info("Editing Diagnosis");
                getContentPane().remove(diagselpanel);
                JPanel diagpanel = createDiagPanel();
                add(diagpanel);
                JComponent listdiag = createListDiag();
                add(listdiag, BorderLayout.WEST);
                refreshDiags();
                JToolBar toolbardiag = createToolBarDiag();
                add(toolbardiag, BorderLayout.NORTH);
                getContentPane().invalidate();
                getContentPane().validate();
			}
		});
		

		return diagselpanel;
		
	}
	
	private JPanel createAppSel() {
		
		final JPanel appselpanel = new JPanel(new GridLayout(2,1));
		
		//Buttons for the panel
		final JButton seeapp = new JButton("SEE APPOINTMENTS");
		final JButton editapp = new JButton("EDIT APPOINTMENTS");
		
		//Adding the buttons to the panel
		appselpanel.add(seeapp);
		appselpanel.add(editapp);
		
		seeapp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	Application.LOGGER.info("Seeing Appointments");
            	getContentPane().remove(appselpanel);
            	JComponent listappshow = createListAppShow();
            	add(new JLabel("LIST OF APPOINTMENTS"), BorderLayout.PAGE_START);
            	add(listappshow, BorderLayout.WEST);
            	JPanel appui = appointmentUI();
            	add(appui, BorderLayout.CENTER);
            	refreshAppsShow();
            	
             	final JButton homeapp = new JButton("MAIN MENU");
            	homeapp.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						Application.LOGGER.debug("Main menu");
		                getContentPane().removeAll();
		                initUI();
		                getContentPane().repaint();
						
					}
            		
            	});
            	
            	add(homeapp, BorderLayout.PAGE_START);
            	
                getContentPane().invalidate();
                getContentPane().validate();
            	
            }
        });
		
		editapp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				Application.LOGGER.info("Editing Appointments");
                getContentPane().remove(appselpanel);
                JPanel apppanel = createAppPanel();
                add(apppanel);
                JComponent listapp = createListApp();
                add(listapp, BorderLayout.WEST);
                refreshApps();
                JToolBar toolbarapp = createToolBarApp();
                add(toolbarapp, BorderLayout.NORTH);
                getContentPane().invalidate();
                getContentPane().validate();
			}
		});
		

		return appselpanel;
		
	}
	
	//Some variables for the appointment ui
	private JTextField drnameTF;
	private JTextField drlnameTF; 
	private JTextField drspecialtyTF;
	private JTextField patnameTF;
	private JTextField patlnameTF;
	private JTextField patphoneTF;
	private JTextField appendTF;
	private JTextField appstartTF;
	private JTextField approomTF;
	private JTextArea appdiagTA;
	private JTextArea appcommTA;
	
	private JPanel appointmentUI(){

		//Creating the component for the patients
		final JPanel appui = new JPanel(new GridBagLayout());
		
		//DR Name
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("Dr. First Name"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridy = 1;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		drnameTF = new JTextField();
		drnameTF.setEditable(false);
		appui.add(drnameTF, constraints);
				
		//DR Last Name
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 3;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("Dr. Last Name"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 4;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		drlnameTF = new JTextField();
		drlnameTF.setEditable(false);
		appui.add(drlnameTF, constraints);
		
		//DR Specialty
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 5;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("Specialty"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 6;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		drspecialtyTF = new JTextField();
		drspecialtyTF.setEditable(false);
		appui.add(drspecialtyTF, constraints);
		
		//Patient First Name
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("Pat. First Name"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridy = 2;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		patnameTF = new JTextField();
		patnameTF.setEditable(false);
		appui.add(patnameTF, constraints);
				
		//PAtient last name
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 3;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("Pat. Last Name"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 4;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		patlnameTF = new JTextField();
		patlnameTF.setEditable(false);
		appui.add(patlnameTF, constraints);
		
		//Patient Phone number
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 5;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("Phone N."), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 6;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		patphoneTF = new JTextField();
		patphoneTF.setEditable(false);
		appui.add(patphoneTF, constraints);
		
		//Appointment Room
		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("Room"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.gridx = 2;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		approomTF = new JTextField();
		approomTF.setEditable(false);
		appui.add(approomTF, constraints);
		
		//Appointment Start Date
		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.gridx = 3;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("Start"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.gridx = 4;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appstartTF = new JTextField();
		appstartTF.setEditable(false);
		appui.add(appstartTF, constraints);
		
		//Appointment End Date
		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.gridx = 5;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("End"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.gridx = 6;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appendTF = new JTextField();
		appendTF.setEditable(false);
		appui.add(appendTF, constraints);
		
		//Diagnosis Area
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("Diagnosis"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 5;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridx = 1;
		constraints.gridwidth = 6; 
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appdiagTA = new JTextArea();
		appdiagTA.setEditable(false);
		appui.add(new JScrollPane(appdiagTA), constraints);
		
		//Appointment Comments
		constraints = new GridBagConstraints();
		constraints.gridy = 6;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("Comments"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 7;
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridwidth = 6;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appcommTA = new JTextArea();
		appcommTA.setEditable(false);
		appui.add(new JScrollPane(appcommTA), constraints);
		
		return appui;
	}
	
	//Variables for the diagnosis UI
	private JTextField duidrnameTF;
	private JTextField duidrlnameTF;
	private JTextField duipatnameTF;
	private JTextField duipatlnameTF;
	private JTextField duidateTF;
	private JTextArea duicommTA;
	
	//UI for the diagnosis
	private JPanel diagnosisUI(){

		//Creating the component for the patients
		final JPanel diagui = new JPanel(new GridBagLayout());
		
		//DR Name
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		diagui.add(new JLabel("Dr. First Name"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridy = 1;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		duidrnameTF = new JTextField();
		duidrnameTF.setEditable(false);
		diagui.add(duidrnameTF, constraints);
				
		//DR Last Name
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 3;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		diagui.add(new JLabel("Dr. Last Name"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.gridx = 4;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		duidrlnameTF = new JTextField();
		duidrlnameTF.setEditable(false);
		diagui.add(duidrlnameTF, constraints);
		
		//Patient First Name
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		diagui.add(new JLabel("Pat. First Name"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridy = 2;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		duipatnameTF = new JTextField();
		duipatnameTF.setEditable(false);
		diagui.add(duipatnameTF, constraints);
				
		//PAtient last name
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 3;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		diagui.add(new JLabel("Pat. Last Name"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 4;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		duipatlnameTF = new JTextField();
		duipatlnameTF.setEditable(false);
		diagui.add(duipatlnameTF, constraints);
		
		//Appointment Date
		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(2,2,2,2);
		diagui.add(new JLabel("Date Diagnosed"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.gridx = 2;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		duidateTF = new JTextField();
		duidateTF.setEditable(false);
		diagui.add(duidateTF, constraints);
		
		//Diagnosis Comments
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		diagui.add(new JLabel("Comments"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 5;
		constraints.gridx = 1;
		constraints.gridwidth = 4; 
		constraints.weighty = 1;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		duicommTA = new JTextArea();
		duicommTA.setEditable(false);
		diagui.add(new JScrollPane(duicommTA), constraints);
		
		return diagui;
	}
	
	//Text fields for the staff ui
	private JTextField staffidTF;
	private JTextField staffnameTF;
	private JTextField stafflnameTF;
	private JTextField staffdeptTF;
	private JTextField staffdridTF;
	
	//UI for the facilities
	private JPanel staffUI(){
	
		//Creating the component for the patients
		final JPanel staffui = new JPanel(new GridBagLayout());
		
		//Staff ID
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		staffui.add(new JLabel("Staff ID"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridy = 1;
		constraints.gridx = 2;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		staffidTF = new JTextField();
		staffui.add(staffidTF, constraints);
				
		//Staff name
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		staffui.add(new JLabel("First Name"), constraints);
	
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 2;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		staffnameTF = new JTextField();
		staffui.add(staffnameTF, constraints);
		
		//Staff Last name
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		staffui.add(new JLabel("Last name"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridy = 3;
		constraints.gridx = 2;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		stafflnameTF = new JTextField();
		staffui.add(stafflnameTF, constraints);
				
		//Staff Dept.
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		staffui.add(new JLabel("Staff Dept."), constraints);
	
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.gridx = 2;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		staffdeptTF = new JTextField();
		staffui.add(staffdeptTF, constraints);
		
		//Facility Type
		constraints = new GridBagConstraints();
		constraints.gridy = 5;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		staffui.add(new JLabel("DR ID"), constraints);
	
		constraints = new GridBagConstraints();
		constraints.gridy = 5;
		constraints.gridx = 2;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		staffdridTF = new JTextField();
		staffui.add(staffdridTF, constraints);
	
		
		return staffui;
		
		}
	
	//Text fields for the facilities ui
	private JTextField facidTF;
	private JTextField facnameTF;
	private JTextField facbuildTF;
	private JTextField facplantTF;
	private JTextField factypeTF;
	private JTextArea facdescTA;
	
	//UI for the facilities
	private JPanel facilitiesUI(){
	
		//Creating the component for the patients
		final JPanel facui = new JPanel(new GridBagLayout());
		
		//Facilities ID
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		facui.add(new JLabel("Facility ID"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridy = 1;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		facidTF = new JTextField();
		//Setting the facilities ID text field as non editable, because the table autoincrements
		facidTF.setEditable(false);
		facui.add(facidTF, constraints);
				
		//Facilities name
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		facui.add(new JLabel("Facility Name"), constraints);
	
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 2;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		facnameTF = new JTextField();
		facui.add(facnameTF, constraints);
		
		//Facility building
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		facui.add(new JLabel("Facility Building"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.weightx = 1;
		constraints.gridy = 3;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		facbuildTF = new JTextField();
		facui.add(facbuildTF, constraints);
				
		//Facility plant
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		facui.add(new JLabel("Plant"), constraints);
	
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.gridx = 2;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		facplantTF = new JTextField();
		facui.add(facplantTF, constraints);
		
		//Facility Type
		constraints = new GridBagConstraints();
		constraints.gridy = 5;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		facui.add(new JLabel("Facility Type"), constraints);
	
		constraints = new GridBagConstraints();
		constraints.gridy = 5;
		constraints.gridx = 2;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		factypeTF = new JTextField();
		facui.add(factypeTF, constraints);
		
		//Facility description
		constraints = new GridBagConstraints();
		constraints.gridy = 6;
		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		facui.add(new JLabel("Description"), constraints);
	
		constraints = new GridBagConstraints();
		constraints.gridy = 7;
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		facdescTA = new JTextArea();
		facui.add(new JScrollPane(facdescTA), constraints);
		
		return facui;
		
	}
	
	//Refresh facilities
	private void refreshFacilities() {
		
		facsListModel.removeAllElements();
		final SwingWorker<Void, Facilities> worker = new SwingWorker<Void, Facilities>() {
			@Override
			protected Void doInBackground() throws Exception{
				final List<Facilities> facilities = FacilitiesHelper.getInstance().getFacilities();
				for(final Facilities facility: facilities) {
					publish(facility);
				}
				return null;
			}
			
			@Override
			protected void process(final List<Facilities> chunks) {
				for(final Facilities facility: chunks) {
					facsListModel.addElement(facility);
				}
			}
		};
		
		worker.execute();
		
}
	
	private JPanel patientui() {

		//Creating the component for the patients
		final JPanel patpanel =  new JPanel(new GridBagLayout());
		
		//Patient ID
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		patpanel.add(new JLabel("Patient ID"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		patidTextField = new JTextField();
		patpanel.add(patidTextField, constraints);
				
		//Patient First Name
		constraints = new GridBagConstraints();
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		patpanel.add(new JLabel("First Name"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		patfnTextField = new JTextField();
		patpanel.add(patfnTextField, constraints);
		
		//Patient Last Name
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		patpanel.add(new JLabel("Last Name"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		patlnTextField = new JTextField();
		patpanel.add(patlnTextField, constraints);
		
		//Patient Phone Number
		constraints = new GridBagConstraints();
		constraints.gridy = 3;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		patpanel.add(new JLabel("Phone Number"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		patpnTextField = new JTextField();
		patpanel.add(patpnTextField, constraints);
				
		//Patient Insurance card
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(2,2,2,2);
		patpanel.add(new JLabel("Insurance Card"), constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		paticTextField = new JTextField();
		patpanel.add(paticTextField, constraints);
		
		return patpanel;
	}
	
	private void initUI() {
		//Buttons for the main frame
		final JButton DrButton = new JButton("DOCTORS");
		final JButton PatButton = new JButton("PATIENTS");
		final JButton FacButton = new JButton("FACILITIES");
		final JButton StaffButton = new JButton("STAFF");

		//Adding the buttons to the main frame
		controlpanel.add(DrButton);
		controlpanel.add(PatButton);
		controlpanel.add(FacButton);
		controlpanel.add(StaffButton);
		
	
		DrButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            getContentPane().remove(controlpanel);
            add(createDrSel(), BorderLayout.CENTER);
            getContentPane().invalidate();
            getContentPane().validate();
            }
        });
		
		PatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            Application.LOGGER.info("Opening Patients Panel");
            getContentPane().remove(controlpanel);
            JPanel patpanel  = patientui();
            add(patpanel);
            add(createListPane(), BorderLayout.WEST);
            refreshPatients();
            JToolBar toolbar = createToolBar();
            add(toolbar, BorderLayout.NORTH);
            getContentPane().invalidate();
            getContentPane().validate();
            	
            }
        });
		
		FacButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	Application.LOGGER.info("Opening Facilities Panel");
                getContentPane().remove(controlpanel);
                JPanel facpanel = facilitiesUI();
                add(facpanel);
                add(createListPaneFac(), BorderLayout.WEST);
                refreshFacilities();
                JToolBar toolbarfac = createToolBarFac();
                add(toolbarfac, BorderLayout.NORTH);
                getContentPane().invalidate();
                getContentPane().validate();
            }
        });
		
		StaffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	Application.LOGGER.info("Opening Staff Panel");
                getContentPane().remove(controlpanel);
                JPanel staffpanel = staffUI();
                add(staffpanel);
                add(createListPaneStaff(), BorderLayout.WEST);
                refreshStaff();
                JToolBar toolbarstaff = createToolBarStaff();
                add(toolbarstaff, BorderLayout.NORTH);
                getContentPane().invalidate();
                getContentPane().validate();
            }
        });
		
		add(controlpanel);
		
	}
	
	
	
    public void actionPerformed(ActionEvent e) {
        JFrame frame2 = new JFrame("Your Stocks");
        frame2.setVisible(true);
        frame2.setSize(600, 600);
        JLabel label = new JLabel("Your Personal Stocks");
        JPanel panel = new JPanel();
        frame2.add(panel);
        panel.add(label);
    }
	
}