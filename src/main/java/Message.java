/**
 * Created by TomasK on 4/6/2017.
 */
public class Message {
    private MessageType messageType;

    public static enum MessageType{
        Welcome, Enter, WaitToBegin, NewChallenge, BeginRound, EndRound, EndOfChallenges, WaitForNext, MatchBeginning,
        GameOver, MakeYourMove, Move, Goodbye
    }

    public boolean sameMessageType(MessageType type){
        return this.messageType == type;
    }

    Message(MessageType messageType){
        this.messageType = messageType;
    }

    public MessageType getMessageType(){
        return this.messageType;
    }
}
