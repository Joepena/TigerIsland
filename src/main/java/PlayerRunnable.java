import java.util.ArrayList;

/**
 * Created by Troy on 3/27/17.
 */
public class PlayerRunnable implements Runnable {

    private boolean gameOver;
    private boolean hasMove;
    private Tile newTile;
    private Tuple decisionCoords;
    private GameAPI game;
    private Hex.Team playerTeam;
    private Hex.Team opponentTeam;


    @Override
    public void run() {
        gameOver = false;
        hasMove = false;
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
                while (!hasMove);

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

    public void executeMessage(Message message){
        //Get type of message
        //Execute changes
        Message.MessageType type = message.getMessageType();

        //if noAction message return
        if(type == Message.MessageType.Welcome || type == Message.MessageType.Enter ||
                type == Message.MessageType.Goodbye || type == Message.MessageType.WaitForNext ||
                type == Message.MessageType.EndOfChallenges)
            return;

        switch(type){
            case BeginRound:
                executeBeginRound((BeginRoundMessage) message);
                break;
            case EndRound:
                executeEndRound((EndRoundMessage) message);
                break;
            case GameOver:
                executeGameOver((GameOverMessage) message);
                break;
            case MakeYourMove:
                executeMakeYourMove((MakeYourMoveMessage) message);
                break;
            case MatchBeginning:
                executeMatchBeginning((MatchBeginningMessage) message);
                break;
            case Move:
                executeMove((MoveMessage) message);
                break;
            case NewChallenge:
                executeNewChallenge((NewChallengeMessage) message);
                break;
            case WaitToBegin:
                executeWaitToBegin((WaitToBeginMessage) message);
                break;
        }
    }


    private void executeBeginRound(BeginRoundMessage message){

    }

    private void executeEndRound(EndRoundMessage message){

    }

    private void executeGameOver(GameOverMessage message){

    }

    private void executeMakeYourMove(MakeYourMoveMessage message){

    }



    private void executeNewChallenge(NewChallengeMessage message){

    }

    private void executeWaitToBegin(WaitToBeginMessage message){

    }

    private void executeMatchBeginning(MatchBeginningMessage message){

    }

    private void executeMove(MoveMessage message){
        switch(message.getMoveType()){
            case Found:
                executeFoundMove((FoundSettlementMessage) message);
                break;
            case Expand:
                executeExpand((ExpandSettlementMessage) message);
                break;
            case Forfeit:
                executeForfeit((ForfeitMessage) message);
                break;
            case Tiger:
                executeTiger((BuildTigerMessage) message);
                break;
            case Totoro:
                executeTotoro((BuildTotoroMessage) message);
                break;
        }
    }

    private void executeFoundMove(FoundSettlementMessage message){
        game.placeTile(message.getTile(), message.getTileLocation());
        game.foundSettlement(message.getBuildLocation(), this.opponentTeam);
    }

    private void executeExpand(ExpandSettlementMessage message){

    }

    private void executeForfeit(ForfeitMessage message){

    }

    private void executeTiger(BuildTigerMessage message){

    }

    private void executeTotoro(BuildTotoroMessage message){
            }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }
}

