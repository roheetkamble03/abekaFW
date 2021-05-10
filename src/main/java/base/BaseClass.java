package base;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import constants.CommonConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.Data;
import lombok.Getter;
import lombok.SneakyThrows;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import static org.openqa.selenium.remote.BrowserType.*;

public abstract class BaseClass {

    Properties properties;
    @Getter
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    public static SoftAssertions softAssertions;
    protected Map<String,WebDriver> sessionMap = new HashMap<>();
    DesiredCapabilities desiredCapabilities;
    int pageLoadTimeOut;
    int elementLoadWait;
    int commonWait;
    int pollingTimeOut;
    String grid;
    String platform;
    public static WebDriver driver;
    static String username = "rohit.kamble%40pcci.edu";
    static String authkey = "u26c428039154c57";
    String testResult = "unset";
    boolean isLocalRun = false;
    String testMethodName;

    public void log(String message){
        getLogger().info(message);
    }

    @BeforeMethod
    protected void setUp(String browserName, String platform){
        log("Setting up the test");
        softAssertions = new SoftAssertions();
        SelenideLogger.addListener("allure", new AllureSelenide());
        launchApp(grid,browserName, this.platform);
        //Allure report writing will be done letter
        log("Setting up test finished");
    }

    @AfterMethod
    public void tearDown(){
        log("Tearing down the test");
        softAssertions.assertAll();
        SelenideLogger.removeListener("allure");
        quitEachDriver(sessionMap);
        log("Tearing down test finished");
    }
    @BeforeSuite
    public void loadConfig(){
        try {
            log("Loading config properties");
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + implementPath("\\src\\main\\java\\configuration\\config.properties"));
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

    private void setBrowserProperties(){
        setSelenideDriver(driver);
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

    public void launchApp(String grid, String browser, String platform){
        switch (grid.toLowerCase()){
            case CommonConstants.local:
                isLocalRun = true;
                launchAppOnLocalMachine(browser);
                break;
            case CommonConstants.crossBrowser:
                isLocalRun = false;
                launchAppOnCrossBrowser(browser,platform);

        }
    }

    @SneakyThrows
    public void launchAppOnCrossBrowser(String browser, String platform){
        setDesiredCapabilities(browser, platform);
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://" + username + ":" + authkey + CommonConstants.crossBrowserUrl), desiredCapabilities);
        setSelenideDriver(driver);
    }

    @BeforeMethod
    public void setTestMethodName(Method m) {
        testMethodName = m.getName();
    }

    public void setDesiredCapabilities(String browser, String platform){
        desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("name", testMethodName);
        desiredCapabilities.setCapability("build", "1.0");
        desiredCapabilities.setCapability("browserName", browser);
        desiredCapabilities.setCapability("version", "72");
        desiredCapabilities.setCapability("platform", platform);
        desiredCapabilities.setCapability("screenResolution", "1366x768");
        desiredCapabilities.setCapability("record_video", "true");
        desiredCapabilities.setCapability("record_network", "false");
    }

    @SneakyThrows
    public void launchAppOnLocalMachine(String browser) {
        log("Launching browser");
        switch (browser.toLowerCase()) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
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

    private void setSelenideDriver(WebDriver driver){
        WebDriverRunner.setWebDriver(driver);
    }

    public JsonNode setScore(String seleniumTestId, String score) throws UnirestException {
        // Mark a Selenium test as Pass/Fail
        HttpResponse<JsonNode> response = Unirest.put("http://crossbrowsertesting.com/api/v3/selenium/{seleniumTestId}")
                .basicAuth(username, authkey)
                .routeParam("seleniumTestId", seleniumTestId)
                .field("action","set_score")
                .field("score", score)
                .asJson();
        return response.getBody();
    }

    public String takeSnapshot(String seleniumTestId) throws UnirestException {
        /*
         * Takes a snapshot of the screen for the specified test.
         * The output of this function can be used as a parameter for setDescription()
         */
        HttpResponse<JsonNode> response = Unirest.post("http://crossbrowsertesting.com/api/v3/selenium/{seleniumTestId}/snapshots")
                .basicAuth(username, authkey)
                .routeParam("seleniumTestId", seleniumTestId)
                .asJson();
        // grab out the snapshot "hash" from the response
        String snapshotHash = (String) response.getBody().getObject().get("hash");

        return snapshotHash;
    }

    public JsonNode setDescription(String seleniumTestId, String snapshotHash, String description) throws UnirestException{
        /*
         * sets the description for the given seleniemTestId and snapshotHash
         */
        HttpResponse<JsonNode> response = Unirest.put("http://crossbrowsertesting.com/api/v3/selenium/{seleniumTestId}/snapshots/{snapshotHash}")
                .basicAuth(username, authkey)
                .routeParam("seleniumTestId", seleniumTestId)
                .routeParam("snapshotHash", snapshotHash)
                .field("description", description)
                .asJson();
        return response.getBody();
    }

    public String implementPath(String path){
        String fp = File.separator;
        return path.replaceAll("\\\\",fp+fp);
    }
}
