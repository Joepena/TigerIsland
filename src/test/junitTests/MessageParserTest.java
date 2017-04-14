/**
 * Created by TomasK on 4/8/2017.
 */
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by T K Vicious on 4/5/2017.
 */

public class MessageParserTest {
    MessageParser mP;


    @Before
    public void setUp() throws Exception {
        this.mP = new MessageParser();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void NewChallengeMessageTest () throws Exception {
        Message message = mP.parseString("NEW CHALLENGE A1$ YOU WILL PLAY 12 MATCHES");

        Assert.assertTrue("NewChallengeMessage sets challengeID" , message.getCid().equals("A1$"));
        Assert.assertTrue("NewChallengeMessage sets rounds", message.getRounds() == 12);
        Assert.assertTrue("NewChallengeMessage sets messageType", message.getMessageType() == Message.MessageType.NewChallenge);
    }

    @Test
    public void WaitToBeginMessageTest () throws Exception {
        Message message = mP.parseString("WAIT FOR THE TOURNAMENT TO BEGIN 56^T");

        Assert.assertTrue("WaitToBeginMessage sets PID" , message.getPid().equals("56^T"));
        Assert.assertTrue("WaitToBeginMessage sets messageType", message.getMessageType() == Message.MessageType.WaitToBegin);
    }

    @Test
    public void BeginRoundMessageTest () throws Exception {
        Message message = mP.parseString("BEGIN ROUND 8 OF 12");

        Assert.assertTrue("BeginRoundMessage sets RID" , message.getRid() == 8);
        Assert.assertTrue("BeginRoundMessage sets rounds", message.getRounds() == 12);
        Assert.assertTrue("BeginRoundMessage sets messageType", message.getMessageType() == Message.MessageType.BeginRound);

    }

    @Test
    public void EndRoundMessageTest () throws Exception {
        Message message = mP.parseString("END OF ROUND 99 OF 112");

        Assert.assertTrue("EndRoundMessage sets RID" , message.getRid() == 99);
        Assert.assertTrue("EndRoundMessage sets Rounds", message.getRounds() == 112);
        Assert.assertTrue("EndRoundMessage sets messageType", message.getMessageType() == Message.MessageType.EndRound);

    }

    @Test
    public void MatchBeginningMessageTest () throws Exception {
        Message message = mP.parseString("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER ABC");

        Assert.assertTrue("MatchBeginningMessage sets opponentPID" , message.getPid().equals("ABC"));
        Assert.assertTrue("MatchBeginningMessage sets MessageFields type field correctly", message.getMessageType() == Message.MessageType.MatchBeginning);
    }

    @Test
    public void MoveFoundSettlementMessageTest () throws Exception {
        Message message = mP.parseString("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 FOUNDED SETTLEMENT AT 12 23 1");

        Assert.assertTrue("FoundSettlementMessage sets GID" , message.getGid().equals("123"));
        Assert.assertTrue("FoundSettlementMessage sets moveNumber" , message.getMoveNumber() == 12);
        Assert.assertTrue("FoundSettlementMessage sets PID", message.getPid().equals("DGD"));
        Assert.assertTrue("FoundSettlementMessage sets Tile", message.getTile().equals(new Tile(12, Terrain.terrainType.Rocky, Terrain.terrainType.Lake, Orientation.Orientations.downRight)));
        Assert.assertTrue("FoundSettlementMessage sets TileLocation", message.getTileLocation().equals(new Tuple(10, -12, 11)));
        Assert.assertTrue("FoundSettlementMessage sets BuildLocation", message.getBuildLocation().equals(new Tuple(12, 23, 1)));

        Assert.assertTrue("FoundSettlementMessage sets MessageType field correctly", message.getMessageType() == Message.MessageType.Move);
    }

    @Test
    public void MoveExpandSettlementMessageTest () throws Exception {
        mP.parseString("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 EXPANDED SETTLEMENT AT 65 89 45 JUNGLE");
        ExpandSettlementMessage message = new ExpandSettlementMessage("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 EXPANDED SETTLEMENT AT 65 89 45 JUNGLE");

        Assert.assertTrue("ExpandSettlementMessage Generation from server string" , message.equals((ExpandSettlementMessage) mP.getMessage()));
        Assert.assertTrue("ExpandSettlementMessage sets MessageFields type field correctly", mP.getMessage().getMessageType() == Message.MessageType.Move);
        Assert.assertTrue("ExpandSettlementMessage sets MessageFields MoveType field correctly", ((ExpandSettlementMessage)(mP.getMessage())).getMoveType() == MoveMessage.MoveType.Expand);
    }

    @Test
    public void MoveBuildTotoroMessageTest () throws Exception {
        mP.parseString("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 BUILT TOTORO SANCTUARY AT 15 53 44");
        BuildTotoroMessage message = new BuildTotoroMessage("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 BUILT TOTORO SANCTUARY AT 15 53 44");

        Assert.assertTrue("BuildTotoroMessage Generation from server string" , message.equals((BuildTotoroMessage) mP.getMessage()));
        Assert.assertTrue("BuildTotoroMessage sets MessageFields type field correctly", mP.getMessage().getMessageType() == Message.MessageType.Move);
        Assert.assertTrue("BuildTotoroMessage sets MessageFields MoveType field correctly", ((BuildTotoroMessage)(mP.getMessage())).getMoveType() == MoveMessage.MoveType.Totoro);

    }

    @Test
    public void MoveBuildTigerMessageTest () throws Exception {
        mP.parseString("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 BUILT TIGER PLAYGROUND AT 15 53 44");
        BuildTigerMessage message = new BuildTigerMessage("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 BUILT TIGER PLAYGROUND AT 15 53 44");

        Assert.assertTrue("BuildTigerMessage Generation from server string" , message.equals((BuildTigerMessage) mP.getMessage()));
        Assert.assertTrue("BuildTigerMessage sets MessageFields type field correctly", mP.getMessage().getMessageType() == Message.MessageType.Move);
        Assert.assertTrue("BuildTigerMessage sets MessageFields MoveType field correctly", ((BuildTigerMessage)(mP.getMessage())).getMoveType() == MoveMessage.MoveType.Tiger);

    }

    @Test
    public void ForfeitMessageTest () {
        mP.parseString("GAME 123 MOVE 12 PLAYER DGD FORFEITED: ILLEGAL TILE PLACEMENT");
        ForfeitMessage message = new ForfeitMessage("GAME 123 MOVE 12 PLAYER DGD FORFEITED: ILLEGAL TILE PLACEMENT");

        Assert.assertTrue("ForfeitMessage Generation from server string" , message.equals((ForfeitMessage) mP.getMessage()));
        Assert.assertTrue("ForfeitMessage sets MessageFields type field correctly", mP.getMessage().getMessageType() == Message.MessageType.Move);
        Assert.assertTrue("ForfeitMessage sets MessageFields MoveType field correctly", ((ForfeitMessage)(mP.getMessage())).getMoveType() == MoveMessage.MoveType.Forfeit);

    }

    @Test
    public void GameOverMessageTest() {
        mP.parseString("GAME 1290 OVER PLAYER ABC 23000 PLAYER DEF 230000");
        GameOverMessage message = new GameOverMessage("GAME 1290 OVER PLAYER ABC 23000 PLAYER DEF 230000");

        Assert.assertTrue("GameOverMessage Generation from server string" , message.equals((GameOverMessage) mP.getMessage()));
        Assert.assertTrue("GameOverMessage sets MessageFields type field correctly", mP.getMessage().getMessageType() == Message.MessageType.GameOver);
    }

    @Test
    public void MakeYourMoveMessageTest() {
        mP.parseString("MAKE YOUR MOVE IN GAME 12345 WITHIN 1.545 SECONDS: MOVE 1234 PLACE ROCK+LAKE");
        MakeYourMoveMessage message = new MakeYourMoveMessage("MAKE YOUR MOVE IN GAME 12345 WITHIN 1.545 SECONDS: MOVE 1234 PLACE ROCK+LAKE");

        Assert.assertTrue("MakeYourMoveMessage Generation from server string" , message.equals((MakeYourMoveMessage) mP.getMessage()));
        Assert.assertTrue("MakeYourMoveMessage sets MessageFields type field correctly", mP.getMessage().getMessageType() == Message.MessageType.MakeYourMove);

    }

    @Test
    public void noActionMessageTest () throws Exception {
        mP.parseString("WELCOME TO ANOTHER EDITION OF THUNDERDOME!");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mP.getMessage().getMessageType() == Message.MessageType.Welcome);

        mP.parseString("TWO SHALL ENTER ONE SHALL LEAVE");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mP.getMessage().getMessageType() == Message.MessageType.Enter);

        mP.parseString("END OF CHALLENGES");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mP.getMessage().getMessageType() == Message.MessageType.EndOfChallenges);

        mP.parseString("WAIT FOR THE NEXT CHALLENGE TO BEGIN");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mP.getMessage().getMessageType() == Message.MessageType.WaitForNext);

        mP.parseString("THANK YOU FOR PLAYING! GOODBYE");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mP.getMessage().getMessageType() == Message.MessageType.Goodbye);
    }




    @Test
    public void makeTileFromStringTest() throws Exception {
        Message message = new Message();
        Tile compareTile = new Tile(1, Terrain.terrainType.Grassland, Terrain.terrainType.Lake, Orientation.Orientations.downLeft);
        Tile testTile = message.makeTileFromString("LAKE+GRASS", 1, Orientation.Orientations.downLeft);

        Assert.assertTrue("Test correctly interprets server tile string to create tile", compareTile.equals(testTile));
    }

    @Test
    public void stringToTerrainTest() throws Exception {
        Message message = new Message();
        Assert.assertTrue("Creating terrainType from server terrain string", Terrain.terrainType.Grassland == message.stringToTerrain("GRASS"));
    }


}
