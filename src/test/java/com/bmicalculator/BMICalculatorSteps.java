package com.bmicalculator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.Locale;
import java.util.Objects;

public class BMICalculatorSteps {

	private enum UnitSystem {
		METRIC,
		US
	}

	private UnitSystem selectedUnit;
	private Integer age;
	private String gender;
	private Double heightCm;
	private Integer heightFeet;
	private Integer heightInches;
	private Double weightKg;
	private Double weightPounds;
	private Double bmiValue;
	private String bmiCategory;
	private boolean bmiCalculated;

	private void resetState() {
		selectedUnit = null;
		age = null;
		gender = null;
		heightCm = null;
		heightFeet = null;
		heightInches = null;
		weightKg = null;
		weightPounds = null;
		bmiValue = null;
		bmiCategory = null;
		bmiCalculated = false;
	}

	@Given("I navigate to the BMI calculator website")
	public void i_navigate_to_the_bmi_calculator_website() {
		resetState();
	}

	@When("I select metric units")
	public void i_select_metric_units() {
		selectedUnit = UnitSystem.METRIC;
	}

	@When("I select US units")
	public void i_select_us_units() {
		selectedUnit = UnitSystem.US;
	}

	@When("I enter age as {string}")
	public void i_enter_age_as(String ageValue) {
		age = Integer.parseInt(ageValue);
		Assert.assertTrue(age > 0, "Age must be greater than zero");
	}

	@When("I select gender as {string}")
	public void i_select_gender_as(String genderValue) {
		gender = genderValue.toLowerCase(Locale.ROOT).trim();
		Assert.assertFalse(gender.isEmpty(), "Gender must be provided");
	}

	@When("I enter height as {string} cm")
	public void i_enter_height_as_cm(String heightValue) {
		heightCm = Double.parseDouble(heightValue);
		Assert.assertTrue(heightCm > 0, "Height (cm) must be greater than zero");
	}

	@When("I enter height feet as {string} and inches as {string}")
	public void i_enter_height_feet_as_and_inches_as(String feetValue, String inchesValue) {
		heightFeet = Integer.parseInt(feetValue);
		heightInches = Integer.parseInt(inchesValue);
		Assert.assertTrue(heightFeet >= 0, "Height feet must not be negative");
		Assert.assertTrue(heightInches >= 0, "Height inches must not be negative");
	}

	@When("I enter weight as {string} kg")
	public void i_enter_weight_as_kg(String weightValue) {
		weightKg = Double.parseDouble(weightValue);
		Assert.assertTrue(weightKg > 0, "Weight (kg) must be greater than zero");
	}

	@When("I enter weight as {string} pounds")
	public void i_enter_weight_as_pounds(String weightValue) {
		weightPounds = Double.parseDouble(weightValue);
		Assert.assertTrue(weightPounds > 0, "Weight (pounds) must be greater than zero");
	}

	@When("I click calculate button")
	public void i_click_calculate_button() {
		Objects.requireNonNull(selectedUnit, "Unit selection is required before calculation");

		switch (selectedUnit) {
			case METRIC:
				Objects.requireNonNull(heightCm, "Height in centimeters is required for metric calculation");
				Objects.requireNonNull(weightKg, "Weight in kilograms is required for metric calculation");
				double heightMeters = heightCm / 100.0;
				Assert.assertTrue(heightMeters > 0, "Height must be greater than zero");
				bmiValue = weightKg / (heightMeters * heightMeters);
				break;
			case US:
				Objects.requireNonNull(heightFeet, "Height feet is required for US calculation");
				Objects.requireNonNull(heightInches, "Height inches is required for US calculation");
				Objects.requireNonNull(weightPounds, "Weight in pounds is required for US calculation");
				int totalInches = (heightFeet * 12) + heightInches;
				Assert.assertTrue(totalInches > 0, "Total height in inches must be greater than zero");
				bmiValue = (weightPounds * 703) / (totalInches * totalInches);
				break;
			default:
				throw new IllegalStateException("Unsupported unit system: " + selectedUnit);
		}

		bmiCategory = determineCategory(bmiValue);
		bmiCalculated = true;
	}

	@When("I click clear button")
	public void i_click_clear_button() {
		resetState();
	}

	@Then("I should see BMI result displayed")
	public void i_should_see_bmi_result_displayed() {
		Assert.assertTrue(bmiCalculated, "BMI should be calculated before validating the result");
		Assert.assertNotNull(bmiValue, "BMI value must not be null");
		Assert.assertTrue(bmiValue > 0, "BMI value must be greater than zero");
	}

	@Then("BMI category should be shown")
	public void bmi_category_should_be_shown() {
		Assert.assertTrue(bmiCalculated, "BMI should be calculated before validating the category");
		Assert.assertNotNull(bmiCategory, "BMI category must not be null");
		Assert.assertFalse(bmiCategory.isBlank(), "BMI category must not be blank");
	}

	@Then("All fields should be cleared")
	public void all_fields_should_be_cleared() {
		Assert.assertNull(selectedUnit, "Unit should be cleared");
		Assert.assertNull(age, "Age should be cleared");
		Assert.assertNull(gender, "Gender should be cleared");
		Assert.assertNull(heightCm, "Height (cm) should be cleared");
		Assert.assertNull(heightFeet, "Height (ft) should be cleared");
		Assert.assertNull(heightInches, "Height (in) should be cleared");
		Assert.assertNull(weightKg, "Weight (kg) should be cleared");
		Assert.assertNull(weightPounds, "Weight (lb) should be cleared");
		Assert.assertNull(bmiValue, "BMI value should be cleared");
		Assert.assertNull(bmiCategory, "BMI category should be cleared");
		Assert.assertFalse(bmiCalculated, "BMI calculation flag should be reset");
	}

	private String determineCategory(double bmi) {
		if (bmi < 18.5) {
			return "Underweight";
		} else if (bmi < 25) {
			return "Normal weight";
		} else if (bmi < 30) {
			return "Overweight";
		} else {
			return "Obesity";
		}
	}
}
