import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by TomasK on 3/27/2017.
 */
public class GameAPITest {
    Tile testTile;
    GameAPI game = new GameAPI();

    @Before
    public void setUp() throws Exception {

        testTile = new Tile(3, Terrain.terrainType.Jungle, Terrain.terrainType.Lake, Orientation.Orientations.left);

    }

    public void createLandMass() throws Exception {
        Pair<Integer,Integer> origin = Orientation.getOriginValue();
        Pair<Integer,Integer> tile1Loc = Orientation.rightOf(Orientation.upRightOf(origin));
        Pair<Integer,Integer> tile2Loc = Orientation.leftOf(tile1Loc);

        Tile tile0 = new Tile(0, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.downLeft);
        Tile tile1 = new Tile(1, Terrain.terrainType.Jungle, Terrain.terrainType.Grassland, Orientation.Orientations.downLeft);
        Tile tile2 = new Tile(2, Terrain.terrainType.Lake, Terrain.terrainType.Grassland, Orientation.Orientations.upLeft);
        game.placeTile(tile0, origin);
        game.placeTile(tile1, tile1Loc);
        game.placeTile(tile2, tile2Loc);


    }

    @Test
    public void validatePlaceTileOnWholeTileTest () throws Exception {
        createLandMass();

        Tile newTile = new Tile(3, Terrain.terrainType.Jungle, Terrain.terrainType.Grassland, Orientation.Orientations.downLeft);
        TilePositionCoordinates tilePosition = new TilePositionCoordinates(Orientation.getOriginValue(), Orientation.Orientations.downLeft);

        Assert.assertEquals("Placing new tile right on top of entire present tile", false, game.isValidTileNukingPosition(tilePosition));
    }

    @Test
    public void validatePlaceTileOnUnevenLevel() throws Exception {
        createLandMass ();

        Tile additionalTile = new Tile(3, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.downLeft);
        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOriginValue()), Orientation.Orientations.downLeft);

        game.placeTile(additionalTile, additionPosition.getVolcanoCoordinates());

        TilePositionCoordinates testPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOriginValue()), Orientation.Orientations.left);

        Assert.assertEquals("Placing tile on uneven hex levels", false, game.isValidTileNukingPosition(testPosition));
    }

    @Test
    public void validatePlaceTileOnTotoro() throws Exception {
        createLandMass();

        Hex hex =  game.gameBoard.getHex(Orientation.getOriginValue());
        hex.setOccupiedBy(Hex.gamePieces.Totoro);

        Tile additionalTile = new Tile(3, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.downLeft);
        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOriginValue()), Orientation.Orientations.downLeft);


        Assert.assertEquals("Placing tile on top of Totoro", false, game.isValidTileNukingPosition(additionPosition));

    }

    @Test
    public void validatePlaceTileOnTiger() throws Exception {
        createLandMass();

        Hex hex =  game.gameBoard.getHex(Orientation.getOriginValue());
        hex.setOccupiedBy(Hex.gamePieces.Tiger);

        Tile additionalTile = new Tile(3, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.downLeft);
        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOriginValue()), Orientation.Orientations.downLeft);


        Assert.assertEquals("Placing tile on top of Tiger", false, game.isValidTileNukingPosition(additionPosition));
    }

    @Test
    public void validatePlaceTileOnLevelOneSettlement() throws Exception {
        createLandMass();

        Hex hex =  game.gameBoard.getHex(Orientation.getOriginValue());
        hex.setOccupiedBy(Hex.gamePieces.Meeple);
        hex.setTeam(Hex.Team.Black);

        Tile additionalTile = new Tile(3, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.downLeft);
        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOriginValue()), Orientation.Orientations.downLeft);


        Assert.assertEquals("Placing tile on top of level One Settlement", false, game.isValidTileNukingPosition(additionPosition));
    }

    @Test
    public void getListOfValidLocationsTest() throws Exception {
        createLandMass();
        ArrayList<Pair<Integer,Integer>> nukingLocations = game.getValidNukingLocations();
        ArrayList<Pair<Integer,Integer>> correctLocations = new ArrayList<>();

        correctLocations.add(Orientation.getOriginValue());
        correctLocations.add(Orientation.upRightOf(Orientation.getOriginValue()));
        correctLocations.add(Orientation.rightOf(Orientation.upRightOf(Orientation.getOriginValue())));

        Assert.assertEquals("Testing valid locations list creation", true, nukingLocations.containsAll(correctLocations));
    }

    @Test
    public void validatePlaceTileOnEntireSettlement() throws Exception {

    }

    @Test
    public void validatePlaceTileOnThreeOccupiedHexesOfLevel4Settlement() throws Exception {

    }


    @Test
    public void getRight() throws Exception {

        Assert.assertEquals("verify getRight", testTile.getRight().getTerrain(), Terrain.terrainType.Lake);

    }

    @Test
    public void firstTileForcedOriginTest(){
        Pair<Integer, Integer> testCoords = new Pair<>(5,5);
        game.placeTile(testTile, testCoords);
        Hex originHex = testTile.getVolcano();

        Assert.assertEquals("test",originHex, game.gameBoard.getHex(Orientation.getOriginValue()));
    }
}
