package Controllers;

import Helper.JDBC;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jdk.jfr.Frequency;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InsertStartTimes();
        InsertEndTimes();
        ContactList.setItems(Contact.getContactNames());
        CustomerID.setItems(Customer.getCustomerIDs());
        UserIDs.setItems(User.getUserIDs());


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
        SingleSelectionModel Customerid = CustomerID.getSelectionModel();
        if (Customerid.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please select a Customer ID");
                error.showAndWait();
            }

        }
        SingleSelectionModel Userid = UserIDs.getSelectionModel();
        if (Userid.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please select a User ID");
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

        System.out.println(" New appt ID is " + newInt + " ");
        String Description = addApptDescription.getText();

        Customer NewCustomer = (Customer) CustomerID.getSelectionModel().getSelectedItem();
        int CustomerID = NewCustomer.getCustomerID();

        System.out.println(" CustomerId box is " + CustomerID + " ");

        User Userid = (User) UserIDs.getValue();
        String location = AddApptLocation.getText();
        SingleSelectionModel contact = ContactList.getSelectionModel();
        String type = AddApptType.getText();
        String title = AddApptTitle.getText();

        System.out.println(" User ID:" );



      /** Getting times in string for overlap validation*/
        LocalDate localdate = AddApptStartDate.getValue();
        String Start = StartTime.getValue();
        String End = EndTime.getValue();

        LocalDateTime startLocal = LocalDateTime.of(localdate,LocalTime.parse(Start,Timeformatter));
        LocalDateTime endLocal = LocalDateTime.of(localdate,LocalTime.parse(End,Timeformatter));

 /** Converting times from Local Date time  to putin DB*/



        LocalTime StartForinsertLoc = LocalTime.parse(StartTime.getSelectionModel().getSelectedItem(),HourMinSecFormatter);
        LocalTime EndForintsertLoc = LocalTime.parse(EndTime.getSelectionModel().getSelectedItem(),HourMinSecFormatter);

        /** Put date and start and end times together*/
        LocalDateTime StartDateAndTime = LocalDateTime.of(localdate,StartForinsertLoc);
        LocalDateTime EndDateAndTime = LocalDateTime.of(localdate,EndForintsertLoc);
        System.out.println( " Local date and time start and end for appt trying to update " + StartDateAndTime + "  " + EndDateAndTime + " ");
        /** Convert start and end date and time to UTC*/

        ZonedDateTime startDB = StartDateAndTime.atZone(CurrentZoneID).withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endDB = EndDateAndTime.atZone(CurrentZoneID).withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println( " UTC Start and end date and times " + startDB + " " + endDB);

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
            ps.setString(8,"phennig");
            ps.setInt(9,NewCustomer.getCustomerID());
            System.out.println(" New appt cust id ");
            ps.setInt(10,Userid.getUserID());
            System.out.println(" New appt user id " + Userid.getUserID());
            System.out.println(" Statement I'm sending to SQL to insert appt " + ps + " ");
            ps.executeUpdate();
        }


            catch( SQLException exception){
            System.out.println(" error adding appointment ");

            }

        if(isOverLapping(startLocal,endLocal,1)){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, appointment overlaps with another appointment for this customer");
            error.showAndWait();

        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Congrats, this appointment does not overlap with another for  this customer");
            error.showAndWait();

        }


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
}
