package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
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

public class Survey {
    WebDriver driver;
    ExtentSparkReporter report = new ExtentSparkReporter("./TestReport12.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    public  String user;
    public  String userPicker;
    @BeforeClass
    public void status1(){
        System.out.println("Testing started for TC01,TC02,TC03,TC04");
    }
    @AfterClass
    public void status2(){
        System.out.println("Testing completed for TC01,TC02,TC03,TC04");
    }
    @BeforeTest
    public void report(){
        extent.attachReporter(report);
    }
    @AfterTest
    public void finalReport() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport12.html").toURI());
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
    @Test(enabled = true , priority = 1)
    @Parameters({"userPicker"})
    public void TC01(String userPicker){
        test= extent.createTest("TC01","Mandatory-Rating-Host").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement rating = driver.findElement(By.xpath("//label[text()='Rating']"));
        rating.click();
        WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
        create.click();
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
        boolean exp1 =popup.isDisplayed();
        Assert.assertTrue(exp1);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
    }
    @Test(enabled = true , priority = 2)
    @Parameters({"userPicker"})
    public void TC02(String userPicker){
        test= extent.createTest("TC02","Mandatory-Dropdown-Host").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement rating = driver.findElement(By.xpath("//label[text()='Dropdown']"));
        rating.click();
        WebElement data = driver.findElement(By.id("predefined_data"));
        data.sendKeys("1,2,3");
        WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
        create.click();
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
        boolean exp1 =popup.isDisplayed();
        Assert.assertTrue(exp1);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
    }
    @Test(enabled = true , priority = 3)
    @Parameters({"userPicker"})
    public void TC03(String userPicker){
        test= extent.createTest("TC03","Mandatory-singleline-Host").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement rating = driver.findElement(By.xpath("//label[text()='Singleline']"));
        rating.click();
        WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
        create.click();
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
        boolean exp1 =popup.isDisplayed();
        Assert.assertTrue(exp1);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
    }
    @Test(enabled = true , priority = 4)
    @Parameters({"userPicker"})
    public void TC04(String userPicker){
        test= extent.createTest("TC04","Mandatory-multiline-Host").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement rating = driver.findElement(By.xpath("//label[text()='Multiline']"));
        rating.click();
        WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
        create.click();
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
        boolean exp1 =popup.isDisplayed();
        Assert.assertTrue(exp1);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
    }
    @Test(enabled = true , priority = 5)
    @Parameters({"userPicker"})
    public void TC05(String userPicker){
        test= extent.createTest("TC05","Mandatory-Radio Button-Host").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement rating = driver.findElement(By.xpath("//label[text()='Radio Button']"));
        rating.click();
        WebElement data = driver.findElement(By.id("predefined_data"));
        data.sendKeys("1,2,3");
        WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
        create.click();
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
        boolean exp1 =popup.isDisplayed();
        Assert.assertTrue(exp1);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
    }
    @Test(enabled = true , priority = 6)
    @Parameters({"userPicker"})
    public void TC06(String userPicker){
        test= extent.createTest("TC06","Mandatory-Rating-Thumbs").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement ratingThumbs = driver.findElement(By.xpath("//label[text()='Rating (Thumbs)']"));
        ratingThumbs.click();
        WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
        create.click();
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
        boolean exp1 =popup.isDisplayed();
        Assert.assertTrue(exp1);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
    }
    @Test(enabled = true , priority = 7)
    @Parameters({"userPicker"})
    public void TC07(String userPicker){
        test= extent.createTest("TC07","Optional-Rating-Host").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement optional = driver.findElement(By.xpath("//label[text()='Optional']"));
        if(!optional.getAttribute("class").contains("active")){
            optional.click();
        }
        WebElement rating = driver.findElement(By.xpath("//label[text()='Rating']"));
        rating.click();
        WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
        create.click();
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
        boolean exp1 =popup.isDisplayed();
        Assert.assertTrue(exp1);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
    }
    @Test(enabled = true , priority = 8)
    @Parameters({"userPicker"})
    public void TC08(String userPicker){
        test= extent.createTest("TC08","Optional-Dropdown-Host").info("userDetail " +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement optional = driver.findElement(By.xpath("//label[text()='Optional']"));
        if(!optional.getAttribute("class").contains("active")){
            optional.click();
            WebElement dropDown = driver.findElement(By.xpath("//label[text()='Dropdown']"));
            dropDown.click();
            WebElement data = driver.findElement(By.id("predefined_data"));
            data.sendKeys("1,2,3");
            WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
            create.click();
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
            boolean exp1 =popup.isDisplayed();
            Assert.assertTrue(exp1);
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);
        }
    }
    @Test(enabled = true , priority = 9)
    @Parameters({"userPicker"})
    public void TC09(String userPicker){
        test= extent.createTest("TC09","Optional-Singleline-Host").info("userDetail " +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement optional = driver.findElement(By.xpath("//label[text()='Optional']"));
        if(!optional.getAttribute("class").contains("active")){
            optional.click();
            WebElement singleLine = driver.findElement(By.xpath("//label[text()='Singleline']"));
            singleLine.click();
            WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
            create.click();
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
            boolean exp1 =popup.isDisplayed();
            Assert.assertTrue(exp1);
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);
        }
    }
    @Test(enabled = true , priority = 10)
    @Parameters({"userPicker"})
    public void TC10(String userPicker){
        test= extent.createTest("TC10","Optional-Multiline-Host").info("userDetail " +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement optional = driver.findElement(By.xpath("//label[text()='Optional']"));
        if(!optional.getAttribute("class").contains("active")){
            optional.click();
            WebElement multiLine = driver.findElement(By.xpath("//label[text()='Multiline']"));
            multiLine.click();
            WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
            create.click();
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
            boolean exp1 =popup.isDisplayed();
            Assert.assertTrue(exp1);
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);
        }
    }
    @Test(enabled = true , priority = 11)
    @Parameters({"userPicker"})
    public void TC11(String userPicker){
        test= extent.createTest("TC11","Optional-Radio-Host").info("userDetail " +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement optional = driver.findElement(By.xpath("//label[text()='Optional']"));
        if(!optional.getAttribute("class").contains("active")){
            optional.click();
            WebElement radioButton = driver.findElement(By.xpath("//label[text()='Radio Button']"));
            radioButton.click();
            WebElement data = driver.findElement(By.id("predefined_data"));
            data.sendKeys("1,2,3");
            WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
            create.click();
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
            boolean exp1 =popup.isDisplayed();
            Assert.assertTrue(exp1);
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);
        }
    }
    @Test(enabled = true , priority = 12)
    @Parameters({"userPicker"})
    public void TC12(String userPicker){
        test= extent.createTest("TC12","Optional-Rating-Host").info("userDetail " +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().to("https://carear-us-qa.web.app/#/admin/surveys/v2");
        WebElement addSurvey = driver.findElement(By.xpath("//button[text()='Add New Survey']"));
        addSurvey.click();
        WebElement question = driver.findElement(By.cssSelector("input[placeholder='Question']"));
        question.sendKeys("Rate your feedback");
        WebElement optional = driver.findElement(By.xpath("//label[text()='Optional']"));
        if(!optional.getAttribute("class").contains("active")){
            optional.click();
            WebElement rating = driver.findElement(By.xpath("//label[text()='Rating']"));
            rating.click();
            WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
            create.click();
            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Survey added successfully']")));
            boolean exp1 =popup.isDisplayed();
            Assert.assertTrue(exp1);
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);
        }
    }

}


