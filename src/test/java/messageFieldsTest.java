import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by T K Vicious on 4/5/2017.
 */
public class messageFieldsTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parsePlacementMessageTest() {
        messageFields m = new messageFields("GAME 5 MOVE 4 PLAYER 23 PLACED LAKE+ROCKY AT 3 5 4 6");
        System.out.println(m.toString());
    }

}
