package Controllers;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import model.Appointment;
import model.Contact;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Reports implements Initializable {
    @FXML
    private TableColumn AppointmentID;
    @FXML
    private TableColumn Title;
    @FXML
    private TableColumn Type;
    @FXML
    private TableColumn Description;
    @FXML
    private TableColumn AppointmentStart;
    @FXML
    private TableColumn AppointmentEnd;
    @FXML
    private TableColumn CustomerID;
    @FXML
    private ComboBox ContactComboBox;
    @FXML
    private TextField TotalTextBox;
    @FXML
    private Button TotalButton;
    @FXML
    private TextField TotalTypeTextBox;
    @FXML
    private TextField TotalMonthTextBox;
    @FXML
    private Button TotalMonthButton;
    @FXML
    private Button TotalTypeButton;
    @FXML
    private ComboBox AppointmentMonthComboBox;
    @FXML
    private ComboBox AppointmentTypeComboBox;
    @FXML
    private TableView ScheduleByContactTableView;
    @FXML
    private TableView TotalAppointmentsByTypeAndMonthTable;
    @FXML
    private TableView ScheduleByDateTableView;
    @FXML
    private DatePicker ReportsDateSelection;
    @FXML
    private static ObservableList<String> AllMonths = FXCollections.observableArrayList();
    @FXML
    private Connection connection;
    @FXML
    private ObservableList<Appointment> AllContactAppointments = FXCollections.observableArrayList();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @FXML
    private ZoneId CurrentZoneID = ZoneId.systemDefault();
    @FXML
    private ZoneId UTCID = ZoneId.of("UTC");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentTypeComboBox.setItems(Appointment.getApptTypes());
        AppointmentMonthComboBox.setItems(getAllMonths());
        ContactComboBox.setItems(Contact.getContactNames());

        AppointmentID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        AppointmentStart.setCellValueFactory(new PropertyValueFactory<>("Start"));
        AppointmentEnd.setCellValueFactory(new PropertyValueFactory<>("End"));
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));



    }
    /** Function to populate Appointments based on ContactID*/
   public void PopulateAppointmentonContactID(int ContactId) {
System.out.print(" Trying to populate appointments by ContactID");
        try {
        String statement = "SELECT  Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments WHERE Contact_ID = \"" + ContactId + "\"";
        connection = JDBC.openConnection();
        ResultSet rs = connection.createStatement().executeQuery(statement);
        System.out.print(" Query for all appts Successful! ");
        AllContactAppointments.clear();
        while (rs.next()) {
            int Appointment_ID = rs.getInt("Appointment_ID");
            int Customer_ID = rs.getInt("Customer_ID");
            String Title = rs.getString("Title");
            String Description = rs.getString("Description");
            String Type = rs.getString("Type");
            String StartString = rs.getString("Start").substring(0,19);
            String EndString = rs.getString("End").substring(0,19);
            System.out.println(" Current Zone Id: " + CurrentZoneID+ " UTC Zone ID " + UTCID+ " ");


            //*Convert Start and End Times to Local Date Time then ZonedDateAndTime*/
            LocalDateTime StartLocal = LocalDateTime.parse(StartString,formatter);
            LocalDateTime EndLocal = LocalDateTime.parse(EndString,formatter);

            ZonedDateTime ZonedStart = StartLocal.atZone(UTCID).withZoneSameInstant(CurrentZoneID);
            ZonedDateTime ZonedEnd = EndLocal.atZone(UTCID).withZoneSameInstant(CurrentZoneID);
            //*Convert back to string to store in Appointment object and table*/


            String FormattedTableStart = ZonedStart.format(formatter);
            String FormattedTableEnd = ZonedEnd.format(formatter);


            AllContactAppointments.add(new Appointment(Appointment_ID,Customer_ID,Title,Description,Type,FormattedTableStart,FormattedTableEnd));
            ScheduleByContactTableView.setItems(AllContactAppointments);
            System.out.print(" Set all appts in table by contact Id");

        }
    }
            catch(SQLException e) {
        System.out.println(" Error updating table by contactID");
    }


}
    /**
     * Function to convert month to number
     */
    public int MonthtoNumber(String month) {
        int monthNum = 0;
        if (month == "January") {
            monthNum = 1;
        }
        ;
        if (month == "February") {
            monthNum = 2;
        }
        ;
        if (month == "March") {
            monthNum = 3;
        }
        ;
        if (month == "April") {
            monthNum = 4;
        }
        ;
        if (month == "May") {
            monthNum = 5;
        }
        ;

        if (month == "June") {
            monthNum = 6;
        }
        ;
        if (month == "July") {
            monthNum = 7;
        }
        ;
        if (month == "August") {
            monthNum = 8;
        }
        ;
        if (month == "September") {
            monthNum = 9;
        }
        ;
        if (month == "October") {
            monthNum = 10;
        }
        ;
        if (month == "November") {
            monthNum = 11;
        }
        ;
        if (month == "December") {
            monthNum = 12;
        }
        ;

        return monthNum;


    }
    /** Function to populate appointment table based on ContactID*/


    /**
     * Function to sum total appointments by month and type
     */

    public int SumTypeAndMonth(String type, int month) throws SQLException {
        int total;
        Connection connection = JDBC.openConnection();
        ResultSet rs = connection.createStatement().executeQuery("SELECT count(*) as total  FROM appointments Where month(start) = \"" + month + "\" and type = \"" + type + "\" ");
        rs.next();
        total = rs.getInt("total");
        return total;


    }
    public static ObservableList<String> getAllMonths(){
        AllMonths.clear();
        String[] Months = {"January" , "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December"};
        AllMonths.addAll(Months);
        return AllMonths;
    }


    public void onReportsContactTab(ActionEvent actionEvent) {
    }

    public void onReportsContactClicked(MouseEvent mouseEvent) {
    }

    public void onTotalApptsByMonthReport(ActionEvent actionEvent) {
    }

    public void ReportsApptTypeClicked(MouseEvent mouseEvent) {
    }

    public void onReportsApptMonth(ActionEvent actionEvent) {
    }

    public void onReportsApptType(ActionEvent actionEvent) {
    }

    public void onReportsMonthClicked(MouseEvent mouseEvent) {
    }

    public void onReportsDateSelection(ActionEvent actionEvent) {
    }

    public void onReportsDateContextMenuRequested(ContextMenuEvent contextMenuEvent) {
    }

    public void onReportsDateClicked(MouseEvent mouseEvent) {
    }

    public void onExitReports(ActionEvent actionEvent) {
    }

    public void onApptTypeComboBox(ActionEvent actionEvent) {
    }

    public void onApptMonthCombo(ActionEvent actionEvent) {
    }

    public void onTotalTypeButton(ActionEvent actionEvent) {
    }

    public void onTotalMonth(ActionEvent actionEvent) {
    }

    public void onTotalTextBox(ActionEvent actionEvent) {
    }

    public void onTotalButton(ActionEvent actionEvent) throws SQLException {
        String type = (String) AppointmentTypeComboBox.getSelectionModel().getSelectedItem();
        String month = (String) AppointmentMonthComboBox.getSelectionModel().getSelectedItem();
        int monNum = 0;
        int total;
        if (month.isEmpty() || type.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error, please select both appointment type and appointment month");
            alert.showAndWait();


        } else {
            monNum = MonthtoNumber(month);
            total = SumTypeAndMonth(type, monNum);

            TotalTextBox.setText(String.valueOf(total));

        }

    }

    public void onContactComboBox(ActionEvent actionEvent) {
    }


    public void onExitReports() {
    }


    public void onReportsDateClicked() {
    }

    public void onReportsDateContextMenuRequested() {
    }

    public void onReportsDateSelection() {
    }

    public void onContactComboBox() {

    }


    public void onTotalTextBox() {
    }

    public void onApptTypeComboBox() {
    }

    public void onPopulateScheduleByContact(ActionEvent actionEvent) {
        String ContactName = String.valueOf(ContactComboBox.getSelectionModel().getSelectedItem());
        int contactId = JDBC.ConvertContactNameToContactID(ContactName);
        PopulateAppointmentonContactID(contactId);

    }
}

