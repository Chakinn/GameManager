package data;

import java.sql.*;

public class LoginDAO
{
    InfoDB infoDB;

    public LoginDAO(InfoDB info)
    {
        infoDB=info;
    }

    /*
        loguje uzytkownika do systemu
        return 0 gdy logowanie pomyślne, 1 jeżeli nie
     */
    public Integer login(String username, String password)
    {
        Integer ret;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String search= "select username,pass from gamemanager.players where username like ? and pass like ?";
        try
        {

            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("gamemaster",infoDB,connection);

            statement=connection.prepareStatement(search);
            statement.setString(1,username);
            statement.setString(2,password);

            rs = statement.executeQuery();
            boolean found = false;
            if(rs.next())
            {
                found=true;
            }
            statement.close();
            connection.close();
            ret = found ? 0 : 1;


        } catch (InstantiationException | IllegalAccessException
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
    public Integer register(String username,String nickname,String password)
    {
        Integer ret;
        Connection connection = null;
        PreparedStatement statement = null;
        String createUser="create user ?@'localhost' identified by ?";
        String grantPerm="grant 'player' to ?@'localhost'";
        String insertPlayer="insert into gamemanager.players(username,pass,nickname) values (?,?,?)";
        try
        {

            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());
            Role.setRole("gamemaster",infoDB,connection);
            connection.setAutoCommit(false);

            statement=connection.prepareStatement(insertPlayer);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.setString(3,nickname);
            statement.executeUpdate();

            statement = connection.prepareStatement(createUser);
            statement.setString(1,username);
            statement.setString(2,password);
            statement.executeUpdate();

            statement=connection.prepareStatement(grantPerm);
            statement.setString(1,username);
            statement.executeUpdate();

            connection.commit();

          ret=0;

        } catch ( InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e)
        {
            //e.printStackTrace();
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
    public String getNickname(String username)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String s=null;
        String select="select nickname from gamemanager.players where username like ?";
        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("gamemaster",infoDB,connection);

            statement=connection.prepareStatement(select);
            statement.setString(1,username);

            rs=statement.executeQuery();

            if(rs.next())
            {
                s=rs.getString(1);
            }


        } catch ( InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e)
        {
            //e.printStackTrace();
            return null;
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
            return s;
        }
    }
    public int getPlayerID(String username)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Integer i=0;
        String select="select playerID from gamemanager.players where username like ?";
        try
        {
            Class.forName(infoDB.getDriver()).newInstance();
            connection = DriverManager.getConnection(infoDB.getUrl(), infoDB.getUser(), infoDB.getPassword());

            Role.setRole("gamemaster",infoDB,connection);

            statement=connection.prepareStatement(select);
            statement.setString(1,username);

            rs=statement.executeQuery();

            if(rs.next())
            {
                i=rs.getInt(1);
            }


        } catch ( InstantiationException | IllegalAccessException
                | ClassNotFoundException | SQLException e)
        {
            //e.printStackTrace();
            return 0;
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
            return i;
        }
    }



}
