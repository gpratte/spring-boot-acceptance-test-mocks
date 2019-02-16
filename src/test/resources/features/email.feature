Feature: send emails
  Scenario: send email to nobody@example.com
    Given email is composed
    When the email is published to the queue
    Then the log show the email was received
