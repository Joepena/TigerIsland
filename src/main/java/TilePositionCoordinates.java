import javafx.util.Pair;

/**
 * Created by TomasK on 3/26/2017.
 */
public class TilePositionCoordinates {

    private Pair<Integer, Integer> volcanoCoordinates;


    private Pair<Integer, Integer> leftHexCoordinates;
    private Pair<Integer, Integer> rightHexCoordinates;




    public TilePositionCoordinates(Pair<Integer,Integer> volcanoCoordinates, Orientation.Orientations leftHexOrientation){
        Pair<Integer, Integer> leftHexCoordinates = Orientation.addPairByOrientation(volcanoCoordinates, leftHexOrientation);
        Orientation.Orientations rightHexOrientation = Orientation.getRightHexMapping(leftHexOrientation);
        Pair<Integer, Integer> rightHexCoordinates = Orientation.addPairByOrientation(volcanoCoordinates, rightHexOrientation);

        this.volcanoCoordinates = volcanoCoordinates;
        this.leftHexCoordinates = leftHexCoordinates;
        this.rightHexCoordinates = rightHexCoordinates;
    }

    public void setVolcanoCoordinates(Pair<Integer, Integer> volcanoCoordinates) {
        this.volcanoCoordinates = volcanoCoordinates;
    }
    public Pair<Integer, Integer> getVolcanoCoordinates() {
        return volcanoCoordinates;
    }
    public Pair<Integer, Integer> getLeftHexCoordinates() {
        return leftHexCoordinates;
    }

    public void setLeftHexCoordinates(Pair<Integer, Integer> leftHexCoordinates) {
        this.leftHexCoordinates = leftHexCoordinates;
    }

    public Pair<Integer, Integer> getRightHexCoordinates() {
        return rightHexCoordinates;
    }

    public void setRightHexCoordinates(Pair<Integer, Integer> rightHexCoordinates) {
        this.rightHexCoordinates = rightHexCoordinates;
    }

}
