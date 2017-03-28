import org.junit.*;
import javafx.util.Pair;

/**
 * Created by Nicholas on 3/28/2017.
 */
public class TilePlacementTests {
    Board gameBoard;
    Tile testTile;
    GameAPI game = new GameAPI();

    @Before
    public void setUp() throws Exception {

        Terrain.terrainType terrainLeft = Terrain.terrainType.Grassland;
        Terrain.terrainType terrainRight = Terrain.terrainType.Lake;
        Orientation.Orientations leftOrientation = Orientation.Orientations.downLeft;

        testTile = new Tile(1,terrainLeft,terrainRight,leftOrientation);
        gameBoard = new Board();
    }

    @Test
    public void isTileDestinationValidTest_OriginNonEmpty(){
        Pair<Integer, Integer> testCoords = Orientation.getRelativeOriginValue();
        game.placeTile(testTile, testCoords);

        gameBoard.printSectionedBoard();

        Assert.assertEquals("test",true,game.isTileDestinationValid(testTile, testCoords));
        //Assert.assertEquals("Empty Origin, placing tile at Origin. Success.",true, game.isTileDestinationValid(testTile, testCoords));
    }

}
