import javafx.util.Pair;
/**
 * Created by Max on 3/16/17.
 */
public class Hex {


    private int tileId;
    private Terrain.terrainType terrain;
    private int level;
    private Pair<Integer,Integer> location;

    public Hex(int tileId, Terrain.terrainType terrain) {
        this.tileId = tileId;
        this.terrain = terrain;
    }

  public int getTileId() {
    return tileId;
  }

  public void setTileId(int tileId) {
    this.tileId = tileId;
  }

  public Terrain.terrainType getTerrain() {
    return terrain;
  }

  public void setTerrain(Terrain.terrainType terrain) {
    this.terrain = terrain;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public Pair getLocation() {
    return location;
  }

  public void setLocation(Pair location) {
    this.location = location;
  }

}
