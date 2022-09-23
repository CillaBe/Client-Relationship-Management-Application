package model;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Appointment {
    @FXML
    private int Appointment_ID;
    @FXML
    private String Title;
    @FXML
    private String Description;
    @FXML
    private String Location;
    @FXML
    private String Type;
    @FXML
    private String Start;
    @FXML
    private String End;
    @FXML
    private Timestamp StartTimeStamp;
    @FXML
    private Timestamp EndTimeStamp;
    @FXML
    private LocalDateTime StartLocal;
    @FXML
   private LocalDateTime  EndLocal;
    @FXML
    private Timestamp Create_Date;
    @FXML
    private String Created_By;
    @FXML
    private Timestamp Last_Update;
    @FXML
    public String Last_Updated_By;
    @FXML
    private int Customer_ID;
    @FXML
    private int User_ID;
    @FXML
    private int Contact_ID;
    @FXML
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    @FXML
    private static  ObservableList<String> AllTypes = FXCollections.observableArrayList();

    /**
     *
     * @param Appointment_ID
     * @param Title
     * @param Description
     * @param Location
     * @param Type
     * @param Start
     * @param End
     * @param Create_Date
     * @param Created_By
     * @param Last_Update
     * @param Last_Updated_By
     * @param Customer_ID
     * @param User_ID
     * @param Contact_ID
     */

    public Appointment(int Appointment_ID, String Title, String Description, String Location, String Type, String Start,
                       String End, Timestamp Create_Date, String Created_By,
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

    /**
     *
     * @param Type
     */
    public Appointment(String Type){ this.Type = Type;}
    public Appointment(int Appointment_ID, int Customer_ID,int User_ID,String Title, String Description, String Location,int Contact_ID, String Type, String Start,
                       String End) {
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

    /**
     *
     * @param Appointment_ID
     * @param Customer_ID
     * @param User_ID
     * @param Title
     * @param Description
     * @param Location
     * @param Contact_ID
     * @param Type
     * @param Start
     * @param End
     */
    public Appointment(int Appointment_ID, int Customer_ID,int User_ID,String Title, String Description, String Location,int Contact_ID, String Type, LocalDateTime Start,
                       LocalDateTime End) {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.StartLocal = Start;
        this.EndLocal = End;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
    }

    /**
     *
     * @param ContactID
     * @param StartTimeStamp
     * @param EndTimeStamp
     */
    public Appointment(int ContactID, Timestamp StartTimeStamp, Timestamp EndTimeStamp){
        this.Customer_ID = ContactID;
        this.StartTimeStamp = StartTimeStamp;
        this.EndTimeStamp = EndTimeStamp;

    }

    /**
     *
     * @param Appointment_ID
     * @param Customer_ID
     * @param Title
     * @param Description
     * @param Type
     * @param Start
     * @param End
     */

    public Appointment(int Appointment_ID, int Customer_ID, String Title, String Description, String Type, String Start, String End) {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.Customer_ID = Customer_ID;
    }

    /**
     *
     * @return StartTimeStamp
     */

    public Timestamp getStartTimeStamp(){
        return StartTimeStamp;
    }

    /**
     *
     * @return End Time stamp
     */
    public Timestamp getEndTimeStamp(){
        return EndTimeStamp;
    }
    public String toString(){
        return(  Type);
    }

    /**
     *
     * @return Appointment_ID
     */

    public int getAppointment_ID() {
        return Appointment_ID;
    }

    /**
     *
     * @param ID
     * @return sets Appt_ID
     */

    public int setAppointment_ID(int ID) {
       return Appointment_ID = ID;
    }

    /**
     *
     * @return Title
     */

    public String getTitle() {
        return Title;
    }

    /**
     *
     * @param title
     * @return sets title
     */

    public String setTitle(String title) {
        return Title = title;
    }

    /**
     *
     * @return Descriptions
     */
    public String getDescription(){
        return Description;
    }

    /**
     *
     * @param Descript
     */
    public void setDescriptions(String Descript){
        Description = Descript;
    }

    /**
     *
     * @return Location
     */
    public String getLocation(){
        return Location;
    }

    /**
     *
     * @param locate
     */
    public void setLocation(String locate){
        Location = locate;
    }

    /**
     *
     * @return type
     */
    public String getType(){
        return Type;
    }

    /**
     *
     * @param TypeOfAppt
     */
    public void setType(String TypeOfAppt){
        Type = TypeOfAppt;
    }

    /**
     *
     * @return Start
     */
    public String getStart(){
        return Start;
    }

    /**
     *
     * @return StartLocal
     */
    public LocalDateTime getStartLocal(){ return StartLocal;}

    /**
     *
     * @param startTime
     */

    public void setStart(String startTime){
        Start = startTime;
    }

    /**
     *
     * @return End
     */
    public String getEnd(){
        return End;
    }

    /**
     *
     * @return EndLocal
     */
    public LocalDateTime getEndLocal(){return EndLocal;}

    /**
     *
     * @param EndTime
     */
    public  void  setEnd(String EndTime){
        End = EndTime;
    }

    /**
     *
     * @return User_ID
     */
    public int getUser_ID(){
        return User_ID;
    }

    /**
     *
     * @param UID
     */
    public void setUser_ID(int UID){
        User_ID = UID;
    }

    /**
     *
     * @return Customer_ID
     */
    public int getCustomer_ID(){
        return Customer_ID;
    }

    /**
     *
     * @param Cust_ID
     */
    public void  setCustomer_ID(int Cust_ID){
        Customer_ID = Cust_ID;
    }

    /**
     *
     * @return Contact_ID
     */
    public int getContact_ID(){
        return Contact_ID;
    }

    /**
     *
     * @param ContactID
     */
    public void setGetContact_ID(int ContactID){
        Contact_ID = ContactID;
    }

    /**
     *
     * @return allAppointments
     */
    public static ObservableList<Appointment> getAllAppointments(){
        return  allAppointments;
    }

    /**
     *
     * @return allAppt Types
     */
    public static ObservableList<String> getApptTypes(){
        Connection connection = JDBC.openConnection();
        AllTypes.clear();
        try {
            String statement = ("SELECT appointments.Type FROM appointments");
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet rs = ps.executeQuery(statement);

            while (rs.next()) {
                String ApptType = rs.getString("Type");


                AllTypes.add(String.valueOf(new Appointment(ApptType)));

            }
        }
        catch (SQLException e){
            System.out.println("Error returning all appt types");


        }
        LinkedHashSet<String> hashSet = new LinkedHashSet<>(AllTypes);
        ObservableList<String> converted  = FXCollections.observableArrayList(hashSet);
        return converted;
    }

}




