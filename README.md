# Selenium Capstone Project

Automated test suite covering three areas:

1. **OrangeHRM UI flows** – Selenium + TestNG regression around login, leave management, and recruitment.
2. **ReqRes API flows** – Rest Assured tests backed by an in-memory mock that reproduces the public API responses without external calls.
3. **BMI Calculator BDD** – Cucumber scenarios that exercise a lightweight BMI calculator model for both metric and US units.

The repo only contains source, configuration, and helper scripts needed to run these suites locally.

## Tech stack

- Java 11
- Maven
- Selenium WebDriver 4.15.0 with WebDriverManager
- TestNG 7.8.0
- Cucumber JVM 7.14.0
- Rest Assured 5.3.2

## Layout

```
src/test/
├── java/com/
│   ├── orangehrm/         # Page objects + TestNG suite
│   ├── reqres/            # Mock-backed Rest Assured tests
│   ├── bmicalculator/     # Cucumber steps + TestNG runner
│   └── utils/             # Shared Selenium base test
└── resources/features/    # BMI feature file

pom.xml                    # Maven build definition
testng.xml                 # Aggregated TestNG suite
run-tests.bat              # Optional Windows helper to run mvn test
```

`target/` and other build artefacts are ignored.

## Getting started

1. Install Java 11 (or higher) and Maven.
2. Install the Chrome browser (latest stable). WebDriverManager will download a matching driver automatically.
3. Clone the repo and install dependencies:

```powershell
git clone https://github.com/karthikurao/SeleniumCapstonePrj.git
cd SeleniumCapstonePrj
mvn -q dependency:go-offline
```

## Running tests

Run the full suite (UI + API + Cucumber):

```powershell
mvn clean test
```

Run individual suites:

```powershell
# OrangeHRM UI
mvn test -Dtest=OrangeHRMTests

# ReqRes API (uses the built-in mock filter)
mvn test -Dtest=ReqResAPITests

# BMI Cucumber scenarios
mvn test -Dtest=BMICalculatorRunner
```

Reports are generated under `target/surefire-reports/` and `target/cucumber-reports/` after a run.

## What the tests cover

### OrangeHRM UI

- **Login** with default demo credentials (`Admin` / `admin123`).
- **Apply Leave** workflow with date selection and comment entry.
- **View Leave List** filtering to confirm newly applied leave visibility.
- **Recruitment** module coverage for adding and listing candidates.

### ReqRes API

- Exercises the CRUD login and registration endpoints exposed by ReqRes.
- Assertions cover status codes and key payload fields.
- Responses are supplied by `ReqResMockFilter` so the suite works offline and avoids third-party rate limits.

### BMI Calculator (Cucumber)

- Metric and US unit calculations with validation of BMI value and category.
- Scenario outline with multiple data sets.
- “Clear” workflow to ensure state resets between calculations.

## Notes & tips

- `BaseTest` spins up Chrome in headed mode. If you need headless execution, add `options.addArguments("--headless=new")` in that class.
- WebDriverManager caches downloaded drivers in the user profile; clear `%USERPROFILE%/.cache/selenium` if you need a fresh binary.
- The API mock intentionally delays the `/users?delay=3` response (~2.6s) to keep the timing assertion meaningful.

Happy testing!

