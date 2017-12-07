--Creating the DOCTORS TABLE
CREATE TABLE DOCTORS (
    DRID INT(6),
    FNAME VARCHAR(30) NOT NULL,
    LNAME VARCHAR(30) NOT NULL,
    SPECIALTY VARCHAR(30) NOT NULL,
    PRIMARY KEY (DRID));
 
--Creating the STAFF table
CREATE TABLE STAFF(
	STAFFID INT(6) NOT NULL,
    FNAME VARCHAR(30) NOT NULL,
    LNAME VARCHAR(30) NOT NULL,
    DEPT VARCHAR(30) NOT NULL,
    DRID INT(6),
    PRIMARY KEY (STAFFID),
    FOREIGN KEY (DRID) REFERENCES DOCTORS(DRID));
    
--Creating the PATIENTS table
CREATE TABLE PATIENTS(
   PATID INT(6) NOT NULL,
   FNAME VARCHAR(30) NOT NULL,
   LNAME VARCHAR(30) NOT NULL,
   PHONEN INT(10) NOT NULL,
   INSCARD INT(9),
   PRIMARY KEY (PATID));
   
--Creating the DIAGNOSIS table
CREATE TABLE DIAGNOSIS
   (DIAGID INT(6) NOT NULL,
   DIAGCOMM TEXT,
   DIAGDATE DATE NOT NULL,
   PATID INT(6) NOT NULL,
   DRID INT(6) NOT NULL,
   PRIMARY KEY (DIAGID),
   FOREIGN KEY (DRID) REFERENCES DOCTORS(DRID),
   FOREIGN KEY (PATID) REFERENCES PATIENTS(PATID));	
   
--Creating the ROOMS table
CREATE TABLE ROOMS
   (ROOMID INT(6) NOT NULL,
   ROOMCAP INT(2) NOT NULL,
   ROOMTYPE VARCHAR(30) NOT NULL,
   PRIMARY KEY (ROOMID));
   
   --Creating the appointments table
CREATE TABLE APPOINTMENTS
	(APPID INT NOT NULL AUTO_INCREMENT,
	PATID INT(6) NOT NULL,
	DRID INT(6) NOT NULL,
	ROOMID INT(6) NOT NULL,
	ASTART DATE,
	AEND DATE, 
	APPCOM TEXT,
	PRIMARY KEY (APPID),
	FOREIGN KEY (DRID) REFERENCES DOCTORS(DRID),
	FOREIGN KEY (PATID) REFERENCES PATIENTS(PATID),
	FOREIGN KEY (ROOMID) REFERENCES ROOMS(ROOMID));