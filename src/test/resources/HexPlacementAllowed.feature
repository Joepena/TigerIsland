Feature: Placing a hex in a valid position will allow the hex to be placed

  Scenario: The game is initialized and the board is initialized with one hex already placed, it is the players turn
    Given there is already a hex on the board
    When a player tries to place another hex in a valid placement position
    Then the hex will be placed on the board