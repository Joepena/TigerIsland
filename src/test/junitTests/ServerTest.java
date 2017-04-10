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
            String cid = "1";
            int rounds = 4;
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
            String message8 = "GAME 2 MOVE 1 PLAYER ASASDFASD PLACED JUNGLE+LAKE AT -1 3 -2 3 FOUNDED SETTLEMENT AT 0 1 -1\r\n";
            String message9 = "GAME 1 OVER PLAYER player5 123 PLAYER player2 2345\r\n";
            String message10 = "GAME 2 OVER PLAYER player2 123 PLAYER player5 2345\r\n";
            //round 2
            String message11 = "BEGIN ROUND "+ 2 +" OF " + rounds + "\r\n";
            String message12 = "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER " + "D" + "\r\n";
            String message13 = "MAKE YOUR MOVE IN GAME " + gid1 + " WITHIN " + time + " SECONDS: MOVE " + moveNum + " PLACE " + tile + "\r\n";
            String message14 = "GAME 2 MOVE 1 PLAYER ASASDFASD PLACED JUNGLE+LAKE AT -1 3 -2 3 FOUNDED SETTLEMENT AT 0 1 -1\r\n";
            String message15 = "GAME 1 OVER PLAYER player5 123 PLAYER player2 2345\r\n";
            String message16 = "GAME 2 OVER PLAYER D 123 PLAYER player5 2345\r\n";
            String message17 = "WAIT FOR THE NEXT CHALLENGE TO BEGIN\r\n";

            //challenge 2
            String message40 = "NEW CHALLENGE " + 2 + " YOU WILL PLAY " + rounds + " MATCHES\r\n";
            String message50 = "BEGIN ROUND "+ 3 +" OF " + rounds + "\r\n";
            String message60 = "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER " + opponentPID + "\r\n";
            String message70 = "MAKE YOUR MOVE IN GAME " + gid1 + " WITHIN " + time + " SECONDS: MOVE " + moveNum + " PLACE " + tile + "\r\n";
            String message80 = "GAME 2 MOVE 1 PLAYER ASASDFASD PLACED JUNGLE+LAKE AT -1 3 -2 3 FOUNDED SETTLEMENT AT 0 1 -1\r\n";
            String message90 = "GAME 1 OVER PLAYER player5 123 PLAYER player2 2345\r\n";
            String message100 = "GAME 2 OVER PLAYER player2 123 PLAYER player5 2345\r\n";
            //round 2
            String message110 = "BEGIN ROUND "+ 4 +" OF " + rounds + "\r\n";
            String message120 = "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER " + "FFFFFFF" + "\r\n";
            String message130 = "MAKE YOUR MOVE IN GAME " + gid1 + " WITHIN " + time + " SECONDS: MOVE " + moveNum + " PLACE " + tile + "\r\n";
            String message140 = "GAME 2 MOVE 1 PLAYER FUCKER PLACED JUNGLE+LAKE AT -1 3 -2 3 FOUNDED SETTLEMENT AT 0 1 -1\r\n";
            String message150 = "GAME 1 OVER PLAYER player5 123 PLAYER player2 2345\r\n";
            String message160 = "GAME 2 OVER PLAYER FFFFFFF 123 PLAYER player5 2345\r\n";
            String message170 = "END OF CHALLENGES\r\n";

            System.out.println("From our AI with love: " + in.readLine());
            System.out.println("From our AI with love: " + in.readLine());
            //System.out.println("From our AI with love: " + in.readLine());
            //System.out.println("From our AI with love: " + in.readLine());

            out.println(message1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message2);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message3);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message4);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message5);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message6);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message7);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message8);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message9);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message10);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message11);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message12);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message13);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message14);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message15);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message16);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message17);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message40);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message50);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message60);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message70);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message80);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message90);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message100);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message110);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message120);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message130);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message140);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message150);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message160);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(message170);

            String fromPlayer = "From our AI with love: " + in.readLine();
           // serverSocket.close();
            System.out.println(fromPlayer);

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }


    }
}