import java.util.Scanner;

/**
 * Created by TomasK on 4/6/2017.
 */
public class EndRoundMessage extends Message {
    public int getRid() {
        return rid;
    }

    public int getRounds() {
        return rounds;
    }

    EndRoundMessage(String message){
        super();
        messageType = MessageType.EndRound;
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        if(!scanner.hasNext())
            return;
        scanner.next();
        scanner.next();
        scanner.next();
        rid = scanner.nextInt();
        scanner.next();
        rounds = scanner.nextInt();
        scanner.close();
    }

    public boolean equals(EndRoundMessage message){
        return(this.rounds == message.getRounds() &&
                this.rid == message.getRid());
    }
}
