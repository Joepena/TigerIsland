/**
 * Created by Joe on 3/26/17.
 */
public class SettlementDataFrame {
  private int settlementlevel;
  private int settlementStartingLocation;

  public SettlementDataFrame(int settlementlevel, int settlementStartingLocation) {
    this.settlementlevel = settlementlevel;
    this.settlementStartingLocation = settlementStartingLocation;
  }

  public int getSettlementlevel() {
    return settlementlevel;
  }

  public int getSettlementStartingLocation() {
    return settlementStartingLocation;
  }
}
