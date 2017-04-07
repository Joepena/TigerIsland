import java.util.Scanner;

/**
 * Created by T K Vicious on 4/5/2017.
 */
public class messageFields {
    private Message message;


    public messageFields(String message){
        parseString(message);

    }

    public Message getMessage(){
        return this.message;
    }


    private void parseString (String message) {
        Scanner scanner = new Scanner(message).useDelimiter(" ");
        if(!scanner.hasNext())
            return;
        String messageStart = scanner.next();
        switch(messageStart){
            case "WELCOME":
                this.message = new noActionMessage(Message.MessageType.Welcome);
                break;
            case "TWO":
                this.message = new noActionMessage(Message.MessageType.Enter);
                break;
            case "NEW":
                if(scanner.next().equals("CHALLENGE")){
                    this.message = new NewChallengeMessage(message);
                }
                else{
                    this.message = new MatchBeginningMessage(message);
                }
                break;
            case "WAIT":
                scanner.next();
                scanner.next();
                switch(scanner.next()){
                    case "TOURNAMENT":
                        this.message = new WaitToBeginMessage(message);
                        break;
                    case "NEXT":
                        this.message = new noActionMessage(Message.MessageType.WaitForNext);
                        break;
                }
                break;
            case "END":
                scanner.next();
                switch(scanner.next()){
                    case "CHALLENGES":
                        this.message = new noActionMessage(Message.MessageType.EndOfChallenges);
                        break;
                    case "ROUND":
                        this.message = new EndRoundMessage(message);
                        break;
                }
                break;
            case "THANK":
                this.message = new noActionMessage(Message.MessageType.Goodbye);
                break;
            case "BEGIN":
                this.message = new BeginRoundMessage(message);
                break;
            case "GAME":
                scanner.next();
                String temp = scanner.next();
                if(temp.equals("MOVE")) {
                    for(int i = 0; i < 11; i++)
                        temp = scanner.next();
                        if(temp.equals("FOUNDED"))
                            this.message = new FoundSettlementMessage(message);
                        else if(temp.equals("EXPANDED"))
                            this.message = new ExpandSettlementMessage(message);
                        else if(temp.equals("BUILT"))
                            if(scanner.next().equals("TOTORO"))
                                this.message = new BuildTotoroMessage(message);
                            else
                                this.message = new BuildTigerMessage(message);
                }
                break;




        }
        scanner.close();

    }



}
