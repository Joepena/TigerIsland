import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

/**
 * Created by Nicholas on 4/6/2017.
 */
public class GamePiecePlacing {
    Board testBoard;
    GameAPI testAPI;
    HexValidation validate;
    Hex testHex;
    Tile testTile;
    Tuple testTuple;
    Tuple hex2Loc;

    //MeeplePlacement.feature
    @Given("^there is already a tile placed on the board")
    public void there_is_already_a_tile_placed_on_the_board() throws Throwable {
        testAPI = new GameAPI();
        testAPI.placeFirstTile();
        testTuple = Orientation.getOrigin();
    }

    @When("^the player wants to place a meeple in a valid hex")
    public void the_player_wants_to_place_a_meeple_in_a_valid_hex() throws Throwable {
        testHex = testAPI.gameBoard.getHex(Orientation.upLeftOf(testTuple));
        testHex.setOccupiedBy(Hex.gamePieces.Meeple);
    }

    @Then("^a meeple will be placed on the board")
    public void a_meeple_will_be_placed_on_the_board() throws Throwable {
        Assert.assertEquals("target hex has a meeple", Hex.gamePieces.Meeple, testHex.getOccupiedBy());
    }

    //TotoroPlacementAllowed.feature
    @Given("^there a settlement of size five or greater for the player's team")
    public void there_a_settlement_of_size_5_or_greater_for_the_player_s_team() throws Throwable {

    }

    @When("^the player selects the option to build a totoro and a valid location exists")
    public void the_player_selects_the_option_to_build_a_totoro_and_a_valid_location_exists() throws Throwable {

    }

    @Then("^a totoro will be placed on the board in a valid hex")
    public void a_totoro_will_be_placed_on_the_board_in_a_valid_hex() throws Throwable {

    }

    //TotoroPlacementNotAllowed.feature
    @Given("^there is not settlement of size five or greater for the player's team")
    public void there_is_not_settlement_of_size_five_or_greater_for_the_player_s_team() throws Throwable {

    }

    @When("^the player selects the option to build a totoro when no valid location exists")
    public void the_player_selects_the_option_to_build_a_totoro_when_no_valid_location_exists() throws Throwable {

    }

    @Then("^a totoro will not be allowed to be placed on the board")
    public void a_totoro_will_not_be_allowed_to_be_placed_on_the_board() throws Throwable {

    }
}
