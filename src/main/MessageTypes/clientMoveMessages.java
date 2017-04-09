/**
 * Created by Megans on 4/7/2017.
 */
public class clientMoveMessages
{
    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public void setTileLocation(Tuple tileLocation) {
        this.tileLocation = tileLocation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setBuildLocation(Tuple buildLocation) {
        this.buildLocation = buildLocation;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setTerrain(Terrain.terrainType terrain) {
        this.terrain = terrain;
    }

    private String gid;
    private int moveNumber;
    private Tuple tileLocation;
    private int orientation;
    private Tuple buildLocation;
    private Tile tile;
    private Terrain.terrainType terrain;
    private clientMoveMessageType moveType;

    public clientMoveMessageType getMoveType() {
        return moveType;
    }

    public void setMoveType(clientMoveMessageType moveType) {
        this.moveType = moveType;
    }

    public static enum clientMoveMessageType{
        Found, Totoro, Expand, Unable, Tiger;
    }

    private String terrainToString(Terrain.terrainType terrain)
    {
        if(terrain == Terrain.terrainType.Grassland)
            return "GRASS";
        if(terrain == Terrain.terrainType.Jungle)
            return "JUNGLE";
        if(terrain == Terrain.terrainType.Lake)
            return "LAKE";
        if(terrain == Terrain.terrainType.Rocky)
            return "ROCK";
        return "";
    }

    private String tileToString(Tile tile)
    {
        String leftTerrain = terrainToString(tile.getLeft().getTerrain());
        String rightTerrain = terrainToString(tile.getRight().getTerrain());
        return (leftTerrain + "+" + rightTerrain);
    }

    public String toString(clientMoveMessageType messageType)
    {
        String message = ("GAME " + gid + " MOVE " + moveNumber
                + " PLACE " + tileToString(tile) + " AT " + tileLocation.getX() + " " + tileLocation.getY() + " " + tileLocation.getZ()
                + " " + orientation);
        switch(messageType){
            case Found:
                return message + (" FOUND SETTLEMENT AT "
                        + buildLocation.getX() + " " + buildLocation.getY() + " " + buildLocation.getZ());
            case Tiger:
                return message + (" BUILD TIGER PLAYGROUND AT "
                        + buildLocation.getX() + " " + buildLocation.getY() + " " + buildLocation.getZ());
            case Expand:
                return message + (" EXPAND SETTLEMENT AT "
                        + buildLocation.getX() + " " + buildLocation.getY() + " " + buildLocation.getZ() + " " + terrainToString(terrain));
            case Totoro:
                return message + (" BUILD TOTORO SANCTUARY AT "
                        + buildLocation.getX() + " " + buildLocation.getY() + " " + buildLocation.getZ());
            case Unable:
                return (message + " UNABLE TO BUILD");

        }
        return "";
    }

    protected int orientationToNumber(Orientation.Orientations orientation){
        switch(orientation){
            case upRight:
                return 1;
            case right:
                return 2;
            case downRight:
                return 3;
            case downLeft:
                return 4;
            case left:
                return 5;
            case upLeft:
                return 6;
            default:
                return 0;
        }
    }


}
