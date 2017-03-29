
import javafx.util.Pair;

/**
 * Created by Joe on 3/26/17.
 */
public class SettlementDataFrame implements Comparable<SettlementDataFrame>{
  private Integer settlementSize;
  private Pair<Integer,Integer> settlementStartingLocation;
  private Hex.Team ownedBy;
  private Hex.gamePieces gamePiece;

  public Hex.gamePieces getGamePiece() {
    return gamePiece;
  }

  public void setGamePiece(Hex.gamePieces gamePiece) {
    this.gamePiece = gamePiece;
  }

  public SettlementDataFrame(int settlementlevel,
    Pair<Integer, Integer> settlementStartingLocation) {
    this.settlementSize = settlementlevel;
    this.settlementStartingLocation = settlementStartingLocation;
  }

  public Integer getSettlementSize() {
    return settlementSize;
  }

  public Pair<Integer, Integer> getSettlementStartingLocation() {
    return settlementStartingLocation;
  }

  public void setSettlementSize(Integer settlementSize) {
    this.settlementSize = settlementSize;
  }

  public void setSettlementStartingLocation(
    Pair<Integer, Integer> settlementStartingLocation) {
    this.settlementStartingLocation = settlementStartingLocation;
  }

  public void setOwnedBy(Hex.Team ownedBy) {
    this.ownedBy = ownedBy;
  }

  public Hex.Team getOwnedBy() {
    return ownedBy;
  }

  @Override
  public int compareTo(SettlementDataFrame o) {
    return -1 * settlementSize.compareTo(o.settlementSize);
  }

}


