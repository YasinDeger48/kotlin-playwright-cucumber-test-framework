@ui
Feature: UI smoke checks

  Scenario: Home page title and URL are correct
    Given user navigates to the home page
    Then page title should contain "Playwright"
    And current URL should contain "playwright.dev"
    And page should contain text "Playwright"

  Scenario: Docs intro page loads
    When user navigates to "docs/intro" page
    Then page title should contain "Playwright"
    And current URL should contain "/docs/intro"
    And page should contain text "Installation"
