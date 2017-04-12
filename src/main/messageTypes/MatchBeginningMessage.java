import java.util.Scanner;

/**
 * Created by TomasK on 4/6/2017.
 */
public class MatchBeginningMessage extends Message{
    private String pid;

    public String getPid() {
        return pid;
    }

    MatchBeginningMessage(String message){
        super(MessageType.MatchBeginning);
        Scanner scanner = new Scanner(message).useDelimiter(" ");

        for(int i = 0; i < 8; i++)
            scanner.next();
        this.pid = scanner.next();
        scanner.close();
    }

    public boolean equals(MatchBeginningMessage message){
        return this.pid.equals(message.getPid());
    }

}
