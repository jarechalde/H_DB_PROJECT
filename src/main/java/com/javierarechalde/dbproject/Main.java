package com.javierarechalde.dbproject;

//STEP 1. Import required packages
import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main{
	
	//Hit ctr+space and select 4j logger
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(final String[] args) throws SQLException {
		
		LOGGER.debug("Creating the datasource");
		//Parameters to connect to the database
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:target/db");
		ds.setUsername("jarechalde");
		ds.setPassword("16387495p");
		
		try {
		//Doing the migration
		LOGGER.debug("Executing Flyway (database migration)");
		Flyway flyway = new Flyway();
		flyway.setDataSource(ds);
		flyway.migrate();
		
		LOGGER.debug("Executing some queries");
		try(Connection connection = ds.getConnection(); Statement stmt = connection.createStatement()){
			//stmt.executeUpdate("INSERT INTO DOCTORS (DRID,FNAME,LNAME,SPECIALTY) VALUES (045632,'Parker','Yang','PREDIATRIST')");
			
			LOGGER.debug("DOCTORS:");
			try (ResultSet rs = stmt.executeQuery("SELECT * FROM DOCTORS")){
				while (rs.next()){
					LOGGER.debug("	>> [{}] {} {} {}", new Object[] {rs.getInt("DRID"), rs.getString("FNAME"), rs.getString("LNAME"),rs.getString("SPECIALTY")});
					}
				}
			} 
			
		} catch (SQLException e) {
			LOGGER.error("Failed",e);
		} finally {
			try {
				ds.close();
			} catch (SQLException e) {
				LOGGER.error("Failed", e);
			}
		}	
		
		LOGGER.info("DONE");
		
	}	
}