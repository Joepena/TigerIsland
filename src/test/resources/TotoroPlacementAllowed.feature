Feature: Totoro can be placed in a valid board configuration

  Scenario: The game is initialized and the board is initialized and its the player's turn
    Given there a settlement of size five or greater for the player's team
    When the player selects the option to build a totoro and a valid location exists
    Then a totoro will be placed on the board in a valid hex