/**
 * Created by Joe on 4/12/17.
 */
public class SearchTools {
  private Board gameBoard;
  private final int BOARD_EDGE = 97;

  public SearchTools(Board gameBoard) {
    this.gameBoard = gameBoard;
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

  protected void dfsExpansionSearch(boolean[][][] availabilityGrid, ExpansionOpDataFrame df, Terrain.terrainType terrain, Tuple tuple) {
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
}
