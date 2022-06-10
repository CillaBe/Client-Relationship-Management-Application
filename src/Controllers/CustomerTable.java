package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerTable implements Initializable {
    @FXML
    private TableView CustomerTable;
    @FXML
    private TableColumn CustomerID;
    @FXML
    private TableColumn CustomerFirstName;
    @FXML
    private TableColumn City;
    @FXML
    private TableColumn CustomerLastName;
    @FXML
    private TableColumn State;
    @FXML
    private TableColumn Country;
    @FXML
    private TableColumn StreetAddress;
    @FXML
    private TableColumn PostalCode;
    @FXML
    private TableColumn PhoneNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onAddCustomer(ActionEvent actionEvent) {
    }

    public void onModiftyCustomer(ActionEvent actionEvent) {
    }

    public void onDeleteCustomer(ActionEvent actionEvent) {
    }

    public void onExitCustomer(ActionEvent actionEvent) {
    }

    public void onCustomerTable(SortEvent<TableView> tableViewSortEvent) {
    }
}
