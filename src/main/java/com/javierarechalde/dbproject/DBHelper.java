package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.flywaydb.core.Flyway;

public class DBHelper {

	public static Connection getConnection() throws SQLException {
		return getInstance().getDataSource().getConnection();
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(DBHelper.class);
	
	private static final DBHelper INSTANCE = new DBHelper();
	
	public static DBHelper getInstance() {
		return DBHelper.INSTANCE;
	}
	
	private BasicDataSource ds;
	
	private DBHelper() {		
	}
	

	
	//Initialize
	public void init() {
		LOGGER.debug("Creating the datasource");
		//Parameters to connect to the database
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:target/db");
		ds.setUsername("root");
		ds.setPassword("16387495p");
		
		//Doing the migration before we use the data source
		LOGGER.debug("Executing Flyway (database migration)");
		Flyway flyway = new Flyway();
		flyway.setDataSource(ds);
		flyway.migrate();
	}
	
	public DataSource getDataSource() {
		return ds;
	}
	
	//Close it
	public void close() {
		//Checking if we initialized before trying to close
		if(ds != null) {
			LOGGER.debug("Closing the data source");
			try {
				ds.close();
			} catch (SQLException e) {
				LOGGER.error("Failed to close the data source", e);
			}
			
		} 
		
	}
	
}
