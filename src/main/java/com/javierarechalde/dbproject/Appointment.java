package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;

public class Appointment {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Appointment.class);

	private int appid;
	private int patid;
	private int drid;
	private int roomid;
	private Date astart;
	private Date aend;
	private String appcom;
	
	public int getAppid() {
		return appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
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

	public int getRoomid() {
		return roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public Date getAstart() {
		return astart;
	}

	public void setAstart(Date astart) {
		this.astart = astart;
	}

	public Date getAend() {
		return aend;
	}

	public void setAend(Date aend) {
		this.aend = aend;
	}

	public String getAppcom() {
		return appcom;
	}

	public void setAppcom(String appcom) {
		this.appcom = appcom;
	}

	private int inlist = 0;
	
	@Override
	public String toString() {
		final StringBuilder formatted = new StringBuilder();
		
		if (appid == -1) {
			formatted.append("[No id]");
		} else {
			formatted.append("[").append(appid).append("] ");
		}
		
		if (patid == 0) {
			formatted.append("No Patient ID");
		} else {
			formatted.append(patid);
		}
		
		if (drid == 0) {
			formatted.append("No Doctor ID");
		} else {
			formatted.append(" "+drid);
		}
		
		if (roomid == 0) {
			formatted.append("No Room ID");
		} else {
			formatted.append(" ").append(roomid).append(" ");
		}
		
		
		if (astart == null) {
			formatted.append("No Start Date");
		} else {
			formatted.append(astart);
		}
		
		if (aend == null) {
			formatted.append("No End Date");
		} else {
			formatted.append(aend);
		}
		
		if (appcom == null) {
			formatted.append("No Comments");
		} else {
			formatted.append(appcom);
		}
		
		return formatted.toString();
	}
	
	public void delete() throws SQLException{
		
		Appointment.LOGGER.warn("Called Delete function");
		
		Appointment.LOGGER.debug("{}", appid);
		
		final List<Appointment> Appointments = AppointmentsHelper.getInstance().getAppointments();
		
		
		if (appid != -1) {
			Appointment.LOGGER.debug("Appointment found in the table, Deleting...");
			final String sql = "DELETE FROM APPOINTMENTS WHERE APPID = ?";
			try (Connection connection = DBHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(1, appid);
				pstmt.execute();
				patid = -1;
			}
		}
	}
	
	public void save() throws SQLException{
		
		final List<Appointment> Appointments = AppointmentsHelper.getInstance().getAppointments();
		
		
		try(Connection connection = DBHelper.getConnection();){
			if (appid == -1){
				Appointment.LOGGER.debug("Key not in the table, Inserting...");
				final String sql = "INSERT INTO APPOINTMENTS (APPPATID,APPDRID,APPROOMID,ASTART,AEND,APPCOM) VALUES (?, ?, ?, ?, ?, ?)";
				try(PreparedStatement pstmt = connection.prepareStatement(sql)){	
					pstmt.setInt(1, patid);
					pstmt.setInt(2, drid);
					pstmt.setInt(3, roomid);
					pstmt.setDate(4, astart);
					pstmt.setDate(5, aend);
					pstmt.setString(6, appcom);
					pstmt.execute();
					
				}
			} else {	
			Appointment.LOGGER.debug("Key already in the table, Updating...");
			Appointment.LOGGER.debug("Appointment id",appid);
			final String sql = "UPDATE APPOINTMENTS SET APPPATID = ?, APPDRID = ?, APPROOMID = ?, ASTART = ?, AEND = ?, APPCOM = ? WHERE APPID = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(1, patid);
				pstmt.setInt(2, drid);
				pstmt.setInt(3, roomid);
				pstmt.setDate(4, astart);
				pstmt.setDate(5, aend);
				pstmt.setString(6, appcom);
				pstmt.setInt(7, appid);
				pstmt.execute();
				
			}
			}		
		}
	}
}
