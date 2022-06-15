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
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class AppointmentTable implements Initializable {
    @FXML
    public RadioButton AllAppointments;
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
    ObservableList<Appointment> AllTableAppointments = FXCollections.observableArrayList();

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
            try {
                PopulateAllAppointments();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }











    }
    public void PopulateAllAppointments() throws SQLException {
        try {
            Connection connection = JDBC.openConnection();
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM appointments");
            while(rs.next()){
                AllTableAppointments.add(new Appointment(rs.getString("Appointment_ID"), rs.getString("Customer_ID"),
                        rs.getString("User_ID"), rs.getString("Title"), rs.getString("Description"),
                        rs.getString("Location"), rs.getString("Contact"), rs.getString("Type"),
                        rs.getString("Start"), rs.getString("End")));
            }



        }
        catch (SQLException e){
            System.out.print("Error loading appointments from database");
        }
        AppointmentTable.setItems(AllTableAppointments);
        JDBC.closeConnection();
    }



    public void onAppointmentTable(SortEvent<TableView> tableViewSortEvent) {
    }

    public void onMonthView(ActionEvent actionEvent) {
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

        }
    }

