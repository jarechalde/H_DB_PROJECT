package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Doctors {

	private int drid;
	private String fname;
	private String lname;
	private String specialty;
	
	public int getDrid() {
		return drid;
	}
	public void setDrid(int drid) {
		this.drid = drid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	
	public void save() throws SQLException{
		String sql = "INSERT INTO DOCTORS (DRID,FNAME,LNAME,SPECIALTY) VALUES (?, ?, ?, ?)";
		try(Connection connection = DBHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
			
			pstmt.setInt(1, drid);
			pstmt.setString(2, fname);
			pstmt.setString(3, lname);
			pstmt.setString(4, specialty);
			
		}
		
	}
	
}
