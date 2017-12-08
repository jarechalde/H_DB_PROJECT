package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffHelper {
	
	private static final StaffHelper INSTANCE = new StaffHelper();

	public static StaffHelper getInstance() {
		return StaffHelper.INSTANCE;
	}
	
	private StaffHelper() {
		
	}
	
	public List<Staff> getStaff() throws SQLException{
		List<Staff> staffs = new ArrayList<>();
		
		String sql = "SELECT * FROM STAFF ORDER BY STAFFID";
		try(Connection connection = DBHelper.getConnection();
				PreparedStatement psmt = connection.prepareStatement(sql);
				ResultSet rs = psmt.executeQuery()){
			
			while(rs.next()) {
				final Staff staff = new Staff();
				
				staff.setStaffid(rs.getInt("STAFFID"));
				staff.setStaffname(rs.getString("FNAME"));
				staff.setStafflname(rs.getString("LNAME"));
				staff.setStaffdept(rs.getString("DEPT"));
				staff.setStaffdrid(rs.getInt("DRID"));
				
				staffs.add(staff);
			}
			
		}
		
		return staffs;
	}
	
}
