package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Reports implements Initializable {
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
}
