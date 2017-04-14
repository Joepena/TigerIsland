import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.ArrayList;
import org.junit.Assert;

/**
 * Created by Joe on 4/10/17.
 */
public class SettlementJoining {
  private GameAPI gameAPI;
  Tile originTile;
  private Hex hex1;
  private Hex hex2;
  private Hex hex3;
  private Hex hex4;
  private Hex hex5;
  private Hex hex6;

  @Given("^Two settlements will become adjacent to one another after a build action$")
  public void twoSettlementsWillBeComeAdjacentToOneAnotherAfterABuildAction() throws Throwable {
    setUpGivenScenario();
    gameAPI.updateSettlements();
    ArrayList<SettlementDataFrame> list = gameAPI.getBlackSettlements().getListOfSettlements();
    ArrayList<SettlementDataFrame> list2 = gameAPI.getWhiteSettlements().getListOfSettlements();

    Assert.assertEquals("assert 2 settlements", 2, list.size()+list2.size());
    originTile.getRight().setTeam(Hex.Team.Black);
  }
  private void setUpGivenScenario() {
    gameAPI = new GameAPI();
    originTile = new Tile(1,Terrain.terrainType.Rocky,Terrain.terrainType.Jungle,Orientation.Orientations.downLeft);
    originTile.getLeft().setTeam(Hex.Team.Black);
    Hex hex1 = new Hex(1,Terrain.terrainType.Jungle);
    hex1.setTeam(Hex.Team.Black);
    Hex hex2 = new Hex(1,Terrain.terrainType.Jungle);
    hex2.setTeam(Hex.Team.Black);
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

  @When("^The turn in completed$")
  public void theTurnInCompleted() throws Throwable {
    setUpGivenScenario2();
    gameAPI.updateSettlements();
  }
  private void setUpGivenScenario2() {
    gameAPI = new GameAPI();
    originTile = new Tile(1,Terrain.terrainType.Rocky,Terrain.terrainType.Jungle,Orientation.Orientations.downLeft);
    originTile.getLeft().setTeam(Hex.Team.Black);
    Hex hex1 = new Hex(1,Terrain.terrainType.Jungle);
    hex1.setTeam(Hex.Team.Black);
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

  @Then("^These two settlements should be counted as part of the same settlement$")
  public void theseTwoSettlementsShouldBeCountedAsPartOfTheSameSettlement() throws Throwable {
    ArrayList<SettlementDataFrame> list = gameAPI.getBlackSettlements().getListOfSettlements();
    ArrayList<SettlementDataFrame> list2 = gameAPI.getWhiteSettlements().getListOfSettlements();
    Assert.assertEquals("assert 1 settlement", 1, list.size()+list2.size());
  }

  @Given("^One settlement has been broken into$")
  public void oneSettlementHasBeenBrokenInto() throws Throwable {

  }

  @When("^The turn is completed$")
  public void theTurnIsCompleted() throws Throwable {
    gameAPI.updateSettlements();

  }


  @Then("^These two settlements should be counted as part of different settlements$")
  public void theseTwoSettlementsShouldBeCountedAsPartOfDifferentSettlements() throws Throwable {
    ArrayList<SettlementDataFrame> list = gameAPI.getBlackSettlements().getListOfSettlements();
    ArrayList<SettlementDataFrame> list2 = gameAPI.getWhiteSettlements().getListOfSettlements();
    Assert.assertEquals("assert 2 settlements", 2, list.size()+list2.size());
  }
}
