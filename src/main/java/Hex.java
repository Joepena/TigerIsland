import javafx.util.Pair;
/**
 * Created by Max on 3/16/17.
 */
public class Hex {

    private int tileId;
    private int level;
    private Terrain.terrainType terrain;
    private Tuple location;
    private gamePieces occupiedBy;
    private Team team;

    public Hex(int tileId, Terrain.terrainType terrain) {
        this.tileId = tileId;
        this.terrain = terrain;
        this.level = 0;
        this.team = Team.Neutral;
        this.occupiedBy = gamePieces.empty;
    }

    public static enum gamePieces {
        Meeple, Totoro, Tiger, empty;
    }

    public static enum Team {
        Black, White, Neutral;
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

    public void incrementLevel() {
        this.level = this.level + 1;
    }

    public Tuple getLocation() {
        return location;
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

    public void setLocation(Tuple location) {
        this.location = location;
    }

    public gamePieces getOccupiedBy() {
        return occupiedBy;
    }

    public void setOccupiedBy(gamePieces occupiedBy) {
        this.occupiedBy = occupiedBy;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void placeMeeple(Team team){
        this.setOccupiedBy(gamePieces.Meeple);
        this.setTeam(team);
    }


    public boolean equals(Hex hex){
        return (this.getTerrain() == hex.getTerrain() &&
                this.getTileId() == hex.getTileId());
    }
}
