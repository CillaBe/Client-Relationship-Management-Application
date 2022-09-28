package Helper;

import java.sql.*;

public class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection = null;  // Connection Interface

    /**
     *
     * @return returns conntection
     */
    public static Connection openConnection()  {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        return connection;
    }

    /**
     * Closes connection
     */

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

    }

    /**
     *
     * @param contactName take contact name and converts to contact ID
     * @return ContactName
     */
    public static int ConvertContactNameToContactID ( String contactName) {

        int ContactID = 0;
        try {
            String statement = ("SELECT Contact_ID FROM contacts WHERE Contact_Name = ?");

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setString(1, contactName);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                ContactID = rs.getInt("Contact_ID");
                System.out.println(" Contact_ID from ContactName is " + ContactID);


            }


        } catch (SQLException e) {
            System.out.println("Error returning ContactID from ContactName");


        }
        return ContactID;
    }

    /**
     *
     * @param customerName
     * @return CustomerID
     */
    public static int convertCustomerNameToCustID( String customerName){
        int CustomerID = 0;
        try {
            String statement = ("SELECT Customer_ID FROM customers WHERE Customer_Name = ?");

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setString(1, customerName);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                CustomerID = rs.getInt("Customer_ID");
                System.out.println(" CustomerID from CustomerName is " + CustomerID);


            }



        } catch (SQLException e) {
            System.out.println("Error returning CustomerID from CustomerName");


        }
        return CustomerID;
    }

    /**
     *
     * @param userName
     * @return userID
     */
    public static int convertUserNameToUserID( String userName){
        int UserID = 0;
        try {
            String statement = ("SELECT User_ID FROM users WHERE User_Name = ?");

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                UserID= rs.getInt("User_ID");
                System.out.println(" UserID from UserName is " + UserID + " ");


            }


        } catch (SQLException e) {
            System.out.println("Error returning UserID from UserName");


        }
        return UserID;
    }

    /**
     *
     * @param UserID
     * @return Username
     */
    public static String convertUserIDToUserName( int UserID){
        String Username = null;
        try {
            String statement = ("SELECT User_Name FROM users WHERE User_ID = ?");

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setInt(1, UserID);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                Username= rs.getString("User_Name");
                System.out.println(" UserName from UserID is " + Username + " ");


            }


        } catch (SQLException e) {
            System.out.println("Error returning Username from UserID");


        }
        return Username;
    }

    /**
     *
     * @param CountryName
     * @return Country ID
     */
    public static int converCountryNameToCountryID( String CountryName){
        int CountryID = 0;
        try {
            String statement = ("SELECT Country_ID FROM countries WHERE Country = ?");

            PreparedStatement ps = JDBC.openConnection().prepareStatement(statement);
            ps.setString(1, CountryName);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                CountryID= rs.getInt("Country_ID");
                System.out.println(" Country ID from Country Name is " + CountryID + " ");


            }


        } catch (SQLException e) {
            System.out.println("Error returning Country Id from Country");


        }
        return CountryID;
    }









    }


