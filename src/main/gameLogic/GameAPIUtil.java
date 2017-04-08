import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Nicholas on 3/24/2017.
 */
public class GameAPIUtil {
    private final int BOARD_EDGE = 97;

    private Board gameBoard;
    private GameAPI game;

    public GameAPIUtil(GameAPI gameAPI) {
        this.game = gameAPI;
        this.gameBoard = gameAPI.gameBoard;
    }

    public void updateBothSettlement() {
        Settlements settlement = new Settlements();
        settlement.wipeSettlementSet();

        boolean[][][] copyArr = copyAvailabilityGrid(gameBoard.getGameBoardAvailability());
        dfsSearch(copyArr, Orientation.getOrigin(), settlement, new SettlementDataFrame(0,new Tuple(0,0,0)));

        Settlements.retriveWhiteSettlements(settlement, game.getWhiteSettlements());
        Settlements.retriveBlackSettlements(settlement, game.getBlackSettlements());

        //conglomerateAdjacentSettlements(Hex.Team.White);
        conglomerateAdjacentSettlements(Hex.Team.Black);
    }

    private boolean[][][] copyAvailabilityGrid(boolean[][][] array) {
      boolean[][][] copyArr = new boolean[array.length][][];

      for (int i = 0; i < array.length; i++) {
        copyArr[i] = new boolean[array[i].length][];
        for (int j = 0; j < array[i].length; j++) {
          copyArr[i][j] = new boolean[array[i][j].length];
          System.arraycopy(array[i][j], 0, copyArr[i][j], 0,
            array[i][j].length);
        }
      }
      return copyArr;
    }

    protected void dfsSearch(boolean[][][] availabilityGrid, Tuple coord, Settlements settlement, SettlementDataFrame df) {
        int xCord = coord.getX();
        int yCord = coord.getY();
        int zCord = coord.getZ();
        Tuple offset = gameBoard.calculateOffset(coord);
        //edge case
        if (xCord >= BOARD_EDGE || xCord <= -BOARD_EDGE || yCord >= BOARD_EDGE || yCord <= -BOARD_EDGE || zCord >= BOARD_EDGE || zCord <= -BOARD_EDGE
                || !availabilityGrid[offset.getX()][offset.getY()][offset.getZ()]) return;

        //invalidate the position
        Hex h = gameBoard.getHex(coord);


        if (h.getTeam() != Hex.Team.Neutral) {
            // initial call
            if (df.getOwnedBy() == null) {
                df.setOwnedBy(h.getTeam());
                df.setSettlementSize(1);
                df.addLocationListOfHexes(coord);
                df.setSettlementStartingLocation(coord);
                settlement.addNewSettlement(df);
            } else if (df.getOwnedBy() != h.getTeam()) {
                // we call dfs for a new clean dataFrame
                dfsSearch(availabilityGrid, coord, settlement, new SettlementDataFrame(0, Orientation.getOrigin()));
            } else {
                // matching ownership
                df.setSettlementSize(df.getSettlementSize() + 1);
                df.addLocationListOfHexes(coord);
            }
        }

        availabilityGrid[offset.getX()][offset.getY()][offset.getZ()] = false;

        //edge case #1: we have a team but this hex is neutral. We do not want to carry this df anymore
        if (df.getOwnedBy() != null && h.getTeam() == Hex.Team.Neutral) {

            dfsSearch(availabilityGrid, Orientation.downLeftOf(coord), settlement, new SettlementDataFrame(0, Orientation.getOrigin()));
            dfsSearch(availabilityGrid, Orientation.downRightOf(coord), settlement, new SettlementDataFrame(0, Orientation.getOrigin()));
            dfsSearch(availabilityGrid, Orientation.leftOf(coord), settlement, new SettlementDataFrame(0, Orientation.getOrigin()));
            dfsSearch(availabilityGrid, Orientation.rightOf(coord), settlement, new SettlementDataFrame(0, Orientation.getOrigin()));
            dfsSearch(availabilityGrid, Orientation.upLeftOf(coord), settlement, new SettlementDataFrame(0, Orientation.getOrigin()));
            dfsSearch(availabilityGrid, Orientation.upRightOf(coord), settlement, new SettlementDataFrame(0, Orientation.getOrigin()));
        }
        //edge case #2: we have a team but this hex is not
        else if (df.getOwnedBy() != null) {

            // loop through all hexes same team first
            Hex.Team dfTeam = df.getOwnedBy();

            for (Orientation.Orientations orientation : Orientation.Orientations.values()) {

                if (orientation == Orientation.Orientations.origin) continue;
                // get adjacent hex
                Tuple hexLocation = Orientation.addCoordinatesByOrientation(coord, orientation);
                Hex adjacentHex = gameBoard.getHex(hexLocation);

                if (adjacentHex == null || adjacentHex.getTeam() != dfTeam) continue;
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

    protected void conglomerateAdjacentSettlements(Hex.Team team) {
        Settlements listSettlements;

        if (team == Hex.Team.White) {
            listSettlements = game.getWhiteSettlements();
        } else if (team == Hex.Team.Black) {
            listSettlements = game.getBlackSettlements();
        } else {
            return;
        }

        ArrayList<SettlementDataFrame> toBeRemoved = new ArrayList<>();
        ArrayList<Tuple> currentDataFrameHexLocations = new ArrayList<>();
        ArrayList<SettlementDataFrame> possibleSettlements = new ArrayList<>();

        possibleSettlements = listSettlements.getListOfSettlements();
        int possibleSettlementsDynamicSize = possibleSettlements.size();
        //Loops through each dataframe
        outerloop:
        for (int i = 0; i < possibleSettlementsDynamicSize; i ++) {
            SettlementDataFrame currentDataFrame = possibleSettlements.get(i);

            currentDataFrameHexLocations = currentDataFrame.getListOfHexLocations();
            //Loops through each hexLoc in dataframe
            for (Tuple currentHexLoc : currentDataFrameHexLocations) {
                Hex currentHex = gameBoard.getHex(currentHexLoc);

                ArrayList<Hex> adjacentHexList = getNeighbors(currentHexLoc);
                //Loops through each neighbor of hex in dataframe
                for (Hex adjacentHex : adjacentHexList) {
                    Tuple adjacentHexCoords = adjacentHex.getLocation();

                    //We found an adjacent hex of same team in a different settlement.
                    if (!currentDataFrame.getListOfHexLocations().contains(adjacentHexCoords) && isSameTeam(adjacentHex, currentHex)) {
                        //Find the dataframe the adjacent hex belongs to.
                        for (SettlementDataFrame adjacentDataFrame : listSettlements.getListOfSettlements()) {
                            if (adjacentDataFrame.getListOfHexLocations().contains(adjacentHexCoords)) {

                                ArrayList<Tuple> combinedDataFrameHexLocs = currentDataFrame.getListOfHexLocations();
                                combinedDataFrameHexLocs.addAll(adjacentDataFrame.getListOfHexLocations());
                                int combinedDataFrameSize = combinedDataFrameHexLocs.size();

                                SettlementDataFrame combinedDataFrame = new SettlementDataFrame(combinedDataFrameSize, currentDataFrame.getSettlementStartingLocation());

                                for (Tuple combinedCoords : combinedDataFrameHexLocs) {
                                    combinedDataFrame.addLocationListOfHexes(combinedCoords);
                                }

                                listSettlements.addNewSettlement(combinedDataFrame);
                                listSettlements.removeSettlement(currentDataFrame);
                                listSettlements.removeSettlement(adjacentDataFrame);

                                //Reinitialize possible settlements list
                                possibleSettlements = listSettlements.getListOfSettlements();
                                //Restart search
                                i = 0;
                                possibleSettlementsDynamicSize--;



                                break outerloop;
                            }

                        }
                    }
                }
            }

        }

    }

    public boolean isValidNukingCoordinates(Tuple volcanoCoordinates) {
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.downLeft)))
            return true;
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.downRight)))
            return true;
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.upLeft)))
            return true;
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.upRight)))
            return true;
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.left)))
            return true;
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.right)))
            return true;

        return false;
    }

    public boolean isValidTileNukingPosition(TilePositionCoordinates tilePositionCoordinates) {

        Hex hexUnderVolcano = gameBoard.getHex(tilePositionCoordinates.getVolcanoCoordinates());
        Hex hexUnderLeft = gameBoard.getHex(tilePositionCoordinates.getLeftHexCoordinates());
        Hex hexUnderRight = gameBoard.getHex(tilePositionCoordinates.getRightHexCoordinates());

        if (hexUnderLeft == null || hexUnderRight == null || hexUnderVolcano == null)
            return false;

        if (!HexValidation.isValidVolcanoPlacement(tilePositionCoordinates.getVolcanoCoordinates(), gameBoard))
            return false;

        if (hexUnderVolcano.getLevel() != hexUnderLeft.getLevel() || hexUnderLeft.getLevel() != hexUnderRight.getLevel())
            return false;

        if (hexUnderVolcano.getTileId() == hexUnderLeft.getTileId() && hexUnderRight.getTileId() == hexUnderVolcano.getTileId())
            return false;

        if (isVolcanoNukingWholeSettlement(tilePositionCoordinates))
            return false;

        if (isLeftHexNukingWholeSettlement(tilePositionCoordinates))
            return false;

        if (isRightHexNukingWholeSettlement(tilePositionCoordinates))
            return false;

        if (hasTigerTotoro(hexUnderVolcano) || hasTigerTotoro(hexUnderLeft) || hasTigerTotoro(hexUnderRight))
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

    protected boolean isTilePlacementNukingWholeSettlementOfHexOne(Tuple location1, Tuple location2, Tuple location3) {
        Hex hexOfSettlement = gameBoard.getHex(location1);

        if (hexOfSettlement.getOccupiedBy() != Hex.gamePieces.empty) {
            if (hexOfSettlement.getTeam() == Hex.Team.Black) {
                int nukeCount = 1;
                SettlementDataFrame settlement = getBlackSettlementFromLocation(location1, game.getBlackSettlements());
                if (settlement == null) {
                } else {
                    if (isInSettlement(location2, settlement))
                        nukeCount++;
                    if (isInSettlement(location3, settlement))
                        nukeCount++;
                    if (settlement.getSettlementSize() == nukeCount)
                        return true;
                }
            } else if (hexOfSettlement.getTeam() == Hex.Team.White) {
                int nukeCount = 1;
                SettlementDataFrame settlement = getWhiteSettlementFromLocation(location1, game.getWhiteSettlements());
                if (settlement == null) {
                } else {
                    if (isInSettlement(location2, settlement))
                        nukeCount++;
                    if (isInSettlement(location3, settlement))
                        nukeCount++;
                    if (settlement.getSettlementSize() == nukeCount)
                        return true;
                }
            }
        }
        return false;
    }

    protected boolean isInSettlement(Tuple coordinates, SettlementDataFrame settlement) {
        return settlement.getListOfHexLocations().contains(coordinates);
    }

    protected SettlementDataFrame getBlackSettlementFromLocation(Tuple coordinates, Settlements blackSettlements) {
        ArrayList<SettlementDataFrame> listOfBlackSettlements = blackSettlements.getListOfSettlements();

        for (int i = 0; i < listOfBlackSettlements.size(); i++) {
            ArrayList<Tuple> hexesInSettlement = listOfBlackSettlements.get(i).getListOfHexLocations();
            for (Tuple aHexesInSettlement : hexesInSettlement) {
                if (aHexesInSettlement.equals(coordinates))
                    return listOfBlackSettlements.get(i);
            }
        }
        return null;
    }

    protected SettlementDataFrame getWhiteSettlementFromLocation(Tuple coordinates, Settlements whiteSettlements) {
        ArrayList<SettlementDataFrame> listOfWhiteSettlements = whiteSettlements.getListOfSettlements();

        for (int i = 0; i < listOfWhiteSettlements.size(); i++) {
            ArrayList<Tuple> hexesInSettlement = listOfWhiteSettlements.get(i).getListOfHexLocations();
            for (Tuple aHexesInSettlement : hexesInSettlement) {
                if (aHexesInSettlement.equals(coordinates))
                    return listOfWhiteSettlements.get(i);

            }
        }
        return null;
    }


    public boolean isTileDestinationValid(Tile tile, Tuple destCoordPair) {
        if (isTileConnected(tile, destCoordPair)) {
            return true;
        }

        if (game.gameBoard.isOriginEmpty()) {
            return true;
        }

        return false;
    }

    public boolean isTileConnected(Tile tile, Tuple absDestCoordPair) {

        Tuple leftCoordPair = Orientation.addCoordinatesByOrientation(absDestCoordPair, tile.getLeftHexOrientation());
        Tuple rightCoordPair = Orientation.addCoordinatesByOrientation(absDestCoordPair, Orientation.getRightHexMapping(tile.getLeftHexOrientation()));

        boolean valid = HexValidation.existsAdjacentHex(absDestCoordPair, game.gameBoard);
        valid = valid || HexValidation.existsAdjacentHex(leftCoordPair, game.gameBoard);
        valid = valid || HexValidation.existsAdjacentHex(rightCoordPair, game.gameBoard);
        return valid;

    }

    public ArrayList<Hex> getNeighbors(Tuple coordinates) {
        ArrayList<Hex> neighbors = new ArrayList<>();
        Board gameBoard = game.gameBoard;

        neighbors.add(gameBoard.getHex(Orientation.upLeftOf(coordinates)));
        neighbors.add(gameBoard.getHex(Orientation.upRightOf(coordinates)));
        neighbors.add(gameBoard.getHex(Orientation.downLeftOf(coordinates)));
        neighbors.add(gameBoard.getHex(Orientation.downRightOf(coordinates)));
        neighbors.add(gameBoard.getHex(Orientation.leftOf(coordinates)));
        neighbors.add(gameBoard.getHex(Orientation.rightOf(coordinates)));

        neighbors.removeAll(Collections.singleton(null));

        return neighbors;

    }

    public ArrayList<ExpansionOpDataFrame> findExpansionOptionsFor(Hex.Team team) {
      // search for all hexes owned by [team] param, use settlements
      ArrayList<SettlementDataFrame> target = new ArrayList<>();
      if(team == Hex.Team.Black) target = game.getBlackSettlements().getListOfSettlements();
      else if(team == Hex.Team.White) target = game.getWhiteSettlements().getListOfSettlements();
      //iterate through each data-frame marking off already visited hexes off of a copy of availability grid
      boolean [][][] copyArr = copyAvailabilityGrid(gameBoard.gameBoardAvailability);
      Iterator<SettlementDataFrame> iterator = target.iterator();
      ArrayList<ExpansionOpDataFrame> list = new ArrayList<>();
      while (iterator.hasNext()) {
        SettlementDataFrame df = iterator.next();
        ArrayList<Tuple> listOfTuples = df.getListOfHexLocations();
        for(Tuple tuple : listOfTuples) {
          // find adjacent hex that is same terrain and not occupied
          Terrain.terrainType currHexTerr = gameBoard.getHex(tuple).getTerrain();
          for (Orientation.Orientations orientation : Orientation.Orientations.values()) {
            Tuple tempTuple = Orientation.addCoordinatesByOrientation(tuple, orientation);
            Hex tempHex = gameBoard.getHex(tempTuple);
            if(tempHex == null) continue;
            Terrain.terrainType tempTerrain = tempHex.getTerrain();
            if (tempTuple == Orientation.getOrigin() || currHexTerr != tempTerrain
              || tempHex.getTeam() != Hex.Team.Neutral)
              continue;
            ExpansionOpDataFrame expansionOpDataFrame = new ExpansionOpDataFrame(tempTuple,
              tempTerrain);
            dfsExpansionSearch(copyArr, expansionOpDataFrame, tempTerrain, tempTuple);
            list.add(expansionOpDataFrame);
          }
        }
      }
      return list;
    }

    private void dfsExpansionSearch(boolean[][][] availabilityGrid, ExpansionOpDataFrame df, Terrain.terrainType terrain, Tuple tuple) {
      int xCord = tuple.getX();
      int yCord = tuple.getY();
      int zCord = tuple.getZ();
      Tuple offSet = gameBoard.calculateOffset(tuple);
      if(!availabilityGrid[offSet.getX()][offSet.getY()][offSet.getZ()] || xCord >= BOARD_EDGE || xCord <= -BOARD_EDGE || yCord >= BOARD_EDGE || yCord <= -BOARD_EDGE || zCord >= BOARD_EDGE || zCord <= -BOARD_EDGE) {
        return;
      }
      availabilityGrid[offSet.getX()][offSet.getY()][offSet.getZ()] = false;
      df.incrementExpansionCost(gameBoard.getHex(tuple).getLevel());
      for(Orientation.Orientations orientation : Orientation.Orientations.values()) {
        Tuple tempTuple = Orientation.addCoordinatesByOrientation(tuple,orientation);
        Tuple tempTupleOff = gameBoard.calculateOffset(tempTuple);
        if(!availabilityGrid[tempTupleOff.getX()][tempTupleOff.getY()][tempTupleOff.getZ()]) continue;
        Hex tempHex = gameBoard.getHex(tempTuple);
        if(tempHex == null) continue;
        Terrain.terrainType tempTerrain = tempHex.getTerrain();
        if(tempTuple == Orientation.getOrigin() || terrain != tempTerrain || tempHex.getTeam() != Hex.Team.Neutral) continue;
        dfsExpansionSearch(availabilityGrid,df,terrain,tempTuple);
      }
    }
    public void performLandGrab(Tuple tuple) {
      boolean[][][] copyArr = copyAvailabilityGrid(gameBoard.gameBoardAvailability);
      Terrain.terrainType terrain = gameBoard.getHex(tuple).getTerrain();

      for (Orientation.Orientations orientation : Orientation.Orientations.values()) {
        Tuple tempTuple = Orientation.addCoordinatesByOrientation(tuple, orientation);
        Hex tempHex = gameBoard.getHex(tempTuple);
        if(tempHex == null) continue;
        Terrain.terrainType tempTerrain = tempHex.getTerrain();
        if (tempTuple == Orientation.getOrigin() || terrain != tempTerrain || tempHex.getTeam() != Hex.Team.Neutral) continue;

        dfsExpansionGrab(copyArr, tempTerrain, tempTuple);
      }
    }
    private void dfsExpansionGrab(boolean[][][] availabilityGrid, Terrain.terrainType terrain, Tuple tuple) {
      int xCord = tuple.getX();
      int yCord = tuple.getY();
      int zCord = tuple.getZ();
      Tuple offSet = gameBoard.calculateOffset(tuple);
      if (!availabilityGrid[offSet.getX()][offSet.getY()][offSet.getZ()] || xCord >= BOARD_EDGE
        || xCord <= -BOARD_EDGE || yCord >= BOARD_EDGE || yCord <= -BOARD_EDGE
        || zCord >= BOARD_EDGE || zCord <= -BOARD_EDGE) {
        return;
      }
      availabilityGrid[offSet.getX()][offSet.getY()][offSet.getZ()] = false;
      game.foundSettlement(tuple);
      for (Orientation.Orientations orientation : Orientation.Orientations.values()) {
        Tuple tempTuple = Orientation.addCoordinatesByOrientation(tuple, orientation);
        Tuple tempTupleOff = gameBoard.calculateOffset(tempTuple);
        if (!availabilityGrid[tempTupleOff.getX()][tempTupleOff.getY()][tempTupleOff.getZ()])
          continue;
        Hex tempHex = gameBoard.getHex(tempTuple);
        if (tempHex == null)
          continue;
        Terrain.terrainType tempTerrain = tempHex.getTerrain();
        if (tempTuple == Orientation.getOrigin() || terrain != tempTerrain
          || tempHex.getTeam() != Hex.Team.Neutral)
          continue;
        dfsExpansionGrab(availabilityGrid, terrain, tempTuple);

      }
    }



  public ArrayList<Tuple> findListOfValidSettlementLocation(Tuple coordPoint, boolean[][][] settlementChecked, ArrayList<Tuple> settlementList) {
    Hex currentHex = gameBoard.getHex(coordPoint);
    if(currentHex == null) return settlementList;
    Hex[] neighborHex = getNeighborHexes(coordPoint,gameBoard);
    Tuple[] neighborCoord = getNeighborCoords(coordPoint);
    if(currentHex.getLevel() == 1 && currentHex.getTeam() == Hex.Team.Neutral)
    {
      settlementList.add(coordPoint);
    }
    Tuple offsetTuple = gameBoard.calculateOffset(coordPoint);
    settlementChecked[offsetTuple.getX()][offsetTuple.getY()][offsetTuple.getZ()] = true;;
    for(int i=0; i < neighborCoord.length; i++) {
      Tuple offSetTemp = gameBoard.calculateOffset(neighborCoord[i]);
      if (!settlementChecked[offSetTemp.getX()][offSetTemp.getY()][offSetTemp.getZ()] && neighborHex[i] != null)
        findListOfValidSettlementLocation(neighborCoord[i], settlementChecked, settlementList);
    }
    return settlementList;
  }

  protected Hex[] getNeighborHexes(Tuple coordPoint, Board gameBoard) {
    Hex[] neighbors = new Hex[6];

    neighbors[0] = gameBoard.getHex(Orientation.leftOf(coordPoint));
    neighbors[1] = gameBoard.getHex(Orientation.rightOf(coordPoint));
    neighbors[2] = gameBoard.getHex(Orientation.upLeftOf(coordPoint));
    neighbors[3] = gameBoard.getHex(Orientation.upRightOf(coordPoint));
    neighbors[4] = gameBoard.getHex(Orientation.downLeftOf(coordPoint));
    neighbors[5] = gameBoard.getHex(Orientation.downRightOf(coordPoint));

    return neighbors;
  }

    private Tuple[] getNeighborCoords(Tuple coordPoint) {
      Tuple[] neighbors = new Tuple[6];
      neighbors[0] = (Orientation.leftOf(coordPoint));
      neighbors[1] = (Orientation.rightOf(coordPoint));
      neighbors[2] = (Orientation.upLeftOf(coordPoint));
      neighbors[3] = (Orientation.upRightOf(coordPoint));
      neighbors[4] = (Orientation.downLeftOf(coordPoint));
      neighbors[5] = (Orientation.downRightOf(coordPoint));
      return neighbors;
  }

    public static boolean isSameTeam(Hex hex1, Hex hex2) {


        if (hex1 == null || hex2 == null)
            return false;

        Hex.Team team1 = hex1.getTeam();
        Hex.Team team2 = hex2.getTeam();


        return (team1 == team2);
    }

    public static boolean hasTigerTotoro(Hex hex) {
        return hex.getOccupiedBy() == Hex.gamePieces.Totoro || hex.getOccupiedBy() == Hex.gamePieces.Tiger;

    }

    public static boolean isEmpty(Hex hex) {
        return hex.getOccupiedBy() == Hex.gamePieces.empty;
    }



}
