package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import  org.apache.commons.io.FileUtils;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Mussafir {
 WebDriver driver;
    ExtentSparkReporter report  = new ExtentSparkReporter(new File("./TestReport14.html"));

    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void status1(){
        System.out.println("Testing started for TC01,TC02");
    }
    @AfterClass
    public void status2(){
        System.out.println("Testing completed for TC01,TC02");
    }
    @BeforeTest
    public void report1(){
        extent.attachReporter(report);
    }
    @AfterTest
    public void report2() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport14.html").toURI());

    }
    @BeforeMethod
    public void setup(){
        EdgeOptions opt = new EdgeOptions();
        opt.addArguments("--guest");
        driver = new EdgeDriver(opt);
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.musafir.com/");
       System.out.println(driver.getTitle());

    }
    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE){
            test.log(Status.FAIL,"Test Case Failed"+result.getName());
            test.log(Status.FAIL,"Test Case Failed" +result.getThrowable());
            File sc = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            Files.copy(sc,new File("C:\\Users\\betty\\Desktop\\ss1.jpg"));
            test.addScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss1.jpg");
        }
        else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP,"Test Case Skipped"+result.getName());

        }else
            test.log(Status.PASS,"Test Case Passed" +result.getName());
        extent.flush();
        //driver.quit();
    }
    @Test
    public void TC01() throws IOException, InterruptedException {
        test = extent.createTest("TC01","Test Case for musafir");
        WebElement logo = driver.findElement(By.xpath("//a[text()='musafir']"));
        String actual = logo.getText();
        String exp = "musafr";
        softAssert.assertEquals(exp,actual,"musafir");
        test.info("musafr   " +actual);
        WebElement oneWay = driver.findElement(By.xpath("//label[@for=\"trip_one\"]"));
        oneWay.click();
        WebElement from = driver.findElement(By.xpath("(//input[@placeholder='From'])[1]"));
        from.sendKeys("Kochi, India (COK)");
        WebElement destination = driver.findElement(By.xpath("//input[@name='Destination']"));
        destination.sendKeys("Mumbai, India (BOM)");
        WebElement startDate = driver.findElement(By.xpath("//input[@name=\"StartDate\"]"));
        startDate.click();
        WebElement forward = driver.findElement(By.cssSelector("a[class='forward']"));
        forward.click();
        WebElement date = driver.findElement(By.xpath("//li[text()='2']"));
        date.click();
        WebElement adult = driver.findElement(By.cssSelector("select[name='AdultsFlight']"));
       Select select = new Select(adult);
       select.selectByIndex(3);
       WebElement findFights = driver.findElement(By.xpath("(//a[contains(@class,\"optional submit button\")])[1]"));
       findFights.click();
       Thread.sleep(6000);
        Actions act = new Actions(driver);

       List <WebElement> logos = driver.findElements(By.xpath("//div[@class='FlightCardMobileComponent__ut-flightcard-mobile__header__name___djWGI']//span//span"));

       for(WebElement each : logos){
  boolean exp1 = each.isDisplayed();
   Assert.assertTrue(exp1);
   System.out.println(each.getText());
   System.out.println("s");
 }
        }



    }



