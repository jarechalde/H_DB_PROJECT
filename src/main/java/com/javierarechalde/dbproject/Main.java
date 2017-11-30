package com.javierarechalde.dbproject;

//STEP 1. Import required packages
import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;

public class Main{
	
	public static void main(final String[] args) throws SQLException {
		
		//Parameters to connect to the database
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.h2.Driver");
		ds.setUrl("jdbc:h2:target/db");
		ds.setUsername("root");
		ds.setPassword("16387495p");
		
		try (Connection connection = ds.getConnection()){
			System.out.println("Connected");
		} finally {
			ds.close();
		}		
	}	
}