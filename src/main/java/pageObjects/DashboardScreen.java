package pageObjects;

import base.GenericAction;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import constants.Calendar;
import constants.CommonConstants;
import elementConstants.Dashboard;
import elementConstants.Enrollments;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static com.codeborne.selenide.Selenide.*;

public class DashboardScreen extends GenericAction {
    public DashboardScreen validateDashboardNewTab(){
        ArrayList<String> tabList = getAllAvailableBrowserTab();
        getDriver().switchTo().window(tabList.get(1));
        softAssertions.assertThat(tabList.size() == Dashboard.TAB_COUNT)
                .as("Actual total tab count ["+tabList.size()+"] is not equal to expected tab count ["+Dashboard.TAB_COUNT +"]").isTrue();
        softAssertions.assertThat(getPageTitle().equals(Dashboard.TAB_TITLE))
                .as("Actual page title ["+ getDriver().getTitle()+"] is not equal to expected page title ["+Dashboard.TAB_TITLE+"]").isTrue();
        softAssertions.assertThat(getCurrentURL().equals(afterLoginURL+Dashboard.URL))
                .as("Actual page URL ["+ getCurrentURL()+"] is not equal to expected page URL ["+afterLoginURL+Dashboard.URL+"]").isTrue();
        return this;
    }

    public DashboardScreen navigateToMyOrderLink(String link){
        bringElementIntoView(String.format(Dashboard.myOrdersLinks, link.replaceAll("\\s","").replaceAll("Clip",""),link)).click();
        waitForPageTobLoaded();
        return this;
    }

    public DashboardScreen validateMyOrdersWidgetLinks(){
        ArrayList<String> linkList = new ArrayList<>(Arrays.asList(Dashboard.ENROLLMENTS,Dashboard.DIGITAL_CLIP_ART,Dashboard.DIGITAL_ASSESSMENTS,Dashboard.DIGITAL_TEXTBOOKS,Dashboard.DIGITAL_TEACHING_AIDS));
        String pageTitle;
        String pageUrl;
        if(isElementExists(Dashboard.MY_ORDERS)){
            bringElementIntoView(Dashboard.MY_ORDERS);
            for(String link:linkList){
                try {
                    navigateToMyOrderLink(link);
                    pageTitle = String.format(Dashboard.MY_ABEKA_NAV_PAGE_TITLE, link);
                    pageUrl = String.format(Dashboard.MY_ABEKA_NAV_PAGE_URL, afterLoginURL, link.replaceAll("\\s","").replaceAll("Clip",""));
                    softAssertions.assertThat(getPageTitle().equals(pageTitle))
                            .as("Navigated [" + link + "] link page's actual title [" + getPageTitle() + "] is not equals to expected page title [" + pageTitle + "]").isTrue();
                    softAssertions.assertThat(getCurrentURL().equals(pageUrl))
                            .as("Navigated [" + link + "] link page's actual URL [" + getCurrentURL() + "] is not equals to expected page URL [" + pageUrl + "]").isTrue();
                    back();
                }catch (ElementNotFound e){
                    softAssertions.fail(link +" is not present in My Orders widget");
                    continue;
                }
            }
        }else{
            Assert.fail(Dashboard.MY_ORDERS + " widget is not present on Dashboard page");
        }
        return this;
    }

    public DashboardScreen validateVideoManualPdfsLinks(){
        String linkHref;
        String linkText;

        if(isElementExists(Dashboard.VIDEO_MANUAL_PDFS)){
            bringElementIntoView(Dashboard.VIDEO_MANUAL_PDFS);
            try {
                for (SelenideElement videoLink : getElements(Dashboard.videoManualPdfLink)) {
                    linkText = getElementText(videoLink);
                    linkHref = getHrefLink(videoLink);
                    bringElementIntoView(videoLink).click();
                    switchToLastOrNewWindow();
                    softAssertions.assertThat(getCurrentURL().equals(linkHref))
                            .as("Navigation to [" + linkText + "] is failed").isTrue();
                    closeWindow();
                    switchToLastOrNewWindow();
                }
            }catch (ElementNotFound e){
                    softAssertions.fail("Link navigation failed in "+Dashboard.VIDEO_MANUAL_PDFS);
            }
        }else{
            Assert.fail(Dashboard.VIDEO_MANUAL_PDFS + " widget is not present on Dashboard page");
        }
        return this;
    }

    public void validateNotificationRows(){
        if(isElementExists(Dashboard.NOTIFICATIONS)){
            bringElementIntoView(Dashboard.NOTIFICATIONS);
            try {
                softAssertions.assertThat(getElements(Dashboard.notificationLinks).size()!=0)
                        .as("Notification links not available in "+Dashboard.NOTIFICATIONS).isTrue();
            }catch (ElementNotFound e){
                softAssertions.fail(Dashboard.NOTIFICATIONS+" links scrolling failed");
            }
        }else{
            Assert.fail(Dashboard.NOTIFICATIONS + " widget is not present on Dashboard page");
        }
    }

    public void validateDashboardMyStudentLink() {
        if (isElementExists(Dashboard.MY_STUDENTS)) {
            bringElementIntoView(Dashboard.MY_STUDENTS);
            navigateToMyStudentProfile(Dashboard.STUDENT_NAME);
            softAssertions.assertThat(getPageTitle().equals(Dashboard.STUDENT_TAB_TITLE))
                    .as("Actual page title [" + getDriver().getTitle() + "] is not equal to expected page title [" + Dashboard.STUDENT_TAB_TITLE + "]").isTrue();
            softAssertions.assertThat(getCurrentURL().equals(afterLoginURL + Dashboard.STUDENT_TAB_URL))
                    .as("Actual page URL [" + getCurrentURL() + "] is not equal to expected page URL [" + afterLoginURL + Dashboard.STUDENT_TAB_URL + "]").isTrue();
        }else {
            Assert.fail(Dashboard.MY_STUDENTS+" widget is not present on Dashboard page");
        }
    }

    public DashboardScreen waitAndCloseWidgetTourPopup(){
        try {
            waitForElementTobeExist(Dashboard.widgetTourPopupClose);
            click(Dashboard.widgetTourPopupClose);
        }catch (Throwable e){
            log(Dashboard.widgetTourPopupClose + " is not loaded.");
        }
        return this;
    }

    public StudentsScreen navigateToMyStudentProfile(String studentName){
        bringElementIntoView(getElement(String.format(Dashboard.studentLink, studentName))).click();
        waitForPageTobLoaded();
        return new StudentsScreen();
    }

    public CalendarScreen navigateToFullCalendarView(){
        click(Dashboard.FULL_CALENDAR_VIEW);
        waitForPageTobLoaded();
        return new CalendarScreen();
    }
}
