Feature: Jira Basic Test


  Scenario: Get an Issue from in JIRA
    Given The user creates Generic Request Specification for in JIRA
    And The user provides the issue id "ST-3"
    When User calls the JIRA API "getJiraIssue" with "get" http request
    And API Response got generated
    Then The API response status code should be 200

  Scenario: Add a New Comment in JIRA issue
    Given The user creates Generic Request Specification for in JIRA
    And The user provides the issue id "ST-3"
    And The user add a new comment "testing for good time"
    When User calls the JIRA API "addJiraComment" with "post" http request
    And API Response got generated
    Then The API response status code should be 201
    And The user saves the "id" of the new comment

  Scenario: Delete a Comment from a JIRA issue
    Given The user creates Generic Request Specification for in JIRA
    And The user provides the issue id "ST-3"
    And The user provides the comment id
    When User calls the JIRA API "deleteJiraComment" with "delete" http request
    And API Response got generated
    Then The API response status code should be 204