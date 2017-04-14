import java.util.Objects;
import java.util.Scanner;

/**
 * Created by TomasK on 4/6/2017.
 */
public class WaitToBeginMessage extends Message{
    private String pid;

    public String getPid() {
        return pid;
    }

    public WaitToBeginMessage(String message){
        super(Message.MessageType.WaitToBegin);

        Scanner scanner = new Scanner(message).useDelimiter(" ");
        for(int i = 0 ; i < 6; i++){
            scanner.next();
        }
        this.pid = scanner.next();
        scanner.close();
    }

    public boolean equals(WaitToBeginMessage message){
        return this.pid.equals(message.getPid());
    }
}
