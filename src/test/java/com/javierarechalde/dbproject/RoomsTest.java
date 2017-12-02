package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RoomsTest {
	
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
	public void testSave() throws SQLException{
		Rooms r = new Rooms();
		r.setrid(125468);
		r.setrcap(10);
		r.setrtype("MESSI");
		r.save();
		
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
	
	}

}
