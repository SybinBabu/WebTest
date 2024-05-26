package org.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.example.EditProfile;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class AccountInformation {

    WebDriver driver;
    ExtentSparkReporter report = new ExtentSparkReporter("./TestReport1.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    String user;
    @BeforeClass
    public void status1(){
        System.out.println("Account information module started");
    }
    @AfterClass
    public void status2(){
        System.out.println("Account information module completed");

}
@BeforeMethod
@Parameters({"userPicker"})
    public void setup(@Optional("sybin.xerox+12@gmail.com") String userPicker){
    EdgeOptions opt = new EdgeOptions();
    opt.addArguments("--guest");
    driver = new EdgeDriver(opt);
    driver.manage().window().maximize();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(13));
    driver.get("https://carear-development.web.app/#/admin/login");
    WebElement username = driver.findElement(By.id("userNameInput"));
    this.user = "userPicker";
    username.sendKeys(userPicker);
    WebElement next = driver.findElement(By.id("submitInput"));
    next.click();
    try{
        WebElement verificationWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='We need to verify your identity']")));
        List<WebElement> vcode = driver.findElements(By.xpath("//input[@type='tel']"));
        Thread.sleep(4000);
        for(WebElement each : vcode){
            each.sendKeys("1");
        }
        WebElement next2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type=\"submit\"]")));
        next2.click();
        WebElement password = driver.findElement(By.id("passwordInput"));
        password.sendKeys("Xerox@2023");
        WebElement signin = driver.findElement(By.id("submitInput"));
        signin.click();

    }
    catch(Exception e){
        WebElement password = driver.findElement(By.id("passwordInput"));
        password.sendKeys("Xerox@2023");
        WebElement signin = driver.findElement(By.id("submitInput"));
        signin.click();
    }
    finally {
        WebElement adminPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Administration']")));
        Boolean pageloaded = adminPage.isEnabled();
        Assert.assertTrue(pageloaded);

    }}
    @AfterMethod
    public void tearDown(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE){
            test.log(Status.FAIL,"Test Case Failed" +result.getName());
            test.log(Status.FAIL, "Test Case Failed " +result.getThrowable());
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);
        }
        else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP,"Test Case Skipped " +result.getName());
            test.log(Status.SKIP,"Test case skipped " +result.getThrowable());
        }
        else{
            test.log(Status.PASS,"Test Case Passed " +result.getName());
        }
        extent.flush();
        driver.quit();
    }
    @BeforeTest
    public void reporting(){
        extent.attachReporter(report);
    }
    @Test
    public void TC01(){
        test = extent.createTest("TC01","Tested account info module").info("user Detail" +user);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //navigate to account information
        WebElement accountinfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Account Information']")));
         boolean exp1 = accountinfo.isDisplayed();
         WebElement usageinfo = driver.findElement(By.xpath("//h6[text()='Usage Information']"));
         usageinfo.isDisplayed();
        WebElement siganalmedia = driver.findElement(By.xpath("//h6[text()='Signaling and Media Controls']"));
        siganalmedia.isDisplayed();
        WebElement joincontrol = driver.findElement(By.xpath("//h6[text()='Join Controls']"));
        joincontrol.isDisplayed();
        WebElement sso = driver.findElement(By.xpath("//h6[text()='SSO (SAML 2.0)']"));
        sso.isDisplayed();
        WebElement companylogo = driver.findElement(By.xpath("//h5[text()='Company Logo']"));
        companylogo.isDisplayed();
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
    }
    @Test
    public void TC02(){
        test = extent.createTest("TC02","Testing of account info module").info("userDetail" +user);
        WebElement companyName = driver.findElement(By.xpath("//strong[text()='Company Name']"));
        companyName.isDisplayed();
        WebElement joinDate = driver.findElement(By.xpath("//strong[text()='Join Date']"));
        joinDate.isDisplayed();
        WebElement expirationDate = driver.findElement(By.xpath("//strong[text()='Expiration Date']"));
        expirationDate.isDisplayed();
        WebElement licensedSubscriber = driver.findElement(By.xpath("//strong[text()='Licensed Subscribers']"));
        licensedSubscriber.isDisplayed();
        WebElement createdBy = driver.findElement(By.xpath("//strong[text()='Created By']"));
        createdBy.isDisplayed();
        WebElement customerStatus = driver.findElement(By.xpath("//strong[text()='Customer Status']"));
        customerStatus.isDisplayed();
        WebElement tenantPhysicalAddress = driver.findElement(By.xpath("//strong[text()='Tenant Physical Address']"));
        tenantPhysicalAddress.isDisplayed();
        WebElement resellerOfRecord = driver.findElement(By.xpath("//strong[text()='Reseller Of Record']"));
        resellerOfRecord.isDisplayed();
        WebElement customerSuccessManager = driver.findElement(By.xpath("//strong[text()='Customer Success Manager']"));
        customerSuccessManager.isDisplayed();
        WebElement primaryTenantAdministrator = driver.findElement(By.xpath("//strong[text()='Primary Tenant Administrator']"));
        primaryTenantAdministrator.isDisplayed();
        WebElement contractEffectiveDate = driver.findElement(By.xpath("//strong[text()='Contract Effective Date']"));
        contractEffectiveDate.isDisplayed();
        WebElement plan = driver.findElement(By.xpath("//strong[text()='Plan']"));
        plan.isDisplayed();
        WebElement servicePackage = driver.findElement(By.xpath("//strong[text()='Service Package']"));
        servicePackage.isDisplayed();
        WebElement usageType = driver.findElement(By.xpath("//strong[text()='Usage Type']"));
        usageType.isDisplayed();
        WebElement subscriptionTerm  = driver.findElement(By.xpath("//strong[text()='Subscription Term']"));
        subscriptionTerm .isDisplayed();
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.info(MediaEntityBuilder.createScreenCaptureFromBase64String(b64model).build());

    }
    @AfterTest
    public void viewReport() throws IOException {
    Desktop.getDesktop().browse(new File("TestReport1.html").toURI());
}

    public static class Main {
        public void testing(){



        }

    }

    static class test extends EditProfile {

    }

    public static class SignalingAndMedia {

        ExtentSparkReporter report = new ExtentSparkReporter("./TestReport9.html");
        ExtentReports extent = new ExtentReports();
        ExtentTest test;
        WebDriver driver;
        String userPicker;
        String user;

    @BeforeClass
    public  void status1(){
        System.out.println("Testing started for TC01,TC02,TC03,TC04");
    }
        @AfterClass
        public  void status2(){
            System.out.println("Testing completed for TC01,TC02,TC03,TC04");
        }
    @BeforeTest
        public void report(){
        extent.attachReporter(report);
    }
    @AfterTest
        public void finalReport() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport9.html").toURI());
    }
    @BeforeMethod
    @Parameters({"userPicker"})
        public void Setup(String userPicker){
        EdgeOptions opt = new EdgeOptions();
        opt.addArguments("--guest");
        driver = new EdgeDriver(opt);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(13));
        driver.get("https://carear-development.web.app/#/admin/login");
        WebElement username = driver.findElement(By.id("userNameInput"));
        this.user ="userPicker";
        username.sendKeys(userPicker);
        WebElement next = driver.findElement(By.id("submitInput"));
        next.click();
        try{
            WebElement verificationWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='We need to verify your identity']")));
            List<WebElement> vcode = driver.findElements(By.xpath("//input[@type='tel']"));
            Thread.sleep(4000);
            for(WebElement each : vcode){
                each.sendKeys("1");
            }
            WebElement next2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type=\"submit\"]")));
            next2.click();
            WebElement password = driver.findElement(By.id("passwordInput"));
            password.sendKeys("Xerox@2023");
            WebElement signin = driver.findElement(By.id("submitInput"));
            signin.click();

        }
        catch(Exception e){
            WebElement password = driver.findElement(By.id("passwordInput"));
            password.sendKeys("Xerox@2023");
            WebElement signin = driver.findElement(By.id("submitInput"));
            signin.click();
        }
        finally {
            WebElement adminPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Administration']")));
            Boolean pageloaded = adminPage.isEnabled();
            Assert.assertTrue(pageloaded);

        }
    }
    @AfterMethod
        public void tearDown(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE){
            test.log(Status.FAIL,"Test Case Failed" +result.getName());
            test.log(Status.FAIL,"Test Case Failed" +result.getThrowable());
    String b64model =((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
    test.addScreenCaptureFromBase64String(b64model);
        }
        else if (result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP,"Test Case Skipped" +result.getName());
            test.log(Status.SKIP,"Test Case Skipped" +result.getThrowable());
        }
        else{
            test.log(Status.PASS,"Test Case Passed" +result.getName());
        }
        extent.flush();
        driver.quit();
    }
    @Test
    @Parameters({"userPicker"})
        public  void TC01(String userPicker){
        test = extent.createTest("TCO1","Testing of signaling and media").info("user detail:" +userPicker);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        WebElement signal = driver.findElement(By.xpath("(//div[@id='tenantAdminHome']//div[3])[1]"));
        signal.click();
        WebElement dropdown1Wait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//select[contains(@class,\"form-control\")])[1]")));
       dropdown1Wait.click();
       WebElement global = driver.findElement(By.xpath("(//option[text()='Global'])[1]"));
       boolean exp1 = global.isEnabled();
       Assert.assertTrue(exp1);
        WebElement asia = driver.findElement(By.xpath("(//option[text()='Asia'])[1]"));
        boolean exp2 = asia.isEnabled();
        Assert.assertTrue(exp2);
        WebElement europe = driver.findElement(By.xpath("(//option[text()='Europe'])[1]"));
        boolean exp3 = europe.isEnabled();
        Assert.assertTrue(exp3);
        WebElement india = driver.findElement(By.xpath("(//option[text()='India'])[1]"));
        boolean exp4 = india.isEnabled();
        Assert.assertTrue(exp4);
        WebElement japan = driver.findElement(By.xpath("(//option[text()='Japan'])[1]"));
        boolean exp5 = japan.isEnabled();
        Assert.assertTrue(exp5);
        WebElement northAmerica = driver.findElement(By.xpath("(//option[text()='North America'])[1]"));
        boolean exp6 = northAmerica.isEnabled();
        Assert.assertTrue(exp6);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);

    }
        @Test
        @Parameters({"userPicker"})
        public  void TC02(String userPicker){
            test = extent.createTest("TCO2","Testing of signaling and media").info("user detail:" +userPicker);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
            WebElement signal = driver.findElement(By.xpath("(//div[@id='tenantAdminHome']//div[3])[1]"));
            signal.click();
            WebElement dropdown1Wait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//select[contains(@class,\"form-control\")])[1]")));
            dropdown1Wait.click();
            WebElement global = driver.findElement(By.xpath("(//option[text()='Global'])[2]"));
            boolean exp1 = global.isEnabled();
            Assert.assertTrue(exp1);
            WebElement asia = driver.findElement(By.xpath("(//option[text()='Asia'])[2]"));
            boolean exp2 = asia.isEnabled();
            Assert.assertTrue(exp2);
            WebElement europe = driver.findElement(By.xpath("(//option[text()='Europe'])[2]"));
            boolean exp3 = europe.isEnabled();
            Assert.assertTrue(exp3);
            WebElement india = driver.findElement(By.xpath("(//option[text()='India'])[2]"));
            boolean exp4 = india.isEnabled();
            Assert.assertTrue(exp4);
            WebElement japan = driver.findElement(By.xpath("(//option[text()='Japan'])[2]"));
            boolean exp5 = japan.isEnabled();
            Assert.assertTrue(exp5);
            WebElement northAmerica = driver.findElement(By.xpath("(//option[text()='North America'])[2]"));
            boolean exp6 = northAmerica.isEnabled();
            Assert.assertTrue(exp6);
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);

        }



    }
}



