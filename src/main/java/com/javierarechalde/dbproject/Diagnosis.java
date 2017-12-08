package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Diagnosis {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Diagnosis.class);

	private int diagid;
	private int patid;
	private int drid;
	private Date diagdate;
	private String diagcomm;
	
	private int inlist = 0;
	

	public int getDiagid() {
		return diagid;
	}

	public void setDiagid(int diagid) {
		this.diagid = diagid;
	}

	public int getPatid() {
		return patid;
	}

	public void setPatid(int patid) {
		this.patid = patid;
	}

	public int getDrid() {
		return drid;
	}

	public void setDrid(int drid) {
		this.drid = drid;
	}

	public Date getDiagdate() {
		return diagdate;
	}

	public void setDiagdate(Date diagdate) {
		this.diagdate = diagdate;
	}

	public String getDiagcomm() {
		return diagcomm;
	}

	public void setDiagcomm(String diagcomm) {
		this.diagcomm = diagcomm;
	}

	
	@Override
	public String toString() {
		final StringBuilder formatted = new StringBuilder();
		
		if (diagid == -1) {
			formatted.append("[No id]");
		} else {
			formatted.append("[").append(diagid).append("] ");
		}
		
		if (drid == 0) {
			formatted.append("No Doctor ID");
		} else {
			formatted.append(drid);
		}
		
		if (patid == 0) {
			formatted.append("No Patient ID");
		} else {
			formatted.append(" "+patid);
		}
		
		if (diagdate == null) {
			formatted.append("No Date");
		} else {
			formatted.append(" ").append(diagdate).append(" ");
		}
		
		return formatted.toString();
	}
	
	private String drname = null;
	private String drlname = null;
	private String patname = null;
	private String patlname = null;
	
	public String getdrname(int mything) throws SQLException{
		
		try(Connection connection = DBHelper.getConnection();){
			final String sql = "SELECT * FROM DOCTORS WHERE DRID = ?";
			try(PreparedStatement pstmt = connection.prepareStatement(sql)){	
				
				//Adding the dr id to the SQL statement
				pstmt.setInt(1, mything);
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()){
					
					drname = rs.getString("FNAME");
					
				}
				
			}
	
		}
		
		return drname;
	}
	
	public String getdrlname(int mything) throws SQLException{
		
		try(Connection connection = DBHelper.getConnection();){
			final String sql = "SELECT * FROM DOCTORS WHERE DRID = ?";
			try(PreparedStatement pstmt = connection.prepareStatement(sql)){	
				
				//Adding the dr id to the SQL statement
				pstmt.setInt(1, mything);
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()){
					
					drlname = rs.getString("LNAME");
					
				}
				
			}
	
		}
		
		return drlname;
	}
	
	public String getpatname(int mything) throws SQLException{
		
		try(Connection connection = DBHelper.getConnection();){
			final String sql = "SELECT * FROM PATIENTS WHERE PATID = ?";
			try(PreparedStatement pstmt = connection.prepareStatement(sql)){	
				
				//Adding the patient id to the SQL statement
				pstmt.setInt(1, mything);
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()){
					
					patname = rs.getString("FNAME");
					
				}
				
			}
	
		}
		
		return patname;
}

	public String getpatlname(int mything) throws SQLException{
	
	try(Connection connection = DBHelper.getConnection();){
		final String sql = "SELECT * FROM PATIENTS WHERE PATID = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(sql)){	
			
			//Adding the patient id to the SQL statement
			pstmt.setInt(1, mything);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()){
				
				patlname = rs.getString("LNAME");
				
				}
			
			}

		}
	
		return patlname;
	}
	
	public void delete() throws SQLException{
		
		Diagnosis.LOGGER.warn("Called Delete function");
		
		Diagnosis.LOGGER.debug("{}", diagid);
		
		final List<Diagnosis> diagnosiss = DiagnosisHelper.getInstance().getDiagnosis();
		
		Diagnosis.LOGGER.debug("Looking for key to delete...");
		for(final Diagnosis diagnosis : diagnosiss) {
			Diagnosis.LOGGER.debug(".");
			if (diagid == diagnosis.getDiagid()) {
				Diagnosis.LOGGER.debug("Key already in the table");
				inlist = 1;
			}
		}
		
		if (inlist == 1) {
			Diagnosis.LOGGER.debug("Diagnosis found in the table, Deleting...");
			final String sql = "DELETE FROM DIAGNOSIS WHERE DIAGID = ?";
			try (Connection connection = DBHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(1, diagid);
				pstmt.execute();
				diagid = -1;
			}
		}
	}
	
	public void save() throws SQLException{
		
		final List<Diagnosis> diagnosiss = DiagnosisHelper.getInstance().getDiagnosis();
		
		Diagnosis.LOGGER.debug("Looking for key in the table...");
		for(final Diagnosis diagnosis : diagnosiss) {
			Diagnosis.LOGGER.debug(".");
			if (diagid == diagnosis.getDiagid()) {
				Diagnosis.LOGGER.debug("Key in the table");
				inlist = 1;
			}
		}
		
		try(Connection connection = DBHelper.getConnection();){
			if (inlist == 0){
				Diagnosis.LOGGER.debug("Key not in the table, Inserting...");
				final String sql = "INSERT INTO DIAGNOSIS (DIAGID, DIAGCOMM, DIAGDATE, PATID, DRID) VALUES (?, ?, ?, ?, ?)";
				try(PreparedStatement pstmt = connection.prepareStatement(sql)){	
					pstmt.setInt(1, diagid);
					pstmt.setString(2, diagcomm);
					pstmt.setDate(3, diagdate);
					pstmt.setInt(4, patid);
					pstmt.setInt(5, drid);
					pstmt.execute();
					
				}
			} else {	
			Diagnosis.LOGGER.debug("Key already in the table, Updating...");
			final String sql = "UPDATE DIAGNOSIS SET  DIAGCOMM = ?, DIAGDATE = ?, PATID = ?, DRID = ? WHERE DIAGID = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(5, diagid);
				pstmt.setString(1, diagcomm);
				pstmt.setDate(2, diagdate);
				pstmt.setInt(3, patid);
				pstmt.setInt(4, drid);
				pstmt.execute();
				
			}
			}		
		}
	}
}
