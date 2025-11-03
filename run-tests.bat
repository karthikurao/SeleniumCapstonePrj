@echo off
setlocal enabledelayedexpansion

:: ========================================
echo   Selenium Capstone Project - Test Runner
:: ========================================
echo.

echo Select a test suite to run:
echo   1) All (testng.xml)
echo   2) ReqRes API only
echo   3) BMI Calculator only
echo   4) OrangeHRM UI only
set /p choice=Enter choice [1-4]:

echo.
set HEADLESS=
if "%choice%"=="2" goto ask_headless
if "%choice%"=="3" goto ask_headless
if "%choice%"=="1" goto all
if "%choice%"=="4" goto orange

echo Invalid choice.
goto end

:ask_headless
set /p headless=Run headless (y/N)?
if /I "%headless%"=="y" set MAVEN_PROPS=-Dheadless=true

if "%choice%"=="2" goto reqres
if "%choice%"=="3" goto bmi

echo Invalid choice.
goto end

:prep
call mvn -q clean
call mvn -q install -DskipTests
exit /b 0

:all
echo Running ALL tests (testng.xml) in headed mode...
set MAVEN_PROPS=
call :prep
call mvn test
goto end

:reqres
echo Running ReqRes API suite...
call :prep
call mvn -q %MAVEN_PROPS% -Dsurefire.suiteXmlFiles=testng-reqres.xml test
goto end

:bmi
echo Running BMI Calculator suite...
call :prep
call mvn -q %MAVEN_PROPS% -Dsurefire.suiteXmlFiles=testng-bmi.xml test
goto end

:orange
echo Running OrangeHRM UI suite in headed mode...
set MAVEN_PROPS=
call :prep
call mvn -q -Dsurefire.suiteXmlFiles=testng-orangehrm.xml test
goto end

:end
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
