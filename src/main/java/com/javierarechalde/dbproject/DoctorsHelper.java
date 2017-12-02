
package com.javierarechalde.dbproject;

import java.util.List;

public class DoctorsHelper {
	
	private static final DoctorsHelper INSTANCE = new DoctorsHelper();

	public static DoctorsHelper getInstance() {
		return INSTANCE;
	}
	
	public List<Doctors> getDoctors(){
		return null;
	}
	
}
