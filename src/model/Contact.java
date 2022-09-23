package model;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contact {
    @FXML
   private static  int ContactID;
    @FXML
   private static String ContactName;
    @FXML
   private static String ContactEmail;
    @FXML
   private static  ObservableList<String> AllContacts = FXCollections.observableArrayList();

   public Contact(){

   }

    /**
     *
     * @param ContactID
     * @param ContactName
     * @param ContactEmail
     */

   public Contact(int ContactID,String ContactName,String ContactEmail){
        this.ContactID = ContactID;
        this.ContactName =ContactName;
        this.ContactEmail = ContactEmail;

    }

    /**
     *
     * @param ContactName
     */
    public Contact(String ContactName){

        this.ContactName =ContactName;


    }

    /**
     *
     * @return
     */
    public int returnContactID(){
       return ContactID;
    }

    /**
     *
     * @return
     */
    public String returnContactName(){
       return ContactName;
    }

    /**
     *
     * @return
     */

    public String toString(){
       return(  ContactName);
    }

    /**
     *
     * @return
     */
    public static ObservableList<String> getContactNames(){
        Connection connection = JDBC.openConnection();
        AllContacts.clear();
       try {
           String statement = ("SELECT contacts.Contact_Name FROM contacts");
           PreparedStatement ps = connection.prepareStatement(statement);
           ResultSet rs = ps.executeQuery(statement);

           while (rs.next()) {
               String contactName = rs.getString("Contact_Name");


               AllContacts.add(String.valueOf(new Contact(contactName)));

           }
       }
       catch (SQLException e){
           System.out.println("Error returning all contacts");


       }
       return AllContacts;
    }

}
