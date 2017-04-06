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

        game = new GameAPI();
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

        Assert.assertEquals("Placing new tile right on top of entire present tile", false, game.APIUtils.isValidTileNukingPosition(tilePosition));
    }

    @Test
    public void validatePlaceTileOnUnevenLevel() throws Exception {
        createLandMass ();

        Tile additionalTile = new Tile(3, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.downLeft);
        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOrigin()), Orientation.Orientations.downLeft);

        game.placeTile(additionalTile, additionPosition.getVolcanoCoordinates());

        TilePositionCoordinates testPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOrigin()), Orientation.Orientations.left);

        Assert.assertEquals("Placing tile on uneven hex levels", false, game.APIUtils.isValidTileNukingPosition(testPosition));
    }

    @Test
    public void validatePlaceTileOnTotoro() throws Exception {
        createLandMass();

        Hex hex =  game.gameBoard.getHex(Orientation.getOrigin());
        hex.setOccupiedBy(Hex.gamePieces.Totoro);

        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOrigin()), Orientation.Orientations.downLeft);


        Assert.assertEquals("Placing tile on top of Totoro", false, game.APIUtils.isValidTileNukingPosition(additionPosition));

    }

    @Test
    public void validatePlaceTileOnTiger() throws Exception {
        createLandMass();

        Hex hex =  game.gameBoard.getHex(Orientation.getOrigin());
        hex.setOccupiedBy(Hex.gamePieces.Tiger);

        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOrigin()), Orientation.Orientations.downLeft);


        Assert.assertEquals("Placing tile on top of Tiger", false, game.APIUtils.isValidTileNukingPosition(additionPosition));
    }

    @Test
    public void validatePlaceTileOnLevelOneSettlement() throws Exception {
        createLandMass();

        Hex hex =  game.gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin()));
        hex.setOccupiedBy(Hex.gamePieces.Meeple);
        hex.setTeam(Hex.Team.Black);

        game.updateSettlements();

        Tile additionalTile = new Tile(3, Terrain.terrainType.Grassland, Terrain.terrainType.Rocky, Orientation.Orientations.downLeft);
        TilePositionCoordinates additionPosition = new TilePositionCoordinates(Orientation.upRightOf(Orientation.getOrigin()), Orientation.Orientations.downLeft);


        Assert.assertEquals("Placing tile on top of level One Settlement", false, game.APIUtils.isValidTileNukingPosition(additionPosition));
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
        createLandMass();

        Hex meeple = game.gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.White);

        meeple = game.gameBoard.getHex(Orientation.downRightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.White);

        game.updateSettlements();

        TilePositionCoordinates tilePlacement = new TilePositionCoordinates(Orientation.getOrigin(), Orientation.Orientations.downRight);

        Assert.assertEquals("Nuking entire level 2 settlement", false, game.APIUtils.isValidTileNukingPosition(tilePlacement));


    }

    @Test
    public void validatePlaceTileOnThreeOccupiedHexesOfLevel4Settlement() throws Exception {

    }

    @Test
    public void getWhiteSettlementFromLocationTest() throws Exception {
        createLandMass();

        Hex meeple = game.gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.White);

        meeple = game.gameBoard.getHex(Orientation.downRightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.White);

        game.updateSettlements();

        SettlementDataFrame settlement = game.APIUtils.getWhiteSettlementFromLocation(Orientation.rightOf(Orientation.getOrigin()), game.getWhiteSettlements());
        SettlementDataFrame testSettlement = game.getWhiteSettlements().getListOfSettlements().get(0);

        Assert.assertEquals("Obtain white settlement from location of member Hex", true,
                settlement.equals(testSettlement));
    }

    @Test
    public void getBlackSettlementFromLocationTest() throws Exception {
        createLandMass();

        Hex meeple = game.gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.Black);

        meeple = game.gameBoard.getHex(Orientation.downRightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.Black);

        game.updateSettlements();

        SettlementDataFrame settlement = game.APIUtils.getBlackSettlementFromLocation(Orientation.rightOf(Orientation.getOrigin()), game.getBlackSettlements());
        SettlementDataFrame testSettlement = game.getBlackSettlements().getListOfSettlements().get(0);

        Assert.assertEquals("Obtain Black settlement from location of member Hex", true,
                settlement.equals(testSettlement));
    }

    @Test
    public void getListOfValidLocationsMultiLevelsTest() throws Exception {
        createLandMass();
        expandLandMass();
        ArrayList<Tuple> nukingLocations = game.getValidNukingLocations();
        ArrayList<Tuple> correctLocations = new ArrayList<>();

        correctLocations.add(Orientation.getOrigin());
        correctLocations.add(Orientation.upRightOf(Orientation.getOrigin()));

        Assert.assertEquals("Testing valid locations list creation on island with multiple levels", true,
                nukingLocations.containsAll(correctLocations) && correctLocations.containsAll(nukingLocations));
    }

    @Test
    public void isInSettlementTest() throws Exception {
        createLandMass();

        Hex meeple = game.gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.Black);

        meeple = game.gameBoard.getHex(Orientation.downRightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.Black);

        game.updateSettlements();

        SettlementDataFrame testSettlement = game.getBlackSettlements().getListOfSettlements().get(0);

        boolean valid = game.APIUtils.isInSettlement(Orientation.rightOf(Orientation.getOrigin()), testSettlement);
        valid = valid || game.APIUtils.isInSettlement(Orientation.downRightOf(Orientation.getOrigin()), testSettlement);

        Assert.assertTrue("Check if hex is found in settlement", valid);

    }

    @Test
    public void isNotInSettlementTest() throws Exception {
        createLandMass();

        Hex meeple = game.gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.Black);

        meeple = game.gameBoard.getHex(Orientation.downRightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.Black);

        game.updateSettlements();

        SettlementDataFrame testSettlement = game.getBlackSettlements().getListOfSettlements().get(0);

        boolean valid = game.APIUtils.isInSettlement(Orientation.getOrigin(), testSettlement);

        Assert.assertFalse("Check if hex is found in settlement when location not a part of settlement", valid);

    }

    @Test
    public void getListOfValidLocationsLevelOneSettlementTest() throws Exception {
        createLandMass();
        expandLandMass();


        Hex meeple = game.gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.White);

        game.gameBoard.getHex(Orientation.upRightOf(Orientation.upRightOf(Orientation.getOrigin()))).setTileId(6);

        game.updateSettlements();

        ArrayList<Tuple> nukingLocations = game.getValidNukingLocations();
        ArrayList<Tuple> correctLocations = new ArrayList<>();

        correctLocations.add(Orientation.upRightOf(Orientation.getOrigin()));


        Assert.assertEquals("Testing valid locations list creation on island with" +
                        " multiple levels and a level one settlement", true,
                (nukingLocations.containsAll(correctLocations)) && correctLocations.containsAll(nukingLocations));
    }


    @Test
    public void getRight() throws Exception {

        Assert.assertEquals("verify getRight", testTile.getRight().getTerrain(), Terrain.terrainType.Lake);

    }

    @Test
    public void isTilePlacementNukingWholeSettlementOfHexOneTest() throws Exception {
        createLandMass();

        Hex meeple = game.gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.Black);

        meeple = game.gameBoard.getHex(Orientation.downRightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.Black);

        game.updateSettlements();

        TilePositionCoordinates tilePositionCoordinates =
                new TilePositionCoordinates(Orientation.getOrigin(), Orientation.Orientations.downRight);

        boolean valid = game.APIUtils.isTilePlacementNukingWholeSettlementOfHexOne(tilePositionCoordinates.getLeftHexCoordinates(),
                tilePositionCoordinates.getVolcanoCoordinates(), tilePositionCoordinates.getRightHexCoordinates());

        Assert.assertTrue("Checks if settlement under leftHex is totally nuked, in this scenario it is", valid);
    }

    @Test
    public void isTilePlacementNOTNukingWholeSettlementOfHexOneTest() throws Exception {
        createLandMass();

        Hex meeple = game.gameBoard.getHex(Orientation.rightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.Black);

        meeple = game.gameBoard.getHex(Orientation.downRightOf(Orientation.getOrigin()));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.Black);

        meeple = game.gameBoard.getHex(Orientation.rightOf(Orientation.rightOf(Orientation.getOrigin())));
        meeple.setOccupiedBy(Hex.gamePieces.Meeple);
        meeple.setTeam(Hex.Team.Black);

        game.updateSettlements();

        TilePositionCoordinates tilePositionCoordinates =
                new TilePositionCoordinates(Orientation.getOrigin(), Orientation.Orientations.downRight);

        boolean valid = game.APIUtils.isTilePlacementNukingWholeSettlementOfHexOne(tilePositionCoordinates.getLeftHexCoordinates(),
                tilePositionCoordinates.getVolcanoCoordinates(), tilePositionCoordinates.getRightHexCoordinates());

        Assert.assertFalse("Checks if settlement under leftHex is totally nuked, in this scenario it is not", valid);
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

    public void expandLandMass() throws Exception {
        Tuple origin = Orientation.getOrigin();
        Tuple tile1Loc = Orientation.rightOf(Orientation.upRightOf(origin));
        Tuple tile2Loc = Orientation.leftOf(tile1Loc);

        Tuple tile3Loc = Orientation.upRightOf(Orientation.upRightOf(tile2Loc));
        Tuple tile4Loc = tile2Loc;
        Tuple tile5Loc = Orientation.getOrigin();

        Tile tile3 = new Tile(3, Terrain.terrainType.Lake, Terrain.terrainType.Grassland, Orientation.Orientations.downLeft);
        Tile tile4 = new Tile(4, Terrain.terrainType.Lake, Terrain.terrainType.Grassland, Orientation.Orientations.upLeft);
        Tile tile5 = new Tile(5, Terrain.terrainType.Lake, Terrain.terrainType.Grassland, Orientation.Orientations.downRight);

        game.placeTile(tile3, tile3Loc);
        game.placeTile(tile4, tile4Loc);
        game.placeTile(tile5, tile5Loc);

    }

}
