import javafx.util.Pair;
import org.junit.*;

/**
 * Created by Max on 3/16/17.
 */
public class BoardTest {
    Board gameBoard;
    Hex testHex;

    @Before
    public void boardSetup() {
        gameBoard = new Board();
        testHex = new Hex(0, Hex.terrainType.Jungle);
    }

    @After
    public void tearDownBoard() {
        gameBoard = null;
        testHex = null;
    }

    @Test
    public void createHexObject() {
        Assert.assertTrue(testHex instanceof Hex);
    }

    @Test
    public void createBoard() {
        Assert.assertTrue(gameBoard instanceof Board);
    }

    @Test
    public void validateTerrain() {
        Assert.assertEquals(Hex.terrainType.Jungle, testHex.getTerrain());
    }

    @Test
    public void isEmpty() {
        Assert.assertEquals("Empty Board Test", true, gameBoard.isEmpty());
    }

    @Test
    public void setHex() {
        Pair<Integer,Integer> testPair = new Pair(0,0);
        gameBoard.setHex(testHex, testPair);
        Assert.assertEquals("Place Hex Test", false, gameBoard.isEmpty());
    }


}
