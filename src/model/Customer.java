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
   private  String CustomerName;
   private static ObservableList<String> AllCustomerIDs= FXCollections.observableArrayList();

    Customer(){

    }


        Customer( int CustomerID, String CustomerName){
        this.CustomerID = CustomerID;
        this.CustomerName = CustomerName;

    }
    @Override
    public String toString() {
        return ("#" + Integer.toString(CustomerID) + " " + CustomerName);
    }
    public static ObservableList<String> getCustomerIDs(){
        Connection connection = JDBC.openConnection();
        try {
            String statement = ("SELECT * FROM customers");
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet rs = ps.executeQuery(statement);

            while (rs.next()) {

                int customerID = rs.getInt("Customer_ID");
                String CustomerName = rs.getString("Customer_Name");
                AllCustomerIDs.add(String.valueOf(new Customer(customerID,CustomerName)));



            }
        }
        catch (SQLException e){
            System.out.println("Error returning all customers");


        }
        return AllCustomerIDs;
    }
}

