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


    public Customer() {

    }


    public Customer(int CustomerID, String CustomerName) {
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;

    }

    @Override
    public String toString() {
        /**return ("#" + Integer.toString(CustomerID) + " " + CustomerName);*/
        return "[" + CustomerID + "] " + CustomerName;
    }

    public static ObservableList<Customer> getCustomerIDAndNames() {
        Connection connection = JDBC.openConnection();
        ObservableList<Customer> allCustomerNamesIds = FXCollections.observableArrayList();
        try {
            String statement = ("SELECT customers.Customer_ID, customers.Customer_Name FROM customers");
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet rs = ps.executeQuery(statement);

            while (rs.next()) {
                Customer cust = new Customer();
                int customerID = rs.getInt("Customer_ID");
                String CustomerName = rs.getString("Customer_Name");
                allCustomerNamesIds.add(new Customer(customerID, CustomerName));


            }
            return allCustomerNamesIds;
        } catch (SQLException e) {
            System.out.println("Error returning all customers");


        }
        return null;

    }



    public int getCustomerID() {
        return CustomerID;
    }
}

