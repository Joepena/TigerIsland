Feature: Create a new settlement

Scenario: I want to create a new settlement by placing a small meeple
    Given I want to place the meeple on a volcano
    When I try to place the meeple
    Then I should not be allowed to place the meeple

  Scenario: I want to create a new settlement by placing a small meeple
    Given I want to place the meeple on an unoccupied tile
    And It is not on a volcano tile
    And It on a Level 1 tile
    When I try to place the meeple
    Then I should be allowed to place the meeple