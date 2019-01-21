package data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InsertDAO
{
    InfoDB infoDB;

    public InsertDAO(InfoDB info)
    {
        infoDB=info;
    }

    public int addMatchRecord(int playerID1,int playerID2,String winner)
    {
        int ret=0;
        Connection connection = null;
        PreparedStatement statement = null;
        String insert= "insert into gamemanager.matches(player1,player2,dat,winner) values (?,?,current_timestamp(),?)";
        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("gamemaster",infoDB,connection);

            statement = connection.prepareStatement(insert);
            statement.setInt(1,playerID1);
            statement.setInt(2,playerID2);
            statement.setString(3,winner);
            statement.executeUpdate();

            ret=0;
        }
        catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
            ret=1;
        }
        finally
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
            return  ret;
        }
    }

    public int addFriend(int playerID1,int playerID2)
    {
        int ret;
        Connection connection = null;
        PreparedStatement statement = null;
        String insert= "insert into gamemanager.friends values (?,?)";
        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("gamemaster",infoDB,connection);
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(insert);
            statement.setInt(1,playerID1);
            statement.setInt(2,playerID2);
            statement.executeUpdate();

            statement= connection.prepareStatement(insert);
            statement.setInt(1,playerID2);
            statement.setInt(2,playerID1);
            statement.executeUpdate();

            connection.commit();

            statement.close();
            connection.close();

            ret = 0;

        }
        catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
            ret=1;
        }
        finally
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
        return ret;
    }

    public int addAchievement(int playerID,int achievementID)
{
    int ret=0;
    Connection connection = null;
    PreparedStatement statement = null;
    String insert= "insert into gamemanager.player_achievement values (?,?,current_timestamp())";
    try
    {
        Class.forName(infoDB.getDriver()).newInstance();
        connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

        Role.setRole("gamemaster",infoDB,connection);

        statement = connection.prepareStatement(insert);
        statement.setInt(1,playerID);
        statement.setInt(2,achievementID);
        statement.executeUpdate();

        statement.close();
        connection.close();

        ret=0;

    }
    catch (InstantiationException | IllegalAccessException
            | ClassNotFoundException | SQLException e)
    {
        e.printStackTrace();
        ret=1;
    }
    finally
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
        return ret;
    }
}
public int addClan(int leaderID,String leaderNick,String clanName)
{
    int ret=0;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet rs = null;
    String insert= "insert into gamemanager.clans(clanname) values(?)";
    String update="update gamemanager.players set isLeader=? where playerID=?";
    String grant="grant 'clanleader' to ?@'localhost'";
    String select="select clanID from gamemanager.clans where clanname like ?";
    String update2="update gamemanager.players set clanID= ? where playerID= ?";
    try
    {
        Class.forName(infoDB.getDriver()).newInstance();
        connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

        Role.setRole("gamemaster",infoDB,connection);

        connection.setAutoCommit(false);

        statement = connection.prepareStatement(insert);
        statement.setString(1,clanName);
        statement.executeUpdate();

        statement=connection.prepareStatement(grant);
        statement.setString(1,leaderNick);
        statement.executeUpdate();


        statement=connection.prepareStatement(update);
        statement.setBoolean(1,true);
        statement.setInt(2,leaderID);
        statement.executeUpdate();
        connection.commit();

        connection.setAutoCommit(true);
        statement=connection.prepareStatement(select);
        statement.setString(1,clanName);
        rs=statement.executeQuery();

        int i=0;
        if(rs.next())
        {
            i=rs.getInt(1);
        }

        statement=connection.prepareStatement(update2);
        statement.setInt(1,i);
        statement.setInt(2,leaderID);
        statement.executeUpdate();


        ret=0;

    }
    catch (InstantiationException | IllegalAccessException
            | ClassNotFoundException | SQLException e)
    {
        e.printStackTrace();
        ret=1;
    }
    finally
    {
        if (rs != null)
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
        return ret;
    }

}
}
