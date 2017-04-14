import java.util.Scanner;

/**
 * Created by TomasK on 4/6/2017.
 */
public class MatchBeginningMessage extends Message{
    public String getPid() {
        return pid;
    }

    MatchBeginningMessage(String message){
        super();
        messageType = MessageType.MatchBeginning;
        Scanner scanner = new Scanner(message).useDelimiter(" ");

        for(int i = 0; i < 8; i++)
            scanner.next();
        pid = scanner.next();
        scanner.close();
    }

    public boolean equals(MatchBeginningMessage message){
        return this.pid.equals(message.getPid());
    }

}
