import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
    private static final Map<Pair<Integer,Integer>, Pair<Integer,Integer>> rightHexMapping;

    static { //immutable map using static initialiser.
      Map<Pair<Integer,Integer>, Pair<Integer,Integer>> aMap = new HashMap<>();
      aMap.put(Orientation.UPRIGHT, Orientation.UPLEFT);
      aMap.put(Orientation.UPLEFT, Orientation.LEFT);
      aMap.put(Orientation.LEFT, Orientation.DOWNLEFT);
      aMap.put(Orientation.DOWNLEFT, Orientation.DOWNRIGHT);
      aMap.put(Orientation.DOWNRIGHT, Orientation.RIGHT);
      aMap.put(Orientation.RIGHT, Orientation.UPRIGHT);
      rightHexMapping = Collections.unmodifiableMap(aMap);
    }

  public static Pair<Integer, Integer> getRightHexMapping(Pair<Integer,Integer> leftHexCoordinates) {
    return rightHexMapping.get(leftHexCoordinates);
  }
}
