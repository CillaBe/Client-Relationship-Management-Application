package Controllers;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import model.Appointment;
import model.Contact;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Reports implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentTypeComboBox.setItems(Appointment.getApptTypes());
        AppointmentMonthComboBox.setItems(getAllMonths());


    }
    /** Function to convert month to number*/
    public int MonthtoNumber(String month){
        int monthNum = 0;
        if ( month == "January"){monthNum = 1;};
        if ( month == "February"){monthNum = 2;};
        if ( month == "March"){monthNum = 3;} ;
        if ( month == "April"){monthNum = 4;} ;
        if ( month == "May"){monthNum = 5;} ;

        if ( month == "June"){monthNum = 6;} ;
        if ( month == "July"){monthNum = 7;} ;
        if ( month == "August"){monthNum = 8;} ;
        if ( month == "September"){monthNum = 9;} ;
        if ( month == "October"){monthNum = 10;} ;
        if ( month == "November"){monthNum = 11;} ;
        if ( month == "December"){monthNum = 12;} ;

        return  monthNum;


    }

    /** Function to get months in a list*/
    public static ObservableList<String> getAllMonths(){
        AllMonths.clear();
        String[] Months = {"January" , "February", "March", "April","May", "June", "July", "August", "September", "October", "November", "December"};
        AllMonths.addAll(Months);
        return AllMonths;
    }


    /** Function to sum total appointments by month and type*/

    public String SumTypeAndMonth (String type, int month) throws SQLException {
        Connection connection = JDBC.openConnection();
        String total = null;

        try {
            String statement = "SELECT count(*) as total FROM appointments Where extract(month from start) = ? and type = ?";

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setInt(1, month);
            ps.setString(2, type);
            System.out.println(" Statement I'm sending to SQL to get sum of month and type appts" + ps + " ");
            ResultSet rs = ps.executeQuery(statement);
            rs.next();
            total = rs.getString("total");
            System.out.println(total);

        }
        catch (SQLException e){
            System.out.println("Error summing appts and types");


        }
        System.out.println(" Total from SumTypeMonth function is " + total + " ");
        return total;

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
        String total;
        if(month.isEmpty() || type.isEmpty()){Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Error, please select both appointment type and appointment month");
        alert.showAndWait();


        }
        else {
            monNum = MonthtoNumber(month);
            total = SumTypeAndMonth(type,monNum);

            TotalTextBox.setText(String.valueOf(total));

        }

    }
}
