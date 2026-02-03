@api
Feature: API smoke checks

  Scenario: Single user details are returned
    Given API endpoint is "/users/2"
    When I send a GET request
    Then status code should be 200
    And response time should be under 3000 ms
    And response body field "id" should be "2"
    And response body field "name" should not be blank
    And response body field "email" should not be blank

  Scenario: Filtered user query returns one result
    Given API endpoint is "/users?id=3"
    When I send a GET request
    Then status code should be 200
    And response time should be under 3000 ms
    And response body should be an array
    And response body array size should be 1
    And response body field "0.id" should be "3"
    And response body field "0.username" should not be blank

  Scenario: Users list is returned
    Given API endpoint is "/users"
    When I send a GET request
    Then status code should be 200
    And response time should be under 3000 ms
    And response body should be an array
    And response body array size should be 10
    And response body field "0.id" should be "1"

  Scenario: Missing record returns 404
    Given API endpoint is "/users/9999"
    When I send a GET request
    Then status code should be 404
    And response time should be under 3000 ms

  Scenario Outline: Multiple endpoints return 200
    Given API endpoint is "<path>"
    When I send a GET request
    Then status code should be 200
    And response time should be under 3000 ms
    And response body field "<fieldPath>" should be "<expectedValue>"

    Examples:
      | path     | fieldPath | expectedValue |
      | /posts/1 | id        | 1             |
      | /todos/1 | id        | 1             |
