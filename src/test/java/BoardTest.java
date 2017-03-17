import javafx.util.Pair;
import org.junit.*;

/**
 * Created by Max on 3/16/17.
 */
class BoardTest {
    Board gameBoard;

    @Before
    void boardSetup() {
        gameBoard = new Board();
    }

    @After
    void tearDownBoard() {
        gameBoard = null;
    }

    @Test
    void isEmpty() {
        Assert.assertEquals("Empty Board Test", true, gameBoard.isEmpty());
    }

    @Test
    void setHex() {
        Hex testHex = new Hex(0, Hex.terrainType.Jungle);
        Pair<Integer,Integer> testPair = new Pair(0,0);
        gameBoard.setHex(testHex, testPair);
        Assert.assertEquals("Place Hex Test", false, gameBoard.isEmpty());
    }


}