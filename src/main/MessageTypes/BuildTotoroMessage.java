import java.util.Scanner;

/**
 * Created by T K Vicious on 4/6/2017.
 */
public class BuildTotoroMessage extends MoveMessage{

    private Tuple tileLocation;
    private Tuple buildLocation;
    private Tile tile;

    public Tile getTile() {
        return tile;
    }

    public Tuple getTileLocation() {
        return tileLocation;
    }

    public Tuple getBuildLocation() {
        return buildLocation;
    }

    BuildTotoroMessage(String message){
        super(message, MoveType.Totoro);
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        if(!scanner.hasNext())
            return;
        for(int i = 0; i < 6 ; i++)
            scanner.next();
        scanner.next();
        String tileString = scanner.next();
        scanner.next();
        this.tileLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        this.tile = makeTileFromString(tileString, getMoveNumber(), this.numberToOrientation(scanner.nextInt()));
        scanner.next();
        scanner.next();
        scanner.next();
        scanner.next();
        this.buildLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());


    }

    public boolean equals(BuildTotoroMessage message){
        return super.equals((MoveMessage)message) &&
                (this.tileLocation.equals(message.getTileLocation()) &&
                        this.buildLocation.equals(message.getBuildLocation()) &&
                        this.tile.equals(message.getTile())
                );
    }

    public String toString(){
        return("tileLocation:  " + tileLocation.toString() + "\nbuildLocation:  " + buildLocation.toString() + "\ntile:  " + tile.toString());
    }



}
