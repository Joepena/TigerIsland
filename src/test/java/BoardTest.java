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
        testHex = new Hex(0, Terrain.terrainType.Jungle);
    }

    @After
    public void tearDownBoard() {
        gameBoard = null;
        testHex = null;
    }

    @Test
    public void getTileIdTest() { Assert.assertEquals(0, testHex.getTileId());}

    @Test
    public void getLevelOfUnplacedHexTest() { Assert.assertEquals(0, testHex.getLevel());}

    @Test
    public void getDefaultTeamTest() { Assert.assertEquals(Hex.Team.Neutral, testHex.getTeam());}

    @Test
    public void getOccupiedByNothingTest() { Assert.assertEquals(Hex.gamePieces.empty, testHex.getOccupiedBy());}

    @Test
    public void getOccupiedByGamePieceTest() {
        testHex.setOccupiedBy(Hex.gamePieces.Meeple);
        Assert.assertEquals(Hex.gamePieces.Meeple, testHex.getOccupiedBy());
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
        Assert.assertEquals(Terrain.terrainType.Jungle, testHex.getTerrain());
    }

    @Test
    public void isEmpty() {
        Assert.assertEquals("Empty Board Test", true, gameBoard.isOriginEmpty());
    }

    @Test
    public void setHex() {
        Pair<Integer,Integer> testPair = new Pair(0,0);
        gameBoard.setHex(testHex, testPair);
        Assert.assertEquals("Place Hex Test", false, gameBoard.isOriginEmpty());
    }

    @Test
    public void getLocationTest() {
        Pair<Integer,Integer> testPair = new Pair(0,0);
        gameBoard.setHex(testHex, testPair);
        Pair<Integer,Integer> secondPair = new Pair(1,0);
        Hex secondHex = new Hex(1, Terrain.terrainType.Grassland);
        gameBoard.setHex(secondHex, secondPair);
        Assert.assertEquals(Orientation.addPairs(secondPair, Orientation.getOriginValue()), secondHex.getLocation());
    }


    @Test
    public void setHexLevel() {
        Pair<Integer,Integer> testPair = new Pair(0,0);
        gameBoard.setHex(testHex, testPair);
        Assert.assertEquals("Place Hex Test", 1, testHex.getLevel());
    }

    @Test
    public void setHexOnOtherHexUpdateTerrainTest() {
        gameBoard.setHex(testHex, new Pair<>(0,0));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Jungle), new Pair<>(0,0));

        Hex updatedHex = gameBoard.getHex(Orientation.getOriginValue());
        boolean valid = (updatedHex.getTerrain() == Terrain.terrainType.Jungle);

        Assert.assertEquals("Place Hex on top of another Hex and update terrain", true, valid);
    }

    @Test
    public void setHexOnOtherHexUpdateTileIdTest() {
        gameBoard.setHex(testHex, new Pair<>(0,0));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Jungle), new Pair<>(0,0));

        Hex updatedHex = gameBoard.getHex(Orientation.getOriginValue());
        boolean valid = (updatedHex.getTileId() == 1);

        Assert.assertEquals("Place Hex on top of another Hex and update tile ID", true, valid);
    }

    @Test
    public void setHexOnOtherHexUpdateLevelTest() {
        gameBoard.setHex(testHex, new Pair<>(0,0));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Jungle), new Pair<>(0,0));

        Hex updatedHex = gameBoard.getHex(Orientation.getOriginValue());
        boolean valid = (updatedHex.getLevel() == 2);

        Assert.assertEquals("Place Hex on top of another Hex and increase level by 1", true, valid);
    }

    @Test
    public void printSectionedBoardTest(){
        gameBoard.setHex(testHex, new Pair<>(0,0));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Rocky), Orientation.getUpleftValue());
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Rocky), Orientation.addPairByOrientation(Orientation.getUpleftValue(), Orientation.Orientations.upRight));

        Assert.assertEquals("printBoardTest", true, true);
    }


}
