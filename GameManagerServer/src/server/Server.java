package server;

import data.InfoDB;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server
{
    int port;
    ServerSocket serverSocket;
    InfoDB infoDB = new InfoDB("jdbc:mysql://localhost:3306?useLegacyDatetimeCode=false&serverTimezone=UTC","HASH","THAT","com.mysql.cj.jdbc.Driver");

    HashSet<Integer> ClientID = new HashSet<Integer>();
    Server( int port ) throws Exception
    {
       // this.port=port;
        System.out.println("Uruchamianie serwera...");
        try
        {
            serverSocket = new ServerSocket(port);
        } catch (Exception e)
        {
            throw new Exception("Nie można uzyskać dostępu to portu " + port);
        }
    }

    void run()
    {
    System.out.println("The server is running.");
    try
    {
        while (true)
        {
            try{
                new Client(serverSocket.accept(),ClientID,infoDB).start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    } catch (Exception e)
    {
        e.printStackTrace();
    }
}

}
