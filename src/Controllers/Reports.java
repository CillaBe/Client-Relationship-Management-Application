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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Reports implements Initializable {
    @FXML
    private TableColumn CustApptID;
    @FXML
    private TableColumn CustEnd;
    @FXML
    private TableColumn CustTitle;
    @FXML
    private TableColumn CustCustID;
    @FXML
    private TableColumn CustType;
    @FXML
    private TableColumn CustStart;
    @FXML
    private TableColumn CustDescription;
    @FXML
    private Button exitreports;
    @FXML
    private TableView ScheduleByCustomerTableView;
    @FXML
    private ComboBox CustomerComboBox;
    @FXML
    private Button PopulateCustScheduleButton;
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
    private Connection connection = JDBC.openConnection();
    @FXML
    private ObservableList<Appointment> AllContactAppointments = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Appointment> AllCustomerAppointments = FXCollections.observableArrayList();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy h:mm a");
    @FXML
    private ZoneId CurrentZoneID = ZoneId.systemDefault();
    @FXML
    private ZoneId UTCID = ZoneId.of("UTC");

    /**
     *
     * @param url initializes the screen
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentTypeComboBox.setItems(Appointment.getApptTypes());
        AppointmentMonthComboBox.setItems(getAllMonths());
        ContactComboBox.setItems(Contact.getContactNames());
        CustomerComboBox.setItems(Customer.getCustomerNames());

        AppointmentID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        Type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        AppointmentStart.setCellValueFactory(new PropertyValueFactory<>("Start"));
        AppointmentEnd.setCellValueFactory(new PropertyValueFactory<>("End"));
        CustomerID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));

        CustApptID.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        CustTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        CustDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        CustType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        CustStart.setCellValueFactory(new PropertyValueFactory<>("Start"));
        CustEnd.setCellValueFactory(new PropertyValueFactory<>("End"));
        CustCustID.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));



    }

    /**
     *
     * @param ContactId Function to populate Appointments based on ContactID
     */
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

            Timestamp timeStart = rs.getTimestamp("Start");
            Timestamp timeEnd = rs.getTimestamp("End");

            LocalDateTime start = timeStart.toLocalDateTime();
            LocalDateTime end = timeEnd.toLocalDateTime();

            String FormattedTableStart = start.format(formatter);
            String FormattedTableEnd = end.format(formatter);



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
     *
     * @param CustomerId populate Appointments based on CustomerID
     */
    public void PopulateAppointmentonCustomerID(int CustomerId) {
        System.out.print(" Trying to populate appointments by Customer ID ");
        try {
            String statement = "SELECT  Appointment_ID, Title, Type, Description, Start, End, Customer_ID FROM appointments WHERE Contact_ID = \"" + CustomerId + "\"";
            connection = JDBC.openConnection();
            ResultSet rs = connection.createStatement().executeQuery(statement);
            System.out.print(" Query for all appts Successful! ");
            AllCustomerAppointments.clear();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                int Customer_ID = rs.getInt("Customer_ID");
                String Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Type = rs.getString("Type");


                Timestamp timeStart = rs.getTimestamp("Start");
                Timestamp timeEnd = rs.getTimestamp("End");

                LocalDateTime start = timeStart.toLocalDateTime();
                LocalDateTime end = timeEnd.toLocalDateTime();

                String FormattedTableStart = start.format(formatter);
                String FormattedTableEnd = end.format(formatter);


                AllCustomerAppointments.add(new Appointment(Appointment_ID,Customer_ID,Title,Description,Type,FormattedTableStart,FormattedTableEnd));
                ScheduleByCustomerTableView.setItems(AllCustomerAppointments);
                System.out.print(" Set all appts in table by Customer ID ");

            }
        }
        catch(SQLException e) {
            System.out.println(" Error updating table by customer ID");
        }


    }

    /**
     *
     * @param month month to convert to month
     * @return returns corresponding number of montth
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


    /**
     *
     * @param type type to sum in function
     * @param month month to sum in the function
     * @return returns total appointments by type and month
     * @throws SQLException exception
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

    /**
     *
     * @param actionEvent not used in this application
     */


    public void onReportsContactTab(ActionEvent actionEvent) {
    }

    /**
     *
     * @param mouseEvent not used in this application
     */

    public void onReportsContactClicked(MouseEvent mouseEvent) {
    }

    /**
     *
     * @param actionEvent not used in this application
     */

    public void onTotalApptsByMonthReport(ActionEvent actionEvent) {
    }

    /**
     *
     * @param mouseEvent not used in this application
     */

    public void ReportsApptTypeClicked(MouseEvent mouseEvent) {
    }

    /**
     *
     * @param actionEvent not used in this application
     */

    public void onReportsApptMonth(ActionEvent actionEvent) {
    }

    /**
     *
     * @param actionEvent not used in this application
     */

    public void onReportsApptType(ActionEvent actionEvent) {
    }

    /**
     *
     * @param mouseEvent not used in this application
     */

    public void onReportsMonthClicked(MouseEvent mouseEvent) {
    }

    /**
     *
     * @param actionEvent not used in this application
     */

    public void onReportsDateSelection(ActionEvent actionEvent) {
    }

    /**
     *
     * @param contextMenuEvent not used in this application
     */

    public void onReportsDateContextMenuRequested(ContextMenuEvent contextMenuEvent) {
    }

    /**
     *
     * @param mouseEvent not used in this application
     */

    public void onReportsDateClicked(MouseEvent mouseEvent) {
    }

    /**
     *
     * @param actionEvent exits the Reports screen and sends user back to main appointment table screen
     * @throws IOException Exception
     */

    public void onExitReports(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/Views/AppointmentTable.fxml"));
        Scene MainScene = new Scene(parent,3800,1200);
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        MainStage.setScene(MainScene);
        parent.setStyle("-fx-font-family: Times New Roman;");
        MainStage.setTitle("Appointment Table");
        MainStage.show();
        System.out.println("Logged out of Reports tab");
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */

    public void onApptTypeComboBox(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */


    public void onApptMonthCombo(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */

    public void onTotalTypeButton(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */

    public void onTotalMonth(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */

    public void onTotalTextBox(ActionEvent actionEvent) {
    }

    /**
     *
     * @param actionEvent totals appointments by type and month and displays them in TotalTextBox
     * @throws SQLException exception
     */

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
    /** This method was not used in this application,
     * @param actionEvent not used in this application */

    public void onContactComboBox(ActionEvent actionEvent) {
    }


    /**
     * not used
     */
    public void onExitReports() {
    }


    /** generated in scenebuilder and not used*/
    public void onReportsDateClicked() {
    }
    /** generated in scenebuilder and not used*/
    public void onReportsDateContextMenuRequested() {
    }
    /** generated in scenebuilder and not used*/
    public void onReportsDateSelection() {
    }
    /** generated in scenebuilder and not used*/
    public void onContactComboBox() {

    }
    /** generated in scenebuilder and not used*/

    public void onTotalTextBox() {
    }
    /** generated in scenebuilder and not used*/
    public void onApptTypeComboBox() {
    }
 /** @param actionEvent Populates schedule by contact table view when pressed*/
    public void onPopulateScheduleByContact(ActionEvent actionEvent) {
        String ContactName = String.valueOf(ContactComboBox.getSelectionModel().getSelectedItem());
        int contactId = JDBC.ConvertContactNameToContactID(ContactName);
        PopulateAppointmentonContactID(contactId);

    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */

    public void onCustomerComboBox(ActionEvent actionEvent) {
    }
    /**
     * @param actionEvent populates customer schedule table view*/

    public void onPopulateCustScheduleButton(ActionEvent actionEvent) {
        String Customer = String.valueOf(CustomerComboBox.getSelectionModel().getSelectedItem());
        int customerId = JDBC.convertCustomerNameToCustID(Customer);
        PopulateAppointmentonCustomerID(customerId);
    }
}

