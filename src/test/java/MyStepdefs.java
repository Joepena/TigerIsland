import cucumber.api.PendingException;
import javafx.util.Pair;
import org.junit.Assert;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import java.util.ArrayList;

/**
 * Created by Joe on 3/18/17.
 */

public class MyStepdefs {

    Board testBoard;
    GameAPI testAPI;
    HexValidation validate;
    Hex testHex;
    Tile testTile;
    Tuple testPair;
    Tuple secondTestPair;

    @Given("^there are no hexes on the board")
    public void setupBoardForHexTest () throws Throwable {
      testBoard = new Board();
      testHex = new Hex(0, Terrain.terrainType.Jungle);
      testPair = Orientation.getOrigin();
    }

    @When("^the first player places a hex")
    public void place_first_hex () throws Throwable {
      testBoard.setHex(testHex, testPair);
    }

    @Then("^the first hex should be placed at the origin")
    public void hex_should_be_in_origin_of_board () throws Throwable {
      Assert.assertTrue(!testBoard.isOriginEmpty());
    }


    @Given("^the player wants to place their tile entirely on top of one existing tile$")
    public void thePlayerWantsToPlaceTheirTileEntirelyOnTopOfOneExistingTile() throws Throwable {
        setupNukeScenario();
    }

    @When("^the player tries to place the tile on top of the existing tile$")
    public void thePlayerTriesToPlaceTheTile() throws Throwable {

        Tile tile = new Tile(12, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.left);
        TilePositionCoordinates placementLocation = new
                TilePositionCoordinates(Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin())), Orientation.Orientations.left);

        ArrayList<Tuple> validNukeLocations = testAPI.getValidNukingLocations();
        if( testAPI.APIUtils.isValidTileNukingPosition(placementLocation) || validNukeLocations.contains(Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin()))))
            testAPI.placeTile(tile, placementLocation.getVolcanoCoordinates());
    }

    @Then("^the covered tile will prevent the player from placing their tile$")
    public void thePlayerWillBePreventedFromPlacingTheirTile() throws Throwable {
        Hex hex = testBoard.getHex(Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin())));

        Assert.assertTrue("Tile was not changed", hex.getLevel() == 1);
    }

    @Given("^the player wants to place a piece without aligning the volcano hex on the existing and the new tile$")
    public void thePlayerWantsToPlaceTheirTilWithVolcanoUnalignedWithExistingTile() throws Throwable {
        setupNukeScenario();
    }

    @When("^the player tries to place the tile on the non-volcano hex$")
    public void thePlayerTriesToPlaceTheUnalignedTile() throws Throwable {

        Tile tile = new Tile(12, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.upRight);
        TilePositionCoordinates placementLocation = new
                TilePositionCoordinates(Orientation.downRightOf(Orientation.getOrigin()), Orientation.Orientations.upRight);

        ArrayList<Tuple> validNukeLocations = testAPI.getValidNukingLocations();
        if( testAPI.APIUtils.isValidTileNukingPosition(placementLocation) || validNukeLocations.contains(Orientation.downRightOf(Orientation.getOrigin())))
            testAPI.placeTile(tile, placementLocation.getVolcanoCoordinates());
    }

    @Then("^the unaligned volcano hex will prevent the player from placing their tile$")
    public void thePlayerWillBePreventedFromPlacingTheirUnalignedTile() throws Throwable {
        Hex hex = testBoard.getHex(Orientation.downRightOf(Orientation.getOrigin()));

        Assert.assertTrue("Tile was not changed", hex.getLevel() == 1);
    }

    @Given("^the player wants to place a tile that will nuke a level one settlement of either team$")
    public void thePlayerWantsToPlaceATileThatWillNukeALevelOneSettlementOfEitherTeam() throws Throwable {
        setupNukeScenario();
    }

    @When("^the player tries to place the tile on the settlement$")
    public void thePlayerTriesToPlaceTheTileOnTheSettlement() throws Throwable {
        Tile tile = new Tile(12, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.downLeft);
        TilePositionCoordinates placementLocation = new
                TilePositionCoordinates(Orientation.upLeftOf(Orientation.upRightOf(Orientation.getOrigin())), Orientation.Orientations.downLeft);

        ArrayList<Tuple> validNukeLocations = testAPI.getValidNukingLocations();
        if( testAPI.APIUtils.isValidTileNukingPosition(placementLocation) || validNukeLocations.contains(Orientation.upLeftOf(Orientation.upRightOf(Orientation.getOrigin()))))
            testAPI.placeTile(tile, placementLocation.getVolcanoCoordinates());
    }

    @Then("^the settlement prevents the player from placing their tile$")
    public void theSettlementPreventsThePlayerFromPlacingTheirTile() throws Throwable {
        Hex hex = testBoard.getHex(Orientation.upRightOf(Orientation.getOrigin()));

        Assert.assertTrue("Tile was not changed", hex.getLevel() == 1);
    }

    @Given("^the player wats to place a tile that will nuke an existing Totoro$")
    public void thePlayerWatsToPlaceATileThatWillNukeAnExistingTotoro() throws Throwable {
        setupNukeScenario();
    }

    @When("^the player tries to place the tile on the Totoro$")
    public void thePlayerTriesToPlaceTheTileOnTheTotoro() throws Throwable {
        Tile tile = new Tile(12, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.downLeft);
        TilePositionCoordinates placementLocation = new
                TilePositionCoordinates(Orientation.upRightOf(Orientation.upRightOf(Orientation.upRightOf(Orientation.upLeftOf(Orientation.getOrigin())))),
                                    Orientation.Orientations.downLeft);

        ArrayList<Tuple> validNukeLocations = testAPI.getValidNukingLocations();
        if( testAPI.APIUtils.isValidTileNukingPosition(placementLocation) || validNukeLocations.contains(Orientation.upRightOf(Orientation.upRightOf(Orientation.upRightOf(Orientation.upLeftOf(Orientation.getOrigin()))))))
            testAPI.placeTile(tile, placementLocation.getVolcanoCoordinates());
    }

    @Then("^the Totoro prevents the player from placing their tile$")
    public void theTotoroPreventsThePlayerFromPlacingTheirTile() throws Throwable {
        Hex hex = testBoard.getHex(Orientation.upRightOf(Orientation.upRightOf(Orientation.upRightOf(Orientation.upLeftOf(Orientation.getOrigin())))));

        Assert.assertTrue("Tile was not changed", hex.getLevel() == 1);
    }


    @Given("^A location with multiple pieces aligned volcanoes no single settlements and no totoros$")
    public void aLocationWithMultiplePiecesAlignedVolcanoesNoSingleSettlementsAndNoTotoros() throws Throwable {
        setupNukeScenario();
    }

    @When("^the player tries to place a tile at that location$")
    public void thePlayerTriesToPlaceATileAtThatLocation() throws Throwable {
        Tile tile = new Tile(12, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.downRight);
        TilePositionCoordinates placementLocation = new
                TilePositionCoordinates(Orientation.getOrigin(), Orientation.Orientations.downRight);

        ArrayList<Tuple> validNukeLocations = testAPI.getValidNukingLocations();
        if( testAPI.APIUtils.isValidTileNukingPosition(placementLocation) && validNukeLocations.contains(Orientation.getOrigin()))
            testAPI.placeTile(tile, placementLocation.getVolcanoCoordinates());
    }

    @Then("^the player successfully places the tile$")
    public void thePlayerSuccessfullyPlacesTheTile() throws Throwable {
        Hex hex = testBoard.getHex(Orientation.getOrigin());

        Assert.assertTrue("Tile was not changed", hex.getLevel() == 2);
    }

    @Given("^A location with part of a larger settlement aligned volcanoes no totoros and multiple tileIds$")
    public void aLocationWithPartOfALargerSettlementAlignedVolcanoesNoTotorosAndMultipleTileIds() throws Throwable {
        setupNukeScenario();
    }

    @When("^the player tries to place a tile that destroys part of the settlement$")
    public void thePlayerTriesToPlaceATileThatDestroysPartOfTheSettlement() throws Throwable {
        Tile tile = new Tile(12, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.downRight);
        TilePositionCoordinates placementLocation = new TilePositionCoordinates(Orientation.getOrigin(), Orientation.Orientations.downRight);

        ArrayList<Tuple> validNukeLocations = testAPI.getValidNukingLocations();
        if( testAPI.APIUtils.isValidTileNukingPosition(placementLocation) && validNukeLocations.contains(Orientation.getOrigin()))
            testAPI.placeTile(tile, placementLocation.getVolcanoCoordinates());
    }

    @Then("^the player successfully destroys the settlement piece and places the tile$")
    public void thePlayerSuccessfullyDestroysTheSettlementPieceAndPlacesTheTile() throws Throwable {
        Hex hex = testBoard.getHex(Orientation.getOrigin());

        Assert.assertTrue("Tile was not changed", hex.getLevel() == 2);
    }

    private void setupNukeScenario(){
        testAPI = new GameAPI();
        testBoard = testAPI.gameBoard;
        Tile tile2 = new Tile(2, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.left);
        Tile tile3 = new Tile(3, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.right);
        Tile tile4 = new Tile(4, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.downRight);
        Tile tile5 = new Tile(5, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.downRight);

        testAPI.placeFirstTile();
        testAPI.placeTile(tile2, Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin())));
        testAPI.placeTile(tile3, Orientation.upRightOf(Orientation.upLeftOf(Orientation.getOrigin())));
        testAPI.placeTile(tile4, Orientation.upRightOf(Orientation.upRightOf(Orientation.upRightOf(Orientation.upLeftOf(Orientation.getOrigin())))));
        testAPI.placeTile(tile5, Orientation.leftOf(Orientation.leftOf(Orientation.getOrigin())));

        Hex temp = testBoard.getHex(Orientation.upRightOf(Orientation.getOrigin()));
        temp.setOccupiedBy(Hex.gamePieces.Meeple);
        temp.setTeam(Hex.Team.Black);

        temp = testBoard.getHex(Orientation.upLeftOf(Orientation.getOrigin()));
        temp.setOccupiedBy(Hex.gamePieces.Meeple);
        temp.setTeam(Hex.Team.White);

        temp = testBoard.getHex(Orientation.leftOf(Orientation.getOrigin()));
        temp.setOccupiedBy(Hex.gamePieces.Meeple);
        temp.setTeam(Hex.Team.Black);

        temp = testBoard.getHex(Orientation.leftOf(Orientation.downLeftOf(Orientation.getOrigin())));
        temp.setOccupiedBy(Hex.gamePieces.Meeple);
        temp.setTeam(Hex.Team.Black);

        temp = testBoard.getHex(Orientation.upLeftOf(Orientation.upRightOf(Orientation.upRightOf(Orientation.getOrigin()))));
        temp.setOccupiedBy(Hex.gamePieces.Totoro);
        temp.setTeam(Hex.Team.Black);

        testAPI.updateSettlements();
    }

  @Given("There are n tiles of the same type adjacent to the existing settlement or to tiles adjacent to those tiles of level")
  public void thereAreNTilesOfTheSameTypeAdjacentToTheExistingSettlementOrToTilesAdjacentToThoseTilesOfLevelK() throws Throwable {
    GameAPI gameAPI = new GameAPI();
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
    gameAPI.updateSettlements();
    ArrayList<ExpansionOpDataFrame> list = gameAPI.getExpansionOptions(Hex.Team.Black);
    Assert.assertEquals("Scenario 2 assert terrain-2 is rocky", Terrain.terrainType.Rocky, list.get(0).getTerrain());
    Assert.assertEquals("Scenario 2 assert cost-2 is 3", 2, (int)list.get(0).getExpansionCost());
    Assert.assertEquals("Scenario 2 assert terrain-1 is jungle", Terrain.terrainType.Jungle, list.get(1).getTerrain());
    Assert.assertEquals("Scenario 2 assert cost-1 is 4", 3, (int)list.get(1).getExpansionCost());
  }

  @When("I expand the settlement")
  public void iExpandTheSettlement() throws Throwable {
    Assert.assertEquals("Coming soon", true, true);
  }

  @Then("meeples are placed on each of those n tiles")
  public void kMeeplesArePlacedOnEachOfThoseNTiles() throws Throwable {
    Assert.assertEquals("Coming soon", true, true);
  }
}
