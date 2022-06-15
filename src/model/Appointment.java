package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    /**First constructor method to create instance of class using all fields in DB*/
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
    /**Second constructor method to create instance of class using all fields in TableView*/
    public Appointment( int Appointment_ID, String Title, String Description, String Location, String Type, LocalDateTime Start,
                        LocalDateTime End,  int Customer_ID, int User_ID , int Contact_ID) {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;

    }
    /**Third constructor method to create instance of class for TableView that accepts String data*/
    public Appointment( String Appointment_ID,String Customer_ID,String User_ID, String Title, String Description,
                       String Location,String Contact_ID, String Type, String Start, String End ) {
        this.Appointment_ID = Integer.parseInt(Appointment_ID);
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Start = LocalDateTime.parse(Start);
        this.Contact_ID  = Integer.parseInt(Contact_ID);
        this.End = LocalDateTime.parse(End);
        this.Customer_ID = Integer.parseInt(Customer_ID);
        this.User_ID = Integer.parseInt(User_ID);

    }

}





