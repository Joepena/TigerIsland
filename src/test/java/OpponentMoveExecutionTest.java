import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by TomasK on 4/8/2017.
 */
public class OpponentMoveExecutionTest {
    PlayerRunnable player;
    MessageParser mP;

    @Before
    public void setUp() throws Exception {
        player = new PlayerRunnable("A", "B", "GAME0");
        mP = new MessageParser();
    }

    @Test
    public void ignoreDifferentGidTest () throws Exception {
        createLandMass();
        mP.parseString("GAME GAME1 MOVE 12 PLAYER B PLACED LAKE+ROCK AT 1 0 -1 3 FOUNDED SETTLEMENT AT 1 1 -2");
        player.executeMessage(mP.getMessage());

        Assert.assertTrue("GID is different so move is ignored", player.getGame().gameBoard.getHex(new Tuple(1, 1, -2)).getLevel() == 1);
    }

  /*  @Test
    public void foundSettlementFromMessageTest () throws Exception {
        createLandMass();
        System.out.println(player.toString());

        FoundSettlementMessage message = (FoundSettlementMessage)mP.parseString("GAME GAME0 MOVE 12 PLAYER B PLACED LAKE+ROCK AT 1 0 -1 3 FOUNDED SETTLEMENT AT 1 1 -2");
        player.executeMessage(mP.getMessage());

        message.toString();



        Assert.assertTrue("Tile placed at 1 0 -1 and settlement founded at 1 1 -2",
                player.getGame().gameBoard.getHex(new Tuple(1, 1, -2)).getLevel() == 2);
    }*/

    public void createLandMass() throws Exception {
        Tuple origin = Orientation.getOrigin();
        Tuple tile1Loc = Orientation.rightOf(Orientation.upRightOf(origin));
        Tuple tile2Loc = Orientation.leftOf(tile1Loc);


        Tile tile0 = new Tile(0, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.downLeft);
        Tile tile1 = new Tile(1, Terrain.terrainType.Jungle, Terrain.terrainType.Grassland, Orientation.Orientations.downLeft);
        Tile tile2 = new Tile(2, Terrain.terrainType.Lake, Terrain.terrainType.Grassland, Orientation.Orientations.upRight);
        player.getGame().placeTile(tile0, origin);
        player.getGame().placeTile(tile1, tile1Loc);
        player.getGame().placeTile(tile2, tile2Loc);

    }

}
