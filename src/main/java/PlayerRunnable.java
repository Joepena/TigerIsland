/**
 * Created by Troy on 3/27/17.
 */
public class PlayerRunnable implements Runnable {
    @Override
    public void run() {
        GameAPI game = new GameAPI();
        System.out.println("Villager count of " + this.toString() + " is: " + game.getVillagerCount());
        System.out.println("Totoro count of " + this.toString() + " is: " + game.getTotoroCount());
        System.out.println("Tiger count of " + this.toString() + " is: " + game.getTigerCount());

        //Player

        //Receive piece

        //Check for tile placement options

        //Check for nuking options

        //Decide normal place or nuke

        //Check settlements

        //Decide move

        //Build/Expand/Tiger/Totoro

        //End Turn


    }
}
