package Controllers;

import Helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {
    @FXML
    private Label CustomerName;
    @FXML
    private ComboBox CountryComboBox;
    @FXML
    private ComboBox StateProvComboBox;
    @FXML
    private TextField AddCustomerLastName;
    @FXML
    private TextField AddCustomerID;
    @FXML
    private TextField AddCustomerFirstName;
    @FXML
    private TextField AddCustomerAddress;
    @FXML
    private ChoiceBox AddState;
    @FXML
    private ChoiceBox AddCountry;
    @FXML
    private TextField AddCustomerPhoneNumber;
    @FXML
    private TextField AddCustomerPostalCode;
    @FXML
    private Button SaveAddCustomer;
    @FXML
    private Button AddCustomerExit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountryComboBox.setItems(model.Country.getCountryNames());

    }

    public void onAddCustomerLastName(ActionEvent actionEvent) {
    }

    public void onAddCustomerFirstName(ActionEvent actionEvent) {
    }

    public void onAddCustomerID(ActionEvent actionEvent) {
    }

    public void onAddCustomerAddress(ActionEvent actionEvent) {
    }

    public void onAddStateDragDetected(MouseEvent mouseEvent) {
    }

    public void onAddCustomerStateMouseClick(MouseEvent mouseEvent) {
    }

    public void onAddCustCountryMouseClick(MouseEvent mouseEvent) {
    }

    public void onAddCountryDragDetected(MouseEvent mouseEvent) {
    }

    public void onAddCustomerPostalCode(ActionEvent actionEvent) {
    }

    public void onSaveAddCustomer(ActionEvent actionEvent) {
    }

    public void onAddCustomerPhoneNumber(ActionEvent actionEvent) {
    }

    public void onAddCustomerExit(ActionEvent actionEvent) {
    }
    /**
     * Populates State/Providence combo box with  corresponding data based on which country is selected
     */
    public void OnCountryComboBox(ActionEvent actionEvent) {
        String SelectedCountry = (String) CountryComboBox.getSelectionModel().getSelectedItem();
        int CountryID = JDBC.converCountryNameToCountryID(SelectedCountry);
        StateProvComboBox.setItems(model.FirstLevel.PopulateDivisonFromID(CountryID));

    }

    public void OnStateProvComboBox(ActionEvent actionEvent) {
    }
}
