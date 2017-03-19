import javafx.util.Pair;

/**
 * Created by Max on 3/16/17.
 */
public class Orientation {

    public static final Pair<Integer, Integer> ORIGIN = new Pair(188,188);
    public static final Pair<Integer, Integer> UPRIGHT = new Pair(2,1);
    public static final Pair<Integer, Integer> UPLEFT = new Pair(2,-1);
    public static final Pair<Integer, Integer> RIGHT = new Pair(0,2);
    public static final Pair<Integer, Integer> LEFT = new Pair(0,-2);
    public static final Pair<Integer, Integer> DOWNRIGHT = new Pair(-2,1);
    public static final Pair<Integer, Integer> DOWNLEFT = new Pair(-2,-1);

    public static  Pair<Integer,Integer> addPairs (Pair<Integer,Integer> pair1, Pair<Integer,Integer> pair2){
        Integer pair1X = pair1.getKey();
        Integer pair1Y = pair1.getValue();

        Integer pair2X = pair2.getKey();
        Integer pair2Y = pair2.getValue();

        return new Pair<Integer,Integer> (pair1X + pair2X, pair1Y + pair2Y);

    }

    public static Pair<Integer,Integer> upLeftOf(Pair<Integer,Integer> startCoordinates){
        Integer resultLocationX = startCoordinates.getKey() + Orientation.UPLEFT.getKey();
        Integer resultLocationY = startCoordinates.getValue() + Orientation.UPLEFT.getValue();

        return new Pair<Integer,Integer> (resultLocationX, resultLocationY);
    }
    public static Pair<Integer,Integer> upRightOf(Pair<Integer,Integer> startCoordinates){
        Integer resultLocationX = startCoordinates.getKey() + Orientation.UPRIGHT.getKey();
        Integer resultLocationY = startCoordinates.getValue() + Orientation.UPRIGHT.getValue();

        return new Pair<Integer,Integer> (resultLocationX, resultLocationY);
    }
    public static Pair<Integer,Integer> downLeftOf(Pair<Integer,Integer> startCoordinates){
        Integer resultLocationX = startCoordinates.getKey() + Orientation.DOWNLEFT.getKey();
        Integer resultLocationY = startCoordinates.getValue() + Orientation.DOWNLEFT.getValue();

        return new Pair<Integer,Integer> (resultLocationX, resultLocationY);
    }
    public static Pair<Integer,Integer> downRightOf(Pair<Integer,Integer> startCoordinates){
        Integer resultLocationX = startCoordinates.getKey() + Orientation.DOWNRIGHT.getKey();
        Integer resultLocationY = startCoordinates.getValue() + Orientation.DOWNRIGHT.getValue();

        return new Pair<Integer,Integer> (resultLocationX, resultLocationY);
    }
    public static Pair<Integer,Integer> leftOf(Pair<Integer,Integer> startCoordinates){
        Integer resultLocationX = startCoordinates.getKey() + Orientation.LEFT.getKey();
        Integer resultLocationY = startCoordinates.getValue() + Orientation.LEFT.getValue();

        return new Pair<Integer,Integer> (resultLocationX, resultLocationY);
    }
    public static Pair<Integer,Integer> rightOf(Pair<Integer,Integer> startCoordinates){
        Integer resultLocationX = startCoordinates.getKey() + Orientation.RIGHT.getKey();
        Integer resultLocationY = startCoordinates.getValue() + Orientation.RIGHT.getValue();

        return new Pair<Integer,Integer> (resultLocationX, resultLocationY);
    }


}
