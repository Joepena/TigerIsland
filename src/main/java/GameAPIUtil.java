import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Nicholas on 3/24/2017.
 */
public class GameAPIUtil {
    private static final int BOARD_EDGE = 97;

    public static void updateBothSettlement(Settlements whiteSettlements, Settlements blackSettlements, Board gameBoard) {
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

        dfsSearch(copyArr, Orientation.getOrigin(), settlement, new SettlementDataFrame(0,new Tuple(0,0,0)), gameBoard);
        Settlements.retriveWhiteSettlements(settlement, whiteSettlements);
        Settlements.retriveBlackSettlements(settlement, blackSettlements);
    }

    protected static void dfsSearch(boolean[][][] availabilityGrid, Tuple coord, Settlements settlement, SettlementDataFrame df, Board gameBoard) {
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
                dfsSearch(availabilityGrid,coord,settlement,new SettlementDataFrame(0,Orientation.getOrigin()), gameBoard);
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

            dfsSearch(availabilityGrid, Orientation.downLeftOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()), gameBoard);
            dfsSearch(availabilityGrid, Orientation.downRightOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()), gameBoard);
            dfsSearch(availabilityGrid, Orientation.leftOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()), gameBoard);
            dfsSearch(availabilityGrid, Orientation.rightOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()), gameBoard);
            dfsSearch(availabilityGrid, Orientation.upLeftOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()), gameBoard);
            dfsSearch(availabilityGrid, Orientation.upRightOf(coord), settlement, new SettlementDataFrame(0,Orientation.getOrigin()), gameBoard);
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

                dfsSearch(availabilityGrid, hexLocation, settlement, df, gameBoard);
            }
        }
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.downLeft), settlement,
                df, gameBoard);
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.downRight), settlement,
                df, gameBoard);
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.left), settlement, df, gameBoard);
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.right), settlement, df, gameBoard);
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.upLeft), settlement, df, gameBoard);
        dfsSearch(availabilityGrid,
                Orientation.addCoordinatesByOrientation(coord, Orientation.Orientations.upRight), settlement, df, gameBoard);



    }

    public static  boolean isValidNukingCoordinates(Tuple volcanoCoordinates, GameAPI game){
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.downLeft), game))
            return true;
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.downRight), game))
            return true;
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.upLeft), game))
            return true;
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.upRight), game))
            return true;
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.left), game))
            return true;
        if(isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.right), game))
            return true;

        return false;
    }

    public static boolean isValidTileNukingPosition(TilePositionCoordinates tilePositionCoordinates, GameAPI game){

        Hex hexUnderVolcano = game.gameBoard.getHex(tilePositionCoordinates.getVolcanoCoordinates());
        Hex hexUnderLeft = game.gameBoard.getHex(tilePositionCoordinates.getLeftHexCoordinates());
        Hex hexUnderRight = game.gameBoard.getHex(tilePositionCoordinates.getRightHexCoordinates());

        if(hexUnderLeft == null || hexUnderRight == null || hexUnderVolcano == null)
            return false;

        if(!HexValidation.isValidVolcanoPlacement(tilePositionCoordinates.getVolcanoCoordinates(),
                game.gameBoard))
            return false;

        if(hexUnderVolcano.getLevel() != hexUnderLeft.getLevel() || hexUnderLeft.getLevel() != hexUnderRight.getLevel())
            return false;

        if(hexUnderVolcano.getTileId() == hexUnderLeft.getTileId() && hexUnderRight.getTileId() == hexUnderVolcano.getTileId())
            return false;

        if(isVolcanoNukingWholeSettlement(tilePositionCoordinates, game))
            return false;

        if(isLeftHexNukingWholeSettlement(tilePositionCoordinates, game))
            return false;

        if(isRightHexNukingWholeSettlement(tilePositionCoordinates, game))
            return false;

        if(GameAPIUtil.hasTigerTotoro(hexUnderVolcano) || GameAPIUtil.hasTigerTotoro(hexUnderLeft) ||
                GameAPIUtil.hasTigerTotoro(hexUnderRight))
            return false;

        return true;
    }

    protected static boolean isVolcanoNukingWholeSettlement(TilePositionCoordinates tileCoordinates, GameAPI game) {
        return isTilePlacementNukingWholeSettlementOfHexOne(tileCoordinates.getVolcanoCoordinates(),
                tileCoordinates.getLeftHexCoordinates(), tileCoordinates.getRightHexCoordinates(), game);
    }
    protected static boolean isLeftHexNukingWholeSettlement(TilePositionCoordinates tileCoordinates, GameAPI game) {
        return isTilePlacementNukingWholeSettlementOfHexOne(tileCoordinates.getLeftHexCoordinates(),
                tileCoordinates.getVolcanoCoordinates(), tileCoordinates.getRightHexCoordinates(), game);
    }
    protected static boolean isRightHexNukingWholeSettlement(TilePositionCoordinates tileCoordinates, GameAPI game) {
        return isTilePlacementNukingWholeSettlementOfHexOne(tileCoordinates.getRightHexCoordinates(),
                tileCoordinates.getLeftHexCoordinates(), tileCoordinates.getVolcanoCoordinates(), game);
    }

    protected static boolean isTilePlacementNukingWholeSettlementOfHexOne(
            Tuple location1, Tuple location2, Tuple location3, GameAPI game){
        Hex hexOfSettlement = game.gameBoard.getHex(location1);

        if (hexOfSettlement.getOccupiedBy() != Hex.gamePieces.empty) {
            if (hexOfSettlement.getTeam() == Hex.Team.Black) {
                int nukeCount = 1;
                SettlementDataFrame settlement = getBlackSettlementFromLocation(location1, game.getBlackSettlements());
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
                SettlementDataFrame settlement = getWhiteSettlementFromLocation(location1, game.getWhiteSettlements());
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

    protected static boolean isInSettlement(Tuple coordinates, SettlementDataFrame settlement){
        return settlement.getListOfHexLocations().contains(coordinates);
    }

    protected static SettlementDataFrame getBlackSettlementFromLocation(Tuple coordinates, Settlements blackSettlements){
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

    protected static SettlementDataFrame getWhiteSettlementFromLocation(Tuple coordinates, Settlements whiteSettlements){
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


    public static boolean isTileDestinationValid(Tile tile, Tuple destCoordPair, Board gameBoard){
        // Pair<Integer,Integer> originvalue = Orientation.getOriginValue();
        // Pair<Integer, Integer> absDestCoordPair = Orientation.addPairs(destCoordPair, originvalue);

        Tuple originValue = Orientation.getOrigin();


        if (isTileConnected(tile, destCoordPair, gameBoard)){
            return true;
        }

        if (gameBoard.isOriginEmpty()){
            return true;
        }

        return false;
    }

    public static boolean isTileConnected(Tile tile, Tuple absDestCoordPair, Board gameBoard){

        Tuple leftCoordPair = Orientation.addCoordinatesByOrientation(absDestCoordPair, tile.getLeftHexOrientation());
        Tuple rightCoordPair = Orientation.addCoordinatesByOrientation(absDestCoordPair, Orientation.getRightHexMapping(tile.getLeftHexOrientation()));

        boolean valid = HexValidation.existsAdjacentHex(absDestCoordPair, gameBoard);
        valid = valid || HexValidation.existsAdjacentHex(leftCoordPair, gameBoard);
        valid = valid || HexValidation.existsAdjacentHex(rightCoordPair, gameBoard);
        return valid;

    }

    public static boolean isSameTeam(Hex hex1, Hex hex2){

        if(hex1 == null || hex2 == null)
            return false;

        Hex.Team team1 = hex1.getTeam();
        Hex.Team team2 = hex2.getTeam();



        return (team1 == team2);
    }

    public static boolean hasTigerTotoro(Hex hex){
        return hex.getOccupiedBy() == Hex.gamePieces.Totoro || hex.getOccupiedBy() == Hex.gamePieces.Tiger;

    }

    public static boolean isEmpty(Hex hex) {
        return hex.getOccupiedBy() == Hex.gamePieces.empty;
    }






    public static ArrayList<Hex> getNeighbors (Tuple coordinates, Board gameBoard) {
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

}
