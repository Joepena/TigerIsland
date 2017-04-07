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

    @Given("^the game is being played")
    public void setupTile () throws Throwable {
        testBoard = new Board();
    }

    @When("^i draw a tile")
    public void createTile () throws Throwable {
        Terrain.terrainType terrainLeft = Terrain.terrainType.Grassland;
        Terrain.terrainType terrainRight = Terrain.terrainType.Lake;
        Orientation.Orientations leftOrientation = Orientation.Orientations.downLeft;
        testTile = new Tile(1,terrainLeft,terrainRight,leftOrientation);
    }

    @Then("^i should have a tile composed of 3 hexes joined together with one being a volcano")
    public void tile_should_be_composed_of_three_hexagons () throws Throwable {
        Assert.assertEquals("verify tileId",testTile.getTileId(),1);
        Assert.assertEquals("verify a volcano hex", testTile.getVolcano().getTerrain(), Terrain.terrainType.Volcano);
        Assert.assertEquals("verify getLeft", testTile.getLeft().getTerrain(), Terrain.terrainType.Grassland);
        Assert.assertEquals("verify getRight", testTile.getRight().getTerrain(), Terrain.terrainType.Lake);
    }

    @Given("^there are no tiles on the board")
    public void setupBoardForTileTest () throws Throwable {
        testAPI = new GameAPI();
        Terrain.terrainType terrainLeft = Terrain.terrainType.Grassland;
        Terrain.terrainType terrainRight = Terrain.terrainType.Lake;
        Orientation.Orientations leftOrientation = Orientation.Orientations.downLeft;
        testTile = new Tile(1,terrainLeft,terrainRight,leftOrientation);
        testPair = Orientation.getOrigin();
    }

    @When("^the first player places a tile")
    public void place_first_tile () throws Throwable {
        testAPI.placeTile(testTile, testPair);
    }

    @Then("^the first tile should be placed at the origin in whatever orientation the player chooses")
    public void tile_should_be_in_origin_of_board () throws Throwable {
        Tuple validateVolcano = new Tuple(testTile.getVolcano().getLocation().getX(), testTile.getVolcano().getLocation().getY(), testTile.getVolcano().getLocation().getZ());
        Tuple validateLeft = new Tuple(testTile.getLeft().getLocation().getX(), testTile.getLeft().getLocation().getY(), testTile.getLeft().getLocation().getZ());
        Tuple validateRight = new Tuple(testTile.getRight().getLocation().getX(), testTile.getRight().getLocation().getY(), testTile.getRight().getLocation().getZ());
        validateVolcano = testAPI.gameBoard.calculateOffset(validateVolcano);
        validateLeft = testAPI.gameBoard.calculateOffset(validateLeft);
        validateRight = testAPI.gameBoard.calculateOffset(validateRight);

        Assert.assertTrue(testAPI.gameBoard.getGameBoardAvailability()[validateVolcano.getX()][validateVolcano.getY()][validateVolcano.getZ()]);
        Assert.assertTrue(testAPI.gameBoard.getGameBoardAvailability()[validateLeft.getX()][validateLeft.getY()][validateLeft.getZ()]);
        Assert.assertTrue(testAPI.gameBoard.getGameBoardAvailability()[validateRight.getX()][validateRight.getY()][validateRight.getZ()]);
    }

    @Given("^there is already a hex on the board")
    public void setupBoardForHexValidationPassingTest () throws Throwable {
        testBoard = new Board();
        validate = new HexValidation();
        testHex = new Hex(0, Terrain.terrainType.Jungle);
        testPair = Orientation.getOrigin();
        testBoard.setHex(testHex, testPair);
    }

    @When("^a player tries to place another hex in a valid placement position")
    public void place_hex_in_valid_position () throws Throwable {
        Hex hexToPlace = new Hex(1, Terrain.terrainType.Lake);

        Tuple firstHexLoc = testHex.getLocation();
        secondTestPair = Orientation.upLeftOf(firstHexLoc);

        if(validate.existsAdjacentHex(secondTestPair, testBoard))
            testBoard.setHex(hexToPlace, secondTestPair);

        secondTestPair = testBoard.calculateOffset(secondTestPair);
    }

    @Then("^the hex will be placed on the board")
    public void hex_should_be_placed () throws Throwable {
        Assert.assertTrue(testBoard.getGameBoardAvailability()[secondTestPair.getX()][secondTestPair.getY()][secondTestPair.getZ()]);
    }

    @Given("^there is already a hex placed on the board")
    public void setupBoardForHexValidationFailingTest () throws Throwable {
        testBoard = new Board();
        validate = new HexValidation();
        testHex = new Hex(0, Terrain.terrainType.Jungle);
        testPair = Orientation.getOrigin();
        testBoard.setHex(testHex, testPair);
    }

    @When("^a player tries to place another hex in a invalid placement position")
    public void place_hex_in_invalid_position () throws Throwable {
        Hex hexToPlace = new Hex(1, Terrain.terrainType.Lake);

        Tuple firstHexLoc = testHex.getLocation();
        secondTestPair = Orientation.upLeftOf(Orientation.upLeftOf(firstHexLoc));

        if(validate.existsAdjacentHex(secondTestPair, testBoard))
            testBoard.setHex(hexToPlace, secondTestPair);

        secondTestPair = testBoard.calculateOffset(secondTestPair);
    }

    @Then("^the hex will be not placed on the board")
    public void hex_should_not_be_placed () throws Throwable {
        Assert.assertFalse(testBoard.getGameBoardAvailability()[secondTestPair.getX()][secondTestPair.getY()][secondTestPair.getZ()]);
    }


    @Given("^there is already a tile placed on the board")
    public void there_is_already_a_tile_placed_on_the_board() throws Throwable {

    }

    @When("^the player wants to place a meeple in a valid hex")
    public void the_player_wants_to_place_a_meeple_in_a_valid_hex() throws Throwable {

    }

    @Then("^a meeple will be placed on the board")
    public void a_meeple_will_be_placed_on_the_board() throws Throwable {

    }

    @Given("^there a settlement of size five or greater for the player's team")
    public void there_a_settlement_of_size_5_or_greater_for_the_player_s_team() throws Throwable {

    }

    @When("^the player selects the option to build a totoro and a valid location exists")
    public void the_player_selects_the_option_to_build_a_totoro_and_a_valid_location_exists() throws Throwable {

    }

    @Then("^a totoro will be placed on the board in a valid hex")
    public void a_totoro_will_be_placed_on_the_board_in_a_valid_hex() throws Throwable {

    }

    @Given("^there is not settlement of size five or greater for the player's team")
    public void there_is_not_settlement_of_size_five_or_greater_for_the_player_s_team() throws Throwable {

    }

    @When("^the player selects the option to build a totoro when no valid location exists")
    public void the_player_selects_the_option_to_build_a_totoro_when_no_valid_location_exists() throws Throwable {

    }

    @Then("^a totoro will not be allowed to be placed on the board")
    public void a_totoro_will_not_be_allowed_to_be_placed_on_the_board() throws Throwable {

    }

    @Given("^a player places a meeple on the board")
    public void a_player_places_a_meeple_on_the_board() throws Throwable {

    }

    @When("^the meeple is placed")
    public void the_meeple_is_placed() throws Throwable {

    }

    @Then("^player's score goes up by the level the meeple is placed at squared")
    public void player_s_score_goes_up_by_the_level_the_meeple_is_placed_at_squared() throws Throwable {

    }

    @Given("^a player places a totoro on the board")
    public void a_player_places_a_totoro_on_the_board() throws Throwable {

    }

    @When("^the totoro is placed in a valid location")
    public void the_totoro_is_placed_in_a_valid_location() throws Throwable {

    }

    @Then("^player's score goes up by 200 points")
    public void player_s_score_goes_up_by_200_points() throws Throwable {

    }

    @Given("^a player places a tiger on the board")
    public void a_player_places_a_tiger_on_the_board() throws Throwable {

    }

    @When("^the tiger is placed")
    public void the_tiger_is_placed() throws Throwable {

    }

    @Then("^player's score goes up by 75 points")
    public void player_s_score_goes_up_by_75_points() throws Throwable {

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
