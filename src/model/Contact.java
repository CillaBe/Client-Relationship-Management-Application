package model;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contact {
   private static  int ContactID;
   private static String ContactName;
   private static String ContactEmail;
   private static  ObservableList<String> AllContacts = FXCollections.observableArrayList();
   public Contact(){

   };
   public Contact(int ContactID,String ContactName,String ContactEmail){
        this.ContactID = ContactID;
        this.ContactName =ContactName;
        this.ContactEmail = ContactEmail;

    }
    public Contact(int ContactID, String ContactName){
        this.ContactID = ContactID;
        this.ContactName =ContactName;


    }
    public int returnContactID(){
       return ContactID;
    }
    public String returnContactName(){
       return ContactName;
    }
    @Override
    public String toString(){
       return("#" + Integer.toString(ContactID) + " " + ContactName);
    }
    public static ObservableList<String> getContactNames(){
        Connection connection = JDBC.openConnection();
       try {
           String statement = ("SELECT * FROM contacts");
           PreparedStatement ps = connection.prepareStatement(statement);
           ResultSet rs = ps.executeQuery(statement);

           while (rs.next()) {
               String contactName = rs.getString("Contact_Name");
               int contactID = rs.getInt("Contact_ID");

               AllContacts.add(String.valueOf(new Contact(contactID,contactName)));

           }
       }
       catch (SQLException e){
           System.out.println("Error returning all contacts");


       }
       return AllContacts;
    }

}
