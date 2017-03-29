import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javafx.util.Pair;

/**
 * Created by Joe on 3/26/17.
 */
public class Settlements {
  private ArrayList<SettlementDataFrame> listOfSettlements;
  private Set<SettlementDataFrame> setOfSettlements;

  public Settlements() {
   listOfSettlements = new ArrayList<>();
    setOfSettlements = new HashSet<>();
  }

  public ArrayList<SettlementDataFrame> getListOfSettlements() {
    return listOfSettlements;
  }

  public void addNewSettlement(SettlementDataFrame df) {
    if(setOfSettlements.contains(df)) return;
    setOfSettlements.add(df);
    listOfSettlements.add(df);
    Collections.sort(listOfSettlements);
  }

  public void wipeSettlementSet() {
    listOfSettlements = new ArrayList<>();
    setOfSettlements = new HashSet<>();
  }



}
