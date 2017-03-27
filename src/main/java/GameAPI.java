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

        gameBoard.setHex(tile.getVolcano(), coordinatePair);
        gameBoard.setHex(tile.getLeft(), Orientation.addPairByOrientation(coordinatePair, tile.getLeftHexOrientation()));
        gameBoard.setHex(tile.getRight(), Orientation.addPairByOrientation(coordinatePair, rightOrient));
    }


    //ArrayList<Pair<Integer, Integer>> getValidTileLocations() {}


    ArrayList<Pair<Integer,Integer>> getValidNukingLocations() {
        if(gameBoard.isOriginEmpty()){
            return null;
        }
        return null;
    }

    boolean isValidNukingCoordinates(Pair<Integer, Integer> volcanoCoordinates){
        Orientation.Orientations leftHexOrientation = Orientation.Orientations.upLeft;

        return isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, leftHexOrientation));
    }

    boolean isValidTileNukingPosition(TilePositionCoordinates tilePositionCoordinates){
        if(!HexValidation.isValidVolcanoPlacement(tilePositionCoordinates.getVolcanoCoordinates(), gameBoard)){
            return false;
        }


        return true;
    }



}
