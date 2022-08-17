Feature: Test data

  Scenario: When test data is received of an existing project it will be added
    Given project "TestProject1" exists
    When project "TestProject1" has received the following testdata:
    """
    {
      "project": "TestProject1",
      "testName": "test01",
      "testRunId": "12",
      "result": "PASSED"
    }
    """
    Then path "/api/v1/TestProject1" should exist and give me:
    """
    {
      "name": "TestProject1",
      "testdata": [{"result":"PASSED","testname":"test01","testrunId":12}]
    }
    """
    And the following message will be published "team has been updated"

  Scenario: The event is ignored if the project does not exist
    When project "TestNonExistent" has received the following testdata:
    """
    {
      "project": "TestNonExistent",
      "testName": "test01",
      "testRunId": "12",
      "result": "PASSED"
    }
    """
    Then path "/api/v1/TestNonExistent" should receive a 404 status code
    And no messages will be published