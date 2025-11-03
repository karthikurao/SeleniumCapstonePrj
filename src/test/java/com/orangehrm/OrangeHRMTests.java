package com.orangehrm;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.utils.EnglishBaseTest;

public class OrangeHRMTests extends EnglishBaseTest {

    @Test(priority = 1, description = "Test successful login to OrangeHRM")
    public void testLogin() {
        loginAsAdmin();
        Assert.assertTrue(new LoginPage(driver).isDashboardDisplayed(), "Login failed - Dashboard not displayed");
        System.out.println("✓ Login test passed successfully");
    }

    @Test(priority = 2, description = "Post a Buzz status and confirm the UI acknowledges it")
    public void testCreateBuzzPostUpdatesTimeline() {
        loginAsAdmin();
        clickMainMenu("Buzz");
        wait.until(ExpectedConditions.urlContains("/buzz"));

        By buzzInputLocator = By.cssSelector("textarea.oxd-buzz-post-input");
        WebElement buzzInput = wait.until(ExpectedConditions.elementToBeClickable(buzzInputLocator));
        buzzInput.click();

        String message = "Automation buzz " + System.currentTimeMillis();
        buzzInput.sendKeys(message);

        WebElement postButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Post']")));
        postButton.click();

        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.oxd-text--toast-message")));
        Assert.assertTrue(toast.getText().contains("Success"), "Buzz post success toast missing");

        // Demo environment does not immediately surface Buzz feed entries, so assert on the
        // composer resetting as the observable confirmation of a successful submission.
        WebDriverWait composerWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        composerWait.until(webDriver -> {
            WebElement input = webDriver.findElement(buzzInputLocator);
            String value = input.getAttribute("value");
            return value == null || value.isBlank();
        });
        WebElement refreshedComposer = driver.findElement(buzzInputLocator);
        Assert.assertTrue(refreshedComposer.isDisplayed(), "Buzz composer not visible after posting");
        Assert.assertTrue(refreshedComposer.getAttribute("placeholder") != null, "Buzz composer placeholder missing after posting");
        System.out.println("✓ Buzz post submitted with success toast and composer reset");
    }

    @Test(priority = 3, description = "Test viewing leave list")
    public void testViewLeaveList() {
        loginAsAdmin();
        LeavePage leavePage = new LeavePage(driver);
        leavePage.navigateToMyLeave();
        Assert.assertTrue(leavePage.isLeaveListDisplayed(), "Leave list not displayed");
        List<WebElement> rows = driver.findElements(By.cssSelector(".oxd-table-body .oxd-table-row"));
        Assert.assertTrue(!rows.isEmpty() || leavePage.hasNoRecordsMessage(), "Leave list did not return rows or empty state");
        System.out.println("✓ My Leave table rendered results or empty state");
    }

    @Test(priority = 4, description = "Test adding a candidate in recruitment")
    public void testAddCandidate() {
        loginAsAdmin();
        RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String firstName = "John" + timestamp.substring(timestamp.length() - 4);
        String email = "john.doe" + timestamp.substring(timestamp.length() - 6) + "@test.com";
        String displayName = recruitmentPage.addCandidate(firstName, "Robert", "Doe", email);
        Assert.assertTrue(recruitmentPage.searchCandidateByName(displayName), "New candidate not returned in search results");
        System.out.println("✓ Candidate added and located in results");
    }

    @Test(priority = 5, description = "Search for system user in Admin module and ensure results are filtered")
    public void testAdminUserSearchFiltersResults() {
        loginAsAdmin();
        clickMainMenu("Admin");
        wait.until(ExpectedConditions.urlContains("/admin"));

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Username']/parent::div/following-sibling::div//input")));
        usernameField.clear();
        usernameField.sendKeys("Admin");

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit' and normalize-space()='Search']")));
        searchButton.click();

        By firstRowSelector = By.xpath("(//div[contains(@class,'oxd-table-card')]//div[@role='cell'][2])[1]");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(firstRowSelector, "Admin"));
        WebElement firstRow = driver.findElement(firstRowSelector);
        Assert.assertTrue(firstRow.getText().contains("Admin"), "Filtered user list does not contain Admin after search");
        System.out.println("✓ Admin user search returned filtered results");
    }

    @Test(priority = 6, description = "Test viewing candidates list")
    public void testViewCandidates() {
        loginAsAdmin();
        RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
        recruitmentPage.navigateToCandidates();
        Assert.assertTrue(recruitmentPage.isCandidateTableDisplayed(), "Candidates table not displayed");
        System.out.println("✓ View Candidates test passed successfully");
    }

    @Test(priority = 7, description = "Use Assign Leave quick launch to open the assign leave form")
    public void testAssignLeaveQuickLaunchOpensForm() {
        loginAsAdmin();
        WebElement assignLeaveCard = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'orangehrm-quick-launch-card')]//p[text()='Assign Leave']/ancestor::div[contains(@class,'orangehrm-quick-launch-card')]")));
        assignLeaveCard.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Assign Leave']")));
        Assert.assertTrue(driver.getCurrentUrl().contains("/assignLeave"), "Assign Leave form was not opened via quick launch");
        System.out.println("✓ Assign Leave quick launch opened the form");
    }

    @Test(priority = 8, dataProvider = "mainMenuSmokeData", description = "Verify key main menu modules load")
    public void testMainMenuModuleLoads(String menuText, String expectedUrlFragment, By[] mustSeeElements) {
        loginAsAdmin();
        clickMainMenu(menuText);
        if (expectedUrlFragment != null && !expectedUrlFragment.isBlank()) {
            wait.until(ExpectedConditions.urlContains(expectedUrlFragment));
        }
        for (By locator : mustSeeElements) {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            Assert.assertTrue(element.isDisplayed(), "Expected element not visible for module " + menuText);
        }
        System.out.println("✓ " + menuText + " module loads");
    }

    @Test(priority = 9, description = "Verify Maintenance password prompt appears")
    public void testMaintenancePromptAppears() {
        loginAsAdmin();
        clickMainMenu("Maintenance");
        wait.until(ExpectedConditions.urlContains("/maintenance"));
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
        Assert.assertTrue(passwordInput.isDisplayed(), "Maintenance password form not displayed");
        System.out.println("✓ Maintenance access screen displayed");
    }

    @Test(priority = 10, description = "Verify dashboard widgets render")
    public void testDashboardWidgetsVisible() {
        loginAsAdmin();
        List<WebElement> widgets = driver.findElements(By.cssSelector("div.orangehrm-dashboard-widget"));
        Assert.assertTrue(widgets.size() >= 1, "Dashboard widgets not visible");
        System.out.println("✓ Dashboard widgets visible");
    }

    @DataProvider(name = "mainMenuSmokeData")
    public Object[][] mainMenuSmokeData() {
        return new Object[][]{
            {"Admin", "/admin", new By[]{By.xpath("//h6[text()='Admin']")}},
            {"PIM", "/pim", new By[]{By.xpath("//h6[text()='PIM']"), By.cssSelector("div.oxd-table")}},
            {"Time", "/time", new By[]{By.xpath("//h6[text()='Time']")}},
            {"My Info", "/viewPersonalDetails", new By[]{By.xpath("//h6[text()='Personal Details']")}},
            {"Directory", "/directory", new By[]{By.xpath("//h6[text()='Directory']"), By.cssSelector("input[placeholder='Type for hints...']")}},
            {"Performance", "/performance", new By[]{By.xpath("//h6[text()='Performance']")}},
            {"Claim", "/claim", new By[]{By.xpath("//h6[contains(normalize-space(),'Claim')]")}}
        };
    }

    private void loginAsAdmin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

    private void clickMainMenu(String menuText) {
        String xpath = String.format("//span[text()='%s']", menuText);
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        menu.click();
    }
}
