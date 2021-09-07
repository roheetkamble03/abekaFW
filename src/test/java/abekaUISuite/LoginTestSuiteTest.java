package abekaUISuite;

import base.GenericAction;
import constants.*;
import dataProvider.DataProviders;
import dataProvider.DataRowNumber;
import elementConstants.*;
import org.testng.annotations.Test;
import pageObjects.AbekaHomeScreen;
import pageObjects.DashboardScreen;
import pageObjects.EnrollmentsScreen;
import utility.ParentAccountDetails;
import utility.RetryUtility;
import utility.StudentAccountDetails;

import java.util.ArrayList;
import java.util.Arrays;

import static elementConstants.ItemDetails.*;

public class LoginTestSuiteTest extends GenericAction {
    AbekaHomeScreen abekaHomeScreen;
    DashboardScreen dashboardScreen;
    EnrollmentsScreen enrollmentsScreen;

    @Test(testName = "Test-1", retryAnalyzer = RetryUtility.class)
    public void enrollmentPurchaseGradeOne() {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(1);
        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
        Product product = new Product();
        product.setProductTitle(GRADE_ONE.getItemName());
        product.setItemNumber(GRADE_ONE.getItemNumber());
        product.setPrice(GRADE_ONE.getPrice());
        product.setQuantity(GRADE_ONE.getQuantity());
        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));

        ArrayList productList = new ArrayList(Arrays.asList(product));

        abekaHomeScreen = loginToAbeka(parentAccountDetails.getParentUserName(), parentAccountDetails.getParentPassword());
        abekaHomeScreen.navigateToShopByGrade(AbekaHome.firstGrade).searchProduct(Enrollments.GRADE_ONE_ENROLLMENT).selectProduct(Enrollments.GRADE_ONE_ENROLLMENT).
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, BookDescription.accredited, CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(checkoutCriteria).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_ONE_ACCREDITED);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(testDataRowNumber = "1")
    @Test(testName = "Test-2", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeOne"})
    public void validateAddNewStudentGradeOneAccredited(String userId, String password, String userName, String signature) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeOneAccreditedStudentDetails();
        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_ONE_ACCREDITED)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, true).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().validateBeginDate().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().submitEnrollment().validateAllSetMessage().addStudentAccountDetailsToTestData(studentDetails);
        softAssertions.assertAll();
    }

    @Test(testName = "Test-3", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void enrollmentPurchaseGradeFour(String userId, String password, String userName, String signature) {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(2);
        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
        Product product = new Product();
        product.setProductTitle(GRADE_FOUR.getItemName());
        product.setItemNumber(GRADE_FOUR.getItemNumber());
        product.setPrice(GRADE_FOUR.getPrice());
        product.setQuantity(GRADE_FOUR.getQuantity());
        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));

        ArrayList productList = new ArrayList(Arrays.asList(product));

        abekaHomeScreen = loginToAbeka(userId, password);
        abekaHomeScreen.navigateToShopByGrade(AbekaHome.fourthGrade).searchProduct(Enrollments.GRADE_FOUR_ENROLLMENT).selectProduct(Enrollments.GRADE_FOUR_ENROLLMENT).
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, "", CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(checkoutCriteria).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_FOUR);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(testDataRowNumber = "2")
    @Test(testName = "Test-4", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeFour"})
    public void validateAddNewStudentGradeFour(String userId, String password, String userName, String signature) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeFourStudentDetails();

        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_FOUR)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, false).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().validateBeginDate().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().clickOnNextButton().fillProofOfCompletion(studentDetails.getGrade()).clickOnNextButton().fillAddClasses().clickOnNextButton().submitEnrollment().validateAllSetMessage().addStudentAccountDetailsToTestData(studentDetails);
        softAssertions.assertAll();
    }

    @Test(testName = "Test-5", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void enrollmentPurchaseGradeNine(String userId, String password, String userName, String signature) {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(3);
        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
        Product product = new Product();
        product.setProductTitle(GRADE_NINE.getItemName());
        product.setItemNumber(GRADE_NINE.getItemNumber());
        product.setPrice(GRADE_NINE.getPrice());
        product.setQuantity(GRADE_NINE.getQuantity());
        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));

        ArrayList productList = new ArrayList(Arrays.asList(product));

        abekaHomeScreen = loginToAbeka(userId, password);
        abekaHomeScreen.navigateToShopByGrade(AbekaHome.ninthGrade).searchProduct(Enrollments.GRADE_NINE_ENROLLMENT).selectProduct(Enrollments.GRADE_NINE_ENROLLMENT).
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, "", CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(checkoutCriteria).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_NINE);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(testDataRowNumber = "3")
    @Test(testName = "Test-6", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeNine"})
    public void validateAddNewStudentGradeNine(String userId, String password, String userName, String signature) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeNineStudentDetails();

        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_NINE)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, false).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().fillAvailableRecommendedCourses(Enrollments.GRADE_NINE).clickOnNextButton().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().clickOnNextButton().fillProofOfCompletion(studentDetails.getGrade()).clickOnNextButton().fillAddClasses().clickOnNextButton().submitEnrollment().validateAllSetMessage().addStudentAccountDetailsToTestData(studentDetails);
        softAssertions.assertAll();
    }

    @Test(testName = "Test-7", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void enrollmentPurchaseGradeTwelve(String userId, String password, String userName, String signature) {
        ParentAccountDetails parentAccountDetails = createAndGetParentAccountDetails(4);
        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
        Product product = new Product();
        product.setProductTitle(GRADE_TWELVE.getItemName());
        product.setItemNumber(GRADE_TWELVE.getItemNumber());
        product.setPrice(GRADE_TWELVE.getPrice());
        product.setQuantity(GRADE_TWELVE.getQuantity());
        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));

        ArrayList productList = new ArrayList(Arrays.asList(product));

        abekaHomeScreen = loginToAbeka(userId, password);
        abekaHomeScreen.navigateToShopByGrade(AbekaHome.twelfthGrade).searchProduct(Enrollments.GRADE_TWELVE_ENROLLMENT).selectProduct(Enrollments.GRADE_TWELVE_ENROLLMENT).
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, "", CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(checkoutCriteria).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_TWELVE);
        abekaHomeScreen.logoutFromAbeka();
        softAssertions.assertAll();
    }

    @DataRowNumber(testDataRowNumber = "4")
    @Test(testName = "Test-8", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class, dependsOnMethods = {"enrollmentPurchaseGradeTwelve"})
    public void validateAddNewStudentGradeTwelve(String userId, String password, String userName, String signature) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = StudentAccountDetails.getGradeTwelveStudentDetails();

        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_TWELVE)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails, false).clickOnNextButton().fillEnrollmentOptionsDetails(studentDetails, enrollmentOptions)
                .clickOnNextButton().fillAvailableRecommendedCourses(Enrollments.GRADE_TWELVE).clickOnNextButton().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().clickOnNextButton().fillProofOfCompletion(studentDetails.getGrade()).clickOnNextButton().fillAddClasses().clickOnNextButton().submitEnrollment().validateAllSetMessage().addStudentAccountDetailsToTestData(studentDetails);
        softAssertions.assertAll();
    }

    @DataRowNumber(testDataRowNumber = "0")
    @Test(testName = "Test-3", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void validateDashBoardWidgets(String userId, String password, String userName, String signature){
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.validateDashboardNewTab().validateMyOrdersWidgetLinks().validateVideoManualPdfsLinks().validateNotificationRows();;
        softAssertions.assertAll();
    }
}