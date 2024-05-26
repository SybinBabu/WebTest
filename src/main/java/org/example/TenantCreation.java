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
import org.openqa.selenium.support.ui.Select;
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

import static java.lang.Thread.sleep;

public class TenantCreation {
    WebDriver driver;
    ExtentSparkReporter report = new ExtentSparkReporter("./TestReport11.html");
    ExtentReports extent = new ExtentReports();
    ExtentTest test;
    String userPicker;
    String user;

    @BeforeClass
    public void status1(){
        System.out.println("Testing started for TC01, TC02,TC03,TC04");
    }
    @AfterClass
    public void status(){
        System.out.println("Testing completed for TC01, TC02,TC03,TC04");
    }
    @BeforeTest
    public void report(){
        extent.attachReporter(report);
    }
    @AfterTest
    public void finalReport() throws IOException {
        Desktop.getDesktop().browse(new File("TestReport11.html").toURI());
    }
    @BeforeMethod
    @Parameters({"userPicker","regionPicker"})
    public void Setup(String userPicker,String regionPicker){
        EdgeOptions opt = new EdgeOptions();
        opt.addArguments("--guest");
        driver = new EdgeDriver(opt);
        driver.manage().window().maximize();
        driver.get("https://carear-us-qa.web.app/#/super/login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        WebElement email = driver.findElement(By.cssSelector("input#userNameInput"));
        this.user = "userPicker";
        email.sendKeys(userPicker);
        WebElement region = driver.findElement(By.cssSelector("select[class^=form-control]"));
Select select = new Select(region);
select.selectByVisibleText(regionPicker);
WebElement next = driver.findElement(By.id("submitInput"));
next.click();
try{
    WebElement verification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='We need to verify your identity']")));
    List<WebElement> vcode = driver.findElements(By.cssSelector("input[type='tel']"));
  sleep(4000);
    for(WebElement each : vcode){
        each.sendKeys("1");
    }
    WebElement next2 = driver.findElement(By.cssSelector("button[type='submit']"));
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
finally{
    WebElement newTenantHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[text()='Add New Tenant']")));
boolean exp1 = newTenantHeader.isDisplayed();
    Assert.assertTrue(exp1);
}

    }
@AfterMethod
    public void tearDown(ITestResult result) {
    if (result.getStatus() == ITestResult.FAILURE) {
        test.log(Status.FAIL, "Test Case Failed" + result.getName());
        test.log(Status.FAIL, "Test Case Failed" + result.getThrowable());
        String b64model = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
    } else if (result.getStatus() == ITestResult.SKIP) {
        test.log(Status.SKIP, "Test Case Skipped" + result.getName());
        test.log(Status.SKIP, "Test Case Skipped" + result.getThrowable());

    } else {
        test.log(Status.PASS, "Test Case Passed" + result.getName());

    }
    extent.flush();
    driver.quit();
}
@Test(priority = 1,enabled = true)
@Parameters({"testEmail1","userPicker"})
    public void TC01(String testEmail1, String userPicker) throws InterruptedException {
        test = extent.createTest("TC01","Test Case for plan type:Enterprise").info("userDetail" +userPicker);
WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
driver.get("https://carear-us-qa.web.app/#/super/add-tenant");
String [] valuesToSend = {"","Test","automation","testing"};
List<WebElement> texts = driver.findElements(By.xpath("//input[@type='text']"));
for(int i= 1; i<=texts.size() && i<valuesToSend.length; i++){
    texts.get(i).sendKeys(valuesToSend[i]);
}
WebElement userEmail = driver.findElement(By.xpath("(//input[@id='inputEmail'])[2]"));
userEmail.sendKeys(testEmail1);

WebElement snow = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[1]"));
if(!snow.getAttribute("class").contains("checked")){
    snow.click();
}
    WebElement sso = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[2]"));
    if(!sso.getAttribute("class").contains("checked")){
        sso.click();
    }
WebElement licensedSubscribers = driver.findElement(By.xpath("//input[@placeholder='Licensed Subscribers']"));
    licensedSubscribers.sendKeys("10");
WebElement licencedExperiences = driver.findElement(By.xpath("//input[@placeholder='Licensed Experiences']"));
licencedExperiences.sendKeys("10");
    WebElement sterm = driver.findElement(By.id("inputSubscriptionTerm"));
    sterm.sendKeys("10");
    WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
    submit.click();
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the Tenant Admin and sent the Initial Invite Email']")));
        boolean exp2 = popup.isDisplayed();
    Assert.assertTrue(exp2);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
        driver.navigate().refresh();
        WebElement newTenant = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@data-automation=\"tenantItem\"])[1]")));
        Actions act = new Actions(driver);
        act.moveToElement(newTenant).perform();
        WebElement viewTenant = driver.findElement(By.xpath("//span[@class='viewRecord']"));
        viewTenant.click();
        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        for (String temp : childWindows) {
            if (!temp.equals(parentWindow)) {
                driver.switchTo().window(temp);
            }
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        WebElement primaryAdmin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//strong[text()='Primary Tenant Administrator']/following::strong)[1]")));
        if (primaryAdmin.getText().contains(testEmail1)) {
            String base64model2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.info("Tenant is created").addScreenCaptureFromBase64String(base64model2);
        }
        WebElement enterprise = driver.findElement(By.xpath("//strong[text()='Enterprise']"));
        boolean exp3 = enterprise.isDisplayed();
        Assert.assertTrue(exp3);
    }

    @Test(priority = 2,enabled = true)
    @Parameters({"testEmail2","userPicker"})
    public void TC02(String testEmail2,String userPicker) throws InterruptedException {
        test = extent.createTest("TC02","Test Case for plan type:Core").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://carear-us-qa.web.app/#/super/add-tenant");
        String [] valuesToSend = {"","Test","automation","testing"};
        List<WebElement> texts = driver.findElements(By.xpath("//input[@type='text']"));
        for(int i= 1; i<=texts.size() && i<valuesToSend.length; i++){
            texts.get(i).sendKeys(valuesToSend[i]);
        }
        WebElement userEmail = driver.findElement(By.xpath("(//input[@id='inputEmail'])[2]"));
        userEmail.sendKeys(testEmail2);
        WebElement planType = driver.findElement(By.id("inputState"));
        Select select = new Select(planType);
        select.selectByVisibleText("Core");
               WebElement sso = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[2]"));
        if(!sso.getAttribute("class").contains("checked")){
            sso.click();
        }
        WebElement licensedSubscribers = driver.findElement(By.xpath("//input[@placeholder='Licensed Subscribers']"));
        licensedSubscribers.sendKeys("10");
        WebElement licencedExperiences = driver.findElement(By.xpath("//input[@placeholder='Licensed Experiences']"));
        licencedExperiences.sendKeys("10");
        WebElement sterm = driver.findElement(By.id("inputSubscriptionTerm"));
        sterm.sendKeys("10");
        WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
        submit.click();

            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the Tenant Admin and sent the Initial Invite Email']")));
            boolean exp2 = popup.isDisplayed();
            Assert.assertTrue(exp2);
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);

        driver.navigate().refresh();
        WebElement newTenant = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@data-automation=\"tenantItem\"])[1]")));
        Actions act = new Actions(driver);
        act.moveToElement(newTenant).perform();
        WebElement viewTenant = driver.findElement(By.xpath("//span[@class='viewRecord']"));
        viewTenant.click();
        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        for(String temp : childWindows){
            if(!temp.equals(parentWindow)){
                driver.switchTo().window(temp);
            }
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        WebElement primaryAdmin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//strong[text()='Primary Tenant Administrator']/following::strong)[1]")));
        if(primaryAdmin.getText().contains(testEmail2)){
            String base64model2 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.info("Tenant is created").addScreenCaptureFromBase64String(base64model2);
        }
        WebElement core = driver.findElement(By.xpath("//strong[text()='Core']"));
        boolean exp3 = core.isDisplayed();
        Assert.assertTrue(exp3);


    }
    @Test(priority = 3,enabled = true)
    @Parameters({"userPicker","testEmail3"})
    public void TC03(String userPicker,String testEmail3) throws InterruptedException {
        test = extent.createTest("TC03","Test Case for plan type:Trial").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://carear-us-qa.web.app/#/super/add-tenant");
        String [] valuesToSend = {"","Test","automation","testing"};
        List<WebElement> texts = driver.findElements(By.xpath("//input[@type='text']"));
        for(int i= 1; i<=texts.size() && i<valuesToSend.length; i++){
            texts.get(i).sendKeys(valuesToSend[i]);
        }
        WebElement userEmail = driver.findElement(By.xpath("(//input[@id='inputEmail'])[2]"));
        userEmail.sendKeys(testEmail3);
        WebElement planType = driver.findElement(By.id("inputState"));
        Select select = new Select(planType);
        select.selectByVisibleText("Trial");
        WebElement sso = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[2]"));
        if(!sso.getAttribute("class").contains("checked")){
            sso.click();
        }
        WebElement licensedSubscribers = driver.findElement(By.xpath("//input[@placeholder='Licensed Subscribers']"));
        licensedSubscribers.sendKeys("10");
        WebElement licencedExperiences = driver.findElement(By.xpath("//input[@placeholder='Licensed Experiences']"));
        licencedExperiences.sendKeys("10");
        WebElement sterm = driver.findElement(By.id("inputSubscriptionTerm"));
        sterm.sendKeys("10");
        WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
        submit.click();

            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the Tenant Admin and sent the Initial Invite Email']")));
            boolean exp2 = popup.isDisplayed();
            Assert.assertTrue(exp2);
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);

        driver.navigate().refresh();
        WebElement newTenant = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@data-automation=\"tenantItem\"])[1]")));
        Actions act = new Actions(driver);
        act.moveToElement(newTenant).perform();
        WebElement viewTenant = driver.findElement(By.xpath("//span[@class='viewRecord']"));
        viewTenant.click();
        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        for(String temp : childWindows){
            if(!temp.equals(parentWindow)){
                driver.switchTo().window(temp);
            }
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        WebElement primaryAdmin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//strong[text()='Primary Tenant Administrator']/following::strong)[1]")));
        if(primaryAdmin.getText().contains(testEmail3)){
            String base64model2 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.info("Tenant is created").addScreenCaptureFromBase64String(base64model2);
        }
        WebElement trial = driver.findElement(By.xpath("//strong[text()='Trial']"));
        boolean exp3 = trial.isDisplayed();
        Assert.assertTrue(exp3);

    }
    @Test(priority = 4,enabled = true)
    @Parameters({"userPicker","testEmail4"})
    public void TC04(String userPicker, String testEmail4) throws InterruptedException {
        test = extent.createTest("TC04","Test Case for plan type:Platform-user-pro").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://carear-us-qa.web.app/#/super/add-tenant");
        String [] valuesToSend = {"","Test","automation","testing"};
        List<WebElement> texts = driver.findElements(By.xpath("//input[@type='text']"));
        for(int i= 1; i<=texts.size() && i<valuesToSend.length; i++){
            texts.get(i).sendKeys(valuesToSend[i]);
        }
        WebElement userEmail = driver.findElement(By.xpath("(//input[@id='inputEmail'])[2]"));
        userEmail.sendKeys(testEmail4);
        WebElement planType = driver.findElement(By.id("inputState"));
        Select select = new Select(planType);
        select.selectByVisibleText("Platform");
        WebElement vVerification = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[1]"));
        if(!vVerification.getAttribute("class").contains("checked")){
            vVerification.click();
        }
        WebElement snow = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[2]"));
        if(!snow.getAttribute("class").contains("checked")){
            snow.click();
        }
        WebElement sfdc = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[3]"));
        if(!sfdc.getAttribute("class").contains("checked")){
            sfdc.click();
        }
        WebElement sso = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[4]"));
        if(!sso.getAttribute("class").contains("checked")){
            sso.click();
        }
        WebElement instructPurchased = driver.findElement(By.id("inputinstructSessionPurchased"));
        instructPurchased.sendKeys("100");
        WebElement licensedSubscribers = driver.findElement(By.xpath("//input[@placeholder='Licensed Subscribers']"));
        licensedSubscribers.sendKeys("10");
        WebElement licencedExperiences = driver.findElement(By.xpath("//input[@placeholder='Licensed Experiences']"));
        licencedExperiences.sendKeys("10");
        WebElement sterm = driver.findElement(By.id("inputSubscriptionTerm"));
        sterm.sendKeys("10");
        WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
        submit.click();

            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the Tenant Admin and sent the Initial Invite Email']")));
            boolean exp2 = popup.isDisplayed();
            Assert.assertTrue(exp2);
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);
               driver.navigate().refresh();
        WebElement newTenant = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@data-automation=\"tenantItem\"])[1]")));
        Actions act = new Actions(driver);
        act.moveToElement(newTenant).perform();
        WebElement viewTenant = driver.findElement(By.xpath("//span[@class='viewRecord']"));
        viewTenant.click();
        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        for(String temp : childWindows){
            if(!temp.equals(parentWindow)){
                driver.switchTo().window(temp);
            }
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        WebElement primaryAdmin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//strong[text()='Primary Tenant Administrator']/following::strong)[1]")));
        if(primaryAdmin.getText().contains(testEmail4)){
            String base64model2 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.info("Tenant is created").addScreenCaptureFromBase64String(base64model2);
        }
        WebElement platform = driver.findElement(By.xpath("//strong[text()='Platform']"));
        boolean exp3 = platform.isDisplayed();
        Assert.assertTrue(exp3);
        WebElement user = driver.findElement(By.xpath("//strong[text()='User']"));
        boolean exp4 = user.isDisplayed();
        Assert.assertTrue(exp4);
    }


    @Test(priority = 5,enabled = true)
    @Parameters({"userPicker","testEmail5"})
    public void TC05(String userPicker,String testEmail5) throws InterruptedException {
        test = extent.createTest("TC05", "Test Case for plan type:Platform-minutes-pro, Ai disabled").info("userDetail" + userPicker);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://carear-us-qa.web.app/#/super/add-tenant");
        String[] valuesToSend = {"", "Test", "automation", "testing"};
        List<WebElement> texts = driver.findElements(By.xpath("//input[@type='text']"));
        for (int i = 1; i <= texts.size() && i < valuesToSend.length; i++) {
            texts.get(i).sendKeys(valuesToSend[i]);
        }
        WebElement userEmail = driver.findElement(By.xpath("(//input[@id='inputEmail'])[2]"));
        userEmail.sendKeys(testEmail5);
        WebElement planType = driver.findElement(By.id("inputState"));
        Select select = new Select(planType);
        select.selectByVisibleText("Platform");
        WebElement vVerification = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[1]"));
        if (!vVerification.getAttribute("class").contains("checked")) {
            vVerification.click();
        }
        WebElement snow = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[2]"));
        if (!snow.getAttribute("class").contains("checked")) {
            snow.click();
        }
        WebElement sfdc = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[3]"));
        if (!sfdc.getAttribute("class").contains("checked")) {
            sfdc.click();
        }
        WebElement sso = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[4]"));
        if (!sso.getAttribute("class").contains("checked")) {
            sso.click();
        }
        WebElement usageType = driver.findElement(By.xpath("(//select[@id='inputState'])[2]"));
        Select select1 = new Select(usageType);
        select1.selectByVisibleText("Minutes");
        WebElement minutesPurchased = driver.findElement(By.id("inputminutesPurchased"));
        minutesPurchased.sendKeys("100");
        WebElement instructPurchased = driver.findElement(By.id("inputinstructSessionPurchased"));
        instructPurchased.sendKeys("100");
        WebElement licensedSubscribers = driver.findElement(By.xpath("//input[@placeholder='Licensed Subscribers']"));
        licensedSubscribers.sendKeys("10");
        WebElement licencedExperiences = driver.findElement(By.xpath("//input[@placeholder='Licensed Experiences']"));
        licencedExperiences.sendKeys("10");
        WebElement sterm = driver.findElement(By.id("inputSubscriptionTerm"));
        sterm.sendKeys("10");


        WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
        submit.click();

            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the Tenant Admin and sent the Initial Invite Email']")));
            boolean exp2 = popup.isDisplayed();
            Assert.assertTrue(exp2);
            String b64model = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);
                  driver.navigate().refresh();
            WebElement newTenant = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@data-automation=\"tenantItem\"])[1]")));
            Actions act = new Actions(driver);
            act.moveToElement(newTenant).perform();
            WebElement viewTenant = driver.findElement(By.xpath("//span[@class='viewRecord']"));
            viewTenant.click();
            String parentWindow = driver.getWindowHandle();
            Set<String> childWindows = driver.getWindowHandles();
            for (String temp : childWindows) {
                if (!temp.equals(parentWindow)) {
                    driver.switchTo().window(temp);
                }
            }
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");
            js.executeScript("window.scrollBy(0,500)");
            js.executeScript("window.scrollBy(0,500)");
            WebElement primaryAdmin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//strong[text()='Primary Tenant Administrator']/following::strong)[1]")));
            if (primaryAdmin.getText().contains(testEmail5)) {
                String base64model2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                test.info("Tenant is created").addScreenCaptureFromBase64String(base64model2);
            }
            WebElement platform = driver.findElement(By.xpath("//strong[text()='Platform']"));
            boolean exp3 = platform.isDisplayed();
            Assert.assertTrue(exp3);
            WebElement minutes = driver.findElement(By.xpath("//strong[text()='Minutes']"));
            boolean exp4 = minutes.isDisplayed();
            Assert.assertTrue(exp4);
        }

    @Test(priority = 6,enabled = true)
    @Parameters({"userPicker","testEmail6"})
    public void TC06(String userPicker,String testEmail6) throws InterruptedException {
        test = extent.createTest("TC06","Test Case for plan type:Platform-sessions-pro").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://carear-us-qa.web.app/#/super/add-tenant");
        String [] valuesToSend = {"","Test","automation","testing"};
        List<WebElement> texts = driver.findElements(By.xpath("//input[@type='text']"));
        for(int i= 1; i<=texts.size() && i<valuesToSend.length; i++){
            texts.get(i).sendKeys(valuesToSend[i]);
        }
        WebElement userEmail = driver.findElement(By.xpath("(//input[@id='inputEmail'])[2]"));
        userEmail.sendKeys(testEmail6);
        WebElement planType = driver.findElement(By.id("inputState"));
        Select select = new Select(planType);
        select.selectByVisibleText("Platform");
        WebElement vVerification = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[1]"));
        if(!vVerification.getAttribute("class").contains("checked")){
            vVerification.click();
        }
        WebElement snow = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[2]"));
        if(!snow.getAttribute("class").contains("checked")){
            snow.click();
        }
        WebElement sfdc = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[3]"));
        if(!sfdc.getAttribute("class").contains("checked")){
            sfdc.click();
        }
        WebElement sso = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[4]"));
        if(!sso.getAttribute("class").contains("checked")){
            sso.click();
        }
        WebElement usageType = driver.findElement(By.xpath("(//select[@id='inputState'])[2]"));
        Select select1 = new Select(usageType);
        select1.selectByVisibleText("Sessions");
        WebElement sessionPurchased = driver.findElement(By.id("inputsessionsPurchased"));
        sessionPurchased.sendKeys("100");
        WebElement instructPurchased = driver.findElement(By.id("inputinstructSessionPurchased"));
        instructPurchased.sendKeys("100");
        WebElement licensedSubscribers = driver.findElement(By.xpath("//input[@placeholder='Licensed Subscribers']"));
        licensedSubscribers.sendKeys("10");
        WebElement licencedExperiences = driver.findElement(By.xpath("//input[@placeholder='Licensed Experiences']"));
        licencedExperiences.sendKeys("10");
        WebElement sterm = driver.findElement(By.id("inputSubscriptionTerm"));
        sterm.sendKeys("10");
        WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
        submit.click();

            WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the Tenant Admin and sent the Initial Invite Email']")));
            boolean exp2 = popup.isDisplayed();
            Assert.assertTrue(exp2);
            String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(b64model);
               driver.navigate().refresh();
        WebElement newTenant = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@data-automation=\"tenantItem\"])[1]")));
        Actions act = new Actions(driver);
        act.moveToElement(newTenant).perform();
        WebElement viewTenant = driver.findElement(By.xpath("//span[@class='viewRecord']"));
        viewTenant.click();
        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        for(String temp : childWindows){
            if(!temp.equals(parentWindow)){
                driver.switchTo().window(temp);
            }
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        WebElement primaryAdmin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//strong[text()='Primary Tenant Administrator']/following::strong)[1]")));
    if(primaryAdmin.getText().contains(testEmail6)){
        String base64model2 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.info("Tenant is created").addScreenCaptureFromBase64String(base64model2);
    }
WebElement platform = driver.findElement(By.xpath("//strong[text()='Platform']"));
    boolean exp3 = platform.isDisplayed();
    Assert.assertTrue(exp3);
        WebElement session = driver.findElement(By.xpath("//strong[text()='Sessions']"));
        boolean exp4 = session.isDisplayed();
        Assert.assertTrue(exp4);
    }
@Test(priority = 7)
@Parameters({"userPicker","testEmail7"})
public void TC07(String userPicker,String testEmail7) throws InterruptedException {
    test = extent.createTest("TC07","Test Case for plan type:Platform-sessions-pro,Ai-disable").info("userDetail" +userPicker);
    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.get("https://carear-us-qa.web.app/#/super/add-tenant");
    String [] valuesToSend = {"","Test","automation","testing"};
    List<WebElement> texts = driver.findElements(By.xpath("//input[@type='text']"));
    for(int i= 1; i<=texts.size() && i<valuesToSend.length; i++){
        texts.get(i).sendKeys(valuesToSend[i]);
    }
    WebElement userEmail = driver.findElement(By.xpath("(//input[@id='inputEmail'])[2]"));
    userEmail.sendKeys(testEmail7);
    WebElement planType = driver.findElement(By.id("inputState"));
    Select select = new Select(planType);
    select.selectByVisibleText("Platform");
    WebElement vVerification = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[1]"));
    if(vVerification.getAttribute("class").contains("checked")) {
        vVerification.click();
    }
    WebElement snow = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[2]"));
    if(!snow.getAttribute("class").contains("checked")){
        snow.click();
    }
    WebElement sfdc = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[3]"));
    if(!sfdc.getAttribute("class").contains("checked")){
        sfdc.click();
    }
    WebElement sso = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[4]"));
    if(!sso.getAttribute("class").contains("checked")){
        sso.click();
    }
    WebElement usageType = driver.findElement(By.xpath("(//select[@id='inputState'])[2]"));
    Select select1 = new Select(usageType);
    select1.selectByVisibleText("Sessions");
    WebElement sessionPurchased = driver.findElement(By.id("inputsessionsPurchased"));
    sessionPurchased.sendKeys("100");
    WebElement instructPurchased = driver.findElement(By.id("inputinstructSessionPurchased"));
    instructPurchased.sendKeys("100");
    WebElement licensedSubscribers = driver.findElement(By.xpath("//input[@placeholder='Licensed Subscribers']"));
    licensedSubscribers.sendKeys("10");
    WebElement licencedExperiences = driver.findElement(By.xpath("//input[@placeholder='Licensed Experiences']"));
    licencedExperiences.sendKeys("10");
    WebElement sterm = driver.findElement(By.id("inputSubscriptionTerm"));
    sterm.sendKeys("10");
    WebElement aI = driver.findElement(By.xpath("//input[@id='aiEnabled']"));
    aI.click();
    WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
    submit.click();

    WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the Tenant Admin and sent the Initial Invite Email']")));
    boolean exp2 = popup.isDisplayed();
    Assert.assertTrue(exp2);
    String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
    test.addScreenCaptureFromBase64String(b64model);
    driver.navigate().refresh();
    WebElement newTenant = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@data-automation=\"tenantItem\"])[1]")));
    Actions act = new Actions(driver);
    act.moveToElement(newTenant).perform();
    WebElement viewTenant = driver.findElement(By.xpath("//span[@class='viewRecord']"));
    viewTenant.click();
    String parentWindow = driver.getWindowHandle();
    Set<String> childWindows = driver.getWindowHandles();
    for(String temp : childWindows){
        if(!temp.equals(parentWindow)){
            driver.switchTo().window(temp);
        }
    }
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("window.scrollBy(0,500)");
    js.executeScript("window.scrollBy(0,500)");
    js.executeScript("window.scrollBy(0,500)");
    WebElement primaryAdmin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//strong[text()='Primary Tenant Administrator']/following::strong)[1]")));
    if(primaryAdmin.getText().contains(testEmail7)){
        String base64model2 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.info("Tenant is created").addScreenCaptureFromBase64String(base64model2);
    }
    WebElement platform = driver.findElement(By.xpath("//strong[text()='Platform']"));
    boolean exp3 = platform.isDisplayed();
    Assert.assertTrue(exp3);
    WebElement session = driver.findElement(By.xpath("//strong[text()='Sessions']"));
    boolean exp4 = session.isDisplayed();
    Assert.assertTrue(exp4);
}
@Test(priority = 8,enabled = true)
    @Parameters({"userPicker","testEmail8"})
    public void TC08(String userPicker,String testEmail8) throws InterruptedException {
        test = extent.createTest("TC08", "Test Case for plan type:Platform-minutes-pro").info("userDetail" + userPicker);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://carear-us-qa.web.app/#/super/add-tenant");
        String[] valuesToSend = {"", "Test", "automation", "testing"};
        List<WebElement> texts = driver.findElements(By.xpath("//input[@type='text']"));
        for (int i = 1; i <= texts.size() && i < valuesToSend.length; i++) {
            texts.get(i).sendKeys(valuesToSend[i]);
        }
        WebElement userEmail = driver.findElement(By.xpath("(//input[@id='inputEmail'])[2]"));
        userEmail.sendKeys(testEmail8);
        WebElement planType = driver.findElement(By.id("inputState"));
        Select select = new Select(planType);
        select.selectByVisibleText("Platform");
    WebElement vVerification = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[1]"));
        if(vVerification.getAttribute("class").contains("checked")) {
            vVerification.click();
        }
        WebElement snow = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[2]"));
        if (!snow.getAttribute("class").contains("checked")) {
            snow.click();
        }
        WebElement sfdc = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[3]"));
        if (!sfdc.getAttribute("class").contains("checked")) {
            sfdc.click();
        }
        WebElement sso = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[4]"));
        if (!sso.getAttribute("class").contains("checked")) {
            sso.click();
        }
        WebElement usageType = driver.findElement(By.xpath("(//select[@id='inputState'])[2]"));
        Select select1 = new Select(usageType);
        select1.selectByVisibleText("Minutes");
        WebElement minutesPurchased = driver.findElement(By.id("inputminutesPurchased"));
        minutesPurchased.sendKeys("100");
        WebElement instructPurchased = driver.findElement(By.id("inputinstructSessionPurchased"));
        instructPurchased.sendKeys("100");
        WebElement licensedSubscribers = driver.findElement(By.xpath("//input[@placeholder='Licensed Subscribers']"));
        licensedSubscribers.sendKeys("10");
        WebElement licencedExperiences = driver.findElement(By.xpath("//input[@placeholder='Licensed Experiences']"));
        licencedExperiences.sendKeys("10");
        WebElement sterm = driver.findElement(By.id("inputSubscriptionTerm"));
        sterm.sendKeys("10");
        WebElement ai = driver.findElement(By.xpath("//input[@id='aiEnabled']"));
        ai.click();
        WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
        submit.click();

        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the Tenant Admin and sent the Initial Invite Email']")));
        boolean exp2 = popup.isDisplayed();
        Assert.assertTrue(exp2);
        String b64model = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
        driver.navigate().refresh();
        WebElement newTenant = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@data-automation=\"tenantItem\"])[1]")));
        Actions act = new Actions(driver);
        act.moveToElement(newTenant).perform();
        WebElement viewTenant = driver.findElement(By.xpath("//span[@class='viewRecord']"));
        viewTenant.click();
        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        for (String temp : childWindows) {
            if (!temp.equals(parentWindow)) {
                driver.switchTo().window(temp);
            }
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        WebElement primaryAdmin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//strong[text()='Primary Tenant Administrator']/following::strong)[1]")));
        if (primaryAdmin.getText().contains(testEmail8)) {
            String base64model2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.info("Tenant is created").addScreenCaptureFromBase64String(base64model2);
        }
        WebElement platform = driver.findElement(By.xpath("//strong[text()='Platform']"));
        boolean exp3 = platform.isDisplayed();
        Assert.assertTrue(exp3);
        WebElement minutes = driver.findElement(By.xpath("//strong[text()='Minutes']"));
        boolean exp4 = minutes.isDisplayed();
        Assert.assertTrue(exp4);
    }

    @Test(priority = 9,enabled = true)
    @Parameters({"userPicker","testEmail9"})
    public void TC09(String userPicker, String testEmail9) throws InterruptedException {
        test = extent.createTest("TC09","Test Case for plan type:Platform-user-pro,AI-disabled").info("userDetail" +userPicker);
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://carear-us-qa.web.app/#/super/add-tenant");
        String [] valuesToSend = {"","Test","automation","testing"};
        List<WebElement> texts = driver.findElements(By.xpath("//input[@type='text']"));
        for(int i= 1; i<=texts.size() && i<valuesToSend.length; i++){
            texts.get(i).sendKeys(valuesToSend[i]);
        }
        WebElement userEmail = driver.findElement(By.xpath("(//input[@id='inputEmail'])[2]"));
        userEmail.sendKeys(testEmail9);
        WebElement planType = driver.findElement(By.id("inputState"));
        Select select = new Select(planType);
        select.selectByVisibleText("Platform");

       ;
        WebElement vVerification = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[1]"));
        if(vVerification.getAttribute("class").contains("checked")){
                vVerification.click();}
        WebElement snow = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[2]"));
        if(!snow.getAttribute("class").contains("checked")){
            snow.click();
        }
        WebElement sfdc = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[3]"));
        if(!sfdc.getAttribute("class").contains("checked")){
            sfdc.click();
        }
        WebElement sso = driver.findElement(By.xpath("(//div[contains(@class,'checkbox-container ')])[4]"));
        if(!sso.getAttribute("class").contains("checked")){
            sso.click();
        }
        WebElement instructPurchased = driver.findElement(By.id("inputinstructSessionPurchased"));
        instructPurchased.sendKeys("100");
        WebElement licensedSubscribers = driver.findElement(By.xpath("//input[@placeholder='Licensed Subscribers']"));
        licensedSubscribers.sendKeys("10");
        WebElement licencedExperiences = driver.findElement(By.xpath("//input[@placeholder='Licensed Experiences']"));
        licencedExperiences.sendKeys("10");
        WebElement sterm = driver.findElement(By.id("inputSubscriptionTerm"));
        sterm.sendKeys("10");
        WebElement ai = driver.findElement(By.xpath("//input[@id='aiEnabled']"));
        ai.click();
        WebElement submit = driver.findElement(By.cssSelector("button[type='submit']"));
        submit.click();

        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Successfully created the Tenant Admin and sent the Initial Invite Email']")));
        boolean exp2 = popup.isDisplayed();
        Assert.assertTrue(exp2);
        String b64model = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        test.addScreenCaptureFromBase64String(b64model);
        driver.navigate().refresh();
        WebElement newTenant = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//tr[@data-automation=\"tenantItem\"])[1]")));
        Actions act = new Actions(driver);
        act.moveToElement(newTenant).perform();
        WebElement viewTenant = driver.findElement(By.xpath("//span[@class='viewRecord']"));
        viewTenant.click();
        String parentWindow = driver.getWindowHandle();
        Set<String> childWindows = driver.getWindowHandles();
        for(String temp : childWindows){
            if(!temp.equals(parentWindow)){
                driver.switchTo().window(temp);
            }
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        js.executeScript("window.scrollBy(0,500)");
        WebElement primaryAdmin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//strong[text()='Primary Tenant Administrator']/following::strong)[1]")));
        if(primaryAdmin.getText().contains(testEmail9)){
            String base64model2 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
            test.info("Tenant is created").addScreenCaptureFromBase64String(base64model2);
        }
        WebElement platform = driver.findElement(By.xpath("//strong[text()='Platform']"));
        boolean exp3 = platform.isDisplayed();
        Assert.assertTrue(exp3);
        WebElement user = driver.findElement(By.xpath("//strong[text()='User']"));
        boolean exp4 = user.isDisplayed();
        Assert.assertTrue(exp4);
    }
}




