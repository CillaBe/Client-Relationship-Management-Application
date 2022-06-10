package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentTable implements Initializable {
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
    private TableView AppointmentTable;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    public void onLogout(ActionEvent actionEvent) {
    }
}
