import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Max on 4/4/17.
 */
public class TigerValidationTest {
    GameAPI gameAPI;
    ArrayList<Tuple> validLocations = new ArrayList<>();
    ArrayList<Tuple> expectedResults = new ArrayList<>();
    Hex.Team testTeam = Hex.Team.Black;
    Hex hex1;
    Hex hex2;
    Hex hex3;
    Hex hex4;
    Hex hex5;
    Hex hex6;
    Hex hex7;
    Hex hex8;
    Hex hex9;
    Hex hex10;
    Hex hex11;
    Hex hex12;
    Hex hex13;
    Hex hex14;
    Hex hex15;
    Hex hex16;
    Hex hex17;
    Hex hex18;
    Hex hex19;
    Hex hex20;
    Hex hex21;

    @Before
    public void setUp() throws Exception {
        gameAPI = new GameAPI();
    }

    @Test
    public void canSelectBuildTigerTestPass() throws Exception {
        setUpTigerValidationScenario1();
        gameAPI.updateSettlements();
        boolean pass = gameAPI.canSelectBuildTiger(testTeam);

        Assert.assertEquals(true, pass);
    }

    @Test
    public void canSelectBuildTigerTestFail() throws Exception {
        setUpTigerValidationScenario1();
        hex10.setLevel(2);
        hex5.setLevel(2);
        gameAPI.updateSettlements();
        boolean pass = gameAPI.canSelectBuildTiger(testTeam);

        Assert.assertEquals(false, pass);
    }

    @Test
    public void tigerValidationRegular () throws Exception {
        setUpTigerValidationScenario1();
        gameAPI.updateSettlements();

        validLocations = gameAPI.validTigerPlacements(testTeam);

        Assert.assertTrue(validLocations.containsAll(expectedResults) && expectedResults.containsAll(validLocations));
    }

    @Test
    public void tigerValidationWithExistingTiger () throws Exception {
        setUpTigerValidationScenario2();
        gameAPI.updateSettlements();

        validLocations = gameAPI.validTigerPlacements(testTeam);

        Assert.assertTrue(validLocations.containsAll(expectedResults) && expectedResults.containsAll(validLocations));
    }

    @Test
    public void tigerHugeTest () throws Exception {
        setUpTigerValidationScenario3();
        gameAPI.updateSettlements();

        validLocations = gameAPI.validTigerPlacements(testTeam);

        Assert.assertTrue(validLocations.containsAll(expectedResults) && expectedResults.containsAll(validLocations));
    }



    private void setUpTigerValidationScenario1() {
        hex1 = new Hex(1, Terrain.terrainType.Volcano);
        hex2 = new Hex(1, Terrain.terrainType.Grassland);
        hex3 = new Hex(1, Terrain.terrainType.Rocky);

        hex4 = new Hex(2, Terrain.terrainType.Volcano);
        hex5 = new Hex(2, Terrain.terrainType.Grassland);
        hex6 = new Hex(2, Terrain.terrainType.Rocky);

        hex7 = new Hex(3, Terrain.terrainType.Volcano);
        hex8 = new Hex(3, Terrain.terrainType.Grassland);
        hex9 = new Hex(3, Terrain.terrainType.Rocky);

        hex10 = new Hex(4, Terrain.terrainType.Rocky);
        hex11 = new Hex(4, Terrain.terrainType.Grassland);
        hex12 = new Hex(4, Terrain.terrainType.Volcano);

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


        gameAPI.gameBoard.setHex(hex1, Orientation.getOrigin());
        gameAPI.gameBoard.setHex(hex2, Orientation.downLeftOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex3, Orientation.downRightOf(Orientation.getOrigin()));

        gameAPI.gameBoard.setHex(hex6, Orientation.upRightOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex5, Orientation.rightOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex4, Orientation.upRightOf(hex5.getLocation()));

        gameAPI.gameBoard.setHex(hex8, Orientation.upLeftOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex7, Orientation.leftOf(hex8.getLocation()));
        gameAPI.gameBoard.setHex(hex9, Orientation.upRightOf(hex7.getLocation()));

        gameAPI.gameBoard.setHex(hex10, Orientation.rightOf(hex9.getLocation()));
        gameAPI.gameBoard.setHex(hex11, Orientation.upRightOf(hex9.getLocation()));
        gameAPI.gameBoard.setHex(hex12, Orientation.rightOf(hex11.getLocation()));

        hex10.setLevel(3);
        hex5.setLevel(3);
        hex11.setLevel(2);

        expectedResults.add(hex5.getLocation());
        expectedResults.add(hex10.getLocation());

    }

    private void setUpTigerValidationScenario2() {
        hex1 = new Hex(1, Terrain.terrainType.Volcano);
        hex2 = new Hex(1, Terrain.terrainType.Grassland);
        hex3 = new Hex(1, Terrain.terrainType.Rocky);

        hex4 = new Hex(2, Terrain.terrainType.Volcano);
        hex5 = new Hex(2, Terrain.terrainType.Grassland);
        hex6 = new Hex(2, Terrain.terrainType.Rocky);

        hex7 = new Hex(3, Terrain.terrainType.Volcano);
        hex8 = new Hex(3, Terrain.terrainType.Grassland);
        hex9 = new Hex(3, Terrain.terrainType.Rocky);

        hex10 = new Hex(4, Terrain.terrainType.Rocky);
        hex11 = new Hex(4, Terrain.terrainType.Grassland);
        hex12 = new Hex(4, Terrain.terrainType.Volcano);

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

        gameAPI.gameBoard.setHex(hex1, Orientation.getOrigin());
        gameAPI.gameBoard.setHex(hex2, Orientation.downLeftOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex3, Orientation.downRightOf(Orientation.getOrigin()));

        gameAPI.gameBoard.setHex(hex6, Orientation.upRightOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex5, Orientation.rightOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex4, Orientation.upRightOf(hex5.getLocation()));

        gameAPI.gameBoard.setHex(hex8, Orientation.upLeftOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex7, Orientation.leftOf(hex8.getLocation()));
        gameAPI.gameBoard.setHex(hex9, Orientation.upRightOf(hex7.getLocation()));

        gameAPI.gameBoard.setHex(hex10, Orientation.rightOf(hex9.getLocation()));
        gameAPI.gameBoard.setHex(hex11, Orientation.upRightOf(hex9.getLocation()));
        gameAPI.gameBoard.setHex(hex12, Orientation.rightOf(hex11.getLocation()));

        hex10.setLevel(3);
        hex5.setLevel(3);
        hex11.setLevel(2);

    }

    private void setUpTigerValidationScenario3() {
        hex1 = new Hex(6, Terrain.terrainType.Volcano);
        hex2 = new Hex(1, Terrain.terrainType.Grassland);
        hex3 = new Hex(1, Terrain.terrainType.Rocky);

        hex4 = new Hex(2, Terrain.terrainType.Volcano);
        hex5 = new Hex(2, Terrain.terrainType.Grassland);
        hex6 = new Hex(2, Terrain.terrainType.Rocky);

        hex7 = new Hex(3, Terrain.terrainType.Volcano);
        hex8 = new Hex(6, Terrain.terrainType.Grassland);
        hex9 = new Hex(3, Terrain.terrainType.Rocky);

        hex10 = new Hex(4, Terrain.terrainType.Rocky);
        hex11 = new Hex(4, Terrain.terrainType.Grassland);
        hex12 = new Hex(4, Terrain.terrainType.Volcano);

        hex13 = new Hex(7, Terrain.terrainType.Rocky);
        hex14 = new Hex(5, Terrain.terrainType.Rocky);
        hex15 = new Hex(5, Terrain.terrainType.Volcano);

        hex16 = new Hex(5, Terrain.terrainType.Grassland);
        hex17 = new Hex(5, Terrain.terrainType.Rocky);
        hex18 = new Hex(5, Terrain.terrainType.Volcano);

        hex19 = new Hex(7, Terrain.terrainType.Rocky);
        hex20 = new Hex(7, Terrain.terrainType.Rocky);
        hex21 = new Hex(6, Terrain.terrainType.Rocky);

        hex9.setOccupiedBy(Hex.gamePieces.Meeple);
        hex16.setOccupiedBy(Hex.gamePieces.Meeple);
        hex11.setOccupiedBy(Hex.gamePieces.Meeple);
        hex2.setOccupiedBy(Hex.gamePieces.Meeple);
        hex20.setOccupiedBy(Hex.gamePieces.Meeple);
        hex13.setOccupiedBy(Hex.gamePieces.Meeple);
        hex17.setOccupiedBy(Hex.gamePieces.Tiger);

        hex9.setTeam(Hex.Team.White);
        hex16.setTeam(Hex.Team.Black);
        hex11.setTeam(Hex.Team.Black);
        hex2.setTeam(Hex.Team.Black);
        hex13.setTeam(Hex.Team.Black);
        hex20.setTeam(Hex.Team.Black);
        hex17.setTeam(Hex.Team.Black);

        gameAPI.gameBoard.setHex(hex1, Orientation.getOrigin());
        gameAPI.gameBoard.setHex(hex2, Orientation.downLeftOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex3, Orientation.downRightOf(Orientation.getOrigin()));

        gameAPI.gameBoard.setHex(hex6, Orientation.upRightOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex5, Orientation.rightOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex4, Orientation.upRightOf(hex5.getLocation()));

        gameAPI.gameBoard.setHex(hex8, Orientation.upLeftOf(Orientation.getOrigin()));
        gameAPI.gameBoard.setHex(hex7, Orientation.leftOf(hex8.getLocation()));
        gameAPI.gameBoard.setHex(hex9, Orientation.upRightOf(hex7.getLocation()));

        gameAPI.gameBoard.setHex(hex10, Orientation.rightOf(hex9.getLocation()));
        gameAPI.gameBoard.setHex(hex11, Orientation.upRightOf(hex9.getLocation()));
        gameAPI.gameBoard.setHex(hex12, Orientation.rightOf(hex11.getLocation()));

        gameAPI.gameBoard.setHex(hex13, Orientation.rightOf(hex5.getLocation()));
        gameAPI.gameBoard.setHex(hex14, Orientation.rightOf(hex3.getLocation()));
        gameAPI.gameBoard.setHex(hex15, Orientation.rightOf(hex14.getLocation()));

        gameAPI.gameBoard.setHex(hex16, Orientation.rightOf(hex10.getLocation()));
        gameAPI.gameBoard.setHex(hex17, Orientation.rightOf(hex12.getLocation()));
        gameAPI.gameBoard.setHex(hex18, Orientation.rightOf(hex16.getLocation()));

        gameAPI.gameBoard.setHex(hex19, Orientation.rightOf(hex4.getLocation()));
        gameAPI.gameBoard.setHex(hex20, Orientation.rightOf(hex13.getLocation()));
        gameAPI.gameBoard.setHex(hex21, Orientation.leftOf(hex1.getLocation()));

        hex17.setLevel(3);
        hex10.setLevel(3);
        hex6.setLevel(3);
        hex21.setLevel(3);
        hex14.setLevel(3);
        hex19.setLevel(3);

        expectedResults.add(hex19.getLocation());
        expectedResults.add(hex10.getLocation());
        expectedResults.add(hex21.getLocation());
        expectedResults.add(hex14.getLocation());
    }
}
