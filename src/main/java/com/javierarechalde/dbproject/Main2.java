package com.javierarechalde.dbproject;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main2 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main2.class);
		
	public static void main(final String[] args) {
		
		//Initializing
		DBHelper.getInstance().init();
		
		try {
			Doctors d = new Doctors();
			d.setDrid(125468);
			d.setFname("LIONEL");
			d.setLname("MESSI");
			d.setSpecialty("LOLASO");
			d.save();
		} catch (SQLException e) {
			LOGGER.error("Failed to Save the doctor");
		}
		
		
		//Closing
        DBHelper.getInstance().close();		
        LOGGER.info("DONE");
		
		
	}

}
