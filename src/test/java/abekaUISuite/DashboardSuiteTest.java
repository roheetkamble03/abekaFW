package abekaUISuite;

import base.GenericAction;

public class DashboardSuiteTest extends GenericAction {

//    DashboardScreen dashboardScreen = new DashboardScreen();
//
//    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
//    public void validateAccessControlForParent(String userId, String password, String userName) {
//        dashboardScreen = new DashboardScreen();
//        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
//        dashboardScreen.waitAndCloseWidgetTourPopup();
//        dashboardScreen.validateDashboardNewTab();
//        softAssertions.assertAll();
//    }
//
//    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
//    public void validateDashboardMyOrderWidgetsLinks(String userId, String password, String userName) {
//        dashboardScreen = new DashboardScreen();
//        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
//        dashboardScreen.waitAndCloseWidgetTourPopup();
//        dashboardScreen.validateMyOrdersWidgetLinks();
//        softAssertions.assertAll();
//    }
//
//    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
//    public void validateDashboardVideoManualPdfsWidgetLinks(String userId, String password, String userName) {
//        dashboardScreen = new DashboardScreen();
//        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
//        dashboardScreen.waitAndCloseWidgetTourPopup();
//        dashboardScreen.validateVideoManualPdfsLinks();
//        softAssertions.assertAll();
//    }
//
//    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
//    public void validateDashboardNotificationWidgetLinks(String userId, String password, String userName) {
//        dashboardScreen = new DashboardScreen();
//        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
//        dashboardScreen.waitAndCloseWidgetTourPopup();
//        dashboardScreen.validateNotificationRows();
//        softAssertions.assertAll();
//    }
//
//    @Test(dataProvider = "parentCredentials", dataProviderClass = DataProviders.class)
//    public void validateDashboardMyStudentsWidgetLinks(String userId, String password, String userName) {
//        dashboardScreen = new DashboardScreen();
//        StudentDetails studentDetails = getStudentAccountDetails(1);
//        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
//        dashboardScreen.waitAndCloseWidgetTourPopup();
//        dashboardScreen.validateDashboardMyStudentLink(studentDetails.getFirstName());
//        softAssertions.assertAll();
//    }
//
//        @Test(testName = "validateCourseBeginDateFieldGradeTwelve", retryAnalyzer = RetryUtility.class)
//    public void validateCourseBeginDateFieldGradeTwelve() {
//        dashboardScreen = new DashboardScreen();
//        enrollmentsScreen = new EnrollmentsScreen();
//        StudentDetails studentDetails = StudentAccountDetails.getGradeTwelveStudentDetails();
//        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();
//
//        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(5, false);
//        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
//        Product product = new Product();
//        product.setProductTitle(GRADE_TWELVE.getItemName());
//        product.setItemNumber(GRADE_TWELVE.getItemNumber());
//        product.setPrice(GRADE_TWELVE.getPrice());
//        product.setQuantity(GRADE_TWELVE.getQuantity());
//        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));
//
//        ArrayList productList = new ArrayList(Arrays.asList(product));
//
//        abekaHomeScreen = loginToAbeka(parentAccountDetails.getParentUserName(), parentAccountDetails.getParentPassword(), true);
//        abekaHomeScreen.navigateToShopByGrade(AbekaHome.twelfthGrade).searchProduct(Enrollments.GRADE_TWELVE_VIDEO).selectProduct(Enrollments.GRADE_TWELVE_VIDEO).
//                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, BookDescription.accredited, CommonTexts.one).
//                clickOnAddToCart();
//        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut(5).
//                selectCheckoutCriteria(checkoutCriteria).
//                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_TWELVE_ACCREDITED);
//
//        enrollmentsScreen.openSectionLink(Enrollments.NEW,Enrollments.GRADE_TWELVE_ACCREDITED)
//                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, false).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
//                .clickOnNextButton().fillAvailableRecommendedCourses(Enrollments.GRADE_TWELVE).clickOnNextButton().validateBeginDate().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
//                .clickOnNextButton().clickOnNextButton().fillProofOfCompletion(studentDetails.getGrade()).clickOnNextButton().fillAddClasses().clickOnNextButton().submitEnrollment().validateAllSetMessage();
//        softAssertions.assertAll();
//    }
//
//        //Grade 9
//    @DataRowNumber(fromDataRowNumber = "4", toDataRowNumber = "4")
//    @Test(testName = "testValidateStudentAbleToWatchVideoStreamingOfGradeNine", dataProvider = STUDENT_CREDENTIALS, dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
//    public void testValidateStudentAbleToWatchVideoStreamingOfGradeNineByUsingDB(String userId, String password, String userName, String signature, String cartNumber){
//        StudentsScreen studentsScreen = new StudentsScreen();
//        AbekaHomeScreen abekaHomeScreen = new AbekaHomeScreen();
//        loginToAbeka(userId, password, true).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
////        setStudentAccountDetailsFromDB(userId);
////        setStudentSubjectDetailsFromDB(userId);
//        studentsScreen.markAllVideosAsNotViewed();
//        dashboardScreen.waitAndCloseWidgetTourPopup();
//        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
//        studentsScreen.validateDigitalAssessmentsAreLockedOrNot();
//        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.HOME);
//        studentsScreen.navigateToStartWatchingYourLessonsLink().validateMyLessonsTodaySectionData().validateDigitalAssessmentsAreLockedOrNot()
//                .validateStudentShouldNotAbleToWatchNextDayLessonFromVideoLibrary()
//                .watchVideoAndValidateMyLessonsTodaySectionWithVideoLibrary()
//                .validateVideoLibraryVideoStatusWithDataBase().validateDigitalAssessmentsAreLockedOrNot();
//
//        abekaHomeScreen.navigateToHeaderBannerSubmenu(AbekaHome.DASHBOARD,AbekaHome.DIGITAL_ASSESSMENTS);
//        studentsScreen.validateDigitalAssessmentsAreLockedOrNot().logoutFromAbeka();
//        softAssertions.assertAll();
//    }
//
//        @DataRowNumber(fromDataRowNumber = "2", toDataRowNumber = "2")
//    @Test(testName = "validateCourseBeginDateFormatGradeOne", dataProvider = DataProviderName.PARENT_CREDENTIALS_GRADE_ONE,
//            dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
//    public void validateCourseBeginDateFormatGradeOne(String userId, String password, String signature, String customerNumber) {
//        enrollmentPurchaseGradeOne();
//        ParentAccountDetails parentAccountDetails = getParentAccountDetails(2);
//        userId = parentAccountDetails.getParentUserName();
//        password = parentAccountDetails.getParentPassword();
//        dashboardScreen = new DashboardScreen();
//        enrollmentsScreen = new EnrollmentsScreen();
//        StudentDetails studentDetails = StudentAccountDetails.getGradeOneAccreditedStudentDetails();
//        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();
//
//        loginToAbeka(userId, password, false).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
//        dashboardScreen.waitAndCloseWidgetTourPopup();
//        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
//        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_ONE_ACCREDITED)
//                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, true).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
//                .clickOnNextButton().validateCourseBeginDateFormat();
//        softAssertions.assertAll();
    //}
}
