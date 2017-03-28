import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by TomasK on 3/27/2017.
 */
public class GameAPITest {
    Tile testTile;
    GameAPI game = new GameAPI();

    @Before
    public void setUp() throws Exception {

        Tile tile0 = new Tile(0, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.downLeft);
        Tile tile1 = new Tile(1, Terrain.terrainType.Jungle, Terrain.terrainType.Grassland, Orientation.Orientations.downLeft);
        game.placeTile(tile0, Orientation.getOriginValue());



    }

    @Test
    public void getRight() throws Exception {
        Assert.assertEquals("verify getRight", testTile.getRight().getTerrain(), Terrain.terrainType.Lake);

    }

    @Test
    public void firstTileForcedOriginTest(){
        Pair<Integer, Integer> testCoords = new Pair<>(5,5);
        game.placeTile(testTile, testCoords);
        Hex originHex = testTile.getVolcano();

        Assert.assertEquals("test",originHex, game.gameBoard.getHex(Orientation.getOriginValue()));
    }
}
