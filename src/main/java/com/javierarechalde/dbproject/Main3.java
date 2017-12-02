package com.javierarechalde.dbproject;

import java.util.List;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main3 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main3.class);
		
	public static void main(final String[] args) {
		
		//Initializing
		DBHelper.getInstance().init();
		
		try {
			
			Room r = new Room();
			r.setrid(121318);
			r.setrcap(215);
		    r.setrtype("MESSI");
			r.save();
			
			final List<Room> rooms = RoomsHelper.getInstance().getRooms();
			
			for(final Room room : rooms) {
				Main3.LOGGER.debug(">> [{}] {} {}", room.getrid(), room.getrcap(), room.getrtype());
			}
			
		} catch (final SQLException e) {
			Main3.LOGGER.error("Failed to Save the room", e);
		}
		
		
		//Closing
        DBHelper.getInstance().close();		
        LOGGER.info("DONE");
		
		
	}

}