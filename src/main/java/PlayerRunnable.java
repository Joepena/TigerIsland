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
    private int playerNum;

    //lisztomania
    //Instantiate all ArrayLists once
    ArrayList<Tuple> tilePlacementOptions;
    ArrayList<Tuple> eruptionOptions;
    ArrayList<Tuple> foundSettlementOptions;
    ArrayList<ExpansionOpDataFrame> expandSettlementOptions;
    ArrayList<Tuple> totoroPlacementOptions;
    ArrayList<Tuple> tigerPlacementOptions;
    ArrayList<Integer> orientationOptions;

    public GameAPI getGame() {
        return game;
    }

    public PlayerRunnable (String playerID, String opponentID, int playerNum) {
        this.playerID = playerID;
        this.opponentID = opponentID;
        this.gameID = gameID;
        this.newTile = null;
        this.gameOver = false;
        this.hasMove = false;
        this.decisionCoords = null;
        this.game = new GameAPI();
        this.moveMessage = null;
        this.playerTeam = Hex.Team.Black;
        this.opponentTeam = Hex.Team.White;
        this.playerNum = playerNum;
    }

    @Override
    public void run() {

        gameOver = false;
        hasMove = false;

        game = new GameAPI();

        //Instantiate all ArrayLists once
        tilePlacementOptions = new ArrayList<>();
        eruptionOptions = new ArrayList<>();
        foundSettlementOptions = new ArrayList<>();
        expandSettlementOptions = new ArrayList<>();
        totoroPlacementOptions = new ArrayList<>();
        tigerPlacementOptions = new ArrayList<>();
        orientationOptions = new ArrayList<>();


        //Player Logic

        game.placeFirstTile();

        while(!gameOver) {
            try {

                while(GameClient.getP1Move() == null) {
                    Thread.sleep(50);
                    System.out.println("Goodnight player 1");
                }

            } catch (InterruptedException e) {
                System.out.println("Player 1 about to do stuff!");
            }

            System.out.println("Troy knows how this works");

            if (GameClient.getP1Move() != null) {
                //executeMessage(GameClient.getP1Move());
                playTurn(1);
                GameClient.setP1Move(null);
            }
//            try {
//
//                while(GameClient.getP2Move() == null) {
//                    Thread.sleep(100);
//                    System.out.println("Goodnight player 2");
//                }
//
//            } catch (InterruptedException e) {
//                System.out.println("Player 2 about to do stuff!");
//            }
        }
    }

    private void playTurn(int playerNum) {
        //Update board state
        game.updateSettlements();

        //Check for tile placement options
        tilePlacementOptions = game.getAvailableTilePlacement();
        System.out.println("Number of placement options: " + tilePlacementOptions.size());

        //Check for valid orientations for first spot
        orientationOptions = game.findValidTileOrientations(tilePlacementOptions.get(0));
        System.out.println("Number of orientation options: " + orientationOptions.size());

        //Check for nuking options
        eruptionOptions = game.getValidNukingLocations();
        System.out.println("Number of Nuking options: " + eruptionOptions.size());

        //Decide normal place or nuke



        //Place tile
        game.gameBoard.printSectionedBoard(game.gameBoard);
        System.out.println("Where we were thinking of placing: " + tilePlacementOptions.get(0));
        Tile troyTestTile = new Tile(1, Terrain.terrainType.Jungle, Terrain.terrainType.Lake, Orientation.Orientations.downLeft);
        game.placeTile(troyTestTile, tilePlacementOptions.get(0));
        game.gameBoard.printSectionedBoard(game.gameBoard);

        //Update board state
        game.updateSettlements();

        //Check for Found Settlement options
        foundSettlementOptions = game.findListOfValidSettlementLocations();

        //Check for Expand Settlement options
        expandSettlementOptions = game.getExpansionOptions(Hex.Team.Black);

        //Check for Totoro placement options
        totoroPlacementOptions = game.validTotoroPlacements(Hex.Team.Black);

        //Check for Tiger placement options
        tigerPlacementOptions = game.validTigerPlacements(Hex.Team.Black);

        //Decide Build Action

        //Perform Build action
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

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getGameID() {
        return this.gameID;
    }
}

