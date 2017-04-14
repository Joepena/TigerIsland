#Feature: Joining two settlements together
#
#  Scenario: Two settlements become adjacent to one another
#    Given Two settlements will become adjacent to one another after a build action
#    When The turn in completed
#    Then These two settlements should be counted as part of the same settlement
#
#  Scenario: One settlement has two pieces that are no longer adjacent to one another
#    Given One settlement has been broken into
#    When The turn is completed
#    Then These two settlements should be counted as part of different settlements
