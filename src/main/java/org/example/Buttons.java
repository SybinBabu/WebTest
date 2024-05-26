package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Buttons {
    WebDriver driver;

    ExtentSparkReporter report = new ExtentSparkReporter("./TestReport10.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    @BeforeClass
    public void status1(){
        System.out.println("Testing started TC01,TC02,TC03");
    }
    @AfterClass
    public void status2(){
        System.out.println("Testing started TC01,TC02,TC03");
    }
    @BeforeTest
    public void report(){
        extent.attachReporter(report);
    }
    @AfterTest
    public void finalReport() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport10.html").toURI());
    }
    @BeforeMethod
    public void setup(){
        EdgeOptions opt = new EdgeOptions();
        opt.addArguments("--guest");
        driver = new EdgeDriver(opt);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(13));
        driver.get("https://carear-development.web.app/#/admin/login");
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
            boolean pageloaded = adminPage.isEnabled();
            Assert.assertTrue(pageloaded);

        }}
    @AfterMethod
    public void tearDown(ITestResult result){
        if(result.getStatus()== ITestResult.FAILURE){
            test.log(Status.FAIL,"Test Case Failed" +result.getName());
            test.log(Status.FAIL,"Test Case Failed" +result.getThrowable());
            String base64 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(base64);
        }
        else if(result.getStatus()== ITestResult.SKIP){
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
    public void TC01() throws InterruptedException, AWTException {
        test = extent.createTest("TC01","Test case to verify user group functionality is working fine or not")
                .info("user Detail");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-development.web.app/#/admin/users");
        Thread.sleep(6000);
        WebElement userGroup = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='User Groups']")));
        userGroup.click();
        WebElement IMPORT = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[text()='Import'])[2]")));
IMPORT.click();
Actions act = new Actions(driver);
WebElement upload = driver.findElement(By.xpath("//span[text()='upload a file']"));
act.moveToElement(upload).click().perform();
Thread.sleep(4000);
StringSelection str = new StringSelection("C:\\Users\\betty\\Desktop\\Test\\userGroups\\Template_Import_Groups_US.csv");
Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str,null);
Robot rb = new Robot();
rb.keyPress(KeyEvent.VK_CONTROL);
rb.keyPress(KeyEvent.VK_V);
rb.keyRelease(KeyEvent.VK_CONTROL);
rb.keyRelease(KeyEvent.VK_V);
rb.keyPress(KeyEvent.VK_ENTER);
rb.keyRelease(KeyEvent.VK_ENTER);
WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Groups has been imported successfully']")));
boolean exp1 = popup.isDisplayed();
Assert.assertTrue(exp1);
WebElement list = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='User Groups']")));
List<WebElement> allGroups = driver.findElements(By.xpath("//p[@class='m-0']"));
        String exp2 = "ABC";
for(WebElement each : allGroups){
    System.out.println(each.getText());
    if(each.getText().contains(exp2)){
        Assert.assertEquals(exp2, "ABC");
    }
}
        String b64model = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
    }
    @Test
    public void TC02() throws InterruptedException, AWTException {

    test = extent.createTest("TC02","Test case to verify import users functionality is working fine or not")
            .info("user Detail");
    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-development.web.app/#/admin/users");
        Thread.sleep(6000);
    WebElement importBulk = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Import']")));
        importBulk.click();
        Actions act = new Actions(driver);
        WebElement upload = driver.findElement(By.xpath("//span[text()='upload a file']"));
        act.moveToElement(upload).click().perform();
        Thread.sleep(4000);
        StringSelection str = new StringSelection("C:\\Users\\betty\\Desktop\\Test\\Importusers\\Template_Import_Users_US.csv");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str,null);
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='User Import Completed Successfully']")));
boolean exp1 = popup.isDisplayed();
Assert.assertTrue(exp1);
WebElement toast = driver.findElement(By.xpath("//span[text()='Import successful']"));
        boolean exp2 = toast.isDisplayed();
        Assert.assertTrue(exp2);
        WebElement successNumber = driver.findElement(By.xpath("//span[text()='Import successful']/following::span[2]"));
String exp3 = successNumber.getText();
Assert.assertEquals(exp3,"5");
        String b64model = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
    }
    @Test
    public void TC03() throws InterruptedException, AWTException {
        test = extent.createTest("TC03","Test case to verify duplicate user group throwing error or not")
                .info("user Detail");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-development.web.app/#/admin/users");
        Thread.sleep(6000);
        WebElement userGroup = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='User Groups']")));
        userGroup.click();
        WebElement IMPORT = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[text()='Import'])[2]")));
        IMPORT.click();
        Actions act = new Actions(driver);
        WebElement upload = driver.findElement(By.xpath("//span[text()='upload a file']"));
        act.moveToElement(upload).click().perform();
        Thread.sleep(4000);
        StringSelection str = new StringSelection("C:\\Users\\betty\\Desktop\\Test\\userGroups\\Template_Import_Groups_US.csv");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str,null);
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Group name already in use')]")));
        boolean exp1 = popup.isDisplayed();
        Assert.assertTrue(exp1);
        String b64model = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
}
    @Test
    public void TC04() throws InterruptedException, AWTException {

        test = extent.createTest("TC04","Test case to verify import users duplicate values throw error or not")
                .info("user Detail");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-development.web.app/#/admin/users");
        Thread.sleep(6000);
        WebElement importBulk = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Import']")));
        importBulk.click();
        Actions act = new Actions(driver);
        WebElement upload = driver.findElement(By.xpath("//span[text()='upload a file']"));
        act.moveToElement(upload).click().perform();
        Thread.sleep(4000);
        StringSelection str = new StringSelection("C:\\Users\\betty\\Desktop\\Test\\Importusers\\Template_Import_Users_US.csv");
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str,null);
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='User Import Failed']")));
        boolean exp1 = popup.isDisplayed();
        Assert.assertTrue(exp1);
        WebElement toast = driver.findElement(By.xpath("//span[text()='SignUp failed']"));
        boolean exp2 = toast.isDisplayed();
        Assert.assertTrue(exp2);
        String b64model = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);



}}



