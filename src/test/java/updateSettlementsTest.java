import static org.junit.Assert.*;

import java.util.Iterator;
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
    originTile.getLeft().setTeam(Hex.Team.White);
    originTile.getRight().setTeam(Hex.Team.Black);
    Tile tile2 = new Tile(2,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.upLeft);
    tile2.getLeft().setTeam(Hex.Team.White);
    tile2.getRight().setTeam(Hex.Team.Black);
    Tile tile3 = new Tile(3,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.downRight);
    tile3.getLeft().setTeam(Hex.Team.White);
    tile3.getRight().setTeam(Hex.Team.Black);
    gameAPI.placeTile(originTile,Orientation.getRelativeOriginValue());
    gameAPI.placeTile(tile2,Orientation.addPairByOrientation(Orientation.getRelativeOriginValue(),Orientation.Orientations.upLeft));
    gameAPI.placeTile(tile3,Orientation.addPairByOrientation(Orientation.getRelativeOriginValue(),Orientation.Orientations.upRight));

  }

  @Test
  public void updateSettlements() throws Exception {
    gameAPI.gameBoard.printSectionedBoard();
    gameAPI.updateSettlements();
    Iterator<SettlementDataFrame> white = gameAPI.getWhiteSettlements().getListOfSettlements().iterator();
    Iterator<SettlementDataFrame> black = gameAPI.getBlackSettlements().getListOfSettlements().iterator();
    while(white.hasNext()){
      SettlementDataFrame df = white.next();
      int level = df.getSettlementlevel();
      Hex.Team owner = df.getOwnedBy();
      System.out.println(level+" "+owner);
    }
  }

}
