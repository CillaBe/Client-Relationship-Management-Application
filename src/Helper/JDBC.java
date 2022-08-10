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

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
        /** Converts ContactName to it's corresponding contactID from the database*/
    }
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
    public static String returnUser(){
        return UserN
    }




    }


