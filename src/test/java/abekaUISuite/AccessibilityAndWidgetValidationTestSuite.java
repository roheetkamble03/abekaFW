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

import static constants.DataProviderName.PARENT_CREDENTIALS;
import static constants.DataProviderName.STUDENT_CREDENTIALS;

public class AccessibilityAndWidgetValidationTestSuite extends GenericAction {
    DashboardScreen dashboardScreen = new DashboardScreen();
    StudentsScreen studentsScreen;

    @DataRowNumber(fromDataRowNumber = "1",toDataRowNumber = "1")
    @Test(testName = "testValidateParentDashboardAccessControl", dataProvider = PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateParentDashboardAccessControl(String userId, String password, String userName, String signature){
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        new AbekaHomeScreen().validatedURLContent(AbekaHome.PARENT_ACCESS_CONTROL_URL_CONTENT);
        logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1",toDataRowNumber = "1")
    @Test(testName = "testValidateStudentDashboardAccessControl", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentDashboardAccessControl(String userId, String password, String userName, String signature) {
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        new AbekaHomeScreen().validatedURLContent(AbekaHome.STUDENT_ACCESS_CONTROL_URL_CONTENT);
        logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1",toDataRowNumber = "1")
    @Test(testName = "validateDashBoardWidgets", dataProvider = DataProviderName.PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void validateDashBoardWidgets(String userId, String password, String userName, String signature){
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.validateDashboardNewTab().validateMyOrdersWidgetLinks().validateVideoManualPdfsLinks().validateNotificationRows();;
        softAssertions.assertAll();
    }

    @Test(testName = "testValidateGraduationPetitionFunctionality", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateGraduationPetitionFunctionality(String userId, String password, String userName, String signature) {
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.navigateToGraduationPetitionPage().startPetition().fillGraduationPetitionForm();
        logoutFromAbeka();
        softAssertions.assertAll();
    }
}
