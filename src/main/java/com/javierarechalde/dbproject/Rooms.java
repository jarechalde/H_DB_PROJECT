package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class Rooms {

	private int rid;
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
	
	public void save() throws SQLException{
		String sql = "INSERT INTO ROOMS (ROOMID,ROOMCAP,ROOMTYPE) VALUES (?, ?, ?)";
		try(Connection connection = DBHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
			
			pstmt.setInt(1, rid);
			pstmt.setInt(2, rcap);
			pstmt.setString(3, rtype);
			pstmt.execute();
			
		}
		
	}
	
}
