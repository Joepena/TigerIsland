import javafx.util.Pair;

/**
 * Created by WIIZZARD on 3/19/2017.
 */
public class HexValidation {



    public static boolean existsAdjacentHex(Pair<Integer,Integer> coordinatePair, Board gameBoard) {
        Pair<Integer,Integer> upLeft = Orientation.upLeftOf(coordinatePair);
        Pair<Integer,Integer> upRight = Orientation.upRightOf(coordinatePair);
        Pair<Integer,Integer> downLeft = Orientation.downLeftOf(coordinatePair);
        Pair<Integer,Integer> downRight = Orientation.downRightOf(coordinatePair);
        Pair<Integer,Integer> left = Orientation.rightOf(coordinatePair);
        Pair<Integer,Integer> right = Orientation.leftOf(coordinatePair);

        return(!isLocationNull(upLeft, gameBoard) || !isLocationNull(upRight, gameBoard) || !isLocationNull(downLeft, gameBoard) ||
                !isLocationNull(downRight, gameBoard) || !isLocationNull(left, gameBoard) || !isLocationNull(right, gameBoard)
                || !isLocationNull(coordinatePair, gameBoard));
    }


    public static boolean isValidVolcanoPlacement(Hex hex, Pair<Integer,Integer> coordinatePair, Board gameBoard){
        Hex presentHex = gameBoard.getHex(coordinatePair);

        if(hex.getTerrain() == Terrain.terrainType.Volcano){
            if(presentHex.getTerrain() == Terrain.terrainType.Volcano)
                return true;
            else
                return false;
        }
        else return true;
    }


    public static boolean isLocationNull(Pair<Integer,Integer> coordinatePair, Board gameBoard){
        Integer locationX = coordinatePair.getKey();
        Integer locationY = coordinatePair.getValue();

        return(gameBoard.gameBoard[locationX][locationY] == null);
    }


    public static boolean isValidEruption(Pair<Integer, Integer> testingLocation, Board gameBoard) {
        Hex targetHex = gameBoard.getHex(testingLocation);
        if(targetHex.getOccupiedBy() == Hex.gamePieces.Totoro || targetHex.getOccupiedBy() == Hex.gamePieces.Tiger){
            return false;
        }
        else if(targetHex.getOccupiedBy() == Hex.gamePieces.empty){
            return true;
        }
        else{
            return existsAdjacentTeamPiece(testingLocation, gameBoard);
        }
    }

    public static boolean existsAdjacentTeamPiece(Pair<Integer, Integer> testingLocation, Board gameBoard){
        Hex targetHex = gameBoard.getHex(testingLocation);
        Hex.Team team = targetHex.getTeam();

        Hex hexLeftOf =  gameBoard.getHex(Orientation.leftOf(testingLocation));
        Hex hexRightOf =  gameBoard.getHex(Orientation.rightOf(testingLocation));
        Hex hexUpLeftOf =  gameBoard.getHex(Orientation.upLeftOf(testingLocation));
        Hex hexUpRightOf =  gameBoard.getHex(Orientation.upRightOf(testingLocation));
        Hex hexDownLeftOf =  gameBoard.getHex(Orientation.downLeftOf(testingLocation));
        Hex hexDownRightOf =  gameBoard.getHex(Orientation.downRightOf(testingLocation));

        boolean valid = (hexLeftOf.getOccupiedBy() != Hex.gamePieces.empty) && (hexLeftOf.getTeam() == team);
        valid = valid || hexRightOf.getOccupiedBy() != Hex.gamePieces.empty && hexRightOf.getTeam() == team;
        valid = valid || hexUpLeftOf.getOccupiedBy() != Hex.gamePieces.empty && hexUpLeftOf.getTeam() == team;
        valid = valid || hexUpRightOf.getOccupiedBy() != Hex.gamePieces.empty && hexUpRightOf.getTeam() == team;
        valid = valid || hexDownLeftOf.getOccupiedBy() != Hex.gamePieces.empty && hexDownLeftOf.getTeam() == team;
        valid = valid || hexDownRightOf.getOccupiedBy() != Hex.gamePieces.empty && hexDownRightOf.getTeam() == team;

        return valid;
    }
}
