import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Joe on 3/28/17.
 */
public class updateSettlementsTest {
  GameAPI gameAPI;

  @Before
  public void setUp() throws Exception {
    gameAPI = new GameAPI();
  }

  @Test
  public void updateSettlementsScenario1() throws Exception {
    setUpScenario1();
    gameAPI.updateSettlements();

    ArrayList<SettlementDataFrame> whiteActual = gameAPI.getWhiteSettlements().getListOfSettlements();
    ArrayList<SettlementDataFrame> blackActual = gameAPI.getBlackSettlements().getListOfSettlements();
    ArrayList<SettlementDataFrame> whiteExpected = new ArrayList<>();
    ArrayList<SettlementDataFrame> blackExpected = new ArrayList<>();
    setUpExpectedForScenario1(whiteExpected,blackExpected);
    Boolean whiteActualFlag = true;
    Boolean blackActualFlag = true;

    if(whiteActual.size() != whiteExpected.size()){
      whiteActualFlag = false;
    }
    else{
      for(int i= 0; i < whiteActual.size(); i++) {
        SettlementDataFrame dfActual = whiteActual.get(0);
        SettlementDataFrame dfExpected = whiteExpected.get(0);
        if(dfActual.getOwnedBy() != dfExpected.getOwnedBy() || dfActual.getSettlementlevel() != dfExpected.getSettlementlevel()) {
          whiteActualFlag = false;
          break;
        }

      }
    }
    if(blackActual.size() != blackExpected.size()){
      blackActualFlag = false;
    }
    else{
      for(int i= 0; i < blackActual.size(); i++) {
        SettlementDataFrame dfActual = blackActual.get(0);
        SettlementDataFrame dfExpected = blackExpected.get(0);
        if(dfActual.getOwnedBy() != dfExpected.getOwnedBy() || dfActual.getSettlementlevel() != dfExpected.getSettlementlevel()) {
          blackActualFlag = false;
          break;
        }

      }
    }
    Assert.assertTrue("White settlement verification scenario 1",whiteActualFlag);
    Assert.assertTrue("Black settlement verification scenario 1",blackActualFlag);

  }
  @Test
  public void updateSettlementsScenario2() throws Exception {
    setUpScenario2();
    gameAPI.updateSettlements();

    ArrayList<SettlementDataFrame> whiteActual = gameAPI.getWhiteSettlements().getListOfSettlements();
    ArrayList<SettlementDataFrame> blackActual = gameAPI.getBlackSettlements().getListOfSettlements();
    ArrayList<SettlementDataFrame> whiteExpected = new ArrayList<>();
    ArrayList<SettlementDataFrame> blackExpected = new ArrayList<>();
    setUpExpectedForScenario2(whiteExpected,blackExpected);
    Boolean whiteActualFlag = true;
    Boolean blackActualFlag = true;

    if(whiteActual.size() != whiteExpected.size()){
      whiteActualFlag = false;
    }
    else{
      for(int i= 0; i < whiteActual.size(); i++) {
        SettlementDataFrame dfActual = whiteActual.get(0);
        SettlementDataFrame dfExpected = whiteExpected.get(0);
        if(dfActual.getOwnedBy() != dfExpected.getOwnedBy() || dfActual.getSettlementlevel() != dfExpected.getSettlementlevel()) {
          whiteActualFlag = false;
          break;
        }

      }
    }
    if(blackActual.size() != blackExpected.size()){
      blackActualFlag = false;
    }
    else{
      for(int i= 0; i < blackActual.size(); i++) {
        SettlementDataFrame dfActual = blackActual.get(0);
        SettlementDataFrame dfExpected = blackExpected.get(0);
        if(dfActual.getOwnedBy() != dfExpected.getOwnedBy() || dfActual.getSettlementlevel() != dfExpected.getSettlementlevel()) {
          blackActualFlag = false;
          break;
        }

      }
    }
    Assert.assertTrue("White settlement verification scenario 2",whiteActualFlag);
    Assert.assertTrue("Black settlement verification scenario 2",blackActualFlag);

  }

  private void setUpScenario1() {
    Tile originTile = new Tile(1,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.downLeft);
    originTile.getLeft().setTeam(Hex.Team.White);
    originTile.getRight().setTeam(Hex.Team.Black);
    Tile tile2 = new Tile(2,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.upLeft);
    tile2.getLeft().setTeam(Hex.Team.White);
    tile2.getRight().setTeam(Hex.Team.Black);
    Tile tile3 = new Tile(3,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.downRight);
    tile3.getLeft().setTeam(Hex.Team.Black);
    tile3.getRight().setTeam(Hex.Team.Black);
    gameAPI.placeTile(originTile,Orientation.getRelativeOriginValue());
    gameAPI.placeTile(tile2,Orientation.addPairByOrientation(Orientation.getRelativeOriginValue(),Orientation.Orientations.upLeft));
    gameAPI.placeTile(tile3,Orientation.addPairByOrientation(Orientation.getRelativeOriginValue(),Orientation.Orientations.upRight));
  }
  private void setUpExpectedForScenario1(ArrayList<SettlementDataFrame> whiteExpected, ArrayList<SettlementDataFrame> blackExpected) {
    whiteExpected.add(new SettlementDataFrame(1, new Pair<>(0,0)));
    whiteExpected.get(0).setOwnedBy(Hex.Team.White);
    whiteExpected.add(new SettlementDataFrame(1, new Pair<>(0,0)));
    whiteExpected.get(1).setOwnedBy(Hex.Team.White);

    blackExpected.add(new SettlementDataFrame(3, new Pair<>(0,0)));
    blackExpected.get(0).setOwnedBy(Hex.Team.Black);
    blackExpected.add(new SettlementDataFrame(1, new Pair<>(0,0)));
    blackExpected.get(1).setOwnedBy(Hex.Team.Black);
  }

  private void setUpScenario2() {
    Tile originTile = new Tile(1,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.downLeft);
    originTile.getLeft().setTeam(Hex.Team.White);
    originTile.getRight().setTeam(Hex.Team.Black);
    Tile tile2 = new Tile(2,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.upLeft);
    tile2.getLeft().setTeam(Hex.Team.White);
    tile2.getRight().setTeam(Hex.Team.Black);
    Tile tile3 = new Tile(3,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.downRight);
    tile3.getLeft().setTeam(Hex.Team.White);
    tile3.getRight().setTeam(Hex.Team.Black);
    gameAPI.placeTile(originTile,Orientation.getRelativeOriginValue());
    gameAPI.placeTile(tile2,Orientation.addPairByOrientation(Orientation.getRelativeOriginValue(),Orientation.Orientations.upLeft));
    gameAPI.placeTile(tile3,Orientation.addPairByOrientation(Orientation.getRelativeOriginValue(),Orientation.Orientations.upRight));
  }
  private void setUpExpectedForScenario2(ArrayList<SettlementDataFrame> whiteExpected, ArrayList<SettlementDataFrame> blackExpected) {
    whiteExpected.add(new SettlementDataFrame(1, new Pair<>(0,0)));
    whiteExpected.get(0).setOwnedBy(Hex.Team.White);
    whiteExpected.add(new SettlementDataFrame(1, new Pair<>(0,0)));
    whiteExpected.get(1).setOwnedBy(Hex.Team.White);
    whiteExpected.add(new SettlementDataFrame(1, new Pair<>(0,0)));
    whiteExpected.get(2).setOwnedBy(Hex.Team.White);

    blackExpected.add(new SettlementDataFrame(1, new Pair<>(0,0)));
    blackExpected.get(0).setOwnedBy(Hex.Team.Black);
    blackExpected.add(new SettlementDataFrame(1, new Pair<>(0,0)));
    blackExpected.get(1).setOwnedBy(Hex.Team.Black);
    blackExpected.add(new SettlementDataFrame(1, new Pair<>(0,0)));
    blackExpected.get(2).setOwnedBy(Hex.Team.Black);
  }

}
