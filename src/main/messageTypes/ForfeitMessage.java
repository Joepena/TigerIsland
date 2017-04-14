import java.util.Scanner;

/**
 * Created by T K Vicious on 4/6/2017.
 */
public class ForfeitMessage extends MoveMessage{
    ForfeitMessage(String message){
        super(message);
        moveType = MoveType.Forfeit;
    }

    public boolean equals(BuildTigerMessage message){
        return super.equals((MoveMessage)message);
    }

}
