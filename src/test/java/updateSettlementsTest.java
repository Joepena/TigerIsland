import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Joe on 3/28/17.
 */
public class updateSettlementsTest {
  GameAPI gameAPI;

  @Before
  public void setUp() throws Exception {
    gameAPI = new GameAPI();
    Tile originTile = new Tile(1,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.downLeft);
    Tile tile2 = new Tile(2,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.upLeft);
    Tile tile3 = new Tile(3,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.downRight);
    gameAPI.placeTile(originTile,Orientation.getOriginValue());
    gameAPI.placeTile(tile2,Orientation.addPairByOrientation(Orientation.getOriginValue(),Orientation.Orientations.upLeft));
    gameAPI.placeTile(tile3,Orientation.addPairByOrientation(Orientation.getOriginValue(),Orientation.Orientations.upRight));

  }

  @Test
  public void updateSettlements() throws Exception {
    gameAPI.gameBoard.printSectionedBoard();
  }

}
