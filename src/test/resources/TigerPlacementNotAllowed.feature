Feature: Tiger can be placed in a valid board configuration

  Scenario: The game is initialized and the board is initialized and its the player's turn
    Given there a settlement with no adjacent hex level 3 or higher without an existing tiger
    When the player selects the option to build a tiger and no valid location exists
    Then a tiger will be not placed on the board