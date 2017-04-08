import java.util.ArrayList;

/**
 * Created by Troy on 3/27/17.
 */
public class PlayerRunnable implements Runnable {

    private boolean gameOver;

    private boolean gotMessage;

    private boolean hasMove;

    private Tile newTile;
    private Tuple decisionCoords;
    private GameAPI game;
    private Hex.Team playerTeam;
    private Hex.Team opponentTeam;
    private String playerID;
    private String opponentID;
    private String gameID;
    private clientMessages moveMessage;

    public GameAPI getGame() {
        return game;
    }

    public PlayerRunnable (String playerID, String opponentID, String gameID){
        this.playerID = playerID;
        this.opponentID = opponentID;
        this.gameID = gameID;
        this.newTile = null;
        this.gameOver = false;
        this.hasMove = false;
        this.decisionCoords = null;
        this.game = new GameAPI();
        this.moveMessage = null;
    }

    @Override
    public void run() {
        gameOver = false;
        hasMove = false;

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

    public void executeMessage(Message message){

        Message.MessageType type = message.getMessageType();

        switch(type){
            case GameOver:
                GameOverMessage gameOverMessage = (GameOverMessage)message;
                if(!gameOverMessage.getGid().equals(this.gameID))
                    return;
                GameOver(gameOverMessage);
                break;
            case MakeYourMove:
                MakeYourMoveMessage makeYourMove = (MakeYourMoveMessage) message;
                if(!makeYourMove.getGid().equals(this.gameID))
                    return;
                MakeYourMove(makeYourMove);
                break;
            case Move:
                MoveMessage move = (MoveMessage) message;
                if(!move.getGid().equals(this.gameID))
                    return;
                Move(move);
                break;
        }
    }

    private void GameOver(GameOverMessage message){
        this.gameOver = true;
    }

    private void MakeYourMove(MakeYourMoveMessage message){
        this.newTile = message.getTile();
        this.hasMove = true;
    }


    private void Move(MoveMessage message){
        switch(message.getMoveType()){
            case Found:
                FoundMove((FoundSettlementMessage) message);
                break;
            case Expand:
                Expand((ExpandSettlementMessage) message);
                break;
            case Forfeit:
                Forfeit((ForfeitMessage) message);
                break;
            case Tiger:
                Tiger((BuildTigerMessage) message);
                break;
            case Totoro:
                Totoro((BuildTotoroMessage) message);
                break;
        }
    }

    private void FoundMove(FoundSettlementMessage message){
        if(message.getPid().equals(this.playerID))
            return;
        game.placeTile(message.getTile(), message.getTileLocation());
        game.foundSettlement(message.getBuildLocation(), this.opponentTeam);
    }

    private void Expand(ExpandSettlementMessage message){
        if(message.getPid().equals(this.playerID))
            return;
        game.placeTile(message.getTile(), message.getTileLocation());
        //game.expandSettlement();

    }

    private void Forfeit(ForfeitMessage message){
        return;
    }

    private void Tiger(BuildTigerMessage message){
        if(message.getPid().equals(this.playerID))
            return;
        game.placeTile(message.getTile(), message.getTileLocation());
        game.createTigerPlayground(message.getBuildLocation(), this.opponentTeam);
    }

    private void Totoro(BuildTotoroMessage message){
        if(message.getPid().equals(this.playerID))
            return;
        game.placeTile(message.getTile(), message.getTileLocation());
        game.createTotoroSanctuary(message.getBuildLocation(), this.opponentTeam);
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String toString(){
        String string = ("playerID:  " + this.playerID + "\nopponentID:  " + this.opponentID + "\ngameID:  " + this.gameID);
        return string;
    }

    public void receiveMessage(Message m) {
        gotMessage = true;
    }
}

