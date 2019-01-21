package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO
{
    InfoDB infoDB;

    public HistoryDAO(InfoDB info)
    {
        infoDB=info;
    }

    public List<String[]> getHistory(String nick,String search)
    {
        List<String[]> list= new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String select= "select p1.nickname nick1,p2.nickname nick2, dat,winner from gamemanager.players p1 join gamemanager.matches m on p1.playerID=m.player1 join gamemanager.players p2 on p2.playerID=m.player2 " +
                "where (p1.nickname like ? or p2.nickname like ?) and (p1.nickname like ? or p2.nickname like ?)";
        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("player",infoDB,connection);

            statement = connection.prepareStatement(select);
            statement.setString(1,nick);
            statement.setString(2,nick);
            statement.setString(3,search);
            statement.setString(4,search);

            rs = statement.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            String[] columns=new String[4];
            columns[0]=rsmd.getColumnLabel(1);
            columns[1]=rsmd.getColumnLabel(2);
            columns[2]=rsmd.getColumnLabel(3);
            columns[3]=rsmd.getColumnLabel(4);
            list.add(columns);

            while(rs.next())
            {
                String[] strings=new String[4];
                strings[0]=rs.getString(1);
                strings[1]=rs.getString(2);
                strings[2]=rs.getString(3);
                strings[3]=rs.getString(4);
                list.add(strings);
            }
            return list;
        }
        catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if(rs!=null)
            {
                try
                {
                    rs.close();
                } catch (SQLException e)
                { /* ignored */}
            }
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
