import java.net.*;
import java.io.*;
 
public class ServerTest {
    public static void main(String[] args) throws IOException {
 
        int portNumber = 8000;
 
        try ( 
            ServerSocket serverSocket = new ServerSocket(portNumber);

            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println(serverSocket.getLocalSocketAddress());
            System.out.println(clientSocket.getRemoteSocketAddress().toString());
            String inputLine, outputLine;

            // Initiate conversation with client

            String pid = "player5";
            String cid = "testTestGame";
            int rounds = 5;
            int roundID = 1;
            String gid1 = "1";
            int time = 2;
            int moveNum = 1;
            String tile = "LAKE+JUNGLE\r\n";
            String opponentPID = "player2\r\n";
            String message1 = "WELCOME TO ANOTHER EDITION OF THUNDERDOME!\r\n";
            String message2 = "TWO SHALL ENTER, ONE SHALL LEAVE\r\n";
            String message3 = "WAIT FOR THE TOURNAMENT TO BEGIN " + pid + "\r\n";
            String message4 = "NEW CHALLENGE " + cid + " YOU WILL PLAY " + rounds + " MATCHES\r\n";
            String message5 = "BEGIN ROUND "+ roundID +" OF " + rounds + "\r\n";
            String message6 = "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER " + opponentPID + "\r\n";
            String message7 = "MAKE YOUR MOVE IN GAME " + gid1 + " WITHIN " + time + " SECONDS: MOVE " + moveNum + " PLACE " + tile + "\r\n";
            String message8 = "GAME 1 MOVE 1 PLACE JUNGLE+LAKE AT -1 3 -2 4 FOUND SETTLEMENT AT 0 1 -1\r\n";
//            String message9 = "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER " + opponentPID;



            out.println(message1);
            out.println(message2);
            out.println(message3);
            out.println(message4);
            out.println(message5);
            out.println(message6);
            out.println(message7);

            System.out.println("From our AI with love: " + in.readLine());

            while(true);


        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}