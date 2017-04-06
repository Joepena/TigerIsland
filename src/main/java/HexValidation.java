/**
 * Created by WIIZZARD on 3/19/2017.
 */
public class HexValidation {



    public static boolean existsAdjacentHex(Tuple coordinates, Board gameBoard) {
        Tuple upLeft = Orientation.upLeftOf(coordinates);
        Tuple upRight = Orientation.upRightOf(coordinates);
        Tuple downLeft = Orientation.downLeftOf(coordinates);
        Tuple downRight = Orientation.downRightOf(coordinates);
        Tuple right = Orientation.rightOf(coordinates);
        Tuple left = Orientation.leftOf(coordinates);

        return(!isLocationNull(upLeft, gameBoard) || !isLocationNull(upRight, gameBoard) || !isLocationNull(downLeft, gameBoard) ||
                   !isLocationNull(downRight, gameBoard) || !isLocationNull(left, gameBoard) || !isLocationNull(right, gameBoard)
                || !isLocationNull(coordinates, gameBoard));
    }


    public static boolean isValidVolcanoPlacement(Tuple coordinates, Board gameBoard){
        Hex presentHex = gameBoard.getHex(coordinates);
            if(presentHex.getTerrain() == Terrain.terrainType.Volcano)
                return true;
            else
                return false;
    }


    public static boolean isLocationNull(Tuple coordinatePair, Board gameBoard){
        if (gameBoard.getHex(coordinatePair) != null) {
            Hex temp = gameBoard.getHex(coordinatePair);
        }
      return(gameBoard.getHex(coordinatePair) == null);
    }


    public static boolean isValidHexEruption(Tuple testingLocation, Board gameBoard) {
        Hex targetHex = gameBoard.getHex(testingLocation);
        if(GameAPIUtil.hasTigerTotoro(targetHex)){
            return false;
        }
        else if(GameAPIUtil.isEmpty(targetHex)){
            return true;
        }
        else{
            return existsAdjacentTeamPiece(testingLocation, gameBoard);
        }
    }

    public static boolean existsAdjacentTeamPiece(Tuple testingLocation, Board gameBoard){
        Hex targetHex = gameBoard.getHex(testingLocation);

        Hex hexLeftOf =  gameBoard.getHex(Orientation.leftOf(testingLocation));
        Hex hexRightOf =  gameBoard.getHex(Orientation.rightOf(testingLocation));
        Hex hexUpLeftOf =  gameBoard.getHex(Orientation.upLeftOf(testingLocation));
        Hex hexUpRightOf =  gameBoard.getHex(Orientation.upRightOf(testingLocation));
        Hex hexDownLeftOf =  gameBoard.getHex(Orientation.downLeftOf(testingLocation));
        Hex hexDownRightOf =  gameBoard.getHex(Orientation.downRightOf(testingLocation));

        boolean valid = GameAPIUtil.isSameTeam(hexLeftOf, targetHex);
        valid = valid || GameAPIUtil.isSameTeam(hexRightOf, targetHex);
        valid = valid || GameAPIUtil.isSameTeam(hexUpLeftOf, targetHex);
        valid = valid || GameAPIUtil.isSameTeam(hexUpRightOf, targetHex);
        valid = valid || GameAPIUtil.isSameTeam(hexDownLeftOf, targetHex);
        valid = valid || GameAPIUtil.isSameTeam(hexDownRightOf, targetHex);

        return valid;
    }
}
