package Controllers;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.time.*;
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
    public TableColumn DivisionID;
    public TextField ModifyCustName;
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
    private Connection connection;
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

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
    public void PopulateCustomers()throws SQLException {
        System.out.print(" Trying to populate  customers ");
        try {
            String statement = "SELECT  customers.Customer_ID,customers.Customer_Name,customers.Address," +
                    "customers.Postal_Code,customers.Phone,customers.Division_ID FROM customers";
            connection = JDBC.openConnection();
            ResultSet rs = connection.createStatement().executeQuery(statement);

            allCustomers.clear();
            while (rs.next()) {
                int Customer_ID = rs.getInt("Customer_ID");
                String  Customer_Name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Postal_Code = rs.getString("Postal_Code");
                String Phone= rs.getString("Phone");
                int Division_ID = rs.getInt("Division_ID");
                String Division = Customer.ConvertDivision(Division_ID);
                String Country = Customer.convertToCountry(Division_ID);



                allCustomers.add(new Customer(Customer_ID,Customer_Name,Address, Division,Country, Postal_Code,Phone,Division_ID));
                CustomerTable.setItems(allCustomers);
                System.out.print(" Set all customers in table");

            }
        }
        catch(SQLException e) {
            System.out.println(" Error populating customer table");
        }


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
 /** Updated Customers to database and checks that they are all filled out*/
    public void onUpdateCustToDB(ActionEvent actionEvent) {

        int CustomerID = Integer.parseInt(ModifyCustID.getText());

        System.out.println(" Customer ID : " + CustomerID + " ");

        String Address = (ModifyCustAddress.getText());

        System.out.println(" Cust Address: " + Address + " ");


        String CustomerName = (ModifyCustName.getText());

        System.out.println(" Customer Name is " + CustomerName + " ");


        String PostalCode = ModifyCustPostalCode.getText();
        System.out.println(" Customer postal code is " + PostalCode + " ");

        String PhoneNumber= ModifyCustPhoneNumber.getText();
        System.out.println(" Customer phone number is  " + PhoneNumber + " ");

        String Division = (String) ModifyStateProvCombBox.getSelectionModel().getSelectedItem();
        System.out.println(" Customer Division is  " + Division+ " ");

        int DivisionID = Customer.ConvertDivisionToID(Division);
        System.out.println(" Customer Division ID is  " + DivisionID + " ");



        /** Update Customer that matches the Customer ID*/
        try{
            String statement = "UPDATE customers  SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Last_Update =  CURRENT_TIMESTAMP WHERE Customer_ID = ?";

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);

            ps.setString(1,CustomerName);
            ps.setString(2,Address);
            ps.setString(3,PostalCode);
            ps.setString(4,PhoneNumber);
            ps.setInt(5, DivisionID);
            ps.setInt(6, CustomerID);

            System.out.println(" Statement I'm sending to SQL to update Customer " + ps + " ");
            ps.executeUpdate();
            try {
                PopulateCustomers();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        catch( SQLException exception){
            System.out.println(" error updating Customer");

        }
    }
/** Pulls selectec Customer data over to be modified in the appropriate fields*/
    public void OnModifySelectedCustomer(ActionEvent actionEvent) {
        Customer SelectedCustomer;
        SelectedCustomer= (Customer) CustomerTable.getSelectionModel().getSelectedItem();
        ModifyCustID.setText(String.valueOf(SelectedCustomer.getCustomerID()));
        ModifyCustName.setText(SelectedCustomer.getCustomerName());
        ModifyCustAddress.setText(String.valueOf(SelectedCustomer.getAddress()));
        ModifyCountryCombobox.setValue(SelectedCustomer.getCountry());
        ModifyStateProvCombBox.setValue(SelectedCustomer.getDivision());
        ModifyCustPhoneNumber.setText(SelectedCustomer.getPhone());
        ModifyCustPostalCode.setText(SelectedCustomer.getPostalCode());


    }
 /** Populates State/Providence combo box with  corresponding data based on which country is selected*/
    public void OnCountryAction(ActionEvent actionEvent) {
        String SelectedCountry = (String) ModifyCountryCombobox.getSelectionModel().getSelectedItem();
        int CountryID = JDBC.converCountryNameToCountryID(SelectedCountry);
        ModifyStateProvCombBox.setItems(model.FirstLevel.PopulateDivisonFromID(CountryID));
    }
}
