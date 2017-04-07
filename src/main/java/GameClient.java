import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by troy on 4/6/2017.
 */
public class GameClient {
    private String host;
    private int port;

//    This will be for when we actually run the project I guess?
    public static void main(String[] args) {
        String host = "10.136.60.73"; //"localhost"
        int port = 8000;
        

        try {
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Server says: " + serverMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
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
