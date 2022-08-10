package Controllers;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import static java.time.LocalTime.now;
import static javafx.collections.FXCollections.observableList;

public class AddAppt implements Initializable {
    public TableView<Customer> CustomerTable;
    public TableColumn AddAppUserNameCol;
    public TableColumn AddApptUserIDCol;
    public TableColumn AddCustIDCol;
    public TableColumn AddCustNameCol;
    public TableView<User> UserTable;
    public TextField CustomerIDTextBox;
    public TextField UserIDTextBox;
    public ComboBox UserNameComboBox;
    public ComboBox CustomerComboBox;
    @FXML
    private ComboBox UserIDs;
    @FXML
    private ComboBox CustomerID;
    @FXML
    private ComboBox ContactList;
    @FXML
    private ComboBox<String> StartTime;
    @FXML
    private ComboBox<String> EndTime;
    @FXML
    private TextField addApptDescription;
    @FXML
    private TextField AddApptID;
    @FXML
    private TextField AddApptUserID;
    @FXML
    private TextField AddApptTitle;
    @FXML
    private TextField AddApptCustID;
    @FXML
    private TextField AddApptType;

    @FXML
    private TextField AddApptLocation;
    @FXML
    private DatePicker AddApptStartDate;

    @FXML
    private Button SaveAddAppt;
    @FXML
    private Button AddAppointmentExit;

    @FXML
    private ObservableList<Customer> CustomerObservableList = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> OLStartTimes = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> OLEndTimes = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Contact> OLContactsFromDB = FXCollections.observableArrayList();
    @FXML
    private DateTimeFormatter Timeformatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private DateTimeFormatter HourMinSecFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @FXML
    private DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter YearMonthHrScFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @FXML
    private ZoneId CurrentZoneID = ZoneId.systemDefault();
    @FXML
    private ZoneId UTCID = ZoneId.of("UTC");
    @FXML
    private Connection connection;
    @FXML
    private ObservableList <User> AllUsers = FXCollections.observableArrayList();
    @FXML
    private ObservableList <Customer> AllCustomers = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /** Insert Start and End Times, set tables for Users and Customers*/
        InsertStartTimes();
        InsertEndTimes();
        ContactList.setItems(Contact.getContactNames());





    }
    /** Populates all users to user table from DB*/
    public void PopulateAllUsers() {
        ObservableList <User>  TestUserOL = FXCollections.observableArrayList();
        System.out.println(" Trying to populate all Users ");

        try {
            String statement = "SELECT  users.User_ID,users.User_Name FROM users";
            connection = JDBC.openConnection();
            ResultSet rs = connection.createStatement().executeQuery(statement);
            System.out.print(" Query for all users Successful! ");
            TestUserOL.clear();


            while (rs.next()) {

                String UserName = rs.getString("User_Name");
                int UserID = rs.getInt("User_ID");

                TestUserOL.add (new User( UserID,UserName));
            }
            UserTable.setItems(TestUserOL);
            System.out.println(" Successfully added all users to table " + TestUserOL + " ");

        } catch (SQLException e) {
            System.out.println(" Error adding users to table ");
        }
    }

    public void PopulateAllCustomers() {
        System.out.println(" Trying to populate all Customers ");

        try {
            String statement = "SELECT  customers.Customer_ID, customers.Customer_Name FROM customers";
            connection = JDBC.openConnection();
            ResultSet rs = connection.createStatement().executeQuery(statement);
            System.out.print(" Query for all customers Successful! ");
            AllCustomers.clear();

            while (rs.next()) {

                String CustomerName = rs.getString("Customer_Name");
                int CustomerID = rs.getInt("Customer_ID");

                AllCustomers.add (new Customer(CustomerID,CustomerName));
            }
            CustomerTable.setItems(AllCustomers);
            System.out.println(" Successfully added all customers to table " + AllCustomers + " ");

        } catch (SQLException e) {
            System.out.println(" Error adding customers to table ");
        }
    }



    /**
     * This method populates the StartTimes Combo Box
     */
    public void InsertStartTimes() {
        LocalTime LocalTimeHolder = LocalTime.MIN.plusHours(8);
        int i;
        for (i = 0; i <= 14; i++) {
            OLStartTimes.add(LocalTimeHolder.format(Timeformatter));
            LocalTimeHolder = LocalTimeHolder.plusHours(1);
        }
        StartTime.setItems(OLStartTimes);

    }

    /**
     * This method populates the EndTimes Combo Box
     */
    public void InsertEndTimes() {
        LocalTime LocalTimeHolder = LocalTime.MIN.plusHours(8);
        int i;
        for (i = 0; i <= 14; i++) {
            OLEndTimes.add(LocalTimeHolder.format(Timeformatter));
            LocalTimeHolder = LocalTimeHolder.plusHours(1);
        }
        EndTime.setItems(OLStartTimes);

    }

    public Boolean validateFields() {

        String Description = addApptDescription.getText();

        if (Description.isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please add a description. PS I LOVE YOU JOE");
            error.showAndWait();
        }
        String Customerid = CustomerIDTextBox.getText();
        if (Customerid.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter a Customer ID");
                error.showAndWait();
            }

        }
        String Userid = UserIDTextBox.getText();
        if (Userid.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter a User ID");
                error.showAndWait();
            }

        }
        String location = AddApptLocation.getText();
        if (location.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter a location");
                error.showAndWait();
            }

        }
        SingleSelectionModel contact = ContactList.getSelectionModel();
        if (contact.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please select a Contact ID");
                error.showAndWait();
            }


        }
        String type = AddApptType.getText();
        if (type.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter an appointment type");
                error.showAndWait();
            }
        }

        SingleSelectionModel Starttime = StartTime.getSelectionModel();
        if (Starttime.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter an appointment start time");
                error.showAndWait();
            }
        }
        SingleSelectionModel Endtime = EndTime.getSelectionModel();
        if (Endtime.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter an appointment end time");
                error.showAndWait();
            }
        }

        LocalDate Startdate = AddApptStartDate.getValue();
        if (Startdate == null) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter an appointment start date");
                error.showAndWait();
            }
        }


        if (Startdate == null || Endtime.isEmpty() || Starttime.isEmpty() || type.isEmpty() || contact.isEmpty() || location.isEmpty() || Userid.isEmpty() || Customerid.isEmpty()
                || Description.isEmpty()) {
            return false;
        } else {
            return true;

        }


    }

    private boolean isOverLapping(LocalDateTime startNewAppt, LocalDateTime endNewAppt, int customerID) {
        boolean overlaps = false;
        ObservableList<Appointment> AllAppointments = FXCollections.observableArrayList();
        System.out.println(" checking for overlapping appointments ");
        try {
            String statement = "SELECT * FROM appointments WHERE Customer_ID = ?";
            connection = JDBC.openConnection();
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            System.out.println(" Successfully queried list to check for overlapping times ");

            System.out.println(rs);

            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                int Contact_ID = rs.getInt("Contact_ID");
                String Type = rs.getString("Type");

                /**Convert DB Start and end to Local Date and Time */
                Timestamp EndTimeFromDB = rs.getTimestamp("End");
                LocalDateTime localEnd = EndTimeFromDB.toLocalDateTime();

                Timestamp StartTimefromDB = rs.getTimestamp("Start");
                LocalDateTime localStart = StartTimefromDB.toLocalDateTime();

                System.out.println(" Trying to add all Appts to list to check for overlap ");
                Appointment AppointmentToCheckAgainst = new Appointment(Appointment_ID, Customer_ID, User_ID, Title, Description, Location, Contact_ID, Type, localStart, localEnd);

                AllAppointments.add(AppointmentToCheckAgainst);
            }

            for (Appointment i : AllAppointments) {
                LocalDateTime DBstart = i.getStartLocal();
                LocalDateTime DBend = i.getEndLocal();
                if (startNewAppt.isAfter(DBstart) && startNewAppt.isBefore(DBend)) {
                    overlaps = true;
                }
                if (endNewAppt.isAfter(DBstart) && endNewAppt.isBefore(DBend)) {
                    overlaps = true;
                }
                if (startNewAppt.equals(DBstart) || endNewAppt.equals(DBend)) {
                    overlaps = true;
                }
                if (startNewAppt.isAfter(DBstart) && endNewAppt.isBefore(DBend)) {
                    overlaps = true;
                }

            }

    }


        catch (SQLException e){
            System.out.println(" Failed to check for overlapping appointments ");
        }
        System.out.println(" Overlapping boolean is " + overlaps + " ");
        return overlaps;



    }



    public void onAddApptDescription(ActionEvent actionEvent) {
    }

    public void onAddApptID(ActionEvent actionEvent) {
    }

    public void onAddApptUserID(ActionEvent actionEvent) {
    }

    public void onAddApptCustID(ActionEvent actionEvent) {
    }

    public void onAddApptContact(MouseEvent mouseEvent) {
    }

    public void onAddApptType(ActionEvent actionEvent) {
    }

    public void onAddApptLocation(ActionEvent actionEvent) {
    }

    public void onAddApptContactClicked(MouseEvent mouseEvent) {
    }

    public void onAddApptStartTimeHour(MouseEvent mouseEvent) {
    }

    public void onAddApptStartTimeHourClicked(MouseEvent mouseEvent) {
    }

    public void onAddApptStartTimeMin(MouseEvent mouseEvent) {
    }

    public void onAddStartTimeMinClicked(MouseEvent mouseEvent) {
    }

    public void onAddApptEndTimeHour(MouseEvent mouseEvent) {
    }

    public void onAddApptEndTimeMinClicked(MouseEvent mouseEvent) {
    }

    public void onAddApptStartDate(ActionEvent actionEvent) {
    }

    public void onAddApptStartDateClicked(MouseEvent mouseEvent) {
    }

    public void onAddApptEndDate(ActionEvent actionEvent) {
    }

    public void onAddApptEndDateClicked(MouseEvent mouseEvent) {
    }

    public void onSaveAddAppt(ActionEvent actionEvent) {
        /** Check all feilds are filled out */
        validateFields();
        System.out.println("Return type of validate fields  in Add appt screen equals  "+ validateFields() + " ");
        System.out.println(" Trying to insert new appointment ");

        int min = 5;
        int max = 100;
        int newInt = (int) Math.floor(Math.random() * (max - min + 1) + min);
        /** Grab data from all fields and print it to check it's grabbing correctly*/

        System.out.println(" New appt ID is " + newInt + " ");

        int CustomerID = Integer.parseInt(CustomerIDTextBox.getText());

        System.out.println(" Customer ID : " + CustomerID + " ");

        int UserID = Integer.parseInt(UserIDTextBox.getText());

        System.out.println(" User ID : " + UserID + " ");




        String Description = addApptDescription.getText();
        System.out.println(" appt description is " + Description + " ");


        String location = AddApptLocation.getText();
        System.out.println(" appt location is " + location + " ");

        String type = AddApptType.getText();
        System.out.println(" appt type is " + type + " ");

        String title = AddApptTitle.getText();
        System.out.println(" appt title is " + title + " ");

        String ContactName = ContactList.getSelectionModel().getSelectedItem().toString();

        System.out.println(" Contact Name: " + ContactName + " ");

        int ContactID = JDBC.ConvertContactNameToContactID(ContactName);

        System.out.println(" ContactID from insert new apt is " + ContactID);






      /** Getting times in string for overlap validation*
        LocalDate localdate = AddApptStartDate.getValue();
        System.out.println(" Localdate date from app " + localdate + " ");
        String Start = StartTime.getValue();
        System.out.println(" String start from app " + Start + " ");
        String End = EndTime.getValue();
        System.out.println(" String end date from app " + End + " ");

        LocalDateTime startLocal = LocalDateTime.of(localdate,LocalTime.parse(Start,Timeformatter));
        LocalDateTime endLocal = LocalDateTime.of(localdate,LocalTime.parse(End,Timeformatter));
       */

 /** Converting times from Local Date time  to putin DB*/


        LocalDate localdate = AddApptStartDate.getValue();
        System.out.println(" Localdate date from app " + localdate + " ");
        LocalTime StartForinsertLoc = LocalTime.parse(StartTime.getSelectionModel().getSelectedItem(),Timeformatter);
        System.out.println(" LocalTime start from app " + StartForinsertLoc + " ");
        LocalTime EndForintsertLoc = LocalTime.parse(EndTime.getSelectionModel().getSelectedItem(),Timeformatter);
        System.out.println(" LocalTime end from app " + EndForintsertLoc + " ");

        /** Put date and start and end times together*/
        LocalDateTime StartDateAndTime = LocalDateTime.of(localdate,StartForinsertLoc);


        LocalDateTime EndDateAndTime = LocalDateTime.of(localdate,EndForintsertLoc);
        System.out.println( " Localdatetime start and end for appt trying to update " + StartDateAndTime + "  " + EndDateAndTime + " ");
        /** Convert start and end date and time to UTC*/

        ZonedDateTime startDB = StartDateAndTime.atZone(CurrentZoneID).withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println(" Zoned Date Time start " + startDB + " ");
        ZonedDateTime endDB = EndDateAndTime.atZone(CurrentZoneID).withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println( " Zoned date time end " + endDB + " ");

        /** Convert start and end time to time stamp for DB*/

        Timestamp TimeStampStart = Timestamp.valueOf(startDB.toLocalDateTime());
        Timestamp TimeStampEnd = Timestamp.valueOf(endDB.toLocalDateTime());
        System.out.println(" Times stamp start and ends for new appt add " + TimeStampStart + " " + TimeStampEnd);


        try {
            String statement = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Updated_By, Customer_ID, User_ID)" +
                    "VALUES(?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?)";

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setInt(1,newInt);
            System.out.println(" New appt INT " + newInt);
            ps.setString(2,title);
            ps.setString(3,Description);
            ps.setString(4,location);
            ps.setString(5,type);
            ps.setTimestamp(6, TimeStampStart);
            ps.setTimestamp(7, TimeStampEnd);
            ps.setString(8, UserID);
            ps.setInt(9, 9);

            ps.setInt(10, UserID);

            System.out.println(" Statement I'm sending to SQL to insert appt " + ps + " ");
            ps.executeUpdate();
        }


            catch( SQLException exception){
            System.out.println(" error adding appointment ");

            }

        /**if(isOverLapping(startLocal,endLocal,1)){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, appointment overlaps with another appointment for this customer");
            error.showAndWait();

        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Congrats, this appointment does not overlap with another for  this customer");
            error.showAndWait();

        }*/


    }




    public void onAddAppointmentExit(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/Views/AppointmentTable.fxml"));
        Scene MainScene = new Scene(parent,3800,1200);
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        MainStage.setScene(MainScene);
        parent.setStyle("-fx-font-family: Times New Roman;");
        MainStage.setTitle("All Appointments");
        MainStage.show();
        System.out.println("Logged out of add Appointments tab");
    }

    public void onAddApptTitle(ActionEvent actionEvent) {
    }

    public void onAddApptEndTimeMin(MouseEvent mouseEvent) {
    }
    public void ConvertLocalTimeAndDatetoTimeStamp( LocalTime localTimeinsert, LocalDate localDateinsert){

        System.out.println(" Localdate date from function " + localDateinsert + " ");

        System.out.println(" LocalTime from function " + localTimeinsert + " ");


        /** Put date and start and end times together*/
        LocalDateTime DateAndTime = LocalDateTime.of(localDateinsert,localTimeinsert);



        /** Convert start and end date and time to UTC*/

        ZonedDateTime ZonedDateAndTime = DateAndTime.atZone(CurrentZoneID).withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println(" Zoned Date Time start " + ZonedDateAndTime + " ");


        /** Convert start and end time to time stamp for DB*/

        Timestamp TimeStamp = Timestamp.valueOf(ZonedDateAndTime.toLocalDateTime());

        System.out.println(" Times stamp start and ends for new appt add " + TimeStamp+ " " );



    }
    /** Converts ContactName to it's corresponding contactID from the database*/


}
