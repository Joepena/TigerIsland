import static org.junit.Assert.*;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Joe on 3/19/17.
 */
public class OrientationClassTest {

  @Test
  public void getRightHexMappingUpRight() throws Exception {
    Assert.assertEquals("UpRight returns UpLeft mapping",Orientation.Orientations.upLeft,Orientation.getRightHexMapping(Orientation.Orientations.upRight));
  }
  @Test
  public void getRightHexMappingUpLeft() throws Exception {
    Assert.assertEquals("UpLeft returns Left mapping",Orientation.Orientations.left,Orientation.getRightHexMapping(Orientation.Orientations.upLeft));
  }
  @Test
  public void getRightHexMappingLeft() throws Exception {
    Assert.assertEquals("Left returns DownLeft mapping",Orientation.Orientations.downLeft,Orientation.getRightHexMapping(Orientation.Orientations.left));
  }
  @Test
  public void getRightHexMappingDownLeft() throws Exception {
    Assert.assertEquals("DownLeft returns DownRight mapping",Orientation.Orientations.downRight,Orientation.getRightHexMapping(Orientation.Orientations.downLeft));
  }
  @Test
  public void getRightHexMappingDownRight() throws Exception {
    Assert.assertEquals("DownRight returns Right mapping",Orientation.Orientations.right,Orientation.getRightHexMapping(Orientation.Orientations.downRight));
  }
  @Test
  public void getRightHexMappingRight() throws Exception {
    Assert.assertEquals("Right returns UpRight mapping",Orientation.Orientations.upRight,Orientation.getRightHexMapping(Orientation.Orientations.right));
  }

  @Test
  public void addPairTest() {
      Tuple result = Orientation.addCoordinates(new Tuple(2,3,1), new Tuple(1,4,1));
      Assert.assertEquals("Adding Pairs 2,3,1 and 1,4,1 expect 3,7,2",new Tuple(3,7,2),result);
  }

  //TODO: ALL THESE TESTS MUST BE REDONE FOR TUPLE..

//  @Test
//  public void upLeftOfTest() {
//    Pair<Integer,Integer> desiredResult = Orientation.addPairs(new Pair<Integer,Integer>(0,0), new Pair<Integer,Integer>(2,-1));
//    Assert.assertEquals("upLeftOf returns tile up and to the left of the input", desiredResult, Orientation.upLeftOf(new Pair<Integer,Integer>(0,0)));
//  }
//    @Test
//    public void upRightOfTest() {
//        Pair<Integer,Integer> desiredResult = Orientation.addPairs(Orientation.getOriginValue(), new Pair<Integer,Integer>(2,1));
//        Assert.assertEquals("upRightOf returns tile up and to the left of the input", desiredResult, Orientation.upRightOf(Orientation.getOriginValue()));
//    }
//    @Test
//    public void leftOfTest() {
//        Pair<Integer,Integer> desiredResult = Orientation.addPairs(Orientation.getOriginValue(), new Pair<Integer,Integer>(0,-2));
//        Assert.assertEquals("leftOf returns tile to the left of the input", desiredResult, Orientation.leftOf(Orientation.getOriginValue()));
//    }
//    @Test
//    public void rightOfTest() {
//        Pair<Integer,Integer> desiredResult = Orientation.addPairs(Orientation.getOriginValue(), new Pair<Integer,Integer>(0, 2));
//        Assert.assertEquals("rightOf returns tile up and to the left of the input", desiredResult, Orientation.rightOf(Orientation.getOriginValue()));
//    }
//    @Test
//    public void downLeftOfTest() {
//        Pair<Integer,Integer> desiredResult = Orientation.addPairs(Orientation.getOriginValue(), new Pair<Integer,Integer>(-2,-1));
//        Assert.assertEquals("downLeftOf returns tile up and to the left of the input", desiredResult, Orientation.downLeftOf(Orientation.getOriginValue()));
//    }
//    @Test
//    public void downRightOfTest() {
//        Pair<Integer,Integer> desiredResult = Orientation.addPairs(Orientation.getOriginValue(), new Pair<Integer,Integer>(-2,1));
//        Assert.assertEquals("downRightOf returns tile up and to the left of the input", desiredResult, Orientation.downRightOf(Orientation.getOriginValue()));
//    }



}
