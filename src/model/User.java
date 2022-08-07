package model;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    int UserID;
    String UserName;
   public static ObservableList <User> AllUsers = FXCollections.observableArrayList();





    public User(int UserID, String UserName){
        this.UserID = UserID;
        this.UserName = UserName;

    }
    @Override
    public String toString() {
        return ("#" + Integer.toString(UserID) + " " + UserName);
    }
   /* public static ObservableList<String> getUserIDs(){
        Connection connection = JDBC.openConnection();
        try {
            String statement = ("SELECT * FROM users");
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet rs = ps.executeQuery(statement);

            while (rs.next()) {

                int UserID = rs.getInt("User_ID");
                String UserName = rs.getString("User_Name");
                AllUserIDs.add(String.valueOf(new User(UserID,UserName)));



            }
        }
        catch (SQLException e){
            System.out.println("Error returning all users");


        }
        return AllUserIDs;
    }*/

    public int getUserID() {
        return UserID;
    }
    public String getUserName(){
        return UserName;
    }
    public  void setUserName(String Name){
        UserName = Name;
    }
    public  void  setUserID(int ID){
        UserID = ID;
    }



}

