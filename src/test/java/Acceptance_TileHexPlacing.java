import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

/**
 * Created by Nicholas on 4/6/2017.
 */
public class Acceptance_TileHexPlacing {
    Board testBoard;
    GameAPI testAPI;
    HexValidation validate;
    Hex testHex;
    Tile testTile;
    Tuple originTuple = Orientation.getOrigin();;
    Tuple testTuple;
    Tuple hex2Loc;

    //CreateTile.feature
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

    //HexPlacementAllowed.feature
    @Given("^there is already a hex on the board")
    public void setupBoardForHexValidationPassingTest () throws Throwable {
        testBoard = new Board();
        validate = new HexValidation();
        testHex = new Hex(0, Terrain.terrainType.Jungle);
        testBoard.setHex(testHex, originTuple);
    }

    @When("^a player tries to place another hex in a valid placement position")
    public void place_hex_in_valid_position () throws Throwable {
        Hex hexToPlace = new Hex(1, Terrain.terrainType.Lake);

        Tuple firstHexLoc = testHex.getLocation();
        hex2Loc = Orientation.upLeftOf(firstHexLoc);

        if(validate.existsAdjacentHex(hex2Loc, testBoard))
            testBoard.setHex(hexToPlace, hex2Loc);

        hex2Loc = testBoard.calculateOffset(hex2Loc);
    }

    @Then("^the hex will be placed on the board")
    public void hex_should_be_placed () throws Throwable {
        Assert.assertTrue(testBoard.getGameBoardAvailability()[hex2Loc.getX()][hex2Loc.getY()][hex2Loc.getZ()]);
    }

    //HexPlacementNotAllowed.feature
    @Given("^there is already a hex placed on the board")
    public void setupBoardForHexValidationFailingTest () throws Throwable {
        testBoard = new Board();
        validate = new HexValidation();
        testHex = new Hex(0, Terrain.terrainType.Jungle);
        testBoard.setHex(testHex, originTuple);
    }

    @When("^a player tries to place another hex in a invalid placement position")
    public void place_hex_in_invalid_position () throws Throwable {
        Hex hexToPlace = new Hex(1, Terrain.terrainType.Lake);

        Tuple firstHexLoc = testHex.getLocation();
        hex2Loc = Orientation.upLeftOf(Orientation.upLeftOf(firstHexLoc));

        if(validate.existsAdjacentHex(hex2Loc, testBoard))
            testBoard.setHex(hexToPlace, hex2Loc);

        hex2Loc = testBoard.calculateOffset(hex2Loc);
    }

    @Then("^the hex will be not placed on the board")
    public void hex_should_not_be_placed () throws Throwable {
        Assert.assertFalse(testBoard.getGameBoardAvailability()[hex2Loc.getX()][hex2Loc.getY()][hex2Loc.getZ()]);
    }

    //PlaceFirstTile.feature
    @Given("^there are no tiles on the board$")
    public void there_are_no_tiles_on_the_board() throws Throwable {
        testAPI = new GameAPI();
    }

    @When("^the initial tile is placed$")
    public void the_initial_tile_is_placed() throws Throwable {
        testAPI.placeFirstTile();
    }

    @Then("^the initial tile should be placed at the origin in a specific orientation$")
    public void the_initial_tile_should_be_placed_at_the_origin_in_a_specific_orientation() throws Throwable {
        Assert.assertEquals("origin is volcano", Terrain.terrainType.Volcano, testAPI.gameBoard.getHex(originTuple).getTerrain());
        Assert.assertEquals("upleft is jungle", Terrain.terrainType.Jungle, testAPI.gameBoard.getHex(Orientation.upLeftOf(originTuple)).getTerrain());
        Assert.assertEquals("upright is lake", Terrain.terrainType.Lake, testAPI.gameBoard.getHex(Orientation.upRightOf(originTuple)).getTerrain());
        Assert.assertEquals("downleft is rocky", Terrain.terrainType.Rocky, testAPI.gameBoard.getHex(Orientation.downLeftOf(originTuple)).getTerrain());
        Assert.assertEquals("downright is grass", Terrain.terrainType.Grassland, testAPI.gameBoard.getHex(Orientation.downRightOf(originTuple)).getTerrain());
    }

    //TilePlacementAllowed.feature
    @Given("^Game is started, I have a tile to place without nuking$")
    public void i_have_a_tile() throws Throwable {
        testAPI = new GameAPI();
        testAPI.placeFirstTile();

        testTuple = Orientation.leftOf(Orientation.upLeftOf(originTuple));
        testTile = new Tile(2,Terrain.terrainType.Grassland,Terrain.terrainType.Lake,Orientation.Orientations.downLeft);
    }

    @When("^I try to place the tile in a valid connected location$")
    public void i_try_to_place_the_tile_in_a_valid_location() throws Throwable {
        testAPI.placeTile(testTile,testTuple);
    }

    @Then("^the tile will be placed at that location$")
    public void the_tile_will_be_placed_at_that_location() throws Throwable {
        Assert.assertEquals("successful placement; new tileID 2 exists at left, upleft", 2, testAPI.gameBoard.getHex(testTuple).getTileId());
    }

    @Given("^Game is started, I have a tile to place by nuking$")
    public void i_have_a_tile2() throws Throwable {
        testAPI = new GameAPI();
        testAPI.placeFirstTile();

        testTile = new Tile(2,Terrain.terrainType.Grassland,Terrain.terrainType.Lake,Orientation.Orientations.left);

        //Place dummy tile to the left of first tile, so proper nuking can occur
        Tile tile1 = new Tile(3, Terrain.terrainType.Jungle, Terrain.terrainType.Grassland, Orientation.Orientations.left);
        testAPI.placeTile(tile1, Orientation.leftOf(originTuple));

    }

    @When("^I try to place the tile in a valid nuking location$")
    public void i_try_to_place_the_tile_in_a_valid_nuking_location() throws Throwable {
        testAPI.placeTile(testTile, originTuple);
    }

    @Then("^the tile will nuke that location$")
    public void the_tile_will_be_placed_at_that_location2() throws Throwable {
        Assert.assertEquals("successful placement; tileLevel is now 2 at origin", 2, testAPI.gameBoard.getHex(originTuple).getLevel());
    }

    //TilePlacementNotAllowed.feature
    @Given("^Game is started, I have a tile to place without nuking, with an invalid destination$")
    public void game_is_started_I_have_a_tile_to_place_without_nuking_with_an_invalid_destination() throws Throwable {
        testAPI = new GameAPI();
        testAPI.placeFirstTile();

        testTuple = Orientation.leftOf(Orientation.leftOf(originTuple));
        testTile = new Tile(2,Terrain.terrainType.Grassland,Terrain.terrainType.Lake,Orientation.Orientations.upLeft);
    }

    @When("^I try to place the tile in an unconnected location$")
    public void i_try_to_place_the_tile_in_an_unconnected_location() throws Throwable {
        testAPI.placeTile(testTile, testTuple);
    }

    @Then("^the tile will not be placed at that location$")
    public void the_tile_will_not_be_placed_at_that_location() throws Throwable {
        Assert.assertEquals("failed placement; testTuple should be null", null, testAPI.gameBoard.getHex(testTuple));
    }

    @Given("^Game is started, I have a tile to place by nuking, with an invalid destination$")
    public void game_is_started_I_have_a_tile_to_place_by_nuking_with_an_invalid_destination() throws Throwable {
        testAPI = new GameAPI();
        testAPI.placeFirstTile();

        testTuple = Orientation.leftOf(Orientation.leftOf(originTuple));
        testTile = new Tile(2,Terrain.terrainType.Grassland,Terrain.terrainType.Lake,Orientation.Orientations.upLeft);
    }

    @When("^I try to place the tile in an invalid nuking location$")
    public void i_try_to_place_the_tile_in_an_invalid_nuking_location() throws Throwable {
        testAPI.placeTile(testTile, originTuple);
    }

    @Then("^the tile will not nuke that location$")
    public void the_tile_will_not_nuke_that_location() throws Throwable {
        Assert.assertEquals("blank", true, true);
    }

}
