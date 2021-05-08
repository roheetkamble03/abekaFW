package base;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import constants.CommonConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.Getter;
import lombok.SneakyThrows;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.remote.BrowserType.*;

public abstract class BaseClass {

    Properties properties;
    @Getter
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    protected SoftAssertions softAssertions = new SoftAssertions();
    @Getter
    protected Map<String,WebDriver> sessionMap = new HashMap<>();
    int pageLoadTimeOut;
    int elementLoadWait;
    int commonWait;
    int pollingTimeOut;
    String grid;
    public static WebDriver driver;

    public void log(String message){
        getLogger().info(message);
    }

    @BeforeMethod
    protected void setUp(){
        log("Setting up the test");
        SelenideLogger.addListener("allure", new AllureSelenide());
        log("Setting up test finished");
    }

    @AfterMethod
    public void tearDown(){
        log("Tearing down the test");
        SelenideLogger.removeListener("allure");
        quitEachDriver(sessionMap);
        log("Tearing down test finished");
    }
    @BeforeSuite
    public void loadConfig(){
        try {
            log("Loading config properties");
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\configuration\\config.properties");
            properties.load(fileInputStream);
            pageLoadTimeOut = Integer.parseInt(properties.getProperty(CommonConstants.pageLoadTimeOut));
            elementLoadWait = Integer.parseInt(properties.getProperty(CommonConstants.elementLoadWait));
            commonWait = Integer.parseInt(properties.getProperty(CommonConstants.commonWait));
            pollingTimeOut = Integer.parseInt(properties.getProperty(CommonConstants.pollingTimeOut));
            grid = properties.getProperty(CommonConstants.grid);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static WebDriver getDriver(){
        return WebDriverRunner.getWebDriver();
    }

    @SneakyThrows
    public void launchApp(String browser) {
        log("Launching browser");
        switch (browser.toLowerCase()) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                //WebDriverRunner.setWebDriver(driver);
                setBrowserProperties();
                break;
            case SAFARI:
                driver = new SafariDriver();
                setBrowserProperties();
                break;
            case IE:
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                setBrowserProperties();
            default:
                throw new Exception("Wrong browser value passed");
        }
    }

    private void setBrowserProperties(){
        WebDriverRunner.setWebDriver(driver);
        getDriver().manage().window().maximize();
        //Delete all the cookies
        getDriver().manage().deleteAllCookies();
        //Implicit TimeOuts
        getDriver().manage().timeouts().implicitlyWait
                (commonWait, TimeUnit.SECONDS);
        //PageLoad TimeOuts
        getDriver().manage().timeouts().pageLoadTimeout
                (pageLoadTimeOut, TimeUnit.SECONDS);
        //Launching the URL
        getDriver().get(properties.getProperty(CommonConstants.URL));
        sessionMap.put("DefaultSession",getDriver());
    }

    private void quitEachDriver(Map<String,WebDriver> driverMap){
        for(String driverKey:driverMap.keySet()){
            driverMap.get(driverKey).quit();
        }
    }
}
