//import org.junit.Test;
//import org.junit.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



//import static org.junit.Assert.*;

/**
 * Created by troy on 4/6/2017.
 */
public class GameClientTest {

    GameClient client;
    String helloTest;

    public void main(String args[] ) throws IOException {
        ServerSocket listener = null;
        boolean messageSent = false;
        try {
            listener = new ServerSocket(3000);
            while (messageSent == false) {
                Socket socket = null;
                try {
                    socket = listener.accept();
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("hello");
                    messageSent = true;
                } finally {
                    socket.close();
                }
            }
        } finally {
            listener.close();
        }
    }
}
