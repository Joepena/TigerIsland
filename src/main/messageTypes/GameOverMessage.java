import java.util.Scanner;

/**
 * Created by T K Vicious on 4/6/2017.
 */
public class GameOverMessage extends Message{
    private String message;
    public String getGid() {
        return gid;
    }

    GameOverMessage(String message){
        super();
        this.message = message;
        messageType = MessageType.GameOver;
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        scanner.next();
        gid = scanner.next();
        scanner.close();
    }

    public boolean equals(GameOverMessage message){
        return(this.gid.equals(message.getGid()));
    }
    public String toString(){
        return message;
    }
}
