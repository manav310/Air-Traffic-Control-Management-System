# Air Traffic Control Management System
## Description

Ground-based air traffic controllers help planes navigate within controlled airspace. They also offer advisory services to aircraft in uncontrolled airspace. Air traffic control (ATC) aims to prevent crashes, organise and speed up traffic flow, and provide pilots with vital information and support. The application will include a variety of features such as aircraft information which include details of all the aircrafts which are about to depart, arrive or already at the airport. The controller would be able to log the aircraft details on touchdown and take-off and would also be able to retrieve in-transit flight information. Moreover, the controller would be able to access and manage administrative aircraft details including cargo load, fuel status etc. A daily flight schedule could be obtained from the underlying database. Hangar status, maintenance crew and emergency vehicle logs would also be maintained.

## Features

* **Aircraft Entry/Exit Log:** The controller would have the ability to log the landing and take-off of aircrafts, thereby keeping track of every aircraft at the airport.

* **2. Addition/Removal of Flight Routes:** The controller would be able to register new flights so that they are authorized to land at the airport. The ability to remove existing flight paths would also be provided.

* **Landing Authorization Check:** ATC would have the ability to verify the landing of any aircraft with the help of the pre-existing flight-route database and availability of free-runways.

* **Updation of Flight-Vitals:** After getting the required signal from the ground crew, the controller would be able to update the flight vitals which include fuel levels and cargo loading.

* **Take-off Authorization:** The controller would only be able to authorize an aircraft to take-off if the necessary parameters (fuel, cargo) are updated and/or are above the designated threshold and availability of free-runways.

* **Multi-Level Clearance:** The level of clearance an individual at the ATC tower has would determine the features accessible to them.

* **Query-Log:** All the recent entries done by users would be stored for read-only access.

* **Analytics:** ATC would be able to retrieve necessary information like weather, wind-speed etc. The ability to manage the operational emergency vehicles would also be provided. 

* **Runway-Availability:** The controller would have the ability to manage the availability of runways. They would be able to assign status (like free, busy, maintenance) to individual runways which would also be linked to landing and take-off authorization.

* **Log-In Portal:** A log-in portal would be implemented to would multiple users. Login credentials would be saved. Admin would also be able create new users and delete existing users.

## Prerequisites

* Java Development Kit (JDK) installed on the system.
* A local SQL server to construct the database.
* An IDE (preferably Eclipse, as the project was built using the same, but can use any) for further development and changes to the source code.

## Setting up and Installing Dependencies

* Clone the repository or download the source code.
* Run the mySQL file in the 'SQL_DDL_Files' folder in order to create the database.
* Open Eclipse IDE and select the 'Import' option in the 'Package Explorer' tab.
* Locate the project folder on your system and select it
* After sucessfully importing the project, you would need to add a few dependencies to the project build path,
    * Right click on the project folder in the 'Package Explorer' tab and select 'Properties'
    * Select the 'Java Build Path' option and navigate to the 'Libraries' tab.
    * To the 'Modulepath', add all the dependencies given in the 'Project_Dependencies' folder of the project as External JAR files.
    * Also select the JRE System Library [JavaSE-11] as the system library.
* When all the dependencies built, run the program to execute the application.

## !NOTE!

* The 'Project_Dependencies' folder is not be imported with the source code as its contents are installed at a later stage.
* An additonal .exe file is also provided with the source code in order to eliminate the need for setting up the project. It is not to be import with the source code.
* The 'SQL_DDL_Files' folder is also to be omitted when importing the project to an IDE. It is just to consturct the database in mySQL.
  
## Usage

1. Execute runner code.

2. Login with your MySQL Username and Password on the SQL Connection Manager page.

3. Login with valid credentials on the Air Traffic Terminal Login page.

3. Use the Action Panel page to perform the following operations: 
   manage: scheduled flights, land and takeoff, flights landed, utility vehicles, flight vitals, runway details.
   view: local radar, scrambler jets, weather details.

4. Query Log Page can be used only by the admin to view all the actions performed by users using
   the system.

5. Flight Scheduler Page can be used for the following functionalities: add flights, delete already scheduled flights, view airline details.

## Contributors
* Swapnil0818 : https://github.com/Swapnil0818
* Ishan :
* Manav :
* Mudit :
* Atharv : 
