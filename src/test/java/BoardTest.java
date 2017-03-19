import javafx.util.Pair;
import org.junit.*;

/**
 * Created by Max on 3/16/17.
 */
public class BoardTest {
    Board gameBoard;

    @Before
    public void boardSetup() {
        gameBoard = new Board();
    }

    @After
    public void tearDownBoard() {
        gameBoard = null;
    }

    @Test
    public void isEmpty() {
        Assert.assertEquals("Empty Board Test", true, gameBoard.isEmpty());
    }

    @Test
    public void setHex() {
        Hex testHex = new Hex(0, Terrain.terrainType.Jungle);
        Pair<Integer,Integer> testPair = new Pair(0,0);
        gameBoard.setHex(testHex, testPair);
        Assert.assertEquals("Place Hex Test", false, gameBoard.isEmpty());
    }


}
