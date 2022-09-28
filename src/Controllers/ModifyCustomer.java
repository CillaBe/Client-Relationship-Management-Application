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

    /**
     *
     * @param url initialzes the screen
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onModifyCustomerLastName(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void oModifyCustomerID(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */

    public void onModifyCustomerAddress(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */

    public void oModifyCustomerFirstName(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */

    public void onModifyStateDragDetected(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */

    public void onModifyCustomerStateMouseClick(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */

    public void onModifyCountryDragDetected(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onModifyCustCountryMouseClick(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */

    public void oModifyCustomerPostalCode(ActionEvent actionEvent) {
    }

    /** This method was not used in this application,
     * @param actionEvent not used in this application */

    public void oModifyCustomerPhoneNumber(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */


    public void onSaveModifyCustomer(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */


    public void onModifyCustomerExit(ActionEvent actionEvent) {
    }
}
