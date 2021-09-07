package abekaUISuite;

import base.GenericAction;
import dataProvider.DataProviders;
import dataProvider.DataRowNumber;
import elementConstants.AbekaHome;
import elementConstants.Dashboard;
import org.testng.annotations.Test;
import pageObjects.AbekaHomeScreen;
import pageObjects.CalendarScreen;
import pageObjects.DashboardScreen;
import pageObjects.StudentsScreen;
import utility.ParentAccountDetails;
import utility.RetryUtility;

import static constants.DataProviderName.PARENT_CREDENTIALS;
import static constants.DataProviderName.STUDENT_CREDENTIALS;
import static elementConstants.Students.*;

public class ParentTestCaseSuiteTest extends GenericAction {
    DashboardScreen dashboardScreen = new DashboardScreen();
    StudentsScreen studentsScreen;

    //Grade 4
    @DataRowNumber(testDataRowNumber = "2")
    @Test(testName = "Test-4", dataProvider = PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testManageStudentInformationFromDashboard(String userId, String password, String userName, String signature){
        setStudentAccountDetailsFromDB("syed.rcg");
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyStudentProfile(Dashboard.STUDENT_NAME).waitAndCloseWidgetTourPopup();
        new StudentsScreen().validateStudentInformationSection(Dashboard.STUDENT_NAME)
             .navigateToProgressReportWidget(Dashboard.STUDENT_NAME).enterStudentGrades(false,false, signature)
                .logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade 4
    @DataRowNumber(testDataRowNumber = "2")
    @Test(testName = "Test-5", dataProvider = PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentAssessmentDetailsOnCalendar(String userId, String password, String userName, String signature){
        CalendarScreen calendarScreen = new CalendarScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetailsFromDB("syed.rcg");
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToFullCalendarView();
        dashboardScreen.waitAndCloseWidgetTourPopup();
        calendarScreen.validateNoStudentIsSelected().selectStudent(Dashboard.STUDENT_NAME).validateStudentCalendarEvents(false).logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade 9
    @DataRowNumber(testDataRowNumber = "3")
    @Test(testName = "Test-6", dataProvider = PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testModifyStudentAssessmentDetailsOnCalendar(String userId, String password, String userName, String signature){
        StudentsScreen studentsScreen = new StudentsScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetailsFromDB("syed.rcg");
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyStudentProfile(Dashboard.STUDENT_NAME).waitAndCloseWidgetTourPopup();
        studentsScreen.navigateToCalendarSettingsWidget().changeStudentAssessmentDetails(SIX_MONTHS_ASSESSMENT,"");
        studentsScreen.navigateToCalendarSettingsWidget().validateStudentAssessmentDetails(SIX_MONTHS_ASSESSMENT,No);
        studentsScreen.navigateToCalendarSettingsWidget().changeStudentAssessmentDetails(NINE_MONTHS_ASSESSMENT,"").logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade one
    @DataRowNumber(testDataRowNumber = "1")
    @Test(testName = "Test-7", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentDashboardData(String userId, String password, String userName, String signature){
        StudentsScreen studentsScreen = new StudentsScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        setStudentAccountDetailsFromDB(userId);
        studentsScreen.validateMyToDoListData().validateAccountInfoPage().
                validateRequestTranScriptFunctionality(Dashboard.STUDENT_NAME).
        validateMyRecentGrades(Dashboard.STUDENT_NAME).verifyLastViewedLessons().logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade 4
    @DataRowNumber(testDataRowNumber = "2")
    @Test(testName = "Test-8", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentAbleToWatchVideoStreaming(String userId, String password, String userName, String signature){
        StudentsScreen studentsScreen = new StudentsScreen();
        AbekaHomeScreen abekaHomeScreen = new AbekaHomeScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetailsFromDB(userId);
        setStudentSubjectDetailsFromDB(userId);
        studentsScreen.markAllVideosAsNotViewed();
        dashboardScreen.waitAndCloseWidgetTourPopup();
        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
        studentsScreen.validateDigitalAssessmentsAreLockedOrNot();
        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.HOME);
        studentsScreen.navigateToStartWatchingYourLessonsLink().validateMyLessonsTodaySectionData().validateDigitalAssessmentsAreLockedOrNot()
                .validateStudentShouldNotAbleToWatchNextDayLessonFromVideoLibrary()
                .watchVideoAndValidateMyLessonsTodaySectionWithVideoLibrary()
                .validateVideoLibraryVideoStatusWithDataBase().validateDigitalAssessmentsAreLockedOrNot();

        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
        studentsScreen.validateDigitalAssessmentsAreLockedOrNot().logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade 9
    @DataRowNumber(testDataRowNumber = "3")
    @Test(testName = "Test-9", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentAbleToCompleteDigitalAssessmentSuccessfully(String userId, String password, String userName, String signature){
        loginToAbeka(userId,password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD).waitAndCloseWidgetTourPopup();
        navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
        setStudentAccountDetailsFromDB(userId);
        new StudentsScreen().answerAndSubmitDigitalAssessment(false);
        softAssertions.assertAll();
    }

    //Grade1
    @DataRowNumber(testDataRowNumber = "1")
    @Test(testName = "Test-10", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentCalendarEvents(String userId, String password, String userName, String signature){
        loginToAbeka(userId,password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD).waitAndCloseWidgetTourPopup();
        navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.CALENDAR);
        new CalendarScreen().validateStudentCalendarEvents(false).logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(testDataRowNumber = "0")
    @Test(testName = "Test-11", dataProvider = PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateParentDashboardAccessControl(String userId, String password, String userName, String signature){
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        new AbekaHomeScreen().validatedURLContent(AbekaHome.PARENT_ACCESS_CONTROL_URL_CONTENT);
        logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(testDataRowNumber = "0")
    @Test(testName = "Test-12", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentDashboardAccessControl(String userId, String password, String userName, String signature) {
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        new AbekaHomeScreen().validatedURLContent(AbekaHome.STUDENT_ACCESS_CONTROL_URL_CONTENT);
        logoutFromAbeka();
        softAssertions.assertAll();
    }

    @Test(testName = "Test-12", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateGraduationPetitionFunctionality(String userId, String password, String userName, String signature) {
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.navigateToGraduationPetitionPage().startPetition().fillGraduationPetitionForm();
        logoutFromAbeka();
        softAssertions.assertAll();
    }
}
