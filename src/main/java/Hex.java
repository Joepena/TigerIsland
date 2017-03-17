import javafx.util.Pair;

/**
 * Created by Max on 3/16/17.
 */
public class Hex {

    public int tileNumber;
    public terrainType terrain;
    public int level;
    public Pair location;

    public enum terrainType {
        Grassland, Jungle, Lake, Rocky, Volcano;
    }

    public Hex(int tileNumber, terrainType terrain) {
        this.tileNumber = tileNumber;
        this.terrain = terrain;
    }

}
