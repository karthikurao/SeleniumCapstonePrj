@echo off
echo ========================================
echo   Selenium Capstone Project - Test Runner
echo ========================================
echo.

echo [1/3] Cleaning previous build...
call mvn clean

echo.
echo [2/3] Installing dependencies...
call mvn install -DskipTests

echo.
echo [3/3] Running all tests...
call mvn test

echo.
echo ========================================
echo   Test Execution Complete!
echo ========================================
echo.
echo View reports at:
echo - TestNG: target\surefire-reports\index.html
echo - Cucumber: target\cucumber-reports\cucumber.html
echo.
pause

