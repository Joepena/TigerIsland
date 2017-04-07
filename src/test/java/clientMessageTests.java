import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Megans on 4/7/2017.
 */
public class clientMessageTests {
    @Test
    public void foundMessageTest()
    {
        clientMoveMessages message = new clientMoveMessages();
        message.setGid(1);
        message.setMoveNumber(1);
        message.setTile(new Tile(1, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.upLeft));
        message.setTileLocation(Orientation.getOrigin());
        message.setOrientation(1);
        message.setBuildLocation(Orientation.getOrigin());
        String testMessage = message.toString(clientMoveMessages.clientMoveMessageType.Found);
        String actualMessage = ("GAME 1 MOVE 1 PLACE GRASSLAND+JUNGLE AT 0 0 0 1 FOUND SETTLEMENT AT 0 0 0");
        Assert.assertEquals(actualMessage, testMessage);

    }

    @Test
    public void expandMessageTest()
    {
        clientMoveMessages message = new clientMoveMessages();
        message.setGid(1);
        message.setMoveNumber(1);
        message.setTile(new Tile(1, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.upLeft));
        message.setTileLocation(Orientation.getOrigin());
        message.setOrientation(1);
        message.setBuildLocation(Orientation.getOrigin());
        message.setTerrain(Terrain.terrainType.Rocky);
        String testMessage = message.toString(clientMoveMessages.clientMoveMessageType.Expand);
        String actualMessage = ("GAME 1 MOVE 1 PLACE GRASSLAND+JUNGLE AT 0 0 0 1 EXPAND SETTLEMENT AT 0 0 0 ROCKY");
        Assert.assertEquals(actualMessage, testMessage);

    }

    @Test
    public void totoroMessageTest()
    {
        clientMoveMessages message = new clientMoveMessages();
        message.setGid(1);
        message.setMoveNumber(1);
        message.setTile(new Tile(1, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.upLeft));
        message.setTileLocation(Orientation.getOrigin());
        message.setOrientation(1);
        message.setBuildLocation(Orientation.getOrigin());
        String testMessage = message.toString(clientMoveMessages.clientMoveMessageType.Totoro);
        String actualMessage = ("GAME 1 MOVE 1 PLACE GRASSLAND+JUNGLE AT 0 0 0 1 BUILD TOTORO SANCTUARY AT 0 0 0");
        Assert.assertEquals(actualMessage, testMessage);

    }

    @Test
    public void tigerMessageTest()
    {
        clientMoveMessages message = new clientMoveMessages();
        message.setGid(1);
        message.setMoveNumber(1);
        message.setTile(new Tile(1, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.upLeft));
        message.setTileLocation(Orientation.getOrigin());
        message.setOrientation(1);
        message.setBuildLocation(Orientation.getOrigin());
        String testMessage = message.toString(clientMoveMessages.clientMoveMessageType.Tiger);
        String actualMessage = ("GAME 1 MOVE 1 PLACE GRASSLAND+JUNGLE AT 0 0 0 1 BUILD TIGER PLAYGROUND AT 0 0 0");
        Assert.assertEquals(actualMessage, testMessage);

    }

    @Test
    public void unableMessageTest()
    {
        clientMoveMessages message = new clientMoveMessages();
        message.setGid(1);
        message.setMoveNumber(1);
        message.setTile(new Tile(1, Terrain.terrainType.Grassland, Terrain.terrainType.Jungle, Orientation.Orientations.upLeft));
        message.setTileLocation(Orientation.getOrigin());
        message.setOrientation(1);
        message.setBuildLocation(Orientation.getOrigin());
        String testMessage = message.toString(clientMoveMessages.clientMoveMessageType.Unable);
        String actualMessage = ("GAME 1 MOVE 1 PLACE GRASSLAND+JUNGLE AT 0 0 0 1 UNABLE TO BUILD");
        Assert.assertEquals(actualMessage, testMessage);

    }

    @Test
    public void thunderDomeMessageTest()
    {
        clientMessages message = new clientMessages();
        String testMessage = message.enterThunderdome("HI");
        String actualMessage = ("ENTER THUNDERDOME HI");
        Assert.assertEquals(actualMessage, testMessage);

    }

    @Test
    public void usernamePasswordMessageTest()
    {
        clientMessages message = new clientMessages();
        String testMessage = message.usernamePassword("MEGAN", "HELLO");
        String actualMessage = ("I AM MEGAN HELLO");
        Assert.assertEquals(actualMessage, testMessage);

    }
}
