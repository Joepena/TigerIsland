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
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Jungle), Orientation.upLeftOf(originCoords));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Lake), Orientation.upRightOf(originCoords));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Rocky), Orientation.downLeftOf(originCoords));
        gameBoard.setHex(new Hex(1, Terrain.terrainType.Grassland), Orientation.downRightOf(originCoords));
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

    //Checking if any tile rotations are valid
    public boolean isValidNukingCoordinates(Tuple volcanoCoordinates){
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.downLeft)))
            return true;
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.downRight)))
            return true;
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.upLeft)))
            return true;
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.upRight)))
            return true;
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.left)))
            return true;
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.right)))
            return true;

        return false;
    }

    public boolean isValidTileNukingPosition(TilePositionCoordinates tilePositionCoordinates){

        Hex hexUnderVolcano = gameBoard.getHex(tilePositionCoordinates.getVolcanoCoordinates());
        Hex hexUnderLeft = gameBoard.getHex(tilePositionCoordinates.getLeftHexCoordinates());
        Hex hexUnderRight = gameBoard.getHex(tilePositionCoordinates.getRightHexCoordinates());

        if(hexUnderLeft == null || hexUnderRight == null || hexUnderVolcano == null)
            return false;

        if(!HexValidation.isValidVolcanoPlacement(tilePositionCoordinates.getVolcanoCoordinates(),
          gameBoard))
            return false;

        if(hexUnderVolcano.getLevel() != hexUnderLeft.getLevel() || hexUnderLeft.getLevel() != hexUnderRight.getLevel())
            return false;

        if(hexUnderVolcano.getTileId() == hexUnderLeft.getTileId() && hexUnderRight.getTileId() == hexUnderVolcano.getTileId())
            return false;

        if(isVolcanoNukingWholeSettlement(tilePositionCoordinates))
            return false;

        if(isLeftHexNukingWholeSettlement(tilePositionCoordinates))
            return false;

        if(isRightHexNukingWholeSettlement(tilePositionCoordinates))
            return false;

        if(TempHexHelpers.hasTigerTotoro(hexUnderVolcano) || TempHexHelpers.hasTigerTotoro(hexUnderLeft) ||
                TempHexHelpers.hasTigerTotoro(hexUnderRight))
            return false;

        return true;
    }

    protected boolean isVolcanoNukingWholeSettlement(TilePositionCoordinates tileCoordinates) {
       return isTilePlacementNukingWholeSettlementOfHexOne(tileCoordinates.getVolcanoCoordinates(),
                tileCoordinates.getLeftHexCoordinates(), tileCoordinates.getRightHexCoordinates());
    }
    protected boolean isLeftHexNukingWholeSettlement(TilePositionCoordinates tileCoordinates) {
        return isTilePlacementNukingWholeSettlementOfHexOne(tileCoordinates.getLeftHexCoordinates(),
                tileCoordinates.getVolcanoCoordinates(), tileCoordinates.getRightHexCoordinates());
    }
    protected boolean isRightHexNukingWholeSettlement(TilePositionCoordinates tileCoordinates) {
        return isTilePlacementNukingWholeSettlementOfHexOne(tileCoordinates.getRightHexCoordinates(),
                tileCoordinates.getLeftHexCoordinates(), tileCoordinates.getVolcanoCoordinates());
    }

    protected boolean isTilePlacementNukingWholeSettlementOfHexOne(Tuple location1, Tuple location2, Tuple location3){
        Hex hexOfSettlement = gameBoard.getHex(location1);

        if (hexOfSettlement.getOccupiedBy() != Hex.gamePieces.empty) {
            if (hexOfSettlement.getTeam() == Hex.Team.Black) {
                int nukeCount = 1;
                SettlementDataFrame settlement = getBlackSettlementFromLocation(location1);
                if (settlement == null) {
                } else {
                    if(isInSettlement(location2, settlement))
                        nukeCount++;
                    if(isInSettlement(location3, settlement))
                        nukeCount++;
                    if(settlement.getSettlementSize() == nukeCount)
                        return true;
                }
            } else if (hexOfSettlement.getTeam() == Hex.Team.White) {
                int nukeCount = 1;
                SettlementDataFrame settlement = getWhiteSettlementFromLocation(location1);
                if (settlement == null) {
                } else {
                   if(isInSettlement(location2, settlement))
                       nukeCount++;
                   if(isInSettlement(location3, settlement))
                       nukeCount++;
                   if(settlement.getSettlementSize() == nukeCount)
                       return true;
                }
            }
        }
        return false;
    }

    public boolean isTileDestinationValid(Tile tile, Tuple destCoordPair){
        if (isTileConnected(tile, destCoordPair)){
            return true;
        }

        if (gameBoard.isOriginEmpty()){
            return true;
        }

        return false;
    }

    public boolean isTileConnected(Tile tile, Tuple absDestCoordPair){

        Tuple leftCoordPair = Orientation.addCoordinatesByOrientation(absDestCoordPair, tile.getLeftHexOrientation());
        Tuple rightCoordPair = Orientation.addCoordinatesByOrientation(absDestCoordPair, Orientation.getRightHexMapping(tile.getLeftHexOrientation()));

        boolean valid = HexValidation.existsAdjacentHex(absDestCoordPair, gameBoard);
        valid = valid || HexValidation.existsAdjacentHex(leftCoordPair, gameBoard);
        valid = valid || HexValidation.existsAdjacentHex(rightCoordPair, gameBoard);
        return valid;

    }
    
    public boolean canSelectBuildTotoro() {

        ArrayList<SettlementDataFrame> blackSettlements = getBlackSettlements().getListOfSettlements();

        for(int i = 0; i < blackSettlements.size(); i++) {
            if(blackSettlements.get(i).getSettlementSize() >= 5)
                return true;
        }
        return false;
    }

}
