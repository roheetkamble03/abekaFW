package constants;

public @interface CommonConstants {
    String URL = "Url";
    String AFTER_LOGIN_URL = "AfterLoginURL";
    String PAGE_LOAD_TIME_OUT = "PageLoadTimeOut";
    String ELEMENT_LOAD_WAIT = "ElementLoadWait";
    String COMMON_WAIT = "CommonWait";
    String POLLING_TIME_OUT = "PollingTimeOut";
    String GRID = "Grid";
    String LOCAL = "local";
    String CROSS_BROWSER = "crossbrowser";
    String CROSS_BROWSER_URL = "@hub.crossbrowsertesting.com:80/wd/hub";
    String LINK_TEXT = "linkText=";
    String MINUS = "MINUS";
    String PLUS = "PLUS";
    String AUTOMATION_TEST = "Automation Test";
    String LESSON_COMPLETED_CLASS_VALUE = "lessonComplete";

    String DB_CONNECTION_URL = "DBConnectionUrl";
    String DB_USER_NAME = "DBUserName";
    String DB_USER_PASSWORD = "DBPassword";
    String IS_CONNECT_TO_DB = "IsConnectToDB";
    String PORT = "Port";
    String DRIVER_TYPE = "DriverType";
    String ADHost = "ADHost";
    String ADSid = "ADSid";
    String SDHost = "SDHost";
    String SDService = "SDService";
    String AD_DATA_BASE = "AD";
    String SD_DATA_BASE = "SD";

    String closeXpath = "xpath=//*[@id='%s']//button[@type='button' and normalize-space(text())='×']";
    String dropDownOption = "xpath=./ul[@class='dropdown']/descendant::span[normalize-space(text())='%s']";
    Object CLOSE = "Close";
}
