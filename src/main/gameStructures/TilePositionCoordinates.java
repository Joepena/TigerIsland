import javafx.util.Pair;

/**
 * Created by TomasK on 3/26/2017.
 */
public class TilePositionCoordinates {

    private Tuple volcanoCoordinates;


    private Tuple leftHexCoordinates;
    private Tuple rightHexCoordinates;




    public TilePositionCoordinates(Tuple volcanoCoordinates, Orientation.Orientations leftHexOrientation){
        Tuple leftHexCoordinates = Orientation.addCoordinatesByOrientation(volcanoCoordinates, leftHexOrientation);
        Orientation.Orientations rightHexOrientation = Orientation.getRightHexMapping(leftHexOrientation);
        Tuple rightHexCoordinates = Orientation.addCoordinatesByOrientation(volcanoCoordinates, rightHexOrientation);

        this.volcanoCoordinates = volcanoCoordinates;
        this.leftHexCoordinates = leftHexCoordinates;
        this.rightHexCoordinates = rightHexCoordinates;
    }

    public Tuple getVolcanoCoordinates() {
        return volcanoCoordinates;
    }
    public Tuple getLeftHexCoordinates() {
        return leftHexCoordinates;
    }

    public Tuple getRightHexCoordinates() {
        return rightHexCoordinates;
    }


}
