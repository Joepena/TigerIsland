/**
 * Created by Joe & Troy on 3/19/17.
 */
public class Tile {
  private Hex volcano;
  private Hex left;
  private Hex right;
  private Orientation.Orientations leftHexOrientation;
  private int tileId;

  public Tile(int tileId, Terrain.terrainType leftTerr, Terrain.terrainType rightTerr, Orientation.Orientations leftHexOrientation) {
    this.tileId = tileId;
    volcano = new Hex(tileId, Terrain.terrainType.Volcano);
    left = new Hex(tileId, leftTerr);
    right = new Hex(tileId, rightTerr);
    this.leftHexOrientation = leftHexOrientation;
  }

  public int getTileId() {
    return tileId;
  }
  // volcano is given because placement is in respect to volcano hex
  public Hex getVolcano() {
    return volcano;
  }

  public Hex getLeft() {
    return left;
  }

  public Hex getRight() {
    return right;
  }

  public Orientation.Orientations getLeftHexOrientation() {
    return leftHexOrientation;
  }

  public void setLeftHexOrientation(Orientation.Orientations leftHexOrientation){ this.leftHexOrientation = leftHexOrientation;}

  public String toString(){
    return "Terrains(V, L, R):  " + volcano.getTerrain() + ", " + left.getTerrain() + ", " + right.getTerrain() + "\n" + "Orientation: " + leftHexOrientation + "\ntileId: " + tileId;
  }
}
