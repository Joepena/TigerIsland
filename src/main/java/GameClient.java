import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

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
    private static int roundID = 0;
    private static int numberOfRounds = 0;
    private static String opponentPID = "";
    private static String game1ID = "";
    private static String game2ID = "";
    private static Socket socket;
    private static PrintWriter out;
    private static Message p1Move;
    private static Message p2Move;
    private static boolean p1RoundIsDone;
    private static boolean p2RoundIsDone;



//    This will be for when we actually run the project I guess?
    public static void main(String[] args) throws Exception {

        String host = "localhost";

        int port = 8000;

        //Game Flags
        boolean challengeIsDone = false;
        boolean roundsAreDone = false;
        p1RoundIsDone = false;
        p2RoundIsDone = false;

        //Player Flags
        boolean p1Read = false;
        boolean p2Read = false;

        try {


            //Create socket and buffers
            socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            String rawServerMessage;
            Message parsedServerMessage;
            MessageParser parser = new MessageParser();

            //Send in username and password
            ClientMessages authentication = new ClientMessages("K", "K");
            out.print(authentication.enterThunderdome("TIGERSRULE"));
            out.print(authentication.usernamePassword());

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
            //while(!challengeIsDone) {

               //
               // while(numberOfRounds++ < numRounds) {
                    //Get round ID get opponent pid
                    parsedServerMessage = parseServerInput(in, Message.MessageType.BeginRound);
                    if (parsedServerMessage instanceof BeginRoundMessage) {
                        roundID = ((BeginRoundMessage) parsedServerMessage).getRid();
                    }
                    parsedServerMessage = parseServerInput(in, Message.MessageType.MatchBeginning);
                    if (parsedServerMessage instanceof MatchBeginningMessage) {
                        opponentPID = ((MatchBeginningMessage) parsedServerMessage).getPid();
                    }
            //
                    Thread player1 = new Thread(new PlayerRunnable(playerID, opponentPID, 1));
                    player1.start();
//                    Thread player2 = new Thread(new PlayerRunnable(playerID, opponentPID, 2));
//                    player2.start();

                    //SINGLE TURN
                    while(!p1RoundIsDone && !p2RoundIsDone) {

                        Message turnMessage;
                        String serverMessage = "";
                        String tempGameID;

                        while (serverMessage.equals("")) {
                            serverMessage = in.readLine();
                        }
                            System.out.println(serverMessage);
                            turnMessage = parser.parseString(serverMessage);

                        System.out.println("Server says: " + turnMessage + "MessageType: " + turnMessage.getMessageType());

                        tempGameID = getGameIDFromMessage(turnMessage);

                        //Will check if gameIDs are empty and set if one is

                        setGameIDs(tempGameID);

                            if(turnMessage instanceof GameOverMessage){
                                if(((GameOverMessage)turnMessage).getGid().equals(game1ID)){
                                    p1RoundIsDone = true;
                                    player1.join();
                                }
                                else {
                                    p2RoundIsDone = true;
//                                    player2.join();
                                }
                            }


                        //Send Message object to proper player
                        if (tempGameID.equals(game1ID)) {
                            p1Move = turnMessage;
                            System.out.println("We got a move");
                            player1.interrupt();
                        } else if (tempGameID.equals(game2ID)) {
                           // p2Move = turnMessage;
                           // System.out.println("We got a move");
                           // player2.interrupt();
                        }

                    }




                //}

                challengeIsDone = true;


           //}


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    //Find gameID of the message we received
    private static String getGameIDFromMessage(Message serverMessage) {

        String returnID = "";

        if (serverMessage instanceof MakeYourMoveMessage) {
            returnID = ((MakeYourMoveMessage) serverMessage).getGid();
            System.out.println("tempID: " + returnID);
        } else if (serverMessage instanceof MoveMessage) {
            returnID = ((MoveMessage) serverMessage).getGid();
        }

        return returnID;
    }

    //Set gameIDs for players if they don't have them already
    private static void setGameIDs(String ID) {
        if (game1ID.equals("")) {
            game1ID = ID;
            System.out.println("Game1ID: " + game1ID);
        } else if (game2ID.equals("")) {
            game2ID = ID;
            System.out.println("Game2ID: " + game1ID);
        }
    }


    public static void sendMessageFromPlayerToServer(clientMoveMessages playerMessage, int playerNum) {
        String finalMessage = playerMessage.toString(playerMessage.getMessageType());
        out.print(finalMessage);

    }


    private static Message parseServerInput(BufferedReader in, Message.MessageType type) throws IOException {
        String rawServerMessage;
        Message parsedServerMessage;
        MessageParser parser = new MessageParser();

        int i = 0;
        int arbitraryTimeoutNumber = 15; //how long we want to run before we realize maybe we entered bad input
        while (i < arbitraryTimeoutNumber) {
            rawServerMessage = in.readLine();
            if (!rawServerMessage.equals("")) {
                parsedServerMessage = parser.parseString(rawServerMessage);
                System.out.println("Server says: " + rawServerMessage);
                if (parsedServerMessage.getMessageType() == type) {
                    return parsedServerMessage;
                }
            }
            i++;
        }
        //
        //}
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

    public static int getRoundID() {
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

    public static Message getP1Move() {
        return p1Move;
    }

    public static void setP1Move(Message p1Move) {
        GameClient.p1Move = p1Move;
    }

    public static Message getP2Move() {
        return p2Move;
    }

    public static void setP2Move(Message p2Move) {
        GameClient.p2Move = p2Move;
    }

    public static void setP1RoundIsDone(boolean p1RoundIsDone) {
        GameClient.p1RoundIsDone = p1RoundIsDone;
    }

    public static void setP2RoundIsDone(boolean p2RoundIsDone) {
        GameClient.p2RoundIsDone = p2RoundIsDone;
    }





}
