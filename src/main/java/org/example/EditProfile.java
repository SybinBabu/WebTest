package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.observer.entity.MediaEntity;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
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

public class EditProfile {
    WebDriver driver;
    ExtentSparkReporter report = new ExtentSparkReporter("./TestReport7.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    String user;
    String userPicker;

    @BeforeClass
    public  void Status1(){
        System.out.println("Testing started for TC01.TC02,TC03,TC04");

    }
    @AfterClass
    public  void Status2(){
        System.out.println("Testing completed for TC01.TC02,TC03,TC04");

    }
    @BeforeTest
    public  void report(){
        extent.attachReporter(report);
    }
    @AfterTest
    public  void finalReport() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport7.html").toURI());
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
        driver.get("https://carear-us-qa.web.app/#/admin/login");
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
            password.sendKeys("Testing1");
            WebElement signin = driver.findElement(By.id("submitInput"));
            signin.click();

        }
        catch(Exception e){
            WebElement password = driver.findElement(By.id("passwordInput"));
            password.sendKeys("Testing1");
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
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.info(MediaEntityBuilder.createScreenCaptureFromBase64String(b64model).build());
        }
        else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP,"Test Case SKIPPED" +result.getName());
            test.log(Status.SKIP,"Test Case SKIPPED" +result.getThrowable());
        }
        else{
            test.log(Status.PASS,"Test Case Passed" +result.getName());
        }
        extent.flush();
        driver.quit();
    }
    @Test
    @Parameters({"primarynumber","userPicker"})
    public void TC01(String primarynumber, String userPicker){
        test = extent.createTest("TC01","Testing of editProfile").info("User Detail:" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement profileWait = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("img[class='mx-2']")));
        profileWait.click();
        WebElement editProfile = driver.findElement(By.xpath("//button[text()='Edit Profile']"));
        editProfile.click();
        WebElement primaryPhoneNumber = driver.findElement(By.cssSelector("input[placeholder='Primary Phone Number']"));
  primaryPhoneNumber.clear();
   primaryPhoneNumber.sendKeys(primarynumber);
   WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
   save.click();
   WebElement popupWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='User has been updated successfully']")));
   boolean exp1 = popupWait.isDisplayed();
   Assert.assertTrue(exp1);
   String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
   test.info(MediaEntityBuilder.createScreenCaptureFromBase64String(b64model).build());
    }
    @Test
    @Parameters({"invalidprimarynumber","userPicker"})
    public void TC02(String invalidprimarynumber,String userPicker){
        test = extent.createTest("TC02","Testing of editProfile").info("User Detail:"+userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement profileWait = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("img[class='mx-2']")));
        profileWait.click();
        WebElement editProfile = driver.findElement(By.xpath("//button[text()='Edit Profile']"));
        editProfile.click();
        WebElement primaryPhoneNumber = driver.findElement(By.cssSelector("input[placeholder='Primary Phone Number']"));
        primaryPhoneNumber.clear();
        primaryPhoneNumber.sendKeys(invalidprimarynumber);
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        save.click();
        WebElement popupWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Invalid phone number']")));
        boolean exp2 = popupWait.isDisplayed();
        Assert.assertTrue(exp2);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.info(MediaEntityBuilder.createScreenCaptureFromBase64String(b64model).build());
    }
    @Test
    @Parameters({"secondarynumber","userPicker"})
    public void TC03(String secondarynumber, String userPicker){
        test = extent.createTest("TC03","Testing of editProfile").info("User Detail:" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement profileWait = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("img[class='mx-2']")));
        profileWait.click();
        WebElement editProfile = driver.findElement(By.xpath("//button[text()='Edit Profile']"));
        editProfile.click();
        WebElement secondaryPhoneNumber = driver.findElement(By.cssSelector("input[placeholder='Secondary Phone Number']"));
        secondaryPhoneNumber.clear();
        secondaryPhoneNumber.sendKeys(secondarynumber);
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        save.click();
        WebElement popupWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='User has been updated successfully']")));
        boolean exp3 = popupWait.isDisplayed();
        Assert.assertTrue(exp3);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.info(MediaEntityBuilder.createScreenCaptureFromBase64String(b64model).build());
    }
    @Test
    @Parameters({"invalidsecondarynumber","userPicker"})
    public void TC04(String invalidsecondarynumber, String userPicker){
        test = extent.createTest("TC04","Testing of editProfile").info("User Detail:" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement profileWait = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("img[class='mx-2']")));
        profileWait.click();
        WebElement editProfile = driver.findElement(By.xpath("//button[text()='Edit Profile']"));
        editProfile.click();
        WebElement secondaryPhoneNumber = driver.findElement(By.cssSelector("input[placeholder='Secondary Phone Number']"));
        secondaryPhoneNumber.clear();
        secondaryPhoneNumber.sendKeys(invalidsecondarynumber);
        WebElement save = driver.findElement(By.xpath("//button[text()='Save']"));
        save.click();
        WebElement popupWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[text()='Invalid phone number']")));
        boolean exp4 = popupWait.isDisplayed();
        Assert.assertTrue(exp4);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.info(MediaEntityBuilder.createScreenCaptureFromBase64String(b64model).build());
    }
}
