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
        Pair<Integer, Integer> originCoords = Orientation.getOriginValue();

        Assert.assertEquals("test",true,game.isTileDestinationValid(testTile, originCoords));
    }

    @Test
    public void isTileDestinationValidTest_OriginNotEmpty(){
        Pair<Integer, Integer> originCoords = Orientation.getOriginValue();
        game.placeTile(existingTile, originCoords);

        Assert.assertEquals("test",true,game.isTileDestinationValid(testTile, originCoords));
    }

    @Test
    public void isTileDestinationValidTest_VolcanoConnected(){
        Pair<Integer, Integer> originCoords = Orientation.getOriginValue();
        game.placeTile(existingTile, originCoords);

        Pair<Integer, Integer> testCoords = Orientation.addPairByOrientation(originCoords, Orientation.Orientations.upRight);
        testCoords = Orientation.addPairByOrientation(testCoords, Orientation.Orientations.right);

        Assert.assertEquals("test",true,game.isTileDestinationValid(testTile, testCoords));

    }

    @Test
    public void isTileDestinationValidTest_VolcanoNotConnected(){
        Pair<Integer, Integer> originCoords = Orientation.getOriginValue();
        game.placeTile(existingTile, originCoords);

        Pair<Integer, Integer> testCoords = Orientation.addPairByOrientation(originCoords, Orientation.Orientations.upRight);
        testCoords = Orientation.addPairByOrientation(testCoords, Orientation.Orientations.upRight);

        Assert.assertEquals("test",true,game.isTileDestinationValid(testTile, testCoords));

    }

    @Test
    public void isTileDestinationValidTest_NoHexConnected(){
        Pair<Integer, Integer> originCoords = Orientation.getOriginValue();
        game.placeTile(existingTile, originCoords);

        Pair<Integer, Integer> testCoords = Orientation.addPairByOrientation(originCoords, Orientation.Orientations.downLeft);
        testCoords = Orientation.addPairByOrientation(testCoords, Orientation.Orientations.downRight);
        testCoords = Orientation.addPairByOrientation(testCoords, Orientation.Orientations.downRight);

        Assert.assertEquals("test",false,game.isTileDestinationValid(testTile, testCoords));

    }



}
