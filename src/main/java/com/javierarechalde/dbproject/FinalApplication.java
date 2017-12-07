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
import java.util.List;

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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	//Panel for the main menu
	private JPanel controlpanel = new JPanel(new GridLayout(2,2));
	
	//Variables for the patients layout
	private JPanel patpanel = new JPanel(new GridBagLayout());
	private JTextField patidTextField;
	private JTextField patfnTextField;
	private JTextField patlnTextField;
	private JTextField patpnTextField;
	private JTextField paticTextField;
	
	//Actions for the toolbar
	private Action refreshAction;
	private Action newAction;
	private Action saveAction;
	private Action deleteAction;
	
	//List for the patients
	private DefaultListModel<Patient> patientsListModel;
	private JList<Patient> patientsList;
	
	//Variable for the selected patient
	private Patient selected;
	
	public FinalApplication() {
		initUI();
		initActions();
	}
	
	
	private void refreshData() {
		
		roomsListModel.removeAllElements();
		final SwingWorker<Void, Room> worker = new SwingWorker<Void, Room>() {
			@Override
			protected Void doInBackground() throws Exception{
				final List<Room> rooms = RoomsHelper.getInstance().getRooms();
				for(final Room room: rooms) {
					publish(room);
				}
				return null;
			}
			
			@Override
			protected void process(final List<Room> chunks) {
				for(final Room room: chunks) {
					roomsListModel.addElement(room);
				}
			}
		};
		
		worker.execute();
		
	}
	
	private void createNew() {
		Room room =  new Room();
		room.setrid(000000);
		room.setrcap(00);
		room.setrtype("New Room Type");
		setSelectedRoom(room);
	}
	
	private void save() {
		if (selected!=null) {
		selected.setrid(Integer.parseInt(roomidTextField.getText()));
		selected.setrcap(Integer.parseInt(rcapTextField.getText()));
		selected.setrtype(rtypeTextField.getText());
		try{
			selected.save();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Failed to save the selected contact", "Save", JOptionPane.WARNING_MESSAGE);
		} finally {
			refreshData();
		}
		}

	}
	
	private void delete() {
		if (selected!=null) {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Delete " + selected +"?", "Delete", JOptionPane.YES_NO_OPTION)) {
			try {
				selected.delete();
			} catch (final SQLException e) {
				JOptionPane.showMessageDialog(this, "Failed to delete the selected room", "Delete", JOptionPane.WARNING_MESSAGE);;
			} finally {
				setSelectedPatient(null);
				refreshData();
				}
			}
		}
	}
	
	private void initActions() {
			
			refreshAction = new AbstractAction("Refresh", load("Refresh")) {
				private static final long serialVersionUID = -3876237444679320139L;
	
				@Override
				public void actionPerformed(final ActionEvent e) {
					refreshData();
					
				}
			};
			
			newAction = new AbstractAction("New", load("New")) {
				private static final long serialVersionUID = -605237333970985709L;
	
				@Override
				public void actionPerformed(final ActionEvent e) {
					createNew();
					
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
            add(patpanel, BorderLayout.CENTER);
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