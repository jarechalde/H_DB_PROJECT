package com.javierarechalde.dbproject;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientsHelper {
	
	private static final PatientsHelper INSTANCE = new PatientsHelper();

	public static PatientsHelper getInstance() {
		return PatientsHelper.INSTANCE;
	}
	
	private PatientsHelper() {
		
	}
	
	public List<Patient> getPatients() throws SQLException{
		List<Patient> patients = new ArrayList<>();
		
		String sql = "SELECT * FROM PATIENTS ORDER BY PATID";
		try(Connection connection = DBHelper.getConnection();
				PreparedStatement psmt = connection.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery()){
			
			while(rs.next()) {
				final Patient patient = new Patient();
				patient.setPatid(rs.getInt("PATID"));
				patient.setFname(rs.getString("FNAME"));
				patient.setLname(rs.getString("LNAME"));
				patient.setPnumber(rs.getInt("PHONEN"));
				patient.setInscard(rs.getInt("INSCARD"));
				patients.add(patient);
			}
			
		}
		
		return patients;
	}
	
}
