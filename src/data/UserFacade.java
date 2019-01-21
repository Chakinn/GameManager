package data;

import java.util.List;

public class UserFacade
{
    InfoDB infoDB;
    public UserFacade(InfoDB info)
    {
        infoDB=info;
    }
    public UserFacade(String url, String user, String pass, String driver)
    {
        infoDB=new InfoDB(url,user,pass,driver);
    }

    public List<String[]> getAllAchievements(String search)
    {
        AchievementDAO achievementDAO = new AchievementDAO(infoDB);
        return achievementDAO.getAllAchievements(search);
    }
    public List<String[]> getAchievements(String nick,String search)
    {
        AchievementDAO achievementDAO = new AchievementDAO(infoDB);
        return achievementDAO.getAchievements(nick, search);
    }
    public List<String[]> getClans(String search)
    {
        ClanDAO clanDAO = new ClanDAO(infoDB);
        return clanDAO.getClans(search);
    }
    public List<String[]> getClanMembers(String nickname,String search)
    {
        ClanDAO clanDAO = new ClanDAO(infoDB);
        return clanDAO.getClanMembers(nickname, search);
    }
    public List<String[]> getFriends(String nick, String search)
    {
        FriendDAO friendDAO = new FriendDAO(infoDB);
        return friendDAO.getFriends(nick,search);
    }
    public List<String[]> getHistory(String nick,String search)
    {
        HistoryDAO historyDAO = new HistoryDAO(infoDB);
        return  historyDAO.getHistory(nick, search);
    }
    public List<String[]> getLeaderboard(String search)
    {
        LeaderboardDAO leaderboardDAO = new LeaderboardDAO(infoDB);
        return leaderboardDAO.getLeaderboard(search);
    }

    public void addClanMember(String playerNick, String leaderNick)
    {
        LeaderDAO leaderDAO = new LeaderDAO(infoDB);
        leaderDAO.addClanMember(playerNick, leaderNick);
    }
    public void removeClanMember(String playerNick, String leaderNick)
    {
        LeaderDAO leaderDAO = new LeaderDAO(infoDB);
        leaderDAO.removeClanMember(playerNick, leaderNick);
    }
    public void passLeadership(String playerNick, String leaderNick)
    {
        LeaderDAO leaderDAO = new LeaderDAO(infoDB);
        leaderDAO.passLeadership(playerNick, leaderNick);
    }

}
