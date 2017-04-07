import java.util.Scanner;

/**
 * Created by T K Vicious on 4/6/2017.
 */
public class MakeYourMoveMessage extends Message{
    private int gid;
    private double moveTime;
    private int moveNumber;
    private Tile tile;

    public int getGid() {
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
        super(MessageType.MakeYourMove);
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        for(int i = 0; i < 5; i++)
            scanner.next();
        this.gid = scanner.nextInt();
        scanner.next();
        this.moveTime = scanner.nextDouble();
        scanner.next();
        scanner.next();
        this.moveNumber = scanner.nextInt();
        scanner.next();
        this.tile = makeTileFromString(scanner.next(), this.moveNumber, Orientation.Orientations.downLeft);
        scanner.close();
    }

    public boolean equals(MakeYourMoveMessage message){
        return(this.gid == message.getGid() &&
                this.tile.equals(message.getTile()) &&
                this.moveNumber == message.getMoveNumber() &&
                this.moveTime == message.getMoveTime());
    }
}
