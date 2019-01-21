package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClanDAO
{
    InfoDB infoDB;

    public ClanDAO(InfoDB info)
    {
        infoDB=info;
    }

    public List<String[]> getClans(String search)
    {
        List<String[]> list= new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String select= "select clanname,count(playerID) as playerCount from gamemanager.clans c join gamemanager.players p on p.clanID=c.clanID where clanname like ? group by c.clanID";
        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("player",infoDB,connection);

            statement = connection.prepareStatement(select);
            statement.setString(1,search);

            rs = statement.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            String[] columns=new String[2];
            columns[0]=rsmd.getColumnLabel(1);
            columns[1]=rsmd.getColumnLabel(2);
            list.add(columns);

            while(rs.next())
            {
                String[] strings=new String[2];
                strings[0]=rs.getString(1);
                strings[1]=rs.getString(2);
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


    public List<String[]> getClanMembers(String nickname, String search)
    {
        List<String[]> list= new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String select= "select nickname from gamemanager.players p join gamemanager.clans c on p.clanID=c.clanID where p.clanID=(select clanID from gamemanager.players " +
                "where nickname like ? ) and nickname like ?";
        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("player",infoDB,connection);

            statement = connection.prepareStatement(select);
            statement.setString(1,nickname);
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
