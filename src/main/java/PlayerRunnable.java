import java.util.ArrayList;
import java.util.Vector;

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
  private ArrayList<Tuple> tilePlacementOptions;
  private ArrayList<Tuple> eruptionOptions;
  private ArrayList<Tuple> foundSettlementOptions;
  private ArrayList<ExpansionOpDataFrame> expandSettlementOptions;
  private ArrayList<Tuple> totoroPlacementOptions;
  private ArrayList<Tuple> tigerPlacementOptions;
  private ArrayList<Integer> orientationOptions;
  private Orientation.Orientations orientation;
  private Vector<Integer> orientationList;


  public PlayerRunnable(String playerID, String opponentID, int playerNum) {
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
    this.orientation = null;
  }

  public void setGameID(String gameID) {
    this.gameID = gameID;

  }

  public GameAPI getGame() {
    return game;
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

    while (!gameOver) {
      Message messageFromClient = new NoActionMessage(Message.MessageType.Welcome);
      try {

        while (true) {
          Thread.sleep(30);
        }

      } catch (InterruptedException e) {
        //System.out.println("Player " + this.playerNum + " about to do stuff!");
        if (playerNum == 1) {
          messageFromClient = GameClient.getP1Moves().remove();
          this.gameID = GameClient.getGame1ID();
        } else {
          messageFromClient = GameClient.getP2Moves().remove();
          this.gameID = GameClient.getGame2ID();
        }

      }

      //System.out.println("Troy knows how this works for player " + playerNum);

      System.out.println(messageFromClient);
      if (!gameOver) {
        executeMessage(messageFromClient);
      }
      {

        if (messageFromClient.getMessageType() == Message.MessageType.MakeYourMove) {
          playTurn();
        }
      }
    }
  }


  protected void playTurn() {
    moveMessage = new clientMoveMessages();
    //Update board state
    game.updateSettlements();

    //Check for tile placement options
    tilePlacementOptions = game.getAvailableTilePlacement();
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
      orientationList = game.APIUtils.findValidOrientationsAtPoint(decisionCoords);
      for (Integer i : orientationList) {
        newTile.setLeftHexOrientation(game.APIUtils.numToOrientation(i));
        if (game.APIUtils.isTileConnected(newTile, decisionCoords)) {
          orientation = game.APIUtils.numToOrientation(i);
          break;
        }
      }
      moveMessage.setOrientation(moveMessage.orientationToNumber(orientation));
    }
    moveMessage.setTile(newTile);
    moveMessage.setGid(this.gameID);
    moveMessage.setMoveNumber(this.moveNumber);

    newTile.setLeftHexOrientation(orientation);
    game.placeTile(newTile, decisionCoords);

    //Update board state
    game.updateSettlements();

    //Find Placement options
    foundSettlementOptions = game.findListOfValidSettlementLocations();
    expandSettlementOptions = game.getExpansionOptions(Hex.Team.Black);
    totoroPlacementOptions = game.validTotoroPlacements(Hex.Team.Black);
    tigerPlacementOptions = game.validTigerPlacements(Hex.Team.Black);

    //Decide Build Action
    //Tiger Rush Strategy
    if (tigerPiecesRemaining(game)) {
      if (canPlaceTiger(tigerPlacementOptions)) {
        //place Tiger
        moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Tiger);
        moveMessage.setBuildLocation(tigerPlacementOptions.get(0));
        game.createTigerPlayground(tigerPlacementOptions.get(0), Hex.Team.Black);
      } else if (canPlaceTotoro(totoroPlacementOptions)) {
        //place Totoro
        moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Totoro);
        moveMessage.setBuildLocation(totoroPlacementOptions.get(0));
        game.createTotoroSanctuary(totoroPlacementOptions.get(0), Hex.Team.Black);

      } else {
        //found settlement
        buildDecisionCoords = findClosestTupleToOrigin(foundSettlementOptions);
        moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Found);
        moveMessage.setBuildLocation(buildDecisionCoords);
        game.foundSettlement(buildDecisionCoords, Hex.Team.Black);
      }
    } else {
      ExpansionOpDataFrame expansionDF = chooseHighestCostValidExpansion(
        expandSettlementOptions);
      if (expansionDF != null) {
        //choose highest cost expansion option
        moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Expand);
        moveMessage.setBuildLocation(expansionDF.getExpansionStart());
        moveMessage.setTerrain(expansionDF.getTerrain());
        game.performLandGrab(expansionDF);
        //System.out.println("We expanded for turn: " + newTile.getTileId());
        //System.out.println("Expanded cost: " + expansionDF.getExpansionCost());
      } else if (game.getVillagerCount() > 0) {
        //found settlement
        buildDecisionCoords = findClosestTupleToOrigin(foundSettlementOptions);
        moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Found);
        moveMessage.setBuildLocation(buildDecisionCoords);
        game.foundSettlement(buildDecisionCoords, Hex.Team.Black);
      } else {
        //We lost.
        moveMessage.setMoveType(clientMoveMessages.clientMoveMessageType.Unable);
      }
    }

    hasMove = false;
    GameClient.sendMessageFromPlayerToServer(moveMessage);

  }


  private boolean canNukeSafely(GameAPI game) {
    return game.findListOfValidSettlementLocations().size() > 2;
  }

  private Tuple canSabotageEnemySettlement(GameAPI game, ArrayList<Tuple> eruptionOptions) {
    ArrayList<SettlementDataFrame> enemySettlements = game.getWhiteSettlements()
      .getListOfSettlements();
    ArrayList<Tuple> sabotageOptions = game
      .findSizeNSettlementsForNukingWithNoTotoro(enemySettlements, 3);
    if (!sabotageOptions.isEmpty()) {
      for (Tuple eruptionTuple : eruptionOptions) {
        boolean contains = sabotageOptions.contains(eruptionTuple);
        if (contains) {
          return eruptionTuple;
        }
      }
    }
    return null;
  }


  private boolean canPlaceTiger(ArrayList<Tuple> tigerOptions) {
    return !tigerOptions.isEmpty();
  }


  private boolean tigerPiecesRemaining(GameAPI game) {
    return game.getTigerCount() > 0;
  }

  private ExpansionOpDataFrame chooseHighestCostValidExpansion(
    ArrayList<ExpansionOpDataFrame> expandSettlementOptions) {

    for (ExpansionOpDataFrame currentEDF : expandSettlementOptions) {
      if (currentEDF.getExpansionCost() <= game.getVillagerCount()) {
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
    for (Tuple tuple : eruptionList) {
      tupleDistance = Board.distanceFromTheOrigin(tuple);
      if (tupleDistance < minDistance) {
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
        GameOver(gameOverMessage);
        break;
      case MakeYourMove:
        MakeYourMoveMessage makeYourMove = (MakeYourMoveMessage) message;
        MakeYourMove(makeYourMove);
        break;
      case Move:
        MoveMessage move = (MoveMessage) message;
        Move(move);
        //game.gameBoard.printSectionedBoard(game.gameBoard);
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
    if (message.getPid().equals(this.playerID)) {
      return;
    }
    game.placeTile(message.getTile(), message.getTileLocation());
    game.foundSettlement(message.getBuildLocation(), this.opponentTeam);
  }

  private void Expand(ExpandSettlementMessage message) {
    if (message.getPid().equals(this.playerID)) {
      return;
    }
    game.placeTile(message.getTile(), message.getTileLocation());
    SettlementDataFrame settlement = game.APIUtils
      .getWhiteSettlementFromLocation(message.getExpandLocation(), game.getWhiteSettlements());
    game.APIUtils.performLandGrab(settlement, message.getExpandTerrain());
  }

  private void Forfeit(ForfeitMessage message) {
    return;
  }

  private void Tiger(BuildTigerMessage message) {
    if (message.getPid().equals(this.playerID)) {
      return;
    }
    game.placeTile(message.getTile(), message.getTileLocation());
    game.createTigerPlayground(message.getBuildLocation(), this.opponentTeam);
  }

  private void Totoro(BuildTotoroMessage message) {
    if (message.getPid().equals(this.playerID)) {
      return;
    }
    game.placeTile(message.getTile(), message.getTileLocation());
    game.createTotoroSanctuary(message.getBuildLocation(), this.opponentTeam);
  }

  public String toString() {
    String string = ("playerID:  " + this.playerID + "\nopponentID:  " + this.opponentID
      + "\ngameID:  " + this.gameID);
    return string;
  }

}

