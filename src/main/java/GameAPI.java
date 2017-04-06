
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.util.Pair;

import java.lang.reflect.Array;
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
    private final int BOARD_EDGE = 97;

    public GameAPI() {
        villagerCount = 20;
        totoroCount = 3;
        tigerCount = 2;
        victoryPoints = 0;
        gameBoard = new Board();
        whiteSettlements = new Settlements();
        blackSettlements = new Settlements();
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

  protected SettlementDataFrame getBlackSettlementFromLocation(Tuple coordinates){
        ArrayList<SettlementDataFrame> listOfBlackSettlements = blackSettlements.getListOfSettlements();

        for(int i = 0; i < listOfBlackSettlements.size(); i++){
            ArrayList<Tuple> hexesInSettlement = listOfBlackSettlements.get(i).getListOfHexLocations();
            for (Tuple aHexesInSettlement : hexesInSettlement) {
                if (aHexesInSettlement.equals(coordinates))
                    return listOfBlackSettlements.get(i);
            }
        }
        return null;
  }

    protected SettlementDataFrame getWhiteSettlementFromLocation(Tuple coordinates){
        ArrayList<SettlementDataFrame> listOfWhiteSettlements = whiteSettlements.getListOfSettlements();

        for(int i = 0; i < listOfWhiteSettlements.size(); i++){
            ArrayList<Tuple> hexesInSettlement = listOfWhiteSettlements.get(i).getListOfHexLocations();
            for (Tuple aHexesInSettlement : hexesInSettlement) {
                if (aHexesInSettlement.equals(coordinates))
                    return listOfWhiteSettlements.get(i);

            }
        }
        return null;
    }

  protected boolean isInSettlement(Tuple coordinates, SettlementDataFrame settlement){
      return settlement.getListOfHexLocations().contains(coordinates);
  }

    void placeTile(Tile tile, Tuple coordinates) {

        Orientation.Orientations rightOrient = Orientation.getRightHexMapping(tile.getLeftHexOrientation());

        if (isTileDestinationValid(tile, coordinates)){

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
      updateBothSettlement(whiteSettlements, blackSettlements);
    }

    private void updateBothSettlement(Settlements whiteSettlements, Settlements blackSettlements) {
      Settlements settlement = new Settlements();
      settlement.wipeSettlementSet();
      // create a copy of the availability array
      boolean[][][] array = gameBoard.getGameBoardAvailability();
      boolean[][][] copyArr = new boolean[array.length][][];

      for (int i = 0; i < array.length; i++) {
        copyArr[i] = new boolean[array[i].length][];
        for (int j = 0; j < array[i].length; j++) {
          copyArr[i][j] = new boolean[array[i][j].length];
          System.arraycopy(array[i][j], 0, copyArr[i][j], 0,
            array[i][j].length);
        }
      }

      dfsSearch(copyArr, Orientation.getOrigin(), settlement, new SettlementDataFrame(0,new Tuple(0,0,0)));
      Settlements.retriveWhiteSettlements(settlement, whiteSettlements);
      Settlements.retriveBlackSettlements(settlement, blackSettlements);
    }

    protected void dfsSearch(boolean[][][] availabilityGrid, Tuple coord, Settlements settlement, SettlementDataFrame df) {
        int xCord = coord.getX();
        int yCord = coord.getY();
        int zCord = coord.getZ();
        Tuple offset = gameBoard.calculateOffset(coord);
        //edge case
        if(xCord >= BOARD_EDGE || xCord <= -BOARD_EDGE|| yCord >= BOARD_EDGE || yCord <= -BOARD_EDGE || zCord >= BOARD_EDGE|| zCord <= -BOARD_EDGE
          || !availabilityGrid[offset.getX()][offset.getY()][offset.getZ()]) return;

        //invalidate the position
        Hex h = gameBoard.getHex(coord);


        if (h.getTeam() != Hex.Team.Neutral) {
            // initial call
            if(df.getOwnedBy() == null) {
                df.setOwnedBy(h.getTeam());
                df.setSettlementSize(1);
                df.addLocationListOfHexes(coord);
                df.setSettlementStartingLocation(coord);
                settlement.addNewSettlement(df);
            }
            else if(df.getOwnedBy() != h.getTeam()){
                // we call dfs for a new clean dataFrame
                dfsSearch(availabilityGrid,coord,settlement,new SettlementDataFrame(0,Orientation.getOrigin()));
            }
            else {
                // matching ownership
                df.setSettlementSize(df.getSettlementSize()+1);
                df.addLocationListOfHexes(coord);
            }
        }

        availabilityGrid[offset.getX()][offset.getY()][offset.getZ()] = false;

        //edge case #1: we have a team but this hex is neutral. We do not want to carry this df anymore
        if(df.getOwnedBy() != null && h.getTeam() == Hex.Team.Neutral) {

            dfsSearch(availabilityGrid, Orientation.downLeftOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()));
            dfsSearch(availabilityGrid, Orientation.downRightOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()));
            dfsSearch(availabilityGrid, Orientation.leftOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()));
            dfsSearch(availabilityGrid, Orientation.rightOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()));
            dfsSearch(availabilityGrid, Orientation.upLeftOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()));
            dfsSearch(availabilityGrid, Orientation.upRightOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()));
        }
        //edge case #2: we have a team but this hex is not
        else if(df.getOwnedBy() != null){

            // loop through all hexes same team first
            Hex.Team dfTeam = df.getOwnedBy();

            for(Orientation.Orientations orientation : Orientation.Orientations.values()) {

                if (orientation == Orientation.Orientations.origin) continue;
                // get adjacent hex
                Tuple hexLocation = Orientation.addCoordinatesByOrientation(coord,orientation);
                Hex adjacentHex = gameBoard.getHex(hexLocation);

                if(adjacentHex == null || adjacentHex.getTeam() != dfTeam) continue;
                // same team recurse through it

                dfsSearch(availabilityGrid, hexLocation, settlement, df);
            }
        }
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.downLeft), settlement,
                df);
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.downRight), settlement,
                df);
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.left), settlement, df);
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.right), settlement, df);
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.upLeft), settlement, df);
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.upRight), settlement, df);



    }


    public ArrayList<Hex> getNeighbors (Tuple coordinates) {
        ArrayList<Hex> neighbors = new ArrayList<>();

        neighbors.add(gameBoard.getHex(Orientation.upLeftOf(coordinates)));
        neighbors.add(gameBoard.getHex(Orientation.upRightOf(coordinates)));
        neighbors.add(gameBoard.getHex(Orientation.downLeftOf(coordinates)));
        neighbors.add(gameBoard.getHex(Orientation.downRightOf(coordinates)));
        neighbors.add(gameBoard.getHex(Orientation.leftOf(coordinates)));
        neighbors.add(gameBoard.getHex(Orientation.rightOf(coordinates)));

        neighbors.removeAll(Collections.singleton(null));

        return neighbors;

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
            if(isValidNukingCoordinates(coordinates)){
                validNukingLocations.add(coordinates);
            }
            neighbors = getNeighbors(coordinates);

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
