import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by T K Vicious on 4/5/2017.
 */
public class messageFieldsTest {
    messageFields mf;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void NewChallengeMessageTest () throws Exception {
        this.mf = new messageFields("NEW CHALLENGE A1$ YOU WILL PLAY 12 MATCHES");
        NewChallengeMessage message = new NewChallengeMessage("NEW CHALLENGE A1$ YOU WILL PLAY 12 MATCHES");

        Assert.assertTrue("NewChallengeMessage Generation from server string" , message.equals((NewChallengeMessage)mf.getMessage()));
        Assert.assertTrue("NewChallengeMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.NewChallenge);
    }

    @Test
    public void WaitToBeginMessageTest () throws Exception {
        this.mf = new messageFields("WAIT FOR THE TOURNAMENT TO BEGIN 56^T");
        WaitToBeginMessage message = new WaitToBeginMessage("WAIT FOR THE TOURNAMENT TO BEGIN 56^T");

        Assert.assertTrue("WaitToBeginMessage Generation from server string" , message.equals((WaitToBeginMessage)mf.getMessage()));
        Assert.assertTrue("WaitToBeginMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.WaitToBegin);
    }

    @Test
    public void BeginRoundMessageTest () throws Exception {
        this.mf = new messageFields("BEGIN ROUND 8 OF 12");
        BeginRoundMessage message = new BeginRoundMessage("BEGIN ROUND 8 OF 12");

        Assert.assertTrue("BeginRoundMessage Generation from server string" , message.equals((BeginRoundMessage)mf.getMessage()));
        Assert.assertTrue("BeginRoundMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.BeginRound);
    }

    @Test
    public void EndRoundMessageTest () throws Exception {
        this.mf = new messageFields("END OF ROUND 99 OF 112");
        EndRoundMessage message = new EndRoundMessage("END OF ROUND 99 OF 112");

        Assert.assertTrue("EndRoundMessage Generation from server string" , message.equals((EndRoundMessage)mf.getMessage()));
        Assert.assertTrue("EndRoundMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.EndRound);
    }

    @Test
    public void MatchBeginningMessageTest () throws Exception {
        this.mf = new messageFields("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER ABC");
        MatchBeginningMessage message = new MatchBeginningMessage("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER ABC");

        Assert.assertTrue("MatchBeginningMessage Generation from server string" , message.equals((MatchBeginningMessage)mf.getMessage()));
        Assert.assertTrue("MatchBeginningMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.MatchBeginning);
    }

    @Test
    public void MoveFoundSettlementMessageTest () throws Exception {
        this.mf = new messageFields("GAME ABC MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 FOUNDED SETTLEMENT AT 12 23 -1");
        MoveMessage message = new MoveMessage("GAME ABC MOVE 12 PLAYER DGD PLACED LAKE+ROCK AT 10 -12 11 FOUNDED SETTLEMENT AT 12 23 -1");

        Assert.assertTrue("MatchBeginningMessage Generation from server string" , message.equals((MoveMessage)mf.getMessage()));
        Assert.assertTrue("MatchBeginningMessage sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.MatchBeginning);
    }

    @Test
    public void noActionMessageTest () throws Exception {
        this.mf = new messageFields("WELCOME TO ANOTHER EDITION OF THUNDERDOME!");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.Welcome);

        this.mf = new messageFields("TWO SHALL ENTER ONE SHALL LEAVE");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.Enter);

        this.mf = new messageFields("END OF CHALLENGES");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.EndOfChallenges);

        this.mf = new messageFields("WAIT FOR THE NEXT CHALLENGE TO BEGIN");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.WaitForNext);

        this.mf = new messageFields("THANK YOU FOR PLAYING! GOODBYE");
        Assert.assertTrue("Welcome message sets MessageFields type field correctly", mf.getMessage().getMessageType() == Message.MessageType.Goodbye);
    }

    /*
    @Test
    public void parsePlacementMessageTest() {
        messageFields m = new messageFields("GAME 5 MOVE 4 PLAYER 23 PLACED LAKE+ROCKY AT 3 5 4 6");
        System.out.println(m.toString());
    }*/

}
