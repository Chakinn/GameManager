package data;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class ServerFacade
{
    InfoDB infoDB;
    public ServerFacade(InfoDB info)
    {
        infoDB=info;
    }
    public ServerFacade(String url, String user, String pass, String driver)
    {
        infoDB=new InfoDB(url,user,pass,driver);
    }
    public int registerPlayer(String username,String nickname,String password)
    {
        LoginDAO registerDAO = new LoginDAO(infoDB);
        return registerDAO.register(username, nickname, password);
    }
    public int login(String username, String password)
    {
        LoginDAO loginDAO = new LoginDAO(infoDB);
        return loginDAO.login(username,password);
    }
    public int addMatchRecord(int playerID1,int playerID2,String winner)
    {
        InsertDAO insertDAO = new InsertDAO(infoDB);
        return insertDAO.addMatchRecord(playerID1,playerID2,winner);
    }
    public int addFriend(int playerID1,int playerID2)
    {
        InsertDAO insertDAO = new InsertDAO(infoDB);
        return insertDAO.addFriend(playerID1,playerID2);
    }
    public int addAchievement(int playerID, int achievementID)
    {
        InsertDAO insertDAO = new InsertDAO(infoDB);
        return insertDAO.addAchievement(playerID, achievementID);
    }
    public int addClan(int leaderID,String leaderNick, String name)
    {
        InsertDAO insertDAO = new InsertDAO(infoDB);
        return insertDAO.addClan(leaderID,leaderNick,name);
    }
    public String getNickname(String username)
    {
        LoginDAO loginDAO = new LoginDAO(infoDB);
        return loginDAO.getNickname(username);
    }
    public int getPlayerID(String username)
    {
        LoginDAO loginDAO = new LoginDAO(infoDB);
        return loginDAO.getPlayerID(username);
    }

    public void backup()
    {
        Backup.backup();
    }
    public void loadDB(File file)
    {
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
            Backup.importSQL(infoDB, fileInputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
