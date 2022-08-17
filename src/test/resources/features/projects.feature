Feature: Projects

  Scenario: I should be able to create a new project
    When I use "/api/v1/" to send:
    """
    {
      "name": "Fin"
    }
    """
    Then path "/api/v1/Fin" should exist and give me:
    """
    {
      "name": "Fin",
      "testdata": []
    }
    """

  Scenario: I should be able to delete an existing project
    Given project "AAA" exists
    When I delete "/api/v1/AAA"
    Then path "/api/v1/AAA" should receive a 404 status code

  Scenario Outline: A team should always have a unique name - <name>
    Given project "<existing>" exists
    When I use "/api/v1/" to send:
    """
    {
      "name": "<name>"
    }
    """
    Then I should receive a <status> status code

    Examples:
      | existing | name   | status |
      | Fin301   | Fin301 | 422    |
      | FIN302   | fin302 | 422    |
      | Fin303   | Fin313 | 200    |