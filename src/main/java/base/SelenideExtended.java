package base;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import constants.CommonConstants;
import constants.EnumUtil;
import elementConstants.AbekaHome;
import elementConstants.Dashboard;
import elementConstants.Students;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.remote.BrowserType.SAFARI;

public abstract class SelenideExtended extends DatabaseExtended {
    String textXpath = "(//*[normalize-space(text())='%s' or normalize-space(@value)='%s']|//text()[normalize-space()='%s'])[1]";
    String textContainsXpath = "(//*[contains(normalize-space(text()),'%s') or contains(normalize-space(@value),'%s')]|//text()[contains(normalize-space(),'%s')])[1]";
    public static String childTextXpath = "xpath=./*[normalize-space(text())='%s' or normalize-space(@value)='%s']|./text()[normalize-space()='%s']";
    protected static String childTextContainsXpath = "xpath./*[contains(normalize-space(text()),'%s') or contains(normalize-space(@value),'%s')]|./text()[contains(normalize-space(),'%s')]";
    SelenideElement element;
    public static String tempXpath;
    public static String tempText;

    public void click(String identifier){
        waitForElementTobeExist(identifier);
        try {
            if(browser.equals(SAFARI)){
                clickByJavaScript(identifier);
            }else {
                click(bringElementIntoView(identifier));
            }
        }catch (Throwable e){
            log("Clicked by java script");
            clickByJavaScript(getElement(identifier));
        }
        waitForPageTobeLoaded();
        log("Clicked on "+identifier);
    }

    public void clickWithoutPageLoadWait(String identifier){
        waitForElementTobeExist(identifier);
        try {
            click(bringElementIntoView(identifier));
        }catch (Throwable e){
            log("Clicked by java script");
            clickByJavaScript(getElement(identifier));
        }
        log("Clicked on "+identifier);
    }

    public void click(SelenideElement element){
        log("Clicking on element");
        waitForElementTobeExist(element);
        try {
            if(browser.equals(SAFARI)) {
                log("Clicked by java script for safari browser");
                clickByJavaScript(element);
            }else {
                element.click();
            }
        }catch (Throwable e){
            log("Clicked by java script");
            clickByJavaScript(element);
        }
    }

    public boolean isElementDisplayed(String identifier) {
        try {
            return getElement(identifier).is(Condition.visible);
        }catch (ElementNotFound e){
            return false;
        }
    }

    public boolean isElementDisplayed(SelenideElement element) {
        try {
            return element.is(Condition.visible);
        }catch (ElementNotFound e){
            return false;
        }
    }

    public boolean isElementDisplayed(SelenideElement parentElement, String childIdentifier) {
        try {
            bringChildElementIntoView(parentElement,childIdentifier);
            return getChildElement(parentElement,childIdentifier).isDisplayed();
        }catch (ElementNotFound e){
            return false;
        }
    }

    public boolean isElementExists(String identifier) {
        try {
            waitForElementTobeExist(identifier);
            return true;
        }catch (Throwable e){
            return false;
        }
    }

    public boolean isChildElementExists(SelenideElement parentElement, String childIdentifier){
        try{
            try{
            $(parentElement.findElement(getByClause(childIdentifier)));
            }catch (NoSuchFrameException e){
                parentElement.findElement(getByClause(childIdentifier));
            }
            return true;
        }catch (Throwable e){
            return false;
        }
    }

    public boolean isSelected(String identifier) {
        waitForElementTobeExist(identifier);
        return getElement(identifier).isSelected();
    }

    public String getCCPropertyValueByJavaScript(SelenideElement element, String cssAttribute, String property){
        return ((JavascriptExecutor)getDriver()).executeScript("return window.getComputedStyle(arguments[0], '"+cssAttribute+"').getPropertyValue('"+property+"');"
                ,element).toString();
    }

    public String getCCPropertyValueByJavaScript(String identifier, String cssAttribute, String property){
        return ((JavascriptExecutor)getDriver()).executeScript("return window.getComputedStyle(arguments[0], '"+cssAttribute+"').getPropertyValue('"+property+"');"
                ,getElement(identifier)).toString();
    }

    public boolean isEnabled(String identifier) {
        return getElement(identifier).isEnabled();
    }

    /**
     * Type text at location
     *
     * @param identifier element identifier
     * @param text
     * @return - true/false
     */
    public void type(String identifier, String text) {
        bringElementIntoView(identifier);
        try {
            getElement(identifier).setValue(text);
            if(!getElementText(identifier).equals(text)){
                getElement(identifier).setValue(text);
            }
        }catch (Throwable e){
            log("Type by java script");
            typeByJavaScript(identifier,text);
        }
    }

    /**
     * Type text at location
     *
     * @param element element identifier
     * @param text
     * @return - true/false
     */
    public void type(SelenideElement element, String text) {
        bringElementIntoView(element);
        try {
            element.setValue(text);
            if(!getElementText(element).equals(text)){
                element.setValue(text);
            }
        }catch (Throwable e){
            log("Type by java script");
            typeByJavaScript(element,text);
        }
    }

    public void pressTab(SelenideElement element){
        element.sendKeys(Keys.TAB);
    }

    public void pressTab(String identifier){
        getElement(identifier).sendKeys(Keys.TAB);
    }
    public void pressEnter(String identifier){
        getElement(identifier).sendKeys(Keys.ENTER);
    }
    public void pressEnter(SelenideElement element){
        element.sendKeys(Keys.ENTER);
    }

    public void typeByJavaScript(String identifier, String text) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].value='"+text+"';", getElement(identifier));
    }

    public void typeByJavaScript(SelenideElement element, String text) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].value='"+text+"';", element);
    }

    public boolean isElementTextEquals(String identifier, String expectedText){
        return getElementText(identifier).equals(expectedText);
    }

    public boolean isElementTextEquals(SelenideElement element, String expectedText){
        return getElementText(element).equals(expectedText);
    }

    public boolean isElementValueEquals(SelenideElement element, String expectedText){
        return getElementValue(element).equals(expectedText);
    }

    public String getElementText(String identifier){
        try{
            return getElementText(getElement(identifier));
        }catch (Throwable e){
            return "";
        }
    }

    public String getElementValue(SelenideElement element){
        return element.getValue();
    }
    public String getElementText(SelenideElement element){
        for(int i=0;i<4;i++){
                try{
                switch (i) {
                    case 0:
                        if (element.text().length() != 0) {
                            return element.text();
                        }
                    case 1:
                        if (element.getText().length() != 0) {
                            return element.getText();
                        }
                    case 2:
                        if (element.innerText().length() != 0) {
                            return element.innerText();
                        }
                    case 3:
                        String javaScript = "return arguments[0].innerText;";
                        JavascriptExecutor js = (JavascriptExecutor) getDriver();
                        if(js.executeScript(javaScript, element).toString().length()!=0){
                            return js.executeScript(javaScript, element).toString();
                        }
                 }
                }catch (NullPointerException e){
                    continue;
                }
        }
        return "";
    }

    public String getHrefLink(SelenideElement element){
        try {
            return element.getAttribute("href").trim();
        }catch (NullPointerException e){
            return "";
        }
    }

    /**
     *
     * @param identifier element identifier
     * @param value to be selected
     */
    public void selectOptionBySendkeys(String identifier, String value) {
        getElement(identifier).sendKeys(value);
    }

    /**
     * select value from DropDown by using selectByIndex
     *
     * @param identifier element identifier
     *
     * @param index       : Index of value wish to select from dropdown list.
     *
     */
    public void selectByIndex(String identifier, int index) {
            Select s = new Select(getElement(identifier));
            s.selectByIndex(index);
    }

    /**
     * select value from DD by using value
     *
     * @param identifier element identifier
     *
     * @param value       : Value wish to select from dropdown list.
     */
    public void selectByValue(String identifier,String value) {
//            Select s = new Select(getElement(identifier).selectOptionByValue(););
//            s.selectByValue(value);
            getElement(identifier).selectOptionByValue(value);
    }

    /**
     * select value from DropDown by using selectByVisibleText
     *
     * @param identifier element identifier
     *
     * @param visibletext : VisibleText wish to select from dropdown list.
     *
    **/
    public void selectByVisibleText(String identifier, String visibletext) {
            Select s = new Select(getElement(identifier));
            s.selectByVisibleText(visibletext);
    }

    /**
     *
     * @param identifier element identifier
     * @return
     */
    public void mouseHoverByJavaScript(String identifier) {
            SelenideElement element = getElement(identifier);
            String javaScript = "var evObj = document.createEvent('MouseEvents');"
                    + "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
                    + "arguments[0].dispatchEvent(evObj);";
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript(javaScript, element);
    }

    /**
     *
     * @param identifier element identifier
     */
    public void clickByJavaScript(String identifier) {
            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].click();", getElement(identifier));
    }

    /**
     *
     * @param element element identifier
     */
    public void clickByJavaScript(SelenideElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
    }

    /**
     * Switching frame by index
     * @param index
     * @return
     */
    public void switchToFrameByIndex(int index) {
            new WebDriverWait(getDriver(), 15).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe")));
            getDriver().switchTo().frame(index);
    }

    /**
     * This method switch the to frame using frame ID.
     *
     * @param idValue : Frame ID wish to switch
     *
     */
    public void switchToFrameById(String idValue) {
            getDriver().switchTo().frame(idValue);
    }

    /**
     * This method switch the to frame using frame Name.
     *
     * @param nameValue : Frame Name wish to switch
     *
     */
    public void switchToFrameByName(String nameValue) {
            getDriver().switchTo().frame(nameValue);
    }

    /**
     * Switching to default frame
     * @param driver
     */
    public void switchToDefaultFrame(WebDriver driver) {
            getDriver().switchTo().defaultContent();
    }

    /**
     * Mouse over on an element
     * @param identifier element identifier
     */
    public void mouseOverOnElement(String identifier) {
        new Actions(getDriver()).moveToElement(getElement(identifier)).build().perform();
    }

    /**
     * Bringing element into view via java script
     * @param identifier
     * @return
     */
    public SelenideElement bringElementIntoView(String identifier) {
        waitForElementTobeExist(identifier);
        log("bringing element into view:"+identifier);
            for(SelenideElement element:getElements(identifier)){
                try {
                    waitForElementTobeExist(identifier);
                    JavascriptExecutor executor = (JavascriptExecutor) getDriver();
                    executor.executeScript("arguments[0].scrollIntoView({block: \"center\"});", element);
                    Actions actions = new Actions(getDriver());
                    actions.moveToElement(element).build().perform();
                    return element;
                }catch (Throwable t){
                    continue;
                }
            }
            log("Visible element not found for given identifier:" +identifier);
            return getElement(identifier);
    }

    public SelenideElement bringElementIntoView(SelenideElement element) {
        try {
            log("Bringing element into view");
            waitForElementTobeExist(element);
            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].scrollIntoView({block: \"center\"});", element);
            Actions actions = new Actions(getDriver());
            actions.moveToElement(element).build().perform();
        }catch (Throwable t){
            new Throwable("Element not found "+element.toString());
        }
        return element;
    }

    /**
     * Bringing element into view via java script
     * @param identifier
     * @return
     */
    public void bringChildElementIntoView(SelenideElement parentElement, String identifier) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView({block: \"center\"});", getChildElement(parentElement,identifier));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(getChildElement(parentElement,identifier)).build().perform();
    }

    /**
     * Dragging selecting element from one point to another
     * @param identifier
     * @param x
     * @param y
     * @return
     */
    public void dragElementFromOnePointXToAnotherY(String identifier, int x, int y) {
            new Actions(getDriver()).dragAndDropBy(getElement(identifier), x, y).build().perform();
            implicitWaitInSeconds(5);
    }

    /**
     * Dragging element from one element to another element
     * @param source
     * @param target
     * @return
     */
    public void dragAndDropElementFromToElement(String source, String target) {
            new Actions(getDriver()).dragAndDrop(getElement(source), getElement(target)).perform();
    }

    /**
     * Slide element
     * @param identifier
     * @param x
     * @param y
     * @return
     */
    public void slideElement(String identifier, int x, int y) {
            new Actions(getDriver()).dragAndDropBy(getElement(identifier), x, y).build().perform();// 150,0
            implicitWaitInSeconds(5);
    }

    /**
     * Right click on element
     * @param identifier
     * @return
     */
    public void rightClickOnElement(String identifier) {
            Actions clicker = new Actions(getDriver());
            clicker.contextClick(getElement(identifier)).perform();
    }


    /**
     * Switching to window by title
     * @param windowTitle
     * @param count
     * @return
     */
    public void switchWindowByTitle(String windowTitle, int count) {
            Set<String> windowList = getDriver().getWindowHandles();

            String[] array = windowList.toArray(new String[0]);

            getDriver().switchTo().window(array[count-1]);
    }

    /**
     * Switching to new window
     */
    public void switchToLastOrNewWindow() {
            Set<String> s=getDriver().getWindowHandles();
            Object popup[]=s.toArray();
            if(browser.equals(SAFARI)) {
                switchToFirstWindow();
            }else{
                getDriver().switchTo().window(popup[popup.length - 1].toString());
            }
    }

    /**
     * Switching to old window
     */
    public void switchToOldWindow() {
         Set<String> s=getDriver().getWindowHandles();
         Object popup[]=s.toArray();
         getDriver().switchTo().window(popup[0].toString());
    }

    /**
     * Switching to old window
     */
    public void switchToFirstWindow() {
        Set<String> s=getDriver().getWindowHandles();
        Object popup[]=s.toArray();
        if(browser.equals(SAFARI)) {
            getDriver().switchTo().window(popup[popup.length - 1].toString());
        }else {
            getDriver().switchTo().window(popup[0].toString());
        }
    }

    /**
     *
     * @param row
     * @return
     */
    public int getColumncount(String row) {
        List<WebElement> columns = getElement(row).findElements(By.tagName("td"));
        int a = columns.size();
        System.out.println(columns.size());
        for (WebElement column : columns) {
            System.out.print(column.getText());
            System.out.print("|");
        }
        return a;
    }

    /**
     *
     * @param table
     * @return
     */
    public int getRowCount(String table) {
        List<WebElement> rows = getElement(table).findElements(By.tagName("tr"));
        int a = rows.size() - 1;
        return a;
    }


    /**
     * Verify alert present or not
     *
     * @return: Boolean (True: If alert preset, False: If no alert)
     *
     */
    public void acceptAlert() {
        Alert alert = getDriver().switchTo().alert();
            // if present consume the alert
            alert.accept();
    }

    /**
     * Navigates to specified URL
     * @param url
     */
    public void navigateTo(String url) {
        getDriver().navigate().to(url);
    }

    /**
     * Is alert peresent
     * @return
     */
    public boolean isAlertPresent()
    {
        try
        {
            getDriver().switchTo().alert();
            return true;
        }   // try
        catch (NoAlertPresentException Ex)
        {
            return false;
        }   // catch
    }

    /**
     *
     * @return
     */
    public String getPageTitle() {
        boolean flag = false;

        String text = getDriver().getTitle();
        if (flag) {
            System.out.println("Title of the page is: \""+text+"\"");
        }
        return text;
    }

    /**
     *
     * @return
     */
    public String getCurrentURL()  {
        boolean flag = false;

        String text = getDriver().getCurrentUrl();
        if (flag) {
            System.out.println("Current URL is: \""+text+"\"");
        }
        return text;
    }

    /**
     *
     * @param identifier
     */
    public void waitForElementTobeVisible(String identifier) {
        log("Waiting for element to be visible");
        LocalTime waitTime = LocalTime.now().plusSeconds(elementLoadWait);
            while (waitTime.isAfter(LocalTime.now())) {
                try{
                    if(getVisibleElement(identifier)!=null){
                        break;
                    }
                    //getElement(identifier).shouldBe(Condition.visible, Duration.ofSeconds(elementLoadWait));
                }catch (Throwable t){

                }
            }
    }

    public SelenideElement getVisibleElement(String identifier){
        log("Getting visible element:"+identifier);
        for (SelenideElement element: getElements(identifier)){
            implicitWaitInSeconds(1);
            if (isElementDisplayed(element)) {
                log("Visible element found");
                return element;
            }
        }
        return null;
    }

    /**
     *
     * @param identifier
     */
    @SneakyThrows
    public void waitForElementTobeExist(String identifier) {
        try {
            log("Waiting for element to be exist:"+identifier);
            getElements(identifier).get(0).shouldBe(Condition.exist, Duration.ofSeconds(elementLoadWait));
        }catch (Throwable t){
            logger.info("Following Element not found \n" +
                    getByClause(identifier));
            throw new Exception(t);
        }
    }

    /**
     *
     * @param identifier
     */
    public void waitForElementTobeExist(String identifier, int timeInSeconds) {
        getElements(identifier).get(0).shouldBe(Condition.exist,Duration.ofSeconds(timeInSeconds));
    }

    /**
     *
     * @param element
     */
    @SneakyThrows
    public void waitForElementTobeExist(SelenideElement element) {
        try {
            element.shouldBe(Condition.exist,Duration.ofSeconds(elementLoadWait));
        }catch (NullPointerException e){
            throw new Exception("Element not found "+element);
        }
    }

    public void waitForElementTobeEnabled(String identifier){
        getElements(identifier).get(0).shouldBe(Condition.enabled,Duration.ofSeconds(elementLoadWait));
    }

    /**
     *
     * @param timeOutInSeconds
     */
    public void implicitWaitInSeconds(int timeOutInSeconds) {
        try {
            Thread.sleep(timeOutInSeconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param timeOutInMilliSeconds
     */
    public void implicitWaitInMilliSeconds(int timeOutInMilliSeconds) {
        getDriver().manage().timeouts().implicitlyWait(timeOutInMilliSeconds, TimeUnit.MILLISECONDS);
    }

    /**
     *
     * @param identifier
     * @param timeOut
     */
    public void explicitWait(String identifier, int timeOut ) {
        WebDriverWait wait = new WebDriverWait(getDriver(),timeOut);
        wait.until(ExpectedConditions.presenceOfElementLocated(getByClause(identifier)));
    }

    /**
     *
     * @param timeOut
     */
    public void waitTillPageLoad(int timeOut) {
        getDriver().manage().timeouts().pageLoadTimeout(timeOut, TimeUnit.SECONDS);
    }

    /**
     *
     * @param filename
     * @return
     */
    public String screenShot(String filename) {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destination = System.getProperty("user.dir") + "\\ScreenShots\\" + filename + "_" + dateName + ".png";

        try {
            FileUtils.copyFile(source, new File(destination));
        } catch (Exception e) {
            e.getMessage();
        }
        // This new path for jenkins
        String newImageString = "http://localhost:8082/job/MyStoreProject/ws/MyStoreProject/ScreenShots/" + filename + "_"
                + dateName + ".png";
        return newImageString;
    }

    @SneakyThrows
    public SelenideElement getElement(String identifier) {
        try {
            element = $(getByClause(identifier));
        }catch (NoSuchFrameException e){
            element = $(getDriver().findElement(getByClause(identifier)));
        }
        return element;
    }

    public SelenideElement getChildElement(SelenideElement parentElement, String childIdentifier){
        try{
            return $(parentElement.findElement(getByClause(childIdentifier)));
        }catch (Throwable e){
            softAssertions.fail("Element not found \n Element details:\nParent Element:"+ parentElement+"\nChild Element:"+childIdentifier);
            return null;
        }
    }

    @SneakyThrows
    public List<SelenideElement> getElements(String identifier) {
        log("Getting element list");
        try {
            return $$(getByClause(identifier));
        }catch (NoSuchFrameException e){
           return $$(getDriver().findElements(getByClause(identifier)));
        }
    }

    @SneakyThrows
    public By getByClause(String identifier){
        String element;
        if(identifier.startsWith(EnumUtil.XPATH)){
            element = identifier.substring(EnumUtil.XPATH.length());
            return By.xpath(element);
        }
        if(identifier.startsWith(EnumUtil.TEXT)){
            element = identifier.substring(EnumUtil.TEXT.length());
            return By.xpath(String.format(textXpath,element,element,element));
        }
        if(identifier.startsWith(EnumUtil.CONTAINS_TEXT)){
            element = identifier.substring(EnumUtil.CONTAINS_TEXT.length());
            return By.xpath(String.format(textContainsXpath,element,element,element));
        }
        if(identifier.startsWith(EnumUtil.ID_KEY)){
            element = identifier.substring(EnumUtil.ID_KEY.length());
            return By.id(element);
        }
        if(identifier.startsWith(EnumUtil.NAME_KEY)){
            element = identifier.substring(EnumUtil.NAME_KEY.length());
            return By.name(element);
        }
        if(identifier.startsWith(EnumUtil.CLASS_NAME)){
            element = identifier.substring(EnumUtil.CLASS_NAME.length());
            return By.className(element);
        }
        if(identifier.startsWith(EnumUtil.LINK_TEXT)){
            element = identifier.substring(EnumUtil.LINK_TEXT.length());
            return By.linkText(element);
        }
        if(identifier.startsWith(EnumUtil.TAG_NAME)){
            element = identifier.substring(EnumUtil.TAG_NAME.length());
            return By.tagName(element);
        }
        if(identifier.startsWith(EnumUtil.CSS_KEY)){
            element = identifier.substring(EnumUtil.CSS_KEY.length());
            return By.cssSelector(element);
        }
        return By.xpath(String.format(textXpath,identifier.trim(),identifier.trim(),identifier.trim()));
    }

    public void waitForElementTobeDisappear(String identifier) {
        log("Waiting for element to disappear:"+identifier);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime stopTime = currentTime.plusSeconds(elementLoadWait/2);
        while (stopTime.isAfter(currentTime)) {
                try {
                    if (!isElementDisplayed(identifier)) {
                        break;
                    }
                    implicitWaitInMilliSeconds(pollingTimeOut);
                } catch (ElementNotFound | TimeoutException e) {
                    continue;
                }
                currentTime = LocalDateTime.now();
            }
        implicitWaitInSeconds(3);
    }

    public void waitForAbekaBGProcessLogoDisappear(){
        log("Waiting for process log to disappear");
        waitForElementTobeDisappear(AbekaHome.abekaBGProcessLogo);
    }

    public void waitForPageTobeLoaded(){
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), pageLoadTimeOut);
            wait.until(webDriver -> ((JavascriptExecutor) getDriver()).executeScript("return document.readyState").toString().equals("complete"));
        }catch (TimeoutException|UnhandledAlertException e){
            log("Page load time out error :"+e.getMessage());
        }
    }

    public void selectValueFromDropDownVideoLibrary(String dropDownIdentifier, String value){
        bringElementIntoView(dropDownIdentifier).click();
        click(getChildElement(getElement(dropDownIdentifier),String.format(CommonConstants.dropDownOption,value)));
        waitForPageTobeLoaded();
    }

    public String getClassAttributeValue(SelenideElement element){
        try{
            return element.getAttribute("class").trim();
        }catch (Throwable t){
            softAssertions.fail("Either Class attribute is not present or element not found \n Element:" + element);
            return "";
        }
    }

    public boolean waitAndCloseWidgetTourPopup(){
        try {
            waitForElementTobeExist(Dashboard.widgetTourPopupClose,elementLoadWait*2);
            click(Dashboard.widgetTourPopupClose);
            waitForPageTobeLoaded();
            return true;
        }catch (Throwable e){
            log(Dashboard.widgetTourPopupClose + " is not loaded.");
        }
        return false;
    }

    public void navigateToHeaderBannerSubmenu(String menu, String submenu){
        mouseOverOnElement(menu);
        click(bringElementIntoView(String.format(AbekaHome.HEADER_SUB_MENU,submenu)));
    }
}
