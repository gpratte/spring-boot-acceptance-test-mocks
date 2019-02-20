Feature: send todos

  Scenario: send todo grocery shop
    Given todo grocery shop is composed
    When the todo is published to the queue
    Then the rest call should happen

  Scenario: send todo walk dogs
    Given todo walk dogs is composed
    When the todo is published to the queue
    Then the rest call should happen
