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

    boolean isBoardEmpty() {
        return gameBoard.isOriginEmpty();
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

    public boolean canSelectBuildTotoro() {
        ArrayList<Tuple> validTotoroLocations = validTotoroPlacements();
        System.out.println(validTotoroLocations);

        if(!validTotoroLocations.isEmpty()){
            return true;
        }
        return false;
    }

    public boolean canSelectBuildTiger() {
        ArrayList<Tuple> validTigerLocations = validTigerPlacements();

        if(!validTigerLocations.isEmpty()){
            return true;
        }
        return false;
    }


    public ArrayList<Tuple> validTotoroPlacements() {
        ArrayList<SettlementDataFrame> ourSettlements = getBlackSettlements().getListOfSettlements();
        ArrayList<Tuple> validLocations = findSizeNSettlements(ourSettlements, 5, Hex.gamePieces.Totoro);
        validLocations = findValidTotoroLocations(validLocations);

        return removeDuplicateTuples(validLocations);
    }

    public ArrayList<Tuple> validTigerPlacements() {
        ArrayList<SettlementDataFrame> ourSettlements = getBlackSettlements().getListOfSettlements();
        ArrayList<Tuple> validLocations = findSizeNSettlements(ourSettlements, 1, Hex.gamePieces.Tiger);
        validLocations = findValidTigerLocations(validLocations);

        return removeDuplicateTuples(validLocations);
    }

    public ArrayList<Tuple> removeDuplicateTuples(ArrayList<Tuple> duplicateList) {
        ArrayList<Tuple> uniqueList = new ArrayList<>();

        for(int i = 0; i < duplicateList.size(); i++) {
            if(!uniqueList.contains(duplicateList.get(i)))
                uniqueList.add(duplicateList.get(i));
        }

        return uniqueList;
    }

    public ArrayList<Tuple> findSizeNSettlements(ArrayList<SettlementDataFrame> ourSettlements, int n, Hex.gamePieces gamePiece) {
        ArrayList<Tuple> tuplesInSettlements = new ArrayList<>();
        boolean existingPiece = false;

        for(int i = 0; i < ourSettlements.size(); i++) {
            if(ourSettlements.get(i).getSettlementSize() >= n) {
                existingPiece = false;
                for (int j = 0; j < ourSettlements.get(i).getSettlementSize(); j++)
                    if (gameBoard.getHex(ourSettlements.get(i).getListOfHexLocations().get(j)).getOccupiedBy() == gamePiece)
                        existingPiece = true;
                for (int j = 0; j < ourSettlements.get(i).getSettlementSize() && !existingPiece; j++)
                    tuplesInSettlements.add(ourSettlements.get(i).getListOfHexLocations().get(j));
            }
        }

        return tuplesInSettlements;
    }

    public ArrayList<Tuple> findValidTotoroLocations(ArrayList<Tuple> testableLocations) {
        ArrayList<Tuple> validLocations = new ArrayList<>();

        for(int i = 0; i < testableLocations.size(); i++) {
            Tuple currentTuple =  testableLocations.get(i);

            if(HexValidation.isLocationFree(Orientation.leftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.leftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.leftOf(currentTuple));
            if(HexValidation.isLocationFree(Orientation.rightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.rightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.rightOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.upLeftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.upLeftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.upLeftOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.upRightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.upRightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.upRightOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.downLeftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.downLeftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.downLeftOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.downRightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.downRightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.downRightOf(currentTuple));
        } // the above loop looks around every hex in the settlements for an empty placed hex

        return validLocations;
    }

    public ArrayList<Tuple> findValidTigerLocations(ArrayList<Tuple> testableLocations) {
        ArrayList<Tuple> validLocations = new ArrayList<>();

        for(int i = 0; i < testableLocations.size(); i++) {
            Tuple currentTuple =  testableLocations.get(i);

            if(HexValidation.isLocationFree(Orientation.leftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.leftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.leftOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.leftOf(currentTuple));
            if(HexValidation.isLocationFree(Orientation.rightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.rightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.rightOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.rightOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.upLeftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.upLeftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.upLeftOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.upLeftOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.upRightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.upRightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.upRightOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.upRightOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.downLeftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.downLeftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.downLeftOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.downLeftOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.downRightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.downRightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.downRightOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.downRightOf(currentTuple));
        } // the above loop looks around every hex in the settlements for an empty placed hex above level 3

        return validLocations;
    }


    public void foundSettlement(Tuple tuple, Hex.Team team) {
        gameBoard.getHex(tuple).setOccupiedBy(Hex.gamePieces.Meeple);
        gameBoard.getHex(tuple).setTeam(team);
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
