import java.util.Scanner;

/**
 * Created by TomasK on 4/6/2017.
 */
public class MoveMessage extends Message {
    private int gid;
    private int moveNumber;
    private String pid;
    private MoveType moveType;

    public static enum MoveType{
        Found, Expand, Totoro, Tiger, Forfeit
    }


    public int getGid() {
        return gid;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public String getPid() {
        return pid;
    }

    MoveMessage(String message, MoveType moveType){
        super(Message.MessageType.Move);
        this.moveType = moveType;
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        if(!scanner.hasNext())
            return;
        scanner.next();
        this.gid = scanner.nextInt();
        scanner.next();
        this.moveNumber = scanner.nextInt();
        scanner.next();
        this.pid = scanner.next();


        scanner.close();

    }


    public boolean equals(BeginRoundMessage message){
        return(this.rounds == message.getRounds() &&
                this.rid == message.getRid());
    }
}
