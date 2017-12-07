package com.javierarechalde.dbproject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFinal2{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainFinal2.class);

	public static void main(String[] args) {
	
	DBHelper.getInstance().init();
	DBHelper.getInstance().registerShutdownHook();
	
	final JFrame mainFrame;
	final JLabel headerLabel;
	final JPanel controlPanel;	
	final JFrame DrFrame;
	final JPanel drpanel;
	final JFrame patframe;
	
	MainFinal2.LOGGER.debug("Starting UI");
	mainFrame = new JFrame("Hospital V1.0");
	mainFrame.setSize(800,400);
	mainFrame.setLocationRelativeTo(null);
	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

	headerLabel = new JLabel("Hospital Application", JLabel.CENTER);
	
	controlPanel = new JPanel();
	controlPanel.setLayout(new GridLayout(2,2));
	
	//controlPanel.add(headerLabel);
	mainFrame.add(controlPanel);
	

	//Buttons for the main frame
	final JButton DrButton = new JButton("DOCTORS");
	final JButton PatButton = new JButton("PATIENTS");
	final JButton FacButton = new JButton("FACILITIES");
	final JButton StaffButton = new JButton("STAFF");
	
	//Adding the buttons to the main frame
	controlPanel.add(DrButton);
	controlPanel.add(PatButton);
	controlPanel.add(FacButton);
	controlPanel.add(StaffButton);
	
	//Setting the main Frame visible
	mainFrame.setVisible(true);
	
	//Setting up the doctor frame
	DrFrame = new JFrame("Doctor's Menu");
	DrFrame.setSize(800,400);
	DrFrame.setLocationRelativeTo(null);
	DrFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	
	drpanel = new JPanel();
	drpanel.setLayout(new GridLayout(1,2));
	
	//Creating Buttons for the Doctors panel
	final JButton mkapt = new JButton("Appointment");
	final JButton diag = new JButton("Diagnose");
	
	//Adding the buttons to the panel
	drpanel.add(mkapt);
	drpanel.add(diag);
	
	//Adding the panel to the doctors frame
	DrFrame.add(drpanel);
	
	//Frame for the patients
	patframe = new JFrame("Doctor's Menu");
	patframe.setSize(800,400);
	patframe.setLocationRelativeTo(null);
	patframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//Buttons for the toolbar
	final JButton refreshb = new JButton("Refresh");
	final JButton createb = new JButton("Create");
	final JButton saveb = new JButton("Save");
	final JButton removeb = new JButton("Delete");
	
	final JToolBar toolbar =  new JToolBar();
	toolbar.add(refreshb);
	toolbar.addSeparator();
	toolbar.add(createb);
	toolbar.addSeparator();
	toolbar.add(saveb);
	toolbar.addSeparator();
	toolbar.add(removeb);
	
	
		
	patframe.add(toolbar,BorderLayout.PAGE_START);
	
	//Listener for Appointment
	mkapt.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			MainFinal2.LOGGER.info("Opening Appointment UI");
			DrFrame.setVisible(false);
		}
	});
	
	
	//Listener for the Patients Button
	PatButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			MainFinal2.LOGGER.info("Patients Button pressed");
			mainFrame.setVisible(false);
			patframe.setVisible(true);
			
		}
	});		
	
	//Listener for the doctors button
	DrButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			MainFinal2.LOGGER.info("Dr Button Pressed");
			mainFrame.setVisible(false);
			DrFrame.setVisible(true);
			
		}
	});		
	
	
	}
}