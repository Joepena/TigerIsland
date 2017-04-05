import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Joe on 4/5/17.
 */
public class ExpansionOptions {
  private ArrayList<ExpansionOpDataFrame> listOfExpansionOptions;
  private Set<ExpansionOpDataFrame> setOfExpansionOptions;

  public ExpansionOptions() {
    listOfExpansionOptions = new ArrayList<>();
    setOfExpansionOptions = new HashSet<>();
  }

  public ArrayList<ExpansionOpDataFrame> getListOfExpansionOptions() {
    return listOfExpansionOptions;
  }

  public void addNewExpansionOption(ExpansionOpDataFrame df) {
    if(setOfExpansionOptions.contains(df)) return;
    setOfExpansionOptions.add(df);
    listOfExpansionOptions.add(df);
    Collections.sort(listOfExpansionOptions);
  }

  public void wipeExpansionOptionsSet() {
    listOfExpansionOptions = new ArrayList<>();
    setOfExpansionOptions = new HashSet<>();
  }


  public static void retriveWhiteSettlementExpansions(Settlements settlement, Settlements target) {
    ArrayList<SettlementDataFrame> settlementList = settlement.getListOfSettlements();
    target.wipeSettlementSet();
    settlementList.forEach((settlementDataFrame -> {
      if(settlementDataFrame.getOwnedBy() == Hex.Team.White) target.addNewSettlement(settlementDataFrame);
    }));
  }

  public static void retriveBlackSettlementExpansions(Settlements settlement, Settlements target) {
    ArrayList<SettlementDataFrame> settlementList = settlement.getListOfSettlements();
    target.wipeSettlementSet();
    settlementList.forEach((settlementDataFrame -> {
      if(settlementDataFrame.getOwnedBy() == Hex.Team.Black) target.addNewSettlement(settlementDataFrame);
    }));
  }
}
