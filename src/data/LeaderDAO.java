package data;

import java.sql.*;

public class LeaderDAO
{
    InfoDB infoDB;

    public LeaderDAO(InfoDB info)
    {
        infoDB = info;
    }

    public boolean checkLeader(String nick)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String select = "select isLeader from gamemanager.players where nickname like ?";

        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("player", infoDB, connection);

            statement = connection.prepareStatement(select);
            statement.setString(1, nick);

            rs = statement.executeQuery();
            if (rs.next())
            {
                return rs.getBoolean(1);
            }
            statement.close();
            connection.close();
            return false;

        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
            return false;
        } finally
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
        }
    }

    public void addClanMember(String playerNick, String leaderNick)
    {
        if (!checkLeader(leaderNick))
        {
            return;
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String check= "select clanID from gamemanager.players where nickname like ?";
        String update = "update gamemanager.players set clanID=(select clanID from gamemanager.players where nickname like ?) where nickname like ?";

        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("clanleader", infoDB, connection);

            statement= connection.prepareStatement(check);
            statement.setString(1,playerNick);
            rs=statement.executeQuery();
            int playerClanID=0;
            if(rs.next())
            {
                playerClanID=rs.getInt(1);
            }


            if(playerClanID==0)
            {
                statement = connection.prepareStatement(update);
                statement.setString(1, leaderNick);
                statement.setString(2, playerNick);
                statement.executeUpdate();
            }


        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        } finally
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
        }
    }

    void removeClanMember(String playerNick, String leaderNick)
    {
        if (!checkLeader(leaderNick))
        {
            return;
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String check= "select clanID from gamemanager.players where nickname like ?";
        String update = "update gamemanager.players set clanID=null where nickname like ?";

        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("clanleader", infoDB, connection);

            statement= connection.prepareStatement(check);
            statement.setString(1,playerNick);
            rs=statement.executeQuery();
            Integer playerClanID=null;
            if(rs.next())
            {
                playerClanID=rs.getInt(1);
            }

            statement= connection.prepareStatement(check);
            statement.setString(1,leaderNick);
            rs=statement.executeQuery();
            Integer leaderClanID=null;
            if(rs.next())
            {
                leaderClanID=rs.getInt(1);
            }

            if(leaderClanID!=null && playerClanID!=null && playerClanID.equals(leaderClanID))
            {
                statement = connection.prepareStatement(update);
                statement.setString(1, playerNick);
                statement.executeUpdate();
            }


        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
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

    void passLeadership(String playerNick, String leaderNick)
    {
        if (!checkLeader(leaderNick))
        {
            return;
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String check = "select clanID from gamemanager.players where nickname like ?";
        String update = "update gamemanager.players set isLeader=? where nickname like ?";

        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("clanleader", infoDB, connection);

            statement = connection.prepareStatement(check);
            statement.setString(1, playerNick);
            rs = statement.executeQuery();
            Integer playerClanID = null;
            if (rs.next())
            {
                playerClanID = rs.getInt(1);
            }

            statement = connection.prepareStatement(check);
            statement.setString(1, leaderNick);
            rs = statement.executeQuery();
            Integer leaderClanID = null;
            if (rs.next())
            {
                leaderClanID = rs.getInt(1);
            }

            if (leaderClanID != null && playerClanID != null && playerClanID.equals(leaderClanID))
            {
                connection.setAutoCommit(false);
                statement = connection.prepareStatement(update);
                statement.setBoolean(1,true);
                statement.setString(2, playerNick);
                statement.executeUpdate();

                statement=connection.prepareStatement(update);
                statement.setBoolean(1,false);
                statement.setString(2,leaderNick);
                statement.executeUpdate();

                connection.commit();
            }


        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
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
