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
    Assert.assertEquals("UpRight returns UpLeft mapping",Orientation.UPLEFT,Orientation.getRightHexMapping(Orientation.UPRIGHT));
  }
  @Test
  public void getRightHexMappingUpLeft() throws Exception {
    Assert.assertEquals("UpLeft returns Left mapping",Orientation.LEFT,Orientation.getRightHexMapping(Orientation.UPLEFT));
  }
  @Test
  public void getRightHexMappingLeft() throws Exception {
    Assert.assertEquals("Left returns DownLeft mapping",Orientation.DOWNLEFT,Orientation.getRightHexMapping(Orientation.LEFT));
  }
  @Test
  public void getRightHexMappingDownLeft() throws Exception {
    Assert.assertEquals("DownLeft returns DownRight mapping",Orientation.DOWNRIGHT,Orientation.getRightHexMapping(Orientation.DOWNLEFT));
  }
  @Test
  public void getRightHexMappingDownRight() throws Exception {
    Assert.assertEquals("DownRight returns Right mapping",Orientation.RIGHT,Orientation.getRightHexMapping(Orientation.DOWNRIGHT));
  }
  @Test
  public void getRightHexMappingRight() throws Exception {
    Assert.assertEquals("Right returns UpRight mapping",Orientation.UPRIGHT,Orientation.getRightHexMapping(Orientation.RIGHT));
  }

  @Test
  public void addPairTest() {
      Pair<Integer,Integer> result = Orientation.addPairs(new Pair<Integer,Integer>(2,3), new Pair<Integer,Integer>(1,4));
      Assert.assertEquals("Adding Pairs 2,3 and 1,4 expect 3,7",new Pair<Integer,Integer>(3,7),result);
  }

  @Test
  public void upLeftOfTest() {
    Pair<Integer,Integer> desiredResult = Orientation.addPairs(new Pair<Integer,Integer>(0,0), new Pair<Integer,Integer>(2,-1));
    Assert.assertEquals("upLeftOf returns tile up and to the left of the input", desiredResult, Orientation.upLeftOf(new Pair<Integer,Integer>(0,0)));
  }
    @Test
    public void upRightOfTest() {
        Pair<Integer,Integer> desiredResult = Orientation.addPairs(Orientation.ORIGIN, new Pair<Integer,Integer>(2,1));
        Assert.assertEquals("upRightOf returns tile up and to the left of the input", desiredResult, Orientation.upRightOf(Orientation.ORIGIN));
    }
    @Test
    public void leftOfTest() {
        Pair<Integer,Integer> desiredResult = Orientation.addPairs(Orientation.ORIGIN, new Pair<Integer,Integer>(0,-2));
        Assert.assertEquals("leftOf returns tile to the left of the input", desiredResult, Orientation.leftOf(Orientation.ORIGIN));
    }
    @Test
    public void rightOfTest() {
        Pair<Integer,Integer> desiredResult = Orientation.addPairs(Orientation.ORIGIN, new Pair<Integer,Integer>(0, 2));
        Assert.assertEquals("rightOf returns tile up and to the left of the input", desiredResult, Orientation.rightOf(Orientation.ORIGIN));
    }
    @Test
    public void downLeftOfTest() {
        Pair<Integer,Integer> desiredResult = Orientation.addPairs(Orientation.ORIGIN, new Pair<Integer,Integer>(-2,-1));
        Assert.assertEquals("downLeftOf returns tile up and to the left of the input", desiredResult, Orientation.downLeftOf(Orientation.ORIGIN));
    }
    @Test
    public void downRightOfTest() {
        Pair<Integer,Integer> desiredResult = Orientation.addPairs(Orientation.ORIGIN, new Pair<Integer,Integer>(-2,1));
        Assert.assertEquals("downRightOf returns tile up and to the left of the input", desiredResult, Orientation.downRightOf(Orientation.ORIGIN));
    }



}
