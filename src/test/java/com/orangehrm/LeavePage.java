package com.orangehrm;

import com.utils.SmartWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class LeavePage {
    private WebDriver driver;
    private SmartWait sw;

    // Locators
    private By leaveMenu = By.xpath("//span[text()='Leave']");
    private By applyButton = By.linkText("Apply");
    private By leaveTypeDropdown = By.xpath("//label[text()='Leave Type']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']");
    private By leaveTypeOption = By.xpath("//div[@role='option']/span");
    private By fromDateField = By.xpath("//label[text()='From Date']/parent::div/following-sibling::div//input");
    private By toDateField = By.xpath("//label[text()='To Date']/parent::div/following-sibling::div//input");
    private By commentsField = By.xpath("//label[text()='Comments']/parent::div/following-sibling::div//textarea");
    private By applyLeaveButton = By.cssSelector("button[type='submit']");
    private By myLeaveLink = By.linkText("My Leave");
    private By leaveListTable = By.cssSelector(".oxd-table");

    public LeavePage(WebDriver driver) {
        this.driver = driver;
        this.sw = new SmartWait(driver);
    }

    public void navigateToLeave() {
        sw.safeClick(leaveMenu);
    }

    public void clickApply() {
        sw.safeClick(applyButton);
    }

    public void selectLeaveType() {
        sw.selectFromPopover(leaveTypeDropdown, leaveTypeOption, 1);
    }

    public void enterFromDate(String date) {
        sw.visible(fromDateField, Duration.ofSeconds(5));
        driver.findElement(fromDateField).clear();
        driver.findElement(fromDateField).sendKeys(date);
    }

    public void enterToDate(String date) {
        sw.visible(toDateField, Duration.ofSeconds(5));
        driver.findElement(toDateField).clear();
        driver.findElement(toDateField).sendKeys(date);
    }

    public void enterComments(String comments) {
        sw.visible(commentsField, Duration.ofSeconds(5));
        driver.findElement(commentsField).sendKeys(comments);
    }

    public void clickApplyLeaveButton() {
        sw.safeClick(applyLeaveButton);
    }

    public void applyLeave(String fromDate, String toDate, String comments) {
        navigateToLeave();
        clickApply();
        selectLeaveType();
        enterFromDate(fromDate);
        enterToDate(toDate);
        enterComments(comments);
        clickApplyLeaveButton();
    }

    public void navigateToMyLeave() {
        navigateToLeave();
        sw.safeClick(myLeaveLink);
    }

    public boolean isLeaveListDisplayed() {
        try {
            sw.visible(leaveListTable, Duration.ofSeconds(10));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
