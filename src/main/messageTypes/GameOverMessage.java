import java.util.Scanner;

/**
 * Created by T K Vicious on 4/6/2017.
 */
public class GameOverMessage extends Message{
    private String gid;
    private String pid1;
    private String pid2;
    private int score1;
    private int score2;

    public String getGid() {
        return gid;
    }

    public String getPid1() {
        return pid1;
    }

    public String getPid2() {
        return pid2;
    }

    public int getScore1() {
        return score1;
    }

    public int getScore2() {
        return score2;
    }

    GameOverMessage(String message){
        super(MessageType.GameOver);
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        scanner.next();
        this.gid = scanner.next();
        scanner.close();
    }

    public boolean equals(GameOverMessage message){
        return(this.gid.equals(message.getGid()));
    }
}
