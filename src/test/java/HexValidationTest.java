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
    public void adjacencyValidationSuccessTest() {
        Hex firstHex = new Hex(0, Hex.terrainType.Jungle);
        Pair<Integer,Integer> hex1Loc = new Pair(0,0);
        gameBoard.setHex(firstHex, hex1Loc);

        Integer firstHexLocX = firstHex.getLocation().getKey();
        Integer firstHexLocY = firstHex.getLocation().getValue();
        Integer secondHexLocX = firstHexLocX + Orientation.UPLEFT.getKey();
        Integer secondHexLocY = firstHexLocY + Orientation.UPLEFT.getValue();

        Pair<Integer,Integer> hex2Loc = new Pair(secondHexLocX,secondHexLocY);



        Assert.assertEquals("adjacencyValidationSuccessTest", true, HexValidation.adjacencyValidation(hex2Loc));
    }

    @Test
    public void adjacencyValidationFailureTest() {
        Hex firstHex = new Hex(0, Hex.terrainType.Jungle);
        Pair<Integer,Integer> hex1Loc = new Pair(0,0);
        gameBoard.setHex(firstHex, hex1Loc);

        Integer firstHexLocX = firstHex.getLocation().getKey();
        Integer firstHexLocY = firstHex.getLocation().getValue();
        Integer secondHexLocX = firstHexLocX + Orientation.UPLEFT.getKey() + Orientation.UPLEFT.getKey();
        Integer secondHexLocY = firstHexLocY + Orientation.UPLEFT.getValue() + Orientation.UPLEFT.getValue();

        Pair<Integer,Integer> hex2Loc = new Pair(secondHexLocX,secondHexLocY);



        Assert.assertEquals("adjacencyValidationFailureTest", false, HexValidation.adjacencyValidation(hex2Loc));
    }

}