package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.Statement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class RoomsHelperTest {

	@After
	public void close() {
		DBHelper.getInstance().close();
	}
	
	@Before
	public void init() throws SQLException {
		DBHelper.getInstance().init();
		
		try (Connection connection = DBHelper.getConnection(); Statement stmt = connection.createStatement()) {
			stmt.execute("TRUNCATE TABLE ROOMS");		
		}	
	}
	
	@Test
	public void testLoad() throws SQLException {
		List<Room> rooms = RoomsHelper.getInstance().getRooms();
		Assert.assertNotNull(rooms);
		Assert.assertTrue(rooms.isEmpty());
		
		try(Connection connection = DBHelper.getConnection(); Statement stmt = connection.createStatement()){
			stmt.execute("INSERT INTO ROOMS (ROOMID,ROOMCAP,ROOMTYPE) VALUES (123456,12,'PENE')");
			stmt.execute("INSERT INTO ROOMS (ROOMID,ROOMCAP,ROOMTYPE) VALUES (123156,12,'PENE')");
			stmt.execute("INSERT INTO ROOMS (ROOMID,ROOMCAP,ROOMTYPE) VALUES (124446,12,'PENE')");
			
			rooms = RoomsHelper.getInstance().getRooms();
			Assert.assertNotNull(rooms);
			Assert.assertEquals(3, rooms.size());
			
			Room room = rooms.get(0);
			Assert.assertNotNull(room);
			Assert.assertEquals(123156, room.getrid());
			Assert.assertEquals(12, room.getrcap());
			Assert.assertEquals("PENE", room.getrtype());
			
			room = rooms.get(2);
			Assert.assertNotNull(room);
			Assert.assertEquals(124446, room.getrid());
			Assert.assertEquals(12, room.getrcap());
			Assert.assertEquals("PENE", room.getrtype());
			
			
		}
		
	}
	
}
