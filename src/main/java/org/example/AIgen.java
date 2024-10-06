package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import lombok.SneakyThrows;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class AIgen {
    WebDriver driver;
    ExtentSparkReporter report = new ExtentSparkReporter(new File("./TestReport13.html"));
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    @BeforeClass
    public void status1(){
        System.out.println("Testing started for GENAI");
    }
    @AfterClass
    public void status2(){
        System.out.println("Testing completed for GENAI");
    }
    @BeforeTest
    public void report1(){
        extent.attachReporter(report);
    }
    @AfterTest
    public void finalReport() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport13.html").toURI());
    }
    @BeforeMethod
    public void setup(){
        EdgeOptions opt = new EdgeOptions();
        opt.addArguments("--guest");
        driver = new EdgeDriver(opt);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(13));
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
            Boolean pageloaded = adminPage.isEnabled();
            Assert.assertTrue(pageloaded);

        }
    }
    @AfterMethod
    public void tearDown(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE){
            test.log(Status.FAIL ,"Test Case Failed" +result.getName());
            test.log(Status.FAIL, "Test Case Failed" +result.getThrowable());
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);
        }
        else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP ," Test Case Skipped" +result.getName());
            test.log(Status.SKIP ," Test Case Skipped" +result.getThrowable());
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);
        }
        else
            test.log(Status.PASS,"Test Case Passed" +result.getName());
        extent.flush();
    }
    @Test
    public void TC01() throws InterruptedException {
        test = extent.createTest("TC01","Gen AI").info("userDetail");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(55));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(55));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/instruct-experience");
        Thread.sleep(4000);
        WebElement addnewWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add New']")));
        WebElement addNew = driver.findElement(By.xpath("//button[text()='Add New']"));
        addNew.click();
        String parentWindow = driver.getWindowHandle();
        Set<String>childWindow = driver.getWindowHandles();
        for(String temp : childWindow){
            if(!temp.equals(parentWindow)){
                driver.switchTo().window(temp);
            }
        }
        WebElement expName = driver.findElement(By.id("experienceName"));
        expName.sendKeys("Auto Test1");
        WebElement discription = driver.findElement(By.id("experienceDescription"));
        discription.sendKeys("Testing");
        WebElement startBulding = driver.findElement(By.xpath("//button[text()='start building']"));
        startBulding.click();
        Thread.sleep(4000);
        WebElement closeIcon = driver.findElement(By.cssSelector("span[class^=OnboardingTipsDialog]"));
        closeIcon.click();
        WebElement imageElement = driver.findElement(By.xpath("(//div[@class='ImageElement_blankImgContainer__jIPVl'])[1]"));
    imageElement.click();
    WebElement Ai = driver.findElement(By.xpath("//span[text()='AI GENERATE']"));
    Ai.click();
    WebElement closeicon2 = driver.findElement(By.cssSelector("svg[width=\"32\"]"));
    closeicon2.click();
        WebElement Ai2 = driver.findElement(By.xpath("//span[text()='AI GENERATE']"));
    Ai2.click();
        WebElement textarea = driver.findElement(By.cssSelector("textarea[class^='AIGenerationDialog']"));
        textarea.sendKeys("Flowers");
        WebElement generate = driver.findElement(By.xpath("(//button[contains(@class,'MuiButtonBase-root')])[5]"));
generate.click();

WebElement imageWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//img[contains(@src,'data:image')])[1]")));
imageWait.click();
WebElement insert = driver.findElement(By.xpath("(//div[contains(@class,'AIGenerationDialog')])[14]"));
insert.click();
    }


}


