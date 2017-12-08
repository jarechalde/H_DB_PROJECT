package com.javierarechalde.dbproject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(final String[] args) {
		DBHelper.getInstance().init();
		DBHelper.getInstance().registerShutdownHook();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Main.LOGGER.debug("Starting UI");
				//Application app = new Application();
				Application app = new Application();
				app.setTitle("Hospital Database V1");
				app.setSize(900,420);
				app.setLocationRelativeTo(null);
				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				app.setVisible(true);
				
			}
		});
	}
}