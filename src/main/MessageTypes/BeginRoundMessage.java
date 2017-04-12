import java.util.Objects;
import java.util.Scanner;

/**
 * Created by TomasK on 4/6/2017.
 */
public class BeginRoundMessage extends Message{
    private int rid;
    private int rounds;

    public int getRid() {
        return rid;
    }

    public int getRounds() {
        return rounds;
    }

    BeginRoundMessage(String message){
        super(MessageType.BeginRound);
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        if(!scanner.hasNext())
            return;
        scanner.next();
        scanner.next();
        this.rid = scanner.nextInt();
        scanner.next();
        this.rounds = scanner.nextInt();
        scanner.close();
    }

    public boolean equals(BeginRoundMessage message){
        return(this.rounds == message.getRounds() &&
                this.rid == message.getRid());
    }
}
