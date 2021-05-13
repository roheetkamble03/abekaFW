package abekaUISuite;

import base.GenericAction;
import dataProvider.DataProviders;
import elementConstants.AbekaHome;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.DashboardScreen;
import utility.RetryUtility;

public class ParentTestCaseSuite extends GenericAction {
    DashboardScreen dashboardScreen = new DashboardScreen();

    @Parameters({"browser", "platform"})
    @BeforeMethod
    public void setUp(String browserName, String platform) {
        super.setUp(browserName, platform);
    }

    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testManageStudentInformationFromDashboard(String userId, String password){
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
    }

}
