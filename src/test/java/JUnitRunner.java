
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * Created by Joe on 3/30/17.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  BoardTest.class,
  GameAPITest.class,
  OrientationClassTest.class,
  TilePlacementTests.class,
  TileTest.class,
  UpdateSettlementsTest.class,
  HexValidationTest.class,
  TotoroValidationTest.class,
  TigerValidationTest.class,
  ExpansionOptionsTest.class,
  TileValidationTest.class,
  SettlementCreationListTest.class,
  MessageParserTest.class
})

public class JUnitRunner {

}
