
import java.util.ArrayList;
import javafx.util.Pair;

/**
 * Created by Joe on 3/26/17.
 */
public class SettlementDataFrame implements Comparable<SettlementDataFrame>{
  private Integer settlementSize;
  private Tuple settlementStartingLocation;

  private ArrayList<Tuple> listOfHexes;
  private Hex.Team ownedBy;


  public SettlementDataFrame(int settlementSize,
    Tuple settlementStartingLocation) {
    this.settlementSize = settlementSize;
    this.settlementStartingLocation = settlementStartingLocation;
    this.listOfHexes = new ArrayList<>();
  }

  public Integer getSettlementSize() {
    return settlementSize;
  }

  public Tuple getSettlementStartingLocation() {
    return settlementStartingLocation;
  }

  public void setSettlementSize(Integer settlementSize) {
    this.settlementSize = settlementSize;
  }

  public void setSettlementStartingLocation(
    Tuple settlementStartingLocation) {
    this.settlementStartingLocation = settlementStartingLocation;
  }

  public void setOwnedBy(Hex.Team ownedBy) {
    this.ownedBy = ownedBy;
  }

  public Hex.Team getOwnedBy() {
    return ownedBy;
  }

  public ArrayList<Tuple> getListOfHexLocations() {
    return listOfHexes;
  }
  public void addLocationListOfHexes(Tuple locationOfHex) {
    listOfHexes.add(locationOfHex);
  }

  @Override
  public int compareTo(SettlementDataFrame o) {
    return -1 * settlementSize.compareTo(o.settlementSize);
  }

}


