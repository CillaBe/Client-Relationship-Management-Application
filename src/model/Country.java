package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Helper.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Country {
    private static  int CountryID;
    private static String CountryName;
    private static ObservableList<String> AllCountries = FXCollections.observableArrayList();

    public Country(){

    };
    public Country(int CountryID,String CountryName){
        this.CountryID = CountryID;
        this.CountryName = CountryName;


    }
    public Country(String CountryName){

        this.CountryName = CountryName;


    }
    public String toString(){
        return(  CountryName);
    }
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
