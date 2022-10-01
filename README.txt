Title of Application: Scheduling Application
This application will allow the user to add, modify, and delete customers as well as appointments.

Author: Priscilla Hennig
Contact Info: Phennig@wgu.edu
MySQL Connector driver version number used: mysql-connector-java-8.0.26
IDE Version: IntelliJ IDEA Community Edition 2021.1.3 x64
JavaFx Version: javafx-sdk-11.0.2
JDK Version: Java 11.0.11
Student Application Version: JigSawPuzzle 2003.1234 Version 4
Date: September 22 2022

For this program you will need a user name and password that are located in the SQL database to enter the program. From the main page you will see all appointments,
you will also be updated if you have appointments upcoming in the next 15 minutes.
From the main appointments page you can navigate to different screens to update, edit, and delete both appointments and customers.

You will also be able to navigate to a reports page where you can generate three different types of reports, including total appointments by month and type,
as well as the appointment schedule for a specific contact or customer.

There are two lambda expressions in this program, they are both found in the AppointmentTable Class under Controllers. One is found in the 'public void onMonthView(ActionEvent actionEvent)' method and the
other is found in the 'public void onWeekView(ActionEvent actionEvent)'method.

The additional report I choose was a report that  allows the user to select a customer and generate a report showing that customer's appointments.

A few things to note, you will not be able to delete a customer if they have a corresponding appointment in the scheduler, you will
first need to delete their corresponding appointment. You will also not be able to schedule overlapping appointments for customers.



