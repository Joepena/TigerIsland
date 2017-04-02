import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joe on 4/1/17.
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
  private static final Tuple Origin = new Tuple(0,0,0);
  private static final Tuple UPRIGHT = new Tuple(-1,1,0);
  private static final Tuple UPLEFT = new Tuple(-1,0,1);
  private static final Tuple RIGHT = new Tuple(0,1,-1);
  private static final Tuple LEFT = new Tuple(0,-1,1);
  private static final Tuple DOWNRIGHT = new Tuple(1,0,-1);
  private static final Tuple DOWNLEFT = new Tuple(1,-1,0);

  public static Tuple getOrigin() {
    return Origin;
  }

  public static Tuple getUPRIGHT() {
    return UPRIGHT;
  }

  public static Tuple getUPLEFT() {
    return UPLEFT;
  }

  public static Tuple getRIGHT() {
    return RIGHT;
  }

  public static Tuple getLEFT() {
    return LEFT;
  }

  public static Tuple getDOWNRIGHT() {
    return DOWNRIGHT;
  }

  public static Tuple getDOWNLEFT() {
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

  public static Tuple addCoordinatesByOrientation(Tuple coordinates,
    Orientations orientation) {
    switch (orientation) {
      case upRight:
        return upRightOf(coordinates);
      case upLeft:
        return upLeftOf(coordinates);
      case left:
        return leftOf(coordinates);
      case right:
        return rightOf(coordinates);
      case downLeft:
        return downLeftOf(coordinates);
      case downRight:
        return downRightOf(coordinates);
      default:
        return Origin;
    }
  }

  public static  Tuple addCoordinates (Tuple coordinate1, Tuple coordinate2){
    Integer pair1X = coordinate1.getX();
    Integer pair1Y = coordinate1.getY();
    Integer pair1Z = coordinate1.getZ();

    Integer pair2X = coordinate2.getX();
    Integer pair2Y = coordinate2.getY();
    Integer pair2Z = coordinate2.getZ();

    return new Tuple(pair1X+pair2X, pair1Y+pair2Y, pair1Z+pair2Z);

  }

  public static Tuple upLeftOf(Tuple startCoordinates){

    int resultLocationX = startCoordinates.getX() + Orientation.getUPLEFT().getX();
    int resultLocationY = startCoordinates.getY() + Orientation.getUPLEFT().getY();
    int resultLocationZ = startCoordinates.getZ() + Orientation.getUPLEFT().getZ();

    return new Tuple(resultLocationX,resultLocationY,resultLocationZ);
  }
  public static Tuple upRightOf(Tuple startCoordinates){

    int resultLocationX = startCoordinates.getX() + Orientation.getUPRIGHT().getX();
    int resultLocationY = startCoordinates.getY() + Orientation.getUPRIGHT().getY();
    int resultLocationZ = startCoordinates.getZ() + Orientation.getUPRIGHT().getZ();

    return new Tuple(resultLocationX,resultLocationY,resultLocationZ);
  }
  public static Tuple downRightOf(Tuple startCoordinates){

    int resultLocationX = startCoordinates.getX() + Orientation.getDOWNRIGHT().getX();
    int resultLocationY = startCoordinates.getY() + Orientation.getDOWNRIGHT().getY();
    int resultLocationZ = startCoordinates.getZ() + Orientation.getDOWNRIGHT().getZ();

    return new Tuple(resultLocationX,resultLocationY,resultLocationZ);
  }
  public static Tuple downLeftOf(Tuple startCoordinates){

    int resultLocationX = startCoordinates.getX() + Orientation.getDOWNLEFT().getX();
    int resultLocationY = startCoordinates.getY() + Orientation.getDOWNLEFT().getY();
    int resultLocationZ = startCoordinates.getZ() + Orientation.getDOWNLEFT().getZ();

    return new Tuple(resultLocationX,resultLocationY,resultLocationZ);
  }
  public static Tuple leftOf(Tuple startCoordinates){

    int resultLocationX = startCoordinates.getX() + Orientation.getLEFT().getX();
    int resultLocationY = startCoordinates.getY() + Orientation.getLEFT().getY();
    int resultLocationZ = startCoordinates.getZ() + Orientation.getLEFT().getZ();

    return new Tuple(resultLocationX,resultLocationY,resultLocationZ);
  }
  public static Tuple rightOf(Tuple startCoordinates){

    int resultLocationX = startCoordinates.getX() + Orientation.getRIGHT().getX();
    int resultLocationY = startCoordinates.getY() + Orientation.getRIGHT().getY();
    int resultLocationZ = startCoordinates.getZ() + Orientation.getRIGHT().getZ();

    return new Tuple(resultLocationX,resultLocationY,resultLocationZ);
  }

}
