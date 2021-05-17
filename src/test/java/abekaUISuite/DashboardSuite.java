package abekaUISuite;

import base.GenericAction;
import dataProvider.DataProviders;
import elementConstants.AbekaHome;
import org.testng.annotations.Test;
import pageObjects.DashboardScreen;

public class DashboardSuite extends GenericAction {

    DashboardScreen dashboardScreen = new DashboardScreen();

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateAccessControlForParent(String userId, String password, String userName) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateDashboardNewTab();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardMyOrderWidgetsLinks(String userId, String password, String userName) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateMyOrdersWidgetLinks();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardVideoManualPdfsWidgetLinks(String userId, String password, String userName) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateVideoManualPdfsLinks();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardNotificationWidgetLinks(String userId, String password, String userName) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateNotificationRows();
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
    public void validateDashboardMyStudentsWidgetLinks(String userId, String password, String userName) {
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup().validateDashboardMyStudentLink();
    }
}
