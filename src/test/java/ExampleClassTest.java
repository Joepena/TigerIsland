import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by Joe on 3/15/17.
 */
public class ExampleClassTest {

  @Test
  public void getMyNumber() throws Exception {
    int testNumber = 13;
    ExampleClass testClass = new ExampleClass(testNumber);
    // params: message, the expected result, and the actual result from the function
    assertEquals("test getMyNumber",testNumber,testClass.getMyNumber());
  }

}
