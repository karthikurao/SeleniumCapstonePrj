package com.orangehrm;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LeavePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By leaveMenu = By.xpath("//span[text()='Leave']");
    private final By myLeaveLink = By.linkText("My Leave");
    private final By leaveListTable = By.cssSelector(".oxd-table");

    public LeavePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToLeave() {
        click(leaveMenu);
    }

    public void navigateToMyLeave() {
        navigateToLeave();
        click(myLeaveLink);
    }

    public boolean isLeaveListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(leaveListTable));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (ElementClickInterceptedException ignored) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
}
