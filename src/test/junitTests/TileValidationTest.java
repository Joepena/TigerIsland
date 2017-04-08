
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Megans on 4/1/2017.
 */
public class TileValidationTest {
  GameAPI gameAPI = new GameAPI();
  Board gameBoard = gameAPI.gameBoard;

    @Test
    public void tilePlacementPrinterTest()
    {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.getOrigin());
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(1,-1,0));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(1,-2,1));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(0,-2,2));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(-1,-1,2));
        ArrayList<Tuple> tileList = gameAPI.getAvailableTilePlacement();
        Assert.assertEquals(false , gameAPI.APIUtils.existsInSetAlready(new Tuple (0, -1, 1), tileList));

    }


    @Test
    public void tilePlacementVectorTest()
    {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.getOrigin());
        ArrayList<Tuple> tileList = gameAPI.getAvailableTilePlacement();
        ArrayList<Tuple> expectedList = new ArrayList<>();
        expectedList.add(new Tuple(-1,0,1));
        expectedList.add(new Tuple(1,1,-2));
        expectedList.add(new Tuple(0,-2,2));
        expectedList.add(new Tuple(-2,0,2));
        expectedList.add(new Tuple(-1,2,-1));
        expectedList.add(new Tuple(-1,1,0));
        expectedList.add(new Tuple(-2,1,1));
        expectedList.add(new Tuple(0,-1,1));
        expectedList.add(new Tuple(0,1,-1));
        expectedList.add(new Tuple(-1,-1,2));
        expectedList.add(new Tuple(2,-1,-1));
        expectedList.add(new Tuple(2,0,-2));
        expectedList.add(new Tuple(1,0,-1));
        expectedList.add(new Tuple(0,2,-2));
        expectedList.add(new Tuple(1,-2,1));
        expectedList.add(new Tuple(2,-2,0));
        expectedList.add(new Tuple(1,-1,0));
        expectedList.add(new Tuple(-2,2,0));

        for (Tuple s :expectedList) {
            Assert.assertEquals(true, gameAPI.APIUtils.existsInSetAlready(s,tileList));
        }
    }


}
