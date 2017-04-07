Feature: Place a tile on top of existing tiles

  Scenario: The player is unable to choose a tile placement that covers an entire tile
    Given the player wants to place their tile entirely on top of one existing tile
    When the player tries to place the tile on top of the existing tile
    Then the covered tile will prevent the player from placing their tile

  Scenario: The player is unable to choose a tile placement where the volcano hexes are not aligned
    Given the player wants to place a piece without aligning the volcano hex on the existing and the new tile
    When the player tries to place the tile on the non-volcano hex
    Then the unaligned volcano hex will prevent the player from placing their tile

  Scenario: The player is prevented from placing a tile that nukes a settlement of size one
    Given the player wants to place a tile that will nuke a level one settlement of either team
    When the player tries to place the tile on the settlement
    Then the settlement prevents the player from placing their tile

  Scenario: The player is prevented from placing a tile on top of a Totoro
    Given the player wats to place a tile that will nuke an existing Totoro
    When the player tries to place the tile on the Totoro
    Then the Totoro prevents the player from placing their tile

  Scenario: The player places a tile on existing tiles on the board
    Given A location with multiple pieces aligned volcanoes no single settlements and no totoros
    When the player tries to place a tile at that location
    Then the player successfully places the tile

  Scenario: The player places a tile that destroys part of an existing settlement
    Given A location with part of a larger settlement aligned volcanoes no totoros and multiple tileIds
    When the player tries to place a tile that destroys part of the settlement
    Then the player successfully destroys the settlement piece and places the tile