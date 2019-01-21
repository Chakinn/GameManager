package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Role
{
    public static void setRole(String role, InfoDB infoDB, Connection connection)
    {
        PreparedStatement statement;
        String setRole="set role ?";

        try
        {
            statement=connection.prepareStatement(setRole);
            statement.setString(1,role);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
