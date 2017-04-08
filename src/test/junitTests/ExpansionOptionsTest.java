import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Joe on 4/6/17.
 */
public class ExpansionOptionsTest {
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
  public void confirmExpansionOccuredForScenario1() throws Exception {
    setUpScenario1();
    gameAPI.updateSettlements();
    ArrayList<ExpansionOpDataFrame> list = gameAPI.getExpansionOptions(Hex.Team.Black);
    gameAPI.performLandGrab(list.get(0).getExpansionStart());
    Hex hex1 = gameAPI.gameBoard.getHex(Orientation.getRIGHT());
    Hex hex2 = gameAPI.gameBoard.getHex(Orientation.getUPRIGHT());
    Hex hex3 = gameAPI.gameBoard.getHex(Orientation.getUPLEFT());
    Assert.assertEquals("Scenario 1 assert Villagers are 16", 16, gameAPI.getVillagerCount());
    Assert.assertEquals("Scenario 1 assert hex 1 is occupied by Meeple", Hex.gamePieces.Meeple, hex1.getOccupiedBy());
    Assert.assertEquals("Scenario 1 assert hex 2 is occupied by Meeple", Hex.gamePieces.Meeple, hex2.getOccupiedBy());
    Assert.assertEquals("Scenario 1 assert hex 3 is occupied by Meeple", Hex.gamePieces.Meeple, hex3.getOccupiedBy());
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

  @Test
  public void confirmExpansionOccuredForScenario2RockyGrab() throws Exception {
    setUpScenario2();
    gameAPI.updateSettlements();
    ArrayList<ExpansionOpDataFrame> list = gameAPI.getExpansionOptions(Hex.Team.Black);
    gameAPI.performLandGrab(list.get(0).getExpansionStart());
    Hex hex1 = gameAPI.gameBoard.getHex(Orientation.getDOWNRIGHT());
    Hex hex2 = gameAPI.gameBoard.getHex(Orientation.getRIGHT());
    Hex hex3 = gameAPI.gameBoard.getHex(Orientation.getUPRIGHT());
    Hex hex4 = gameAPI.gameBoard.getHex(Orientation.getUPLEFT());
    Hex hex5 = gameAPI.gameBoard.getHex(Orientation.upRightOf(hex4.getLocation()));
    Hex hex6 = gameAPI.gameBoard.getHex(Orientation.getLEFT());
    Assert.assertEquals("Scenario 2-RockyGrab assert Villagers are 18", 18, gameAPI.getVillagerCount());
    Assert.assertEquals("Scenario 2-RockyGrab assert hex 4 is occupied by Meeple", Hex.gamePieces.Meeple, hex4.getOccupiedBy());
    Assert.assertEquals("Scenario 2-RockyGrab assert hex 6 is occupied by Meeple", Hex.gamePieces.Meeple, hex6.getOccupiedBy());
    Assert.assertEquals("Scenario 2-RockyGrab hex 1 is occupied by empty", Hex.gamePieces.empty, hex1.getOccupiedBy());
    Assert.assertEquals("Scenario 2-RockyGrab hex 2 is occupied by empty", Hex.gamePieces.empty, hex2.getOccupiedBy());
    Assert.assertEquals("Scenario 2-RockyGrab hex 3 is occupied by empty", Hex.gamePieces.empty, hex3.getOccupiedBy());
    Assert.assertEquals("Scenario 2-RockyGrab hex 5 is occupied by empty", Hex.gamePieces.empty, hex5.getOccupiedBy());
  }
  @Test
  public void confirmExpansionOccuredForScenario2JungleGrab() throws Exception {
    setUpScenario2();
    gameAPI.updateSettlements();
    ArrayList<ExpansionOpDataFrame> list = gameAPI.getExpansionOptions(Hex.Team.Black);
    gameAPI.performLandGrab(list.get(1).getExpansionStart());
    Hex hex2 = gameAPI.gameBoard.getHex(Orientation.getRIGHT());
    Hex hex3 = gameAPI.gameBoard.getHex(Orientation.getUPRIGHT());
    Hex hex4 = gameAPI.gameBoard.getHex(Orientation.getUPLEFT());
    Hex hex5 = gameAPI.gameBoard.getHex(Orientation.upRightOf(hex4.getLocation()));
    Hex hex6 = gameAPI.gameBoard.getHex(Orientation.getLEFT());
    Assert.assertEquals("Scenario 2-JungleGrab assert Villagers are 17", 17, gameAPI.getVillagerCount());
    Assert.assertEquals("Scenario 2-JungleGrab hex 2 is occupied by Meeple", Hex.gamePieces.Meeple, hex2.getOccupiedBy());
    Assert.assertEquals("Scenario 2-JungleGrab hex 3 is occupied by Meeple", Hex.gamePieces.Meeple, hex3.getOccupiedBy());
    Assert.assertEquals("Scenario 2-JungleGrab hex 5 is occupied by Meeple", Hex.gamePieces.Meeple, hex5.getOccupiedBy());
    Assert.assertEquals("Scenario 2-JungleGrab assert hex 4 is occupied by empty", Hex.gamePieces.empty, hex4.getOccupiedBy());
    Assert.assertEquals("Scenario 2-JungleGrab assert hex 6 is occupied by empty", Hex.gamePieces.empty, hex6.getOccupiedBy());
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
