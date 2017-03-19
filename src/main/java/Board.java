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

    public boolean isEmpty() {
      Integer x = Orientation.ORIGIN.getKey();
      Integer y = Orientation.ORIGIN.getValue();
      return (gameBoard[x][y] == null) ? true : false;
    }

    void printSectionedBoard() {
        // This will print out a 30x30 rectangle around the origin location
        for(int i = 173; i < 204; i++) {
            System.out.println();
            for (int j = 173; j < 204; j++) {
                if(gameBoard[i][j] == null) {
                    System.out.print("- ");
                }
                else {
                    System.out.print(gameBoard[i][j].getTileNumber()+ " ");
                }
            }
        }
    }

    void setHex (Hex hex, Pair<Integer,Integer> coordinatePair) {
      Integer originX = Orientation.ORIGIN.getKey();
      Integer originY = Orientation.ORIGIN.getValue();
      Integer x = coordinatePair.getKey() + originX;
      Integer y = coordinatePair.getValue() + originY;

      if(isEmpty()) { //First tile placement
          gameBoard[originX][originY] = hex;
          gameBoardAvailability[originX][originY] = true;
          return;
      }
      gameBoard[x][y] = hex;
      gameBoardAvailability[x][y] = true;

    }

    public boolean[][] getGameBoardAvailability() {
        return gameBoardAvailability;
    }
}
