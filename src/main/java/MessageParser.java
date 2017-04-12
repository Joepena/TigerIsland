import java.util.Scanner;

/**
 * Created by T K Vicious on 4/5/2017.
 */
public class MessageParser {
    private Message message;


    public MessageParser(){
        this.message = null;

    }

    public Message getMessage(){
        return this.message;
    }


    public Message parseString (String message) {
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        if(!scanner.hasNext())
            return this.message;
        String messageStart = scanner.next();
        switch(messageStart){
            case "WELCOME":
                this.message = new NoActionMessage(Message.MessageType.Welcome);
                break;
            case "TWO":
                this.message = new NoActionMessage(Message.MessageType.Enter);
                break;
            case "NEW":
                challengeOrMatch(message, scanner);
                break;
            case "WAIT":
                waitToBeginOrNoAction(message, scanner);
                break;
            case "END":
                endOfChallengeOrEndOfRound(message, scanner);
                break;
            case "THANK":
                this.message = new NoActionMessage(Message.MessageType.Goodbye);
                break;
            case "BEGIN":
                this.message = new BeginRoundMessage(message);
                break;
            case "GAME":
                gameMoveOrGameOver(message, scanner);
                break;
            case "MAKE":
                this.message = new MakeYourMoveMessage(message);
                break;
        }
        scanner.close();
        return this.message;
    }

    private void challengeOrMatch(String message, Scanner scanner){
        if(scanner.next().equals("CHALLENGE")){
            this.message = new NewChallengeMessage(message);
        }
        else{
            this.message = new MatchBeginningMessage(message);
        }
    }

    private void waitToBeginOrNoAction(String message, Scanner scanner){
        scanner.next();
        scanner.next();
        switch(scanner.next()){
            case "TOURNAMENT":
                this.message = new WaitToBeginMessage(message);
                break;
            case "NEXT":
                this.message = new NoActionMessage(Message.MessageType.WaitForNext);
                break;
        }
    }

    private void endOfChallengeOrEndOfRound(String message, Scanner scanner){
        scanner.next();
        switch(scanner.next()){
            case "CHALLENGES":
                this.message = new NoActionMessage(Message.MessageType.EndOfChallenges);
                break;
            case "ROUND":
                this.message = new EndRoundMessage(message);
                break;
        }
    }

    private void gameMoveOrGameOver(String message, Scanner scanner){
        scanner.next();
        String temp = scanner.next();
        if(temp.equals("MOVE")) {
            chooseMoveType(message, scanner);
        }
        else{
            this.message = new GameOverMessage(message);
        }
    }

    private void buildTotoroOrTiger(String message, Scanner scanner){
        if (scanner.next().equals("TOTORO"))
            this.message = new BuildTotoroMessage(message);
        else
            this.message = new BuildTigerMessage(message);
    }

    private void chooseMoveType(String message, Scanner scanner){
        String temp = "";
        for(int i = 0; i < 11; i++) {
            if(scanner.hasNext())
                temp = scanner.next();
        }
        switch (temp) {
            case "FOUNDED":
                this.message = new FoundSettlementMessage(message);
                break;
            case "EXPANDED":
                this.message = new ExpandSettlementMessage(message);
                break;
            case "BUILT":
                buildTotoroOrTiger(message, scanner);
                break;
            default:
                this.message = new ForfeitMessage(message);
                break;
        }
    }

}