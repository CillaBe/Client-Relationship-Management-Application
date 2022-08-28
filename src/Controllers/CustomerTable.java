package Controllers;

import Helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerTable implements Initializable {
    public TableColumn CustomerName;
    public TextField ModifyCustPhoneNumber;
    public TextField ModifyCustID;
    public TextField ModifyCustAddress;
    public ComboBox ModifyStateProvCombBox;
    public TextField ModifyCustPostalCode;
    public ComboBox ModifyCountryCombobox;
    public Button ExitCustomer;
    public Button UpdateCustToDB;
    public Button DeleteCustomer;
    public Button UpdateSelectedCustomer;
    public Button AddNewCustomer;
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
        ModifyCountryCombobox.setItems(model.Country.getCountryNames());

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

    public void onModifyApptDescription(ActionEvent actionEvent) {
    }

    public void CustomerID(ActionEvent actionEvent) {
    }

    public void onModifyApptTitle(ActionEvent actionEvent) {
    }

    public void onUpdateCustToDB(ActionEvent actionEvent) {
    }

    public void OnModifySelectedCustomer(ActionEvent actionEvent) {
    }

    public void OnCountryAction(ActionEvent actionEvent) {
        String SelectedCountry = (String) ModifyCountryCombobox.getSelectionModel().getSelectedItem();
        int CountryID = JDBC.converCountryNameToCountryID(SelectedCountry);
        ModifyStateProvCombBox.setItems(model.FirstLevel.PopulateDivisonFromID(CountryID));
    }
}
