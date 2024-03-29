package Controllers;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.*;
import java.util.ResourceBundle;

/**
 * Customer Table Class
 */
public class CustomerTable implements Initializable {
    @FXML
    private TableColumn CustomerName;
    @FXML
    private TextField ModifyCustPhoneNumber;
    @FXML
    private TextField ModifyCustID;
    @FXML
    private TextField ModifyCustAddress;
    @FXML
    private ComboBox ModifyStateProvCombBox;
    @FXML
    private TextField ModifyCustPostalCode;
    @FXML
    private ComboBox ModifyCountryCombobox;
    @FXML
    private Button ExitCustomer;
    @FXML
    private Button UpdateCustToDB;
    @FXML
    private Button DeleteCustomer;
    @FXML
    private Button UpdateSelectedCustomer;
    @FXML
    private Button AddNewCustomer;
    @FXML
    private TableColumn DivisionID;
    @FXML
   private TextField ModifyCustName;
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
    @FXML
    private Connection connection;
    @FXML
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * Initializes screen
     * @param url initializes screen
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ModifyCountryCombobox.setItems(model.Country.getCountryNames());

        CustomerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        CustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        StreetAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        State.setCellValueFactory(new PropertyValueFactory<>("Division"));
        Country.setCellValueFactory(new PropertyValueFactory<>("Country"));
        PostalCode.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        PhoneNumber.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        DivisionID.setCellValueFactory(new PropertyValueFactory<>("DivisionID"));
        try {
            PopulateCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Populates Customers
     * @throws SQLException Populates Customers
     */
    public void PopulateCustomers() throws SQLException {
        System.out.print(" Trying to populate  customers ");
        try {
            String statement = "SELECT  customers.Customer_ID,customers.Customer_Name,customers.Address," +
                    "customers.Postal_Code,customers.Phone,customers.Division_ID FROM customers";
            connection = JDBC.openConnection();
            ResultSet rs = connection.createStatement().executeQuery(statement);

            allCustomers.clear();
            while (rs.next()) {
                int Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Postal_Code = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                int Division_ID = rs.getInt("Division_ID");
                String Division = Customer.ConvertDivision(Division_ID);
                String Country = Customer.convertToCountry(Division_ID);


                allCustomers.add(new Customer(Customer_ID, Customer_Name, Address, Division, Country, Postal_Code, Phone, Division_ID));
                CustomerTable.setItems(allCustomers);
                System.out.print(" Set all customers in table");

            }
        } catch (SQLException e) {
            System.out.println(" Error populating customer table");
        }


    }

    /**
     * Sends user to Add Customer Screen*
     * @param actionEvent Sends user to Add Customer Screen*
     * @throws IOException exception
     */
    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/Views/AddCustomer.fxml"));
        Scene MainScene = new Scene(parent,1200,1200);
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        MainStage.setScene(MainScene);
        parent.setStyle("-fx-font-family: Times New Roman;");
        MainStage.setTitle("Add Customer");
        MainStage.show();
        System.out.println("Logged out of Customer Table tab");
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onModiftyCustomer(ActionEvent actionEvent) {
    }

    /**
     * Deletes Customer if there are no corresponding appointments for that customer in the database
     * @param actionEvent actionEvent Deletes Customer if there are no corresponding appointments for that customer in the database
     * @throws SQLException exception
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
        warning.setContentText("Are you sure you want to delete this customer from the data base?");
        warning.showAndWait();

        Customer SelectedCustomer;
        ResultSet rs = null;
        int CustID;
        SelectedCustomer = (Customer) CustomerTable.getSelectionModel().getSelectedItem();
        CustID = SelectedCustomer.getCustomerID();
        try{
            String statement = " SELECT  * FROM appointments WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setInt(1,CustID);

            System.out.println(" Statement I'm sending to SQL to check for cust appts before deleting " + ps + " ");
             rs = ps.executeQuery();

        }
        catch( SQLException exception){
            System.out.println(" error checking for corresponding appointments ");

        }
        if (rs.next() == true){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, this customer has appointments scheduled. Please delete corresponding appointments and try again");
            error.showAndWait();

        }
        else{
            try{
                String statement = " DELETE FROM customers WHERE Customer_ID = ?";

                PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
                ps.setInt(1,CustID);

                System.out.println(" Statement I'm sending to SQL to delete customer " + ps + " ");
                ps.executeUpdate();

            }
            catch( SQLException exception){
                System.out.println(" error deleting customer");

            }

        }
        try {
            PopulateCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exits the Customer table and goes back to the Appointment Table
     * @param actionEvent  Exits the Customer table and goes back to the Appointment Table*
     * @throws IOException exception
     */
    public void onExitCustomer(ActionEvent actionEvent) throws IOException {
        Parent parent= FXMLLoader.load(getClass().getResource("/Views/AppointmentTable.fxml"));
        Scene MainScene = new Scene(parent,3800,1200);
        Stage MainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        MainStage.setScene(MainScene);
        parent.setStyle("-fx-font-family: Times New Roman;");
        MainStage.setTitle("Appointment Table");
        MainStage.show();
        System.out.println("Logged out of Customer Table tab");
    }
    /** This method was not used in this application,
     * @param tableViewSortEvent not used in this application */
    public void onCustomerTable(SortEvent<TableView> tableViewSortEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void onModifyApptDescription(ActionEvent actionEvent) {
    }
    /** This method was not used in this application,
     * @param actionEvent not used in this application */
    public void CustomerID(ActionEvent actionEvent) {
    }

    /**
     *  not used in application
     * @param actionEvent not used in application
     */
    public void onModifyApptTitle(ActionEvent actionEvent) {
    }

    /** Updates Customer record in the database and checks that they are all filled out
     * @param actionEvent Updates Customer record in the database and checks that they are all filled out
     */
    public void onUpdateCustToDB(ActionEvent actionEvent) {
        boolean CustomerValidated = validateFieldsCust();

        int CustomerID = Integer.parseInt(ModifyCustID.getText());

        System.out.println(" Customer ID : " + CustomerID + " ");

        String Address = (ModifyCustAddress.getText());

        System.out.println(" Cust Address: " + Address + " ");


        String CustomerName = (ModifyCustName.getText());

        System.out.println(" Customer Name is " + CustomerName + " ");


        String PostalCode = ModifyCustPostalCode.getText();
        System.out.println(" Customer postal code is " + PostalCode + " ");

        String PhoneNumber = ModifyCustPhoneNumber.getText();
        System.out.println(" Customer phone number is  " + PhoneNumber + " ");

        String Division = (String) ModifyStateProvCombBox.getSelectionModel().getSelectedItem();
        System.out.println(" Customer Division is  " + Division + " ");

        int DivisionID = Customer.ConvertDivisionToID(Division);
        System.out.println(" Customer Division ID is  " + DivisionID + " ");


        /**  Update Customer that matches the Customer ID if fields are validated*/
        if (CustomerValidated == true) {
            try {
                String statement = "UPDATE customers  SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Last_Update =  CURRENT_TIMESTAMP WHERE Customer_ID = ?";

                PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);

                ps.setString(1, CustomerName);
                ps.setString(2, Address);
                ps.setString(3, PostalCode);
                ps.setString(4, PhoneNumber);
                ps.setInt(5, DivisionID);
                ps.setInt(6, CustomerID);

                System.out.println(" Statement I'm sending to SQL to update Customer " + ps + " ");
                ps.executeUpdate();
                try {
                    PopulateCustomers();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException exception) {
                System.out.println(" error updating Customer");

            }
        }
        else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please correct missing fields and try again");
            error.showAndWait();
        }
    }

    /** Pulls selected Customer data over to be modified in the appropriate fields
     * @param actionEvent Pulls selected Customer data over to be modified in the appropriate fields
     */
    public void OnModifySelectedCustomer(ActionEvent actionEvent) {
        Customer SelectedCustomer;
        SelectedCustomer = (Customer) CustomerTable.getSelectionModel().getSelectedItem();
        ModifyCustID.setText(String.valueOf(SelectedCustomer.getCustomerID()));
        ModifyCustName.setText(SelectedCustomer.getCustomerName());
        ModifyCustAddress.setText(String.valueOf(SelectedCustomer.getAddress()));
        ModifyCountryCombobox.setValue(SelectedCustomer.getCountry());
        ModifyStateProvCombBox.setValue(SelectedCustomer.getDivision());
        ModifyCustPhoneNumber.setText(SelectedCustomer.getPhone());
        ModifyCustPostalCode.setText(SelectedCustomer.getPostalCode());


    }

    /** Populates State/Providence combo box with  corresponding data based on which country is selected
     * @param actionEvent Populates State/Providence combo box with  corresponding data based on which country is selected
     */
    public void OnCountryAction(ActionEvent actionEvent) {
        String SelectedCountry = (String) ModifyCountryCombobox.getSelectionModel().getSelectedItem();
        int CountryID = JDBC.converCountryNameToCountryID(SelectedCountry);
        ModifyStateProvCombBox.setItems(model.FirstLevel.PopulateDivisonFromID(CountryID));
    }

    /**
     * returns true if fields are filled out
     * @return returns true if fields are filled out
     */
    public Boolean validateFieldsCust() {

        String Name = ModifyCustName.getText();

        if (Name.isEmpty()) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please add a client name");
            error.showAndWait();
        }
        String Address = ModifyCustAddress.getText();
        if (Address.isEmpty()) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please add a client address");
            error.showAndWait();


        }
        SingleSelectionModel Country = ModifyCountryCombobox.getSelectionModel();
        if (Country.isEmpty()) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please select a country");
            error.showAndWait();


        }
        SingleSelectionModel Province = ModifyStateProvCombBox.getSelectionModel();
        if (Province.isEmpty()) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please select a state or province");
            error.showAndWait();


        }
        String PostalCode = ModifyCustPostalCode.getText();
        if (PostalCode.isEmpty()) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please add a Postal Code");
            error.showAndWait();


        }
        String PhoneNumber = ModifyCustPhoneNumber.getText();
        if (PhoneNumber.isEmpty()) {

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setContentText("Error, please add a phone number");
            error.showAndWait();


        }
        if (Name.isEmpty() || Address.isEmpty() || Country.isEmpty() || Province.isEmpty() || PostalCode.isEmpty() || PhoneNumber.isEmpty()) {
            return false;
        } else {
            return true;

        }
    }

    /**
     * not used in application
     * @param mouseEvent not used in application
     */

    public void test(MouseEvent mouseEvent) {
    }
}

