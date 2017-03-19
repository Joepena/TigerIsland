import javafx.util.Pair;
/**
 * Created by Max on 3/16/17.
 */
public class Hex {

    public int tileId;
    public Terrain.terrainType terrain;
    public int level;
    public Pair location;

    public Hex(int tileId, Terrain.terrainType terrain) {
        this.tileId = tileId;
        this.terrain = terrain;
    }

}
