import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by T K Vicious on 4/5/2017.
 */
public class MessageParserTest {
    MessageParser mf;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void NewChallengeMessageTest () throws Exception {
        this.mf = new MessageParser("NEW CHALLENGE A1$ YOU WILL PLAY 12 MATCHES");
        NewChallengeMessage message = new NewChallengeMessage("NEW CHALLENGE A1$ YOU WILL PLAY 12 MATCHES");

        Assert.assertTrue("NewChallengeMessage Generation from server string" , message.equals((NewChallengeMessage)mf.getMessage()));
        Assert.assertTrue("NewChallengeMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.NewChallenge);
    }

    @Test
    public void WaitToBeginMessageTest () throws Exception {
        this.mf = new MessageParser("WAIT FOR THE TOURNAMENT TO BEGIN 56^T");
        WaitToBeginMessage message = new WaitToBeginMessage("WAIT FOR THE TOURNAMENT TO BEGIN 56^T");

        Assert.assertTrue("WaitToBeginMessage Generation from server string" , message.equals((WaitToBeginMessage)mf.getMessage()));
        Assert.assertTrue("WaitToBeginMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.WaitToBegin);
    }

    @Test
    public void BeginRoundMessageTest () throws Exception {
        this.mf = new MessageParser("BEGIN ROUND 8 OF 12");
        BeginRoundMessage message = new BeginRoundMessage("BEGIN ROUND 8 OF 12");

        Assert.assertTrue("BeginRoundMessage Generation from server string" , message.equals((BeginRoundMessage)mf.getMessage()));
        Assert.assertTrue("BeginRoundMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.BeginRound);
    }

    @Test
    public void EndRoundMessageTest () throws Exception {
        this.mf = new MessageParser("END OF ROUND 99 OF 112");
        EndRoundMessage message = new EndRoundMessage("END OF ROUND 99 OF 112");

        Assert.assertTrue("EndRoundMessage Generation from server string" , message.equals((EndRoundMessage)mf.getMessage()));
        Assert.assertTrue("EndRoundMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.EndRound);
    }

    @Test
    public void MatchBeginningMessageTest () throws Exception {
        this.mf = new MessageParser("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER ABC");
        MatchBeginningMessage message = new MatchBeginningMessage("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER ABC");

        Assert.assertTrue("MatchBeginningMessage Generation from server string" , message.equals((MatchBeginningMessage)mf.getMessage()));
        Assert.assertTrue("MatchBeginningMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.MatchBeginning);
    }

    @Test
    public void MoveFoundSettlementMessageTest () throws Exception {
        this.mf = new MessageParser("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 FOUNDED SETTLEMENT AT 12 23 1");
        FoundSettlementMessage message = new FoundSettlementMessage("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 FOUNDED SETTLEMENT AT 12 23 1");

        Assert.assertTrue("FoundSettlementMessage Generation from server string" , message.equals((FoundSettlementMessage)mf.getMessage()));
        Assert.assertTrue("FoundSettlementMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.Move);
        Assert.assertTrue("FoundSettlementMessage sets MessageFields MoveType field correctly", ((FoundSettlementMessage)(mf.getMessage())).getMoveType() == MoveMessage.MoveType.Found);
    }

    @Test
    public void MoveExpandSettlementMessageTest () throws Exception {
        this.mf = new MessageParser("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 EXPANDED SETTLEMENT AT 65 89 45 JUNGLE");
        ExpandSettlementMessage message = new ExpandSettlementMessage("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 EXPANDED SETTLEMENT AT 65 89 45 JUNGLE");

        Assert.assertTrue("ExpandSettlementMessage Generation from server string" , message.equals((ExpandSettlementMessage)mf.getMessage()));
        Assert.assertTrue("ExpandSettlementMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.Move);
        Assert.assertTrue("ExpandSettlementMessage sets MessageFields MoveType field correctly", ((ExpandSettlementMessage)(mf.getMessage())).getMoveType() == MoveMessage.MoveType.Expand);
    }

    @Test
    public void MoveBuildTotoroMessageTest () throws Exception {
        this.mf = new MessageParser("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 BUILT TOTORO SANCTUARY AT 15 53 44");
        BuildTotoroMessage message = new BuildTotoroMessage("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 BUILT TOTORO SANCTUARY AT 15 53 44");

        Assert.assertTrue("BuildTotoroMessage Generation from server string" , message.equals((BuildTotoroMessage)mf.getMessage()));
        Assert.assertTrue("BuildTotoroMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.Move);
        Assert.assertTrue("BuildTotoroMessage sets MessageFields MoveType field correctly", ((BuildTotoroMessage)(mf.getMessage())).getMoveType() == MoveMessage.MoveType.Totoro);

    }

    @Test
    public void MoveBuildTigerMessageTest () throws Exception {
        this.mf = new MessageParser("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 BUILT TIGER PLAYGROUND AT 15 53 44");
        BuildTigerMessage message = new BuildTigerMessage("GAME 123 MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 3 BUILT TIGER PLAYGROUND AT 15 53 44");

        Assert.assertTrue("BuildTigerMessage Generation from server string" , message.equals((BuildTigerMessage)mf.getMessage()));
        Assert.assertTrue("BuildTigerMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.Move);
        Assert.assertTrue("BuildTigerMessage sets MessageFields MoveType field correctly", ((BuildTigerMessage)(mf.getMessage())).getMoveType() == MoveMessage.MoveType.Tiger);

    }

    @Test
    public void ForfeitMessageTest () {
        this.mf = new MessageParser("GAME 123 MOVE 12 PLAYER DGD FORFEITED: ILLEGAL TILE PLACEMENT");
        ForfeitMessage message = new ForfeitMessage("GAME 123 MOVE 12 PLAYER DGD FORFEITED: ILLEGAL TILE PLACEMENT");

        Assert.assertTrue("ForfeitMessage Generation from server string" , message.equals((ForfeitMessage)mf.getMessage()));
        Assert.assertTrue("ForfeitMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.Move);
        Assert.assertTrue("ForfeitMessage sets MessageFields MoveType field correctly", ((ForfeitMessage)(mf.getMessage())).getMoveType() == MoveMessage.MoveType.Forfeit);

    }

    @Test
    public void GameOverMessageTest() {
        this.mf = new MessageParser("GAME 1290 OVER PLAYER ABC 23000 PLAYER DEF 230000");
        GameOverMessage message = new GameOverMessage("GAME 1290 OVER PLAYER ABC 23000 PLAYER DEF 230000");

        Assert.assertTrue("GameOverMessage Generation from server string" , message.equals((GameOverMessage)mf.getMessage()));
        Assert.assertTrue("GameOverMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.GameOver);
    }

    @Test
    public void MakeYourMoveMessageTest() {
        this.mf = new MessageParser("MAKE YOUR MOVE IN GAME 12345 WITHIN 1.545 SECONDS: MOVE 1234 PLACE ROCK+LAKE");
        MakeYourMoveMessage message = new MakeYourMoveMessage("MAKE YOUR MOVE IN GAME 12345 WITHIN 1.545 SECONDS: MOVE 1234 PLACE ROCK+LAKE");

        Assert.assertTrue("MakeYourMoveMessage Generation from server string" , message.equals((MakeYourMoveMessage)mf.getMessage()));
        Assert.assertTrue("MakeYourMoveMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.MakeYourMove);

    }

    @Test
    public void noActionMessageTest () throws Exception {
        this.mf = new MessageParser("WELCOME TO ANOTHER EDITION OF THUNDERDOME!");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.Welcome);

        this.mf = new MessageParser("TWO SHALL ENTER ONE SHALL LEAVE");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.Enter);

        this.mf = new MessageParser("END OF CHALLENGES");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.EndOfChallenges);

        this.mf = new MessageParser("WAIT FOR THE NEXT CHALLENGE TO BEGIN");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.WaitForNext);

        this.mf = new MessageParser("THANK YOU FOR PLAYING! GOODBYE");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.Goodbye);
    }




    @Test
    public void makeTileFromStringTest() throws Exception {
        Message message = new Message(Message.MessageType.BeginRound);
        Tile compareTile = new Tile(1, Terrain.terrainType.Grassland, Terrain.terrainType.Lake, Orientation.Orientations.downLeft);
        Tile testTile = message.makeTileFromString("LAKE+GRASS", 1, Orientation.Orientations.downLeft);

        Assert.assertTrue("Test correctly interprets server tile string to create tile", compareTile.equals(testTile));
    }

    @Test
    public void stringToTerrainTest() throws Exception {
        Message message = new Message(Message.MessageType.BeginRound);
        Assert.assertTrue("Creating terrainType from server terrain string", Terrain.terrainType.Grassland == message.stringToTerrain("GRASS"));
    }



    /*
    @Test
    public void parsePlacementMessageTest() {
        MessageParser m = new MessageParser("GAME 5 MOVE 4 PLAYER 23 PLACED LAKE+ROCKY AT 3 5 4 6");
        System.out.println(m.toString());
    }*/

}
