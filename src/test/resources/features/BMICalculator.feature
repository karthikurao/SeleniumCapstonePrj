Feature: BMI Calculator
  As a user
  I want to calculate my BMI
  So that I can know my body mass index

  Scenario: Calculate BMI using metric system
    Given I navigate to the BMI calculator website
    When I select metric units
    And I enter age as "25"
    And I select gender as "male"
    And I enter height as "180" cm
    And I enter weight as "75" kg
    And I click calculate button
    Then I should see BMI result displayed
    And BMI category should be shown

  Scenario: Calculate BMI using US units
    Given I navigate to the BMI calculator website
    When I select US units
    And I enter age as "30"
    And I select gender as "female"
    And I enter height feet as "5" and inches as "6"
    And I enter weight as "140" pounds
    And I click calculate button
    Then I should see BMI result displayed
    And BMI category should be shown

  Scenario: Clear BMI calculation
    Given I navigate to the BMI calculator website
    When I select metric units
    And I enter age as "25"
    And I select gender as "male"
    And I enter height as "180" cm
    And I enter weight as "75" kg
    And I click calculate button
    And I click clear button
    Then All fields should be cleared

  Scenario Outline: Calculate BMI for different body types
    Given I navigate to the BMI calculator website
    When I select metric units
    And I enter age as "<age>"
    And I select gender as "<gender>"
    And I enter height as "<height>" cm
    And I enter weight as "<weight>" kg
    And I click calculate button
    Then I should see BMI result displayed

    Examples:
      | age | gender | height | weight |
      | 20  | male   | 170    | 60     |
      | 35  | female | 165    | 55     |
      | 40  | male   | 175    | 85     |
      | 28  | female | 160    | 70     |

