package mysqlfirst;

//STEP 1. Import required packages
import java.sql.*;

public class JDBCExample {
 // JDBC driver name and database URL
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
 static final String DB_URL = "jdbc:mysql://localhost/";

 //  Database credentials
 static final String USER = "root";
 static final String PASS = "16387495p";
 
 public static void main(String[] args) {
 Connection conn = null;
 Statement stmt = null;
 try{
    //STEP 2: Register JDBC driver
    Class.forName("com.mysql.jdbc.Driver");

    //STEP 3: Open a connection
    System.out.println("Connecting to database...");
    conn = DriverManager.getConnection(DB_URL, USER, PASS);

    //STEP 4: Execute a query
    System.out.println("Creating database...");
    stmt = conn.createStatement();
    
    //First we drop the database, so we can create it later
    String sql = "DROP DATABASE HOSPITAL";
    stmt.executeUpdate(sql);
    
    sql = "CREATE DATABASE HOSPITAL";
    stmt.executeUpdate(sql); 
 
     
    //Creating the DOCTORS table
    sql = ("CREATE TABLE HOSPITAL.DOCTORS " +
    "(DRID INT(6)," +
    "FNAME VARCHAR(30) NOT NULL," +
    "LNAME VARCHAR(30) NOT NULL," +
    "SPECIALTY VARCHAR(30) NOT NULL," +
    "PRIMARY KEY (DRID))");
    stmt.executeUpdate(sql);
    
    //Creating the STAFF table
    sql = ("CREATE TABLE HOSPITAL.STAFF " +
    "(STAFFID INT(6) NOT NULL," +
    "FNAME VARCHAR(30) NOT NULL," +
    "LNAME VARCHAR(30) NOT NULL," +
    "DEPT VARCHAR(30) NOT NULL," +
    "DRID INT(6)," +
    "PRIMARY KEY (STAFFID)," + 
    "FOREIGN KEY (DRID) REFERENCES HOSPITAL.DOCTORS(DRID))");
    stmt.executeUpdate(sql);
    
    //Creating the PATIENTS table
    sql = ("CREATE TABLE HOSPITAL.PATIENTS " +
   "(PATID INT(6) NOT NULL," +
   "FNAME VARCHAR(30) NOT NULL," +
   "LNAME VARCHAR(30) NOT NULL," +
   "PHONEN INT(10) NOT NULL," +
   "DATEIN DATE NOT NULL," +
   "DATEOUT DATE NOT NULL," +
   "INSCARD INT(9)," +
   "PRIMARY KEY (PATID))");
   stmt.executeUpdate(sql);
   
   //Creating the DIAGNOSIS table
   sql = ("CREATE TABLE HOSPITAL.DIAGNOSIS " +
   "(DIAGID INT(6) NOT NULL," +
   "DIAGCOMM TEXT," +
   "DIAGDATE DATE NOT NULL," +
   "PATID INT(6) NOT NULL," +
   "DRID INT(6) NOT NULL," +
   "PRIMARY KEY (DIAGID)," +
   "FOREIGN KEY (DRID) REFERENCES HOSPITAL.DOCTORS(DRID)," +
   "FOREIGN KEY (PATID) REFERENCES HOSPITAL.PATIENTS(PATID))");
   stmt.executeUpdate(sql);
   
   //Creating the ROOMS table
   sql = ("CREATE TABLE HOSPITAL.ROOMS " +
   "(ROOMID INT(6) NOT NULL," +
   "ROOMCAP INT(2) NOT NULL," +
   "ROOMTYPE VARCHAR(30) NOT NULL," +
   "PRIMARY KEY (ROOMID))");
   stmt.executeUpdate(sql);
   
    
    //Populating the DOCTORS TABLE
    sql = ("INSERT INTO HOSPITAL.DOCTORS (DRID,FNAME,LNAME,SPECIALTY) VALUES " +
    "(021547,'JAVIER', 'ARECHALDE', 'GINECOLOGY')," +
    "(022587,'JOHNNY', 'MELAVO', 'TRAUMATOLOGY')," + 
    "(021427,'KEPA', 'HAMECHO', 'ODONTOLOGY')," + 
    "(028897,'ANA', 'ARECHALDE', 'DERMATOLOGY')," + 
    "(025789, 'JAVIER', 'PELAZ', 'SURGERY')");
    stmt.executeUpdate(sql);
    
    //Populating the STAFF TABLE
    //We have a foreign key so if we have a doctor the ids must match
    sql = ("INSERT INTO HOSPITAL.STAFF (STAFFID, FNAME, LNAME, DEPT, DRID) VALUES" + 
    "(024587, 'JAVIER', 'ARECHALDE', 'DOCTORS', (SELECT DRID FROM HOSPITAL.DOCTORS WHERE FNAME='JAVIER' && LNAME='ARECHALDE'))," +
    "(024271, 'JOHNNY', 'MELAVO', 'DOCTORS', (SELECT DRID FROM HOSPITAL.DOCTORS WHERE FNAME='JOHNNY' && LNAME='MELAVO'))," +
    "(369587, 'KEPA', 'HAMECHO', 'DOCTORS', (SELECT DRID FROM HOSPITAL.DOCTORS WHERE FNAME='KEPA' && LNAME='HAMECHO'))," +
    "(025487, 'ANA', 'ARECHALDE', 'DOCTORS', (SELECT DRID FROM HOSPITAL.DOCTORS WHERE FNAME='ANA' && LNAME='ARECHALDE'))," +
    "(024017, 'JAVIER', 'PELAZ', 'DOCTORS', (SELECT DRID FROM HOSPITAL.DOCTORS WHERE FNAME='JAVIER' && LNAME='PELAZ'))," +
    "(102655, 'JOHN', 'SNOW', 'NURSES', (SELECT DRID FROM HOSPITAL.DOCTORS WHERE FNAME='JOHN' && LNAME='SNOW'))," + 
    "(102365, 'WENDY', 'BURGER', 'RECEPTIONIST', (SELECT DRID FROM HOSPITAL.DOCTORS WHERE FNAME='WENDY' && LNAME='BURGER'))," + 
    "(256555, 'KIMI', 'RAIKKONEN', 'DRIVER', (SELECT DRID FROM HOSPITAL.DOCTORS WHERE FNAME='KIMI' && LNAME='RAIKKONEN'))," + 
    "(189655, 'FERNANDO', 'ALONSO', 'DRIVER', (SELECT DRID FROM HOSPITAL.DOCTORS WHERE FNAME='FERNANDO' && LNAME='ALONSO'))," + 
    "(023545, 'JANE', 'SMITH', 'NURSES', (SELECT DRID FROM HOSPITAL.DOCTORS WHERE FNAME='JANE' && LNAME='SMITH'))");
    stmt.executeUpdate(sql);
    
    //Populating the PATIENTS TABLE
    sql = ("INSERT INTO HOSPITAL.PATIENTS (PATID,FNAME,LNAME,PHONEN,DATEIN,DATEOUT,INSCARD) VALUES " +
    "(021547,'JAVIER', 'ARECHALDE', 0214523698, '2022-12-31','2009-4-30',235647896)," +
    "(032547,'HOLY', 'GUACAMOLE', 0214523698, '1999-11-22','2009-4-30',235647896)," +
    "(089547,'PATATILLAS', 'FRITAS', 0214523698, '1999-11-22','2009-4-30',235647896)," +
    "(059547,'WAZZUP', 'BUDDY', 0214523698, '1999-11-22','2009-4-30',235647896)," +
    "(047547,'OUTOF', 'IDEAS', 0214523698, '1999-11-22','2009-4-30',235647896)," +
    "(098547,'WTFIM', 'DOING', 0214523698, '1999-11-22','2009-4-30',235647896)");
    stmt.executeUpdate(sql);
    
    //Populating the DIAGNOSIS TABLE
    sql = ("INSERT INTO HOSPITAL.DIAGNOSIS (DIAGID,DIAGCOMM,DIAGDATE,PATID,DRID) VALUES " +
    "(098257,'RIP my friend we will always miss you, lol','2017-9-5',021547,021547)," + 
    "(098547,'He was a good cat, but an even better programmer','2017-9-5',021547,021547)");
    stmt.executeUpdate(sql);
   
    //Populating the ROOMS TABLE
    sql = ("INSERT INTO HOSPITAL.ROOMS (ROOMID,ROOMCAP,ROOMTYPE) VALUES " +
    "(098257,3,'Bedroom')," + 
    "(092157,3,'Office')," + 
    "(098967,5,'Op. Room')," + 
    "(097827,5,'Op. Room')," + 
    "(896547,4,'Bedroom')");
    stmt.executeUpdate(sql);
    
    System.out.println("Database created successfully...");
 }catch(SQLException se){
    //Handle errors for JDBC
    se.printStackTrace();
 }catch(Exception e){
    //Handle errors for Class.forName
    e.printStackTrace();
 }finally{
    //finally block used to close resources
    try{
       if(stmt!=null)
          stmt.close();
    }catch(SQLException se2){
    }// nothing we can do
    try{
       if(conn!=null)
          conn.close();
    }catch(SQLException se){
       se.printStackTrace();
    }//end finally try
 }//end try
 System.out.println("Goodbye!");
}//end main
}//end JDBCExample