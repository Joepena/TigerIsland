import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;

/**
 * Created by Nicholas on 4/6/2017.
 */
public class Acceptance_GamePiecePlacing {
    Board testBoard;
    GameAPI testAPI = new GameAPI();
    ArrayList<Tuple> validHexes = new ArrayList<>();
    HexValidation validate;
    Hex testHex;
    Tile testTile;
    Tuple testTuple;
    Tuple hex2Loc;
    boolean cantPlace = false;

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

    private void setUpTotoroValidationScenario3() {
        Hex hex1 = new Hex(6, Terrain.terrainType.Volcano);
        Hex hex2 = new Hex(1, Terrain.terrainType.Grassland);
        Hex hex3 = new Hex(1, Terrain.terrainType.Rocky);
        Hex hex4 = new Hex(2, Terrain.terrainType.Volcano);
        Hex hex5 = new Hex(2, Terrain.terrainType.Grassland);
        Hex hex6 = new Hex(2, Terrain.terrainType.Rocky);
        Hex hex7 = new Hex(3, Terrain.terrainType.Volcano);
        Hex hex8 = new Hex(6, Terrain.terrainType.Grassland);
        Hex hex9 = new Hex(3, Terrain.terrainType.Rocky);
        Hex hex10 = new Hex(4, Terrain.terrainType.Rocky);
        Hex hex11 = new Hex(4, Terrain.terrainType.Grassland);
        Hex hex12 = new Hex(4, Terrain.terrainType.Volcano);
        Hex hex13 = new Hex(7, Terrain.terrainType.Rocky);
        Hex hex14 = new Hex(5, Terrain.terrainType.Rocky);
        Hex hex15 = new Hex(5, Terrain.terrainType.Volcano);
        Hex hex16 = new Hex(5, Terrain.terrainType.Grassland);
        Hex hex17 = new Hex(5, Terrain.terrainType.Rocky);
        Hex hex18 = new Hex(5, Terrain.terrainType.Volcano);
        Hex hex19 = new Hex(7, Terrain.terrainType.Rocky);
        Hex hex20 = new Hex(7, Terrain.terrainType.Rocky);
        Hex hex21 = new Hex(6, Terrain.terrainType.Rocky);
        hex9.setOccupiedBy(Hex.gamePieces.Meeple);
        hex10.setOccupiedBy(Hex.gamePieces.Meeple);
        hex16.setOccupiedBy(Hex.gamePieces.Meeple);
        hex11.setOccupiedBy(Hex.gamePieces.Meeple);
        hex8.setOccupiedBy(Hex.gamePieces.Meeple);
        hex20.setOccupiedBy(Hex.gamePieces.Meeple);
        hex3.setOccupiedBy(Hex.gamePieces.Meeple);
        hex14.setOccupiedBy(Hex.gamePieces.Meeple);
        hex13.setOccupiedBy(Hex.gamePieces.Meeple);
        hex19.setOccupiedBy(Hex.gamePieces.Meeple);
        hex17.setOccupiedBy(Hex.gamePieces.Totoro);
        hex9.setTeam(Hex.Team.Black);
        hex10.setTeam(Hex.Team.Black);
        hex16.setTeam(Hex.Team.Black);
        hex11.setTeam(Hex.Team.Black);
        hex8.setTeam(Hex.Team.Black);
        hex19.setTeam(Hex.Team.Black);
        hex3.setTeam(Hex.Team.Black);
        hex14.setTeam(Hex.Team.Black);
        hex13.setTeam(Hex.Team.Black);
        hex20.setTeam(Hex.Team.Black);
        hex17.setTeam(Hex.Team.Black);
        testAPI.gameBoard.setHex(hex1, Orientation.getOrigin());
        testAPI.gameBoard.setHex(hex2, Orientation.downLeftOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex3, Orientation.downRightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex6, Orientation.upRightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex5, Orientation.rightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex4, Orientation.upRightOf(hex5.getLocation()));
        testAPI.gameBoard.setHex(hex8, Orientation.upLeftOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex7, Orientation.leftOf(hex8.getLocation()));
        testAPI.gameBoard.setHex(hex9, Orientation.upRightOf(hex7.getLocation()));
        testAPI.gameBoard.setHex(hex10, Orientation.rightOf(hex9.getLocation()));
        testAPI.gameBoard.setHex(hex11, Orientation.upRightOf(hex9.getLocation()));
        testAPI.gameBoard.setHex(hex12, Orientation.rightOf(hex11.getLocation()));
        testAPI.gameBoard.setHex(hex13, Orientation.rightOf(hex5.getLocation()));
        testAPI.gameBoard.setHex(hex14, Orientation.rightOf(hex3.getLocation()));
        testAPI.gameBoard.setHex(hex15, Orientation.rightOf(hex14.getLocation()));
        testAPI.gameBoard.setHex(hex16, Orientation.rightOf(hex10.getLocation()));
        testAPI.gameBoard.setHex(hex17, Orientation.rightOf(hex12.getLocation()));
        testAPI.gameBoard.setHex(hex18, Orientation.rightOf(hex16.getLocation()));
        testAPI.gameBoard.setHex(hex19, Orientation.rightOf(hex4.getLocation()));
        testAPI.gameBoard.setHex(hex20, Orientation.rightOf(hex13.getLocation()));
        testAPI.gameBoard.setHex(hex21, Orientation.leftOf(hex1.getLocation()));

        testAPI.updateSettlements();
    }



    @Given("^there a settlement of size five or greater for the player's team")
    public void setupSettlementsForTotoroTests() throws Throwable {
        setUpTotoroValidationScenario3();
    }

    @When("^the player selects the option to build a totoro and a valid location exists")
    public void selectTotoroBuildOption() throws Throwable {
        if(testAPI.canSelectBuildTotoro(Hex.Team.Black))
            validHexes = testAPI.validTotoroPlacements(Hex.Team.Black);
        else
            cantPlace = true;
    }

    @Then("^a totoro will be placed on the board in a valid hex")
    public void placeTotoroInValidHex() throws Throwable {
        Tuple placementLocation = validHexes.get(0);
        testAPI.createTotoroSanctuary(placementLocation, Hex.Team.Black);

        Assert.assertEquals(Hex.gamePieces.Totoro, testAPI.gameBoard.getHex(placementLocation).getOccupiedBy());
        Assert.assertEquals(Hex.Team.Black, testAPI.gameBoard.getHex(placementLocation).getTeam());
        Assert.assertFalse(cantPlace);
    }

    private void setUpTotoroValidationScenario2() {
        Hex hex1 = new Hex(1, Terrain.terrainType.Volcano);
        Hex hex2 = new Hex(1, Terrain.terrainType.Grassland);
        Hex hex3 = new Hex(1, Terrain.terrainType.Rocky);
        Hex hex4 = new Hex(2, Terrain.terrainType.Volcano);
        Hex hex5 = new Hex(2, Terrain.terrainType.Grassland);
        Hex hex6 = new Hex(2, Terrain.terrainType.Rocky);
        Hex hex7 = new Hex(3, Terrain.terrainType.Volcano);
        Hex hex8 = new Hex(3, Terrain.terrainType.Grassland);
        Hex hex9 = new Hex(3, Terrain.terrainType.Rocky);
        Hex hex10 = new Hex(4, Terrain.terrainType.Rocky);
        Hex hex11 = new Hex(4, Terrain.terrainType.Grassland);
        Hex hex12 = new Hex(4, Terrain.terrainType.Volcano);
        hex9.setTeam(Hex.Team.Black);
        hex8.setTeam(Hex.Team.Black);
        hex6.setTeam(Hex.Team.Black);
        hex5.setTeam(Hex.Team.Black);
        hex3.setTeam(Hex.Team.Black);
        hex2.setTeam(Hex.Team.White);
        hex10.setTeam(Hex.Team.Black);
        hex9.setOccupiedBy(Hex.gamePieces.Meeple);
        hex8.setOccupiedBy(Hex.gamePieces.Meeple);
        hex6.setOccupiedBy(Hex.gamePieces.Meeple);
        hex5.setOccupiedBy(Hex.gamePieces.Meeple);
        hex3.setOccupiedBy(Hex.gamePieces.Meeple);
        hex2.setOccupiedBy(Hex.gamePieces.Meeple);
        hex10.setOccupiedBy(Hex.gamePieces.Totoro);
        testAPI.gameBoard.setHex(hex1, Orientation.getOrigin());
        testAPI.gameBoard.setHex(hex2, Orientation.downLeftOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex3, Orientation.downRightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex6, Orientation.upRightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex5, Orientation.rightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex4, Orientation.upRightOf(hex5.getLocation()));
        testAPI.gameBoard.setHex(hex8, Orientation.upLeftOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex7, Orientation.leftOf(hex8.getLocation()));
        testAPI.gameBoard.setHex(hex9, Orientation.upRightOf(hex7.getLocation()));
        testAPI.gameBoard.setHex(hex10, Orientation.rightOf(hex9.getLocation()));
        testAPI.gameBoard.setHex(hex11, Orientation.upRightOf(hex9.getLocation()));
        testAPI.gameBoard.setHex(hex12, Orientation.rightOf(hex11.getLocation()));
        testAPI.updateSettlements();
    }

    //TotoroPlacementNotAllowed.feature
    @Given("^there is not settlement of size five or greater without a totoro for the player's team")
    public void there_is_not_settlement_of_size_five_or_greater_for_the_player_s_team() throws Throwable {
        setUpTotoroValidationScenario2();
    }

    @When("^the player selects the option to build a totoro when no valid location exists")
    public void the_player_selects_the_option_to_build_a_totoro_when_no_valid_location_exists() throws Throwable {
        if(testAPI.canSelectBuildTotoro(Hex.Team.Black))
            validHexes = testAPI.validTotoroPlacements(Hex.Team.Black);
        else
            cantPlace = true;

    }

    @Then("^a totoro will not be allowed to be placed on the board")
    public void a_totoro_will_not_be_allowed_to_be_placed_on_the_board() throws Throwable {
        Assert.assertTrue(cantPlace);
    }

    private void setUpTigerValidationScenario1() {
        Hex hex1 = new Hex(1, Terrain.terrainType.Volcano);
        Hex hex2 = new Hex(1, Terrain.terrainType.Grassland);
        Hex hex3 = new Hex(1, Terrain.terrainType.Rocky);
        Hex hex4 = new Hex(2, Terrain.terrainType.Volcano);
        Hex hex5 = new Hex(2, Terrain.terrainType.Grassland);
        Hex hex6 = new Hex(2, Terrain.terrainType.Rocky);
        Hex hex7 = new Hex(3, Terrain.terrainType.Volcano);
        Hex hex8 = new Hex(3, Terrain.terrainType.Grassland);
        Hex hex9 = new Hex(3, Terrain.terrainType.Rocky);
        Hex hex10 = new Hex(4, Terrain.terrainType.Rocky);
        Hex hex11 = new Hex(4, Terrain.terrainType.Grassland);
        Hex hex12 = new Hex(4, Terrain.terrainType.Volcano);
        hex9.setTeam(Hex.Team.Black);
        hex8.setTeam(Hex.Team.White);
        hex6.setTeam(Hex.Team.Black);
        hex3.setTeam(Hex.Team.Black);
        hex2.setTeam(Hex.Team.White);
        hex9.setOccupiedBy(Hex.gamePieces.Meeple);
        hex8.setOccupiedBy(Hex.gamePieces.Meeple);
        hex6.setOccupiedBy(Hex.gamePieces.Meeple);
        hex3.setOccupiedBy(Hex.gamePieces.Meeple);
        hex2.setOccupiedBy(Hex.gamePieces.Meeple);
        testAPI.gameBoard.setHex(hex1, Orientation.getOrigin());
        testAPI.gameBoard.setHex(hex2, Orientation.downLeftOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex3, Orientation.downRightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex6, Orientation.upRightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex5, Orientation.rightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex4, Orientation.upRightOf(hex5.getLocation()));
        testAPI.gameBoard.setHex(hex8, Orientation.upLeftOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex7, Orientation.leftOf(hex8.getLocation()));
        testAPI.gameBoard.setHex(hex9, Orientation.upRightOf(hex7.getLocation()));
        testAPI.gameBoard.setHex(hex10, Orientation.rightOf(hex9.getLocation()));
        testAPI.gameBoard.setHex(hex11, Orientation.upRightOf(hex9.getLocation()));
        testAPI.gameBoard.setHex(hex12, Orientation.rightOf(hex11.getLocation()));
        hex10.setLevel(3);
        hex5.setLevel(3);
        hex11.setLevel(2);
        testAPI.updateSettlements();
    }

    @Given("^there a settlement with an adjacent hex level 3 or higher")
    public void settlement_with_level_3_or_higher_adjacent_hex() throws Throwable {
        setUpTigerValidationScenario1();
    }

    @When("^the player selects the option to build a tiger and a valid location exists")
    public void player_selects_tiger_build_option_valid() throws Throwable {
        if(testAPI.canSelectBuildTiger(Hex.Team.Black))
            validHexes = testAPI.validTigerPlacements(Hex.Team.Black);
        else
            cantPlace = true;
    }

    @Then("^a tiger will be placed on the board in a valid hex")
    public void tiger_placed_in_valid_hex() throws Throwable {
        Tuple placementLocation = validHexes.get(0);
        testAPI.createTigerPlayground(placementLocation, Hex.Team.Black);

        Assert.assertEquals(Hex.gamePieces.Tiger, testAPI.gameBoard.getHex(placementLocation).getOccupiedBy());
        Assert.assertEquals(Hex.Team.Black, testAPI.gameBoard.getHex(placementLocation).getTeam());
        Assert.assertFalse(cantPlace);
    }

    private void setUpTigerValidationScenario2() {
        Hex hex1 = new Hex(1, Terrain.terrainType.Volcano);
        Hex hex2 = new Hex(1, Terrain.terrainType.Grassland);
        Hex hex3 = new Hex(1, Terrain.terrainType.Rocky);
        Hex hex4 = new Hex(2, Terrain.terrainType.Volcano);
        Hex hex5 = new Hex(2, Terrain.terrainType.Grassland);
        Hex hex6 = new Hex(2, Terrain.terrainType.Rocky);
        Hex hex7 = new Hex(3, Terrain.terrainType.Volcano);
        Hex hex8 = new Hex(3, Terrain.terrainType.Grassland);
        Hex hex9 = new Hex(3, Terrain.terrainType.Rocky);
        Hex hex10 = new Hex(4, Terrain.terrainType.Rocky);
        Hex hex11 = new Hex(4, Terrain.terrainType.Grassland);
        Hex hex12 = new Hex(4, Terrain.terrainType.Volcano);
        hex9.setTeam(Hex.Team.Black);
        hex8.setTeam(Hex.Team.White);
        hex6.setTeam(Hex.Team.Black);
        hex3.setTeam(Hex.Team.Black);
        hex5.setTeam(Hex.Team.Black);
        hex2.setTeam(Hex.Team.White);
        hex11.setTeam(Hex.Team.Black);
        hex9.setOccupiedBy(Hex.gamePieces.Meeple);
        hex8.setOccupiedBy(Hex.gamePieces.Meeple);
        hex6.setOccupiedBy(Hex.gamePieces.Meeple);
        hex3.setOccupiedBy(Hex.gamePieces.Meeple);
        hex2.setOccupiedBy(Hex.gamePieces.Meeple);
        hex5.setOccupiedBy(Hex.gamePieces.Tiger);
        hex11.setOccupiedBy(Hex.gamePieces.Tiger);
        testAPI.gameBoard.setHex(hex1, Orientation.getOrigin());
        testAPI.gameBoard.setHex(hex2, Orientation.downLeftOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex3, Orientation.downRightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex6, Orientation.upRightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex5, Orientation.rightOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex4, Orientation.upRightOf(hex5.getLocation()));
        testAPI.gameBoard.setHex(hex8, Orientation.upLeftOf(Orientation.getOrigin()));
        testAPI.gameBoard.setHex(hex7, Orientation.leftOf(hex8.getLocation()));
        testAPI.gameBoard.setHex(hex9, Orientation.upRightOf(hex7.getLocation()));
        testAPI.gameBoard.setHex(hex10, Orientation.rightOf(hex9.getLocation()));
        testAPI.gameBoard.setHex(hex11, Orientation.upRightOf(hex9.getLocation()));
        testAPI.gameBoard.setHex(hex12, Orientation.rightOf(hex11.getLocation()));
        hex10.setLevel(3);
        hex5.setLevel(3);
        hex11.setLevel(2);
        testAPI.updateSettlements();
    }

    @Given("^there a settlement with no adjacent hex level 3 or higher without an existing tiger")
    public void there_a_settlement_with_no_adjacent_hex_level_3_or_higher_without_an_existing_tiger() throws Throwable {
        setUpTigerValidationScenario2();
    }

    @When("^the player selects the option to build a tiger and no valid location exists")
    public void the_player_selects_the_option_to_build_a_tiger_and_no_valid_location_exists() throws Throwable {
        if(testAPI.canSelectBuildTiger(Hex.Team.Black))
            validHexes = testAPI.validTigerPlacements(Hex.Team.Black);
        else
            cantPlace = true;
    }

    @Then("^a tiger will be not placed on the board")
    public void a_tiger_will_be_not_placed_on_the_board_in_a_valid_hex() throws Throwable {
        Assert.assertTrue(cantPlace);
    }


}

