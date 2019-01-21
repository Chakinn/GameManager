package data;

public class InfoDB
{
    private  String url;
    private  String user;
    private  String password;
    private  String driver;

    public InfoDB(String url, String user, String pass, String driver)
    {
        this.url=url;
        this.user=user;
        this.password=pass;
        this.driver=driver;
    }

    public String getUrl()
    {
        return url;
    }

    public String getUser()
    {
        return user;
    }

    public String getPassword()
    {
        return password;
    }

    public String getDriver()
    {
        return driver;
    }
}
