package org.example.src.main.java.org.example;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
////

public class WebTest {
    @SneakyThrows
    @Test
    public void testGoogleSearchusingSelenoid(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "MicrosoftEdge");
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
        driver.get("https://google.com.in");
        String actual= driver.getCurrentUrl();
        Assert.assertEquals(actual,"google");
        Thread.sleep(10000);
        driver.quit();
    }
}