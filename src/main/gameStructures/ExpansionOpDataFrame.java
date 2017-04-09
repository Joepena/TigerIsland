/**
 * Created by Joe on 4/5/17.
 */
public class ExpansionOpDataFrame implements Comparable<ExpansionOpDataFrame>{
  private Integer expansionCost;
  private SettlementDataFrame settlementDataFrame;
  private Terrain.terrainType terrain;
  private Tuple expansionStart;


  public ExpansionOpDataFrame(SettlementDataFrame settlementDataFrame, Terrain.terrainType terrain, Tuple expansionStart) {
    this.settlementDataFrame = settlementDataFrame;
    this.terrain = terrain;
    this.expansionCost = 0;
    this.expansionStart = expansionStart;

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

  public Tuple getExpansionStart() {
    return expansionStart;
  }

  public Terrain.terrainType getTerrain() {
    return terrain;
  }

  @Override
  public int compareTo(ExpansionOpDataFrame o) {
    return -1 * expansionCost.compareTo(o.expansionCost);
  }
}
