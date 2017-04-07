/**
 * Created by Joe on 4/1/17.
 */
public class Board {

    protected Hex[][][] gameBoard;
    protected boolean[][][] gameBoardAvailability;
    private final int ORIGINOFFSET = 97;

    public Board() {
        gameBoard = new Hex[194][194][194];
        gameBoardAvailability = new boolean[194][194][194];
    }

    public boolean isOriginEmpty() {
        Tuple origin = calculateOffset(Orientation.getOrigin());
        return (gameBoard[origin.getX()][origin.getY()][origin.getZ()] == null);
    }


    public void setHex(Hex hex, Tuple coordinates) {


        if (!HexValidation.isLocationNull(coordinates, this)) {
            Hex presentHex = getHex(coordinates);
            presentHex.incrementLevel();
            presentHex.setTerrain(hex.getTerrain());
            presentHex.setTileId(hex.getTileId());
        } else {
            Tuple actualCoordinates = calculateOffset(coordinates);
            gameBoardAvailability[actualCoordinates.getX()][actualCoordinates.getY()][actualCoordinates.getZ()] = true;
            gameBoard[actualCoordinates.getX()][actualCoordinates.getY()][actualCoordinates.getZ()] = hex;
            hex.setLocation(coordinates);
            hex.incrementLevel();
        }

    }

    Hex getHex(Tuple hexLocation) {
        Tuple coordinates = calculateOffset(hexLocation);
        return gameBoard[coordinates.getX()][coordinates.getY()][coordinates.getZ()];
    }

    public boolean[][][] getGameBoardAvailability() {
        return gameBoardAvailability;
    }

    public Tuple calculateOffset(Tuple coordinates) {
        int x = coordinates.getX() + ORIGINOFFSET;
        int y = coordinates.getY() + ORIGINOFFSET;
        int z = coordinates.getZ() + ORIGINOFFSET;

        return new Tuple(x, y, z);

    }

    private Tuple removeOffset(Tuple coordinates) {
        int x = coordinates.getX() - ORIGINOFFSET;
        int y = coordinates.getY() - ORIGINOFFSET;
        int z = coordinates.getZ() - ORIGINOFFSET;

        return new Tuple(x, y, z);

    }

    void printSectionedBoard(Board boardToTranscribe) {
        // This will print out a 30x30 rectangle around the origin location

        //Hex[][] gameBoard2D = transcribe2D(this);

        Hex[][] gameBoard2D = new Hex[376][376];

        for (int x = 67; x < 127; x++) {
            for (int y = 67; y < 98; y++) {
                for (int z = 67; z < 98; z++) {
                    Hex currentHex = boardToTranscribe.getHex(new Tuple(x-97, y-97, z-97));
                    int xOffset = x - 97;
                    int yOffset = y - 97;
                    int zOffset = z - 97;
                    if (currentHex == null && gameBoard2D[(2 * xOffset) + 188][yOffset - zOffset + 188] == null) {
                        gameBoard2D[(2 * xOffset) + 188][yOffset - zOffset + 188] = null;
                    } else {
                        gameBoard2D[(2 * xOffset) + 188][yOffset - zOffset + 188] = currentHex;
                    }
                }
            }
        }


        for (int i = 183; i < 196; i++) {
            System.out.println();
            for (int j = 183; j < 196; j++) {
                if (i == 188 && j == 188) {
                    if (gameBoard2D[i][j] == null) {
                        System.out.print("***\t");
                    } else {
                        System.out.print("*");
                        System.out.print(gameBoard2D[i][j].getTileId());
                        System.out.print(gameBoard2D[i][j].getTerrainForVisualization());
                        System.out.print(gameBoard2D[i][j].getLevel());
                    }
                } else {
                    if (gameBoard2D[i][j] == null) {
                        System.out.print("---\t");
                    } else {

                        System.out.print(gameBoard2D[i][j].getTileId());
                        System.out.print(gameBoard2D[i][j].getTerrainForVisualization());
                        System.out.print(gameBoard2D[i][j].getLevel() + "\t");
                    }
                }
            }
        }
    }

/*    private Hex[][] transcribe2D(Board boardToTranscribe) {
        Hex[][] gameBoard2D = new Hex[376][376];

        for (int x = 94; x < 100; x++) {
            for (int z = 94; z < 100; z++) {
                for (int y = 94; y < 100; y++) {
                    Hex currentHex = boardToTranscribe.getHex(new Tuple(x-97, y-97, z-97));
                    int xOffset = x - 97;
                    int yOffset = y - 97;
                    int zOffset = z - 97;
                    if (currentHex == null) {
                        gameBoard2D[yOffset - zOffset + 188][2 * xOffset + 188] = null;
                    } else {
                        gameBoard2D[yOffset - zOffset + 188][2 * xOffset + 188] = currentHex;
                    }
                }
            }
        }
        return gameBoard2D;
    }*/


}
