import java.util.Scanner;

/**
 * Created by TomasK on 4/6/2017.
 */
public class Message {
    protected MessageType messageType;
    protected int rid;
    protected int rounds;
    protected Tuple buildLocation;
    protected Tuple tileLocation;
    protected Tile tile;
    protected Terrain.terrainType expandTerrain;
    protected String pid;
    protected String gid;
    protected double moveTime;
    protected int moveNumber;
    protected MoveMessage.MoveType moveType;
    protected String cid;


    public enum MessageType{
        Welcome, Enter, WaitToBegin, NewChallenge, BeginRound, EndRound, EndOfChallenges, WaitForNext, MatchBeginning,
        GameOver, MakeYourMove, Move, Goodbye, Invalid
    }

    Message(){
        messageType = MessageType.Invalid;
        rid = -1;
        rounds = -1;
        buildLocation = null;
        tileLocation = null;
        tile = null;
        expandTerrain = null;
        pid = "";
        gid = "";
        cid = "";
        moveTime = -1;
        moveNumber = -1;
        moveType = null;
    }



    protected Tile makeTileFromString(String tileString, int tileNo, Orientation.Orientations orientation){
        Scanner scanner = new Scanner(tileString).useDelimiter("\\+");
        String rightTerrain = scanner.next();
        String leftTerrain = scanner.next();

        return new Tile(tileNo, stringToTerrain(leftTerrain), stringToTerrain(rightTerrain), orientation);

    }

    protected Terrain.terrainType stringToTerrain (String terrain){
        switch(terrain){
            case "LAKE":
                return Terrain.terrainType.Lake;
            case "JUNGLE":
                return Terrain.terrainType.Jungle;
            case "GRASS":
                return Terrain.terrainType.Grassland;
            case "ROCK":
                return Terrain.terrainType.Rocky;
            default:
                return Terrain.terrainType.Grassland;
        }

    }

    protected Orientation.Orientations numberToOrientation(int number){
        switch(number){
            case 1:
                return Orientation.Orientations.upRight;
            case 2:
                return Orientation.Orientations.right;
            case 3:
                return Orientation.Orientations.downRight;
            case 4:
                return Orientation.Orientations.downLeft;
            case 5:
                return Orientation.Orientations.left;
            case 6:
                return Orientation.Orientations.upLeft;
            default:
                return Orientation.Orientations.upRight;
        }
    }

    public int getRid() {
        System.out.println("Invalid message type, field does not exist");
        return rid;
    }

    public int getRounds() {
        System.out.println("Invalid message type, field does not exist");
        return rounds;
    }

    public Tuple getBuildLocation() {
        System.out.println("Invalid message type, field does not exist");
        return buildLocation;
    }

    public Tuple getTileLocation() {
        System.out.println("Invalid message type, field does not exist");
        return tileLocation;
    }

    public Tile getTile() {
        System.out.println("Invalid message type, field does not exist");
        return tile;
    }

    public Terrain.terrainType getExpandTerrain() {
        System.out.println("Invalid message type, field does not exist");
        return expandTerrain;
    }

    public String getPid() {
        System.out.println("Invalid message type, field does not exist");
        return pid;
    }

    public String getGid() {
        System.out.println("Invalid message type, field does not exist");
        return gid;
    }

    public double getMoveTime() {
        System.out.println("Invalid message type, field does not exist");
        return moveTime;
    }

    public int getMoveNumber() {
        System.out.println("Invalid message type, field does not exist");
        return moveNumber;
    }

    public MoveMessage.MoveType getMoveType() {
        System.out.println("Invalid message type, field does not exist");
        return moveType;
    }

    public MessageType getMessageType(){
        return this.messageType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public void setBuildLocation(Tuple buildLocation) {
        this.buildLocation = buildLocation;
    }

    public void setTileLocation(Tuple tileLocation) {
        this.tileLocation = tileLocation;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setExpandTerrain(Terrain.terrainType expandTerrain) {
        this.expandTerrain = expandTerrain;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setMoveTime(double moveTime) {
        this.moveTime = moveTime;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public void setMoveType(MoveMessage.MoveType moveType) {
        this.moveType = moveType;
    }
}
