Feature: Tiger can be placed in a valid board configuration

  Scenario: The game is initialized and the board is initialized and its the player's turn
    Given there a settlement with an adjacent hex level 3 or higher
    When the player selects the option to build a tiger and a valid location exists
    Then a tiger will be placed on the board in a valid hex