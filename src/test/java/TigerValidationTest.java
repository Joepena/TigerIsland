import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Max on 4/4/17.
 */
public class TigerValidationTest {
    GameAPI gameAPI;
    ArrayList<Tuple> expectedResults = new ArrayList<>();
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

    @Before
    public void setUp() throws Exception {
        gameAPI = new GameAPI();
    }

    @Test
    public void tigerValidation () throws Exception {
        setUpTigerValidationScenario1();
        gameAPI.updateSettlements();

        ArrayList<Tuple> validLocations = gameAPI.validTigerPlacements();

        Assert.assertEquals(expectedResults, validLocations);
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
}
