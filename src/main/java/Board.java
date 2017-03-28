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
      Integer x = Orientation.getOriginValue().getKey();
      Integer y = Orientation.getOriginValue().getValue();
      return (gameBoard[x][y] == null);
    }

    void printSectionedBoard() {
        // This will print out a 30x30 rectangle around the origin location
        for(int i = 173; i < 204; i++) {
            System.out.println();
            for (int j = 173; j < 204; j++) {
                if(gameBoard[i][j] == null) {
                    System.out.print("---\t");
                }
                else {

                    System.out.print(gameBoard[i][j].getTileId());
                    System.out.print(gameBoard[i][j].getTerrainForVisualization());
                    System.out.print(gameBoard[i][j].getLevel() + "\t");
                }
            }
        }
    }


    void setHex (Hex hex, Pair<Integer,Integer> coordinatePair) {
      Integer originX = Orientation.getOriginValue().getKey();
      Integer originY = Orientation.getOriginValue().getValue();
      Integer x = coordinatePair.getKey() + originX;
      Integer y = coordinatePair.getValue() + originY;
      Pair<Integer, Integer> placementLocation = new Pair<>(x, y);

//      if(isOriginEmpty()) { //First tile placement
//          gameBoard[originX][originY] = hex;
//          gameBoardAvailability[originX][originY] = true;
//          hex.setLocation(new Pair<Integer,Integer>(originX, originY));
//          hex.incrementLevel();
//      }
    if(!HexValidation.isLocationNull(placementLocation, this)){
          Hex presentHex = this.getHex(placementLocation);
          presentHex.incrementLevel();
          presentHex.setTerrain(hex.getTerrain());
          presentHex.setTileId(hex.getTileId());
      }
      else {
          gameBoard[x][y] = hex;
          gameBoardAvailability[x][y] = true;
          hex.setLocation(placementLocation);
          hex.incrementLevel();
      }

    }

    Hex getHex (Pair<Integer, Integer> hexLocation){
        return gameBoard[hexLocation.getKey()][hexLocation.getValue()];
    }


    public boolean[][] getGameBoardAvailability() {
        return gameBoardAvailability;
    }

    public boolean getGameBoardAvailabilityAtCoordinatePair(Pair<Integer,Integer> coordinatePair) {

        return gameBoardAvailability[coordinatePair.getKey()][coordinatePair.getValue()];
    }

}
