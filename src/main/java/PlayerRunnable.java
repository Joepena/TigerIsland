import java.util.ArrayList;

/**
 * Created by Troy on 3/27/17.
 */
public class PlayerRunnable implements Runnable {

    private boolean gameOver;
    private boolean hasTile;
    private Tile newTile;
    private Tuple decisionCoords;
    private GameAPI game;

    @Override
    public void run() {
        gameOver = false;
        hasTile = false;
        game = new GameAPI();
        System.out.println("Villager count of " + this.toString() + " is: " + game.getVillagerCount());
        System.out.println("Totoro count of " + this.toString() + " is: " + game.getTotoroCount());
        System.out.println("Tiger count of " + this.toString() + " is: " + game.getTigerCount());

        //Player Logic
        while(!gameOver) {

            if (game.isBoardEmpty()) {

                game.placeFirstTile();

            } else {

                //Wait to receive piece
                while (!hasTile);

            }

            //Update board state
            game.updateSettlements();

            //Check for tile placement options


            //Check for nuking options
            ArrayList<Tuple> eruptionOptions = new ArrayList<>();
            eruptionOptions = game.getValidNukingLocations();

            //Decide normal place or nuke


            //Place tile
            game.placeTile(newTile, decisionCoords);

            //Check settlements
            game.updateSettlements();

            //Decide move

            //Build/Expand/Tiger/Totoro

        }
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;

            //End Turn
    }
}

