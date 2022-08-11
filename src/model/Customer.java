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
    private static  ObservableList<String> AllCustomers = FXCollections.observableArrayList();


    public Customer() {

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



    public int getCustomerID() {
        return CustomerID;
    }
}

