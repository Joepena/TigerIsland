import java.util.*;

/**
 * Created by Troy on 3/22/17.
 */
public class GameAPI {

    protected Board gameBoard;
    private int villagerCount;
    private int totoroCount;
    private int tigerCount;
    private int victoryPoints;
    private Settlements whiteSettlements;
    private Settlements blackSettlements;
    protected GameAPIUtil APIUtils;

    public GameAPI() {
        villagerCount = 20;
        totoroCount = 3;
        tigerCount = 2;
        victoryPoints = 0;
        gameBoard = new Board();
        whiteSettlements = new Settlements();
        blackSettlements = new Settlements();
        APIUtils = new GameAPIUtil(this);
    }


    //Getters and Setters

    public int getVillagerCount() {
        return villagerCount;
    }

    public void setVillagerCount(int villagerCount) {
        this.villagerCount = villagerCount;
    }

    public int getTotoroCount() {
        return totoroCount;
    }

    public void setTotoroCount(int totoroCount) {
        this.totoroCount = totoroCount;
    }

    public int getTigerCount() {
        return tigerCount;
    }

    public void setTigerCount(int tigerCount) {
        this.tigerCount = tigerCount;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public Settlements getWhiteSettlements() {
    return whiteSettlements;
  }

    public Settlements getBlackSettlements() {
    return blackSettlements;
  }


    void placeTile(Tile tile, Tuple coordinates) {

        Orientation.Orientations rightOrient = Orientation.getRightHexMapping(tile.getLeftHexOrientation());

        if (APIUtils.isTileDestinationValid(tile, coordinates)){

            gameBoard.setHex(tile.getVolcano(), coordinates);
            gameBoard.setHex(tile.getLeft(), Orientation.addCoordinatesByOrientation(coordinates, tile.getLeftHexOrientation()));
            gameBoard.setHex(tile.getRight(), Orientation.addCoordinatesByOrientation(coordinates, rightOrient));
        }
    }

    void placeFirstTile() {

        Tuple originCoords = new Tuple(0,0,0);
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Volcano), originCoords);
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Jungle), Orientation.addCoordinatesByOrientation(originCoords, Orientation.Orientations.upLeft));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Lake), Orientation.addCoordinatesByOrientation(originCoords, Orientation.Orientations.upRight));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Rocky), Orientation.addCoordinatesByOrientation(originCoords, Orientation.Orientations.downLeft));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Grassland), Orientation.addCoordinatesByOrientation(originCoords, Orientation.Orientations.downRight));
    }


    protected void updateSettlements() {
      APIUtils.updateBothSettlement();
    }

    ArrayList<Tuple> getValidNukingLocations() {
        if(gameBoard.isOriginEmpty()){
            return null;
        }
        ArrayList<Tuple> validNukingLocations = new ArrayList<>();
        HashMap<Tuple,Integer> traversedLocations = new HashMap<>();

        ArrayList<Hex> neighbors;

        Queue<Tuple> bfsQueue = new ArrayDeque<>();

        bfsQueue.add(Orientation.getOrigin());
        traversedLocations.put(Orientation.getOrigin(), 1);

        while(!bfsQueue.isEmpty()){
            Tuple coordinates = bfsQueue.remove();
            if(APIUtils.isValidNukingCoordinates(coordinates)){
                validNukingLocations.add(coordinates);
            }
            neighbors = APIUtils.getNeighbors(coordinates);

            for(Hex neighbor : neighbors) {
                if(!traversedLocations.containsKey(neighbor.getLocation())) {
                    traversedLocations.put(neighbor.getLocation(), 1);
                    bfsQueue.add(neighbor.getLocation());
                }
            }

        }


        return validNukingLocations;
    }

    public boolean canSelectBuildTotoro() {

        ArrayList<SettlementDataFrame> blackSettlements = getBlackSettlements().getListOfSettlements();

        for(int i = 0; i < blackSettlements.size(); i++) {
            if(blackSettlements.get(i).getSettlementSize() >= 5)
                return true;
        }
        return false;
    }

    public

}
