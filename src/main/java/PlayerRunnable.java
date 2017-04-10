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
    private Tuple buildDecisionCoords;
    private BuildDecision buildDecision;
    private GameAPI game;
    private Hex.Team playerTeam;
    private Hex.Team opponentTeam;
    private String playerID;
    private String opponentID;
    private String gameID;
    private int moveNumber;
    private clientMoveMessages moveMessage;
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
        this.newTile = null;
        this.gameOver = false;
        this.hasMove = false;
        this.decisionCoords = null;
        this.buildDecision = null;
        this.buildDecisionCoords = null;
        this.game = new GameAPI();
        this.moveMessage = null;
        this.moveNumber = 0;
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

               // System.out.println(GameClient.getP1Move().toString());
                while(true) {
                    Thread.sleep(100);
                    System.out.println("Goodnight player " + this.playerNum);
                }

            } catch (InterruptedException e) {
                System.out.println("Player " + this.playerNum + " about to do stuff!");
            }

            System.out.println("Troy knows how this works");
            this.gameID = GameClient.getGame1ID();

            if (this.playerNum == 1) {
                System.out.println(GameClient.getP1Move());
                if(!gameOver)
                    executeMessage(GameClient.getP1Move());
                System.out.println("THis is player " + playerNum + "'s board\n");
                game.gameBoard.printSectionedBoard(game.gameBoard);

                if(GameClient.getP1Move().getMessageType() == Message.MessageType.MakeYourMove)
                    playTurn(1);
                GameClient.setP1Move(null);
            }
            else {
                System.out.println(GameClient.getP2Move());
                if(!gameOver)
                    executeMessage(GameClient.getP2Move());
                System.out.println("THis is player " + playerNum + "'s board\n");
                game.gameBoard.printSectionedBoard(game.gameBoard);

                if(GameClient.getP2Move().getMessageType() == Message.MessageType.MakeYourMove)
                    playTurn(2);
                GameClient.setP2Move(null);
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
        System.out.println("NOT HERE");
        moveMessage = new clientMoveMessages();
        //Update board state
        game.updateSettlements();

        //Check for tile placement options
        tilePlacementOptions = game.getAvailableTilePlacement();
        System.out.println("Number of placement options: " + tilePlacementOptions.size());


            //Decide normal place or nuke
            if (canNukeSafely()) {
                if (canSabotageEnemySettlement()){

                } else if (canRecycleOwnTotoroSettlement()){

                } else {
                    //Pick a random nuking location.
                }
            } else{
                //Pick a random tile placement location.
            }

            moveMessage.setTileLocation(tilePlacementOptions.get(0));
            Orientation.Orientations orientation = game.APIUtils.getViableNonNukingOrientation(tilePlacementOptions.get(0));
            System.out.println(tilePlacementOptions.get(0));
            newTile.setLeftHexOrientation(orientation);
            moveMessage.setOrientation(moveMessage.orientationToNumber(orientation));
            moveMessage.setTile(newTile);
            moveMessage.setGid(this.gameID);
            moveMessage.setMoveNumber(this.moveNumber);


            //Place tile
            game.placeTile(newTile, tilePlacementOptions.get(0));
        //Check for nuking options
        eruptionOptions = game.getValidNukingLocations();
        System.out.println("Number of Nuking options: " + eruptionOptions.size());

        //Decide normal place or nuke



        //Place tile
        System.out.println("Where we were thinking of placing: " + tilePlacementOptions.get(0));
        Tile troyTestTile = new Tile(1, Terrain.terrainType.Jungle, Terrain.terrainType.Lake, Orientation.Orientations.downLeft);
        game.placeTile(troyTestTile, tilePlacementOptions.get(0));


        //Update board state
        game.updateSettlements();

        //Check for Found Settlement options
        foundSettlementOptions = game.findListOfValidSettlementLocations();

            //Check for Totoro placement options
            totoroPlacementOptions = game.validTotoroPlacements(this.playerTeam);

            //Check for Tiger placement options
            tigerPlacementOptions = game.validTigerPlacements(this.playerTeam);

            //Decide Build Action
            if (canPlaceTiger(tigerPlacementOptions)){
                buildDecision = new BuildDecision(tigerPlacementOptions.get(0), BuildDecision.buildDecisions.Tiger);
            } else if (tigerPiecesRemaining(game)){
                buildDecision = new BuildDecision(foundSettlementOptions.get(0), BuildDecision.buildDecisions.Found);
            } else if (canExpand(expandSettlementOptions)){
                buildDecision = new BuildDecision(expandSettlementOptions.get(0).getExpansionStart(), BuildDecision.buildDecisions.Expand);
            }else if (canPlaceTotoro(totoroPlacementOptions)){
                buildDecision = new BuildDecision(totoroPlacementOptions.get(0), BuildDecision.buildDecisions.Totoro);
            } else
            {
                buildDecision = new BuildDecision(foundSettlementOptions.get(0), BuildDecision.buildDecisions.Found);
            }

            buildDecisionCoords = foundSettlementOptions.get(1);

            //FOR SERVER TESTING
            moveMessage.setBuildLocation(foundSettlementOptions.get(0));
            moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Found);

            //Perform Build action
            //game.foundSettlement(buildDecisionCoords, Hex.Team.Black);

            //Print out moveMessage and send to Client

            System.out.println(moveMessage.toString(moveMessage.getMoveType()));
            if(this.playerNum == 1)
                GameClient.sendMessageFromPlayerToServer(moveMessage, 1);
            else
                GameClient.sendMessageFromPlayerToServer(moveMessage, 2);

        }




    private boolean canNukeSafely() {
      return false;
    }
    private boolean canSabotageEnemySettlement() {
        return false;
    }
    private boolean canRecycleOwnTotoroSettlement() {
        return false;
    }
    private boolean canPlaceTiger(ArrayList<Tuple> tigerOptions) {
      return !tigerOptions.isEmpty();
    }
    private boolean tigerPiecesRemaining(GameAPI game) {
      return game.getTigerCount() > 0;
    }
    private boolean canExpand(ArrayList<ExpansionOpDataFrame> expandSettlementOptions) {
      return !expandSettlementOptions.isEmpty();
    }
    private boolean canPlaceTotoro(ArrayList<Tuple> totoroPlacementOptions) {
      return !totoroPlacementOptions.isEmpty();
    }

    public void executeMessage(Message message){

        Message.MessageType type = message.getMessageType();

        switch(type){
            case GameOver:
                GameOverMessage gameOverMessage = (GameOverMessage)message;
                GameOver(gameOverMessage);
                break;
            case MakeYourMove:
                MakeYourMoveMessage makeYourMove = (MakeYourMoveMessage) message;
                MakeYourMove(makeYourMove);
                break;
            case Move:
                MoveMessage move = (MoveMessage) message;
                Move(move);
                break;
        }
    }

    private void GameOver(GameOverMessage message){
        System.out.println("GAMEOVER!!!!!!!!!");
        this.gameOver = true;
    }

    private void MakeYourMove(MakeYourMoveMessage message){
        System.out.println("MAKE YOUR MOVE!!");
        this.newTile = message.getTile();
        this.hasMove = true;
        this.moveNumber = message.getMoveNumber();
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
        SettlementDataFrame settlement = game.APIUtils.getWhiteSettlementFromLocation(message.getExpandLocation(), game.getWhiteSettlements());
        game.APIUtils.performLandGrab(settlement, message.getExpandTerrain());
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

