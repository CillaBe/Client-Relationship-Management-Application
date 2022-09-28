package model;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    @FXML
   private String UserName;
    @FXML
    private int UserID;
    @FXML

    private static  ObservableList<String> AllUserNames = FXCollections.observableArrayList();

    public User(){

    }

    /**
     *
     * @param UserName
     */
    public User (String UserName){
        this.UserName = UserName;
    }

    /**
     *
     * @return UserName
     */
    public String toString(){
        return(UserName);
    }

    /**
     *
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








