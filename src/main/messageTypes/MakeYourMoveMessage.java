import java.util.Scanner;

/**
 * Created by T K Vicious on 4/6/2017.
 */
public class MakeYourMoveMessage extends Message{
    private String message;
    public String getGid() {
        return gid;
    }

    public double getMoveTime() {
        return moveTime;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public Tile getTile() {
        return tile;
    }

    MakeYourMoveMessage(String message){
        super();
        this.message = message;
        messageType = MessageType.MakeYourMove;
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        for(int i = 0; i < 5; i++)
            scanner.next();
        gid = scanner.next();
        scanner.next();
        moveTime = scanner.nextDouble();
        scanner.next();
        scanner.next();
        moveNumber = scanner.nextInt();
        scanner.next();
        tile = makeTileFromString(scanner.next(), this.moveNumber, Orientation.Orientations.downLeft);
        scanner.close();
    }

    public boolean equals(MakeYourMoveMessage message){
        return(this.gid.equals(message.getGid()) &&
                this.tile.equals(message.getTile()) &&
                this.moveNumber == message.getMoveNumber() &&
                this.moveTime == message.getMoveTime());
    }
    public String toString(){
        return message;
    }
}
