package org.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.devtools.v121.media.Media;
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

public class Usage {
    WebDriver driver;
    ExtentSparkReporter report = new ExtentSparkReporter("./TestReport3.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    public String user;
    public String userPicker;
@BeforeTest
public void reporting(){

    extent.attachReporter(report);}

    @AfterTest
            public void reporting2() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport3.html").toURI());
}
    @BeforeClass
    public  void status1(){
        System.out.println("Started testing: TC01,TC02,TC03,TC04,TC05");
    }
    @AfterClass
    public  void status2(){
        System.out.println("Testing completed: TC01,TC02,TC03,TC04,TC05");
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
    @AfterMethod()
    public void tearDown(ITestResult result){
    if(result.getStatus() == ITestResult.FAILURE){
        test.log(Status.FAIL,"Test Case Failed " +result.getName());
        test.log(Status.FAIL,"Test case Failed " +result.getThrowable());
    String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
    test.addScreenCaptureFromBase64String(b64model);
    }
        else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP,"Test Case Skipped " +result.getName());
            test.log(Status.SKIP,"Test Case Skipped " +result.getThrowable());}
            else{
                test.log(Status.PASS,"Test case Passed " +result.getName());
            }
            extent.flush();
    driver.quit();
    }
@Test
        public void TC01() throws IOException, InterruptedException {
     test = extent.createTest("TC01","Testing of module usage tab").info("userDetail:" +userPicker);

    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    driver.navigate().to("https://carear-development.web.app/#/admin/dashboard/usage/v2");
    WebElement last7Days = driver.findElement(By.xpath("//button[text()='Last 7 Days']"));
    boolean exp1 = last7Days.isDisplayed();
    Assert.assertTrue(exp1);
    WebElement activeUsers = driver.findElement(By.xpath("//small[text()='Total Active Users']"));
    boolean exp2 = activeUsers.isDisplayed();
    Assert.assertTrue(exp2);
    WebElement activeMinutes = driver.findElement(By.xpath("//small[text()='Total Minutes Used']"));
    boolean exp3 = activeMinutes.isDisplayed();
    Assert.assertTrue(exp3);
    WebElement chart = driver.findElement(By.cssSelector("canvas[role='img']"));
    boolean exp4 = chart.isDisplayed();
    Assert.assertTrue(exp4);
    String ssfolder = "C:\\Users\\betty\\Desktop\\ss";
    File ss = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    Files.copy(ss,new File("C:\\Users\\betty\\Desktop\\ss\\ usage7days.jpg"));
    test.addScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss\\ usage7days.jpg");



}
        @Test
    public void TC02() throws IOException {

            test = extent.createTest("TC02","Testing of module usage tab")
                    .info("userDetail:" +userPicker);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
            driver.navigate().to("https://carear-development.web.app/#/admin/dashboard/usage/v2");
            WebElement last30Days = driver.findElement(By.xpath("//button[text()='Last 30 Days']"));
            boolean exp1 = last30Days.isDisplayed();
            WebElement last30daysWait = wait.until(ExpectedConditions.elementToBeClickable(last30Days));
            last30Days.click();
            Assert.assertTrue(exp1);
            WebElement activeUsers = driver.findElement(By.xpath("//small[text()='Total Active Users']"));
            boolean exp2 = activeUsers.isDisplayed();
            Assert.assertTrue(exp2);
            WebElement activeMinutes = driver.findElement(By.xpath("//small[text()='Total Minutes Used']"));
            boolean exp3 = activeMinutes.isDisplayed();
            Assert.assertTrue(exp3);
            WebElement chart = driver.findElement(By.cssSelector("canvas[role='img']"));
            boolean exp4 = chart.isDisplayed();
            Assert.assertTrue(exp4);
           String base64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(base64model);


        }
            @Test
            public void TC03() throws IOException {
                test = extent.createTest("TC03","Testing of module usage tab").info("user Detail:" +userPicker);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
                driver.navigate().to("https://carear-development.web.app/#/admin/dashboard/usage/v2");
                WebElement last12Months = driver.findElement(By.xpath("//button[text()='Last 12 Months']"));
                boolean exp1 = last12Months.isDisplayed();
                WebElement last12monthsWait = wait.until(ExpectedConditions.elementToBeClickable(last12Months));
                last12Months.click();
                Assert.assertTrue(exp1);
                WebElement activeUsers = driver.findElement(By.xpath("//small[text()='Total Active Users']"));
                boolean exp2 = activeUsers.isDisplayed();
                Assert.assertTrue(exp2);
                WebElement activeMinutes = driver.findElement(By.xpath("//small[text()='Total Minutes Used']"));
                boolean exp3 = activeMinutes.isDisplayed();
                Assert.assertTrue(exp3);
                WebElement chart = driver.findElement(By.cssSelector("canvas[role='img']"));
                boolean exp4 = chart.isDisplayed();
                Assert.assertTrue(exp4);
                File ss = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                Files.copy(ss,new File("C:\\Users\\betty\\Desktop\\ss\\ usage12months.jpg"));
                test.addScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss\\ usage12months.jpg");
            }
    @Test
    public void TC04() throws IOException {
        test = extent.createTest("TC04","Testing of module usage tab").info("user Detail:" +userPicker);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.navigate().to("https://carear-development.web.app/#/admin/dashboard/usage/v2");
        WebElement billingCycle = driver.findElement(By.xpath("//button[text()='Current Billing Cycle']"));
        boolean exp1 = billingCycle.isDisplayed();
        WebElement billingCycleWait = wait.until(ExpectedConditions.elementToBeClickable(billingCycle));
        billingCycle.click();
        Assert.assertTrue(exp1);
        WebElement activeUsers = driver.findElement(By.xpath("//small[text()='Total Active Users']"));
        boolean exp2 = activeUsers.isDisplayed();
        Assert.assertTrue(exp2);
        WebElement activeMinutes = driver.findElement(By.xpath("//small[text()='Total Minutes Used']"));
        boolean exp3 = activeMinutes.isDisplayed();
        Assert.assertTrue(exp3);
        WebElement chart = driver.findElement(By.cssSelector("canvas[role='img']"));
        boolean exp4 = chart.isDisplayed();
        Assert.assertTrue(exp4);
        File ss = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        Files.copy(ss,new File("C:\\Users\\betty\\Desktop\\ss\\ billingcycle.jpg"));
        test.addScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss\\ billingcycle.jpg");

        }
    @Test
    public void TC05() throws InterruptedException, IOException {
         test = extent.createTest("TC05","Testing of module usage tab").info("user Detail:" +userPicker);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.navigate().to("https://carear-development.web.app/#/admin/dashboard/usage/v2");

        WebElement customDate = driver.findElement(By.xpath("//button[text()='Custom Date']"));
        boolean exp1 = customDate.isDisplayed();
        WebElement customWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Custom Date']")));
        customDate.click();
        Assert.assertTrue(exp1);
        WebElement fromDate = driver.findElement(By.xpath("(//div[contains(@class,'input-group-append')])[1]"));
    fromDate.click();
    WebElement month = driver.findElement(By.xpath("//div[@class='react-datepicker__current-month']"));
    String monthText = month.getText();
    WebElement previousButton = driver.findElement(By.xpath("//button[text()='Previous Month']"));
    while(!monthText.equals("January 2024")){
        previousButton.click();
        month = driver.findElement(By.xpath("//div[@class='react-datepicker__current-month']"));
        monthText = month.getText();

    }
    WebElement selectDate = driver.findElement(By.cssSelector("div[class^='react-datepicker__day'][aria-label*='January 1st']"));
selectDate.click();

        WebElement toDate = driver.findElement(By.xpath("(//div[contains(@class,'input-group-append')])[2]"));
        toDate.click();
        WebElement month2 = driver.findElement(By.xpath("//div[@class='react-datepicker__current-month']"));
        String monthText2 = month2.getText();
        WebElement nextButton = driver.findElement(By.xpath("//button[text()='Next Month']"));
        while(!monthText2.equals("May 2024")){
            nextButton.click();
            month2 = driver.findElement(By.xpath("//div[@class='react-datepicker__current-month']"));
            monthText2 = month2.getText();

        }
        WebElement selectDate2 = driver.findElement(By.cssSelector("div[class^='react-datepicker__day'][aria-label*='May 1st']"));

        selectDate2.click();
        WebElement search   = driver.findElement(By.xpath("//button[text()='Search']"));
        search.click();
        Thread.sleep(3000);
        WebElement activeUsers = driver.findElement(By.xpath("(//div[@class='h4 mb-0'])[1]"));
        activeUsers.getText();
        WebElement activeMinutes = driver.findElement(By.xpath("(//div[@class='h4 mb-0'])[2]"));
        activeMinutes.getText();
        File ss = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        Files.copy(ss,new File("C:\\Users\\betty\\Desktop\\ss\\ customdate.jpg"));
        test.addScreenCaptureFromPath("C:\\Users\\betty\\Desktop\\ss\\ customdate.jpg");


    }}

