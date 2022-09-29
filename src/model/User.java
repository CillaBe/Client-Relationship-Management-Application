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
 * User Class
 */
public class User {
    @FXML
   private String UserName;
    @FXML
    private int UserID;
    @FXML

    private static  ObservableList<String> AllUserNames = FXCollections.observableArrayList();

    /**
     * User Constructor
     */
    public User(){

    }

    /**
     * Sets User Name
     * @param UserName User Name
     */
    public User (String UserName){
        this.UserName = UserName;
    }

    /**
     * Gets User Name
     * @return UserName
     */
    public String toString(){
        return(UserName);
    }

    /**
     * Gets list of user names
     * @return UserNames
     */
    public static ObservableList<String> getUserNames(){
        Connection connection = JDBC.openConnection();
        AllUserNames.clear();
        try {
            String statement = ("SELECT users.User_Name FROM users");
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet rs = ps.executeQuery(statement);

            while (rs.next()) {
                String userName = rs.getString("User_Name");


                AllUserNames.add(String.valueOf(new User(userName)));

            }
        }
        catch (SQLException e){
            System.out.println("Error returning all user");


        }
        return AllUserNames;
    }
}








