import java.util.Scanner;

/**
 * Created by T K Vicious on 4/6/2017.
 */
public class BuildTigerMessage extends MoveMessage {
    public Tile getTile() {
        return tile;
    }

    public Tuple getTileLocation() {
        return tileLocation;
    }

    public Tuple getBuildLocation() {
        return buildLocation;
    }

    BuildTigerMessage(String message){
        super(message);
        messageType = MessageType.Move;
        setMoveType(MoveType.Tiger);
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        if(!scanner.hasNext())
            return;
        for(int i = 0; i < 6 ; i++)
            scanner.next();
        scanner.next();
        String tileString = scanner.next();
        scanner.next();
        tileLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        tile = makeTileFromString(tileString, getMoveNumber(), this.numberToOrientation(scanner.nextInt()));
        scanner.next();
        scanner.next();
        scanner.next();
        scanner.next();
        buildLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());


    }

    public boolean equals(BuildTigerMessage message){
        return super.equals((MoveMessage)message) &&
                (this.tileLocation.equals(message.getTileLocation()) &&
                        this.buildLocation.equals(message.getBuildLocation()) &&
                        this.tile.equals(message.getTile())
                );
    }

//    public String toString(){
//        return("tileLocation:  " + tileLocation.toString() + "\nbuildLocation:  " + buildLocation.toString() + "\ntile:  " + tile.toString());
//    }
}
