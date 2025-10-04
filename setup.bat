@echo off
echo ========================================
echo   Quick Setup - Selenium Capstone Project
echo ========================================
echo.

echo Installing all dependencies...
echo This may take a few minutes on first run.
echo.

call mvn clean install -DskipTests

echo.
echo ========================================
echo   Setup Complete!
echo ========================================
echo.
echo Your project is ready to run tests.
echo Run 'run-tests.bat' to execute all tests.
echo.
pause

