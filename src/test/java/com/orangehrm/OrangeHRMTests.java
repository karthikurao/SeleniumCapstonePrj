package com.orangehrm;

import com.utils.EnglishBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class OrangeHRMTests extends EnglishBaseTest {

    @Test(priority = 1, description = "Test successful login to OrangeHRM")
    public void testLogin() {
        loginAsAdmin();
        Assert.assertTrue(new LoginPage(driver).isDashboardDisplayed(), "Login failed - Dashboard not displayed");
        System.out.println("✓ Login test passed successfully");
    }

    @Test(priority = 2, description = "Test opening the Buzz module")
    public void testBuzzPageLoads() {
        loginAsAdmin();
        clickMainMenu("Buzz");
        wait.until(ExpectedConditions.urlContains("/buzz"));
        WebElement buzzInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("textarea.oxd-buzz-post-input")));
        Assert.assertTrue(buzzInput.isDisplayed(), "Buzz post text area not displayed");
        System.out.println("✓ Buzz Page test passed successfully");
    }

    @Test(priority = 3, description = "Test viewing leave list")
    public void testViewLeaveList() {
        loginAsAdmin();
        LeavePage leavePage = new LeavePage(driver);
        leavePage.navigateToMyLeave();
        Assert.assertTrue(leavePage.isLeaveListDisplayed(), "Leave list not displayed");
        System.out.println("✓ View Leave List test passed successfully");
    }

    @Test(priority = 4, description = "Test adding a candidate in recruitment")
    public void testAddCandidate() {
        loginAsAdmin();
        RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
        String timestamp = String.valueOf(System.currentTimeMillis());
        recruitmentPage.addCandidate(
            "John" + timestamp.substring(timestamp.length() - 4),
            "Robert",
            "Doe",
            "john.doe" + timestamp.substring(timestamp.length() - 6) + "@test.com"
        );
        System.out.println("✓ Add Candidate test passed successfully");
    }

    @Test(priority = 5, description = "Test viewing candidates list")
    public void testViewCandidates() {
        loginAsAdmin();
        RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
        recruitmentPage.navigateToCandidates();
        Assert.assertTrue(recruitmentPage.isCandidateTableDisplayed(), "Candidates table not displayed");
        System.out.println("✓ View Candidates test passed successfully");
    }

    @Test(priority = 6, description = "Verify dashboard quick launch cards are visible")
    public void testDashboardQuickLaunchVisible() {
        loginAsAdmin();
        List<WebElement> quickLaunchCards = driver.findElements(By.cssSelector("div.orangehrm-quick-launch-card"));
        Assert.assertTrue(quickLaunchCards.size() >= 1, "Quick Launch cards not found on dashboard");
        System.out.println("✓ Dashboard quick launch cards visible");
    }

    @Test(priority = 7, description = "Verify Admin page loads")
    public void testAdminPageLoads() {
        loginAsAdmin();
        clickMainMenu("Admin");
        WebElement adminHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Admin']")));
        Assert.assertTrue(adminHeader.isDisplayed(), "Admin page header not visible");
        System.out.println("✓ Admin page loads");
    }

    @Test(priority = 8, description = "Verify PIM employee list page loads")
    public void testPimPageLoads() {
        loginAsAdmin();
        clickMainMenu("PIM");
        WebElement pimHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='PIM']")));
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.oxd-table")));
        Assert.assertTrue(pimHeader.isDisplayed() && table.isDisplayed(), "PIM page elements missing");
        System.out.println("✓ PIM page loads with table");
    }

    @Test(priority = 9, description = "Verify Time page loads")
    public void testTimePageLoads() {
        loginAsAdmin();
        clickMainMenu("Time");
        WebElement timeHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Time']")));
        Assert.assertTrue(timeHeader.isDisplayed(), "Time page header not visible");
        System.out.println("✓ Time page loads");
    }

    @Test(priority = 10, description = "Verify My Info page shows personal details")
    public void testMyInfoPageLoads() {
        loginAsAdmin();
        clickMainMenu("My Info");
        WebElement personalDetailsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Personal Details']")));
        Assert.assertTrue(personalDetailsHeader.isDisplayed(), "Personal Details header not visible");
        System.out.println("✓ My Info page loads");
    }

    @Test(priority = 11, description = "Verify Directory page loads")
    public void testDirectoryPageLoads() {
        loginAsAdmin();
        clickMainMenu("Directory");
        WebElement directoryHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Directory']")));
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder='Type for hints...']")));
        Assert.assertTrue(directoryHeader.isDisplayed() && searchInput.isDisplayed(), "Directory search not visible");
        System.out.println("✓ Directory page loads");
    }

    @Test(priority = 12, description = "Verify Performance page loads")
    public void testPerformancePageLoads() {
        loginAsAdmin();
        clickMainMenu("Performance");
        WebElement performanceHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Performance']")));
        Assert.assertTrue(performanceHeader.isDisplayed(), "Performance page header missing");
        System.out.println("✓ Performance page loads");
    }

    @Test(priority = 13, description = "Verify Claim page loads")
    public void testClaimPageLoads() {
        loginAsAdmin();
        clickMainMenu("Claim");
        wait.until(ExpectedConditions.urlContains("/claim"));
        WebElement claimHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(normalize-space(),'Claim')]")));
        Assert.assertTrue(claimHeader.isDisplayed(), "Claim page header missing");
        System.out.println("✓ Claim page loads");
    }

    @Test(priority = 14, description = "Verify Maintenance password prompt appears")
    public void testMaintenancePromptAppears() {
        loginAsAdmin();
        clickMainMenu("Maintenance");
        wait.until(ExpectedConditions.urlContains("/maintenance"));
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
        Assert.assertTrue(passwordInput.isDisplayed(), "Maintenance password form not displayed");
        System.out.println("✓ Maintenance access screen displayed");
    }

    @Test(priority = 15, description = "Verify dashboard widgets render")
    public void testDashboardWidgetsVisible() {
        loginAsAdmin();
        List<WebElement> widgets = driver.findElements(By.cssSelector("div.orangehrm-dashboard-widget"));
        Assert.assertTrue(widgets.size() >= 1, "Dashboard widgets not visible");
        System.out.println("✓ Dashboard widgets visible");
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
