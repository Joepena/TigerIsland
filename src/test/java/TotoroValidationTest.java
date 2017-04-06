import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Max on 4/1/17.
 */

public class TotoroValidationTest {
    GameAPI gameAPI;

    @Before
    public void setUp() throws Exception {
        gameAPI = new GameAPI();
    }

    @Test
    public void totoroValidation() {
        setUpTotoroValidationScenario1();
        gameAPI.updateSettlements();

    }


    @Test
    public void canSelectBuildTotoroTestPass() {
        setUpTotoroValidationScenario1();
        gameAPI.updateSettlements();
        boolean pass = gameAPI.canSelectBuildTotoro();

        Assert.assertEquals(true, pass);
    }

    @Test
    public void canSelectBuildTotoroTestFail() {
        setUpTotoroValidationScenario1();
        gameAPI.gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin())).setTeam(Hex.Team.White);
        gameAPI.updateSettlements();
        boolean pass = gameAPI.canSelectBuildTotoro();

        Assert.assertEquals(false, pass);
    }





    private void setUpTotoroValidationScenario1() {
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

    }






}
