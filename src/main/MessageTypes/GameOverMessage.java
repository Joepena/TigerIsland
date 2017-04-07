import java.util.Scanner;

/**
 * Created by T K Vicious on 4/6/2017.
 */
public class GameOverMessage extends Message{
    private int gid;
    private String pid1;
    private String pid2;
    private int score1;
    private int score2;

    public int getGid() {
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
        this.gid = scanner.nextInt();
        scanner.next();
        scanner.next();
        this.pid1 = scanner.next();
        this.score1 = scanner.nextInt();
        scanner.next();
        this.pid2 = scanner.next();
        this.score2 = scanner.nextInt();
        scanner.close();
    }

    public boolean equals(GameOverMessage message){
        return(this.gid == message.getGid() &&
                this.pid1.equals(message.getPid1()) &&
                this.pid2.equals(message.getPid2()) &&
                this.score1 == message.getScore1() &&
                this.score2 == message.getScore2());
    }
}
