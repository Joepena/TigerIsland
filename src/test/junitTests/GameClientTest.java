import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.CharBuffer;

/**
 * Created by Troy on 4/8/17.
 */
public class GameClientTest {

    String message1;
    String message2;
    String message3;
    String message4;
    String pid;
    String cid;
    int rounds;
    String[] args;
    FileInputStream fips;


    @Before
    public void setup() throws IOException {
        pid = "player1";
        cid = "testGame";
        rounds = 3;
        message1 = "WELCOME TO ANOTHER EDITION OF THUNDERDOME!";
        message2 = "TWO SHALL ENTER, ONE SHALL LEAVE";
        message3 = "WAIT FOR THE TOURNAMENT TO BEGIN " + pid;
        message4 = "NEW CHALLENGE " + cid + " YOU WILL PLAY " + rounds + " MATCHES";
        args = null;
        //fips = new FileInputStream(new File("src/test/resources/GameClientTestIntroMessageParsing.txt"));
        //System.setIn(fips);
    }

    @Test
    public void testIntroMessageParsing() {

        try {
            GameClient.main(args);
            Assert.assertEquals("PlayerID should be player1", GameClient.getPlayerID(), "player5");
            Assert.assertEquals("cid should be testGame", GameClient.getChallengeID(), "testTestGame");
            Assert.assertEquals("numRounds should be 3", GameClient.getNumRounds(),5);
            Assert.assertEquals("roundID should be 1", GameClient.getRoundID(), "1");
            Assert.assertEquals("opponent pid should be player2",  GameClient.getOpponentPID(), "player2");
            GameClient.closeSocket();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testRoundMessageParsing() {
//
//        try {
//            //GameClient.main(args);
//
//            GameClient.closeSocket();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
