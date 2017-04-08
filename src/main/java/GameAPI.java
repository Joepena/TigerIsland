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

    public int getTotoroCount() {
        return totoroCount;
    }

    public int getTigerCount() {
        return tigerCount;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public Settlements getWhiteSettlements() {
        return whiteSettlements;
    }

    public Settlements getBlackSettlements() {
        return blackSettlements;
    }

    boolean isBoardEmpty() {
        return gameBoard.isOriginEmpty();
    }

    void decrementVillagersBy(int hexLevel) {
      villagerCount-=hexLevel;
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

    public ArrayList<ExpansionOpDataFrame> getExpansionOptions(Hex.Team targetTeam) {
      return APIUtils.findExpansionOptionsFor(targetTeam);
    }
    public void performLandGrab(Tuple tuple, Hex.Team team) {
      APIUtils.performLandGrab(tuple, team);
    }
    public ArrayList<Tuple> getValidNukingLocations() {
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


  public ArrayList<Tuple> findListOfValidSettlementLocations() {
    return APIUtils.findListOfValidSettlementLocation(Orientation.getOrigin(), new boolean[194][194][194], new ArrayList<Tuple>());
  }

  public ArrayList<Tuple> getAvailableTilePlacement() {
    ArrayList<Tuple> list = new ArrayList<>();
    boolean[][][] hexCheckedforPlacement = new boolean[194][194][194];
    APIUtils.tileValidationListFinder(Orientation.getOrigin(),hexCheckedforPlacement,list);
    return list;
  }

    public boolean canSelectBuildTotoro(Hex.Team team) {
        ArrayList<Tuple> validTotoroLocations = validTotoroPlacements(team);

        if(!validTotoroLocations.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean canSelectBuildTiger(Hex.Team team) {
        ArrayList<Tuple> validTigerLocations = validTigerPlacements(team);

        if(!validTigerLocations.isEmpty()){
            return true;
        }
        return false;
    }


    public ArrayList<Tuple> validTotoroPlacements(Hex.Team team) {
        ArrayList<SettlementDataFrame> ourSettlements = new ArrayList<>();
        if (team == Hex.Team.White)
            ourSettlements = getWhiteSettlements().getListOfSettlements();
        else if (team == Hex.Team.Black)
            ourSettlements = getBlackSettlements().getListOfSettlements();

        ArrayList<Tuple> validLocations = APIUtils.findSizeNSettlements(ourSettlements, 5, Hex.gamePieces.Totoro);
        validLocations = APIUtils.findValidTotoroLocations(validLocations);

        return APIUtils.removeDuplicateTuples(validLocations);
    }

    public ArrayList<Tuple> validTigerPlacements(Hex.Team team) {
        ArrayList<SettlementDataFrame> ourSettlements = new ArrayList<>();
        if (team == Hex.Team.White)
            ourSettlements = getWhiteSettlements().getListOfSettlements();
        else if (team == Hex.Team.Black)
            ourSettlements = getBlackSettlements().getListOfSettlements();

        ArrayList<Tuple> validLocations = APIUtils.findSizeNSettlements(ourSettlements, 1, Hex.gamePieces.Tiger);
        validLocations = APIUtils.findValidTigerLocations(validLocations);

        return APIUtils.removeDuplicateTuples(validLocations);
    }


    public void foundSettlement(Tuple tuple, Hex.Team team) {
        gameBoard.getHex(tuple).setOccupiedBy(Hex.gamePieces.Meeple);
        gameBoard.getHex(tuple).setTeam(team);
        decrementVillagersBy(gameBoard.getHex(tuple).getLevel());
    }

    public void createTotoroSanctuary(Tuple tuple, Hex.Team team) {
        gameBoard.getHex(tuple).setOccupiedBy(Hex.gamePieces.Totoro);
        gameBoard.getHex(tuple).setTeam(team);
    }

    public void createTigerPlayground(Tuple tuple, Hex.Team team) {
        gameBoard.getHex(tuple).setOccupiedBy(Hex.gamePieces.Tiger);
        gameBoard.getHex(tuple).setTeam(team);
    }

}
