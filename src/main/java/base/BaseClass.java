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
import lombok.Getter;
import lombok.SneakyThrows;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.TimeoutException;
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

    public static Properties properties;
    @Getter
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    public static SoftAssertions softAssertions;
    private static final ThreadLocal<WebDriver> threadLocalDriver = new InheritableThreadLocal<>();
    private static WebDriver driver;
    public static String domainURL;
    public static String afterLoginURL;
    public static String browser;
    protected Map<String,WebDriver> sessionMap = new HashMap<>();
    protected DesiredCapabilities desiredCapabilities;
    protected int pageLoadTimeOut;
    protected int elementLoadWait;
    int commonWait;
    protected int pollingTimeOut;
    String grid;
    String platform;
    boolean isLocalRun = false;
    String testMethodName;
    static String username = "rohit.kamble%40pcci.edu";
    static String authkey = "u26c428039154c57";

    public void log(String message){
        getLogger().info(message);
    }

    @BeforeMethod
    protected void setUp(String browserName, String platform){
        log("Setting up the test");
        browser = browserName;
        softAssertions = new SoftAssertions();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
        launchApp(grid,browserName, this.platform);
        //Allure report writing will be done letter
        log("setUp done successfully");
    }

    @AfterMethod
    public void tearDown(){
        log("Tearing down the test");
        quitEachDriver(sessionMap);
        SelenideLogger.removeListener("allure");
        log("Tearing down test finished");
    }
    @BeforeSuite
    public void prerequisiteSetup(){
        loadConfig();
    }

    private void loadConfig() {
        try {
        log("Loading config properties");
        properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir") + implementPath("\\src\\main\\java\\configuration\\config.properties"));
        properties.load(fileInputStream);
        pageLoadTimeOut = Integer.parseInt(properties.getProperty(CommonConstants.PAGE_LOAD_TIME_OUT));
        elementLoadWait = Integer.parseInt(properties.getProperty(CommonConstants.ELEMENT_LOAD_WAIT));
        commonWait = Integer.parseInt(properties.getProperty(CommonConstants.COMMON_WAIT));
        pollingTimeOut = Integer.parseInt(properties.getProperty(CommonConstants.POLLING_TIME_OUT));
        grid = properties.getProperty(CommonConstants.GRID);
        domainURL = properties.getProperty(CommonConstants.URL);
        afterLoginURL = properties.getProperty(CommonConstants.AFTER_LOGIN_URL);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static WebDriver getDriver(){
        return WebDriverRunner.getWebDriver();
    }

    private void setBrowserProperties(){
        log("Setting selenide driver");
        setSelenideDriver(driver);
        log("Setting window maximize");
        getDriver().manage().window().maximize();
        //Delete all the cookies
        log("Deleting all cookies");
        getDriver().manage().deleteAllCookies();
        //Implicit TimeOuts
        log("setting time out with with common wait");
        getDriver().manage().timeouts().implicitlyWait
                (commonWait, TimeUnit.SECONDS);
        //PageLoad TimeOuts
        log("Setting page load timeout");
        getDriver().manage().timeouts().pageLoadTimeout
                (pageLoadTimeOut, TimeUnit.SECONDS);
        //Launching the URL
        try {
            log("Opening domain URL");
            getDriver().get(domainURL);
        }catch (TimeoutException e){
            if(!browser.equals(SAFARI)){
                tearDown();
            }
        }
        log("Setting driver in default session");
        sessionMap.put("DefaultSession",getDriver());
    }

    private void quitEachDriver(Map<String,WebDriver> driverMap){
        for(String driverKey:driverMap.keySet()){
            driverMap.get(driverKey).quit();
        }
    }

    public void launchApp(String grid, String browser, String platform){
        switch (grid.toLowerCase()){
            case CommonConstants.LOCAL:
                isLocalRun = true;
                launchAppOnLocalMachine(browser);
                break;
            case CommonConstants.CROSS_BROWSER:
                isLocalRun = false;
                launchAppOnCrossBrowser(browser,platform);
        }
    }

    @SneakyThrows
    public void launchAppOnCrossBrowser(String browser, String platform){
        setDesiredCapabilities(browser, platform);
        RemoteWebDriver driver = new RemoteWebDriver(new URL("http://" + username + ":" + authkey + CommonConstants.CROSS_BROWSER_URL), desiredCapabilities);
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
        if(platform == null){
            desiredCapabilities.setCapability("platform", platform);
        }
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

    private static void setThreadLocalDriver(WebDriver driver){
        threadLocalDriver.set(driver);
    }
    private void setSelenideDriver(WebDriver driver){
        setThreadLocalDriver(driver);
        WebDriverRunner.setWebDriver(threadLocalDriver.get());
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

    /**
     * Takes a snapshot of the screen for the specified test.
     * The output of this function can be used as a parameter for setDescription()
     */
    public String takeSnapshot(String seleniumTestId) throws UnirestException {
               HttpResponse<JsonNode> response = Unirest.post("http://crossbrowsertesting.com/api/v3/selenium/{seleniumTestId}/snapshots")
                .basicAuth(username, authkey)
                .routeParam("seleniumTestId", seleniumTestId)
                .asJson();
        // grab out the snapshot "hash" from the response
        String snapshotHash = (String) response.getBody().getObject().get("hash");

        return snapshotHash;
    }

    /**
     * sets the description for the given seleniemTestId and snapshotHash
     */
    public JsonNode setDescription(String seleniumTestId, String snapshotHash, String description) throws UnirestException{
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
