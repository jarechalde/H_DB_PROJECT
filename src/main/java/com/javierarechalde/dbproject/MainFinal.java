package com.javierarechalde.dbproject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFinal{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainFinal.class);
	
	

	
	public static void main(final String[] args) {
		DBHelper.getInstance().init();
		DBHelper.getInstance().registerShutdownHook();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				
				private ImageIcon load(final String name) {
					return new ImageIcon(getClass().getResource("/icons/" + name + ".png"));
				}
				
				hola = new AbstractAction("Doctors", load("Doctors")) {
					private static final long serialVersionUID = -605237333970985709L;

					@Override
					public void actionPerformed(final ActionEvent e) {
						
					}
				};
				
				
				final JFrame mainFrame;
				final JLabel headerLabel;
				final JPanel controlPanel;	
				final JFrame DrFrame;
				final JPanel drpanel;
				final JFrame patframe;
				
				MainFinal.LOGGER.debug("Starting UI");
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
				
				final JToolBar toolbar =  new JToolBar();
				toolbar.add(hola12);
				toolbar.addSeparator();
				toolbar.add(hola);
				toolbar.addSeparator();
				toolbar.add(hola);
				toolbar.addSeparator();
				toolbar.add(hola);
				
				patframe.add(toolbar,BorderLayout.PAGE_START);
				
				//Listener for Appointment
				mkapt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						MainFinal.LOGGER.info("Opening Appointment UI");
					}
				});
				
				
				//Button Listeners
				DrButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						MainFinal.LOGGER.info("Dr Button Pressed");
						mainFrame.setVisible(false);
						DrFrame.setVisible(true);
						
					}
				});		
				
				//Listener for the Patients Button
				DrButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						MainFinal.LOGGER.info("Dr Button Pressed");
						mainFrame.setVisible(false);
						DrFrame.setVisible(true);
						
					}
				});					
			
			}
			
		});
	}
	
}