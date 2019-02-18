Feature: send emails

  Scenario: send email to nobody@example.com
    Given email is composed
    When the email is published to the queue
    Then the rest call should happen

  Scenario: send email to anybody@example.com
    Given email is composed to anybody
    When the email is published to the queue
    Then the rest call should happen
