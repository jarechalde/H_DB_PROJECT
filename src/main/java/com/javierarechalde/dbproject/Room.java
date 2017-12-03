package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;

public class Room {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Room.class);

	private int rid;
	private int inlist = 0;
	private int rcap;
	private String rtype;
	
	public int getrid() {
		return rid;
	}
	public void setrid(int rid) {
		this.rid = rid;
	}
	public int getrcap() {
		return rcap;
	}
	public void setrcap(int rcap) {
		this.rcap = rcap;
	}
	public String getrtype() {
		return rtype;
	}
	public void setrtype(String rtype) {
		this.rtype = rtype;
	}
	
	public void delete() throws SQLException{
		Room.LOGGER.warn("Called Delete function");
		
		Room.LOGGER.debug("{}", rid);
		
		final List<Room> rooms = RoomsHelper.getInstance().getRooms();
		
		Room.LOGGER.debug("Looking for key to delete...");
		for(final Room room : rooms) {
			Room.LOGGER.debug(".");
			if (rid == room.getrid()) {
				Room.LOGGER.debug("Key in the table");
				inlist = 1;
			}
		}
		
		if (inlist == 1) {
			Room.LOGGER.debug("Room found in the table, Deleting...");
			final String sql = "DELETE FROM ROOMS WHERE ROOMID = ?";
			try (Connection connection = DBHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(1, rid);
				pstmt.execute();
				rid = -1;
			}
		}
	}
	
	public void save() throws SQLException{
		
		final List<Room> rooms = RoomsHelper.getInstance().getRooms();
		
		Room.LOGGER.debug("Looking for key in the table...");
		for(final Room room : rooms) {
			Room.LOGGER.debug(".");
			if (rid == room.getrid()) {
				Room.LOGGER.debug("Key in the table");
				inlist = 1;
			}
		}
		
		try(Connection connection = DBHelper.getConnection();){
			if (inlist == 0){
				Room.LOGGER.debug("Key not in the table, Inserting...");
				final String sql = "INSERT INTO ROOMS (ROOMID,ROOMCAP,ROOMTYPE) VALUES (?, ?, ?)";
				try(PreparedStatement pstmt = connection.prepareStatement(sql)){	
					pstmt.setInt(1, rid);
					pstmt.setInt(2, rcap);
					pstmt.setString(3, rtype);
					pstmt.execute();
					
				}
			} else {	
			Room.LOGGER.debug("Key already in the table, Updating...");
			final String sql = "UPDATE ROOMS SET ROOMCAP = ?, ROOMTYPE = ? WHERE ROOMID = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(1, rcap);
				pstmt.setString(2, rtype);
				pstmt.setInt(3, rid);
				pstmt.execute();
				
			}
			}		
		}
	}
}
