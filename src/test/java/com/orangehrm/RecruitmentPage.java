package com.orangehrm;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RecruitmentPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By recruitmentMenu = By.xpath("//span[text()='Recruitment']");
    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By firstNameField = By.name("firstName");
    private final By middleNameField = By.name("middleName");
    private final By lastNameField = By.name("lastName");
    private final By emailField = By.xpath("//label[text()='Email']/parent::div/following-sibling::div//input");
    private final By saveButton = By.xpath("//button[@type='submit' and normalize-space()='Save']");
    private final By candidatesLink = By.linkText("Candidates");
    private final By candidateTable = By.cssSelector(".oxd-table");
    private final By candidateNameFilter = By.xpath("//label[text()='Candidate Name']/parent::div/following-sibling::div//input");
    private final By autoCompleteOption = By.cssSelector(".oxd-autocomplete-option");
    private final By searchButton = By.xpath("//button[@type='submit' and normalize-space()='Search']");
    private final By deleteButton = By.xpath("//i[contains(@class,'bi-trash')]");
    private final By confirmDeleteButton = By.xpath("//button[normalize-space()='Yes, Delete']");
    private final By editButton = By.xpath("//i[contains(@class,'bi-pencil')]");
    private final By toastContainer = By.cssSelector("div.oxd-toast");
    private final By toastMessage = By.cssSelector("p.oxd-text--toast-message");
    private final By tableRows = By.cssSelector(".oxd-table-body .oxd-table-card");

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

    public String addCandidate(String firstName, String middleName, String lastName, String email) {
        navigateToRecruitment();
        clickAddButton();
        enterFirstName(firstName);
        enterMiddleName(middleName);
        enterLastName(lastName);
        enterEmail(email);
        clickSaveButton();
        waitForToast();
        return buildDisplayName(firstName, middleName, lastName);
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

    public boolean searchCandidateByName(String candidateName) {
        navigateToCandidates();
        WebElement filterField = wait.until(ExpectedConditions.visibilityOfElementLocated(candidateNameFilter));
        filterField.click();
        filterField.clear();
        filterField.sendKeys(candidateName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(autoCompleteOption)).click();
        click(searchButton);
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
        String lower = candidateName.toLowerCase();
        return rows.stream().anyMatch(row -> row.getText().toLowerCase().contains(lower));
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

    private void waitForToast() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(toastContainer));
        wait.until(ExpectedConditions.visibilityOfElementLocated(toastMessage));
        List<WebElement> lingering = driver.findElements(toastContainer);
        if (!lingering.isEmpty()) {
            wait.until(ExpectedConditions.invisibilityOfAllElements(lingering));
        }
    }

    private String buildDisplayName(String firstName, String middleName, String lastName) {
        StringBuilder builder = new StringBuilder();
        if (firstName != null && !firstName.isBlank()) {
            builder.append(firstName.trim());
        }
        if (middleName != null && !middleName.isBlank()) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(middleName.trim());
        }
        if (lastName != null && !lastName.isBlank()) {
            if (builder.length() > 0) {
                builder.append(' ');
            }
            builder.append(lastName.trim());
        }
        return builder.toString();
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
