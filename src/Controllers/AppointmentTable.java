package Controllers;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static java.time.format.DateTimeFormatter.*;

public class AppointmentTable implements Initializable {
    @FXML
    private RadioButton AllAppointments;
    @FXML
    private ToggleGroup apptTgroup;
    @FXML
    private TableColumn User_ID;
    @FXML
    private TableColumn StartTime;
    @FXML
    private TableColumn EndTime;
    @FXML
    private TableColumn EndDate;
    @FXML
    private TableColumn StartDate;
    @FXML
    private TableView <Appointment> AppointmentTable;
    @FXML
    private TableColumn AppointmentID;
    @FXML
    private TableColumn CustomerID;
    @FXML
    private TableColumn Location;
    @FXML
    private TableColumn Title;
    @FXML
    private TableColumn Description;
    @FXML
    private TableColumn Type;
    @FXML
    private TableColumn Contact;
    @FXML
    private TableColumn StartDateAndTime;
    @FXML

    private TableColumn EndDateAndTime;
    @FXML
    private RadioButton MonthView;
    @FXML
    private RadioButton WeekView;
    @FXML
    private Button CustomerDataBase;
    @FXML
    private Button addAppointment;
    @FXML
    private Button modifyAppointment;
    @FXML
    private Button deleteAppointment;
    @FXML
    private Button Reports;
    @FXML
    private Button LogOut;
    @FXML
    private Connection connection = JDBC.openConnection();
    @FXML
    private ObservableList<Appointment> AllTableAppointments = FXCollections.observableArrayList();
    @FXML
    private ObservableList AppointmentStartTimesOL = FXCollections.observableArrayList();
    @FXML
    private  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy h:mm a");
    @FXML
    private DateTimeFormatter formatterFor15MinChecking = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @FXML
    private  ZoneId CurrentZoneID = ZoneId.systemDefault();
    @FXML
    private ZoneId UTCID = ZoneId.of("UTC");



    @FXML
    private JDBC DbHandler;

/** Initializes screen, both lambda expressions are on this screen*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AllAppointments.setSelected(true);

        AppointmentID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        StartDateAndTime.setCellValueFactory(new PropertyValueFactory<>("Start"));
        EndDateAndTime.setCellValueFactory(new PropertyValueFactory<>("End"));
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        User_ID.setCellValueFactory(new PropertyValueFactory<>("User_ID"));

        if (AllAppointments.isSelected()) {
            System.out.print(" All appointments selected");
            try {
                PopulateAllAppointments();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        try {
            CheckForAppointments();
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        /** This method checks if there are any appts with in 15 mins of logging in and alerts if there are or not.
         *
         * */
    }

        public void CheckForAppointments() throws SQLException {
            System.out.println(" Trying to check for appointments 15 minutes from now");
            try {
                String statement = "SELECT  appointments.Appointment_ID,appointments.Customer_ID,appointments.User_ID," +
                        "appointments.Title,appointments.Description,appointments.Location," +
                        "appointments.Contact_ID,appointments.Type,appointments.Start," +
                        "appointments.End FROM appointments";
                connection = JDBC.openConnection();
                ResultSet rs = connection.createStatement().executeQuery(statement);
                System.out.print(" Query for appt times successful!");
                while(rs.next())  {
                    int Appointment_ID = rs.getInt("Appointment_ID");
                    int Customer_ID = rs.getInt("Customer_ID");
                    int User_ID = rs.getInt("User_ID");
                    String Title = rs.getString("Title");
                    String Description = rs.getString("Description");
                    String Location = rs.getString("Location");
                    int Contact_ID = rs.getInt("Contact_ID");
                    String Type = rs.getString("Type");

                   /** LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                    LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();

                    ZonedDateTime localStart = ZonedDateTime.ofInstant(start.toInstant(ZoneOffset.UTC),CurrentZoneID);
                    ZonedDateTime localEnd = ZonedDateTime.ofInstant(end.toInstant(ZoneOffset.UTC),CurrentZoneID);
*/
                    /*Convert DB Start and end to Local Zoned Date and Time */
                    Timestamp EndTimeFromDB= rs.getTimestamp("End");
                    ZonedDateTime ZonedEnd = EndTimeFromDB.toLocalDateTime().atZone(UTCID);
                    ZonedDateTime LocalEnd =ZonedEnd.withZoneSameInstant(CurrentZoneID);

                    Timestamp StartTimefromDB = rs.getTimestamp("Start");
                    ZonedDateTime ZonedStart = StartTimefromDB.toLocalDateTime().atZone(UTCID);
                    ZonedDateTime LocalStart =ZonedStart.withZoneSameInstant(CurrentZoneID);

                    System.out.println(" Try to add appts to list");
                    AppointmentStartTimesOL.add(new Appointment(Appointment_ID,Customer_ID,User_ID,Title,Description,Location,Contact_ID,Type,LocalStart.toString(),LocalEnd.toString()));
                    System.out.println("Added all start times to list" + "Start and end Time are " + LocalStart + " " + LocalEnd);


                }
                LocalDateTime today = LocalDateTime.now();
                LocalDateTime FifteenMinutes = today.plusMinutes(15);
 /** First Lambda expression used to check if there are any appointments in the next 15 minutes by going through a filtered list of the appointments*/
                FilteredList<Appointment> FilteredByTime = new FilteredList<>(AppointmentStartTimesOL);
                FilteredByTime.setPredicate(TimeToCheck -> {
                    LocalDateTime TimeBeingChecked = LocalDateTime.parse(TimeToCheck.getStart().substring(0,16), formatterFor15MinChecking);
                    return TimeBeingChecked.isAfter(today.minusMinutes(1)) && TimeBeingChecked.isBefore(FifteenMinutes);
                        });
                    if (FilteredByTime.isEmpty()) {
                        System.out.println(" No appts within 15 mins");
                        Alert NoApptAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        NoApptAlert.setContentText("You have no appointments in the next 15 minutes");
                        NoApptAlert.showAndWait();
                    }
                        else {
                            int ApptID = FilteredByTime.get(0).getAppointment_ID();
                            String Description = FilteredByTime.get(0).getDescription();
                            String StartTime = FilteredByTime.get(0).getStart().substring(0,16);

                            Alert ApptAlert = new Alert(Alert.AlertType.CONFIRMATION);
                            ApptAlert.setHeaderText("You have the following appointments in the next 15 minutes.");
                            ApptAlert.setContentText("");
                            System.out.println(" appts in next 15 mins " + "Appointment ID: " + ApptID + " Description: "
                                    + Description + " Start Time " + FilteredByTime);
                            ApptAlert.showAndWait();
                        }


            }
            catch (SQLException e){
                System.out.println(" Error selecting appts times");
            }

        }

 /** This method populates all appointments to the schedule tableview.
  *
  * */


    public void PopulateAllAppointments() throws SQLException {
        System.out.print(" Trying to populate appointments");
        try {
            String statement = "SELECT  appointments.Appointment_ID,appointments.Customer_ID,appointments.User_ID," +
                    "appointments.Title,appointments.Description,appointments.Location," +
                    "appointments.Contact_ID,appointments.Type,appointments.Start," +
                    "appointments.End FROM appointments";
            connection = JDBC.openConnection();
            ResultSet rs = connection.createStatement().executeQuery(statement);
            System.out.print(" Query for all appts Successful! ");
            AllTableAppointments.clear();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                int Contact_ID = rs.getInt("Contact_ID");
                String Type = rs.getString("Type");

                Timestamp timeStart = rs.getTimestamp("Start");
                Timestamp timeEnd = rs.getTimestamp("End");

                LocalDateTime start = timeStart.toLocalDateTime();
                LocalDateTime end = timeEnd.toLocalDateTime();

                String FormattedTableStart = start.format(formatter);
                String FormattedTableEnd = end.format(formatter);


                /**
                String StartString = rs.getString("Start").substring(0,19);
                String EndString = rs.getString("End").substring(0,19);
                System.out.println(" Current Zone Id: " + CurrentZoneID+ " UTC Zone ID " + UTCID+ " ");


                //*Convert Start and End Times to Local Date Time then ZonedDateAndTime*/
             /**   LocalDateTime StartLocal = LocalDateTime.parse(StartString,formatter);
                LocalDateTime EndLocal = LocalDateTime.parse(EndString,formatter);
/**
                ZonedDateTime ZonedStart = StartLocal.atZone(UTCID).withZoneSameInstant(CurrentZoneID);
                ZonedDateTime ZonedEnd = EndLocal.atZone(UTCID).withZoneSameInstant(CurrentZoneID);
                //*Convert back to string to store in Appointment object and table*/

/**
                String FormattedTableStart = ZonedStart.format(formatter);
                String FormattedTableEnd = ZonedEnd.format(formatter);
*/
                AllTableAppointments.add(new Appointment(Appointment_ID,Customer_ID,User_ID,Title,Description,Location,Contact_ID,Type,FormattedTableStart,FormattedTableEnd));
                AppointmentTable.setItems(AllTableAppointments);
                System.out.print(" Set all appts in table");

            }
        }
            catch(SQLException e) {
                    System.out.println(" Error updating table");
                }


        }




    public void onAppointmentTable(SortEvent<TableView> tableViewSortEvent) {
    }
 /**This method populates the month view for the all Appointments Calender
  * @param  actionEvent populated month view*/

    public void onMonthView(ActionEvent actionEvent) {
        System.out.print(" Trying to populate monthly appointments");
        try {
            String statement = "SELECT  appointments.Appointment_ID,appointments.Customer_ID,appointments.User_ID," +
                    "appointments.Title,appointments.Description,appointments.Location," +
                    "appointments.Contact_ID,appointments.Type,appointments.Start," +
                    "appointments.End FROM appointments";
            connection = JDBC.openConnection();
            ResultSet rs = connection.createStatement().executeQuery(statement);
            System.out.print(" Query Successful! ");
            AllTableAppointments.clear();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                int Contact_ID = rs.getInt("Contact_ID");
                String Type = rs.getString("Type");
                Timestamp timeStart = rs.getTimestamp("Start");
                Timestamp timeEnd = rs.getTimestamp("End");

                LocalDateTime start = timeStart.toLocalDateTime();
                LocalDateTime end = timeEnd.toLocalDateTime();

                String FormattedTableStart = start.format(formatter);
                String FormattedTableEnd = end.format(formatter);



                AllTableAppointments.add(new Appointment(Appointment_ID, Customer_ID, User_ID, Title, Description, Location, Contact_ID, Type, FormattedTableStart, FormattedTableEnd));
            }

            LocalDate today = LocalDate.now();
            LocalDate MonthLater = today.plusMonths(1);
 /** First lambda expression used*/
            FilteredList<Appointment> FilteredByMonth = new FilteredList<>(AllTableAppointments);
            FilteredByMonth.setPredicate(dateToCheck -> {
                LocalDate dateBeingChecked = LocalDate.parse(dateToCheck.getStart(), formatter);
                return dateBeingChecked.isAfter(today.minusDays(1)) && dateBeingChecked.isBefore(MonthLater);
            });

            AppointmentTable.setItems(FilteredByMonth);

        } catch (SQLException e) {
            System.out.println(" Error updating table month view");
        }
    }
    /**This method populates the weekly view for the all Appointments Calender
     * @param  actionEvent populates weekly view lambda expression used* to filter through appointments and sort them by week*/
    public void onWeekView(ActionEvent actionEvent) {
        System.out.print(" Trying to populate weekly appointments");
        try {
            String statement = "SELECT  appointments.Appointment_ID,appointments.Customer_ID,appointments.User_ID," +
                    "appointments.Title,appointments.Description,appointments.Location," +
                    "appointments.Contact_ID,appointments.Type,appointments.Start," +
                    "appointments.End FROM appointments";
            connection = JDBC.openConnection();
            ResultSet rs = connection.createStatement().executeQuery(statement);
            System.out.print(" Query Successful! ");
            AllTableAppointments.clear();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Location = rs.getString("Location");
                int Contact_ID = rs.getInt("Contact_ID");
                String Type = rs.getString("Type");

                Timestamp timeStart = rs.getTimestamp("Start");
                Timestamp timeEnd = rs.getTimestamp("End");

                LocalDateTime start = timeStart.toLocalDateTime();
                LocalDateTime end = timeEnd.toLocalDateTime();

                String FormattedTableStart = start.format(formatter);
                String FormattedTableEnd = end.format(formatter);
                ;


                AllTableAppointments.add(new Appointment(Appointment_ID, Customer_ID, User_ID, Title, Description, Location, Contact_ID, Type, FormattedTableStart, FormattedTableEnd));
            }

            LocalDate today = LocalDate.now();
            LocalDate WeekLater = today.plusWeeks(1);
/** Second lambda expression used*/
            FilteredList<Appointment> FilteredByWeek = new FilteredList<>(AllTableAppointments);
            FilteredByWeek.setPredicate(dateToCheck -> {
                LocalDate dateBeingChecked = LocalDate.parse(dateToCheck.getStart(), formatter);
                return dateBeingChecked.isAfter(today.minusDays(1)) && dateBeingChecked.isBefore(WeekLater);
            });

            AppointmentTable.setItems(FilteredByWeek);

        } catch (SQLException e) {
            System.out.println(" Error updating table week view");
        }
    }

/** @param actionEvent  moves user to Customer Database*/
    public void onCustomerDatabase(ActionEvent actionEvent) throws IOException{
        Parent parent= FXMLLoader.load(getClass().getResource("/Views/CustomerTable.fxml"));
        Scene MainScene = new Scene(parent,2000,2000);
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        MainStage.setScene(MainScene);
        parent.setStyle("-fx-font-family: Times New Roman;");
        MainStage.setTitle("Customer Database");
        MainStage.show();
        System.out.println("Logged out of Appointments tab");
    }

   /** @param actionEvent This method launches the Modify Appointment */
    public void onModifyAppointment(ActionEvent actionEvent) throws IOException{
        Parent parent= FXMLLoader.load(getClass().getResource("/Views/ModifyAppt.fxml"));
        Scene MainScene = new Scene(parent,13000,1200);
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        MainStage.setScene(MainScene);
        parent.setStyle("-fx-font-family: Times New Roman;");
        MainStage.setTitle("Add Appointment");
        MainStage.show();
        System.out.println("Moved to Modify appointments");
    }






    /**This method launches the Add Appointment Screen
     @param actionEvent navigates to Add Appointment screen
     */
    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/Views/AddAppt.fxml"));
        Scene MainScene = new Scene(parent,900,1000);
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        MainStage.setScene(MainScene);
        parent.setStyle("-fx-font-family: Times New Roman;");
        MainStage.setTitle("Add Appointment");
        MainStage.show();
        System.out.println("Moved to Add appointments");
    }
/** @param actionEvent This method deletes selected appointmen t*/
    public void onDeleteAppointment(ActionEvent actionEvent) {


        Appointment SelectedAppointment;

        int ApptID;
        SelectedAppointment = (Appointment) AppointmentTable.getSelectionModel().getSelectedItem();
        ApptID = SelectedAppointment.getAppointment_ID();

        String apptType;
        apptType = SelectedAppointment.getType();

        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setContentText(" Preparing to delete  the following appointment: Appointment ID: " + ApptID + " Appt Type: "  + apptType + " . Are you sure you want to delete this appointment from the data base?");
        warning.showAndWait();

        try{
            String statement = " DELETE FROM appointments WHERE Appointment_ID = ?";

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setInt(1,ApptID);

            System.out.println(" Statement I'm sending to SQL to delete appointment " + ps + " ");
            ps.executeUpdate();
            Alert  confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setContentText(" You have succefully deleted the following appointment: Appointment ID: " + ApptID + " Appt Type: "  + apptType + " ");
            confirmation.showAndWait();

        }
        catch( SQLException exception){
            System.out.println(" error deleting appointment");

        }
        try {
            PopulateAllAppointments();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

/**@param actionEvent this action moves user to Reports Screen and launches that screen*/
    public void onReports(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/Views/Reports.fxml"));
        Scene MainScene = new Scene(parent,1200,1200);
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        MainStage.setScene(MainScene);
        parent.setStyle("-fx-font-family: Times New Roman;");
        MainStage.setTitle("Reports");
        MainStage.show();
        System.out.println("Logged out of Appointment Table tab");
    }

    /**@param actionEvent this action moves user to the login screen and launches that screen*/
    public void onLogout(ActionEvent actionEvent)  throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/Views/LoginScreen.fxml"));
        Scene MainScene = new Scene(parent,900,400);
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        MainStage.setScene(MainScene);
        parent.setStyle("-fx-font-family: Times New Roman;");
        MainStage.setTitle("Login Screen");
        MainStage.show();
        System.out.println("Logged out of Appointments tab");
    }
    /**@param actionEvent this action populates the table view with all appointments */
    public void onAllAppointmentsView(ActionEvent actionEvent)  throws SQLException {
        PopulateAllAppointments();;

        }

    }

