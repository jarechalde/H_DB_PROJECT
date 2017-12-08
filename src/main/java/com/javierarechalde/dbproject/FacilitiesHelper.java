package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacilitiesHelper {
	
	private static final FacilitiesHelper INSTANCE = new FacilitiesHelper();

	public static FacilitiesHelper getInstance() {
		return FacilitiesHelper.INSTANCE;
	}
	
	private FacilitiesHelper() {
		
	}
	
	public List<Facilities> getFacilities() throws SQLException{
		List<Facilities> facilities = new ArrayList<>();
		
		String sql = "SELECT * FROM FACILITIES ORDER BY FACID";
		try(Connection connection = DBHelper.getConnection();
				PreparedStatement psmt = connection.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery()){
			
			while(rs.next()) {
				final Facilities facility = new Facilities();
				
				facility.setFacid(rs.getInt("FACID"));
				facility.setFacname(rs.getString("FACNAME"));
				facility.setFacbuild(rs.getString("FACBUILD"));
				facility.setFacplant(rs.getInt("FACPLANT"));
				facility.setFactype(rs.getString("FACTYPE"));
				facility.setFacdesc(rs.getString("FACDESC"));
				
				facilities.add(facility);
			}
			
		}
		
		return facilities;
	}
	
}
