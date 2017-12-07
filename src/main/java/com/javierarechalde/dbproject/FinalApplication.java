package com.javierarechalde.dbproject;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.sql.Date;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
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

public class FinalApplication extends JFrame{
	
	private static final long serialVersionUID = 5162873977671955329L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinalApplication.class);
	
	//Panel for the main menu
	private JPanel controlpanel = new JPanel(new GridLayout(2,2));
	
	//Variables for the patients layout
	private JPanel patpanel = new JPanel(new GridBagLayout());
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
	
	//Actions for the appointments toolbar
	private Action refreshActionApp;
	private Action newActionApp;
	private Action saveActionApp;
	private Action deleteActionApp;
	
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
	
	public FinalApplication() {
		initUI();
		initActions();
		initActionsApp();
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
				FinalApplication.LOGGER.error("WTF just happened",e);
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
		toolBar.add(refreshActionApp);
		toolBar.addSeparator();
		toolBar.add(newActionApp);
		toolBar.addSeparator();
		toolBar.add(saveActionApp);
		toolBar.addSeparator();
		toolBar.add(deleteActionApp);
		
		return toolBar;
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
	
	//Function for refreshing the appointment list
	private void refreshApps() {
		
		FinalApplication.LOGGER.info("Refreshing Appointments");
		
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
		appointment.setAppcom("eeeey macarena aaaaay");
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
			FinalApplication.LOGGER.debug("Error", e);
		} finally {
			refreshApps();
		}
		}

	}
	
	private void deleteApp() {
		FinalApplication.LOGGER.debug("Called delete function");
		
		if (selectedapp!=null) {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete " + selectedapp +"?", "Delete", JOptionPane.YES_NO_OPTION)) {
			try {
				selectedapp.delete();
			} catch (final SQLException e) {
				FinalApplication.LOGGER.error("WTF just happened",e);
				JOptionPane.showMessageDialog(this, "Failed to delete the selected patient", "Delete", JOptionPane.WARNING_MESSAGE);;
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
		apppanel.add(new JScrollPane(appstartTextField), constraints);
				
		
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
		apppanel.add(new JScrollPane(appendTextField), constraints);
		
		
		
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
		final JButton appbutton = new JButton("Appointments");
		final JButton diagbutton = new JButton("Diagnosis");
		
		drselpanel.add(appbutton);
		drselpanel.add(diagbutton);
		
		appbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	FinalApplication.LOGGER.info("Creating appointment");
            	//createAppSel();
                getContentPane().remove(drselpanel);
                JPanel apppanel = createAppSel();
                add(apppanel);
                //JComponent listapp = createListApp();
                //add(listapp, BorderLayout.WEST);
                //refreshApps();
                //Have to create another toolbar for the appointments, or at least modify the existing one
                //JToolBar toolbarapp = createToolBarApp();
                //add(toolbarapp, BorderLayout.NORTH);
                getContentPane().invalidate();
                getContentPane().validate();
            	
            }
        });
		
		diagbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FinalApplication.LOGGER.info("This is boring af");
			}
		});
		

		return drselpanel;
		
	}
	
	private JPanel createAppSel() {
		
		final JPanel appselpanel = new JPanel(new GridLayout(2,1));
		
		//Buttons for the panel
		final JButton seeapp = new JButton("See Appointments");
		final JButton editapp = new JButton("Edit Appointments");
		
		appselpanel.add(seeapp);
		appselpanel.add(editapp);
		
		seeapp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	FinalApplication.LOGGER.info("Seeing Appointments");
            	getContentPane().remove(appselpanel);
            	JPanel hola = appointmentUI();
            	add(hola);
                getContentPane().invalidate();
                getContentPane().validate();
            	
            }
        });
		
		editapp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FinalApplication.LOGGER.info("Editing Appointments");
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
		appui.add(patlnameTF, constraints);
		
		//PAtient Phone N
		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 5;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(2,2,2,2);
		appui.add(new JLabel("Pat. Phone N"), constraints);

		constraints = new GridBagConstraints();
		constraints.gridy = 2;
		constraints.gridx = 6;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		patphoneTF = new JTextField();
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
		constraints.gridx = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appdiagTA = new JTextArea();
		appui.add(appdiagTA, constraints);
		
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
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets =  new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		appcommTA = new JTextArea();
		appui.add(appcommTA, constraints);
		
		return appui;
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
		
		//Creating the component for the patients
		
		//Patient ID
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
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
		constraints.anchor = GridBagConstraints.WEST;
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
		constraints.anchor = GridBagConstraints.WEST;
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
		constraints.anchor = GridBagConstraints.WEST;
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
		patpanel.add(new JScrollPane(patpnTextField), constraints);
				
		//Patient Insurance card
		constraints = new GridBagConstraints();
		constraints.gridy = 4;
		constraints.anchor = GridBagConstraints.WEST;
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
		patpanel.add(new JScrollPane(paticTextField), constraints);
		
		//Adding the panel to edit and the list to the frame
		//add(patpanel, BorderLayout.CENTER);
		//add(listpanel, BorderLayout.WEST);
	
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
            FinalApplication.LOGGER.info("Opening Patients Panel");
            getContentPane().remove(controlpanel);
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
            	controlpanel.setVisible(false);
            	FinalApplication.LOGGER.info("LISTENED BRO");
            }
        });
		
		StaffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	controlpanel.setVisible(false);
            	FinalApplication.LOGGER.info("LISTENED BRO");
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