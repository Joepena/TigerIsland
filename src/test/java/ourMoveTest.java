import org.junit.Before;
import org.junit.Test;

/**
 * Created by TomasK on 4/9/2017.
 */
public class ourMoveTest {
    public PlayerRunnable player;

    @Before
    public void setup(){
        this.player = new PlayerRunnable("A", "B");
        player.setGameID("ABC");
    }

    @Test
    public void basicMoveTest() throws Exception{
        createLandMass();
        MessageParser mP = new MessageParser();
        MakeYourMoveMessage message = (MakeYourMoveMessage)mP.parseString("MAKE YOUR MOVE IN GAME ABC WITHIN 1.5 SECONDS: MOVE 8 PLACE LAKE+ROCK");


        player.executeMessage(message);

        player.run();

    }


    public void createLandMass() throws Exception {
        Tuple origin = Orientation.getOrigin();
        Tuple tile1Loc = Orientation.rightOf(Orientation.upRightOf(origin));
        Tuple tile2Loc = Orientation.leftOf(tile1Loc);


        Tile tile0 = new Tile(0, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.downLeft);
        Tile tile1 = new Tile(1, Terrain.terrainType.Jungle, Terrain.terrainType.Grassland, Orientation.Orientations.downLeft);
        Tile tile2 = new Tile(2, Terrain.terrainType.Lake, Terrain.terrainType.Grassland, Orientation.Orientations.upRight);
        player.getGame().placeTile(tile0, origin);
        player.getGame().placeTile(tile1, tile1Loc);
        player.getGame().placeTile(tile2, tile2Loc);

    }
}
