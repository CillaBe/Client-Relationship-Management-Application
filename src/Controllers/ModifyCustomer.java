package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomer  implements Initializable {
    @FXML
    private TextField ModifyCustomerID;
    @FXML
    private TextField MddifyCustomerLastName;
    @FXML
    private TextField ModifyCustomerFirstName;
    @FXML
    private TextField ModifyCustomerAddress;
    @FXML
    private ChoiceBox ModifyState;
    @FXML
    private ChoiceBox ModifyCountry;
    @FXML

    private TextField ModifyCustomerPostalCode;
    @FXML
    private TextField ModifyCustomerPhoneNumber;
    @FXML
    private Button SaveModifyCustomer;
    @FXML
    private Button ModifyCustomerExit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onModifyCustomerLastName(ActionEvent actionEvent) {
    }

    public void oModifyCustomerID(ActionEvent actionEvent) {
    }

    public void onModifyCustomerAddress(ActionEvent actionEvent) {
    }

    public void oModifyCustomerFirstName(ActionEvent actionEvent) {
    }

    public void onModifyStateDragDetected(MouseEvent mouseEvent) {
    }

    public void onModifyCustomerStateMouseClick(MouseEvent mouseEvent) {
    }

    public void onModifyCountryDragDetected(MouseEvent mouseEvent) {
    }

    public void onModifyCustCountryMouseClick(MouseEvent mouseEvent) {
    }

    public void oModifyCustomerPostalCode(ActionEvent actionEvent) {
    }

    public void oModifyCustomerPhoneNumber(ActionEvent actionEvent) {
    }

    public void onSaveModifyCustomer(ActionEvent actionEvent) {
    }

    public void onModifyCustomerExit(ActionEvent actionEvent) {
    }
}
