package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Auditlogs {

    ExtentSparkReporter report = new ExtentSparkReporter("./TestReport18.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    WebDriver driver;
    SoftAssert softAssert = new SoftAssert();
    String testUser;
    String testEmail;

    @BeforeClass
    public void status1(){
        System.out.println("Testing started for audit logs");
    }
    @AfterClass
    public void status2(){
        System.out.println("Testing completed for audit logs");
    }
    @BeforeTest
    public void report(){
        extent.attachReporter(report);
    }
    @AfterTest
    public void finalReport() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport18.html").toURI());
    }
    @BeforeMethod
    @Parameters({"testUser"})
    public void setup(String testUser){
        EdgeOptions opt = new EdgeOptions();
        opt.addArguments("--guest");
        driver = new EdgeDriver(opt);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15))  ;
        driver.manage().window().maximize();
        driver.get("https://carear-us-qa.web.app/#/admin/login");
        WebElement username = driver.findElement(By.id("userNameInput"));
        username.sendKeys(testUser);
        WebElement nextButton = driver.findElement(By.id("submitInput"));
        nextButton.click();
        try{
    WebElement verification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='We need to verify your identity']")));
    List<WebElement> vcode = driver.findElements(By.xpath("//input[@type='tel']"));
    Thread.sleep(4000);
    for(WebElement each : vcode){
        each.sendKeys("1");
    }
    WebElement nextButton2 = driver.findElement(By.xpath("//button[@type='submit']"));
    nextButton2.click();
            WebElement password = driver.findElement(By.id("passwordInput"));
            password.sendKeys("Testing1");
            WebElement signin = driver.findElement(By.id("submitInput"));
            signin.click();

        }
        catch (Exception e){
            WebElement password = driver.findElement(By.id("passwordInput"));
            password.sendKeys("Testing1");
            WebElement signin = driver.findElement(By.id("submitInput"));
            signin.click();
        }
        finally{
            WebElement portalPage = driver.findElement(By.xpath("//h4[text()='Administration']"));
            boolean expectedString = portalPage.isDisplayed();
            softAssert.assertTrue(expectedString);

        }
    }
    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE){
            test.log(Status.FAIL,"Test Case Failed" +result.getName());
            test.log(Status.FAIL,"Test Case Failed" +result.getThrowable());
            File ss = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            Files.copy(ss,new File("C:\\Users\\betty\\Desktop\\Test\\ss\\SS.jpg"));
            test.addScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\Test\\ss\\SS.jpg");
        }
        else if (result.getStatus()== ITestResult.SKIP){
            test.log(Status.SKIP,"Test Case Skipped" +result.getName());
            test.log(Status.SKIP,"Test Case Skipped" +result.getThrowable());
        }
        else {
            test.log(Status.PASS,"Test Case Passed" +result.getName());
            test.log(Status.PASS,"Test Case Passed" +result.getThrowable());
        }
        extent.flush();
        //driver.quit();
    }
    @Test(enabled = false)
    @Parameters({"testUser"})
    public void TC01(String testUser){
        test = extent.createTest("TC01 : Verify whether audit log feature is implemented in admin page","Verify whether audit log feature is implemented in admin page").info("user Detail " +testUser);
   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
        WebElement auditLogs = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLogs.click();
        test = extent.createTest("step01","Verify whether audit log header is displayed or not");
        WebElement auditLog = driver.findElement(By.xpath("//h5[text()='Audit Log']"));
        boolean auditLogHeadrer = auditLog.isDisplayed();
        softAssert.assertTrue(auditLogHeadrer);
        test = extent.createTest("step02","Verify whether last 7 days tab is displayed or not");
        WebElement last7Days = driver.findElement(By.xpath("//div[text()='Last 7 Days']"));
        boolean last7DaysTab = last7Days.isEnabled();
        softAssert.assertTrue(last7DaysTab);
        test = extent.createTest("step03","Verify whether last 30 days tab is displayed or not");
        WebElement last30Days = driver.findElement(By.xpath("//div[text()='Last 30 Days']"));
        boolean last30DaysTab = last30Days.isEnabled();
        softAssert.assertTrue(last30DaysTab);
        test = extent.createTest("step04","Verify whether last 12 months tab is displayed or not");
        WebElement last12Months = driver.findElement(By.xpath("//div[text()='Last 12 Months']"));
        boolean last12MonthsTab = last12Months.isEnabled();
        softAssert.assertTrue(last12MonthsTab);
        test = extent.createTest("step05","Verify whether last custom date tab is displayed or not");
        WebElement customDate = driver.findElement(By.xpath("//div[text()='Custom Date']"));
        boolean customDateTab = customDate.isEnabled();
        softAssert.assertTrue(customDateTab);
        test = extent.createTest("step06","Verify whether export button is enabled or not");
        WebElement export = driver.findElement(By.xpath("//button[text()='Export']"));
        boolean exportButton = export.isEnabled();
        softAssert.assertTrue(exportButton);
        test = extent.createTest("step07","Verify whether setting button is enabled or not");
        WebElement settings = driver.findElement(By.xpath("//button[text()='Settings']"));
        boolean settingButton = settings.isEnabled();
        softAssert.assertTrue(settingButton);
        test = extent.createTest("step08","Verify whether all event categories column is enabled or not");
        WebElement event = driver.findElement(By.xpath("//input[@placeholder='All event categories']"));
        boolean eventButton = event.isEnabled();
        softAssert.assertTrue(eventButton);
        test = extent.createTest("step09","Verify whether all user groups column is enabled or not");
        WebElement userGroup = driver.findElement(By.xpath("//input[@placeholder='All user groups']"));
        boolean userGroupButton = userGroup.isEnabled();
        softAssert.assertTrue(userGroupButton);
        test = extent.createTest("step10","Verify whether date column is displayed or not");
        WebElement date = driver.findElement(By.xpath("//div[text()='Date']"));
        boolean dateColumn = date.isEnabled();
        softAssert.assertTrue(dateColumn);
        test = extent.createTest("step11","Verify whether user column is displayed or not");
        WebElement user = driver.findElement(By.xpath("//div[text()='User']"));
        boolean userColumn = user.isEnabled();
        softAssert.assertTrue(userColumn);
        test = extent.createTest("step12","Verify whether event category column is displayed or not");
        WebElement eventCategory = driver.findElement(By.xpath("//div[text()='Event Category']"));
        boolean eventCategoryColumn = eventCategory.isEnabled();
        softAssert.assertTrue(eventCategoryColumn);
        test = extent.createTest("step13","Verify whether event column is displayed or not");
        WebElement events = driver.findElement(By.xpath("//div[text()='Event Category']"));
        boolean eventsColumn = events.isEnabled();
        softAssert.assertTrue(eventsColumn);
        test = extent.createTest("step14","Verify whether Access details column is displayed or not");
        WebElement accessDetails = driver.findElement(By.xpath("//div[text()='Access Details']"));
        boolean accessDetailsColumn = accessDetails.isEnabled();
        softAssert.assertTrue(accessDetailsColumn);

    }
    @Test(enabled = false)
    @Parameters({"testUser"})
    public void TC02(String testUser) throws IOException, InterruptedException {
        test = extent.createTest("TC02 : Verify whether user access details are generating in audit logs","Verify whether user access details are generating in audit logs").info("user Detail " +testUser);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(25));
        WebElement auditLogs = driver.findElement(By.xpath("//h6[text()='Audit Logs']"));
        auditLogs.click();
        //
        test = extent.createTest("Step01","Verify whether date and day is displayed or not");
        WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[2]"));
        boolean dateStatus = date.isDisplayed();
        Assert.assertTrue(dateStatus);
        String ss1 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss1);
        //
        test = extent.createTest("Step02","Verify whether time is displayed or not");
        WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[2]"));
        boolean timeStatus = time.isDisplayed();
        Assert.assertTrue(timeStatus);
        String ss2 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss2);
        //
        test = extent.createTest("Step03","Verify whether username is displayed or not");
        WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[2]"));
        if(user.getText().contains(testUser)){
            String username = user.getText();
            Assert.assertEquals(username,testUser);
        }
        String ss3 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss3);
           WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[2]")));

           //
           test = extent.createTest("Step04","Verify whether event category is displayed as user access" );
   WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[2]"));
   String eventString = event.getText();
   Assert.assertEquals(eventString,"User Access");
        String ss4 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(ss4);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(1000,0)");

       //
        test = extent.createTest("Step05","Verify whether event description is displayed as user logged in" );
        WebElement eventDescription = driver.findElement(By.xpath("(//tbody//tr//td[4])[2]"));
        String expectedDescription = eventDescription.getText();
        if(expectedDescription.contains("logged in") && expectedDescription.contains (testUser)) {
            String ss5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss5);

            //
            Thread.sleep(5000);
            test = extent.createTest("Step06", "Verify whether access details is displayed or not");
            WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[2]"));
            String accessDescription = accessDetails.getText();
            softAssert.assertEquals(accessDetails, "Desktop, Edge 126.0.0.0");
            String ss6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(ss6);

        }
}
 @Test
 @Parameters({"testUser","testEmail"})
public void TC03(String testUser,String testEmail) throws InterruptedException {
        test = extent.createTest("TC03 : Verify whether user creation logs are generating in audit log","Verify whether user creation logs are generating in audit log").info("user Detail " +testUser);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
     test = extent.createTest("Step01 : Verify whether user is able to create a user");
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/users");
        Thread.sleep(4000);
        WebElement addNewUser = driver.findElement(By.xpath("//button[text()='Add New User']"));
        addNewUser.click();
       List <WebElement> userDetails = driver.findElements(By.xpath("//input[@type='text']"));
       String [] valuesToSend = {"","Test","user","+913456787665","","Testing"};
       for(int i =1;i<=userDetails.size() &&i<valuesToSend.length;i++) {
             userDetails.get(i).sendKeys(valuesToSend[i]);
       }
       WebElement email = driver.findElement(By.id("email"));
       email.sendKeys(testEmail);
      WebElement create = driver.findElement(By.xpath("//button[@type = 'submit']"));
      create.click();
           String ss1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
           test.addScreenCaptureFromBase64String(ss1);
     //


           test = extent.createTest("Step02","");
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

     test = extent.createTest("Step03","Verify whether date and day is displayed or not");
     WebElement date = driver.findElement(By.xpath("(//div[@class='style_date__UsvaQ'])[1]"));
     boolean dateStatus = date.isDisplayed();
     Assert.assertTrue(dateStatus);
     String ss3 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
     test.addScreenCaptureFromBase64String(ss3);
     //
     test = extent.createTest("Step04","Verify whether time is displayed or not");
     WebElement time = driver.findElement(By.xpath("(//div[@class='style_dateTime__I2bxo'])[1]"));
     boolean timeStatus = time.isDisplayed();
     Assert.assertTrue(timeStatus);
     String ss4 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
     test.addScreenCaptureFromBase64String(ss4);
     //
     test = extent.createTest("Step05","Verify whether username is displayed or not");
     WebElement user = driver.findElement(By.xpath("(//tbody//tr//td[2]//div)[1]"));
     if(user.getText().contains(testUser)){
         String username = user.getText();
         Assert.assertEquals(username,testUser);
     }
     String ss5 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
     test.addScreenCaptureFromBase64String(ss5);
     WebElement eventWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tbody//tr//td[3]//div)[1]")));

     //
     test = extent.createTest("Step06","Verify whether event category is displayed as user administration" );
     WebElement event = driver.findElement(By.xpath("(//tbody//tr//td[3]//div)[1]"));
     String eventString = event.getText();
     Assert.assertEquals(eventString,"User Administration");
     String ss6 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
     test.addScreenCaptureFromBase64String(ss6);

        //
     test = extent.createTest("Step07","Verify whether event description is displayed as user created" );
     WebElement eventDescription = driver.findElement(By.xpath("(//tbody//tr//td[4])[1]"));
     String expectedDescription = eventDescription.getText();
     if(expectedDescription.contains("created") && expectedDescription.contains (testEmail) && expectedDescription.contains("user")) {
         String ss7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
         test.addScreenCaptureFromBase64String(ss7);

         //
         Thread.sleep(5000);
         test = extent.createTest("Step08", "Verify whether access details is displayed or not");
         WebElement accessDetails = driver.findElement(By.xpath("(//tbody//tr//td[5])[1]"));
         String accessDescription = accessDetails.getText();
         softAssert.assertEquals(accessDetails, "Desktop, Edge 126.0.0.0");
         String ss8 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
         test.addScreenCaptureFromBase64String(ss8);
         //rwae
       }
 }}


