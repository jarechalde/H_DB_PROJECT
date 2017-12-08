   
--Populating the DOCTORS TABLE
INSERT INTO DOCTORS (DRID,FNAME,LNAME,SPECIALTY) VALUES
    (022587,'JOHNNY', 'MELAVO', 'TRAUMATOLOGY'),
    (021427,'GREGORY', 'HOUSE', 'ODONTOLOGY'),
    (028897,'ANA', 'ARECHALDE', 'DERMATOLOGY'),
    (025789, 'JAVIER', 'PELAZ', 'SURGERY'),
    (025859, 'JAVIER', 'ARECHALDE', 'SURGERY');
    
--Populating the STAFF TABLE
--We have a foreign key so if we have a doctor the ids must match
INSERT INTO STAFF (STAFFID, FNAME, LNAME, DEPT, DRID) VALUES
    (024587, 'JAVIER', 'ARECHALDE', 'DOCTORS', 025859),
    (024271, 'JAIME', 'LANNISTER', 'DOCTORS', 022587),
    (369587, 'GREGORY', 'HOUSE', 'DOCTORS', 021427),
    (025487, 'ANA', 'ARECHALDE', 'DOCTORS', 028897),
    (024017, 'JAVIER', 'PELAZ', 'DOCTORS', 025789),
    (102655, 'JOHN', 'SNOW', 'NURSES', NULL),
    (102365, 'WENDY', 'SIMMONS', 'RECEPTIONIST', NULL), 
    (256555, 'KIMI', 'RAIKKONEN', 'DRIVER', NULL),
    (189655, 'FERNANDO', 'ALONSO', 'DRIVER', NULL),
    (023545, 'JANE', 'SMITH', 'NURSES', NULL);

    
--Populating the PATIENTS TABLE
INSERT INTO PATIENTS (PATID,FNAME,LNAME,PHONEN,INSCARD) VALUES
    (021547,'JAVIER', 'ARECHALDE', 0214523698, 235647896),
    (032547,'TYRION', 'LANNISTER', 0420223698, 235647896),
    (089547,'PARKER', 'MANNY', 0214523698, 235647896),
    (059547,'FRIEDA', 'LOTUS', 0214523698, 235647896),
    (047547,'CASSANDRA', 'MONTY', 0214523698, 235647896),
    (098547,'DEVIN', 'DONGY', 0214523698, 235647896);

    
--Populating the DIAGNOSIS TABLE
INSERT INTO DIAGNOSIS (DIAGID,DIAGCOMM,DIAGDATE,PATID,DRID) VALUES
    (098257,'Broken nose with symptoms of rhynosth','2017-9-5',021547,021427), 
    (098127,'Take 2 pills a day, one in the morning an one in the afternoon.','2017-9-5',032547,028897), 
    (098547,'X-ray shows broken leg, put a cast','2017-9-5',021547,021427);
   
    
--Populating the ROOMS TABLE
INSERT INTO ROOMS (ROOMID,ROOMCAP,ROOMTYPE) VALUES
    (098257,3,'Bedroom'), 
    (092157,3,'Office'),
    (098967,5,'Op. Room'),
    (097827,5,'Op. Room'), 
    (896547,4,'Bedroom');
    
--Populating the appointments table
INSERT INTO APPOINTMENTS (APPID, APPPATID, APPDRID, APPROOMID, ASTART, AEND, APPCOM) VALUES
	(default, 032547,028897,098257,'2017-02-12','2017-03-12','Annual Check'),
	(default, 021547,021427,098257,'2017-02-12','2017-03-12','Retire cast');