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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jdk.jfr.Frequency;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import static java.time.LocalTime.now;
import static javafx.collections.FXCollections.observableList;

public class AddAppt implements Initializable {
    @FXML
    private ComboBox ContactList;
    @FXML
    private ComboBox StartTime;
    @FXML
    private ComboBox EndTime;
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
    private DatePicker AddApptEndDate;
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
    private DateTimeFormatter Timeformatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM);
    @FXML
    private DateTimeFormatter DateFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
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

    }
    /**This method populates the StartTimes Combo Box
     */
    public void InsertStartTimes() {
        LocalTime LocalTimeHolder = LocalTime.MIN.plusHours(8);
        int i;
        for( i = 0; i<= 14; i++){
            OLStartTimes.add(LocalTimeHolder.format(Timeformatter));
            LocalTimeHolder = LocalTimeHolder.plusHours(1);
        }
        StartTime.setItems(OLStartTimes);

    }
    /**This method populates the EndTimes Combo Box
     */
    public void  InsertEndTimes(){
        LocalTime LocalTimeHolder = LocalTime.MIN.plusHours(8);
        int i;
        for( i = 0; i<= 14; i++){
            OLEndTimes.add(LocalTimeHolder.format(Timeformatter));
            LocalTimeHolder = LocalTimeHolder.plusHours(1);
        }
        EndTime.setItems(OLStartTimes);

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
