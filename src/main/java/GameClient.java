import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class GameClient {

  //Game info
  private static String playerID = "";
  private static int numRounds = 0;
  private static String opponentPID = "";
  private static String game1ID = "";
  private static String game2ID = "";
  private static Socket socket;
  private static Queue<Message> p1Moves;
  private static Queue<Message> p2Moves;
  private static Queue<String> outqueue;


  // 0:IP/Host, 1:port, 2:username, 3:pass, 4:tournament pass
  public static void main(String[] args) throws Exception {

    String host = args[0];
    System.out.println(args[0]);
    int port = Integer.parseInt(args[1]);
    System.out.println(args[1]);
    String userName = args[2];
    System.out.println(args[2]);
    String password = args[3];
    System.out.println(args[3]);
    String tournamentpass = args[4];
    System.out.println(args[4]);

    //Game Flags
    boolean challengeIsDone = false;
    boolean p1RoundIsDone;
    boolean p2RoundIsDone;

    try {

      //Create socket and buffers
      socket = new Socket(host, port);
      PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      Message parsedServerMessage;
      MessageParser parser = new MessageParser();
      outqueue = new LinkedList<>();

      //Send in username and password
      ClientMessages authentication = new ClientMessages(userName, password);

      Message beginMessage;

      //Get Welcome, send game password
      beginMessage = parseServerInput(in, Message.MessageType.Welcome);
      if (beginMessage.getMessageType() == Message.MessageType.Welcome) {
        output.println(authentication.enterThunderdome(tournamentpass));
        System.out.println("said pass: "+tournamentpass);
      }

      //Get enter, send in username and password
      beginMessage = parseServerInput(in, Message.MessageType.Enter);
      if (beginMessage.getMessageType() == Message.MessageType.Enter) {
        output.println(authentication.usernamePassword());
        System.out.println("said username and pass: "+authentication.usernamePassword());
      }

      while (beginMessage.getMessageType() != Message.MessageType.WaitToBegin) {
        beginMessage = parseServerInput(in, Message.MessageType.WaitToBegin);
      }

      //Get player ID
      if (beginMessage instanceof WaitToBeginMessage) {
        playerID = ((WaitToBeginMessage) beginMessage).getPid();
      }

      System.out.println("This is our playerID: " + playerID);

      while (!challengeIsDone) {
        System.out.println("challenge is done: " + false);

        //get number of rounds
        int roundCount = 1;
        parsedServerMessage = parseServerInput(in, Message.MessageType.NewChallenge);
        if (parsedServerMessage instanceof NewChallengeMessage) {
          String challengeID = ((NewChallengeMessage) parsedServerMessage).getCid();
        }
        if (parsedServerMessage instanceof NewChallengeMessage) {
          numRounds = ((NewChallengeMessage) parsedServerMessage).getRounds();
        }
        System.out.println(
          "Message type of beginning round message:  " + parsedServerMessage.getMessageType());

        // This loop performs an iteration for each individual opponent we play, playing a set of numRounds
        // rounds against them

        //
        while (roundCount++ <= numRounds) {
          System.out.println((roundCount - 1) + " < " + numRounds);
          parsedServerMessage = parseServerInput(in, Message.MessageType.BeginRound);
          if (parsedServerMessage instanceof BeginRoundMessage) {
            int roundID = ((BeginRoundMessage) parsedServerMessage).getRid();
          }
          parsedServerMessage = parseServerInput(in, Message.MessageType.MatchBeginning);
          if (parsedServerMessage instanceof MatchBeginningMessage) {
            opponentPID = ((MatchBeginningMessage) parsedServerMessage).getPid();
          }
          //
          Thread player1 = new Thread(new PlayerRunnable(playerID, opponentPID, 1));
          player1.start();
          Thread player2 = new Thread(new PlayerRunnable(playerID, opponentPID, 2));
          player2.start();

          p1Moves = new LinkedList<>();
          p2Moves = new LinkedList<>();

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

            if (tempGameID.equals(game1ID)) {
              p1Moves.add(turnMessage);
              player1.interrupt();
            } else if (tempGameID.equals(game2ID)) {
              p2Moves.add(turnMessage);
              player2.interrupt();
            }

          }

          while (!p1Moves.isEmpty() || !p2Moves.isEmpty()) {
            if (!p1Moves.isEmpty()) {
              player1.interrupt();
            }
            if (!p2Moves.isEmpty()) {
              player2.interrupt();
            }
          }

          player1.join();
          player2.join();

          System.out.println("Threads should be dead");

          game1ID = "";
          game2ID = "";

          while (parsedServerMessage.getMessageType() != Message.MessageType.EndRound) {
            parsedServerMessage = parseServerInput(in, Message.MessageType.EndRound);
          }


        }

        parsedServerMessage = parseServerInput(in, Message.MessageType.WaitForNext);
        if (parsedServerMessage.getMessageType() == Message.MessageType.EndOfChallenges) {
          challengeIsDone = true;
        }

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
    } else if (serverMessage instanceof GameOverMessage) {
      returnID = ((GameOverMessage) serverMessage).getGid();
    }

    return returnID;
  }

  //Set gameIDs for players if they don't have them already
  private static void setGameIDs(String ID) {
    if (game1ID.equals("")) {
      game1ID = ID;
    } else if (game2ID.equals("")) {
      game2ID = ID;
    }
  }


  public static void sendMessageFromPlayerToServer(clientMoveMessages playerMessage) {
    String finalMessage = playerMessage.toString(playerMessage.getMessageType());
    outqueue.add(finalMessage);
    System.out.println("To server with love: " + finalMessage);
    try {
      PrintWriter playerWriter = new PrintWriter(socket.getOutputStream(), true);
      while (!outqueue.isEmpty()) {
        playerWriter.println(outqueue.remove());
      }
      //playerWriter.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }


  private static Message parseServerInput(BufferedReader in, Message.MessageType type)
    throws IOException {
    String rawServerMessage;
    Message parsedServerMessage;
    MessageParser parser = new MessageParser();

    int i = 0;
    int arbitraryTimeoutNumber = 15; //how long we want to run before we realize maybe we entered bad input
    while (i < arbitraryTimeoutNumber) {
      rawServerMessage = in.readLine();
      if (!rawServerMessage.equals("")) {
        parsedServerMessage = parser.parseString(rawServerMessage);
        return parsedServerMessage;
      }
      i++;
    }
    return new Message(Message.MessageType.Welcome);
  }


  public static String getGame1ID() {
    return game1ID;
  }

  public static String getGame2ID() {
    return game2ID;
  }

  public static Queue<Message> getP1Moves() {
    return p1Moves;
  }

  public static Queue<Message> getP2Moves() {
    return p2Moves;
  }


}
