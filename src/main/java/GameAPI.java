
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

      dfsSearch(copyArr, Orientation.getOriginValue(), settlement, new SettlementDataFrame(0,new Pair<Integer, Integer>(0,0)));
    }

    protected void dfsSearch(boolean[][] availabilityGrid, Pair<Integer,Integer> pair, Settlements settlement, SettlementDataFrame df) {
      int xCord = pair.getKey();
      int yCord = pair.getValue();

      //edge case
      if(!availabilityGrid[xCord][yCord] || pair.getKey() >= 376 || pair.getValue() >= 376) return;

      //invalidate the position
      availabilityGrid[xCord][yCord] = false;
      Hex h = gameBoard.getHex(pair);


      if (h.getTeam() != Hex.Team.Neutral) {
        // initial call
        if(df.getOwnedBy() == null) {
          df.setOwnedBy(h.getTeam());
          df.setSettlementlevel(h.getLevel() + df.getSettlementlevel());
          df.setSettlementStartingLocation(pair);
        }
        else if(df.getOwnedBy() != h.getTeam()){
          // we call dfs for a new clean dataFrame
          dfsSearch(availabilityGrid,pair,settlement,new SettlementDataFrame(0,new Pair<Integer, Integer>(0,0)));
          return;
        }
        else {
          // matching ownership
          df.setSettlementlevel(h.getLevel() + df.getSettlementlevel());
        }
      }

      dfsSearch(availabilityGrid,Orientation.addPairByOrientation(pair, Orientation.Orientations.downLeft),settlement,df);
      dfsSearch(availabilityGrid,Orientation.addPairByOrientation(pair, Orientation.Orientations.downRight),settlement,df);
      dfsSearch(availabilityGrid,Orientation.addPairByOrientation(pair, Orientation.Orientations.left),settlement,df);
      dfsSearch(availabilityGrid,Orientation.addPairByOrientation(pair, Orientation.Orientations.right),settlement,df);
      dfsSearch(availabilityGrid,Orientation.addPairByOrientation(pair, Orientation.Orientations.upLeft),settlement,df);
      dfsSearch(availabilityGrid,Orientation.addPairByOrientation(pair, Orientation.Orientations.upRight),settlement,df);

      if(settlement.getListOfSettlements().contains(df)) {
        settlement.getListOfSettlements().remove(df);
        settlement.getListOfSettlements().add(df);
      }
      else {
        settlement.addNewSettlement(df);
      }


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
