Feature: To test if attempting to place a hex in a invalid position will allow the hex to be placed

  Scenario: The game is initialized and the board is initialized with one hex already placed, it is the players turn
    Given there is already a hex placed on the board
    When a player tries to place another hex in a invalid placement position
    Then the hex will be not placed on the board