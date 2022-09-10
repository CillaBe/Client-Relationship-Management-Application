package Controllers;

import Helper.JDBC;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentTypeComboBox.setItems(Appointment.getApptTypes());

    }
    /** Function to sum total appointments by month and type*/

    public int SumTypeAndMonth (String type, int month) throws SQLException {
        Connection connection = JDBC.openConnection();
        int total = 0;

        try {
            String statement = "SELECT count(*) as total  FROM appointments Where extract(month from start) = ? and type = ?";

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setInt(1, month);
            ps.setString(2, type);
            System.out.println(" Statement I'm sending to SQL to get sum of month and type appts" + ps + " ");
            ResultSet rs = ps.executeQuery(statement);
            while (rs.next()) {
                 total = rs.getInt("total");
            }
        }
        catch (SQLException e){
            System.out.println("Error summing appts and typs");


        }
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

    public void onTotalButton(ActionEvent actionEvent) {
    }
}
