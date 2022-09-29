package model;


import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * First Level Class
 */
public class FirstLevel {
    @FXML
    private static  int CountryID;
    @FXML
    private static String Division;
    @FXML
    private static ObservableList<String> AllDivisions = FXCollections.observableArrayList();

    /**
     * First Level Constructor
     */
    public FirstLevel(){

    }

    /**
     * First Level Constructor
     * @param CountryID Country ID
     * @param Division Division
     */
    public FirstLevel(int CountryID,String Division){
        this.CountryID = CountryID;
        this.Division= Division;


    }

    /**
     * Sets Divsion
     * @param Division Division
     */
    public FirstLevel(String Division){

        this.Division = Division;


    }

    /**
     * Gets Divsion
     * @return returns Division
     */
    public String toString(){
        return(  Division);
    }

    /**
     * Gets list of Divisions from Country ID
     * @param CountryID Country ID
     * @return Division Division
     */
    public static ObservableList<String> PopulateDivisonFromID( int CountryID){
        String Division = null;
        AllDivisions.clear();

        try {
            String statement = ("SELECT Division FROM first_level_divisions WHERE Country_ID = ?");

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setInt(1, CountryID);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                Division= rs.getString("Division");
                AllDivisions.add(String.valueOf(new FirstLevel(Division)));
                System.out.println(" Country ID from Division Function is " + CountryID + " List of Divisions is " + AllDivisions + " ");



            }


        } catch (SQLException e) {
            System.out.println("Error returning Divisions from Country ID");


        }
        return AllDivisions;

    }

    /**
     * Converts Division to Division ID
     * @param Division Division
     * @return DivisionID
     */
    public static int ConvertDivisionToDivisionID ( String Division) {
        int DivisionID = 0;


        try {
            String statement = ("SELECT Division_ID FROM first_level_divisions WHERE Division = ?");

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setString(1, Division);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                DivisionID = rs.getInt("Division_ID");

                System.out.println(" Division ID from Division Function is " + DivisionID + " ");


            }


        } catch (SQLException e) {
            System.out.println("Error returning Division_ID from Division");


        }
        return DivisionID;
    }


}
