package abekaUISuite;

import base.GenericAction;
import constants.*;
import dataProvider.DataProviders;
import elementConstants.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.AbekaHomeScreen;
import pageObjects.DashboardScreen;
import pageObjects.EnrollmentsScreen;
import utility.RetryUtility;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginTestSuiteTest extends GenericAction {
    AbekaHomeScreen abekaHomeScreen;
    DashboardScreen dashboardScreen;
    EnrollmentsScreen enrollmentsScreen;

    @Test(testName = "Test-1", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void enrollmentPurchase(String userId, String password, String userName) {
        CheckoutCriteria checkoutCriteria = new CheckoutCriteria();
        Product product = new Product();
        product.setProductTitle(ShoppingCart.GRADE_ONE_VIDEO_BOOKS_ENROLLMENT_ACCREDITED);
        product.setItemNumber(ShoppingCart.ITEM_NUMBER);
        product.setPrice(ShoppingCart.PRICE);
        product.setQuantity(ShoppingCart.QUANTITY);
        product.setSubtotal(getCalculatedSubTotal(product.getQuantity(), product.getPrice()));

        ArrayList productList = new ArrayList(Arrays.asList(product));

        abekaHomeScreen = loginToAbeka(userId, password, userName);
        abekaHomeScreen.navigateToShopByGrade().searchProduct(Enrollments.GRADE_ONE_ENROLLMENT).selectProduct(Enrollments.GRADE_ONE_ENROLLMENT).
                selectBookingCriteria(BookDescription.fullYear, BookDescription.videoAndBooks, BookDescription.accredited, CommonTexts.one).
                clickOnAddToCart();
        abekaHomeScreen.navigateToShoppingCartPage().validateProductInCart(productList).clickOnCheckOut().
                selectCheckoutCriteria(checkoutCriteria).
                clickOnPlaceOrder().clickOnFinishYourEnrollment().validateNewlyEnrolledCourses(Enrollments.GRADE_ONE_ACCREDITED);
        abekaHomeScreen.logoutFromAbeka();
    }

    @Test(testName = "Test-2", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void validateDashBoardWidgets(String userId, String password, String userName){
        dashboardScreen = new DashboardScreen();
        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.validateDashboardNewTab().validateMyOrdersWidgetLinks().validateVideoManualPdfsLinks().validateNotificationRows();;
    }

    @Test(testName = "Test-3", dataProvider = "parentCredentials", dataProviderClass = DataProviders.class, retryAnalyzer = RetryUtility.class)
    public void validateNewStudentCreation(String userId, String password, String userName) {
        dashboardScreen = new DashboardScreen();
        enrollmentsScreen = new EnrollmentsScreen();
        StudentDetails studentDetails = new StudentDetails();
        EnrollmentOptions enrollmentOptions = new EnrollmentOptions();

        loginToAbeka(userId, password, userName).navigateToAccountGreetingSubMenu(AbekaHome.DASHBOARD);
        dashboardScreen.waitAndCloseWidgetTourPopup();
        dashboardScreen.navigateToMyOrderLink(Dashboard.ENROLLMENTS);
        enrollmentsScreen.validateEnrollmentPageSection().openSectionLink(Enrollments.NEW,Enrollments.GRADE_ONE_ACCREDITED)
                .validateStudentPageHeader().goToAddNewStudentPage().fillAndSubmitNewStudentDetails(studentDetails).clickOnNextButton().fillEnrollmentOptionsDetails(enrollmentOptions)
                .clickOnNextButton().validateBeginDate().addBeginDate().clickOnNextButton().signEnrollmentAgreement(enrollmentOptions.getSignature())
                .clickOnNextButton().submitEnrollment().validateAllSetMessage();
    }
}