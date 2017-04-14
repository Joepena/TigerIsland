import cucumber.api.PendingException;
import cucumber.api.java.en.*;
import org.junit.Assert;

import java.util.ArrayList;

/**
 * Created by Megans on 4/10/2017.
 */
public class SettlementCreation {
        Board gameBoard;
        GameAPI gameAPI;
        ArrayList<Tuple> validSettlementLocations;
        ArrayList<Tuple> expectedList;
        @Given("^I want to place the meeple on a volcano$")
        public void settlementCreation() throws Throwable {
            gameAPI = new GameAPI();
            gameBoard = gameAPI.gameBoard;
            gameAPI.placeFirstTile();

        }

    @When("^I try to place the meeple$")
    public void iTryToPlaceTheMeeple() throws Throwable {
        validSettlementLocations = gameAPI.findListOfValidSettlementLocations();

    }

    @Then("^I should not be allowed to place the meeple$")
    public void iShouldNotBeAllowedToPlaceTheMeeple() throws Throwable {
        Assert.assertFalse(gameAPI.APIUtils.existsInSetAlready(Orientation.getOrigin(), validSettlementLocations));

    }

    @Given("^I want to place the meeple on an unoccupied tile$")
    public void iWantToPlaceTheMeepleOnAnUnoccupiedTile() throws Throwable {
        expectedList = new ArrayList<>();
        gameAPI = new GameAPI();
        gameBoard = gameAPI.gameBoard;
        gameAPI.placeFirstTile();
        gameBoard.getHex(Orientation.upRightOf(Orientation.getOrigin())).incrementLevel();
        validSettlementLocations = gameAPI.findListOfValidSettlementLocations();
        expectedList.add(Orientation.upLeftOf(Orientation.getOrigin()));
        expectedList.add(Orientation.downRightOf(Orientation.getOrigin()));
        expectedList.add(Orientation.downLeftOf(Orientation.getOrigin()));
    }

    @And("^It is not on a volcano tile$")
    public void itIsNotOnAVolcanoTile() throws Throwable {
        Assert.assertFalse(gameAPI.APIUtils.existsInSetAlready(Orientation.getOrigin(), validSettlementLocations));
    }

    @And("^It on a Level (\\d+) tile$")
    public void itOnALevelTile(int arg0) throws Throwable {
        Assert.assertFalse(gameAPI.APIUtils.existsInSetAlready(Orientation.upRightOf(Orientation.getOrigin()), validSettlementLocations));
    }

    @Then("^I should be allowed to place the meeple$")
    public void iShouldBeAllowedToPlaceTheMeeple() throws Throwable {
        for (Tuple s :expectedList) {
            Assert.assertEquals(true, gameAPI.APIUtils.existsInSetAlready(s,validSettlementLocations));
        }
    }
}
