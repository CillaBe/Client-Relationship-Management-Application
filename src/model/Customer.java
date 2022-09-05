package model;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    private int CustomerID;
    private String CustomerName;
    String Address;
    String PostalCode;
    String Phone;
    int DivisionID;
    String Division;
    String Country;
    private static  ObservableList<String> AllCustomers = FXCollections.observableArrayList();


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
    public Customer(){

    }


    public Customer( String CustomerName) {

        this.CustomerName = CustomerName;

    }

    @Override
    public String toString() {
        /**return (CustomerName)*/
        return (CustomerName);
    }


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


/** Create Getters*/
    public int getCustomerID() {
        return CustomerID;
    }

    public int getDivisionID() { return  DivisionID;}

    public String getCustomerName() {return CustomerName;}

    public  String getAddress() {return Address;}

    public  String getPostalCode () {return PostalCode;}

    public  String getPhone (){return Phone;}

    public String getDivision(){return Division;}

    public String getCountry(){return Country;}

    /** Converts Division ID to Division Name*/

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


    /** Converts Division ID to Country*/
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
    /** Converts Division to Division ID*/

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


