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

import static elementConstants.Students.*;

public class ParentTestCaseSuite extends GenericAction {
    DashboardScreen dashboardScreen = new DashboardScreen();
    StudentsScreen studentsScreen;

    @Test(testName = "Test-4", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testManageStudentInformationFromDashboard(String userId, String password, String userName){
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyStudentProfile(Dashboard.STUDENT_NAME).
                validateStudentInformationSection(Dashboard.STUDENT_NAME)
//             disabled, since progress report not working   .navigateToProgressReportWidget().enterStudentGrades()
//                .signProgressReport("signature").submitProgressReport()
                .logoutFromAbeka();
    }

    @Test(testName = "Test-5", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentAssessmentDetailsOnCalendar(String userId, String password, String userName){
        CalendarScreen calendarScreen = new CalendarScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetailsFromDB("syed.rcg");
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToFullCalendarView();
        dashboardScreen.waitAndCloseWidgetTourPopup();
        calendarScreen.validateNoStudentIsSelected().selectStudent(Dashboard.STUDENT_NAME).validateStudentCalendarEvents(false).logoutFromAbeka();
        //need to check navigation part
    }

    @Test(testName = "Test-6", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testModifyStudentAssessmentDetailsOnCalendar(String userId, String password, String userName){
        StudentsScreen studentsScreen = new StudentsScreen();
        CalendarScreen calendarScreen = new CalendarScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetailsFromDB("syed.rcg");
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyStudentProfile(Dashboard.STUDENT_NAME).waitAndCloseWidgetTourPopup();
        studentsScreen.navigateToCalendarSettingsWidget().changeStudentAssessmentDetails(SIX_MONTHS_ASSESSMENT,"");
        studentsScreen.navigateToCalendarSettingsWidget().validateStudentAssessmentDetails(SIX_MONTHS_ASSESSMENT,No);
        studentsScreen.navigateToCalendarSettingsWidget().changeStudentAssessmentDetails(NINE_MONTHS_ASSESSMENT,"").logoutFromAbeka();

        //need to check navigation part
    }

    @Test(testName = "Test-7", dataProvider = "studentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentDashboardData(String userId, String password, String userName){
        StudentsScreen studentsScreen = new StudentsScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        setStudentAccountDetailsFromDB(userId);
        studentsScreen.validateMyToDoListData().validateAccountInfoPage().
                validateRequestTranScriptFunctionality(Dashboard.STUDENT_NAME).
        validateMyRecentGrades(Dashboard.STUDENT_NAME).verifyLastViewedLessons().logoutFromAbeka();
    }

    @Test(testName = "Test-8", dataProvider = "studentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentAbleToWatchVideoStreaming(String userId, String password, String userName){
        StudentsScreen studentsScreen = new StudentsScreen();
        AbekaHomeScreen abekaHomeScreen = new AbekaHomeScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetailsFromDB(userId);
        setStudentSubjectDetailsFromDB(userId);
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

        //data need to fetch from DB for validation
    }



}
