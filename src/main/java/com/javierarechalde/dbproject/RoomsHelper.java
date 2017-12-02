package com.javierarechalde.dbproject;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomsHelper {
	
	private static final RoomsHelper INSTANCE = new RoomsHelper();

	public static RoomsHelper getInstance() {
		return RoomsHelper.INSTANCE;
	}
	
	private RoomsHelper() {
		
	}
	
	public List<Room> getRooms() throws SQLException{
		List<Room> rooms = new ArrayList<>();
		
		String sql = "SELECT * FROM ROOMS ORDER BY ROOMID";
		try(Connection connection = DBHelper.getConnection();
				PreparedStatement psmt = connection.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery()){
			
			while(rs.next()) {
				final Room room = new Room();
				room.setrid(rs.getInt("ROOMID"));
				room.setrcap(rs.getInt("ROOMCAP"));
				room.setrtype(rs.getString("ROOMTYPE"));
				rooms.add(room);
			}
			
		}
		
		return rooms;
	}
	
}
