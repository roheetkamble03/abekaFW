package abekaUISuite;

import base.GenericAction;
import constants.*;
import dataProvider.DataProviders;
import dataProvider.DataRowNumber;
import elementConstants.AbekaHome;
import elementConstants.BookDescription;
import elementConstants.Dashboard;
import elementConstants.Enrollments;
import org.testng.annotations.Test;
import pageObjects.*;
import utility.ParentAccountDetails;
import utility.RetryUtility;
import utility.StudentAccountDetails;
import java.util.ArrayList;
import java.util.Arrays;

import static constants.DataProviderName.PARENT_CREDENTIALS;
import static constants.DataProviderName.STUDENT_CREDENTIALS;
import static elementConstants.Enrollments.CURSIVE;
import static elementConstants.ItemDetails.*;
import static elementConstants.Students.*;

public class ParentAndStudentSuiteTest extends GenericAction {
    AbekaHomeScreen abekaHomeScreen = new AbekaHomeScreen();
    DashboardScreen dashboardScreen = new DashboardScreen();;
    EnrollmentsScreen enrollmentsScreen = new EnrollmentsScreen();

    @Test(testName = "enrollmentPurchaseGradeOne", retryAnalyzer = RetryUtility.class)
    public void enrollmentPurchaseGradeOne() {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(2);
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
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(checkoutCriteria).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_ONE_ACCREDITED);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "2", toDataRowNumber = "2")
    @Test(testName = "validateAddNewStudentGradeOne", dataProvider = DataProviderName.PARENT_CREDENTIALS,
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
                .clickOnNextButton().validateBeginDate().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().submitEnrollment().validateAllSetMessage().updateCourseBeginDateToBackDate(studentDetails.getStudentUserId());
        setStudentAccountDetailsInTestDataExcel(studentDetails, 2);
        softAssertions.assertAll();
    }


    @DataRowNumber(fromDataRowNumber = "2", toDataRowNumber = "2")
    @Test(testName = "validateCourseBeginDateFormatGradeOne", dataProvider = DataProviderName.PARENT_CREDENTIALS,
            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeOne"})
    public void validateCourseBeginDateFormatGradeOne(String userId, String password, String signature, String customerNumber) {
        enrollmentPurchaseGradeOne();
        ParentAccountDetails parentAccountDetails = getParentAccountDetails(2);
        userId = parentAccountDetails.getParentUserName();
        password = parentAccountDetails.getParentPassword();
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeOneAccreditedStudentDetails();
        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password, false).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_ONE_ACCREDITED)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, true).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().validateCourseBeginDateFormat();
        softAssertions.assertAll();
    }

    //Grade one
    @DataRowNumber(fromDataRowNumber = "2", toDataRowNumber = "2")
    @Test(testName = "testValidateStudentDashboardDataOfGradeOne", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeOne"})
    public void testValidateStudentDashboardDataOfGradeOne(String userId, String password, String studentName, String signature){
        StudentsScreen studentsScreen = new StudentsScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        setStudentAccountDetailsFromDB(userId);
        studentsScreen.validateMyToDoListData().validateAccountInfoPage().
                validateRequestTranScriptFunctionality(studentName).
                validateMyRecentGrades(studentName).verifyLastViewedLessons().logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade one
    @DataRowNumber(fromDataRowNumber = "2", toDataRowNumber = "2")
    @Test(testName = "testValidateStudentCursiveVideoLessonOfGradeOne", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void testValidateStudentCursiveVideoLessonOfGradeOne(String userId, String password, String studentName, String signature){
        StudentsScreen studentsScreen = new StudentsScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        setStudentAccountDetailsFromDB(userId);
        studentsScreen.navigateToStartWatchingYourLessonsLink().validateCursiveWritingVideo();
        softAssertions.assertAll();
    }

    //Grade1
    @DataRowNumber(fromDataRowNumber = "2", toDataRowNumber = "2")
    @Test(testName = "testValidateStudentCalendarEventsOfGradeOne", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeOne"})
    public void testValidateStudentCalendarEventsOfGradeOne(String userId, String password, String userName, String signature){
        loginToAbeka(userId,password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD).waitAndCloseWidgetTourPopup();
        navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.CALENDAR);
        new CalendarScreen().validateStudentCalendarEvents(false).logoutFromAbeka();
        softAssertions.assertAll();
    }

    @Test(testName = "enrollmentPurchaseGradeFour", retryAnalyzer = RetryUtility.class)
    public void enrollmentPurchaseGradeFour() {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(3);
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
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(checkoutCriteria).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_FOUR_ACCREDITED);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "3", toDataRowNumber = "3")
    @Test(testName = "validateAddNewStudentGradeFour", dataProvider = DataProviderName.PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeFour"})
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
                .clickOnNextButton().clickOnNextButton().fillProofOfCompletion(studentDetails.getGrade()).clickOnNextButton().fillAddClasses().clickOnNextButton().submitEnrollment().validateAllSetMessage().updateCourseBeginDateToBackDate(studentDetails.getStudentUserId());;
        setStudentAccountDetailsInTestDataExcel(studentDetails, 3);
        softAssertions.assertAll();
    }

    //Grade 4
    @DataRowNumber(fromDataRowNumber = "3", toDataRowNumber = "3")
    @Test(testName = "testValidateStudentAbleToWatchVideoStreamingOfGradeFour", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class,dependsOnMethods = {"validateAddNewStudentGradeFour"})
    public void testValidateStudentAbleToWatchVideoStreamingOfGradeFour(String userId, String password, String userName, String signature){
        StudentsScreen studentsScreen = new StudentsScreen();
        AbekaHomeScreen abekaHomeScreen = new AbekaHomeScreen();
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
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

    //Grade 4
    @DataRowNumber(fromDataRowNumber = "3", toDataRowNumber = "3")
    @Test(testName = "testManageStudentInformationFromDashboardOfGradeFour", dataProvider = PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeFour"})
    public void testManageStudentInformationFromDashboardOfGradeFour(String userId, String password, String userName, String signature){
        StudentDetails studentDetails = getStudentAccountDetails(3);
        setStudentAccountDetailsFromDB(studentDetails.getStudentUserId());
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyStudentProfile(studentDetails.getFirstName()).waitAndCloseWidgetTourPopup();
        new StudentsScreen().validateStudentInformationSection(Dashboard.STUDENT_NAME)
                .navigateToProgressReportWidget(Dashboard.STUDENT_NAME).enterStudentGrades(false,false, signature)
                .logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade 4
    @DataRowNumber(fromDataRowNumber = "3", toDataRowNumber = "3")
    @Test(testName = "testValidateStudentAssessmentDetailsOnCalendarGradeFour", dataProvider = PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeFour"})
    public void testValidateStudentAssessmentDetailsOnCalendarGradeFour(String userId, String password, String userName, String signature){
        CalendarScreen calendarScreen = new CalendarScreen();
        StudentDetails studentDetails = getStudentAccountDetails(3);
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        setStudentAccountDetailsFromDB(studentDetails.getStudentUserId());
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToFullCalendarView();
        dashboardScreen.waitAndCloseWidgetTourPopup();
        calendarScreen.validateNoStudentIsSelected().selectStudent(studentDetails.getStudentFullName()).validateStudentCalendarEvents(false).logoutFromAbeka();
        softAssertions.assertAll();
    }


    @Test(testName = "enrollmentPurchaseGradeNine", retryAnalyzer = RetryUtility.class)
    public void enrollmentPurchaseGradeNine() {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(4);
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
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, "", CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(checkoutCriteria).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_NINE);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "4", toDataRowNumber = "4")
    @Test(testName = "validateAddNewStudentGradeNine", dataProvider = DataProviderName.PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeNine"})
    public void validateAddNewStudentGradeNine(String userId, String password, String signature, String customerNumber) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeNineStudentDetails();

        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_NINE)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, false).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().fillAvailableRecommendedCourses(Enrollments.GRADE_NINE).clickOnNextButton().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().clickOnNextButton().fillProofOfCompletion(studentDetails.getGrade()).clickOnNextButton().fillAddClasses().clickOnNextButton().submitEnrollment().validateAllSetMessage();
        setStudentAccountDetailsInTestDataExcel(studentDetails, 4);
        softAssertions.assertAll();
    }

    //Grade 9
    @DataRowNumber(fromDataRowNumber = "4", toDataRowNumber = "4")
    @Test(testName = "testModifyStudentAssessmentDetailsOnCalendarOfGradeNine", dataProvider = PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeNine"})
    public void testModifyStudentAssessmentDetailsOnCalendarOfGradeNine(String userId, String password, String userName, String signature){
        StudentsScreen studentsScreen = new StudentsScreen();
        StudentDetails studentDetails = getStudentAccountDetails(4);
        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyStudentProfile(studentDetails.getFirstName()).waitAndCloseWidgetTourPopup();
        studentsScreen.navigateToCalendarSettingsWidget().changeStudentAssessmentDetails(SIX_MONTHS_ASSESSMENT,"");
        studentsScreen.navigateToCalendarSettingsWidget().validateStudentAssessmentDetails(SIX_MONTHS_ASSESSMENT,No);
        studentsScreen.navigateToCalendarSettingsWidget().changeStudentAssessmentDetails(NINE_MONTHS_ASSESSMENT,"").logoutFromAbeka();
        softAssertions.assertAll();
    }

    //Grade 9
    @DataRowNumber(fromDataRowNumber = "4", toDataRowNumber = "4")
    @Test(testName = "testValidateStudentAbleToCompleteDigitalAssessmentSuccessfullyOfGradeNine", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"validateAddNewStudentGradeNine"})
    public void testValidateStudentAbleToCompleteDigitalAssessmentSuccessfullyOfGradeNine(String userId, String password, String studentName, String signature){
        loginToAbeka(userId,password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD).waitAndCloseWidgetTourPopup();
        navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
        setStudentAccountDetailsFromDB(userId);
        new StudentsScreen().answerAndSubmitDigitalAssessment(false);
        softAssertions.assertAll();
    }

    @Test(testName = "enrollmentPurchaseGradeTwelve", retryAnalyzer = RetryUtility.class)
    public void enrollmentPurchaseGradeTwelve() {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(5);
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
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, "", CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(checkoutCriteria).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_TWELVE);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "5", toDataRowNumber = "5")
    @Test(testName = "validateAddNewStudentGradeTwelve", dataProvider = DataProviderName.PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeTwelve"})
    public void validateAddNewStudentGradeTwelve(String userId, String password, String signature, String customerNumber) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeTwelveStudentDetails();

        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_TWELVE)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, false).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().fillAvailableRecommendedCourses(Enrollments.GRADE_TWELVE).clickOnNextButton().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().clickOnNextButton().fillProofOfCompletion(studentDetails.getGrade()).clickOnNextButton().fillAddClasses().clickOnNextButton().submitEnrollment().validateAllSetMessage();
        setStudentAccountDetailsInTestDataExcel(studentDetails,5);
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "5", toDataRowNumber = "5")
    @Test(testName = "validateAddNewStudentGradeTwelve", dataProvider = DataProviderName.PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeTwelve"})
    public void validateCourseBeginDateFieldGradeTwelve(String userId, String password, String signature, String customerNumber) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeTwelveStudentDetails();

        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_TWELVE)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, false).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().fillAvailableRecommendedCourses(Enrollments.GRADE_TWELVE).clickOnNextButton().validateBeginDate().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().clickOnNextButton().fillProofOfCompletion(studentDetails.getGrade()).clickOnNextButton().fillAddClasses().clickOnNextButton().submitEnrollment().validateAllSetMessage();
        setStudentAccountDetailsInTestDataExcel(studentDetails,5);
        softAssertions.assertAll();
    }

    @DataRowNumber(fromDataRowNumber = "1",toDataRowNumber = "1")
    @Test(testName = "validateAddNewStudentGradeTwelve", dataProvider = DataProviderName.PARENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void validatePreviewStudentVideoLessonsGradeTwelve(String userId, String password, String signature, String customerNumber) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = getStudentAccountDetails(1);
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
}