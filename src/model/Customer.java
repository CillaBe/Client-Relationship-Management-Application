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
    private static  ObservableList<String> AllCustomers = FXCollections.observableArrayList();


    public Customer(int CustomerID, String CustomerName, String Address, String PostalCode, String Phone, int DivisionID) {
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;
        this.Address = Address;
        this.PostalCode = PostalCode;
        this.Phone= Phone;
        this.DivisionID = DivisionID;


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
}

