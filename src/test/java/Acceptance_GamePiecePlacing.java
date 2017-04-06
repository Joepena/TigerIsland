import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

/**
 * Created by Nicholas on 4/6/2017.
 */
public class Acceptance_GamePiecePlacing {
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
}
