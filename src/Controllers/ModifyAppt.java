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

public class ModifyAppt implements Initializable {
    public ComboBox ModifyApptCustComboBox;
    public ComboBox ModifyApptContactBox;
    public ComboBox ModifyapptUserComboBox;
    public ComboBox ModifyApptStartTime;

    public ComboBox ModifyApptEndTime;
    public DatePicker ModifyApptDate;
    public TextField UserIDTextBox;
    public TextField CustomerIDTextBox;
    public TableView AppointmentTable;
    public TableColumn AppointmentID;
    public TableColumn CustomerID;
    public TableColumn User_ID;
    public TableColumn Title;
    public TableColumn Description;
    public TableColumn EndDateAndTime;
    public TableColumn Location;
    public TableColumn Contact;
    public TableColumn StartDateAndTime;
    public TableColumn Type;
    public TableColumn Contactt;
    @FXML
    private TextField ModifyApptDescription;
    @FXML
    private TextField ModifyApptID;
    @FXML
    public TextField ModifyApptUserID;
    @FXML
    private TextField ModifyApptTitle;
    @FXML
    private TextField ModifyApptCustID;
    @FXML
    private ChoiceBox ModifyApptContact;
    @FXML
    private TextField ModifyApptType;
    @FXML
    private TextField ModifyApptLocation;
    @FXML
    private DatePicker ModifyApptStartDate;
    @FXML
    private Button SaveModifyAppt;
    @FXML
    private Button ModifyAppointmentExit;

    private DateTimeFormatter Timeformatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private ObservableList<User> AllUsers = FXCollections.observableArrayList();
    @FXML
    private ObservableList <Customer> AllCustomers = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> OLStartTimes = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> OLEndTimes = FXCollections.observableArrayList();

    @FXML
    private ObservableList<Appointment> AllTableAppointments = FXCollections.observableArrayList();
    @FXML
    private ObservableList AppointmentStartTimesOL = FXCollections.observableArrayList();
    @FXML
    private  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @FXML
    private DateTimeFormatter formatterFor15MinChecking = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    @FXML
    private ZoneId CurrentZoneID = ZoneId.systemDefault();
    @FXML
    private ZoneId UTCID = ZoneId.of("UTC");
    private Connection connection;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InsertStartTimes();
        InsertEndTimes();
        ModifyApptContactBox.setItems(model.Contact.getContactNames());


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
        try {
            PopulateAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    /** Inserts End Times into the combobox*/
    public void InsertEndTimes() {
        LocalTime LocalTimeHolder = LocalTime.MIN.plusHours(8);
        int i;
        for (i = 0; i <= 14; i++) {
            OLEndTimes.add(LocalTimeHolder.format(Timeformatter));
            LocalTimeHolder = LocalTimeHolder.plusHours(1);
        }
        ModifyApptEndTime.setItems(OLStartTimes);

    }
    /**Inserts Start Times into the Combo Box*/
    public void InsertStartTimes() {
        LocalTime LocalTimeHolder = LocalTime.MIN.plusHours(8);
        int i;
        for (i = 0; i <= 14; i++) {
            OLStartTimes.add(LocalTimeHolder.format(Timeformatter));
            LocalTimeHolder = LocalTimeHolder.plusHours(1);
        }
        ModifyApptStartTime.setItems(OLStartTimes);

    }


    public void onModifyApptID(ActionEvent actionEvent) {
    }

    public void onModifyApptDescription(ActionEvent actionEvent) {
    }

    public void onModifyUserID(ActionEvent actionEvent) {
    }

    public void onModifyApptTitle(ActionEvent actionEvent) {
    }

    public void onModifyCustID(ActionEvent actionEvent) {
    }

    public void onModifyApptType(ActionEvent actionEvent) {
    }

    public void onModifyApptContact(MouseEvent mouseEvent) {
    }


    public void onModifyApptLocation(ActionEvent actionEvent) {
    }




    public void onModifyApptStartDate(ActionEvent actionEvent) {
    }







    public void onSaveModifyppt(ActionEvent actionEvent) {
        if (validateFields() == true ){
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
                System.out.println("Logged out of Modify Appointments tab");
            }
            catch (IOException e){
                System.out.println("Error moving from save  modified appt to full appts screen");
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please fix missing fields before proceeding");
                error.showAndWait();

            }
        }
    }

    public void onModifyAppointmentExit(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/Views/AppointmentTable.fxml"));
        Scene MainScene = new Scene(parent,3800,1200);
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        MainStage.setScene(MainScene);
        parent.setStyle("-fx-font-family: Times New Roman;");
        MainStage.setTitle("All Appointments");
        MainStage.show();
        System.out.println("Logged out of Modfy Appointments tab");
    }

    public void onAppointmentTable(SortEvent<TableView> tableViewSortEvent) {
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


                AllTableAppointments.add(new Appointment(Appointment_ID,Customer_ID,User_ID,Title,Description,Location,Contact_ID,Type,FormattedTableStart,FormattedTableEnd));
                AppointmentTable.setItems(AllTableAppointments);
                System.out.print(" Set all appts in table");

            }
        }
        catch(SQLException e) {
            System.out.println(" Error updating table");
        }


    }


    public void OnClickToEdit(ActionEvent actionEvent) {
        Appointment SelectedAppointment;
        SelectedAppointment = (Appointment) AppointmentTable.getSelectionModel().getSelectedItem();
        CustomerIDTextBox.setText(String.valueOf(SelectedAppointment.getCustomer_ID()));
        UserIDTextBox.setText(String.valueOf(SelectedAppointment.getUser_ID()));
        ModifyApptDescription.setText(SelectedAppointment.getDescription());
        ModifyApptTitle.setText(SelectedAppointment.getTitle());
        ModifyApptLocation.setText(SelectedAppointment.getLocation());
        ModifyApptType.setText(SelectedAppointment.getType());
        ModifyApptID.setText(String.valueOf(SelectedAppointment.getAppointment_ID()));

    }
    public Boolean validateFields() {

        String Description = ModifyApptDescription.getText();

        if (Description.isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please add a description. PS I LOVE YOU JOE");
            error.showAndWait();
        }
        String Customerid = CustomerIDTextBox.getText();
        if (Customerid.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please select a Customer Name");
                error.showAndWait();
            }

        }
        String Userid = UserIDTextBox.getText();
        if (Userid.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please select a User Name");
                error.showAndWait();
            }

        }
        String location = ModifyApptLocation.getText();
        if (location.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter a location");
                error.showAndWait();
            }

        }
        SingleSelectionModel contact = ModifyApptContactBox.getSelectionModel();
        if (contact.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please select a Contact ID");
                error.showAndWait();
            }


        }
        String type = ModifyApptType.getText();
        if (type.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter an appointment type");
                error.showAndWait();
            }
        }

        SingleSelectionModel Starttime = ModifyApptStartTime.getSelectionModel();
        if (Starttime.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter an appointment start time");
                error.showAndWait();
            }
        }
        SingleSelectionModel Endtime = ModifyApptEndTime.getSelectionModel();
        if (Endtime.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter an appointment end time");
                error.showAndWait();
            }
        }

        LocalDate Startdate = ModifyApptStartDate.getValue();
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
                if (TimeStartFromApp.before(DBstart) && TimeEndFromApp.before(DBend)) {
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

            }

        }


        catch (SQLException e){
            System.out.println(" Failed to check for overlapping appointments ");
        }
        System.out.println(" Overlapping boolean is " + overlaps + " ");
        return overlaps;



    }
}
