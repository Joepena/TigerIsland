import java.util.ArrayList;
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
        if(dfActual.getOwnedBy() != dfExpected.getOwnedBy() || dfActual.getSettlementSize() != dfExpected.getSettlementSize()) {
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
        if(dfActual.getOwnedBy() != dfExpected.getOwnedBy() || dfActual.getSettlementSize() != dfExpected.getSettlementSize()) {
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
        if(dfActual.getOwnedBy() != dfExpected.getOwnedBy() || dfActual.getSettlementSize() != dfExpected.getSettlementSize()) {
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
        if(dfActual.getOwnedBy() != dfExpected.getOwnedBy() || dfActual.getSettlementSize() != dfExpected.getSettlementSize()) {
          blackActualFlag = false;
          break;
        }

      }
    }
    Assert.assertTrue("White settlement verification scenario 2",whiteActualFlag);
    Assert.assertTrue("Black settlement verification scenario 2",blackActualFlag);

  }
  @Test
  public void updateSettlementsScenario3() throws Exception {
    setUpScenario3();
    gameAPI.updateSettlements();

    ArrayList<SettlementDataFrame> whiteActual = gameAPI.getWhiteSettlements().getListOfSettlements();
    ArrayList<SettlementDataFrame> blackActual = gameAPI.getBlackSettlements().getListOfSettlements();
    ArrayList<SettlementDataFrame> whiteExpected = new ArrayList<>();
    ArrayList<SettlementDataFrame> blackExpected = new ArrayList<>();
    setUpExpectedForScenario3(whiteExpected,blackExpected);
    Boolean whiteActualFlag = true;
    Boolean blackActualFlag = true;

    if(whiteActual.size() != whiteExpected.size()){
      whiteActualFlag = false;
    }
    else{
      for(int i= 0; i < whiteActual.size(); i++) {
        SettlementDataFrame dfActual = whiteActual.get(0);
        SettlementDataFrame dfExpected = whiteExpected.get(0);
        if(dfActual.getOwnedBy() != dfExpected.getOwnedBy() || dfActual.getSettlementSize() != dfExpected.getSettlementSize()) {
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
        if(dfActual.getOwnedBy() != dfExpected.getOwnedBy() || dfActual.getSettlementSize() != dfExpected.getSettlementSize()) {
          blackActualFlag = false;
          break;
        }

      }
    }
    Assert.assertTrue("White settlement verification scenario 3",whiteActualFlag);
    Assert.assertTrue("Black settlement verification scenario 3",blackActualFlag);

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
    gameAPI.placeTile(originTile,Orientation.getOrigin());
    gameAPI.placeTile(tile2,Orientation.upLeftOf(Orientation.getOrigin()));
    gameAPI.placeTile(tile3,Orientation.upRightOf(Orientation.getOrigin()));
  }
  private void setUpExpectedForScenario1(ArrayList<SettlementDataFrame> whiteExpected, ArrayList<SettlementDataFrame> blackExpected) {
    whiteExpected.add(new SettlementDataFrame(1, Orientation.getOrigin()));
    whiteExpected.get(0).setOwnedBy(Hex.Team.White);
    whiteExpected.add(new SettlementDataFrame(1, Orientation.getOrigin()));
    whiteExpected.get(1).setOwnedBy(Hex.Team.White);

    blackExpected.add(new SettlementDataFrame(3, Orientation.getOrigin()));
    blackExpected.get(0).setOwnedBy(Hex.Team.Black);
    blackExpected.add(new SettlementDataFrame(1, Orientation.getOrigin()));
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
    gameAPI.placeTile(originTile,Orientation.getOrigin());
    gameAPI.placeTile(tile2,Orientation.addCoordinatesByOrientation(Orientation.getOrigin(),Orientation.Orientations.upLeft));
    gameAPI.placeTile(tile3,Orientation.addCoordinatesByOrientation(Orientation.getOrigin(),Orientation.Orientations.upRight));
  }
  private void setUpExpectedForScenario2(ArrayList<SettlementDataFrame> whiteExpected, ArrayList<SettlementDataFrame> blackExpected) {
    whiteExpected.add(new SettlementDataFrame(1, Orientation.getOrigin()));
    whiteExpected.get(0).setOwnedBy(Hex.Team.White);
    whiteExpected.add(new SettlementDataFrame(1, Orientation.getOrigin()));
    whiteExpected.get(1).setOwnedBy(Hex.Team.White);
    whiteExpected.add(new SettlementDataFrame(1, Orientation.getOrigin()));
    whiteExpected.get(2).setOwnedBy(Hex.Team.White);

    blackExpected.add(new SettlementDataFrame(1, Orientation.getOrigin()));
    blackExpected.get(0).setOwnedBy(Hex.Team.Black);
    blackExpected.add(new SettlementDataFrame(1, Orientation.getOrigin()));
    blackExpected.get(1).setOwnedBy(Hex.Team.Black);
    blackExpected.add(new SettlementDataFrame(1, Orientation.getOrigin()));
    blackExpected.get(2).setOwnedBy(Hex.Team.Black);
  }

  private void setUpScenario3() {
    Tile originTile = new Tile(1,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.downLeft);
    originTile.getLeft().setTeam(Hex.Team.White);
    originTile.getRight().setTeam(Hex.Team.White);
    Tile tile2 = new Tile(2,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.upLeft);
    tile2.getLeft().setTeam(Hex.Team.White);
    tile2.getRight().setTeam(Hex.Team.Black);
    Tile tile3 = new Tile(3,Terrain.terrainType.Grassland,Terrain.terrainType.Jungle,Orientation.Orientations.downRight);
    tile3.getLeft().setTeam(Hex.Team.White);
    tile3.getRight().setTeam(Hex.Team.White);
    gameAPI.placeTile(originTile,Orientation.getOrigin());
    gameAPI.placeTile(tile2,Orientation.addCoordinatesByOrientation(Orientation.getOrigin(),Orientation.Orientations.upLeft));
    gameAPI.placeTile(tile3,Orientation.addCoordinatesByOrientation(Orientation.getOrigin(),Orientation.Orientations.upRight));
  }

  private void setUpExpectedForScenario3(ArrayList<SettlementDataFrame> whiteExpected, ArrayList<SettlementDataFrame> blackExpected) {
    whiteExpected.add(new SettlementDataFrame(4, Orientation.getOrigin()));
    whiteExpected.get(0).setOwnedBy(Hex.Team.White);
    whiteExpected.add(new SettlementDataFrame(1, Orientation.getOrigin()));
    whiteExpected.get(1).setOwnedBy(Hex.Team.White);

    blackExpected.add(new SettlementDataFrame(1, Orientation.getOrigin()));
    blackExpected.get(0).setOwnedBy(Hex.Team.Black);
  }

}
