import javafx.util.Pair;

/**
 * Created by Joe on 3/26/17.
 */
public class SettlementDataFrame {
  private int settlementlevel;
  private Pair<Integer,Integer> settlementStartingLocation;

  public SettlementDataFrame(int settlementlevel,
    Pair<Integer, Integer> settlementStartingLocation) {
    this.settlementlevel = settlementlevel;
    this.settlementStartingLocation = settlementStartingLocation;
  }

  public int getSettlementlevel() {
    return settlementlevel;
  }

  public Pair<Integer, Integer> getSettlementStartingLocation() {
    return settlementStartingLocation;
  }
}
