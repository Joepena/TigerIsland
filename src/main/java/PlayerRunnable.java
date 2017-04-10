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
    private clientMoveMessages moveMessage;
    private int moveNumber;
    Orientation.Orientations orientation;

    public GameAPI getGame() {
        return game;
    }

    public PlayerRunnable(String playerID, String opponentID) {
        this.playerID = playerID;
        this.opponentID = opponentID;
        this.gameID = "";
        this.newTile = null;
        this.gameOver = false;
        this.hasMove = false;
        this.decisionCoords = null;
        this.buildDecision = null;
        this.buildDecisionCoords = null;
        this.game = new GameAPI();
        this.moveMessage = null;
        this.moveNumber = 0;
        this.orientation = null;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    @Override
    public void run() {
        // System.out.println("Villager count of " + this.toString() + " is: " + game.getVillagerCount());
        //System.out.println("Totoro count of " + this.toString() + " is: " + game.getTotoroCount());
        //System.out.println("Tiger count of " + this.toString() + " is: " + game.getTigerCount());

        //Instantiate all ArrayLists once
        ArrayList<Tuple> tilePlacementOptions = new ArrayList<>();
        ArrayList<Tuple> eruptionOptions = new ArrayList<>();
        ArrayList<Tuple> foundSettlementOptions = new ArrayList<>();
        ArrayList<ExpansionOpDataFrame> expandSettlementOptions = new ArrayList<>();
        ArrayList<Tuple> totoroPlacementOptions = new ArrayList<>();
        ArrayList<Tuple> tigerPlacementOptions = new ArrayList<>();


        //Player Logic

        game.placeFirstTile();

        while (!gameOver) {
            while (!hasMove) ;
            this.moveMessage = new clientMoveMessages();

            //Update board state
            game.updateSettlements();

            //Check for tile placement options
            tilePlacementOptions = game.getAvailableTilePlacement();

            //Check for nuking options
            eruptionOptions = game.getValidNukingLocations();

            //Decide normal place or nuke
            //Tiger Rush Strategy
            if (canNukeSafely(game) && eruptionOptions.size() != 0) {
                decisionCoords = canSabotageEnemySettlement(game, eruptionOptions);
                if (decisionCoords != null) {
                    moveMessage.setTileLocation(decisionCoords);
                    orientation = game.APIUtils.getViableNukingOrientation(decisionCoords);
                    moveMessage.setOrientation(moveMessage.orientationToNumber(orientation));
                } else {
                    //nuke tile nearest origin.
                    decisionCoords = findClosestTupleToOrigin(eruptionOptions);
                    moveMessage.setTileLocation(decisionCoords);
                    orientation = game.APIUtils.getViableNukingOrientation(decisionCoords);
                    moveMessage.setOrientation(moveMessage.orientationToNumber(orientation));
                }
            } else {
                //Place tile nearest origin.
                decisionCoords = findClosestTupleToOrigin(tilePlacementOptions);
                moveMessage.setTileLocation(decisionCoords);
                orientation = game.APIUtils.getViableNonNukingOrientation(decisionCoords);
                moveMessage.setOrientation(moveMessage.orientationToNumber(orientation));
            }

            //Totoro Turtle Strategy
/*            if (totoroPiecesRemaining(game)) {
                if (canNukeSafely(game)) {
                    if (canSabotageEnemySettlement()) {
                        //Sabotage enemy settlement
                    }
                } else {
                    //Place tiles near existing settlements of < 5 w/ no totoro
                }
            } else{
                if (canNukeSafely(game)) {
                    //Nuke tiles near existing settlements (to prep for expansion)
                } else {
                    //Place tile
                }
            }*/

                //FOR SERVER TESTING
                //moveMessage.setTileLocation(tilePlacementOptions.get(0));
                //Orientation.Orientations orientation = game.APIUtils.getViableNonNukingOrientation(tilePlacementOptions.get(0));
                //moveMessage.setOrientation(moveMessage.orientationToNumber(orientation));

                moveMessage.setTile(newTile);
                moveMessage.setGid(this.gameID);
                moveMessage.setMoveNumber(this.moveNumber);


                //Place tile to handle on our own board
                newTile.setLeftHexOrientation(orientation);
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
                //Tiger Rush Strategy
                if (tigerPiecesRemaining(game)) {
                    if (canPlaceTiger(tigerPlacementOptions)) {
                        //place Tiger
                        moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Tiger);
                        moveMessage.setBuildLocation(tigerPlacementOptions.get(0));
                    } else if (canPlaceTotoro(totoroPlacementOptions)) {
                        //place Totoro
                        moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Totoro);
                        moveMessage.setBuildLocation(totoroPlacementOptions.get(0));
                    } else {
                        //found settlement
                        moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Found);
                        moveMessage.setBuildLocation(foundSettlementOptions.get(0));
                    }
                } else {
                    ExpansionOpDataFrame expansionDF = chooseHighestCostValidExpansion(expandSettlementOptions);
                    if (expansionDF != null) {
                        //choose highest cost expansion option
                        moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Expand);
                        moveMessage.setBuildLocation(expansionDF.getExpansionStart());
                        moveMessage.setTerrain(expansionDF.getTerrain());
                    } else {
                        //found settlement
                        moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Found);
                        moveMessage.setBuildLocation(foundSettlementOptions.get(0));
                    }
                }

                //Totoro Turtle Strategy
/*                if (totoroPiecesRemaining(game)) {
                    if (canPlaceTotoro(totoroPlacementOptions)) {
                        //place Totoro
                    } else if (canPlaceTiger(tigerPlacementOptions)) {
                        //place Tiger
                    } else if (existsSub3ExpandOption()) {
                        //perform expansion with cost of 1-2
                    } else {
                        //found settlement near largest settlement w/ no totoro
                    }
                } else {
                    if (canExpand(expandSettlementOptions)) {
                        //choose highest-cost expansion option
                    } else {
                        //Found settlement
                    }
                }*/


                //FOR SERVER TESTING
                //moveMessage.setBuildLocation(foundSettlementOptions.get(0));
                //moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Found);

                //Perform Build action
                //foundSettlement(buildDecisionCoords, Hex.Team.Black);
                switch  (moveMessage.getMoveType()){
                    case Expand:
                        game.performLandGrab(moveMessage.getBuildLocation());
                        break;
                    case Found:
                        break;
                    case Totoro:
                        break;
                    case Tiger:
                        break;
                    case Unable:
                        break;
                }


                //Print out moveMessage and send to Client

                System.out.println(moveMessage.toString(moveMessage.getMoveType()));

                hasMove = false;
                gameOver = true;
            }
        }

    private boolean existsSub3ExpandOption() {
        return false;
    }

    private boolean canNukeSafely(GameAPI game) {
        return game.findListOfValidSettlementLocations().size() > 2;
    }

    private Tuple canSabotageEnemySettlement(GameAPI game, ArrayList<Tuple> eruptionOptions) {
        ArrayList<SettlementDataFrame> enemySettlements = game.getWhiteSettlements().getListOfSettlements();
        ArrayList<Tuple> sabotageOptions = game.findSizeNSettlementsForNukingWithNoTotoro(enemySettlements,3);
        if(!sabotageOptions.isEmpty()) {
            for(Tuple eruptionTuple: eruptionOptions) {
                boolean contains = sabotageOptions.contains(eruptionTuple);
                if(contains)
                    return eruptionTuple;
            }
        }
        return null;
    }

    private boolean canRecycleOwnTotoroSettlement() {
        return false;
    }

    private boolean canPlaceTiger(ArrayList<Tuple> tigerOptions) {
        return !tigerOptions.isEmpty();
    }
    private boolean totoroPiecesRemaining(GameAPI game) {
        return game.getTotoroCount() > 0;
    }

    private boolean tigerPiecesRemaining(GameAPI game) {
        return game.getTigerCount() > 0;
    }

    private ExpansionOpDataFrame chooseHighestCostValidExpansion(ArrayList<ExpansionOpDataFrame> expandSettlementOptions) {

        for (ExpansionOpDataFrame currentEDF : expandSettlementOptions){
            if (currentEDF.getExpansionCost() <= game.getVillagerCount()){
                return currentEDF;
            }
        }


        return null;
    }

    private boolean canPlaceTotoro(ArrayList<Tuple> totoroPlacementOptions) {
        return !totoroPlacementOptions.isEmpty();
    }
    private Tuple findClosestTupleToOrigin(ArrayList<Tuple> eruptionList) {
        Tuple closestTuple = null;
        int tupleDistance;
        int minDistance = Integer.MAX_VALUE;
        for(Tuple tuple : eruptionList) {
            tupleDistance = Board.distanceFromTheOrigin(tuple);
            if(tupleDistance < minDistance) {
                closestTuple = tuple;
                minDistance = tupleDistance;
            }
        }
        return closestTuple;
    }

    public void executeMessage(Message message) {

        Message.MessageType type = message.getMessageType();

        switch (type) {
            case GameOver:
                GameOverMessage gameOverMessage = (GameOverMessage) message;
                if (!gameOverMessage.getGid().equals(this.gameID))
                    return;
                GameOver(gameOverMessage);
                break;
            case MakeYourMove:
                MakeYourMoveMessage makeYourMove = (MakeYourMoveMessage) message;
                if (!makeYourMove.getGid().equals(this.gameID))
                    return;
                MakeYourMove(makeYourMove);
                break;
            case Move:
                MoveMessage move = (MoveMessage) message;
                if (!move.getGid().equals(this.gameID))
                    return;
                Move(move);
                break;
        }
    }

    private void GameOver(GameOverMessage message) {
        this.gameOver = true;
    }

    private void MakeYourMove(MakeYourMoveMessage message) {
        this.newTile = message.getTile();
        this.hasMove = true;
        this.moveNumber = message.getMoveNumber();
    }


    private void Move(MoveMessage message) {
        switch (message.getMoveType()) {
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

    private void FoundMove(FoundSettlementMessage message) {
        if (message.getPid().equals(this.playerID))
            return;
        game.placeTile(message.getTile(), message.getTileLocation());
        game.foundSettlement(message.getBuildLocation(), this.opponentTeam);
    }

    private void Expand(ExpandSettlementMessage message) {
        if (message.getPid().equals(this.playerID))
            return;
        game.placeTile(message.getTile(), message.getTileLocation());
        //game.expandSettlement();

    }

    private void Forfeit(ForfeitMessage message) {
        return;
    }

    private void Tiger(BuildTigerMessage message) {
        if (message.getPid().equals(this.playerID))
            return;
        game.placeTile(message.getTile(), message.getTileLocation());
        game.createTigerPlayground(message.getBuildLocation(), this.opponentTeam);
    }

    private void Totoro(BuildTotoroMessage message) {
        if (message.getPid().equals(this.playerID))
            return;
        game.placeTile(message.getTile(), message.getTileLocation());
        game.createTotoroSanctuary(message.getBuildLocation(), this.opponentTeam);
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String toString() {
        String string = ("playerID:  " + this.playerID + "\nopponentID:  " + this.opponentID + "\ngameID:  " + this.gameID);
        return string;
    }

    public void receiveMessage(Message m) {
        gotMessage = true;
    }
}

