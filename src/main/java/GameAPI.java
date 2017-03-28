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

    public GameAPI() {
        villagerCount = 20;
        totoroCount = 3;
        tigerCount = 2;
        victoryPoints = 0;
        gameBoard = new Board();
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

    void placeTile(Tile tile, Pair<Integer, Integer> coordinatePair) {

        Orientation.Orientations rightOrient = Orientation.getRightHexMapping(tile.getLeftHexOrientation());

        if (isTileDestinationValid(tile, coordinatePair)){
            gameBoard.setHex(tile.getVolcano(), coordinatePair);
            gameBoard.setHex(tile.getLeft(), Orientation.addPairByOrientation(coordinatePair, tile.getLeftHexOrientation()));
            gameBoard.setHex(tile.getRight(), Orientation.addPairByOrientation(coordinatePair, rightOrient));
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

        if (gameBoard.isOriginEmpty() && (absDestCoordPair.equals(originvalue))){
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
