import java.util.Scanner;

/**
 * Created by TomasK on 4/6/2017.
 */
public class MoveMessage extends Message {
    public static enum MoveType{
        Found, Expand, Totoro, Tiger, Forfeit
    }


    public String getGid() {
        return gid;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public String getPid() {
        return pid;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {this.moveType = moveType;}

    MoveMessage(String message){
        super();
        messageType = MessageType.Move;
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        if(!scanner.hasNext())
            return;
        scanner.next();
        this.gid = scanner.next();
        scanner.next();
        this.moveNumber = scanner.nextInt();
        scanner.next();
        this.pid = scanner.next();


        scanner.close();

    }


    public boolean equals(MoveMessage message){
        return(this.gid.equals(message.getGid()) &&
                this.moveNumber == message.getMoveNumber() &&
                this.pid.equals(message.getPid()) &&
                this.moveType == message.getMoveType());
    }
}
