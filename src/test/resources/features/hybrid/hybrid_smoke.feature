@hybrid
Feature: Hybrid API and UI checks

  @ui @hybrid
  Scenario: API and UI validate the same post title
    Given API endpoint is "/posts/1"
    When I send a GET request
    Then status code should be 200
    And response body field "title" should be "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
    When user navigates to "https://jsonplaceholder.typicode.com/posts/1" page
    Then current URL should contain "/posts/1"
    And page should contain text "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"