Feature: Totoro can not be placed in an invalid board configuration

  Scenario: The game is initialized and the board is initialized and its the player's turn
    Given there is not settlement of size five or greater without a totoro for the player's team
    When the player selects the option to build a totoro when no valid location exists
    Then a totoro will not be allowed to be placed on the board