/**
 * Created by Joe on 4/5/17.
 */
public class ExpansionOpDataFrame implements Comparable<ExpansionOpDataFrame>{
  private Integer expansionCost;
  private Tuple expansionStart;
  private Terrain.terrainType terrain;


  public ExpansionOpDataFrame(Tuple expansionStart, Terrain.terrainType terrain) {
    this.expansionStart = expansionStart;
    this.terrain = terrain;
    this.expansionCost = 0;
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

  @Override
  public int compareTo(ExpansionOpDataFrame o) {
    return -1 * expansionCost.compareTo(o.expansionCost);
  }
}
