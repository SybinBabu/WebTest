package org.Test;

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
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class InstructWidget {
    WebDriver driver;
    ExtentSparkReporter report= new ExtentSparkReporter("./TestReport2.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    String user;
    public  String userPicker;
    @BeforeTest
    public void prereq1(){
        extent.attachReporter(report);
    }
    @AfterTest
    public void prereq() throws IOException {
        Desktop.getDesktop().browse(new File("Testreport2.html").toURI());
    }
    @BeforeClass
    public void status1(){
        System.out.println("testing started for instruc widget");
    }
    @AfterClass
    public void status2(){
        System.out.println("testing comleted for instruc widget");
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
public void tearDown(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE){
            test.log(Status.FAIL,"Test case failed" +result.getName());
            test.log(Status.FAIL,"Test case failed" +result.getThrowable());

            String base64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(base64model);
        }
        else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP,"Test case skipped" +result.getName());
            test.log(Status.SKIP,"Test case skipped" +result.getThrowable());
        }
        else{
            test.log(Status.PASS,"Test case passed" +result.getName());
        }
        extent.flush();

    driver.quit();
    }
    @Test
    public void TC01(){
        test = extent.createTest("TC01","Testing of module instruct").info("user Detail:" +userPicker);
    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    //navigate to instruct
        driver.navigate().to("https://carear-development.web.app/#/admin/instruct-session");
        WebElement name = driver.findElement(By.xpath("//div[text()='Name']"));
       boolean nametext = name.isDisplayed();
        Assert.assertTrue(nametext);
        WebElement permission = driver.findElement(By.xpath("//div[text()='Permissions']"));
        boolean permissiontext=  permission.isDisplayed();
        Assert.assertTrue(permissiontext);
        WebElement sessions = driver.findElement(By.xpath("//div[text()='Sessions']"));
       boolean sessiontext = sessions.isDisplayed();
        Assert.assertTrue(sessiontext);
        WebElement search = driver.findElement(By.cssSelector("input.form-control[type='text'][placeholder='Search']"));
        boolean searchtext = search.isDisplayed();
        Assert.assertTrue(searchtext);
        WebElement header = driver.findElement(By.xpath("//div[text()='Analytics Instruct']"));
        boolean headertext=  header.isDisplayed();
        Assert.assertTrue(headertext);
        String base64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(base64model);



    }

    public static class UserCreation {
        WebDriver driver;
        ExtentSparkReporter report = new ExtentSparkReporter("./TestReport6.html");
        ExtentReports extent = new ExtentReports();
        ExtentTest test;
       public String user;
        public    String mail1;
        public  String mail2;
        public String mail3;
        public String mail4;
        public String userPicker;


        @BeforeClass
        public void status1(){
            System.out.println("Testing started for usercreation : TC01,TC02,TC03,TC04");
        }
        @AfterClass
        public void status2(){
            System.out.println("Testing completed for usercreation : TC01,TC02,TC03,TC04");
        }
        @BeforeTest
        void report(){
            extent.attachReporter(report);
        }
        @AfterTest
        void finalReport() throws IOException {
            Desktop.getDesktop().browse(new File("TestReport6.html").toURI());

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
        public void tearDown(ITestResult result){
            if(result.getStatus() == ITestResult.FAILURE){
                test.log(Status.FAIL,"Test Case Failed" +result.getName());
                test.log(Status.FAIL,"Test Case Failed" +result.getThrowable());
                String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
                test.addScreenCaptureFromBase64String(b64model);
            }
            else if(result.getStatus() == ITestResult.FAILURE){
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
        @Parameters({"email1","userPicker"})
        public void TC01(String email1,String userPicker) throws InterruptedException {
          test = extent.createTest("TC01","Testing of module user creation").info("User Detail:" +userPicker);
    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(16));
    driver.navigate().to("https://carear-development.web.app/#/admin/users");
    Thread.sleep(6000);
    WebElement userCreationWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add New User']")));
    userCreationWait.click();
    WebElement usercreation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Add New User']")));
    usercreation.isDisplayed();


       WebElement email = driver.findElement(By.id("email"));
       this.mail1 = "email1";
            email.sendKeys(email1);
            WebElement firstName = driver.findElement(By.id("firstName"));
            firstName.sendKeys("Test");
            WebElement lastName = driver.findElement(By.id("lastName"));
            lastName.sendKeys("User");
            WebElement primaryNumber = driver.findElement(By.id("phoneNumber"));
            primaryNumber.sendKeys("+917897654556");
            WebElement secondaryNumber = driver.findElement(By.id("secondaryPhoneNumber"));
            secondaryNumber.sendKeys("+917897654556");
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0,500)");
    WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
    create.click();
    WebElement popupWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the User and sent the Initial Invite Email']")));
    boolean exp1 = popupWait.isDisplayed();
    Assert.assertTrue(exp1);
    Thread.sleep(4000);
    List<WebElement> grid = driver.findElements(By.xpath("//tbody//tr//td[4]"));
    for(WebElement each : grid){
        if(each.getText().contains(email1)){
         System.out.println("Testcase passed");

    }}
    String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
    test.addScreenCaptureFromBase64String(b64model);



        }
        @Test
        @Parameters({"email2","userPicker"})
        public void TC02(String email2,String userPicker) throws InterruptedException {
            test = extent.createTest("TC02","Testing of module user creation").info("User Detail:" +userPicker);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(16));
            driver.navigate().to("https://carear-development.web.app/#/admin/users");
            Thread.sleep(6000);
            WebElement userCreationWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add New User']")));
            userCreationWait.click();
            WebElement usercreation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Add New User']")));
            usercreation.isDisplayed();
            this.mail2 = "email2";
            WebElement email = driver.findElement(By.id("email"));
            email.sendKeys(email2);
            WebElement firstName = driver.findElement(By.id("firstName"));
            firstName.sendKeys("Test");
            WebElement lastName = driver.findElement(By.id("lastName"));
            lastName.sendKeys("User");
            WebElement primaryNumber = driver.findElement(By.id("phoneNumber"));
            primaryNumber.sendKeys("+917897654556");
            WebElement secondaryNumber = driver.findElement(By.id("secondaryPhoneNumber"));
            secondaryNumber.sendKeys("+917897654556");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");
            WebElement role = driver.findElement(By.xpath("((//div[contains(@class,'search')])[1])"));
            if(!role.getAttribute("class").contains("opened")){
                role.click();
            }
            WebElement roleSelect = driver.findElement(By.xpath("//li[text()='Administrator']"));
            roleSelect.click();
            WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
            create.click();
            WebElement popupWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the User and sent the Initial Invite Email']")));
            boolean exp1 = popupWait.isDisplayed();
            Assert.assertTrue(exp1);
            Thread.sleep(4000);
            List<WebElement> grid = driver.findElements(By.xpath("//tbody//tr//td[4]"));
            for(WebElement each : grid){
                if(each.getText().contains(email2)){
                  System.out.println("Test Case Passed");
            }}
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);



        }
        @Test
        @Parameters({"email3","userPicker"})
        public void TC03(String email3,String userPicker) throws InterruptedException {
            test = extent.createTest("TC03","Testing of module user creation").info("User Detail:" +userPicker);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(16));
            driver.navigate().to("https://carear-development.web.app/#/admin/users");
            Thread.sleep(6000);
            WebElement userCreationWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add New User']")));
            userCreationWait.click();
            WebElement usercreation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Add New User']")));
            usercreation.isDisplayed();
            WebElement email = driver.findElement(By.id("email"));
            this.mail3 = "email3";
            email.sendKeys(email3);
            WebElement firstName = driver.findElement(By.id("firstName"));
            firstName.sendKeys("Test");
            WebElement lastName = driver.findElement(By.id("lastName"));
            lastName.sendKeys("User");
            WebElement primaryNumber = driver.findElement(By.id("phoneNumber"));
            primaryNumber.sendKeys("+917897654556");
            WebElement secondaryNumber = driver.findElement(By.id("secondaryPhoneNumber"));
            secondaryNumber.sendKeys("+917897654556");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");
            WebElement role = driver.findElement(By.xpath("((//div[contains(@class,'search')])[1])"));
            if(!role.getAttribute("class").contains("opened")){
                role.click();
            }
            WebElement roleSelect = driver.findElement(By.xpath("//li[text()='Analyst']"));
            roleSelect.click();
            WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
            create.click();
            WebElement popupWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the User and sent the Initial Invite Email']")));
            boolean exp1 = popupWait.isDisplayed();
            Assert.assertTrue(exp1);
            Thread.sleep(4000);
            List<WebElement> grid = driver.findElements(By.xpath("//tbody//tr//td[4]"));
            for(WebElement each : grid){
                if(each.getText().contains(email3)){
                    System.out.println("Test Case Passed");
                }
            }
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);



        }
        @Test
        @Parameters({"email4","userPicker"})
        public void TC04(String email4, String userPicker) throws InterruptedException {
            test = extent.createTest("TC04","Testing of module user creation").info("User Detail:" +userPicker);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(16));
            driver.navigate().to("https://carear-development.web.app/#/admin/users");
            Thread.sleep(6000);
            WebElement userCreationWait = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add New User']")));
            userCreationWait.click();
            WebElement usercreation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Add New User']")));
            usercreation.isDisplayed();
            WebElement email = driver.findElement(By.id("email"));
            this.mail4 = "email4";
            email.sendKeys(email4);
            WebElement firstName = driver.findElement(By.id("firstName"));
            firstName.sendKeys("Test");
            WebElement lastName = driver.findElement(By.id("lastName"));
            lastName.sendKeys("User");
            WebElement primaryNumber = driver.findElement(By.id("phoneNumber"));
            primaryNumber.sendKeys("+917897654556");
            WebElement secondaryNumber = driver.findElement(By.id("secondaryPhoneNumber"));
            secondaryNumber.sendKeys("+917897654556");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");
            WebElement role = driver.findElement(By.xpath("((//div[contains(@class,'search')])[1])"));
            if(!role.getAttribute("class").contains("opened")){
                role.click();
            }
            WebElement roleSelect = driver.findElement(By.xpath("//li[text()='Content Creator']"));
            roleSelect.click();
            WebElement create = driver.findElement(By.cssSelector("button[type='submit']"));
            create.click();
            WebElement popupWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the User and sent the Initial Invite Email']")));
            boolean exp1 = popupWait.isDisplayed();
            Assert.assertTrue(exp1);
            Thread.sleep(4000);
            List<WebElement> grid = driver.findElements(By.xpath("//tbody//tr//td[4]"));
            for(WebElement each : grid){
                if(each.getText().contains(email4)){
                    System.out.println("Test Case Passed");
                }
            }
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);



        }

        }

    public static class ExpienceBuilder {
        WebDriver driver;
        ExtentSparkReporter report = new ExtentSparkReporter("./TestReport8.html");
        ExtentReports extent = new ExtentReports();
        ExtentTest test;
        @BeforeClass
        public void status1(){
            System.out.println("Testing started TC01");
        }
        @AfterClass
        public void status2(){
            System.out.println("Testing completed TC01");
        }
        @BeforeTest
        public void report(){
            extent.attachReporter(report);
        }
        @AfterTest
        public void finalReport() throws IOException {
            Desktop.getDesktop().browse(new File ("TestReport8.html").toURI());
        }
        @BeforeMethod
                public void Setup(){
                EdgeOptions opt = new EdgeOptions();
                opt.addArguments("--guest");
                driver = new EdgeDriver(opt);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(13));
                driver.get("https://carear-development.web.app/#/admin/login");
                WebElement username = driver.findElement(By.id("userNameInput"));
                username.sendKeys("sybin.xerox+12@gmail.com");
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
                String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
                test.addScreenCaptureFromBase64String(b64model);
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.log(Status.SKIP,"Test Case Skipped" +result.getName());
                test.log(Status.SKIP,"Test Case Skipped" +result.getThrowable());
            }
            else{
                test.log(Status.PASS,"Test Case Passed" +result.getName());
            }
            extent.flush();
            //driver.quit();
            }
            @Test
        public void TC01() throws InterruptedException {
            test = extent.createTest("TC01","Testing of experience builder").info("User Detail");
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.navigate().to("https://carear-development.web.app/#/admin/instruct-experience");
            Thread.sleep(6000);
            WebElement addNew = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add New']")));
            addNew.click();
            String parentWindow = driver.getWindowHandle();
            Set<String> childWindow = driver.getWindowHandles();
            for(String temp : childWindow){
                if(!temp.equals(parentWindow)){
                    driver.switchTo().window(temp);
                }
            }
           WebElement expName = driver.findElement(By.id("experienceName"));
            expName.sendKeys("AuoExpTest");
                WebElement expDescription = driver.findElement(By.id("experienceDescription"));
                expDescription.sendKeys("testing");
                Thread.sleep(2000);
                WebElement startBuilding = driver.findElement(By.xpath("//button[text()='start building']"));
           startBuilding.click();
           WebElement closeIconWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='OnboardingTipsDialog_closeIcon__oqjbk']")));
           WebElement closeIcon = driver.findElement(By.xpath("//span[@class='OnboardingTipsDialog_closeIcon__oqjbk']"));
     closeIcon.click();
           Actions act = new Actions(driver) ;
     WebElement contentPage = driver.findElement(By.xpath("(//div[@class='PageTemplates_page__14hDW'])[1]"));
    WebElement pane = driver.findElement(By.xpath("//div[@class='react-flow__pane']"));
     act.clickAndHold(contentPage).pause(50).moveToElement(pane).pause(50).release().build().perform();

            }
        }
}