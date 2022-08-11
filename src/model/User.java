package model;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    public String UserName;
    public int UserID;

    private static  ObservableList<String> AllUserNames = FXCollections.observableArrayList();

    public User(){

    }
    public User (String UserName){
        this.UserName = UserName;
    }
    public String toString(){
        return(UserName);
    }
    public static ObservableList<String> getUserNames(){
        Connection connection = JDBC.openConnection();
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








