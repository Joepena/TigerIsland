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
    Tuple hex2Loc;










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
