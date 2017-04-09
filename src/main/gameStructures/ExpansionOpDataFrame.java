
/**
 * Created by Joe on 4/5/17.
 */
public class ExpansionOpDataFrame implements Comparable<ExpansionOpDataFrame>{
  private Integer expansionCost;
  private SettlementDataFrame settlementDataFrame;
  private Terrain.terrainType terrain;
  private Tuple startPoint;


  public ExpansionOpDataFrame(SettlementDataFrame settlementDataFrame, Terrain.terrainType terrain, Tuple startPoint) {
    this.settlementDataFrame = settlementDataFrame;
    this.terrain = terrain;
    this.expansionCost = 0;
    this.startPoint = startPoint;

  }

  public Integer getExpansionCost() {
    return expansionCost;
  }

  public void incrementExpansionCost(Integer hexLevel) {
    this.expansionCost += hexLevel;
  }

  public SettlementDataFrame getSettlementDataframe() {
    return settlementDataFrame;
  }

  public Terrain.terrainType getTerrain() {
    return terrain;
  }

  public Tuple getStartPoint() {
    return startPoint;
  }
  @Override
  public int compareTo(ExpansionOpDataFrame o) {
    return -1 * expansionCost.compareTo(o.expansionCost);
  }
}
