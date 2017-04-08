import java.util.Iterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Max on 4/1/17.
 */

public class TotoroValidationTest {
    GameAPI gameAPI;
    ArrayList<Tuple> expectedResults = new ArrayList<>();
    ArrayList<Tuple> validLocations = new ArrayList<>();
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
    public void totoroValidationRegular() throws Exception {
        setUpTotoroValidationScenario1();
        gameAPI.updateSettlements();

        validLocations = gameAPI.validTotoroPlacements();

        Assert.assertEquals(expectedResults, validLocations);
    }

    @Test
    public void totoroValidationWithExistingTotoro() throws Exception {
        setUpTotoroValidationScenario2();
        gameAPI.updateSettlements();
        Iterator<SettlementDataFrame> it = gameAPI.getBlackSettlements().getListOfSettlements().iterator();
        it.forEachRemaining((df) -> System.out.println("size: "+df.getListOfHexLocations().size()));
        validLocations = gameAPI.validTotoroPlacements();

        Assert.assertEquals(expectedResults, validLocations);
    }

//    @Test
//    public void totoroValidationHugeTest() throws Exception {
//        setUpTotoroValidationScenario3();
//        gameAPI.updateSettlements();
//
//        validLocations = gameAPI.validTotoroPlacements();
//
//        Assert.assertEquals(expectedResults, validLocations);
//
//    }


    @Test
    public void canSelectBuildTotoroTestPass() throws Exception {
        setUpTotoroValidationScenario1();
        gameAPI.updateSettlements();
        boolean pass = gameAPI.canSelectBuildTotoro();

        Assert.assertEquals(true, pass);
    }

    @Test
    public void canSelectBuildTotoroTestFail() throws Exception {
        setUpTotoroValidationScenario1();
        gameAPI.gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin())).setTeam(Hex.Team.White);
        gameAPI.updateSettlements();

        boolean pass = gameAPI.canSelectBuildTotoro();

        Assert.assertEquals(false, pass);
    }

    private void setUpTotoroValidationScenario1() {
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
        hex8.setTeam(Hex.Team.Black);
        hex6.setTeam(Hex.Team.Black);
        hex5.setTeam(Hex.Team.Black);
        hex3.setTeam(Hex.Team.Black);
        hex2.setTeam(Hex.Team.White);

        hex9.setOccupiedBy(Hex.gamePieces.Meeple);
        hex8.setOccupiedBy(Hex.gamePieces.Meeple);
        hex6.setOccupiedBy(Hex.gamePieces.Meeple);
        hex5.setOccupiedBy(Hex.gamePieces.Meeple);
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

        expectedResults.add(hex10.getLocation());
        expectedResults.add(hex11.getLocation());

    }

    private void setUpTotoroValidationScenario2() {
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

    }

//    private void setUpTotoroValidationScenario3() {
//        hex1 = new Hex(6, Terrain.terrainType.Volcano);
//        hex2 = new Hex(1, Terrain.terrainType.Grassland);
//        hex3 = new Hex(1, Terrain.terrainType.Rocky);
//
//        hex4 = new Hex(2, Terrain.terrainType.Volcano);
//        hex5 = new Hex(2, Terrain.terrainType.Grassland);
//        hex6 = new Hex(2, Terrain.terrainType.Rocky);
//
//        hex7 = new Hex(3, Terrain.terrainType.Volcano);
//        hex8 = new Hex(6, Terrain.terrainType.Grassland);
//        hex9 = new Hex(3, Terrain.terrainType.Rocky);
//
//        hex10 = new Hex(4, Terrain.terrainType.Rocky);
//        hex11 = new Hex(4, Terrain.terrainType.Grassland);
//        hex12 = new Hex(4, Terrain.terrainType.Volcano);
//
//        hex13 = new Hex(7, Terrain.terrainType.Rocky);
//        hex14 = new Hex(5, Terrain.terrainType.Rocky);
//        hex15 = new Hex(5, Terrain.terrainType.Volcano);
//
//        hex16 = new Hex(5, Terrain.terrainType.Grassland);
//        hex17 = new Hex(5, Terrain.terrainType.Rocky);
//        hex18 = new Hex(5, Terrain.terrainType.Volcano);
//
//        hex19 = new Hex(7, Terrain.terrainType.Rocky);
//        hex20 = new Hex(7, Terrain.terrainType.Rocky);
//        hex21 = new Hex(6, Terrain.terrainType.Rocky);
//
//        hex9.setOccupiedBy(Hex.gamePieces.Meeple);
//        hex10.setOccupiedBy(Hex.gamePieces.Meeple);
//        hex16.setOccupiedBy(Hex.gamePieces.Meeple);
//        hex6.setOccupiedBy(Hex.gamePieces.Meeple);
//        hex8.setOccupiedBy(Hex.gamePieces.Meeple);
//        hex2.setOccupiedBy(Hex.gamePieces.Meeple);
//        hex3.setOccupiedBy(Hex.gamePieces.Meeple);
//        hex14.setOccupiedBy(Hex.gamePieces.Meeple);
//        hex13.setOccupiedBy(Hex.gamePieces.Meeple);
//        hex5.setOccupiedBy(Hex.gamePieces.Meeple);
//        hex17.setOccupiedBy(Hex.gamePieces.Totoro);
//
//        hex9.setTeam(Hex.Team.Black);
//        hex10.setTeam(Hex.Team.Black);
//        hex16.setTeam(Hex.Team.Black);
//        hex6.setTeam(Hex.Team.Black);
//        hex8.setTeam(Hex.Team.Black);
//        hex2.setTeam(Hex.Team.Black);
//        hex3.setTeam(Hex.Team.Black);
//        hex14.setTeam(Hex.Team.Black);
//        hex13.setTeam(Hex.Team.Black);
//        hex5.setTeam(Hex.Team.Black);
//        hex17.setTeam(Hex.Team.Black);
//
//        gameAPI.gameBoard.setHex(hex1, Orientation.getOrigin());
//        gameAPI.gameBoard.setHex(hex2, Orientation.downLeftOf(Orientation.getOrigin()));
//        gameAPI.gameBoard.setHex(hex3, Orientation.downRightOf(Orientation.getOrigin()));
//
//        gameAPI.gameBoard.setHex(hex6, Orientation.upRightOf(Orientation.getOrigin()));
//        gameAPI.gameBoard.setHex(hex5, Orientation.rightOf(Orientation.getOrigin()));
//        gameAPI.gameBoard.setHex(hex4, Orientation.upRightOf(hex5.getLocation()));
//
//        gameAPI.gameBoard.setHex(hex8, Orientation.upLeftOf(Orientation.getOrigin()));
//        gameAPI.gameBoard.setHex(hex7, Orientation.leftOf(hex8.getLocation()));
//        gameAPI.gameBoard.setHex(hex9, Orientation.upRightOf(hex7.getLocation()));
//
//        gameAPI.gameBoard.setHex(hex10, Orientation.rightOf(hex9.getLocation()));
//        gameAPI.gameBoard.setHex(hex11, Orientation.upRightOf(hex9.getLocation()));
//        gameAPI.gameBoard.setHex(hex12, Orientation.rightOf(hex11.getLocation()));
//
//        gameAPI.gameBoard.setHex(hex13, Orientation.rightOf(hex5.getLocation()));
//        gameAPI.gameBoard.setHex(hex14, Orientation.rightOf(hex3.getLocation()));
//        gameAPI.gameBoard.setHex(hex15, Orientation.rightOf(hex14.getLocation()));
//
//        gameAPI.gameBoard.setHex(hex16, Orientation.rightOf(hex10.getLocation()));
//        gameAPI.gameBoard.setHex(hex17, Orientation.rightOf(hex12.getLocation()));
//        gameAPI.gameBoard.setHex(hex18, Orientation.rightOf(hex16.getLocation()));
//
//        gameAPI.gameBoard.setHex(hex19, Orientation.rightOf(hex4.getLocation()));
//        gameAPI.gameBoard.setHex(hex20, Orientation.rightOf(hex13.getLocation()));
//        gameAPI.gameBoard.setHex(hex21, Orientation.leftOf(hex1.getLocation()));
//
//        expectedResults.add(hex19.getLocation());
//        expectedResults.add(hex20.getLocation());
//
//    }

}
