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

import javax.security.auth.callback.Callback;
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
    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn AddAppUserNameCol;
    @FXML
    private TableColumn AddApptUserIDCol;
    @FXML
    private TableColumn AddCustIDCol;
    @FXML
    private TableColumn AddCustNameCol;
    @FXML
    private TableView<User> UserTable;
    @FXML
    private TextField CustomerIDTextBox;
    @FXML
    private TextField UserIDTextBox;
    @FXML
    private ComboBox CustomerComboBox;
    @FXML

    private ComboBox UserNameComboTest;
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
        CustomerComboBox.setItems(Customer.getCustomerNames());
        UserNameComboTest.setItems(User.getUserNames());






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
     * Popoulates End Times
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
        SingleSelectionModel Customerid = CustomerComboBox.getSelectionModel();
        if (Customerid.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please select a Customer Name");
                error.showAndWait();
            }

        }
        SingleSelectionModel Userid = UserNameComboTest.getSelectionModel();
        if (Userid.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please select a User Name");
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
  /** Checks for overlapping appointments based on customerID*/
    private boolean isOverLapping(Timestamp TimeStartFromApp, Timestamp TimeEndFromApp, int customerID) {
        boolean overlaps = false;
        ObservableList<Appointment> AllAppointments = FXCollections.observableArrayList();
        System.out.println(" checking for overlapping appointments ");
        try {
            String statement = "SELECT Start, End, Customer_ID FROM appointments WHERE Customer_ID = ?";
            connection = JDBC.openConnection();
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            System.out.println(" Successfully queried list to check for overlapping times ");

            System.out.println(rs);

            while (rs.next()) {
                Timestamp StartTimeStamp = rs.getTimestamp("Start");
                Timestamp EndTimeStamp = rs.getTimestamp("End");
                int CustomerId = rs.getInt("Customer_ID");

                System.out.println(" Trying to add all Appts to list to check for overlap ");
                Appointment AppointmentToCheckAgainst = new Appointment( CustomerId, StartTimeStamp,EndTimeStamp);

                AllAppointments.add(AppointmentToCheckAgainst);
            }

            for (Appointment i : AllAppointments) {
                Timestamp DBstart = i.getStartTimeStamp();
                Timestamp DBend = i.getEndTimeStamp();
                if (TimeStartFromApp.before(DBstart) && TimeEndFromApp.before(DBend) && TimeEndFromApp.after(DBstart)) {
                    overlaps = true;
                }
                if (TimeEndFromApp.after(DBstart) && TimeEndFromApp.before(DBend)) {
                    overlaps = true;
                }
                if (TimeStartFromApp.equals(DBstart) || TimeEndFromApp.equals(DBend)) {
                    overlaps = true;
                }
                if (TimeStartFromApp.after(DBstart) && TimeEndFromApp.before(DBend)) {
                    overlaps = true;
                }
                if(TimeStartFromApp.after(DBstart) && TimeStartFromApp.before(DBend)){
                    overlaps = true;
                }
                if(TimeEndFromApp.after(DBstart) && TimeEndFromApp.before(DBstart)){
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


   /** This method was not used in this application,
    * @param actionEvent not used in this application */
    public void onAddApptDescription(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddApptID(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddApptUserID(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddApptCustID(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */

    public void onAddApptContact(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddApptType(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddApptLocation(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddApptContactClicked(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddApptStartTimeHour(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddApptStartTimeHourClicked(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddApptStartTimeMin(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddStartTimeMinClicked(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */

    public void onAddApptEndTimeHour(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddApptEndTimeMinClicked(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddApptStartDate(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddApptStartDateClicked(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddApptEndDate(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddApptEndDateClicked(MouseEvent mouseEvent) {
    }
 /** @param actionEvent  validates fields then saves appointment to database if all fields are filled out and there are no overlapping appointments*/
    public void onSaveAddAppt(ActionEvent actionEvent) {



        /** Check all feilds are filled out */

        Boolean Overlapping;
        UpdateCustomerIDTextBox();
        UpdateUserIDTextBox();
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
        String UserName = (String) UserNameComboTest.getSelectionModel().getSelectedItem();







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








 /** Converting times from Local Date To TimeStamp time  to putin DB*/


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




        if(isOverLapping(TimeStampStart,TimeStampEnd, CustomerID)){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, appointment overlaps with another appointment for this customer, appointment will not save");
            error.showAndWait();
            Overlapping = true;




        }
        else{
            Overlapping = false;
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Congrats, this appointment does not overlap with another for  this customer, adding to database");
            error.showAndWait();
            try {
                String statement = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID,Last_Update)" +
                        "VALUES(?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,?,?,?,?,CURRENT_TIMESTAMP)";

                PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
                ps.setInt(1,newInt);
                ps.setString(2,title);
                ps.setString(3,Description);
                ps.setString(4,location);
                ps.setString(5,type);
                ps.setTimestamp(6, TimeStampStart);
                ps.setTimestamp(7, TimeStampEnd);
                ps.setString(8, UserName);
                ps.setString(9, UserName);
                ps.setInt(10,CustomerID);

                ps.setInt(11, UserID);
                ps.setInt(12,ContactID);

                System.out.println(" Statement I'm sending to SQL to insert appt " + ps + " ");
                ps.executeUpdate();
            }


            catch( SQLException exception){
                System.out.println(" error adding appointment ");

            }

        }
        if (validateFields() == true  && Overlapping == false){
            try {
                Alert Confirm = new Alert(Alert.AlertType.CONFIRMATION);
                Confirm.setContentText("Appointment Updated!");
                Confirm.showAndWait();
                Parent parent = FXMLLoader.load(getClass().getResource("/Views/AppointmentTable.fxml"));
                Scene MainScene = new Scene(parent, 3800, 1200);
                Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                MainStage.setScene(MainScene);
                parent.setStyle("-fx-font-family: Times New Roman;");
                MainStage.setTitle("All Appointments");
                MainStage.show();
                System.out.println("Logged out of add Appointments tab");
            }
            catch (IOException e){
                System.out.println("Error moving from save app to full appts screen");

            }

        }
        else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please fix missing fields before proceeding");
            error.showAndWait();

        }



    }


    /**
     * @param actionEvent exits add appointment screen and sends user back to main appointment table */

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
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddApptTitle(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
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
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void CustomerNameDropDownAction(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void MouseClickedForSelectCustName(MouseEvent mouseEvent) {

    }
/** this method updates the customer ID textbox with customer names*/
    public void UpdateCustomerIDTextBox() {
        String CustomerName = (String) CustomerComboBox.getSelectionModel().getSelectedItem();
        CustomerIDTextBox.setText(String.valueOf(JDBC.convertCustomerNameToCustID(CustomerName)));
    }
    /** this method updates the user ID textbox with customer names*/
    public void UpdateUserIDTextBox() {
        String UserName = (String) UserNameComboTest.getSelectionModel().getSelectedItem();
        UserIDTextBox.setText(String.valueOf(JDBC.convertUserNameToUserID(UserName)));
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void MouseReleasedCustName(MouseEvent mouseEvent) {
    }



}
