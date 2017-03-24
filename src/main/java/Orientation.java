import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

/**
 * Created by Max on 3/16/17.
 */
public class Orientation {

    public static enum Orientations {
        origin,
        upRight,
        upLeft,
        right,
        left,
        downRight,
        downLeft
    }

    private static final Pair<Integer, Integer> ORIGIN = new Pair<>(188,188);
    private static final Pair<Integer, Integer> UPRIGHT = new Pair<>(2,1);
    private static final Pair<Integer, Integer> UPLEFT = new Pair<>(2,-1);
    private static final Pair<Integer, Integer> RIGHT = new Pair<>(0,2);
    private static final Pair<Integer, Integer> LEFT = new Pair<>(0,-2);
    private static final Pair<Integer, Integer> DOWNRIGHT = new Pair<>(-2,1);
    private static final Pair<Integer, Integer> DOWNLEFT = new Pair<>(-2,-1);

    public static Pair<Integer, Integer> getOriginValue() {
        return ORIGIN;
    }

    public static Pair<Integer, Integer> getUprightValue() {
        return UPRIGHT;
    }

    public static Pair<Integer, Integer> getUpleftValue() {
        return UPLEFT;
    }

    public static Pair<Integer, Integer> getRightValue() {
        return RIGHT;
    }

    public static Pair<Integer, Integer> getLeftValue() {
        return LEFT;
    }

    public static Pair<Integer, Integer> getDownrightValue() {
        return DOWNRIGHT;
    }

    public static Pair<Integer, Integer> getDownleftValue() {
        return DOWNLEFT;
    }

    private static final Map<Orientations, Orientations> rightHexMapping;


    static { //immutable map using static initialiser.
      Map<Orientations, Orientations> aMap = new HashMap<>();
      aMap.put(Orientations.upRight, Orientations.upLeft);
      aMap.put(Orientations.upLeft, Orientations.left);
      aMap.put(Orientations.left, Orientations.downLeft);
      aMap.put(Orientations.downLeft, Orientations.downRight);
      aMap.put(Orientations.downRight, Orientations.right);
      aMap.put(Orientations.right, Orientations.upRight);
      rightHexMapping = Collections.unmodifiableMap(aMap);
    }

    public static Orientations getRightHexMapping(Orientations orientation) {
      return rightHexMapping.get(orientation);
    }

    public static Pair<Integer,Integer> addPairByOrientation(Pair<Integer,Integer> coord,
                                                             Orientations orientation) {
        switch (orientation) {
            case upRight:
                return upRightOf(coord);
            case upLeft:
                return upLeftOf(coord);
            case left:
                return leftOf(coord);
            case right:
                return rightOf(coord);
            case downLeft:
                return downLeftOf(coord);
            case downRight:
                return downRightOf(coord);
            default:
                return ORIGIN;
        }
    }
  
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
