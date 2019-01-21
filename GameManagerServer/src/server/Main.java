package server;

public class Main
{
    public static void main(String[] args)
    {
        Server server=null;
        try
        {
            server = new Server(4444);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        server.run();
    }
}
