import cucumber.api.PendingException;
import java.util.ArrayList;
import org.junit.Assert;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

/**
 * Created by Joe on 4/8/17.
 */
public class ExpansionStepDef {

  private GameAPI gameAPI;
  private Hex hex1;
  private Hex hex2;
  private Hex hex3;
  private Hex hex4;
  private Hex hex5;
  private Hex hex6;

  @Given("^There are n tiles of the same type adjacent to the existing settlement or to tiles adjacent to those tiles of level k$")
  public void thereAreNTilesOfTheSameTypeAdjacentToTheExistingSettlementOrToTilesAdjacentToThoseTilesOfLevelK() throws Throwable {
    setUpGivenScenario();
    gameAPI.updateSettlements();
    ArrayList<ExpansionOpDataFrame> list = gameAPI.getExpansionOptions(Hex.Team.Black);
    Assert.assertEquals("Scenario 2 assert terrain-2 is rocky", Terrain.terrainType.Rocky, list.get(1).getTerrain());
    Assert.assertEquals("Scenario 2 assert cost-2 is 3", 2, (int)list.get(1).getExpansionCost());
    Assert.assertEquals("Scenario 2 assert terrain-1 is jungle", Terrain.terrainType.Jungle, list.get(0).getTerrain());
    Assert.assertEquals("Scenario 2 assert cost-1 is 4", 3, (int)list.get(0).getExpansionCost());
  }

  private void setUpGivenScenario() {
    gameAPI = new GameAPI();
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

  @When("I expand the settlement")
  public void iExpandTheSettlement() throws Throwable {
    ArrayList<ExpansionOpDataFrame> list = gameAPI.getExpansionOptions(Hex.Team.Black);

    gameAPI.performLandGrab(list.get(1));

    hex1 = gameAPI.gameBoard.getHex(Orientation.getDOWNRIGHT());
    hex2 = gameAPI.gameBoard.getHex(Orientation.getRIGHT());
    hex3 = gameAPI.gameBoard.getHex(Orientation.getUPRIGHT());
    hex4 = gameAPI.gameBoard.getHex(Orientation.getUPLEFT());
    hex5 = gameAPI.gameBoard.getHex(Orientation.upRightOf(hex4.getLocation()));
    hex6 = gameAPI.gameBoard.getHex(Orientation.getLEFT());
    Assert.assertEquals("Scenario 2-RockyGrab assert Villagers are 18", 18, gameAPI.getVillagerCount());

  }

  @Then("^k meeples are placed on each of those n tiles$")
  public void kMeeplesArePlacedOnEachOfThoseNTiles() throws Throwable {
    Assert.assertEquals("Scenario 2-RockyGrab assert hex 4 is occupied by Meeple", Hex.gamePieces.Meeple, hex4.getOccupiedBy());
    Assert.assertEquals("Scenario 2-RockyGrab assert hex 6 is occupied by Meeple", Hex.gamePieces.Meeple, hex6.getOccupiedBy());
    Assert.assertEquals("Scenario 2-RockyGrab hex 1 is occupied by empty", Hex.gamePieces.empty, hex1.getOccupiedBy());
    Assert.assertEquals("Scenario 2-RockyGrab hex 2 is occupied by empty", Hex.gamePieces.empty, hex2.getOccupiedBy());
    Assert.assertEquals("Scenario 2-RockyGrab hex 3 is occupied by empty", Hex.gamePieces.empty, hex3.getOccupiedBy());
    Assert.assertEquals("Scenario 2-RockyGrab hex 5 is occupied by empty", Hex.gamePieces.empty, hex5.getOccupiedBy());
  }

}
