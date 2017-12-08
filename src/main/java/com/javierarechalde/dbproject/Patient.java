package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;

public class Patient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Patient.class);

	private int patid;
	private String fname;
	private String lname;
	private int pnumber;
	private int inscard;
	private int inlist = 0;
	
	public int getPatid() {
		return patid;
	}
	public void setPatid(int patid) {
		this.patid = patid;
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
	public int getPnumber() {
		return pnumber;
	}
	public void setPnumber(int pnumber) {
		this.pnumber = pnumber;
	}
	public int getInscard() {
		return inscard;
	}
	public void setInscard(int inscard) {
		this.inscard = inscard;
	}
	
	@Override
	public String toString() {
		final StringBuilder formatted = new StringBuilder();
		
		if (patid == -1) {
			formatted.append("[No id]");
		} else {
			formatted.append("[").append(patid).append("] ");
		}
		
		if (fname == null) {
			formatted.append("No First Name");
		} else {
			formatted.append(fname);
		}
		
		if (lname == null) {
			formatted.append("No first name");
		} else {
			formatted.append(" "+lname);
		}
		
		return formatted.toString();
	}
	
	public void delete() throws SQLException{
		
		Patient.LOGGER.warn("Called Delete function");
		
		Patient.LOGGER.debug("{}", patid);
		
		final List<Patient> patients = PatientsHelper.getInstance().getPatients();
		
		Patient.LOGGER.debug("Looking for key to delete...");
		for(final Patient patient : patients) {
			Patient.LOGGER.debug(".");
			if (patid == patient.getPatid()) {
				Patient.LOGGER.debug("Key already in the table");
				inlist = 1;
			}
		}
		
		if (inlist == 1) {
			Patient.LOGGER.debug("Patient found in the table, Deleting...");
			final String sql = "DELETE FROM PATIENTS WHERE PATID = ?";
			try (Connection connection = DBHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(1, patid);
				pstmt.execute();
				patid = -1;
			}
		}
	}
	
	public void save() throws SQLException{
		
		final List<Patient> patients = PatientsHelper.getInstance().getPatients();
		
		Patient.LOGGER.debug("Looking for key in the table...");
		for(final Patient patient : patients) {
			Patient.LOGGER.debug(".");
			if (patid == patient.getPatid()) {
				Patient.LOGGER.debug("Key in the table");
				inlist = 1;
			}
		}
		
		try(Connection connection = DBHelper.getConnection();){
			if (inlist == 0){
				Patient.LOGGER.debug("Key not in the table, Inserting...");
				final String sql = "INSERT INTO PATIENTS (PATID,FNAME,LNAME,PHONEN,INSCARD) VALUES (?, ?, ?, ?, ?)";
				try(PreparedStatement pstmt = connection.prepareStatement(sql)){	
					pstmt.setInt(1, patid);
					pstmt.setString(2, fname);
					pstmt.setString(3, lname);
					pstmt.setInt(4, pnumber);
					pstmt.setInt(5, inscard);
					pstmt.execute();
					
				}
			} else {	
			Patient.LOGGER.debug("Key already in the table, Updating...");
			final String sql = "UPDATE PATIENTS SET FNAME = ?, LNAME = ?, PHONEN = ?, INSCARD = ? WHERE PATID = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(5, patid);
				pstmt.setString(1, fname);
				pstmt.setString(2, lname);
				pstmt.setInt(3, pnumber);
				pstmt.setInt(4, inscard);
				pstmt.execute();
				
			}
			}		
		}
	}
}
