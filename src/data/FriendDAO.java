package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendDAO
{
    InfoDB infoDB;

    public FriendDAO(InfoDB info)
    {
        infoDB=info;
    }

    public List<String[]> getFriends(String nick, String search)
    {
        List<String[]> list= new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String select= "select p2.nickname from gamemanager.friends f join gamemanager.players p1 on p1.playerID=f.player1 join gamemanager.players p2 on f.player2=p2.playerID where p1.nickname like ? and p2.nickname like ?";
        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("player",infoDB,connection);

            statement = connection.prepareStatement(select);
            statement.setString(1,nick);
            statement.setString(2,search);

            rs = statement.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            String[] columns=new String[1];
            columns[0]=rsmd.getColumnLabel(1);
            list.add(columns);

            while(rs.next())
            {
                String[] strings=new String[1];
                strings[0]=rs.getString(1);
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
