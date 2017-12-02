package com.javierarechalde.dbproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

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
			
			try(Connection connection = DBHelper.getConnection(); Statement stmt = connection.createStatement()){	
				try(ResultSet rs = stmt.executeQuery("SELECT * FROM DOCTORS")){
					while(rs.next()) {
						LOGGER.debug(">> [{}] {} {} {}", new Object[] {rs.getInt("DRID"), rs.getString("FNAME"), rs.getString("LNAME"), rs.getString("SPECIALTY")});
					}
				}
			
			}
			
		} catch (SQLException e) {
			Main2.LOGGER.error("Failed to Save the doctor");
		}
		
		
		//Closing
        DBHelper.getInstance().close();		
        LOGGER.info("DONE");
		
		
	}

}