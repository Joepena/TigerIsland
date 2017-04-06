import javafx.util.Pair;
import java.util.*;

/**
 * Created by Megans on 3/26/2017.
 */
public class TileValidation {

    Set<Tuple> availableTilePlacement = new HashSet<Tuple>();
    protected boolean[][][] hexCheckedforPlacement = new boolean[194][194][194];
    public Set<Tuple> getAvailableTilePlacement()
    {
        return availableTilePlacement;
    }
    private void setHexChecked(Tuple locationVisited)
    {
        hexCheckedforPlacement[locationVisited.getX()+97][locationVisited.getY()+97][locationVisited.getZ()+97] = true;
    }

    public boolean checkAlreadyVisited(Tuple coordPoint)
    {
        return hexCheckedforPlacement[coordPoint.getX()+ 97][coordPoint.getY()+97][coordPoint.getZ()+97];
    }

    public boolean existsInSetAlready(Tuple coordPoint)
    {
        boolean exists = false;
        for (Tuple s : availableTilePlacement) {
            if(coordPoint.getX() == s.getX() && coordPoint.getY() == s.getY() && coordPoint.getZ() == s.getZ()) exists = true;
        }

        return exists;
    }

    public Vector<Integer> findValidOrientationsAtPoint(Tuple testingLocation,Board gameBoard)
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



    public void tileValidationListFinder(Tuple testingLocation, Board gameBoard)
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
            setHexChecked(testingLocation);
            if(checkAlreadyVisited(coordUpLeft) == false)
                tileValidationListFinder(Orientation.upLeftOf(testingLocation),  gameBoard);
            if(checkAlreadyVisited(coordUpRight) == false)
                tileValidationListFinder(Orientation.upRightOf(testingLocation), gameBoard);
            if(checkAlreadyVisited(coordLeft) == false)
                tileValidationListFinder(Orientation.leftOf(testingLocation), gameBoard);
            if(checkAlreadyVisited(coordRight) == false)
                tileValidationListFinder(Orientation.rightOf(testingLocation), gameBoard);
            if(checkAlreadyVisited(coordDownLeft) == false)
                tileValidationListFinder(Orientation.downLeftOf(testingLocation), gameBoard);
            if(checkAlreadyVisited(coordDownRight) == false)
                tileValidationListFinder(Orientation.downRightOf(testingLocation), gameBoard);
        }
        else {

            Hex hexLeftOf = gameBoard.getHex(Orientation.leftOf(testingLocation));
            Hex hexRightOf = gameBoard.getHex(Orientation.rightOf(testingLocation));
            Hex hexUpLeftOf = gameBoard.getHex(Orientation.upLeftOf(testingLocation));
            Hex hexUpRightOf = gameBoard.getHex(Orientation.upRightOf(testingLocation));
            Hex hexDownLeftOf = gameBoard.getHex(Orientation.downLeftOf(testingLocation));
            Hex hexDownRightOf = gameBoard.getHex(Orientation.downRightOf(testingLocation));

            setHexChecked(testingLocation);

            if(hexLeftOf == null && hexUpLeftOf == null)
            {
                if(checkAlreadyVisited(coordLeft) == false && !existsInSetAlready(coordLeft))
                    availableTilePlacement.add(coordLeft);
                if(checkAlreadyVisited(coordUpLeft) == false && !existsInSetAlready(coordUpLeft))
                    availableTilePlacement.add(coordUpLeft);
                if(!existsInSetAlready(testingLocation))
                    availableTilePlacement.add(testingLocation);
                setHexChecked(testingLocation);

            }
            if(hexLeftOf == null && hexDownLeftOf == null)
            {
                if(checkAlreadyVisited(coordLeft) == false && !existsInSetAlready(coordLeft))
                    availableTilePlacement.add(coordLeft);
                if(checkAlreadyVisited(coordDownLeft) == false && !existsInSetAlready(coordDownLeft))
                    availableTilePlacement.add(coordDownLeft);
                if(!existsInSetAlready(testingLocation))
                    availableTilePlacement.add(testingLocation);
                setHexChecked(testingLocation);
            }
            if(hexUpLeftOf == null && hexUpRightOf == null)
            {
                if(checkAlreadyVisited(coordUpLeft) == false && !existsInSetAlready(coordUpLeft))
                    availableTilePlacement.add(coordUpLeft);
                if(checkAlreadyVisited(coordUpRight) == false && !existsInSetAlready(coordUpRight))
                    availableTilePlacement.add(coordUpRight);
                if(!existsInSetAlready(testingLocation))
                    availableTilePlacement.add(testingLocation);
                setHexChecked(testingLocation);
            }
            if(hexUpRightOf == null && hexRightOf == null)
            {
                if(checkAlreadyVisited(coordUpRight) == false && !existsInSetAlready(coordUpRight))
                    availableTilePlacement.add(coordUpRight);
                if(checkAlreadyVisited(coordRight) == false && !existsInSetAlready(coordRight))
                    availableTilePlacement.add(coordRight);
                if(!existsInSetAlready(testingLocation))
                    availableTilePlacement.add(testingLocation);
            }
            if(hexRightOf == null && hexDownRightOf == null)
            {
                if(checkAlreadyVisited(coordRight) == false && !existsInSetAlready(coordRight))
                    availableTilePlacement.add(coordRight);
                if(checkAlreadyVisited(coordDownRight) == false && !existsInSetAlready(coordDownRight))
                    availableTilePlacement.add(coordDownRight);
                if(!existsInSetAlready(testingLocation))
                    availableTilePlacement.add(testingLocation);
                setHexChecked(testingLocation);
            }
            if(hexDownRightOf == null && hexDownLeftOf == null)
            {
                if(checkAlreadyVisited(coordDownRight) == false && !existsInSetAlready(coordDownRight))
                    availableTilePlacement.add(coordDownRight);
                if(checkAlreadyVisited(coordDownLeft) == false && !existsInSetAlready(coordDownLeft))
                    availableTilePlacement.add(coordDownLeft);
                if(!existsInSetAlready(testingLocation))
                    availableTilePlacement.add(testingLocation);

            }

        }
    }

}
