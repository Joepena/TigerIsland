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
            String opponentPID = "player2";
            String message1 = "WELCOME TO ANOTHER EDITION OF THUNDERDOME!";
            String message2 = "TWO SHALL ENTER, ONE SHALL LEAVE";
            String message3 = "WAIT FOR THE TOURNAMENT TO BEGIN " + pid;
            String message4 = "NEW CHALLENGE " + cid + " YOU WILL PLAY " + rounds + " MATCHES";
            String message5 = "BEGIN ROUND "+ roundID +" OF " + rounds;
            String message6 = "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER " + opponentPID;


            out.println(message1);
            out.println(message2);
            out.println(message3);
            out.println(message4);
            out.println(message5);
            out.println(message6);

            while(true);


        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}