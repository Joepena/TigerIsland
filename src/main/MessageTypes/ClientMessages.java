/**
 * Created by Megans on 4/7/2017.
 */
public class ClientMessages {
    String gamePassword;
    String username;
    String password;

    public ClientMessages(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String enterThunderdome(String gamePassword)
    {
        return ("ENTER THUNDERDOME " + gamePassword + "\r\n");
    }

    public String usernamePassword()
    {
        return ("I AM " + this.username + " " + this.password + "\r\n");
    }


}
