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


    public static boolean isValidVolcanoPlacement(Pair<Integer,Integer> coordinatePair, Board gameBoard){
        Hex presentHex = gameBoard.getHex(coordinatePair);
            if(presentHex.getTerrain() == Terrain.terrainType.Volcano)
                return true;
            else
                return false;
    }


    public static boolean isLocationNull(Pair<Integer,Integer> coordinatePair, Board gameBoard){
        Integer locationX = coordinatePair.getKey();
        Integer locationY = coordinatePair.getValue();

        return(gameBoard.gameBoard[locationX][locationY] == null);
    }


    public static boolean isValidHexEruption(Pair<Integer, Integer> testingLocation, Board gameBoard) {
        Hex targetHex = gameBoard.getHex(testingLocation);
        if(TempHexHelpers.hasTigerTotoro(targetHex)){
            return false;
        }
        else if(TempHexHelpers.isEmpty(targetHex)){
            return true;
        }
        else{
            return existsAdjacentTeamPiece(testingLocation, gameBoard);
        }
    }

    public static boolean existsAdjacentTeamPiece(Pair<Integer, Integer> testingLocation, Board gameBoard){
        Hex targetHex = gameBoard.getHex(testingLocation);

        Hex hexLeftOf =  gameBoard.getHex(Orientation.leftOf(testingLocation));
        Hex hexRightOf =  gameBoard.getHex(Orientation.rightOf(testingLocation));
        Hex hexUpLeftOf =  gameBoard.getHex(Orientation.upLeftOf(testingLocation));
        Hex hexUpRightOf =  gameBoard.getHex(Orientation.upRightOf(testingLocation));
        Hex hexDownLeftOf =  gameBoard.getHex(Orientation.downLeftOf(testingLocation));
        Hex hexDownRightOf =  gameBoard.getHex(Orientation.downRightOf(testingLocation));

        boolean valid = TempHexHelpers.isSameTeam(hexLeftOf, targetHex);
        valid = valid || TempHexHelpers.isSameTeam(hexRightOf, targetHex);
        valid = valid || TempHexHelpers.isSameTeam(hexUpLeftOf, targetHex);
        valid = valid || TempHexHelpers.isSameTeam(hexUpRightOf, targetHex);
        valid = valid || TempHexHelpers.isSameTeam(hexDownLeftOf, targetHex);
        valid = valid || TempHexHelpers.isSameTeam(hexDownRightOf, targetHex);

        return valid;
    }
}
