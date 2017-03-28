import java.util.Comparator;
import java.util.TreeSet;
import javafx.util.Pair;

/**
 * Created by Joe on 3/26/17.
 */
public class Settlements {
  private TreeSet<SettlementDataFrame> listOfSettlements;

  public Settlements() {
    // comparator for sorting dataframe
    Comparator<SettlementDataFrame> dataFrameComparator =
      (SettlementDataFrame df1, SettlementDataFrame df2)->df1.getSettlementlevel().compareTo(df2.getSettlementlevel());

    listOfSettlements = new TreeSet<>(dataFrameComparator);
  }

  public TreeSet<SettlementDataFrame> getListOfSettlements() {
    return listOfSettlements;
  }

  public void addNewSettlement(SettlementDataFrame df) {
    listOfSettlements.add(df);
  }

  public void wipeSettlementSet() {
    Comparator<SettlementDataFrame> dataFrameComparator =
      (SettlementDataFrame df1, SettlementDataFrame df2)->df1.getSettlementlevel().compareTo(df2.getSettlementlevel());

    listOfSettlements = new TreeSet<>(dataFrameComparator);
  }



}
