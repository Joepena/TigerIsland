import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

/**
 * Created by Troy on 4/10/17.
 */
public class SendToServerTest {
    public static void main(String[] args) throws IOException {

        int portNumber = 1708;

        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);

                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            System.out.println(serverSocket.getLocalSocketAddress());
            System.out.println(clientSocket.getRemoteSocketAddress().toString());
            String output;
            while (true) {
                while ((output = in.readLine()) != null) {
                    System.out.println(output);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
