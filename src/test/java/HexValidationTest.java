import javafx.util.Pair;
import org.junit.After;
import org.junit.*;

/**
 * Created by WIIZZARD on 3/19/2017.
 */
public class HexValidationTest {
    Board gameBoard;

    @Before
    public void setUp() throws Exception {
        gameBoard = new Board();
    }

    @After
    public void tearDown() throws Exception {
        gameBoard = null;
    }

    @Test
    public void existsAdjacentHexSuccessTest() {
        Hex firstHex = new Hex(0, Terrain.terrainType.Jungle);
        Pair<Integer, Integer> hex1Loc = new Pair(0, 0);
        gameBoard.setHex(firstHex, hex1Loc);

        Integer firstHexLocX = firstHex.getLocation().getKey();
        Integer firstHexLocY = firstHex.getLocation().getValue();
        Integer secondHexLocX = firstHexLocX + Orientation.getUpleftValue().getKey();
        Integer secondHexLocY = firstHexLocY + Orientation.getUpleftValue().getValue();

        Pair<Integer, Integer> hex2Loc = new Pair(secondHexLocX, secondHexLocY);


        Assert.assertEquals("existsAdjacentHexSuccessTest", true, HexValidation.existsAdjacentHex(hex2Loc, gameBoard));
    }

    @Test
    public void existsAdjacentHexFailureTest() {
        Hex firstHex = new Hex(0, Terrain.terrainType.Jungle);
        Pair<Integer, Integer> hex1Loc = new Pair(0, 0);
        gameBoard.setHex(firstHex, hex1Loc);

        Integer firstHexLocX = firstHex.getLocation().getKey();
        Integer firstHexLocY = firstHex.getLocation().getValue();
        Integer secondHexLocX = firstHexLocX + Orientation.getUpleftValue().getKey() + Orientation.getUpleftValue().getKey();
        Integer secondHexLocY = firstHexLocY + Orientation.getUpleftValue().getValue() + Orientation.getUpleftValue().getValue();

        Pair<Integer, Integer> hex2Loc = new Pair(secondHexLocX, secondHexLocY);


        Assert.assertEquals("existsAdjacentHexFailureTest", false, HexValidation.existsAdjacentHex(hex2Loc, gameBoard));
    }

    @Test
    public void isValidVolcanoPlacementSuccessTest() {
        Hex firstHex = new Hex(0, Terrain.terrainType.Volcano);
        Pair<Integer, Integer> hex1Loc = new Pair(0, 0);
        gameBoard.setHex(firstHex, hex1Loc);

        Integer secondHexLocX = firstHex.getLocation().getKey();
        Integer secondHexLocY = firstHex.getLocation().getValue();

        Pair<Integer, Integer> hex2Loc = new Pair(secondHexLocX, secondHexLocY);

        Hex secondHex = new Hex(0, Terrain.terrainType.Volcano);


        Assert.assertEquals("isValidVolcanoPlacementSuccessTest", true, HexValidation.isValidVolcanoPlacement(secondHex, hex2Loc, gameBoard));

    }


    @Test
    public void isValidVolcanoPlacementGenericSuccessTest() {
        Hex firstHex = new Hex(0, Terrain.terrainType.Jungle);
        Pair<Integer, Integer> hex1Loc = new Pair(0, 0);
        gameBoard.setHex(firstHex, hex1Loc);

        Integer secondHexLocX = firstHex.getLocation().getKey();
        Integer secondHexLocY = firstHex.getLocation().getValue();

        Pair<Integer, Integer> hex2Loc = new Pair(secondHexLocX, secondHexLocY);

        Hex secondHex = new Hex(0, Terrain.terrainType.Rocky);


        Assert.assertEquals("isValidVolcanoPlacementGenericSuccessTest", true, HexValidation.isValidVolcanoPlacement(secondHex, hex2Loc, gameBoard));

    }
    @Test
    public void isValidVolcanoPlacementGenericTerrainFailureTest() {
        Hex firstHex = new Hex(0, Terrain.terrainType.Jungle);
        Pair<Integer, Integer> hex1Loc = new Pair(0, 0);
        gameBoard.setHex(firstHex, hex1Loc);

        Integer secondHexLocX = firstHex.getLocation().getKey();
        Integer secondHexLocY = firstHex.getLocation().getValue();

        Pair<Integer, Integer> hex2Loc = new Pair<>(secondHexLocX, secondHexLocY);

        Hex secondHex = new Hex(0, Terrain.terrainType.Volcano);


        Assert.assertEquals("isValidVolcanoPlacementGenericTerrainFailureTest", false, HexValidation.isValidVolcanoPlacement(secondHex, hex2Loc, gameBoard));

    }

    @Test
    public void hexGamePiecesGetterTest() {
        Hex hex = new Hex(0, Terrain.terrainType.Jungle);
        hex.setOccupiedBy(Hex.gamePieces.Meeple);

        Assert.assertEquals("OccupiedByValidationSuccess", Hex.gamePieces.Meeple, hex.getOccupiedBy());
    }

    public void setHexesAroundCoordinates(Pair<Integer,Integer> coordinates){
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Rocky), new Pair<>(0,0));

        gameBoard.setHex(new Hex(2, Terrain.terrainType.Grassland), Orientation.addPairByOrientation(coordinates, Orientation.Orientations.left));
        gameBoard.setHex(new Hex(3, Terrain.terrainType.Rocky), Orientation.addPairByOrientation(coordinates, Orientation.Orientations.right));
        gameBoard.setHex(new Hex(2, Terrain.terrainType.Volcano), Orientation.addPairByOrientation(coordinates, Orientation.Orientations.upLeft));
        gameBoard.setHex(new Hex(4, Terrain.terrainType.Jungle), Orientation.addPairByOrientation(coordinates, Orientation.Orientations.upRight));
        gameBoard.setHex(new Hex(2, Terrain.terrainType.Lake), Orientation.addPairByOrientation(coordinates, Orientation.Orientations.downLeft));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Grassland), Orientation.addPairByOrientation(coordinates, Orientation.Orientations.downRight));

    }

    @Test
    public void nukeMeepleValidationSuccessTest() {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Pair<>(4, 0));
        Pair<Integer,Integer> testingLocation = new Pair<>(192,188);
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Pair<>(4, 0));
        gameBoard.getHex(testingLocation).setOccupiedBy(Hex.gamePieces.Meeple);
        gameBoard.getHex(testingLocation).setTeam(Hex.Team.Black);
        setHexesAroundCoordinates(new Pair<>(4, 0));

        gameBoard.getHex(Orientation.addPairByOrientation(testingLocation, Orientation.Orientations.downLeft)).setOccupiedBy(Hex.gamePieces.Meeple);
        gameBoard.getHex(Orientation.addPairByOrientation(testingLocation, Orientation.Orientations.downLeft)).setTeam(Hex.Team.Black);


        Assert.assertEquals("Nuke a meeple that is connected to another meeple of the same team", true, HexValidation.isValidHexEruption(testingLocation, gameBoard));
    }

    @Test
    public void nukeMeepleValidationFailureTest() {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Pair<>(4, 0));
        Pair<Integer,Integer> testingLocation = new Pair<>(192,188);
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Pair<>(4, 0));
        gameBoard.getHex(testingLocation).setOccupiedBy(Hex.gamePieces.Meeple);
        gameBoard.getHex(testingLocation).setTeam(Hex.Team.Black);
        setHexesAroundCoordinates(new Pair<>(4, 0));

        gameBoard.getHex(Orientation.addPairByOrientation(testingLocation, Orientation.Orientations.downLeft)).setOccupiedBy(Hex.gamePieces.Meeple);
        gameBoard.getHex(Orientation.addPairByOrientation(testingLocation, Orientation.Orientations.downLeft)).setTeam(Hex.Team.White);

        gameBoard.printSectionedBoard();

        Assert.assertEquals("Nuke a meeple that is NOT connected to another meeple of the same team", false, HexValidation.isValidHexEruption(testingLocation, gameBoard));
    }

}