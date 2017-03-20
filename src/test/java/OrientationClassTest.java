import static org.junit.Assert.*;

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


}
