import java.util.ArrayList;

/**
 * Created by Troy on 3/27/17.
 */
public class PlayerRunnable implements Runnable {

    private boolean gameOver;
    private boolean gotMessage;
    private Tile newTile;
    private Tuple decisionCoords;
    private GameAPI game;

    @Override
    public void run() {
        gameOver = false;
        //hasTile = false;
        game = new GameAPI();
        System.out.println("Villager count of " + this.toString() + " is: " + game.getVillagerCount());
        System.out.println("Totoro count of " + this.toString() + " is: " + game.getTotoroCount());
        System.out.println("Tiger count of " + this.toString() + " is: " + game.getTigerCount());

        //Instantiate all ArrayLists once
        ArrayList<Tuple> tilePlacementOptions = new ArrayList<>();
        ArrayList<Tuple> eruptionOptions = new ArrayList<>();
        ArrayList<Tuple> foundSettlementOptions = new ArrayList<>();
        ArrayList<ExpansionOpDataFrame> expandSettlementOptions = new ArrayList<>();
        ArrayList<Tuple> totoroPlacementOptions = new ArrayList<>();
        ArrayList<Tuple> tigerPlacementOptions = new ArrayList<>();


        //Player Logic

        game.placeFirstTile();

        while(!gameOver) {

            while(!gotMessage);

            //Update board state
            game.updateSettlements();

            //Check for tile placement options
            tilePlacementOptions = game.getAvailableTilePlacement();

            //Check for nuking options
            eruptionOptions = game.getValidNukingLocations();

            //Decide normal place or nuke



            //Place tile
            game.placeTile(newTile, decisionCoords);

            //Update board state
            game.updateSettlements();

            //Check for Found Settlement options
            foundSettlementOptions = game.findListOfValidSettlementLocations();

            //Check for Expand Settlement options
            expandSettlementOptions = game.getExpansionOptions(Hex.Team.Black);

            //Check for Totoro placement options
            totoroPlacementOptions = game.validTotoroPlacements();

            //Check for Tiger placement options
            tigerPlacementOptions = game.validTigerPlacements();

            //Decide Build Action

            //Perform Build action

        }
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;

            //End Turn
    }

    public void receiveMessage(Message m) {
        gotMessage = true;
    }
}

