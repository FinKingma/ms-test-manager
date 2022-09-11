Feature: Rating
  The mock will return an average passing rate of 70%
  The current tolerance is 10%

  Scenario Outline: I should get a good rating if my passing percentage exceed the average of all companies <rating>
    Given project "RatingProject<id>" exists
    When project "RatingProject<id>" has received <passing> passing tests and <failing> failing tests
    Then path "/api/projects/RatingProject<id>" should exist and give me:
    """
    {
      "name": "RatingProject<id>",
      "rating": "<rating>",
      "testdata": "${json-unit.ignore}"
    }
    """

    Examples:
      | id | passing | failing | rating  |
      | 1  | 7       | 3       | GOOD    |
      | 2  | 6       | 4       | AVERAGE |
      | 3  | 5       | 5       | POOR    |