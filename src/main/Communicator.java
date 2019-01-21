package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;

public class Communicator
{
    int port;
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public Communicator(int port)
    {
        this.port=port;
        try
        {
            socket = new Socket("localhost", 4444);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void sendID()
    {
        String s;
        for(int i=1; i<1000; i++)
        {
            try{
                s=in.readLine();
                System.out.print(s);
                if(s.equals("ID"))
                {
                    out.println(i);
                }
                if(s.equals("IDACCEPTED"))
                {
                    break;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //
    public String sendLoginRequest(String username, String password)
    {
        out.println("login");
        out.println(username);
        out.println(password);
        String s=null;
        try
        {
            s= in.readLine();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }
    public String sendRegisterRequest(String username,String nickname, String password)
    {
        out.println("register");
        out.println(username);
        out.println(nickname);
        out.println(password);
        String s=null;
        try
        {
            s= in.readLine();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }
    public String sendAchievementInsert(int playerID,int achievementID)
    {
        out.println("achievement");
        out.println(playerID);
        out.println(achievementID);
        String s=null;
        try
        {
            s= in.readLine();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }
    public String sendFriendInsert(int player1,int player2)
    {
        out.println("friend");
        out.println(player1);
        out.println(player2);
        String s=null;
        try
        {
            s= in.readLine();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }
    public String sendMatchInsert(int player1,int player2,String winner)
    {
        out.println("match");
        out.println(player1);
        out.println(player2);
        out.println(winner);
        String s=null;
        try
        {
            s= in.readLine();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }


    public String sendClanInsert(int leaderID,String leaderUsername,String name)
    {
        out.println("clan");
        out.println(leaderID);
        out.println(leaderUsername);
        out.println(name);

        String s=null;
        try
        {
            s= in.readLine();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }
    public void exit()
    {
        out.println("exit");
    }

}
