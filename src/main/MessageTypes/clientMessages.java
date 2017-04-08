/**
 * Created by Megans on 4/7/2017.
 */
public class clientMessages {
    String gamePassword;
    String username;
    String password;


    public void setGamePassword(String gamePassword) {
        this.gamePassword = gamePassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String enterThunderdome(String gamePassword)
    {
        return ("ENTER THUNDERDOME " + gamePassword);
    }

    public String usernamePassword(String username, String password)
    {
        return ("I AM " + username + " " + password);
    }


}
