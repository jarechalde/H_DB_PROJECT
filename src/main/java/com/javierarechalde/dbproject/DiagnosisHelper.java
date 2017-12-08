package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiagnosisHelper {
	
	private static final DiagnosisHelper INSTANCE = new DiagnosisHelper();
	private static final Logger LOGGER = LoggerFactory.getLogger(DiagnosisHelper.class);

	public static DiagnosisHelper getInstance() {
		return DiagnosisHelper.INSTANCE;
	}
	
	private DiagnosisHelper() {
		
	}
	
	public List<Diagnosis> getDiagnosis() throws SQLException{
		List<Diagnosis> Diagnosiss = new ArrayList<>();
		
		String sql = "SELECT * FROM DIAGNOSIS ORDER BY DIAGID";
		try(Connection connection = DBHelper.getConnection();
				PreparedStatement psmt = connection.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery()){
			
			while(rs.next()) {
				final Diagnosis Diagnosis = new Diagnosis();

				DiagnosisHelper.LOGGER.info("Getting Diagnosis");
				
				Diagnosis.setDiagid(rs.getInt("DIAGID"));
				Diagnosis.setDiagcomm(rs.getString("DIAGCOMM"));
				Diagnosis.setDiagdate(rs.getDate("DIAGDATE"));
				Diagnosis.setDrid(rs.getInt("DRID"));
				Diagnosis.setPatid(rs.getInt("PATID"));
				
				Diagnosiss.add(Diagnosis);
			}
			
		}
		
		return Diagnosiss;
	}
	
}
