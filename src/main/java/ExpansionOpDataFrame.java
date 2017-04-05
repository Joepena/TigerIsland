/**
 * Created by Joe on 4/5/17.
 */
public class ExpansionOpDataFrame implements Comparable<ExpansionOpDataFrame>{
  private Integer expansionCost;
  private Tuple expansionStart;
  private Terrain.terrainType terrain;
  private Hex.Team ownedBy;

  public ExpansionOpDataFrame(Tuple expansionStart, Terrain.terrainType terrain, Hex.Team ownedBy, int hexLevel) {
    this.expansionStart = expansionStart;
    this.terrain = terrain;
    this.ownedBy = ownedBy;
    this.expansionCost = hexLevel;
  }

  public Integer getExpansionCost() {
    return expansionCost;
  }

  public void incrementExpansionCost(Integer hexLevel) {
    this.expansionCost += hexLevel;
  }

  public Tuple getExpansionStart() {
    return expansionStart;
  }

  public Terrain.terrainType getTerrain() {
    return terrain;
  }

  public Hex.Team getOwnedBy() {
    return ownedBy;
  }

  @Override
  public int compareTo(ExpansionOpDataFrame o) {
    return -1 * expansionCost.compareTo(o.expansionCost);
  }
}
