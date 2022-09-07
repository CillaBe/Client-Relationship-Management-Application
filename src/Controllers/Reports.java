package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

public class Reports implements Initializable {
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
}
