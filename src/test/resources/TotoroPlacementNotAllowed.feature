Feature: To test the totoro build option will not be allowed to be selected if no valid placement exists

  Scenario: The game is initialized and the board is initialized and its the player's turn
    Given there is not settlement of size five or greater for the player's team
    When the player selects the option to build a totoro when no valid location exists
    Then a totoro will not be allowed to be placed on the board