import java.util.ArrayList;
import org.junit.*;

import java.util.Vector;

/**
 * Created by Megans on 4/3/2017.
 */
public class SettlementCreationListTest {
    GameAPI gameAPI;
    Board gameBoard;

    @Before
    public void setUp(){
      gameAPI = new GameAPI();
      gameBoard = gameAPI.gameBoard;
    }

    public void setHexesAroundCoordinates(Tuple coordinates){

        gameBoard.setHex(new Hex(2, Terrain.terrainType.Grassland), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.left));
        gameBoard.setHex(new Hex(3, Terrain.terrainType.Rocky), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.right));
        gameBoard.setHex(new Hex(2, Terrain.terrainType.Volcano), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.upLeft));
        gameBoard.setHex(new Hex(4, Terrain.terrainType.Jungle), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.upRight));
        gameBoard.setHex(new Hex(2, Terrain.terrainType.Lake), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.downLeft));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Grassland), Orientation.addCoordinatesByOrientation(coordinates, Orientation.Orientations.downRight));

    }
    @Test
    public void settlementDFSTest(){
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.getOrigin());
        setHexesAroundCoordinates(Orientation.getOrigin());
        Hex[] neighbors = gameAPI.APIUtils.getNeighborHexes(Orientation.getOrigin(), gameBoard);
        neighbors[0].setTeam(Hex.Team.Black);
        neighbors[1].incrementLevel();
        neighbors[2].setTeam(Hex.Team.Black);
        neighbors[3].setTeam(Hex.Team.White);
        ArrayList<Tuple> settles = gameAPI.findListOfValidSettlementLocations();
        Vector<Tuple> actualSettlement = new Vector<>();
        actualSettlement.add(Orientation.getOrigin());
        actualSettlement.add(Orientation.downRightOf(Orientation.getOrigin()));
        actualSettlement.add(Orientation.downLeftOf(Orientation.getOrigin()));
        Assert.assertEquals(settles, actualSettlement);
    }

    @Test
    public void settleOriginTestFail()
    {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.getOrigin());
        Hex originHex = gameBoard.getHex(Orientation.getOrigin());
        originHex.incrementLevel();
        ArrayList<Tuple> actualSettleList = new ArrayList<>();
        ArrayList<Tuple> settleList = gameAPI.findListOfValidSettlementLocations();
        Assert.assertEquals(actualSettleList, settleList);
    }

    @Test
    public void settlementTesting()
    {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.getOrigin());
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(1,-1,0));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(1,-2,1));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(0,-2,2));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(-1,-1,2));
        Vector<Tuple> actualSettleList = new Vector<>();
        actualSettleList.add(new Tuple(0,0,0));
        actualSettleList.add(new Tuple(1, -1, 0));
        actualSettleList.add(new Tuple(1, -2, 1));
        actualSettleList.add(new Tuple(0, -2, 2));
        actualSettleList.add(new Tuple(-1, -1, 2));
        ArrayList<Tuple> settleList = gameAPI.findListOfValidSettlementLocations();
        Assert.assertEquals(actualSettleList, settleList);

    }

    @Test
    public void settlementTestingList()
    {
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), Orientation.getOrigin());
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(1,-1,0));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(1,-2,1));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(0,-2,2));
        gameBoard.setHex(new Hex(0, Terrain.terrainType.Jungle), new Tuple(-1,-1,2));
        Hex targetHex = gameBoard.getHex(new Tuple (0,-2,2));
        targetHex.incrementLevel();
        Vector<Tuple> actualSettleList = new Vector<>();
        actualSettleList.add(new Tuple(0,0,0));
        actualSettleList.add(new Tuple(1, -1, 0));
        actualSettleList.add(new Tuple(1, -2, 1));
        actualSettleList.add(new Tuple(-1, -1, 2));
        ArrayList<Tuple> settleList = gameAPI.findListOfValidSettlementLocations();
        Assert.assertEquals(actualSettleList, settleList);

    }


}
