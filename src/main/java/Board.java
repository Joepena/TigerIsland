import javafx.util.Pair;

/**
 * Created by Joe on 3/15/17.
 */
public class Board {

    protected Hex[][] gameBoard;
    protected boolean[][] gameBoardAvailability;

    public Board() {
    gameBoard = new Hex[376][376];
    gameBoardAvailability = new boolean[376][376];
    }

    public boolean isOriginEmpty() {
      Integer x = Orientation.ORIGIN.getKey();
      Integer y = Orientation.ORIGIN.getValue();
      return (gameBoard[x][y] == null) ? true : false;
    }

    void setHex (Hex hex, Pair<Integer,Integer> coordinatePair) {
      Integer originX = Orientation.ORIGIN.getKey();
      Integer originY = Orientation.ORIGIN.getValue();
      Integer x = coordinatePair.getKey() + originX;
      Integer y = coordinatePair.getValue() + originY;


      if(isOriginEmpty()) { //First tile placement
          gameBoard[originX][originY] = hex;
          gameBoardAvailability[originX][originY] = true;
          hex.setLocation(new Pair<Integer,Integer>(originX, originY));
          return;
      }
      gameBoard[x][y] = hex;
      gameBoardAvailability[x][y] = true;
      hex.setLocation(new Pair<Integer,Integer> (x, y));

    }

// commented out place tile for relocation to new gamplay class
//    void placeTile(Tile tile, Pair<Integer, Integer> coordinatePair) {
//
//        int leftXMath = tile.getLeftHexOrientation().getKey() + coordinatePair.getKey();
//        int leftYMath = tile.getLeftHexOrientation().getValue() + coordinatePair.getValue();
//        int rightCoordinateMath = tile.getLeftHexOrientation().getKey() + coordinatePair.getKey()
//        Pair<Integer, Integer> leftHexPair = new Pair<>();
//        Pair<Integer, Integer> rightHexPair;
//
//        setHex(tile.getVolcano(), coordinatePair);
//        setHex(tile.getLeft(), );
//    }

    public boolean[][] getGameBoardAvailability() {
        return gameBoardAvailability;
    }

    public boolean getGameBoardAvailabilityAtCoordinatePair(Pair<Integer,Integer> coordinatePair) {

        return gameBoardAvailability[coordinatePair.getKey()][coordinatePair.getValue()];
    }

}
