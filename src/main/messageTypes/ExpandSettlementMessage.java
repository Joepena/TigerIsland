import java.util.Scanner;

/**
 * Created by T K Vicious on 4/6/2017.
 */
public class ExpandSettlementMessage extends MoveMessage {

        private Tuple tileLocation;
        private Tuple expandLocation;
        private Tile tile;
        private Terrain.terrainType expandTerrain;

        public Tile getTile() {
            return tile;
        }

        public Tuple getTileLocation() {
            return tileLocation;
        }

        public Tuple getExpandLocation() {
            return expandLocation;
        }

        public Terrain.terrainType getExpandTerrain() {
            return expandTerrain;
        }

        ExpandSettlementMessage(String message){
                super(message, MoveType.Expand);
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
                this.expandLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                this.expandTerrain = stringToTerrain(scanner.next());
                scanner.close();

            }

            public boolean equals(ExpandSettlementMessage message){
                return super.equals((MoveMessage)message) &&
                        (this.tileLocation.equals(message.getTileLocation()) &&
                                this.expandLocation.equals(message.getExpandLocation()) &&
                                this.tile.equals(message.getTile()) &&
                                this.expandTerrain == message.getExpandTerrain()
                                );
            }

            public String toString(){
                return("tileLocation:  " + tileLocation.toString() + "\nbuildLocation:  " + expandLocation.toString() + "\ntile:  " + tile.toString());
            }


}
