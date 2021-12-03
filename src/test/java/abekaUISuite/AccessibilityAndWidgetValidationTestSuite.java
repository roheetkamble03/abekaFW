package abekaUISuite;

import base.GenericAction;
import constants.DataProviderName;
import dataProvider.DataProviders;
import dataProvider.DataRowNumber;
import elementConstants.AbekaHome;
import org.testng.annotations.Test;
import pageObjects.AbekaHomeScreen;
import pageObjects.DashboardScreen;
import pageObjects.StudentsScreen;
import utility.RetryUtility;

import static constants.DataProviderName.*;

public class AccessibilityAndWidgetValidationTestSuite extends GenericAction {
    DashboardScreen dashboardScreen = new DashboardScreen();
    StudentsScreen studentsScreen;

    @DataRowNumber(fromDataRowNumber = "1",toDataRowNumber = "1")
    @Test(testName = "testValidateParentDashboardAccessControl", dataProvider = PARENT_CREDENTIALS_GENERIC, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateParentDashboardAccessControl(String userId, String password, String userName, String signature){
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        new AbekaHomeScreen().validatedURLContent(AbekaHome.PARENT_ACCESS_CONTROL_URL_CONTENT);
        logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1",toDataRowNumber = "1")
    @Test(testName = "testValidateStudentDashboardAccessControl", dataProvider = STUDENT_CREDENTIALS_GENERIC, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentDashboardAccessControl(String userId, String password, String userName, String signature, String cartNumber) {
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        new AbekaHomeScreen().validatedURLContent(AbekaHome.STUDENT_ACCESS_CONTROL_URL_CONTENT);
        logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1",toDataRowNumber = "1")
    @Test(testName = "validateDashBoardWidgets", dataProvider = DataProviderName.PARENT_CREDENTIALS_GENERIC, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void validateDashBoardWidgets(String userId, String password, String userName, String signature){
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.validateDashboardNewTab().validateMyOrdersWidgetLinks().validateVideoManualPdfsLinks().validateNotificationRows();;
        softAssertions.assertAll();
    }
}
