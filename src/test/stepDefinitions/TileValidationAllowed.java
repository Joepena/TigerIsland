import cucumber.api.PendingException;
import org.junit.Assert;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;

import java.util.ArrayList;

/**
 * Created by Megans on 4/10/2017.
 */
public class TileValidationAllowed {
        GameAPI gameAPI;
        ArrayList<Tuple> availableTiles;
        ArrayList<Tuple> expectedList = new ArrayList<>();
        @Given("^Game is started, I would like to place a tile$")
        public void placeTile() throws Throwable{
            gameAPI = new GameAPI();
            gameAPI = new GameAPI();
            gameAPI.gameBoard.setHex(new Hex(1, Terrain.terrainType.Jungle),Orientation.getOrigin());
            expectedList.add(new Tuple(-1,0,1));
            expectedList.add(new Tuple(1,1,-2));
            expectedList.add(new Tuple(0,-2,2));
            expectedList.add(new Tuple(-2,0,2));
            expectedList.add(new Tuple(-1,2,-1));
            expectedList.add(new Tuple(-1,1,0));
            expectedList.add(new Tuple(-2,1,1));
            expectedList.add(new Tuple(0,-1,1));
            expectedList.add(new Tuple(0,1,-1));
            expectedList.add(new Tuple(-1,-1,2));
            expectedList.add(new Tuple(2,-1,-1));
            expectedList.add(new Tuple(2,0,-2));
            expectedList.add(new Tuple(1,0,-1));
            expectedList.add(new Tuple(0,2,-2));
            expectedList.add(new Tuple(1,-2,1));
            expectedList.add(new Tuple(2,-2,0));
            expectedList.add(new Tuple(1,-1,0));
            expectedList.add(new Tuple(-2,2,0));

        }

    @When("^I look to see valid locations to expand terrain$")
    public void iLookToSeeValidLocationsToExpandTerrain() throws Throwable {
        availableTiles = gameAPI.getAvailableTilePlacement();

    }

    @Then("^the I will have a list of valid tile locations to choose from$")
    public void theTileWillBePlacedAtThatLocationIfItExistsInTheList() throws Throwable {
        for (Tuple s :expectedList) {
            Assert.assertEquals(true, gameAPI.APIUtils.existsInSetAlready(s,availableTiles));
        }
    }
}
