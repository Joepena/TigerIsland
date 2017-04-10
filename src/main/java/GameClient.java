import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

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
    private static int roundCount;
    private static String opponentPID = "";
    private static String game1ID = "";
    private static String game2ID = "";
    private static Socket socket;
    private static PrintWriter output;
    private static Queue<Message> p1Moves;
    private static Queue<Message> p2Moves;
    private static boolean p1RoundIsDone;
    private static boolean p2RoundIsDone;
    private static boolean challengeIsDone;




//    This will be for when we actually run the project I guess?
    public static void main(String[] args) throws Exception {

        String host = "localhost";

        int port = 8000;

        //Game Flags
        challengeIsDone = false;
        p1RoundIsDone = false;
        p2RoundIsDone = false;


        try {


            //Create socket and buffers
            socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            String rawServerMessage;
            Message parsedServerMessage;
            MessageParser parser = new MessageParser();

            //Send in username and password
            ClientMessages authentication = new ClientMessages("K", "K");
            output.println(authentication.enterThunderdome("TIGERSRULE"));
            output.println(authentication.usernamePassword());
            //output.close();

            Message beginMessage = new NoActionMessage(Message.MessageType.Welcome);
            while(beginMessage.getMessageType() != Message.MessageType.WaitToBegin) {
                beginMessage = parseServerInput(in, Message.MessageType.WaitToBegin);
            }

            //Get player ID
            if (beginMessage instanceof WaitToBeginMessage) {
                playerID = ((WaitToBeginMessage)beginMessage).getPid();
            }

            System.out.println("This is our playerID: " + playerID);

         //   while(!challengeIsDone) {

                //get number of rounds
                roundCount = 1;
                parsedServerMessage = parseServerInput(in, Message.MessageType.NewChallenge);
                if (parsedServerMessage instanceof NewChallengeMessage) {
                    challengeID = ((NewChallengeMessage) parsedServerMessage).getCid();
                }
                if (parsedServerMessage instanceof NewChallengeMessage) {
                    numRounds = ((NewChallengeMessage) parsedServerMessage).getRounds();
                }
                System.out.println("Message type of beginning round message:  " + parsedServerMessage.getMessageType());


                // This loop performs an iteration for each individual opponent we play, playing a set of numRounds
                // rounds against them

               //
                while(roundCount++ <= numRounds) {
                    //Get round ID get opponent pid
//                    String waitForRound = "";
//                    while(waitForRound.equals("")) {
//                        waitForRound = in.readLine();
//                    }
//                    parsedServerMessage = parser.parseString(waitForRound);
//                    if (parsedServerMessage.getMessageType() == Message.MessageType.BeginRound) {
//                        if (parsedServerMessage instanceof BeginRoundMessage) {
//                            roundID = ((BeginRoundMessage) parsedServerMessage).getRid();
//                            System.out.println("BeginRound success");
//                        }
//                    } else {
                        parsedServerMessage = parseServerInput(in, Message.MessageType.BeginRound);
                        if (parsedServerMessage instanceof BeginRoundMessage) {
                            roundID = ((BeginRoundMessage) parsedServerMessage).getRid();
                        }
                    //}
                    parsedServerMessage = parseServerInput(in, Message.MessageType.MatchBeginning);
                    if (parsedServerMessage instanceof MatchBeginningMessage) {
                        opponentPID = ((MatchBeginningMessage) parsedServerMessage).getPid();
                    }
                    //
                    Thread player1 = new Thread(new PlayerRunnable(playerID, opponentPID, 1));
                    player1.start();
                    Thread player2 = new Thread(new PlayerRunnable(playerID, opponentPID, 2));
                    player2.start();

                    p1Moves = new LinkedList<Message>();
                    p2Moves = new LinkedList<Message>();

                    p1RoundIsDone = false;
                    p2RoundIsDone = false;

                    //SINGLE TURN
                    while (!p1RoundIsDone || !p2RoundIsDone) {

                        Message turnMessage;
                        String serverMessage = "";
                        String tempGameID;

                        while (serverMessage.equals("")) {
                            serverMessage = in.readLine();
                        }
                        System.out.println("ServerString:   " + serverMessage);
                        turnMessage = parser.parseString(serverMessage);

                        //System.out.println("Server says: " + turnMessage + "  MessageType: " + turnMessage.getMessageType());

                        tempGameID = getGameIDFromMessage(turnMessage);

                        //Will check if gameIDs are empty and set if one is

                        setGameIDs(tempGameID);

                        if (turnMessage instanceof GameOverMessage) {
                            if (((GameOverMessage) turnMessage).getGid().equals(game1ID)) {
                                p1RoundIsDone = true;
                            } else {
                                p2RoundIsDone = true;
                            }
                        }


                        System.out.println("GameID1:  " + game1ID + "   tempGameID:  " + tempGameID);
                        System.out.println("GameID2:  " + game2ID);

//                        if (tempGameID.equals(game1ID)) {
//                            System.out.println("Client entering Sycnhronized block1");
//                            synchronized (p1Moves) {
//                                p1Moves.add(turnMessage);
//                                System.out.println("Added to Queue1");
//                                p1Moves.notifyAll();
//                            }
//                        } else if (tempGameID.equals(game2ID)) {
//                            System.out.println("Client entering Sycnhronized block2");
//                            synchronized (p2Moves) {
//                                p2Moves.add(turnMessage);
//                                System.out.println("Added to Queue2");
//                                p2Moves.notifyAll();
//                            }
//
//                        }
                        if (tempGameID.equals(game1ID)) {
                            p1Moves.add(turnMessage);
                            player1.interrupt();
                        } else if (tempGameID.equals(game2ID)) {
                            p2Moves.add(turnMessage);
                            player2.interrupt();
                        }

                    }

                   player1.join();
                   player2.join();

                   //player2.interrupt();
                    System.out.println("Threads should be dead");

                    game1ID = "";
                    game2ID = "";
//                    synchronized (p1Moves) {
//                        System.out.println("p1Moves.size():  " + p1Moves.size());
//                        p1Moves.notifyAll();
//                    }
              //  }
                System.out.println("currentRound: " + roundCount);
                System.out.println("numRounds: " + numRounds);
               // parsedServerMessage = parseServerInput(in, Message.MessageType.WaitForNext);
              //  System.out.println("parsedServerMessage:  " + parsedServerMessage);

           }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
         try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Find gameID of the message we received
    private static String getGameIDFromMessage(Message serverMessage) {

        String returnID = "";

        if (serverMessage instanceof MakeYourMoveMessage) {
            returnID = ((MakeYourMoveMessage) serverMessage).getGid();
        } else if (serverMessage instanceof MoveMessage) {
            returnID = ((MoveMessage) serverMessage).getGid();
        }
        else if (serverMessage instanceof  GameOverMessage)
            returnID = ((GameOverMessage)serverMessage).getGid();

        return returnID;
    }

    //Set gameIDs for players if they don't have them already
    private static void setGameIDs(String ID) {
        if (game1ID.equals("")) {
            game1ID = ID;
        } else if (game2ID.equals("")) {
            game2ID = ID;}
    }


    public static void sendMessageFromPlayerToServer(clientMoveMessages playerMessage) {
        String finalMessage = playerMessage.toString(playerMessage.getMessageType());
        //System.out.println("To server with love: " + finalMessage);
        try {
            PrintWriter playerWriter = new PrintWriter(socket.getOutputStream());
            playerWriter.print(finalMessage);
            //playerWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                //System.out.println("Server says: " + rawServerMessage);
                if (parsedServerMessage.getMessageType() == type) {
                    System.out.println(type);
                    return parsedServerMessage;
                }
                else if(type == Message.MessageType.EndOfChallenges){
                    //System.out.println("Queue1 size: " + p1Moves.size());
                   // System.out.println("Queue2 size: " + p2Moves.size());
                    challengeIsDone = true;
                }
                else {
                    System.out.println("Expected type " + type);
                    System.out.println("Actual type: "+ parsedServerMessage.getMessageType());
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

    public static Queue<Message> getP1Moves() {
        return p1Moves;
    }

//    public static void setP1Move(Message p1Move) {
//        GameClient.p1Move = p1Move;
//    }

    public static Queue<Message> getP2Moves() {
        return p2Moves;
    }

//    public static void setP2Move(Message p2Move) {
//        GameClient.p2Move = p2Move;
//    }

    public static void setP1RoundIsDone(boolean p1RoundIsDone) {
        GameClient.p1RoundIsDone = p1RoundIsDone;
    }

    public static void setP2RoundIsDone(boolean p2RoundIsDone) {
        GameClient.p2RoundIsDone = p2RoundIsDone;
    }





}
