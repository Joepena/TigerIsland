import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


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

  public void removeSettlement(SettlementDataFrame df){
    setOfSettlements.remove(df);
    listOfSettlements.remove(df);
    Collections.sort(listOfSettlements);
  }

  public void wipeSettlementSet() {
    listOfSettlements = new ArrayList<>();
    setOfSettlements = new HashSet<>();
  }


  public static void retriveWhiteSettlements(Settlements settlement, Settlements target) {
    ArrayList<SettlementDataFrame> settlementList = settlement.getListOfSettlements();
    target.wipeSettlementSet();
    settlementList.forEach((settlementDataFrame -> {
      if(settlementDataFrame.getOwnedBy() == Hex.Team.White) target.addNewSettlement(settlementDataFrame);
    }));
  }

  public static void retriveBlackSettlements(Settlements settlement, Settlements target) {
    ArrayList<SettlementDataFrame> settlementList = settlement.getListOfSettlements();
    target.wipeSettlementSet();
    settlementList.forEach((settlementDataFrame -> {
      if(settlementDataFrame.getOwnedBy() == Hex.Team.Black) target.addNewSettlement(settlementDataFrame);
    }));
  }


}
