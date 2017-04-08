import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;


/**
 * Created by Nicholas on 4/6/2017.
 */
public class Scoring {
    Board testBoard;
    GameAPI testAPI;
    HexValidation validate;
    Hex testHex;
    Tile testTile;
    Tuple testPair;
    Tuple hex2Loc;

    //MeepleScoring.feature
    @Given("^a player places a meeple on the board")
    public void a_player_places_a_meeple_on_the_board() throws Throwable {

    }

    @When("^the meeple is placed")
    public void the_meeple_is_placed() throws Throwable {

    }

    @Then("^player's score goes up by the level the meeple is placed at squared")
    public void player_s_score_goes_up_by_the_level_the_meeple_is_placed_at_squared() throws Throwable {

    }

    //TotoroScoring.feature
    @Given("^a player places a totoro on the board")
    public void a_player_places_a_totoro_on_the_board() throws Throwable {

    }

    @When("^the totoro is placed in a valid location")
    public void the_totoro_is_placed_in_a_valid_location() throws Throwable {

    }

    @Then("^player's score goes up by 200 points")
    public void player_s_score_goes_up_by_200_points() throws Throwable {

    }

    //TigerScoring.feature
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
