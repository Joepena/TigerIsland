import javafx.util.Pair;

/**
 * Created by WIIZZARD on 3/19/2017.
 */
public class HexValidation {



    public void adjacencyValidation(Pair<Integer,Integer> coordinatePair) {



        Integer anchorHexX = coordinatePair.getKey();
        Integer anchorHexY = coordinatePair.getValue();

        Integer upleftLocX = anchorHexX + Orientation.UPLEFT.getKey();
        Integer upleftLocY = anchorHexY + Orientation.UPLEFT.getValue();


        //Pair<Integer,Integer> upleftPair = coordinatePair.Orientation.UPLEFT;






    }



}
