/**
 * Model Package
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Helper.JDBC;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Country Class
 */
public class Country {
    @FXML
    private static  int CountryID;
    @FXML
    private static String CountryName;
    @FXML
    private static ObservableList<String> AllCountries = FXCollections.observableArrayList();

    public Country(){

    };

    /**
     * Country Constuctor
     * @param CountryID Country ID
     * @param CountryName Country Name
     */
    public Country(int CountryID,String CountryName){
        this.CountryID = CountryID;
        this.CountryName = CountryName;


    }

    /**
     * Sets Country Name
     * @param CountryName Country Name
     */
    public Country(String CountryName){

        this.CountryName = CountryName;


    }

    /**
     * Gets Country Name as string
     * @return Country Name
     */
    public String toString(){
        return(  CountryName);
    }

    /**
     * Gets list of Country Names
     * @return Country Names
     */
    public static ObservableList<String> getCountryNames(){
        Connection connection = JDBC.openConnection();
        AllCountries.clear();
        try {
            String statement = ("SELECT countries.Country FROM countries");
            PreparedStatement ps = connection.prepareStatement(statement);
            ResultSet rs = ps.executeQuery(statement);

            while (rs.next()) {
                String countryName = rs.getString("Country");


                AllCountries.add(String.valueOf(new Country(countryName)));

            }
        }
        catch (SQLException e){
            System.out.println("Error returning all contacts");


        }
        return AllCountries;
    }
}
