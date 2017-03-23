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

  public char getTerrainForVisualization() {
        Terrain.terrainType terrain = getTerrain();
        if(terrain == Terrain.terrainType.Grassland) {
            return 'G';
        }
        else if(terrain == Terrain.terrainType.Jungle) {
            return 'J';
        }
        else if(terrain == Terrain.terrainType.Lake) {
            return 'L';
        }
        else if(terrain == Terrain.terrainType.Rocky) {
            return 'R';
        }
        else {
            return 'V';
        }
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

  public Pair<Integer, Integer> getLocation() {
    return location;
  }

  public void setLocation(Pair location) {
    this.location = location;
  }

}
