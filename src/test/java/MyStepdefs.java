import cucumber.api.PendingException;
import javafx.util.Pair;
import org.junit.Assert;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

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

    /***
     * TODO
     *
     * When we make a getHex() function, change this acceptance test
     * Change magic numbers for origin to global constant
     *
     * ***/
    @Then("^the first hex should be placed at the origin")
    public void hex_should_be_in_origin_of_board () throws Throwable {

      Assert.assertTrue(!testBoard.isOriginEmpty());
    }

    // @Given definition will be reworked once our program supports turn functionality
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
        testPair = Orientation.getOriginValue();

    }

    @When("^the first player places a tile")
    public void place_first_tile () throws Throwable {
        testAPI.placeTile(testTile, testPair);
    }

    @Then("^the first tile should be placed at the origin in whatever orientation the player chooses")
    public void tile_should_be_in_origin_of_board () throws Throwable {
        Assert.assertTrue(testAPI.gameBoard.getGameBoardAvailability()[188][188]);
        Assert.assertTrue(testAPI.gameBoard.getGameBoardAvailability()[186][187]);
        Assert.assertTrue(testAPI.gameBoard.getGameBoardAvailability()[186][189]);
    }

    @Given("^there is already a hex on the board")
    public void setupBoardForHexValidationPassingTest () throws Throwable {
        testBoard = new Board();
        validate = new HexValidation();
        testHex = new Hex(0, Terrain.terrainType.Jungle);
        testPair = Orientation.getOriginValue();
        testBoard.setHex(testHex, testPair);
    }

    @When("^a player tries to place another hex in a valid placement position")
    public void place_hex_in_valid_position () throws Throwable {
        Hex hexToPlace = new Hex(1, Terrain.terrainType.Lake);

        Pair firstHexLoc = new Pair(testHex.getLocation().getKey(), testHex.getLocation().getValue());
        Pair hex2Loc = Orientation.upLeftOf(firstHexLoc);

        if(validate.existsAdjacentHex(hex2Loc, testBoard))
            testBoard.setHex(hexToPlace, hex2Loc);
    }

    @Then("^the hex will be placed on the board")
    public void hex_should_be_placed () throws Throwable {
        Assert.assertTrue(testBoard.getGameBoardAvailability()[190][187]);
    }

    @Given("^there is already a hex placed on the board")
    public void setupBoardForHexValidationFailingTest () throws Throwable {
        testBoard = new Board();
        validate = new HexValidation();
        testHex = new Hex(0, Terrain.terrainType.Jungle);
        testPair = Orientation.getOriginValue();
        testBoard.setHex(testHex, testPair);
    }

    @When("^a player tries to place another hex in a invalid placement position")
    public void place_hex_in_invalid_position () throws Throwable {
        Hex hexToPlace = new Hex(1, Terrain.terrainType.Lake);

        Pair firstHexLoc = new Pair(testHex.getLocation().getKey(), testHex.getLocation().getValue());
        Pair hex2Loc = Orientation.upLeftOf(Orientation.upLeftOf(firstHexLoc));

        if(validate.existsAdjacentHex(hex2Loc, testBoard))
            testBoard.setHex(hexToPlace, hex2Loc);
    }

    @Then("^the hex will be not placed on the board")
    public void hex_should_not_be_placed () throws Throwable {
        Assert.assertFalse(testBoard.getGameBoardAvailability()[190][187]);

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
}
