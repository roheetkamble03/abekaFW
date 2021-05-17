package abekaUISuite;

import base.GenericAction;
import constants.RecentGrades;
import constants.StudentToDoList;
import dataProvider.DataProviders;
import elementConstants.AbekaHome;
import elementConstants.Dashboard;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.DashboardScreen;
import pageObjects.StudentsScreen;
import utility.RetryUtility;

import java.util.ArrayList;

public class ParentTestCaseSuite extends GenericAction {
    DashboardScreen dashboardScreen = new DashboardScreen();
    StudentsScreen studentsScreen;

    @Parameters({"browser", "platform"})
    @BeforeMethod
    public void setUp(String browserName, String platform) {
        super.setUp(browserName, platform);
        getStudentToDoListFromDB("test");
    }

    @Test(testName = "Test-4", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testManageStudentInformationFromDashboard(String userId, String password, String userName){
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup().navigateToMyStudentProfile(Dashboard.STUDENT_NAME).validateStudentInformationSection(Dashboard.STUDENT_NAME);
    }

    @Test(testName = "Test-5", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentAssessmentDetailsOnCalendar(String userId, String password, String userName){
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup().navigateToMyStudentProfile(Dashboard.STUDENT_NAME);
        //need to check navigation part
    }

    @Test(testName = "Test-6", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testModifyStudentAssessmentDetailsOnCalendar(String userId, String password, String userName){
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup().navigateToMyStudentProfile(Dashboard.STUDENT_NAME);
        //need to check navigation part
    }

    @Test(testName = "Test-7", dataProvider = "studentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentDashboardData(String userId, String password, String userName){
        StudentsScreen studentsScreen = new StudentsScreen();
        ArrayList<StudentToDoList> studentToDoLists = new ArrayList<>();//getStudentToDoListFromDB(Dashboard.STUDENT_NAME);

        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        studentsScreen.validateMyToDoListData(studentToDoLists).validateAccountInfoPage().validateRequestTranScriptFunctionality(Dashboard.STUDENT_NAME).validateMyRecentGrades(new RecentGrades());
        //Validation of dashboard page pending
    }

    @Test(testName = "Test-8", dataProvider = "studentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentAbleToWatchVideoStreaming(String userId, String password, String userName){
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        //Validation of dashboard page pending
    }



}
