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
        player = new PlayerRunnable("A", "B", 1);
        player.setGameID("GAME0");

        mP = new MessageParser();
    }

    @Test
    public void ignoreDifferentGidTest () throws Exception {
        createLandMass();

        mP.parseString("GAME GAME1 MOVE 12 PLAYER B PLACED LAKE+ROCK AT 1 0 -1 3 FOUNDED SETTLEMENT AT 1 1 -2");
        player.executeMessage(mP.getMessage());

        Assert.assertTrue("GID is different so move is ignored", player.getGame().gameBoard.getHex(new Tuple(1, 0, -1)).getLevel() == 1);
    }

    @Test
    public void foundSettlementFromMessageTest () throws Exception {
        createLandMass();

        FoundSettlementMessage message = (FoundSettlementMessage)mP.parseString("GAME GAME0 MOVE 12 PLAYER B PLACED LAKE+ROCK AT 1 0 -1 3 FOUNDED SETTLEMENT AT 1 1 -2");
        player.executeMessage(mP.getMessage());


        Assert.assertTrue("Tile placed at 1 0 -1",
                player.getGame().gameBoard.getHex(new Tuple(1, 0, -1)).getLevel() == 2);
        Assert.assertTrue("Settlement founded at 1 1 -2",
                player.getGame().gameBoard.getHex(new Tuple(1, 1, -2)).getOccupiedBy() == Hex.gamePieces.Meeple &&
                        player.getGame().gameBoard.getHex(new Tuple(1, 1, -2)).getTeam() == Hex.Team.White);
    }

    @Test
    public void expandSettlementFromMessageTest () throws Exception {
        createLandMass();
        player.getGame().gameBoard.getHex(new Tuple(-1, 0 , 1)).setOccupiedBy(Hex.gamePieces.Meeple);
        player.getGame().gameBoard.getHex(new Tuple(-1, 0 , 1)).setTeam(Hex.Team.White);

        player.getGame().updateSettlements();

        ExpandSettlementMessage message = (ExpandSettlementMessage)mP.parseString("GAME GAME0 MOVE 12 PLAYER B PLACED LAKE+JUNGLE AT 1 0 -1 3 EXPANDED SETTLEMENT AT -1 0 1 JUNGLE");
        player.executeMessage(mP.getMessage());

        Assert.assertTrue("Tile placed at 1 0 -1",
                player.getGame().gameBoard.getHex(Orientation.upRightOf(Orientation.getOrigin())).getLevel() == 2);
        Assert.assertTrue("Settlement founded at 0 -1 1",
                player.getGame().gameBoard.getHex(Orientation.downRightOf(Orientation.getOrigin())).getOccupiedBy() == Hex.gamePieces.Meeple &&
                        player.getGame().gameBoard.getHex(Orientation.downRightOf(Orientation.getOrigin())).getTeam() == Hex.Team.White);
        Assert.assertTrue("Settlement founded at 1 -1 0 has Meeple",
                        player.getGame().gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin())).getOccupiedBy() == Hex.gamePieces.Meeple&&
                        player.getGame().gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin())).getTeam() == Hex.Team.White);
    }

    @Test
    public void totoroParsingTest () throws Exception {
        createLandMass();
        mP.parseString("GAME GAME0 MOVE 13 PLAYER B PLACED LAKE+ROCK AT 1 0 -1 3 BUILT TOTORO SANCTUARY AT 1 1 -2");
        player.executeMessage(mP.getMessage());
        Tuple testTuple = new Tuple(1, 1, -2);
        Tuple testTuple2 = new Tuple(1, 0, -1);

        Assert.assertEquals(Terrain.terrainType.Volcano, player.getGame().gameBoard.getHex(testTuple2).getTerrain());
        Assert.assertEquals(Terrain.terrainType.Lake, player.getGame().gameBoard.getHex(Orientation.rightOf(testTuple2)).getTerrain());
        Assert.assertEquals(Terrain.terrainType.Rocky, player.getGame().gameBoard.getHex(Orientation.downRightOf(testTuple2)).getTerrain());
        Assert.assertEquals(Hex.gamePieces.Totoro, player.getGame().gameBoard.getHex(testTuple).getOccupiedBy());
    }

    @Test
    public void tigerParsingTest () throws Exception {
        createLandMass();
        mP.parseString("GAME GAME0 MOVE 13 PLAYER B PLACED LAKE+ROCK AT 1 0 -1 3 BUILT TIGER PLAYGROUND AT 1 1 -2");
        player.executeMessage(mP.getMessage());
        Tuple testTuple = new Tuple(1, 1, -2);
        Tuple testTuple2 = new Tuple(1, 0, -1);

        Assert.assertEquals(Terrain.terrainType.Volcano, player.getGame().gameBoard.getHex(testTuple2).getTerrain());
        Assert.assertEquals(Terrain.terrainType.Lake, player.getGame().gameBoard.getHex(Orientation.rightOf(testTuple2)).getTerrain());
        Assert.assertEquals(Terrain.terrainType.Rocky, player.getGame().gameBoard.getHex(Orientation.downRightOf(testTuple2)).getTerrain());
        Assert.assertEquals(Hex.gamePieces.Tiger, player.getGame().gameBoard.getHex(testTuple).getOccupiedBy());
    }

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
