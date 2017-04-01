/**
 * Created by Joe on 4/1/17.
 */
public class Board {

  protected  Hex[][][] gameBoard;
  protected  boolean[][][] gameBoardAvailability;
  private  final int ORIGINOFFSET = 97;
  public Board() {
    gameBoard = new Hex[194][194][194];
    gameBoardAvailability = new boolean[194][194][194];
  }

  public  boolean isOriginEmpty() {
    Tuple origin = calculateOffset(Orientation.getOrigin());
    return (gameBoard[origin.getX()][origin.getY()][origin.getZ()] == null);
  }


  public void setHex (Hex hex, Tuple coordinates) {


    if(!HexValidation.isLocationNull(coordinates, this)){
      Hex presentHex = getHex(coordinates);
      presentHex.incrementLevel();
      presentHex.setTerrain(hex.getTerrain());
      presentHex.setTileId(hex.getTileId());
    }
    else {
      Tuple actualCoordinates = calculateOffset(coordinates);
      gameBoard[actualCoordinates.getX()][actualCoordinates.getY()][actualCoordinates.getZ()] = hex;
      hex.setLocation(coordinates);
      hex.incrementLevel();
    }

  }

  Hex getHex(Tuple hexLocation) {
    Tuple coordinates = calculateOffset(hexLocation);
    return gameBoard[coordinates.getX()][coordinates.getY()][coordinates.getZ()];
  }

  public boolean[][][] getGameBoardAvailability() {
    return gameBoardAvailability;
  }

  private Tuple calculateOffset(Tuple coordinates) {
    int x = coordinates.getX() + ORIGINOFFSET;
    int y = coordinates.getY() + ORIGINOFFSET;
    int z = coordinates.getZ() + ORIGINOFFSET;

    return new Tuple(x,y,z);

  }
  private Tuple removeOffset(Tuple coordinates) {
    int x = coordinates.getX() - ORIGINOFFSET;
    int y = coordinates.getY() - ORIGINOFFSET;
    int z = coordinates.getZ() - ORIGINOFFSET;

    return new Tuple(x,y,z);

  }

}
