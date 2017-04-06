import java.util.Scanner;

/**
 * Created by TomasK on 4/6/2017.
 */
public class FoundSettlementMessage extends MoveMessage {
    private Tuple tileLocation;
    private Tuple buildLocation;
    private Tile tile;

    public Tuple getTileLocation() {
        return tileLocation;
    }

    public Tuple getBuildLocation() {
        return buildLocation;
    }

    MoveMessage(String message){
        super(message, MoveMessage.MoveType.Found);
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
        this.buildLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        scanner.close();

    }

    public boolean equals(FoundSettlementMessage message){
        return(this.tileLocation == message.getTileLocation() &&
                this.buildLocation == message.getBuildLocation());
    }

}
