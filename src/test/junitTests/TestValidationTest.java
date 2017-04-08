
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Megans on 4/1/2017.
 */
public class TestValidationTest {
    Board gameBoard = new Board();

    public void setHexesAroundCoordinates(Tuple coordinates){

        gameBoard.setHex(new Hex(2, Terrain.terrainType.Grassland), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.left));
        gameBoard.setHex(new Hex(3, Terrain.terrainType.Rocky), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.right));
        gameBoard.setHex(new Hex(2, Terrain.terrainType.Volcano), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.upLeft));
        gameBoard.setHex(new Hex(4, Terrain.terrainType.Jungle), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.upRight));
        gameBoard.setHex(new Hex(2, Terrain.terrainType.Lake), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.downLeft));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Grassland), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.downRight));

    }

    @Test
    public void tilePlacementPrinterTest()
    {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.getOrigin());
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(1,-1,0));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(1,-2,1));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(0,-2,2));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(-1,-1,2));
        TileValidation tile = new TileValidation();
        tile.tileValidationListFinder(Orientation.getOrigin(), gameBoard);
        Set<Tuple> tileList = tile.getAvailableTilePlacement();
        Assert.assertEquals(false , tile.existsInSetAlready(new Tuple (0, -1, 1)));

    }
    @Test
    public void tilePlacementOrigin()
    {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.getOrigin());
        TileValidation tile = new TileValidation();
        tile.tileValidationListFinder(Orientation.getOrigin(), gameBoard);
        Vector<Integer> list = tile.findValidOrientationsAtPoint(Orientation.downLeftOf(Orientation.getOrigin()), gameBoard);
        Vector<Integer> correctAnswer = new Vector<Integer>();
        correctAnswer.add(3); correctAnswer.add(4); correctAnswer.add(5); correctAnswer.add(6);
       Assert.assertEquals(list ,correctAnswer);
    }
    @Test
    public void tilePlacementOriginFail()
    {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.getOrigin());
        TileValidation tile = new TileValidation();
        tile.tileValidationListFinder(Orientation.getOrigin(), gameBoard);
        Vector<Integer> list = tile.findValidOrientationsAtPoint((Orientation.getOrigin()), gameBoard);
        Integer correctAnswer = -1;
        Assert.assertEquals(correctAnswer, list.get(0));
    }
    @Test
    public void tilePlacementVectorTest()
    {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.getOrigin());
        TileValidation tile = new TileValidation();
        tile.tileValidationListFinder(Orientation.getOrigin(), gameBoard);
        Set<Tuple> tileList = tile.getAvailableTilePlacement();
        Set<Tuple> expectedList = new HashSet<>();
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
            Assert.assertEquals(true, tile.existsInSetAlready(s));
        }
    }


}
