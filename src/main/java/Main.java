import javafx.util.Pair;

/**
 * Created by Max on 3/19/17.
 */
public class Main {
    public static void main(String[] args) {
        Thread player1 = new Thread(new PlayerRunnable());
        player1.start();

        Thread player2 = new Thread(new PlayerRunnable());
        player2.start();
    }
}
