package org.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;
import freemarker.core._ArrayEnumeration;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.FileAssert;
import org.testng.ITestResult;
import org.testng.annotations.*;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static org.openqa.selenium.devtools.v120.page.Page.captureScreenshot;

public class UsageInformation {
    WebDriver driver;
    ExtentSparkReporter report = new ExtentSparkReporter("./TestReport4.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    public  String user;
    public  String userPicker;

    @BeforeClass
    public  void status1(){
        System.out.println("Started testing: TC01,TC02");
    }
    @AfterClass
    public  void status2(){
        System.out.println("Testing completed: TC01,TC02");
    }
    @BeforeMethod
    @Parameters({"userPicker"})
    public void setup(String userPicker) throws InterruptedException {
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
           test.log(Status.FAIL,"Test case Failed " +result.getName()) ;
           test.log(Status.FAIL,"Test case Failed " +result.getThrowable());
           String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
           test.addScreenCaptureFromBase64String(b64model);
        }
        else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP,"Test Case skipped " +result.getName());
            test.log(Status.SKIP, "Test Case Skipped" +result.getThrowable());
        }
        else{
            test.log(Status.PASS,"Test Case Passed " +result.getName());
        }
        extent.flush();
        driver.quit();}

    @BeforeTest
public void prereq1(){
        extent.attachReporter(report);
}
    @AfterTest
    public void prereq2() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport4.html").toURI());
    }
@Test
    public void TC01() throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
       //navigate to usage information
    test =  extent.createTest("TC01","Testing of module usage information").info(userPicker);
   WebElement usageinfoWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[text()='Usage Information']")));
    usageinfoWait.click();
    Thread.sleep(4000);
    WebElement usageinformation = driver.findElement(By.xpath("(//strong[text()='Instruct Sessions Available']/following::label[@class='value col-form-label'])[1]"));
    String before = usageinformation.getAttribute("title");
    boolean expected = usageinformation.isDisplayed();
    Assert.assertTrue(expected);
    File ss = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    Files.copy(ss,new File("C:\\Users\\betty\\Desktop\\ss\\ usageinfo.jpg"));
    test.addScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss\\ usageinfo.jpg");
}
@Test
    public void TC02() throws InterruptedException, IOException {
    test = extent.createTest("TCO2", "Testing of module usage information ").info("User Detail:" +userPicker);
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    WebElement usageinfoWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[text()='Usage Information']")));
    usageinfoWait.click();
    Thread.sleep(4000);
    WebElement usageinformation = driver.findElement(By.xpath("(//strong[text()='Instruct Sessions Available']/following::label[@class='value col-form-label'])[1]"));
    String before = usageinformation.getAttribute("title");
    boolean expected = usageinformation.isDisplayed();
    Assert.assertTrue(expected);
    //navigate to experience builder
    Thread.sleep(3000);
    driver.navigate().to("https://carear-development.web.app/#/admin/instruct-experience");
    WebElement experience = driver.findElement(By.linkText("https://careardevlp.page.link/R85U"));
    experience.click();
    String parentWindow = driver.getWindowHandle();
    Set<String> childWindows = driver.getWindowHandles();
    for (String temp : childWindows) {
        if (!temp.equals(parentWindow)) {
            driver.switchTo().window(temp);
            System.out.println(driver.getCurrentUrl());
            Thread.sleep(7000);
            WebElement primaryButtonWait = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class^='MuiButtonBase']")));
            WebElement primaryButton = driver.findElement(By.cssSelector("button[class^='MuiButtonBase']"));
            primaryButton.click();
            WebElement welcomeInstruct = driver.findElement(By.xpath("//strong[text()='Welcome to Instruct']"));
            boolean contentPage = welcomeInstruct.isDisplayed();
            Assert.assertTrue(contentPage);
        }

    }
    driver.switchTo().window(parentWindow);
    driver.navigate().to("https://carear-development.web.app/#/admin/company");
    WebElement usageinfoWait2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h6[text()='Usage Information']")));
    usageinfoWait2.click();
    Thread.sleep(4000);
    WebElement usageinformation2 = driver.findElement(By.xpath("(//strong[text()='Instruct Sessions Available']/following::label[@class='value col-form-label'])[1]"));
    String after = usageinformation2.getAttribute("title");
    Assert.assertEquals(before, after);
    System.out.println("Consumed value is same");

        String base64code = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
//Files.copy(ss,new File("C:\\Users\\betty\\Desktop\\ss\\  usageinfo2"));


        test.addScreenCaptureFromBase64String(base64code, "usageinfo2");
    }
}


