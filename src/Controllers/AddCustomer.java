package Controllers;

import Helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.FirstLevel;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    @FXML
    private int newInt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int min = 5;
        int max = 100;
        newInt = (int) Math.floor(Math.random() * (max - min + 1) + min);
        AddCustomerID.setText(String.valueOf(newInt));
        CountryComboBox.setItems(model.Country.getCountryNames());

    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddCustomerLastName(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddCustomerFirstName(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddCustomerID(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddCustomerAddress(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddStateDragDetected(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddCustomerStateMouseClick(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddCustCountryMouseClick(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param mouseEvent not used in this application */
    public void onAddCountryDragDetected(MouseEvent mouseEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onAddCustomerPostalCode(ActionEvent actionEvent) {
    }

    /**
     * @param actionEvent Adds Customer to datatbase if all fields are filled out
     */
    public void onSaveAddCustomer(ActionEvent actionEvent) {

        if( ValidateCustomers()== true) {
            int CustomerID = Integer.parseInt(AddCustomerID.getText());
            String CustomerName = AddCustomerFirstName.getText();
            String Address = AddCustomerAddress.getText();
            String Country = (String) CountryComboBox.getSelectionModel().getSelectedItem();
            String StateProv = (String) StateProvComboBox.getSelectionModel().getSelectedItem();
            String PostalCode = AddCustomerPostalCode.getText();
            String Phone = AddCustomerPhoneNumber.getText();
            int DivisionID = FirstLevel.ConvertDivisionToDivisionID(StateProv);

            try {
                String statement = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Last_Update, Division_ID)" +
                        "VALUES(?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,?)";

                PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
                ps.setInt(1, newInt);
                ps.setString(2, CustomerName);
                ps.setString(3, Address);
                ps.setString(4, PostalCode);
                ps.setString(5, Phone);
                ps.setInt(6, DivisionID);


                System.out.println(" Statement I'm sending to SQL to insert appt " + ps + " ");
                ps.executeUpdate();
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/Views/CustomerTable.fxml"));
                    Scene MainScene = new Scene(parent, 2000, 2000);
                    Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    MainStage.setScene(MainScene);
                    parent.setStyle("-fx-font-family: Times New Roman;");
                    MainStage.setTitle("Customer Database");
                    MainStage.show();
                    System.out.println("Logged out of Appointments tab");


                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (SQLException exception) {
                System.out.println(" error adding customer ");

            }
        }
        else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please correct missing fields and try again");
            error.showAndWait();
        }
    }
    /** This method was not used in this application,
     * @param actionEventnot used in this application */
    public void onAddCustomerPhoneNumber(ActionEvent actionEvent) {
    }
 /** @param actionEvent Exits Add Customer Screen and sends user back to Customer Database Screen*/
    public void onAddCustomerExit(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/Views/CustomerTable.fxml"));
            Scene MainScene = new Scene(parent, 2000, 2000);
            Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            MainStage.setScene(MainScene);
            parent.setStyle("-fx-font-family: Times New Roman;");
            MainStage.setTitle("Customer Database");
            MainStage.show();
            System.out.println("Logged out of Appointments tab");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param actionEvent Populates State/Providence combo box with  corresponding data based on which country is selected
     */
    public void OnCountryComboBox(ActionEvent actionEvent) {
        String SelectedCountry = (String) CountryComboBox.getSelectionModel().getSelectedItem();
        int CountryID = JDBC.converCountryNameToCountryID(SelectedCountry);
        StateProvComboBox.setItems(model.FirstLevel.PopulateDivisonFromID(CountryID));

    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void OnStateProvComboBox(ActionEvent actionEvent) {
    }
/** Validates all customer fields are filled out*/
    public boolean ValidateCustomers() {
        String Name = AddCustomerFirstName.getText();
        if (Name.isEmpty()) {
            {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setContentText("Error, please enter customer name");
                error.showAndWait();
            }
        }
        String Address = AddCustomerAddress.getText();
        if(Address.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please enter customer address");
            error.showAndWait();

        }
        SingleSelectionModel Country = CountryComboBox.getSelectionModel();
        if(Country.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please select customer address");
            error.showAndWait();

        }
        SingleSelectionModel StateProv = StateProvComboBox.getSelectionModel();
        if(StateProv.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please select customer state or province");
            error.showAndWait();

        }
        String PostalCode = AddCustomerPostalCode.getText();
        if(PostalCode.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please enter customer postal code");
            error.showAndWait();

        }
        String Phone = AddCustomerPhoneNumber.getText();
        if(Phone.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please enter customer phone number");
            error.showAndWait();

        }
        if (Name.isEmpty() || Address.isEmpty() || Country.isEmpty() || StateProv.isEmpty() || PostalCode.isEmpty() || Phone.isEmpty()) {
            return false;
        } else {
            return true;

        }


    }
}
