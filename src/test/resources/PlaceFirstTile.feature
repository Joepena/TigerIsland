Feature: Board will be properly initialized with 5-hex tile

  Scenario: The game is initialized and the board is initialized
    Given there are no tiles on the board
    When the initial tile is placed
    Then the initial tile should be placed at the origin in a specific orientation