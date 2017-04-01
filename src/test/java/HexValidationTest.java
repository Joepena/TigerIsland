import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.util.Pair;
import org.junit.After;
import org.junit.*;

/**
 * Created by Nicholas on 3/19/2017.
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
        Tuple hex1Loc = Orientation.getOrigin();
        gameBoard.setHex(firstHex, hex1Loc);
        Tuple hex2Loc = Orientation.upLeftOf(hex1Loc);


        Assert.assertEquals("existsAdjacentHexSuccessTest", true, HexValidation.existsAdjacentHex(hex2Loc, gameBoard));
    }

    @Test
    public void existsAdjacentHexFailureTest() {
        Hex firstHex = new Hex(0, Terrain.terrainType.Jungle);
        Tuple hex1Loc = Orientation.getOrigin();
        gameBoard.setHex(firstHex, hex1Loc);
        Tuple hex2Loc = Orientation.upLeftOf(hex1Loc);


        Assert.assertEquals("existsAdjacentHexFailureTest", false, HexValidation.existsAdjacentHex(hex2Loc, gameBoard));
    }

    @Test
    public void isValidVolcanoPlacementSuccessTest() {
        Hex firstHex = new Hex(0, Terrain.terrainType.Volcano);
        gameBoard.setHex(firstHex, Orientation.getOrigin());

        Tuple hex2Loc = Orientation.getOrigin();

        Assert.assertEquals("isValidVolcanoPlacementSuccessTest", true, HexValidation.isValidVolcanoPlacement(hex2Loc, gameBoard));

    }

    @Test
    public void isValidVolcanoPlacementGenericTerrainFailureTest() {
        Hex firstHex = new Hex(0, Terrain.terrainType.Jungle);
        gameBoard.setHex(firstHex, Orientation.getOrigin());

        Tuple hex2Loc = Orientation.getOrigin();

        Assert.assertEquals("isValidVolcanoPlacementGenericTerrainFailureTest", false, HexValidation.isValidVolcanoPlacement(hex2Loc, gameBoard));

    }

    @Test
    public void hexGamePiecesGetterTest() {
        Hex hex = new Hex(0, Terrain.terrainType.Jungle);
        hex.setOccupiedBy(Hex.gamePieces.Meeple);

        Assert.assertEquals("OccupiedByValidationSuccess", Hex.gamePieces.Meeple, hex.getOccupiedBy());
    }

    @Test
    public void nukeMeepleValidationSuccessTest() {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin())));
        Tuple testingLocation =Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin()));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin())));
        gameBoard.getHex(testingLocation).setOccupiedBy(Hex.gamePieces.Meeple);
        gameBoard.getHex(testingLocation).setTeam(Hex.Team.Black);
        setHexesAroundCoordinates(Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin())));

        gameBoard.getHex(Orientation.addCoordinatesByOrientation(testingLocation, Orientation.Orientations.downLeft)).setOccupiedBy(Hex.gamePieces.Meeple);
        gameBoard.getHex(Orientation.addCoordinatesByOrientation(testingLocation, Orientation.Orientations.downLeft)).setTeam(Hex.Team.Black);


        Assert.assertEquals("Nuke a meeple that is connected to another meeple of the same team", true, HexValidation.isValidHexEruption(testingLocation, gameBoard));
    }

  public void setHexesAroundCoordinates(Tuple coordinates){
    gameBoard.setHex(new Hex(1, Terrain.terrainType.Rocky), Orientation.getOrigin());

    gameBoard.setHex(new Hex(2, Terrain.terrainType.Grassland), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.left));
    gameBoard.setHex(new Hex(3, Terrain.terrainType.Rocky), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.right));
    gameBoard.setHex(new Hex(2, Terrain.terrainType.Volcano), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.upLeft));
    gameBoard.setHex(new Hex(4, Terrain.terrainType.Jungle), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.upRight));
    gameBoard.setHex(new Hex(2, Terrain.terrainType.Lake), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.downLeft));
    gameBoard.setHex(new Hex(1, Terrain.terrainType.Grassland), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.downRight));

  }

    @Test
    public void nukeMeepleValidationFailureTest() {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin())));
        Tuple testingLocation = Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin()));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin())));
        gameBoard.getHex(testingLocation).setOccupiedBy(Hex.gamePieces.Meeple);
        gameBoard.getHex(testingLocation).setTeam(Hex.Team.Black);
        setHexesAroundCoordinates(Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin())));

        gameBoard.getHex(Orientation.addCoordinatesByOrientation(testingLocation, Orientation.Orientations.downLeft)).setOccupiedBy(Hex.gamePieces.Meeple);
        gameBoard.getHex(Orientation.addCoordinatesByOrientation(testingLocation, Orientation.Orientations.downLeft)).setTeam(Hex.Team.White);

        Assert.assertEquals("Nuke a meeple that is NOT connected to another meeple of the same team", false, HexValidation.isValidHexEruption(testingLocation, gameBoard));
    }

}
