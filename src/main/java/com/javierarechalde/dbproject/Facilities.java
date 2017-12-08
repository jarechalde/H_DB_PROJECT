package com.javierarechalde.dbproject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;

public class Facilities {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Facilities.class);

	private int facid;
	private String facname;
	private String facbuild;
	private int facplant;
	private String factype;
	private String facdesc;
	private int inlist = 0;
	
	
	public int getFacid() {
		return facid;
	}

	public void setFacid(int facid) {
		this.facid = facid;
	}

	public String getFacname() {
		return facname;
	}

	public void setFacname(String facname) {
		this.facname = facname;
	}

	public String getFacbuild() {
		return facbuild;
	}

	public void setFacbuild(String facbuild) {
		this.facbuild = facbuild;
	}

	public int getFacplant() {
		return facplant;
	}

	public void setFacplant(int facplant) {
		this.facplant = facplant;
	}

	public String getFactype() {
		return factype;
	}

	public void setFactype(String factype) {
		this.factype = factype;
	}

	public String getFacdesc() {
		return facdesc;
	}

	public void setFacdesc(String facdesc) {
		this.facdesc = facdesc;
	}
	
	
	@Override
	public String toString() {
		final StringBuilder formatted = new StringBuilder();
		
		if (facid == -1) {
			formatted.append("[No id]");
		} else {
			formatted.append("[").append(facid).append("] ");
		}
		
		if (facname == null) {
			formatted.append("No Fac. Name");
		} else {
			formatted.append(facname);
		}
		
		if (facbuild == null) {
			formatted.append("No Building");
		} else {
			formatted.append(" "+facbuild);
		}
		
		if (facplant == 0) {
			formatted.append("No Plant");
		} else {
			formatted.append(" ").append(facplant).append(" ");
		}
		
		
		if (factype == null) {
			formatted.append("No Fac.Type");
		} else {
			formatted.append(factype);
		}
		
		if (facdesc == null) {
			formatted.append("No Description");
		} else {
			formatted.append(facdesc);
		}
		
		return formatted.toString();
	}
	
	public void delete() throws SQLException{
		
		Facilities.LOGGER.warn("Called Delete function");
		
		Facilities.LOGGER.debug("{}", facid);
	
		if (facid != -1) {
			Facilities.LOGGER.debug("Facility found in the table, Deleting...");
			final String sql = "DELETE FROM FACILITIES WHERE FACID = ?";
			try (Connection connection = DBHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(1, facid);
				pstmt.execute();
				facid = -1;
			}
		}
	}
	
	public void save() throws SQLException{
		
		
		try(Connection connection = DBHelper.getConnection();){
			if (facid == -1){
				Facilities.LOGGER.debug("Key not in the table, Inserting...");
				final String sql = "INSERT INTO FACILITIES (FACNAME,FACBUILD,FACPLANT,FACTYPE,FACDESC) VALUES (?, ?, ?, ?, ?)";
				try(PreparedStatement pstmt = connection.prepareStatement(sql)){	
					pstmt.setString(1, facname);
					pstmt.setString(2, facbuild);
					pstmt.setInt(3, facplant);
					pstmt.setString(4, factype);
					pstmt.setString(5, facdesc);
					pstmt.execute();
					
				}
			} else {	
			Facilities.LOGGER.debug("Key already in the table, Updating...");
			final String sql = "UPDATE FACILITIES SET FACNAME = ?, FACBUILD = ?, FACPLANT = ?, FACTYPE = ?, FACDESC = ?  WHERE FACID = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(sql)){
				pstmt.setInt(6, facid);
				pstmt.setString(1, facname);
				pstmt.setString(2, facbuild);
				pstmt.setInt(3, facplant);
				pstmt.setString(4, factype);
				pstmt.setString(5, facdesc);
				pstmt.execute();
				
			}
			}		
		}
	}
}
