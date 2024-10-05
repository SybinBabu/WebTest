package org.example;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.beust.jcommander.IStringConverter;
import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import javax.sql.rowset.Joinable;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Audit_logs {
    ExtentSparkReporter report = new ExtentSparkReporter("./TestReport+18.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    WebDriver driver;
    SoftAssert softAssert = new SoftAssert();
    String testUser;
    String testEmail;

    @BeforeClass
    public void status1() {
        System.out.println("Testing started for audit logs");
    }

    @AfterClass
    public void status2() {
        System.out.println("Testing completed for audit logs");
    }

    @BeforeTest
    public void report() {
        extent.attachReporter(report);
    }

    @AfterTest
    public void finalReport() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport+18.html").toURI());
    }

    @BeforeMethod
    @Parameters({"testUser"})
    public void setup(String testUser) throws InterruptedException {
        EdgeOptions opt = new EdgeOptions();
        opt.addArguments("--guest");
        driver = new EdgeDriver(opt);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://carear-us-qa.web.app/#/admin/login");
        WebElement username = driver.findElement(By.id("userNameInput"));
        username.sendKeys(testUser);
        WebElement nextButton = driver.findElement(By.id("submitInput"));
        nextButton.click();
        try {
            WebElement verification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='We need to verify your identity']")));
            List<WebElement> vcode = driver.findElements(By.xpath("//input[@type='tel']"));
            Thread.sleep(4000);
            for (WebElement each : vcode) {
                each.sendKeys("1");
            }
            WebElement nextButton2 = driver.findElement(By.xpath("//button[@type='submit']"));
            nextButton2.click();
            WebElement password = driver.findElement(By.id("passwordInput"));
            password.sendKeys("Testing1");
            WebElement signin = driver.findElement(By.id("submitInput"));
            signin.click();
        } catch (Exception e) {
            WebElement password = driver.findElement(By.id("passwordInput"));
            password.sendKeys("Testing1");
            WebElement signin = driver.findElement(By.id("submitInput"));
            signin.click();
        } finally {
            Thread.sleep(5000);
            WebElement portalPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Administration']")));
            boolean expectedString = portalPage.isDisplayed();
            softAssert.assertTrue(expectedString);
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Case Failed" + result.getName());
            test.log(Status.FAIL, "Test Case Failed" + result.getThrowable());
            File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(ss, new File("C:\\Users\\betty\\Desktop\\Test\\ss\\SS.jpg"));
            test.addScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\Test\\ss\\SS.jpg");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Case Skipped" + result.getName());
            test.log(Status.SKIP, "Test Case Skipped" + result.getThrowable());
        } else {
            test.log(Status.PASS, "Test Case Passed" + result.getName());
            test.log(Status.PASS, "Test Case Passed" + result.getThrowable());
        }
        extent.flush();
        driver.quit();
    }

    @Test(priority = 1)
    @Parameters({"testUser"})
    public void TC01(String testUser) throws InterruptedException {
        test = extent.createTest("TC01 : Verify whether audit log feature is implemented in admin page", "Verify whether audit log feature is implemented in admin page").info("user Detail " + testUser);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement auditLogs = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLogs.click();
        test = extent.createTest("step01", "Verify whether audit log header is displayed or not");
        Thread.sleep(15000);
        WebElement auditLog = driver.findElement(By.xpath("//h5[text()='Audit Log']"));
        boolean auditLogHeadrer = auditLog.isDisplayed();
        softAssert.assertTrue(auditLogHeadrer);
        test = extent.createTest("step02", "Verify whether last 7 days tab is displayed or not");
        WebElement last7Days = driver.findElement(By.xpath("//div[text()='7 Days']"));
        boolean last7DaysTab = last7Days.isEnabled();
        softAssert.assertTrue(last7DaysTab);
        test = extent.createTest("step03", "Verify whether last 30 days tab is displayed or not");
        WebElement last30Days = driver.findElement(By.xpath("//div[text()='30 Days']"));
        boolean last30DaysTab = last30Days.isEnabled();
        softAssert.assertTrue(last30DaysTab);
        test = extent.createTest("step04", "Verify whether last 12 months tab is displayed or not");
        WebElement last12Months = driver.findElement(By.xpath("//div[text()='12 Months']"));
        boolean last12MonthsTab = last12Months.isEnabled();
        softAssert.assertTrue(last12MonthsTab);
        test = extent.createTest("step05", "Verify whether last custom date tab is displayed or not");
        WebElement customDate = driver.findElement(By.xpath("//div[text()='Custom Date']"));
        boolean customDateTab = customDate.isEnabled();
        softAssert.assertTrue(customDateTab);
        test = extent.createTest("step06", "Verify whether export button is enabled or not");
        WebElement export = driver.findElement(By.xpath("//button[text()='Export']"));
        boolean exportButton = export.isEnabled();
        softAssert.assertTrue(exportButton);
        test = extent.createTest("step07", "Verify whether setting button is enabled or not");
        WebElement settings = driver.findElement(By.xpath("//button[text()='Settings']"));
        boolean settingButton = settings.isEnabled();
        softAssert.assertTrue(settingButton);
        test = extent.createTest("step08", "Verify whether all event categories column is enabled or not");
        WebElement event = driver.findElement(By.xpath("//input[@placeholder='All event categories']"));
        boolean eventButton = event.isEnabled();
        softAssert.assertTrue(eventButton);
        test = extent.createTest("step09", "Verify whether all user groups column is enabled or not");
        WebElement userGroup = driver.findElement(By.xpath("//input[@placeholder='All user groups']"));
        boolean userGroupButton = userGroup.isEnabled();
        softAssert.assertTrue(userGroupButton);
        test = extent.createTest("step10", "Verify whether date column is displayed or not");
        WebElement date = driver.findElement(By.xpath("//div[text()='Date']"));
        boolean dateColumn = date.isEnabled();
        softAssert.assertTrue(dateColumn);
        test = extent.createTest("step11", "Verify whether user column is displayed or not");
        WebElement user = driver.findElement(By.xpath("//div[text()='User']"));
        boolean userColumn = user.isEnabled();
        softAssert.assertTrue(userColumn);
        test = extent.createTest("step12", "Verify whether event category column is displayed or not");
        WebElement eventCategory = driver.findElement(By.xpath("//div[text()='Event Category']"));
        boolean eventCategoryColumn = eventCategory.isEnabled();
        softAssert.assertTrue(eventCategoryColumn);
        test = extent.createTest("step13", "Verify whether event column is displayed or not");
        WebElement events = driver.findElement(By.xpath("//div[text()='Event Category']"));
        boolean eventsColumn = events.isEnabled();
        softAssert.assertTrue(eventsColumn);
        test = extent.createTest("step14", "Verify whether Access details column is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("//div[text()='Access Details']"));
        boolean accessDetailsColumn = accessDetails.isEnabled();
        softAssert.assertTrue(accessDetailsColumn);
    }

    @Test(priority = 2)
    @Parameters({"testUser"})
    public void TC02(String testUser) throws IOException, InterruptedException {
        test = extent.createTest("TC02 : Verify whether user access details are generating in audit logs", "Verify whether user access details are generating in audit logs").info("user Detail " + testUser);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        WebElement auditLogs = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Audit Logs']")));
        auditLogs.click();
        Thread.sleep(15000);
        //
        test = extent.createTest("Step01", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[2]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss1);
        //
        test = extent.createTest("Step02", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[2]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss2);
        //
        test = extent.createTest("Step03", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[2]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            Assert.assertEquals(username, testUser);
        }
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[2]")));
        //
        test = extent.createTest("Step04", "Verify whether event category is displayed as user access");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[2]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "User Access");
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(1000,0)");
        //
        test = extent.createTest("Step05", "Verify whether event description is displayed as user logged in");
        WebElement eventDescription = driver.findElement(By.xpath("(//tbody//tr//td[4])[2]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("logged in") && expectedDescription.contains(testUser)) {
            String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss5);
            //
            Thread.sleep(5000);
            test = extent.createTest("Step06", "Verify whether access details is displayed or not");
            WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[2]"));
            String accessDescription = accessDetails.getText();
            softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
            String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss6);
        }
    }

    @Test(priority = 3)
    @Parameters({"testUser", "testEmail"})
    public void TC03(String testUser, String testEmail) throws InterruptedException {
        test = extent.createTest("TC03 : Verify whether user creation logs are generating in audit log", "Verify whether user creation logs are generating in audit log").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to create a user");
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/users");
        Thread.sleep(15000);
        WebElement addNewUser = driver.findElement(By.xpath("//button[text()='Add New User']"));
        addNewUser.click();
        List<WebElement> userDetails = driver.findElements(By.xpath("//input[@type='text']"));
        String[] valuesToSend = {"", "Test", "user", "+913456787665", "", "Testing"};
        for (int i = 1; i <= userDetails.size() && i < valuesToSend.length; i++) {
            userDetails.get(i).sendKeys(valuesToSend[i]);
        }
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(testEmail);
        WebElement create = driver.findElement(By.xpath("//button[@type = 'submit']"));
        create.click();
        String ss1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss1);
        //
        test = extent.createTest("Step02", "Verify whether toast message is dispalyed or not");
        WebElement toast = driver.findElement(By.xpath("//span[contains(text(),'Successfully created the User ')]"));
        boolean expectedToast = toast.isDisplayed();
        softAssert.assertTrue(expectedToast);
        String ss2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss2);
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/company");
        Thread.sleep(8000);
        WebElement auditLogs = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLogs.click();
        //
        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            Assert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as user administration");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "User Administration");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as user created");
        WebElement eventDescription = driver.findElement(By.xpath("(//tbody//tr//td[4])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("created") && expectedDescription.contains(testEmail) && expectedDescription.contains("user")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
            //
            Thread.sleep(5000);
            test = extent.createTest("Step08", "Verify whether access details is displayed or not");
            WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
            String accessDescription = accessDetails.getText();
            softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
            String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss8);
        }
    }

    @Test(priority = 4)
    @Parameters({"testUser"})
    public void TC04(String testUser) throws InterruptedException {
        test = extent.createTest("TC04: Verify whether user updation is generating logs ", "Verify whether user updation is generating logs").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to edit a user");
        Thread.sleep(5000);
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/users");
        Thread.sleep(4000);
        Actions act = new Actions(driver);
        WebElement firstUser = driver.findElement(By.xpath("(//tbody//tr//td[3])[1]"));
        WebElement edit = driver.findElement(By.xpath("(//span[@class='editRecord'])[1]"));
        act.moveToElement(firstUser).click(edit).perform();
        Thread.sleep(4000);
        WebElement role = driver.findElement(By.xpath("(//div[contains(@class,'search')])[1]"));
        if (!role.getAttribute("class").contains("opened")) {
            role.click();
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        WebElement selectRole = driver.findElement(By.xpath("//li[text()='Administrator']"));
        selectRole.click();
        WebElement create = driver.findElement(By.xpath("//button[@type = 'submit']"));
        create.click();
        String ss1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss1);
        //
        test = extent.createTest("Step02", "Verify whether toast is displayed or not");
        WebElement popuop = driver.findElement(By.xpath("//span[text()='User was successfully updated']"));
        boolean popupmsg = popuop.isDisplayed();
        softAssert.assertTrue(popupmsg);
        String ss2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss2);
//
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/company");
        Thread.sleep(8000);
        WebElement auditLogs = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLogs.click();
        //
        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            Assert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as user administration");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "User Administration");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as user updated");
        WebElement eventDescription = driver.findElement(By.xpath("(//tbody//tr//td[4])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("User update")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 5)
    @Parameters({"testUser"})
    public void TC05(String testUser) throws AWTException, InterruptedException {
        test = extent.createTest("TC05: Verify logs for activating branding control are generating or not", "Verify logs for branding control are generating or not.").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step:01", "Verify whether user is able to change call to action text");
        WebElement brandControlWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Branding Controls']")));
        brandControlWait.click();
        Thread.sleep(15000);
        WebElement customLogo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Custom Logo']")));
        WebElement callToActionText = driver.findElement(By.id("callToActionText"));
        callToActionText.clear();
        callToActionText.sendKeys("Testing");
        WebElement supplementaryInformationText = driver.findElement(By.id("supplementaryInformationText"));
        supplementaryInformationText.sendKeys("Testing");
        WebElement assistInvitationString = driver.findElement(By.id("assistInvitationString"));
        assistInvitationString.clear();
        assistInvitationString.sendKeys("You are invited to connect via CareAR. Click {INVITE_LINK} “to show what you see”.Testing");
        WebElement save = driver.findElement(By.cssSelector("button[type='Submit']"));
        save.click();
        WebElement popup = driver.findElement(By.xpath("//span[text()='Branding Controls successfully updated.']"));
        softAssert.assertTrue(popup.isDisplayed());
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(12000);
        test = extent.createTest("Step02", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step03", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Co-branded landing page activated");
        WebElement eventDescription = driver.findElement(By.xpath("(//tbody//tr//td[4])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Co-branded landing page activated")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 6, enabled = true)
    @Parameters({"testUser"})
    public void TC06(String testUser) throws InterruptedException {
        test = extent.createTest("TC06: Verify whether deactivating branding control generate audit logs or not", "Verify whether deactivating branding control generate audit logs or not").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //navigate to branding control
        test = extent.createTest("Step01", " Verify whether user is able to deactivate branding control ");
        WebElement brandingControl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Branding Controls']")));
        brandingControl.click();
        Thread.sleep(15000);
        WebElement customLogoWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Custom Logo']")));
        WebElement restore = driver.findElement(By.xpath("//button[text()='Restore to default']"));
        restore.click();
        Thread.sleep(4000);
        WebElement save = driver.findElement(By.cssSelector("button[type='Submit']"));
        save.click();
        WebElement popup = driver.findElement(By.xpath("//span[text()='Branding Controls successfully updated.']"));
        softAssert.assertTrue(popup.isDisplayed());
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(12000);
        test = extent.createTest("Step02", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step03", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Co-branded landing page deactivated");
        WebElement eventDescription = driver.findElement(By.xpath("(//tbody//tr//td[4])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Co-branded landing page deactivated")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//td[@title='Desktop, Edge 129.0.0.0'])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);

    }

    @Test(priority = 8)
    @Parameters({"testUser"})
    public void TC08(String testUser) throws InterruptedException {
        test = extent.createTest("TC07:Verify whether logs are generating for join controls (off) changes").info("Verify whether logs are generating for join controls (off)changes    +user details " + testUser);
        test = extent.createTest("Step01", "Verify whether user is able to turn off join controls");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement joinwait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Join Controls']")));
        WebElement joinControls = driver.findElement(By.xpath("//h6[text()='Join Controls']"));
        joinControls.click();
        WebElement optionsWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toggle_guestJoinByBrowser")));
        WebElement joinBYBrowser1 = driver.findElement(By.id("toggle_guestJoinByBrowser"));
        if (!joinBYBrowser1.getAttribute("class").contains("checked")) {
            joinBYBrowser1.click();
        }
        Thread.sleep(5000);
        WebElement joinBYGlasses1 = driver.findElement(By.id("toggle_guestJoinBySmartGlasses"));
        if (!joinBYGlasses1.getAttribute("class").contains("checked")) {
            joinBYGlasses1.click();
        }
        WebElement save1 = driver.findElement(By.xpath("//button[text()='Save']"));
        save1.click();
        Thread.sleep(15000);
        WebElement joinBYBrowser = driver.findElement(By.id("toggle_guestJoinByBrowser"));
        if (joinBYBrowser.getAttribute("class").contains("checked")) {
            joinBYBrowser.click();
        }
        Thread.sleep(5000);
        WebElement joinBYGlasses = driver.findElement(By.id("toggle_guestJoinBySmartGlasses"));
        if (joinBYGlasses.getAttribute("class").contains("checked")) {
            joinBYGlasses.click();
        }
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        save.click();
        String ss16 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss16);

        test = extent.createTest("Step02", "Verify toast message is displayed");
        WebElement toast1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Company info has been updated successfully']")));
        String toastMessage = toast1.getText();
        softAssert.assertEquals(toastMessage, "Company info has been updated successfully");
        WebElement toast2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='For this change to take effect, users must log out of the app and log back in.']")));
        String toast2Message = toast2.getText();
        softAssert.assertEquals(toast2Message, "For this change to take effect, users must log out of the app and log back in.");
        String s17 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(s17);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);


        test = extent.createTest("Step03", "Verify whether date and day is displayed for join by glasses event or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[2]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);

        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[2]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);

        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[2]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);

        test = extent.createTest("Step06", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[2]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);

        test = extent.createTest("Step07", "Verify whether event description is displayed as Join Controls: Join by smart glasses updated to enabled");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[@title='Join Controls: Join by smart glasses updated to enabled'])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "Join Controls: Join by smart glasses updated to enabled");
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);
        Thread.sleep(5000);

        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[2]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDetails, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);

        test = extent.createTest("Step09", "Verify whether date and day is displayed for join by browser or not");
        WebElement date2 = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus2 = date.isDisplayed();
        Assert.assertTrue(dateStatus2);
        String ss9 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss9);

        test = extent.createTest("Step10", "Verify whether time is displayed or not");
        WebElement time2 = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus2 = time2.isDisplayed();
        Assert.assertTrue(timeStatus2);
        String ss10 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss10);

        test = extent.createTest("Step11", "Verify whether username is displayed or not");
        WebElement user2 = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user2.getText().contains(testUser)) {
            String username2 = user2.getText();
            softAssert.assertEquals(username2, testUser);
        }
        String ss12 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss12);

        test = extent.createTest("Step12", "Verify whether event category is displayed as administration settings");
        WebElement event2 = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString2 = event2.getText();
        Assert.assertEquals(eventString2, "Administration Settings");
        String ss13 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss13);

        test = extent.createTest("Step13", "Verify whether event description is displayed as Join Controls: Join by browser updated to enabled");
        WebElement eventDescription2 = driver.findElement(By.xpath("(//td[@title='Join Controls: Join by browser updated to enabled'])[1]"));
        String expectedDescription2 = eventDescription2.getText();
        Assert.assertEquals(expectedDescription2, "Join Controls: Join by browser updated to enabled");
        String ss14 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss14);

        Thread.sleep(5000);
        test = extent.createTest("Step14", "Verify whether access details is displayed or not");
        WebElement accessDetails2 = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription2 = accessDetails2.getText();
        softAssert.assertEquals(accessDescription2, "Desktop, Edge 129.0.0.0");
        String ss15 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss15);
    }

    @Test(priority = 7)
    @Parameters({"testUser"})
    public void TC07(String testUser) throws InterruptedException {
        test = extent.createTest("TC07:Verify whether logs are generating for join controls changes").info("Verify whether logs are generating for join controls changes    +user details " + testUser);
        test = extent.createTest("Step01", "Verify whether user is able to turn on join controls");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement joinwait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Join Controls']")));
        WebElement joinControls = driver.findElement(By.xpath("//h6[text()='Join Controls']"));
        joinControls.click();
        WebElement optionsWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toggle_guestJoinByBrowser")));
        WebElement joinBYBrowser1 = driver.findElement(By.id("toggle_guestJoinByBrowser"));
        if (joinBYBrowser1.getAttribute("class").contains("checked")) {
            joinBYBrowser1.click();
        }
        Thread.sleep(5000);
        WebElement joinBYGlasses1 = driver.findElement(By.id("toggle_guestJoinBySmartGlasses"));
        if (joinBYGlasses1.getAttribute("class").contains("checked")) {
            joinBYGlasses1.click();
        }
        WebElement save1 = driver.findElement(By.xpath("//button[text()='Save']"));
        save1.click();
        Thread.sleep(15000);
        WebElement joinBYBrowser = driver.findElement(By.id("toggle_guestJoinByBrowser"));
        if (!joinBYBrowser.getAttribute("class").contains("checked")) {
            joinBYBrowser.click();
        }
        Thread.sleep(5000);
        WebElement joinBYGlasses = driver.findElement(By.id("toggle_guestJoinBySmartGlasses"));
        if (!joinBYGlasses.getAttribute("class").contains("checked")) {
            joinBYGlasses.click();
        }
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        save.click();
        String ss16 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss16);

        test = extent.createTest("Step02", "Verify toast message is displayed");
        WebElement toast1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Company info has been updated successfully']")));
        String toastMessage = toast1.getText();
        softAssert.assertEquals(toastMessage, "Company info has been updated successfully");
        WebElement toast2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='For this change to take effect, users must log out of the app and log back in.']")));
        String toast2Message = toast2.getText();
        softAssert.assertEquals(toast2Message, "For this change to take effect, users must log out of the app and log back in.");
        String s17 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(s17);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);


        test = extent.createTest("Step03", "Verify whether date and day is displayed for join by glasses event or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[2]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);

        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[2]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);

        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[2]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);

        test = extent.createTest("Step06", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[2]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);

        test = extent.createTest("Step07", "Verify whether event description is displayed as Join Controls: Join by smart glasses updated to disabled");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[@title='Join Controls: Join by smart glasses updated to disabled'])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "Join Controls: Join by smart glasses updated to disabled");
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);
        Thread.sleep(5000);

        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[2]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);

        test = extent.createTest("Step09", "Verify whether date and day is displayed for join by browser or not");
        WebElement date2 = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus2 = date2.isDisplayed();
        Assert.assertTrue(dateStatus2);
        String ss9 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss9);

        test = extent.createTest("Step10", "Verify whether time is displayed or not");
        WebElement time2 = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus2 = time2.isDisplayed();
        Assert.assertTrue(timeStatus2);
        String ss10 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss10);

        test = extent.createTest("Step11", "Verify whether username is displayed or not");
        WebElement user2 = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user2.getText().contains(testUser)) {
            String username2 = user2.getText();
            softAssert.assertEquals(username2, testUser);
        }
        String ss12 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss12);

        test = extent.createTest("Step12", "Verify whether event category is displayed as administration settings");
        WebElement event2 = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString2 = event2.getText();
        Assert.assertEquals(eventString2, "Administration Settings");
        String ss13 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss13);

        test = extent.createTest("Step13", "Verify whether event description is displayed as Join Controls: Join by browser updated to disabled");
        WebElement eventDescription2 = driver.findElement(By.xpath("(//td[@title='Join Controls: Join by browser updated to disabled'])[1]"));
        String expectedDescription2 = eventDescription2.getText();
        Assert.assertEquals(expectedDescription2, "Join Controls: Join by browser updated to disabled");
        String ss14 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss14);

        Thread.sleep(5000);
        test = extent.createTest("Step14", "Verify whether access details is displayed or not");
        WebElement accessDetails2 = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription2 = accessDetails2.getText();
        softAssert.assertEquals(accessDescription2, "Desktop, Edge 129.0.0.0");
        String ss15 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss15);
    }

    @Test(priority = 9)
    @Parameters({"testUser"})
    public void TC09(String testUser) throws InterruptedException {
        test = extent.createTest("TC09: Verify whether logs are generating for changes in usage information:Reminder 1 Assist", "Verify whether logs are generating for changes in usage information :Reminder 1 Assist").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to change values in low balance notification");
        WebElement usageInfo = driver.findElement(By.xpath("//h6[text()='Usage Information']"));
        usageInfo.click();
        WebElement lowBalanceNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'Low')]")));
        lowBalanceNotification.click();
        WebElement reminder1 = driver.findElement(By.xpath("(//input[@type='text'])[1]"));
        String r1 = reminder1.getAttribute("value");
        int v1 = Integer.parseInt(r1);
        int constant = 10;
        int v = v1 + constant;
        String r1v1 = String.valueOf(v);
        reminder1.clear();
        reminder1.sendKeys(r1v1);
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement save = driver.findElement(By.xpath("//strong[text()='Save']"));
        save.click();
        test = extent.createTest("Step02", "Verify whether success popup is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Notification setting update successful']")));
        String toastMessage = toast.getText();
        softAssert.assertEquals(toastMessage, "Notification setting update successful");
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        String ssu2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssu2);
        auditLog.click();
        Thread.sleep(15000);


        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Usage Information: Low Balance Notification updated");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Usage Information:')])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Usage Information: Low Balance Notification updated")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);

    }

    @Test(priority = 10)
    @Parameters({"testUser"})
    public void TC10(String testUser) throws InterruptedException {
        test = extent.createTest("TC10: Verify whether logs are generating for changes in usage information:Reminder 2 Assist", "Verify whether logs are generating for changes in usage information :Reminder 2 Assist").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to change values in low balance notification");
        WebElement usageInfo = driver.findElement(By.xpath("//h6[text()='Usage Information']"));
        usageInfo.click();
        WebElement lowBalanceNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'Low')]")));
        lowBalanceNotification.click();
        WebElement reminder1 = driver.findElement(By.xpath("(//input[@type='text'])[1]"));
        Thread.sleep(3000);
        WebElement reminder2 = driver.findElement(By.xpath("(//input[@type='text'])[3]"));
        String v1 = reminder1.getAttribute("value");
        int v = Integer.parseInt(v1);
        int constant = 1;
        int v2 = v - constant;
        String v3 = String.valueOf(v2);
        reminder2.clear();
        reminder2.sendKeys(v3);
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement save = driver.findElement(By.xpath("//strong[text()='Save']"));
        save.click();

        test = extent.createTest("Step02", "Verify whether success popup is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Notification setting update successful']")));
        String toastMessage = toast.getText();
        softAssert.assertEquals(toastMessage, "Notification setting update successful");
        String ssu2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssu2);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);


        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Usage Information: Low Balance Notification updated");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Usage Information:')])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Usage Information: Low Balance Notification updated")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);

    }

    @Test(priority = 11)
    @Parameters({"testUser"})
    public void TC11(String testUser) throws InterruptedException {
        test = extent.createTest("TC11: Verify whether logs are generating for changes in usage information:Reminder 3 Assist", "Verify whether logs are generating for changes in usage information :Reminder 3 Assist").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to change values in low balance notification");
        WebElement usageInfo = driver.findElement(By.xpath("//h6[text()='Usage Information']"));
        usageInfo.click();
        WebElement lowBalanceNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'Low')]")));
        lowBalanceNotification.click();
        WebElement reminder2 = driver.findElement(By.xpath("(//input[@type='text'])[3]"));
        WebElement reminder3 = driver.findElement(By.xpath("(//input[@type='text'])[5]"));
        String r2 = reminder2.getAttribute("value");
        int v2 = Integer.parseInt(r2);
        int constant = 1;
        int v2r2 = v2 - constant;
        String r2v2 = String.valueOf(v2r2);
        reminder3.clear();
        reminder3.sendKeys(r2v2);
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement save = driver.findElement(By.xpath("//strong[text()='Save']"));
        save.click();
        test = extent.createTest("Step02", "Verify whether success popup is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Notification setting update successful']")));
        String toastMessage = toast.getText();
        softAssert.assertEquals(toastMessage, "Notification setting update successful");
        String ssu2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssu2);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);


        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Usage Information: Low Balance Notification updated");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Usage Information:')])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Usage Information: Low Balance Notification updated")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);

    }

    @Test(priority = 12)
    @Parameters({"testUser"})
    public void TC12(String testUser) throws InterruptedException {
        test = extent.createTest("TC12: Verify whether logs are generating for changes in usage information:Reminder 1 Instruct", "Verify whether logs are generating for changes in usage information :Reminder 1 Instruct").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to change values in low balance notification");
        WebElement usageInfo = driver.findElement(By.xpath("//h6[text()='Usage Information']"));
        usageInfo.click();
        WebElement lowBalanceNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'Low')]")));
        lowBalanceNotification.click();
        WebElement reminder1 = driver.findElement(By.xpath("(//input[@type='text'])[2]"));
        String r1 = reminder1.getAttribute("value");
        int v1 = Integer.parseInt(r1);
        int constant = 10;
        int v = v1 + constant;
        String r1v1 = String.valueOf(v);
        reminder1.clear();
        reminder1.sendKeys(r1v1);
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement save = driver.findElement(By.xpath("//strong[text()='Save']"));
        save.click();
        test = extent.createTest("Step02", "Verify whether success popup is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Notification setting update successful']")));
        String toastMessage = toast.getText();
        softAssert.assertEquals(toastMessage, "Notification setting update successful");
        String ssu2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssu2);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);


        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Usage Information: Low Balance Notification updated");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Usage Information:')])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Usage Information: Low Balance Notification updated")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);

    }

    @Test(priority = 13)
    @Parameters({"testUser"})
    public void TC13(String testUser) throws InterruptedException {
        test = extent.createTest("TC13: Verify whether logs are generating for changes in usage information:Reminder 2 Instruct", "Verify whether logs are generating for changes in usage information :Reminder 2 Instruct").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to change values in low balance notification");
        WebElement usageInfo = driver.findElement(By.xpath("//h6[text()='Usage Information']"));
        usageInfo.click();
        WebElement lowBalanceNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'Low')]")));
        lowBalanceNotification.click();
        WebElement reminder1 = driver.findElement(By.xpath("(//input[@type='text'])[2]"));
        WebElement reminder2 = driver.findElement(By.xpath("(//input[@type='text'])[4]"));
        String r2 = reminder1.getAttribute("value");
        int v2 = Integer.parseInt(r2);
        int constant = 1;
        int v = v2 - constant;
        String v2r2 = String.valueOf(v);
        reminder2.clear();
        reminder2.sendKeys(v2r2);
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement save = driver.findElement(By.xpath("//strong[text()='Save']"));
        save.click();
        test = extent.createTest("Step02", "Verify whether success popup is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Notification setting update successful']")));
        String toastMessage = toast.getText();
        softAssert.assertEquals(toastMessage, "Notification setting update successful");
        String ssu2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssu2);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);
        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Usage Information: Low Balance Notification updated");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Usage Information:')])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Usage Information: Low Balance Notification updated")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);

    }

    @Test(priority = 14)
    @Parameters({"testUser"})
    public void TC14(String testUser) throws InterruptedException {
        test = extent.createTest("TC14: Verify whether logs are generating for changes in usage information:Reminder 3 Instruct", "Verify whether logs are generating for changes in usage information :Reminder 3 Instruct").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to change values in low balance notification");
        WebElement usageInfo = driver.findElement(By.xpath("//h6[text()='Usage Information']"));
        usageInfo.click();
        WebElement lowBalanceNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[contains(text(),'Low')]")));
        lowBalanceNotification.click();
        WebElement reminder2 = driver.findElement(By.xpath("(//input[@type='text'])[4]"));
        WebElement reminder3 = driver.findElement(By.xpath("(//input[@type='text'])[6]"));
        String r3 = reminder2.getAttribute("value");
        int v3 = Integer.parseInt(r3);
        int constant = 1;
        int v = v3 - constant;
        String v3r3 = String.valueOf(v);
        reminder3.clear();
        reminder3.sendKeys(v3r3);
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement save = driver.findElement(By.xpath("//strong[text()='Save']"));
        save.click();
        test = extent.createTest("Step02", "Verify whether success popup is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Notification setting update successful']")));
        String toastMessage = toast.getText();
        softAssert.assertEquals(toastMessage, "Notification setting update successful");
        String ssu2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssu2);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);


        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Usage Information: Low Balance Notification updated");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Usage Information:')])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Usage Information: Low Balance Notification updated")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);

    }

    @Test(priority = 15)
    @Parameters({"testUser"})
    public void TC15(String testUser) throws InterruptedException {
        test = extent.createTest("TC15: Verify whether logs are generating for changes for email notification list creation", "Verify whether logs are generating for changes for email notification list update").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to add new email");
        WebElement usageInfo = driver.findElement(By.xpath("//h6[text()='Usage Information']"));
        usageInfo.click();
        WebElement addEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Add Email']")));
        addEmail.click();
        Thread.sleep(2000);
        WebElement email = driver.findElement(By.cssSelector("input[placeholder='Email']"));
        email.sendKeys("abc@gmail.com");
        WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Add']")));
        add.click();

        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);

        test = extent.createTest("Step02", "Verify whether success toast is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Notification setting update successful']")));
        String expectedToast = toast.getText();
        softAssert.assertEquals(expectedToast, "Notification setting update successful");
        String ssu1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssu1);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Usage Information: Low Balance Notification updated");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[@title='Usage Information: Email notification List updated'])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Usage Information: Email notification List updated")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);

    }

    @Test(priority = 16)
    @Parameters({"testUser"})
    public void TC16(String testUser) throws InterruptedException {
        test = extent.createTest("TC16: Verify whether logs are generating for changes for email notification list delete", "Verify whether logs are generating for changes for email notification list update").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(55));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to add new email");
        WebElement usageInfo = driver.findElement(By.xpath("//h6[text()='Usage Information']"));
        usageInfo.click();
        WebElement addEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Add Email']")));
        addEmail.click();
        Thread.sleep(2000);
        WebElement email = driver.findElement(By.cssSelector("input[placeholder='Email']"));
        email.sendKeys("abctest2@gmail.com");
        WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Add']")));
        add.click();
        Thread.sleep(15000);
        WebElement trash = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete_1")));
        trash.click();
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);

        test = extent.createTest("Step02", "Verify whether success toast is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Notification setting update successful']")));
        String expectedToast = toast.getText();
        softAssert.assertEquals(expectedToast, "Notification setting update successful");
        String ssu1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssu1);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Usage Information: Low Balance Notification updated");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[@title='Usage Information: Email notification List updated'])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Usage Information: Email notification List updated")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);

    }

    @Test(priority = 17)
    @Parameters({"testUser"})
    public void TC17(String testUser) throws InterruptedException {
        test = extent.createTest("TC17: Verify whether logs are generating for changes for email notification list update", "Verify whether logs are generating for changes for email notification list update").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(55));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to add new email");
        WebElement usageInfo = driver.findElement(By.xpath("//h6[text()='Usage Information']"));
        usageInfo.click();
        WebElement addEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Add Email']")));
        addEmail.click();
        Thread.sleep(2000);
        WebElement email = driver.findElement(By.cssSelector("input[placeholder='Email']"));
        email.sendKeys("abctest5@gmail.com");
        WebElement add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Add']")));
        add.click();
        Thread.sleep(15000);
        WebElement update = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit_1")));
        update.click();
        Thread.sleep(5000);
        WebElement emailUpdate = driver.findElement(By.cssSelector("input[placeholder='Email']"));
        emailUpdate.clear();
        emailUpdate.sendKeys("abctest6@gmail.com");
        WebElement addUpdate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Save']")));
        addUpdate.click();
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);

        test = extent.createTest("Step02", "Verify whether success toast is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Notification setting update successful']")));
        String expectedToast = toast.getText();
        softAssert.assertEquals(expectedToast, "Notification setting update successful");
        String ssu1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssu1);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Usage Information: Low Balance Notification updated");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[@title='Usage Information: Email notification List updated'])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Usage Information: Email notification List updated")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 18)
    @Parameters({"testUser"})
    public void TC18(String testUser) throws InterruptedException {
        test = extent.createTest("TC18: Verify whether logs are generating for changes for Host Media Region*", "Verify whether logs are generating for changes for Host Media Region*").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to change host media region");
        WebElement signalControl = driver.findElement(By.xpath("//h6[text()='Signaling and Media Controls']"));
        signalControl.click();
        WebElement signalControlWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
        WebElement hostRegion = driver.findElement(By.xpath("(//select[contains(@class,'form-control')])[1]"));
        Select select = new Select(hostRegion);
        WebElement selectedOption = select.getFirstSelectedOption();
        String selectedText = selectedOption.getText();
        if (selectedText.contains("Global")) {
            select.selectByIndex(1);
        } else if (selectedText.contains("Asia")) {
            select.selectByIndex(2);
        } else if (selectedText.contains("Europe")) {
            select.selectByIndex(3);
        } else if (selectedText.contains("India")) {
            select.selectByIndex(4);
        } else if (selectedText.contains("Japan")) {
            select.selectByIndex(5);
        } else {
            select.selectByIndex(0);
        }

        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        save.click();
        test = extent.createTest("Step02", "Verify whether success toast is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Signaling and Media')]")));
        String expectedToast = toast.getText();
        softAssert.assertEquals(expectedToast, "Signaling and Media setting has been updated successfully");
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step05", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step06", "Verify whether event description is displayed as Signaling and Media Controls: Host media region updated to selected option");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Signaling and Media Controls: Host media region updated to')])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Signaling and Media Controls: Host media region updated to")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step07", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 19)
    @Parameters({"testUser"})
    public void TC19(String testUser) throws InterruptedException {
        test = extent.createTest("TC19: Verify whether logs are generating for changes for Guest Media Region*", "Verify whether logs are generating for changes for Host Media Region*").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to change guest media region");
        WebElement signalControl = driver.findElement(By.xpath("//h6[text()='Signaling and Media Controls']"));
        signalControl.click();
        WebElement signalControlWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
        WebElement guestRegion = driver.findElement(By.xpath("(//select[contains(@class,'form-control')])[2]"));
        Select select = new Select(guestRegion);
        WebElement selectedOption = select.getFirstSelectedOption();
        String selectedText = selectedOption.getText();
        if (selectedText.contains("Global")) {
            select.selectByIndex(1);
        } else if (selectedText.contains("Asia")) {
            select.selectByIndex(2);
        } else if (selectedText.contains("Europe")) {
            select.selectByIndex(3);
        } else if (selectedText.contains("India")) {
            select.selectByIndex(4);
        } else if (selectedText.contains("Japan")) {
            select.selectByIndex(5);
        } else {
            select.selectByIndex(0);
        }
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        save.click();
        test = extent.createTest("Step02", "Verify whether success toast is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Signaling and Media')]")));
        String expectedToast = toast.getText();
        softAssert.assertEquals(expectedToast, "Signaling and Media setting has been updated successfully");
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as Signaling and Media Controls: Guest media region updated to selected option");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Signaling and Media Controls: Guest media region updated to')])[1]"));
        String expectedDescription = eventDescription.getText();
        if (expectedDescription.contains("Signaling and Media Controls: Host media region updated to")) {
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
        }
        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 20)
    @Parameters({"testUser"})
    public void TC20(String testUser) throws InterruptedException {
        test = extent.createTest("TC20: Verify whether logs are generating for changes for Enabling host proxy*", "Verify whether logs are generating for changes for enabling host proxy*").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to enable host proxy");
        WebElement signalControl = driver.findElement(By.xpath("//h6[text()='Signaling and Media Controls']"));
        signalControl.click();
        WebElement signalControlWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
        WebElement hostProxy = driver.findElement(By.id("toggle_hostProxy"));
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        if (!hostProxy.getAttribute("class").contains("checked")) {
            hostProxy.click();
            save.click();
            String ssue = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssue);
        } else if (hostProxy.getAttribute("class").contains("checked")) {
            hostProxy.click();
            save.click();
            Thread.sleep(5000);
            driver.navigate().refresh();
            Thread.sleep(10000);
            WebElement signalControlSecondWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Signaling and Media Controls']")));
            signalControlSecondWait.click();
            WebElement signalControlWaitSecond = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
            WebElement hostProxyEnableWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toggle_hostProxy")));
            hostProxyEnableWait.click();
            WebElement saveEnable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']")));
            saveEnable.click();
            String ssuu = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssuu);
        }
        test = extent.createTest("Step02", "Verify whether toast message is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Company info has been updated successfully')]")));
        String expectedToast = toast.getText();
        softAssert.assertEquals(expectedToast, "Company info has been updated successfully");
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as Signaling and Media Controls: Host proxy updated to enabled");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Signaling and Media Controls: Host proxy updated to enabled')])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "Signaling and Media Controls: Host proxy updated to enabled");
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);

        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 21)
    @Parameters({"testUser"})
    public void TC21(String testUser) throws InterruptedException {
        test = extent.createTest("TC21: Verify whether logs are generating for changes for Disabling host proxy*", "Verify whether logs are generating for changes for disabling host proxy*").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to disable host proxy");
        WebElement signalControl = driver.findElement(By.xpath("//h6[text()='Signaling and Media Controls']"));
        signalControl.click();
        WebElement signalControlWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
        WebElement hostProxy = driver.findElement(By.id("toggle_hostProxy"));
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        if (hostProxy.getAttribute("class").contains("checked")) {
            hostProxy.click();
            save.click();
            String ssue = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssue);
        } else if (!hostProxy.getAttribute("class").contains("checked")) {
            hostProxy.click();
            save.click();
            Thread.sleep(5000);
            driver.navigate().refresh();
            Thread.sleep(10000);
            WebElement signalControlSecondWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Signaling and Media Controls']")));
            signalControlSecondWait.click();
            WebElement signalControlWaitSecond = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
            WebElement hostProxyDisableWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toggle_hostProxy")));
            hostProxyDisableWait.click();
            WebElement saveDisable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']")));
            saveDisable.click();
            String ssuu = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssuu);
        }
        test = extent.createTest("Step02", "Verify whether toast message is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Company info has been updated successfully')]")));
        String expectedToast = toast.getText();
        softAssert.assertEquals(expectedToast, "Company info has been updated successfully");
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as Signaling and Media Controls: Signaling and Media Controls: Host proxy updated to disabled");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Signaling and Media Controls: Host proxy updated to disabled')])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "Signaling and Media Controls: Host proxy updated to disabled");
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);

        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 22)
    @Parameters({"testUser"})
    public void TC22(String testUser) throws InterruptedException {
        test = extent.createTest("TC22: Verify whether logs are generating for changes for enabling guest proxy*", "Verify whether logs are generating for changes for enabling guest proxy*").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to enable guest proxy");
        WebElement signalControl = driver.findElement(By.xpath("//h6[text()='Signaling and Media Controls']"));
        signalControl.click();
        WebElement signalControlWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
        WebElement guestProxy = driver.findElement(By.id("toggle_guestProxy"));
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        if (!guestProxy.getAttribute("class").contains("checked")) {
            guestProxy.click();
            save.click();
            String ssue = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssue);
        } else if (guestProxy.getAttribute("class").contains("checked")) {
            guestProxy.click();
            save.click();
            Thread.sleep(5000);
            driver.navigate().refresh();
            Thread.sleep(10000);
            WebElement signalControlSecondWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Signaling and Media Controls']")));
            signalControlSecondWait.click();
            WebElement signalControlWaitSecond = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
            WebElement guestEnableProxyWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toggle_guestProxy")));
            guestEnableProxyWait.click();
            WebElement saveEnable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']")));
            saveEnable.click();
            String ssuu = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssuu);
        }
        test = extent.createTest("Step02", "Verify whether toast message is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Company info has been updated successfully')]")));
        String expectedToast = toast.getText();
        softAssert.assertEquals(expectedToast, "Company info has been updated successfully");
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as Signaling and Media Controls: Signaling and Media Controls: Guest proxy updated to enabled");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Signaling and Media Controls: Guest proxy updated to enabled')])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "Signaling and Media Controls: Guest proxy updated to enabled");
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);

        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 23)
    @Parameters({"testUser"})
    public void TC23(String testUser) throws InterruptedException {
        test = extent.createTest("TC23: Verify whether logs are generating for changes for disabling guest proxy*", "Verify whether logs are generating for changes for disabling guest proxy*").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to disable guest proxy");
        WebElement signalControl = driver.findElement(By.xpath("//h6[text()='Signaling and Media Controls']"));
        signalControl.click();
        WebElement signalControlWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
        WebElement guestProxy = driver.findElement(By.id("toggle_guestProxy"));
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        if (guestProxy.getAttribute("class").contains("checked")) {
            guestProxy.click();
            save.click();
            String ssue = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssue);
        } else if (!guestProxy.getAttribute("class").contains("checked")) {
            guestProxy.click();
            save.click();
            Thread.sleep(5000);
            driver.navigate().refresh();
            Thread.sleep(10000);
            WebElement signalControlSecondWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Signaling and Media Controls']")));
            signalControlSecondWait.click();
            WebElement signalControlWaitSecond = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
            WebElement guestDisableProxyWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toggle_guestProxy")));
            guestDisableProxyWait.click();
            WebElement saveDisable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']")));
            saveDisable.click();
            String ssuu = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssuu);
        }
        test = extent.createTest("Step02", "Verify whether toast message is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Company info has been updated successfully')]")));
        String expectedToast = toast.getText();
        softAssert.assertEquals(expectedToast, "Company info has been updated successfully");
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as Signaling and Media Controls: Signaling and Media Controls: Guest proxy updated to disabled");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Signaling and Media Controls: Guest proxy updated to disabled')])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "Signaling and Media Controls: Guest proxy updated to disabled");
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);

        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 24)
    @Parameters({"testUser"})
    public void TC24(String testUser) throws InterruptedException {
        test = extent.createTest("TC24: Verify whether logs are generating for changes for enabling encryption", "Verify whether logs are generating for changes for enabling encryption").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to enable encryption");
        WebElement signalControl = driver.findElement(By.xpath("//h6[text()='Signaling and Media Controls']"));
        signalControl.click();
        WebElement signalControlWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
        WebElement encryption = driver.findElement(By.id("toggle_encryption"));
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        if (!encryption.getAttribute("class").contains("checked")) {
            encryption.click();
            save.click();
            String ssue = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssue);
        } else if (encryption.getAttribute("class").contains("checked")) {
            encryption.click();
            save.click();
            Thread.sleep(5000);
            driver.navigate().refresh();
            Thread.sleep(10000);
            WebElement signalControlSecondWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Signaling and Media Controls']")));
            signalControlSecondWait.click();
            WebElement signalControlWaitSecond = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
            WebElement encryptionEnableWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toggle_encryption")));
            encryptionEnableWait.click();
            WebElement saveEnable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']")));
            saveEnable.click();
            String ssuu = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssuu);
        }
        test = extent.createTest("Step02", "Verify whether toast message is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Company info has been updated successfully')]")));
        String expectedToast = toast.getText();
        softAssert.assertEquals(expectedToast, "Company info has been updated successfully");
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as Signaling and Media Controls: Signaling and Media Controls: Encryption updated to enabled");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Signaling and Media Controls: Encryption updated to enabled')])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "Signaling and Media Controls: Encryption updated to enabled");
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);

        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 25)
    @Parameters({"testUser"})
    public void TC25(String testUser) throws InterruptedException {
        test = extent.createTest("TC25: Verify whether logs are generating for changes for disabling encryption", "Verify whether logs are generating for changes for disabling encryption").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        test = extent.createTest("Step01", "Verify whether user is able to disable encryption");
        WebElement signalControl = driver.findElement(By.xpath("//h6[text()='Signaling and Media Controls']"));
        signalControl.click();
        WebElement signalControlWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
        WebElement encryption = driver.findElement(By.id("toggle_encryption"));
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        if (encryption.getAttribute("class").contains("checked")) {
            encryption.click();
            save.click();
            String ssue = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssue);
        } else if (!encryption.getAttribute("class").contains("checked")) {
            encryption.click();
            save.click();
            Thread.sleep(5000);
            driver.navigate().refresh();
            Thread.sleep(10000);
            WebElement signalControlSecondWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Signaling and Media Controls']")));
            signalControlSecondWait.click();
            WebElement signalControlWaitSecond = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[text()='Signaling and Media Controls']")));
            WebElement encryptionDisableWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toggle_encryption")));
            encryptionDisableWait.click();
            WebElement saveDisable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save']")));
            saveDisable.click();
            String ssuu = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ssuu);
        }
        test = extent.createTest("Step02", "Verify whether toast message is displayed or not");
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Company info has been updated successfully')]")));
        String expectedToast = toast.getText();
        softAssert.assertEquals(expectedToast, "Company info has been updated successfully");
        String ssui = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ssui);
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as Signaling and Media Controls: Signaling and Media Controls: Encryption updated to disabled");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Signaling and Media Controls: Encryption updated to disabled')])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "Signaling and Media Controls: Encryption updated to disabled");
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);

        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 26)
    @Parameters({"testUser"})
    public void TC26(String testUser) throws InterruptedException {
        test = extent.createTest("TC26: Verify whether logs are generating for changes for changing retention values", "Verify whether logs are generating for changes for changing retention values").info("user Detail " + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement auditLogs = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLogs.click();
        test = extent.createTest("step01", "Verify whether user is able to change retention value");
        Thread.sleep(15000);
        WebElement settings = driver.findElement(By.id("style_settingsButton__djkv2"));
        settings.click();
        Thread.sleep(2000);
        WebElement retentionDropdown = driver.findElement(By.id("auditLogRetention"));
        Select select = new Select(retentionDropdown);
        WebElement selectedOption = select.getFirstSelectedOption();
        String selectedText = selectedOption.getText();
        if (selectedText.contains("30 days")) {
            select.selectByValue("60");
        } else if (selectedText.contains("60 days")) {
            select.selectByValue("90");
        } else if (selectedText.contains("90 days")) {
            select.selectByValue("120");
        } else if (selectedText.contains("120 days")) {
            select.selectByValue("150");
        } else if (selectedText.contains("150 days")) {
            select.selectByValue("180");
        } else {
            select.selectByValue("30");
        }
        Thread.sleep(3000);
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        save.click();
        Thread.sleep(15000);
        test = extent.createTest("Step03", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step04", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step05", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as administration settings");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Administration Settings");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);
        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as Audit log retention value: updated to the retention value given");
        if (selectedText.contains("30 days")) {
            WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Audit log retention value: updated to')])[1]"));
            String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss7);
            int newSelect = 60;
            String expectedDescription = eventDescription.getText();
            Assert.assertEquals(expectedDescription, "Audit log retention value: updated to " + newSelect);
        } else if (selectedText.contains("60 days")) {
            WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Audit log retention value: updated to')])[1]"));
            int newSelect = 90;
            String expectedDescription = eventDescription.getText();
            Assert.assertEquals(expectedDescription, "Audit log retention value: updated to " + newSelect);
        } else if (selectedText.contains("90 days")) {
            WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Audit log retention value: updated to')])[1]"));
            int newSelect = 120;
            String expectedDescription = eventDescription.getText();
            Assert.assertEquals(expectedDescription, "Audit log retention value: updated to " + newSelect);
        } else if (selectedText.contains("120 days")) {
            WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Audit log retention value: updated to')])[1]"));
            int newSelect = 150;
            String expectedDescription = eventDescription.getText();
            Assert.assertEquals(expectedDescription, "Audit log retention value: updated to " + newSelect);
        } else if (selectedText.contains("150 days")) {
            WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Audit log retention value: updated to')])[1]"));
            int newSelect = 180;
            String expectedDescription = eventDescription.getText();
            Assert.assertEquals(expectedDescription, "Audit log retention value: updated to " + newSelect);
        } else {
            WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(@title,'Audit log retention value: updated to')])[1]"));
            int newSelect = 30;
            String expectedDescription = eventDescription.getText();
            Assert.assertEquals(expectedDescription, "Audit log retention value: updated to " + newSelect);
        }
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);
        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);
    }

    @Test(priority = 27)
    @Parameters({"testUser"})
    public void TC27(String testUser) throws InterruptedException {
        test = extent.createTest("TC27: Verify whether audit logs is generated for experience creation", "Verify whether audit logs is generated for experience creation").info("user Details" + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/instruct-experience");
        Thread.sleep(12000);
        WebElement addNew = driver.findElement(By.xpath("//button[text()='Add New']"));
        addNew.click();
        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        if (!childWindows.equals(parentWindow))
            for (String temp : childWindows) {
                driver.switchTo().window(temp);
            }
        System.out.println(driver.getCurrentUrl());
        Thread.sleep(8000);
        test = extent.createTest("Step01: Verify whether user is able to create experience");
        WebElement expName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("experienceName")));
        String name = "CreatedExp";
        expName.sendKeys(name);
        WebElement description = driver.findElement(By.id("experienceDescription"));
        description.sendKeys("test");
        Thread.sleep(2000);
        WebElement startBuilding = driver.findElement(By.xpath("//button[text()='start building']"));
        startBuilding.click();
        Thread.sleep(5000);
        driver.switchTo().window(parentWindow);
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/company");
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step02", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step03", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as Experiences");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Experiences");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);

        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as New Experience created");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(text(),'New Experience created')])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "New Experience created: " + name);
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);

        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);


    }

    @Test(priority = 28)
    @Parameters({"testUser"})
    public void TC28(String testUser) throws InterruptedException {
        test = extent.createTest("TC28: Verify whether audit logs is generated for experience publish", "Verify whether audit logs is generated for experience publish").info("user Details" + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/instruct-experience");
        Thread.sleep(12000);
        WebElement addNew = driver.findElement(By.xpath("//button[text()='Add New']"));
        addNew.click();
        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        if (!childWindows.equals(parentWindow))
            for (String temp : childWindows) {
                driver.switchTo().window(temp);
            }
        System.out.println(driver.getCurrentUrl());
        Thread.sleep(8000);
        test = extent.createTest("Step01: Verify whether user is able to create experience");
        WebElement expName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("experienceName")));
        String name = "PublishExp";
        expName.sendKeys(name);
        WebElement description = driver.findElement(By.id("experienceDescription"));
        description.sendKeys("test");
        Thread.sleep(2000);
        WebElement startBuilding = driver.findElement(By.xpath("//button[text()='start building']"));
        startBuilding.click();
        Thread.sleep(15000);
        WebElement closeIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='OnboardingTipsDialog_closeIcon__oqjbk']")));
        closeIcon.click();
        WebElement publish = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class,'MuiButtonBase-root')])[2]")));
        publish.click();
        driver.switchTo().window(parentWindow);
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/company");
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step02", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step03", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as Experiences");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Experiences");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);

        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as Experience published");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(text(),'Experience published')])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "Experience published: " + name);
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);

        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);


    }

    @Test(priority = 29)
    @Parameters({"testUser"})
    public void TC29(String testUser) throws InterruptedException {
        test = extent.createTest("TC29: Verify whether audit logs is generated for experience copy", "Verify whether audit logs is generated for experience copy").info("user Details" + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/instruct-experience");
        Thread.sleep(12000);
        WebElement addNew = driver.findElement(By.xpath("//button[text()='Add New']"));
        addNew.click();
        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        if (!childWindows.equals(parentWindow))
            for (String temp : childWindows) {
                driver.switchTo().window(temp);
            }
        System.out.println(driver.getCurrentUrl());
        Thread.sleep(8000);
        test = extent.createTest("Step01: Verify whether user is able to add translation to an experience");
        WebElement expName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("experienceName")));
        String name = "CopyExp";
        expName.sendKeys(name);
        WebElement description = driver.findElement(By.id("experienceDescription"));
        description.sendKeys("test");
        Thread.sleep(2000);
        WebElement startBuilding = driver.findElement(By.xpath("//button[text()='start building']"));
        startBuilding.click();
        Thread.sleep(15000);
        WebElement closeIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='OnboardingTipsDialog_closeIcon__oqjbk']")));
        closeIcon.click();
        WebElement publish = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class,'MuiButtonBase-root')])[2]")));
        publish.click();
        Thread.sleep(7000);
        WebElement closeicon2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class='PublishExperienceSuccessDialog_closeIcon__bmK3D']")));
        closeicon2.click();
        WebElement langSelector = driver.findElement(By.xpath("//span[contains(@class,'LanguageSelector_langName__')]"));
        langSelector.click();
        WebElement addTranslation = driver.findElement(By.xpath("//span[contains(text(),'Add translation')]"));
        addTranslation.click();
        WebElement translation = driver.findElement(By.xpath("//span[contains(text(),'ADD TRANSLATION')]"));
        translation.click();
        WebElement toast = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'New translation has been added!')]")));
        String expString = toast.getText();
        Assert.assertEquals(expString, "New translation has been added! Text content was auto translated.");
        Thread.sleep(2000);
        publish.click();
        driver.switchTo().window(parentWindow);
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/company");
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step02", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[2]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step03", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[2]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[2]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as Experiences");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[2]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Experiences");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);

        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as Experience published");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(text(),'Experience copied')])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "Experience copied: " + name);
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);

        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);


    }

    @Test(priority = 30)
    @Parameters({"testUser"})
    public void TC30(String testUser) throws InterruptedException {
        test = extent.createTest("TC30: Verify whether audit logs is generated for experience export feature", "Verify whether audit logs is generated for experience export").info("user Details" + testUser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/instruct-experience");
        Thread.sleep(12000);
        WebElement addNew = driver.findElement(By.xpath("//button[text()='Add New']"));
        addNew.click();
        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        if (!childWindows.equals(parentWindow))
            for (String temp : childWindows) {
                driver.switchTo().window(temp);
            }
        System.out.println(driver.getCurrentUrl());
        Thread.sleep(8000);
        test = extent.createTest("Step01: Verify whether user is able to export an experience");
        WebElement expName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("experienceName")));
        String name = "ExportExp";
        expName.sendKeys(name);
        WebElement description = driver.findElement(By.id("experienceDescription"));
        description.sendKeys("test");
        Thread.sleep(2000);
        WebElement startBuilding = driver.findElement(By.xpath("//button[text()='start building']"));
        startBuilding.click();
        Thread.sleep(15000);
        WebElement closeIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='OnboardingTipsDialog_closeIcon__oqjbk']")));
        closeIcon.click();
        Thread.sleep(3000);
        WebElement publish = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(@class,'MuiButtonBase-root')])[2]")));
        publish.click();
        Thread.sleep(9000);
        WebElement closeicon2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[class='PublishExperienceSuccessDialog_closeIcon__bmK3D']")));
        closeicon2.click();
        WebElement hamburger = driver.findElement(By.id("editor-menu-button"));
        hamburger.click();
        WebElement export = driver.findElement(By.xpath("//span[text()='Export']"));
        export.click();
        Thread.sleep(2000);
        WebElement exportPopup = driver.findElement(By.xpath("//span[text()='EXPORT']"));
        exportPopup.click();
        Thread.sleep(2000);
        driver.switchTo().window(parentWindow);
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/company");
        WebElement auditLog = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLog.click();
        Thread.sleep(15000);

        test = extent.createTest("Step02", "Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
        //
        test = extent.createTest("Step03", "Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);
        //
        test = extent.createTest("Step04", "Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
        if (user.getText().contains(testUser)) {
            String username = user.getText();
            softAssert.assertEquals(username, testUser);
        }
        String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss5);
        WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));
        //
        test = extent.createTest("Step06", "Verify whether event category is displayed as Experiences");
        WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
        String eventString = event.getText();
        Assert.assertEquals(eventString, "Experiences");
        String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss6);

        //
        test = extent.createTest("Step07", "Verify whether event description is displayed as Experience exported");
        WebElement eventDescription = driver.findElement(By.xpath("(//td[contains(text(),'Experience exported')])[1]"));
        String expectedDescription = eventDescription.getText();
        Assert.assertEquals(expectedDescription, "Experience exported: " +name);
        String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss7);

        //
        Thread.sleep(5000);
        test = extent.createTest("Step08", "Verify whether access details is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
        String accessDescription = accessDetails.getText();
        softAssert.assertEquals(accessDescription, "Desktop, Edge 129.0.0.0");
        String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss8);


    }
}