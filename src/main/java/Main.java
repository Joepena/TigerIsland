import javafx.util.Pair;

/**
 * Created by Max on 3/19/17.
 */
public class Main {
    public static void main(String[] args) {
        Board testBoard = new Board();
        Hex testHex0 = new Hex(0, Terrain.terrainType.Volcano); Pair<Integer,Integer> testPair0 = new Pair(0,0);
        Hex testHex1 = new Hex(0, Terrain.terrainType.Jungle); Pair<Integer,Integer> testPair1 = new Pair(1,0);
        Hex testHex2 = new Hex(0, Terrain.terrainType.Grassland); Pair<Integer,Integer> testPair2 = new Pair(0,1);
        Hex testHex3 = new Hex(1, Terrain.terrainType.Volcano); Pair<Integer,Integer> testPair3 = new Pair(1,1);
        Hex testHex4 = new Hex(1, Terrain.terrainType.Rocky); Pair<Integer,Integer> testPair4 = new Pair(2,1);
        Hex testHex5 = new Hex(1, Terrain.terrainType.Jungle); Pair<Integer,Integer> testPair5 = new Pair(1,2);
        testBoard.setHex(testHex0, testPair0);
        testBoard.setHex(testHex1, testPair1);
        testBoard.setHex(testHex2, testPair2);
        testBoard.setHex(testHex3, testPair3);
        testBoard.setHex(testHex4, testPair4);
        testBoard.setHex(testHex5, testPair5);
        testBoard.printSectionedBoard();









    }
}
