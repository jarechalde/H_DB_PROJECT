
package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorsHelper {
	
	private static final DoctorsHelper INSTANCE = new DoctorsHelper();

	public static DoctorsHelper getInstance() {
		return DoctorsHelper.INSTANCE;
	}
	
	private DoctorsHelper() {
		
	}
	
	public List<Doctor> getDoctors() throws SQLException{
		List<Doctor> doctors = new ArrayList<>();
		
		String sql = "SELECT * FROM DOCTORS ORDER BY FNAME";
		try(Connection connection = DBHelper.getConnection();
				PreparedStatement psmt = connection.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery()){
			
			while(rs.next()) {
				final Doctor doctor = new Doctor();
				doctor.setDrid(rs.getInt("DRID"));
				doctor.setFname(rs.getString("FNAME"));
				doctor.setLname(rs.getString("LNAME"));
				doctor.setSpecialty(rs.getString("SPECIALTY"));
				doctors.add(doctor);
			}
			
		}
		
		return doctors;
	}
	
}
