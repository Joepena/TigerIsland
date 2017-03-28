import org.junit.*;

/**
 * Created by Nicholas on 3/28/2017.
 */
public class TilePlacementTests {
    Board gameBoard;
    Tile testTile;

    @Before
    public void setUp() throws Exception {
        Terrain.terrainType terrainLeft = Terrain.terrainType.Grassland;
        Terrain.terrainType terrainRight = Terrain.terrainType.Lake;
        Orientation.Orientations leftOrientation = Orientation.Orientations.downLeft;

        testTile = new Tile(1,terrainLeft,terrainRight,leftOrientation);
        gameBoard = new Board();
    }

    @Test
    public void checkTileConnectedTest(){

    }

}
