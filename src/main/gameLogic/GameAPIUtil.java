import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

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

        conglomerateAdjacentSettlements(Hex.Team.White);
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

    public Orientation.Orientations getViableNukingOrientation(Tuple volcanoCoordinates) {
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.downLeft)))
            return Orientation.Orientations.downLeft;
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.downRight)))
            return  Orientation.Orientations.downRight;
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.upLeft)))
            return Orientation.Orientations.upLeft;
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.upRight)))
            return Orientation.Orientations.upRight;
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.left)))
            return Orientation.Orientations.left;
        if (isValidTileNukingPosition(new TilePositionCoordinates(volcanoCoordinates, Orientation.Orientations.right)))
            return Orientation.Orientations.right;
        return null;
    }

    public Orientation.Orientations getViableNonNukingOrientation(Tuple volcanoCoordinates) {
        if (gameBoard.getHex(Orientation.downLeftOf(volcanoCoordinates)) == null
                && gameBoard.getHex(Orientation.downRightOf(volcanoCoordinates)) == null)
            return Orientation.Orientations.downLeft;
        if (gameBoard.getHex(Orientation.downRightOf(volcanoCoordinates)) == null
                && gameBoard.getHex(Orientation.rightOf(volcanoCoordinates)) == null)
            return  Orientation.Orientations.downRight;
        if (gameBoard.getHex(Orientation.upLeftOf(volcanoCoordinates)) == null
                && gameBoard.getHex(Orientation.rightOf(volcanoCoordinates)) == null)
            return Orientation.Orientations.upLeft;
        if (gameBoard.getHex(Orientation.upRightOf(volcanoCoordinates)) == null
                && gameBoard.getHex(Orientation.upLeftOf(volcanoCoordinates)) == null)
            return Orientation.Orientations.upRight;
        if (gameBoard.getHex(Orientation.leftOf(volcanoCoordinates)) == null
                && gameBoard.getHex(Orientation.downLeftOf(volcanoCoordinates)) == null)
            return Orientation.Orientations.left;
        if (gameBoard.getHex(Orientation.rightOf(volcanoCoordinates)) == null
                && gameBoard.getHex(Orientation.upRightOf(volcanoCoordinates)) == null)
            return Orientation.Orientations.right;
        return null;
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
      HashMap<Terrain.terrainType,ExpansionOpDataFrame> map;
      // search for all hexes owned by [team] param, use settlements
      ArrayList<SettlementDataFrame> target = new ArrayList<>();
      if(team == Hex.Team.Black) target = game.getBlackSettlements().getListOfSettlements();
      else if(team == Hex.Team.White) target = game.getWhiteSettlements().getListOfSettlements();
      //iterate through each data-frame marking off already visited hexes off of a copy of availability grid
      boolean [][][] copyArr;
      Iterator<SettlementDataFrame> iterator = target.iterator();
      ArrayList<ExpansionOpDataFrame> list = new ArrayList<>();
      while (iterator.hasNext()) {
        copyArr = copyAvailabilityGrid(gameBoard.gameBoardAvailability);
        SettlementDataFrame df = iterator.next();
        map = new HashMap<>();
        for (Terrain.terrainType terrain : Terrain.terrainType.values()) {
          ExpansionOpDataFrame expansionOpDataFrame;
          if (terrain == Terrain.terrainType.Volcano) continue;
          for( Tuple t : df.getListOfHexLocations()){
            if (map.containsKey(terrain)) {
              expansionOpDataFrame = map.get(terrain);
            }
            else {
              expansionOpDataFrame = new ExpansionOpDataFrame(df,terrain, t);
              map.put(terrain,expansionOpDataFrame);
              list.add(expansionOpDataFrame);
            }

            for (Orientation.Orientations orientation : Orientation.Orientations.values()) {

              Tuple tempTuple = Orientation.addCoordinatesByOrientation(t, orientation);
              Hex adjacentHex = gameBoard.getHex(tempTuple);
              if(adjacentHex == null || isInSettlement(tempTuple,df)) {
                  continue;
              }
              Terrain.terrainType tempTerrain = adjacentHex.getTerrain();
              if (terrain != tempTerrain || adjacentHex.getTeam() != Hex.Team.Neutral) {
                continue;
              }
              dfsExpansionSearch(copyArr, expansionOpDataFrame, tempTerrain, tempTuple);
            }
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


    public void performLandGrab(SettlementDataFrame settlementDataFrame, Terrain.terrainType terrain) {
      boolean[][][] copyArr = copyAvailabilityGrid(gameBoard.gameBoardAvailability);
      markAllInSettlementFalse(settlementDataFrame, copyArr);
      ArrayList<Tuple> listOfLocations = settlementDataFrame.getListOfHexLocations();
      for(Tuple location : listOfLocations){
        for (Orientation.Orientations orientation : Orientation.Orientations.values()) {
          Tuple tempTuple = Orientation.addCoordinatesByOrientation(location, orientation);
          Hex tempHex = gameBoard.getHex(tempTuple);
          if(tempHex == null) continue;
          Terrain.terrainType tempTerrain = tempHex.getTerrain();
          if (tempTuple == Orientation.getOrigin() || terrain != tempTerrain || tempHex.getTeam() != Hex.Team.Neutral) continue;

          dfsExpansionGrab(copyArr, terrain, tempTuple, settlementDataFrame.getOwnedBy());
        }
      }

    }
    private void markAllInSettlementFalse(SettlementDataFrame df, boolean[][][] copyArr) {
      for (Tuple t : df.getListOfHexLocations()) {
        Tuple offsetTuple = game.gameBoard.calculateOffset(t);
        copyArr[offsetTuple.getX()][offsetTuple.getY()][offsetTuple.getZ()] = false;
      }
    }
    private void dfsExpansionGrab(boolean[][][] availabilityGrid, Terrain.terrainType terrain, Tuple tuple, Hex.Team team) {
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
      game.foundSettlement(tuple, team);
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
        dfsExpansionGrab(availabilityGrid, terrain, tempTuple, team);

      }
    }



  public ArrayList<Tuple> findListOfValidSettlementLocation(Tuple coordPoint, boolean[][][] settlementChecked, ArrayList<Tuple> settlementList) {
    Hex currentHex = gameBoard.getHex(coordPoint);
    if(currentHex == null) return settlementList;
    Hex[] neighborHex = getNeighborHexes(coordPoint,gameBoard);
    Tuple[] neighborCoord = getNeighborCoords(coordPoint);
    if(currentHex.getLevel() == 1 && currentHex.getTeam() == Hex.Team.Neutral && currentHex.getTerrain() != Terrain.terrainType.Volcano)
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

  public void tileValidationListFinder(Tuple testingLocation, boolean[][][] hexCheckedforPlacement, ArrayList<Tuple> availableTilePlacement)
  {
    Hex testingLocHex;
    testingLocHex = gameBoard.getHex(testingLocation);

    Tuple coordLeft = (Orientation.leftOf(testingLocation));
    Tuple coordRight = (Orientation.rightOf(testingLocation));
    Tuple coordUpLeft = (Orientation.upLeftOf(testingLocation));
    Tuple coordUpRight = (Orientation.upRightOf(testingLocation));
    Tuple coordDownLeft = (Orientation.downLeftOf(testingLocation));
    Tuple coordDownRight = (Orientation.downRightOf(testingLocation));

    if(testingLocHex != null)
    {
      setHexChecked(testingLocation, hexCheckedforPlacement);
      if(!checkAlreadyVisited(coordUpLeft, hexCheckedforPlacement))
        tileValidationListFinder(Orientation.upLeftOf(testingLocation), hexCheckedforPlacement, availableTilePlacement);
      if(!checkAlreadyVisited(coordUpRight, hexCheckedforPlacement))
        tileValidationListFinder(Orientation.upRightOf(testingLocation), hexCheckedforPlacement, availableTilePlacement);
      if(!checkAlreadyVisited(coordLeft, hexCheckedforPlacement))
        tileValidationListFinder(Orientation.leftOf(testingLocation), hexCheckedforPlacement, availableTilePlacement);
      if(!checkAlreadyVisited(coordRight, hexCheckedforPlacement))
        tileValidationListFinder(Orientation.rightOf(testingLocation), hexCheckedforPlacement, availableTilePlacement);
      if(!checkAlreadyVisited(coordDownLeft, hexCheckedforPlacement))
        tileValidationListFinder(Orientation.downLeftOf(testingLocation), hexCheckedforPlacement, availableTilePlacement);
      if(!checkAlreadyVisited(coordDownRight, hexCheckedforPlacement))
        tileValidationListFinder(Orientation.downRightOf(testingLocation), hexCheckedforPlacement, availableTilePlacement);
    }
    else {

      Hex hexLeftOf = gameBoard.getHex(Orientation.leftOf(testingLocation));
      Hex hexRightOf = gameBoard.getHex(Orientation.rightOf(testingLocation));
      Hex hexUpLeftOf = gameBoard.getHex(Orientation.upLeftOf(testingLocation));
      Hex hexUpRightOf = gameBoard.getHex(Orientation.upRightOf(testingLocation));
      Hex hexDownLeftOf = gameBoard.getHex(Orientation.downLeftOf(testingLocation));
      Hex hexDownRightOf = gameBoard.getHex(Orientation.downRightOf(testingLocation));

      setHexChecked(testingLocation, hexCheckedforPlacement);

      if(hexLeftOf == null && hexUpLeftOf == null)
      {
        if(!checkAlreadyVisited(coordLeft, hexCheckedforPlacement) && !existsInSetAlready(coordLeft, availableTilePlacement))
          availableTilePlacement.add(coordLeft);
        if(!checkAlreadyVisited(coordUpLeft, hexCheckedforPlacement) && !existsInSetAlready(coordUpLeft,availableTilePlacement))
          availableTilePlacement.add(coordUpLeft);
        if(!existsInSetAlready(testingLocation, availableTilePlacement))
          availableTilePlacement.add(testingLocation);
        setHexChecked(testingLocation, hexCheckedforPlacement);

      }
      if(hexLeftOf == null && hexDownLeftOf == null)
      {
        if(!checkAlreadyVisited(coordLeft, hexCheckedforPlacement) && !existsInSetAlready(coordLeft, availableTilePlacement))
          availableTilePlacement.add(coordLeft);
        if(!checkAlreadyVisited(coordDownLeft, hexCheckedforPlacement) && !existsInSetAlready(coordDownLeft, availableTilePlacement))
          availableTilePlacement.add(coordDownLeft);
        if(!existsInSetAlready(testingLocation, availableTilePlacement))
          availableTilePlacement.add(testingLocation);
      }
      if(hexUpLeftOf == null && hexUpRightOf == null)
      {
        if(!checkAlreadyVisited(coordUpLeft, hexCheckedforPlacement) && !existsInSetAlready(coordUpLeft, availableTilePlacement))
          availableTilePlacement.add(coordUpLeft);
        if(!checkAlreadyVisited(coordUpRight, hexCheckedforPlacement) && !existsInSetAlready(coordUpRight, availableTilePlacement))
          availableTilePlacement.add(coordUpRight);
        if(!existsInSetAlready(testingLocation, availableTilePlacement))
          availableTilePlacement.add(testingLocation);
      }
      if(hexUpRightOf == null && hexRightOf == null)
      {
        if(!checkAlreadyVisited(coordUpRight, hexCheckedforPlacement) && !existsInSetAlready(coordUpRight, availableTilePlacement))
          availableTilePlacement.add(coordUpRight);
        if(!checkAlreadyVisited(coordRight, hexCheckedforPlacement) && !existsInSetAlready(coordRight,availableTilePlacement))
          availableTilePlacement.add(coordRight);
        if(!existsInSetAlready(testingLocation, availableTilePlacement))
          availableTilePlacement.add(testingLocation);
      }
      if(hexRightOf == null && hexDownRightOf == null)
      {
        if(!checkAlreadyVisited(coordRight, hexCheckedforPlacement) && !existsInSetAlready(coordRight,availableTilePlacement))
          availableTilePlacement.add(coordRight);
        if(!checkAlreadyVisited(coordDownRight, hexCheckedforPlacement) && !existsInSetAlready(coordDownRight,availableTilePlacement))
          availableTilePlacement.add(coordDownRight);
        if(!existsInSetAlready(testingLocation, availableTilePlacement))
          availableTilePlacement.add(testingLocation);
      }
      if(hexDownRightOf == null && hexDownLeftOf == null)
      {
        if(!checkAlreadyVisited(coordDownRight, hexCheckedforPlacement) && !existsInSetAlready(coordDownRight,availableTilePlacement))
          availableTilePlacement.add(coordDownRight);
        if(!checkAlreadyVisited(coordDownLeft, hexCheckedforPlacement) && !existsInSetAlready(coordDownLeft,availableTilePlacement))
          availableTilePlacement.add(coordDownLeft);
        if(!existsInSetAlready(testingLocation, availableTilePlacement))
          availableTilePlacement.add(testingLocation);

      }

    }
  }


  private void setHexChecked(Tuple locationVisited, boolean[][][] hexCheckedforPlacement)
  {
    hexCheckedforPlacement[locationVisited.getX()+97][locationVisited.getY()+97][locationVisited.getZ()+97] = true;
  }

  public boolean checkAlreadyVisited(Tuple coordPoint, boolean[][][] hexCheckedforPlacement)
  {
    return hexCheckedforPlacement[coordPoint.getX()+ 97][coordPoint.getY()+97][coordPoint.getZ()+97];
  }

  public boolean existsInSetAlready(Tuple coordPoint, ArrayList<Tuple> availableTilePlacement)
  {
    boolean exists = false;
    for (Tuple s : availableTilePlacement) {
      if(coordPoint.getX() == s.getX() && coordPoint.getY() == s.getY() && coordPoint.getZ() == s.getZ()) exists = true;
    }

    return exists;
  }

  public Vector<Integer> findValidOrientationsAtPoint(Tuple testingLocation)
  {

    Hex hexTestingLocation = gameBoard.getHex(testingLocation);
    Vector<Integer> validOrientations = new Vector<Integer>();

    if(hexTestingLocation != null)
    {
      validOrientations.add(-1);
    }
    else {
      Hex hexLeftOf = gameBoard.getHex(Orientation.leftOf(testingLocation));
      Hex hexRightOf = gameBoard.getHex(Orientation.rightOf(testingLocation));
      Hex hexUpLeftOf = gameBoard.getHex(Orientation.upLeftOf(testingLocation));
      Hex hexUpRightOf = gameBoard.getHex(Orientation.upRightOf(testingLocation));
      Hex hexDownLeftOf = gameBoard.getHex(Orientation.downLeftOf(testingLocation));
      Hex hexDownRightOf = gameBoard.getHex(Orientation.downRightOf(testingLocation));

      if (hexUpLeftOf == null && hexUpRightOf == null) {
        validOrientations.add(1);
      }
      if (hexUpRightOf == null && hexRightOf == null) {
        validOrientations.add(2);
      }
      if (hexRightOf == null && hexDownRightOf == null) {
        validOrientations.add(3);
      }
      if (hexDownRightOf == null && hexDownLeftOf == null) {
        validOrientations.add(4);
      }
      if (hexLeftOf == null && hexDownLeftOf == null) {
        validOrientations.add(5);
      }
      if (hexLeftOf == null && hexUpLeftOf == null) {
        validOrientations.add(6);
      }
    }
    return validOrientations;
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

    public ArrayList<Tuple> removeDuplicateTuples(ArrayList<Tuple> duplicateList) {
        ArrayList<Tuple> uniqueList = new ArrayList<>();

        for(int i = 0; i < duplicateList.size(); i++) {
            if(!uniqueList.contains(duplicateList.get(i)))
                uniqueList.add(duplicateList.get(i));
        }

        return uniqueList;
    }

    public ArrayList<Tuple> findSizeNSettlements(ArrayList<SettlementDataFrame> ourSettlements, int n, Hex.gamePieces gamePiece) {
        ArrayList<Tuple> tuplesInSettlements = new ArrayList<>();
        boolean existingPiece = false;
        for(int i = 0; i < ourSettlements.size(); i++) {
            if(ourSettlements.get(i).getSettlementSize() >= n) {
                existingPiece = false;
                for (int j = 0; j < ourSettlements.get(i).getSettlementSize(); j++)
                    if (gameBoard.getHex(ourSettlements.get(i).getListOfHexLocations().get(j)).getOccupiedBy() == gamePiece)
                        existingPiece = true;
                for (int j = 0; j < ourSettlements.get(i).getSettlementSize() && !existingPiece; j++)
                    tuplesInSettlements.add(ourSettlements.get(i).getListOfHexLocations().get(j));
            }
        }

        return tuplesInSettlements;
    }

    public ArrayList<Tuple> findValidTotoroLocations(ArrayList<Tuple> testableLocations) {
        ArrayList<Tuple> validLocations = new ArrayList<>();

        for(int i = 0; i < testableLocations.size(); i++) {
            Tuple currentTuple =  testableLocations.get(i);

            if(HexValidation.isLocationFree(Orientation.leftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.leftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.leftOf(currentTuple));
            if(HexValidation.isLocationFree(Orientation.rightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.rightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.rightOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.upLeftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.upLeftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.upLeftOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.upRightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.upRightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.upRightOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.downLeftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.downLeftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.downLeftOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.downRightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.downRightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano)
                validLocations.add(Orientation.downRightOf(currentTuple));
        } // the above loop looks around every hex in the settlements for an empty placed hex

        return validLocations;
    }

    public ArrayList<Tuple> findValidTigerLocations(ArrayList<Tuple> testableLocations) {
        ArrayList<Tuple> validLocations = new ArrayList<>();

        for(int i = 0; i < testableLocations.size(); i++) {
            Tuple currentTuple =  testableLocations.get(i);

            if(HexValidation.isLocationFree(Orientation.leftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.leftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.leftOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.leftOf(currentTuple));
            if(HexValidation.isLocationFree(Orientation.rightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.rightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.rightOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.rightOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.upLeftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.upLeftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.upLeftOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.upLeftOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.upRightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.upRightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.upRightOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.upRightOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.downLeftOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.downLeftOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.downLeftOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.downLeftOf(currentTuple));
            if (HexValidation.isLocationFree(Orientation.downRightOf(currentTuple), gameBoard)
                    && gameBoard.getHex(Orientation.downRightOf(currentTuple)).getTerrain() != Terrain.terrainType.Volcano
                    && gameBoard.getHex(Orientation.downRightOf(currentTuple)).getLevel() >= 3)
                validLocations.add(Orientation.downRightOf(currentTuple));
        } // the above loop looks around every hex in the settlements for an empty placed hex above level 3
        return validLocations;
    }

}
