package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppointmentsHelper {
	
	private static final AppointmentsHelper INSTANCE = new AppointmentsHelper();
	private static final Logger LOGGER = LoggerFactory.getLogger(AppointmentsHelper.class);

	public static AppointmentsHelper getInstance() {
		return AppointmentsHelper.INSTANCE;
	}
	
	private AppointmentsHelper() {
		
	}
	
	public List<Appointment> getAppointments() throws SQLException{
		List<Appointment> Appointments = new ArrayList<>();
		
		String sql = "SELECT * FROM APPOINTMENTS ORDER BY APPID";
		try(Connection connection = DBHelper.getConnection();
				PreparedStatement psmt = connection.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery()){
			
			while(rs.next()) {
				final Appointment Appointment = new Appointment();

				AppointmentsHelper.LOGGER.info("Ayy we are doing it");
				
				Appointment.setAppid(rs.getInt("APPID"));
				Appointment.setPatid(rs.getInt("PATID"));
				Appointment.setDrid(rs.getInt("DRID"));
				Appointment.setRoomid(rs.getInt("ROOMID"));
				Appointment.setAstart(rs.getDate("ASTART"));
				Appointment.setAend(rs.getDate("AEND"));
				Appointment.setAppcom(rs.getString("APPCOM"));
				
				
				Appointments.add(Appointment);
			}
			
		}
		
		return Appointments;
	}
	
}
