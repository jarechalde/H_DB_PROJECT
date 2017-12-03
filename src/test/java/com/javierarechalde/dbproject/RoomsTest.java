package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class RoomsTest {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(RoomsTest.class);
	
	//Executed before any test that we put under @test
	@Before
	public void init() throws SQLException {
		DBHelper.getInstance().init();
		
		try(Connection connection = DBHelper.getConnection(); Statement stmt = connection.createStatement()){
			stmt.execute("TRUNCATE TABLE ROOMS");
		}
	}
	
	@After
	public void close() {
		DBHelper.getInstance().close();
	}
	
	@Test
	public void testDelete() throws SQLException{
		
		try(Connection connection = DBHelper.getConnection(); Statement stmt = connection.createStatement()){
			stmt.execute("INSERT INTO ROOMS (ROOMID,ROOMCAP,ROOMTYPE) VALUES (123456,12,'PENE')");
			stmt.execute("INSERT INTO ROOMS (ROOMID,ROOMCAP,ROOMTYPE) VALUES (123556,12,'PENE')");
			stmt.execute("INSERT INTO ROOMS (ROOMID,ROOMCAP,ROOMTYPE) VALUES (124446,12,'PENE')");
			
			List<Room> rooms = RoomsHelper.getInstance().getRooms();
			Assert.assertEquals(3, rooms.size());
		
			final Room room = rooms.get(1);
			//Checking if before and after the save, the id == -1  and the id
			Assert.assertEquals(123556, room.getrid());
			room.delete();
			Assert.assertEquals(-1,room.getrid());
			
			//Checking if the row was deleted
			rooms = RoomsHelper.getInstance().getRooms();
			Assert.assertEquals(2, rooms.size());
			Assert.assertEquals(123456, rooms.get(0).getrid());
			Assert.assertEquals(124446, rooms.get(1).getrid());
			
		}
	}
	
	@Test
	public void testSave() throws SQLException{
		
		final Room r = new Room();
		r.setrid(125468);
		r.setrcap(10);
		r.setrtype("MESSI");
		
		//Checking if before and after the save, the id == -1  and the id
		//Assert.assertEquals(125468, r.getrid());
		r.save();
		Assert.assertEquals(125468, r.getrid());
		
		
		try(Connection connection = DBHelper.getConnection(); Statement stmt = connection.createStatement()){
			try(ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM ROOMS")){
				Assert.assertTrue("Count should return at least one row", rs.next());
				Assert.assertEquals(1L,rs.getLong(1));
				Assert.assertFalse("Count should not return more than one row", rs.next());
			}
			
			try(ResultSet rs = stmt.executeQuery("SELECT * FROM ROOMS")){
				Assert.assertTrue("Select should return at least one row", rs.next());
				Assert.assertEquals("MESSI", rs.getString("ROOMTYPE"));
				Assert.assertFalse("Select should not return more than one row", rs.next());
			}
			
		}
		
		r.setrid(233454);
		r.save();
		
		//Checking if it was updated
		Assert.assertEquals(233454, r.getrid());
		Assert.assertEquals("MESSI", r.getrtype());
		Assert.assertEquals(10, r.getrcap());
		
		final List<Room> rooms = RoomsHelper.getInstance().getRooms();
		Assert.assertEquals(2, rooms.size());
		
		final Room room = rooms.get(0);
		Assert.assertEquals(125468, room.getrid());
		Assert.assertEquals("MESSI", room.getrtype());
		Assert.assertEquals(10, room.getrcap());
		
	
	}

}
