
import javafx.util.Pair;

import java.util.ArrayList;

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

    void placeTile(Tile tile, Pair<Integer, Integer> coordinatePair) {

        Orientation.Orientations rightOrient = Orientation.getRightHexMapping(tile.getLeftHexOrientation());

        if (gameBoard.isOriginEmpty()){
            coordinatePair = Orientation.getRelativeOriginValue();
        }
        if (isTileDestinationValid(tile, coordinatePair)){
            gameBoard.setHex(tile.getVolcano(), coordinatePair);
            gameBoard.setHex(tile.getLeft(), Orientation.addPairByOrientation(coordinatePair, tile.getLeftHexOrientation()));
            gameBoard.setHex(tile.getRight(), Orientation.addPairByOrientation(coordinatePair, rightOrient));
        }
    }


    protected void updateSettlements() {
      updateSettlement(whiteSettlements);
      updateSettlement(blackSettlements);
    }

    private void updateSettlement(Settlements settlement) {
      settlement.wipeSettlementSet();
      // create a copy of the availability array
      boolean [][] copyArr = new boolean[gameBoard.getGameBoardAvailability().length][];

      for(int i = 0; i < gameBoard.getGameBoardAvailability().length; i++)
      {
        boolean[] aCol = gameBoard.getGameBoardAvailability()[i];
        int   aLength = aCol.length;
        copyArr[i] = new boolean[aLength];
        System.arraycopy(aCol, 0, copyArr[i], 0, aLength);
      }

      dfsSearch(copyArr, Orientation.getOriginValue(), settlement, new SettlementDataFrame(0,new Pair<>(0,0)));
    }

    protected void dfsSearch(boolean[][] availabilityGrid, Pair<Integer,Integer> pair, Settlements settlement, SettlementDataFrame df) {
      int xCord = pair.getKey();
      int yCord = pair.getValue();

      //edge case
      if(!availabilityGrid[xCord][yCord] || pair.getKey() >= 376 || pair.getValue() >= 376) return;

      //invalidate the position
      Hex h = gameBoard.getHex(pair);


      if (h.getTeam() != Hex.Team.Neutral) {
        // initial call
        if(df.getOwnedBy() == null) {
          df.setOwnedBy(h.getTeam());
          df.setSettlementlevel(1);
          df.setSettlementStartingLocation(pair);
          settlement.addNewSettlement(df);
        }
        else if(df.getOwnedBy() != h.getTeam()){
          // we call dfs for a new clean dataFrame
          dfsSearch(availabilityGrid,pair,settlement,new SettlementDataFrame(0,new Pair<>(0,0)));
        }
        else {
          // matching ownership
          df.setSettlementlevel(df.getSettlementlevel()+1);
        }
      }

      availabilityGrid[xCord][yCord] = false;

      //edge case #1: we have a team but this hex is neutral. We do not want to carry this df anymore
      if(df.getOwnedBy() != null && h.getTeam() == Hex.Team.Neutral) {

        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.downLeft), settlement,
          new SettlementDataFrame(0,new Pair<>(0,0)));
        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.downRight), settlement,
          new SettlementDataFrame(0,new Pair<>(0,0)));
        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.left), settlement, new SettlementDataFrame(0,new Pair<>(0,0)));
        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.right), settlement, new SettlementDataFrame(0,new Pair<>(0,0)));
        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.upLeft), settlement, new SettlementDataFrame(0,new Pair<>(0,0)));
        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.upRight), settlement, new SettlementDataFrame(0,new Pair<>(0,0)));
      }
      //edge case #2: we have a team but this hex is not
      else if(df.getOwnedBy() != null){

        // loop through all hexes same team first
        Hex.Team dfTeam = df.getOwnedBy();

        for(Orientation.Orientations orientation : Orientation.Orientations.values()) {

          if (orientation == Orientation.Orientations.origin) continue;
          // get adjacent hex
          Pair<Integer,Integer> hexLocation = Orientation.addPairByOrientation(pair,orientation);
          Hex adjacentHex = gameBoard.getHex(hexLocation);

          if(adjacentHex == null || adjacentHex.getTeam() != dfTeam) continue;
          // same team recurse through it

          dfsSearch(availabilityGrid, hexLocation, settlement, df);
        }
      }
        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.downLeft), settlement,
          df);
        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.downRight), settlement,
          df);
        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.left), settlement, df);
        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.right), settlement, df);
        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.upLeft), settlement, df);
        dfsSearch(availabilityGrid,
          Orientation.addPairByOrientation(pair, Orientation.Orientations.upRight), settlement, df);



    }




    //ArrayList<Pair<Integer, Integer>> getValidTileLocations() {}



    ArrayList<Pair<Integer,Integer>> getValidNukingLocations() {
        if(gameBoard.isOriginEmpty()){
            return null;
        }
        return null;
    }

    private boolean isValidNukingCoordinates(Pair<Integer, Integer> volcanoCoordinates){
        Orientation.Orientations leftHexOrientation = Orientation.Orientations.upLeft;

        return isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, leftHexOrientation));
    }

    private boolean isValidTileNukingPosition(TilePositionCoordinates tilePositionCoordinates){

        Hex hexUnderVolcano = gameBoard.getHex(tilePositionCoordinates.getVolcanoCoordinates());
        Hex hexUnderLeft = gameBoard.getHex(tilePositionCoordinates.getLeftHexCoordinates());
        Hex hexUnderRight = gameBoard.getHex(tilePositionCoordinates.getRightHexCoordinates());

        int settlementPiecesNuked = 0;
        int settlementSize = 3;

        if(!HexValidation.isValidVolcanoPlacement(tilePositionCoordinates.getVolcanoCoordinates(), gameBoard))
            return false;

        if(hexUnderVolcano.getLevel() != hexUnderLeft.getLevel() || hexUnderLeft.getLevel() != hexUnderRight.getLevel())
            return false;

        if(hexUnderVolcano.getOccupiedBy() != Hex.gamePieces.empty){
            settlementPiecesNuked++;
            if(!HexValidation.isValidHexEruption(tilePositionCoordinates.getVolcanoCoordinates(), gameBoard))
                return false;
        }
        if(hexUnderLeft.getOccupiedBy() != Hex.gamePieces.empty){
            settlementPiecesNuked++;
            if(!HexValidation.isValidHexEruption(tilePositionCoordinates.getLeftHexCoordinates(), gameBoard))
                return false;
        }
        if(hexUnderRight.getOccupiedBy() != Hex.gamePieces.empty){
            settlementPiecesNuked++;
            if(!HexValidation.isValidHexEruption(tilePositionCoordinates.getRightHexCoordinates(), gameBoard))
                return false;
            }

        if(settlementPiecesNuked == settlementSize)
            return false;


        return true;
    }

    public boolean isTileDestinationValid(Tile tile, Pair<Integer, Integer> destCoordPair){
        Pair<Integer,Integer> originvalue = Orientation.getOriginValue();
        Pair<Integer, Integer> absDestCoordPair = Orientation.addPairs(destCoordPair, originvalue);

        if (isTileConnected(tile, absDestCoordPair)){
            return true;
        }

        if (gameBoard.isOriginEmpty()){
            return true;
        }

        return false;
    }

    public boolean isTileConnected(Tile tile, Pair<Integer, Integer>absDestCoordPair){

        Pair<Integer, Integer> leftCoordPair = Orientation.addPairByOrientation(absDestCoordPair, tile.getLeftHexOrientation());
        Pair<Integer, Integer> rightCoordPair = Orientation.addPairByOrientation(absDestCoordPair, Orientation.getRightHexMapping(tile.getLeftHexOrientation()));

        boolean valid = HexValidation.existsAdjacentHex(absDestCoordPair, gameBoard);
        valid = valid || HexValidation.existsAdjacentHex(leftCoordPair, gameBoard);
        valid = valid || HexValidation.existsAdjacentHex(rightCoordPair, gameBoard);
        return valid;
    }
}
