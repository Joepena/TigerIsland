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




        }
        scanner.close();

    }

    /*private void move(Scanner scanner){
        this.gid = scanner.next();
        switch(scanner.next()) {
            case "MOVE":
                this.moveNumber = scanner.nextInt();
                scanner.next();
                this.pid = scanner.next();
                switch(scanner.next()){
                    case "FORFEITED:":
                        this.forfeited = true;
                        this.gameOver = true;
                        break;
                    case "LOST:":
                        this.lost = true;
                        this.gameOver = true;
                        break;
                    default:
                        this.tileString = scanner.next();
                        scanner.next();
                        this.placementLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                        if(!scanner.hasNext())
                            return;
                        switch(scanner.next()){
                            case "FOUND":
                                scanner.next();
                                scanner.next();
                                this.moveLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                                break;
                            case "EXPAND":
                                scanner.next();
                                scanner.next();
                                this.moveLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                                break;
                            case "BUILD":
                                if(scanner.next().equals("TIGER")){
                                    this.tiger = true;
                                }
                                else
                                    this.totoro = true;
                                this.moveLocation = new Tuple(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                                break;
                            case "UNABLE":
                                scanner.next();
                                scanner.next();
                                this.forfeited = true;
                                break;
                        }

                }
        }

    }*/

}
