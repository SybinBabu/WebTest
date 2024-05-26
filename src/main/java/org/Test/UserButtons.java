package org.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.observer.entity.MediaEntity;
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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class UserButtons {

    WebDriver driver;
    ExtentSparkReporter report = new ExtentSparkReporter("./TestReport5.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    String user;
    @BeforeClass
    public void status1(){
        System.out.println("testing started for TC01,TC02,TC03,TC04,TC05");
    }
    @AfterClass
    public void status2(){
        System.out.println("testing completed for TC01,TC02,TC03,TC04,TC05");
    }
    @BeforeTest
    public void report(){
        extent.attachReporter(report);
    }
    @BeforeMethod
    @Parameters({"userPicker"})
    public void setup(String userPicker){
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
    public void tearDown(ITestResult result) throws IOException {
        if(result.getStatus()== ITestResult.FAILURE){
            test.log(Status.FAIL,"Test case Failed" +result.getName());
            test.log(Status.FAIL,"Test Case Failed" +result.getThrowable());
            File ss = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            Files.copy(ss,new File("C:\\Users\\betty\\Desktop\\ss\\ failedcase.jpg"));
            test.info(MediaEntityBuilder.createScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss\\ failedcase.jpg").build());
        }
        else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP,"Test Case Skipped" +result.getName());
            test.log(Status.SKIP,"Test Case Skipped" +result.getThrowable());
        }
        else{
            test.log(Status.PASS,"Test case Passed" +result.getName());
        }
        extent.flush();
        driver.quit();
    }
    @AfterTest
    public void finalReport() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport5.html").toURI());
    }
    @Test
    public void TC01() throws IOException {
//navigate to users
        test = extent.createTest("TC01","Tested for user module").info("user Detail:" +user);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.navigate().to("https://carear-development.web.app/#/admin/users");
        WebElement userGropsWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='User Groups']")));
                WebElement userGroups = driver.findElement(By.xpath("//button[text()='User Groups']"));
            boolean exp1 =    userGroups.isEnabled();
            Assert.assertTrue(exp1);
            File ss = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            Files.copy(ss,new File("C:\\Users\\betty\\Desktop\\ss\\usergroup.jpg"));
            test.info("Screenshot",MediaEntityBuilder.createScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss\\usergroup.jpg").build());
    }
    @Test
    public void TC02() throws IOException {
//navigate to users
        test = extent.createTest("TC02", "Tested for user module").info("user Detail:" +user);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.navigate().to("https://carear-development.web.app/#/admin/users");
        WebElement exportallWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Export All']")));
        WebElement exportAll = driver.findElement(By.xpath("//button[text()='Export All']"));
        boolean exp2 = exportAll.isEnabled();
        Assert.assertTrue(exp2);
        File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(ss, new File("C:\\Users\\betty\\Desktop\\ss\\exportall.jpg"));
        test.info("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss\\exportall.jpg").build());
    }
    @Test
    public void TC03() throws IOException {
//navigate to users
        test = extent.createTest("TC03", "Tested for user module").info("user Detail:" +user);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.navigate().to("https://carear-development.web.app/#/admin/users");
        WebElement importWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Import']")));
        WebElement importAll = driver.findElement(By.xpath("//button[text()='Import']"));
        boolean exp3 = importAll.isEnabled();
        Assert.assertTrue(exp3);
        File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(ss, new File("C:\\Users\\betty\\Desktop\\ss\\importall.jpg"));
        test.info("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss\\importall.jpg").build());
    }
    @Test
    public void TC04() throws IOException {
//navigate to users
        test = extent.createTest("TC04", "Tested for user module").info("user Detail:" +user);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.navigate().to("https://carear-development.web.app/#/admin/users");
        WebElement bulkEditWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Bulk Edit']")));
        WebElement bulkEdit = driver.findElement(By.xpath("//button[text()='Bulk Edit']"));
        boolean exp4 = bulkEdit.isEnabled();
        Assert.assertTrue(exp4);
        File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(ss, new File("C:\\Users\\betty\\Desktop\\ss\\bulkedit.jpg"));
        test.info("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss\\bulkedit.jpg").build());
    }
    @Test
    public void TC05() throws IOException {
//navigate to users
        test = extent.createTest("TC05", "Tested for user module").info("user Detail:" +user);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.navigate().to("https://carear-development.web.app/#/admin/users");
        WebElement newUserWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add New User']")));
        WebElement addNewUser = driver.findElement(By.xpath("//button[text()='Add New User']"));
        boolean exp5 = addNewUser.isEnabled();
        Assert.assertTrue(exp5);
        File ss = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Files.copy(ss, new File("C:\\Users\\betty\\Desktop\\ss\\addnewuser.jpg"));
        test.info("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss\\addnewuser.jpg").build());
    }





}

