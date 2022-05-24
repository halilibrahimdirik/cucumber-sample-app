Feature: Order API Testing

  Scenario: Calling order list API
    Given Order list API is provided
    When User call order list API
    Then  list will be shown
  Scenario: Calling order get API
    Given Order get API is provided
    When User call order get API
    Then Specific order info will be shown