package com.orangehrm;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RecruitmentPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By recruitmentMenu = By.xpath("//span[text()='Recruitment']");
    private By addButton = By.xpath("//button[normalize-space()='Add']");
    private By firstNameField = By.name("firstName");
    private By middleNameField = By.name("middleName");
    private By lastNameField = By.name("lastName");
    private By emailField = By.xpath("//label[text()='Email']/parent::div/following-sibling::div//input");
    private By saveButton = By.cssSelector("button[type='submit']");
    private By candidatesLink = By.linkText("Candidates");
    private By candidateTable = By.cssSelector(".oxd-table");
    private By searchButton = By.cssSelector("button[type='submit']");
    private By deleteButton = By.xpath("//i[contains(@class,'bi-trash')]");
    private By confirmDeleteButton = By.xpath("//button[normalize-space()='Yes, Delete']");
    private By editButton = By.xpath("//i[contains(@class,'bi-pencil')]");

    public RecruitmentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void navigateToRecruitment() {
        wait.until(ExpectedConditions.elementToBeClickable(recruitmentMenu));
        driver.findElement(recruitmentMenu).click();
    }

    public void clickAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        driver.findElement(addButton).click();
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterMiddleName(String middleName) {
        driver.findElement(middleNameField).sendKeys(middleName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void clickSaveButton() {
        driver.findElement(saveButton).click();
    }

    public void addCandidate(String firstName, String middleName, String lastName, String email) {
        navigateToRecruitment();
        clickAddButton();
        enterFirstName(firstName);
        enterMiddleName(middleName);
        enterLastName(lastName);
        enterEmail(email);
        clickSaveButton();
    }

    public void navigateToCandidates() {
        navigateToRecruitment();
        wait.until(ExpectedConditions.elementToBeClickable(candidatesLink));
        driver.findElement(candidatesLink).click();
    }

    public boolean isCandidateTableDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(candidateTable));
            return driver.findElement(candidateTable).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteFirstCandidate() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteButton));
        driver.findElements(deleteButton).get(0).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteButton));
        driver.findElement(confirmDeleteButton).click();
    }

    public void editFirstCandidate() {
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        driver.findElements(editButton).get(0).click();
    }

    public void updateCandidateMiddleName(String newMiddleName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(middleNameField));
        WebElement middleName = driver.findElement(middleNameField);
        middleName.clear();
        middleName.sendKeys(newMiddleName);
        clickSaveButton();
    }
}
