package model;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Customer Class
 */
public class Customer {
    @FXML
    private int CustomerID;
    @FXML
    private String CustomerName;
    @FXML
    private String Address;
    @FXML
    private String PostalCode;
    @FXML
    private String Phone;
    @FXML
    private int DivisionID;
    @FXML
    private String Division;
    @FXML
    private String Country;
    @FXML
    private static  ObservableList<String> AllCustomers = FXCollections.observableArrayList();

    /**
     * Customer Constructor
     * @param CustomerID Customer ID
     * @param CustomerName Customer Name
     * @param Address Address
     * @param Division Divsion
     * @param Country Country
     * @param PostalCode Postal Code
     * @param Phone Phone
     * @param DivisionID Divsion ID
     */

    public Customer(int CustomerID, String CustomerName, String Address,String Division, String Country, String PostalCode, String Phone, int DivisionID) {
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;
        this.Address = Address;
        this.PostalCode = PostalCode;
        this.Phone= Phone;
        this.DivisionID = DivisionID;
        this.Division = Division;
        this.Country = Country;


    }

    /**
     * Customer Constructor
     */
    public Customer(){

    }

    /**
     * Sets Customer Name
     * @param CustomerName Customer Name
     */

    public Customer( String CustomerName) {

        this.CustomerName = CustomerName;

    }

    /**
     * Gets Customer Name as string
     * @return Customer Name
     */
    @Override
    public String toString() {

        return (CustomerName);
    }

    /**
     * Gets List of Customer Names
     * @return CustomerNames
     */


    public static ObservableList<String> getCustomerNames(){
        Connection connection = JDBC.openConnection();
        AllCustomers.clear();
        try {
            String statement = ("SELECT customers.Customer_Name FROM customers");
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet rs = ps.executeQuery(statement);

            while (rs.next()) {
                String customerName = rs.getString("Customer_Name");


                AllCustomers.add(String.valueOf(new Customer(customerName)));

            }
        }
        catch (SQLException e){
            System.out.println("Error returning all customer names");


        }
        return AllCustomers;
    }


    /**
     * Gets Customer ID
     * @return CustomerID
     */
    public int getCustomerID() {
        return CustomerID;
    }

    /**
     * Gets Division ID
     * @return Division ID
     */
    public int getDivisionID() { return  DivisionID;}

    /**
     * Gets Customer Name
     * @return CustomerName
     */

    public String getCustomerName() {return CustomerName;}

    /**
     * Gets Address
     * @return Address
     */

    public  String getAddress() {return Address;}

    /**
     * Gets Postal Code
     * @return PostalCode
     */

    public  String getPostalCode () {return PostalCode;}

    /**
     * Gets phone number
     * @return Phone
     */

    public  String getPhone (){return Phone;}

    /**
     * Gets Division
     * @return Division
     */

    public String getDivision(){return Division;}

    /**
     * Gets Country
     * @return Country
     */

    public String getCountry(){return Country;}

    /**
     * Conerts Divsion Id to Division
     * @param DivisionID Division ID
     * @return Division
     */

    public static String ConvertDivision(int DivisionID){
        String Division = null;
        try {
            String statement = ("SELECT Division FROM first_level_divisions WHERE Division_ID = ?");

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setInt(1, DivisionID);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                Division= rs.getString("Division");
                System.out.println(" Division from Division ID is " + Division + " ");


            }


        } catch (SQLException e) {
            System.out.println("Error returning Division from Division ID");


        }
        return Division;
    }


    /**
     * Conversion Division Id to Country
     * @param DivisionID Division ID
     * @return Country Country
     */
    public static String convertToCountry ( int DivisionID ){
        String Country = null;
        try {
            String statement = ("SELECT countries.Country FROM countries JOIN  first_level_divisions ON countries.Country_ID = first_level_divisions.Country_ID  WHERE first_level_divisions.Division_ID = ?");

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setInt(1, DivisionID);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                Country = rs.getString("Country");
                System.out.println(" Country from Division ID is " + Country+ " ");


            }


        } catch (SQLException e) {
            System.out.println("Error returning Country from Division ID");


        }
        return Country;


    }

    /**
     * Converts Divsion to Division Id
     * @param Division Division
     * @return Division ID
     */

    public static int ConvertDivisionToID (String Division){
         int DivisionID = 0;
        try {
            String statement = ("SELECT Division_ID FROM first_level_divisions WHERE Division = ?");

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setString(1, Division);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                DivisionID= rs.getInt("Division_ID");
                System.out.println(" Division ID from Division is " + DivisionID + " ");


            }


        } catch (SQLException e) {
            System.out.println("Error returning Division ID from Division");


        }
        return DivisionID;
    }

    }


