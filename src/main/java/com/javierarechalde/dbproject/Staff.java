package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;

public class Staff {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Staff.class);

	
	public int getStaffid() {
		return staffid;
	}

	public void setStaffid(int staffid) {
		this.staffid = staffid;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}

	public String getStafflname() {
		return stafflname;
	}

	public void setStafflname(String stafflname) {
		this.stafflname = stafflname;
	}

	public String getStaffdept() {
		return staffdept;
	}

	public void setStaffdept(String staffdept) {
		this.staffdept = staffdept;
	}

	public int getStaffdrid() {
		return staffdrid;
	}

	public void setStaffdrid(int i) {
		this.staffdrid = i;
	}

	private int staffid;
	private String staffname;
	private String stafflname;
	private String staffdept;
	private int staffdrid;
	
	private int inlist = 0;
	
	
	@Override
	public String toString() {
		final StringBuilder formatted = new StringBuilder();
		
		if (staffid == -1) {
			formatted.append("[No id]");
		} else {
			formatted.append("[").append(staffid).append("] ");
		}
		
		if (staffname == null) {
			formatted.append("No Name");
		} else {
			formatted.append(staffname);
		}
		
		if (stafflname == null) {
			formatted.append("No LName");
		} else {
			formatted.append(" "+stafflname);
		}
		
		if (staffdept == null) {
			formatted.append("No Dept.");
		} else {
			formatted.append(" ").append(staffdept).append(" ");
		}
		
		
		if (staffdrid == 0) {
			formatted.append("No DR ID");
		} else {
			formatted.append(staffdrid);
		}
		
		
		return formatted.toString();
	}
	
	public void delete() throws SQLException{
		
		Staff.LOGGER.warn("Called Delete function");
		
		Staff.LOGGER.debug("{}", staffid);
		
		final List<Staff> staffs = StaffHelper.getInstance().getStaff();
		
		Staff.LOGGER.debug("Looking for key to delete...");
		for(final Staff staff : staffs) {
			Staff.LOGGER.debug(".");
			if (staffid == staff.getStaffid()) {
				Staff.LOGGER.debug("Key already in the table");
				inlist = 1;
			}
		}
	
		if (inlist == 1) {
			Staff.LOGGER.debug("Staff found in the table, Deleting...");
			final String sql = "DELETE FROM STAFF WHERE STAFFID = ?";
			try (Connection connection = DBHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(1, staffid);
				pstmt.execute();
				inlist = -1;
			}
		}
	}
	
	public void save() throws SQLException{
		
	final List<Staff> staffs = StaffHelper.getInstance().getStaff();
		
		Staff.LOGGER.debug("Looking for key in the table...");
		for(final Staff staff : staffs) {
			Staff.LOGGER.debug(".");
			if (staffid == staff.getStaffid()) {
				Staff.LOGGER.debug("Key in the table");
				inlist = 1;
			}
		}
		
		try(Connection connection = DBHelper.getConnection();){
			if (inlist == 1){
				Staff.LOGGER.debug("Key not in the table, Inserting...");
				final String sql = "INSERT INTO SAFF (STAFFID, FNAME, LNAME, DEPT, DRID) VALUES (?, ?, ?, ?, ?)";
				try(PreparedStatement pstmt = connection.prepareStatement(sql)){	
					pstmt.setInt(1, staffid);
					pstmt.setString(2, staffname);
					pstmt.setString(3, stafflname);
					pstmt.setString(4, staffdept);
					pstmt.setInt(5, staffdrid);
					pstmt.execute();
					
				}
			} else {	
			Staff.LOGGER.debug("Key already in the table, Updating...");
			final String sql = "UPDATE STAFF SET FNAME = ?, LNAME = ?, DEPT = ?, DRID = ?  WHERE STAFFID = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(5, staffid);
				pstmt.setString(1, staffname);
				pstmt.setString(2, stafflname);
				pstmt.setString(3, staffdept);
				pstmt.setInt(4, staffdrid);
				pstmt.execute();
				
			}
			}		
		}
	}
}
