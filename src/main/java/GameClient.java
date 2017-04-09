import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by troy on 4/6/2017.
 */
public class GameClient {

    //network info
    private String host;
    private int port;

    //Game info
    private static String playerID = "";
    private static int numRounds = 0;
    private static String challengeID;
    private static String roundID;
    private static String opponentPID = "";
    private static String game1ID;
    private static String game2ID;
    private static Socket socket;


//    This will be for when we actually run the project I guess?
    public static void main(String[] args) {

        String host = "localhost";

        int port = 8000;

        //Flags
        boolean challengeIsDone = false;
        boolean roundsAreDone = false;

        try {


            //Create socket and buffers
            socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String rawServerMessage;
            Message parsedServerMessage;
            MessageParser parser = new MessageParser();


            //get the player id
            parsedServerMessage = parseServerInput(in, Message.MessageType.WaitToBegin);
            if (parsedServerMessage instanceof WaitToBeginMessage) {
                playerID = ((WaitToBeginMessage)parsedServerMessage).getPid();
            }
            System.out.println("This is our playerID: " + playerID);

            //get number of rounds
            parsedServerMessage = parseServerInput(in, Message.MessageType.NewChallenge);
            if (parsedServerMessage instanceof NewChallengeMessage) {
                challengeID = ((NewChallengeMessage) parsedServerMessage).getCid();
            }
            if (parsedServerMessage instanceof NewChallengeMessage) {
                numRounds = ((NewChallengeMessage) parsedServerMessage).getRounds();
            }
            System.out.println("This is our challengeID: " + challengeID);
            System.out.println("This is the amount of rounds to play: " + numRounds);

            // This loop performs an iteration for each individual opponent we play, playing a set of numRounds
            // rounds against them
           // while(!challengeIsDone) {

               // int i = 0;
               // while(i < 10) {
                    //Get round ID get opponent pid
                    parsedServerMessage = parseServerInput(in, Message.MessageType.BeginRound);
                    if (parsedServerMessage instanceof BeginRoundMessage) {
                        roundID = ((BeginRoundMessage) parsedServerMessage).getRid();
                    }
                    parsedServerMessage = parseServerInput(in, Message.MessageType.MatchBeginning);
                    if (parsedServerMessage instanceof MatchBeginningMessage) {
                        opponentPID = ((MatchBeginningMessage) parsedServerMessage).getPid();
                        System.out.println("opponent's pid: " + opponentPID);
                    }
            //      i++;
            //    }

                challengeIsDone = true;


           // }


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Message parseServerInput(BufferedReader in, Message.MessageType type) throws IOException {
        String rawServerMessage;
        Message parsedServerMessage;
        MessageParser parser = new MessageParser();

        int i = 0;
        int arbitraryTimeoutNumber = 25; //how long we want to run before we realize maybe we entered bad input
        while (i < arbitraryTimeoutNumber) {
            while ((rawServerMessage = in.readLine()) != null) {
                parsedServerMessage = parser.parseString(rawServerMessage);
                System.out.println("Server says: " + rawServerMessage);
                if (parsedServerMessage.getMessageType() == type) {
                    return parsedServerMessage;
                }
            }
            i++;
        }
        return new Message(Message.MessageType.Welcome);
    }

    /*** FOR TESTING PURPOSES ONLY I KNOW THEY ARENT SUPPOSED TO BE STATIC ***/

    public static String getPlayerID() {
        return playerID;
    }

    public static int getNumRounds() {
        return numRounds;
    }

    public static String getChallengeID() {
        return challengeID;
    }

    public static String getRoundID() {
        System.out.print(roundID);
        return roundID;
    }

    public static String getOpponentPID() {
        return opponentPID;
    }

    public static String getGame1ID() {
        return game1ID;
    }

    public static String getGame2ID() {
        return game2ID;
    }

    public static void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //    public GameClient(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }
//
//    protected String testConnection() {
//        String serverString = "";
//        try {
//            Socket socket = new Socket(host, port);
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String serverMessage;
//            while ((serverMessage = in.readLine()) != null) {
//                System.out.println("Server says: " + serverMessage);
//                serverString = serverMessage;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//
//        return serverString;
//    }


}
