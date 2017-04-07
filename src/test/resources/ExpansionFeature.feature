Feature: Expansion Feature

Scenario: It is my turn and I choose to expand an existing settlement

Given There are n tiles of the same type adjacent to the existing settlement or to tiles adjacent to those tiles of level k
When I expand the settlement
Then k meeples are placed on each of those n tiles.
