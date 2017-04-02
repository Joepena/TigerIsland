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

    @Test
    public void validatePlaceFirstTile_Volcano() throws Exception {

        game.placeFirstTile();
        Tuple originCoords = new Tuple(0,0,0);
        Assert.assertEquals("Checking volcano", game.gameBoard.getHex(originCoords).getTerrain(), Terrain.terrainType.Volcano);

    }

    @Test
    public void validatePlaceFirstTile_Jungle() throws Exception {

        game.placeFirstTile();
        Tuple jungleCoords = new Tuple(0, 1, -1);
        Assert.assertEquals("Checking jungle", game.gameBoard.getHex(jungleCoords).getTerrain(), Terrain.terrainType.Jungle);
    }

    @Test
    public void validatePlaceFirstTile_Lake() throws Exception {

        game.placeFirstTile();
        Tuple lakeCoords = new Tuple(1, 0, -1);
        Assert.assertEquals("Checking lake", game.gameBoard.getHex(lakeCoords).getTerrain(), Terrain.terrainType.Lake);
    }

    @Test
    public void validatePlaceFirstTile_Rocky() throws Exception {

        game.placeFirstTile();
        Tuple rockyCoords = new Tuple(-1, 0, 1);
        Assert.assertEquals("Checking rocky", game.gameBoard.getHex(rockyCoords).getTerrain(), Terrain.terrainType.Rocky);
    }

    @Test
    public void validatePlaceFirstTile_Grassland() throws Exception {

        game.placeFirstTile();
        Tuple grassCoords = new Tuple(0, -1, 1);
        Assert.assertEquals("Checking grassland", game.gameBoard.getHex(grassCoords).getTerrain(), Terrain.terrainType.Grassland);
    }


    @Test
    public void validatePlaceTileOnWholeTileTest () throws Exception {
        createLandMass();

        Tile newTile = new Tile(3, Terrain.terrainType.Jungle, Terrain.terrainType.Grassland, Orientation.Orientations.downLeft);
        TilePositionCoordinates tilePosition = new TilePositionCoordinates(Orientation.getOrigin(), Orientation.Orientations.downLeft);

        Assert.assertEquals("Placing new tile right on top of entire present tile", false, game.isValidTileNukingPosition(tilePosition));
    }

    @Test
    public void validatePlaceTileOnUnevenLevel() throws Exception {
        createLandMass ();

        Tile additionalTile = new Tile(3, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.downLeft);
        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOrigin()), Orientation.Orientations.downLeft);

        game.placeTile(additionalTile, additionPosition.getVolcanoCoordinates());

        TilePositionCoordinates testPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOrigin()), Orientation.Orientations.left);

        Assert.assertEquals("Placing tile on uneven hex levels", false, game.isValidTileNukingPosition(testPosition));
    }

    @Test
    public void validatePlaceTileOnTotoro() throws Exception {
        createLandMass();

        Hex hex =  game.gameBoard.getHex(Orientation.getOrigin());
        hex.setOccupiedBy(Hex.gamePieces.Totoro);

        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOrigin()), Orientation.Orientations.downLeft);


        Assert.assertEquals("Placing tile on top of Totoro", false, game.isValidTileNukingPosition(additionPosition));

    }

    @Test
    public void validatePlaceTileOnTiger() throws Exception {
        createLandMass();

        Hex hex =  game.gameBoard.getHex(Orientation.getOrigin());
        hex.setOccupiedBy(Hex.gamePieces.Tiger);

        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOrigin()), Orientation.Orientations.downLeft);


        Assert.assertEquals("Placing tile on top of Tiger", false, game.isValidTileNukingPosition(additionPosition));
    }

    @Test
    public void validatePlaceTileOnLevelOneSettlement() throws Exception {
        createLandMass();

        Hex hex =  game.gameBoard.getHex(Orientation.getOrigin());
        hex.setOccupiedBy(Hex.gamePieces.Meeple);
        hex.setTeam(Hex.Team.Black);

        Tile additionalTile = new Tile(3, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.downLeft);
        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOrigin()), Orientation.Orientations.downLeft);


        Assert.assertEquals("Placing tile on top of level One Settlement", false, game.isValidTileNukingPosition(additionPosition));
    }

    @Test
    public void getListOfValidLocationsTest() throws Exception {
        createLandMass();
        ArrayList<Tuple> nukingLocations = game.getValidNukingLocations();
        ArrayList<Tuple> correctLocations = new ArrayList<>();

        correctLocations.add(Orientation.getOrigin());
        correctLocations.add(Orientation.upRightOf(Orientation.getOrigin()));
        correctLocations.add(Orientation.rightOf(Orientation.upRightOf(Orientation.getOrigin())));

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

  public void createLandMass() throws Exception {
    Tuple origin = Orientation.getOrigin();
    Tuple tile1Loc = Orientation.rightOf(Orientation.upRightOf(origin));
    Tuple tile2Loc = Orientation.leftOf(tile1Loc);


    Tile tile0 = new Tile(0, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.downLeft);
    Tile tile1 = new Tile(1, Terrain.terrainType.Jungle, Terrain.terrainType.Grassland, Orientation.Orientations.downLeft);
    Tile tile2 = new Tile(2, Terrain.terrainType.Lake, Terrain.terrainType.Grassland, Orientation.Orientations.upRight);
    game.placeTile(tile0, origin);
    game.placeTile(tile1, tile1Loc);
    game.placeTile(tile2, tile2Loc);

  }
}
