import java.util.TreeSet;
import javafx.util.Pair;

/**
 * Created by Joe on 3/26/17.
 */
public class Settlement {
  private TreeSet<SettlementDataFrame> listOfSettlements;

  public Settlement() {
    listOfSettlements = new TreeSet<>();
  }

  public TreeSet<SettlementDataFrame> getListOfSettlements() {
    return listOfSettlements;
  }

  public void addNewSettlement(int level, Pair<Integer,Integer> settlementStartingLocation) {
    listOfSettlements.add(new SettlementDataFrame(level, settlementStartingLocation));
  }

  
}
