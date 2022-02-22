package abekaUISuite;

import base.GenericAction;
import constants.*;
import dataProvider.DataProviders;
import dataProvider.DataRowNumber;
import elementConstants.*;
import lombok.SneakyThrows;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import pageObjects.*;
import utility.ParentAccountDetails;
import utility.RetryUtility;
import utility.StudentAccountDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static constants.CommonConstants.*;
import static constants.DataProviderName.*;
import static elementConstants.Enrollments.CURSIVE;
import static elementConstants.Enrollments.MANUSCRIPT;
import static elementConstants.ItemDetails.*;
import static elementConstants.Students.*;

public class ParentAndStudentSuiteTest extends GenericAction {
    AbekaHomeScreen abekaHomeScreen = new AbekaHomeScreen();
    DashboardScreen dashboardScreen = new DashboardScreen();;
    EnrollmentsScreen enrollmentsScreen = new EnrollmentsScreen();

    @SneakyThrows
    @Test(testName = "enrollmentPurchaseGradeOne", retryAnalyzer = RetryUtility.class, dependsOnGroups = {"UnitTest"})
    public void enrollmentPurchaseGradeOne() {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(1, true, TEST_DATA_GRADE_ONE);
        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
        Product product = new Product();
        product.setProductTitle(GRADE_ONE.getItemName());
        product.setItemNumber(GRADE_ONE.getItemNumber());
        product.setPrice(GRADE_ONE.getPrice());
        product.setQuantity(GRADE_ONE.getQuantity());
        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));

        ArrayList productList = new ArrayList(Arrays.asList(product));

        abekaHomeScreen = loginToAbeka(parentAccountDetails.getParentUserName(), parentAccountDetails.getParentPassword(), true);
        abekaHomeScreen.navigateToShopByGrade(AbekaHome.firstGrade).searchProduct(Enrollments.GRADE_ONE_VIDEO).selectProduct(Enrollments.GRADE_ONE_VIDEO).
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, BookDescription.accredited, CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut(1, TEST_DATA_GRADE_ONE).
                selectCheckoutCriteria(checkoutCriteria, true).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_ONE_ACCREDITED);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    //validateCourseBeginDateFormatGradeOne
    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "validateAddNewStudentGradeOne", dataProvider = PARENT_CREDENTIALS_GRADE_ONE,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeOne"})
    public void validateAddNewStudentGradeOne(String userId, String password, String signature, String customerNumber) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeOneAccreditedStudentDetails();
        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();
        enrollmentOptions.setPenmanship(CURSIVE);

        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_ONE_ACCREDITED)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, true).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().validateBeginDate().validateCourseBeginDateFormat().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().submitEnrollment().validateAllSetMessage().updateCourseBeginDateToBackDateAndRemoveHolds(studentDetails.getStudentUserId());
        setStudentAccountDetailsInTestDataExcel(studentDetails, 1, TEST_DATA_GRADE_ONE);
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "validateSubjectListOnProgressPage", dataProvider = DataProviderName.PARENT_CREDENTIALS_GRADE_ONE,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeOne"})
    public void validateSubjectListOnProgressPageOne(String userId, String password, String signature, String customerNumber){
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        StudentDetails studentDetails = getStudentAccountDetails(1, TEST_DATA_GRADE_ONE);
        setStudentAccountDetailsFromDB(studentDetails.getStudentUserId(), true);
        dashboardScreen.navigateToMyStudentProfile(studentDetails.getFirstName()).waitAndCloseWidgetTourPopup();
        new StudentsScreen().validateSubjectListInSubjectProgressSection(TEST_DATA_GRADE_ONE);
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "validateSwitchPenmanShipScenario", dataProvider = STUDENT_CREDENTIALS_GRADE_ONE,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeOne"})
    public void validateSwitchPenmanShipScenarioGradeOne(String userId, String password, String signature, String customerNumber, String cartNumber){
        StudentsScreen studentsScreen = new StudentsScreen();
        AbekaHomeScreen abekaHomeScreen = new AbekaHomeScreen();
        StudentDetails studentDetails = getStudentAccountDetails(1, TEST_DATA_GRADE_ONE);
        setStudentAccountDetailsFromDB(userId, true);
        setStudentSubjectDetailsFromDB(userId);
        studentsScreen.switchPenmanShipFromBackEnd(CURSIVE);
        studentDetails.implicitWaitInSeconds(900);
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();

        studentsScreen.navigateToStartWatchingYourLessonsLink().validateMyLessonsTodaySectionData(true, GRADE_ONE_VIDEO_LIST, 0, 9, TEST_DATA_GRADE_ONE)
                .watchVideoAndValidateMyLessonsTodaySectionWithVideoLibrary(false, GRADE_ONE_VIDEO_LIST, TEST_DATA_GRADE_ONE)
                .validateVideoLibraryVideoStatusWithDataBase(true);

        studentsScreen.switchPenmanShipFromBackEnd(MANUSCRIPT);
        studentDetails.implicitWaitInSeconds(900);
        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.HOME);
        studentsScreen.navigateToStartWatchingYourLessonsLink().validateMyLessonsTodaySectionData(true, GRADE_ONE_VIDEO_LIST,  19, 31, TEST_DATA_GRADE_ONE);
        softAssertions.assertAll();
    }

    //Grade one
    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testValidateStudentCursiveVideoLessonOfGradeOne", dataProvider = STUDENT_CREDENTIALS_GRADE_ONE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeOne"})
    public void testValidateStudentCursiveVideoLessonOfGradeOne(String userId, String password, String studentName, String signature, String cartNumber){
        StudentsScreen studentsScreen = new StudentsScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        setStudentAccountDetailsFromDB(userId, true);
        studentsScreen.navigateToStartWatchingYourLessonsLink().validateCursiveWritingVideo();
        softAssertions.assertAll();
    }

    //Grade1
    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testValidateStudentCalendarEventsOfGradeOne", dataProvider = STUDENT_CREDENTIALS_GRADE_ONE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeOne"})
    public void testValidateStudentCalendarEventsOfGradeOne(String userId, String password, String userName, String signature, String cartNumber){
        setStudentAccountDetailsFromDB(userId, true);
        loginToAbeka(userId,password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD).waitAndCloseWidgetTourPopup();
        navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.CALENDAR);
        new CalendarScreen().validateStudentCalendarEvents(false).logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade1
    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testValidateStudentCalendarEventsOfGradeOne", dataProvider = STUDENT_CREDENTIALS_GRADE_ONE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeOne"})
    public void testValidateStudentCalendarEventsOfProgressReportOfGradeOne(String userId, String password, String userName, String signature, String cartNumber){
        CalendarScreen calendarScreen = new CalendarScreen();
        StudentsScreen studentsScreen = new StudentsScreen();
        //Change day count according to validation http://abatestweb.pcci.int/abadb/abaweb/aba_grading/viewonlinepr.aspx
        ProgressReportEventPreviewTestData progressReportEventPreviewTestData = studentsScreen.getProgressReportPreviewEventDataFroExcel(GRADE_ONE_VIDEO_LIST, 36, 37, TEST_DATA_GRADE_ONE);
        progressReportEventPreviewTestData.setProgressReportEventDate(calendarScreen.fetchProgressReportEventDate(progressReportEventPreviewTestData.getDayCount(), TEST_DATA_GRADE_ONE));
        setStudentAccountDetailsFromDB(userId, true);
        loginToAbeka(userId,password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD).waitAndCloseWidgetTourPopup();
        navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.CALENDAR);

        calendarScreen.navigateToCalendarDate(progressReportEventPreviewTestData.getProgressReportEventDate().get(0)).validateProgressReportEventPreview(progressReportEventPreviewTestData);
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testValidateStudentCalendarEventsOfGradeOne", dataProvider = PARENT_CREDENTIALS_GRADE_ONE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeOne"})
    public void testValidateStudentCalendarAddEditDeleteEventsOfGradeOne(String userId, String password, String userName, String signature){
        CalendarScreen calendarScreen = new CalendarScreen();
        StudentsScreen studentsScreen = new StudentsScreen();
        StudentDetails studentDetails = getStudentAccountDetails(1, TEST_DATA_GRADE_ONE);
        //Change day count according to validation http://abatestweb.pcci.int/abadb/abaweb/aba_grading/viewonlinepr.aspx
        ProgressReportEventPreviewTestData progressReportEventPreviewTestData = studentsScreen.getProgressReportPreviewEventDataFroExcel(GRADE_ONE_VIDEO_LIST, 36, 37, TEST_DATA_GRADE_ONE);
        progressReportEventPreviewTestData.setProgressReportEventDate(calendarScreen.fetchProgressReportEventDate(progressReportEventPreviewTestData.getDayCount(), TEST_DATA_GRADE_ONE));
        //setStudentAccountDetailsFromDB(userId, true);
        loginToAbeka(userId,password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD).waitAndCloseWidgetTourPopup();
        navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.CALENDAR);
        LocalDate eventDate = progressReportEventPreviewTestData.getProgressReportEventDate().get(0);
        calendarScreen.selectStudent(studentDetails).navigateToCalendarDate(eventDate)
                .addNewEventToCalendar(eventDate, VIDEO, AUTOMATION_TEST)
                .validateNewlyAddedEvent(eventDate).editCreatedEvent(eventDate).deleteAndVerifyCreatedEvent(eventDate);
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testValidateStudentCalendarEventsOfGradeOne", dataProvider = PARENT_CREDENTIALS_GRADE_ONE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeOne"})
    public void testValidateStudentCalendarCategoryGradeOne(String userId, String password, String userName, String signature){
        CalendarScreen calendarScreen = new CalendarScreen();
        StudentsScreen studentsScreen = new StudentsScreen();
        StudentDetails studentDetails = getStudentAccountDetails(1, TEST_DATA_GRADE_ONE);
        //Change day count according to validation http://abatestweb.pcci.int/abadb/abaweb/aba_grading/viewonlinepr.aspx
        ProgressReportEventPreviewTestData progressReportEventPreviewTestData = studentsScreen.getProgressReportPreviewEventDataFroExcel(GRADE_ONE_VIDEO_LIST, 36, 37, TEST_DATA_GRADE_ONE);
        progressReportEventPreviewTestData.setProgressReportEventDate(calendarScreen.fetchProgressReportEventDate(progressReportEventPreviewTestData.getDayCount(), TEST_DATA_GRADE_ONE));
        //setStudentAccountDetailsFromDB(userId, true);
        loginToAbeka(userId,password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD).waitAndCloseWidgetTourPopup();
        navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.CALENDAR);
        LocalDate eventDate = progressReportEventPreviewTestData.getProgressReportEventDate().get(0);
        calendarScreen.selectStudent(studentDetails).navigateToCalendarDate(eventDate)
                .addNewEventToCalendar(eventDate, MISC_ASSIGNMENTS, MISC_AUTOMATION_TEST).selectDeselectCalendarCategory(MISC_ASSIGNMENTS).validateIsEventBoxPresent(eventDate, MISC_AUTOMATION_TEST);
        softAssertions.assertAll();
    }


    @Test(testName = "enrollmentPurchaseGradeFour", retryAnalyzer = RetryUtility.class, dependsOnGroups = {"UnitTest"})
    public void enrollmentPurchaseGradeFour() {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(1, true, TEST_DATA_GRADE_FOUR);
        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
        Product product = new Product();
        product.setProductTitle(GRADE_FOUR.getItemName());
        product.setItemNumber(GRADE_FOUR.getItemNumber());
        product.setPrice(GRADE_FOUR.getPrice());
        product.setQuantity(GRADE_FOUR.getQuantity());
        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));

        ArrayList productList = new ArrayList(Arrays.asList(product));

        abekaHomeScreen = loginToAbeka(parentAccountDetails.getParentUserName(), parentAccountDetails.getParentPassword(), true);
        abekaHomeScreen.navigateToShopByGrade(AbekaHome.fourthGrade).searchProduct(Enrollments.GRADE_FOUR_VIDEO).selectProduct(Enrollments.GRADE_FOUR_VIDEO).
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, BookDescription.accredited, CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut(1, TEST_DATA_GRADE_FOUR).
                selectCheckoutCriteria(checkoutCriteria, true).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_FOUR_ACCREDITED);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "validateAddNewStudentGradeFour", dataProvider = DataProviderName.PARENT_CREDENTIALS_GRADE_FOUR, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeFour"})
    public void validateAddNewStudentGradeFour(String userId, String password, String signature, String customerNumber) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeFourStudentDetails();

        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_FOUR_ACCREDITED)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, false).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().validateBeginDate().addBeginDate().clickOnNextButton().signEnrollmentAgreement(signature)
                .clickOnNextButton().clickOnNextButton().fillProofOfCompletion(studentDetails.getGrade()).clickOnNextButton().fillAddClasses().clickOnNextButton().submitEnrollment().validateAllSetMessage().updateCourseBeginDateToBackDateAndRemoveHolds(studentDetails.getStudentUserId());
        setStudentAccountDetailsInTestDataExcel(studentDetails, 1, TEST_DATA_GRADE_FOUR);
        softAssertions.assertAll();
    }

    //Grade 4
    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testManageStudentInformationFromDashboardOfGradeFour", dataProvider = PARENT_CREDENTIALS_GRADE_FOUR, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeFour"})
    public void testManageStudentInformationFromDashboardOfGradeFour(String userId, String password, String userName, String signature){
        StudentDetails studentDetails = getStudentAccountDetails(1, TEST_DATA_GRADE_FOUR);
        setStudentAccountDetailsFromDB(studentDetails.getStudentUserId(), true);
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyStudentProfile(studentDetails.getFirstName()).waitAndCloseWidgetTourPopup();
        new StudentsScreen().validateStudentInformationSection(Dashboard.STUDENT_NAME)
                .navigateToProgressReportWidget(Dashboard.STUDENT_NAME).enterStudentGrades(false,false, signature)
                .logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade 4
    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testValidateStudentAssessmentDetailsOnCalendarGradeFour", dataProvider = PARENT_CREDENTIALS_GRADE_FOUR, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeFour"})
    public void testValidateStudentAssessmentDetailsOnCalendarGradeFour(String userId, String password, String userName, String signature){
        CalendarScreen calendarScreen = new CalendarScreen();
        StudentDetails studentDetails = getStudentAccountDetails(1, TEST_DATA_GRADE_FOUR);
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetailsFromDB(studentDetails.getStudentUserId(), true);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToFullCalendarView();
        dashboardScreen.waitAndCloseWidgetTourPopup();
        calendarScreen.validateNoStudentIsSelected().selectStudent(studentDetails.getFirstName()).validateStudentCalendarEvents(false).logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testValidateStudentAbleToWatchVideoStreamingOfGradeFour", dataProvider = STUDENT_CREDENTIALS_GRADE_FOUR, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeFour"})
    public void testValidateStudentAbleToWatchVideoStreamingOfGradeFour(String userId, String password, String userName, String signature, String cartNumber){
        StudentsScreen studentsScreen = new StudentsScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        studentsScreen.navigateToStartWatchingYourLessonsLink();
               studentsScreen.validateVideoListAvailableOnVideoLibrary(TEST_DATA_GRADE_FOUR);
        softAssertions.assertAll();
    }


    @Test(testName = "enrollmentPurchaseGradeNine", retryAnalyzer = RetryUtility.class, dependsOnGroups = {"UnitTest"})
    public void enrollmentPurchaseGradeNine() {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(1, true, TEST_DATA_GRADE_NINE);
        //ParentAccountDetails parentAccountDetails = getParentAccountDetails(1, TEST_DATA_GENERIC);
        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
        Product product = new Product();
        product.setProductTitle(GRADE_NINE.getItemName());
        product.setItemNumber(GRADE_NINE.getItemNumber());
        product.setPrice(GRADE_NINE.getPrice());
        product.setQuantity(GRADE_NINE.getQuantity());
        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));

        ArrayList productList = new ArrayList(Arrays.asList(product));

        abekaHomeScreen = loginToAbeka(parentAccountDetails.getParentUserName(), parentAccountDetails.getParentPassword(), true);
        abekaHomeScreen.navigateToShopByGrade(AbekaHome.ninthGrade).searchProduct(Enrollments.GRADE_NINE_VIDEO).selectProduct(Enrollments.GRADE_NINE_VIDEO).
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, BookDescription.accredited, CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut(1, TEST_DATA_GRADE_NINE).
                selectCheckoutCriteria(checkoutCriteria, true).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_NINE_ACCREDITED);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "validateAddNewStudentGradeNine", dataProvider = DataProviderName.PARENT_CREDENTIALS_GRADE_NINE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeNine"})
    public void validateAddNewStudentGradeNine(String userId, String password, String signature, String customerNumber) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeNineStudentDetails();
        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_NINE_ACCREDITED)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, false).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().fillAvailableRecommendedCourses(Enrollments.GRADE_NINE).clickOnNextButton().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().clickOnNextButton().fillProofOfCompletion(studentDetails.getGrade()).clickOnNextButton().fillAddClasses().clickOnNextButton().submitEnrollment().validateAllSetMessage().updateCourseBeginDateToBackDateAndRemoveHolds(studentDetails.getStudentUserId());
        setStudentAccountDetailsInTestDataExcel(studentDetails, 1, TEST_DATA_GRADE_NINE);
        softAssertions.assertAll();
    }

    //Grade 9
    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testModifyStudentAssessmentDetailsOnCalendarOfGradeNine", dataProvider = PARENT_CREDENTIALS_GRADE_NINE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeNine"})
    public void testModifyStudentAssessmentDetailsOnCalendarOfGradeNine(String userId, String password, String userName, String signature){
        StudentsScreen studentsScreen = new StudentsScreen();
        StudentDetails studentDetails = getStudentAccountDetails(1, TEST_DATA_GRADE_NINE);
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyStudentProfile(studentDetails.getFirstName()).waitAndCloseWidgetTourPopup();
        studentsScreen.navigateToCalendarSettingsWidget().changeStudentAssessmentDetails(SIX_MONTHS_ASSESSMENT,"");
        studentsScreen.navigateToCalendarSettingsWidget().validateStudentAssessmentDetails(SIX_MONTHS_ASSESSMENT,No);
        studentsScreen.navigateToCalendarSettingsWidget().changeStudentAssessmentDetails(NINE_MONTHS_ASSESSMENT,"").logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade 9
    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testValidateStudentAbleToCompleteDigitalAssessmentSuccessfullyOfGradeNine", dataProvider = STUDENT_CREDENTIALS_GRADE_NINE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeNine"})
    public void testValidateStudentAbleToCompleteDigitalAssessmentSuccessfullyOfGradeNine(String userId, String password, String studentName, String signature, String cartNumber){
        loginToAbeka(userId,password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD).waitAndCloseWidgetTourPopup();
        navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
        setStudentAccountDetailsFromDB(userId, true);
        new StudentsScreen().answerAndSubmitDigitalAssessment(false);
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1",toDataRowNumber = "1")
    @Test(testName = "validatePreviewStudentVideoLessonsGradeNine", dataProvider = DataProviderName.PARENT_CREDENTIALS_GRADE_NINE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeNine"})
    public void validatePreviewStudentVideoLessonsGradeNine(String userId, String password, String signature, String customerNumber) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = getStudentAccountDetails(1, TEST_DATA_GRADE_NINE);
        //setStudentAccountDetailsFromDB(studentDetails.getStudentUserId());
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        StudentsScreen studentsScreen = dashboardScreen.navigateToMyStudentProfile(studentDetails.getFirstName());
        waitAndCloseWidgetTourPopup();
        studentsScreen.navigateToPreviewVideoStudentScreen();
        waitAndCloseWidgetTourPopup();
        studentsScreen.isVideoLibraryPageOpened();
        softAssertions.assertAll();
    }

    //Grade 9 change to 4 currently fetching data for syed
    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testValidateStudentAbleToWatchVideoStreamingOfGradeNine", dataProvider = STUDENT_CREDENTIALS_GRADE_NINE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeNine"})
    public void testValidateStudentAbleToWatchVideoStreamingOfGradeNine(String userId, String password, String userName, String signature, String cartNumber){
        StudentsScreen studentsScreen = new StudentsScreen();
        AbekaHomeScreen abekaHomeScreen = new AbekaHomeScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetailsFromDB(userId, true);
        setStudentSubjectDetailsFromDB(userId);
        //studentsScreen.markAllVideosAsNotViewed();
        dashboardScreen.waitAndCloseWidgetTourPopup();
        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
        studentsScreen.skipPracticeTest();
        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
        studentsScreen.validateDigitalAssessmentsAreLockedOrNot(true, TEST_DATA_GRADE_NINE);
        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.HOME);
        studentsScreen.navigateToStartWatchingYourLessonsLink().validateMyLessonsTodaySectionData(true, GRADE_NINE_VIDEO_LIST, 0, 0, TEST_DATA_GRADE_NINE).validateDigitalAssessmentsAreLockedOrNot(true, TEST_DATA_GRADE_NINE)
                .validateStudentShouldNotAbleToWatchNextDayLessonFromVideoLibrary(true, TEST_DATA_GRADE_NINE)
                .watchVideoAndValidateMyLessonsTodaySectionWithVideoLibrary(false, GRADE_NINE_VIDEO_LIST, TEST_DATA_GRADE_NINE)
                .validateVideoLibraryVideoStatusWithDataBase(true);

        //studentsScreen.validateDigitalAssessmentsAreLockedOrNot(true);

//        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
//        studentsScreen.validateDigitalAssessmentsAreLockedOrNot(true).logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade 12
    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testValidateStudentAbleToWatchVideoStreamingOfGradeNine", dataProvider = STUDENT_CREDENTIALS_GRADE_NINE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeNine"})
    public void testValidateStudentDashboardDataOfGradeNine(String userId, String password, String studentName, String signature, String cartNumber){
        StudentsScreen studentsScreen = new StudentsScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        setStudentAccountDetailsFromDB(userId, true);
        studentsScreen.validateMyToDoListData().validateAccountInfoPage().
                validateRequestTranScriptFunctionality(studentName).
                validateMyRecentGrades(studentName).verifyLastViewedLessons().logoutFromAbeka();
        softAssertions.assertAll();
    }

    @Test(testName = "enrollmentPurchaseGradeTwelve", retryAnalyzer = RetryUtility.class, dependsOnGroups = {"UnitTest"})
    public void enrollmentPurchaseGradeTwelve() {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(1, true, TEST_DATA_GRADE_TWELVE);
        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
        Product product = new Product();
        product.setProductTitle(GRADE_TWELVE.getItemName());
        product.setItemNumber(GRADE_TWELVE.getItemNumber());
        product.setPrice(GRADE_TWELVE.getPrice());
        product.setQuantity(GRADE_TWELVE.getQuantity());
        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));

        ArrayList productList = new ArrayList(Arrays.asList(product));

        abekaHomeScreen = loginToAbeka(parentAccountDetails.getParentUserName(), parentAccountDetails.getParentPassword(), true);
        abekaHomeScreen.navigateToShopByGrade(AbekaHome.twelfthGrade).searchProduct(Enrollments.GRADE_TWELVE_VIDEO).selectProduct(Enrollments.GRADE_TWELVE_VIDEO).
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, BookDescription.accredited, CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut(1, TEST_DATA_GRADE_TWELVE).
                selectCheckoutCriteria(checkoutCriteria, true).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_TWELVE_ACCREDITED);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "validateAddNewStudentGradeTwelve", dataProvider = PARENT_CREDENTIALS_GRADE_TWELVE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeTwelve"})
    public void validateAddNewStudentGradeTwelve(String userId, String password, String signature, String customerNumber) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeTwelveStudentDetails();
        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_TWELVE_ACCREDITED)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, false).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().fillAvailableRecommendedCourses(Enrollments.GRADE_TWELVE).clickOnNextButton().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().clickOnNextButton().fillProofOfCompletion(studentDetails.getGrade()).clickOnNextButton().fillAddClasses().clickOnNextButton().submitEnrollment().validateAllSetMessage().updateCourseBeginDateToBackDateAndRemoveHolds(studentDetails.getStudentUserId());
        setStudentAccountDetailsInTestDataExcel(studentDetails,1, TEST_DATA_GRADE_TWELVE);
        JavascriptExecutor js = (JavascriptExecutor)getDriver();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1", toDataRowNumber = "1")
    @Test(testName = "testValidateGraduationPetitionFunctionality", dataProvider = PARENT_CREDENTIALS_GRADE_TWELVE, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateGraduationPetitionFunctionality(String userId, String password, String userName, String signature) {
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetailsFromDB(getStudentAccountDetails(1, TEST_DATA_GRADE_TWELVE).getStudentUserId(), true);
        waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToGraduationPetitionPage().startPetition().fillGraduationPetitionForm(userName).approveSubmittedPetition().validatePetitionIsApproved();
        logoutFromAbeka();
        softAssertions.assertAll();
    }
}