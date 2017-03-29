import javafx.util.Pair;

/**
 * Created by Joe on 3/26/17.
 */
public class SettlementDataFrame implements Comparable<SettlementDataFrame>{
  private Integer settlementlevel;
  private Pair<Integer,Integer> settlementStartingLocation;
  private Hex.Team ownedBy;

  public SettlementDataFrame(int settlementlevel,
    Pair<Integer, Integer> settlementStartingLocation) {
    this.settlementlevel = settlementlevel;
    this.settlementStartingLocation = settlementStartingLocation;
  }

  public Integer getSettlementlevel() {
    return settlementlevel;
  }

  public Pair<Integer, Integer> getSettlementStartingLocation() {
    return settlementStartingLocation;
  }

  public void setSettlementlevel(Integer settlementlevel) {
    this.settlementlevel = settlementlevel;
  }

  public void setSettlementStartingLocation(
    Pair<Integer, Integer> settlementStartingLocation) {
    this.settlementStartingLocation = settlementStartingLocation;
  }

  public void setOwnedBy(Hex.Team ownedBy) {
    this.ownedBy = ownedBy;
  }

  public Hex.Team getOwnedBy() {
    return ownedBy;
  }

  @Override
  public int compareTo(SettlementDataFrame o) {
    return -1 * settlementlevel.compareTo(o.settlementlevel);
  }
}
