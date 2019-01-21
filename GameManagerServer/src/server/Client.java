package server;

import data.InfoDB;
import data.ServerFacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashSet;

public class Client extends Thread
{
    private Integer ID;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int playerID;
    private HashSet<Integer> ClientIDs;
    private ServerFacade serverFacade;

    /**
     * Constructs a handler thread, squirreling away the socket.
     * All the interesting work is done in the run method.
     */
    public Client(Socket socket,HashSet<Integer> IDs,InfoDB info) {
        this.socket = socket;
        ClientIDs=IDs;
        serverFacade=new ServerFacade(info);
    }

    /**
     * Services this thread's client by repeatedly requesting a
     * screen name until a unique one has been submitted, then
     * acknowledges the name and registers the output stream for
     * the client in a global set, then repeatedly gets inputs and
     * broadcasts them.
     */
    public void run()
    {
        System.out.print("RUN");
            // Create character streams for the socket.
            try
            {

                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            }
            catch(IOException ioex)
            {
                ioex.printStackTrace();
            }
            allocateClientID();
            respond();

    }


    public void allocateClientID()
    {
        while (true) {
            out.println("ID");
            try
            {
                String s = in.readLine();
                ID = Integer.parseInt(s);
            }
            catch (IOException | NumberFormatException ex)
            {
                ex.printStackTrace();
            }

            synchronized (ClientIDs) {
                if (!ClientIDs.contains(ID)) {
                    ClientIDs.add(ID);
                    out.println("IDACCEPTED");
                    break;
                }
            }
        }
    }
    public void respond()
    {
        while(true){
            String s="";
            try
            {
                s = in.readLine();
            }
            catch (IOException ex)
            {

            }
            switch (s){
                case "login":
                {
                    String user=null,pass=null;
                    try
                    {
                        user=in.readLine();
                        pass=in.readLine();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    if(user!=null&&pass!=null)
                    {
                        System.out.print(user+' '+pass);
                        login(user,pass);
                    }
                    break;
                }
                case"register":
                {
                    String user=null,nick=null,pass=null;
                    try
                    {
                        user=in.readLine();
                        nick=in.readLine();
                        pass=in.readLine();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    if(user!=null&&pass!=null)
                    {
                        System.out.print(user+' '+nick+' '+pass);
                        register(user,nick,pass);
                    }
                    break;
                }
                case "match":
                {
                    String player1=null,player2=null,winner=null;
                    int id1=-1,id2=-1;
                    try
                    {
                        player1=in.readLine();
                        player2=in.readLine();
                        winner=in.readLine();
                        id1=Integer.parseInt(player1);
                        id2=Integer.parseInt(player2);
                    }
                    catch (IOException | NumberFormatException e)
                    {
                        e.printStackTrace();
                    }
                    if(player1!=null&&player2!=null&&winner!=null)
                    {
                        System.out.print(player1+' '+player2+' '+winner);
                        addMatchRecord(id1,id2,winner);
                    }
                    break;
                }
                case "friend":
                {
                    String player1=null,player2=null;
                    int id1=-1,id2=-1;
                    try
                    {
                        player1=in.readLine();
                        player2=in.readLine();
                        id1=Integer.parseInt(player1);
                        id2=Integer.parseInt(player2);
                    }
                    catch (IOException | NumberFormatException e)
                    {
                        e.printStackTrace();
                    }
                    if(player1!=null&&player2!=null)
                    {
                        System.out.print(player1+' '+player2);
                        addFriend(id1,id2);
                    }
                    break;
                }
                case "achievement":
                {
                    String playerID = null, achievementID = null;
                    int id1 = -1, id2 = -1;
                    try
                    {
                        playerID = in.readLine();
                        achievementID = in.readLine();
                        id1 = Integer.parseInt(playerID);
                        id2 = Integer.parseInt(achievementID);
                    } catch (IOException | NumberFormatException e)
                    {
                        e.printStackTrace();
                    }
                    if (playerID != null && achievementID != null)
                    {
                        System.out.print(playerID + ' ' + achievementID);
                        addAchievement(id1, id2);
                    }
                    break;
                }
                case "clan":
                {
                    String leaderID = null,leaderUsername=null, name = null;
                    int id1 = -1;
                    try
                    {
                        leaderID = in.readLine();
                        leaderUsername=in.readLine();
                        name = in.readLine();
                        id1 = Integer.parseInt(leaderID);
                    } catch (IOException | NumberFormatException e)
                    {
                        e.printStackTrace();
                    }
                    if (leaderID != null && name != null)
                    {
                        System.out.print(leaderID + ' ' + name);
                        addClan(id1,leaderUsername,name);
                    }
                    break;
                }
                case "exit":
                {
                    try{
                        socket.close();
                        return;
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
    public void login(String username, String password)
    {
       int i= serverFacade.login(username, password);
       System.out.print(i);
       if(i==0)
       {
           playerID=serverFacade.getPlayerID(username);
           out.println(serverFacade.getNickname(username));
           System.out.println("payerID:"+playerID);
       }
       else
       {
           out.println("_LOGIN_ERROR");
       }

    }
    public void register(String username, String nickname, String password)
    {
        int i= serverFacade.registerPlayer(username,nickname, password);
        System.out.print(i);
        if(i==0)
        {
            out.println("REGISTERED");
        }
        else
        {
            out.println("_REGISTER_ERROR");
        }

    }
    public void addMatchRecord(int playerID1,int playerID2,String winner)
    {
        int i= serverFacade.addMatchRecord(playerID1, playerID2, winner);
        System.out.print(i);
        if(i==0)
        {
            out.println("ADDED");
        }
        else
        {
            out.println("_ADD_ERROR");
        }
    }
    public void addFriend(int playerID1,int playerID2)
    {
        int i= serverFacade.addFriend(playerID1, playerID2);
        System.out.print(i);
        if(i==0)
        {
            out.println("ADDED");
        }
        else
        {
            out.println("_ADD_ERROR");
        }
    }
    public void addAchievement(int playerID, int achievementID)
    {
        int i= serverFacade.addAchievement(playerID, achievementID);
        System.out.print(i);
        if(i==0)
        {
            out.println("ADDED");
        }
        else
        {
            out.println("_ADD_ERROR");
        }
    }
    public void addClan(int leaderID,String leaderUsername,String name)
    {
        int i= serverFacade.addClan(leaderID,leaderUsername,name);
        System.out.print(i);
        if(i==0)
        {
            out.println("ADDED");
        }
        else
        {
            out.println("_ADD_ERROR");
        }
    }
}

