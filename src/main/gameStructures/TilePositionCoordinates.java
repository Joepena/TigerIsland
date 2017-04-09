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

    public void setVolcanoCoordinates(Tuple volcanoCoordinates) {
        this.volcanoCoordinates = volcanoCoordinates;
    }
    public Tuple getVolcanoCoordinates() {
        return volcanoCoordinates;
    }
    public Tuple getLeftHexCoordinates() {
        return leftHexCoordinates;
    }

    public void setLeftHexCoordinates(Tuple leftHexCoordinates) {
        this.leftHexCoordinates = leftHexCoordinates;
    }

    public Tuple getRightHexCoordinates() {
        return rightHexCoordinates;
    }

    public void setRightHexCoordinates(Tuple rightHexCoordinates) {
        this.rightHexCoordinates = rightHexCoordinates;
    }

}
