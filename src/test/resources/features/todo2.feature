Feature: send todos
  Scenario: send todo exercise
    Given todo exercise is composed
    When the todo is published to the queue
    Then the rest call should happen
