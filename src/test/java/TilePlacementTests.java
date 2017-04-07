
import org.junit.*;
import javafx.util.Pair;

/**
 * Created by Nicholas on 3/28/2017.
 */
public class TilePlacementTests {
    Tile testTile;
    Tile existingTile;
    GameAPI game = new GameAPI();

    @Before
    public void setUp() throws Exception {

        existingTile = new Tile(1,Terrain.terrainType.Jungle,Terrain.terrainType.Rocky,Orientation.Orientations.upRight);
        testTile = new Tile(2,Terrain.terrainType.Grassland,Terrain.terrainType.Lake,Orientation.Orientations.downLeft);
    }

    @Test
    public void isTileDestinationValidTest_OriginEmpty(){
        Tuple originCoords = Orientation.getOrigin();

        Assert.assertEquals("test",true,game.APIUtils.isTileDestinationValid(testTile, originCoords));
    }

    @Test
    public void isTileDestinationValidTest_OriginNotEmpty(){
        Tuple originCoords = Orientation.getOrigin();
        game.placeTile(existingTile, originCoords);

        Assert.assertEquals("test",true,game.APIUtils.isTileDestinationValid(testTile, originCoords));
    }

    @Test
    public void isTileDestinationValidTest_VolcanoConnected(){
        Tuple originCoords = Orientation.getOrigin();
        Tile tempTile = new Tile(0,Terrain.terrainType.Jungle,Terrain.terrainType.Rocky,Orientation.Orientations.upRight);
        game.placeTile(tempTile, originCoords);
        game.gameBoard.printSectionedBoard(game.gameBoard);

        Tuple testCoords = Orientation.addCoordinatesByOrientation(originCoords, Orientation.Orientations.upRight);
        testCoords = Orientation.addCoordinatesByOrientation(testCoords, Orientation.Orientations.right);

        game.placeTile(testTile, testCoords);
        game.gameBoard.printSectionedBoard(game.gameBoard);

        Assert.assertEquals("test",true,game.APIUtils.isTileDestinationValid(testTile, testCoords));

    }

    @Test
    public void isTileDestinationValidTest_VolcanoNotConnected(){
        Tuple originCoords = Orientation.getOrigin();
        game.placeTile(existingTile, originCoords);

        Tuple testCoords = Orientation.addCoordinatesByOrientation(originCoords, Orientation.Orientations.upRight);
        testCoords = Orientation.addCoordinatesByOrientation(testCoords, Orientation.Orientations.upRight);

        Assert.assertEquals("test",true,game.APIUtils.isTileDestinationValid(testTile, testCoords));

    }

    @Test
    public void isTileDestinationValidTest_NoHexConnected(){
        Tuple originCoords = Orientation.getOrigin();
        game.placeTile(existingTile, originCoords);

        Tuple testCoords = Orientation.addCoordinatesByOrientation(originCoords, Orientation.Orientations.downLeft);
        testCoords = Orientation.addCoordinatesByOrientation(testCoords, Orientation.Orientations.downRight);
        testCoords = Orientation.addCoordinatesByOrientation(testCoords, Orientation.Orientations.downRight);

        Assert.assertEquals("test",false,game.APIUtils.isTileDestinationValid(testTile, testCoords));

    }



}
