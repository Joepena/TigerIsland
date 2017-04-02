/**
 * Created by Joe on 4/1/17.
 */
public class Tuple {
  private int x;
  private int y;
  private int z;

  public Tuple(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public int getY() {
    return y;
  }

  public int getZ() {
    return z;
  }

  public int getX() {
    return x;
  }

  public String toString() {
    return " " + x + " " + y + " " + z;
  }

  public boolean equals(Object o) {
    Tuple tuple = (Tuple)o;
    return((this.x == tuple.x) && (this.y == tuple.y) && (this.z == tuple.z));
  }
}
