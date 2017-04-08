import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Joe on 4/6/17.
 */
public class expansionOptionsTest {
  GameAPI gameAPI;

  @Before
  public void setUp() throws Exception {
    gameAPI = new GameAPI();
  }

  @Test
  public void findExpansionOptionsForScenario1() throws Exception {
    setUpScenario1();
    gameAPI.updateSettlements();
    ArrayList<ExpansionOpDataFrame> list = gameAPI.getExpansionOptions(Hex.Team.Black);
    Assert.assertEquals("Scenario 1 assert terrain is jungle", Terrain.terrainType.Jungle, list.get(0).getTerrain());
    Assert.assertEquals("Scenario 1 assert cost is 4", 4, (int)list.get(0).getExpansionCost());
  }

  @Test
  public void findExpansionOptionsForScenario2() throws Exception {
    setUpScenario2();
    gameAPI.updateSettlements();
    ArrayList<ExpansionOpDataFrame> list = gameAPI.getExpansionOptions(Hex.Team.Black);
    Assert.assertEquals("Scenario 2 assert terrain-2 is rocky", Terrain.terrainType.Rocky, list.get(0).getTerrain());
    Assert.assertEquals("Scenario 2 assert cost-2 is 3", 2, (int)list.get(0).getExpansionCost());
    Assert.assertEquals("Scenario 2 assert terrain-1 is jungle", Terrain.terrainType.Jungle, list.get(1).getTerrain());
    Assert.assertEquals("Scenario 2 assert cost-1 is 4", 3, (int)list.get(1).getExpansionCost());
  }
  private void setUpScenario1() {
    Tile originTile = new Tile(1,Terrain.terrainType.Jungle,Terrain.terrainType.Jungle,Orientation.Orientations.downLeft);
    originTile.getLeft().setTeam(Hex.Team.Black);
    Hex hex1 = new Hex(1,Terrain.terrainType.Jungle);
    Hex hex2 = new Hex(1,Terrain.terrainType.Jungle);
    Hex hex3 = new Hex(1,Terrain.terrainType.Jungle);
    gameAPI.placeTile(originTile,Orientation.getOrigin());
    gameAPI.gameBoard.setHex(hex1,Orientation.getRIGHT());
    gameAPI.gameBoard.setHex(hex2,Orientation.getUPRIGHT());
    gameAPI.gameBoard.setHex(hex3,Orientation.getUPLEFT());
  }


  private void setUpScenario2() {
    Tile originTile = new Tile(1,Terrain.terrainType.Rocky,Terrain.terrainType.Jungle,Orientation.Orientations.downLeft);
    originTile.getLeft().setTeam(Hex.Team.Black);
    originTile.getRight().setTeam(Hex.Team.Black);
    Hex hex1 = new Hex(1,Terrain.terrainType.Jungle);
    Hex hex2 = new Hex(1,Terrain.terrainType.Jungle);
    Hex hex3 = new Hex(1,Terrain.terrainType.Jungle);
    Hex hex4 = new Hex(1,Terrain.terrainType.Rocky);
    Hex hex5 = new Hex(1,Terrain.terrainType.Jungle);
    Hex hex6 = new Hex(1,Terrain.terrainType.Rocky);
    Hex hex7 = new Hex(1, Terrain.terrainType.Volcano);
    gameAPI.placeTile(originTile,Orientation.getOrigin());
    gameAPI.gameBoard.setHex(hex1,Orientation.getDOWNRIGHT());
    gameAPI.gameBoard.setHex(hex2,Orientation.getRIGHT());
    gameAPI.gameBoard.setHex(hex3,Orientation.getUPRIGHT());
    gameAPI.gameBoard.setHex(hex4,Orientation.getUPLEFT());
    gameAPI.gameBoard.setHex(hex5,Orientation.upRightOf(hex4.getLocation()));
    gameAPI.gameBoard.setHex(hex6,Orientation.getLEFT());
    gameAPI.gameBoard.setHex(hex7,Orientation.upLeftOf(hex6.getLocation()));
  }

}
