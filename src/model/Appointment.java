package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Appointment {
    int Appointment_ID;
    String Title;
    String Description;
    String Location;
    String Type;
    LocalDateTime Start;
    LocalDateTime End;
    Timestamp Create_Date;
    String Created_By;
    Timestamp Last_Update;
    String Last_Updated_By;
    int Customer_ID;
    int User_ID;
    int Contact_ID;

    /**First constructor method to create instance of class */
public Appointment( int Appointment_ID, String Title, String Description, String Location, String Type, LocalDateTime Start,
                    LocalDateTime End, Timestamp Create_Date, String Created_By,
                    Timestamp Last_Update, String Last_Updated_By, int Customer_ID, int User_ID, int Contact_ID) {
    this.Appointment_ID = Appointment_ID;
    this.Title = Title;
    this.Description = Description;
    this.Location = Location;
    this.Type = Type;
    this.Start = Start;
    this.End = End;
    this.Create_Date = Create_Date;
    this.Created_By = Created_By;
    this.Last_Update = Last_Update;
    this.Last_Updated_By = Last_Updated_By;
    this.Customer_ID = Customer_ID;
    this.User_ID = User_ID;
    this.Contact_ID = Contact_ID;
}

}





