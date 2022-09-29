package Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabase {


    private static final String SelectUserNames = " SELECT * FROM users WHERE User_Name = ? and Password = ?";

    /**
     *
     * @param userName takes username
     * @param Password takes password
     * @return returns true or false if  does or does not find a corresponding username and password
     * @throws SQLException Exception
     */
    public boolean checkUser(String userName, String Password) throws SQLException {
        Connection connection = JDBC.openConnection();
        PreparedStatement statement = connection.prepareStatement(SelectUserNames);
        statement.setString(1, userName);
        statement.setString(2, Password);

        ResultSet rs = statement.executeQuery();
        return rs.next();

    }
}
