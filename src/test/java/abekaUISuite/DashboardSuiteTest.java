package abekaUISuite;

import base.GenericAction;
import constants.StudentDetails;
import dataProvider.DataProviders;
import elementConstants.AbekaHome;
import org.testng.annotations.Test;
import pageObjects.DashboardScreen;

public class DashboardSuiteTest extends GenericAction {

    DashboardScreen dashboardScreen = new DashboardScreen();

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateAccessControlForParent(String userId, String password, String userName) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.validateDashboardNewTab();
        softAssertions.assertAll();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardMyOrderWidgetsLinks(String userId, String password, String userName) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.validateMyOrdersWidgetLinks();
        softAssertions.assertAll();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardVideoManualPdfsWidgetLinks(String userId, String password, String userName) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.validateVideoManualPdfsLinks();
        softAssertions.assertAll();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardNotificationWidgetLinks(String userId, String password, String userName) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.validateNotificationRows();
        softAssertions.assertAll();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardMyStudentsWidgetLinks(String userId, String password, String userName) {
        dashboardScreen = new DashboardScreen();
        StudentDetails studentDetails = getStudentAccountDetails(1);
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.validateDashboardMyStudentLink(studentDetails.getFirstName());
        softAssertions.assertAll();
    }
}
