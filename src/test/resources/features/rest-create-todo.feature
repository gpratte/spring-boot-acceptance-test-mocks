Feature: create a todo via rest
  Scenario: create a todo via rest
    Given todo paperword is composed
    When the todos endpoint is called
    Then the todo is persisted to the database
