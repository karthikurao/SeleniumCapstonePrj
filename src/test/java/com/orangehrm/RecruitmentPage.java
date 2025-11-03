package com.orangehrm;

import org.openqa.selenium.By;
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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToRecruitment() {
        click(recruitmentMenu);
    }

    public void clickAddButton() {
        click(addButton);
    }

    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
    }

    public void enterMiddleName(String middleName) {
        type(middleNameField, middleName);
    }

    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
    }

    public void enterEmail(String email) {
        type(emailField, email);
    }

    public void clickSaveButton() {
        click(saveButton);
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
        click(candidatesLink);
    }

    public boolean isCandidateTableDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(candidateTable));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteFirstCandidate() {
        click(deleteButton);
        click(confirmDeleteButton);
    }

    public void editFirstCandidate() {
        click(editButton);
    }

    public void updateCandidateMiddleName(String newMiddleName) {
        WebElement middleName = wait.until(ExpectedConditions.visibilityOfElementLocated(middleNameField));
        middleName.clear();
        middleName.sendKeys(newMiddleName);
        clickSaveButton();
    }

    private void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    private void type(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }
}
