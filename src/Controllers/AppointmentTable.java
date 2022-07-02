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

import java.io.IOError;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import static java.time.format.DateTimeFormatter.*;

public class AppointmentTable implements Initializable {
    @FXML
    public RadioButton AllAppointments;
    public ToggleGroup apptTgroup;
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
    private Connection connection;
    @FXML
    private ObservableList<Appointment> AllTableAppointments = FXCollections.observableArrayList();
    @FXML
    private  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @FXML
    private  ZoneId CurrentZoneID = ZoneId.systemDefault();
    @FXML
    private ZoneId UTCID = ZoneId.of("UTC");


    @FXML
    private JDBC DbHandler;


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

 /** This method populates all appointments to the schedule tableview.
  *
  * */
    }
    public void PopulateAllAppointments() throws SQLException {
        System.out.print(" Trying to populate appointments");
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
                String StartString = rs.getString("Start").substring(0,19);
                String EndString = rs.getString("End").substring(0,19);
                System.out.println(" Current Zone Id: " + CurrentZoneID+ " UTC Zone ID " + UTCID+ " ");


                //*Convert Start and End Times to Local Date Time then ZonedDateAndTime*?
                LocalDateTime StartLocal = LocalDateTime.parse(StartString,formatter);
                LocalDateTime EndLocal = LocalDateTime.parse(EndString,formatter);

                ZonedDateTime ZonedStart = StartLocal.atZone(UTCID).withZoneSameInstant(CurrentZoneID);
                ZonedDateTime ZonedEnd = EndLocal.atZone(UTCID).withZoneSameInstant(CurrentZoneID);
                //*Convert back to string to store in Appointment object and table*/


                String FormattedTableStart = ZonedStart.format(formatter);
                String FormattedTableEnd = ZonedEnd.format(formatter);


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
  * @param  actionEvent populated month vview*/

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
                String StartString = rs.getString("Start").substring(0, 19);
                String EndString = rs.getString("End").substring(0, 19);
                System.out.println(" Current Zone Id: " + CurrentZoneID + " UTC Zone ID " + UTCID + " ");


                //*Convert Start and End Times to Local Date Time then ZonedDateAndTime*?
                LocalDateTime StartLocal = LocalDateTime.parse(StartString, formatter);
                LocalDateTime EndLocal = LocalDateTime.parse(EndString, formatter);

                ZonedDateTime ZonedStart = StartLocal.atZone(UTCID).withZoneSameInstant(CurrentZoneID);
                ZonedDateTime ZonedEnd = EndLocal.atZone(UTCID).withZoneSameInstant(CurrentZoneID);
                //*Convert back to string to store in Appointment object and table*/


                String FormattedTableStart = ZonedStart.format(formatter);
                String FormattedTableEnd = ZonedEnd.format(formatter);


                AllTableAppointments.add(new Appointment(Appointment_ID, Customer_ID, User_ID, Title, Description, Location, Contact_ID, Type, FormattedTableStart, FormattedTableEnd));
            }

            LocalDate today = LocalDate.now();
            LocalDate MonthLater = today.plusMonths(1);

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

    public void onWeekView(ActionEvent actionEvent) {
    }

    public void onCustomerDatabase(ActionEvent actionEvent) {
    }

    public void onModifyAppointment(ActionEvent actionEvent) {
    }

    public void onAddAppointment(ActionEvent actionEvent) {
    }

    public void onDeleteAppointment(ActionEvent actionEvent) {
    }

    public void onReports(ActionEvent actionEvent) {
    }

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

    public void onAllAppointmentsView(ActionEvent actionEvent)  throws SQLException {
        PopulateAllAppointments();;

        }
    }

