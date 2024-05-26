package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class ImportExp {
    WebDriver driver;
    ExtentSparkReporter reporter = new ExtentSparkReporter("./TestReport15.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    SoftAssert softAssert = new SoftAssert();
    @BeforeClass
    public  void status1(){
        System.out.println("Testing started");
    }
    @AfterClass
    public  void status2(){
        System.out.println("Testing completed");
    }
    @BeforeTest
    public void report(){
        extent.attachReporter(reporter);
    }
    @AfterTest
    public void finalReport() throws IOException {
        Desktop.getDesktop().browse(new File ("TestReport15.html").toURI());
    }
    @BeforeMethod
    public void setup(){
        EdgeOptions opt = new EdgeOptions();
        opt.addArguments("--guest");
        driver = new EdgeDriver(opt);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        driver.get("https://carear-us-qa.web.app/#/admin/login");
        WebElement username = driver.findElement(By.id("userNameInput"));

        username.sendKeys("sybin.xerox+18@gmail.com");
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
            boolean pageloaded = adminPage.isEnabled();
            Assert.assertTrue(pageloaded);

        }
    }
    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
      if(result.getStatus() == ITestResult.FAILURE){
          test.log(Status.FAIL,"Test Case Failed" +result.getName());
          test.log(Status.FAIL,"Test Case Failed" +result.getThrowable());
          File ss = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
          Files.copy(ss,new File ("C:\\Users\\betty\\Desktop\\Test\\ss\\ sc.jpg"));
          test.addScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\Test\\ss\\ sc.jpg");
      }
      else if(result.getStatus() == ITestResult.SKIP){
          test.log(Status.SKIP,"Test case skipped"+ result.getName());
          test.log(Status.SKIP,"Test case skipped"+ result.getThrowable());
      }
      else{
          test.log(Status.PASS,"Test case passed"+ result.getName());
      }
      extent.flush();
      //driver.quit();
    }
    @Test
    public void TCO1() throws InterruptedException, AWTException {
        test = extent.createTest("TCO1","Import experience").info("user detail:");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/instruct-experience");
        Thread.sleep(6000);
        WebElement addNew = driver.findElement(By.xpath("//button[text()='Add New']"));
        addNew.click();
        String parentWindow = driver.getWindowHandle();
         Set<String> childWindow = driver.getWindowHandles();
         for(String temp : childWindow){
             if(!temp.equals(parentWindow)){
                 driver.switchTo().window(temp);
             }
         }
         WebElement expName = driver.findElement(By.id("experienceName"));
         expName.sendKeys("ImportTest");
        WebElement expDescription = driver.findElement(By.id("experienceDescription"));
        expDescription.sendKeys("Testing");
        Thread.sleep(3000);
        WebElement startbuilding = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='start building']")));
        startbuilding.click();
        Thread.sleep(10000);
        WebElement closeIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'OnboardingTipsDialog')]")));
        closeIcon.click();
        WebElement editor = driver.findElement(By.id("editor-menu-button"));
        editor.click();
        WebElement importButton = driver.findElement(By.xpath("//span[text()='Import']"));
        importButton.click();
        Thread.sleep(2000);
        WebElement importButton2 = driver.findElement(By.xpath("//span[text()='Import']"));
        Actions act = new Actions(driver);
        act.moveToElement(importButton2).perform();
        importButton2.click();
         Thread.sleep(3000);
        StringSelection str = new StringSelection("C:\\Users\\betty\\Desktop\\Test\\exp\\Form Builder Test.zip");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str,null);
         Robot rb = new Robot();
         rb.keyPress(KeyEvent.VK_CONTROL);
         rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(10000);
        WebElement publish = driver.findElement(By.xpath("//span[text()='PUBLISH']"));
        publish.click();
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Experience has been published successfully']")));
        String popupText = popup.getText();
        softAssert.assertEquals(popupText,"Experience has been published successfully");
        WebElement status = driver.findElement(By.xpath("//p[text()='Experience published']"));
        boolean exp1 = status.isDisplayed();
        WebElement shortlink = driver.findElement(By.xpath("//p[@class='PublishExperienceSuccessDialog_previewLink__vjelp']"));
              boolean exp2 = shortlink.isDisplayed();
              softAssert.assertTrue(exp2);

    }
}
