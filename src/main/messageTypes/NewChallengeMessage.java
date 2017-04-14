import java.util.Scanner;

/**
 * Created by TomasK on 4/6/2017.
 */
public class NewChallengeMessage extends Message{

    public String getCid() {
        return cid;
    }

    public int getRounds() {
        return rounds;
    }

    NewChallengeMessage(String message){
        super();
        messageType = MessageType.NewChallenge;
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        if(!scanner.hasNext())
            return;
        scanner.next();
        scanner.next();
        cid = scanner.next();
        for(int i = 0; i < 3; i++)
            scanner.next();
        rounds = scanner.nextInt();
        scanner.close();
    }

    public boolean equals(NewChallengeMessage message){
        return (this.cid.equals(message.getCid()) &&
                this.rounds == message.getRounds());

    }

    public String toString(){
        return "cid:  " + this.cid + "\nrounds:  " + this.rounds;
    }
}
