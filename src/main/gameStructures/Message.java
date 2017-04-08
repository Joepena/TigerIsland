import java.util.Scanner;

/**
 * Created by TomasK on 4/6/2017.
 */
public class Message {
    private MessageType messageType;

    public static enum MessageType{
        Welcome, Enter, WaitToBegin, NewChallenge, BeginRound, EndRound, EndOfChallenges, WaitForNext, MatchBeginning,
        GameOver, MakeYourMove, Move, Goodbye
    }

    public boolean sameMessageType(MessageType type){
        return this.messageType == type;
    }

    Message(MessageType messageType){
        this.messageType = messageType;
    }

    public MessageType getMessageType(){
        return this.messageType;
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
}
