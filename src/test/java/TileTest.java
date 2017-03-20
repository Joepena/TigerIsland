import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Joe on 3/19/17.
 */
public class TileTest {
  Tile testTile;
  @Before
  public void setUp() throws Exception {
    Terrain.terrainType terrainLeft = Terrain.terrainType.Grassland;
    Terrain.terrainType terrainRight = Terrain.terrainType.Lake;
    Pair<Integer,Integer> leftOrientation = Orientation.DOWNLEFT;

    testTile = new Tile(1,terrainLeft,terrainRight,leftOrientation);
  }
  @Test
  public void getTileId() throws Exception {
    Assert.assertEquals("verify tileId",testTile.getTileId(),1);
  }

  @Test
  public void getVolcano() throws Exception {
    Assert.assertEquals("verify a volcano hex", testTile.getVolcano().getTerrain(), Terrain.terrainType.Volcano);
  }

  @Test
  public void getLeft() throws Exception {
    Assert.assertEquals("verify getLeft", testTile.getLeft().getTerrain(), Terrain.terrainType.Grassland);
  }

  @Test
  public void getRight() throws Exception {
    Assert.assertEquals("verify getRight", testTile.getRight().getTerrain(), Terrain.terrainType.Lake);

  }


}
