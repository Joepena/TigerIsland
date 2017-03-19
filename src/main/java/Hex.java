import javafx.util.Pair;

/**
 * Created by Max on 3/16/17.
 */
public class Hex {

    private int tileNumber;
    private terrainType terrain;
    private int level;
    private Pair location;

    public enum terrainType {
        Grassland, Jungle, Lake, Rocky, Volcano
    }

    public Hex(int tileNumber, terrainType terrain) {
        this.tileNumber = tileNumber;
        this.terrain = terrain;
    }

    public int getTileNumber() {
        return tileNumber;
    }

    public void setTileNumber(int tileNumber) {
        this.tileNumber = tileNumber;
    }

    public terrainType getTerrain() {
        return terrain;
    }

    public void setTerrain(terrainType terrain) {
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
