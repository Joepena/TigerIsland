import javafx.util.Pair;

import static javax.swing.text.html.HTML.Tag.HEAD;

/**
 * Created by WIIZZARD on 3/19/2017.
 */
public class HexValidation {



    public static boolean adjacencyValidation(Pair<Integer,Integer> coordinatePair, Board gameBoard) {
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


    public static boolean terrainValidation(Hex hex, Pair<Integer,Integer> coordinatePair, Board gameBoard){
        Hex presentHex = gameBoard.getHex(coordinatePair);

        if(hex.getTerrain() == Terrain.terrainType.Volcano){
            if(presentHex.getTerrain() == Terrain.terrainType.Volcano)
                return true;
            else
                return false;
        }
        else{
            if(presentHex.getTerrain() == Terrain.terrainType.Volcano)
                return false;
            else
                return true;
        }
    }


    public static boolean isLocationNull(Pair<Integer,Integer> coordinatePair, Board gameBoard){
        Integer locationX = coordinatePair.getKey();
        Integer locationY = coordinatePair.getValue();

        return(gameBoard.gameBoard[locationX][locationY] == null);
    }



}
