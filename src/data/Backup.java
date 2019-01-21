package data;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Backup
{
    public static void backup()
    {
        String cmd = "c:\\MariaDB\\bin\\mysqldump -u gamemaster1 -p12345 --result-file=d:\\javadump.sql --single-transaction gamemanager";
        try
        {
            Process runtimeProcess = Runtime.getRuntime().exec(cmd);
            int processComplete = runtimeProcess.waitFor();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void importSQL(InfoDB infoDB, InputStream in) throws SQLException
    {
        Connection connection = null;
        Statement statement = null;
        try
        {
            Scanner s = new Scanner(in);
            s.useDelimiter("(;(\r)?\n)|((\r)?\n)?(--)?.*(--(\r)?\n)");

            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            statement = connection.createStatement();
            while (s.hasNext())
            {
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/"))
                {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }

                if (line.trim().length() > 0)
                {
                    statement.execute(line);
                }
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        } finally
        {
            if (statement != null)
            {
                try
                {
                    statement.close();
                } catch (SQLException e)
                { /* ignored */}
            }
            if (connection != null)
            {
                try
                {
                    connection.close();
                } catch (SQLException e)
                { /* ignored */}
            }
        }
    }
}
