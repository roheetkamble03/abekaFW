package abekaUISuite;

import base.GenericAction;
import dataProvider.DataProviders;
import elementConstants.AbekaHome;
import elementConstants.Dashboard;
import org.testng.annotations.Test;
import pageObjects.AbekaHomeScreen;
import pageObjects.CalendarScreen;
import pageObjects.DashboardScreen;
import pageObjects.StudentsScreen;
import utility.RetryUtility;

public class ParentTestCaseSuite extends GenericAction {
    DashboardScreen dashboardScreen = new DashboardScreen();
    StudentsScreen studentsScreen;

    @Test(testName = "Test-4", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testManageStudentInformationFromDashboard(String userId, String password, String userName){
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup().navigateToMyStudentProfile(Dashboard.STUDENT_NAME).
                validateStudentInformationSection(Dashboard.STUDENT_NAME).navigateToProgressReportSection().logoutFromAbeka();
    }

    @Test(testName = "Test-5", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentAssessmentDetailsOnCalendar(String userId, String password, String userName){
        AbekaHomeScreen abekaHomeScreen = new AbekaHomeScreen();
        CalendarScreen calendarScreen = new CalendarScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        //setStudentAccountDetails(Dashboard.STUDENT_NAME);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToFullCalendarView();
        dashboardScreen.waitAndCloseWidgetTourPopup();
        calendarScreen.validateNoStudentIsSelected().selectStudent(Dashboard.STUDENT_NAME).validateStudentCalendarEvents(false).logoutFromAbeka();
        //need to check navigation part
    }

    @Test(testName = "Test-6", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testModifyStudentAssessmentDetailsOnCalendar(String userId, String password, String userName){
        AbekaHomeScreen abekaHomeScreen = new AbekaHomeScreen();
        CalendarScreen calendarScreen = new CalendarScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        //setStudentAccountDetails(Dashboard.STUDENT_NAME);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToFullCalendarView();
        dashboardScreen.waitAndCloseWidgetTourPopup();
        //need to ask krupa about scenario
        calendarScreen.selectStudent(Dashboard.STUDENT_NAME).changeStudentAssessmentDetails().logoutFromAbeka();

        //need to check navigation part
    }

    @Test(testName = "Test-7", dataProvider = "studentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentDashboardData(String userId, String password, String userName){
        StudentsScreen studentsScreen = new StudentsScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetails(userId);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        setStudentAccountDetails("Syed");
        studentsScreen.validateMyToDoListData().validateAccountInfoPage().
                validateRequestTranScriptFunctionality(Dashboard.STUDENT_NAME).validateMyRecentGrades(Dashboard.STUDENT_NAME).verifyLastViewedLessons().logoutFromAbeka();
    }

    @Test(testName = "Test-8", dataProvider = "studentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentAbleToWatchVideoStreaming(String userId, String password, String userName){
        StudentsScreen studentsScreen = new StudentsScreen();
        AbekaHomeScreen abekaHomeScreen = new AbekaHomeScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetails(userId);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
        studentsScreen.validateDigitalAssessmentsAreLockedOrNot(true);
        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.HOME);
        studentsScreen.navigateToStartWatchingYourLessonsLink().validateMyLessonsTodaySectionData().validateDigitalAssessmentsAreLockedOrNot(true)
                .validateStudentShouldNotAbleToWatchNextDayLessonFromVideoLibrary()
                .watchVideoAndValidateMyLessonsTodaySectionWithVideoLibrary()
                .validateVideoLibraryVideoStatusWithDataBase().validateDigitalAssessmentsAreLockedOrNot(false);

        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
        studentsScreen.validateDigitalAssessmentsAreLockedOrNot(false).logoutFromAbeka();

        //data need to fetch from DB for validation
    }



}
