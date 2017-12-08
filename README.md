# Hospital Database

This project's purpose is to create a Hospital database application in which you can add, modify or delete staff, facilities or patients data. It will also provide tools to edit appointments, see the appointments made, diagnose a patient, and see all diagnosis.

## Getting Started

This database was developed under Eclipse, and its based on a Maven project. The reason to choose Maven was its simplicity when it comes to add dependencies to the project and its high configurability.

After creating all the project resources, we will compile it on a simple .jar file, which the user will just need to click it. After clicking on the file, a GUI will open, and the user will be able to operate the database by clicking on the different buttons on the 

### Prerequisites

To run this application, you will need to have java 1.8 installed into your machine.

## Application Architecture

This application was built using Mave, this will help us to compile our project once everything is ready, and it will also help us to load in a simple way all the dependencies that we will use under this project's scome.

Once we are ready to build our project, we will just run this simple command on the Maven Build context.

```
clean install
```

And the Maven build tool will take care of everyth

## Operating the database

### Main menu

Once the .jar file is executed, a GUI will open showing different buttons for operating the Database.

### 1. Doctors Menu

On the doctors Menu, we will have two different choices. Appointments, and Diagnosis. 

In the appointment menu we will have two different options, one for showing the appointments an one for creating appointments.

#### Creating Appointments

For creating appointments we will need to use the doctor's ID, patient's ID, the room's ID and the start and end date of the appointment. Obviously, the end date of the appointment should be later than the start date of the appointment. The user will also be able to add comments to the appointment, in case needed.

#### Seeing Appointments

In case we want to see any appointment details, the user can click on any appointment shown in the appointments list. After clicking on each appointment, the data on the form in the right will be updated, showing the doctor's name, last name and specialty, patient's name, 
last name and phone number, room, start appointment date, end appointment date, appointment comments, and all the patient diagnosis.

### Diagnosing Patients

For diagnosing patients we will need to introduce the doctors ID, the patients ID and the diagnosis comments, the diagnosis date should be set by default to the date on which the appointment is being made.

### Seeing Diagnosis

In case we want to see any diagnosis details, the user can click on any diagnosis shown in the diagnosis list. After clicking on any diagnosis, the data on the form in the right will be updatesd showing the doctor's first name and last name, the patient's first name and last name, the date the patient was diagnosed, and the diagnosis comments.

### 2. Patients Menu

In this menu, we can Add, Update, or Remove patients from the hospital database. In case

### 3. Facilities Menu

In this menu we can Add, Update or Remove facilities data from the hospital database.

### 4. Staff Menu

In this menu, we can Add, Update, or Remove Staff from the hospital database.


## Authors

* **Javier Arechalde** - *Project Development* - [Jarechalde] (https://github.com/jarechalde)
