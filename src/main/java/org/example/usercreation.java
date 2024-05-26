package org.example;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
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

    public class usercreation {
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
            driver.get("https://carear-us-qa.web.app/#/admin/login");
            WebElement username = driver.findElement(By.id("userNameInput"));
            this.user = "userPicker";
            username.sendKeys(userPicker);
            WebElement next = driver.findElement(By.id("submitInput"));
            next.click();
            try{
                WebElement verificationWait = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='We need to verify your identity']")));
                java.util.List<WebElement> vcode = driver.findElements(By.xpath("//input[@type='tel']"));
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
        @Test(priority = 1)
        @Parameters({"email1","userPicker"})
        public void TC01(String email1,String userPicker) throws InterruptedException {
            test = extent.createTest("TC01","Testing of module user creation:general").info("User Detail:" +userPicker);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(16));
            driver.navigate().to("https://carear-us-qa.web.app/#/admin/users");
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
            java.util.List<WebElement> grid = driver.findElements(By.xpath("//tbody//tr//td[4]"));
            for(WebElement each : grid){
                if(each.getText().contains(email1)){
                    System.out.println("Testcase passed");

                }}
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);



        }
        @Test(priority=2)
        @Parameters({"email2","userPicker"})
        public void TC02(String email2,String userPicker) throws InterruptedException {
            test = extent.createTest("TC02","Testing of module user creation:admin").info("User Detail:" +userPicker);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(16));
            driver.navigate().to("https://carear-us-qa.web.app/#/admin/users");
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
            java.util.List<WebElement> grid = driver.findElements(By.xpath("//tbody//tr//td[4]"));
            for(WebElement each : grid){
                if(each.getText().contains(email2)){
                    System.out.println("Test Case Passed");
                }}
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);



        }
        @Test(priority = 3)
        @Parameters({"email3","userPicker"})
        public void TC03(String email3,String userPicker) throws InterruptedException {
            test = extent.createTest("TC03","Testing of module user creation:analyst").info("User Detail:" +userPicker);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(16));
            driver.navigate().to("https://carear-us-qa.web.app/#/admin/users");
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
            java.util.List<WebElement> grid = driver.findElements(By.xpath("//tbody//tr//td[4]"));
            for(WebElement each : grid){
                if(each.getText().contains(email3)){
                    System.out.println("Test Case Passed");
                }
            }
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);



        }
        @Test(priority = 4)
        @Parameters({"email4","userPicker"})
        public void TC04(String email4, String userPicker) throws InterruptedException {
            test = extent.createTest("TC04","Testing of module user creation:content creator").info("User Detail:" +userPicker);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(15));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(16));
            driver.navigate().to("https://carear-us-qa.web.app/#/admin/users");
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


