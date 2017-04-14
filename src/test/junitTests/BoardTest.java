import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Joe on 4/1/17.
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
    Assert.assertEquals("Empty Board3 Test", true, gameBoard.isOriginEmpty());
  }

  @Test
  public void setHex() {
    Tuple testCoord = Orientation.getOrigin();
    gameBoard.setHex(testHex, testCoord);
    Assert.assertEquals("Place Hex Test", false, gameBoard.isOriginEmpty());
  }

  @Test
  public void getLocationTest() {
    Tuple testCoord = Orientation.getOrigin();
    gameBoard.setHex(testHex, testCoord);
    Tuple secondPair = Orientation.rightOf(testCoord);
    Hex secondHex = new Hex(1, Terrain.terrainType.Grassland);
    gameBoard.setHex(secondHex, secondPair);
    Assert.assertEquals((secondPair.getX() == secondHex.getLocation().getX() && secondPair.getY() == secondHex.getLocation().getY() &&
    secondPair.getZ() == secondHex.getLocation().getZ()), true);
  }


  @Test
  public void setHexLevel() {
    Tuple testCoord = Orientation.getOrigin();
    gameBoard.setHex(testHex, testCoord);
    Assert.assertEquals("Place Hex Test", 1, testHex.getLevel());
  }

  @Test
  public void setHexOnOtherHexUpdateTerrainTest() {
    gameBoard.setHex(testHex, Orientation.getOrigin());
    gameBoard.setHex(new Hex(1, Terrain.terrainType.Jungle), Orientation.getOrigin());

    Hex updatedHex = gameBoard.getHex(Orientation.getOrigin());
    boolean valid = (updatedHex.getTerrain() == Terrain.terrainType.Jungle);

    Assert.assertEquals("Place Hex on top of another Hex and update terrain", true, valid);
  }

  @Test
  public void setHexOnOtherHexUpdateTileIdTest() {
    gameBoard.setHex(testHex, Orientation.getOrigin());
    gameBoard.setHex(new Hex(1, Terrain.terrainType.Jungle), Orientation.getOrigin());

    Hex updatedHex = gameBoard.getHex(Orientation.getOrigin());
    boolean valid = (updatedHex.getTileId() == 1);

    Assert.assertEquals("Place Hex on top of another Hex and update tile ID", true, valid);
  }

  @Test
  public void setHexOnOtherHexUpdateLevelTest() {
    gameBoard.setHex(testHex, Orientation.getOrigin());
    gameBoard.setHex(new Hex(1, Terrain.terrainType.Jungle), Orientation.getOrigin());

    Hex updatedHex = gameBoard.getHex(Orientation.getOrigin());
    boolean valid = (updatedHex.getLevel() == 2);

    Assert.assertEquals("Place Hex on top of another Hex and increase level by 1", true, valid);
  }
}
