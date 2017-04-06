import java.util.Vector;

/**
 * Created by Megans on 4/2/2017.
 */
public class SettlementCreationList {
    private Vector<Tuple> settlementList = new Vector<Tuple>();
    private boolean[][][] settlementChecked = new boolean[194][194][194];
    protected Hex[] getNeighborHexes(Tuple coordPoint, Board gameBoard)
    {
        Hex[] neighbors = new Hex[6];

        neighbors[0] = gameBoard.getHex(Orientation.leftOf(coordPoint));
        neighbors[1] = gameBoard.getHex(Orientation.rightOf(coordPoint));
        neighbors[2] = gameBoard.getHex(Orientation.upLeftOf(coordPoint));
        neighbors[3] = gameBoard.getHex(Orientation.upRightOf(coordPoint));
        neighbors[4] = gameBoard.getHex(Orientation.downLeftOf(coordPoint));
        neighbors[5] = gameBoard.getHex(Orientation.downRightOf(coordPoint));

        return neighbors;
    }

    private Tuple[] getNeighborCoords(Tuple coordPoint)
    {
        Tuple[] neighbors = new Tuple[6];
        neighbors[0] = (Orientation.leftOf(coordPoint));
        neighbors[1] = (Orientation.rightOf(coordPoint));
        neighbors[2] = (Orientation.upLeftOf(coordPoint));
        neighbors[3] = (Orientation.upRightOf(coordPoint));
        neighbors[4] = (Orientation.downLeftOf(coordPoint));
        neighbors[5] = (Orientation.downRightOf(coordPoint));
        return neighbors;
    }
    public boolean existsAlreadyInList(Tuple coordPoint)
    {
        boolean exists = false;
        for(int i=0; i < settlementList.size(); i++)
        {
            if(coordPoint.getX() == settlementList.get(i).getX()
                    && coordPoint.getY() == settlementList.get(i).getY() && coordPoint.getZ() == settlementList.get(i).getZ())
                exists = true;
        }
        return true;
    }
    private void setSettlementCheck(Tuple coordPoint)
    {
        settlementChecked[coordPoint.getX()+97][coordPoint.getY()+97][coordPoint.getZ()+97] = true;
    }

    private boolean getSettlementCheck(Tuple coordPoint)
    {
        return settlementChecked[coordPoint.getX()+97][coordPoint.getY()+97][coordPoint.getZ()+97];
    }

    public Vector<Tuple> findListOfValidSettlementLocation(Tuple coordPoint, Board gameBoard)
    {
        Hex currentHex = gameBoard.getHex(coordPoint);
        if(currentHex == null) return settlementList;
        Hex[] neighborHex = getNeighborHexes(coordPoint,gameBoard);
        Tuple[] neighborCoord = getNeighborCoords(coordPoint);
        if(currentHex.getLevel() == 1 && currentHex.getTeam() == Hex.Team.Neutral)
        {
            settlementList.add(coordPoint);
        }
        setSettlementCheck(coordPoint);
        for(int i=0; i < neighborCoord.length; i++) {
            if (!getSettlementCheck(neighborCoord[i]) && neighborHex[i] != null)
                findListOfValidSettlementLocation(neighborCoord[i], gameBoard);
        }
        return settlementList;
    }
}
