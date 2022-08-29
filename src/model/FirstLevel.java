package model;

import Helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevel {
    private static  int CountryID;
    private static String Division;
    private static ObservableList<String> AllDivisions = FXCollections.observableArrayList();

    public FirstLevel(){

    };
    public FirstLevel(int CountryID,String Division){
        this.CountryID = CountryID;
        this.Division= Division;


    }
    public FirstLevel(String Division){

        this.Division = Division;


    }
    public String toString(){
        return(  Division);
    }

    /** This method returns a list of Divisions that correspond to the country ID
     *
     * */
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
}
