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
            outputLine = "PlaceTile at 0,2,-2 Build Settlement at -3,2,1";
            out.println(outputLine);
            int i = 0;
            while ((inputLine = in.readLine()) != null) {
                outputLine = "hello number: " + i;
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
                i++;
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}